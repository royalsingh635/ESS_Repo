package chartofaccount.view.bean;


import chartofaccount.model.service.ChartOfAccountAMImpl;

import chartofaccount.model.view.CoaVOImpl;
import chartofaccount.model.view.FinCoaVOImpl;

import chartofaccount.model.view.FinCoaVORowImpl;
//import chartofaccount.model.view.OrgCoaVOImpl;
import chartofaccount.model.view.SearchVORowImpl;

import chartofaccount.model.view.views.OrgCoaVWVOImpl;

import com.sun.org.apache.xpath.internal.operations.And;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.LaunchPopupEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.ValidationException;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class Coa {
    private RichPopup adSearchPopup;
    private RichTable coaTable;
    private RichPopup addCoaPopup;
    private RichInputText createdDt;
    private static int VARCHAR = Types.VARCHAR;

    private RichInputText coaName;
    private RichInputText coaAlias;
    private RichSelectOneChoice coaAccId;

    private RichInputText coaLegCd;
    private RichSelectBooleanCheckbox balSheet;
    private RichSelectBooleanCheckbox trfBal;

    private RichSelectBooleanCheckbox pnl;
    private RichSelectBooleanCheckbox coaResv;
    private RichSelectBooleanCheckbox prtBudgt;
    private RichSelectBooleanCheckbox coaCf;
    private RichSelectBooleanCheckbox coaActv;
    private RichSelectOneChoice coaGrpType;
    private RichSelectOneChoice coaGrpBalType;
    private RichSelectOneChoice budjetCalcLogic;
    private RichPopup linkToOrgPopup;
    private RichRegion linkToOrgTf;
    private String taskFlowId = "/WEB-INF/LinkToOrgTF.xml#LinkToOrgTF";
    private RichInputText coaId;
    private RichRegion regionorgCoa;
    public Integer i = 0;
    private RichInputText test;
    private RichSelectBooleanCheckbox coaFluct;
    private RichSelectBooleanCheckbox bsItempopup;
    private RichSelectBooleanCheckbox pnlItemPopup;
    private RichPanelBox coaPbox;
    private RichPopup delpop;
    private RichSelectOneChoice coaGrpTypBind;
    private RichSelectOneChoice coaBalTypBind;
    private RichInputListOfValues coaCogIdFormBind;
    private RichInputListOfValues coaAltGrpIdBindForm;
    private RichInputListOfValues coaCogIdBindPopUp;
    private String mode = "V";
    private RichSelectBooleanCheckbox bgtLogicBind;
    private RichSelectOneChoice bdjectCalLogicLOVBind;
    private RichSelectBooleanCheckbox prtOfBdgtChkBxBind;
    private RichSelectBooleanCheckbox resRevenue;
    private RichSelectBooleanCheckbox roundAcc;
    private RichSelectBooleanCheckbox roundAccFormBind;
    ChartOfAccountAMImpl am;
    private RichInputListOfValues coaAccIdBindPopup;
    private Boolean searchDisable = false;

    private Integer count = (Integer) getBindings().getOperationBinding("on_load_page").execute();
    private RichInputText coa_name_bind;
    private static ADFLogger _adfLog = ADFLogger.createADFLogger(Coa.class);
    private RichSelectBooleanCheckbox coaCFBindVar;
    private RichSelectBooleanCheckbox tfrBalBindVar;
    private RichColumn coaNametableBind;
    private RichInputListOfValues searchAction;
    //  private RichInputListOfValues search;
    private RichPopup activeCoaPopupBinding;
    private String coaNameRound_exchng = null;
    private String defRoudOfValue = "N"; //this is default value of round of check box to use in validation.
    private String defExngFlu = "N"; //this is default value of exchange flucation checkbox to use in validation.
    private Boolean isInTrnx = false;
    private RichSelectBooleanCheckbox orgLinkCoaCHK;
    private RichTable orgTableBind;
    private RichPanelFormLayout searchFormBind;


    public Coa() {

    }


    public void showPopup(RichPopup popup, boolean visible) {
        try {

            am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");


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

    /*   public void setAdSearchPopup(RichPopup adSearchPopup) {
        this.adSearchPopup = adSearchPopup;
    }

    public RichPopup getAdSearchPopup() {
        return adSearchPopup;
    } */

    /*  public void onIndexSelected(ActionEvent actionEvent) {
        RichCommandLink linkPressed = (RichCommandLink)actionEvent.getSource();
        Key jboRowKey = (Key)linkPressed.getAttributes().get("jboRowKey");

        am = (ChartOfAccountAMImpl)resolvElDC("ChartOfAccountAMDataControl");
        ViewObjectImpl v = am.getFinCoa1();

        String[] s = v.getApplyViewCriteriaNames();
        for (int i = 0; i < s.length; i++) {
            v.removeApplyViewCriteriaName(s[i]);
        }
        v.executeQuery();


        v.setWhereClause("Coa_nm like'" + linkPressed.getText() + "%'");
        v.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(coaTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaPbox);
    }*/


    public void onIndexSelected(ActionEvent actionEvent) {
        RichCommandLink linkPressed = (RichCommandLink) actionEvent.getSource();
        //  Key jboRowKey = (Key)linkPressed.getAttributes().get("jboRowKey");

        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        ViewObjectImpl v = am.getFinCoa1();

        String[] s = v.getApplyViewCriteriaNames();
        if (s != null) {

            for (int i = 0; i < s.length; i++) {
                v.removeApplyViewCriteriaName(s[i]);
            }
            v.executeQuery();
        }


        v.setWhereClause("upper(Coa_nm) like'" + linkPressed.getText() + "%'");
        v.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(coaTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaPbox);


    }


    public void setCoaTable(RichTable coaTable) {
        this.coaTable = coaTable;
    }

    public RichTable getCoaTable() {
        return coaTable;
    }

    public void setAddCoaPopup(RichPopup addCoaPopup) {
        this.addCoaPopup = addCoaPopup;
    }

    public RichPopup getAddCoaPopup() {
        return addCoaPopup;
    }

    public void addCoa(ActionEvent actionEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert");
        createOpBAddress.execute();

        this.defExngFlu = "N";
        this.defRoudOfValue = "N";
        showPopup(addCoaPopup, true);
        this.mode = "A";

    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    //To resolve XML key to String

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void addDialogListner(DialogEvent dialogEvent) {
        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        String a = bsItempopup.getValue().toString();
        String pnl = pnlItemPopup.getValue().toString();
        //coaBalTypBind.setRequired(true);
        //coaGrpTypBind.setRequired(true);
        //coaCogIdBindPopUp.setRequired(true);

        ViewObjectImpl v = am.getFinCoa1();

        Row cRow = v.getCurrentRow();
        String coaNm = (String) cRow.getAttribute("CoaNm");
        if (coaNm == null) {
            System.out.println("null chk");
            FacesMessage message = new FacesMessage(":", resolvElDCMsg("#{bundle['MSG.153']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            int totalCount = v.getRowCount(); //get ALL rows
            int rangeSize = v.getRangeSize(); //all in range
            v.setRangeSize(totalCount);
            Row[] rArray = v.getAllRowsInRange();

            int count = 0;
            String currName = "";

            for (Row r : rArray) {

                if (!r.equals(cRow)) {
                    try {
                        currName = r.getAttribute("CoaNm").toString();
                    } catch (NullPointerException npe) {

                        currName = "";
                    }

                    if (currName.equalsIgnoreCase(coaNm)) {
                        count = count + 1;
                    }
                }

            }
            v.setRangeSize(rangeSize); //set to original range

            //Date date = new Date();
            //am = (ChartOfAccountAMImpl)resolvElDC("ChartOfAccountAMDataControl");
            Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer NA_ID = (Integer) am.getFinCoa1().getCurrentRow().getAttribute("CoaAccId");
            //date = (Date)am.getFinCoa1().getCurrentRow().getAttribute("UsrIdCreateDt");
            Timestamp date = new Timestamp(System.currentTimeMillis());

            String flag = "";
            try {
                flag = (String) callStoredFunction(VARCHAR, "CHECK_COA_CREATE_ALLOWED(?,?,?,?)", new Object[] {
                                                   NA_ID, HO_ORG_ID, CLD_ID, SLOC_ID
                });

                //System.out.println("MULTIPLE COA CREATE ALLOWED OR NOT :"+flag);

            } catch (Exception e) {
                //System.out.println("EXCEPTION IN CALLING FUNCTION :=>CHECK_COA_CREATE_ALLOWED");
            }


            if (a.equalsIgnoreCase("true") && pnl.equalsIgnoreCase("true")) {
                String msg1 = resolvElDCMsg("#{bundle['MSG.1']}").toString();


                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else if (a.equalsIgnoreCase("false") && pnl.equalsIgnoreCase("false")) {

                String msg2 = resolvElDCMsg("#{bundle['MSG.3']}").toString();
                FacesMessage message = new FacesMessage(msg2);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }

            else if (count > 0) {

                String msg2 = resolvElDCMsg("#{bundle['APP.COA.FinCOAMsgDuplicate.Label']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message2);
            } else if ((this.prtOfBdgtChkBxBind.getValue().equals(true)) &&
                       (this.bdjectCalLogicLOVBind.getValue() == "")) {

                FacesMessage message = new FacesMessage(":", resolvElDCMsg("#{bundle['MSG.224']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(this.bdjectCalLogicLOVBind.getClientId(), message);

            } else if (flag.equals("N")) {
                FacesMessage message =
                    new FacesMessage(":",
                                     resolvElDCMsg("#{bundle['MSG.228']}").toString()); //Multiple Ref in COA' for the selected NA is unchecked....
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                if (dialogEvent.getOutcome().name().equals("ok")) {

                    if (prtBudgt.getValue().toString().equalsIgnoreCase("false") &&
                        budjetCalcLogic.getValue().toString().length() > 0) {
                        FacesMessage message = new FacesMessage("Please select part of budgeting.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        String saveData = "Y";

                        _adfLog.info(bsItempopup.getValue().equals(false) + "----6------" +
                                     bsItempopup.getValue().toString());
                        _adfLog.info(coaGrpTypBind.getValue().toString() + "----1------" +
                                     "A".equalsIgnoreCase(coaGrpTypBind.getValue().toString()));
                        _adfLog.info(coaBalTypBind.getValue().toString() + "----2------" +
                                     "Cr".equalsIgnoreCase(coaBalTypBind.getValue().toString()));
                        _adfLog.info(pnlItemPopup.getValue().equals(true) + "----3------" +
                                     "Y".equalsIgnoreCase(pnlItemPopup.getValue().toString()));
                        _adfLog.info(coaCFBindVar.getValue().equals(true) + "----4------" +
                                     "Y".equalsIgnoreCase(coaCFBindVar.getValue().toString()));
                        _adfLog.info(tfrBalBindVar.getValue().equals(true) + "----5------" +
                                     "Y".equalsIgnoreCase(tfrBalBindVar.getValue().toString()));
                        if ((("A".equalsIgnoreCase(coaGrpTypBind.getValue().toString())) ||
                             ("E".equalsIgnoreCase(coaGrpTypBind.getValue().toString()))) &&
                            ("Cr".equalsIgnoreCase(coaBalTypBind.getValue().toString()))) {
                            _adfLog.info("ERROR = 1086 = Balance type for Asset/Expences should be Dr!!");
                            saveData = "N";
                            String msg1 = resolvEl("#{bundle['MSG.1086']}").toString();
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                        if ((("L".equalsIgnoreCase(coaGrpTypBind.getValue().toString())) ||
                             ("I".equalsIgnoreCase(coaGrpTypBind.getValue().toString()))) &&
                            ("Dr".equalsIgnoreCase(coaBalTypBind.getValue().toString()))) {
                            saveData = "N";
                            _adfLog.info("ERROR = 1087 = Balance type for Income/Liabilities should be Cr!!");
                            String msg1 = resolvEl("#{bundle['MSG.1087']}").toString();
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                        if ((("A".equalsIgnoreCase(coaGrpTypBind.getValue().toString())) ||
                             ("L".equalsIgnoreCase(coaGrpTypBind.getValue().toString()))) &&
                            (bsItempopup.getValue().equals(false))) {
                            saveData = "N";
                            _adfLog.info("ERROR = 1088 = COA should be a part of Balance Sheet for Assets/Liabilities!!");
                            String msg1 = resolvEl("#{bundle['MSG.1088']}").toString();
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                        if (("I".equalsIgnoreCase(coaGrpTypBind.getValue().toString())) ||
                            ("E".equalsIgnoreCase(coaGrpTypBind.getValue().toString()))) {
                            _adfLog.info(pnlItemPopup.getValue().equals(true) +
                                         "---------------------------------------" +
                                         "Y".equalsIgnoreCase(pnlItemPopup.getValue().toString()));
                            if (pnlItemPopup.getValue().equals(false) ||
                                "N".equalsIgnoreCase(pnlItemPopup.getValue().toString())) {
                                saveData = "N";
                                _adfLog.info("ERROR = 1089 = COA should be a part of Profit And loss for Income/Expences!!");
                                String msg1 = resolvEl("#{bundle['MSG.1089']}").toString();
                                FacesMessage message = new FacesMessage(msg1);
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            } else if (false) {
                                saveData = "N";
                                _adfLog.info("ERROR = 1090 = Income/Expences COA can not be a part of Cash Flow!!");
                                String msg1 = resolvEl("#{bundle['MSG.1090']}").toString();
                                FacesMessage message = new FacesMessage(msg1);
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            } else if (tfrBalBindVar.getValue().equals(true) ||
                                       "Y".equalsIgnoreCase(tfrBalBindVar.getValue().toString())) {
                                saveData = "N";
                                _adfLog.info("ERROR = 1091 = Income/Expences COA can not be a Transfer Balance Account!!");
                                String msg1 = resolvEl("#{bundle['MSG.1091']}").toString();
                                FacesMessage message = new FacesMessage(msg1);
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }
                        }
                        if ("Y".equalsIgnoreCase(saveData)) {

                            BindingContainer bindings = getBindings();
                            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                            operationBinding.execute();

                            this.isInTrnx = false;

                            String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                            Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));

                            String u = "N";

                            // ChartOfAccountAMImpl am = (ChartOfAccountAMImpl)resolvElDC("ChartOfAccountAMDataControl");

                            ViewObjectImpl v1 = am.getFinCoa1();
                            if (v1 != null) {
                                Row currentRow = v1.getCurrentRow();
                                if (currentRow != null) {

                                    DBSequence coaid = (DBSequence) currentRow.getAttribute("CoaId");
                                    if (coaid != null) {
                                        Integer coa = (Integer) coaid.getSequenceNumber().intValue();


                                        if (coa != null) {
                                            System.out.println("Current Coa Id --------" + coa);
                                            u = callStoredFunction(Types.VARCHAR,
                                                                   "fn_coa_operations(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                                   CLD_ID, SLOC_ID, HO_ORG_ID, coa, null, null, null,
                                                                   null, usrId, date, "I"
                                            }).toString();
                                        }
                                    }
                                }
                            }

                            if (u.equals("N"))
                                u = callStoredFunction2(Types.VARCHAR, "fn_coa_operations(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                        CLD_ID, SLOC_ID, HO_ORG_ID, null, null, null, null, null, usrId,
                                                        date, "I"
                                }).toString();
                            if ((u.equals("Y"))) {
                                BindingContainer bindingss = getBindings();
                                OperationBinding operationBindings = bindingss.getOperationBinding("Commit");
                                operationBindings.execute();
                                //  v.executeQuery();
                                if (operationBindings.getErrors().isEmpty()) {
                                    this.mode = "V";
                                    FacesMessage message =
                                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.225']}").toString()); //COA created Successfully!
                                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                }

                            } else {
                                /*  BindingContainer binding = getBindings();
                     OperationBinding operationBindin = binding.getOperationBinding("Execute");
                     operationBindin.execute();
                     OperationBinding operationBindin1 = binding.getOperationBinding("Rollback");
                     operationBindin1.execute();    */
                                FacesMessage message = new FacesMessage("Error in Post COA operations");
                                message.setSeverity(FacesMessage.SEVERITY_INFO);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }

                        }


                    }
                }
            }
        }

    }

    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.registerOutParameter(6, VARCHAR);
            st.executeUpdate();
            try {

                if (st.getObject(6) != null) {
                    setResult(st.getObject(6).toString());
                }
            } catch (NullPointerException sqle) {
                sqle.printStackTrace();
            }

            return st.getObject(1);

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    // System.out.println(e.getMessage());
                }
            }
        }
    }

    public void addDialogCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute();
        this.mode = "V";
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

    public void setCreatedDt(RichInputText createdDt) {
        this.createdDt = createdDt;
    }

    public RichInputText getCreatedDt() {
        return createdDt;
    }

    public void setCoaName(RichInputText coaName) {
        this.coaName = coaName;
    }

    public RichInputText getCoaName() {
        return coaName;
    }

    public void setCoaAlias(RichInputText coaAlias) {
        this.coaAlias = coaAlias;
    }

    public RichInputText getCoaAlias() {
        return coaAlias;
    }

    public void setCoaAccId(RichSelectOneChoice coaAccId) {
        this.coaAccId = coaAccId;
    }

    public RichSelectOneChoice getCoaAccId() {
        return coaAccId;
    }


    public void setCoaLegCd(RichInputText coaLegCd) {
        this.coaLegCd = coaLegCd;
    }

    public RichInputText getCoaLegCd() {
        return coaLegCd;
    }

    public void setBalSheet(RichSelectBooleanCheckbox balSheet) {
        this.balSheet = balSheet;
    }

    public RichSelectBooleanCheckbox getBalSheet() {
        return balSheet;
    }

    public void setTrfBal(RichSelectBooleanCheckbox trfBal) {
        this.trfBal = trfBal;
    }

    public RichSelectBooleanCheckbox getTrfBal() {
        return trfBal;
    }


    public void setPnl(RichSelectBooleanCheckbox pnl) {
        this.pnl = pnl;
    }

    public RichSelectBooleanCheckbox getPnl() {
        return pnl;
    }

    public void setCoaResv(RichSelectBooleanCheckbox coaResv) {
        this.coaResv = coaResv;
    }

    public RichSelectBooleanCheckbox getCoaResv() {
        return coaResv;
    }

    public void setPrtBudgt(RichSelectBooleanCheckbox prtBudgt) {
        this.prtBudgt = prtBudgt;
    }

    public RichSelectBooleanCheckbox getPrtBudgt() {
        return prtBudgt;
    }

    public void setCoaCf(RichSelectBooleanCheckbox coaCf) {
        this.coaCf = coaCf;
    }

    public RichSelectBooleanCheckbox getCoaCf() {
        return coaCf;
    }

    public void setCoaActv(RichSelectBooleanCheckbox coaActv) {
        this.coaActv = coaActv;
    }

    public RichSelectBooleanCheckbox getCoaActv() {
        return coaActv;
    }

    public String edit() {
        /* createdDt.setReadOnly(false);

        coaName.setReadOnly(false);
        coaAlias.setReadOnly(false);
        coaFluct.setReadOnly(false);
        coaCogIdFormBind.setReadOnly(false);
        coaAltGrpIdBindForm.setReadOnly(false);
        coaLegCd.setReadOnly(false);
        balSheet.setReadOnly(false);
        trfBal.setReadOnly(false);

        pnl.setReadOnly(false);
        coaResv.setReadOnly(false);
        prtBudgt.setReadOnly(false);
        coaCf.setReadOnly(false);
        coaActv.setReadOnly(false);

        coaGrpType.setReadOnly(false);
        coaGrpBalType.setReadOnly(false);
        budjetCalcLogic.setReadOnly(false);
       */

        //  store value of round of account and flucuation checkbox
        if (this.coaFluct.isSelected()) {

            System.out.println("acount is already seletedyed-------------------------------------------");
            this.defExngFlu = "Y";
        } else {
            this.defExngFlu = "N";
        }
        if (this.roundAcc.isSelected()) {
            System.out.println("acount is already seletedyed-------------------------------------------");
            this.defRoudOfValue = "Y";
        } else {
            this.defRoudOfValue = "N";
        }

        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        String coaId = am.getFinCoa1().getCurrentRow().getAttribute("CoaId").toString();
        String check = (String) callStoredFunction(Types.VARCHAR, "FIN.fn_is_coa_nm_editable(?,?,?,?)", new Object[] {
                                                   getCld(), getSloc(), getHo(), coaId
        });


        if (check.equalsIgnoreCase("Y")) {
            this.isInTrnx = true;

            this.coaName.setDisabled(false);
            this.coaAlias.setDisabled(false);
            this.coaCogIdFormBind.setDisabled(true); // this line is added to hnadle the visibility of buttons.
            this.coaActv.setDisabled(false);

        }

        else {

            this.isInTrnx = false;
            createdDt.setDisabled(false);

            coaName.setDisabled(false);
            coaAlias.setDisabled(false);
            coaFluct.setDisabled(false);
            coaCogIdFormBind.setDisabled(false);
            coaAltGrpIdBindForm.setDisabled(false);
            coaLegCd.setDisabled(false);
            balSheet.setDisabled(false);
            trfBal.setDisabled(false);

            pnl.setDisabled(false);
            //coaResv.setDisabled(false);
            prtBudgt.setDisabled(false);
            resRevenue.setDisabled(false);
            coaCf.setDisabled(false);
            coaActv.setDisabled(false);
            //search.setDisabled(true);
            coaGrpType.setDisabled(false);
            coaGrpBalType.setDisabled(false);
            budjetCalcLogic.setDisabled(false);
            roundAcc.setDisabled(false);

        }


        coaTable.setRowSelection(RichTable.ROW_SELECTION_NONE);
        this.mode = "E";
        this.searchDisable = true;

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.coaTable);

        return null;
    }

    /**
     * @param actionEvent for save operation
     */
    protected void callStoredProcedure(String stmt, Object[] bindVars) {
        PreparedStatement st = null;
        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");

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

    public void save(ActionEvent actionEvent) {

        Integer coa_id = 0;
        String saveData = "Y";
        String a = balSheet.getValue().toString();
        String pnl1 = pnl.getValue().toString();
        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        System.out.println("prtBudgt.getValue().toString() = " + prtBudgt.getValue().toString() +
                           " budjetCalcLogic.getValue() = " + budjetCalcLogic.getValue());

        if (prtBudgt.getValue().toString().equalsIgnoreCase("true") &&
            (budjetCalcLogic.getValue() == null || budjetCalcLogic.getValue() == "")) {
            // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,null, "Budget Calc Logic required when part of budgeting is selected"));
            FacesMessage message = new FacesMessage("Budget Calc Logic required when part of budgeting is selected");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (prtBudgt.getValue().toString().equalsIgnoreCase("false") &&
                   budjetCalcLogic.getValue().toString().length() > 0) {
            FacesMessage message = new FacesMessage("Please select part of budgeting.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            if ((a.equalsIgnoreCase("true") && pnl1.equalsIgnoreCase("true"))) {
                saveData = "N";
                String msg1 = resolvElDCMsg("#{bundle['MSG.1']}").toString();
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else if (a.equalsIgnoreCase("false") && pnl1.equalsIgnoreCase("false")) {
                saveData = "N";
                String msg2 = resolvElDCMsg("#{bundle['MSG.3']}").toString();
                FacesMessage message = new FacesMessage(msg2);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {

                // code for tigger apply by BL


                _adfLog.info(balSheet.getValue().equals(false) + "----61------" + balSheet.getValue().toString());
                _adfLog.info(coaGrpType.getValue().toString() + "----11------" +
                             "A".equalsIgnoreCase(coaGrpType.getValue().toString()));
                _adfLog.info(coaGrpBalType.getValue().toString() + "----21------" +
                             "Cr".equalsIgnoreCase(coaGrpBalType.getValue().toString()));
                _adfLog.info(pnl.getValue().equals(true) + "----31------" +
                             "Y".equalsIgnoreCase(pnl.getValue().toString()));
                _adfLog.info(coaCf.getValue().equals(true) + "----41------" +
                             "Y".equalsIgnoreCase(coaCf.getValue().toString()));
                _adfLog.info(trfBal.getValue().equals(true) + "----51------" +
                             "Y".equalsIgnoreCase(trfBal.getValue().toString()));
                if ((("A".equalsIgnoreCase(coaGrpType.getValue().toString())) ||
                     ("E".equalsIgnoreCase(coaGrpType.getValue().toString()))) &&
                    ("Cr".equalsIgnoreCase(coaGrpBalType.getValue().toString()))) {
                    _adfLog.info("ERROR = 1086 = Balance type for Asset/Expences should be Dr!!");
                    saveData = "N";
                    String msg1 = resolvEl("#{bundle['MSG.1086']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
                if ((("L".equalsIgnoreCase(coaGrpType.getValue().toString())) ||
                     ("I".equalsIgnoreCase(coaGrpType.getValue().toString()))) &&
                    ("Dr".equalsIgnoreCase(coaGrpBalType.getValue().toString()))) {
                    saveData = "N";
                    _adfLog.info("ERROR = 1087 = Balance type for Income/Liabilities should be Cr!!");
                    String msg1 = resolvEl("#{bundle['MSG.1087']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
                if ((("A".equalsIgnoreCase(coaGrpType.getValue().toString())) ||
                     ("L".equalsIgnoreCase(coaGrpType.getValue().toString()))) && (balSheet.getValue().equals(false))) {
                    saveData = "N";
                    _adfLog.info("ERROR = 1088 = COA should be a part of Balance Sheet for Assets/Liabilities!!");
                    String msg1 = resolvEl("#{bundle['MSG.1088']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
                if (("I".equalsIgnoreCase(coaGrpType.getValue().toString())) ||
                    ("E".equalsIgnoreCase(coaGrpType.getValue().toString()))) {
                    _adfLog.info(pnl.getValue().equals(true) + "---------------------------------------" +
                                 "Y".equalsIgnoreCase(pnl.getValue().toString()));
                    if (pnl.getValue().equals(false) || "N".equalsIgnoreCase(pnl.getValue().toString())) {
                        saveData = "N";
                        _adfLog.info("ERROR = 1089 = COA should be a part of Profit And loss for Income/Expences!!");
                        String msg1 = resolvEl("#{bundle['MSG.1089']}").toString();
                        FacesMessage message = new FacesMessage(msg1);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else if (false) {
                        saveData = "N";
                        _adfLog.info("ERROR = 1090 = Income/Expences COA can not be a part of Cash Flow!!");
                        String msg1 = resolvEl("#{bundle['MSG.1090']}").toString();
                        FacesMessage message = new FacesMessage(msg1);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else if (trfBal.getValue().equals(true) || "Y".equalsIgnoreCase(trfBal.getValue().toString())) {
                        saveData = "N";
                        _adfLog.info("ERROR = 1091 = Income/Expences COA can not be a Transfer Balance Account!!");
                        String msg1 = resolvEl("#{bundle['MSG.1091']}").toString();
                        FacesMessage message = new FacesMessage(msg1);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }

                if (saveData.equals("Y")) {
                    BindingContainer bindings = getBindings();
                    DCIteratorBinding parentIter = null;
                    Key parentKey = null;
                    try {
                        parentIter = (DCIteratorBinding) bindings.get("FinCoa1Iterator");
                        parentKey = parentIter.getCurrentRow().getKey();
                    } catch (Exception e) {
                        // System.out.println("Exception in getting rowKey.");
                    }


                    //Date date = new Date();
                    //am = (ChartOfAccountAMImpl)resolvElDC("ChartOfAccountAMDataControl");
                    Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                    String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
                    String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                    Integer NA_ID = (Integer) am.getFinCoa1().getCurrentRow().getAttribute("CoaAccId");
                    //date = (Date)am.getFinCoa1().getCurrentRow().getAttribute("UsrIdCreateDt");
                    Timestamp date = new Timestamp(System.currentTimeMillis());

                    String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                    Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));

                    String u = "N";

                    // ChartOfAccountAMImpl am = (ChartOfAccountAMImpl)resolvElDC("ChartOfAccountAMDataControl");

                    ViewObjectImpl v1 = am.getFinCoa1();
                    if (v1 != null) {
                        Row currentRow = v1.getCurrentRow();
                        if (currentRow != null) {

                            DBSequence coaid = (DBSequence) currentRow.getAttribute("CoaId");
                            if (coaid != null) {
                                Integer coa = (Integer) coaid.getSequenceNumber().intValue();


                                if (coa != null) {
                                    System.out.println("Current Coa Id --------" + coa);
                                    u = callStoredFunction(Types.VARCHAR, "fn_coa_operations(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                           CLD_ID, SLOC_ID, HO_ORG_ID, coa, null, null, null, null,
                                                           usrId, date, "I"
                                    }).toString();
                                }
                            }
                        }
                    }

                    if (u.equals("N"))
                        u = callStoredFunction2(Types.VARCHAR, "fn_coa_operations(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                CLD_ID, SLOC_ID, HO_ORG_ID, null, null, null, null, null, usrId, date,
                                                "I"
                        }).toString();
                    if ((u.equals("Y") && "Y".equalsIgnoreCase(saveData))) {
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();

                        this.isInTrnx = false;
                        try {
                            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                        } catch (Exception e) {
                            // System.out.println("Exception in setting rowKey.");
                        }

                        ViewObjectImpl v = am.getFinCoa1();
                        if (operationBinding.getErrors().isEmpty()) {
                            String coaNm = v.getCurrentRow().getAttribute("CoaNm").toString();
                            String msg =
                                resolvElDCMsg("#{bundle['MSG.226']}").toString() + " " + coaNm + " " +
                                resolvElDCMsg("#{bundle['MSG.227']}").toString();

                            FacesMessage message = new FacesMessage(msg);
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);

                            /* createdDt.setReadOnly(true);
                    coaFluct.setReadOnly(true);
                    coaName.setReadOnly(true);
                    coaAlias.setReadOnly(true);

                    coaCogIdFormBind.setReadOnly(true);
                    coaAltGrpIdBindForm.setReadOnly(true);
                    coaLegCd.setReadOnly(true);
                    balSheet.setReadOnly(true);
                    trfBal.setReadOnly(true);

                    pnl.setReadOnly(true);
                    coaResv.setReadOnly(true);
                    prtBudgt.setReadOnly(true);
                    coaCf.setReadOnly(true);
                    coaActv.setReadOnly(true);
                    coaGrpType.setReadOnly(true);
                    coaGrpBalType.setReadOnly(true);
                    budjetCalcLogic.setReadOnly(true); */


                            createdDt.setDisabled(true);
                            coaFluct.setDisabled(true);
                            coaName.setDisabled(true);
                            coaAlias.setDisabled(true);

                            coaCogIdFormBind.setDisabled(true);
                            coaAltGrpIdBindForm.setDisabled(true);
                            coaLegCd.setDisabled(true);
                            balSheet.setDisabled(true);
                            trfBal.setDisabled(true);

                            pnl.setDisabled(true);
                            coaResv.setDisabled(true);
                            prtBudgt.setDisabled(true);
                            resRevenue.setDisabled(true);
                            coaCf.setDisabled(true);
                            coaActv.setDisabled(true);
                            coaGrpType.setDisabled(true);
                            coaGrpBalType.setDisabled(true);
                            budjetCalcLogic.setDisabled(true);
                            roundAcc.setDisabled(true);
                            coaTable.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
                            this.searchDisable = false;
                            this.mode = "V";

                        }

                    } else {
                        /*  BindingContainer binding = getBindings();
                OperationBinding operationBindin = binding.getOperationBinding("Execute");
                operationBindin.execute();
                OperationBinding operationBindin1 = binding.getOperationBinding("Rollback");
                operationBindin1.execute();    */
                        FacesMessage message = new FacesMessage("Error in Post COA operations");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }


            }
        }
        /*  String cldId = resolvEl("GLBL_APP_CLD_ID");
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        FinCoaVOImpl coa1 = am.getFinCoa1();
        coa_id = (Integer)coa1.getCurrentRow().getAttribute("CoaId");
        System.out.println("Mode is inside save---->" + mode);
        callStoredProcedure("PK_REPLICATE.PROC_REF_FIN_COA(?,?,?,?,?)",
                            new Object[] { slocid, orgid, cldId, coa_id, mode });
        System.out.println("Procedure called"); */
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

    public void cancel(ActionEvent actionEvent) {

        this.searchDisable = false;

        BindingContainer bindings = getBindings();

        DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("FinCoa1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
        createOpBAddress.execute();


        OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute");
        createOpBAddress1.execute();

        this.isInTrnx = false;

        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

        /*
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute(); */

        /* createdDt.setReadOnly(true);
        coaFluct.setReadOnly(true);
        coaGrpType.setReadOnly(true);
        coaGrpBalType.setReadOnly(true);
        budjetCalcLogic.setReadOnly(true);
        coaName.setReadOnly(true);
        coaAlias.setReadOnly(true);
        coaCogIdFormBind.setReadOnly(true);
        coaAltGrpIdBindForm.setReadOnly(true);

        coaLegCd.setReadOnly(true);
        balSheet.setReadOnly(true);
        trfBal.setReadOnly(true);

        pnl.setReadOnly(true);
        coaResv.setReadOnly(true);
        prtBudgt.setReadOnly(true);
        coaCf.setReadOnly(true);
        coaActv.setReadOnly(true);
        */

        createdDt.setDisabled(true);
        coaFluct.setDisabled(true);
        coaName.setDisabled(true);
        coaAlias.setDisabled(true);

        coaCogIdFormBind.setDisabled(true);
        coaAltGrpIdBindForm.setDisabled(true);
        coaLegCd.setDisabled(true);
        balSheet.setDisabled(true);
        trfBal.setDisabled(true);

        pnl.setDisabled(true);
        coaResv.setDisabled(true);
        prtBudgt.setDisabled(true);
        resRevenue.setDisabled(true);
        coaCf.setDisabled(true);
        coaActv.setDisabled(true);
        coaGrpType.setDisabled(true);
        coaGrpBalType.setDisabled(true);
        budjetCalcLogic.setDisabled(true);
        roundAcc.setDisabled(true);
        coaTable.setRowSelection(RichTable.ROW_SELECTION_SINGLE);

        this.mode = "V";
    }

    public void setCoaGrpType(RichSelectOneChoice coaGrpType) {
        this.coaGrpType = coaGrpType;
    }

    public RichSelectOneChoice getCoaGrpType() {
        return coaGrpType;
    }

    public void setCoaGrpBalType(RichSelectOneChoice coaGrpBalType) {
        this.coaGrpBalType = coaGrpBalType;
    }

    public RichSelectOneChoice getCoaGrpBalType() {
        return coaGrpBalType;
    }

    public void setBudjetCalcLogic(RichSelectOneChoice budjetCalcLogic) {
        this.budjetCalcLogic = budjetCalcLogic;
    }

    public RichSelectOneChoice getBudjetCalcLogic() {
        return budjetCalcLogic;
    }


    public void setLinkToOrgPopup(RichPopup linkToOrgPopup) {
        this.linkToOrgPopup = linkToOrgPopup;
    }

    public RichPopup getLinkToOrgPopup() {
        return linkToOrgPopup;
    }

    public void resetTable(ActionEvent actionEvent) {
        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        ViewObjectImpl v = am.getFinCoa1();
        v.setWhereClause(null);
        String[] s = v.getApplyViewCriteriaNames();
        if (s != null) {
            System.out.println("S is " + s);
            System.out.println("No of view criteria" + s.length);
            for (int i = 0; i < s.length; i++) {
                v.removeApplyViewCriteriaName(s[i]);
            }
        }
        v.executeQuery();


    }


    /*   public void setLinkToOrgTf(RichRegion linkToOrgTf) {
        this.linkToOrgTf = linkToOrgTf;
    }

    public RichRegion getLinkToOrgTf() {
        return linkToOrgTf;

    } */

    /*   public String linkToOrgButton() {
        coaId.isChanged();
        TaskFlowId t = getDynamicTaskFlowId();

        linkToOrgTf.setVisible(true);
        return null;
    } */


    /*  public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public String linkToOrgTF() {
        taskFlowId = "/WEB-INF/LinkToOrgTF.xml#LinkToOrgTF";
        return null;
    } */

    public void coaIdValueChangeListener(ValueChangeEvent valueChangeEvent) {


    }

    public void setCoaId(RichInputText coaId) {
        this.coaId = coaId;
    }

    public RichInputText getCoaId() {
        return coaId;
    }

    public void setRegionorgCoa(RichRegion regionorgCoa) {
        this.regionorgCoa = regionorgCoa;
    }

    public RichRegion getRegionorgCoa() {
        return regionorgCoa;
    }

    public void setTest(RichInputText test) {
        this.test = test;
    }

    public RichInputText getTest() {
        return test;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getI() {
        return i;
    }

    public void saveAdd(ActionEvent actionEvent) {
        /* System.out.println("prtBudgt.getValue().toString() = "+prtBudgt.getValue().toString() +" budjetCalcLogic.getValue() = "+budjetCalcLogic.getValue());

        if(prtBudgt.getValue().toString().equalsIgnoreCase("Y") && budjetCalcLogic.getValue() == null ){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,null, "Budget Calc Logic required when part of budgeting is selected"));
        }
        else{ */


        if ((this.prtBudgt.isSelected() && this.budjetCalcLogic.getValue() != null) ||
            ((!this.prtBudgt.isSelected()) && this.budjetCalcLogic.getValue() == null)) {
            Long t1 = System.currentTimeMillis();
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            Long t2 = System.currentTimeMillis();

            System.out.println("Total time " + (t2 - t1));

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();
        } else {


            String msg1 = resolvEl("#{bundle['MSG.240']}").toString();
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

    }

    public String resetRecordsForInEdit() {

        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        ViewObjectImpl v = am.getFinCoa1();
        Row row = v.getCurrentRow();
        Integer i = Integer.parseInt(row.getAttribute("CoaAccId").toString());

        Integer cogId = Integer.parseInt(row.getAttribute("CoaCogId").toString());

        ViewObjectImpl cogVO = am.getLovCog1();
        ViewObjectImpl naVo = am.getLovNa1();
        naVo.setWhereClause(null);
        cogVO.setWhereClause(null);
        cogVO.executeQuery();
        naVo.executeQuery();
        cogVO.setWhereClause("COG_ID = '" + cogId + "'");
        naVo.setWhereClause("ACC_ID = " + i + "");
        cogVO.executeQuery();
        naVo.executeQuery();
        RowIterator ritNa = naVo.createRowSetIterator(null);
        RowIterator ritcog = cogVO.createRowSetIterator(null);
        Row rowNA = ritNa.first();
        Row rowCog = ritcog.first();


        //String prtOfEm = rowNA.getAttribute("PartOfEm").toString();
        String prtOfBdgt = rowNA.getAttribute("PartOfBdgt").toString();


        String cfItem = rowCog.getAttribute("CogCfItem").toString();
        String bsItem = rowCog.getAttribute("CogBsItem").toString();
        String pnl = rowCog.getAttribute("CogPnlItem").toString();
        //  row.setAttribute("PartOfEm", prtOfEm);
        row.setAttribute("PartOfBdgt", prtOfBdgt);
        row.setAttribute("CoaCfItem", cfItem);
        row.setAttribute("CoaBsItem", bsItem);
        row.setAttribute("CoaPnlItem", pnl);
        v.executeQuery();
        return null;
    }

    public void setCoaFluct(RichSelectBooleanCheckbox coaFluct) {
        this.coaFluct = coaFluct;
    }

    public RichSelectBooleanCheckbox getCoaFluct() {
        return coaFluct;
    }

    public void setBsItempopup(RichSelectBooleanCheckbox bsItempopup) {
        this.bsItempopup = bsItempopup;
    }

    public RichSelectBooleanCheckbox getBsItempopup() {
        return bsItempopup;
    }

    public void setPnlItemPopup(RichSelectBooleanCheckbox pnlItemPopup) {
        this.pnlItemPopup = pnlItemPopup;
    }

    public RichSelectBooleanCheckbox getPnlItemPopup() {
        return pnlItemPopup;
    }

    public void coaNameValidatorForm(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().trim().length() > 0) {
            String coaNm = object.toString();


            //String nameDesc=object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;

            char[] xx = coaNm.toCharArray();
            if (xx[0] == '%' || xx[0] == '$' || xx[0] == '&') {

                String msg2 = resolvElDCMsg("#{bundle['MSG.9']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            for (char c : xx) {

                if (c == '(') {

                    openB = openB + 1;

                } else if (c == ')') {

                    closeB = closeB + 1;

                }

                if (closeB > openB) {
                    closeFg = true; /**1.no. of closed brackets will not be more than open brackets at any given time.*/
                }
            }

            /**2.if openB=0 then no. of closing and opening brackets equal ||
                     * 3.opening bracket must always come before closing brackets
                     * 4.closing brackets must not come before first occurrence of openning bracket
                    */
            if (openB != closeB || closeFg == true || (coaNm.lastIndexOf("(") > coaNm.lastIndexOf(")")) ||
                (coaNm.indexOf(")") < coaNm.indexOf("("))) {

                String msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            /**5.check for empty brackets
                     */
            if (coaNm.contains("()")) {

                String msg2 = resolvElDCMsg("#{bundle['MSG.49']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**check for to dot not comes together
                     */
            if (coaNm.contains("..")) {

                String msg2 = resolvElDCMsg("#{bundle['MSG.109']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**  check for wrong combo
                     */
            if (coaNm.contains("(.") || coaNm.contains("(-") || coaNm.contains("-)")) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.59']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            /**check for valid coanm .. Starts with character. can have dots .No two dots/:/'/'/_/@ can be adjacent.
                         *
                         */
            /* String expression =
                "^(?:(?>[A-Za-z0-9 % $)]+)(?:\\_|\\&|\\-|\\(|\\.|\\)|\\:|\\@|\\/|\\\\(?!\\_|\\&|\\%|\\-|\\:|\\@|\\/|\\\\|$))?)*$";*/
            String expression =
                "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\&(?!\\.|\\&|$))*(\\.|\\:(?!\\.|\\:|$))*" +
                "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\%(?!\\.|\\%))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";

            CharSequence inputStr = coaNm;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);

            String error = resolvElDCMsg("#{bundle['MSG.1185']}").toString();

            if (matcher.matches()) {
            } else {
                FacesMessage message = new FacesMessage("", error);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }


            //check for duplicate rows
            am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
            ViewObjectImpl v = am.getFinCoa1();

            Row cRow = v.getCurrentRow();
            DBSequence coa = (DBSequence) cRow.getAttribute("CoaId");
            if (coa != null) {
                int coaId = coa.getSequenceNumber().intValue();
                Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
                String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

                String res = (String) callStoredFunction(VARCHAR, "fin.fn_is_coa_nm_unique(?,?,?,?,?)", new Object[] {
                                                         CLD_ID, SLOC_ID, HO_ORG_ID, coaNm, coaId
                });

                System.out.println("result in bean = " + res);

                if (res.equalsIgnoreCase("N")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate record",
                                                                  null));
                }
            }
            /*
            int totalCount = v.getRowCount(); //get ALL rows
            int rangeSize = v.getRangeSize(); //all in range
            v.setRangeSize(totalCount);
            Row[] rArray = v.getAllRowsInRange();

            int count = 0;
            String currName = "";

            for (Row r : rArray) {

                if (!r.equals(cRow)) {
                    try {
                        currName = r.getAttribute("CoaNm").toString();
                    } catch (NullPointerException npe) {

                        currName = "";
                    }

                    if (currName.equalsIgnoreCase(coaNm)) {
                        count = count + 1;
                    }
                }

            }
            v.setRangeSize(rangeSize); //set to original range

            if (count > 0) {
                String msg2 = resolvElDCMsg("#{bundle['APP.COA.FinCOAMsgDuplicate.Label']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            } */

        }

    }

    public void coaNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().trim().length() > 0) {
            String coaNm = object.toString();


            //String nameDesc=object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;

            char[] xx = coaNm.toCharArray();
            if (xx[0] == '%' || xx[0] == '$' || xx[0] == '&') {

                String msg2 = resolvElDCMsg("#{bundle['MSG.9']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            for (char c : xx) {

                if (c == '(') {

                    openB = openB + 1;

                } else if (c == ')') {

                    closeB = closeB + 1;

                }

                if (closeB > openB) {
                    closeFg = true; /**1.no. of closed brackets will not be more than open brackets at any given time.*/
                }
            }

            /**2.if openB=0 then no. of closing and opening brackets equal ||
                 * 3.opening bracket must always come before closing brackets
                 * 4.closing brackets must not come before first occurrence of openning bracket
                */
            if (openB != closeB || closeFg == true || (coaNm.lastIndexOf("(") > coaNm.lastIndexOf(")")) ||
                (coaNm.indexOf(")") < coaNm.indexOf("("))) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            /**5.check for empty brackets
                 */
            if (coaNm.contains("()")) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.49']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**check for to dot not comes together
                 */
            if (coaNm.contains("..")) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.109']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**  check for wrong combo
                 */
            if (coaNm.contains("(.") || coaNm.contains("(-") || coaNm.contains("-)")) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.59']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            /**check for valid coanm .. Starts with character. can have dots .No two dots/:/'/'/_/@ can be adjacent.
                     *
                     */
            /*  String expression =
                "^(?:(?>[A-Za-z0-9 % $)]+)(?:\\_|\\&|\\-|\\(|\\.|\\)|\\:|\\@|\\/|\\\\(?!\\_|\\&|\\%|\\-|\\:|\\@|\\/|\\\\|$))?)*$";*/

            String expression =
                "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\&(?!\\.|\\&|$))*(\\.|\\:(?!\\.|\\:|$))*" +
                "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\%(?!\\.|\\%))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";


            CharSequence inputStr = coaNm;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.1185']}").toString();

            if (matcher.matches()) {
            } else {
                FacesMessage message = new FacesMessage("", error);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

                /* FacesMessage message = new FacesMessage("",error);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(coa_name_bind.getClientId(), message);*/
                //  throw new ValidatorException(message);

                //  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }


            //check for duplicate rows
            Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

            String res = (String) callStoredFunction(VARCHAR, "fin.fn_is_coa_nm_unique(?,?,?,?,?)", new Object[] {
                                                     CLD_ID, SLOC_ID, HO_ORG_ID, coaNm, null
            });
            /*   BindingContainer bc = getBindings();
            OperationBinding op = bc.getOperationBinding("coaValidator");
            op.getParamsMap().put("naName", coaNm);
            op.execute();
            Integer res = (Integer)op.getResult(); */
            System.out.println("result in bean = " + res);

            if (res.equalsIgnoreCase("N")) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate record", null));
            }
        }
    }

    public void setCoaPbox(RichPanelBox coaPbox) {
        this.coaPbox = coaPbox;
    }

    public RichPanelBox getCoaPbox() {
        return coaPbox;
    }

    public void deleteAction(ActionEvent actionEvent) {

        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        String coaId = am.getFinCoa1().getCurrentRow().getAttribute("CoaId").toString();
        Integer check = (Integer) callStoredFunction(Types.INTEGER, "FIN.DELETE_COA(?,?,?,?)", new Object[] {
                                                     getCld(), getSloc(), getHo(), coaId
        });
        if (check.intValue() == 0) {


            showPopup(delpop, true);

        } else {

            ViewObjectImpl v = am.getFinCoa1();
            String coaNm = v.getCurrentRow().getAttribute("CoaNm").toString();

            String msg =
                resolvElDCMsg("#{bundle['MSG.226']}").toString() + coaNm +
                resolvElDCMsg("#{bundle['MSG.229']}").toString();
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        }


    }


    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
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
                    throw new JboException(e);
                }
            }
        }
    }


    protected void refreshPage() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String refreshpage = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
        UIV.setViewId(refreshpage);
        fctx.setViewRoot(UIV);
    }


    public void setDelpop(RichPopup delpop) {
        this.delpop = delpop;
    }

    public RichPopup getDelpop() {
        return delpop;
    }

    public void delDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {

            am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
            ViewObjectImpl v = am.getFinCoa1();
            if (v != null) {
                Row currentRow = v.getCurrentRow();
                if (currentRow != null) {
                    String coaNm = currentRow.getAttribute("CoaNm").toString();
                    DBSequence coaid = (DBSequence) currentRow.getAttribute("CoaId");
                    if (coaid != null) {
                        Integer coa = (Integer) coaid.getSequenceNumber().intValue();


                        if (coa != null) {
                            Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                            String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
                            String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                            Timestamp date = new Timestamp(System.currentTimeMillis());
                            Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));


                            String u = callStoredFunction(Types.VARCHAR, "fn_coa_operations(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                          CLD_ID, SLOC_ID, HO_ORG_ID, coa, null, null, null, null,
                                                          usrId, date, "D"
                            }).toString();
                            if ((u.equals("Y"))) {
                                BindingContainer bindings = getBindings();
                                OperationBinding operationBinding = bindings.getOperationBinding("Delete");
                                operationBinding.execute();
                                BindingContainer bindings1 = getBindings();
                                OperationBinding operationBinding1 = bindings1.getOperationBinding("Commit");
                                operationBinding1.execute();
                                BindingContainer binding = getBindings();
                                OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
                                createOpBAddress.execute();

                                if (operationBinding1.getErrors().isEmpty() && operationBinding.getErrors().isEmpty()) {
                                    String msg =
                                        resolvElDCMsg("#{bundle['MSG.226']}").toString() + coaNm +
                                        resolvElDCMsg("#{bundle['MSG.230']}").toString(); //deleted successfully
                                    FacesMessage message = new FacesMessage(msg);
                                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                    this.mode = "V";
                                }


                            } else {

                                FacesMessage message = new FacesMessage("Error in Post COA operations");
                                message.setSeverity(FacesMessage.SEVERITY_INFO);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }
                        }
                    }
                }

            }


        }

    }


    public void setCoaGrpTypBind(RichSelectOneChoice coaGrpTypBind) {
        this.coaGrpTypBind = coaGrpTypBind;
    }

    public RichSelectOneChoice getCoaGrpTypBind() {
        return coaGrpTypBind;
    }

    public void setCoaBalTypBind(RichSelectOneChoice coaBalTypBind) {
        this.coaBalTypBind = coaBalTypBind;
    }

    public RichSelectOneChoice getCoaBalTypBind() {
        return coaBalTypBind;
    }


    public void CoaAccIdValueChangeListener(ValueChangeEvent valueChangeEvent) {

        String coa_nm = (String) valueChangeEvent.getNewValue();
        System.out.println(" -------------- > " + coa_nm);

        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        ViewObjectImpl vo = am.getFinCoa1();
        Row row = vo.getCurrentRow();

        System.out.println("before setting coa id");
        row.setAttribute("CoaNm", coa_nm);
        System.out.println("after setting coa id");

        //coaBalTypBind.setRequired(false);
        //coaGrpTypBind.setRequired(false);
        //coaCogIdBindPopUp.setRequired(false);
    }

    public void setCoaCogIdFormBind(RichInputListOfValues coaCogIdFormBind) {
        this.coaCogIdFormBind = coaCogIdFormBind;
    }

    public RichInputListOfValues getCoaCogIdFormBind() {
        return coaCogIdFormBind;
    }

    public void setCoaAltGrpIdBindForm(RichInputListOfValues coaAltGrpIdBindForm) {
        this.coaAltGrpIdBindForm = coaAltGrpIdBindForm;
    }

    public RichInputListOfValues getCoaAltGrpIdBindForm() {
        return coaAltGrpIdBindForm;
    }

    public void setCoaCogIdBindPopUp(RichInputListOfValues coaCogIdBindPopUp) {
        this.coaCogIdBindPopUp = coaCogIdBindPopUp;
    }

    public RichInputListOfValues getCoaCogIdBindPopUp() {
        return coaCogIdBindPopUp;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void exchangeFluctuationValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {

        Boolean val = (Boolean) object;
        String value = "";
        String coaNm = "";
        if (val == true) {
            value = "Y";
        } else if (val == false) {
            value = "N";
        }

        String msg4 = "";
        if (val == true) {


            String validate = "N";
            try {
                System.out.println("--------------------------------value is " + this.defExngFlu);
                if (this.defExngFlu.equalsIgnoreCase("N")) {

                    //validate = (BigDecimal) callStoredFunction(Types.NUMERIC, "fin.fn_get_coa_count_ex_fluc(?,?,?)", new Object[] {getCld(),getSloc(),getHo()});

                    validate = (String) callStoredFunction3(Types.VARCHAR, "fin.fn_is_exchangFlu_acc_exist(?,?,?,?)", new Object[] {
                                                            getCld(), getSloc(), getHo()
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (validate.equalsIgnoreCase("Y")) {


                msg4 =
                    "another account named " + this.coaNameRound_exchng + " " +
                    resolvEl("#{bundle['MSG.232']}").toString();

                String msg2 = resolvEl("#{bundle['MSG.233']}").toString();

                FacesMessage message2 = new FacesMessage(msg4, msg2);

                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }


        }


    }

    public void setBgtLogicBind(RichSelectBooleanCheckbox bgtLogicBind) {
        this.bgtLogicBind = bgtLogicBind;
    }

    public RichSelectBooleanCheckbox getBgtLogicBind() {
        return bgtLogicBind;
    }

    public void setBdjectCalLogicLOVBind(RichSelectOneChoice bdjectCalLogicLOVBind) {
        this.bdjectCalLogicLOVBind = bdjectCalLogicLOVBind;
    }

    public RichSelectOneChoice getBdjectCalLogicLOVBind() {
        return bdjectCalLogicLOVBind;
    }

    public void setPrtOfBdgtChkBxBind(RichSelectBooleanCheckbox prtOfBdgtChkBxBind) {
        this.prtOfBdgtChkBxBind = prtOfBdgtChkBxBind;
    }

    public RichSelectBooleanCheckbox getPrtOfBdgtChkBxBind() {
        return prtOfBdgtChkBxBind;
    }

    public void setResRevenue(RichSelectBooleanCheckbox resRevenue) {
        this.resRevenue = resRevenue;
    }

    public RichSelectBooleanCheckbox getResRevenue() {
        return resRevenue;
    }

    public void reserveRevValid(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object.equals(true)) {
            am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
            ViewObjectImpl v = am.getFinCoa1();
            FinCoaVOImpl coa1 = am.getFinCoaVO1();
            Row cRow = v.getCurrentRow();
            //String cogId = valueChangeEvent.getNewValue().toString();
            String cognm = "";
            String COGID = "";
            try {
                AdfFacesContext.getCurrentInstance().addPartialTarget(coaCogIdBindPopUp);
                cognm = this.coaCogIdBindPopUp.getValue().toString();
            } catch (NullPointerException npe) {
            }

            if (cognm != null) {
                Row[] xx = am.getLovCog1().getFilteredRows("CogNm", cognm);
                if (xx.length > 0) {
                    COGID = xx[0].getAttribute("CogId").toString();
                }
            }


            //String COGID = this.coaCogIdBindPopUp.getValue().toString();
            if (!COGID.equals("")) {
                char[] array = COGID.toCharArray();
                char cogIdFirstDigit = array[0];

                if (!((cogIdFirstDigit == '2') || (cogIdFirstDigit == '1'))) {


                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.234']}").toString() +
                                                                  resolvEl("#{bundle['MSG.235']}").toString(), null));

                } else {

                }
            }
            coa1.setNamedWhereClauseParam("CoaCldIdBind", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
            coa1.setNamedWhereClauseParam("SlocIdBind", resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            coa1.setNamedWhereClauseParam("CoaHoOrgIdBind", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));
            coa1.executeQuery();
            Row[] row = coa1.getFilteredRows("CoaRr", "Y");
            if (row.length > 0) {
                for (Row r : row) {
                    if (r.getAttribute("CoaId").toString().equals(v.getCurrentRow().getAttribute("CoaId").toString()) &&
                        r.getAttribute("CoaSlocId").toString().equals(v.getCurrentRow().getAttribute("CoaSlocId").toString()) &&
                        r.getAttribute("CoaHoOrgId").toString().equals(v.getCurrentRow().getAttribute("CoaHoOrgId").toString()) &&
                        r.getAttribute("CoaCldId").toString().equals(v.getCurrentRow().getAttribute("CoaCldId").toString())) {

                    } else {
                       
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                     "COA- " +r.getAttribute("CoaNm").toString()+ " is already selected for Reserved Revenue account !",
                                                                      null));

                    }
                }
            }
        }

    }

    public void reservedRevenueFormVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //System.out.println("OBJECT :"+object);
        if (object.equals(true)) {
            am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
            ViewObjectImpl v = am.getFinCoa1();
            FinCoaVOImpl coa1 = am.getFinCoaVO1();
            Row cRow = v.getCurrentRow();
            //String cogId = valueChangeEvent.getNewValue().toString();
            String cognm = "";
            String COGID = "";
            AdfFacesContext.getCurrentInstance().addPartialTarget(coaCogIdFormBind);

            try {
                cognm = this.coaCogIdFormBind.getValue().toString();
            } catch (NullPointerException npe) {
            }
            if (cognm != null) {
                Row[] xx = am.getLovCog1().getFilteredRows("CogNm", cognm);
                if (xx.length > 0) {
                    COGID = xx[0].getAttribute("CogId").toString();
                }
            }


            //String COGID = this.coaCogIdBindPopUp.getValue().toString();
            //System.out.println("COG_ID :"+COGID);
            if (!COGID.equals("")) {
                char[] array = COGID.toCharArray();
                char cogIdFirstDigit = array[0];
                //System.out.println("FIRST DIGIT OF COG_ID :"+cogIdFirstDigit);
                if (!((cogIdFirstDigit == '2') || (cogIdFirstDigit == '1'))) {

                    // System.out.println("NOT EQUAL TO 1 OR 2 : SO DISABLED");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.234']}").toString() +
                                                                  resolvEl("#{bundle['MSG.235']}").toString(), null));

                } else {
                    //System.out.println("EQUALS TO 1 OR 2 : SO ENABLED");
                }
            }


            coa1.setNamedWhereClauseParam("CoaCldIdBind", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
            coa1.setNamedWhereClauseParam("SlocIdBind", resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            coa1.setNamedWhereClauseParam("CoaHoOrgIdBind", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));
            coa1.executeQuery();
            Row[] row = coa1.getFilteredRows("CoaRr", "Y");
            if (row.length > 0) {
                for (Row r : row) {
                    if (r.getAttribute("CoaId").toString().equals(v.getCurrentRow().getAttribute("CoaId").toString()) &&
                        r.getAttribute("CoaSlocId").toString().equals(v.getCurrentRow().getAttribute("CoaSlocId").toString()) &&
                        r.getAttribute("CoaHoOrgId").toString().equals(v.getCurrentRow().getAttribute("CoaHoOrgId").toString()) &&
                        r.getAttribute("CoaCldId").toString().equals(v.getCurrentRow().getAttribute("CoaCldId").toString())) {

                    } else {
                                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                     "COA- " +r.getAttribute("CoaNm").toString()+" is already selected Reserved Revenue account !",
                                                                      null));

                    }
                }

            }

        }
    }

    public void pnlVCL(ValueChangeEvent vce) {
        //System.out.println(vce.getNewValue().toString());
        if (vce.getNewValue().equals("true")) {
            this.pnl.setDisabled(false);
            this.trfBal.setDisabled(true);
            //System.out.println("if new value :"+vce.getNewValue());
        } else if (vce.getNewValue().equals("false")) {
            this.pnl.setDisabled(false);
            this.trfBal.setDisabled(false);
            //System.out.println("else new value :"+vce.getNewValue());
        }
    }

    public void coaRoundVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Boolean val = (Boolean) object;
        String value = "";
        String coaNm = "";
        if (val == true) {
            value = "Y";
        } else if (val == false) {
            value = "N";
        }
        System.out.println("VALUE : " + val + " " + value);


        if (val == true) {


            //BigDecimal count=new BigDecimal(0);
            String validate = "N";
            try {

                //count = (BigDecimal) callStoredFunction(Types.NUMERIC, "fin.fn_get_coa_count_roundoff(?,?,?)", new Object[] {getCld(),getSloc(),getHo()});
                System.out.println("--------------------------------value is " + this.defRoudOfValue);
                if (this.defRoudOfValue.equalsIgnoreCase("N")) {
                    validate = (String) callStoredFunction3(Types.VARCHAR, "fin.fn_is_roundoff_acc_exist(?,?,?,?)", new Object[] {
                                                            getCld(), getSloc(), getHo()
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String msg4 = "";

            if (validate.equalsIgnoreCase("Y")) {
                msg4 =
                    "Other Coa named  " + this.getCoaNameRound_exchng() + " " +
                    resolvEl("#{bundle['MSG.244']}").toString();
                String msg2 = resolvEl("#{bundle['MSG.243']}").toString();

                FacesMessage message2 = new FacesMessage(msg4, msg2);

                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }


        }


    }

    public void setRoundAcc(RichSelectBooleanCheckbox roundAcc) {
        this.roundAcc = roundAcc;
    }

    public RichSelectBooleanCheckbox getRoundAcc() {
        return roundAcc;
    }

    public void setRoundAccFormBind(RichSelectBooleanCheckbox roundAccFormBind) {
        this.roundAccFormBind = roundAccFormBind;
    }

    public RichSelectBooleanCheckbox getRoundAccFormBind() {
        return roundAccFormBind;
    }

    /**
     * Validation of unique NA_ACC and COGTYPE
     */
    public void coaGrpIdVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {

        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        ViewObjectImpl v2 = am.getFinCoa1();
        Row currRow = v2.getCurrentRow();
        //System.out.println("VALIDATOR---------------------------------- : ");
        if (object != null && this.coaAccIdBindPopup.getValue() != null && currRow.getAttribute("CoaAccId") != null) {
            //  FinCoaVOImpl coavo = am.getFinCoa1();
            RowQualifier rowQualifier = new RowQualifier(v2);
            String CogId = "";
            Row[] xx = am.getLovCog1().getFilteredRows("CogNm", object.toString());
            // Row [] xx=am.getLovInputItmId1().getFilteredRowsInRange("ItmId", inputitm);
            // System.out.println("xx.length : "+xx.length);
            if (xx.length > 0) {
                CogId = xx[0].getAttribute("CogId").toString();
            }
            // filtering data using method setWhereClause
            if (!CogId.equals("")) {
                rowQualifier.setWhereClause("CoaAccId=" + (Integer) currRow.getAttribute("CoaAccId") +
                                            " and CoaCogId='" + CogId + "'");
                Row[] rowI = v2.getFilteredRows(rowQualifier);
                // To get the current row
                /*  System.out.println("CoaAccId = " + (Integer)currRow.getAttribute("CoaAccId"));
                        System.out.println("CoaCogId = " + CogId);
                        System.out.println("_COUNT2 : "+rowI.length); */
                if (rowI.length >
                    1) {
                    //System.out.println("_COUNT3 : "+rowI.length);
                    FacesMessage message2 =
          new FacesMessage("COA with this NATURAL ACCOUNT and ACCOUNT GROUP combination already exist.",
                           "Only one COA can be created for one NATURAL ACCOUNT and ACCOUNT GROUP combination.");
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            }


        } else {
            FacesMessage message2 = new FacesMessage("", "Account type cannot be null.");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }
    }

    public void setCoaAccIdBindPopup(RichInputListOfValues coaAccIdBindPopup) {
        this.coaAccIdBindPopup = coaAccIdBindPopup;
    }

    public RichInputListOfValues getCoaAccIdBindPopup() {
        return coaAccIdBindPopup;
    }

    public void setSearchDisable(Boolean searchDisable) {
        this.searchDisable = searchDisable;
    }

    public Boolean getSearchDisable() {
        return searchDisable;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCoa_name_bind(RichInputText coa_name_bind) {
        this.coa_name_bind = coa_name_bind;
    }

    public RichInputText getCoa_name_bind() {
        return coa_name_bind;
    }

    public void setCoaCFBindVar(RichSelectBooleanCheckbox coaCFBindVar) {
        this.coaCFBindVar = coaCFBindVar;
    }

    public RichSelectBooleanCheckbox getCoaCFBindVar() {
        return coaCFBindVar;
    }

    public void setTfrBalBindVar(RichSelectBooleanCheckbox tfrBalBindVar) {
        this.tfrBalBindVar = tfrBalBindVar;
    }

    public RichSelectBooleanCheckbox getTfrBalBindVar() {
        return tfrBalBindVar;
    }

    public String searchAction() {
        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        ViewObjectImpl search = am.getSearchVO1();

        if (search != null) {
            Row currentRow = search.getCurrentRow();
            if (currentRow != null) {
                String coaName = (String) currentRow.getAttribute("Coaname");
                if (coaName != null) {
                    BindingContainer bc = getBindings();
                    OperationBinding op = bc.getOperationBinding("searchAction");
                    op.getParamsMap().put("coaname", coaName);
                    op.execute();
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaNametableBind);
        return null;
    }

    public String resetAction() {
        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        ViewObjectImpl search = am.getSearchVO1();

        if (search != null) {
            Row currentRow = search.getCurrentRow();
            if (currentRow != null) {
                BindingContainer bc = getBindings();
                OperationBinding op = bc.getOperationBinding("resetAction");
                op.execute();
                this.resetValueInputItems(AdfFacesContext.getCurrentInstance(), this.searchFormBind);


            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(coaNametableBind);
        return null;
    }


    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }


    public void coaNameLaunchPopUpListner(LaunchPopupEvent launchPopupEvent) {
        BindingContainer bc = getBindings();
        OperationBinding op = bc.getOperationBinding("filter");
        op.execute();
    }

    public void naValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String naName = object.toString();
            BindingContainer bc = getBindings();
            OperationBinding op = bc.getOperationBinding("NaValidator");
            op.getParamsMap().put("naName", naName);
            op.execute();
            Integer res = (Integer) op.getResult();
            System.out.println("result in bean = " + res);

            if (res != null && res == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Coa for this Account already exists! Please select another NA",
                                                              null));
            }
        }
    }

    public void partOfBudgetVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("valueChangeEvent.getNewValue().toString() = " + valueChangeEvent.getNewValue().toString());
        String temp = "";
        if (valueChangeEvent.getNewValue() != null) {
            temp = valueChangeEvent.getNewValue().toString();
        }
        System.out.println("temp" + temp);

        if (temp.equalsIgnoreCase("false")) {
            budjetCalcLogic.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(budjetCalcLogic);

        }

        //valueChangeEvent.getComponent().p
        AdfFacesContext.getCurrentInstance().addPartialTarget(budjetCalcLogic);

    }

    public void partOfBudgetValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*  if(prtBudgt.getValue().toString().equalsIgnoreCase("true") && object == null){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,null,"selection required"));
            } */


    }

    public void BudgetCalcLogicVCL(ValueChangeEvent vce) {


        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().length() > 0) {
                prtBudgt.setValue(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(prtBudgt);
            } else {
                prtBudgt.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(prtBudgt);
            }
        } else {
            prtBudgt.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prtBudgt);
        }


    }

    public void BudgetCalcLogicPopVCL(ValueChangeEvent vcee) {
        if (vcee.getNewValue() != null) {
            if (vcee.getNewValue().toString().length() > 0) {
                prtOfBdgtChkBxBind.setValue(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(prtOfBdgtChkBxBind);
            } else {
                prtOfBdgtChkBxBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(prtOfBdgtChkBxBind);
            }
        } else {
            prtOfBdgtChkBxBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prtOfBdgtChkBxBind);
        }
    }

    public void setCoaNametableBind(RichColumn coaNametableBind) {
        this.coaNametableBind = coaNametableBind;
    }

    public RichColumn getCoaNametableBind() {
        return coaNametableBind;
    }

    public void setSearchAction(RichInputListOfValues searchAction) {
        this.searchAction = searchAction;
    }

    public RichInputListOfValues getSearchAction() {
        return searchAction;
    }


    public void setActiveCoaPopupBinding(RichPopup activeCoaPopupBinding) {
        this.activeCoaPopupBinding = activeCoaPopupBinding;
    }

    public RichPopup getActiveCoaPopupBinding() {
        return activeCoaPopupBinding;
    }

    public void coaActivePopupDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
        }
    }

    public String activeCoaAtion() {
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        //  String orgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");


        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        if (am != null) {
            FinCoaVOImpl coa1 = am.getFinCoa1();
            if (coa1 != null) {
                Row currentRow = coa1.getCurrentRow();
                if (currentRow != null) {
                    DBSequence coaIdInDB = (DBSequence) currentRow.getAttribute("CoaId");
                    if (coaIdInDB != null) {
                        Number number = coaIdInDB.getSequenceNumber();
                        if (number != null) {
                            Integer coaId = new Integer(number.intValue());
                            if (coaId != null) {
                                OrgCoaVWVOImpl coa = am.getOrgCoaVW1();
                                if (coa != null) {
                                    coa.setNamedWhereClauseParam("BindCldId", cldId);
                                    coa.setNamedWhereClauseParam("BindSlocId", slocId);
                                    coa.setNamedWhereClauseParam("BindHoOrgId", HO_ORG_ID);
                                    coa.setNamedWhereClauseParam("BindCoaId", coaId);
                                    coa.executeQuery();

                                    showPopup(activeCoaPopupBinding, true);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public String selectAllOrgAction() {
        BindingContainer bc = getBindings();
        OperationBinding binding = bc.getOperationBinding("checkAllOrgCoa");
        binding.execute();
        return null;
    }

    public String unCheckAllOrgAction() {
        BindingContainer bc = getBindings();
        OperationBinding binding = bc.getOperationBinding("UnCheckAllOrgCoa");
        binding.execute();
        if (binding.getResult() != null) {
            if (((String) binding.getResult()).equalsIgnoreCase("N")) {
                this.showMessage("There are some Organization which coa cant be made inactive");
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgTableBind);


        return null;
    }

    public void activeCoaValueChangeListner(ValueChangeEvent valueChangeEvent) {
        System.out.println("valueChangeEvent.getNewValue().toString() = " + valueChangeEvent.getNewValue().toString());
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("false")) {

        } else {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                FacesContext.getCurrentInstance().addMessage(valueChangeEvent.getComponent().getClientId(),
                                                             new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                                              "Important Information",
                                                                              resolvElDCMsg("#{bundle['MSG.1331']}").toString()));
            }
        }


    }
    private String result;

    public void activeCancelListner(PopupCanceledEvent popupCanceledEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        this.isInTrnx = false;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void coaActivateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            System.out.println("object = " + object);
            if (object.toString().equalsIgnoreCase("true")) {
                String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                //  String orgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
                Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
                String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
                FinCoaVOImpl coa1 = am.getFinCoa1();
                if (coa1 != null) {
                    Row currentRow = coa1.getCurrentRow();
                    if (currentRow != null) {
                        DBSequence coaIdInDB = (DBSequence) currentRow.getAttribute("CoaId");
                        if (coaIdInDB != null) {
                            Number number = coaIdInDB.getSequenceNumber();
                            if (number != null) {
                                Integer coaId = new Integer(number.intValue());

                                if (coaId != null) {
                                    String result1 =
                                        (String) callStoredFunction2(VARCHAR, "fn_coa_allow_act(?,?,?,?,?)", new Object[] {
                                                                     cldId, slocId, HO_ORG_ID, coaId
                                    });
                                    System.out.println("rsult is = " + result1 +
                                                       "---------------> when parameters are:- cldId = " + cldId +
                                                       " slocId = " + slocId + " HO_ORG_ID = " + HO_ORG_ID +
                                                       "coaId = " + coaId);
                                    if (result1.equalsIgnoreCase("N")) {
                                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                                      result, null));
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (object.toString().equalsIgnoreCase("false")) {
                String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
                String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");


                FinCoaVOImpl coa1 = am.getFinCoa1();
                if (coa1 != null) {
                    Row currentRow = coa1.getCurrentRow();
                    if (currentRow != null) {
                        DBSequence coaIdInDB = (DBSequence) currentRow.getAttribute("CoaId");
                        if (coaIdInDB != null) {
                            Number number = coaIdInDB.getSequenceNumber();
                            if (number != null) {
                                Integer coaId = new Integer(number.intValue());

                                if (coaId != null) {
                                    String result =
                                        (String) callStoredFunction(Types.VARCHAR, "fn_chk_coa_validity(?,?,?,?)", new Object[] {
                                                                    cldId, slocId, HO_ORG_ID, coaId
                                    });

                                    if (result.equalsIgnoreCase("N")) {
                                        FacesMessage message =
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Check the box",
                                                             "Coa Can't be made Inactive for all Organization.");

                                        throw new ValidatorException(message);
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public String getHo() {


        String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");

        return HO_ORG_ID;


    }

    public String getCld() {
        String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        return CLD_ID;
    }

    public Integer getSloc() {
        Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        return SLOC_ID;
    }

    public void coaCogIdTrnsVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaGrpType);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.coaGrpBalType);
    }

    public void listenGrpChangeVCE(ValueChangeEvent valueChangeEvent) {

        AdfFacesContext.getCurrentInstance().addPartialTarget(coaGrpTypBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaBalTypBind);
    }


    protected Object callStoredFunction3(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        am = (ChartOfAccountAMImpl) resolvElDC("ChartOfAccountAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.registerOutParameter(5, VARCHAR);
            st.executeUpdate();
            try {

                if (st.getObject(5) != null) {
                    // setResult(st.getObject(5).toString());
                    this.setCoaNameRound_exchng(st.getObject(5).toString());
                }
            } catch (NullPointerException sqle) {
                sqle.printStackTrace();
            }

            return st.getObject(1);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    // System.out.println(e.getMessage());
                }
            }
        }
    }

    public void setCoaNameRound_exchng(String coaNameRound_exchng) {
        this.coaNameRound_exchng = coaNameRound_exchng;
    }

    public String getCoaNameRound_exchng() {
        return coaNameRound_exchng;
    }

    public void setDefRoudOfValue(String defRoudOfValue) {
        this.defRoudOfValue = defRoudOfValue;
    }

    public String getDefRoudOfValue() {
        return defRoudOfValue;
    }

    public void setDefExngFlu(String defExngFlu) {
        this.defExngFlu = defExngFlu;
    }

    public String getDefExngFlu() {
        return defExngFlu;
    }

    public void setIsInTrnx(Boolean isInTrnx) {
        this.isInTrnx = isInTrnx;
    }

    public Boolean getIsInTrnx() {
        return isInTrnx;
    }


    public void orgMadeInactiveValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //Object orgId = uIComponent.getAttributes().get("orgId");
        UIComponent parent = uIComponent.getParent();
        Object orgId = null;
        try {
            orgId = parent.getAttributes().get("orgId");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("value is     ----------" + object);
        System.out.println("Current id of component is---------" + uIComponent.getClientId());
        Boolean b = null;
        if (object.getClass().getName().equalsIgnoreCase("java.lang.String")) {
            if (((String) object).equalsIgnoreCase("Y")) {
                b = true;
            } else {
                b = false;
            }

        } else {
            b = (Boolean) object;
        }


        if (!b) {
            System.out.println("Current org id------------" + orgId);
            String result = this.am.validateOrgMadeInactiveSingleCoa((String) orgId);
            RichSelectBooleanCheckbox chk = null;
            if (result.equalsIgnoreCase("Y")) {

            } else if (result.equalsIgnoreCase("N")) {


                AdfFacesContext.getCurrentInstance().addPartialTarget(uIComponent);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "You Cant Inactive this org to current coa", null));

            } else {
                System.out.println("in Exception");


                AdfFacesContext.getCurrentInstance().addPartialTarget(uIComponent);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception", null));

            }

        }

    }

    public void setOrgLinkCoaCHK(RichSelectBooleanCheckbox orgLinkCoaCHK) {
        this.orgLinkCoaCHK = orgLinkCoaCHK;
    }

    public RichSelectBooleanCheckbox getOrgLinkCoaCHK() {
        return orgLinkCoaCHK;
    }


    public void showMessage(String s) {

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null));

    }

    public void setOrgTableBind(RichTable orgTableBind) {
        this.orgTableBind = orgTableBind;
    }

    public RichTable getOrgTableBind() {
        return orgTableBind;
    }

    public void setSearchFormBind(RichPanelFormLayout searchFormBind) {
        this.searchFormBind = searchFormBind;
    }

    public RichPanelFormLayout getSearchFormBind() {
        return searchFormBind;
    }

}
