

/***
 * @author- Rohitashwa Barthwal, Ashish Kumar, Priyank Khare , Amandeep Shrivastava
 * Don't dare to tamper this class unnecessarily and without permission of Project Manager,
 * Handle with extreme care
 * */
package tempVoucherList.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
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

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.ContextInfoEvent;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.PollEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import tempVoucherList.model.services.TempVoucherAMImpl;
import tempVoucherList.model.views.LovLatestCurrImpl;
import tempVoucherList.model.views.TvouLineChqVOImpl;
import tempVoucherList.model.views.TvouLineChqVORowImpl;
import tempVoucherList.model.views.TvouLinesVOImpl;
import tempVoucherList.model.views.TvouLinesVORowImpl;
import tempVoucherList.model.views.TvouVOImpl;
import tempVoucherList.model.views.TvouVORowImpl;
import tempVoucherList.model.views.template.TmplVouVOImpl;


public class TempVoucherDtlForm {
    private String reqAllw;
    private String onAcntpaymntAlwAllw;
    private Object tvouRow;

    public void setOnAcntpaymntAlwAllw(String onAcntpaymntAlwAllw) {
        this.onAcntpaymntAlwAllw = onAcntpaymntAlwAllw;
    }

    public String getOnAcntpaymntAlwAllw() {
        OperationBinding createOpBAddress = getBindings().getOperationBinding("OnAcntPaymntAllowed");
        createOpBAddress.execute();
        return createOpBAddress.getResult().toString();
    }

    private RichTable taxRuleLineBillBinding;

    public void setReqAllw(String reqAllw) {
        this.reqAllw = reqAllw;
    }

    public String getReqAllw() {
        OperationBinding createOpBAddress = getBindings().getOperationBinding("reqAllowed");
        createOpBAddress.execute();
        return createOpBAddress.getResult().toString();
    }
    private boolean chqAvailable;
    private boolean rend_create = false;
    private static int NUMBER = Types.NUMERIC;
    private RichInputText vouId;
    private Integer doc_no = 55;
    private RichInputText totalAmt;
    private RichInputText basic_amt;
    private static final int VARCHAR = Types.VARCHAR;
    private RichSelectBooleanCheckbox advance;
    private RichSelectBooleanCheckbox adjustment;
    private RichInputText debitAmt;
    private RichInputText creditAmt;
    private Boolean taxYN;
    private Boolean tdsYN;
    private Number totalCredit;
    private Number totalDebit;
    private Number totalBsCredit;
    private Number totalBsDebit;
    private Number voucherAmount;
    private Boolean adjustCrDrNote;
    private Boolean unsaved;
    private Integer DocId = 55;
    private Integer DocEntType = 5055;
    private String weStatus;
    private String mode = modeGet();
    private String fnTxnMode = "S";
    private Integer fwdUserId;
    private Integer fwdUserLvl;
    private String fwdComment;
    private String fwdActivity;
    private Integer fwdWfId;
    private Boolean saveAsGl;
    private RichRegion region;
    private String templVouId;
    private String docDisplayId;
    private RichPopup fileImportPopUp;
    private UploadedFile tvouLnFile;
    private RichSelectOneChoice chartOfAc;
    private Boolean narrationCopy = false;
    private RichPopup tmplVPopup;
    private RichTable touLinesTab;
    private static final String amName = "TempVoucherAMDataControl";
    private Boolean form = false;
    private Boolean table = true;
    private String cost_center_src = "H";
    private Integer cost_center_slno = 0;
    private BigDecimal cost_center_amount = new BigDecimal(0);
    private String workFlwId;
    private String checkBoxTax;
    private String checkBoxTds;
    private RichSelectBooleanCheckbox morA;
    private RichInputText chequeNo;
    private RichTable chequeTable;
    private String fillPolicy = "M";
    private Integer Docid = 55;
    private RichInputText chqBukNo;
    private Integer chqSlNo;
    private Integer chqBkNo;

    private RichInputText amount;
    private RichInputText totalAmount;
    private RichSelectOneChoice instrmntTypBind;
    private boolean cancelTaxDis;
    private boolean cancelTdsDis;

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); //formatting date
    java.util.Date date = new java.util.Date();
    String currentDate = dateFormat.format(date); //to get current date in string
    private oracle.jbo.domain.Date voucherDate = getCurrentDate(currentDate);
    private RichSelectBooleanCheckbox txdtl;
    private RichSelectBooleanCheckbox tdsDtl;
    private RichInputText coaNameDetail;
    private RichSelectBooleanCheckbox manualTaxBox;
    private RichSelectBooleanCheckbox manualTdsBox;
    private RichRegion wfRegion;
    private RichPopup costCenterPopup;
    private RichPopup importPopup; //to set value of voucher date
    private boolean importsave = true;

    private RichPopup provisionalPopUp;
    private RichInputText tvouCcBind;
    private RichPopup chequeDeletePopUp;
    private RichPopup globalMessagePopUp;
    String org_id = resolvEl("#{pageFlowScope.ParamOrgId}");
    String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
    Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
    String HoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID1}");
    private String globalMsg = "";
    String returnFromWf = "I";
    private RichPopup tdsDetailBillPoUp;
    private boolean taxChangeFlag;
    private RichSelectBooleanCheckbox addChequeBind;
    Integer amt_digit = Integer.parseInt(resolvElAmt("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());
    Number act_bgt_amt;
    String act_bgt_typ;
    private RichPopup budgetWarnPopUpbind;
    private RichInputDate dueDateBind;
    private RichSelectOneChoice tvouTypBind;
    private RichPopup taxDetailBillPopUpBind;
    private boolean bilDtVsbl;
    private RichInputListOfValues coaNmTransBind;
    private RichSelectOneRadio statusChqBind;
    private RichSelectBooleanCheckbox autoAmtTransBind;
    private RichSelectOneChoice tvouIdBind;
    private boolean modeCheque = true;
    private RichSelectOneChoice taxRuleIdBind;
    private boolean isCurridDsbl;
    private RichInputText billAmtSpBind;
    private RichInputText billAmtBsBind;
    private Integer paramCount;
    private RichInputDate dueDateBillBind;
    private boolean firstLn;
    private boolean hdrDisable;
    private boolean chqRequired;
    private RichInputText chequeAmountBind;
    private Integer coaType;
    private RichInputText assessableValBind;
    private RichCommandButton createTemplateButton;
    private RichPopup hdrCoaDtlPopup;
    // variables for hdrCOA
    private String totDr;
    private String totCr;
    private RichPopup duplicateWarningBinding;
    private RichSelectOneChoice intimationIdBinding;
    private RichSelectOneChoice refCoaBinding;
    private RichPopup deletePopupBinding;
    private String isBillLineExist = "N";
    private String mnlImprstTaxAlw = "Y";
    private String alwTaxPayInTvou = "N";

    public void setAlwTaxPayInTvou(String alwTaxPayInTvou) {
        this.alwTaxPayInTvou = alwTaxPayInTvou;
    }

    public String getAlwTaxPayInTvou() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        Row[] row = am.getReqAlwVO1().getAllRowsInRange();
        String alwTaxPayInTvou = (String) row[0].getAttribute("AlwTaxPayInTvou");
    System.out.println("Tvou allow check for tax payment"+alwTaxPayInTvou);
        return alwTaxPayInTvou;
    }

    public void setMnlImprstTaxAlw(String mnlImprstTaxAlw) {
        this.mnlImprstTaxAlw = mnlImprstTaxAlw;
    }

    public String getMnlImprstTaxAlw() {
        mnlImprstTaxAlw = (String) ADFBeanUtils.callBindingsMethod("manAlwImprst", null, null);
        return mnlImprstTaxAlw;
    }
    private String mnlImprstTdsAlw = "Y";

    public void setMnlImprstTdsAlw(String mnlImprstTdsAlw) {
        this.mnlImprstTdsAlw = mnlImprstTdsAlw;
    }

    public String getMnlImprstTdsAlw() {
        mnlImprstTdsAlw = (String) ADFBeanUtils.callBindingsMethod("manAlwImprstTDS", null, null);
        return mnlImprstTdsAlw;
    }

    public void setIsBillLineExist(String isBillLineExist) {
        this.isBillLineExist = isBillLineExist;
    }

    public String getIsBillLineExist() {
        Object res = ADFBeanUtils.callBindingsMethod("totalBill", null, null);
        if (res != null)
            return res.toString();
        return "N";
    }
    private String isMultiOrg = "N";

    public void setIsMultiOrg(String isMultiOrg) {
        this.isMultiOrg = isMultiOrg;
    }

    public String getIsMultiOrg() {
        //        Object res = ADFBeanUtils.callBindingsMethod("isMultiOrg", null, null);
        //        if(res!=null){
        //            return res.toString();
        //        }
        return isMultiOrg;
    }

    public void setTotDr(String totDr) {
        this.totDr = totDr;
    }

    public String getTotDr() {
        return totalCrInString(getTotalDebit());
        //   return totDr;
    }

    public void setTotCr(String totCr) {
        this.totCr = totCr;
    }

    public String getTotCr() {
        return totalCrInString(getTotalCredit());
        //   return totCr;
    }

    private RichPanelLabelAndMessage templatePanel;
    private RichCommandLink manualAdjLnk;
    private RichCommandLink autoAdjLnk;
    private RichPopup instrumentAmtValidatePopUp;
    OperationBinding op = null;
    private RichTable billDtlTab;
    private RichTable bankInstrumntTab;
    public String adjDataFetchDisable = "Y"; //to be used in parameter to decide data fetch enable/disable
    public Integer manualTax;
    public Integer manualTds;
    public String oldManualTax;
    public String oldManualTds;
    private RichCommandImageLink saveBinding;
    private RichCommandImageLink saveAndForwardBinding;
    private RichSelectOneChoice instrumentTypeBinding;
    private RichInputText exemptedFlagBinding;
    private RichCommandLink addInstrumentDetailBinding;
    private RichPopup specificAmountInfopopupBinding;
    private RichInputText billNumberBinding;
    private String ModeExpense = getMode();
    private RichPopup ocPopupBinding;
    private RichPanelFormLayout ocFormBinding;
    private RichSelectOneChoice coaocbinding;
    private RichPopup taxPopupBinding;
    private RichPanelCollection ocPanelCollectionBinding;
    private RichTable ocTableBinding;
    private RichLink canceltaxButtonBinding;
    private RichPopup tdsPopupBinding;
    private RichLink cancelTdsBinding;
    private RichLink cancelocBinding;
    private RichLink submitocBinding;
    private Number zero = new Number(0);
    private RichInputText coaIdBinding;
    String GLBL_CLD_ID = EbizParams.GLBL_APP_CLD_ID();
    Integer GLBL_SLOC_ID = EbizParams.GLBL_APP_SERV_LOC();
    Integer GLBL_USR_ID = EbizParams.GLBL_APP_USR();
    private RichPanelFormLayout billFormBinding;

    public void setZero(Number zero) {
        this.zero = zero;
    }

    public Number getZero() {
        return zero;
    }

    public TempVoucherDtlForm() {
    }

    /**Method to resolve String from XML file @06-07-2013*/

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }


    private Integer getVoucherType() {

        Integer vouType = 0;
        if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouTypeId") != null) {
            vouType = Integer.parseInt(getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouTypeId").toString());

        }
        return vouType;
    }

    public oracle.jbo.domain.Date getCurrentDate(String dateVal) {
        oracle.jbo.domain.Date jboDate = null;
        try {

            java.util.Date utilDate = dateFormat.parse(dateVal);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            jboDate = new oracle.jbo.domain.Date(sqlDate);


        } catch (ParseException a) {
            System.out.println("ParseException");

        }
        return jboDate;
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
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
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static String modeGet() {
        return (String) resolveElExp("#{pageFlowScope.PARAM_VOU_MODE}");
    }

    /**
     * @param data
     * @return
     * function to get string value of a EL expression written like "#{somevalue}".
     */
    public static Object resolveElExp(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }

    /**
     * @param data
     * @return
     * function to get Application module value from a EL expression. Input will be a string value containing name of application module.
     */
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void commit() {
        OperationBinding createOpBAddress = getBindings().getOperationBinding("Commit");
        createOpBAddress.execute();
    }

    /**
     * Method added to call commit binding for Information link Page
     * **/
    public void commitInformationLink() {
        OperationBinding createOpBAddress = getBindings().getOperationBinding("Commit1");
        createOpBAddress.execute();
    }

    public void rollback() {
        OperationBinding createOpBAddress = getBindings().getOperationBinding("Rollback");
        createOpBAddress.execute();
    }

    /**
     * @return
     * Generalized function to call object of application module.
     */
    public TempVoucherAMImpl getAm() {
        return (TempVoucherAMImpl) resolvElDC(amName);
    }


    /**
     * @param vouId
     */
    public void setVouId(RichInputText vouId) {
        this.vouId = vouId;
    }

    /**
     * @return
     */
    public RichInputText getVouId() {
        return vouId;
    }


    /**
     * @return
     * Create function called from create button.
     */
    public String createAction() {
        OperationBinding rollB = getBindings().getOperationBinding("Rollback");
        rollB.execute();
        /**set mode to add.*/
        setMode("A");
        setModeExpense("A");
        assessableValBind.setVisible(false);
        return "create";
    }

    public String isVoucherAllow(String CldId, Integer SlocId, String HoOrgId, String Org_id, Integer DocType,
                                 Integer DocTypeId, Date VouDate, Integer UsrId) {
        /** This code is currently not in use . It was written for Voucher wise closing in financial year*/
        //        System.out.println("parameters in isVoucherAllow" + CldId + " - " + SlocId + " - " + HoOrgId + " - " + Org_id +
        //                           " - " + DocType + " - " + DocTypeId + " - " + VouDate + " - " + UsrId);
        Object res = callStoredFunction(Types.VARCHAR, "APP.fn_vou_type_allow_in_fy(?,?,?,?,?,?,?,?)", new Object[] {
                                        CldId, SlocId, HoOrgId, Org_id, DocType, DocTypeId, VouDate, UsrId
        });
        if (res != null) {
            if (res.equals("Y")) {
                return "Y";
            }
            return "N";
        }
        return "N";
    }


    public String isTransactionAllowed(String table) {
        OperationBinding op = getBindings().getOperationBinding("getClosingBalance");
        op.getParamsMap().put("table", table);
        op.execute();
        Object res = op.getResult();
        if (res != null) {
            Number rres = (Number) res;
            if (rres.compareTo(0) > 0) {
                return "Y";
            } else
                return "N";
        }
        return "N";
    }

    public String paymentLimit() {
        ViewObjectImpl impl = getAm().getTvouLinesLnk();
        Row[] filteredRows = impl.getFilteredRows("CoaTypeTrans", 79);
        if (filteredRows.length > 0) {
            for (int i = 0; i < filteredRows.length; i++) {
                Object TvouAmtTyp = filteredRows[i].getAttribute("TvouAmtTyp");
                if (TvouAmtTyp != null) {
                    if (TvouAmtTyp.toString().equalsIgnoreCase("Cr")) {
                        OperationBinding op = getBindings().getOperationBinding("getClosingBalance");
                        op.getParamsMap().put("table", table);
                        op.getParamsMap().put("row", filteredRows[i]);

                        op.execute();
                        Object res = op.getResult();
                        if (res != null) {
                            Number rres = (Number) res;
                            if (rres.compareTo(0) < 0) {
                                return "N";
                            } else if (rres.compareTo((Number) filteredRows[i].getAttribute("TvouAmtBs")) < 0) {
                                return "N";
                            }

                        }
                    }
                }
            }

            return "Y";
        }
        return "Y";
    }

    /**
     * @param actionEvent
     * Function createDetailLine is called from add line button of form to create voucher line in table "TvouLines".
     */
    public void createDetailLine(ActionEvent actionEvent) {
        ViewObject vouLineViw = getAm().getTvouLinesLnk();
        vouLineViw.executeQuery();
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject vouViw = am.getTvou1();
        /**Get current rows of both tables.*/

        Row vouHD = vouViw.getCurrentRow();
        if (tvouTypBind.getValue() == null) {
            String msg1 = resolvElDCMsg("#{bundle['MSG.124']}").toString(); //Voucher Type is Required
            String msg2 = resolvElDCMsg("#{bundle['MSG.492']}").toString(); //Please Select Voucher Type
            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(tvouTypBind.getClientId(), errMsg);
        }
        /**Check whether coa is selected or not in Case Bank/Cash Reciept/Payment- 05-03-2013*/
        else if ((vouHD.getAttribute("TvouTypeId").equals(2) || vouHD.getAttribute("TvouTypeId").equals(3) ||
                  vouHD.getAttribute("TvouTypeId").equals(4) || vouHD.getAttribute("TvouTypeId").equals(5) ||
                  vouHD.getAttribute("TvouTypeId").equals(6)) && chartOfAc.getValue() == null) {
            String msg1 = resolvElDCMsg("#{bundle['MSG.343']}").toString(); //Chart of Account is required
            String msg2 = resolvElDCMsg("#{bundle['MSG.493']}").toString(); //Please Select Chart of Account

            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(chartOfAc.getClientId(), errMsg);
        }
        //         else if ((vouHD.getAttribute("TvouTypeId").equals(16)) &&
        //                   (refCoaBinding.getValue() == null || refCoaBinding.getValue() == "")) {
        //            String msg1 = "Ref. COA is required for Imprest Voucher";
        //            String msg2 = "Please select some value";
        //
        //            FacesMessage errMsg = new FacesMessage(msg1);
        //            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
        //            errMsg.setDetail(msg2);
        //            FacesContext.getCurrentInstance().addMessage(refCoaBinding.getClientId(), errMsg);
        //        }
        //else if ((vouHD.getAttribute("TvouTypeId").equals(16)) &&
        //                   (intimationIdBinding.getValue() == null || intimationIdBinding.getValue() == "")) {
        //            String msg1 = "Intimation Id is required for Imprest Voucher";
        //            String msg2 = "Please select some value";
        //
        //            FacesMessage errMsg = new FacesMessage(msg1);
        //            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
        //            errMsg.setDetail(msg2);
        //            FacesContext.getCurrentInstance().addMessage(intimationIdBinding.getClientId(), errMsg);
        //        }
        else if (isTransactionAllowed("TVOU").equalsIgnoreCase("N") &&
                 (vouHD.getAttribute("TvouTypeId").equals(4) || vouHD.getAttribute("TvouTypeId").equals(6)) &&
                 vouHD.getAttribute("CoaTypeTrans").equals(79) && vouLineViw.getRowCount() == 0) {
            String msg = resolvEl("#{bundle['MSG.1958']}"); //Insufficient amount to make payment

            FacesMessage errMsg = new FacesMessage(msg);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail("");
            FacesContext.getCurrentInstance().addMessage(chartOfAc.getClientId(), errMsg);
        }
        //        else if ((vouHD.getAttribute("TvouTypeId").equals(2) || vouHD.getAttribute("TvouTypeId").equals(3) ||
        //                    vouHD.getAttribute("TvouTypeId").equals(4) || vouHD.getAttribute("TvouTypeId").equals(5) ||
        //                    vouHD.getAttribute("TvouTypeId").equals(16) || vouHD.getAttribute("TvouTypeId").equals(6)) &&
        //                   (intimationIdBinding.getValue() != null && intimationIdBinding.getValue() != "")) {
        //            System.out.println("in intim");
        //            OperationBinding createOpBAddress = getBindings().getOperationBinding("processIntimationId");
        //            createOpBAddress.execute();
        //        }
        else {
            if (vouHD.getAttribute("TvouTypeId").equals(14) || vouHD.getAttribute("TvouTypeId").equals(7) ||
                vouHD.getAttribute("TvouTypeId").equals(8)) {
                vouHD.setAttribute("TvouDueDt", vouHD.getAttribute("TvouDt"));
            }
            getAm().getTvouLineChq().executeQuery();
            /** Get createinsert operation from bindings and execute it to create a new row in TvouLines table.*/

            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("TvouLinesLnkIterator");
            RowSetIterator rsi = dciter.getRowSetIterator();
            Row lastRow = rsi.last();
            int lastRowIndex = rsi.getRangeIndexOf(lastRow);
            Row newRow = rsi.createRow();
            newRow.setNewRowState(Row.STATUS_INITIALIZED);
            rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
            rsi.setCurrentRow(newRow);


            /**To insert value in TvouSlNo attribute, first call a method "getMaxNo". It will get maximum serial no of voucher's line item,
		after that this serial no will be incremented in application till user add lines to this voucher line i.e. "TvouLines".*/

            Row vouLn = vouLineViw.getCurrentRow();
            TvouLinesVORowImpl row = (TvouLinesVORowImpl) vouLineViw.getCurrentRow();

            vouLn.setAttribute("TvouSlNo", getMaxNo());

            /** Copy voucher sub type value from header "Tvou" to detail table "TvouLines".*/
            if (vouHD.getAttribute("TvouSubTypeId") != null) {
                vouLn.setAttribute("TvouSubTypeId", vouHD.getAttribute("TvouSubTypeId"));
            }
            /** Copy Basic currency id from header "Tvou" to detail table "TvouLines".*/
            if (vouHD.getAttribute("TvouPrjId") != null) {
                vouLn.setAttribute("TvouPrjId", vouHD.getAttribute("TvouPrjId"));
            }
            if (vouHD.getAttribute("TvouCurrIdBs") != null) {
                vouLn.setAttribute("TvouCurrIdBs", vouHD.getAttribute("TvouCurrIdBs"));
            }
            /** Copy Specific currency id from header "Tvou" to detail table "TvouLines".*/
            if (vouHD.getAttribute("TvouCurrIdSp") != null) {
                vouLn.setAttribute("TvouCurrIdSp", vouHD.getAttribute("TvouCurrIdSp"));
                row.setAttribute("TvouCurrIdSp", vouHD.getAttribute("TvouCurrIdSp"));
            }
            /** Copy Organization from header "Tvou" to detail table "TvouLines".*/
            if (vouHD.getAttribute("TvouOrgId") != null) {
                vouLn.setAttribute("TvouOrgId", vouHD.getAttribute("TvouOrgId"));
            }
            /** Copy Narration from header "Tvou" to detail table "TvouLines".*/
            if (vouHD.getAttribute("TvouDesc") != null) {
                vouLn.setAttribute("Narr", vouHD.getAttribute("TvouDesc"));
            }

            /**Copy currency conversion rate  from header "Tvou" to detail table "TvouLines". */
            if (vouHD.getAttribute("TvouCc") != null) {
                vouLn.setAttribute("TvouCc", vouHD.getAttribute("TvouCc"));
            }
            if (vouHD.getAttribute("TvouDt") != null) {
                Date dt = (Date) vouHD.getAttribute("TvouDt");
                vouLn.setAttribute("TvouDt", dt.dateValue().toString());
            }
            if (vouHD.getAttribute("TvouTypeId").equals(14) || vouHD.getAttribute("TvouTypeId").equals(7) ||
                vouHD.getAttribute("TvouTypeId").equals(8)) {
                if (vouHD.getAttribute("TvouDueDt") != null) {
                    vouLn.setAttribute("TvouDueDt", vouHD.getAttribute("TvouDueDt"));
                }
            }


            /** Set value for amount type in line based on voucher type. */
            if (vouHD.getAttribute("TvouTypeId").equals(1) || vouHD.getAttribute("TvouTypeId").equals(2) ||
                vouHD.getAttribute("TvouTypeId").equals(4) || vouHD.getAttribute("TvouTypeId").equals(6) ||
                vouHD.getAttribute("TvouTypeId").equals(7) || vouHD.getAttribute("TvouTypeId").equals(9) ||
                vouHD.getAttribute("TvouTypeId").equals(10) || vouHD.getAttribute("TvouTypeId").equals(13) ||
                vouHD.getAttribute("TvouTypeId").equals(15)) {
                vouLn.setAttribute("TvouAmtTyp", "Dr");
            }
            if (vouHD.getAttribute("TvouTypeId").equals(3) || vouHD.getAttribute("TvouTypeId").equals(5) ||
                vouHD.getAttribute("TvouTypeId").equals(8) || vouHD.getAttribute("TvouTypeId").equals(11) ||
                vouHD.getAttribute("TvouTypeId").equals(12)) {
                vouLn.setAttribute("TvouAmtTyp", "Cr");
            } else {
                vouLn.setAttribute("TvouAmtTyp", "Dr");
            }
            setHdrDisable(true);
        }
    }

    public String getHexDocNoFromFun(String CldId, Integer SlocId, String OrgId, Integer UsrId, Integer typId) {

        String hexDocIdFromFun = "0";
        try {
            hexDocIdFromFun = this.callStoredFunction(Types.VARCHAR, "APP.GET_TXN_ID_CC(?,?,?,?,?,?,?)", new Object[] {
                                                      CldId, SlocId, OrgId, UsrId, 55, 84651, typId
            }).toString();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR IN CALLING FUNCTION GET_TXN_ID at BdgEoBudgetEoImpl :" + e.getMessage());
        }
        System.out.println("===========" + hexDocIdFromFun);

        return hexDocIdFromFun;
    }

    /**
     * Helper function to call a database funtion.
     * @param sqlReturnType
     * @param stmt
     * @param bindVars
     * @return
     */
    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            System.out.println(st.getObject(1));
            return st.getObject(1);

        } catch (SQLException e) {
            e.printStackTrace();
            int end = e.getMessage().indexOf("\n");
            System.out.println("e.getMessage() = " + e.getMessage());
            String message = e.getMessage().substring(11, end);

            FacesMessage msg = new FacesMessage(message);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
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

    /**
     * @param rend_create
     */
    public void setRend_create(boolean rend_create) {
        this.rend_create = rend_create;
    }

    /**
     * @return
     */
    public boolean isRend_create() {
        return rend_create;
    }

    /**function to call object of bindings.
     * @return
     */


    public Boolean checkManualTax() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject vouViwln = am.getTvouLinesLnk();
        int count = 0;
        RowSetIterator rit = vouViwln.createRowSetIterator(null);
        while (rit.hasNext()) {
            Row curLn = rit.next();

            if (curLn.getAttribute("TvouLnTyp").equals("DTAX")) {
                count = count + 1;
            }
        }

        if (getTaxAmount().compareTo(getTaxBasicAmount()) == 1 || getTaxAmount().compareTo(getTaxBasicAmount()) == -1) {

            rit.closeRowSetIterator();

            return true;
        } else {
            rit.closeRowSetIterator();

            return false;
        }
    }

    public Boolean checkManualTds() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject vouViwln = am.getTvouLinesLnk();
        int count = 0;
        RowSetIterator rit = vouViwln.createRowSetIterator(null);
        while (rit.hasNext()) {
            Row curLn = rit.next();

            if (curLn.getAttribute("TvouLnTyp").equals("DTDS")) {
                count = count + 1;
            }
        }
        if (getTdsAmount().compareTo(getTdsBasicAmount()) == 1 || getTdsAmount().compareTo(getTdsBasicAmount()) == -1) {
            rit.closeRowSetIterator();
            return true;
        } else {
            rit.closeRowSetIterator();
            return false;
        }
    }

    /** Validation to check if tax present in line item without manual tax entry checkbox selection.
     * */
    public Boolean checkManTaxValid() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject vouViw = am.getTvou1();
        ViewObject vouViwln = am.getTvouLinesLnk();
        am.getLovForTaxVO().setNamedWhereClauseParam("org",
                                                     vouViw.getCurrentRow().getAttribute("TvouOrgId").toString());
        am.getLovForTaxVO().executeQuery();
        int countax = 0;
        if ("N".equalsIgnoreCase(vouViw.getCurrentRow().getAttribute("TvouManTax").toString())) {
            RowSetIterator rit = vouViwln.createRowSetIterator(null);
            while (rit.hasNext()) {
                Row curLn = rit.next();
                Integer coai = (Integer.parseInt(curLn.getAttribute("TvouCoaId").toString()));
                Row[] r = am.getLovForTaxVO().getFilteredRows("CoaId", coai);
                if (r.length > 0 && !("DTAX".equalsIgnoreCase(curLn.getAttribute("TvouLnTyp").toString()))) {

                    countax = countax + 1;
                }
            }
            rit.closeRowSetIterator();
        }
        if (countax > 0) {

            return true;
        } else {
            return false;
        }
    }

    /** Validation to check if tds present in line item without manual tds entry checkbox selection.
     * */
    public Boolean checkManTdsValid() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject vouViw = am.getTvou1();
        ViewObject vouViwln = am.getTvouLinesLnk();
        am.getLovForTdsVO().setNamedWhereClauseParam("org",
                                                     vouViw.getCurrentRow().getAttribute("TvouOrgId").toString());
        am.getLovForTdsVO().executeQuery();
        int countax = 0;
        if ("N".equalsIgnoreCase(vouViw.getCurrentRow().getAttribute("TvouManTds").toString())) {
            RowSetIterator rit = vouViwln.createRowSetIterator(null);
            while (rit.hasNext()) {
                Row curLn = rit.next();
                Integer coai = (Integer.parseInt(curLn.getAttribute("TvouCoaId").toString()));
                Row[] r = am.getLovForTdsVO().getFilteredRows("CoaId", coai);

                if (r.length > 0 && !("DTDS".equalsIgnoreCase(curLn.getAttribute("TvouLnTyp").toString()))) {

                    countax = countax + 1;
                }
            }
            rit.closeRowSetIterator();
        }
        if (countax > 0) {
            return true;
        } else {
            return false;
        }
    }


    /** Function to call a database procedure.
     * @param stmt
     * @param bindVars
     * @throws SQLException
     */
    public void callStoredProcedure(String stmt, Object[] bindVars) {
        PreparedStatement st = null;
        try {
            st = getAm().getDBTransaction().createPreparedStatement("begin " + stmt + "; end;", 0);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 1, bindVars[z]);
                }
            }
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("interbranch exception");
            if (e.getMessage().length() < 11) {

                throw new JboException(e.getMessage());
            } else {
                int end = e.getMessage().indexOf("\n");
                throw new JboException(e.getMessage().substring(11, end));
            }
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e.getMessage());
                }
            }
        }
    }

    protected Object callStoredProcedure1(String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            // 1. Create a JDBC CallableStatement
            st = getAm().getDBTransaction().createCallableStatement("begin " + stmt + "; end ;", 0);
            // 2. Register the first bind variable for the return value
            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);
            st.setObject(5, bindVars[4]);
            st.setObject(6, bindVars[5]);


            st.registerOutParameter(7, Types.NUMERIC);
            st.registerOutParameter(8, Types.VARCHAR);

            st.executeUpdate();
            try {

                act_bgt_amt = new Number(st.getObject(7));
                act_bgt_typ = (st.getObject(8)).toString();

            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }

            return st.getObject(8);
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    /**Function Call to get Financial year Sum of COA 16-05-2013 @Ashish Kumar */
    public String coaFySum(Number currentAmt) {
        ViewObject tvou = getAm().getTvou1();
        Row curRow = tvou.getCurrentRow();
        Row curRowLine = getAm().getTvouLinesLnk().getCurrentRow();
        String alertTyp = "X";


        if (curRowLine.getAttribute("TvouCoaId") != null && curRowLine.getAttribute("PartOfBdgtChk").equals("Y")) {
            // System.out.println("checking coa budget");
            Integer coaId = Integer.parseInt(curRowLine.getAttribute("TvouCoaId").toString());
            Date txnDate = (Date) curRow.getAttribute("TvouDt");

            String orgId = curRow.getAttribute("TvouOrgId").toString();
            Integer slocId = Integer.parseInt(curRow.getAttribute("TvouSlocId").toString());
            String hoorgId = (curRowLine.getAttribute("TvouHoOrgId").toString());
            String cldId = curRowLine.getAttribute("TvouCldId").toString();
            Integer instId;
            if (curRow.getAttribute("TvouApplInstId") != null) {
                instId = Integer.parseInt(curRow.getAttribute("TvouApplInstId").toString());
            } else {

                instId = 1;
            }

            Number fy_Id = new Number(0);
            BigDecimal fyId = new BigDecimal(1);
            try {


                fyId = (BigDecimal) callStoredFunction2(NUMBER, "APP.FN_GET_FY_ID(?,?,?,?)", new Object[] {
                                                        cldId, orgId, txnDate, "FY"
                });
                fy_Id = new Number(fyId);
            } catch (Exception e) {
                fy_Id = new Number(0);
            }
            BigDecimal sum;
            try {


                sum = (BigDecimal) callStoredFunction2(NUMBER, "FIN.FN_COA_FY_SUM(?,?,?,?,?,?,?)", new Object[] {
                                                       cldId, slocId, hoorgId, orgId, instId, coaId, fyId
                });
            } catch (Exception e) {
                sum = new BigDecimal(0);
                e.printStackTrace();
            }


            callStoredProcedure1("FIN.GET_COA_ALLOCATED_BUDGET(?,?,?,?,?,?,?,?)", new Object[] {
                                 slocId, hoorgId, cldId, orgId, coaId, txnDate
            });


            Number coaSum = new Number(0);
            try {
                coaSum = new Number(sum);
                if (coaSum.compareTo(0) == -1) {
                    coaSum = coaSum.multiply(-1);
                }
                coaSum = coaSum.add(currentAmt);
            } catch (SQLException e) {
                System.out.println(e);
            }
            //   System.out.println("Financial year sum is--->" + coaSum + "for COA-->" + coaId);


            alertTyp = (String) callStoredFunction2(VARCHAR, "APP.PKG_APP_ALERT.GET_ALERT_TYPE(?,?,?,?,?,?,?,?)", new Object[] {
                                                    slocId, cldId, orgId, 62, 1, coaId, act_bgt_amt, coaSum
            });


        }
        return alertTyp;
    }

    /**Round off Function to add extra line for round off account- 18-06-2013*/
    public void roundOff() {

        ViewObject tvou = getAm().getTvou1();
        Row curRow = tvou.getCurrentRow();
        String orgId = curRow.getAttribute("TvouOrgId").toString();
        Integer slocId = Integer.parseInt(curRow.getAttribute("TvouSlocId").toString());
        String hoorgId = (curRow.getAttribute("TvouHoOrgId").toString());
        String cldId = curRow.getAttribute("TvouCldId").toString();
        String vouId = curRow.getAttribute("TvouId").toString();
        try {
            callStoredFunction2(NUMBER, "FIN.FN_ROUND_OFF(?,?,?,?,?)", new Object[] {
                                cldId, slocId, hoorgId, orgId, vouId });
        } catch (Exception e) {
            System.out.println("Exception in round off--" + e);
        }
    }

    /**Method to call workflow function*/
    public void workFlowCall() {
        callBindingsMethod("callWfFunctions", null, null);
        setWorkFlwId(getWfId());
    }

    /**Method to copy all narration from tvou_lines to tvou header @23-07-2013*/
    public void copyNarration() {
        StringBuilder narration = new StringBuilder("");
        ViewObject tvouLn = getAm().getTvouLinesLnk();
        ViewObject tvouHd = getAm().getTvou1();
        RowSetIterator rset = tvouLn.createRowSetIterator(null);
        while (rset.hasNext()) {
            Row nextRow = rset.next();
            if (nextRow.getAttribute("Narr") != null) {
                String curNarr = nextRow.getAttribute("Narr").toString();
                narration.append(" , ");
                narration.append(curNarr);
            }
        }
        Row curRow = tvouHd.getCurrentRow();
        if (narration.length() >= 1000) {
            String narrationBig = narration.substring(0, 1000);
            curRow.setAttribute("TvouDesc", narrationBig);
        } else {
            curRow.setAttribute("TvouDesc", narration);
        }

    }

    public String isMultiOrgValid() {
        System.out.println("in isMultiOrgValid of  bean ");
        Object res = callBindingsMethod("isMultiOrgValid", null, null);
        System.out.println("res = " + res);
        if (res != null) {

            return res.toString();
        }
        return "Y";
    }

    public boolean saveAction() {
        boolean retVal = true;
        System.out.println("IN SAVE ACTION");
        FacesContext fc = FacesContext.getCurrentInstance();
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        am.getDBTransaction().postChanges();
        ViewObject vouViwln = am.getTvouLinesLnk();
        ViewObject vouViw = am.getTvou1();
        Row vouHD = vouViw.getCurrentRow();
        Object TvouId = vouHD.getAttribute("TvouId");


        /** Before saving check change in taxable amount in voucher and amount on which tax is applied. method "getTaxAmount()" get the amount on which tax has been applied.*/


        if (vouHD.getAttribute("TvouTypeId").equals(14) || vouHD.getAttribute("TvouTypeId").equals(6)) {
        } else {
            checkTaxTds();
        }

        /**A global message popup is added for showing message 20-04-2013 @Ashish Kumar*/
        // checkManualTax()- need to change according to basic amount and same in case of tds also
        if (getTaxYN() == true && checkManualTax()) {
            /** if not matched an error message.*/
            String msg1 = resolvElDCMsg("#{bundle['MSG.496']}").toString(); //Taxable amount changed reapply Tax
            FacesMessage msg = new FacesMessage(msg1);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, msg);
            retVal = false;

        }

        //check change in taxable amount in voucher and amount on which tds is applied.
        //method "getTdsAmount()" get the amount on which tds has been applied.
        else if (getTdsYN() == true && checkManualTds()) {
            String msg1 = resolvElDCMsg("#{bundle['MSG.497']}").toString(); //TDS amount changed, reapply TDS
            FacesMessage msg = new FacesMessage(msg1);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, msg);

            retVal = false;

        } else if (am.instrumentTypeCheck().equals("N")) {
            String msg1 = resolvElDCMsg("#{bundle['MSG.1874']}").toString(); //Invalid instrument type
            // String  msg1="Invalid Instrument type";
            FacesMessage msg = new FacesMessage(msg1);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, msg);
            retVal = false;

        } else if (!isMultiOrgValid().equalsIgnoreCase("Y")) {
            String isMultiOrgValid = isMultiOrgValid();
            //   String msg1 = resolvElDCMsg("#{bundle['MSG.1874']}").toString();
            // String  msg1="Invalid Instrument type";
            FacesMessage msg = new FacesMessage(isMultiOrgValid);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, msg);
            retVal = false;
        }

        /**If tax and tds amount are ok then proceed to save this voucher.
         first check whether this is on account or not, if tax, tds, other charges  or adjustment is done then it is not on account and change txnType to 'L' its default value is 'O'*/
        else {
            if (getTaxAmtType().equals("Y")) {
                am.taxConsolidation();
                TaxAmtType = "N";
            }
            if (getTdsAmtType().equals("Y")) {
                am.tdsConsolidation();
                TdsAmtType = "N";
            }

            Map m = new HashMap();

            RowSetIterator rit = vouViwln.createRowSetIterator(null);
            while (rit.hasNext()) {
                Row curLn = rit.next();


                if (!curLn.getAttribute("TvouTxnTyp").equals("L")) {
                    if (curLn.getAttribute("TvouTxnTypTax").equals("Y") ||
                        curLn.getAttribute("TvouTxnTypTds").equals("Y") ||
                        curLn.getAttribute("TvouTxnTypOc").equals("Y") ||
                        curLn.getAttribute("TvouTxnTypAdj").equals("Y") ||
                        curLn.getAttribute("TvouTxnTypBc").equals("Y")) {
                        curLn.setAttribute("TvouTxnTyp", "L");

                    }
                }
            }
            rit.closeRowSetIterator();
            if (narrationCopy.booleanValue() == true) {
                OperationBinding updateOp = getBindings().getOperationBinding("updateTvouNarration");
                updateOp.execute();
                OperationBinding ob = getBindings().getOperationBinding("Execute");
                ob.execute();
            }


            /**Get method action from bindings for exchange fluctuation entry.*/

            /**Execute exchange fluctuation method only if there is an adjustment entry.*/
            RowSetIterator rowIt = vouViwln.createRowSetIterator(null);
            String flg = "Y";
            while (rowIt.hasNext()) {
                Row curLn = rowIt.next();
                if (curLn.getAttribute("TvouTxnTypAdj").equals("Y") ||
                    curLn.getAttribute("TvouTxnTypAdj").equals("A")) {

                    OperationBinding exFluctOp = getBindings().getOperationBinding("exchangeFluct");
                    exFluctOp.execute();
                    flg = "N";

                }
            }
            rowIt.closeRowSetIterator();
            vouViwln.executeQuery();
            //            OperationBinding exFluctOp = getBindings().getOperationBinding("exchangeFluctuationUpdate");
            //            exFluctOp.execute();
            //            vouViwln.executeQuery();
            //            commit();
            /**Function call for opposite line entry for bank and cash payment and recipt.*/
            if (vouHD.getAttribute("TvouTypeId") != null) {
                if (vouHD.getAttribute("TvouTypeId").equals(2) || vouHD.getAttribute("TvouTypeId").equals(3) ||
                    vouHD.getAttribute("TvouTypeId").equals(4) || vouHD.getAttribute("TvouTypeId").equals(5) ||
                    vouHD.getAttribute("TvouTypeId").equals(6)) {
                    tvouHdLineOp();
                }
            }

            /**Function call to generate round off value with Account 19-06-2013**/
            if (vouHD.getAttribute("TvouRoundFlg") != null) {
                if (vouHD.getAttribute("TvouRoundFlg").toString().equalsIgnoreCase("Y")) {
                    roundOff();
                    OperationBinding ob = getBindings().getOperationBinding("Execute");
                    ob.execute();
                }
            }

            ViewObject lovview = am.getTvou1();
            TvouVORowImpl r1 = (TvouVORowImpl) lovview.getCurrentRow();
            docDisplayId = r1.getVouId();

            if (vouHD.getAttribute("TvouManTax").equals("Y")) {
                manualTaxCheck();
            } /** This Function called to delete the existing record entered in TVouTaxRule and TVouTaxRulelines Thorugh Manual TAx by Amandeep on 08-July-2014 */
            else if (getMode().equalsIgnoreCase("E") && vouHD.getAttribute("TvouManTax").equals("N") &&
                     oldManualTax.equalsIgnoreCase("true")) {

                Object res = callStoredFunction(Types.VARCHAR, "fin.fn_del_manual_tax_lines(?,?,?,?)", new Object[] {
                                                cld_id, sloc_id, HoOrgId, TvouId.toString()
                });


                if (res.toString().equalsIgnoreCase("N")) {
                    String msg1 =
                        resolvElDCMsg("#{bundle['MSG.1263']}").toString(); //Error in Deleting Record. Please Contact ESS!
                    FacesMessage msg = new FacesMessage(msg1);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, msg);
                }
            }
            if (vouHD.getAttribute("TvouManTds").equals("Y")) {
                manualTdsCheck();
            } else if (getMode().equalsIgnoreCase("E") && vouHD.getAttribute("TvouManTds").equals("N") &&
                       oldManualTds.equalsIgnoreCase("true")) {
                Object res = callStoredFunction(Types.VARCHAR, "fin.fn_del_manual_tds_lines(?,?,?,?)", new Object[] {
                                                cld_id, sloc_id, HoOrgId, TvouId.toString()
                });


                if (res.toString().equalsIgnoreCase("N")) {
                    String msg1 =
                        resolvElDCMsg("#{bundle['MSG.1263']}").toString(); //Error in Deleting Record. Please Contact ESS!
                    FacesMessage msg = new FacesMessage(msg1);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, msg);
                }
            }


            OperationBinding createOpBAddress1 = getBindings().getOperationBinding("Execute");
            createOpBAddress1.execute();
            /**Procedure call for MultiOrg Lines entry in Tvou Lines*/
            interBranchFunc();
            /**save work flow entry*/
            getAm().getDBTransaction().postChanges();
            /** Message after successful save of temporary voucher showing voucher no and Credit debit amount*/
            RowSetIterator rsi = vouViwln.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row next = rsi.next();
                Object TvouCurrIdSp = next.getAttribute("TvouCurrIdSp");
                m.put(TvouCurrIdSp, TvouCurrIdSp);
            }
            rsi.closeRowSetIterator();
            //   /** If Voucher is in single currency then size is equal to 1*/

            String Interim_Voucher = resolvElDCMsg("#{bundle['LBL.1464']}").toString();
            String Saved_Successfully = resolvElDCMsg("#{bundle['MSG.227']}").toString();
            String Specific_Amount = resolvElDCMsg("#{bundle['LBL.2759']}").toString();
            String Total_Credit = resolvElDCMsg("#{bundle['LBL.1040']}").toString();
            String Total_Debit = resolvElDCMsg("#{bundle['LBL.1041']}").toString();


            StringBuilder saveMsg =
                new StringBuilder("<html><body><p><b> " + Interim_Voucher + " " + docDisplayId + " " +
                                  Saved_Successfully + " </b></p>");

            if (m.size() == 1) {
                saveMsg.append("<ol><li> " + Specific_Amount + ":- <ul><li> " + Total_Credit + ": <b>" +
                               totalCrInString(getTotalCredit()) + "</b></li><li>" + Total_Debit + ": <b>" +
                               totalCrInString(getTotalDebit()) + "</b></li></ul></li>");
            }

            saveMsg.append("<li> Basic Amount-<ul><li>Total Credit: <b>" + totalCrInString(getTotalBsCredit()) +
                           "</b></li><li>Total Debit: <b>" + totalCrInString(getTotalBsDebit()) +
                           "</b></li></ul></li></ol>");
            saveMsg.append("</body></html>");
            FacesMessage message = new FacesMessage(saveMsg.toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            fc.addMessage(null, message);
            /**Execute view object after saving the voucher*/

            /**set mode to S after saving the voucher*/

            if (getMode() != "E") {
                String dateVal = r1.getAttribute("TvouDt").toString();
                voucherDate = new Date(dateVal);

            }
            setMode("S");
            System.out.println("Mode is set to S after Saving Temporary Voucher--->");

            setImportsave(true);

            System.out.println("check 6");
            retVal = true;
            setHdrDisable(false);
            generateDocNo();
            commit();
            commitInformationLink();

            workFlowCall();
            commit();

            TvouVORowImpl curRowVou = (TvouVORowImpl) am.getTvou1().getCurrentRow();
            curRowVou.getLovTvouIdVO().resetExecuted();

            AdfFacesContext.getCurrentInstance().addPartialTarget(tvouIdBind);
        }
        return retVal;
    }

    /** Function to call task flow operation of going back to search page
     *
     */
    public String backToSearch() {
        getAm().getDBTransaction().rollback();

        /** For cancel of partial filled voucher, delete from all related tables for the current TvouId.*/
        ViewObject tvouHdr = getAm().getTvou1();
        Row currRow = tvouHdr.getCurrentRow();

        if (currRow != null) {
            getAm().getDBTransaction().rollback();
            getAm().getTmplVou().executeQuery();
            getAm().getTvou1().executeQuery();

            String tvouId = (String) currRow.getAttribute("TvouId");

            Integer SLOC_ID = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String CLD_ID = (resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID1}"));
            String HO_ORG_ID = (resolvElDCBindVar("#{pageFlowScope.GLBL_HO_ORG_ID1}"));
            String ORG_ID = (resolvElDCBindVar("#{pageFlowScope.ParamOrgId}"));
            String val = setToTvouCancelCC(SLOC_ID, CLD_ID, HO_ORG_ID, ORG_ID, 55, tvouId, cost_center_slno, "N");


            //            if (getMode().equals("A")) {
            //
            //
            //                String p_flag = "D";
            //
            //                /** Check if DocTxnId already generated. */
            //                Row[] rDocTxn = getAm().getLovTvouIdVO().getFilteredRows("DocTxnId", tvouId);
            //                if (rDocTxn.length == 0) {
            //                    String deleted =
            //                        (String)callStoredFunction2(VARCHAR, "FIN.FN_DEL_PARTIAL_TV(?,?,?,?,?,?)", new Object[] { CLD_ID,
            //                                                                                                                  SLOC_ID,
            //                                                                                                                  p_flag,
            //                                                                                                                  HO_ORG_ID,
            //                                                                                                                  ORG_ID,
            //                                                                                                                  tvouId });
            //                    System.out.println("Deleted ? :" + deleted);
            //                    getAm().getDBTransaction().commit();
            //                }
            //            }
        }
        setMode("");
        setImportsave(true);

        /*   checkBoxTax=null;
        checkBoxTds=null; */
        //set the task flow parameter to null on exit from voucher page
        RequestContext.getCurrentInstance().getPageFlowScope().put("TVouID11", null);

        return "goback";

    }

    /** Function called from revert button to call rollback operation on task flow
     * @return
     */
    public String revert() {

        setMode("");

        return "goback";
    }

    /**Function to save temporary voucher as a template voucher.
     * It will open a popup to add comment.
     */
    public void saveAsTemplate(ActionEvent actionEvent) {
        showPopup(tmplVPopup, true);
    }

    /** Function to save temporary voucher to GL.
     * @return
     */
    public String saveAsGL() {

        // BindingContainer bindings = getBindings();
        FacesContext fc = FacesContext.getCurrentInstance();
        /** Call a AM client function from binding to validate temporary voucher before saving it as a GL*/
        BindingContainer bindings = getBindings();

        OperationBinding glOP = bindings.getOperationBinding("validateTempVouForGl");
        glOP.execute();
        Integer count = 0;
        if (glOP.getResult().toString() != null) {
            count = Integer.parseInt(glOP.getResult().toString());
        }
        System.out.println("Validate Temp Vou returns-->" + count + "and value of error-->" +
                           glOP.getErrors().isEmpty());


        Number totCr = getTotalBsCredit();
        Number totDr = getTotalBsDebit();
        /**check for credit debit mismatch*/
        if (glOP.getErrors().isEmpty() && count == 0) {
            if (totCr.compareTo(totDr) == 1) {
                String msg1 =
                    resolvElDCMsg("#{bundle['MSG.501']}").toString(); //Credit amount is more than debit amount by
                FacesMessage msg = new FacesMessage(msg1 + (totCr.minus(totDr)));
                fc.addMessage(null, msg);

            } else if (totCr.compareTo(totDr) == -1) {
                String msg1 =
                    resolvElDCMsg("#{bundle['MSG.502']}").toString(); //Debit amount is more than Credit amount by
                FacesMessage msg = new FacesMessage(msg1 + (totDr.minus(totCr)));
                fc.addMessage(null, msg);
            } /* else if(r1.getChequeBal().compareTo(totCr)!= 0){
                FacesMessage msg =
                    new FacesMessage("Amount in cheque "+r1.getChequeBal() +" is not equal to voucher amount ");
                fc.addMessage(null, msg);

            } */
            /**after validate voucher call client method from binding to save voucher as GL*/
            //   else {

            return "wf";

            //    }
        }

        return null;

    }


    /**
     * Function to call method from binding for checking manual tax entry.
     * */
    public void manualTaxCheck() {
        BindingContainer bindings = getBindings();

        OperationBinding operationBinding = bindings.getOperationBinding("insTvouLineTax");
        operationBinding.execute();
    }

    /**
     * Function to call method from binding for checking manual tds entry.
     * */
    public void manualTdsCheck() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("insTvouLineTds");
        operationBinding.execute();
    }

    /**
     * Function to call method from binding for Opposite line entry for bank and cash vouchers.
     * */
    public void tvouHdLineOp() {
        BindingContainer bindings = getBindings();

        OperationBinding operationBinding = bindings.getOperationBinding("tvouLineOp");
        operationBinding.execute();

    }

    /**
     * Function to call method from binding for generation of voucher no.
     * */
    public void generateDocNo() {
        BindingContainer bindings = getBindings();

        OperationBinding operationBinding = bindings.getOperationBinding("generateDocNo");
        operationBinding.execute();

    }

    /**
     * Function to refresh page.
     * */
    protected void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }

    /**
     * Function called on value change of specific currency Id to calculate basic amount.
     * */
    public void convertCurrencyListner(ValueChangeEvent valueChangeEvent) {
        TempVoucherAMImpl am = getAm();
        ViewObjectImpl v = am.getTvouLinesLnk();
        /**Object of Voucher line view object*/
        TvouLinesVORowImpl r1 = (TvouLinesVORowImpl) v.getCurrentRow();
        System.out.println("current rate from row--" + r1.getTvouCc());
        if (valueChangeEvent.getNewValue() != null) {

            Integer currId = Integer.parseInt(valueChangeEvent.getNewValue().toString());
            /** Call method from tvouLinesVoRowImpl to get currency conversion rate */
            if (r1.getValue(currId) != null) {
                /**if rate is not null then calculate basic amount and set value on basic amount field*/
                Number rate = r1.getValue(currId);
                Number basic = (Number) this.getBasic_amt().getValue();
                Number result = basic.multiply(rate);
                totalAmt.setValue(result.round(amt_digit));
                r1.reload();

            }
        }
    }

    /**
     * Function called on value change of specific amount to calculate basic amount.
     * */
    public void basicAmountChange(ValueChangeEvent valueChangeEvent) {
        //System.out.println("basic amount calculating");
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null) {
            String alertType = coaFySum((Number) valueChangeEvent.getNewValue());
            //  System.out.println("Alert type final is--->" + alertType);

            if (alertType.equalsIgnoreCase("W")) {
                /**Voucher Amount Alert*/
                // showPopup(budgetWarnPopUpbind, true);
                String msg1 = resolvElDCMsg("#{bundle['MSG.503']}").toString(); //Voucher Amount Alert
                String msg2 =
                    resolvElDCMsg("#{bundle['MSG.504']}").toString(); //Voucher amount cross warning level, about to reach budgeted amount
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                message.setDetail(msg2);
                FacesContext.getCurrentInstance().addMessage(basic_amt.getClientId(), message);
            } else if (alertType.equalsIgnoreCase("N")) {
                FacesMessage message =
                    new FacesMessage(resolvEl("#{bundle['MSG.505']}")); //This is a Informational Message
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(basic_amt.getClientId(), message);

            } else {
                TempVoucherAMImpl am = getAm();
                /**Object of Voucher line view object*/
                ViewObjectImpl v = am.getTvouLinesLnk();
                Row r1 = v.getCurrentRow();
                /*     if (r1.getAttribute("TvouCurrIdSp") != null) {


                    Integer curr = Integer.parseInt(r1.getAttribute("TvouCurrIdSp").toString());
                    TvouLinesVORowImpl r2 = (TvouLinesVORowImpl)v.getCurrentRow();

            */
                // System.out.println("r1.getAttribute(TvouCc)" + r1.getAttribute("TvouCc"));
                if (r1.getAttribute("TvouCc") != null) //tvou currency conversion
                {
                    Number rate = (Number) r1.getAttribute("TvouCc");
                    /**if rate is not null then calculate basic amount and set value on basic amount field*/
                    Number basic = (Number) valueChangeEvent.getNewValue();
                    Number result = (Number) basic.multiply(rate).round(amt_digit);

                    totalAmt.setValue(result);
                    if (r1.getAttribute("TransIsCostCenterAlw") != null) {
                        if (r1.getAttribute("TransIsCostCenterAlw").toString().equalsIgnoreCase("Y")) {
                            if (r1.getAttribute("CcId") == null) {
                                r1.setAttribute("CcId",
                                                getHexDocNoFromFun((String) r1.getAttribute("TvouCldId"),
                                                                   (Integer) r1.getAttribute("TvouSlocId"),
                                                                   (String) r1.getAttribute("TvouOrgId"),
                                                                   (Integer) r1.getAttribute("UsrIdCreate"),
                                                                   Integer.parseInt(r1.getAttribute("TvouTypeId").toString())));
                            }
                            ADFBeanUtils.callBindingsMethod("updateCostCenterAmt", null, null);

                        } else {
                            r1.setAttribute("CcId", null);
                        }

                    }
                }
            }
        }
        //setAccssAmount((Number) valueChangeEvent.getNewValue());
        AdfFacesContext.getCurrentInstance().addPartialTarget(txdtl);
        System.out.println("basic amount calculated");
    }

    public void setAccssAmount(Number amt) {
        Row tvouRow = getAm().getTvou1().getCurrentRow();
        if (amt != null) {
            if (tvouRow != null) {
                String cldId = (String) tvouRow.getAttribute("TvouCldId");
                Integer slocId = (Integer) tvouRow.getAttribute("TvouSlocId");
                String hoOrgId = (String) tvouRow.getAttribute("TvouHoOrgId");
                Integer coaId = 0;
                Row currRow = getAm().getTvouLinesLnk().getCurrentRow();
                if (currRow != null)
                    coaId = (Integer) currRow.getAttribute("TvouCoaId");
                System.out.println("cldId " + cldId + "slocId " + slocId + "hoOrgId " + hoOrgId + "coaId " + coaId);
                Number prcnt = new Number(10);
                BigDecimal res = (BigDecimal) (callStoredFunction2(Types.NUMERIC, "FIN.FN_GET_TAX_PER_FRM_COA(?,?,?,?)", new Object[] {
                                                                   cldId, slocId, hoOrgId, coaId
                }));
                Integer resInt = res.intValue();
                try {
                    prcnt = new Number(resInt);
                } catch (SQLException e) {
                }
                if (prcnt != null && prcnt.compareTo(0) > 0) {
                    System.out.println("percentage " + prcnt);
                    Number spAmt = (Number) amt;
                    System.out.println("apecific amount " + spAmt);
                    Number taxableAmt = (spAmt.multiply(100)).divide(prcnt);
                    System.out.println("taxable amount " + taxableAmt);
                    getAm().getTvouLinesLnk().getCurrentRow().setAttribute("TvouTaxtdsAssessVal", taxableAmt);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(basic_amt);
                }
            }
        }
    }

    /**
     * @param totalAmt
     */
    public void setTotalAmt(RichInputText totalAmt) {
        this.totalAmt = totalAmt;
    }

    /**
     * @return
     */
    public RichInputText getTotalAmt() {
        return totalAmt;
    }


    /**
     * @param basic_amt
     */
    public void setBasic_amt(RichInputText basic_amt) {
        this.basic_amt = basic_amt;
    }

    /**
     * @return
     */
    public RichInputText getBasic_amt() {
        return basic_amt;
    }
    private Number Sum = new Number(0);
    private Number as = new Number();

    /**
     * @param as
     */
    public void setAs(Number as) {
        this.as = as;
    }

    /**
     * @return
     */
    public Number getAs() {
        return as;
    }

    /**
     * @param Sum
     */
    public void setSum(Number Sum) {
        this.Sum = Sum;
    }

    /**
     * @return
     */
    public Number getSum() {
        return Sum;
    }

    /**Function to go to tax page.
     * @return
     */
    public String addHdTax() {
        System.out.println("In addHdTax method");
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObjectImpl taxVouHd = am.getTvouTaxRuleHd();
        taxVouHd.executeQuery();
        Row vouHD = am.getTvou1().getCurrentRow();

        if (getMode().equalsIgnoreCase("E") && vouHD.getAttribute("TvouManTax").equals("N") &&
            oldManualTax.equalsIgnoreCase("true")) {
            System.out.println("In Tax--------------");

            taxDelete();
        }
        /**Check for the tax entry in tvou_tax_rule table if not create a new row on table. */
        if (taxVouHd.getRowCount() == 0) {

            OperationBinding createOpBAddress = getBindings().getOperationBinding("CreateInsert2");
            createOpBAddress.execute();
            this.cancelTaxDis = false;

        } else {
            this.cancelTaxDis = true;

        }

        /** Go to task flow action HdTax to go to tax page */
        showPopup(taxPopupBinding, true);
        return null;
    }

    /**Function to go to TDS page.
     * @return
     */
    public String addHdTds() {

        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        TvouVOImpl tv = am.getTvou1();
        Row currentRow = tv.getCurrentRow();
        Object TvouTypeId = currentRow.getAttribute("TvouTypeId");
        int count = 0;

        if (TvouTypeId.equals(8)) {
            TvouLinesVOImpl impl = am.getTvouLinesLnk();
            RowSetIterator rsi = impl.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row next = rsi.next();
                if (!(next.getAttribute("CoaTypeTrans").equals(0) || next.getAttribute("CoaTypeTrans").equals(5022))) {
                    count = 1;
                }
            }
            rsi.closeRowSetIterator();
        }

        if ((TvouTypeId.equals(8) && count == 1) || (!TvouTypeId.equals(8) && count == 0)) {

            ViewObject tdsHd = am.getTvouTdsRuleHd();
            tdsHd.executeQuery();
            Row vouHD = am.getTvou1().getCurrentRow();

            if (getMode().equalsIgnoreCase("E") && vouHD.getAttribute("TvouManTds").equals("N") &&
                oldManualTds.equalsIgnoreCase("true")) {
                System.out.println("In Tax--------------");

                tdsDelete();
            }
            /**Check for the tax entry in tvou_tds_rule table if not create a new row on table. */

            if (tdsHd.getRowCount() == 0) {
                OperationBinding createOpBAddress = getBindings().getOperationBinding("CreateInsert3");
                createOpBAddress.execute();
                this.cancelTdsDis = false;
            } else {
                this.cancelTdsDis = true;
            }
            /** Go to task flow action HdTds to go to TDS page */
            showPopup(tdsPopupBinding, true);
        } else {
            String msg1 = resolvEl("#{bundle['MSG.2414']}"); //There should be one party account
            String msg2 = "Please enter one Creditor/Debtor account";
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;
    }

    /**
     *Function to open popup, this function takes value of popup name and generates a java script to open popup.
     */
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


    /**
     * Function called on value change of voucher type.
     */
    public void vouTypChange(ValueChangeEvent valueChangeEvent) {

        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObjectImpl v = am.getTvou1();
        TvouVORowImpl r1 = (TvouVORowImpl) v.getCurrentRow();
        /** Sort chart of account lov to cash and bank accounts */
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 2 ||
            Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 3 ||
            Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 6) {
            /**If voucher is Bank payment , bank receipt or contra voucher chart of account will show only bank accounts*/
            r1.setCoaLov(78);
            chartOfAc.setRequired(true);
        } else if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 4 ||
                   Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 5) {
            /**If voucher is cash payment or cash receipt chart of account will show only cash accounts*/
            r1.setCoaLov(79);
            chartOfAc.setRequired(true);
        } else {
            /**For all other vouchers chart of account is not required */
            r1.setCoaLov(0);
            chartOfAc.setRequired(false);
        }
        // r1.reload();
    }


    /**
     * @param advance
     */
    public void setAdvance(RichSelectBooleanCheckbox advance) {
        this.advance = advance;
    }

    /**
     * @return
     */
    public RichSelectBooleanCheckbox getAdvance() {
        return advance;
    }


    public void selectAdjustment(ValueChangeEvent valueChangeEvent) {

        /** If adjustment check box is checked to true then advance check box should remain false*/

        if (valueChangeEvent.getNewValue() != null) {
            String accntExist = isExchngFlctnAccntExist();
            if (valueChangeEvent.getNewValue().equals("A") && accntExist.equals("Y")) {
                advance.setValue(false);
                basic_amt.setValue(new Number(0));
                totalAmt.setValue(new Number(0));

                //call the method to set the value of data fetch enable/disable parameter
                setAdjDataFetchDisable(autoAdjDataFetch());

                ActionEvent autoAdjCall = new ActionEvent(autoAdjLnk);
                autoAdjCall.queue();

            } else if (valueChangeEvent.getNewValue().equals("Y") && accntExist.equals("Y")) {
                // advance.setValue(true);
                // advance.setReadOnly(false);
                ActionEvent manualAdjCall = new ActionEvent(manualAdjLnk);
                manualAdjCall.queue();

            } else if (valueChangeEvent.getNewValue().equals("N")) {


            } else if (accntExist.equals("N")) {
                FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1959']}"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        System.out.println("adjustment" + valueChangeEvent.getNewValue());
    }

    public String isExchngFlctnAccntExist() {
        System.out.println("in isExchngFlctnAccntExist");
        TempVoucherAMImpl am = getAm();
        ViewObjectImpl cnt = am.getExchngeFlctnCount1();
        TvouVOImpl impl = am.getTvou1();
        Row currentRow = impl.getCurrentRow();

        cnt.setNamedWhereClauseParam("BindSlocId", currentRow.getAttribute("TvouSlocId"));
        cnt.setNamedWhereClauseParam("BindHoOrgId", currentRow.getAttribute("TvouHoOrgId"));
        cnt.setNamedWhereClauseParam("BindCldId", currentRow.getAttribute("TvouCldId"));
        cnt.executeQuery();
        Row[] allRowsInRange = cnt.getAllRowsInRange();
        if (allRowsInRange.length > 0) {
            Object count = allRowsInRange[0].getAttribute("FlcAcc");
            if (count != null) {
                System.out.println("count in exc flctn = " + count);
                Integer cn = (Integer) count;
                if (cn == 0)
                    return "N";
                return "Y";
            }
        }
        return "N";
    }

    /** Function to call db function "is_coa_debtor_creditor_org" to check whether chart of account is creditor or debtor.
     * @param slocId
     * @param orgId
     * @param coaId
     * @return
     */
    public String getCrorDrValue(String cldId, Integer slocId, String hoOrgId, String orgId, Integer coaId) {
        return (String) callStoredFunction2(VARCHAR, "FIN.is_coa_debtor_creditor_org(?,?,?,?,?)", new Object[] {
                                            cldId, slocId, hoOrgId, orgId, coaId
    });
    }

    /**
     * @param adjustment
     */
    public void setAdjustment(RichSelectBooleanCheckbox adjustment) {
        this.adjustment = adjustment;
    }

    /**
     * @return
     */
    public RichSelectBooleanCheckbox getAdjustment() {
        return adjustment;
    }


    /**
     * @param debitAmt
     */
    public void setDebitAmt(RichInputText debitAmt) {
        this.debitAmt = debitAmt;
    }

    /**
     * @return
     */
    public RichInputText getDebitAmt() {
        return debitAmt;
    }

    /**
     * @param creditAmt
     */
    public void setCreditAmt(RichInputText creditAmt) {
        this.creditAmt = creditAmt;
    }

    /**
     * @return
     */
    public RichInputText getCreditAmt() {
        return creditAmt;
    }


    /**
     * @param taxYN
     */
    public void setTaxYN(Boolean taxYN) {
        this.taxYN = taxYN;
    }

    /**
     * @return boolean to check whether apply tax check box is checked in one of the line entries.
     */
    public Boolean getTaxYN() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouLinesLnk();
        /**Create iterator of tvou line view*/
        RowSetIterator rit = v1.createRowSetIterator(null);
        rit.reset();
        Integer i = 0;

        if (rit.first() != null && rit.first().getAttribute("TvouTxnTypTax").equals("Y")) {
            i = 1;

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouTxnTypTax").equals("Y")) {
                i = i + 1;

            }
        }
        rit.closeRowSetIterator();
        if (i > 0)

            return true;
        else
            return false;

    }

    /**
     * @param tdsYN
     */
    public void setTdsYN(Boolean tdsYN) {
        this.tdsYN = tdsYN;
    }

    /**
     * @return boolean to check whether apply tds check box is checked in one of the line entries.
     */

    public Boolean getTdsYN() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouLinesLnk();
        RowSetIterator rit = v1.createRowSetIterator(null);

        Integer i = 0;
        if (rit.first() != null && rit.first().getAttribute("TvouTxnTypTds").equals("Y")) {
            i = 1;

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouTxnTypTds").equals("Y")) {
                i = i + 1;
            }
        }
        rit.closeRowSetIterator();
        if (i > 0) {

            return true;
        } else

            return false;

    }


    /**
     * @param totalCredit
     */
    public void setTotalCredit(Number totalCredit) {
        this.totalCredit = totalCredit;
    }

    /**
     * @return
     */
    public Number getTotalCredit() {
        /** Get total credit specific amount by adding all lines specific amount. */
        setAs(new Number(0));
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouLinesLnk();
        RowSetIterator rit = v1.createRowSetIterator(null);

        /** Create a iterator of tvou lines and by use of a while loop add specific amount of row having credit value. */

        /* if (rit.first() != null && rit.first().getAttribute("TvouAmtTyp").equals("Cr")) {
            as = (Number)(rit.first().getAttribute("TvouAmtSp"));
            System.out.println("rit.first().getAttribute(\"TvouAmtSp\") = "+rit.first().getAttribute("TvouAmtSp"));
            System.out.println("as = "+as);
        } */
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouAmtTyp").equals("Cr")) {
                as = as.add((Number) (lineRow.getAttribute("TvouAmtSp")));
                System.out.println("rit.first().getAttribute(\"TvouAmtSp\") cr= " + lineRow.getAttribute("TvouAmtSp"));
                System.out.println("as = " + as);
            }
        }
        rit.closeRowSetIterator();
        System.out.println("(Number)as.round(amt_digit) Cr=" + (Number) as.round(amt_digit));
        String crInString = totalCrInString(as);
        System.out.println("crInString = " + crInString);
        return (Number) as.round(amt_digit);
    }

    /**
     * @param totalDebit
     */
    public void setTotalDebit(Number totalDebit) {
        this.totalDebit = totalDebit;
    }

    /**
     * @return
     */
    public Number getTotalDebit() {
        /** Get total debit specific amount by adding all lines specific amount. */
        System.out.println("In getTotalDebit-------------------");
        setAs(new Number(0));
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouLinesLnk();
        RowSetIterator rit = v1.createRowSetIterator(null);

        /** Create a iterator of tvou lines and by use of a while loop add specific amount of row having debit value. */

        /*   if (rit.first() != null && rit.first().getAttribute("TvouAmtTyp").equals("Dr")) {
            System.out.println("rit.first().getAttribute(\"TvouAmtSp\") = "+rit.first().getAttribute("TvouAmtSp"));
            as = (Number)(rit.first().getAttribute("TvouAmtSp"));
            System.out.println("as = "+as);


        } */
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouAmtTyp").equals("Dr")) {
                System.out.println("rit.first().getAttribute(\"TvouAmtSp\") dr = " + lineRow.getAttribute("TvouAmtSp"));
                as = as.add((Number) (lineRow.getAttribute("TvouAmtSp")));
                System.out.println("as = " + as);
            }
        }
        rit.closeRowSetIterator();
        System.out.println("(Number)as.round(amt_digit) Dr= " + (Number) as.round(amt_digit));
        String crInString = totalCrInString(as);
        System.out.println("crInString = " + crInString);

        return (Number) as.round(amt_digit);
    }

    /**
     * @param totalBsCredit
     */
    public void setTotalBsCredit(Number totalBsCredit) {
        this.totalBsCredit = totalBsCredit;
    }

    /**
     * @param totalBsDebit
     */
    public void setTotalBsDebit(Number totalBsDebit) {
        this.totalBsDebit = totalBsDebit;
    }

    /**
     * @return
     */
    public Number getTotalBsCredit() {
        /** Get total credit base amount by adding all lines specific amount. */
        setAs(new Number(0));
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouLinesLnk();
        RowSetIterator rit = v1.createRowSetIterator(null);

        /** Create a iterator of tvou lines and by use of a while loop add basic amount of rows having credit value. */
        //   System.out.println(rit.getRowCount());
        while (rit.hasNext()) {
            Row lineRow = rit.next();

            if (lineRow.getAttribute("TvouAmtTyp").equals("Cr")) {
                //  System.out.println(lineRow.getAttribute("TvouId") + " " + lineRow.getAttribute("TvouAmtBs") + " " +
                //                   lineRow.getAttribute("TvouAmtTyp"));
                try {
                    if (lineRow.getAttribute("TvouAmtBs") != null) {

                        as = as.add((Number) (lineRow.getAttribute("TvouAmtBs")));
                        //                        System.out.println("rit.first().getAttribute(\"TvouAmtBs\") cr= " +
                        //                                           lineRow.getAttribute("TvouAmtBs"));
                        //                        System.out.println("as = " + as);
                    } else {

                        as = as.add(0);
                    }
                } catch (Exception e) {
                    as = as.add(0);
                    e.printStackTrace();
                }
            }
        }
        //  rit.closeRowSetIterator();
        return (Number) as.round(amt_digit);
    }


    /**
     * @return
     */
    public Number getTotalBsDebit() {
        /** Get total debit base amount by adding all lines specific amount. */
        setAs(new Number(0));
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouLinesLnk();
        RowSetIterator rit = v1.createRowSetIterator(null);

        /** Create a iterator of tvou lines and by use of a while loop add basic amount of row having debit value. */

        while (rit.hasNext()) {
            //  System.out.println("total bs debit-->"+rit.hasNext()+"and nex row-->"+rit.next());
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouAmtTyp").equals("Dr")) {
                try {
                    // System.out.println("Amout  from line-->"+lineRow.getAttribute("TvouAmtBs"));
                    as = as.add((Number) (lineRow.getAttribute("TvouAmtBs")));
                    //                    System.out.println("rit.first().getAttribute(\"TvouAmtBs\") debitr: = " +
                    //                                       lineRow.getAttribute("TvouAmtBs"));
                    //                    System.out.println("as = " + as);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
        //    rit.closeRowSetIterator();
        return (Number) as.round(amt_digit);
    }

    private Number asCr = new Number();

    /** Function to get total taxable amount from tvou lines.
     * @return
     */
    public Number getTaxAmount() {
        setSum(new Number(0));
        setAs(new Number(0));
        setAsCr(new Number(0));
        TempVoucherAMImpl am;
        am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouLinesLnk();
        RowSetIterator rit = v1.createRowSetIterator(null);
        /** NOTE : This Calculation should be computed on Basic Amount not on Specific amount*/
        /**Add debit taxable amount of tvou lines*/
        if (rit.first() != null && rit.first().getAttribute("TvouTxnTypTax").equals("Y") &&
            rit.first().getAttribute("TvouAmtTyp").equals("Dr")) {
            as = (Number) (rit.first().getAttribute("TvouAmtSp"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouTxnTypTax").equals("Y") && lineRow.getAttribute("TvouAmtTyp").equals("Dr")) {
                as = as.add((Number) (lineRow.getAttribute("TvouAmtSp")));

            }
        }

        /**Add credit taxable amount of tvou lines*/
        if (rit.first() != null && rit.first().getAttribute("TvouTxnTypTax").equals("Y") &&
            rit.first().getAttribute("TvouAmtTyp").equals("Cr")) {
            asCr = (Number) (rit.first().getAttribute("TvouAmtSp"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouTxnTypTax").equals("Y") && lineRow.getAttribute("TvouAmtTyp").equals("Cr")) {
                asCr = asCr.add((Number) (lineRow.getAttribute("TvouAmtSp")));
            }
        }

        /**Get taxable amount by getting difference of credit and debit amount*/
        if (as.compareTo(asCr) == 1) {
            setSum((Number) (as.minus(asCr)));
        } else if (as.compareTo(asCr) == -1) {

            setSum((Number) (asCr.minus(as)));
        }
        //   rit.closeRowSetIterator();
        return Sum;
    }

    /** Function to get total taxable amount from tvou lines.
     * @return
     */
    public Number getTdsAmount() {
        setSum(new Number(0));
        setAs(new Number(0));
        setAsCr(new Number(0));
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouLinesLnk();
        RowSetIterator rit = v1.createRowSetIterator(null);

        /**Add debit tds amount of tvou lines*/
        if (rit.first() != null && rit.first().getAttribute("TvouTxnTypTds").equals("Y") &&
            rit.first().getAttribute("TvouAmtTyp").equals("Dr")) {
            as = (Number) (rit.first().getAttribute("TvouAmtSp"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouTxnTypTds").equals("Y") && lineRow.getAttribute("TvouAmtTyp").equals("Dr")) {
                as = as.add((Number) (lineRow.getAttribute("TvouAmtSp")));

            }
        }

        /**Add credit tds amount of tvou lines*/
        if (rit.first() != null && rit.first().getAttribute("TvouTxnTypTds").equals("Y") &&
            rit.first().getAttribute("TvouAmtTyp").equals("Cr")) {
            asCr = (Number) (rit.first().getAttribute("TvouAmtSp"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouTxnTypTds").equals("Y") && lineRow.getAttribute("TvouAmtTyp").equals("Cr")) {
                asCr = asCr.add((Number) (lineRow.getAttribute("TvouAmtSp")));

            }
        }

        /**Get tds amount by getting difference of credit and debit amount*/
        if (as.compareTo(asCr) == 1) {
            setSum((Number) (as.minus(asCr)));
        } else if (as.compareTo(asCr) == -1) {

            setSum((Number) (asCr.minus(as)));
        }
        rit.closeRowSetIterator();
        return Sum;
    }

    /**
     * @return basic amount on which tax is applied.
     */
    public Number getTaxBasicAmount() {

        //System.out.println("inside getTaxBasicAmount");
        BindingContainer bindingContainer = getBindings();
        OperationBinding binding = bindingContainer.getOperationBinding("getTaxBA");
        BigDecimal basicTax = (BigDecimal) binding.execute();
        //System.out.println("basicTax " + basicTax);

        Number basicTaxamt;
        try {
            if (basicTax != null) {
                basicTaxamt = new Number(basicTax);
            } else {
                basicTaxamt = new Number(0);
            }

        } catch (SQLException e) {
            basicTaxamt = new Number(0);
            e.printStackTrace();
        }
        return basicTaxamt;
    }

    /**
     * @return basic amount on which tds is applied.
     */
    public BigDecimal getTdsBasicAmount() {

        BindingContainer bindingContainer = getBindings();
        OperationBinding binding = bindingContainer.getOperationBinding("getTdsBA");
        BigDecimal basicTds = (BigDecimal) binding.execute();
        System.out.println("basicTds = " + basicTds);

        return basicTds;
    }

    /**
     * @param asCr
     */
    public void setAsCr(Number asCr) {
        this.asCr = asCr;
    }

    /**
     * @return
     */
    public Number getAsCr() {
        return asCr;
    }

    /**
     * @param adjustCrDrNote
     */
    public void setAdjustCrDrNote(Boolean adjustCrDrNote) {
        this.adjustCrDrNote = adjustCrDrNote;
    }

    /**
     * @return boolean value to check whether voucher type is credit/debit note or not. This value is used by task flow router "adjust" to choose one of the operation.
     */
    public Boolean getAdjustCrDrNote() {

        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouLinesLnk();
        Row LnRow = v1.getCurrentRow();
        if (Integer.parseInt(LnRow.getAttribute("TvouTypeId").toString()) == 9 ||
            Integer.parseInt(LnRow.getAttribute("TvouTypeId").toString()) == 10 ||
            Integer.parseInt(LnRow.getAttribute("TvouTypeId").toString()) == 11 ||
            Integer.parseInt(LnRow.getAttribute("TvouTypeId").toString()) == 12) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param unsaved
     */
    public void setUnsaved(Boolean unsaved) {
        this.unsaved = unsaved;
    }

    /**
     * @return boolean value after checking application modules status whether it is unsaved or not.
     */
    public Boolean getUnsaved() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        return am.getTransaction().isDirty();
        //return true;
    }

    /** Function called from cancel button on Other charges page.
     * @return
     */


    /**
     * @param DocId
     */
    public void setDocId(Integer DocId) {
        this.DocId = DocId;
    }

    /**
     * @return
     */
    public Integer getDocId() {
        return DocId;
    }

    /**
     * @param DocEntType
     */
    public void setDocEntType(Integer DocEntType) {
        this.DocEntType = DocEntType;
    }

    /**
     * @return
     */
    public Integer getDocEntType() {
        return DocEntType;
    }


    /**
     * @param weStatus
     */
    public void setWeStatus(String weStatus) {
        this.weStatus = weStatus;
    }

    /**
     * @return get value of Work flow status using binding method
     */
    public String getWeStatus() {

        OperationBinding createOpBAddress = getBindings().getOperationBinding("getVoucherStatus");
        String result = (String) createOpBAddress.execute();
        // System.out.println("status " + result);
        return result;
    }

    /**
     * @param mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return
     */
    public String getMode() {
        if (mode.equals("")) {
            return modeGet();
        } else {
            return mode;
        }
    }

    /**
     * @param actionEvent
     * Edited by Priyank Khare on 04-06-2014.
     */
    public void editVoucher(ActionEvent actionEvent) {

        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}").toString());

        Integer pending = (Integer) callBindingsMethod("getDocUsrFromWF", null, null);

        System.out.println(pending + "--------" + usr_id);

        if (!pending.equals(usr_id)) {
            System.out.println("user lov row " + getAm().getUserLov1().getEstimatedRowCount());
            Row[] usrRows = getAm().getUserLov1().getFilteredRows("UsrId", pending);
            System.out.println("usrRows " + usrRows.length);
            StringBuffer usrName = new StringBuffer("");
            if (usrRows.length > 0) {
                usrName = new StringBuffer(usrRows[0].getAttribute("UsrName").toString());
                usrName = new StringBuffer("[").append(usrName).append("]");
            }

            //This Voucher is pending at other user for approval, You can not edit this.*
            //create multi line faces message using StringBuilder

            String msg2 = resolvElDCMsg("#{bundle['MSG.506']}").toString();
            StringBuilder saveMsg = new StringBuilder("<html><body><p><b>" + msg2 + "</b></p>");
            saveMsg.append("Pending at- <b> " + usrName + "</b>");
            saveMsg.append("</body></html>");

            // use the helper method to show faces messages.
            showFacesMsg(saveMsg.toString(), null, FacesMessage.SEVERITY_INFO, null);

        } else {
            //get and set the work flow Id variable.
            setWorkFlwId(getWfId());
            oldManualTax = manualTaxBox.getValue().toString();
            oldManualTds = manualTdsBox.getValue().toString();
            //set the edit mode flag
            System.out.println("setting edit mode");
            setMode("E");
            setModeExpense("E");

        }
    }

    public Object resolvElAmt(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object digit = 2;
        if (valueExp != null) {
            digit = valueExp.getValue(elContext);
        }
        if (digit == null) {
            digit = 2;
        }
        //  System.out.println("Amount digit is--->" + digit);
        return digit;
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = "N";
        if (valueExp != null) {
            msg = valueExp.getValue(elContext).toString();
        }
        return msg;
    }

    /**
     * @param fnTxnMode
     */
    public void setFnTxnMode(String fnTxnMode) {
        this.fnTxnMode = fnTxnMode;
        System.out.println("inside " + fnTxnMode);
    }

    /**
     * @return
     */
    public String getFnTxnMode() {
        return fnTxnMode;
    }


    /**
     * @param saveAsGl
     */
    public void setSaveAsGl(Boolean saveAsGl) {
        this.saveAsGl = saveAsGl;
    }

    /**
     * @return
     */
    public Boolean getSaveAsGl() {
        return saveAsGl;
    }

    /**
     * @param region
     */
    public void setRegion(RichRegion region) {
        this.region = region;
    }

    /**
     * @return
     */
    public RichRegion getRegion() {
        return region;
    }

    /** Function called on value change of template LOV
     * @param valueChangeEvent
     */
    public void templateSelect(ValueChangeEvent valueChangeEvent) {


        if (valueChangeEvent.getNewValue() != null) {

            ViewObjectImpl tvouImpl = getAm().getTvou1();

            /* RowSet rSet = ((TvouVORowImpl)tvouImpl.getCurrentRow()).getTmplVouVO();
            Row[] r = rSet.getFilteredRows("TmplName", valueChangeEvent.getNewValue()); */

            Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}").toString());
            String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID1}").toString();
            String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
            String orgId = resolvEl("#{pageFlowScope.ParamOrgId}").toString();
            Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}").toString());
            System.out.println("In template lov change userId= " + userId + " hoOrg_id = " + hoOrg_id + " cld_id = " +
                               cld_id + " orgId = " + orgId + " slocId = " + slocId);
            TmplVouVOImpl tmpl = getAm().getTmplVou();
            tmpl.setNamedWhereClauseParam("BindCldId", cld_id);
            tmpl.setNamedWhereClauseParam("BindHoOrgId", hoOrg_id);
            tmpl.setNamedWhereClauseParam("BindOrgId", orgId);
            tmpl.setNamedWhereClauseParam("BindSlcId", slocId);
            tmpl.setNamedWhereClauseParam("BindUsrId", userId);
            tmpl.executeQuery();
            Row[] r = tmpl.getFilteredRows("TmplName", valueChangeEvent.getNewValue());
            // System.out.println("r length " + r.length);
            System.out.println("valueChangeEvent.getNewValue() = " + valueChangeEvent.getNewValue());
            //TvouTemplateId is required in the generateFromTemplate method
            if (r.length > 0) {
                System.out.println("r[0].getAttribute(\"TmplVouId\") = " + r[0].getAttribute("TmplVouId"));
                tvouImpl.getCurrentRow().setAttribute("TvouTemplateId", r[0].getAttribute("TmplVouId"));
                tvouImpl.getCurrentRow().setAttribute("TvouTypeId", r[0].getAttribute("TmplVouTypeId"));
                tvouImpl.getCurrentRow().setAttribute("TvouCoaId", r[0].getAttribute("TmplVouCoaId"));
                tvouImpl.getCurrentRow().setAttribute("TvouSubTypeId", r[0].getAttribute("TmplVouSubTypeId"));
                tvouImpl.getCurrentRow().setAttribute("TvouCurrIdSp", r[0].getAttribute("TmplCurrIdSp"));
                tvouImpl.getCurrentRow().setAttribute("TvouCc", r[0].getAttribute("TmplVouCc"));
                if (r[0].getAttribute("TmplVouTypeId").equals(7) || r[0].getAttribute("TmplVouTypeId").equals(8) ||
                    r[0].getAttribute("TmplVouTypeId").equals(14)) {
                    tvouImpl.getCurrentRow().setAttribute("TvouDueDt", tvouImpl.getCurrentRow().getAttribute("TvouDt"));
                }


            }

            //Queue an action event to create lines after valueChangeEvent
            ActionEvent ae = new ActionEvent(createTemplateButton);
            ae.queue();

            AdfFacesContext.getCurrentInstance().addPartialTarget(debitAmt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(creditAmt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(touLinesTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(templatePanel);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveAndForwardBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tvouTypBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(addInstrumentDetailBinding);

            // System.out.println("action called");
        }
    }

    /**
     * @param templVouId
     */
    public void setTemplVouId(String templVouId) {
        this.templVouId = templVouId;
    }

    /**
     * @return
     */
    public String getTemplVouId() {
        return templVouId;
    }


    /**
     * @return
     */
    public String addCheque() {

        getAm().getTvouLineChq().setWhereClause(null);
        getAm().getTvouLineChq().executeQuery();
        Integer rCount = getAm().getTvouLineChq().getRowCount();

        //   System.out.println("RowCount is cheque page-->" + rCount);
        if (rCount > 0) {
            setTable(true);
            setForm(false);
        } else {
            // System.out.println("No row in Cheque page--->");
            setTable(false);
            setForm(true);
            this.modeCheque = false;
            /** Add a new row. */
            OperationBinding createOpB1 = getBindings().getOperationBinding("CreateInsert1");
            createOpB1.execute();
            //Code to get total amount from TempVoucherDtlForm bean- 06-03-2013

            // System.out.println("Total Amount from Bean Object---->" + getTotalCredit());
            /** Get amount from voucher table. */
            Number amt = getTotalCredit();
            if (amt.compareTo(0) == 0) {
                amt = getTotalDebit();
            }
            // System.out.println("Amount is---->" + amt);
            TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC("TempVoucherAMDataControl");
            ViewObject vo = am.getTvouLineChq();

            /**New Code to get Serial No.- 6-03-2013-@Ashish Kumar*/
            Integer Srno = 0;
            Integer max = 0;
            vo.setRangeSize(-1);
            Row row[] = vo.getAllRowsInRange();
            for (Row r : row) {
                try {
                    Srno = Integer.parseInt(r.getAttribute("TvouChqSlNo").toString());
                } catch (NullPointerException e) {
                    Srno = 0;

                }
                //      System.out.println("BeanCount is--->" + Srno);
                if (Srno > max) {
                    max = Srno;
                }

            }

            max = max + 1;
            Row crow = vo.getCurrentRow();
            crow.setAttribute("TvouChqSlNo", max);

            try {
                Row curRow = vo.getCurrentRow();
                // System.out.println("Voucher Id is this row--->" + curRow.getAttribute("TvouId"));
                curRow.setAttribute("TvouAmtSp", amt);
                Row tvouLine = getAm().getTvouLinesLnk().getCurrentRow();

                TvouLineChqVORowImpl chqRow = (TvouLineChqVORowImpl) vo.getCurrentRow();
                //     System.out.println("CoaId from Lines--->" + tvouLine.getAttribute("TvouCoaId"));
                chqRow.setTvouCoaId((Integer) tvouLine.getAttribute("TvouCoaId"));
                chqRow.setTvouCoaBnkId(Integer.parseInt(getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId").toString()));
                chqRow.setAttribute("TvouInstrmntDt", getAm().getTvou1().getCurrentRow().getAttribute("TvouDt"));
                String name = chqRow.getChqNm();
                curRow.setAttribute("TvouChqNm", name);

            } catch (NullPointerException e) {
                System.out.println("Exceptio is-->" + e);
                // e.printStackTrace();
            }


        }
        return "chequeDt";
    }

    /**
     * Function called to import CSV file of tvou lines.
     */
    public void importFileTvouLine(ActionEvent actionEvent) {
        /**Open pop up for file selection */
        this.showPopup(fileImportPopUp, true);
    }

    /**
     * @param fileImportPopUp
     */
    public void setFileImportPopUp(RichPopup fileImportPopUp) {
        this.fileImportPopUp = fileImportPopUp;
    }

    /**
     * @return
     */
    public RichPopup getFileImportPopUp() {
        return fileImportPopUp;
    }

    /** Function called on selection of file for import.
     * @param tvouLnFile
     */
    public void setTvouLnFile(UploadedFile tvouLnFile) {
        this.tvouLnFile = tvouLnFile;

        try {
            /** Function call to copy file to server */
            saveTvouLineFile(tvouLnFile.getInputStream());
            /** After saving file on server create a command to run sql loader. */
            String cmd =
                "sqlldr userid=fin/ebizFIN@live  control=c:\\temp\\adj\\tvouline.ctl log=c:\\temp\\adj\\tvouline.log bad=c:\\temp\\adj\\tvouline.bad data=c:\\temp\\adj\\" +
                tvouLnFile.getFilename() + " discard=c:\\temp\\adj\\tvouline.discard";
            //   System.out.println("CMD------->> :" + cmd);
            /**run command, executing command on sql loader data from uploaded file will be copied to a temporary table "TvouLinesDummy"*/
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            System.out.println("Exception in upload to db");
        }
    }

    /**
     * Function to copy file from client to server.
     */
    public void saveTvouLineFile(java.io.InputStream is) {

        try {
            byte b[] = new byte[4096];
            int bytes_read;
            /** Create file by giving full path and file name */
            File file = new File("c:\\temp\\adj\\" + tvouLnFile.getFilename());
            /** Create a file output stream to write on to output file */
            FileOutputStream dout = new FileOutputStream(file);
            /** Write byte by byte on to output file */
            while ((bytes_read = is.read(b)) != -1) {
                // Read until EOF
                dout.write(b, 0, bytes_read); // write





















            }
        } catch (Exception ex) {
            throw new JboException(ex.getMessage());
        } finally {
            try {
                /** Close file after completion */
                is.close();
            } catch (Exception ex) {
                throw new JboException(ex.getMessage());
            }
        }
    }

    /**
     * @return
     */
    public UploadedFile getTvouLnFile() {
        return tvouLnFile;
    }

    /** Function called after pressing ok or cancel button of dialog on file upload popup.
     * @param dialogEvent
     */
    public void fileDialog(DialogEvent dialogEvent) {
        /** If ok button of popup is pressed */
        if (dialogEvent.getOutcome().name().equals("ok")) {
            /** Call "importTvouLn" function from bindings to copy data from temporary table "tvouLinesDummy" to tvoulines */

            OperationBinding createOpBAddress = getBindings().getOperationBinding("importTvouLn");
            createOpBAddress.execute();
        }
    }


    /**
     * @param chartOfAc
     */
    public void setChartOfAc(RichSelectOneChoice chartOfAc) {
        this.chartOfAc = chartOfAc;
    }

    /**
     * @return
     */
    public RichSelectOneChoice getChartOfAc() {
        return chartOfAc;
    }


    /**
     * @param narrationCopy
     */
    public void setNarrationCopy(Boolean narrationCopy) {
        this.narrationCopy = narrationCopy;
    }

    /**
     * @return
     */
    public Boolean getNarrationCopy() {
        return narrationCopy;
    }


    /** Function called after pressing ok or cancel button of dialog on file upload popup.
     * @param dialogEvent
     */

    public void templateSave(DialogEvent dialogEvent) {
        /** If ok button of popup is pressed */
        if (dialogEvent.getOutcome().name().equals("ok")) {
            /** Call "saveAsTemplate" function from bindings to save voucher in to voucher template tables */
            FacesContext fc = FacesContext.getCurrentInstance();
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("saveAsTemplate");
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                commit();
                OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute");
                createOpBAddress1.execute();
                //Saved As Template
                FacesMessage msg =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.507']}").toString()); //Saved As template
                fc.addMessage(null, msg);
            }
        }
    }

    /**
     * @param tmplVPopup
     */
    public void setTmplVPopup(RichPopup tmplVPopup) {
        this.tmplVPopup = tmplVPopup;
    }

    /**
     * @return
     */
    public RichPopup getTmplVPopup() {
        return tmplVPopup;
    }


    /**
     * @param touLinesTab
     */
    public void setTouLinesTab(RichTable touLinesTab) {
        this.touLinesTab = touLinesTab;
    }

    /**
     * @return
     */
    public RichTable getTouLinesTab() {
        return touLinesTab;
    }

    /** Function to get next maximum no for tvou lines slNo
     * @return
     */
    public Integer getMaxNo() {
        /** Create iterator for tvou lines */
        TempVoucherAMImpl am = this.getAm();
        ViewObject TvouVw = am.getTvouLinesLnk();
        RowSetIterator rSetIter = TvouVw.createRowSetIterator(null);
        int max = 0;
        Row cRow = null;
        /** Run loop for all rows of tvou lines and set incremented value to variable max */
        while (rSetIter.hasNext()) {
            cRow = rSetIter.next();
            //   System.out.println("row " + cRow.getAttribute("TvouSlNo"));
            if (Integer.parseInt(cRow.getAttribute("TvouSlNo").toString()) >= max) {
                max = Integer.parseInt(cRow.getAttribute("TvouSlNo").toString()) + 1;
            }
        }
        /** Return incremented slNo value */
        rSetIter.closeRowSetIterator();
        //System.out.println("TvouLine Max Slno-->" + max);
        return max;
    }

    /**
     * Fuction will check whether or not tax and tds is applied, if not then delete previous tax, tds entries .
     * */
    public void checkTaxTds() {
        System.out.println("<--- In  checkTaxTds--->");

        TempVoucherAMImpl am = this.getAm();
        /** Create object of TvouLines */
        ViewObject tvouLine = am.getTvouLinesLnk();
        RowSetIterator rowIterator = tvouLine.createRowSetIterator(null);
        int taxLines = 0;
        int tdsLines = 0;
        if (getTaxYN() == false) {
            System.out.println("when taxYn is false");
            /** Run loop for all rows of tvou lines if check box for tax is not checked */
            while (rowIterator.hasNext()) {
                Row currVouLne = rowIterator.next();
                if (currVouLne.getAttribute("TvouLnTyp").equals("DTAX")) {
                    /** Check for row of tax entry and delete it*/
                    taxLines += 1;

                    currVouLne.remove();
                    System.out.println("after removing row = " + taxLines);
                }
            }
            tvouLine.executeQuery();
            if (taxLines > 0) {
                /**if a row for tax entry is present then delete rows from tax tables. */
                taxDelete();
            } else if (exemptedFlagBinding.getValue() != null) {
                if (exemptedFlagBinding.getValue().toString().equalsIgnoreCase("Y")) {
                    taxDelete();
                }
            }
        }
        if (getTdsYN() == false) {
            /** Run loop for all rows of tvou lines if check box for tds is not checked */
            while (rowIterator.hasNext()) {
                Row currVouLne = rowIterator.next();
                if (currVouLne.getAttribute("TvouLnTyp").equals("DTDS")) {
                    /** Check for row of tds entry and delete it */
                    tdsLines += 1;
                    currVouLne.remove();
                } else if (currVouLne.getAttribute("TvouLnTyp").equals("DETD") &&
                           currVouLne.getAttribute("TvouTypeId").equals(8)) {
                    currVouLne.remove();
                }
            }
            tvouLine.executeQuery();

            if (tdsLines > 0) {
                /**if a row for tdsentry is present then delete rows from tds tables. */
                tdsDelete();
            }
        }
        rowIterator.closeRowSetIterator();
    }

    public void taxDelete() {
        System.out.println("<--- In TaxDelete--->");

        TempVoucherAMImpl am = this.getAm();
        Row curTvou = am.getTvou1().getCurrentRow();
        String vouId = curTvou.getAttribute("TvouId").toString();
        //  System.out.println("Voucher id from Tax delete is-->" + vouId);

        /** Create object for tax rule view*/
        ViewObject tvouTax = am.getTvouTaxRuleHd();
        /** Create object for tax rule line view*/
        ViewObject tvouTaxLine = am.getTvouTaxRuleLineHd();
        //    Row currentRow = tvouTax.getCurrentRow();

        RowSetIterator rowIterator = tvouTaxLine.createRowSetIterator(null);
        /**Remove all row from tax rule line table for this voucher */

        while (rowIterator.hasNext()) {
            Row currVouLne = rowIterator.next();
            System.out.println("<--- before removing tax line --->");

            currVouLne.remove();
        }
        /**Remove current row from tax rule table*/
        try {
            Row row[] = tvouTax.getFilteredRows("TvouId", vouId);
            if (row.length > 0) {
                System.out.println("<--- before removing tax rule --->");

                row[0].remove();
                System.out.println("Total Tax Row Removed is--->" + row.length);
            }
        } catch (Exception e) {
            System.out.println("Inside Tax Delete Exception-->" + e.getMessage());
            e.printStackTrace();
        }
        rowIterator.closeRowSetIterator();
        tvouTaxLine.executeQuery();
    }

    public void tdsDelete() {
        TempVoucherAMImpl am = this.getAm();
        Row curTvou = am.getTvou1().getCurrentRow();
        String vouId = curTvou.getAttribute("TvouId").toString();
        //  System.out.println("Voucher id from Tax delete is-->" + vouId);
        /** Create object for tds rule view*/
        ViewObject tvouTds = am.getTvouTdsRuleHd();
        /** Create object for tds rule line view*/
        ViewObject tvouTdsLine = am.getTvouTdsRuleLineHd();

        RowSetIterator rowIterator = tvouTdsLine.createRowSetIterator(null);
        /**Remove all row from tds rule line table for this voucher */
        while (rowIterator.hasNext()) {
            Row currVouLne = rowIterator.next();
            System.out.println("<--- before removing tds rule line --->");
            currVouLne.remove();
        }
        /**Remove current row from tds rule table*/
        rowIterator.closeRowSetIterator();
        try {
            Row row[] = tvouTds.getFilteredRows("TvouId", vouId);
            System.out.println("<--- before removing tds rule --->");

            row[0].remove();
            System.out.println("Total TDS Row Removed is--->" + row.length);
        } catch (Exception e) {
            System.out.println("Inside Tds Delete Exception-->" + e.getMessage());
            e.printStackTrace();
        }
        tvouTdsLine.executeQuery();
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void taxRuleVCE(ValueChangeEvent valueChangeEvent) {

        TempVoucherAMImpl am = this.getAm();

        String rulei = valueChangeEvent.getNewValue().toString();

        if (rulei != null) {
            am.procTaxForHdr(rulei);
            this.cancelTaxDis = true;
        }

        ViewObject impl = am.getTvouTaxRuleLineHd();
        impl.executeQuery();

        if (impl.getRowCount() > 0) {
            this.cancelTaxDis = true;
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(canceltaxButtonBinding);
    }

    public void tdsRuleVCE(ValueChangeEvent valueChangeEvent) {

        TempVoucherAMImpl am = this.getAm();


        String rulei = valueChangeEvent.getNewValue().toString();

        if (rulei != null) {
            am.procTdsForHdr(rulei);
            this.cancelTdsDis = true;
        }

        ViewObjectImpl impl = am.getTvouTdsRuleLineHd();
        impl.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelTdsBinding);

        //  System.out.println("No of TDS lines--->" + impl.getEstimatedRowCount());
    }

    public void coaValueChangeDtl(ValueChangeEvent vce) {
        //System.out.println("coa VCE");
        AdfFacesContext.getCurrentInstance().addPartialTarget(basic_amt);
        String coaName = vce.getNewValue().toString();
        Integer coaId = null;
        //  System.out.println("In value change Listener");
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObjectImpl v = am.getTvouLinesLnk();

        Row currRow = v.getCurrentRow();
        if (resolveElExp("#{bindings.TvouTypeId.inputValue}") != null) {
            Integer vouType = Integer.parseInt(resolveElExp("#{bindings.TvouTypeId.inputValue}").toString());
            if (vouType.equals(8)) {
                if (billNumberBinding != null) {
                    if (billNumberBinding.getValue() != null) {
                        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
                        duplicateBillNumber(billNumberBinding.getValue(), null);
                    }
                }
            }
        }
        if (vce.getNewValue() != null) {

            Row[] xx = am.getLovVouCoa().getFilteredRows("CoaNm", coaName);

            if (xx.length > 0) {
                coaId = (Integer) xx[0].getAttribute("CoaId");
                v.getCurrentRow().setAttribute("TvouCoaId", coaId);
                System.out.println("Code added to change the currency id ---------coaId>>>> " + coaId);
                /** Code added to change the currency id according to coa selected*/
                if (getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(6)) {
                    BigDecimal currency = (BigDecimal) callStoredFunction2(Types.NUMERIC, "FIN.FN_COA_CURR(?,?,?,?)", new Object[] {
                                                                           cld_id, sloc_id, HoOrgId, coaId
                    });
                    System.out.println("currency " + currency);

                    Integer curr = 73;

                    try {
                        curr = Integer.parseInt(currency.toString());

                        if (curr != null) {

                            //error in getting filtered row,  hence setNamedWhereClauseParam is being added
                            ViewObjectImpl tvou = getAm().getTvou1();
                            Row currentRow = tvou.getCurrentRow();

                            Integer TvouCurrIdBs = 73;
                            if (currentRow != null) {
                                TvouCurrIdBs = (Integer) currentRow.getAttribute("TvouCurrIdBs");
                            }

                            LovLatestCurrImpl lovLatestCurr = getAm().getLovLatestCurr1();
                            lovLatestCurr.setNamedWhereClauseParam("BindVouDt", currentRow.getAttribute("TvouDt"));
                            lovLatestCurr.setNamedWhereClauseParam("BindOrgId", currentRow.getAttribute("TvouOrgId"));
                            lovLatestCurr.setNamedWhereClauseParam("BindCurrId", currRow.getAttribute("TvouCurrIdBs"));
                            lovLatestCurr.executeQuery();
                            Row[] xx1 = lovLatestCurr.getFilteredRows("CcCurrIdTxn", curr);

                            if (xx1.length > 0) {
                                Number rate = (Number) xx1[0].getAttribute("CcBuy");
                                currRow.setAttribute("TvouCurrIdSp", curr);
                                currRow.setAttribute("TvouCc", rate);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                /** Get value for the chart of account whether it is a creditor or debtor*/
                String crOrDr =
                    getCrorDrValue(currRow.getAttribute("TvouCldId").toString(),
                                   Integer.parseInt(currRow.getAttribute("TvouSlocId").toString()),
                                   currRow.getAttribute("TvouHoOrgId").toString(),
                                   currRow.getAttribute("TvouOrgId").toString(), coaId);
                /** Set transient attribute "CredrOrDebtr" with return   value of DB function call */
                currRow.setAttribute("CredrOrDebtr", crOrDr);
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(basic_amt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(txdtl);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tdsDtl);
            AdfFacesContext.getCurrentInstance().addPartialTarget(advance);
            AdfFacesContext.getCurrentInstance().addPartialTarget(addChequeBind);
            if (getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(7) ||
                getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(8) ||
                getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(14)) {
                ADFBeanUtils.callBindingsMethod("defaultBillDateSet", null, null);
            }
        }
        Number accblAmt = (Number) v.getCurrentRow().getAttribute("TvouTaxtdsAssessVal");
        setAccblAmt(accblAmt);
    }

    public void ManualTaxEntryVCE(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String check = vce.getNewValue().toString();
        System.out.println("CHECK BOXX:" + check);
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        /**Object of TvouLines.*/
        ViewObject vouViwln = am.getTvouLinesLnk();
        /**current row of tvouLines.*/


        if (check.equalsIgnoreCase("true")) {

            RowSetIterator rit = vouViwln.createRowSetIterator(null);
            while (rit.hasNext()) {
                Row curLn = rit.next();
                System.out.println(curLn.getAttribute("TvouTxnTyp"));
                if (curLn.getAttribute("TvouTxnTypTax").equals("Y")) {
                    curLn.setAttribute("TvouTxnTypTax", "N");

                }
            }

            rit.closeRowSetIterator();

            //if manual tax is applied then tax rule selection is disabled
            // txdtl.setDisabled(true);

            //to enable assessable value field if manual TAX is checked.
            assessableValBind.setVisible(true);

        } else {
            // txdtl.setDisabled(false);

            //to disable assesable value field only if manual TAX & TDS are unchecked.
            ViewObject tvouVO = am.getTvou1();
            Row hdrRow = tvouVO.getCurrentRow();
            if (hdrRow.getAttribute("TvouManTds").equals("N")) {
                assessableValBind.setVisible(false);
                assessableValBind.setValue(new Number(0));
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(txdtl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(assessableValBind);
    }

    public void coaNameDtlValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // uIComponent.processUpdates(FacesContext.getCurrentInstance());
        //  System.out.println("Inside Validator");duplicateCoaValidator

        OperationBinding binding = getBindings().getOperationBinding("duplicateCoaValidator");
        binding.getParamsMap().put("coaName", object.toString());
        binding.execute();
        Object res = binding.getResult();
        System.out.println("result of coa validtor = " + res);
        if (res.equals("Y")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.364']}"),
                                                          null)); //Duplicate COA Found.
        }
        Number basic = new Number(0);
        if (basic_amt.getValue() != null) {
            basic = (Number) basic_amt.getValue();
        }
        /*  if(billNumberBinding!=null){
            if(billNumberBinding.getValue()!=null){
                billNumberBinding.setValue(null);
            }
        } */
        Integer coaType = null;
        Integer vouType = null;
        Integer lineNo = null;
        String coaBehaviour = null;
        Integer minLineNo = getMinLineNo();

        if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CoaTypeTrans") != null) {
            coaType =
                Integer.parseInt(getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CoaTypeTrans").toString());
            setCoaType(coaType);
            // System.out.println("coaType in coaValidator---->" + coaType);
        }
        if (resolveElExp("#{bindings.TvouTypeId.inputValue}") != null) {
            vouType = Integer.parseInt(resolveElExp("#{bindings.TvouTypeId.inputValue}").toString());
            // System.out.println("vouType in coaValidator---->" + vouType);
        }
        if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouSlNo") != null) {
            lineNo = Integer.parseInt(getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouSlNo").toString());
            // System.out.println("lineNo in coaValidator---->" + lineNo);
        }
        if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CredrOrDebtr") != null) {

            coaBehaviour = getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CredrOrDebtr").toString();

        }
        /*  Object CoaIdCurrLine=null;
        Row[] filteredRows = getAm().getLovVouCoa().getFilteredRows("CoaNm", object);
        if(filteredRows.length>0){
             CoaIdCurrLine = filteredRows[0].getAttribute("CoaId");
            System.out.println("CoaIdCurrLine = "+CoaIdCurrLine);
            getAm().getTvouLinesLnk().getCurrentRow().setAttribute("TvouCoaId", CoaIdCurrLine);

        }  */

        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);

        if (object == null && basic.compareTo(0) == 1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.508']}").toString(),
                                                          null)); //This field is required
        } else if (object != null) {
            // To get the account type of COA in validation.
            String coaNm = object.toString();
            Row[] xx = am.getLovVouCoa().getFilteredRows("CoaNm", coaNm);

            if (xx.length > 0) {
                coaType = Integer.parseInt(xx[0].getAttribute("AccType").toString());

                if (xx[0].getAttribute("EoBhav") != null) {

                    coaBehaviour = xx[0].getAttribute("EoBhav").toString();
                    // System.out.println("coaBehaviour " + coaBehaviour);

                } else {
                    coaBehaviour = "N";
                }
            }
        }

        /** Added on 09/09/2013 by Priyank Khare
         * Validation for  each voucher as per their type id in ascending order.
        * **/

        if (coaType != null && vouType != null && lineNo != null && coaBehaviour != null) {

            //  To validate for non Bank cash account in each line item.

            if (vouType.equals(1)) {
                System.out.println("validation voucher 1");
                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Journal Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.587']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg,
                                                                  null)); //Bank or Cash account are not allowed for Journal Voucher.

                }

            } else if (vouType.equals(2)) {

                //  To validate for non Bank cash account in each line item.
                // System.out.println("validation voucher 2");
                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Bank Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.588']}").toString(); //Bank or Cash account are not allowed for Bank Payment Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                }

                RowSetIterator rit = getAm().getTvouLinesLnk().createRowSetIterator(null);
                Integer countDebtor = 0;
                Integer countCreditor = 0;
                //  System.out.println("coaType----->" + coaType);

                if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CoaTypeTrans") == null) {

                    if (coaBehaviour.equals("C")) {

                        countCreditor = countCreditor + 1;

                    } else if (coaBehaviour.equals("D")) {

                        countDebtor = countDebtor + 1;
                    }
                }
                // System.out.println("countCreditor---->" + countCreditor);

                //  System.out.println("countDebtor------>" + countDebtor);
                if (rit.first() != null && rit.first().getAttribute("CredrOrDebtr") != null) {

                    if (rit.first().getAttribute("CredrOrDebtr").equals("C")) {

                        countCreditor = countCreditor + 1;
                    } else if (rit.first().getAttribute("CredrOrDebtr").equals("D")) {

                        countDebtor = countDebtor + 1;
                    }
                }

                while (rit.hasNext()) {

                    Row lineRow = rit.next();
                    if (lineRow.getAttribute("CredrOrDebtr") != null) {

                        if (lineRow.getAttribute("CredrOrDebtr").equals("C")) {

                            countCreditor = countCreditor + 1;
                        } else if (lineRow.getAttribute("CredrOrDebtr").equals("D")) {

                            countDebtor = countDebtor + 1;
                        }
                    }
                }
                rit.closeRowSetIterator();

                // System.out.println("countDebtor " + countDebtor + " countCreditor " + countCreditor);

                if (countCreditor > 0 && countDebtor > 0) {
                    //Either Creditor or Debtor account is allowed for Bank Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.589']}").toString(); //Either Creditor or Debtor account is allowed for Bank Payment Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                } else if (countCreditor > 1) {
                    //Only one Creditor account is allowed for Bank Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.590']}").toString(); //Only one Creditor account is allowed for Bank Payment Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                } else if (countDebtor > 1) {
                    //Only one Debtor  account is allowed for Bank Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.591']}").toString(); //Only one Debtor account is allowed for Bank Payment Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }

            } else if (vouType.equals(3)) {


                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Bank Receipt Voucher
                    String msg = resolvElDCMsg("#{bundle['MSG.592']}").toString(); //Bank or Cash account are not allowed for Bank Receipt Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }

                RowSetIterator rit = getAm().getTvouLinesLnk().createRowSetIterator(null);
                Integer countDebtor = 0;
                Integer countCreditor = 0;

                if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CoaTypeTrans") == null) {

                    if (coaBehaviour.equals("C")) {

                        countCreditor = countCreditor + 1;

                    } else if (coaBehaviour.equals("D")) {

                        countDebtor = countDebtor + 1;
                    }
                }
                //    System.out.println("countCreditor---->" + countCreditor);

                //    System.out.println("countDebtor------>" + countDebtor);

                if (rit.first() != null && rit.first().getAttribute("CredrOrDebtr") != null) {

                    if (rit.first().getAttribute("CredrOrDebtr").equals("C")) {

                        countCreditor = countCreditor + 1;
                    } else if (rit.first().getAttribute("CredrOrDebtr").equals("D")) {

                        countDebtor = countDebtor + 1;
                    }
                }

                while (rit.hasNext()) {

                    Row lineRow = rit.next();
                    if (lineRow.getAttribute("CredrOrDebtr") != null) {

                        if (lineRow.getAttribute("CredrOrDebtr").equals("C")) {

                            countCreditor = countCreditor + 1;
                        } else if (lineRow.getAttribute("CredrOrDebtr").equals("D")) {

                            countDebtor = countDebtor + 1;
                        }
                    }
                }
                rit.closeRowSetIterator();

                //      System.out.println("countDebtor " + countDebtor + " countCreditor " + countCreditor);

                if (countCreditor > 0 && countDebtor > 0) {
                    //Either Creditor or Debtor account is allowed for Bank Receipt Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.593']}").toString(); //Either Creditor or Debtor account is allowed for Bank Receipt Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                } else if (countCreditor > 1) {
                    //Only one Creditor account is allowed for Bank Receipt Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.594']}").toString(); //Only one Creditor account is allowed for Bank Receipt Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                } else if (countDebtor > 1) {
                    //Only one Debtor  account is allowed for Bank Receipt Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.595']}").toString(); //Only one Debtor account is allowed for Bank Receipt Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }

            } else if (vouType.equals(4)) {

                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Cash Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.596']}").toString(); //Bank or Cash account are not allowed for Cash Payment Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
                RowSetIterator rit = getAm().getTvouLinesLnk().createRowSetIterator(null);
                Integer countDebtor = 0;
                Integer countCreditor = 0;

                if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CoaTypeTrans") == null) {

                    if (coaBehaviour.equals("C")) {

                        countCreditor = countCreditor + 1;

                    } else if (coaBehaviour.equals("D")) {

                        countDebtor = countDebtor + 1;
                    }
                }
                //    System.out.println("countCreditor---->" + countCreditor);

                //    System.out.println("countDebtor------>" + countDebtor);

                if (rit.first() != null && rit.first().getAttribute("CredrOrDebtr") != null) {

                    if (rit.first().getAttribute("CredrOrDebtr").equals("C")) {

                        countCreditor = countCreditor + 1;
                    } else if (rit.first().getAttribute("CredrOrDebtr").equals("D")) {

                        countDebtor = countDebtor + 1;
                    }
                }

                while (rit.hasNext()) {

                    Row lineRow = rit.next();
                    if (lineRow.getAttribute("CredrOrDebtr") != null) {

                        if (lineRow.getAttribute("CredrOrDebtr").equals("C")) {

                            countCreditor = countCreditor + 1;
                        } else if (lineRow.getAttribute("CredrOrDebtr").equals("D")) {

                            countDebtor = countDebtor + 1;
                        }
                    }
                }
                rit.closeRowSetIterator();

                //      System.out.println("countDebtor " + countDebtor + " countCreditor " + countCreditor);

                if (countCreditor > 0 && countDebtor > 0) {
                    //Either Creditor or Debtor account is allowed for Cash Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1121']}").toString(); //Either Creditor or Debtor account is allowed for Cash Payment Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                } else if (countCreditor > 1) {
                    //Only one Creditor account is allowed for Cash Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1122']}").toString(); //Only one Creditor account is allowed for Cash Payment Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                } else if (countDebtor > 1) {
                    //Only one Debtor  account is allowed for Cash Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1123']}").toString(); //Only one Debtor account is allowed for Cash Payment Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }


            } else if (vouType.equals(5)) {

                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Cash Receipt Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.597']}").toString(); //Bank or Cash account are not allowed for Cash Receipt Voucher.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                }

                RowSetIterator rit = getAm().getTvouLinesLnk().createRowSetIterator(null);
                Integer countDebtor = 0;
                Integer countCreditor = 0;

                if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CoaTypeTrans") == null) {

                    if (coaBehaviour.equals("C")) {

                        countCreditor = countCreditor + 1;

                    } else if (coaBehaviour.equals("D")) {

                        countDebtor = countDebtor + 1;
                    }
                }
                //    System.out.println("countCreditor---->" + countCreditor);

                //    System.out.println("countDebtor------>" + countDebtor);

                if (rit.first() != null && rit.first().getAttribute("CredrOrDebtr") != null) {

                    if (rit.first().getAttribute("CredrOrDebtr").equals("C")) {

                        countCreditor = countCreditor + 1;
                    } else if (rit.first().getAttribute("CredrOrDebtr").equals("D")) {

                        countDebtor = countDebtor + 1;
                    }
                }

                while (rit.hasNext()) {

                    Row lineRow = rit.next();
                    if (lineRow.getAttribute("CredrOrDebtr") != null) {

                        if (lineRow.getAttribute("CredrOrDebtr").equals("C")) {

                            countCreditor = countCreditor + 1;
                        } else if (lineRow.getAttribute("CredrOrDebtr").equals("D")) {

                            countDebtor = countDebtor + 1;
                        }
                    }
                }
                rit.closeRowSetIterator();

                //      System.out.println("countDebtor " + countDebtor + " countCreditor " + countCreditor);

                if (countCreditor > 0 && countDebtor > 0) {
                    //Either Creditor or Debtor account is allowed for Cash Receipt Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1124']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                } else if (countCreditor > 1) {
                    //Only one Creditor account is allowed for Cash Receipt Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1125']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                } else if (countDebtor > 1) {
                    //Only one Debtor  account is allowed for Cash Receipt Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1126']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            } else if (vouType.equals(6)) {

                if (!coaType.equals(78) && !coaType.equals(79)) {
                    //Only Bank or Cash account are allowed for Contra Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.598']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                }

            } else if (vouType.equals(7)) {

                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Sales Invoice.
                    String msg = resolvElDCMsg("#{bundle['MSG.599']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                }
                if (!lineNo.equals(minLineNo)) {

                    /*
                    *
                    * if (coaType.equals(68)) {
                        //Debtor Account is not allowed for the line Item.
                        String msg1 = resolvElDCMsg("#{bundle['MSG.601']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                    } */
                    RowSetIterator rit = getAm().getTvouLinesLnk().createRowSetIterator(null);
                    Integer countDebtor = 0;
                    Integer countCreditor = 0;

                    if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CredrOrDebtr") == null) {

                        if (coaBehaviour.equals("C")) {

                            countCreditor = countCreditor + 1;

                        } else if (coaBehaviour.equals("D")) {

                            countDebtor = countDebtor + 1;
                        }
                    }
                    //    System.out.println("countCreditor---->" + countCreditor);

                    //    System.out.println("countDebtor------>" + countDebtor);

                    if (rit.first() != null && rit.first().getAttribute("CredrOrDebtr") != null) {

                        if (rit.first().getAttribute("CredrOrDebtr").equals("C")) {

                            countCreditor = countCreditor + 1;
                        } else if (rit.first().getAttribute("CredrOrDebtr").equals("D")) {

                            countDebtor = countDebtor + 1;
                        }
                    }

                    while (rit.hasNext()) {

                        Row lineRow = rit.next();
                        if (lineRow.getAttribute("CredrOrDebtr") != null) {

                            if (lineRow.getAttribute("CredrOrDebtr").equals("C")) {

                                countCreditor = countCreditor + 1;
                            } else if (lineRow.getAttribute("CredrOrDebtr").equals("D")) {

                                countDebtor = countDebtor + 1;
                            }
                        }
                    }
                    rit.closeRowSetIterator();

                    //      System.out.println("countDebtor " + countDebtor + " countCreditor " + countCreditor);

                    if (countDebtor > 1) {
                        //Only one Debtor  account is allowed for Sales Voucher.
                        String msg = resolvElDCMsg("#{bundle['MSG.697']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                }

            } else if (vouType.equals(8)) {

                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Purchase Bill.
                    String msg = resolvElDCMsg("#{bundle['MSG.602']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }

                if (!lineNo.equals(minLineNo)) {

                    RowSetIterator rit = getAm().getTvouLinesLnk().createRowSetIterator(null);
                    Integer countDebtor = 0;
                    Integer countCreditor = 0;
                    Object TvouLnTyp = getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouLnTyp");
                    if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CredrOrDebtr") == null &&
                        !TvouLnTyp.equals("DETD")) {

                        if (coaBehaviour.equals("C")) {

                            countCreditor = countCreditor + 1;

                        } else if (coaBehaviour.equals("D")) {

                            countDebtor = countDebtor + 1;
                        }
                    }
                    if (rit.first() != null && rit.first().getAttribute("CredrOrDebtr") != null &&
                        !TvouLnTyp.equals("DETD")) {

                        if (rit.first().getAttribute("CredrOrDebtr").equals("C")) {

                            countCreditor = countCreditor + 1;
                        } else if (rit.first().getAttribute("CredrOrDebtr").equals("D")) {

                            countDebtor = countDebtor + 1;
                        }

                    }

                    while (rit.hasNext()) {

                        Row lineRow = rit.next();
                        if (lineRow.getAttribute("CredrOrDebtr") != null && !TvouLnTyp.equals("DETD")) {

                            if (lineRow.getAttribute("CredrOrDebtr").equals("C")) {

                                countCreditor = countCreditor + 1;
                            } else if (lineRow.getAttribute("CredrOrDebtr").equals("D")) {

                                countDebtor = countDebtor + 1;
                            }

                        }
                    }
                    rit.closeRowSetIterator();

                    //      System.out.println("countDebtor " + countDebtor + " countCreditor " + countCreditor);

                    if (countCreditor > 1) {
                        //Only one Creditor account is allowed for Purchase Voucher.
                        String msg = resolvElDCMsg("#{bundle['MSG.696']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }

                }


            } else if (vouType.equals(9)) {

                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Debit Note Customer.
                    String msg = resolvElDCMsg("#{bundle['MSG.605']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                }

                if (lineNo.equals(minLineNo)) {

                    if (!coaBehaviour.equals("D")) {
                        //Only Debtor Account is allowed for the first line.
                        String msg1 = resolvElDCMsg("#{bundle['MSG.600']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));

                    }

                } else if (!lineNo.equals(minLineNo)) {

                    if (coaBehaviour.equals("D")) {
                        //Debtor Account is not allowed for the line.
                        String msg1 = resolvElDCMsg("#{bundle['MSG.601']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                    }
                }

            } else if (vouType.equals(10)) {

                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Debit Note Supplier.
                    String msg = resolvElDCMsg("#{bundle['MSG.606']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                }

                if (lineNo.equals(minLineNo)) {

                    if (!coaBehaviour.equals("C")) {
                        //Only Creditor Account is allowed for the first line.
                        String msg1 = resolvElDCMsg("#{bundle['MSG.603']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                    }

                } else if (!lineNo.equals(minLineNo)) {

                    if (coaBehaviour.equals("C")) {
                        //Creditor Account is not allowed for the line.
                        String msg1 = resolvElDCMsg("#{bundle['MSG.604']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                    }
                }
            } else if (vouType.equals(11)) {

                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Credit Note Customer.
                    String msg = resolvElDCMsg("#{bundle['MSG.607']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }

                if (lineNo.equals(minLineNo)) {

                    if (!coaBehaviour.equals("D")) {
                        //Only Debtor Account is allowed for first line item.
                        String msg1 = resolvElDCMsg("#{bundle['MSG.600']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                    }

                } else if (!lineNo.equals(minLineNo)) {

                    if (coaBehaviour.equals("D")) {
                        //Debtor Account is not allowed for the line.
                        String msg1 = resolvElDCMsg("#{bundle['MSG.601']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                    }
                }

            } else if (vouType.equals(12)) {
                //   System.out.println("validation voucher 12");
                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Credit Note To Supplier.
                    String msg = resolvElDCMsg("#{bundle['MSG.1128']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }

                if (lineNo.equals(minLineNo)) {

                    if (!coaBehaviour.equals("C")) {
                        //Only Creditor Account is allowed for the first line.
                        String msg1 = resolvElDCMsg("#{bundle['MSG.603']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                    }

                } else if (!lineNo.equals(minLineNo)) {

                    if (coaBehaviour.equals("C")) {
                        //Creditor Account is not allowed for the line.
                        String msg1 = resolvElDCMsg("#{bundle['MSG.604']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                    }
                }
            } else if (vouType.equals(13)) {
                //System.out.println("validation voucher 13");
                if (coaType.equals(78) || coaType.equals(79) || coaType.equals(5022) || coaBehaviour.equals("C") ||
                    coaBehaviour.equals("D")) {
                    //Bank, Cash, TAX/TDS, Debitor/Creditor accounts are not allowed for Provisional Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1129']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }

            } else if (vouType.equals(14)) {

                //Only Others account are allowed for voucher type 14

                //System.out.println("validation voucher 14");
                // removed on 06/12/2013 after review by Vidya Ma'am
                //validation reapplied on 28-01-2014
                if (!coaType.equals(0)) {
                    //Only Others account are allowed for Expense Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1130']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }

            } else if (vouType.equals(15)) {

                // only debtor/creditor allowed for voucher type 15.
                // System.out.println("validation voucher 15");
                if (!coaBehaviour.equals("D") && !coaBehaviour.equals("C")) {
                    //Only Debtor and Creditor account are allowed for Adjustment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1131']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            } else if (vouType.equals(16)) {

                //Only Others account are allowed for voucher type 14

                //System.out.println("validation voucher 14");
                // removed on 06/12/2013 after review by Vidya Ma'am
                //validation reapplied on 28-01-2014
                if (!coaType.equals(0) && !coaType.equals(5022)) {
                    String msg = "Only Others and Tax/TDS account are allowed for Imprest Voucher.";
                    //  String msg = resolvElDCMsg("#{bundle['MSG.1130']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }

            }

        } else {
            //Failed to Validate Chart Of Account. Contact ESS for Support
            String msg1 = resolvElDCMsg("#{bundle['MSG.1132']}").toString();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
        }
        // System.out.println("<-----------validation ends here------->");
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveAndForwardBinding);

    }

    public void DeleteLineItem(ActionEvent actionEvent) {


        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject vouHdr = am.getTvou1();
        Row hdRow = vouHdr.getCurrentRow();
        ViewObject vouViwln = am.getTvouLinesLnk();
        Integer coaId = (Integer) vouViwln.getCurrentRow().getAttribute("TvouCoaId");
        String vouId = vouHdr.getCurrentRow().getAttribute("TvouId").toString();
        FacesContext fctx = FacesContext.getCurrentInstance();
        ViewObject vouBill = am.getTvouBillDtl1();

        /**Validation done for voucher types on 19-06-2013 -*/

        if (hdRow.getAttribute("TvouTypeId").equals(2) || hdRow.getAttribute("TvouTypeId").equals(3) ||
            hdRow.getAttribute("TvouTypeId").equals(4) || hdRow.getAttribute("TvouTypeId").equals(5) ||
            hdRow.getAttribute("TvouTypeId").equals(10) || hdRow.getAttribute("TvouTypeId").equals(11) ||
            hdRow.getAttribute("TvouTypeId").equals(15)) {

            ViewObject vouadj = am.getTvouAdjAutoVO();
            ViewObject tvouAdvDtl = am.getTvouAdvDtlAutoVO();
            ViewObject vouChq = am.getTvouLineChq();

            Row chqRow[] = vouChq.getFilteredRows("TvouCoaId", coaId);
            Row rows[] = vouadj.getFilteredRows("TvouAdjCoaId", coaId);
            if (rows.length > 0 || (tvouAdvDtl.getEstimatedRowCount()) > 0) {
                StringBuilder msg =
                    new StringBuilder("<html><body><p><b>Adjustment is done for the line, Can not be deleted</b></p>");
                //  msg.append("<ul><li>First delete adjustment lines</li>");
                // msg.append("<li>then try to delete voucher line</li></ul>");
                msg.append("</body></html>");

                FacesMessage errMsg = new FacesMessage(msg.toString());
                errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                fctx.addMessage(null, errMsg);
            } else if (chqRow.length > 0) {
                StringBuilder msg =
                    new StringBuilder("<html><body><b>Instrument is associated with this party, can not be deleted</b>");
                msg.append("<ul><li>First delete instrument</li>");
                msg.append("<li>then try to delete voucher line</li></ul>");
                msg.append("</body></html>");
                FacesMessage errMsg = new FacesMessage(msg.toString());
                errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                fctx.addMessage(null, errMsg);
            } else {
                BindingContainer bindings = getBindings();

                OperationBinding delCostCntr = bindings.getOperationBinding("deleteCostCenterItem");
                delCostCntr.execute();
                OperationBinding createOpBAddress = bindings.getOperationBinding("Delete");
                createOpBAddress.execute();
                OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute");
                createOpBAddress1.execute();
                setHdrDisable(true);
            }
        }

        else if ((hdRow.getAttribute("TvouTypeId").equals(14) || hdRow.getAttribute("TvouTypeId").equals(16) ||
                  hdRow.getAttribute("TvouTypeId").equals(8)) && vouBill.getRowCount() > 0) {
            System.out.println("bill detail --");

            StringBuilder msg =
                new StringBuilder("<html><body><b>Bill Detail is present for this account , can not be deleted</b>");
            msg.append("<ul><li>First delete bill and its detail (Tax/TDS)</li>");
            msg.append("<li>then try to delete voucher line</li></ul>");
            msg.append("</body></html>");
            FacesMessage errMsg = new FacesMessage(msg.toString());
            errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            fctx.addMessage(null, errMsg);

        }

        else {
            System.out.println("Delete line --");
            /** also delete from rule table if no detail line is present */
            BindingContainer bindings = getBindings();
            OperationBinding delCostCntr = bindings.getOperationBinding("deleteCostCenterItem");
            delCostCntr.execute();
            OperationBinding createOpBAddress = bindings.getOperationBinding("Delete");
            createOpBAddress.execute();
            OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute");
            createOpBAddress1.execute();
            setHdrDisable(true);
        }

    }

    public void ManualTdsEntryVCE(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String check = vce.getNewValue().toString();

        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        /**Object of TvouLines.*/
        ViewObject vouViwln = am.getTvouLinesLnk();
        ViewObject vouViw = am.getTvouLinesLnk();

        //if manual entry is not allowed.
        if (check.equalsIgnoreCase("true")) {

            RowSetIterator rit = vouViwln.createRowSetIterator(null);
            while (rit.hasNext()) {
                Row curLn = rit.next();
                //System.out.println(curLn.getAttribute("TvouTxnTyp"));
                if (curLn.getAttribute("TvouTxnTypTds").equals("Y")) {
                    curLn.setAttribute("TvouTxnTypTds", "N");

                }
            }
            rit.closeRowSetIterator();

            //if manual tds is applied then tds rule selection is disabled.
            // tdsDtl.setDisabled(true);

            //enable assessable field if manual tds is checked.
            assessableValBind.setVisible(true);

        } else {
            //tdsDtl.setDisabled(false);

            //to disable assesable value field if manual TAX & TDS are unchecked.
            ViewObject tvouVO = am.getTvou1();
            Row hdrRow = tvouVO.getCurrentRow();
            if (hdrRow.getAttribute("TvouManTax").equals("N")) {
                assessableValBind.setVisible(false);
                assessableValBind.setValue(new Number(0));
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(tdsDtl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(assessableValBind);
    }

    /** Method to set default value for Tax checkBox*/


    public void setCheckBoxTax(String checkBoxTax) {
        this.checkBoxTax = checkBoxTax;
    }

    /**
     * @return
     */
    public String getCheckBoxTax() {
        return checkBoxTax;
    }

    public void setCheckBoxTds(String checkBoxTds) {
        this.checkBoxTds = checkBoxTds;
    }

    public String getCheckBoxTds() {
        return checkBoxTds;
    }

    public void setTxdtl(RichSelectBooleanCheckbox txdtl) {
        this.txdtl = txdtl;
    }

    public RichSelectBooleanCheckbox getTxdtl() {
        return txdtl;
    }

    public void setTdsDtl(RichSelectBooleanCheckbox tdsDtl) {
        this.tdsDtl = tdsDtl;
    }

    public RichSelectBooleanCheckbox getTdsDtl() {
        return tdsDtl;
    }

    public void setCoaNameDetail(RichInputText coaNameDetail) {
        this.coaNameDetail = coaNameDetail;
    }

    public RichInputText getCoaNameDetail() {
        return coaNameDetail;
    }

    public void setManualTaxBox(RichSelectBooleanCheckbox manualTaxBox) {
        this.manualTaxBox = manualTaxBox;
    }

    public RichSelectBooleanCheckbox getManualTaxBox() {
        return manualTaxBox;
    }

    public void setManualTdsBox(RichSelectBooleanCheckbox manualTdsBox) {
        this.manualTdsBox = manualTdsBox;
    }

    public RichSelectBooleanCheckbox getManualTdsBox() {
        return manualTdsBox;
    }

    public void setFwdUserId(Integer fwdUserId) {
        this.fwdUserId = fwdUserId;
    }

    public Integer getFwdUserId() {
        return fwdUserId;
    }

    public void setFwdComment(String fwdComment) {
        this.fwdComment = fwdComment;
    }

    public String getFwdComment() {
        return fwdComment;
    }


    public void setWfRegion(RichRegion wfRegion) {
        this.wfRegion = wfRegion;
    }

    public RichRegion getWfRegion() {
        return wfRegion;
    }

    public void setFwdUserLvl(Integer fwdUserLvl) {
        this.fwdUserLvl = fwdUserLvl;
    }

    public Integer getFwdUserLvl() {
        return fwdUserLvl;
    }


    public void setDocDisplayId(String docDisplayId) {
        this.docDisplayId = docDisplayId;
    }

    public String getDocDisplayId() {
        return docDisplayId;
    }

    public void setFwdWfId(Integer fwdWfId) {
        this.fwdWfId = fwdWfId;
    }

    public Integer getFwdWfId() {
        return fwdWfId;
    }

    public void setVoucherAmount(Number voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public Number getVoucherAmount() {
        if (getTotalBsCredit().equals(0)) {
            return getTotalBsDebit();
        } else
            return getTotalBsCredit();
    }

    public String checkOrgDocActivity(Integer P_SLOC_ID, String P_ORG_ID, Integer P_DOC_ID, Integer P_DOC_TYPE_ID,
                                      String P_ACTIVITY) {

        OperationBinding chkActivity = this.getBindings().getOperationBinding("fnChkOrgDocActivity");
        String flag = "";
        chkActivity.getParamsMap().put("P_SLOC_ID", P_SLOC_ID);
        chkActivity.getParamsMap().put("P_ORG_ID", P_ORG_ID);
        chkActivity.getParamsMap().put("P_DOC_ID", P_DOC_ID);
        chkActivity.getParamsMap().put("P_DOC_TYPE_ID", P_DOC_TYPE_ID);
        chkActivity.getParamsMap().put("P_ACTIVITY", P_ACTIVITY);
        // System.out.println("Value from check OrgActivity---->" + chkActivity.execute());
        if (chkActivity.execute() == null) {
            System.out.println("Chk Activity returned null so it is N now:::::::::::");
            flag = "N";
        } else {
            flag = chkActivity.execute().toString();
        }

        return flag;
    }

    public void setFwdActivity(String fwdActivity) {
        this.fwdActivity = fwdActivity;
    }

    public String getFwdActivity() {
        return fwdActivity;
    }

    public Boolean getLineEdit() {

        return (Boolean) resolveElExp("#{ bindings.TvouLnTyp1.inputValue!='D' || pageFlowScope.TempVoucherDtlForm.mode== 'V' || pageFlowScope.TempVoucherDtlForm.mode=='S' }");
    }

    public void setCostCenterPopup(RichPopup costCenterPopup) {
        this.costCenterPopup = costCenterPopup;
    }

    public RichPopup getCostCenterPopup() {
        return costCenterPopup;
    }

    public String resolvElDCBindVar(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public String setToTvouSaveCC(Integer P_TEMP_SLOC_ID, String P_TEMP_CLD_ID, String P_TEMP_HO_ORG_ID,
                                  String P_TEMP_ORG_ID, Integer P_TEMP_DOC_ID, String P_TEMP_ID) {
        System.out.println("before calling cost centre save ");
        String ret = null;
        //        ret = (String) callStoredFunction2(VARCHAR, "APP.FN_SAVE_COST_CENTER(?,?,?,?,?,?,?,?)", new Object[] {
        //                                           P_TEMP_CLD_ID, P_TEMP_SLOC_ID, P_TEMP_HO_ORG_ID, P_TEMP_ORG_ID,
        //                                           P_TEMP_DOC_ID, "FIN", "TVOU$COST$CENTER", P_TEMP_ID
        //        });
        ret = (String) callStoredFunction2(VARCHAR, "FIN.FN_SAVE_COST_CENTER_FOR_TVOU(?,?,?,?)", new Object[] {
                                           P_TEMP_CLD_ID, P_TEMP_SLOC_ID, P_TEMP_HO_ORG_ID, P_TEMP_ID
        });
        return ret;
    }


    public String setToTvouCancelCC(Integer P_TEMP_SLOC_ID, String P_TEMP_CLD_ID, String P_TEMP_HO_ORG_ID,
                                    String P_TEMP_ORG_ID, Integer P_TEMP_DOC_ID, String P_TEMP_ID, Integer P_TEMP_SL_NO,
                                    String Mode) {

        String ret = null;
        ret = (String) callStoredFunction2(VARCHAR, "FIN.PKG_FIN_TVOU.FN_CANCEL_COST_CENTER(?,?,?,?,?,?,?,?)", new Object[] {
                                           P_TEMP_SLOC_ID, P_TEMP_CLD_ID, P_TEMP_HO_ORG_ID, P_TEMP_ORG_ID,
                                           P_TEMP_DOC_ID, P_TEMP_ID, P_TEMP_SL_NO, Mode
        });

        return ret;
    }


    public String costCenterSaveAction() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObjectImpl v = am.getTvou1();
        //  Row curr = v.getCurrentRow();
        String tvouId = "";

        if (tvouIdBind.getValue() != null) {
            tvouId = tvouIdBind.getValue().toString();
        }

        System.out.println("Tvou id -->" + tvouId);
        Integer SLOC_ID = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String CLD_ID = (resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID1}"));
        String HO_ORG_ID = (resolvElDCBindVar("#{pageFlowScope.GLBL_HO_ORG_ID1}"));
        String ORG_ID = (resolvElDCBindVar("#{pageFlowScope.ParamOrgId}"));


        String val = setToTvouSaveCC(SLOC_ID, CLD_ID, HO_ORG_ID, ORG_ID, 55, tvouId);
        //System.out.println(val);
        return val;

    }

    public void costCenterDialogListner(DialogEvent de) {

        if (de.getOutcome().name().equals("ok")) {
            System.out.println("inside ok");
        }

    }

    public String costCenterLineAction() {
        //        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        //        ViewObjectImpl v = am.getTvouLinesLnk();
        //        Row curr = v.getCurrentRow();
        //        Integer P_SLOC_ID = (Integer) curr.getAttribute("TvouSlocId");
        //        String P_CLD_ID = (String) curr.getAttribute("TvouCldId");
        //        String P_ORG_ID = (String) curr.getAttribute("TvouOrgId");
        //        String P_HO_ORG_ID = (String) curr.getAttribute("TvouHoOrgId");
        //        Integer val = isCostCenterPresent(P_SLOC_ID, P_CLD_ID, P_ORG_ID, P_HO_ORG_ID);
        //        if (val > 1) {
        //            //Cost Center Message
        //            FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.131']}").toString());
        //            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
        //            FacesContext ctx = FacesContext.getCurrentInstance();
        //            ctx.addMessage(null, Message);
        //        } else if (val == 0) {
        //            FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.344']}").toString());
        //            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
        //            FacesContext ctx = FacesContext.getCurrentInstance();
        //            ctx.addMessage(null, Message);
        //        } else {
        //            cost_center_slno = (Integer) curr.getAttribute("TvouSlNo");
        //            Number amount_temp = (Number) curr.getAttribute("TvouAmtSp");
        //
        //            cost_center_amount = amount_temp.bigDecimalValue();
        //            cost_center_src = "L";
        //            showPopup(costCenterPopup, true);
        //        }
        //
        //
        //        return null;
        Object res = ADFBeanUtils.callBindingsMethod("chkCcApplicableOrNot", null, null);
        if (res != null && (Boolean) res) {
            // ADFBeanUtils.callBindingsMethod("checkLineOrg", null, null);
            return "costCentreLine";
        } else {
            ADFModelUtils.showFacesMessage(resolvEl("#{bundle['MSG.1960']}"), resolvEl("#{bundle['MSG.1960']}"),
                                           FacesMessage.SEVERITY_INFO, null); //Profile not created
            return null;
        }
    }

    private Integer isCostCenterPresent(Integer P_SLOC_ID, String P_CLD_ID, String P_ORG_ID, String P_HO_ORG_ID) {
        Integer retVal = (Integer) callStoredFunction2(Types.INTEGER, "FIN.FN_IS_COST_CENTER_PRESENT(?,?,?,?,?)", new Object[] {
                                                       P_CLD_ID, P_SLOC_ID, P_HO_ORG_ID, P_ORG_ID, 55
        });
        return retVal;
    }

    public String costCenterAction() {
        //        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        //        ViewObjectImpl v = am.getTvou1();
        //        Row curr = v.getCurrentRow();
        //        Integer P_SLOC_ID = (Integer) curr.getAttribute("TvouSlocId");
        //        String P_CLD_ID = (String) curr.getAttribute("TvouCldId");
        //        String P_ORG_ID = (String) curr.getAttribute("TvouOrgId");
        //        String P_HO_ORG_ID = (String) curr.getAttribute("TvouHoOrgId");
        //        Integer val = isCostCenterPresent(P_SLOC_ID, P_CLD_ID, P_ORG_ID, P_HO_ORG_ID);
        //        if (val > 1) {
        //            FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.131']}").toString());
        //            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
        //            FacesContext ctx = FacesContext.getCurrentInstance();
        //            ctx.addMessage(null, Message);
        //        } else if (val == 0) {
        //            FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.344']}").toString());
        //            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
        //            FacesContext ctx = FacesContext.getCurrentInstance();
        //            ctx.addMessage(null, Message);
        //        } else {
        //            cost_center_slno = 0;
        //            cost_center_amount = new BigDecimal(0);
        //            cost_center_src = "H";
        //            showPopup(costCenterPopup, true);
        //        }

        Object res = ADFBeanUtils.callBindingsMethod("chkCcApplicableOrNot", null, null);
        if (res != null && (Boolean) res) {
            return "costCentre";
        } else {
            ADFModelUtils.showFacesMessage(resolvEl("#{bundle['MSG.1960']}"), resolvEl("#{bundle['MSG.1960']}"),
                                           FacesMessage.SEVERITY_INFO, null); //Profile not created
            return null;
        }
    }

    public void setCost_center_src(String cost_center_src) {
        this.cost_center_src = cost_center_src;
    }

    public String getCost_center_src() {
        return cost_center_src;
    }

    public void setCost_center_slno(Integer cost_center_slno) {
        this.cost_center_slno = cost_center_slno;
    }

    public Integer getCost_center_slno() {
        return cost_center_slno;
    }

    public void setCost_center_amount(BigDecimal cost_center_amount) {
        this.cost_center_amount = cost_center_amount;
    }

    public BigDecimal getCost_center_amount() {
        return cost_center_amount;
    }

    public void voucherTypeValueChangeListener(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {

            chartOfAc.setValue(null);

            //to reset prior manual tax/tds selection
            ViewObject tvouVO = getAm().getTvou1();
            Row hdrRow = tvouVO.getCurrentRow();

            hdrRow.setAttribute("TvouManTds", "N");
            hdrRow.setAttribute("TvouManTax", "N");

            AdfFacesContext.getCurrentInstance().addPartialTarget(manualTdsBox);
            intimationIdBinding.setValue(null);
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(chartOfAc);
        chartOfAc.validate(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(chartOfAc);
    }

    public void AddotherCharges(ActionEvent actionEvent) {
        ViewObject vo = getAm().getTvouOc();
        /** Add a new row. */
        Row[] filteredRows = vo.getFilteredRows("TvouCoaId", null);
        if (filteredRows.length == 0) {
            Row[] rows = vo.getFilteredRows("TvouOcAmtSp", new Number(0));
            if (rows.length == 0) {
                OperationBinding createOpB1 = getBindings().getOperationBinding("CreateInsert4");
                createOpB1.execute();
                //New Code to get Serial No.- 09-03-2013

                Integer Srno = 0;
                Integer max = 0;

                Row row[] = vo.getAllRowsInRange();
                for (Row r : row) {
                    try {
                        Srno = Integer.parseInt(r.getAttribute("TvouOcSlNo").toString());
                    } catch (NullPointerException e) {
                        Srno = 0;

                    }

                    if (Srno > max) {
                        max = Srno;
                    }

                }

                max = max + 1;
                Row crow = vo.getCurrentRow();
                crow.setAttribute("TvouOcSlNo", max);
                crow.setAttribute("TvouDt", getAm().getTvou1().getCurrentRow().getAttribute("TvouDt"));
            } else {
                ADFUtil.showFacesMsg(resolvEl("#{bundle['MSG.1168']} !"), resolvEl("#{bundle['MSG.382']}"),
                                     FacesMessage.SEVERITY_ERROR,
                                     null); //Enter Amount Value..  //Amount must be greater than 0.

            }
        } else {
            ADFUtil.showFacesMsg(resolvEl("#{bundle['MSG.1961']} !"), resolvEl("#{bundle['MSG.126']}."), //Enter COA  //COA is Required.
                                 FacesMessage.SEVERITY_ERROR, null);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocPanelCollectionBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelocBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(submitocBinding);


    }

    public void importDialogLst(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            commit();
            BindingContainer bindings = getBindings();
            OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
            createOpBAddress.execute();
            setMode("E");
            // System.out.println(getTotalBsCredit());
            //System.out.println(getTotalBsDebit());
            AdfFacesContext.getCurrentInstance().addPartialTarget(touLinesTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(creditAmt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(debitAmt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveAndForwardBinding);
            setImportsave(false);


            // System.out.println(getTotalBsCredit());
            // System.out.println(getTotalBsDebit());
        }
    }

    public String importExcelAction() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject vouViw = am.getTvou1();

        /**Get current rows of both tables.*/
        Row vouHD = vouViw.getCurrentRow();

        /**Check whether coa is selected or not in Case Bank/Cash Reciept/Payment- 05-03-2013*/
        if (tvouTypBind.getValue() == null) {
            /**Voucher type is required*/
            FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.124']}").toString()); //Voucher Type is Required
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(resolvElDCMsg("#{bundle['MSG.492']}").toString()); //Please Select Voucher Type
            FacesContext.getCurrentInstance().addMessage(tvouTypBind.getClientId(), errMsg);
        }

        /**Check whether coa is selected or not in Case Bank/Cash Reciept/Payment- 05-03-2013*/
        else if ((vouHD.getAttribute("TvouTypeId").equals(2) || vouHD.getAttribute("TvouTypeId").equals(3) ||
                  vouHD.getAttribute("TvouTypeId").equals(4) || vouHD.getAttribute("TvouTypeId").equals(5) ||
                  vouHD.getAttribute("TvouTypeId").equals(6)) && chartOfAc.getValue() == null) {
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.343']}").toString()); //Chart of Account is required
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(resolvElDCMsg("#{bundle['MSG.493']}").toString()); //Please Select Chart of Account
            FacesContext.getCurrentInstance().addMessage(chartOfAc.getClientId(), errMsg);
        } else if ((vouHD.getAttribute("TvouTypeId").equals(14) || vouHD.getAttribute("TvouTypeId").equals(7) ||
                    vouHD.getAttribute("TvouTypeId").equals(8)) && dueDateBind.getValue() == null) {
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.494']}").toString()); //Due Date is required
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(resolvElDCMsg("#{bundle['MSG.495']}").toString()); //Please Enter Due Date
            FacesContext.getCurrentInstance().addMessage(dueDateBind.getClientId(), errMsg);
        } else {
            commit();
            showPopup(importPopup, true);
        }
        return null;
    }

    public void setImportPopup(RichPopup importPopup) {
        this.importPopup = importPopup;
    }

    public RichPopup getImportPopup() {
        return importPopup;
    }

    public void setImportsave(boolean importsave) {
        this.importsave = importsave;
    }

    public boolean isImportsave() {
        return importsave;
    }


    public void setForm(Boolean form) {
        this.form = form;
    }

    public Boolean getForm() {
        return form;
    }

    public void setTable(Boolean table) {
        this.table = table;
    }

    public Boolean getTable() {
        return table;
    }

    /**Function called on ok button press.
     * @param actionEvent
     */
    public void okButton(ActionEvent actionEvent) {

        /* commented on 12/06/2014 by priyank khare.
        * The code is suspected to mix up data on newly created row
        * BindingContainer bindings = getBindings();
        OperationBinding ob = bindings.getOperationBinding("Execute");
        ob.execute(); */

        this.modeCheque = true;
        //setTable(true);
        //setForm(false);
    }

    /** Delete cheque detail row.
     * @param actionEvent
     */
    public void deleteRow(ActionEvent actionEvent) {
        /** Delete a row. */
        OperationBinding createOpB = getBindings().getOperationBinding("Delete");
        createOpB.execute();
        /** Execute view. */
        OperationBinding createOp = getBindings().getOperationBinding("Execute");
        createOp.execute();
        this.modeCheque = true;
        /** Make table visible and hide form.*/
        //   setTable(true);
        // setForm(false);

    }

    /**Working on Cheque Detail- 18-03-2013---@Ashish Kumar*/
    public void AddRow(ActionEvent actionEvent) {
        this.modeCheque = false;
        getAm().getTvouLineChq().executeQuery();

        /** Add a new row. */

        OperationBinding createOpB1 = getBindings().getOperationBinding("CreateInsert");
        createOpB1.execute();

        //Code to get total amount from TempVoucherDtlForm bean- 06-03-2013

        /** Get amount from voucher table. */
        Number amt = new Number(0);

        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC("TempVoucherAMDataControl");
        ViewObject vo = am.getTvouLineChq();

        /**New Code to get Serial No.- 6-03-2013-@Ashish Kumar*/
        Integer Srno = 0;
        Integer max = 0;

        Row row[] = vo.getAllRowsInRange();
        for (Row r : row) {
            try {
                Srno = Integer.parseInt(r.getAttribute("TvouChqSlNo").toString());
            } catch (NullPointerException e) {
                Srno = 0;
                //e.printStackTrace();
            }

            if (Srno > max) {
                max = Srno;
            }

        }

        max = max + 1;
        TvouLineChqVORowImpl chqRow = (TvouLineChqVORowImpl) vo.getCurrentRow();
        try {

            chqRow.setAttribute("TvouChqSlNo", max);
            chqRow.setAttribute("TvouCoaBnkId", getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId"));
            chqRow.setAttribute("TvouOrgId", getAm().getTvou1().getCurrentRow().getAttribute("TvouOrgId"));

            if (getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(6)) {
                chqRow.setAttribute("TvouCurrIdSp",
                                    getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouCurrIdSp"));
                chqRow.setAttribute("TvouCoaBnkId",
                                    getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouCoaId"));
                if (chqRow.getAttribute("CoaTypeTrans").equals(79) &&
                    chqRow.getAttribute("CoaTypeTransHeader").equals(79)) {
                    chqRow.setAttribute("TvouInstrmntTyp", 736);

                }
            } else {
                chqRow.setAttribute("TvouCurrIdSp", getAm().getTvou1().getCurrentRow().getAttribute("TvouCurrIdSp"));
                chqRow.setAttribute("TvouCoaBnkId", getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId"));
                chqRow.setTvouCoaBnkId((Integer) getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId"));
            }
            chqRow.setAttribute("TvouAmtSp", amt);
            chqRow.setAttribute("TvouInstrmntDt", getAm().getTvou1().getCurrentRow().getAttribute("TvouDt"));
            String name = chqRow.getChqNm();
            chqRow.setAttribute("TvouChqNm", name);
            chqRow.setTvouCoaBnkId(Integer.parseInt(getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId").toString()));
        } catch (NullPointerException e) {
            System.out.println("Exceptio is-->" + e);
            // e.printStackTrace();
        }

        setTable(false);
        setForm(true);
        System.out.println("end of create row function");
    }

    /**Value change event on selection of instrument type.
     * @param valueChangeEvent
     */
    public void instrTypeSelect(ValueChangeEvent valueChangeEvent) {
        /** Call AM function from binding to get fill policy of instrument no. whether it is auto or manual. */
        OperationBinding createOpB1 = getBindings().getOperationBinding("chqNoFillPolicy");
        String fill = (String) createOpB1.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(chequeTable);

        if ((Integer) valueChangeEvent.getNewValue() == 111) {
            /** If selection is cheque */
            OperationBinding createOpB2 = getBindings().getOperationBinding("getLastChqNo");
            if (fill.equals("A")) {
                /** And fill policy is auto then execut binding function to get latest cheque no. */
                morA.setValue(true);
                setFillPolicy("A");
                String number = createOpB2.execute().toString();
                int dot = number.indexOf(".");

                String chqBNo = number.substring(0, dot);
                String ChqNo = number.substring(dot + 1);
                chequeNo.setValue(ChqNo);
                chqBukNo.setValue(chqBNo);

                setChqBkNo(Integer.parseInt(chqBNo));
                setChqSlNo(Integer.parseInt(ChqNo));
            }

            if (fill.equals("M")) {
                /** If fill policy is manual then make instrument no field visible and clear it. */
                setFillPolicy("M");
                morA.setValue(false);
                chequeNo.setValue("");
                chqBukNo.setValue("");
                chqBukNo.setVisible(false);
            }
            if (fill.equals("B")) {
                /** If fill policy is both then checkbox will use to set auto or manual. */
                setFillPolicy("B");
                morA.setValue(false);
                chequeNo.setValue("");
            }


        }
        if ((Integer) valueChangeEvent.getNewValue() == 112) {
            /** If instrument type is draft then it wl be filled manually. */
            setFillPolicy("M");
            morA.setValue(false);
            chequeNo.setValue("");
        }

    }


    public void instrumentNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        uIComponent.processUpdates(FacesContext.getCurrentInstance());
        // same validator used in chequeDetailPage hence addChequeBind cannot be checked
        // if (addChequeBind.getValue().equals(true)) {

        //  System.out.println("Inside Validator" + "getAm().getTvouLinesLnk().getCurrentRow().getAttribute(\"TvouInstruFlg\")"+getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouInstruFlg"));
        // getAm().getTvouLineChq().getCurrentRow() != null &&
        //  System.out.println("getAm().getTvouLinesLnk().getCurrentRow().getAttribute(\"TvouInstruFlg\") == \"Y\" ="+getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouInstruFlg").equals("Y"));
        if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouInstruFlg").equals("Y")) {
            //    System.out.println("getAm().getTvouLinesLnk().getCurrentRow().getAttribute(\"TvouInstruFlg\")"+getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouInstruFlg").equals("Y"));
            Integer instrTyp = Integer.parseInt(instrumentTypeBinding.getValue().toString());

            String instrNo = object.toString();
            String error = "Invalid Cheque Number";
            if (instrTyp == 111) {
                String expression = "^[0-9]*$";
                CharSequence inputStr = instrNo;
                Pattern pattern = Pattern.compile(expression);
                Matcher matcher = pattern.matcher(inputStr);

                if (matcher.matches()) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvElDCMsg("#{bundle['MSG.454']}").toString())); //Only Numeric Values allowed
                }

            } else {
                String expression = "^[a-zA-Z0-9]*$";
                CharSequence inputStr = instrNo;
                Pattern pattern = Pattern.compile(expression);
                Matcher matcher = pattern.matcher(inputStr);

                if (matcher.matches()) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvEl("#{bundle['MSG.2415']}"))); //"Alpha-Numeric Value allowed"
                }
            }
            if ("0".equalsIgnoreCase(instrNo)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvEl("#{bundle['MSG.2416']}"))); //Instrument No can not be 0
            }
            Integer CoaId = null;
            if (getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(6)) {
                CoaId = (Integer) getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouCoaId");
            } else {
                CoaId = (Integer) getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId");
            }
            /* System.out.println("param " + getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouCldId") +
                               getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouSlocId") +
                               getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouHoOrgId") +
                               getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouOrgId") +
                               CoaId + instrNo +
                               getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouInstrmntTyp")+
                               getAm().getTvou1().getCurrentRow().getAttribute("TvouId")+
                               getMode()); */
            Object fnValidate = "Y";
            /* callStoredFunction2(Types.VARCHAR, "FIN.fn_chk_instru_no_unique(?,?,?,?,?,?,?,?,?)", new Object[] { getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouCldId"),
                                                                                                                getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouSlocId"),
                                                                                                                getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouHoOrgId"),
                                                                                                                getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouOrgId"),
                                                                                                                CoaId,
                                                                                                                instrNo,
                                                                                                                getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouInstrmntTyp"),
                                                                                                                getAm().getTvou1().getCurrentRow().getAttribute("TvouId"),
                                                                                                                getMode()}); */

            if ("N".equals(fnValidate)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, error,
                                                              resolvElDCMsg("#{bundle['MSG.1239']}").toString())); //Duplicate Instrument No. Found
            } else {
                System.out.println("in else");
                /** Code to check the dupliate instrument number which are at uncommited state*/
                ViewObjectImpl chq = getAm().getTvouLineChq();
                if (chq != null) {
                    Row currentRow = chq.getCurrentRow();
                    RowSetIterator rsi = chq.createRowSetIterator(null);
                    Map m = new HashMap();
                    while (rsi.hasNext()) {
                        Row next = rsi.next();
                        //   System.out.println("next.getAttribute(\"TvouInstrmntNo\") = "+next.getAttribute("TvouInstrmntNo")+"object = "+object);
                        // System.out.println("next.getAttribute(\"TvouInstrmntNo\").equals(object) = "+next.getAttribute("TvouInstrmntNo").equals(object));
                        if (next.getAttribute("TvouInstrmntNo").equals(object) && currentRow != next) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                          resolvElDCMsg("#{bundle['MSG.1239']}").toString())); //Duplicate Instrument No. Found
                        }
                        //                        if (next.getAttribute("TvouInstrmntNo")!=null) {
                        //                            System.out.println("when not null instrmnt number");
                        //                            System.out.println("beof4rre next.getAttribute(\"TvouInstrmntNo\").toString() = "+next.getAttribute("TvouInstrmntNo").toString());
                        //
                        //                            if (!m.containsKey(next.getAttribute("TvouInstrmntNo").toString())) {
                        //                                System.out.println("next.getAttribute(\"TvouInstrmntNo\").toString() = "+next.getAttribute("TvouInstrmntNo").toString());
                        //                                m.put(next.getAttribute("TvouInstrmntNo").toString(), next.getAttribute("TvouInstrmntNo").toString());
                        //                            } else {
                        //                                System.out.println("beiofre thorwing erro");
                        //                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                        //                                                                              resolvElDCMsg("#{bundle['MSG.1239']}").toString()));
                        //
                        //                            }
                        //                        }
                    }
                    rsi.closeRowSetIterator();
                }

            }
        }
        //  }
    }

    public void TvouAmtSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number amount = (Number) object;
            String alertType = coaFySum(amount);
            Row curRow = getAm().getTvouLinesLnk().getCurrentRow();
            if (curRow.getAttribute("TvouCoaId") != null) {
                if (alertType.equalsIgnoreCase("S")) {
                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.509']}").toString()); //Invalid Amount-Alert
                    message.setDetail(resolvElDCMsg("#{bundle['MSG.510']}").toString()); //Voucher amount exceeds budgeted amount ,can't move
                    throw new ValidatorException(message);
                }
                if (amount.compareTo(0) == -1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.489']}").toString(),
                                                                  //Invalid Amount
                                                                  resolvElDCMsg("#{bundle['MSG.490']}").toString())); //Amount must be a positive value
                }
            } else {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.343']}").toString(),
                                                              //Chart of Account is required
                                                              resolvElDCMsg("#{bundle['MSG.511']}").toString())); //Please first enter Chart of Account

            }
        }

    }

    public void ChqAmtSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number amount = (Number) object;
            String alertType = coaFySum(amount);
            Row curRow = getAm().getTvouLinesLnk().getCurrentRow();
            if (curRow.getAttribute("TvouCoaId") != null) {
                if (amount.compareTo(0) == -1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.489']}").toString(),
                                                                  //Invalid Amount
                                                                  resolvElDCMsg("#{bundle['MSG.490']}").toString())); //Amount must be a positive value
                }
            } else {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.343']}").toString(),
                                                              //Chart of Account is required
                                                              resolvElDCMsg("#{bundle['MSG.511']}").toString())); //Please first enter Chart of Account

            }
        }
    }

    /** function to go back to temporary voucher page.
     * @return "back" to go back through task flow.
     */
    public String back() {

        /**Changed For TvouLines Cheque Concept @Ashish Kumar - 09-04-2013*/

        Integer CoaChqCount = getAm().getTvouLineChq().getRowCount();
        if (CoaChqCount.equals(0)) {

            getAm().getTvouLinesLnk().getCurrentRow().setAttribute("TvouInstruFlg", "N");

        }
        return "back";
    }

    public void setInstrmntTypBind(RichSelectOneChoice instrmntTypBind) {
        this.instrmntTypBind = instrmntTypBind;
    }

    public RichSelectOneChoice getInstrmntTypBind() {
        return instrmntTypBind;
    }

    /**
     * @param morA
     */
    public void setMorA(RichSelectBooleanCheckbox morA) {
        this.morA = morA;
    }

    /**
     * @return
     */
    public RichSelectBooleanCheckbox getMorA() {
        return morA;
    }

    /**
     * @param chequeNo
     */
    public void setChequeNo(RichInputText chequeNo) {
        this.chequeNo = chequeNo;
    }

    /**
     * @return
     */
    public RichInputText getChequeNo() {
        return chequeNo;
    }

    /**
     * @param chequeTable
     */
    public void setChequeTable(RichTable chequeTable) {
        this.chequeTable = chequeTable;
    }

    /**
     * @return
     */
    public RichTable getChequeTable() {
        return chequeTable;
    }

    /**
     * @param fillPolicy
     */
    public void setFillPolicy(String fillPolicy) {
        this.fillPolicy = fillPolicy;
    }

    /**
     * @return
     */
    public String getFillPolicy() {
        return fillPolicy;
    }

    /**
     * @param Docid
     */
    public void setDocid(Integer Docid) {
        this.Docid = Docid;
    }

    /**
     * @return
     */
    public Integer getDocid() {
        return Docid;
    }


    /**
     * @param chqBukNo
     */
    public void setChqBukNo(RichInputText chqBukNo) {
        this.chqBukNo = chqBukNo;
    }

    /**
     * @return
     */
    public RichInputText getChqBukNo() {
        return chqBukNo;
    }

    /**
     * @param chqSlNo
     */
    public void setChqSlNo(Integer chqSlNo) {
        this.chqSlNo = chqSlNo;
    }

    /**
     * @return
     */
    public Integer getChqSlNo() {
        return chqSlNo;
    }

    /**
     * @param chqBkNo
     */
    public void setChqBkNo(Integer chqBkNo) {
        this.chqBkNo = chqBkNo;
    }

    /**
     * @return
     */
    public Integer getChqBkNo() {
        return chqBkNo;
    }

    /**
     * @param amount
     */
    public void setAmount(RichInputText amount) {
        this.amount = amount;
    }

    /**
     * @return
     */
    public RichInputText getAmount() {
        return amount;
    }

    /**
     * @param totalAmount
     */
    public void setTotalAmount(RichInputText totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return
     */
    public RichInputText getTotalAmount() {
        return totalAmount;
    }

    /**For Multi Currency - change Specific currency of Tvou header as per selected Bank or Cash Account @Ashish Kumar 01-03-2013
     * Modified on 06-01-2014 by Priyank Khare
     * */
    public void cashBankCoaValuechangeListener(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            ViewObject tvou = getAm().getTvou1();
            Row curRow = tvou.getCurrentRow();
            Integer coaId = Integer.parseInt(vce.getNewValue().toString());
            String cldId = curRow.getAttribute("TvouCldId").toString();
            Integer slocId = (Integer) curRow.getAttribute("TvouSlocId");
            Integer instId = (Integer) curRow.getAttribute("TvouApplInstId");
            String hoOrgId = curRow.getAttribute("TvouHoOrgId").toString();
            String OrgId = curRow.getAttribute("TvouOrgId").toString();

            BigDecimal currency = (BigDecimal) callStoredFunction2(Types.NUMERIC, "FIN.FN_COA_CURR(?,?,?,?)", new Object[] {
                                                                   cldId, slocId, hoOrgId, coaId
            });
            Integer curr = 73;

            try {
                curr = Integer.parseInt(currency.toString());

                if (curr != null) {

                    LovLatestCurrImpl lovLatestCurr = getAm().getLovLatestCurr1();
                    lovLatestCurr.setNamedWhereClauseParam("BindVouDt", curRow.getAttribute("TvouDt"));
                    lovLatestCurr.setNamedWhereClauseParam("BindOrgId", curRow.getAttribute("TvouOrgId"));
                    lovLatestCurr.setNamedWhereClauseParam("BindCurrId", curRow.getAttribute("TvouCurrIdBs"));
                    lovLatestCurr.executeQuery();
                    Row[] xx = lovLatestCurr.getFilteredRows("CcCurrIdTxn", curr);


                    if (xx.length > 0) {

                        Number rate = (Number) xx[0].getAttribute("CcBuy");
                        curRow.setAttribute("TvouCurrIdSp", curr);
                        curRow.setAttribute("TvouCc", rate);
                    }
                }
            } catch (Exception e) {

                System.out.println("Currency is---->" + curr);
                curRow.setAttribute("TvouCurrIdSp", 73);
                curRow.setAttribute("TvouCc", 1);
                System.out.println(e);
            }
        }
    }

    /**Procedure call to insert multiOrg line in TvouLines-- 01-04-2013 @Ashish Kumar*/
    public void interBranchFunc() {
        try {
            ViewObject tvou = getAm().getTvou1();
            Row curRow = tvou.getCurrentRow();
            String cldId = curRow.getAttribute("TvouCldId").toString();
            Integer slocId = (Integer) curRow.getAttribute("TvouSlocId");
            Integer instId = (Integer) curRow.getAttribute("TvouApplInstId");
            String orgId = curRow.getAttribute("TvouOrgId").toString();
            String vouId = curRow.getAttribute("TvouId").toString();
            String hoOrgId = curRow.getAttribute("TvouHoOrgId").toString();
            Integer docId = 55;
            //     getAm()
            /*   System.out.println("parameters interBranch--->cldi-->" + cldId + "sloc--->" + slocId + "inst-->" + instId +
                               "org-->" + orgId + "vouID--->" + vouId + "hoOrgId " + hoOrgId); */

            callStoredProcedure("FIN.PROC_TVOU_INTERBRANCH(?,?,?,?,?,?,?)", new Object[] {
                                cldId, slocId, hoOrgId, orgId, instId, vouId, docId
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void newSaveAction(ActionEvent actionEvent) {
        saveAction();
        costCenterAction();
    }


    public void setProvisionalPopUp(RichPopup provisionalPopUp) {
        this.provisionalPopUp = provisionalPopUp;
    }

    public RichPopup getProvisionalPopUp() {
        return provisionalPopUp;
    }


    public String dialogyesbutton() {
        provisionalPopUp.hide();
        return saveAsGL();
    }

    public void addChequeNew() {

        this.modeCheque = false;

        getAm().getTvouLineChq().executeQuery();

        //First Cheque added get default amount from getChqAmt();
        //System.out.println("Amount is in add Cheque---->" + amt);
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC("TempVoucherAMDataControl");
        ViewObject vo = am.getTvouLineChq();

        // /**New Code to get Serial No.- 6-03-2013-@Ashish Kumar*/
        Integer Srno = 0;
        Integer max = 0;
        vo.setRangeSize(-1);
        Row row[] = vo.getAllRowsInRange();
        for (Row r : row) {
            try {
                Srno = Integer.parseInt(r.getAttribute("TvouChqSlNo").toString());
            } catch (NullPointerException e) {
                Srno = 0;
            }
            if (Srno > max) {
                max = Srno;
            }
        }
        max = max + 1;
        //System.out.println("Max Sl count-->" + max);
        /** Add a new row. */
        OperationBinding createOpB1 = getBindings().getOperationBinding("CreateInsert1");
        createOpB1.execute();
        //        System.out.println("after create Insert on chq");
        Number amt = new Number(0);
        if (createOpB1.getErrors().isEmpty()) {

            TvouLineChqVORowImpl chqRow = (TvouLineChqVORowImpl) vo.getCurrentRow();
            //          System.out.println("iin if when no erro occured");
            if (chqRow != null) { //TvouCoaId
                //            System.out.println("in if when chqrow!=null");
                chqRow.setAttribute("TvouChqSlNo", max);
                //          System.out.println("coa for bank " + getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId"));
                chqRow.setAttribute("TvouPrjId", getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouPrjId"));

                chqRow.setAttribute("TvouInstrmntDt", getAm().getTvou1().getCurrentRow().getAttribute("TvouDt"));
                String name = chqRow.getChqNm();
                chqRow.setAttribute("TvouOrgId", getAm().getTvou1().getCurrentRow().getAttribute("TvouOrgId"));
                //    /** CoaBankId will be same as CoaId in case of contra and for rest of the Vouchers CoaBankId will be same as Header CoaID*/
                if (getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(6)) {
                    amt = (Number) getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouAmtSp");
                    chqRow.setAttribute("TvouCurrIdSp",
                                        getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouCurrIdSp"));
                    chqRow.setAttribute("TvouCoaBnkId",
                                        getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouCoaId"));
                    if (chqRow.getAttribute("CoaTypeTrans").equals(79) &&
                        chqRow.getAttribute("CoaTypeTransHeader").equals(79)) {
                        chqRow.setAttribute("TvouInstrmntTyp", 736);

                    }
                } else {
                    amt = getAmtForChq();
                    //            System.out.println("amt = "+amt);
                    //          System.out.println("getAm().getTvou1().getCurrentRow().getAttribute(\"TvouCoaId\") = "+getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId"));
                    chqRow.setAttribute("TvouCoaBnkId", getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId"));
                    chqRow.setTvouCoaBnkId((Integer) getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId"));
                    //        System.out.println("bank id= "+chqRow.getAttribute("TvouCoaBnkId"));
                    /* if(chqRow.getAttribute("TvouCoaBnkId")==null){
                         chqRow.setTvouCoaBnkId((Integer)getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId"));
                    }  */
                    chqRow.setAttribute("TvouCurrIdSp",
                                        getAm().getTvou1().getCurrentRow().getAttribute("TvouCurrIdSp"));

                }


                chqRow.setTvouAmtSp(amt);
                chqRow.setTvouChqNm(name);

            } else {

                FacesMessage msg =
                    new FacesMessage(resolvEl("#{bundle['MSG.1962']}")); //Current Row not found in Object Set.
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            StringBuilder msg = new StringBuilder();
            msg.append("<html><body><p><b>Failed to add instrument</b></p>");
            msg.append("<p><b>Data Processing Failed-</b></p>");
            msg.append("<ul><li>Try Again</li><li>Or close tab</li><li>Something went wrong-Contact ESS!</li></ul>");
            msg.append("</body></html>");
            FacesMessage message = new FacesMessage(msg.toString());
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

    }

    /***09-04-2013 -- Cheque Detail Changed @Ashish Kumar*/
    public void addChequeVCE(ValueChangeEvent vce) {

        if (vce.getNewValue().toString() == "true") {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            setChqRequired(true);
            addChequeNew();
        } else if (vce.getNewValue().toString() == "false") {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            setChqRequired(false);
            showPopup(chequeDeletePopUp, true);
        }
    }


    /**
     * Programmatic evaluation of EL.
     *
     * @param el EL to evaluate
     * @return Result of the evaluation
     */


    /***Selection listener overided for Cheque Detail - 09-03-2013*/
    public void tvouLineSelectionListener(SelectionEvent selectionEvent) {

        ADFUtil.invokeEL("#{bindings.TvouLinesLnk1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                         selectionEvent });

        // get the selected row , by this you can get any attribute of that row

        Row selectedRow =
            (Row) ADFUtil.evaluateEL("#{bindings.TvouLinesLnkIterator.currentRow}"); // get the current selected row

    }

    public void setTvouCcBind(RichInputText tvouCcBind) {
        this.tvouCcBind = tvouCcBind;
    }

    public RichInputText getTvouCcBind() {
        return tvouCcBind;
    }

    /**Re-Apply Tax-TDS 12-04-2013 @Ashish Kumar*/
    public void reapplyTaxButton(ActionEvent actionEvent) {
        TempVoucherAMImpl am = this.getAm();
        Row impl1 = am.getTvouTaxRuleHd().getCurrentRow();
        String rulei = impl1.getAttribute("TvouTaxRuleId").toString();
        if (rulei != null) {
            am.procTaxForHdr(rulei);
        }
        ViewObjectImpl impl = am.getTvouTaxRuleLineHd();
        impl.executeQuery();
    }

    public void reapplyTdsButton(ActionEvent actionEvent) {
        TempVoucherAMImpl am = this.getAm();
        Row impl1 = am.getTvouTdsRuleHd().getCurrentRow();
        String rulei = impl1.getAttribute("TvouTdsRuleId").toString();
        if (rulei != null) {
            am.procTdsForHdr(rulei);
        }
        ViewObjectImpl impl = am.getTvouTdsRuleLineHd();
        impl.executeQuery();
    }

    public void setChequeDeletePopUp(RichPopup chequeDeletePopUp) {
        this.chequeDeletePopUp = chequeDeletePopUp;
    }

    public RichPopup getChequeDeletePopUp() {
        return chequeDeletePopUp;
    }

    public void chequeDeletePopUpDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            ViewObject tvouLineChq = getAm().getTvouLineChq();
            ViewObject tvouLine = getAm().getTvouLinesLnk();
            Row row = tvouLine.getCurrentRow();
            if (row != null) {
                Row curRow[] = tvouLineChq.getFilteredRows("TvouCoaId", row.getAttribute("TvouCoaId"));
                //   System.out.println("total rows in cheque for this id-->" + curRow.length);
                for (Row r : curRow) {
                    r.remove();
                }
                getAm().getTvouLineChq().executeQuery();
                TvouLinesVORowImpl tvouRow = (TvouLinesVORowImpl) tvouLine.getCurrentRow();
                tvouRow.setTvouInstruFlg("N");
            }
        } else {

        }
    }

    public void setGlobalMessagePopUp(RichPopup globalMessagePopUp) {
        this.globalMessagePopUp = globalMessagePopUp;
    }

    public RichPopup getGlobalMessagePopUp() {
        return globalMessagePopUp;
    }

    public void setGlobalMsg(String globalMsg) {
        this.globalMsg = globalMsg;
    }

    public String getGlobalMsg() {
        return globalMsg;
    }

    /**af:poll component listener for auto closing of Global Messag Popup*/
    public void handleClose() {
        globalMessagePopUp.hide();
    }

    public void pollListener(PollEvent pollEvent) {
        handleClose();
    }

    public void SaveButtonCall() {

        // System.out.println("Flsg from Save Action-->" + saveFlg);
        if (!instrumentAmtValid()) {

            //popup called to send warning message for instrument amount mismatch with line amount.
            //continuation for save action thus depends on output of ok/cancel of the popup

            //System.out.println("show :instrumentAmtValidatePopUp");
            showPopup(instrumentAmtValidatePopUp, true);

            //if no attached bank instruments or their amount mismatch found call save action.

        } else if (saveAction() == true) {
            costCenterSaveAction();
        }
    }

    public void saveButton(ActionEvent actionEvent) {
        System.out.println("<--- In  Save Button--->");
        SaveButtonCall();
    }

    /**
     * Method called on save and forward action.
     * Has logic blocks which validates the voucher through multiple
     * valitions in the order in which they have been incorporated.
     * **/
    public String instruCheck() {
        ViewObjectImpl chq = getAm().getTvouLineChq();
        System.out.println("chqv= ----------" + chq);
        String flag = "Y";
        RowSetIterator rsi = chq.createRowSetIterator(null);
        System.out.println("rsi------------- = " + rsi);
        while (rsi.hasNext() && flag.equalsIgnoreCase("Y")) {
            Row row = rsi.next();
            String instruNumberUnique = instruNumberUnique((String) row.getAttribute("TvouInstrmntNo"), row, "s&f");
            if (instruNumberUnique.equalsIgnoreCase("N")) {
                //rsi.closeRowSetIterator();
                flag = "N";
                showPopup(duplicateWarningBinding, true);
            }
        }
        rsi.closeRowSetIterator();
        return flag;
    }

    public String saveAndForward() {
        // boolean saveFlg = saveAction();
        String check = instruCheck();
        if (check.equalsIgnoreCase("Y")) {
            if (saveAction() == true) {

                costCenterSaveAction();
                if (paymentLimit().equalsIgnoreCase("N")) {
                    String msg = resolvEl("#{bundle['MSG.1958']}"); //Insufficient amount to make payment

                    FacesMessage errMsg = new FacesMessage(msg);
                    errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    errMsg.setDetail("");
                    FacesContext.getCurrentInstance().addMessage(null, errMsg);
                } else if (getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(13)) {
                    showPopup(provisionalPopUp, true);
                    return null;

                } else {

                    return saveAsGL();
                }
            }
        }

        System.out.println("Flag from Save Action--> false");
        return null;

    }


    public void setDoc_no(Integer doc_no) {
        this.doc_no = doc_no;
    }

    public Integer getDoc_no() {
        return doc_no;
    }

    public void setReturnFromWf(String returnFromWf) {
        this.returnFromWf = returnFromWf;
    }

    public String getReturnFromWf() {
        return returnFromWf;
    }

    public String actionForward() {
        //String returnP = resolvEl("#{pageFlowScope.Ret_wf}");

        return null;

    }
    //public Integer valid=0;
    public Integer duplicateBillNumberCheckInVoucher() {
        BindingContainer bindings = getBindings();
        DCIteratorBinding dupli = (DCIteratorBinding) bindings.get("TvouBillDtl1Iterator");
        ViewObject vo1 = dupli.getViewObject();
        RowSetIterator iterator = vo1.createRowSetIterator(null);
        Map m = new HashMap();
        int valid = 0;
        while (iterator.hasNext() && valid == 0) {
            System.out.println("valid= " + valid);
            Row next = iterator.next();
            Object TvouBillNo = next.getAttribute("TvouBillNo");
            System.out.println("billno = " + TvouBillNo);
            if (TvouBillNo != null) {
                System.out.println("m.containsKey(TvouBillNo) = " + m.containsKey(TvouBillNo));
                System.out.println("m.size = " + m.size());
                if (!m.containsKey(TvouBillNo)) {
                    m.put(TvouBillNo, TvouBillNo);
                } else {
                    System.out.println("error");
                    valid = 1;
                }
            }
        }
        iterator.closeRowSetIterator();
        return valid;
    }

    public void addBillButton(ActionEvent actionEvent) {
        //   int valid=0;
        /** new code to generate bill line at the end in the table 02-09-2013 @PRIYANK KHARE**/

        //dciter.clearForRecreate();
        //access the underlying RowSetIterator
        int valid = duplicateBillNumberCheckInVoucher();
        if (valid == 0) {
            BindingContainer bindingContainer = getBindings();
            OperationBinding binding = bindingContainer.getOperationBinding("CreateInsert");
            binding.execute();
            /**New Code to get Serial No.- 6-03-2013-@Ashish Kumar*/
            TvouVOImpl impl = getAm().getTvou1();
            Row HdRow = impl.getCurrentRow();
            ViewObject tvouln = getAm().getTvouLinesLnk();
            Row currentRow = tvouln.getCurrentRow();
            TvouLinesVORowImpl curRow = (TvouLinesVORowImpl) tvouln.getCurrentRow();
            System.out.println("Current row VO-->" + currentRow + "from Impl-->" + curRow);

            Integer coa_id = 0;
            if (HdRow.getAttribute("TvouTypeId").equals(16) && HdRow.getAttribute("TvouReqId") != null) {
                coa_id = Integer.parseInt(HdRow.getAttribute("TvouReqCoaId").toString());
            } else if (currentRow.getAttribute("CoaIdBillTrans") != null) {
                coa_id = Integer.parseInt(currentRow.getAttribute("CoaIdBillTrans").toString());
            } else {
                coa_id = curRow.getCoaIdBillTrans();
            }
            System.out.println("coaid in bill page --=" + coa_id);
            ViewObjectImpl billDtlVO1 = getAm().getTvouBillDtlVO1();
            ViewObject vo = getAm().getTvouBillDtl1();
            Row newRow = vo.getCurrentRow();
            Integer Srno = 0;
            Integer max = 0;
            Row row[] = billDtlVO1.getFilteredRows("TvouId", HdRow.getAttribute("TvouId"));
            for (Row r : row) {
                try {
                    Srno = Integer.parseInt(r.getAttribute("TvouBillSlNo").toString());
                } catch (NullPointerException e) {
                    Srno = 0;

                }
                if (Srno > max) {
                    max = Srno;
                }
            }

            max = max + 1;
            String billSeries = "";
            Object date = curRow.getTvouDt();

            if (curRow.getTvouBillNo() != null)
                billSeries = curRow.getTvouBillNo();

            if (curRow.getTvouBillDate() != null) {
                date = curRow.getTvouBillDate();
            }
            System.out.println("billSeries = " + billSeries);
            String bilNo = billSeries + max;
            // Date curDate = (Date)Date.getCurrentDate();
            newRow.setAttribute("TvouBillSlNo", max);
            // newRow.setAttribute("TvouBillNo", 0);
            newRow.setAttribute("TvouBillNo", bilNo);
            newRow.setAttribute("TvouBillDt", date);
            newRow.setAttribute("TvouDueDt", curRow.getTvouDueDt());
            newRow.setAttribute("TvouCoaId", coa_id);
            newRow.setAttribute("TvouAmtSp", 0);
            // System.out.println("curRow.getTvouDt().dateValue() = " + curRow.getTvouDt().dateValue());
            newRow.setAttribute("TvouDt", curRow.getTvouDt().dateValue());
        } else {
            ADFUtil.showFacesMsg(resolvEl("#{bundle['MSG.1963']}"), resolvEl("#{bundle['MSG.1964']}"),
                                 FacesMessage.SEVERITY_ERROR,
                                 null); //Invalid Bill Number  //Duplicate Invoice Number Found within a Same Voucher

        }
    }

    public String duplicateCheckOnBack() {
        System.out.println("in duplicateCheckOnBack");
        ViewObject lines = ADFUtil.getAM().findViewObject("TvouBillDtl1");
        Row currentRow = lines.getCurrentRow();
        if (currentRow != null) {
            Object TvouCoaId = currentRow.getAttribute("TvouCoaId");
            System.out.println("TvouCoaId = " + TvouCoaId);
            if (TvouCoaId != null) {
                OperationBinding binding = ADFUtil.getBindings().getOperationBinding("checkExpenseBillNumberDuplicate");
                binding.getParamsMap().put("CoaId", TvouCoaId);
                binding.execute();
                Object res = binding.getResult();
                System.out.println("res = " + res);
                if (res != null) {
                    if (res.toString().length() > 0) {
                        return res.toString();
                    }
                }
            }
        }
        return null;
    }

    public String backButtonBill() {
        if (!(mode.equalsIgnoreCase("S") || mode.equalsIgnoreCase("V"))) {
            BindingContainer bindings = getBindings();
            DCIteratorBinding dupli = (DCIteratorBinding) bindings.get("TvouBillDtl1Iterator");
            ViewObject vo1 = dupli.getViewObject();
            RowSetIterator iterator = vo1.createRowSetIterator(null);
            Map m = new HashMap();
            int valid = 0;
            while (iterator.hasNext() && valid == 0) {
                System.out.println("valid= " + valid);
                Row next = iterator.next();
                Object TvouBillNo = next.getAttribute("TvouBillNo");
                System.out.println("billno = " + TvouBillNo);
                if (TvouBillNo != null) {
                    System.out.println("m.containsKey(TvouBillNo) = " + m.containsKey(TvouBillNo));
                    System.out.println("m.size = " + m.size());
                    if (!m.containsKey(TvouBillNo)) {
                        m.put(TvouBillNo, TvouBillNo);
                    } else {
                        System.out.println("error");
                        valid = 1;
                    }
                }
            }
            iterator.closeRowSetIterator();
            if (valid == 0) {
                System.out.println("<<--In Back button bill method-->>");
                // System.out.println("BilAMt frombinding-->"+billAmtSpBind.getValue()+"and base amt-->"+billAmtBsBind.getValue());
                //  System.out.println("Total Row in Bill Detail-->" + getAm().getTvouBillDtl1().getRowCount());
                if (getAm().getTvouBillDtl1().getRowCount() > 0) {

                    if (getTdsYNBill() == true && checkTdsBill() == true) {
                        FacesMessage msg =
                            new FacesMessage(resolvElDCMsg("#{bundle['MSG.512']}").toString()); //Taxable Amount changed, Re-Apply TDS
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        return null;
                    } /* else if (getTaxYNBill() == true && checkTaxBill() == true) {
                    FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.513']}").toString());
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }  */ else {

                        if (duplicateCheckOnBack() != null) {
                            String msg1 = duplicateCheckOnBack();
                            FacesMessage msg =
                                new FacesMessage(resolvEl("#{bundle['MSG.1965']}  = ") +
                                                 msg1); //Duplicate Invoice Number Found
                            msg.setSeverity(FacesMessage.SEVERITY_WARN);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            // return null;
                        }
                        Row curTvou = getAm().getTvou1().getCurrentRow();

                        if (!curTvou.getAttribute("TvouTypeId").equals(16)) {
                            deleteTdsBill();
                        }
                        // deleteTaxBill();

                        String vouId = curTvou.getAttribute("TvouId").toString();
                        Row curBill = getAm().getTvouBillDtl1().getCurrentRow();

                        Integer Tvouslno = Integer.parseInt(curBill.getAttribute("TvouSlNo").toString());
                        // System.out.println("Tvou slno is-->" + Tvouslno);

                        /** Create object for tvouline view*/

                        ViewObjectImpl tvouln = getAm().getTvouLinesLnk();
                        RowQualifier rowQualifier = new RowQualifier(tvouln);
                        rowQualifier.setWhereClause("TvouId='" + vouId + "' AND TvouSlNo=" + Tvouslno);
                        Row row[] = tvouln.getFilteredRows(rowQualifier);

                        /*  System.out.println("Row in line for this vouid--->" + row.length);
                System.out.println("Value From Replica Checkbox-->" + autoAmtTransBind.getValue());
                System.out.println("Amt sp bill---" + row[0].getAttribute("BillAmtTotSp") + "base amount--->" +
                                   row[0].getAttribute("BillAmtTotBs")); */

                        if (autoAmtTransBind.getValue().equals(true)) {
                            System.out.println("in if when true");
                            if (row.length > 0) {
                                System.out.println("in if when row>0");
                                if (row[0].getAttribute("TransIsCostCenterAlw") != null) {
                                    System.out.println("before check of updateCostCenterAmt ");
                                    if (row[0].getAttribute("TransIsCostCenterAlw").toString().equalsIgnoreCase("Y")) {
                                        System.out.println("beofre updateCostCenterAmt call");
                                        if (row[0].getAttribute("CcId") == null) {
                                            row[0].setAttribute("CcId",
                                                                getHexDocNoFromFun((String) row[0].getAttribute("TvouCldId"),
                                                                                   (Integer) row[0].getAttribute("TvouSlocId"),
                                                                                   (String) row[0].getAttribute("TvouOrgId"),
                                                                                   (Integer) row[0].getAttribute("UsrIdCreate"),
                                                                                   Integer.parseInt(row[0].getAttribute("TvouTypeId").toString())));
                                        }
                                        // ADFBeanUtils.callBindingsMethod("updateCostCenterAmt", null, null);
                                        ADFBeanUtils.callBindingsMethod("updateCostCenterAmt", new Object[] {
                                                                        row[0].getAttribute("BillAmtTotSp") }, new Object[] {
                                                                        "amountSp" });
                                    } else {
                                        row[0].setAttribute("CcId", null);
                                    }

                                }


                                row[0].setAttribute("TvouAmtSp", row[0].getAttribute("BillAmtTotSp"));
                                row[0].setAttribute("TvouAmtBs", row[0].getAttribute("BillAmtTotBs"));
                            }
                        }

                        getAm().getTvouBillDtl1().executeQuery();
                        setModeExpense("E");
                        return "back";
                    }
                } else {

                    return "back";
                }
            } else {
                ADFUtil.showFacesMsg(resolvEl("#{bundle['MSG.1965']}"), resolvEl("#{bundle['MSG.1964']}"),
                                     FacesMessage.SEVERITY_ERROR, null);
                return null;
            }
        } else {
            return "back";
        }
    }

    /**
     * Method created to manual remove the row since Delete method from
     * operation binding is not functioning as aspected.
     * The key for row which is to be removed may be required for this and
     * since the key cannot be casted out from string
     * the value in f:attribute is obtained by trans ATTRIBUTE defined in row Impl class
     * which returns the Key as an object type.
     * **/
    public void deleteBillLineLink(ActionEvent actionEvent) {

        //get the key value from f:attribute attached to the calling command link
        // Key key = (oracle.jbo.Key)actionEvent.getComponent().getAttributes().get("billTabKey");

        //call the method to remove with Key key.
        // removeTvouBilDtlRow(key);
        OperationBinding obCommit = getBindings().getOperationBinding("Delete");
        obCommit.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(billDtlTab);
        AdfFacesContext.getCurrentInstance().addPartialTarget(billFormBinding);

    }

    public void removeTvouBilDtlRow(Key k) {

        /** Never  Remove this setAttribute for  amount and bill no . As it will create some problem. Amandeep(14-july-2014) */
        System.out.println("key val :" + k);
        getAm().getTvouBillDtl1().getRow(k).setAttribute("TvouAmtSp", new Number(0));
        getAm().getTvouBillDtl1().getRow(k).setAttribute("TvouBillNo", "");
        getAm().getTvouBillDtl1().getRow(k).remove();


    }

    /**
     * Method created to manual remove the row since Delete method from
     * operation binding is not functioning as aspected.
     * The key for row which is to be removed may be required for this and
     * since the key cannot be casted out from string
     * the value in f:attribute is obtained by trans ATTRIBUTE defined in row Impl class
     * which returns the Key as an object type.
     * **/
    public void deleteBankInstrmnt(ActionEvent actionEvent) {

        //get the key value from f:attribute attached to the calling command link
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("deleteInstrmnt");

        //call the method to remove with Key key.
        removeTvouBankInstrmntRow(key);

        AdfFacesContext.getCurrentInstance().addPartialTarget(bankInstrumntTab);

    }

    /**
     * Method to remove rows from TvouLineChqVO
     * **/
    public void removeTvouBankInstrmntRow(Key k) {

        System.out.println("key val :" + k);
        getAm().getTvouLineChq().getRow(k).remove();
        // System.out.println("refrsh required" + getAm().getTvouBillDtl1().needsRefresh());

    }

    /**
     * Method to set current row in TvouLineChqVO
     * **/
    public void setTvouBankInstrmntRow(ActionEvent actionEvent) {

        //get the key for seleted bank instrument
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("chqNo");

        //set the current row with help of key
        getAm().getTvouLineChq().setCurrentRow(getAm().getTvouLineChq().getRow(key));

        System.out.println("refrsh required" + getAm().getTvouLineChq().needsRefresh());
        AdfFacesContext.getCurrentInstance().addPartialTarget(bankInstrumntTab);
    }

    public void setCancelTaxDis(boolean cancelTaxDis) {
        this.cancelTaxDis = cancelTaxDis;
    }

    public boolean isCancelTaxDis() {
        return cancelTaxDis;
    }


    /**Process TDS for Bill- Expense Voucher*/
    public void procTdsBillDetail(ValueChangeEvent valueChangeEvent) {
        TempVoucherAMImpl am = this.getAm();

        String rulei = valueChangeEvent.getNewValue().toString();


        if (rulei != null) {

            //  getAm().getDBTransaction().postChanges();
            am.procTdsForHdrBill(rulei);
        }

        ViewObjectImpl impl = am.getTvouTdsRuleLineHd();
        impl.executeQuery();
    }

    /**Opens popup for TDS Detail for Bill- Expense Voucher*/
    public void applyTdsBill(ActionEvent actionEvent) {

        /*  OperationBinding obCommit = getBindings().getOperationBinding("Commit");
        obCommit.execute(); */

        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        am.getDBTransaction().postChanges();
        ViewObject tdsHd = am.getTvouTdsRuleHd();
        tdsHd.executeQuery();

        /**Check for the tax entry in tvou_tds_rule table if not create a new row on table. */

        System.out.println("RowCount in TDS Rule--->" + tdsHd.getRowCount());
        if (tdsHd.getRowCount() == 0) {
            BindingContainer binding = getBindings();
            OperationBinding ob = binding.getOperationBinding("CreateInsert1");
            ob.execute();
        }
        //  System.out.println("Replica amt is-->" + replicaAmt);
        // autoAmtTransBind.setValue(replicaAmt);
        showPopup(tdsDetailBillPoUp, true);

    }

    public void tdsDetailLinkBill(ActionEvent actionEvent) {

    }

    public void setTdsDetailBillPoUp(RichPopup tdsDetailBillPoUp) {
        this.tdsDetailBillPoUp = tdsDetailBillPoUp;
    }

    public RichPopup getTdsDetailBillPoUp() {
        return tdsDetailBillPoUp;
    }

    public void setCancelTdsDis(boolean cancelTdsDis) {
        this.cancelTdsDis = cancelTdsDis;
    }

    public boolean isCancelTdsDis() {
        return cancelTdsDis;
    }

    /**Re-Apply TDS for Bill- Expense Voucher*/
    public void reApplyTdsOnBillLink(ActionEvent actionEvent) {
        TempVoucherAMImpl am = this.getAm();
        Row impl1 = am.getTvouTdsRuleHd().getCurrentRow();
        String rulei = impl1.getAttribute("TvouTdsRuleId").toString();
        if (rulei != null) {
            am.procTdsForHdrBill(rulei);
        }
        ViewObjectImpl impl = am.getTvouTdsRuleLineHd();
        impl.executeQuery();
    }

    /**Method to check whether any checkbox is selected in bill detail page or not 2-05-2013 @Ashish Kumar*/
    public Boolean getTdsYNBill() {
        System.out.println("in getTdsYNBill");
        TempVoucherAMImpl am = getAm();
        ViewObjectImpl v1 = am.getTvouBillDtl1();
        RowSetIterator rit = v1.createRowSetIterator(null);

        Integer i = 0;

        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouTdsFlg") != null) {
                if (lineRow.getAttribute("TvouTdsFlg").equals("Y")) {
                    i = i + 1;
                }
            }
        }
        rit.closeRowSetIterator();
        if (i > 0) {
            // System.out.println("TDS Applied in Bill");
            return true;
        } else
            return false;
    }

    /**Method to calculate total taxable amount of Bill detail 02-05-2013 @Ashish Kumar*/

    public Number getTdsAmountBill() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouBillDtl1();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number totAmt = new Number(0);


        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouTdsFlg") != null) {
                if (lineRow.getAttribute("TvouTdsFlg").equals("Y")) {
                    totAmt = totAmt.add((Number) (lineRow.getAttribute("TvouAmtSp")));
                }
            }
        }
        rit.closeRowSetIterator();
        System.out.println("total amount in tds amount bill = " + totAmt);
        return totAmt;
    }

    /**Method to check taxable amount for tds in bill and basic amount for tds 02-05-2013 @Ashish Kumar*/
    public Boolean checkTdsBill() {
        System.out.println("getTdsAmountBill().compareTo(getTdsBasicAmount()) = " +
                           getTdsAmountBill().compareTo(getTdsBasicAmount()));
        if (getTdsYNBill() == true && getTdsAmountBill().compareTo(getTdsBasicAmount()) != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**Method to delete tds on bill when no checkbox is selected 03-05-2013 @Ashish Kumar*/
    public void deleteTdsBill() {
        if (getTdsYNBill() == false) {
            TempVoucherAMImpl am = this.getAm();
            Row curTvou = am.getTvou1().getCurrentRow();
            String vouId = curTvou.getAttribute("TvouId").toString();

            /** Create object for tds rule view*/
            ViewObject tvouTds = am.getTvouTdsRuleHd();
            /** Create object for tds rule line view*/
            ViewObject tvouTdsLine = am.getTvouTdsRuleLineHd();


            RowSetIterator rowIterator = tvouTdsLine.createRowSetIterator(null);
            rowIterator.reset();
            /**Remove all row from tds rule line table for this voucher */
            while (rowIterator.hasNext()) {
                Row currVouLne = rowIterator.next();
                currVouLne.remove();
            }
            /**Remove current row from tds rule table*/
            rowIterator.closeRowSetIterator();

            Row row[] = tvouTds.getFilteredRows("TvouId", vouId);
            if (row.length > 0) {
                row[0].remove();
            }
            //  System.out.println("Total TDS Row Removed is--->" + row.length);

            // tvouTdsLine.executeQuery();
        }
    }

    public void setTaxChangeFlag(boolean taxChangeFlag) {
        this.taxChangeFlag = taxChangeFlag;
    }

    public boolean isTaxChangeFlag() {
        if (getTaxYN() == true && checkManualTax()) {
            return true;
        } else {
            return false;
        }
    }

    /**Dialog Listener for Bill Tds Dialog to commit current transaction 06-05-2013 @Ashish Kumar*/

    public void tdsDialogListener(DialogEvent dialogEvent) {

    }

    public void setAddChequeBind(RichSelectBooleanCheckbox addChequeBind) {
        this.addChequeBind = addChequeBind;
    }

    public RichSelectBooleanCheckbox getAddChequeBind() {
        return addChequeBind;
    }

    public void setBudgetWarnPopUpbind(RichPopup budgetWarnPopUpbind) {
        this.budgetWarnPopUpbind = budgetWarnPopUpbind;
    }

    public RichPopup getBudgetWarnPopUpbind() {
        return budgetWarnPopUpbind;
    }

    /**DialogListener for Warning Message Popup for budget 18-05-2013 @Ashish Kumar*/
    public void warningBudgetDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            // SaveButtonCall();
        }
    }

    public void setDueDateBind(RichInputDate dueDateBind) {
        this.dueDateBind = dueDateBind;
    }

    public RichInputDate getDueDateBind() {
        return dueDateBind;
    }

    public String dialogueNoButton() {
        provisionalPopUp.hide();
        return null;
    }

    public void billAmtSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //&& dueDateBillBind.getValue()!=null
        if (object != null) {
            Number amount = (Number) object;
            if (amount.compareTo(0) == -1 || amount.compareTo(0) == 0) {
                FacesMessage errMsg =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.489']}").toString()); //Invalid Amount
                errMsg.setDetail(resolvElDCMsg("#{bundle['MSG.514']}").toString()); //Amount must be greater than Zero(0)
                throw new ValidatorException(errMsg);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(billDtlTab);

        AdfFacesContext.getCurrentInstance().addPartialTarget(billFormBinding);

    }

    public void setTvouTypBind(RichSelectOneChoice tvouTypBind) {
        this.tvouTypBind = tvouTypBind;
    }

    public RichSelectOneChoice getTvouTypBind() {
        return tvouTypBind;
    }

    public void applyTaxlink(ActionEvent actionEvent) {
        /**To maintain state of Replicate amount checkbox @23-07-2013*/

        //        OperationBinding obCommit = getBindings().getOperationBinding("Commit");
        //        obCommit.execute();
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        am.getDBTransaction().postChanges();
        ViewObject taxHd = am.getTvouTaxRuleHd();
        taxHd.executeQuery();
        /**Check for the tax entry in tvou_tds_rule table if not create a new row on table. */

        if (taxHd.getRowCount() == 0) {
            BindingContainer binding = getBindings();
            OperationBinding ob = binding.getOperationBinding("CreateInsert2");
            ob.execute();
        }
        //   autoAmtTransBind.setValue(replicaAmt);
        showPopup(taxDetailBillPopUpBind, true);
    }

    /**Process TAX for Bill- Expense Voucher- 31-05-2013 @Ashish kumar*/
    public void procTaxBillDetail(ValueChangeEvent valueChangeEvent) {
        TempVoucherAMImpl am = this.getAm();

        String rulei = valueChangeEvent.getNewValue().toString();
        //   System.out.println("Rule iD TDS:" + rulei);

        if (rulei != null) {
            am.procTaxForHdrBill(rulei);
        }

        ViewObjectImpl impl = am.getTvouTaxRuleLineHd();
        impl.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineBillBinding);
    }

    public void taxDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            //            BindingContainer bindings = getBindings();
            //            DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("TvouLinesLnkIterator");
            //            Key parentKey = parentIter.getCurrentRow().getKey();
            //            OperationBinding obCommit = bindings.getOperationBinding("Commit");
            //            obCommit.execute();
            //            OperationBinding ob1 = bindings.getOperationBinding("Execute1");
            //            ob1.execute();
            //            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
    }

    public void setTaxDetailBillPopUpBind(RichPopup taxDetailBillPopUpBind) {
        this.taxDetailBillPopUpBind = taxDetailBillPopUpBind;
    }

    public RichPopup getTaxDetailBillPopUpBind() {
        return taxDetailBillPopUpBind;
    }

    /**Re-Apply TAX for Bill- Expense Voucher*/
    public void reApplyTaxOnBillLink(ActionEvent actionEvent) {
        TempVoucherAMImpl am = this.getAm();
        Row impl1 = am.getTvouTaxRuleHd().getCurrentRow();
        String rulei = impl1.getAttribute("TvouTaxRuleId").toString();
        if (rulei != null) {
            am.procTaxForHdrBill(rulei);
        }
        ViewObjectImpl impl = am.getTvouTaxRuleLineHd();
        impl.executeQuery();
    }

    /**Code to validate tax amount for Bill Amount 01-06-2013*/

    /**Method to check whether any checkbox is selected in bill detail for Tax page or not */
    public Boolean getTaxYNBill() {
        TempVoucherAMImpl am = getAm();
        ViewObjectImpl v1 = am.getTvouBillDtl1();
        RowSetIterator rit = v1.createRowSetIterator(null);

        Integer i = 0;
        //   System.out.println("Iterator is-->" + rit.hasNext() + "and RowCount in iterator is--->" + rit.getRowCount());
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouTaxFlg") != null) {
                if (lineRow.getAttribute("TvouTaxFlg").equals("Y")) {
                    i = i + 1;
                }
            }
        }
        rit.closeRowSetIterator();
        if (i > 0) {
            //System.out.println("Tax Applied in Bill");
            return true;
        } else
            return false;
    }

    /**Method to calculate total taxable amount of Bill detail 02-05-2013 @Ashish Kumar*/

    public Number getTaxAmountBill() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouBillDtl1();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Number totAmt = new Number(0);


        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TvouTaxFlg") != null) {
                if (lineRow.getAttribute("TvouTaxFlg").equals("Y")) {
                    totAmt = totAmt.add((Number) (lineRow.getAttribute("TvouAmtSp")));
                }
            }
        }
        rit.closeRowSetIterator();
        //   System.out.println("Total amount for tax is-->" + totAmt);
        return totAmt;
    }

    /**Method to check taxable amount for tax in bill and basic amount for tax */
    public Boolean checkTaxBill() {

        if (getTaxYNBill() == true && getTaxAmountBill().compareTo(getTaxBasicAmount()) == 1 ||
            getTaxAmountBill().compareTo(getTaxBasicAmount()) == -1) {
            return true;
        } else {
            return false;
        }
    }

    /**Method to delete tds on bill when no checkbox is selected 03-05-2013 @Ashish Kumar*/
    public void deleteTaxBill() {
        if (getTaxYNBill() == false) {
            System.out.println("in if when getTaxYNBill is false");
            TempVoucherAMImpl am = this.getAm();
            Row curTvou = am.getTvou1().getCurrentRow();
            String vouId = curTvou.getAttribute("TvouId").toString();
            //   System.out.println("Voucher id from tax delete is-->" + vouId);

            /** Create object for tds rule view*/
            ViewObject tvouTds = am.getTvouTaxRuleHd();

            /** Create object for tds rule line view*/
            ViewObject tvouTdsLine = am.getTvouTaxRuleLineHd();

            RowSetIterator rowIterator = tvouTdsLine.createRowSetIterator(null);
            // System.out.println("size =" + rowIterator.getRangeSize());
            rowIterator.reset();
            /**Remove all row from tds rule line table for this voucher */
            while (rowIterator.hasNext()) {
                System.out.println("in while");
                Row currVouLne = rowIterator.next();
                currVouLne.remove();
            }
            /**Remove current row from tds rule table*/
            rowIterator.closeRowSetIterator();

            Row row[] = tvouTds.getFilteredRows("TvouId", vouId);
            if (row.length > 0) {
                System.out.println("removed rule");
                row[0].remove();
            }
            //    System.out.println("Total tax Row Removed is--->" + row.length);
            // tvouTdsLine.executeQuery();
        }
    }

    public void dupltemplateNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String tmplName = object.toString();
            /*  ViewObject tmplVou = getAm().getTmplVou();
            Row rows[] = tmplVou.getFilteredRows("TmplName", tmplName); */
            //  System.out.println("Total Duplicate Row is--->"+rows.length);
            String result = (String) callBindingsMethod("CheckDuplicateTemplateName", new Object[] { tmplName }, new Object[] {
                                                        "TemplateName" });

            if (result.equalsIgnoreCase("N")) {
                FacesMessage duplMsg =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.515']}").toString()); //Duplicate Template Name
                duplMsg.setDetail(resolvElDCMsg("#{bundle['MSG.516']}").toString()); //Change template name
                throw new ValidatorException(duplMsg);
            } else if (result.equalsIgnoreCase("E")) {
                FacesMessage duplMsg =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1260']}").toString()); //Saved Successfully !
                duplMsg.setDetail(resolvElDCMsg("#{bundle['MSG.477']}").toString()); //Something went wrong. Please contact ESS
                throw new ValidatorException(duplMsg);
            }
        }
    }

    public void setBilDtVsbl(boolean bilDtVsbl) {
        this.bilDtVsbl = bilDtVsbl;
    }
    /**Method to show and hide bill detail according to voucher type @23-07-2013*/

    /**
     * @return
     */
    public boolean isBilDtVsbl() {
        try {
            ViewObject vouViw = getAm().getTvou1();
            Row vouHD = vouViw.getCurrentRow();
            Row vouLn = getAm().getTvouLinesLnk().getCurrentRow();

            if (vouLn != null) {
                        if (vouHD.getAttribute("TvouTypeId").equals(7) || vouHD.getAttribute("TvouTypeId").equals(9) ||
                            vouHD.getAttribute("TvouTypeId").equals(10) ||
                            vouHD.getAttribute("TvouTypeId").equals(11) ||
                            vouHD.getAttribute("TvouTypeId").equals(12) || vouHD.getAttribute("TvouTypeId").equals(14)
                            || vouHD.getAttribute("TvouTypeId").equals(8) ||
                        vouHD.getAttribute("TvouTypeId").equals(1) || vouHD.getAttribute("TvouTypeId").equals(17)) {
                            this.bilDtVsbl = true;
                        } else {
                            this.bilDtVsbl = false;
                        }
                    
                }
            
        } catch (Exception e) {
            this.bilDtVsbl = true;
        }
        return bilDtVsbl;
    }

    public void setCoaNmTransBind(RichInputListOfValues coaNmTransBind) {
        this.coaNmTransBind = coaNmTransBind;
    }

    public RichInputListOfValues getCoaNmTransBind() {
        return coaNmTransBind;
    }

    public void setStatusChqBind(RichSelectOneRadio statusChqBind) {
        this.statusChqBind = statusChqBind;
    }

    public RichSelectOneRadio getStatusChqBind() {
        return statusChqBind;
    }

    /**Status Validator for Cheque Detail 21-06-2013- Not in Use*/
    public void statusValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String chqNoCur = object.toString();
        ViewObject tvouChq = getAm().getTvouLineChq();
        Row vouHd = getAm().getTvou1().getCurrentRow();
        ViewObject tvouChqWhl = getAm().getTvouLineChqVO1();
        tvouChqWhl.setRangeSize(tvouChqWhl.getRowCount());
        Row rArray[] = tvouChqWhl.getAllRowsInRange();
        Row cRow = tvouChq.getCurrentRow();
        String status = cRow.getAttribute("TvouInstrmntTypStat").toString();
        System.out.println("Cheq no from Current row is-->" + chqNoCur);
        int count = 0;
        if (vouHd.getAttribute("TvouTypeId").equals(2)) {
            Row rows[] = tvouChq.getFilteredRows("TvouId", vouHd.getAttribute("TvouId"));
            if (rows.length > 0) {
                if (status.equalsIgnoreCase("N")) {
                    String chqNo = "";
                    for (Row r : rArray) {

                        if (!r.equals(cRow)) {
                            try {
                                chqNo = r.getAttribute("TvouInstrmntNo").toString();
                            } catch (NullPointerException npe) {
                                System.out.println("NPE:" + npe);
                                chqNo = "";
                            }
                            if (chqNo.equalsIgnoreCase(chqNoCur)) {
                                count = count + 1;
                            }
                        }
                    }
                    if (count > 0) {
                        String msg2 = resolvEl("#{bundle['MSG.517']}"); //Duplicate Cheque No. found
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                    }

                } else if (status.equalsIgnoreCase("R")) {

                }
            }
        }
    }

    /**Validation for voucher date fin. year wise @26-05-2013*/
    public void voucherDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Date vouDate = (Date) object;

        Row curRow = getAm().getTvou1().getCurrentRow();
        String orgId = curRow.getAttribute("TvouOrgId").toString();
        Integer slocId = Integer.parseInt(curRow.getAttribute("TvouSlocId").toString());
        /** Commented by amandeep on 07-July-2014*/
        /*  String dtFlg =
            (String)callStoredFunction2(VARCHAR, "APP.PKG_APP.GET_FY_STAT_FOR_TXN(?,?,?)", new Object[] { slocId,
                                                                                                          orgId,
     System.out.prin);                                                                                                     vouDate }); */
        System.out.println("cld_id = " + cld_id + " orgId = " + orgId + " = vouDate" + vouDate);
        String dtFlg = (String) callStoredFunction2(VARCHAR, "APP.FN_GET_FY_STAT_FOR_TXN(?,?,?)", new Object[] {
                                                    cld_id, orgId, vouDate
        });
        if (resolveElExp("#{bindings.Sysdate.inputValue}") != null) {
            Date sysDt = (Date) resolveElExp("#{bindings.Sysdate.inputValue}");
            //java.sql.Timestamp datetime=new java.sql.Timestamp(System.currentTimeMillis());
            // Date sysdate=new oracle.jbo.domain.Date(datetime);
            //  java.sql.Date dt=new java.sql.Date(vouDate.dateValue());
            String dt = vouDate.dateValue().toString();

            System.out.println("vouDate = " + dt + " sysdate = " + sysDt);
            if (dt.compareTo(sysDt.toString()) > 0) {
                System.out.println("more than current date");
                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.521']}").toString()); //Invalid Date
                errMsg.setDetail(resolvElDCMsg("#{bundle['MSG.519']}").toString()); //Entry is not allowed for this date
                throw new ValidatorException(errMsg);
            }
        }

        if (dtFlg.equalsIgnoreCase("Y")) {
            System.out.println("when function return n");
            FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.521']}").toString()); //Invalid Date
            errMsg.setDetail(resolvElDCMsg("#{bundle['MSG.519']}").toString()); //Entry is not allowed for this date
            throw new ValidatorException(errMsg);
        } else {
            String mnthChk = (String) callStoredFunction2(VARCHAR, "APP.fn_get_mnthly_txn_alw(?,?,?,?)", new Object[] {
                                                          cld_id, orgId, slocId, vouDate
            });
            if (mnthChk.equalsIgnoreCase("Y")) {
                System.out.println("when function return n");
                // Book is closed for the following month selected in voucher date
                FacesMessage errMsg =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.2241']}").toString()); //Book is closed for the following month selected in voucher date
                errMsg.setDetail(resolvElDCMsg("#{bundle['MSG.519']}").toString()); //Entry is not allowed for this date
                throw new ValidatorException(errMsg);
            }
        }
    }

    public void instrumentStatusVCE(ValueChangeEvent vce) {
        System.out.println("Value from Radio button--->" + vce.getNewValue());
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setAutoAmtTransBind(RichSelectBooleanCheckbox autoAmtTransBind) {
        this.autoAmtTransBind = autoAmtTransBind;
    }

    public RichSelectBooleanCheckbox getAutoAmtTransBind() {
        return autoAmtTransBind;
    }

    /**To process Checkbox value up to model level 03-07-2013*/
    public void replicateAmountVCE(ValueChangeEvent vce) {
        //   vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setTvouIdBind(RichSelectOneChoice tvouIdBind) {
        this.tvouIdBind = tvouIdBind;
    }

    public RichSelectOneChoice getTvouIdBind() {
        return tvouIdBind;
    }


    public void setModeCheque(boolean modeCheque) {
        this.modeCheque = modeCheque;
    }

    public boolean isModeCheque() {
        return modeCheque;
    }

    /** Validator for cheque amount @22-07-2013*/

    public void ChequeAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number chequeBal = new Number(0);
        Number amount = (Number) object;
        ViewObject tvouHd = getAm().getTvou1();
        Row curRow = tvouHd.getCurrentRow();
        ViewObject tvouLn = getAm().getTvouLinesLnk();
        Row curRowLn = tvouLn.getCurrentRow();
        Number amountVouch = new Number(0);
        if (curRowLn.getAttribute("TvouAmtSp") != null) {
            amountVouch = (Number) curRowLn.getAttribute("TvouAmtSp");
        }
        if (curRow.getAttribute("ChequeBal") != null) {
            chequeBal = (Number) curRow.getAttribute("ChequeBal");
        }
        //System.out.println("Total Cheque amount-->" + chequeBal + "Voucher line amount-->" + amountVouch +
        //                 "current cheque amount-->" + amount);
        if (amount.compareTo(0) == -1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.489']}").toString(),
                                                          //Invalid Amount
                                                          resolvElDCMsg("#{bundle['MSG.490']}").toString())); //Amount must be a positive value
        }
    }

    /** Exempted Check box for Tax Exemption @23-07-2013*/
    public void exemptedTaxVCE(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String exmpTax = "N";
        if (vce.getNewValue() != null) {
            exmpTax = vce.getNewValue().toString();
            System.out.println("Exmpt flg is--" + exmpTax);
        }
        TempVoucherAMImpl am = this.getAm();
        String rulei = null;
        if (taxRuleIdBind.getValue() != null) {
            rulei = taxRuleIdBind.getValue().toString();
        }

        if (rulei != null) {
            am.procTaxForHdr(rulei);
            this.cancelTaxDis = true;
        }

        ViewObject impl = am.getTvouTaxRuleLineHd();
        impl.executeQuery();

        if (impl.getRowCount() > 0) {
            this.cancelTaxDis = true;
        }

    }

    public void setTaxRuleIdBind(RichSelectOneChoice taxRuleIdBind) {
        this.taxRuleIdBind = taxRuleIdBind;
    }

    public RichSelectOneChoice getTaxRuleIdBind() {
        return taxRuleIdBind;
    }


    /**Methods created to delete voucher completely from Tvou @ 25-07-2013*/
    public boolean canDelete() {

        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}").toString());

        Integer pending = (Integer) callBindingsMethod("getDocUsrFromWF", null, null);
        System.out.println(pending + "--------" + usr_id);

        if (!pending.equals(usr_id)) {
            System.out.println("return false");
            return false;
        } else {
            System.out.println("return true");
            return true;
        }
    }

    /**
     * @return
     */
    public String deleteVoucherButton() {
        return null;
    }

    /**To make curridSp disable in tvou_lines, in case of there is any bill added @27-07-2013*/

    public void setIsCurridDsbl(boolean isCurridDsbl) {
        this.isCurridDsbl = isCurridDsbl;
    }

    public boolean isIsCurridDsbl() {
        ViewObject billDtl = getAm().getTvouBillDtl1();
        Integer rowCount = billDtl.getRowCount();
        if (rowCount > 0) {
            isCurridDsbl = true;
        } else {
            isCurridDsbl = false;
        }
        return isCurridDsbl;
    }

    public void setBillAmtSpBind(RichInputText billAmtSpBind) {
        this.billAmtSpBind = billAmtSpBind;
    }

    public RichInputText getBillAmtSpBind() {
        return billAmtSpBind;
    }

    public void setBillAmtBsBind(RichInputText billAmtBsBind) {
        this.billAmtBsBind = billAmtBsBind;
    }

    public RichInputText getBillAmtBsBind() {
        return billAmtBsBind;
    }

    /**
     * Edited on 04-06-2014 by Priyank Khare.
     * **/
    public String deleteCompleteVoucher() {
        ViewObject vouHd = getAm().getTvou1();
        Row curRow = vouHd.getCurrentRow();
        String deleted = "N";
        Integer createdby = 0;
        if (curRow.getAttribute("TvouId") != null) {

            String vouId = curRow.getAttribute("TvouId").toString();

            //new parameters added on 12/03/2014

            String cldId = curRow.getAttribute("TvouCldId").toString();
            Integer slocId = Integer.parseInt(curRow.getAttribute("TvouSlocId").toString());
            String hoOrgId = curRow.getAttribute("TvouHoOrgId").toString();
            String orgId = curRow.getAttribute("TvouOrgId").toString();
            Integer instId = Integer.parseInt(curRow.getAttribute("TvouApplInstId").toString());
            createdby = Integer.parseInt(curRow.getAttribute("UsrIdCreate").toString());

            // parameter removed  on 12/03/2014
            //  String p_flag = "D";


            if (canDelete() == true) {

                Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}").toString());
                System.out.println("usr_id :" + usr_id + " createdby :" + createdby);


                if (usr_id.equals(createdby)) {
                    //                    deleted = (String) callStoredFunction2(VARCHAR, "FIN.FN_DEL_TVOU(?,?,?,?,?,?)", new Object[] {
                    //                                                           cldId, slocId, hoOrgId, orgId, instId, vouId
                    //                    });
                    showPopup(deletePopupBinding, true);
                } else {
                    //  System.out.println("user lov row "+getAm().getUserLov1().getEstimatedRowCount());
                    Row[] usrRows = getAm().getUserLov1().getFilteredRows("UsrId", createdby);
                    StringBuffer usrName = new StringBuffer("");
                    //  System.out.println("user row length "+usrRows.length);
                    if (usrRows.length > 0) {
                        usrName = new StringBuffer(usrRows[0].getAttribute("UsrName").toString());
                        usrName = new StringBuffer("[").append(usrName).append("]");
                    }

                    String msg2 = "Voucher can not be deleted, It can only be deleted by that user who have created it";
                    StringBuilder saveMsg = new StringBuilder("<html><body><p><b>" + msg2 + "</b></p>");
                    saveMsg.append("Created by- <b> " + usrName + "</b>");
                    saveMsg.append("</body></html>");

                    showFacesMsg(saveMsg.toString(), null, FacesMessage.SEVERITY_WARN, null);
                }
            } else {
                // System.out.println("user lov row "+getAm().getUserLov1().getEstimatedRowCount());
                Row[] usrRows = getAm().getUserLov1().getFilteredRows("UsrId", createdby);
                //  System.out.println("user row length "+usrRows.length);
                StringBuffer usrName = new StringBuffer("");
                if (usrRows.length > 0) {
                    usrName = new StringBuffer(usrRows[0].getAttribute("UsrName").toString());
                    usrName = new StringBuffer("[").append(usrName).append("]");
                }

                //This Voucher is pending at other user for approval, You can not edit this.

                String msg2 = "Voucher can not be deleted, this Voucher is pending at other user for approval";
                StringBuilder saveMsg = new StringBuilder("<html><body><p><b>" + msg2 + "</b></p>");
                saveMsg.append("Created by- <b> " + usrName + "</b>");
                saveMsg.append("</body></html>");

                showFacesMsg(saveMsg.toString(), null, FacesMessage.SEVERITY_WARN, null);

            }

            //            if (deleted.equalsIgnoreCase("Y")) {
            //
            //                showFacesMsg("Voucher Deleted Successfully", null, FacesMessage.SEVERITY_INFO, null);
            //
            //                return "goback";
            //            } else {
            //                return null;
            //            }

        } else {
            return null;
        }
        return null;

    }

    public void setParamCount(Integer paramCount) {
        this.paramCount = paramCount;
    }

    public Integer getParamCount() {
        OperationBinding ob = getBindings().getOperationBinding("on_load_page");
        paramCount = (Integer) ob.execute();

        return paramCount;
    }

    public void orgListVCE(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        TvouLinesVORowImpl curRow = (TvouLinesVORowImpl) getAm().getTvouLinesLnk().getCurrentRow();
        curRow.getLovVouCoaVO().setNamedWhereClauseParam("orgId", vce.getNewValue());
        curRow.getLovVouCoaVO().executeQuery();
        if (vce.getNewValue() != null) {
            String bs = getCurrIdBs(cld_id, sloc_id, (String) vce.getNewValue());
            System.out.println("base currr = " + bs + " cld_id = " + cld_id + " sloc_id = " + sloc_id);
            ViewObjectImpl impl = getAm().getTvouLinesLnk();
            Row currentRow = impl.getCurrentRow();
            if (currentRow != null) {
                TvouVOImpl hd = getAm().getTvou1();
                Row row = hd.getCurrentRow();
                LovLatestCurrImpl lovLatestCurr = getAm().getLovLatestCurr1();
                lovLatestCurr.setNamedWhereClauseParam("BindVouDt", row.getAttribute("TvouDt"));
                lovLatestCurr.setNamedWhereClauseParam("BindOrgId", row.getAttribute("TvouOrgId"));
                lovLatestCurr.setNamedWhereClauseParam("BindCurrId", bs);
                lovLatestCurr.executeQuery();

                Row[] xx = lovLatestCurr.getFilteredRows("CcCurrIdTxn", row.getAttribute("TvouCurrIdSp"));

                if (xx.length > 0) {
                    Number rate = (Number) xx[0].getAttribute("CcBuy");
                    System.out.println("rate = " + rate);
                    currentRow.setAttribute("TvouCc", rate);
                    currentRow.setAttribute("TvouCurrIdBs", bs);

                    Number spAmt = new Number(0);
                    if (currentRow.getAttribute("TvouAmtSp") != null)
                        spAmt = (Number) currentRow.getAttribute("TvouAmtSp");

                    currentRow.setAttribute("TvouAmtBs",
                                            spAmt.multiply(rate).round(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}").toString())));

                } else {
                    System.out.println("in else");
                }
            }
        }
    }

    public String getCurrIdBs(String cldId, Integer slcId, String OrgId) {
        return (String) callStoredFunction(Types.VARCHAR, "app.get_org_def_curr_bs1(?,?,?)", new Object[] {
                                           cldId, slcId, OrgId
    });
    }

    public Boolean getAddBill() {
        ViewObject vouViwln = getAm().getTvouLinesLnk();
        RowSetIterator rit = vouViwln.createRowSetIterator(null);
        Row r = rit.first();
        if (r != null &&
            vouViwln.getCurrentRow() !=
null) {
            // System.out.println("first row---->"+r);
            // System.out.println("current row--->"+vouViwln.getCurrentRow());
            if (r.equals(vouViwln.getCurrentRow())) {
                //    System.out.println("Inside Expression return-->"+(Boolean)resolveElExp("#{bindings.TvouLnTyp1.inputValue!='D' || pageFlowScope.TempVoucherDtlForm.mode== 'V' || pageFlowScope.TempVoucherDtlForm.mode=='S' }"));
                return (Boolean) resolveElExp("#{bindings.TvouLnTyp1.inputValue!='D' || pageFlowScope.TempVoucherDtlForm.mode== 'V' || pageFlowScope.TempVoucherDtlForm.mode=='S' }");

            } else {
                //      System.out.println("return true;");
                return true;
            }
        } else {
            return true;
        }
    }

    public void setDueDateBillBind(RichInputDate dueDateBillBind) {
        this.dueDateBillBind = dueDateBillBind;
    }

    public RichInputDate getDueDateBillBind() {
        return dueDateBillBind;
    }

    /**Created on 09/06/2013 by Priyank Khare
     * This method is used to disable the add instrument links for Line Type
     * other than Creditor, Debtor and Bank **/

    public Boolean getLineInstruDisabled() {

        Integer vouTypeId = null;
        Integer coaType = null;
        String behaviourType = null;
        //Integer coaTypeHd = null;
        ViewObjectImpl line = getAm().getTvouLinesLnk();

        if (line != null && line.getCurrentRow() != null) {
            if (line.getCurrentRow().getAttribute("CredrOrDebtr") != null) {
                behaviourType = getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CredrOrDebtr").toString();
                //System.out.println("behaviourType in vou bean -->" + behaviourType);
            }

            if (line.getCurrentRow().getAttribute("CoaTypeTrans") != null) {
                coaType =
                    Integer.parseInt(getAm().getTvouLinesLnk().getCurrentRow().getAttribute("CoaTypeTrans").toString());
                //System.out.println("coaType in vou bean -->" + coaType);
            }
            /*  if(getAm().getTvou1().getCurrentRow().getAttribute("CoaTypeTrans")!=null){
            coaTypeHd=Integer.parseInt(getAm().getTvou1().getCurrentRow().getAttribute("CoaTypeTrans").toString());
            System.out.println("coaTypeHd = "+coaTypeHd +" ----------------->");
            } */
            if (resolveElExp("#{bindings.TvouTypeId.inputValue}") != null) {
                //  System.out.println("resolveElExp(\"bindings.TvouTypeId.inputValue\") = "+resolveElExp("#{bindings.TvouTypeId.inputValue}"));
                vouTypeId = Integer.parseInt(resolveElExp("#{bindings.TvouTypeId.inputValue}").toString());
                //  System.out.println("vouctype in tvoubean -->" + vouTypeId);

            }

            if (vouTypeId != null && coaType != null && behaviourType != null) {
                if (vouTypeId.equals(2) || vouTypeId.equals(3)) {

                    if (behaviourType.equals("C") || behaviourType.equals("D") || coaType.equals(78) || coaType.equals(0)|| coaType.equals(5022)) { //
                        //   System.out.println("before returning false");
                        return false;
                    }
                    return true;
                } else if (vouTypeId.equals(6)) {
                    return false;
                } else
                    return true;
            } else
                return true;
        }
        return false;
    }

    /**
     * @return
     */
    public Number getTotalSpCreditForChq() {
        /** Get total credit base amount by adding all lines specific amount. */

        setAs(new Number(0));
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouLinesLnk();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Integer vouType = getVoucherType();

        /** Create a iterator of tvou lines and by use of a while loop add basic amount of rows having credit value. */

        if (vouType.equals(6)) {

            return as;
        } else {
            while (rit.hasNext()) {
                //sum for amount only for bank account
                Row lineRow = rit.next();
                //get header currency and conv factor, get sum basic, divide sum by
                if (lineRow.getAttribute("TvouAmtTyp").equals("Cr") &&
                    !lineRow.getAttribute("CoaTypeTrans").equals(78) &&
                    !lineRow.getAttribute("TvouLnTyp").equals("H")) {

                    try {
                        if (lineRow.getAttribute("TvouAmtSp") != null) {
                            as = as.add((Number) (lineRow.getAttribute("TvouAmtSp")));
                        } else {

                            as = as.add(0);
                        }
                    } catch (Exception e) {
                        as = as.add(0);
                        e.printStackTrace();
                    }
                }
            }
            rit.closeRowSetIterator();
            //    System.out.println("getTotalBsCredit---->" + (Number)as.round(amt_digit));
            return (Number) as.round(amt_digit);
        }
    }


    /**
     * @return
     */
    public Number getTotalSpDebitForChq() {
        /** Get total debit base amount by adding all lines specific amount. */
        setAs(new Number(0));
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v1 = am.getTvouLinesLnk();
        RowSetIterator rit = v1.createRowSetIterator(null);
        Integer vouType = getVoucherType();
        /** Create a iterator of tvou lines and by use of a while loop add basic amount of row having debit value. */

        if (vouType.equals(6)) {

            return as;
        } else {
            while (rit.hasNext()) {
                //sum for amount of bank account
                Row lineRow = rit.next();
                if (lineRow.getAttribute("TvouAmtTyp").equals("Dr") &&
                    !lineRow.getAttribute("CoaTypeTrans").equals(78)) {

                    try {

                        as = as.add((Number) (lineRow.getAttribute("TvouAmtSp")));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
            rit.closeRowSetIterator();
            //    System.out.println("getTotalBsDebit---->" + (Number)as.round(amt_digit));
            return (Number) as.round(amt_digit);
        }
    }

    /**
     * Created by Priyank Khare
     * Code to get total amount from TempVoucherDtlForm bean- 03-06-2013
     * Method used in addChequeNew and recheck Cheque amount.
     * **/

    private Number getAmtForChq() {
        Number amt = new Number(0);
        Number newAmt = new Number(0);
        Number crAmt = getTotalSpCreditForChq();
        Number drAmt = getTotalSpDebitForChq();
        Integer vouType = getVoucherType();

        try {
            if (vouType.equals(3)) {

                amt = new Number(drAmt.subtract(crAmt));
                amt = (Number) amt.abs();

            } else {
                amt = new Number(crAmt.subtract(drAmt));
                // System.out.println("crAmt = " + crAmt + " drAmt = " + drAmt + " amt = " + amt);

                amt = (Number) amt.abs();
                //  System.out.println("crAmt = " + crAmt + " drAmt = " + drAmt + " amt = " + amt);

            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        newAmt = (Number) amt.round(amt_digit);

        System.out.println("newAmt in getChqAmt---->" + newAmt);
        return newAmt;
    }

    /**
     * Method to validate total amount for cheque according to total amount in voucher lines.
     * Method returns true if revalidation required otherwise false.
     * Created by Priyank on 10/01/2014
     * **/

    public boolean validateChqAmt() {

        Number chqAmt = getAmtForChq();
        Number currentChqAmt = new Number(0);

        //calculate sum of amount in all cheques.

        ViewObject tvouChqLine = getAm().getTvouLineChq();
        RowSetIterator rit = tvouChqLine.createRowSetIterator(null);

        while (rit.hasNext()) {
            Row r = rit.next();

            if (r.getAttribute("TvouAmtSp") != null) {
                currentChqAmt = currentChqAmt.add((Number) r.getAttribute("TvouAmtSp"));
            } else {

                currentChqAmt = currentChqAmt.add(0);
            }
        }
        System.out.println("currentChqAmt" + currentChqAmt);

        if (chqAmt.compareTo(currentChqAmt) == 0) {
            return false;
        } else {
            return true;
        }
    }


    public Integer getMinLineNo() {

        Integer tempNo = 1;
        RowSetIterator rit = getAm().getTvouLinesLnk().createRowSetIterator(null);

        if (rit.first() != null) {
            Row frstRow = rit.first();
            tempNo = Integer.parseInt(frstRow.getAttribute("TvouSlNo").toString());
        }

        while (rit.hasNext()) {
            rit.next();
            Row r = rit.getCurrentRow();

            Integer slNo = Integer.parseInt(r.getAttribute("TvouSlNo").toString());
            //  System.out.println("slno----->" + slNo);
            if (slNo < tempNo) {

                tempNo = slNo;
            }
        }
        rit.closeRowSetIterator();
        //   System.out.println("minLineNo----->" + tempNo);
        return tempNo;
    }

    public boolean isFirstLn() {

        firstLn = true;
        Integer minLineNo = getMinLineNo();
        Integer lineNo = 1;
        ViewObjectImpl line = getAm().getTvouLinesLnk();
        if (line.getCurrentRow() != null) {
            if (getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouSlNo") != null) {
                lineNo =
                    Integer.parseInt(getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouSlNo").toString());
                //System.out.println("lineNo in coaValidator---->" + lineNo);
            }
            // System.out.println("minlineNo " + minLineNo);
            //  System.out.println("lineNo :" + lineNo);
            if (minLineNo.compareTo(lineNo) >= 0) {

                firstLn = true;
                // System.out.println("first line true");
            } else {
                // System.out.println("first line false");
                firstLn = false;
            }
        }
        return firstLn;
    }

    /**
     * This method will set disable a boolean variable to avoid editing the header item
     * once line item is created. Value set on line item create, delete, save , save &
     * forward.
     * **/
    public void setHdrDisable(boolean hdrDisable) {
        this.hdrDisable = hdrDisable;
    }

    public boolean isHdrDisable() {
        return hdrDisable;
    }

    public void setChqRequired(boolean chqRequired) {
        this.chqRequired = chqRequired;
    }

    public boolean isChqRequired() {
        return chqRequired;
    }

    public void setChequeAmountBind(RichInputText chequeAmountBind) {
        this.chequeAmountBind = chequeAmountBind;
    }

    public RichInputText getChequeAmountBind() {
        return chequeAmountBind;
    }

    public void setCoaType(Integer coaType) {
        this.coaType = coaType;
    }

    public Integer getCoaType() {
        return coaType;
    }

    public void setAssessableValBind(RichInputText assessableValBind) {
        this.assessableValBind = assessableValBind;
    }

    public RichInputText getAssessableValBind() {
        return assessableValBind;
    }

    public void assessableAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number amount = (Number) object;
            if (!ADFUtil.isPrecisionValid(26, 6, amount)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.1637']}"),
                                                              //Currency Rate is not Valid.
                                                              resolvEl("#{bundle['MSG.1637']}"))); //Currency Rate is not Valid.
            } else if (amount.compareTo(new BigDecimal(0)) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.489']}").toString(),
                                                              //Invalid Amount
                                                              resolvElDCMsg("#{bundle['MSG.490']}").toString())); //Amount must be a positive value
            } else if (amount.compareTo((Number) basic_amt.getValue()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.1966']}"), //Specific Amount must be smaller than equal to Accessible Value.
                                                              resolvEl("#{bundle['MSG.253']}"))); //Invalid Value.
            }
        }
    }

    public void deleteChequeAction(ActionEvent actionEvent) {
        ViewObject tvouLineChq = getAm().getTvouLineChq();
        ViewObject tvouLine = getAm().getTvouLinesLnk();
        Row row = tvouLine.getCurrentRow();
        if (row != null) {
            Row curRow[] = tvouLineChq.getFilteredRows("TvouCoaId", row.getAttribute("TvouCoaId"));
            //   System.out.println("total rows in cheque for this id-->" + curRow.length);
            for (Row r : curRow) {
                r.remove();
            }
            getAm().getTvouLineChq().executeQuery();
            TvouLinesVORowImpl tvouRow = (TvouLinesVORowImpl) tvouLine.getCurrentRow();
            tvouRow.setTvouInstruFlg("N");

        } else {
        }

        chequeDeletePopUp.hide();
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveAndForwardBinding);
    }

    public void ChequeDeletePopUpHideAction(ActionEvent actionEvent) {
        ViewObject TvouLinesLnk = ADFUtil.getAM().findViewObject("TvouLinesLnk");
        Row currentRow = TvouLinesLnk.getCurrentRow();
        if (currentRow != null) {
            currentRow.setAttribute("TvouInstruFlg", "Y");
        }
        chequeDeletePopUp.hide();
    }

    protected Object callStoredFunctionForRoundOff(int sqlReturnType, String stmt, Object[] bindVars) {
        System.out.println("in callStoredFunctionForRoundOff");
        CallableStatement st = null;
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        try {
            System.out.println("in try");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            System.out.println("after call");
            st.registerOutParameter(1, sqlReturnType);
            System.out.println("after registtre");
            if (bindVars != null) {
                /*  for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                } */
                System.out.println("before setting");
                st.setObject(2, bindVars[0]);
                st.setObject(3, bindVars[1]);
                st.setObject(4, bindVars[2]);
                System.out.println("after setting");
            }
            st.registerOutParameter(5, VARCHAR);
            //  System.out.println("st.getObject(4) = "+st.getObject(4));
            //   String name=st.getObject(5).toString();
            st.executeUpdate();
            System.out.println(st.getObject(1));
            return st.getObject(1);

        } catch (SQLException e) {
            e.printStackTrace();
            int end = e.getMessage().indexOf("\n");
            System.out.println("e.getMessage() = " + e.getMessage());
            String message = e.getMessage().substring(11, end);

            FacesMessage msg = new FacesMessage(message);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
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

    public void roundOffvalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            String check = object.toString();
            //System.out.println("CHECK BOXX:"+check);
            ViewObject tvou = getAm().getTvou1();
            Row curRow = tvou.getCurrentRow();

            Integer slocId = Integer.parseInt(curRow.getAttribute("TvouSlocId").toString());
            String hoorgId = (curRow.getAttribute("TvouHoOrgId").toString());
            String cldId = curRow.getAttribute("TvouCldId").toString();

            if (check.equalsIgnoreCase("true")) {

                String validate;
                //check if COA for round off exists or not.
                try {
                    validate = callStoredFunctionForRoundOff(VARCHAR, "FIN.FN_IS_ROUNDOFF_ACC_EXIST(?,?,?,?)", new Object[] {
                                                             cldId, slocId, hoorgId
                    }).toString();
                    System.out.println("validate " + validate);
                } catch (Exception e) {
                    validate = "N";
                    System.out.println("Exception in round off--" + e);
                }
                if (validate.equalsIgnoreCase("N")) {

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.1092']}").toString(), //Please create Chart of Account for Round off
                                                                  null));
                }
            }
        }
    }

    public void setCreateTemplateButton(RichCommandButton createTemplateButton) {
        this.createTemplateButton = createTemplateButton;
    }

    public RichCommandButton getCreateTemplateButton() {
        return createTemplateButton;
    }

    /**
     * Method to call task flow method to create line according to the selected template.
     * The action is associated with createTemplateButton(hidden) and has been queued in the value change event for the template LOV.
     * created on 24/01/2014 by Priyank Khare
     * **/
    public String createTemplateAction() {

        //  workFlowCall();
        AdfFacesContext.getCurrentInstance().addPartialTarget(templatePanel);

        return "createTemplate";
    }

    public void setHdrCoaDtlPopup(RichPopup hdrCoaDtlPopup) {
        this.hdrCoaDtlPopup = hdrCoaDtlPopup;
    }

    public RichPopup getHdrCoaDtlPopup() {
        return hdrCoaDtlPopup;
    }

    public void hdrCoaDtlPopupCall(ActionEvent actionEvent) {
        // Add event code here...
        ADFBeanUtils.callBindingsMethod("showCoaInfo", new Object[] { "TVOU" }, new Object[] { "table" });
        showPopup(hdrCoaDtlPopup, true);

    }


    public void setTemplatePanel(RichPanelLabelAndMessage templatePanel) {
        this.templatePanel = templatePanel;
    }

    public RichPanelLabelAndMessage getTemplatePanel() {
        return templatePanel;
    }

    public void TvouAmtBsValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object == null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.343']}").toString(),
                                                          //Chart of Account is required
                                                          resolvElDCMsg("#{bundle['MSG.511']}").toString())); //Please first enter Chart of Account
        }
    }

    public Number getAmtDifference() {

        return (getTotalBsCredit()).subtract(getTotalBsDebit());
    }

    public void setManualAdjLnk(RichCommandLink manualAdjLnk) {
        this.manualAdjLnk = manualAdjLnk;
    }

    public RichCommandLink getManualAdjLnk() {
        return manualAdjLnk;
    }

    public void setAutoAdjLnk(RichCommandLink autoAdjLnk) {
        this.autoAdjLnk = autoAdjLnk;
    }

    public RichCommandLink getAutoAdjLnk() {
        return autoAdjLnk;
    }

    public void setInstrumentAmtValidatePopUp(RichPopup instrumentAmtValidatePopUp) {
        this.instrumentAmtValidatePopUp = instrumentAmtValidatePopUp;
    }

    public RichPopup getInstrumentAmtValidatePopUp() {
        return instrumentAmtValidatePopUp;
    }

    /**
     * Method to match the instrument amount with the line specific amount.
     * Logic written in AM IMPL.
     * return false if amount is not valid
     * return true if amount is valid
     * Created by Priyank Khare on 30-05-2014.
     * Modified on 11-06-2014 to validate amount with bank amount.
     * **/

    private boolean instrumentAmtValid() {

        OperationBinding op = getBindings().getOperationBinding("compareInstrumentAmt");
        op.execute();

        //compare total Basic amount of voucher with total amount for cheque

        if (getAm().getTvouLineChqVO1().getCurrentRow() != null &&
            getAm().getTvouLineChqVO1().getCurrentRow().getAttribute("SumChqAmtTrans") != null &&
            !getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(6)) {

            //compare instrument total amount with the return value of method getAmtForChq which calculate amount for new cheque
            if (getAmtForChq().compareTo((Number) getAm().getTvouLineChqVO1().getCurrentRow().getAttribute("SumChqAmtTrans")) !=
                0) {
                /*  System.out.println("getAm().getTvouLineChqVO1().getCurrentRow().getAttribute(\"SumChqAmtTrans\") = " +
                                   getAm().getTvouLineChqVO1().getCurrentRow().getAttribute("SumChqAmtTrans"));
                System.out.println("getAmtForChq() = " + getAmtForChq()); */
                return false;
            }
        } else if (getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(6)) {
            TempVoucherAMImpl am = getAm();
            ViewObjectImpl lines = am.getTvouLinesLnk();
            RowSetIterator rsi = lines.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Number amtChq = new Number(0);
                Row next = rsi.next();
                Number TvouAmtSp = (Number) next.getAttribute("TvouAmtSp");
                // System.out.println("TvouAmtSp of lins= "+TvouAmtSp);
                Object TvouSlNo = next.getAttribute("TvouSlNo");
                //System.out.println("TvouSlNo of lines = "+TvouSlNo);
                if (TvouSlNo != null && next.getAttribute("CoaTypeTrans").equals(78)) {
                    TvouLineChqVOImpl chq = am.getTvouLineChqVO1();
                    Row[] filteredRows = chq.getFilteredRows("TvouSlNo", TvouSlNo);
                    if (filteredRows.length > 0) {
                        int l = filteredRows.length;
                        //      System.out.println(" l = "+l);
                        for (int i = 0; i < l; i++) {
                            Number AmtSp = (Number) filteredRows[i].getAttribute("TvouAmtSp");

                            amtChq = amtChq.add(AmtSp);
                            // l--;
                        }
                        if (TvouAmtSp.compareTo(amtChq) != 0) {
                            return false;
                        }
                    }

                }

            }
        }

        return true;
    }

    public String instrumentAmtValidateOk() {

        //call to this action means user is ok with the mismatch and
        //the validaions for the voucher will be continued in save action.

        instrumentAmtValidatePopUp.hide();
        if (saveAction() == true) {

            costCenterSaveAction();
        }
        return null;
    }

    public void instrumentAmtValidateCancel(ActionEvent actionEvent) {
        //
        //if user presses cancel then the save action for voucher should be aborted.
        //and the popup will hide.
        instrumentAmtValidatePopUp.hide();
    }

    /**
     * Generalized method used to display the facesmessages.
     * Created by Priyank Khare on 03-06-2014.
     * @param msgHdr is to set the header for message dialog
     * @param msgDtl is to set if further detail information is required.
     * @param msgSeverity is to set severity mode.
     * @param msgMode is kept to incorporate conditional activities.
     * **/
    public void showFacesMsg(String msgHdr, String msgDtl, javax.faces.application.FacesMessage.Severity msgSeverity,
                             String msgMode) {

        FacesMessage msg = new FacesMessage(msgHdr);
        msg.setDetail(msgDtl);
        msg.setSeverity(msgSeverity);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    /**
     * Methods to call method defined in bindings.
     * @param bindingName to call method of this name from model bindings.
     * Created by Priyank Khare on 03-06-2014.
     * **/

    public Object callBindingsMethod(String bindingName, Object[] paramVal, Object[] paramNm) {

        //set the operation binding name
        op = getBindings().getOperationBinding(bindingName);

        //set the parameter values if present
        if (paramVal != null && paramNm != null) {

            for (int z = 0; z < paramVal.length; z++) {

                op.getParamsMap().put(paramNm[z], paramVal[z]);

            }
        }

        op.execute();

        return op.getResult();
    }

    public void setBillDtlTab(RichTable billDtlTab) {
        this.billDtlTab = billDtlTab;
    }

    public RichTable getBillDtlTab() {
        return billDtlTab;
    }

    protected String getWfId() {
        return callBindingsMethod("getWfId", null, null).toString();
    }

    public String getWorkFlwId() {
        return workFlwId;
    }

    public void setWorkFlwId(String workFlwId) {
        this.workFlwId = workFlwId;
    }

    public void setBankInstrumntTab(RichTable bankInstrumntTab) {
        this.bankInstrumntTab = bankInstrumntTab;
    }

    public RichTable getBankInstrumntTab() {
        return bankInstrumntTab;
    }

    public void setAdjDataFetchDisable(String adjDataFetchDisable) {
        this.adjDataFetchDisable = adjDataFetchDisable;
    }

    public String getAdjDataFetchDisable() {
        return adjDataFetchDisable;
    }

    /**
     * Method used to decide if Fetch button in Auto Adjustment page is to be enabled or not
     * If there is data in TvouAdjAutoVO then it should remain disable other wise not.
     * Created on 16-06-2014 by Priyank Khare.
     * **/
    public String autoAdjDataFetch() {

        if ((getAm().getTvouAdjAutoVO().getEstimatedRowCount()) == 0) {

            System.out.println("AdjDataFetch " + getAm().getTvouAdjAutoVO().getEstimatedRowCount());
            return "N";
        }

        return "Y";
    }

    /**
     * Action Method to call Auto Adjustment page.
     * AdjDataFetchDisable variable is set before the action.
     * **/
    public String autoAdjCallAction() {

        //call the method to set the value of variavle used to set data fetch enable/disable parameter
        setAdjDataFetchDisable(autoAdjDataFetch());

        return "autoAdjustment";
    }
    /*  */

    public void setManualTax(Integer manualTax) {
        this.manualTax = manualTax;
    }

    public Integer getManualTax() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject vouViwln = am.getTvouLinesLnk();
        if (vouViwln != null) {
            RowSetIterator rsi = vouViwln.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row next = rsi.next();
                if (next.getAttribute("TvouLnTyp").equals("DTAX")) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public void setManualTds(Integer manualTds) {
        this.manualTds = manualTds;
    }

    public Integer getManualTds() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject vouViwln = am.getTvouLinesLnk();
        if (vouViwln != null) {
            RowSetIterator rsi = vouViwln.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row next = rsi.next();
                if (next.getAttribute("TvouLnTyp").equals("DTDS")) {
                    return 1;
                }
            }
            rsi.closeRowSetIterator();
        }
        return 0;
    }

    public void setOldManualTax(String oldManualTax) {
        this.oldManualTax = oldManualTax;
    }

    public String getOldManualTax() {
        return oldManualTax;
    }

    public void setOldManualTds(String oldManualTds) {
        this.oldManualTds = oldManualTds;
    }

    public String getOldManualTds() {
        return oldManualTds;
    }

    public void setSaveBinding(RichCommandImageLink saveBinding) {
        this.saveBinding = saveBinding;
    }

    public RichCommandImageLink getSaveBinding() {
        return saveBinding;
    }

    public void setSaveAndForwardBinding(RichCommandImageLink saveAndForwardBinding) {
        this.saveAndForwardBinding = saveAndForwardBinding;
    }

    public RichCommandImageLink getSaveAndForwardBinding() {
        return saveAndForwardBinding;
    }


    public void manualTaxValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            System.out.println("object=" + object.toString());

            if (object.toString().equalsIgnoreCase("true")) {
                ViewObject tvou = getAm().getTvou1();
                Row curRow = tvou.getCurrentRow();

                Integer slocId = Integer.parseInt(curRow.getAttribute("TvouSlocId").toString());
                String hoorgId = (curRow.getAttribute("TvouHoOrgId").toString());
                String cldId = curRow.getAttribute("TvouCldId").toString();
                String orgId = curRow.getAttribute("TvouOrgId").toString();
                Integer instId = Integer.parseInt(curRow.getAttribute("TvouApplInstId").toString());
                String tvouId = curRow.getAttribute("TvouId").toString();
                // System.out.println("tvouId in Manual Tax Validator = " + tvouId);
                Object callStoredFunction = callStoredFunction(Types.VARCHAR, "FIN.fn_manual_tax_exmpted(?,?,?,?,?,?)", new Object[] {
                                                               cldId, slocId, hoorgId, orgId, instId, tvouId
                });
                //System.out.println("callStoredFunction = " + callStoredFunction);
                if (callStoredFunction != null) {
                    if (callStoredFunction.toString().equalsIgnoreCase("Y")) {
                        //       System.out.println("before through validation");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['MSG.1967']}"),
                                                                      null)); //Auto Tax is applied and Exempted.So you can't Apply Manual Tax!
                    }
                }
            }
        }
    }

    public void setInstrumentTypeBinding(RichSelectOneChoice instrumentTypeBinding) {
        this.instrumentTypeBinding = instrumentTypeBinding;
    }

    public RichSelectOneChoice getInstrumentTypeBinding() {
        return instrumentTypeBinding;
    }

    public void setExemptedFlagBinding(RichInputText exemptedFlagBinding) {
        this.exemptedFlagBinding = exemptedFlagBinding;
    }

    public RichInputText getExemptedFlagBinding() {
        return exemptedFlagBinding;
    }

    public void setAddInstrumentDetailBinding(RichCommandLink addInstrumentDetailBinding) {
        this.addInstrumentDetailBinding = addInstrumentDetailBinding;
    }

    public RichCommandLink getAddInstrumentDetailBinding() {
        return addInstrumentDetailBinding;
    }

    public void setSpecificAmountInfopopupBinding(RichPopup specificAmountInfopopupBinding) {
        this.specificAmountInfopopupBinding = specificAmountInfopopupBinding;
    }

    public RichPopup getSpecificAmountInfopopupBinding() {
        return specificAmountInfopopupBinding;
    }

    public String specificAmountInfoAction() {
        showPopup(specificAmountInfopopupBinding, true);
        return null;
    }

    public void billNumberVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent != null) {
            Object bill = valueChangeEvent.getNewValue();
            duplicateBillNumber(bill, null);
        }
    }

    public void duplicateBillNumber(Object bill, Object CoaId) {
        System.out.println("duplicateBillNumber");
        if (bill != null) {
            String billNumber = (String) bill;
            if (billNumber.toString().length() > 0) {
                BindingContainer bc = getBindings();
                OperationBinding binding = bc.getOperationBinding("checkBillNumberUniqueness");
                binding.getParamsMap().put("BillNo", billNumber);
                if (CoaId != null) {
                    binding.getParamsMap().put("CoaId", CoaId);
                }
                binding.execute();
                if (binding.getErrors().isEmpty()) {
                    Object object = binding.getResult();
                    if (object != null) {
                        if (object.toString().equalsIgnoreCase("Y")) {
                            showFacesMsg(resolvEl("#{bundle['MSG.1965']}!"), null, FacesMessage.SEVERITY_WARN, null);
                        } else if (object.toString().equalsIgnoreCase("E")) {
                            showFacesMsg(resolvEl("#{bundle['MSG.1975']}"), resolvEl("#{bundle['MSG.120']}"), //Something went wrong. Contact ESS
                                         FacesMessage.SEVERITY_ERROR, null);

                        }
                    } else {
                        showFacesMsg(resolvEl("#{bundle['MSG.1975']}."), resolvEl("#{bundle['MSG.1684']}"), //Please select COA Name.
                                     FacesMessage.SEVERITY_ERROR, null);

                    }
                }
            }
        }
    }

    public void setBillNumberBinding(RichInputText billNumberBinding) {
        this.billNumberBinding = billNumberBinding;
    }

    public RichInputText getBillNumberBinding() {
        return billNumberBinding;
    }


    public void setChqAvailable(boolean chqAvailable) {
        this.chqAvailable = chqAvailable;
    }

    public boolean isChqAvailable() {
        System.out.println("in isChqAvailable");
        OperationBinding binding = ADFUtil.getBindings().getOperationBinding("checkInstrumentAddedOrNot");
        binding.execute();
        Object result = binding.getResult();
        System.out.println("result = " + result);
        if (result != null) {
            if (result.toString().equalsIgnoreCase("Y"))
                return true;
            else
                return false;
        }
        return chqAvailable;
    }


    public void expenseBillNumberDupliCheck(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            //Object coaIdAttr = valueChangeEvent.getComponent().getAttributes().get("coaIdAttr");
            Object coaIdAttr = coaIdBinding.getValue();
            System.out.println("coaIdAttr = " + coaIdAttr);
            duplicateBillNumber(valueChangeEvent.getNewValue(), coaIdAttr);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(billDtlTab);
        AdfFacesContext.getCurrentInstance().addPartialTarget(billFormBinding);

    }


    public void setModeExpense(String ModeExpense) {
        this.ModeExpense = ModeExpense;
    }

    public String getModeExpense() {
        System.out.println("ModeExpense = " + ModeExpense);
        return ModeExpense;
    }

    public String gobillDetailAction() {
        return "gobillDetail";
    }

    public String instrumentTypeCheck() {

        return null;
    }
    private String TaxAmtType = "N";
    private String TdsAmtType = "N";

    public void amountTypeVCL(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            System.out.println("getManualTax() = " + getManualTax() + " getManualTax().compareTo(1) = " +
                               getManualTax().compareTo(1));
            System.out.println("getManualTds() = " + getManualTds() + " getManualTds().compareTo(1) = " +
                               getManualTds().compareTo(1));
            if (getManualTax().compareTo(1) == 0) {
                TaxAmtType = "Y";
            }
            if (getManualTds().compareTo(1) == 0) {
                TdsAmtType = "Y";
            }
            System.out.println("getTaxAmtType() = " + getTaxAmtType() + "getTdsAmtType() = " + getTdsAmtType());
        }
    }

    public void setTaxAmtType(String TaxAmtType) {
        this.TaxAmtType = TaxAmtType;
    }

    public String getTaxAmtType() {
        return TaxAmtType;
    }

    public void setTdsAmtType(String TdsAmtType) {
        this.TdsAmtType = TdsAmtType;
    }

    public String getTdsAmtType() {
        return TdsAmtType;
    }

    public void billDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("object  = " + object);
        if (object != null) {
            Date dt = (Date) object;
            Date vouDt = (Date) resolveElExp("#{bindings.TvouDt.inputValue}");
            System.out.println("sysDt = " + vouDt + " dt = " + dt +
                               " vouDt.dateValue().toString().compareTo(dt.dateValue().toString())<0 = " +
                               vouDt.dateValue().toString().compareTo(dt.dateValue().toString()));
            if (vouDt.dateValue().toString().compareTo(dt.dateValue().toString()) < 0) {
                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.521']}").toString()); //Invalid Date
                errMsg.setDetail(resolvElDCMsg("#{bundle['MSG.519']}").toString()); //Entry is not allowed for this date
                throw new ValidatorException(errMsg);
            }

        }

    }

    public void instruNumberVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            String instr = (String) valueChangeEvent.getNewValue();
            Row row = getAm().getTvouLineChq().getCurrentRow();
            instruNumberUnique(instr, row, "vce");
        }

    }

    public String instruNumberUnique(String instr, Row row, String action) {
        Integer CoaId = null;
        if (getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(6)) {
            CoaId = (Integer) getAm().getTvouLinesLnk().getCurrentRow().getAttribute("TvouCoaId");
        } else {
            CoaId = (Integer) getAm().getTvou1().getCurrentRow().getAttribute("TvouCoaId");
        }
        String error = "Invalid Cheque Number";
        String subError = (String) resolvElDCMsg("#{bundle['MSG.1239']}");
        /* System.out.println("param " + getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouCldId") +
                       getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouSlocId") +
                       getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouHoOrgId") +
                       getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouOrgId") +
                       CoaId + instrNo +
                       getAm().getTvouLineChq().getCurrentRow().getAttribute("TvouInstrmntTyp")+
                       getAm().getTvou1().getCurrentRow().getAttribute("TvouId")+
                       getMode()); */
        Object fnValidate = callStoredFunction2(Types.VARCHAR, "FIN.fn_chk_instru_no_unique(?,?,?,?,?,?,?,?,?)", new Object[] {
                                                row.getAttribute("TvouCldId"), row.getAttribute("TvouSlocId"),
                                                row.getAttribute("TvouHoOrgId"), row.getAttribute("TvouOrgId"), CoaId,
                                                instr, row.getAttribute("TvouInstrmntTyp"), row.getAttribute("TvouId"),
                                                getMode()
        });

        if ("N".equals(fnValidate) && action.equalsIgnoreCase("vce")) {
            ADFUtil.showFacesMsg(error, subError, FacesMessage.SEVERITY_WARN, null);
            //showPopup(duplicateWarningBinding, true);
        } else if ("N".equals(fnValidate) && action.equalsIgnoreCase("s&f")) {
            // showPopup(duplicateWarningBinding, true);
            return "N";
        }
        return "Y";
    }

    public void submitOCAction(ActionEvent actionEvent) {
        //tvouOtherCharges Execute
        ViewObjectImpl oc = getAm().getTvouOc();
        RowSetIterator rsi = oc.createRowSetIterator(null);
        Integer valid = 0;
        while (rsi.hasNext() && valid == 0) {
            Row next = rsi.next();
            Object TvouOcAmtSp = next.getAttribute("TvouOcAmtSp");
            Object TvouCoaId = next.getAttribute("TvouCoaId");

            if (TvouCoaId != null) {
                if (TvouOcAmtSp != null) {
                    Number amt = (Number) TvouOcAmtSp;
                    if (amt.compareTo(0) <= 0) {
                        valid = 1;
                    }
                }
            } else
                valid = 1;


        }
        if (valid == 0) {
            BindingContainer bc = getBindings();
            OperationBinding binding = bc.getOperationBinding("tvouOtherCharges");
            binding.execute();
            OperationBinding line = bc.getOperationBinding("Execute");
            line.execute();
            actionEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            OperationBinding cc = bc.getOperationBinding("insertCostCenterInLines");
            cc.execute();
            ocPopupBinding.hide();
        } else {
            ADFUtil.showFacesMsg(resolvEl("#{bundle['MSG.489']}"), resolvEl("#{bundle['MSG.1974']}"), //Invalid Amount  //COA And Amount is Mandatory! Enter the value of Required fields.Amount must be greater than 0
                                 FacesMessage.SEVERITY_ERROR, null);
        }
    }

    public void setOcPopupBinding(RichPopup ocPopupBinding) {
        this.ocPopupBinding = ocPopupBinding;
    }

    public RichPopup getOcPopupBinding() {
        return ocPopupBinding;
    }

    public void openOcPopup(ActionEvent actionEvent) {
        showPopup(ocPopupBinding, true);
    }

    public void ocCoaDuplicateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            ViewObjectImpl oc = getAm().getTvouOc();
            Row currentRow = oc.getCurrentRow();
            RowSetIterator rsi = oc.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row next = rsi.next();
                if (next != currentRow) {
                    Object TvouCoaId = next.getAttribute("TvouCoaId");
                    if (TvouCoaId != null) {
                        System.out.println("object = " + object + " TvouCoaId = " + TvouCoaId +
                                           " TvouCoaId.equals(object) = " + TvouCoaId.equals(object));
                        if (TvouCoaId.equals(object)) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          resolvEl("#{bundle['MSG.46']}"),
                                                                          null)); //Duplicate Record Found.
                        }
                    }
                }
            }
        }

    }

    public void deleteOcAction(ActionEvent actionEvent) {
        getBindings().getOperationBinding("Delete1").execute();
        //  getBindings().getOperationBinding("Execute2").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocPanelCollectionBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBinding);

    }


    public void ocAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number amt = (Number) object;
            if (!ADFUtil.isPrecisionValid(26, 6, amt)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.1637']}"),
                                                              null)); //Invalid Precision/Scale! Please Enter valid amount.
            } else if (amt.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.1152']}"),
                                                              null)); //Negative value not allowed!!
            }
        }
    }

    public void setTaxPopupBinding(RichPopup taxPopupBinding) {
        this.taxPopupBinding = taxPopupBinding;
    }

    public RichPopup getTaxPopupBinding() {
        return taxPopupBinding;
    }

    public void setOcPanelCollectionBinding(RichPanelCollection ocPanelCollectionBinding) {
        this.ocPanelCollectionBinding = ocPanelCollectionBinding;
    }

    public RichPanelCollection getOcPanelCollectionBinding() {
        return ocPanelCollectionBinding;
    }

    public void setOcTableBinding(RichTable ocTableBinding) {
        this.ocTableBinding = ocTableBinding;
    }

    public RichTable getOcTableBinding() {
        return ocTableBinding;
    }

    public void submitTaxAction(ActionEvent actionEvent) {
        getBindings().getOperationBinding("taxConsolidation").execute();
        OperationBinding line = getBindings().getOperationBinding("Execute");
        line.execute();
        taxPopupBinding.hide();
    }

    public void cancelTaxAction(ActionEvent actionEvent) {
        taxPopupBinding.hide();
    }

    public void setCanceltaxButtonBinding(RichLink canceltaxButtonBinding) {
        this.canceltaxButtonBinding = canceltaxButtonBinding;
    }

    public RichLink getCanceltaxButtonBinding() {
        return canceltaxButtonBinding;
    }


    public void setTdsPopupBinding(RichPopup tdsPopupBinding) {
        this.tdsPopupBinding = tdsPopupBinding;
    }

    public RichPopup getTdsPopupBinding() {
        return tdsPopupBinding;
    }

    public void canceltdsAction(ActionEvent actionEvent) {
        // Add event code here...
        tdsPopupBinding.hide();
    }

    public void submitTdsAction(ActionEvent actionEvent) {
        // Add event code here...tdsConsolidation
        getBindings().getOperationBinding("tdsConsolidation").execute();
        OperationBinding line = getBindings().getOperationBinding("Execute");
        line.execute();
        tdsPopupBinding.hide();
    }

    public void setCancelTdsBinding(RichLink cancelTdsBinding) {
        this.cancelTdsBinding = cancelTdsBinding;
    }

    public RichLink getCancelTdsBinding() {
        return cancelTdsBinding;
    }

    public String cancelOcAction() {
        // Add event code here...
        ocPopupBinding.hide();
        return null;
    }

    public void setCancelocBinding(RichLink cancelocBinding) {
        this.cancelocBinding = cancelocBinding;
    }

    public RichLink getCancelocBinding() {
        return cancelocBinding;
    }

    public void setSubmitocBinding(RichLink submitocBinding) {
        this.submitocBinding = submitocBinding;
    }

    public RichLink getSubmitocBinding() {
        return submitocBinding;
    }

    public void setCoaIdBinding(RichInputText coaIdBinding) {
        this.coaIdBinding = coaIdBinding;
    }

    public RichInputText getCoaIdBinding() {
        return coaIdBinding;
    }

    public void applyBillTdsVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(billDtlTab);
        AdfFacesContext.getCurrentInstance().addPartialTarget(billFormBinding);

    }

    public void billNumberValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        BindingContainer bindings = getBindings();
        DCIteratorBinding dupli = (DCIteratorBinding) bindings.get("TvouBillDtl1Iterator");
        ViewObject vo1 = dupli.getViewObject();
        Row currentRow = vo1.getCurrentRow();
        RowSetIterator iterator = vo1.createRowSetIterator(null);
        //  Map m=new HashMap();
        int valid = 0;
        while (iterator.hasNext() && valid == 0) {
            System.out.println("valid= " + valid);
            Row next = iterator.next();
            if (next != currentRow) {
                Object TvouBillNo = next.getAttribute("TvouBillNo");
                System.out.println("billno = " + TvouBillNo);
                if (TvouBillNo != null) {
                    System.out.println("object.equals(TvouBillNo) = " + object.equals(TvouBillNo));
                    if (object.equals(TvouBillNo)) {
                        valid = 1;
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['MSG.1973']}"),
                                                                      null)); //Duplicate Bill Number

                    }
                    /*  System.out.println("m.size = " + m.size());
                    if (!m.containsKey(TvouBillNo)) {
                        m.put(TvouBillNo, TvouBillNo);
                    } else {
                        System.out.println("error");
                        valid = 1;
                    } */
                }
            }
        }
        iterator.closeRowSetIterator();
        /* if(object!=null){
            if(duplicateBillNumberCheckInVoucher().compareTo(0)!=0){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Duplicate Bill Number!",null));
            }
        } */
    }

    public void setBillFormBinding(RichPanelFormLayout billFormBinding) {
        this.billFormBinding = billFormBinding;
    }

    public RichPanelFormLayout getBillFormBinding() {
        return billFormBinding;
    }

    public void contextInfoLitnerHdr(ContextInfoEvent contextInfoEvent) {
        // Add event code here...
        ADFBeanUtils.callBindingsMethod("showCoaInfo", new Object[] { "TVOU" }, new Object[] { "table" });

    }

    public String showCoaInfoLine() {
        ADFBeanUtils.callBindingsMethod("showCoaInfo", new Object[] { "TVOU_LINES" }, new Object[] { "table" });
        showPopup(hdrCoaDtlPopup, true);
        return null;
    }


    public String goToEntity() {
        Object res = ADFBeanUtils.callBindingsMethod("applicationAllowed", new Object[] { 2019 }, new Object[] {
                                                     "MenuId" });
        if (res != null) {
            if (res.toString().equalsIgnoreCase("N")) {
                //                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //                                                              "You are not allowed to access Entity Setup", null));
                ADFModelUtils.showFacesMessage(resolvEl("#{bundle['MSG.1972']}"), resolvEl("#{bundle['MSG.1972']}"),
                                               FacesMessage.SEVERITY_INFO, null);
                return null;

            }
        }
        return "entity";
    }

    public String goToNA() {
        Object res = ADFBeanUtils.callBindingsMethod("applicationAllowed", new Object[] { 5504 }, new Object[] {
                                                     "MenuId" });
        if (res != null) {
            if (res.toString().equalsIgnoreCase("N")) {
                //                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //                                                              "You are not allowed to access Natural Account", null));
                ADFModelUtils.showFacesMessage(resolvEl("#{bundle['MSG.1971']}"), resolvEl("#{bundle['MSG.1971']}"),
                                               FacesMessage.SEVERITY_INFO, null);
                return null;

            }
        }
        return "na";
    }

    public String geToTax() {
        Object res = ADFBeanUtils.callBindingsMethod("applicationAllowed", new Object[] { 36 }, new Object[] {
                                                     "MenuId" });
        if (res != null) {
            if (res.toString().equalsIgnoreCase("N")) {
                //                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //                                                              "You are not allowed to access Tax profile", null));

                ADFModelUtils.showFacesMessage(resolvEl("#{bundle['MSG.1970']}"), resolvEl("#{bundle['MSG.1970']}"),
                                               FacesMessage.SEVERITY_INFO, null);
                return null;

            }
        }
        return "tax";

    }

    public String goToCc() {
        Object res = ADFBeanUtils.callBindingsMethod("applicationAllowed", new Object[] { 2020 }, new Object[] {
                                                     "MenuId" });
        if (res != null) {
            if (res.toString().equalsIgnoreCase("N")) {
                //                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //                                                              "You are not allowed to access Currency Convertion Application", null));
                //
                ADFModelUtils.showFacesMessage(resolvEl("#{bundle['MSG.1969']}"), resolvEl("#{bundle['MSG.1969']}"),
                                               FacesMessage.SEVERITY_INFO, null);
                return null;
            }
        }
        return "cc";
    }

    public String totalCrInString(Number amount) {
        if (amount != null) {
            String amt = amount.toString();
            if (!amt.contains(".")) {
                amt = amt.concat(".");
                for (int i = 0; i < amt_digit; i++) {
                    amt = amt.concat("0");
                }
            } else {
                int i = amt.indexOf(".");
                int count = 0;
                for (int j = i + 1; j < amt.toString().length(); j++) {
                    count++;
                }
                for (int j = 0; j < (amt_digit - count); j++) {
                    amt = amt.concat("0");
                }
            }
            int l = amt.indexOf(".");
            StringBuffer sb = new StringBuffer(amt);

            if (l > 3) {
                int rem = l % 3;
                if (rem == 0)
                    rem = 3;
                sb = sb.insert(rem, ",");
                int n = sb.indexOf(",");
                l++;
                for (int m = n + 3; m < l - 1; m = m + 3) {
                    sb = sb.insert(m + 1, ",");
                    m = m + 1;
                    l++;
                }

            }

            return sb.toString();
        }

        return null;
    }

    public void adjustmentValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            if (!object.toString().equalsIgnoreCase("N")) {
                String accntExist = isExchngFlctnAccntExist();
                System.out.println("accntExist = " + accntExist);
                if (accntExist.equalsIgnoreCase("N")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.1959']}"),
                                                                  null)); //Please Create Exchange Fluctuation account
                }
            }
        }

    }

    public void setDuplicateWarningBinding(RichPopup duplicateWarningBinding) {
        this.duplicateWarningBinding = duplicateWarningBinding;
    }

    public RichPopup getDuplicateWarningBinding() {
        return duplicateWarningBinding;
    }


    public String dupliInstrumentWarningAction() {
        duplicateWarningBinding.hide();
        System.out.println("after hidhing duplicateWarningBinding");
        if (saveAction() == true) {
            System.out.println("in if after saveAction return true");
            costCenterSaveAction();
            if (paymentLimit().equalsIgnoreCase("N")) {
                String msg = resolvEl("#{bundle['MSG.1958']}"); //Insufficient amount to make payment

                FacesMessage errMsg = new FacesMessage(msg);
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                errMsg.setDetail("");
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            } else if (getAm().getTvou1().getCurrentRow().getAttribute("TvouTypeId").equals(13)) {

                showPopup(provisionalPopUp, true);
                return null;

            } else {
                System.out.println("before return saveas gl");
                return saveAsGL();
            }
        }
        return null;
    }

    public void setIntimationIdBinding(RichSelectOneChoice intimationIdBinding) {
        this.intimationIdBinding = intimationIdBinding;
    }

    public RichSelectOneChoice getIntimationIdBinding() {
        return intimationIdBinding;
    }

    public void applyTaxBillLink(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(billDtlTab);
        AdfFacesContext.getCurrentInstance().addPartialTarget(billFormBinding);
    }

    public void setRefCoaBinding(RichSelectOneChoice refCoaBinding) {
        this.refCoaBinding = refCoaBinding;
    }

    public RichSelectOneChoice getRefCoaBinding() {
        return refCoaBinding;
    }

    public void setDeletePopupBinding(RichPopup deletePopupBinding) {
        this.deletePopupBinding = deletePopupBinding;
    }

    public RichPopup getDeletePopupBinding() {
        return deletePopupBinding;
    }

    public void deleteDialogListner(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public String deleteActionButton() {
        deletePopupBinding.hide();
        ViewObject vouHd = getAm().getTvou1();
        Row curRow = vouHd.getCurrentRow();
        String vouId = curRow.getAttribute("TvouId").toString();

        String cldId = curRow.getAttribute("TvouCldId").toString();
        Integer slocId = Integer.parseInt(curRow.getAttribute("TvouSlocId").toString());
        String hoOrgId = curRow.getAttribute("TvouHoOrgId").toString();
        String orgId = curRow.getAttribute("TvouOrgId").toString();
        Integer instId = Integer.parseInt(curRow.getAttribute("TvouApplInstId").toString());
        String deleted = (String) callStoredFunction2(VARCHAR, "FIN.FN_DEL_TVOU(?,?,?,?,?,?)", new Object[] {
                                                      cldId, slocId, hoOrgId, orgId, instId, vouId
        });
        if (deleted.equalsIgnoreCase("Y")) {

            showFacesMsg(resolvEl("#{bundle['MSG.1968']}"), null, FacesMessage.SEVERITY_INFO,
                         null); //Voucher Deleted Successfully

            return "goback";
        } else {
            return null;
        }
    }

    public void applyTDSVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.out.println("valueChangeEvent.getNewValue() = " + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                OperationBinding createOpBAddress = getBindings().getOperationBinding("insertDefaultTDS");
                createOpBAddress.execute();
            }
        }
    }

    public void setTaxRuleLineBillBinding(RichTable taxRuleLineBillBinding) {
        this.taxRuleLineBillBinding = taxRuleLineBillBinding;
    }

    public RichTable getTaxRuleLineBillBinding() {
        return taxRuleLineBillBinding;
    }

    public String loadReqAction() {
        OperationBinding createOpBAddress = getBindings().getOperationBinding("processIntimationId");
        createOpBAddress.execute();

        //Code by Nitesh Garg to pass CCID in case of imprest voucher 18-08-2015
        TempVoucherAMImpl am = getAm();
        /**Object of Voucher line view object*/
        ViewObjectImpl v = am.getTvouLinesLnk();
        RowSetIterator itr = v.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row row = itr.next();
            if (row.getAttribute("TransIsCostCenterAlw") != null) {
                System.out.println("before check of updateCostCenterAmt ");
                if (row.getAttribute("TransIsCostCenterAlw").toString().equalsIgnoreCase("Y")) {
                    System.out.println("beofre updateCostCenterAmt call");
                    if (row.getAttribute("CcId") == null) {
                        row.setAttribute("CcId",
                                         getHexDocNoFromFun((String) row.getAttribute("TvouCldId"),
                                                            (Integer) row.getAttribute("TvouSlocId"),
                                                            (String) row.getAttribute("TvouOrgId"),
                                                            (Integer) row.getAttribute("UsrIdCreate"),
                                                            Integer.parseInt(row.getAttribute("TvouTypeId").toString())));
                    }
                    ADFBeanUtils.callBindingsMethod("updateCostCenterAmt", null, null);
                } else {
                    row.setAttribute("CcId", null);
                }
            }
        }
        itr.closeRowSetIterator();


        return null;
    }

    public void setAccblAmt(Number amt) {
        Row tvouRow = getAm().getTvou1().getCurrentRow();
        if (amt != null) {
            if (tvouRow != null) {
                String cldId = (String) tvouRow.getAttribute("TvouCldId");
                Integer slocId = (Integer) tvouRow.getAttribute("TvouSlocId");
                String hoOrgId = (String) tvouRow.getAttribute("TvouHoOrgId");
                Integer coaId = 0;
                Row currRow = getAm().getTvouLinesLnk().getCurrentRow();
                if (currRow != null)
                    coaId = (Integer) currRow.getAttribute("TvouCoaId");
                System.out.println("cldId " + cldId + "slocId " + slocId + "hoOrgId " + hoOrgId + "coaId " + coaId);
                Number prcnt = new Number(0);
                BigDecimal res = (BigDecimal) (callStoredFunction2(Types.NUMERIC, "FIN.FN_GET_TAX_PER_FRM_COA(?,?,?,?)", new Object[] {
                                                                   cldId, slocId, hoOrgId, coaId
                }));
                
                System.out.println("Integer value" +res.intValue());
              //Integer resInt = res.intValue();
                try {
                    prcnt = new Number(res);
                    System.out.println("percentage in try" + prcnt);
                } catch (SQLException e) {
                }
                // Integer prcnt = res.intValue();
                if (prcnt != null && prcnt.compareTo(0) > 0) {
                    System.out.println("percentage " + prcnt);
                    Number accessableAmt = (Number) amt;
                    System.out.println("accessableAmt " + accessableAmt);
                    Number spAmt = (accessableAmt.multiply(prcnt)).divide(100);
                    System.out.println("tax amount " + spAmt);
                    getAm().getTvouLinesLnk().getCurrentRow().setAttribute("TvouAmtSp", spAmt);
                    Number rate = (Number) currRow.getAttribute("TvouCc");
                    Number result = (Number) spAmt.multiply(rate).round(amt_digit);
                    totalAmt.setValue(result);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(basic_amt);
                }
            }
        }
    }

    public void AccessableblAmtVCE(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.out.println("Value Change of Accessable amount");
        setAccblAmt((Number) valueChangeEvent.getNewValue());

    }
}

