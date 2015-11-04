package templateVoucher.view.bean;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.ManagedDataControl;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import templateVoucher.model.module.TmplVouAMImpl;
import templateVoucher.model.view.TmplVouLineVORowImpl;
import templateVoucher.model.view.TmplVouVOImpl;
import templateVoucher.model.view.TmplVouVORowImpl;


public class TmplAddBean implements ManagedDataControl {
    private String ocmode = "V";
    private String taxTyp = "A";
    private String tdsTyp = "A";
    private RichInputText amtSp;
    private RichInputText amtBs;
    private static int VARCHAR = Types.VARCHAR;
    private RichSelectOneChoice vouId;
    private Number totalCredit;
    private Number totalDebit;
    private Number totalSpCredit;
    private Number totalSpDebit;
    private RichPopup narrPopUp;
    private Boolean taxYN;
    private Boolean tdsYN;
    private Boolean checkBoxFlag;
    private Integer coaType;
    private RichSelectOneChoice chartofAC;
    private Integer slno = 0;
    private Boolean amtYN;
    private RichTable lineTable;
    private String backOnTaxDisable = "Y";
    private Number taxbasicamount;
    private String Flag = "N";
    private String modeFlag = "";
    private String cost_center_src = "H";
    private Integer cost_center_slno = 0;
    private BigDecimal cost_center_amount = new BigDecimal(0);
    private RichPopup costCenterPopup;
    private RichCommandLink taxRuleLink;
    private String docDisplayId;
    private String ocmodeflag = "false";

    private static int INTEGER = Types.INTEGER;
    private RichInputText ocAmtSp;
    private RichOutputText currRate;
    private RichInputListOfValues currIdSP;
    Integer count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
    private RichSelectBooleanCheckbox txdtl;
    private RichSelectBooleanCheckbox tdsDtl;

    // String OrgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
    String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
    String HoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
    Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
    private RichSelectOneChoice amtTypBind;
    private RichInputText templateNameBinding;

    public TmplAddBean() {
    }


    public void createButton(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("Createwithparameters");
        createOpBAddress.execute();

        Flag = "Y";
        setModeFlag("C");
    }


    /**For Multi Currency - change Specific currency of Tmpl header as per selected Bank or Cash Account @Ashish Kumar 01-03-2013 **/

    public void cashBankCoaValuechangeListener(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && vce.getNewValue() != "") {
            ViewObject tmplVouVO = getAm().getTmplVou();
            Row tmplVouRow = tmplVouVO.getCurrentRow();
            Integer coaId = Integer.parseInt(vce.getNewValue().toString());
            String orgId = tmplVouRow.getAttribute("TmplHoOrgId").toString();
            Integer currIdBs = Integer.parseInt(tmplVouRow.getAttribute("TmplCurrIdBs").toString());
            BigDecimal currency =
                (BigDecimal)callStoredFunction2(Types.NUMERIC, "FIN.FN_COA_CURR(?,?,?,?)", new Object[] { CldId,
                                                                                                          SlocId,
                                                                                                          HoOrgId,
                                                                                                          coaId });
            Integer curr = 73;


            try {
                curr = Integer.parseInt(currency.toString());
                if (curr != null) {

                    ViewObjectImpl lovLatestCurr = getAm().getLovLatestCurr();
                    lovLatestCurr.setNamedWhereClauseParam("BindOrgId", orgId);
                    lovLatestCurr.executeQuery();
                    RowQualifier rq = new RowQualifier(lovLatestCurr);
                    rq.setWhereClause("CcCurrId= " + currIdBs + " and CcCurrIdTxn= " + curr + "'");
                    Row[] rows = lovLatestCurr.getFilteredRows(rq);
                    Number rate = (Number)rows[0].getAttribute("CcBuy");
                    System.out.println("Rate for this currency is--->" + rate);
                    tmplVouRow.setAttribute("TmplCurrIdSp", curr);
                    tmplVouRow.setAttribute("TmplVouCc", rate);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(currIdSP);
                }
            } catch (Exception e) {
                tmplVouRow.setAttribute("TmplCurrIdSp", 73);
                tmplVouRow.setAttribute("TmplVouCc", 1);
            }
        }
    }

    public void currIdValueChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            ViewObjectImpl vo = getAm().getTmplVou();
            String orgId = vo.getCurrentRow().getAttribute("TmplVouOrgId").toString();
            ViewObjectImpl tmplVouLineVO = getAm().getTmplVouLine();
            Row tmplVouLineRow = tmplVouLineVO.getCurrentRow();

            if (tmplVouLineRow.getAttribute("TmplVouCurrIdSp") != null) {
                if (tmplVouLineRow.getAttribute("TmplVouCc") != null) {
                    Integer curr = Integer.parseInt(valueChangeEvent.getNewValue().toString());
                    Integer currIdBs = Integer.parseInt(tmplVouLineRow.getAttribute("TmplCurrIdBs").toString());
                    ViewObjectImpl lovLatestCurr = getAm().getLovLatestCurr();
                    lovLatestCurr.setNamedWhereClauseParam("BindOrgId", orgId);
                    lovLatestCurr.executeQuery();
                    RowQualifier rq = new RowQualifier(lovLatestCurr);
                    rq.setWhereClause("CcCurrId= " + currIdBs + " and CcCurrIdTxn= " + curr + "'");

                    Row[] rows = lovLatestCurr.getFilteredRows(rq);
                    System.out.println("total number of filterd rows are is =" + rows.length);
                    if (rows.length > 0) {
                        Number rate = (Number)rows[0].getAttribute("CcBuy");
                        System.out.println("rate------------->" + rate);
                        Number basic = (Number)getAmtSp().getValue();
                        System.out.println("basic amount--------------->" + basic);
                        Number result = basic.multiply(rate);
                        System.out.println("new amount----------------->" + result);
                        amtBs.setValue(result);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(amtBs);
                    }
                }
            }
        }
        BindingContainer bindings = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("TmplVouLineIterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        OperationBinding operationBinding = bindings.getOperationBinding("Execute1");
        operationBinding.execute();
        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        AdfFacesContext.getCurrentInstance().addPartialTarget(lineTable);


    }

    public void amtSpValueChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            ViewObjectImpl tmplVouLineVO = getAm().getTmplVouLine();
            Row tmplVouLineRow = tmplVouLineVO.getCurrentRow();
            if (tmplVouLineRow.getAttribute("TmplVouCurrIdSp") != null) {
                if (tmplVouLineRow.getAttribute("TmplVouCc") != null) {
                    Number rate = (Number)tmplVouLineRow.getAttribute("TmplVouCc");
                    Number basic = (Number)valueChangeEvent.getNewValue();
                    Number result = basic.multiply(rate);
                    amtBs.setValue(result);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(amtBs);
                }
            }
        }
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

    public void addLineAction(ActionEvent actionEvent) {
        Integer lineNo = null;

        ViewObject vouViw = getAm().getTmplVou();
        Row vouHD = vouViw.getCurrentRow();
        if ((vouHD.getAttribute("TmplVouTypeId").equals(2) || vouHD.getAttribute("TmplVouTypeId").equals(3) ||
             vouHD.getAttribute("TmplVouTypeId").equals(4) || vouHD.getAttribute("TmplVouTypeId").equals(5) ||
             vouHD.getAttribute("TmplVouTypeId").equals(6)) && vouHD.getAttribute("TmplVouCoaId") == null) {
            FacesMessage errMsg = new FacesMessage(resolvEl("#{bundle['MSG.343']}"));
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(chartofAC.getClientId(), errMsg);
        } else {

            BindingContext bindingctx = BindingContext.getCurrent();
            BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
            OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert");
            createOpBAddress.execute();
            ViewObject vouLineViw = getAm().getTmplVouLine();
            Row vouLn = vouLineViw.getCurrentRow();
            lineNo = getMaxNo();
            vouLn.setAttribute("TmplVouLnTyp", "D");
            String copyFlag = "Y";
            vouLn.setAttribute("TmplVouDt", vouHD.getAttribute("TmplVouDt"));


            vouLn.setAttribute("TmplVouSlNo", getMaxNo());
            if (vouHD.getAttribute("TmplVouSubTypeId") != null) {
                vouLn.setAttribute("TmplVouSubTypeId", vouHD.getAttribute("TmplVouSubTypeId"));
            }
            if (vouHD.getAttribute("TmplCurrIdSp") != null) {
                vouLn.setAttribute("TmplVouCurrIdSp", vouHD.getAttribute("TmplCurrIdSp"));
                vouLn.setAttribute("CurrIdTrans", vouHD.getAttribute("TmplCurrIdSp"));
            }

            if (vouHD.getAttribute("TmplVouCc") != null) {
                vouLn.setAttribute("TmplVouCc", vouHD.getAttribute("TmplVouCc"));

            }

            //  if (vouHD.getAttribute("CopyDescFlag") != null) {
            if (vouHD.getAttribute("TmplVouDesc") != null) {
                if (copyFlag.compareTo((String)vouHD.getAttribute("CopyDescFlag")) == 0)
                    vouLn.setAttribute("TmplNarr", vouHD.getAttribute("TmplVouDesc"));
            }
            // }
            if (vouHD.getAttribute("TmplVouOrgId") != null) {
                vouLn.setAttribute("TmplVouOrgId", vouHD.getAttribute("TmplVouOrgId"));
            }
            if (vouHD.getAttribute("TmplVouSlocId") != null) {
                vouLn.setAttribute("TmplVouSlocId", vouHD.getAttribute("TmplVouSlocId"));
            }
            if (vouHD.getAttribute("TmplCldId") != null) {
                vouLn.setAttribute("TmplCldId", vouHD.getAttribute("TmplCldId"));
            }
            if (vouHD.getAttribute("TmplHoOrgId") != null) {
                vouLn.setAttribute("TmplHoOrgId", vouHD.getAttribute("TmplHoOrgId"));
            }
            /* if(vouHD.getAttribute("TmplVouDesc") != null) {
                vouLn.setAttribute("TmplNarr", vouHD.getAttribute("TmplVouDesc"));
            }  */
            String orgId = vouLn.getAttribute("TmplVouOrgId").toString();
            Integer CurrBs = Integer.parseInt(getCurrIdBs(CldId, SlocId, orgId));
            vouLn.setAttribute("TmplCurrIdBs", CurrBs);
            vouLn.setAttribute("UsrIdCreate", Integer.parseInt(vouHD.getAttribute("UsrIdCreate").toString()));
            /**  Wrong validation commentd by amandeep on 20 june*/
            /* if (vouHD.getAttribute("TmplVouTypeId").equals(1)) {
                TmplVouLineVORowImpl r2 = (TmplVouLineVORowImpl)vouLineViw.getCurrentRow();
                r2.setLovforJV();
            } */

            if (vouHD.getAttribute("TmplVouTypeId").equals(1) || vouHD.getAttribute("TmplVouTypeId").equals(2) ||
                vouHD.getAttribute("TmplVouTypeId").equals(4) || vouHD.getAttribute("TmplVouTypeId").equals(6) ||
                vouHD.getAttribute("TmplVouTypeId").equals(7) || vouHD.getAttribute("TmplVouTypeId").equals(9) ||
                vouHD.getAttribute("TmplVouTypeId").equals(10)) {
                vouLn.setAttribute("TmplVouAmtTyp", "Dr");
            } else if (vouHD.getAttribute("TmplVouTypeId").equals(3) ||
                       vouHD.getAttribute("TmplVouTypeId").equals(5) ||
                       vouHD.getAttribute("TmplVouTypeId").equals(8) ||
                       vouHD.getAttribute("TmplVouTypeId").equals(12)) {
                vouLn.setAttribute("TmplVouAmtTyp", "Cr");
            } else {
                vouLn.setAttribute("TmplVouAmtTyp", "Dr");
            }
            if ((vouHD.getAttribute("TmplVouTypeId").equals(9) || vouHD.getAttribute("TmplVouTypeId").equals(10))) {
                if (lineNo == 1) {
                    vouLn.setAttribute("TmplVouAmtTyp", "Dr");
                    amtTypBind.setDisabled(true);
                } else {
                    vouLn.setAttribute("TmplVouAmtTyp", "Cr");
                    amtTypBind.setDisabled(false);
                }
            }
            if ((vouHD.getAttribute("TmplVouTypeId").equals(11) || vouHD.getAttribute("TmplVouTypeId").equals(12))) {
                if (lineNo == 1) {
                    vouLn.setAttribute("TmplVouAmtTyp", "Cr");
                    amtTypBind.setDisabled(true);
                } else {
                    vouLn.setAttribute("TmplVouAmtTyp", "Dr");
                    amtTypBind.setDisabled(false);
                }
            }
        }
    }

    public Integer getMaxNo() {
        /** Create iterator for tmpl lines */
        ViewObject TvouVw = getAm().getTmplVouLine();
        RowSetIterator rSetIter = TvouVw.createRowSetIterator(null);
        int max = 0;
        Row cRow = null;

        /** Run loop for all rows of tmpl lines and set incremented value to variable max */

        while (rSetIter.hasNext()) {
            cRow = rSetIter.next();

            if (Integer.parseInt(cRow.getAttribute("TmplVouSlNo").toString()) >= max) {
                max = Integer.parseInt(cRow.getAttribute("TmplVouSlNo").toString()) + 1;
            }
        }
        /** Return incremented slNo value */
        rSetIter.closeRowSetIterator();
        return max;
    }

    public String getCurrIdBs(String CldId, Integer SlocId, String OrgId) {
        return (String)callStoredFunction2(VARCHAR, "app.get_org_def_curr_bs1(?,?,?)",
                                           new Object[] { CldId, SlocId, OrgId });
    }

    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {

        CallableStatement st = null;
        try {
            st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
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
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveAction(ActionEvent actionEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        generateDocNo();
        operationBinding.execute();
        getAm().getLovVouId().executeQuery();
        TmplVouVORowImpl row = (TmplVouVORowImpl)getAm().getTmplVou().getCurrentRow();
        row.getLovVouIdVO().executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(vouId);
        slno = 0;
        getAm().tvouLineOp();
        getAm().getTmplVouLine().executeQuery();


    }

    /**Function call to generate document no in table 'APP$DOC$TXN' with the help of database function.*/
    public void generateDocNo() {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("generateDocNo");
        operationBinding.execute();

    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public void RuleIdValueChange(ValueChangeEvent valueChangeEvent) {

        Integer taxRule = Integer.parseInt(valueChangeEvent.getNewValue().toString());
        System.out.println("Tax rule is-" + taxRule);
        getAm().processTaxForHdr(taxRule);

    }
    private String TaxMode = "V";

    public String linkTaxRuleAction() {
        TaxMode = "E";
        ViewObject taxVo = getAm().getTmplVouTaxRule();
        // taxVo.executeQuery();
        if (taxVo.getRowCount() == 0) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
            operationBinding.execute();
        }

        return "tax";
    }

    public void tdsRuleIdValueChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            TmplVouAMImpl am = getAm();
            Integer tdsRule = Integer.parseInt(valueChangeEvent.getNewValue().toString());
            am.processTdsForLine(tdsRule);
            TaxMode = "E";
        }
    }

    public String tdsReApplyAction() {
        tdsTyp = "A";
        Integer tdsRule =
            Integer.parseInt(getAm().getTmplVouTdsRule().getCurrentRow().getAttribute("TmplVouTdsRuleId").toString());
        System.out.println("tds rule id----------->" + tdsRule);
        getAm().processTdsForLine(tdsRule);
        return null;
    }

    public String taxReApplyAction() {
        taxTyp = "A";
        Integer taxRule =
            Integer.parseInt(getAm().getTmplVouTaxRule().getCurrentRow().getAttribute("TmplVouTaxRuleId").toString());
        System.out.println("tax rule id------------>" + taxRule);
        getAm().processTaxForHdr(taxRule);
        return null;
    }

    public String tdsLinkAction() {
        ViewObject tdsVo = getAm().getTmplVouTdsRule();

        if (tdsVo.getRowCount() == 0) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
            operationBinding.execute();

        }
        //  tdsVo.executeQuery();
        return "tds";

    }

    public void setTotalCredit(Number totalCredit) {
        this.totalCredit = totalCredit;
    }

    public Number getTotalCredit() {
        setAs(new Number(0));
        ViewObject v1 = getAm().getTmplVouLine();
        RowSetIterator rit = v1.createRowSetIterator(null);


        if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp") != null) {
            if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp").equals("Cr")) {
                as = (Number)(rit.first().getAttribute("TmplVouAmtSp"));

            }
            while (rit.hasNext()) {
                Row lineRow = rit.next();
                if (lineRow.getAttribute("TmplVouAmtSp") != null && lineRow.getAttribute("TmplVouAmtTyp") != null) {
                    if (lineRow.getAttribute("TmplVouAmtTyp").equals("Cr")) {
                        as = as.add((Number)(lineRow.getAttribute("TmplVouAmtSp")));

                    }
                }
            }
        }
        rit.closeRowSetIterator();
        return as;
    }

    private static Number as = new Number();

    public void setTotalDebit(Number totalDebit) {
        this.totalDebit = totalDebit;
    }

    public Number getTotalDebit() {
        setAs(new Number(0));
        ViewObject v1 = getAm().getTmplVouLine();
        RowSetIterator rit = v1.createRowSetIterator(null);


        if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp") != null) {
            if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp").equals("Dr")) {
                as = (Number)(rit.first().getAttribute("TmplVouAmtSp"));

            }
            while (rit.hasNext()) {
                Row lineRow = rit.next();
                if (lineRow.getAttribute("TmplVouAmtTyp") != null && lineRow.getAttribute("TmplVouAmtSp") != null) {
                    if (lineRow.getAttribute("TmplVouAmtTyp").equals("Dr")) {
                        as = as.add((Number)(lineRow.getAttribute("TmplVouAmtSp")));

                    }
                }
            }
        }
        rit.closeRowSetIterator();
        return as;
    }

    public static void setAs(Number as) {
        TmplAddBean.as = as;
    }


    public void narrLinkAction(ActionEvent actionEvent) {
        showPopup(narrPopUp, true);
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

    public void setTaxYN(Boolean taxYN) {
        this.taxYN = taxYN;
    }

    public Boolean getTaxYN() {

        ViewObject v1 = getAm().getTmplVouLine();
        RowSetIterator rit = v1.createRowSetIterator(null);

        Integer i = 0;

        if (rit.first() != null && rit.first().getAttribute("TmplVouTxnTypTax").equals("Y")) {
            i = 1;
        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TmplVouTxnTypTax").equals("Y")) {
                i = i + 1;
            }
        }
        if (i > 0) {

            return true;
        } else {
            return false;
        }

    }

    public void setTdsYN(Boolean tdsYN) {
        this.tdsYN = tdsYN;
    }

    public Boolean getTdsYN() {
        ViewObject v1 = getAm().getTmplVouLine();
        RowSetIterator rit = v1.createRowSetIterator(null);

        Integer i = 0;
        if (rit.first() != null && rit.first().getAttribute("TmplVouTxnTypTds").equals("Y")) {
            i = 1;

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TmplVouTxnTypTds").equals("Y")) {
                i = i + 1;

            }
        }
        if (i > 0)
            return true;
        else
            return false;

    }


    public void hdCClinkAction(ActionEvent actionEvent) {

        ViewObjectImpl tmplVouVO = getAm().getTmplVou();
        Row tmplVouRow = tmplVouVO.getCurrentRow();
        String orgId = tmplVouRow.getAttribute("TmplVouOrgId").toString();
        System.out.println("slocId = " + SlocId + "orgId = " + orgId + "HoOrgId = " + HoOrgId + "CldId = " + CldId);
        Integer val = 0;
        val = isCostCenterPresent(SlocId, CldId, orgId, HoOrgId);
        System.out.println("value of is cost centre present = " + val);

        if (val > 1) {
            FacesMessage Message = new FacesMessage(resolvEl("#{bundle['MSG.131']}"));
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, Message);
        } else if (val == 0) {
            FacesMessage Message = new FacesMessage(resolvEl("#{bundle['MSG.344']}"));
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, Message);
        } else {
            cost_center_slno = 0;
            cost_center_amount = new BigDecimal(0);
            cost_center_src = "H";
            showPopup(costCenterPopup, true);
        }
    }

    public void dtCCLinkAction(ActionEvent actionEvent) {

        ViewObjectImpl tmplVouLineVO = getAm().getTmplVouLine();
        Row tmplVouLineRow = tmplVouLineVO.getCurrentRow();
        String orgId = tmplVouLineRow.getAttribute("TmplVouOrgId").toString();
        Integer val = 0;
        val = isCostCenterPresent(SlocId, CldId, orgId, HoOrgId);
        if (val > 1) {

            FacesMessage Message = new FacesMessage(resolvEl("#{bundle['MSG.131']}"));
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, Message);
        } else if (val == 0) {

            FacesMessage Message = new FacesMessage(resolvEl("#{bundle['MSG.344']}"));
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, Message);
        } else {

            cost_center_slno = (Integer)tmplVouLineRow.getAttribute("TmplVouSlNo");
            Number amount_temp = (Number)tmplVouLineRow.getAttribute("TmplVouAmtSp");
            cost_center_amount = amount_temp.bigDecimalValue();
            cost_center_src = "L";
            showPopup(costCenterPopup, true);
        }
    }

    public String setToTmplSaveCC(Integer P_TEMP_SLOC_ID, String P_TEMP_CLD_ID, String P_TEMP_HO_ORG_ID,
                                  String P_TEMP_ORG_ID, Integer P_TEMP_DOC_ID, String P_TEMP_ID,
                                  Integer P_TEMP_SL_NO) {

        String ret = null;
        ret =
(String)callStoredFunction2(VARCHAR, "FIN.FN_SAVE_COST_CENTER_TEMPLATE(?,?,?,?,?,?,?)", new Object[] { P_TEMP_CLD_ID,
                                                                                                       P_TEMP_SLOC_ID,
                                                                                                       P_TEMP_HO_ORG_ID,
                                                                                                       P_TEMP_ORG_ID,
                                                                                                       P_TEMP_DOC_ID,
                                                                                                       P_TEMP_ID,
                                                                                                       P_TEMP_SL_NO });

        return ret;
    }


    public String setToTmplCancelCC(Integer P_TEMP_SLOC_ID, String P_TEMP_CLD_ID, String P_TEMP_HO_ORG_ID,
                                    String P_TEMP_ORG_ID, Integer P_TEMP_DOC_ID, String P_TEMP_ID,
                                    Integer P_TEMP_SL_NO, String Mode) {
        String ret = null;
        ret =
(String)callStoredFunction2(VARCHAR, "FIN.FN_CANCEL_COST_CENTER_TEMPLATE(?,?,?,?,?,?,?,?)", new Object[] { P_TEMP_CLD_ID,
                                                                                                           P_TEMP_SLOC_ID,
                                                                                                           P_TEMP_HO_ORG_ID,
                                                                                                           P_TEMP_ORG_ID,
                                                                                                           P_TEMP_DOC_ID,
                                                                                                           P_TEMP_ID,
                                                                                                           P_TEMP_SL_NO,
                                                                                                           Mode });

        return ret;
    }

    private Integer isCostCenterPresent(Integer P_SLOC_ID, String P_CLD_ID, String P_ORG_ID, String P_HO_ORG_ID) {
        Integer retVal =
            (Integer)callStoredFunction2(INTEGER, "FIN.FN_IS_COST_CENTER_PRESENT(?,?,?,?,?)", new Object[] { P_CLD_ID,
                                                                                                             P_SLOC_ID,
                                                                                                             P_HO_ORG_ID,
                                                                                                             P_ORG_ID,
                                                                                                             54 });


        return retVal;
    }

    public void deleteLineAction(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("Delete");
        createOpBAddress.execute();
        getAm().getTmplVouLine().executeQuery();
    }

    public void voutypeChange(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            chartofAC.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(chartofAC);
            chartofAC.validate(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(chartofAC);
        }
    }


    public void editButton(ActionEvent actionEvent) {
        Flag = "N";
        setModeFlag("E");
    }


    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
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

    public String backToSearchButton() {
        slno = 0;
        ViewObject tvouHdr = getAm().getTmplVou();
        Row currRow = tvouHdr.getCurrentRow();

        String tvouId = (String)currRow.getAttribute("TmplVouId");
        String ORG_ID = (String)currRow.getAttribute("TmplVouOrgId");
        setToTmplCancelCC(SlocId, CldId, HoOrgId, ORG_ID, 54, tvouId, cost_center_slno, "N");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        getAm().getDBTransaction().rollback();
        if (getModeFlag().equals("C")) {


            String tmplVouId = (String)currRow.getAttribute("TmplVouId");
            String p_flag = "D";

            /** Check if DocTxnId already generated. */
            Row[] rDocTxn = getAm().getLovVouId().getFilteredRows("DocTxnId", tmplVouId);

            if (rDocTxn.length == 0) {
                try {
                    callStoredFunction2(VARCHAR, "FIN.FN_DEL_PARTIAL_TMPL(?,?,?,?,?)",
                                        new Object[] { CldId, SlocId, HoOrgId, ORG_ID, tmplVouId });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        setModeFlag("");
        return "back";
    }

    public String cancelOcButton() {
        ocmode = "V";
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding it = bindings.findIteratorBinding("TmplVouOc1Iterator");
        for (int i = 0; i < it.getViewObject().getEstimatedRowCount(); i++) {
            Row row = it.getRowAtRangeIndex(i);
            if (row.getAttribute("TmplVouCoaId") == null || row.getAttribute("TmplVouOcAmtSp") == null ||
                row.getAttribute("TmplVouOcAmtTyp") == null) {
                row.remove();
            }
        }

        return "cancel";
    }

    public String costCenterSaveAction() {
        ViewObject tvouHdr = getAm().getTmplVou();
        Row currRow = tvouHdr.getCurrentRow();
        String tvouId = currRow.getAttribute("TmplVouId").toString();
        String ORG_ID = (String)currRow.getAttribute("TmplVouOrgId");


        String val = setToTmplSaveCC(SlocId, CldId, HoOrgId, ORG_ID, 54, tvouId, cost_center_slno);

        return val;

    }

    /***New Methods for checking Tax and Tds amount- Ashish KUmar 14-03-2013*/

    private Number asCr = new Number();
    private Number Sum = new Number(0);
    // -------------------------------------------------------------------------


    public Number getTaxBasicAmount() {
        setAs(new Number(0));
        ViewObject tmplVouVO = getAm().getTmplVou();

        ViewObject tmplVouTaxRuleVO = getAm().getTmplVouTaxRule();
        tmplVouTaxRuleVO.executeQuery();

        RowSetIterator rit = tmplVouTaxRuleVO.createRowSetIterator(null);

        if (rit.first() != null &&
            rit.first().getAttribute("TmplVouId").equals(tmplVouVO.getCurrentRow().getAttribute("TmplVouId"))) {
            as = (Number)(rit.first().getAttribute("TmplVouTaxableAmt"));
        }
        System.out.println("taxable amount in taxRule----------->" + as);
        rit.closeRowSetIterator();
        return as;
    }

    public Number getTaxAmount() {
        setSum(new Number(0));
        setAs(new Number(0));
        setAsCr(new Number(0));

        ViewObject tmplVouLineVO = getAm().getTmplVouLine();
        tmplVouLineVO.executeQuery();

        RowSetIterator rit = tmplVouLineVO.createRowSetIterator(null);

        /**Add debit taxable amount of TmplVou lines*/
        if (rit.first() != null && rit.first().getAttribute("TmplVouTxnTypTax").equals("Y") &&
            rit.first().getAttribute("TmplVouAmtTyp").equals("Dr")) {
            as = (Number)(rit.first().getAttribute("TmplVouAmt"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TmplVouTxnTypTax").equals("Y") &&
                lineRow.getAttribute("TmplVouAmtTyp").equals("Dr")) {
                as = as.add((Number)(lineRow.getAttribute("TmplVouAmt")));

            }
        }

        /**Add credit taxable amount of TmplVou  lines*/
        if (rit.first() != null && rit.first().getAttribute("TmplVouTxnTypTax").equals("Y") &&
            rit.first().getAttribute("TmplVouAmtTyp").equals("Cr")) {
            asCr = (Number)(rit.first().getAttribute("TmplVouAmt"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TmplVouTxnTypTax").equals("Y") &&
                lineRow.getAttribute("TmplVouAmtTyp").equals("Cr")) {
                asCr = asCr.add((Number)(lineRow.getAttribute("TmplVouAmt")));

            }
        }

        rit.closeRowSetIterator();
        /**Get taxable amount by getting difference of credit and debit amount*/
        if (as.compareTo(asCr) == 1) {
            setSum((Number)(as.minus(asCr)));
        } else if (as.compareTo(asCr) == -1) {

            setSum((Number)(asCr.minus(as)));
        }
        // System.out.println("taxable amount in TmplLine----------->" + Sum);
        return Sum;
    }

    /**Method to check whether TDS amount is changed or not- Ashish Kumar- 14-03-2013*/

    public Number getTdsAmount() {
        setSum(new Number(0));
        setAs(new Number(0));
        setAsCr(new Number(0));

        ViewObject v1 = getAm().getTmplVouLine();
        RowSetIterator rit = v1.createRowSetIterator(null);

        /**Add debit tds amount of Template lines*/
        if (rit.first() != null && rit.first().getAttribute("TmplVouTxnTypTds").equals("Y") &&
            rit.first().getAttribute("TmplVouAmtTyp").equals("Dr")) {
            as = (Number)(rit.first().getAttribute("TmplVouAmt"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TmplVouTxnTypTds").equals("Y") &&
                lineRow.getAttribute("TmplVouAmtTyp").equals("Dr")) {
                as = as.add((Number)(lineRow.getAttribute("TmplVouAmt")));
            }
        }

        /**Add credit tds amount of Template lines*/
        if (rit.first() != null && rit.first().getAttribute("TmplVouTxnTypTds").equals("Y") &&
            rit.first().getAttribute("TmplVouAmtTyp").equals("Cr")) {
            asCr = (Number)(rit.first().getAttribute("TmplVouAmt"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TmplVouTxnTypTds").equals("Y") &&
                lineRow.getAttribute("TmplVouAmtTyp").equals("Cr")) {
                asCr = asCr.add((Number)(lineRow.getAttribute("TmplVouAmt")));

            }
        }

        rit.closeRowSetIterator();
        /**Get tds amount by getting difference of credit and debit amount*/
        if (as.compareTo(asCr) == 1) {
            setSum((Number)(as.minus(asCr)));
        } else if (as.compareTo(asCr) == -1) {

            setSum((Number)(asCr.minus(as)));
        }

        return Sum;
    }


    /**Method to get taxable amount for tds validation added by Priyank Khare on 29/03/2013**/

    public Number getTdsBasicAmount() {
        setAs(new Number(0));
        ViewObject tmplVouVO = getAm().getTmplVou();

        /** Get  basic amount from TmplTdsRuleLine */

        ViewObject tmplVouTdsRuleVO = getAm().getTmplVouTdsRule();
        tmplVouTdsRuleVO.executeQuery();

        RowSetIterator rit = tmplVouTdsRuleVO.createRowSetIterator(null);

        if (rit.first() != null &&
            rit.first().getAttribute("TmplVouId").equals(tmplVouVO.getCurrentRow().getAttribute("TmplVouId"))) {
            as = (Number)(rit.first().getAttribute("TmplTaxableAmt"));
        }

        rit.closeRowSetIterator();
        return as;
    }

    public void save2Action(ActionEvent actionEvent) {
        HashMap map = new HashMap();
        getAm().getTmplVouLine().executeQuery();
        ViewObjectImpl tmpllineVo = getAm().getTmplVouLine();
        ViewObject tmplVou = getAm().getTmplVou();
        Row vouHD = tmplVou.getCurrentRow();
        FacesContext fc = FacesContext.getCurrentInstance();

        checkTaxTds();
        /** If Taxable Amount is changed and Tax is not re-processed then following code will show error before commit */

        if (this.getTaxYN() == true &&
            (this.getTaxAmount().compareTo(this.getTaxBasicAmount()) == 1 || this.getTaxAmount().compareTo(this.getTaxBasicAmount()) ==
             -1)) {

            /** if not matched an error message.*/
            FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.345']}"));
            fc.addMessage(null, msg);
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLink);
        } else if (this.getTdsYN() == true &&
                   (this.getTdsAmount().compareTo(this.getTdsBasicAmount()) == 1 || this.getTdsAmount().compareTo(this.getTdsBasicAmount()) ==
                    -1)) {
            FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.346']}"));
            fc.addMessage(null, msg);
        } else {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (this.getTaxYN() == true && taxTyp == "reTx") {
                   getAm().taxConsolidation();
                   }
            if (this.getTdsYN() == true && tdsTyp == "reTds") {
                 getAm().tdsConsolidation();
            }
            operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            generateDocNo();
            getAm().getDBTransaction().commit();

            getAm().getLovVouId().executeQuery();
            TmplVouVORowImpl row = (TmplVouVORowImpl)getAm().getTmplVou().getCurrentRow();

            row.getLovVouIdVO().executeQuery();

            AdfFacesContext.getCurrentInstance().addPartialTarget(vouId);
            slno = 0;
            /**Function call for opposite line entry for bank and cash payment and recipt.*/
            if (vouHD.getAttribute("TmplVouTypeId").equals(2) || vouHD.getAttribute("TmplVouTypeId").equals(3) ||
                vouHD.getAttribute("TmplVouTypeId").equals(4) || vouHD.getAttribute("TmplVouTypeId").equals(5) ||
                vouHD.getAttribute("TmplVouTypeId").equals(6)) {
                getAm().tvouLineOp();
            }

            getAm().getTmplVouLine().executeQuery();
            String vouNo = getAm().getTmplVou().getCurrentRow().getAttribute("TmplVouId").toString();

            /***To get voucher id of voucher*/
            ViewObject voui = getAm().getLovVouId();
            voui.setNamedWhereClauseParam("BindSlcId", SlocId);
            voui.setNamedWhereClauseParam("BindCldId", CldId);
            voui.setNamedWhereClauseParam("BindHoOrgID", HoOrgId);
            voui.setNamedWhereClauseParam("BindDocId", 54);
            voui.executeQuery();
            Row[] ro = voui.getFilteredRows("DocTxnId", tmplVou.getCurrentRow().getAttribute("TmplVouId").toString());


            /***To save Cost Center if any*/
            costCenterSaveAction();

            try {
                if (ro.length > 0) {
                    docDisplayId = ro[0].getAttribute("DocTxnIdDisp").toString();
                    System.out.println(" is ========DocTxnIdDisp after click on save button====" + docDisplayId);
                }
            } catch (NullPointerException np) {
                docDisplayId = "";
            }
            OperationBinding operationBinding1 = bindings.getOperationBinding("Commit");
            operationBinding1.execute();
            RowSetIterator lineitr = tmpllineVo.createRowSetIterator(null);
            while (lineitr.hasNext()) {
                Row rw = lineitr.next();
                Object TmplVouCurrIdSp = rw.getAttribute("TmplVouCurrIdSp");
                map.put(TmplVouCurrIdSp, TmplVouCurrIdSp);
            }
            if (operationBinding1.getErrors().isEmpty()) {
                try {

                    StringBuilder saveMsg =
                        new StringBuilder("<html><body><p><b> " + resolvEl("#{bundle['LBL.1375']}") + " " +
                                          docDisplayId + " " + resolvEl("#{bundle['MSG.347']}") + "</b></p>");
                    if (map.size() == 1) {
                        saveMsg.append("<ol><li> " + resolvEl("#{bundle['LBL.932']}") + "-<ul><li>" +
                                       resolvEl("#{bundle['LBL.1040']}") + ":<b>" +
                                       getTotalCredit().round(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}"))) +
                                       "</b></li><li>" + resolvEl("#{bundle['LBL.1041']}") + ": <b>" +
                                       getTotalDebit().round(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}"))) +
                                       "</b></li></ul></li>");
                    }
                    saveMsg.append("<li> " + resolvEl("#{bundle['LBL.1166']}") + "-<ul><li>" +
                                   resolvEl("#{bundle['LBL.1040']}") + ": <b>" +
                                   getTotalBsCredit().round(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}"))) +
                                   "</b></li><li> " + resolvEl("#{bundle['LBL.1041']}") + ": <b>" +
                                   getTotalBsDebit().round(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}"))) +
                                   "</b></li></ul></li></ol>");
                    saveMsg.append("</body></html>");
                    FacesMessage message = new FacesMessage(saveMsg.toString());
                    //  message.setSeverity(FacesMessage.SEVERITY_INFO);

                    fc.addMessage(null, message);

                } catch (NullPointerException e) {

                    StringBuilder saveMsg =
                        new StringBuilder("<html><body><p><b>" + resolvEl("#{bundle['LBL.1375']}") + " " + vouNo +
                                          " " + resolvEl("#{bundle['MSG.347']}") + "</b></p>");
                    saveMsg.append("<ol><li>  " + resolvEl("#{bundle['LBL.932']}") + "-<ul><li>" +
                                   resolvEl("#{bundle['LBL.1040']}") + ": <b>" +
                                   getTotalCredit().round(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}"))) +
                                   "</b></li><li> " + resolvEl("#{bundle['LBL.1041']}") + ": <b>" +
                                   getTotalDebit().round(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}"))) +
                                   "</b></li></ul></li>");
                    saveMsg.append("<li> " + resolvEl("#{bundle['LBL.1166']}") + "-<ul><li>" +
                                   resolvEl("#{bundle['LBL.1040']}") + ": <b>" +
                                   getTotalBsCredit().round(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}"))) +
                                   "</b></li><li>" + resolvEl("#{bundle['LBL.1041']}") + ": <b>" +
                                   getTotalBsDebit().round(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}"))) +
                                   "</b></li></ul></li></ol>");
                    saveMsg.append("</body></html>");
                    FacesMessage message = new FacesMessage(saveMsg.toString());
                    // message.setSeverity(FacesMessage.SEVERITY_INFO);

                    fc.addMessage(null, message);
                    e.printStackTrace();
                }

            }
            Flag = "N";
            setModeFlag("V");
            taxTyp = "A";
            tdsTyp = "A";
        }
    }


    public String getBackOnTaxDisable() {
        ViewObject ruleLine = getAm().getTmplVouTaxRuleLine();
        ruleLine.executeQuery();
        int i = ruleLine.getRowCount();

        ViewObject rule = getAm().getTmplVouTaxRule();
        int j = rule.getRowCount();


        String s = "Y";

        if (i > 0) {
            s = "N";
        }
        return s;
    }

    public void setTaxbasicamount(Number taxbasicamount) {
        this.taxbasicamount = taxbasicamount;
    }

    public Number getTaxbasicamount() {
        setAs(new Number(0));
        ViewObject TmplVouVO = getAm().getTmplVou();


        ViewObject tmplVouTaxRuleVO = getAm().getTmplVouTaxRuleLine();
        tmplVouTaxRuleVO.executeQuery();
        RowSetIterator rit = tmplVouTaxRuleVO.createRowSetIterator(null);


        if (rit.first() != null &&
            rit.first().getAttribute("TmplVouId").equals(TmplVouVO.getCurrentRow().getAttribute("TmplVouId")) &&
            Integer.parseInt(rit.first().getAttribute("TmplVouTaxRulePriority").toString()) == 0) {
            as = (Number)(rit.first().getAttribute("TmplVouTaxRlTaxableAmtSp"));
        }

        return as;

    }


    public Number getTotalBsCredit() {

        setAs(new Number(0));
        ViewObject v1 = getAm().getTmplVouLine();
        RowSetIterator rit = v1.createRowSetIterator(null);

        // Number i = new Number(0);
        if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp") != null) {
            if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp").equals("Cr")) {
                as = (Number)(rit.first().getAttribute("TmplVouAmt"));

            }
            while (rit.hasNext()) {
                Row lineRow = rit.next();
                if (lineRow.getAttribute("TmplVouAmt") != null && lineRow.getAttribute("TmplVouAmtTyp") != null) {
                    if (lineRow.getAttribute("TmplVouAmtTyp").equals("Cr")) {
                        try {
                            as = as.add((Number)(lineRow.getAttribute("TmplVouAmt")));
                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    }
                }
            }
        }
        rit.closeRowSetIterator();
        return as;

    }

    public Number getTotalSpAmtCredit() {

        setAs(new Number(0));
        TmplVouAMImpl am = getAm();
        ViewObject tmplVouLine = am.getTmplVouLine();
        RowSetIterator rit = tmplVouLine.createRowSetIterator(null);

        // Number i = new Number(0);
        if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp") != null) {
            if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp").equals("Cr")) {
                as = (Number)(rit.first().getAttribute("TmplVouAmtSp"));

            }
            while (rit.hasNext()) {
                Row lineRow = rit.next();
                if (lineRow.getAttribute("TmplVouAmtSp") != null && lineRow.getAttribute("TmplVouAmtTyp") != null) {
                    if (lineRow.getAttribute("TmplVouAmtTyp").equals("Cr")) {
                        try {
                            as = as.add((Number)(lineRow.getAttribute("TmplVouAmtSp")));
                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    }
                }
            }
        }
        rit.closeRowSetIterator();
        return as;

    }


    public Number getTotalBsDebit() {

        setAs(new Number(0));
        ViewObject v1 = getAm().getTmplVouLine();
        RowSetIterator rit = v1.createRowSetIterator(null);
        if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp") != null) {
            if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp").equals("Dr")) {
                as = (Number)(rit.first().getAttribute("TmplVouAmt"));

            }
            while (rit.hasNext()) {
                Row lineRow = rit.next();
                if (lineRow.getAttribute("TmplVouAmtTyp") != null && lineRow.getAttribute("TmplVouAmt") != null) {
                    if (lineRow.getAttribute("TmplVouAmtTyp").equals("Dr")) {
                        as = as.add((Number)(lineRow.getAttribute("TmplVouAmt")));

                    }
                }
            }
        }
        rit.closeRowSetIterator();
        return as;

    }

    public Number getTotalSpAmtDebit() {

        setAs(new Number(0));
        TmplVouAMImpl am = getAm();
        ViewObject tmplVouLineVO = am.getTmplVouLine();
        RowSetIterator rit = tmplVouLineVO.createRowSetIterator(null);
        if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp") != null) {
            if (rit.first() != null && rit.first().getAttribute("TmplVouAmtTyp").equals("Dr")) {
                as = (Number)(rit.first().getAttribute("TmplVouAmtSp"));

            }
            while (rit.hasNext()) {
                Row lineRow = rit.next();
                if (lineRow.getAttribute("TmplVouAmtTyp") != null && lineRow.getAttribute("TmplVouAmtSp") != null) {
                    if (lineRow.getAttribute("TmplVouAmtTyp").equals("Dr")) {
                        as = as.add((Number)(lineRow.getAttribute("TmplVouAmtSp")));

                    }
                }
            }
        }
        rit.closeRowSetIterator();
        return as;

    }

    public void checkTaxTds() {

        /** Create object of TmplLines */
        ViewObject tmplLine = getAm().getTmplVouLine();
        RowSetIterator rowIterator = tmplLine.createRowSetIterator(null);
        int taxLines = 0;
        int tdsLines = 0;
        if (getTaxYN() == false) {

            /** Run loop for all rows of tvou lines if check box for tax is not checked */
            while (rowIterator.hasNext()) {
                Row currVouLne = rowIterator.next();
                if (currVouLne.getAttribute("TmplVouLnTyp").equals("DTAX")) {
                    /** Check for row of tax entry and delete it*/
                    taxLines += 1;

                    currVouLne.remove();
                }
            }
            tmplLine.executeQuery();
            if (taxLines > 0) {
                /**if a row for tax entry is present then delete rows from tax tables. */
                taxDelete();
            }
        }
        if (getTdsYN() == false) {
            /** Run loop for all rows of tvou lines if check box for tds is not checked */
            while (rowIterator.hasNext()) {
                Row currVouLne = rowIterator.next();
                if (currVouLne.getAttribute("TmplVouLnTyp").equals("DTDS")) {
                    /** Check for row of tds entry and delete it */
                    tdsLines += 1;
                    currVouLne.remove();
                }
            }
            tmplLine.executeQuery();

            if (tdsLines > 0) {
                /**if a row for tdsentry is present then delete rows from tds tables. */
                tdsDelete();
            }
        }
    }

    public void taxDelete() {
        String vouId = getAm().getTmplVou().getCurrentRow().getAttribute("TmplVouId").toString();
        /** Create object for tax rule view*/
        ViewObject tvouTax = getAm().getTmplVouTaxRule();
        /** Create object for tax rule line view*/
        ViewObject tvouTaxLine = getAm().getTmplVouTaxRuleLine();

        tvouTaxLine.setWhereClause("Tmpl_vou_id = '" + vouId + "'");
        tvouTaxLine.executeQuery();
        RowSetIterator rti = tvouTaxLine.createRowSetIterator(null);
        while (rti.hasNext()) {

            rti.next().remove();
        }
        tvouTaxLine.executeQuery();
        tvouTaxLine.setWhereClause(null);
        tvouTaxLine.executeQuery();

        tvouTax.setWhereClause("Tmpl_vou_id ='" + vouId + "'");
        tvouTax.executeQuery();
        RowSetIterator rit = tvouTax.createRowSetIterator(null);
        while (rit.hasNext()) {
            rit.next().remove();

        }
        tvouTax.executeQuery();
        tvouTax.setWhereClause(null);
        tvouTax.executeQuery();


    }

    public void tdsDelete() {

        String vouId = getAm().getTmplVou().getCurrentRow().getAttribute("TmplVouId").toString();
        /** Create object for tds rule view*/
        ViewObject tvouTds = getAm().getTmplVouTdsRule();
        /** Create object for tds rule line view*/
        ViewObject tvouTdsLine = getAm().getTmplVouTdsRuleLine();

        tvouTdsLine.setWhereClause("tmpl_vou_id ='" + vouId + "'");
        tvouTdsLine.executeQuery();
        RowSetIterator rit = tvouTdsLine.createRowSetIterator(null);
        System.out.println("rowcount for tdsLine" + rit.getRowCountInRange());
        while (rit.hasNext()) {
            rit.next().remove();
            System.out.println("Removing TDS line with Vou id" + vouId);
        }
        tvouTdsLine.executeQuery();
        tvouTdsLine.setWhereClause(null);
        tvouTdsLine.executeQuery();

        tvouTds.setWhereClause("tmpl_vou_id ='" + vouId + "'");
        tvouTds.executeQuery();
        RowSetIterator rti = tvouTds.createRowSetIterator(null);
        System.out.println("rowcount for tdsRule" + rti.getRowCountInRange());
        while (rti.hasNext()) {
            rti.next().remove();
            System.out.println("Removing TDS rule with Vou id" + vouId);
        }
        tvouTds.executeQuery();
        tvouTds.setWhereClause(null);
        tvouTds.executeQuery();
    }


    public void setModeFlag(String modeFlag) {
        this.modeFlag = modeFlag;
    }

    public String getModeFlag() {
        if (modeFlag.equals("")) {

            return resolvEl("#{pageFlowScope.callIngFormMode}");

        } else {
            return modeFlag;
        }
    }

    public void templateNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String quotId = object.toString().trim();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;

            char[] xx = quotId.toCharArray();

            for (char c : xx) {

                if (c == '(') {

                    openB = openB + 1;

                } else if (c == ')') {

                    closeB = closeB + 1;

                }

                if (closeB > openB) {
                    closeFg =
                            true; /**1.no. of closed brackets will not be more than open brackets at any given time.*/
                }
            }

            /**2.if openB=0 then no. of closing and opening brackets equal ||
                   * 3.opening bracket must always come before closing brackets
                   * 4.closing brackets must not come before first occurrence of openning bracket
                   */
            if (openB != closeB || closeFg == true || (quotId.lastIndexOf("(") > quotId.lastIndexOf(")")) ||
                (quotId.indexOf(")") < quotId.indexOf("("))) {
                String msg2 = resolvEl("#{bundle['MSG.7']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            /**5.check for empty brackets
                    */
            if (quotId.contains("()")) {
                String msg2 = resolvEl("#{bundle['MSG.49']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**check for two dot not comes together
                    */
            /* if(quotId.contains("..")){
                               String msg2="Consecutive dots .. value not allowed together.";
                               FacesMessage message2 = new FacesMessage(msg2);
                               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                               throw new ValidatorException(message2);
                       }  */
            /**check for two dot not comes together
                    */
            /*  if(quotId.contains("--")){
                               String msg2="Consecutive hyphens -- value not allowed together.";
                               FacesMessage message2 = new FacesMessage(msg2);
                               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                               throw new ValidatorException(message2);
                       }  */
            /**  check for wrong combo
                    */
            if (quotId.contains("(.") || quotId.contains("(-") || quotId.contains("-)")) {
                String msg2 = resolvEl("#{bundle['MSG.59']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            openB = 0;
            closeB = 0;
            closeFg = false;


            /**check for valid cornm .. Starts with character. can have dots .No two dots can be adjacent.
                        *
                        */

            //check for valid country name.. Starts with character. can have dots .No two dots can be adjacent.
            String expression =
                "^(?:(?>[A-Za-z0-9 )]+)(?:\\_|\\-|\\(|\\.|\\)|\\:|\\#|\\/|\\\\(?!\\_|\\-|\\:|\\#|\\/|\\\\|$))?)*$";
            CharSequence inputStr = quotId;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvEl("#{bundle['MSG.199']}");

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
            Object Result =
                callStoredFunction2(Types.VARCHAR, "fin.FN_CHK_TMPL_NAME_UNQE(?,?,?,?,?,?)", new Object[] { CldId,
                                                                                                            SlocId,
                                                                                                            HoOrgId,
                                                                                                            quotId,
                                                                                                            UsrId,
                                                                                                            "T" });
            String error1 = resolvEl("#{bundle['MSG.375']}");
            System.out.println("RESULT OF UNIQUE  = " + Result);
            if (Result != null) {
                if (Result.toString().equalsIgnoreCase("N")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error1, null));
                }
            }
        }
    }

    public void specificAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number specificAmt = new Number(0);

        try {

            specificAmt = (Number)object;

        } catch (Exception e) {

            e.printStackTrace();
        }
        if (specificAmt.getValue() < 0) {
            String msg = resolvEl("#{bundle['MSG.349']}");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        } else if (isPrecisionValid(26, 6, specificAmt) == false) {
            String msg = resolvEl("#{bundle['MSG.57']}");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);

        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void setCheckBoxFlag(Boolean checkBoxFlag) {
        this.checkBoxFlag = checkBoxFlag;
    }

    public Boolean getCheckBoxFlag() {
        if (amtSp.getValue() != null) {
            if (((Number)amtSp.getValue()).compareTo(0) == 0) {
                // System.out.println("inside getCheckBoxFlag---->"+(Number)amtSp.getValue());
                checkBoxFlag = true;
                getAm().getTmplVouLine().getCurrentRow().setAttribute("TmplVouTxnTypTax", "N");
                getAm().getTmplVouLine().getCurrentRow().setAttribute("TmplVouTxnTypTds", "N");
            } else {
                checkBoxFlag = false;
            }
        } else {

            checkBoxFlag = false;
        }

        return checkBoxFlag;
    }

    public void ocSpecificAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number specificAmt = new Number(0);

        try {
            specificAmt = (Number)object;
        } catch (Exception e) {
            specificAmt = (Number)ocAmtSp.getValue();
            e.printStackTrace();
        }
        if (specificAmt.getValue() < 0) {
            String msg = resolvEl("#{bundle['MSG.348']}");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        } else if (isPrecisionValid(26, 6, specificAmt) == false) {
            String msg = resolvEl("#{bundle['MSG.57']}");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }


    public String createOtherCharges() {
        ocmode = "A";
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        ViewObject vo = getAm().getTmplVouOc1();
        Integer Srno = 0;
        Integer max = 0;

        Row row[] = vo.getAllRowsInRange();
        for (Row r : row) {
            try {
                Srno = Integer.parseInt(r.getAttribute("TmplVouOcSlNo").toString());
            } catch (NullPointerException e) {
                Srno = 0;
            }

            if (Srno > max) {
                max = Srno;
            }

        }

        max = max + 1;
        Row crow = vo.getCurrentRow();
        crow.setAttribute("TmplVouOcSlNo", max);

        return null;
    }

    public Integer getMinLineNo() {

        Integer tempNo = 1;
        RowSetIterator rit = getAm().getTmplVouLine().createRowSetIterator(null);

        if (rit.first() != null) {
            Row frstRow = rit.first();
            tempNo = Integer.parseInt(frstRow.getAttribute("TmplVouSlNo").toString());
        }

        while (rit.hasNext()) {
            rit.next();
            Row r = rit.getCurrentRow();

            Integer slNo = Integer.parseInt(r.getAttribute("TmplVouSlNo").toString());
            //  System.out.println("slno----->" + slNo);
            if (slNo < tempNo) {

                tempNo = slNo;
            }
        }
        rit.closeRowSetIterator();
        //   System.out.println("minLineNo----->" + tempNo);
        return tempNo;
    }

    public static Object resolveElExp(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }

    public void coaValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(uIComponent);
        ViewObject tmplVouVO = getAm().getTmplVou();
        Row tmplVouRow = tmplVouVO.getCurrentRow();

        if (object != null) {
            String coaName = object.toString();
            System.out.println("coaName iin bean is =" + coaName);
            BindingContainer bc = getBindings();
            OperationBinding op = bc.getOperationBinding("coaNameValidator");
            op.getParamsMap().put("coaName", coaName);
            op.execute();
            Integer result = (Integer)op.getResult();
            System.out.println("result =" + result);
            if (result > 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Duplicate record found-->" + coaName, null));

            }

        }
        Number basic = new Number(0);
        if (amtSp.getValue() != null) {
            basic = (Number)amtSp.getValue();
        }

        Integer coaType = null;
        Integer vouType = null;
        Integer lineNo = null;
        String coaBehaviour = null;
        Integer minLineNo = getMinLineNo();

        if (getAm().getTmplVouLine().getCurrentRow().getAttribute("CoaTypeTrans") != null) {
            coaType =
                    Integer.parseInt(getAm().getTmplVouLine().getCurrentRow().getAttribute("CoaTypeTrans").toString());
            setCoaType(coaType);
            System.out.println("coaType in coaValidator---->" + coaType);
        }
        if (resolveElExp("#{bindings.TmplVouTypeId.inputValue}") != null) {
            vouType = Integer.parseInt(resolveElExp("#{bindings.TmplVouTypeId.inputValue}").toString());
            System.out.println("vouType in coaValidator---->" + vouType);
        }
        if (getAm().getTmplVouLine().getCurrentRow().getAttribute("TmplVouSlNo") != null) {
            lineNo = Integer.parseInt(getAm().getTmplVouLine().getCurrentRow().getAttribute("TmplVouSlNo").toString());
            System.out.println("lineNo in coaValidator---->" + lineNo);
        }
        if (getAm().getTmplVouLine().getCurrentRow().getAttribute("CredrOrDebtr") != null) {

            coaBehaviour = getAm().getTmplVouLine().getCurrentRow().getAttribute("CredrOrDebtr").toString();

        }

        if (object == null && basic.compareTo(0) == 1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.508']}").toString(), null));
        } else if (object != null) {
            // To get the account type of COA in validation.
            String coaNm = object.toString();
            ViewObjectImpl coavo = getAm().getLovCoaForEditInLineItem1();
            coavo.setNamedWhereClauseParam("BindHoOrgId", tmplVouRow.getAttribute("TmplHoOrgId"));
            coavo.setNamedWhereClauseParam("BindCldId", tmplVouRow.getAttribute("TmplCldId"));
            coavo.setNamedWhereClauseParam("BindSlocId", tmplVouRow.getAttribute("TmplVouSlocId"));
            coavo.executeQuery();
            Row[] xx = coavo.getFilteredRows("CoaNm", coaNm);
            System.out.println("number of rows in in lovcoa1 is===>>>>" + xx.length);
            if (xx.length > 0) {
                coaType = Integer.parseInt(xx[0].getAttribute("AccType").toString());

                if (xx[0].getAttribute("EoBhav") != null) {

                    coaBehaviour = xx[0].getAttribute("EoBhav").toString();
                    System.out.println("coaBehaviour " + coaBehaviour);

                } else {
                    coaBehaviour = "N";
                }
            }
        }

        /** Added on 09/09/2013 by Priyank Khare
          * Validation for  each voucher as per their type id in ascending order.
         * **/

        if (coaType != null && vouType != null && lineNo != null && coaBehaviour != null) {
            if (vouType.equals(1)) {
                System.out.println("validation voucher 1");

                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Journal Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.587']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                }

            }
            if (vouType.equals(9) || vouType.equals(11)) {
                if ((lineNo.equals(minLineNo)) && (!coaBehaviour.equals("D"))) {
                    String msg = " Only Debtor Account is allowed for first line item.";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                } else if ((!lineNo.equals(minLineNo)) && (coaBehaviour.equals("D"))) {
                    String msg = " Debtor Account is not allowed for the line item.";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }

            else if (vouType.equals(10) || vouType.equals(12)) {
                if ((lineNo.equals(minLineNo)) && (!coaBehaviour.equals("C"))) {
                    String msg = " Only Creditor Account is allowed for first line item.";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                } else if ((!lineNo.equals(minLineNo)) && (coaBehaviour.equals("C"))) {
                    String msg = "Creditor Account is not allowed for line item.";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            } else if (vouType.equals(7)) {
                System.out.println("voucherrrrrrrrr type in sales voucngr is=== sales voucher");
                if (coaType.equals(78) || coaType.equals(79)) {
                    String msg = "Bank or Cash account are not allowed for Sales Voucher.";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
                if (!lineNo.equals(minLineNo)) {
                    Integer CrCount = 0;
                    Integer DrCount = 0;
                    ViewObjectImpl vo = getAm().getTmplVouLine();
                    RowSetIterator itr = vo.createRowSetIterator(null);
                    if (getAm().getTmplVouLine().getCurrentRow().getAttribute("CredrOrDebtr") == null) {
                        if (coaBehaviour.equals("C"))
                            CrCount = CrCount + 1;
                        else if (coaBehaviour.equals("D"))
                            DrCount = DrCount + 1;
                    }
                    while (itr.hasNext()) {
                        Row rw = itr.next();
                        if (rw.getAttribute("CredrOrDebtr") != null) {
                            if (rw.getAttribute("CredrOrDebtr").equals("C"))
                                CrCount = CrCount + 1;
                            if (rw.getAttribute("CredrOrDebtr").equals("D"))
                                DrCount = DrCount + 1;
                        }
                    }
                    if (DrCount > 1) {
                        getAm().getTmplVouLine().getCurrentRow().setAttribute("TmplVouCoaId", null);
                        String msg = "Only one Debtor  account is allowed for Sales Voucher.";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }

                }
            } else if (vouType.equals(8)) {
                System.out.println("coa id is===>>>>>" +
                                   getAm().getTmplVouLine().getCurrentRow().getAttribute("TmplVouCoaId"));
                System.out.println("<<<<<<<<coa behaviour is====>>>>>>" + coaBehaviour);
                if (coaType.equals(78) || coaType.equals(79)) {
                    String msg = "Bank or Cash account are not allowed for Purchase Voucher.";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
                if (!lineNo.equals(minLineNo)) {
                    Integer CrCount = 0;
                    Integer DrCount = 0;
                    ViewObjectImpl vo = getAm().getTmplVouLine();
                    Row currrow = vo.getCurrentRow();
                    RowSetIterator itr = vo.createRowSetIterator(null);
                    if (getAm().getTmplVouLine().getCurrentRow().getAttribute("CredrOrDebtr") == null) {
                        if (coaBehaviour.equals("C"))
                            CrCount = CrCount + 1;
                        else if (coaBehaviour.equals("D"))
                            DrCount = DrCount + 1;
                    }
                    while (itr.hasNext()) {
                        Row rw = itr.next();
                        if (rw.getAttribute("CredrOrDebtr") != null) {
                            if (rw.getAttribute("CredrOrDebtr").equals("C"))
                                CrCount = CrCount + 1;
                            if (rw.getAttribute("CredrOrDebtr").equals("D"))
                                DrCount = DrCount + 1;
                        }
                    }
                    itr.closeRowSetIterator();
                    if (CrCount > 1) {
                        getAm().getTmplVouLine().getCurrentRow().setAttribute("TmplVouCoaId", null);
                        String msg = "Only one Creditor  account is allowed for Sales Voucher.";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }

                }
            }

            else if (vouType.equals(4) || vouType.equals(2) || vouType.equals(3) || vouType.equals(5)) {
                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Cash Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.596']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
                RowSetIterator rit = getAm().getTmplVouLine().createRowSetIterator(null);
                Integer countDebtor = 0;
                Integer countCreditor = 0;

                if (getAm().getTmplVouLine().getCurrentRow().getAttribute("CoaTypeTrans") == null) {

                    if (coaBehaviour.equals("C")) {

                        countCreditor = countCreditor + 1;

                    } else if (coaBehaviour.equals("D")) {

                        countDebtor = countDebtor + 1;
                    }
                    System.out.println("After Model Level Validation where  countCreditor = " + countCreditor +
                                       " countDebtor = " + countDebtor);
                }
                /*  if (rit.first() != null && rit.first().getAttribute("CredrOrDebtr") != null) {

                    if (rit.first().getAttribute("CredrOrDebtr").equals("C")) {

                        countCreditor = countCreditor + 1;
                    } else if (rit.first().getAttribute("CredrOrDebtr").equals("D")) {

                        countDebtor = countDebtor + 1;
                    }
                    System.out.println("After First row  Validation where  countCreditor = "+countCreditor+" countDebtor = "+countDebtor);

                }   */

                while (rit.hasNext()) {

                    Row lineRow = rit.next();
                    if (lineRow.getAttribute("CredrOrDebtr") != null) {

                        if (lineRow.getAttribute("CredrOrDebtr").equals("C")) {

                            countCreditor = countCreditor + 1;
                        } else if (lineRow.getAttribute("CredrOrDebtr").equals("D")) {

                            countDebtor = countDebtor + 1;
                        }
                    }
                    System.out.println("After Iterator Validation where  countCreditor = " + countCreditor +
                                       " countDebtor = " + countDebtor);

                }
                rit.closeRowSetIterator();

                System.out.println("countDebtor " + countDebtor + " countCreditor " + countCreditor);

                if (countCreditor > 0 && countDebtor > 0) {
                    //Either Creditor or Debtor account is allowed for Cash Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1121']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                } else if (countCreditor > 1) {
                    //Only one Creditor account is allowed for Cash Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1122']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                } else if (countDebtor > 1) {
                    //Only one Debtor  account is allowed for Cash Payment Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1123']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }


            }
        } else {
            //Failed to Validate Chart Of Account. Contact ESS for Support
            String msg1 = resolvElDCMsg("#{bundle['MSG.1132']}").toString();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(uIComponent);

        /*  else if (vouType.equals(8)) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(uIComponent);

                if (coaType.equals(78) || coaType.equals(79)) {
                    //Bank or Cash account are not allowed for Purchase Bill.
                    String msg = resolvElDCMsg("#{bundle['MSG.602']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }

                else if (!lineNo.equals(minLineNo)) {

                    RowSetIterator rit = getAm().getTmplVouLine().createRowSetIterator(null);
                    Integer countDebtor = 0;
                    Integer countCreditor = 0;

                    if (getAm().getTmplVouLine().getCurrentRow().getAttribute("CredrOrDebtr") == null) {

                        if (coaBehaviour.equals("C")) {

                            countCreditor = countCreditor + 1;

                        } else if (coaBehaviour.equals("D")) {

                            countDebtor = countDebtor + 1;
                        }
                    }


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

                    if (countCreditor > 1) {
                        //Only one Creditor account is allowed for Purchase Voucher.
                        String msg = resolvElDCMsg("#{bundle['MSG.696']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }

                }

            } else if (vouType.equals(13)) {
                System.out.println("validation voucher 13");
                if (coaType.equals(78) || coaType.equals(79) || coaType.equals(5022) || coaBehaviour.equals("C") ||
                    coaBehaviour.equals("D")) {
                    //Bank, Cash, TAX/TDS, Debitor/Creditor accounts are not allowed for Provisional Voucher.
                    String msg = resolvElDCMsg("#{bundle['MSG.1129']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }

            }  */


        // System.out.println("<-----------validation ends here------->");


    }


    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }


    public void coaValueChangeListener(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ViewObject tmplVouVO = getAm().getTmplVou();
        Row tmplVouRow = tmplVouVO.getCurrentRow();
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtSp);
        String coaName = vce.getNewValue().toString();
        Integer coaId = null;
        //  System.out.println("In value change Listener");
        TmplVouAMImpl am = (TmplVouAMImpl)resolvElDC("TmplVouAMDataControl");
        ViewObjectImpl v = am.getTmplVouLine();
        Row currRow = v.getCurrentRow();
        if (vce.getNewValue() != null) {
            ViewObjectImpl coavo = am.getLovCoa1();
            coavo.setNamedWhereClauseParam("BindOrgId", tmplVouRow.getAttribute("TmplVouOrgId"));
            coavo.setNamedWhereClauseParam("BindHoOrgId", HoOrgId);
            coavo.setNamedWhereClauseParam("BindCldId", CldId);
            coavo.setNamedWhereClauseParam("BindSlocId", SlocId);
            coavo.executeQuery();

            Row[] xx = coavo.getFilteredRows("CoaNm", coaName);

            if (xx.length > 0) {
                coaId = (Integer)xx[0].getAttribute("CoaId");
                //System.out.println("coaid in vallue change listeenr of caon nam is ==>>>>>" + coaId);
                v.getCurrentRow().setAttribute("TmplVouCoaId", coaId);

                /** Get value for the chart of account whether it is a creditor or debtor*/
                String crOrDr =
                    getCrorDrValue(CldId, SlocId, HoOrgId, currRow.getAttribute("TmplVouOrgId").toString(), coaId);
                /** Set transient attribute "CredrOrDebtr" with return   value of DB function call */
                currRow.setAttribute("CredrOrDebtr", crOrDr);
            }

            if (v.getRowCount() > 0) {

                if (((Number)(currRow.getAttribute("TmplVouAmtSp"))).compareTo(new Number(0)) == 0) {
                    setAmtYN(true);

                } else {
                    setAmtYN(false);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(amtSp);
                AdfFacesContext.getCurrentInstance().addPartialTarget(txdtl);
                AdfFacesContext.getCurrentInstance().addPartialTarget(tdsDtl);
            }

        }
    }

    public String getCrorDrValue(String cldId, Integer slocId, String hoOrgId, String orgId, Integer coaId) {
        String value =
            (String)callStoredFunction3(VARCHAR, "FIN.is_coa_debtor_creditor_org(?,?,?,?,?)", new Object[] { cldId,
                                                                                                             slocId,
                                                                                                             hoOrgId,
                                                                                                             orgId,
                                                                                                             coaId });
        System.out.println("value return by the getCrorDrValue is===>>>>>" + value);
        return value;
    }

    /** Function to call database function.
     * @param sqlReturnType
     * @param stmt
     * @param bindVars
     * @return
     */
    protected Object callStoredFunction3(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        TmplVouAMImpl am = (TmplVouAMImpl)resolvElDC("TmplVouAMDataControl");
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


    public void setAmtYN(Boolean amtYN) {
        this.amtYN = amtYN;
    }

    public Boolean getAmtYN() {
        /**Check whether specific amount in 0 or not*/
        TmplVouAMImpl am = (TmplVouAMImpl)resolvElDC("TempVoucherAMDataControl");
        ViewObject v1 = am.getTmplVouLine();
        //  System.out.println("RowCount is-->"+v1.getRowCount());
        if (v1.getRowCount() > 0) {
            Row rownew = v1.getCurrentRow();
            if (((Number)(rownew.getAttribute("TmplVouAmtSp"))).compareTo(new Number(0)) == 0) {

                return true;
            } else

                return false;
        }
        return true;
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


    public void setTaxMode(String TaxMode) {
        this.TaxMode = TaxMode;
    }

    public String getTaxMode() {
        return TaxMode;
    }

    public TmplVouAMImpl getAm() {
        TmplVouAMImpl am = (TmplVouAMImpl)resolvElDC("TmplVouAMDataControl");
        return am;
    }

    public void setAmtSp(RichInputText amtSp) {
        this.amtSp = amtSp;
    }

    public RichInputText getAmtSp() {
        return amtSp;
    }

    public void setAmtBs(RichInputText amtBs) {
        this.amtBs = amtBs;
    }

    public RichInputText getAmtBs() {
        return amtBs;
    }

    public void setVouId(RichSelectOneChoice vouId) {
        this.vouId = vouId;
    }

    public RichSelectOneChoice getVouId() {
        return vouId;
    }

    public void setNarrPopUp(RichPopup narrPopUp) {
        this.narrPopUp = narrPopUp;
    }

    public RichPopup getNarrPopUp() {
        return narrPopUp;
    }

    public void setChartofAC(RichSelectOneChoice chartofAC) {
        this.chartofAC = chartofAC;
    }

    public RichSelectOneChoice getChartofAC() {
        return chartofAC;
    }

    public void setLineTable(RichTable lineTable) {
        this.lineTable = lineTable;
    }

    public RichTable getLineTable() {
        return lineTable;
    }

    /*  public void setBackButtonOnTax(RichCommandButton backButtonOnTax) {
        this.backButtonOnTax = backButtonOnTax;
    }

    public RichCommandButton getBackButtonOnTax() {
        return backButtonOnTax;
    } */


    public void setBackOnTaxDisable(String backOnTaxDisable) {
        this.backOnTaxDisable = backOnTaxDisable;
    }

    public void setFlag(String Flag) {
        this.Flag = Flag;
    }

    public String getFlag() {
        return Flag;
    }

    public String getName() {
        return null;
    }

    public void release() {
    }

    public Object getDataProvider() {
        return null;
    }

    public boolean invokeOperation(Map p0, OperationBinding p1) {
        return false;
    }

    public void beginRequest(HashMap p0) {
    }

    public void endRequest(HashMap p0) {
    }

    public boolean resetState() {
        return false;
    }

    public void setCost_center_src(String cost_center_src) {
        System.out.println("cost_center_src in setter =" + cost_center_src);
        this.cost_center_src = cost_center_src;
    }

    public String getCost_center_src() {
        System.out.println("cost_center_src in getter =" + cost_center_src);

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

    public void setCostCenterPopup(RichPopup costCenterPopup) {
        this.costCenterPopup = costCenterPopup;
    }

    public RichPopup getCostCenterPopup() {
        return costCenterPopup;
    }

    public void setTaxRuleLink(RichCommandLink taxRuleLink) {
        this.taxRuleLink = taxRuleLink;
    }

    public RichCommandLink getTaxRuleLink() {
        return taxRuleLink;
    }

    public void setAsCr(Number asCr) {
        this.asCr = asCr;
    }

    public Number getAsCr() {
        return asCr;
    }

    public void setSum(Number Sum) {
        this.Sum = Sum;
    }

    public void setDocDisplayId(String docDisplayId) {
        this.docDisplayId = docDisplayId;
    }

    public String getDocDisplayId() {
        return docDisplayId;
    }

    public void testEvent(ActionEvent actionEvent) {

    }

    public void setOcAmtSp(RichInputText ocAmtSp) {
        this.ocAmtSp = ocAmtSp;
    }

    public RichInputText getOcAmtSp() {
        return ocAmtSp;
    }

    public void setCurrRate(RichOutputText currRate) {
        this.currRate = currRate;
    }

    public RichOutputText getCurrRate() {
        return currRate;
    }

    public void setCurrIdSP(RichInputListOfValues currIdSP) {
        this.currIdSP = currIdSP;
    }

    public RichInputListOfValues getCurrIdSP() {
        return currIdSP;
    }

    public void setTotalSpCredit(Number totalSpCredit) {
        this.totalSpCredit = totalSpCredit;
    }

    public Number getTotalSpCredit() {
        totalSpCredit = getTotalSpAmtCredit();
        return totalSpCredit;
    }

    public void setTotalSpDebit(Number totalSpDebit) {
        this.totalSpDebit = totalSpDebit;
    }

    public Number getTotalSpDebit() {
        totalSpDebit = getTotalSpAmtDebit();
        return totalSpDebit;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCoaType(Integer coaType) {
        this.coaType = coaType;
    }

    public Integer getCoaType() {
        return coaType;
    }

    public String deleteTemplateAction() {
        TmplVouVOImpl tmpl = getAm().getTmplVou();
        if (tmpl != null) {
            Row currentRow = tmpl.getCurrentRow();
            if (currentRow != null) {
                Object TmplVouOrgId = currentRow.getAttribute("TmplVouOrgId");
                Object TmplVouId = currentRow.getAttribute("TmplVouId");
                Object delete =
                    callStoredFunction2(Types.VARCHAR, "fn_del_template(?,?,?,?,?)", new Object[] { CldId, SlocId,
                                                                                                    HoOrgId,
                                                                                                    TmplVouOrgId.toString(),
                                                                                                    TmplVouId.toString() });
                if (delete != null) {
                    if (delete.toString().equalsIgnoreCase("Y")) {
                        //String msg = resolvEl("Record Deleted Successfuly");
                        getAm().getDBTransaction().commit();
                        FacesMessage Message = new FacesMessage(resolvEl("Record Deleted Successfuly"));
                        Message.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, Message);
                        return "back";
                    } else {
                        //   String msg = resolvEl("Unable to delete Record. Child Record found");
                        FacesMessage Message =
                            new FacesMessage(resolvEl("Unable to delete Record. Child Record found"));
                        Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, Message);
                        return null;
                    }
                }

            }
        }


        return null;
    }

    public void ocDeleteAction(ActionEvent actionEvent) {
        ocmode = "D";
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        operationBinding.execute();
        operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        if (getAm().getTmplVouOc1().getEstimatedRowCount() == 0) {
            ocmodeflag = "true";
        }

    }

    public void setOcmode(String ocmode) {
        this.ocmode = ocmode;
    }

    public String getOcmode() {
        return ocmode;
    }

    public String ocBackAction() {
        ocmode = "V";
        ocmodeflag = "false";
        return "Back";
    }

    public String ocLinkAction() {
        ocmode = "V";
        return "oc";
    }

    public void setAmtTypBind(RichSelectOneChoice amtTypBind) {
        this.amtTypBind = amtTypBind;
    }

    public RichSelectOneChoice getAmtTypBind() {
        return amtTypBind;
    }

    public void templateNameVCL(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            String name = valueChangeEvent.getNewValue().toString().toUpperCase();
            templateNameBinding.setValue(name);
        }
    }

    public void setTemplateNameBinding(RichInputText templateNameBinding) {
        this.templateNameBinding = templateNameBinding;
    }

    public RichInputText getTemplateNameBinding() {
        return templateNameBinding;
    }

    public void setOcmodeflag(String ocmodeflag) {
        this.ocmodeflag = ocmodeflag;
    }

    public String getOcmodeflag() {
        return ocmodeflag;
    }

    public void amtTypeVCl(ValueChangeEvent valueChangeEvent) {
        ViewObject tmplLine = getAm().getTmplVouLine();
        RowSetIterator rowIterator = tmplLine.createRowSetIterator(null);
        int taxLines = 0;
        int tdsLines = 0;
        if (valueChangeEvent != null) {
            while (rowIterator.hasNext()) {
                Row currVouLne = rowIterator.next();
                if (currVouLne.getAttribute("TmplVouLnTyp").equals("DTAX")) {
                    taxLines += 1;
                }
                if (currVouLne.getAttribute("TmplVouLnTyp").equals("DTDS")) {
                    tdsLines += 1;
                }
            }
            if (taxLines > 0) {
                taxTyp = "reTx";
            }
            if (tdsLines > 0) {
                tdsTyp = "reTds";
            }


        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lineTable);
    }
}


