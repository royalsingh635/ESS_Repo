package glApp.view.bean;

import glApp.model.module.GlAppAMImpl;
import glApp.model.view.GlLinesVORowImpl;
import glApp.model.view.GlVORowImpl;

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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeContext;
import oracle.binding.BindingContainer;
import oracle.binding.ManagedDataControl;
import oracle.binding.OperationBinding;
import oracle.binding.RowContext;
import oracle.binding.TransactionalDataControl;
import oracle.binding.UpdateableDataControl;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowInconsistentException;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class GlAddBean implements TransactionalDataControl, UpdateableDataControl, ManagedDataControl {
    private Integer count;
    private String org_id = "01";
    private String cld_id = "0000";
    private Integer sloc_id = 1;
    private RichPopup ccPopUp;
    private RichPopup narrpopup;
    private RichCommandLink saveAsTemplateLink;
    private RichInputText amtSp;
    private RichInputText amtBs;
    private Boolean taxYN;
    private Boolean amtYN;
    private static int VARCHAR = Types.VARCHAR;
    private static int BOOLEAN = Types.BOOLEAN;
    private Boolean tdsYN;
    private RichPopup ccDtlPopup;
    private Number totalCredit;
    private Number totalDebit;
    private Number glAmount;
    private Boolean adjustCrDrNote;
    private RichSelectOneChoice chartofAC;
    private RichInputText amount;
    private Boolean form = false;
    private Boolean table = true;
    private Integer docId = 56;
    private Integer DocEntType = 5055;
    private String docDisplayId;
    private RichInputText chequeNo;
    private RichInputText chqBukNo;
    private Integer ChqBkNo;
    private RichInputText totalAmount;
    GlAppAMImpl am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
    private String editMode = "true";
    private String editButton = "false";
    private String addButton = "false";
    private RichCommandImageLink rowTable;
    private String disableTaxLink = "true";
    private String disableTdsLink = "true";
    private RichCommandLink selTaxRuleLnk;
    private RichCommandLink selTdsRuleLnk;
    private String mode = "V";
    private RichTable glLineTable;
    private RichSelectOneChoice vouIdPage;
    private RichSelectBooleanCheckbox advance;
    private String wfMode;
    private String wfComment;
    private Integer wfNextUser;
    private Integer wfNextLvl;
    private Integer wfId;
    private String wfFlag;
    private RichSelectBooleanCheckbox applyTaxChkBind;
    private RichSelectBooleanCheckbox applyTdsChkBind;
    private RichSelectBooleanCheckbox adjustmentChkBind;
    private String contentStyleGreen = "font-weight:bold;color:green;";
    private String contentStyleBlack = "font-weight:bold;color:black;";
    private RichSelectOneChoice instrmntTypBind;
    private RichCommandButton editButtonLines;
    private String cost_center_src = "H";
    private Integer cost_center_slno = 0;
    private BigDecimal cost_center_amount = new BigDecimal(0);
    private RichPopup costCenterPopup;
    private RichCommandLink cclinkBind;

    public GlAddBean() {
    }

    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }

    public void createNew(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("Createwithparameters");
        createOpBAddress.execute();

    }

    public void glHdLineOp() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("glLineOp");
        operationBinding.execute();
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void saveAction() {

        System.out.println("Inside Save Action---->");

        FacesContext fc = FacesContext.getCurrentInstance();
        //  am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject gl = am.getGl1();
        ViewObject glLine = am.getGlLines1();

        ViewObject voui = am.getLovVouId1();
        Row[] ro = voui.getFilteredRows("DocTxnId", gl.getCurrentRow().getAttribute("GlVouId").toString());
        voui.executeQuery();


        Row r = gl.getCurrentRow();
        GlVORowImpl r1 = (GlVORowImpl)gl.getCurrentRow();

        /**Function call for opposite line entry for bank and cash payment and recipt.*/
        if (r.getAttribute("GlTypeId").equals(2) || r.getAttribute("GlTypeId").equals(3) ||
            r.getAttribute("GlTypeId").equals(4) || r.getAttribute("GlTypeId").equals(5) ||
            r.getAttribute("GlTypeId").equals(6)) {
            System.out.println("Inserting opposite line in Temporary Voucher------->>>For Bank or Cash type Voucher-----");
            glHdLineOp();
        }
        try {
            if (ro.length > 0) {

                docDisplayId = ro[0].getAttribute("DocTxnIdDisp").toString();
            }
        } catch (NullPointerException np) {
            docDisplayId = "";
        }
        voui.setWhereClause(null);
        voui.executeQuery();

        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();

        checkTaxTds();
        /**To check that other charges lines deleted from Oc table, if deleted then delete from GL Lines also 4-03-2013*/
        // checkOtherCharge();
        System.out.println("Inside save action-----Tax amount---" + getTaxAmount() + "--Basic Amount---->" +
                           getTaxBasicAmount());
        System.out.println("Inside save action-----Tds amount---" + getTdsAmount() + "--Basic TDS Amount---->" +
                           getTdsBasicAmount());
        if (this.getTaxYN() == true &&
            (this.getTaxAmount().compareTo(this.getTaxBasicAmount()) == 1 || this.getTaxAmount().compareTo(this.getTaxBasicAmount()) ==
             -1)) {
            /** if not matched an error message.*/
            FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.496']}").toString());
            fc.addMessage(null, msg);
        }
        /**check change in taxable amount in voucher and amount on which tds is applied. method "getTdsAmount()" get the amount on which tds has been applied.*/
        else if (this.getTdsYN() == true &&
                 (this.getTdsAmount().compareTo(this.getTdsBasicAmount()) == 1 || this.getTdsAmount().compareTo(this.getTdsBasicAmount()) ==
                  -1)) {
            FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.497']}").toString());
            fc.addMessage(null, msg);
        }

        /**If tax and tds amount are ok then proceed to save this voucher.
         first check whether this is on account or not, if tax, tds, other charges  or adjustment is done then it is not on account and change txnType to 'L' its default value is 'O'*/
        else {
            System.out.println("Inside Final else------");
            RowSetIterator rit = glLine.createRowSetIterator(null);
            while (rit.hasNext()) {
                Row curLn = rit.next();
                System.out.println(curLn.getAttribute("GlTxnTyp"));
                if (!curLn.getAttribute("GlTxnTyp").equals("L")) {
                    if (curLn.getAttribute("GlTxnTypTax").equals("Y") ||
                        curLn.getAttribute("GlTxnTypTds").equals("Y") ||
                        curLn.getAttribute("GlTxnTypOc").equals("Y") ||
                        curLn.getAttribute("GlTxnTypAdj").equals("Y") ||
                        curLn.getAttribute("GlTxnTypBc").equals("Y")) {
                        curLn.setAttribute("GlTxnTyp", "L");
                    } else {
                        curLn.setAttribute("GlTxnTyp", "O");
                    }
                }
            }
            rit.closeRowSetIterator();
            try {
                glLine.executeQuery();
                OperationBinding createOpB = getBindings().getOperationBinding("Commit");
                createOpB.execute();
                //am.getDBTransaction().commit();
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
            glLine.executeQuery();
            /**Function call for opposite line entry for bank and cash payment and recipt.*/
            if (r.getAttribute("GlTypeId").equals(2) || r.getAttribute("GlTypeId").equals(3) ||
                r.getAttribute("GlTypeId").equals(4) || r.getAttribute("GlTypeId").equals(5) ||
                r.getAttribute("GlTypeId").equals(6)) {
                System.out.println("Inserting opposite line in Temporary Voucher------->>>For Bank or Cash type Voucher-----");
                glHdLineOp();
            }

            Number tc = getTotalCredit();
            Number td = getTotalDebit();
            System.out.println("Total Debit is---->" + td + "And total Credit is----->" + tc);
            if (!(tc.compareTo(td) == 0)) {
                System.out.println("Debit credit mismatch for this voucher---");
                editMode = "false";
                addButton = "false";
                if (mode.equalsIgnoreCase("E")) {
                    mode = "E";
                    editButton = "true";
                } else if (mode.equalsIgnoreCase("A")) {
                    mode = "A";
                }
                StringBuilder mismatchMsg =
                    new StringBuilder("<html><body><p><b>" + resolvElDCMsg("#{bundle['MSG.434']}").toString() + " " +
                                      docDisplayId + "</b></p>");
                mismatchMsg.append("<ul><li>" + resolvElDCMsg("#{bundle['MSG.435']}").toString() + " :<b>" + tc +
                                   "</b></li><li>" + resolvElDCMsg("#{bundle['MSG.437']}").toString() + " :<b>" + td +
                                   "</b></li></ul>");
                mismatchMsg.append("</body></html>");
                FacesMessage message = new FacesMessage(mismatchMsg.toString());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                fc.addMessage(null, message);
            } else {

                System.out.println("Inside else");

                try {
                    glLine.executeQuery();
                    OperationBinding createOpB = getBindings().getOperationBinding("Commit");
                    createOpB.execute();
                    System.out.println("------>Commited now----");
                    //  createOpBAddress.execute();
                } catch (RowInconsistentException e) {
                    System.out.println(e);
                    System.out.println("Before Commit in Exception");
                    OperationBinding createOpB = getBindings().getOperationBinding("Commit");
                    createOpB.execute();
                }


                if (!am.getDBTransaction().isDirty()) {
                    OperationBinding createOpB = getBindings().getOperationBinding("Commit");
                    createOpB.execute();

                    Number xCr = getTotalCredit();
                    Number xDr = getTotalDebit();
                    /***Function call to save costcenter 29-03-2013 @Ashish Kumar*/
                    costCenterSaveAction();
                    StringBuilder saveMsg =
                        new StringBuilder("<html><body><p><b>" + resolvElDCMsg("#{bundle['MSG.439']}").toString() +
                                          " " + docDisplayId + " " + resolvElDCMsg("#{bundle['MSG.347']}").toString() +
                                          "</b></p>");
                    saveMsg.append("<ul><li>" + resolvElDCMsg("#{bundle['MSG.435']}").toString() + " :<b>" + xCr +
                                   "</b></li><li>" + resolvElDCMsg("#{bundle['MSG.437']}").toString() + " :<b>" + xDr +
                                   "</b></li></ul>");
                    saveMsg.append("</body></html>");
                    FacesMessage message = new FacesMessage(saveMsg.toString());
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    fc.addMessage(null, message);


                    editMode = "true";
                    editButton = "false";
                    addButton = "false";
                    mode = "V";
                    AdfFacesContext.getCurrentInstance().addPartialTarget(selTaxRuleLnk);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(selTdsRuleLnk);
                }

            }


            OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute");
            createOpBAddress1.execute();
            OperationBinding createOpBAddress2 = bindings.getOperationBinding("Execute1");
            createOpBAddress2.execute();
            OperationBinding createOpBAddress3 = bindings.getOperationBinding("Execute2");
            createOpBAddress3.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(selTaxRuleLnk);
            AdfFacesContext.getCurrentInstance().addPartialTarget(selTdsRuleLnk);
            AdfFacesContext.getCurrentInstance().addPartialTarget(glLineTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(editButtonLines);

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(glLineTable);
    }

    public void saveButton(ActionEvent actionEvent) {
        saveAction();

    }

    public void setCcPopUp(RichPopup ccPopUp) {
        this.ccPopUp = ccPopUp;
    }

    public RichPopup getCcPopUp() {
        return ccPopUp;
    }

    public void setNarrpopup(RichPopup narrpopup) {
        this.narrpopup = narrpopup;
    }

    public RichPopup getNarrpopup() {
        return narrpopup;
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

    public void narrLink(ActionEvent actionEvent) {
        showPopup(narrpopup, true);
    }

    private Integer isCostCenterPresent(Integer P_SLOC_ID, String P_CLD_ID, String P_ORG_ID, String P_HO_ORG_ID) {
        Integer retVal =
            (Integer)callStoredFunction2(Types.INTEGER, "FIN.FN_IS_COST_CENTER_PRESENT(?,?,?,?,?)", new Object[] { P_CLD_ID,
                                                                                                                   P_SLOC_ID,
                                                                                                                   P_HO_ORG_ID,
                                                                                                                   P_ORG_ID,
                                                                                                                   56 });
        return retVal;
    }

    public void costCenterLink(ActionEvent actionEvent) {
        showPopup(ccPopUp, true);
        /* am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObjectImpl v = am.getGl1();
        Row curr = v.getCurrentRow();
        Integer P_SLOC_ID = (Integer)curr.getAttribute("GlSlocId");
        String P_CLD_ID = (String)curr.getAttribute("GlCldId");
        String P_ORG_ID = (String)curr.getAttribute("GlOrgId");
        String P_HO_ORG_ID = (String)curr.getAttribute("GlHoOrgId");
        Integer val = isCostCenterPresent(P_SLOC_ID, P_CLD_ID, P_ORG_ID, P_HO_ORG_ID);

        if (val > 1) {

            FacesMessage Message =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.441']}").toString());
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, Message);
        } else if (val == 0) {

            FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.443']}").toString());
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, Message);
        } else {

        cost_center_slno = 0;
        cost_center_amount = new BigDecimal(0);
        cost_center_src = "H";
        showPopup(ccPopUp, true);
        }   */
    }

    public void saveAsTempValueChange(ValueChangeEvent valueChangeEvent) {
        String s = valueChangeEvent.getNewValue().toString();
        if (s.equalsIgnoreCase("true")) {
            saveAsTemplateLink.setDisabled(false);
        } else {
            saveAsTemplateLink.setDisabled(true);
        }
    }

    public void setSaveAsTemplateLink(RichCommandLink saveAsTemplateLink) {
        this.saveAsTemplateLink = saveAsTemplateLink;
    }

    public RichCommandLink getSaveAsTemplateLink() {
        return saveAsTemplateLink;
    }

    public void valueChangeListener(ValueChangeEvent valueChangeEvent) {

        //  GlAppAMImpl am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObjectImpl v = am.getGlLines1();
        GlLinesVORowImpl r1 = (GlLinesVORowImpl)v.getCurrentRow();
        if (valueChangeEvent.getNewValue() != null) {

            Integer currId = Integer.parseInt(valueChangeEvent.getNewValue().toString());
            if (r1.getRate(currId) != null) {
                System.out.println(currId + "<-----CurrId");

                Number rate = r1.getRate(currId);
                System.out.println(rate + "<---Rate");

                Number basic = (Number)getAmtSp().getValue();
                Number result = basic.multiply(rate);
                System.out.println(result + "<-----Result");
                amtBs.setValue(result);
                r1.reload();

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

    public void amtSpValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
            ViewObjectImpl v = am.getGlLines1();
            Row r1 = v.getCurrentRow();
            if (r1.getAttribute("GlCurrIdSp") != null) {
                Integer curr = Integer.parseInt(r1.getAttribute("GlCurrIdSp").toString());
                GlLinesVORowImpl r2 = (GlLinesVORowImpl)v.getCurrentRow();

                if (r2.getRate(curr) != null) {
                    Number rate = r2.getRate(curr);

                    Number basic = (Number)valueChangeEvent.getNewValue();
                    Number result = basic.multiply(rate);
                    amtBs.setValue(result);
                    r2.reload();
                }
            }
        }
    }


    public void setTaxYN(Boolean taxYN) {
        this.taxYN = taxYN;
    }

    public Boolean getTaxYN() {
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject v1 = am.getGlLines1();
        RowSetIterator rit = v1.createRowSetIterator(null);

        Integer i = 0;

        if (rit.first() != null && rit.first().getAttribute("GlTxnTypTax").equals("Y")) {

            i = 1;

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("GlTxnTypTax").equals("Y")) {
                i = i + 1;

            }
        }
        System.out.println("Tax applied in lines---->:" + i);
        rit.closeRowSetIterator();
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
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject v1 = am.getGlLines1();
        RowSetIterator rit = v1.createRowSetIterator(null);

        Integer i = 0;
        if (rit.first() != null && rit.first().getAttribute("GlTxnTypTds").equals("Y")) {
            i = 1;

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("GlTxnTypTds").equals("Y")) {
                i = i + 1;

            }
        }
        System.out.println("Tds applied in Lines--->" + i);
        rit.closeRowSetIterator();
        if (i > 0)
            return true;
        else
            return false;
    }

    public void glTypValueChangeList(ValueChangeEvent valueChangeEvent) {
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObjectImpl v = am.getGl1();
        GlVORowImpl r1 = (GlVORowImpl)v.getCurrentRow();

        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 2 ||
            Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 3) {
            r1.setCoaLov(78);
        } else if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 4 ||
                   Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 5) {
            r1.setCoaLov(79);
        } else {
            r1.setCoaLov(0);
        }
        r1.reload();
    }

    public void setCcDtlPopup(RichPopup ccDtlPopup) {
        this.ccDtlPopup = ccDtlPopup;
    }

    public RichPopup getCcDtlPopup() {
        return ccDtlPopup;
    }

    public void dtlCostCenterLink(ActionEvent actionEvent) {

        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObjectImpl v = am.getGlLines1();
        Row curr = v.getCurrentRow();
        Integer P_SLOC_ID = (Integer)curr.getAttribute("GlSlocId");
        String P_CLD_ID = (String)curr.getAttribute("GlCldId");
        String P_ORG_ID = (String)curr.getAttribute("GlOrgId");
        String P_HO_ORG_ID = (String)curr.getAttribute("GlHoOrgId");
        Integer val = isCostCenterPresent(P_SLOC_ID, P_CLD_ID, P_ORG_ID, P_HO_ORG_ID);
        if (val > 1) {
            FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.441']}").toString());
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, Message);
        } else if (val == 0) {
            FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.443']}").toString());
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, Message);
        } else {
            cost_center_slno = (Integer)curr.getAttribute("GlSlNo");
            Number amount_temp = (Number)curr.getAttribute("GlAmtSp");

            cost_center_amount = amount_temp.bigDecimalValue();
            cost_center_src = "L";
            showPopup(ccPopUp, true);
        }
    }

    public String linktaxRule() {
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        System.out.println("Voucher Id----->" + am.getGl1().getCurrentRow().getAttribute("GlVouId"));
        int count = am.getGlTaxRule1().getRowCount();
        if (count == 0) {
            BindingContext bindingctx = BindingContext.getCurrent();
            BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
            OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert");
            createOpBAddress.execute();
        }
        return "tax";
    }

    public String linkTdsRule() {
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        int count = am.getGlTdsLine1().getRowCount();
        if (count == 0) {
            BindingContext bindingctx = BindingContext.getCurrent();
            BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
            OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert1");
            createOpBAddress.execute();
        }
        return "tds";
    }

    public void setTotalCredit(Number totalCredit) {
        this.totalCredit = totalCredit;
    }

    public Number getTotalCredit() {
        setAs(new Number(0));
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject v1 = am.getGlLines1();

        RowSetIterator rit = v1.createRowSetIterator(null);


        if (rit.first() != null && rit.first().getAttribute("GlAmtTyp") != null) {
            if (rit.first() != null && rit.first().getAttribute("GlAmtTyp").equals("Cr")) {
                as = (Number)(rit.first().getAttribute("GlAmtSp"));

            }
            while (rit.hasNext()) {
                Row lineRow = rit.next();
                if (lineRow.getAttribute("GlAmtSp") != null && lineRow.getAttribute("GlAmtTyp") != null) {
                    if (lineRow.getAttribute("GlAmtTyp").equals("Cr")) {
                        as = as.add((Number)(lineRow.getAttribute("GlAmtSp")));

                    }
                }
            }
        }
        rit.closeRowSetIterator();
        return as;
    }
    private static Number as = new Number();

    public void setAs(Number as) {
        this.as = as;
    }

    public Number getAs() {
        return as;
    }

    public void setTotalDebit(Number totalDebit) {
        this.totalDebit = totalDebit;
    }

    public Number getTotalDebit() {
        setAs(new Number(0));
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject v1 = am.getGlLines1();

        RowSetIterator rit = v1.createRowSetIterator(null);

        if (rit.first() != null && rit.first().getAttribute("GlAmtTyp") != null) {
            if (rit.first() != null && rit.first().getAttribute("GlAmtTyp").equals("Dr")) {
                as = (Number)(rit.first().getAttribute("GlAmtSp"));

            }
            while (rit.hasNext()) {
                Row lineRow = rit.next();
                if (lineRow.getAttribute("GlAmtTyp") != null && lineRow.getAttribute("GlAmtSp") != null) {
                    if (lineRow.getAttribute("GlAmtTyp").equals("Dr")) {
                        as = as.add((Number)(lineRow.getAttribute("GlAmtSp")));

                    }
                }
            }
        }
        rit.closeRowSetIterator();
        return as;
    }

    /**Method to get Serial Number for GlSlNo- 13-03-2013*/
    public void getSerialNo() {
        Integer Srno = 0;
        Integer max = 0;
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject vo = am.getGlLines1();
        int range = vo.getRangeSize();
        System.out.println("Default range is-->" + range);
        vo.setRangeSize(vo.getRowCount());
        Row row[] = vo.getAllRowsInRange();
        for (Row r : row) {
            try {
                Srno = (Integer)r.getAttribute("GlSlNo");
            } catch (NullPointerException e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
            System.out.println("BeanCount is--->" + Srno);
            if (Srno > max) {
                max = Srno;
            }
        }
        System.out.println("BeanCount is after loop---->" + max);
        max = max + 1;
        Row crow = vo.getCurrentRow();
        crow.setAttribute("GlSlNo", max);
        vo.setRangeSize(range);

    }

    public void addLineAction(ActionEvent actionEvent) {

        editMode = "false";
        editButton = "true";
        addButton = "true";
        mode = "A";
        AdfFacesContext.getCurrentInstance().addPartialTarget(selTaxRuleLnk);
        AdfFacesContext.getCurrentInstance().addPartialTarget(selTdsRuleLnk);
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert2");
        createOpBAddress.execute();
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject vouViw = am.getGl1();
        ViewObject vouLineViw = am.getGlLines1();
        Row vouHD = vouViw.getCurrentRow();
        Row vouLn = vouLineViw.getCurrentRow();
        //Set Max serial number to this line
        getSerialNo();
        System.out.println("Serial No of this line is-->" + vouLn.getAttribute("GlSlNo"));
        if (vouHD.getAttribute("GlSubTypeId") != null) {
            vouLn.setAttribute("GlSubTypeId", vouHD.getAttribute("GlSubTypeId"));
        }
        if (vouHD.getAttribute("GlCurrIdSp") != null) {
            vouLn.setAttribute("GlCurrIdSp", vouHD.getAttribute("GlCurrIdSp"));
            vouLn.setAttribute("GlCurrIdSp", vouHD.getAttribute("GlCurrIdSp"));
            System.out.println("CCTxnFlag is-->" + vouLn.getAttribute("GlCcTxnFlg"));
        }

        if (vouHD.getAttribute("GlOrgId") != null) {
            vouLn.setAttribute("GlOrgId", vouHD.getAttribute("GlOrgId"));
        }
        if (vouHD.getAttribute("GlDesc") != null) {
            vouLn.setAttribute("GlNarr", vouHD.getAttribute("GlDesc"));
        }
        System.out.println("Conversion rate from header is--->" + vouHD.getAttribute("GlCc"));
        if (vouHD.getAttribute("GlCc") != null) {
            vouLn.setAttribute("GlCc", vouHD.getAttribute("GlCc"));
        } else {
            GlLinesVORowImpl row = (GlLinesVORowImpl)vouLn;
            if (row.getGlCurrIdSp() != null) {
                Number rate = row.getRate(row.getGlCurrIdSp());
                System.out.println("Rate from addline is-->" + rate);
                vouLn.setAttribute("GlCc", rate);
            }
        }


        String orgId = vouLn.getAttribute("GlOrgId").toString();
        Integer CurrBs = Integer.parseInt(getCurrIdBs(orgId));
        vouLn.setAttribute("GlCurrIdBs", CurrBs);

        if (vouHD.getAttribute("GlTypeId").equals(1)) {
            GlLinesVORowImpl r1 = (GlLinesVORowImpl)vouLineViw.getCurrentRow();
            r1.setLovforJV();
        }

        if (vouHD.getAttribute("GlTypeId").equals(1) || vouHD.getAttribute("GlTypeId").equals(2) ||
            vouHD.getAttribute("GlTypeId").equals(4) || vouHD.getAttribute("GlTypeId").equals(6) ||
            vouHD.getAttribute("GlTypeId").equals(7) || vouHD.getAttribute("GlTypeId").equals(11) ||
            vouHD.getAttribute("GlTypeId").equals(12)) {
            vouLn.setAttribute("GlAmtTyp", "Cr");
        }
        if (vouHD.getAttribute("GlTypeId").equals(3) || vouHD.getAttribute("GlTypeId").equals(5) ||
            vouHD.getAttribute("GlTypeId").equals(8) || vouHD.getAttribute("GlTypeId").equals(9) ||
            vouHD.getAttribute("GlTypeId").equals(10)) {
            vouLn.setAttribute("GlAmtTyp", "Dr");
        } else {
            vouLn.setAttribute("GlAmtTyp", "Dr");
        }
    }

    public String getCurrIdBs(String OrgId) {
        return (String)callStoredFunction2(VARCHAR, "app.pkg_app.get_org_def_curr_bs1(?)", new Object[] { OrgId });
    }

    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        GlAppAMImpl am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
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


    public void setAdjustCrDrNote(Boolean adjustCrDrNote) {
        this.adjustCrDrNote = adjustCrDrNote;
    }

    public Boolean getAdjustCrDrNote() {
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject v1 = am.getGlLines1();
        Row LnRow = v1.getCurrentRow();
        if (Integer.parseInt(LnRow.getAttribute("GlTypeId").toString()) == 9 ||
            Integer.parseInt(LnRow.getAttribute("GlTypeId").toString()) == 10 ||
            Integer.parseInt(LnRow.getAttribute("GlTypeId").toString()) == 11 ||
            Integer.parseInt(LnRow.getAttribute("GlTypeId").toString()) == 12) {

            return true;

        } else {

            return false;
        }
    }

    public void deleteLine(ActionEvent actionEvent) {
        /* editMode = "true";
        addButton = "false";
        editButton = "false";
        mode = "V"; */
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("Delete");
        createOpBAddress.execute();
    }


    public void voutypeChange(ValueChangeEvent valueChangeEvent) {
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObjectImpl v = am.getGl1();
        GlVORowImpl r1 = (GlVORowImpl)v.getCurrentRow();

        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 2 ||
            Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 3 ||
            Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 6) {
            r1.setCoaLov(78);
            chartofAC.setRequired(true);
        } else if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 4 ||
                   Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 5) {
            r1.setCoaLov(79);
            chartofAC.setRequired(true);
        } else {
            r1.setCoaLov(0);
            chartofAC.setRequired(false);
        }

    }

    public void setChartofAC(RichSelectOneChoice chartofAC) {
        this.chartofAC = chartofAC;
    }

    public RichSelectOneChoice getChartofAC() {
        return chartofAC;
    }

    public void addChequeButton(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert");
        createOpBAddress.execute();
        Number amt = getTotalCredit();

        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject chqView = am.getGlLineInstrumnt();
        Row curRow = chqView.getCurrentRow();
        Row glLine = am.getGlLines1().getCurrentRow();
        curRow.setAttribute("GlCoaId", glLine.getAttribute("GlCoaId"));
        curRow.setAttribute("GlAmtSp", amt);
        amount.setValue(amt);
        curRow.setAttribute("GlInstrmntDt", curRow.getAttribute("GlVouDt"));
        /**New Code to get Serial No.- 6-03-2013-@Ashish Kumar*/
        Integer Srno = 0;
        Integer max = 0;

        Row row[] = chqView.getAllRowsInRange();
        for (Row r : row) {
            try {
                Srno = Integer.parseInt(r.getAttribute("GlChqSlNo").toString());
            } catch (NullPointerException e) {
                Srno = 0;

            }
            System.out.println("BeanCount is--->" + Srno);
            if (Srno > max) {
                max = Srno;
            }

        }
        System.out.println("BeanCount is after loop---->" + max);
        max = max + 1;

        curRow.setAttribute("GlChqSlNo", max);

        setTable(false);
        setForm(true);
        //As of now -Name will be written by user 11-03-2013
        /*   GlLineInstrumntVORowImpl chqRow = (GlLineInstrumntVORowImpl)chqView.getCurrentRow();
        String name = chqRow.getChqNm();
        curRow.setAttribute("GlInstrmntNm", name); */


    }

    public void setAmount(RichInputText amount) {
        this.amount = amount;
    }

    public RichInputText getAmount() {
        return amount;
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

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Integer getDocId() {
        return docId;
    }

    public void okButton(ActionEvent actionEvent) {
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject chqView = am.getGlLineInstrumnt();
        Row crRow = chqView.getCurrentRow();

        if ((Integer)crRow.getAttribute("GlInstrmntTyp") == 111) {

            OperationBinding validOp = getBindings().getOperationBinding("validateChqNo");
            validOp.execute();
            if (validOp.getErrors().isEmpty()) {
                OperationBinding createOpB = getBindings().getOperationBinding("updateChqNo");
                createOpB.execute();
                if (createOpB.getErrors().isEmpty()) {
                    setTable(true);
                    setForm(false);
                }
            }
        }
        if ((Integer)crRow.getAttribute("GlInstrmntTyp") == 112) {

            setTable(true);
            setForm(false);
        }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void cancelButtonOncheq(ActionEvent actionEvent) {
        OperationBinding createOpB = getBindings().getOperationBinding("Delete");
        createOpB.execute();
        OperationBinding createOp = getBindings().getOperationBinding("Execute");
        createOp.execute();
        /* setFillPolicy("B"); */
        setTable(true);
        setForm(false);
    }

    public void autoValuechange(ValueChangeEvent valueChangeEvent) {
        System.out.println(valueChangeEvent.getNewValue());
        String num = "0";
        if (valueChangeEvent.getNewValue().toString().equals("true")) {
            OperationBinding createOpB2 = getBindings().getOperationBinding("getLastChqNo");
            System.out.println("Get last cheque Number---->" + createOpB2.execute());
            if (createOpB2.execute() == (null)) {
                num = "0";
                System.out.println("Is null");
            } else {
                num = createOpB2.execute().toString();
            }


            int dot = num.indexOf(".");

            String chqBNo = num.substring(0, dot);
            String ChqNo = num.substring(dot + 1);
            System.out.println(chqBNo + " " + ChqNo);
            chequeNo.setValue(ChqNo);
            setChqBkNo(Integer.parseInt(chqBNo));
            chqBukNo.setValue(getChqBkNo());

            chequeNo.setReadOnly(true);
        }
        if (valueChangeEvent.getNewValue().toString().equals("false")) {
            chequeNo.setValue("");
            chequeNo.setReadOnly(false);
            chqBukNo.setValue("");
            //  chqBukNo.setVisible(false);
        }
    }

    public void setChequeNo(RichInputText chequeNo) {
        this.chequeNo = chequeNo;
    }

    public RichInputText getChequeNo() {
        return chequeNo;
    }

    public void setChqBukNo(RichInputText chqBukNo) {
        this.chqBukNo = chqBukNo;
    }

    public RichInputText getChqBukNo() {
        return chqBukNo;
    }

    public void setChqBkNo(Integer ChqBkNo) {
        this.ChqBkNo = ChqBkNo;
    }

    public Integer getChqBkNo() {
        return ChqBkNo;
    }

    public void setTotalAmount(RichInputText totalAmount) {
        this.totalAmount = totalAmount;
    }

    public RichInputText getTotalAmount() {
        return totalAmount;
    }

    public String backButton() {

        /**Commented by Priyank Khare on 16-06-2013
         * Functionality to add check is not required in General Ledger
         * check Details can be added from Interim Voucher**/
        /*  Number amt = getTotalCredit();
        System.out.println(amt);
        System.out.println(totalAmount.getValue());
        if (((Number)totalAmount.getValue()).compareTo(amt) == 0 ||
            ((Number)totalAmount.getValue()).compareTo(new Number(0)) == 0) { */
        /*   editMode = "true";
            addButton = "false";
            editButton = "false";
            disableTaxLink = "true";
            disableTdsLink = "true"; */

        /* OperationBinding createOpB1 = getBindings().getOperationBinding("Commit");
            createOpB1.execute();
            return "back";

        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("Cheque Amount is not equal to Voucher Amount " + amt);
            msg.setSeverity(msg.SEVERITY_ERROR);
            fc.addMessage(null, msg);
            return null; */
        return "back";
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

    public void editLineAction(ActionEvent actionEvent) {
        // this.disTaxLink();
        // this.disTdsLink();
        editMode = "false";
        editButton = "true";
        addButton = "true";
        mode = "E";
        AdfFacesContext.getCurrentInstance().addPartialTarget(selTaxRuleLnk);
        AdfFacesContext.getCurrentInstance().addPartialTarget(selTdsRuleLnk);
    }

    public void setEditMode(String editMode) {
        this.editMode = editMode;
    }

    public String getEditMode() {
        return editMode;
    }

    public void setEditButton(String editButton) {
        this.editButton = editButton;
    }

    public String getEditButton() {
        return editButton;
    }

    public void setAddButton(String addButton) {
        this.addButton = addButton;
    }

    public String getAddButton() {
        return addButton;
    }

    public String setToTmplSaveCC(Integer P_TEMP_SLOC_ID, String P_TEMP_CLD_ID, String P_TEMP_HO_ORG_ID,
                                  String P_TEMP_ORG_ID, Integer P_TEMP_DOC_ID, String P_TEMP_ID,
                                  Integer P_TEMP_SL_NO) {

        String ret = null;
        ret =
(String)callStoredFunction2(VARCHAR, "FIN.PKG_FIN_GL.FN_SAVE_COST_CENTER_GL(?,?,?,?,?,?,?)", new Object[] { P_TEMP_SLOC_ID,
                                                                                                            P_TEMP_CLD_ID,
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
(String)callStoredFunction2(VARCHAR, "FIN.PKG_FIN_GL.FN_CANCEL_COST_CENTER_GL(?,?,?,?,?,?,?,?)", new Object[] { P_TEMP_SLOC_ID,
                                                                                                                P_TEMP_CLD_ID,
                                                                                                                P_TEMP_HO_ORG_ID,
                                                                                                                P_TEMP_ORG_ID,
                                                                                                                P_TEMP_DOC_ID,
                                                                                                                P_TEMP_ID,
                                                                                                                P_TEMP_SL_NO,
                                                                                                                Mode });

        return ret;
    }

    public void RevertButton(ActionEvent actionEvent) {

        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject v1 = am.getGl1();
        Row currRow = v1.getCurrentRow();


        String tvouId = (String)currRow.getAttribute("GlVouId");

        Integer SLOC_ID = (Integer)currRow.getAttribute("GlSlocId");
        String CLD_ID = (String)currRow.getAttribute("GlCldId");
        String HO_ORG_ID = (String)currRow.getAttribute("GlHoOrgId");
        String ORG_ID = (String)currRow.getAttribute("GlOrgId");
        String val = setToTmplCancelCC(SLOC_ID, CLD_ID, HO_ORG_ID, ORG_ID, 54, tvouId, cost_center_slno, "N");
        System.out.println(val);
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("Rollback");
        createOpBAddress.execute();
        editButton = "false";
        addButton = "false";
        editMode = "true";
        mode = "V";
        AdfFacesContext.getCurrentInstance().addPartialTarget(selTaxRuleLnk);
        AdfFacesContext.getCurrentInstance().addPartialTarget(selTdsRuleLnk);
    }

    public void setRowTable(RichCommandImageLink rowTable) {
        this.rowTable = rowTable;
    }

    public RichCommandImageLink getRowTable() {
        return rowTable;
    }

    public String backToSearchAction() {
        editMode = "true";
        addButton = "false";
        editButton = "false";
        mode = "V";
        AdfFacesContext.getCurrentInstance().addPartialTarget(selTaxRuleLnk);
        AdfFacesContext.getCurrentInstance().addPartialTarget(selTdsRuleLnk);
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject v = am.getGlSearchView();


        /*  DCBindingContainer dc = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iterBind= (DCIteratorBinding)dc.get("GlSearchViewIterator");
        iterBind.executeQuery();
        iterBind.refresh(DCIteratorBinding.RANGESIZE_UNLIMITED);
         */
        v.setNamedWhereClauseParam("BindCldId", getCld_id());
        v.setNamedWhereClauseParam("BindSlocId", getSloc_id());
        v.setNamedWhereClauseParam("BindDispID", null);
        v.setNamedWhereClauseParam("BindOrgId", getOrg_id());
        v.setNamedWhereClauseParam("BindFromDate", null);
        v.setNamedWhereClauseParam("BindToDate", null);
        v.setNamedWhereClauseParam("BindVoutype", -1);
        v.setNamedWhereClauseParam("BindVouSubType", null);
        v.setNamedWhereClauseParam("BindCurrIdBs", null);
        v.setNamedWhereClauseParam("BindVouStat", null);
        v.setNamedWhereClauseParam("BindAmtFrom", null);
        v.setNamedWhereClauseParam("BindAmtTo", null);
        v.setNamedWhereClauseParam("BindCoaId", null);
        v.setNamedWhereClauseParam("BindCogId", null);
        v.setNamedWhereClauseParam("BindNaId", null);
        v.setNamedWhereClauseParam("BindEoId", null);
        v.setNamedWhereClauseParam("BindEoMstid", null);
        v.executeQuery();

        BindingContext bindingctx2 = BindingContext.getCurrent();
        BindingContainer bindings2 = bindingctx2.getCurrentBindingsEntry();
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("Rollback");
        createOpBAddress.execute();
        OperationBinding createOpBAddress2 = bindings2.getOperationBinding("Execute");
        createOpBAddress2.execute();

        // ResetUtils.reset(searchForm);


        ViewObject v1 = am.getLovVouId1();
        v1.setWhereClause(null);
        v1.executeQuery();
        return "back";
    }

    public String cancelOCTable() {
        /** If canceled then delete empty row */
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding it = bindings.findIteratorBinding("GlOc1Iterator");
        for (int i = 0; i < it.getViewObject().getEstimatedRowCount(); i++) {
            Row row = it.getRowAtRangeIndex(i);
            if (row.getAttribute("GlCoaId") == null || row.getAttribute("GlOcAmtSp") == null ||
                row.getAttribute("GlOcAmtTyp") == null) {
                row.remove();
            }
        }

        return "Cancel";

    }


    /*  public void disTaxLink(){
        GlAppAMImpl am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObjectImpl v = am.getGlLines1();
        Row[] r=v.getAllRowsInRange();
        int count=0;

       // for (int i = 0; i < it.getViewObject().getEstimatedRowCount(); i++) {
        for(Row row:r){
            //Row row = it.getRowAtRangeIndex(i);
            System.out.println("Tax in Roww==="+(row.getAttribute("GlTxnTypTax").toString()));
            if((row.getAttribute("GlTxnTypTax").toString()).equalsIgnoreCase("Y")){
               count=count+1;
            }
        }
        System.out.println("Count in disTaxLink:"+count);
        if(count>0){
        disableTaxLink="false";
        }
        else if(count==0){
            disableTaxLink="true";
        }
    } */

    public void setDisableTaxLink(String disableTaxLink) {

        this.disableTaxLink = disableTaxLink;
    }

    public String getDisableTaxLink() {
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObjectImpl v = am.getGlLines1();
        Row[] r = v.getAllRowsInRange();
        int count = 0;
        for (Row row : r) {
            //Row row = it.getRowAtRangeIndex(i);

            if ((row.getAttribute("GlTxnTypTax").toString()).equalsIgnoreCase("Y")) {
                count = count + 1;
            }
        }
        if (count > 0) {
            disableTaxLink = "false";
        } else if (count == 0) {
            disableTaxLink = "true";
        }
        return disableTaxLink;
    }

    /* public void disTdsLink(){
        GlAppAMImpl am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObjectImpl v = am.getGlLines1();
        Row[] r=v.getAllRowsInRange();
        int count=0;
        for(Row row:r){

        //for (int i = 0; i < it.getViewObject().getEstimatedRowCount(); i++) {
            //Row row = it.getRowAtRangeIndex(i);
            System.out.println("Tds in Roww==="+(row.getAttribute("GlTxnTypTds").toString()));
            if((row.getAttribute("GlTxnTypTds").toString()).equalsIgnoreCase("Y")){
               count=count+1;
            }
        }
        System.out.println("Count in disTdsLink:"+count);
        if(count>0){
        disableTdsLink="false";
        }
        else if(count==0){
            disableTdsLink="true";
        }
    } */

    public void setDisableTdsLink(String disableTdsLink) {
        this.disableTdsLink = disableTdsLink;
    }

    public String getDisableTdsLink() {
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObjectImpl v = am.getGlLines1();
        Row[] r = v.getAllRowsInRange();
        int count = 0;
        for (Row row : r) {

            //for (int i = 0; i < it.getViewObject().getEstimatedRowCount(); i++) {
            //Row row = it.getRowAtRangeIndex(i);

            if ((row.getAttribute("GlTxnTypTds").toString()).equalsIgnoreCase("Y")) {
                count = count + 1;
            }
        }

        if (count > 0) {
            disableTdsLink = "false";
        } else if (count == 0) {
            disableTdsLink = "true";
        }
        return disableTdsLink;
    }

    public void applyTaxVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(selTaxRuleLnk);

        String x = valueChangeEvent.getNewValue().toString();

        if (x.equalsIgnoreCase("true")) {
            selTaxRuleLnk.setDisabled(false);

        } else {
            /**Check for Tax and TDS lines-28-02-2013*/

            am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
            ViewObjectImpl v = am.getGlLines1();
            Row[] r = v.getAllRowsInRange();
            Row currR = v.getCurrentRow();
            int count = 0;

            for (Row row : r) {
                //Row row = it.getRowAtRangeIndex(i);
                if (row != currR) {

                    if ((row.getAttribute("GlTxnTypTax").toString()).equalsIgnoreCase("Y")) {
                        count = count + 1;
                    }
                }
            }

            if (count > 0) {
                selTaxRuleLnk.setDisabled(false);
            } else if (count == 0) {
                selTaxRuleLnk.setDisabled(true);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(selTaxRuleLnk);
    }

    public void applyTdsVCE(ValueChangeEvent valueChangeEvent) {


        String x = valueChangeEvent.getNewValue().toString();

        if (x.equalsIgnoreCase("true")) {
            selTdsRuleLnk.setDisabled(false);

        } else {

            am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
            ViewObjectImpl v = am.getGlLines1();
            Row[] r = v.getAllRowsInRange();
            Row currR = v.getCurrentRow();
            int count = 0;
            for (Row row : r) {
                /*  DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding it = bindings.findIteratorBinding("GlLines1Iterator");
             */
                if (row != currR) {
                    //for (int i = 0; i < it.getViewObject().getEstimatedRowCount(); i++) {
                    //Row row = it.getRowAtRangeIndex(i);

                    if ((row.getAttribute("GlTxnTypTds").toString()).equalsIgnoreCase("Y")) {
                        count = count + 1;
                    }
                }
            }


            if (count > 0) {
                selTdsRuleLnk.setDisabled(false);
            } else {
                selTdsRuleLnk.setDisabled(true);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(selTdsRuleLnk);


    }

    public void setSelTaxRuleLnk(RichCommandLink selTaxRuleLnk) {
        this.selTaxRuleLnk = selTaxRuleLnk;
    }

    public RichCommandLink getSelTaxRuleLnk() {
        return selTaxRuleLnk;
    }

    public void setSelTdsRuleLnk(RichCommandLink selTdsRuleLnk) {
        this.selTdsRuleLnk = selTdsRuleLnk;
    }

    public RichCommandLink getSelTdsRuleLnk() {
        return selTdsRuleLnk;
    }

    public String backTaxAction() {

        return "back";
    }


    public Number getTaxBasicAmount() {
        setAs(new Number(0));
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");

        ViewObject GLVw = am.getGl1();

        /** Get  basic amount from GlTaxRuleLine */
        ViewObject v1 = am.getGlTaxRuleLine1();
        v1.executeQuery();
        RowSetIterator rit = v1.createRowSetIterator(null);
        //System.out.println("Voucher id from tax rule line-->"+rit.first().getAttribute("GlVouId")+" and from GL is-->"+GLVw.getCurrentRow().getAttribute("GlVouId"));
        if (rit.first() != null &&
            rit.first().getAttribute("GlVouId").equals(GLVw.getCurrentRow().getAttribute("GlVouId")) &&
            Integer.parseInt(rit.first().getAttribute("GlTaxRulePriority").toString()) == 0) {

            as = (Number)(rit.first().getAttribute("GlTaxRuleTaxableAmtSp"));
        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();

            if ((Integer)(lineRow.getAttribute("GlTaxRulePriority")) == 0) {
                System.out.println("Inside new if statement of basic tax amount");
                as = as.add((Number)(lineRow.getAttribute("GlTaxRuleTaxableAmtSp")));
                System.out.println("Value of amy sp line wise---->" + as);
            }
        }
        rit.closeRowSetIterator();
        System.out.println("Basic Tax Amount is ----->" + as);
        return as;
    }


    public Number getTdsBasicAmount() {
        setAs(new Number(0));
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");

        ViewObject GLVw = am.getGl1();

        /** Get  basic amount from GlTdsRuleLine */

        ViewObject v1 = am.getGlTdsRuleLine1();
        v1.executeQuery();
        RowSetIterator rit = v1.createRowSetIterator(null);

        if (rit.first() != null &&
            rit.first().getAttribute("GlVouId").equals(GLVw.getCurrentRow().getAttribute("GlVouId")) &&
            Integer.parseInt(rit.first().getAttribute("GlTdsRulePriority").toString()) == 0) {
            as = (Number)(rit.first().getAttribute("GlTdsRuleTdsAmtSp"));
        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();

            if ((Integer)(lineRow.getAttribute("GlTdsRulePriority")) == 0) {
                System.out.println("Inside new if statement of basic tds amount");
                as = as.add((Number)(lineRow.getAttribute("GlTdsRuleTdsAmtSp")));
                System.out.println("Value of Tds Ruleamt sp line wise---->" + as);
            }
        }

        rit.closeRowSetIterator();
        return as;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void checkTaxTds() {
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        /** Create object of GLLines */
        ViewObject glLine = am.getGlLines1();
        RowSetIterator rowIterator = glLine.createRowSetIterator(null);
        int taxLines = 0;
        int tdsLines = 0;
        if (getTaxYN() == false) {
            System.out.println("No checkbox is selected--delete rows");
            /** Run loop for all rows of gl lines if check box for tax is not checked */
            while (rowIterator.hasNext()) {
                Row currVouLne = rowIterator.next();
                if (currVouLne.getAttribute("GlLnTyp").equals("DTAX")) {
                    /** Check for row of tax entry and delete it*/
                    taxLines += 1;
                    currVouLne.remove();
                    System.out.println("Row removed-->" + taxLines);
                }
            }
            glLine.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(glLineTable);

            if (taxLines > 0) {
                /**if a row for tax entry is present then delete rows from tax tables. */
                taxDelete();
            }

        }
        if (getTdsYN() == false) {
            /** Run loop for all rows of gl lines if check box for tds is not checked */
            while (rowIterator.hasNext()) {
                Row currVouLne = rowIterator.next();
                if (currVouLne.getAttribute("GlLnTyp").equals("DTDS")) {
                    /** Check for row of tds entry and delete it */
                    tdsLines += 1;
                    currVouLne.remove();
                    System.out.println("TDS lines removed-->" + tdsLines);
                }
            }


            if (tdsLines > 0) {
                /**if a row for tdsentry is present then delete rows from tds tables. */
                tdsDelete();
            }
        }
        rowIterator.closeRowSetIterator();
        /**Commited -Removed lines 27-02-2013*/
        //  am.getDBTransaction().commit();
        glLine.executeQuery();
    }

    public void taxDelete() {
        System.out.println("Inside tax delete-->");
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        /** Create object for tax rule view*/
        String vouId = am.getGl1().getCurrentRow().getAttribute("GlVouId").toString();
        System.out.println("Voucher id inside tax delete--->" + vouId);
        ViewObject glTax = am.getGlTaxRule1();
        /** Create object for tax rule line view*/
        ViewObject glTaxLine = am.getGlTaxRuleLine2();
        Row row[] = glTaxLine.getFilteredRows("GlVouId", vouId);
        System.out.println("No. of lines in TaxLine-->" + row.length);
        glTaxLine.setWhereClause("GL_VOU_ID = '" + vouId + "'");

        glTaxLine.executeQuery();


        RowSetIterator rowIterator = glTaxLine.createRowSetIterator(null);
        /**Remove all row from tax rule line table for this voucher */
        System.out.println("Number of rows in TaxRuleLine--->" + rowIterator.getRowCount());
        while (rowIterator.hasNext()) {
            Row currVouLne = rowIterator.next();
            currVouLne.remove();
            System.out.println("Tax RUle line row removed");
        }

        glTaxLine.executeQuery();
        rowIterator.closeRowSetIterator();
        glTaxLine.setWhereClause(null);
        glTaxLine.executeQuery();


        glTax.setWhereClause("GL_VOU_ID ='" + vouId + "'");
        glTax.executeQuery();
        RowSetIterator rit = glTax.createRowSetIterator(null);
        System.out.println("Number of row in GL_TAX---<" + rit.getRowCount());
        /**Remove current row from tax rule table*/
        while (rit.hasNext()) {
            rit.next().remove();
            System.out.println("GL tax Removed--->");
        }
        rit.closeRowSetIterator();
        glTax.executeQuery();
        glTax.setWhereClause(null);
        glTax.executeQuery();
        //am.getDBTransaction().commit();
    }

    public void tdsDelete() {
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        String vouId = am.getGl1().getCurrentRow().getAttribute("GlVouId").toString();

        /** Create object for tds rule view*/
        ViewObject glTds = am.getGlTdsLine1();
        /** Create object for tds rule line view*/
        ViewObject glTdsLine = am.getGlTdsRuleLine1();
        glTdsLine.setWhereClause("GL_VOU_ID = '" + vouId + "'");
        glTdsLine.executeQuery();

        RowSetIterator rowIterator = glTdsLine.createRowSetIterator(null);
        System.out.println("Rows in TDS Rule Line---->" + rowIterator);
        /**Remove all row from tds rule line table for this voucher */
        while (rowIterator.hasNext()) {
            Row currVouLne = rowIterator.next();
            currVouLne.remove();
        }
        glTdsLine.executeQuery();
        rowIterator.closeRowSetIterator();
        glTdsLine.setWhereClause(null);
        glTdsLine.executeQuery();


        /**Remove current row from tds rule table*/
        glTds.setWhereClause("GL_VOU_ID ='" + vouId + "'");
        glTds.executeQuery();
        RowSetIterator rit = glTds.createRowSetIterator(null);

        while (rit.hasNext()) {
            rit.next().remove();
        }
        rit.closeRowSetIterator();
        glTds.executeQuery();
        glTds.setWhereClause(null);
        glTds.executeQuery();

    }
    private Number Sum = new Number(0);

    public Number getTaxAmount() {
        setSum(new Number(0));
        setAs(new Number(0));
        setAsCr(new Number(0));
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject v1 = am.getGlLines1();
        RowSetIterator rit = v1.createRowSetIterator(null);

        /**Add debit taxable amount of Gl lines*/
        if (rit.first() != null && rit.first().getAttribute("GlTxnTypTax").equals("Y") &&
            rit.first().getAttribute("GlAmtTyp").equals("Dr")) {
            as = (Number)(rit.first().getAttribute("GlAmtSp"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("GlTxnTypTax").equals("Y") && lineRow.getAttribute("GlAmtTyp").equals("Dr")) {
                as = as.add((Number)(lineRow.getAttribute("GlAmtSp")));

            }
        }
        System.out.println("Debit amount of voucher--->" + as);
        /**Add credit taxable amount of gl lines*/
        if (rit.first() != null && rit.first().getAttribute("GlTxnTypTax").equals("Y") &&
            rit.first().getAttribute("GlAmtTyp").equals("Cr")) {
            asCr = (Number)(rit.first().getAttribute("GlAmtSp"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("GlTxnTypTax").equals("Y") && lineRow.getAttribute("GlAmtTyp").equals("Cr")) {
                asCr = asCr.add((Number)(lineRow.getAttribute("GlAmtSp")));

            }
        }
        System.out.println("Credit amount of voucher------>" + asCr);
        rit.closeRowSetIterator();
        /**Get taxable amount by getting difference of credit and debit amount*/
        if (as.compareTo(asCr) == 1) {
            setSum((Number)(as.minus(asCr)));
        } else if (as.compareTo(asCr) == -1) {

            setSum((Number)(asCr.minus(as)));
        }
        //    rit.closeRowSetIterator();
        System.out.println("taxable amount of voucher  is---->" + Sum);
        return Sum;
    }

    /** Function to get total taxable amount from tvou lines.
     * @return
     */
    private Number asCr = new Number();

    public Number getTdsAmount() {
        setSum(new Number(0));
        setAs(new Number(0));
        setAsCr(new Number(0));
        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject v1 = am.getGlLines1();
        RowSetIterator rit = v1.createRowSetIterator(null);

        /**Add debit tds amount of Gl lines*/
        if (rit.first() != null && rit.first().getAttribute("GlTxnTypTds").equals("Y") &&
            rit.first().getAttribute("GlAmtTyp").equals("Dr")) {
            as = (Number)(rit.first().getAttribute("GlAmtSp"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("GlTxnTypTds").equals("Y") && lineRow.getAttribute("GlAmtTyp").equals("Dr")) {
                as = as.add((Number)(lineRow.getAttribute("GlAmtSp")));

            }
        }

        /**Add credit tds amount of gl lines*/
        if (rit.first() != null && rit.first().getAttribute("GlTxnTypTds").equals("Y") &&
            rit.first().getAttribute("GlAmtTyp").equals("Cr")) {
            asCr = (Number)(rit.first().getAttribute("GlAmtSp"));

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("GlTxnTypTds").equals("Y") && lineRow.getAttribute("GlAmtTyp").equals("Cr")) {
                asCr = asCr.add((Number)(lineRow.getAttribute("GlAmtSp")));

            }
        }

        rit.closeRowSetIterator();
        /**Get tds amount by getting difference of credit and debit amount*/
        if (as.compareTo(asCr) == 1) {
            setSum((Number)(as.minus(asCr)));
        } else if (as.compareTo(asCr) == -1) {

            setSum((Number)(asCr.minus(as)));
        }
        //   rit.closeRowSetIterator();
        return Sum;
    }

    public void setSum(Number Sum) {
        this.Sum = Sum;
    }

    public void setAsCr(Number asCr) {
        this.asCr = asCr;
    }

    public Number getAsCr() {
        return asCr;
    }

    public void setGlLineTable(RichTable glLineTable) {
        this.glLineTable = glLineTable;
    }

    public RichTable getGlLineTable() {
        return glLineTable;
    }

    public void setVouIdPage(RichSelectOneChoice vouIdPage) {
        this.vouIdPage = vouIdPage;
    }

    public RichSelectOneChoice getVouIdPage() {
        return vouIdPage;
    }

    public void setAdvance(RichSelectBooleanCheckbox advance) {
        this.advance = advance;
    }

    public RichSelectBooleanCheckbox getAdvance() {
        return advance;
    }

    public void setAmtYN(Boolean amtYN) {
        this.amtYN = amtYN;
    }

    public Boolean getAmtYN() {

        am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject v1 = am.getGlLines1();
        if (v1.getRowCount() > 0) {
            Row rownew = v1.getCurrentRow();
            if (((Number)(rownew.getAttribute("GlAmtSp"))).compareTo(new Number(0)) == 0) {
                return true;
            } else
                return false;
        }
        return true;

    }

    public void amountSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number spamt = (Number)object;

        if (spamt.compareTo(0) == -1) {
            String msg2 = resolvElDCMsg("#{bundle['MSG.382']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        } else if (isPrecisionValid(26, 6, spamt) == false) {
            String msg2 = resolvElDCMsg("#{bundle['MSG.57']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);

        }

    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void setWfMode(String wfMode) {
        System.out.println(wfMode);
        this.wfMode = wfMode;
    }

    public String getWfMode() {
        return wfMode;
    }

    public void setWfComment(String wfComment) {
        this.wfComment = wfComment;
    }

    public String getWfComment() {
        return wfComment;
    }

    public void setWfNextUser(Integer wfNextUser) {
        this.wfNextUser = wfNextUser;
    }

    public Integer getWfNextUser() {
        return wfNextUser;
    }

    public void setWfNextLvl(Integer wfNextLvl) {
        this.wfNextLvl = wfNextLvl;
    }

    public Integer getWfNextLvl() {
        return wfNextLvl;
    }


    public String costCenterSaveAction() {
        GlAppAMImpl am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        ViewObject v1 = am.getGl1();
        Row currRow = v1.getCurrentRow();
        String tvouId = (String)currRow.getAttribute("GlVouId");

        Integer SLOC_ID = (Integer)currRow.getAttribute("GlSlocId");
        String CLD_ID = (String)currRow.getAttribute("GlCldId");
        String HO_ORG_ID = (String)currRow.getAttribute("GlHoOrgId");
        String ORG_ID = (String)currRow.getAttribute("GlOrgId");


        String val = setToTmplSaveCC(SLOC_ID, CLD_ID, HO_ORG_ID, ORG_ID, 54, tvouId, cost_center_slno);
        System.out.println(val);
        return val;

    }

    public String checkOrgDocActivity(Integer P_SLOC_ID, String P_ORG_ID, Integer P_DOC_ID, Integer P_DOC_TYPE_ID,
                                      String P_ACTIVITY) {

        OperationBinding chkActivity = this.getBindings().getOperationBinding("fnChkOrgDocActivity");
        chkActivity.getParamsMap().put("P_SLOC_ID", P_SLOC_ID);
        chkActivity.getParamsMap().put("P_ORG_ID", P_ORG_ID);
        chkActivity.getParamsMap().put("P_DOC_ID", P_DOC_ID);
        chkActivity.getParamsMap().put("P_DOC_TYPE_ID", P_DOC_TYPE_ID);
        chkActivity.getParamsMap().put("P_ACTIVITY", P_ACTIVITY);
        /*    if (chkActivity.execute().toString() != null) {
            String flag = chkActivity.execute().toString();
            return flag;
        } else */
        return "N";

    }


    public void setDocDisplayId(String docDisplayId) {
        this.docDisplayId = docDisplayId;
    }

    public String getDocDisplayId() {
        return docDisplayId;
    }

    public void setDocEntType(Integer DocEntType) {
        this.DocEntType = DocEntType;
    }

    public Integer getDocEntType() {
        return DocEntType;
    }

    public void setGlAmount(Number glAmount) {
        this.glAmount = glAmount;
    }

    public Number getGlAmount() {
        if (getTotalCredit().equals(0)) {
            return getTotalDebit();
        } else
            return getTotalCredit();
    }

    public void setWfId(Integer wfId) {
        this.wfId = wfId;
    }

    public Integer getWfId() {
        return wfId;
    }

    public void setWfFlag(String wfFlag) {
        this.wfFlag = wfFlag;
    }

    public String getWfFlag() {
        return wfFlag;
    }

    public void exceptioHandeler() {


        System.out.println("Inside Handeler");
        StringBuilder msg = new StringBuilder();
        msg.append("<html><body><p><b>" + resolvElDCMsg("#{bundle['MSG.445']}").toString() + "</b></p>");
        msg.append("<p><b>" + resolvElDCMsg("#{bundle['MSG.447']}").toString() + "</b></p>");
        msg.append("<ul><li>" + resolvElDCMsg("#{bundle['MSG.449']}").toString() + "</li><li>" +
                   resolvElDCMsg("#{bundle['MSG.450']}").toString() + "</li><li>" +
                   resolvElDCMsg("#{bundle['MSG.477']}").toString() + "!</li></ul>");
        msg.append("</body></html>");
        FacesMessage message = new FacesMessage(msg.toString());
        message.setSeverity(FacesMessage.SEVERITY_WARN);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);

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

    public boolean isTransactionDirty() {
        return false;
    }

    public void rollbackTransaction() {
    }

    public void commitTransaction() {
    }

    public boolean setAttributeValue(AttributeContext p0, Object p1) {
        return false;
    }

    public Object createRowData(RowContext p0) {
        return null;
    }

    public Object registerDataProvider(RowContext p0) {
        return null;
    }

    public boolean removeRowData(RowContext p0) {
        return false;
    }

    public void validate() {
    }

    public void beginRequest(HashMap p0) {
    }

    public void endRequest(HashMap p0) {
    }

    public boolean resetState() {
        return false;
    }

    public void setApplyTaxChkBind(RichSelectBooleanCheckbox applyTaxChkBind) {
        this.applyTaxChkBind = applyTaxChkBind;
    }

    public RichSelectBooleanCheckbox getApplyTaxChkBind() {
        return applyTaxChkBind;
    }

    public void advanceValueChangeListener(ValueChangeEvent vce) {

    }

    public void setApplyTdsChkBind(RichSelectBooleanCheckbox applyTdsChkBind) {
        this.applyTdsChkBind = applyTdsChkBind;
    }

    public RichSelectBooleanCheckbox getApplyTdsChkBind() {
        return applyTdsChkBind;
    }

    public void setAdjustmentChkBind(RichSelectBooleanCheckbox adjustmentChkBind) {
        this.adjustmentChkBind = adjustmentChkBind;
    }

    public RichSelectBooleanCheckbox getAdjustmentChkBind() {
        return adjustmentChkBind;
    }

    public void adjustmentValueChangeListener(ValueChangeEvent vce) {

    }

    public void setContentStyleGreen(String contentStyleGreen) {
        this.contentStyleGreen = contentStyleGreen;
    }

    public String getContentStyleGreen() {
        return contentStyleGreen;
    }

    public void setContentStyleBlack(String contentStyleBlack) {
        this.contentStyleBlack = contentStyleBlack;
    }

    public String getContentStyleBlack() {
        return contentStyleBlack;
    }

    public String backButtonOc() {
        return "back";

    }

    /*  public GlAppAMImpl getAm() {
         am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
        return am;
    }  */

    public void coaIdOcValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer coaId = Integer.parseInt(object.toString());
        System.out.println("Inside Validator---" + coaId);

        ViewObject glOc1 = am.getGlOc1();
        String vouId = glOc1.getCurrentRow().getAttribute("GlVouId").toString();
        System.out.println("Coa Id is--->" + coaId + "Vou ID--->" + vouId);
        System.out.println("From Current Row of VO-->" + glOc1.getCurrentRow().getAttribute("GlCoaId"));
        Row row[] = glOc1.getFilteredRows("GlCoaId", coaId);
        System.out.println("Duplicate row in filtered rows-->" + row.length);
        if (row.length > 1) {
            FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.451']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public void ocCoaIdVCE(ValueChangeEvent vce) {
        Integer val = Integer.parseInt(vce.getNewValue().toString());
        System.out.println("Coa Id inside value change--->" + val);
    }

    public void glInstrmntNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer instrTyp = Integer.parseInt(instrmntTypBind.getValue().toString());
        String instrNo = object.toString();
        System.out.println("Typ is-->" + instrTyp + "and cheque no is-->" + instrNo);
        if (instrTyp == 111) {
            String expression = "^[0-9]*$";
            CharSequence inputStr = instrNo;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = "Invalid Cheque Number";
            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvElDCMsg("#{bundle['MSG.454']}").toString()));
            }

        }
    }

    public void setInstrmntTypBind(RichSelectOneChoice instrmntTypBind) {
        this.instrmntTypBind = instrmntTypBind;
    }

    public RichSelectOneChoice getInstrmntTypBind() {
        return instrmntTypBind;
    }

    public void setEditButtonLines(RichCommandButton editButtonLines) {
        this.editButtonLines = editButtonLines;
    }

    public RichCommandButton getEditButtonLines() {
        return editButtonLines;
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

    public void setCostCenterPopup(RichPopup costCenterPopup) {
        this.costCenterPopup = costCenterPopup;
    }

    public RichPopup getCostCenterPopup() {
        return costCenterPopup;
    }

    public void taxRuleVCE(ValueChangeEvent vce) {
        try {
            if (vce.getNewValue() != null) {
                Integer rule = Integer.parseInt(vce.getNewValue().toString());
                System.out.println("Rule Id tax inside VCE====" + rule);
                am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
                am.procTaxForHdr(rule);
                System.out.println("Function Called in VCE");

            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }

    }

    public void tdsRuleVCE(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            Integer rule = Integer.parseInt(vce.getNewValue().toString());
            System.out.println("Rule Id tax inside VCE====" + rule);
            am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
            am.procTdsForLine(rule);
            System.out.println("Function Called in VCE");

        }

    }


    public void currIdChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
            ViewObjectImpl glLineVO = am.getGlLines1();
            Row glLineRow = glLineVO.getCurrentRow();

            if (glLineRow.getAttribute("GlCurrIdSp") != null) {

                if (glLineRow.getAttribute("GlCc") != null) {

                    Integer curr = Integer.parseInt(valueChangeEvent.getNewValue().toString());
                    Integer currIdBs = Integer.parseInt(glLineRow.getAttribute("GlCurrIdBs").toString());
                    String orgId = glLineRow.getAttribute("GlOrgId").toString();
                    ViewObjectImpl lovLatestCurr = am.getLovCurrLatest1();
                    RowQualifier rq = new RowQualifier(lovLatestCurr);
                    rq.setWhereClause("CcCurrId= " + currIdBs + " and CcCurrIdTxn= " + curr + " and OrgId = '" +
                                      orgId + "'");
                    Row[] rows = lovLatestCurr.getFilteredRows(rq);

                    Number rate = (Number)rows[0].getAttribute("CcBuy");

                    System.out.println("rate------------->" + rate);
                    Number basic = (Number)getAmtSp().getValue();
                    System.out.println("basic amount--------------->" + basic);
                    Number result = basic.multiply(rate);
                    System.out.println("new amount----------------->" + result);
                    amtBs.setValue(result);
                    //   RequestContext.getCurrentInstance().getPageFlowScope().put("", null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(amtBs);
                }
            }
        }
    }

    public String getOrg_id() {
        if (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}") != null)
            return resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        else
            return org_id;
    }

    public String getCld_id() {
        if (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}") != null)
            return resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        else
            return cld_id;
    }

    public Integer getSloc_id() {
        if (resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}") != null)
            return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        else
            return sloc_id;
    }

    public Integer getCount() {
        if (count == null) {
            try {
                count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
                am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
                ViewObjectImpl v = am.getGl1();
                Row curr = v.getCurrentRow();
                Integer P_SLOC_ID = (Integer)curr.getAttribute("GlSlocId");
                String P_CLD_ID = (String)curr.getAttribute("GlCldId");
                String P_ORG_ID = (String)curr.getAttribute("GlOrgId");
                String P_HO_ORG_ID = (String)curr.getAttribute("GlHoOrgId");
                Integer val = isCostCenterPresent(P_SLOC_ID, P_CLD_ID, P_ORG_ID, P_HO_ORG_ID);
                System.out.println("valueeee of isCostCenterPresent(P_SLOC_ID, P_CLD_ID, P_ORG_ID, P_HO_ORG_ID)=>>>>> " +
                                   val);
                if (val != 1)
                    cclinkBind.setDisabled(true);
                else
                    cclinkBind.setDisabled(false);

            } catch (Exception e) {
                System.out.println("error in on_load_page");
                e.printStackTrace();
            }
        }

        return count;
    }


    public void setCclinkBind(RichCommandLink cclinkBind) {
        this.cclinkBind = cclinkBind;
    }

    public RichCommandLink getCclinkBind() {
        return cclinkBind;
    }
}

