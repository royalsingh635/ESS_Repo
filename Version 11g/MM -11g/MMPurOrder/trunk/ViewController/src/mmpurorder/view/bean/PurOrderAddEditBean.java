package mmpurorder.view.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

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

import mmpurorder.model.service.PurOrderAMImpl;
import mmpurorder.model.views.MmDrftPoItmVORowImpl;
import mmpurorder.model.views.MmDrftPoVORowImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetail;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/** This class comprises all the methods for PO related functionalities on the Add/Edit Pages.
 * @author SM
 * @HandOver NG
 * Dated-10 Dec 2012
 * * */

public class PurOrderAddEditBean {

    private RichPopup delpop;
    private RichSelectBooleanCheckbox selectChkBox;
    private Boolean chkbxDisable;
    private RichShowDetail disclosedItem;
    private Boolean showFields = true;
    private RichPopup taxPopUp;
    private RichPopup ocPopUp;
    private Integer TaxRuleId = 0;
    private Number taxableAmount = new Number(0);
    private RichTable dlvSchdlTable;
    private RichPopup discPopUp;
    private RichInputText transPmtAmt;
    private RichInputText sumDiscValue;
    private BigDecimal Avl_stk;
    private BigDecimal Req_stk;
    private BigDecimal Ord_stk;
    private RichPopup itmHistPopUp;
    private Integer TaxRuleId1 = 0;
    private Number taxableAmount1 = new Number(0);
    private String wfId = null;
    private static int INTEGER = Types.NUMERIC;
    private static int DATE = Types.DATE;
    private static int STRING = Types.VARCHAR;
    private static int NUMBER = Types.NUMERIC;
    private Integer TaxRuleId2 = 0;
    private Number taxableAmount2 = new Number(0);
    private Number itmtaxamt1 = new Number(0);
    private String taxExmpt = "N";
    private String taxFlg = "N";
    private String taxruleflg = "I";
    private String visAfterAuth = "N";
    private RichTable itmTableBind;
    private Boolean disHeader;
    private RichPopup suggestSupPopUp;
    private RichPopup itmLinkSupPopUp;
    private RichSelectBooleanCheckbox checkBxSugSup;
    private RichInputDate dateDlvSchdl;
    private RichPanelTabbed panelTabed;
    private RichShowDetailItem dlvryTab;
    private String showItemsTab = "true";
    private String showDlvSchdlTab = "true";
    private String showPaymentSchdlTab = "true";
    private String showTnAgreeTab = "true";
    private String showPortAndContnrTab = "true";
    private RichTable tncTableBind;
    private RichInputListOfValues eoNameTransBind;
    private RichInputText transTotalPoCostBind;
    private RichInputText transTotalPoCostBsBind;
    private RichShowDetailItem supAndItmTab;
    private RichShowDetailItem termsAndAggTab;
    private RichShowDetailItem paySchdlTab;
    private RichInputListOfValues itmIdBind;
    private RichInputText transSchdlQuantity;
    private RichInputDate frmDateBind;
    private RichInputDate toDateBind;
    private RichSelectOneChoice itmUomBind;
    private RichInputText itmPriceBind;
    private RichInputText itmQtyBind;
    private RichTable ocTableBind;
    private String itmNameForLink = "";
    private String policyNm = null;
    private String policyId = null;
    private RichPopup linkSupPopUp;
    private RichInputText sumitemSpBind;
    private RichPanelGroupLayout sumgroupBind;
    private RichSelectOneRadio discType;
    private RichTable poDlvSchlTable;
    private Boolean enableSaveAs = true;
    private RichInputText poWiseDiscount;
    private Number poDiscount = new Number(0);
    private String poDiscType = "A";
    private RichInputText transSumOcAmt;
    private RichInputText itmDiscAmtBind;
    private RichCommandButton saveAsButton;
    private RichSelectOneRadio discTypeForItemBind;
    private RichInputText poDiscAmtBind;
    private RichInputDate poDtBind;
    private RichInputText poTaxAmtBind;
    private RichSelectBooleanCheckbox transPmtAdvFlgBind;
    private RichInputDate pmtDateBind;
    private RichCommandButton suggestedSuppBtnBind;
    private RichInputText balanceQtyBind;
    private RichShowDetail disclosedSupplier;
    private RichInputText eoIdDbBind;

    private static Integer PO_MODE_DRAFT = 227;
    private static Integer PO_MODE_INCOMPLETE = 228;
    private static Integer PO_MODE_AMENDED = 237;
    private static Integer PO_BASIS_QUOT = 156;
    private static Integer PO_BASIS_TEMPLATE = 174;
    private static Integer PO_BASIS_SUGGORDER = 155;
    private static Integer PO_BASIS_PREVPO = 154;
    private static Integer PO_BASIS_RATECONTRCT = 175;
    private static Integer PO_BASIS_MANUAL = 181;

    private static Integer PO_TYPE_STD = 170;
    private static Integer PO_TYPE_DIRECT = 171;
    private static Integer PO_TYPE_SCDLPO = 172;
    private static Integer PO_TYPE_RC = 173;
    private static Integer PO_TYPE_IMPORT = 741;
    private static String resetflg = "P";
    private static Integer PO_PAYMODE_CASH = 161;
    private static Integer PO_PAYMODE_CHEQUE = 160;
    private static Integer PO_DOC_NO = 18504;
    private Integer taxRule = null;
    private Integer taxRuleitem = null;
    //private RichSelectOneChoice itmPoBind;
    private RichPopup amendPopUp;
    private RichInputText taxOnItmBind;
    private RichInputText priceInBhdBind;
    private RichInputText priceInInrBind;
    private RichCommandImageLink resetTaxPOBind;
    private RichCommandImageLink resetTaxItemBind;
    private RichPopup powiseSelectTaxPop;
    private RichSelectOneRadio applyTaxRadioBind;
    private RichSelectOneChoice selectTaxPoBind;
    private RichSelectBooleanCheckbox selectitmCB;
    private RichSelectBooleanCheckbox selectAllCB;
    private RichPopup resettaxPop;
    private RichSelectOneChoice poBasisBinding;
    private RichCommandButton goToTandCButton;
    private RichInputListOfValues currIdBinding;
    private RichPopup shortClosePoPopup;
    private static ADFLogger adfLog = (ADFLogger)ADFLogger.createADFLogger(PurOrderAddEditBean.class);
    private RichInputText itmPoBind;
    private RichInputListOfValues coaNmBind;
    private RichSelectOneChoice transOcDescBind;
    private RichInputText transAmtBind;
    Number zero = new Number(0);
    private UploadedFile file;
    private RichPopup execlUploadPopup;
    private RichShowDetailItem portsContnrTab;
    private RichInputText totalPoCastBeforeTax;
    private RichTable portDetailTableBind;
    private RichInputDate etaDateBind;
    private RichInputDate etdDateBind;
    private RichInputText contnrNameBind;
    private RichInputText contnrSizeBind;
    private RichInputText contnrQtyBind;

    public PurOrderAddEditBean() {
    }

    /**
     *                   HELPER METHODS
     ***/

    public PurOrderAMImpl getAm() {
        return (PurOrderAMImpl)resolvElDC("PurOrderAMDataControl");
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

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
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

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {

            st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
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

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     *  Method for function call - To show items history
     * */

    protected Object callStoredFunctionHistory(String stmt, Object[] bindVars) {

        CallableStatement st = null;
        try {
            /** 1. Create a JDBC CallabledStatement */

            st = getAm().getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            /** 2. Register the first bind variable for the return value. and last 2 variable for output variable of function. */
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
            /** 5. Set the value of user-supplied bind vars in the stmt */
            st.executeUpdate();

            try {
                setAvl_stk((BigDecimal)st.getObject(5));
                setReq_stk((BigDecimal)(st.getObject(6)));
                setOrd_stk((BigDecimal)st.getObject(7));
            } catch (NullPointerException e) {
                /*    BindingContext bctx = BindingContext.getCurrent();
                ((DCBindingContainer)bctx.getCurrentBindingsEntry()).reportException(e); */

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

    protected void callSimpleStoredProcedure(String stmt, Object[] bindVars) {
        PreparedStatement st = null;

        try {
            // 1. Create a JDBC PreparedStatement for
            st = getAm().getDBTransaction().createPreparedStatement("begin " + stmt + ";end;", 0);
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

    /**
     * Procedure call
     *
     * */

    protected Object callStoredProcedure(String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            // 1. Create a JDBC CallableStatement
            st = getAm().getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);
            st.registerOutParameter(5, NUMBER);
            st.setObject(6, bindVars[4]);
            st.setObject(7, bindVars[5]);

            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            return st.getObject(5);
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    /**
     *  Method to Add the items
     * */

    public void addItemAction(ActionEvent actionEvent) {
        Row rowPo = getAm().getMmDrftPo().getCurrentRow();
        Object obj = rowPo.getAttribute("PoBasis");

        if (obj != null) {
            if (rowPo.getAttribute("EoId") != null) {
                if (rowPo.getAttribute("CurrIdSp") != null) {
                    if (rowPo.getAttribute("CurrConvFctr") != null) {
                        Number fctr = new Number(0);
                        fctr = (Number)(rowPo.getAttribute("CurrConvFctr"));
                        if (fctr.compareTo(new Number(0)) > 0) {
                            if (rowPo.getAttribute("TransEoPolicyId").equals("0")) {
                                String msg2 = "Please Add Policy for Supplier.";
                                FacesMessage message2 = new FacesMessage(msg2);
                                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(eoNameTransBind.getClientId(), message2);
                            } else if (rowPo.getAttribute("TransEoPolicyId").equals("-1")) {

                                String msg2 = "Please Check Supplier or Price Policy for Supplier.";
                                FacesMessage message2 = new FacesMessage(msg2);
                                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(eoNameTransBind.getClientId(), message2);
                            } else {
                                String valid = "Y";
                                if ((!rowPo.getAttribute("TransEoPolicyId").equals("-2"))) {
                                    String plcId = (String)rowPo.getAttribute("TransEoPolicyId");
                                    Integer eoId = (Integer)rowPo.getAttribute("EoId");
                                    String cldId = (String)rowPo.getAttribute("CldId");
                                    Integer slocId = (Integer)rowPo.getAttribute("SlocId");
                                    String hoOrgId = (String)rowPo.getAttribute("TransHoOrgId");
                                    RowQualifier rq = new RowQualifier(getAm().getAppEoPricePlc());
                                    rq.setWhereClause("CldId='" + cldId + "' and SlocId=" + slocId + " and HoOrgId='" +
                                                      hoOrgId + "' and PlcId='" + plcId + "' and EoId=" + eoId);
                                    Row[] fr = getAm().getAppEoPricePlc().getFilteredRows(rq);
                                    if (fr[0].getAttribute("Valid").toString().equals("Y"))
                                        valid = "Y";
                                    else
                                        valid = "N";
                                }

                                if (valid.equals("N")) {
                                    String msg2 = "Please Check Price Policy for Supplier.";
                                    FacesMessage message2 = new FacesMessage(msg2);
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext.getCurrentInstance().addMessage(eoNameTransBind.getClientId(),
                                                                                 message2);
                                } else {
                                    if (Integer.parseInt(obj.toString()) == PO_BASIS_MANUAL) {
                                        OperationBinding operationBinding =
                                            getBindings().getOperationBinding("Createwithparameters");
                                        operationBinding.execute();

                                    } else if (rowPo.getAttribute("QuotDocId") == null &&
                                               rowPo.getAttribute("TmplDocId") == null &&
                                               rowPo.getAttribute("RefPoDocId") == null &&
                                               rowPo.getAttribute("SoGrpId") == null) {
                                        String val = "";
                                        if (Integer.parseInt(obj.toString()) == PO_BASIS_QUOT &&
                                            rowPo.getAttribute("QuotDocId") == null) {
                                            val = "Quotation No.";
                                        } else if (Integer.parseInt(obj.toString()) == PO_BASIS_TEMPLATE &&
                                                   rowPo.getAttribute("TmplDocId") == null) {
                                            val = "Template No.";
                                        } else if (Integer.parseInt(obj.toString()) == PO_BASIS_PREVPO &&
                                                   rowPo.getAttribute("RefPoDocId") == null) {
                                            val = "Previous Order No.";
                                        } else if (Integer.parseInt(obj.toString()) == PO_BASIS_SUGGORDER &&
                                                   rowPo.getAttribute("SoGrpId") == null) {
                                            val = "Suggested Order No.";
                                        }
                                        String msg2 = resolvEl("#{bundle['LBL.1453']}") + val;
                                        FacesMessage message2 = new FacesMessage(msg2);
                                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext.getCurrentInstance().addMessage(null, message2);
                                    } else {
                                        OperationBinding operationBinding =
                                            getBindings().getOperationBinding("Createwithparameters");
                                        operationBinding.execute();
                                        //  addMode="A";
                                    }

                                    disclosedItem.setDisclosed(true);
                                }
                            }
                        } else {
                            String msg2 = "Currency not valid or Conversion for this currency is not define.";
                            FacesMessage message2 = new FacesMessage(msg2);
                            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(currIdBinding.getClientId(), message2);
                        }
                    } else {
                        String msg2 = "Currency not valid or Conversion for this currency is not define.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(currIdBinding.getClientId(), message2);
                    }
                } else {
                    String msg2 = "Please select Supplier Currency.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(currIdBinding.getClientId(), message2);
                }
            } else {
                String msg2 = "Please select Supplier.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(eoNameTransBind.getClientId(), message2);
            }
        } else {
            String msg2 = resolvEl("#{bundle['MSG.402']}");
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
    }

    /**
     *  Method to Delete the selected items- From DrftPOItm,DrftPOdlvSchdl , DrftPOTr and DrftPOTrLines
     * */

    public void delSelectedAction(ActionEvent actionEvent) {
        PurOrderAMImpl am = getAm();
        ViewObjectImpl poItm = am.getMmDrftPoItm();
        ViewObjectImpl poDlvSchdl = am.getMmDrftPoDlvSchdl();
        ViewObjectImpl poTaxVo = am.getMmDrftPoTr();
        ViewObjectImpl poTaxLineVo = am.getMmDrftPoTrLinesVO1();
        poTaxLineVo.executeQuery();
        //  ViewObject poTax=am.getMmDrftPoTr();
        RowQualifier rqTax = new RowQualifier(poTaxVo);
        //    System.out.println("total rows in taxrule="+poTaxVo.getEstimatedRowCount());
        RowSetIterator rsi = poItm.createRowSetIterator(null);
        int count = 0;
        while (rsi.hasNext()) {
            Row nxt = rsi.next();
            Object mfd = nxt.getAttribute("MarkedForDelete");
            //System.out.println("mfd--->"+mfd);
            if (mfd != null) {
                Boolean b = (Boolean)mfd;
                if (b == true) {
                    count = count + 1;

                }
            }
        }
        rsi.closeRowSetIterator();
        //  System.out.println("No. of rows marked to delete="+count);
        if (count > 0) {
            RowSetIterator rsi1 = poItm.createRowSetIterator(null);

            while (rsi1.hasNext()) {
                Row nxt = rsi1.next();
                Object mfd = nxt.getAttribute("MarkedForDelete");
                //   System.out.println("current item ="+nxt.getAttribute("ItmId")+" and uom="+nxt.getAttribute("ItmUom"));

                if (mfd != null) {
                    Boolean b = (Boolean)mfd;
                    //  System.out.println("Is this row marked?="+b);
                    if (b == true) {
                        if (nxt.getAttribute("ItmId") != null && nxt.getAttribute("ItmUom") != null) {
                            RowQualifier dvlrq = new RowQualifier(poDlvSchdl);
                            dvlrq.setWhereClause("ItmId='" + nxt.getAttribute("ItmId") + "' and ItmUom='" +
                                                 nxt.getAttribute("ItmUom") + "'");
                            Row[] r = poDlvSchdl.getFilteredRows(dvlrq);
                            /*    RowQualifier rq=new RowQualifier();
                              Row[] v=poTaxLine.getFilteredRows(arg0, arg1) */

                            RowQualifier rq = new RowQualifier(poItm);
                            rq.setWhereClause("ItmId='" + nxt.getAttribute("ItmId") + "' and ItmUom='" +
                                              nxt.getAttribute("ItmUom") + "'");
                            Row[] itmrow = poItm.getFilteredRows(rq);
                            if (itmrow.length == 1) {
                                rqTax.setWhereClause("DocId= '" + nxt.getAttribute("DocId").toString() +
                                                     "' and ItmId= '" + nxt.getAttribute("ItmId").toString() +
                                                     "' and ItmUom= '" + nxt.getAttribute("ItmUom").toString() + "'");
                                Row[] rTax = poTaxVo.getFilteredRows(rqTax);
                                //   System.out.println("no. of rows in tr for this item="+rTax.length);
                                if (rTax.length > 0) {
                                    //-----------------
                                    //  System.out.println("no. of rows in trlines befor where clause="+poTaxLineVo.getEstimatedRowCount());
                                    RowQualifier rqTaxLine = new RowQualifier(poTaxLineVo);
                                    rqTaxLine.setWhereClause("DocId='" + nxt.getAttribute("DocId").toString() +
                                                             "' and ItmId='" + nxt.getAttribute("ItmId").toString() +
                                                             "' and TaxRuleId=" + rTax[0].getAttribute("TaxRuleId") +
                                                             " and ItmUom='" + nxt.getAttribute("ItmUom").toString() +
                                                             "'");
                                    // System.out.println("WhereClauseQuery-->");
                                    //  System.out.println("DocId='"+nxt.getAttribute("DocId").toString()+"' and ItmId='"+ nxt.getAttribute("ItmId").toString()+"' and TaxRuleId="+rTax[0].getAttribute("TaxRuleId")+" and ItmUom='"+nxt.getAttribute("ItmUom").toString()+"'");
                                    Row[] rTaxLine = poTaxLineVo.getFilteredRows(rqTaxLine);
                                    //   System.out.println("after where clause no. of rows in trlines to remove="+rTaxLine.length);
                                    if (rTaxLine.length > 0) {
                                        for (Row rTl : rTaxLine) {
                                            //  System.out.println("Removing trline");
                                            rTl.remove();
                                        }
                                    }
                                    //-----------------
                                    // System.out.println("Removing tr");
                                    rTax[0].remove();
                                }
                            }

                            for (Row rx : r) {
                                // System.out.println("Removing dlv schdl");
                                rx.remove();
                            }
                        }
                        //  System.out.println("Removing itm="+nxt.getAttribute("ItmId"));
                        nxt.remove();
                    }
                }
            }
            rsi1.closeRowSetIterator();
            poItm.executeQuery();
            poTaxVo.executeQuery();
            poTaxLineVo.executeQuery();
            am.getMmDrftPoTrLines().executeQuery();
            OperationBinding operationBinding = getBindings().getOperationBinding("Execute");
            operationBinding.execute();
            disclosedItem.setDisclosed(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poDlvSchlTable);

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, resolvEl("#{bundle['MSG.270']}"), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
    }

    public void setDelpop(RichPopup delpop) {
        this.delpop = delpop;
    }

    public RichPopup getDelpop() {
        return delpop;
    }

    /**
     *
     * */
    public void delSelectedAction(DialogEvent dialogEvent) {
        /*  if (dialogEvent.getOutcome().name().equals("yes")) {

            ViewObject poItm = getAm().getMmDrftPoItm();
            RowSetIterator rsi = poItm.createRowSetIterator(null);

            while (rsi.hasNext()) {
                Row nxt = rsi.next();
                Object mfd = nxt.getAttribute("MarkedForDelete");
                if (mfd != null) {
                    Boolean b = (Boolean)mfd;
                    if (b == true) {
                        nxt.remove();
                    }
                }
            }
            rsi.closeRowSetIterator();
            OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute");
            operationBinding1.execute();
            getAm().getMmDrftPoItm().executeQuery();

        } */
    }


    /**
     *  Method to Save the PO finally.
     * */
    public void savePoAction(ActionEvent actionEvent) {
        PurOrderAMImpl poAM = getAm();
        ViewObject po = poAM.getMmDrftPo();
        Row currPo = po.getCurrentRow();
        String poDocId = po.getCurrentRow().getAttribute("DocId").toString();
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        // System.out.println("ORG ID=" + currPo.getAttribute("OrgId"));
        if (currPo.getAttribute("OrgId") == null) {
            currPo.setAttribute("OrgId", OrgId);
        }

        if (currPo.getAttribute("PoType") != null && currPo.getAttribute("PoType").equals(491)) {
            if (currPo.getAttribute("PoDt") == null) {
                oracle.jbo.domain.Date dateCurr =
                    (oracle.jbo.domain.Date)new oracle.jbo.domain.Date().getCurrentDate();
                currPo.setAttribute("PoDt", dateCurr);
            } else if (currPo.getAttribute("PoBasis") == null) {
                FacesMessage message = new FacesMessage("Please Select PO Basis.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else if (currPo.getAttribute("EoId") == null) {
                FacesMessage message = new FacesMessage("Please Select Supplier.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            } else if (currPo.getAttribute("CurrIdSp") == null) {
                FacesMessage message = new FacesMessage("Currency is not Valid.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else if (currPo.getAttribute("CurrConvFctr") == null) {
                FacesMessage message = new FacesMessage("Currency Rate is not Valid.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else if (currPo.getAttribute("CurrConvFctr") != null) {
                Number fct = (Number)currPo.getAttribute("CurrConvFctr");
                if (fct.compareTo(new Number(0)) == 0) {
                    FacesMessage message = new FacesMessage("Currency Rate is not Valid.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else if (currPo.getAttribute("OpenOrdBasis") == null) {
                    FacesMessage message = new FacesMessage("Please Select Open Order Basis.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else if (currPo.getAttribute("OpenOrdVal") == null) {
                    FacesMessage message = new FacesMessage("Please Enter Open Order Value.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else if ((currPo.getAttribute("OpenOrdBasis").toString().equals("487")) &&
                           currPo.getAttribute("OpenOrdUom") == null) {
                    FacesMessage message = new FacesMessage("Please Select Open Order Unit.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else if (currPo.getAttribute("OpenOrdVal") != null) {
                    Number val = (Number)currPo.getAttribute("OpenOrdVal");
                    if (val.compareTo(new Number(0)) <= 0) {
                        FacesMessage message = new FacesMessage("Open Order Value must be greater than Zero.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else if (currPo.getAttribute("ValidFrmDt") == null || currPo.getAttribute("ValidToDt") == null) {
                        FacesMessage message = new FacesMessage("Please enter both Validity Date for Open Order.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        Integer eo = (Integer)currPo.getAttribute("EoId");
                        Date dt = new Date(currPo.getAttribute("PoDt").toString());
                        Integer fyid =
                            (Integer)(callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] { cld_id,
                                                                                                                    OrgId,
                                                                                                                    dt }));
                        if (fyid > 0) {
                            currPo.setAttribute("FyId", fyid);
                            if (currPo.getAttribute("PoId") == null) {


                                String docId = currPo.getAttribute("DocId").toString();
                                Integer p_slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
                                String p_OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
                                String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
                                String tableName = "MM$DRFT$PO";
                                //    Integer fyid=Integer.parseInt(currRow.getAttribute("FyId").toString());
                                String poId =
                                    (String)(callStoredFunction(Types.VARCHAR, "MM.fn_mm_gen_id(?,?,?,?,?,?)",
                                                                new Object[] { p_slocid, p_cldId, p_OrgId, tableName,
                                                                               null, fyid }));


                                Integer currId = (Integer)currPo.getAttribute("CurrIdSp");
                                Number fctr = (Number)currPo.getAttribute("CurrConvFctr");
                                Date vldfrm = (Date)currPo.getAttribute("ValidFrmDt");
                                Date vldto = (Date)currPo.getAttribute("ValidToDt");
                                currPo.setAttribute("PoId", poId);
                                currPo.setAttribute("CurrIdSp", currId);
                                currPo.setAttribute("CurrConvFctr", fctr);
                                currPo.setAttribute("ValidFrmDt", vldfrm);
                                currPo.setAttribute("ValidToDt", vldto);
                            }
                            currPo.setAttribute("PoMode", PO_MODE_DRAFT);
                            currPo.setAttribute("EoId", eo);
                            RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "V");


                            DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("MmDrftPoIterator");
                            Key parentKey = parentIter.getCurrentRow().getKey();
                            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                            operationBinding.execute();
                            if (operationBinding.getErrors().size() == 0) {
                                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));


                                OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                                WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                                WfnoOp.getParamsMap().put("cld_id", cld_id);
                                WfnoOp.getParamsMap().put("org_id", OrgId);
                                WfnoOp.getParamsMap().put("doc_no", PO_DOC_NO);
                                WfnoOp.execute();
                                String WfNum = null;
                                Integer level = 0;
                                if (WfnoOp.getResult() != null) {
                                    WfNum = WfnoOp.getResult().toString();
                                }

                                if (WfNum != null) {
                                    OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                                    usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                                    usrLevelOp.getParamsMap().put("CldId", cld_id);
                                    usrLevelOp.getParamsMap().put("OrgId", OrgId);
                                    usrLevelOp.getParamsMap().put("usr_id", usr_id);
                                    usrLevelOp.getParamsMap().put("WfNum", WfNum);
                                    usrLevelOp.getParamsMap().put("PoDocId", PO_DOC_NO);
                                    usrLevelOp.execute();
                                    level = -1;
                                    if (usrLevelOp.getResult() != null) {
                                        if (usrLevelOp.getResult() != null)
                                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                                    }
                                    if (level == -1) {
                                        FacesMessage message =
                                            new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    } else {
                                        Integer res = -1;
                                        String action = "I";
                                        String remark = "A";
                                        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                                        insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                                        insTxnOp.getParamsMap().put("cld_id", cld_id);
                                        insTxnOp.getParamsMap().put("pOrgId", OrgId);
                                        insTxnOp.getParamsMap().put("PO_DOC_NO", PO_DOC_NO);
                                        insTxnOp.getParamsMap().put("WfNum", WfNum);
                                        insTxnOp.getParamsMap().put("poDocId", poDocId);
                                        insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                                        insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                                        insTxnOp.getParamsMap().put("levelFrm", level);
                                        insTxnOp.getParamsMap().put("levelTo", level);
                                        insTxnOp.getParamsMap().put("action", action);
                                        insTxnOp.getParamsMap().put("remark", remark);
                                        insTxnOp.getParamsMap().put("amount", currPo.getAttribute("OpenOrdVal"));
                                        insTxnOp.getParamsMap().put("post", "S");
                                        insTxnOp.execute();

                                        if (insTxnOp.getResult() != null) {
                                            res = Integer.parseInt(insTxnOp.getResult().toString());
                                        }
                                        if (res == 1) {
                                            operationBinding.execute(); //commit
                                        } else {
                                            System.out.println("Failed to Insert to WF");
                                        }
                                        RichPanelTabbed richPanelTabbed = getPanelTabed();
                                        for (UIComponent child : richPanelTabbed.getChildren()) {
                                            RichShowDetailItem sdi = (RichShowDetailItem)child;
                                            if (sdi.getClientId().equals(supAndItmTab.getClientId())) {
                                                setShowItemsTab("false");
                                                setShowDlvSchdlTab("false");
                                                setShowPaymentSchdlTab("false");
                                                setShowTnAgreeTab("false");
                                            }
                                            if (sdi.getClientId().equals(termsAndAggTab.getClientId()))
                                                sdi.setDisclosed(true);
                                            else
                                                sdi.setDisclosed(false);
                                        }

                                        StringBuilder msg =
                                            new StringBuilder("<html><body><p>Open Purchase Order - <b>" +
                                                              currPo.getAttribute("PoId").toString() +
                                                              "</b> Saved Successfully.</p>");
                                        msg.append("</body></html>");
                                        FacesMessage message = new FacesMessage(msg.toString());
                                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    }
                                } else {
                                    FacesMessage message = new FacesMessage("Workflow not Defined for PO.");
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                }
                            } else {
                                FacesMessage message = new FacesMessage("Something went wrong.Contact to ESS!");
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }
                        } else {
                            FacesMessage message = new FacesMessage("Date is not in valid Financial Year.");
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);

                        }
                    }
                }
            }
        } else if (currPo.getAttribute("PoType") != null) {
            setShowItemsTab("false");
            setShowDlvSchdlTab("false");
            setShowPaymentSchdlTab("false");
            setShowTnAgreeTab("false");

            int dlvRows = getAm().getDlvSchdlBalanceView().getAllRowsInRange().length;
            //  System.out.println("No. of DlvRows:"+dlvRows);

            Object obj1 = currPo.getAttribute("TransSumPoPmtAmt");
            Object obj2 = currPo.getAttribute("TransTotalPoCostSp");
            Object obj3 = currPo.getAttribute("TransTotalPoCostBs");
            Object obj4 = currPo.getAttribute("TransCurrencyDesc");

            Number zero = new Number(0);

            Number poCost = zero;
            Number poPmtAmt = zero;
            Number poCostBs = zero;
            String spCur = "INR";

            if (obj1 != null) {
                poCost = (Number)obj2;
            }
            if (obj2 != null) {
                poPmtAmt = (Number)obj1;
            }
            if (obj3 != null) {
                poCostBs = (Number)obj3;
            }
            if (obj4 != null) {
                spCur = obj4.toString();
            }

            // System.out.println(spCur+"---"+poCost+"---POCOST----POPMTAMT--"+poPmtAmt+"---dlvrws--"+dlvRows);

            if (dlvRows == 0 && poCost.compareTo(zero) == 1 && poPmtAmt.compareTo(zero) == 1 &&
                poCost.compareTo(poPmtAmt) == 0) {
                //  System.out.println("In sAVE MSG");
                currPo.setAttribute("PoMode", PO_MODE_DRAFT);
                // getAm().getDBTransaction().postChanges();
                //   System.out.println("First Eo Id is----->" + po.getCurrentRow().getAttribute("EoId"));
                RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "V");


                // System.out.println("Eo Id is----->" + po.getCurrentRow().getAttribute("EoId"));
                RichPanelTabbed richPanelTabbed = getPanelTabed();
                DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("MmDrftPoIterator");
                Key parentKey = parentIter.getCurrentRow().getKey();
                //    System.out.println("Parent key is---->"+parentKey);
                //                            parentIter.executeQuery();
                /*   OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute1");
            operationBinding1.execute(); */
                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                operationBinding.execute();

                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                if (operationBinding.getErrors().isEmpty()) {
                    /**
                *  Insert entry into WF items..
              * */

                    String wf_id = "W034";
                    String action = "I";
                    String remark = "A";

                    OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                    WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                    WfnoOp.getParamsMap().put("cld_id", cld_id);
                    WfnoOp.getParamsMap().put("org_id", OrgId);
                    WfnoOp.getParamsMap().put("doc_no", PO_DOC_NO);
                    WfnoOp.execute();
                    String WfNum = null;
                    Integer level = -1;
                    if (WfnoOp.getResult() != null) {
                        WfNum = WfnoOp.getResult().toString();
                    }

                    if (WfNum != null) {
                        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                        usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                        usrLevelOp.getParamsMap().put("CldId", cld_id);
                        usrLevelOp.getParamsMap().put("OrgId", OrgId);
                        usrLevelOp.getParamsMap().put("usr_id", usr_id);
                        usrLevelOp.getParamsMap().put("WfNum", WfNum);
                        usrLevelOp.getParamsMap().put("PoDocId", PO_DOC_NO);
                        usrLevelOp.execute();

                        if (usrLevelOp.getResult() != null) {
                            if (usrLevelOp.getResult() != null)
                                level = Integer.parseInt(usrLevelOp.getResult().toString());
                        }
                        if (level == -1) {
                            FacesMessage message =
                                new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else {
                            // String WfNum=getWf_no(sloc_id,cld_id,OrgId,PO_DOC_NO);
                            /* Integer level =Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_USR_LEVEL(?,?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                        cld_id,
                                                                                                                        OrgId,
                                                                                                                        usr_id,
                                                                                                                        WfNum,
                                                                                                                        PO_DOC_NO }).toString());


             */System.out.println(sloc_id + "-SAS---" + cld_id + "---" + OrgId + "--WFNUM--" + WfNum + "----" + wf_id +
                                  "------" + "-----" + usr_id + "--" + usr_id + "---" + level + "---" + action +
                                  "---" + remark + "---");

                            Integer res = -1;
                            Number amount11 = (Number)currPo.getAttribute("PoAmtBs");
                            /*    Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                        new Object[] { sloc_id, cld_id, OrgId, PO_DOC_NO, WfNum, poDocId,
                                                                       usr_id, usr_id, level, level, action, remark,
                                                                       new Number(0) }).toString());
                 */

                            OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                            insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                            insTxnOp.getParamsMap().put("cld_id", cld_id);
                            insTxnOp.getParamsMap().put("pOrgId", OrgId);
                            insTxnOp.getParamsMap().put("PO_DOC_NO", PO_DOC_NO);
                            insTxnOp.getParamsMap().put("WfNum", WfNum);
                            insTxnOp.getParamsMap().put("poDocId", poDocId);
                            insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                            insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                            insTxnOp.getParamsMap().put("levelFrm", level);
                            insTxnOp.getParamsMap().put("levelTo", level);
                            insTxnOp.getParamsMap().put("action", action);
                            insTxnOp.getParamsMap().put("remark", remark);
                            insTxnOp.getParamsMap().put("amount", amount11);
                            insTxnOp.getParamsMap().put("post", "S");
                            insTxnOp.execute();

                            if (insTxnOp.getResult() != null) {
                                res = Integer.parseInt(insTxnOp.getResult().toString());
                            }

                            System.out.println("res in save PO-" + res);
                            if (res == 1) {
                                operationBinding.execute(); //commit
                            } else {
                                System.out.println("Failed to Insert to WF");
                            }

                            for (UIComponent child : richPanelTabbed.getChildren()) {
                                RichShowDetailItem sdi = (RichShowDetailItem)child;
                                //pt1:drMM1:2:sdi1
                                if (sdi.getClientId().equals(supAndItmTab.getClientId())) {
                                    setShowItemsTab("false");
                                    setShowDlvSchdlTab("false");
                                    setShowPaymentSchdlTab("false");
                                    setShowTnAgreeTab("false");
                                    sdi.setDisclosed(true);
                                    /*  String mode="D";
                    currPo.setAttribute("PoMode", mode);
                    */
                                    // setShowPaymentSchdlTab("false");
                                } else {
                                    sdi.setDisclosed(false);
                                }
                            }
                            /* StringBuilder msg=new StringBuilder("<html><body><p>Purchase Order - <b>"+currPo.getAttribute("PoId").toString()+"</b> Saved Successfully.</p>");
                   msg.append("<p>Total Amount Specific :<b>"+poCost+"</b></p>");
                   msg.append("<p>Total Amount Base :<b>"+poCostBs+"</b></p>");
                   msg.append("</body></html>"); */

                            StringBuilder msg =
                                new StringBuilder("<html><body><p>Purchase Order - <b>" + currPo.getAttribute("PoId").toString() +
                                                  "</b> Saved Successfully.</p>");
                            msg.append("<ul> <li>Total Amount Specific : <b>" + poCost + "</b> " + spCur + "</li>");
                            msg.append("<li>Total Amount Base &nbsp;&nbsp;&nbsp;&nbsp;    : <b>" + poCostBs +
                                       "</b> INR</li></ul>");
                            msg.append("</body></html>");

                            FacesMessage message = new FacesMessage(msg.toString());
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage("Workflow not Defined for PO.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                } else {
                    System.out.println("----error during commit-----");
                }
            } else if (getShowDlvSchdlTab().equalsIgnoreCase("false") && getShowItemsTab().equalsIgnoreCase("false") &&
                       getShowPaymentSchdlTab().equalsIgnoreCase("false")) {
                System.out.println("in save else--");
                RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "V");
                currPo.setAttribute("PoMode", PO_MODE_DRAFT);

                //getAm().getDBTransaction().postChanges();
                // getAm().getMmDrftPo().executeQuery();
                // getAm().getDBTransaction().commit();
                DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("MmDrftPoIterator");
                Key parentKey = parentIter.getCurrentRow().getKey();
                System.out.println("Parent key is---->" + parentKey);


                OperationBinding operationBinding2 = getBindings().getOperationBinding("Execute");
                operationBinding2.execute();
                /*  OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute1");
            operationBinding1.execute(); */

                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                operationBinding.execute();
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                if (operationBinding.getErrors().isEmpty()) {
                    /**
                   *  Insert entry into WF items..
                 * */

                    String wf_id = "W034";
                    String action = "I";
                    String remark = "A";
                    //  String WfNum=getWf_no(sloc_id,cld_id,OrgId,PO_DOC_NO);
                    String WfNum = null;
                    Integer level = -1;

                    OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                    WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                    WfnoOp.getParamsMap().put("cld_id", cld_id);
                    WfnoOp.getParamsMap().put("org_id", OrgId);
                    WfnoOp.getParamsMap().put("doc_no", PO_DOC_NO);
                    WfnoOp.execute();

                    if (WfnoOp.getResult() != null) {
                        WfNum = WfnoOp.getResult().toString();
                    }
                    if (WfNum != null) {
                        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                        usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                        usrLevelOp.getParamsMap().put("CldId", cld_id);
                        usrLevelOp.getParamsMap().put("OrgId", OrgId);
                        usrLevelOp.getParamsMap().put("usr_id", usr_id);
                        usrLevelOp.getParamsMap().put("WfNum", WfNum);
                        usrLevelOp.getParamsMap().put("PoDocId", PO_DOC_NO);
                        usrLevelOp.execute();

                        if (usrLevelOp.getResult() != null) {
                            if (usrLevelOp.getResult() != null)
                                level = Integer.parseInt(usrLevelOp.getResult().toString());
                        }
                        if (level == -1) {
                            FacesMessage message =
                                new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else {
                            /* Integer level =Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_USR_LEVEL(?,?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                           cld_id,
                                                                                                                           OrgId,
                                                                                                                           usr_id,
                                                                                                                           WfNum,
                                                                                                                           PO_DOC_NO }).toString());


                 */System.out.println(sloc_id + "-WFFFF---" + cld_id + "---" + OrgId + "----" + "----" + wf_id +
                                      "------" + "-----" + usr_id + "--" + usr_id + "---" + level + "---" + action +
                                      "---" + remark + "---");

                            Integer res = -1;
                            Number amount11 = (Number)currPo.getAttribute("PoAmtBs");
                            /*  Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                           new Object[] { sloc_id, cld_id, OrgId, PO_DOC_NO, WfNum, poDocId,
                                                                          usr_id, usr_id, level, level, action, remark,
                                                                          new Number(0) }).toString());
                 */
                            OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                            insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                            insTxnOp.getParamsMap().put("cld_id", cld_id);
                            insTxnOp.getParamsMap().put("pOrgId", OrgId);
                            insTxnOp.getParamsMap().put("PO_DOC_NO", PO_DOC_NO);
                            insTxnOp.getParamsMap().put("WfNum", WfNum);
                            insTxnOp.getParamsMap().put("poDocId", poDocId);
                            insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                            insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                            insTxnOp.getParamsMap().put("levelFrm", level);
                            insTxnOp.getParamsMap().put("levelTo", level);
                            insTxnOp.getParamsMap().put("action", action);
                            insTxnOp.getParamsMap().put("remark", remark);
                            insTxnOp.getParamsMap().put("amount", amount11);
                            insTxnOp.getParamsMap().put("post", "S");
                            insTxnOp.execute();

                            if (insTxnOp.getResult() != null) {
                                res = Integer.parseInt(insTxnOp.getResult().toString());
                            }

                            if (res == 1) {
                                operationBinding.execute(); //commit
                            } else {
                                System.out.println("Failed to Insert to WF");
                            }
                            RichPanelTabbed richPanelTabbed = getPanelTabed();

                            for (UIComponent child : richPanelTabbed.getChildren()) {
                                RichShowDetailItem sdi = (RichShowDetailItem)child;
                                //pt1:drMM1:2:sdi1
                                if (sdi.getClientId().equals(supAndItmTab.getClientId())) {
                                    setShowItemsTab("false");
                                    setShowDlvSchdlTab("false");
                                    setShowPaymentSchdlTab("false");
                                    setShowTnAgreeTab("false");
                                    sdi.setDisclosed(true);
                                    // setShowPaymentSchdlTab("false");
                                } else {
                                    sdi.setDisclosed(false);
                                }
                            }

                            StringBuilder msg =
                                new StringBuilder("<html><body><p>Purchase Order - <b>" + currPo.getAttribute("PoId").toString() +
                                                  "</b> Saved Successfully.</p>");
                            msg.append("<ul> <li>Total Amount Specific : <b>" + poCost + "</b> " + spCur + "</li>");
                            msg.append("<li>Total Amount Base &nbsp;&nbsp;&nbsp;&nbsp;    : <b>" + poCostBs +
                                       "</b> INR</li></ul>");
                            msg.append("</body></html>");

                            FacesMessage message = new FacesMessage(msg.toString());
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage("Workflow not Defined for PO.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                } else {
                    System.out.println("----error during commit-----");
                }
            } else if (dlvRows != 0) {
                currPo.setAttribute("PoMode", PO_MODE_INCOMPLETE);
                // getAm().getDBTransaction().postChanges();
                // getAm().getMmDrftPo().executeQuery();
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.404']}"));
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else if (poCost.compareTo(poPmtAmt) == 1 || poCost.compareTo(poPmtAmt) == -1) {
                currPo.setAttribute("PoMode", PO_MODE_INCOMPLETE);
                // getAm().getDBTransaction().postChanges();
                // getAm().getMmDrftPo().executeQuery();
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.406']}"));
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage("Please Select PO Type.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

    }

    /**
     *  Method to Go Back to Search Page
     * */

    public String exitButton() {
        disclosedItem.setDisclosed(true);
        //  disclosedSupplier.setDisclosed(false);
        /* System.out.println("DOC-ID in bean---->" +
                           RequestContext.getCurrentInstance().getPageFlowScope().get("DOC_ID")); */
        OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute");
        operationBinding1.execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "V");
        setShowFields(true);
        setShowItemsTab("true");
        setShowDlvSchdlTab("true");
        setShowPaymentSchdlTab("true");
        setShowTnAgreeTab("true");
        //poDtBind.setDisabled(false); //??--removed SM 16/7/2013
        RichPanelTabbed richPanelTabbed = getPanelTabed();
        for (UIComponent child : richPanelTabbed.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem)child;
            //pt1:drMM1:2:sdi1
            if (sdi.getClientId().equals(supAndItmTab.getClientId())) {
                sdi.setDisclosed(true);
                /* setShowItemsTab("false");
                  setShowDlvSchdlTab("false");
                  setShowPaymentSchdlTab("false");
                   */
            } else {
                sdi.setDisclosed(false);
            }
        }

        return "goBack";
    }

    public void setSelectChkBox(RichSelectBooleanCheckbox selectChkBox) {
        this.selectChkBox = selectChkBox;
    }

    public RichSelectBooleanCheckbox getSelectChkBox() {
        return selectChkBox;
    }

    public void setChkbxDisable(Boolean chkbxDisable) {
        this.chkbxDisable = chkbxDisable;
    }

    /**
     * VCE fro PO basis LOV
     * Depending on the value selected, null is inserted into unrelated attributes.
     * */

    public void poBasisVCE(ValueChangeEvent vce) {
        PurOrderAMImpl am = getAm();
        ViewObject po = am.getMmDrftPo();
        Row curr = po.getCurrentRow();

        String OrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        System.out.println("ORG ID=" + curr.getAttribute("OrgId"));
        if (curr.getAttribute("OrgId") == null) {

            curr.setAttribute("OrgId", OrgId);
            System.out.println("ORG ID=" + curr.getAttribute("OrgId"));

        }
        Integer newVal = 0;
        Integer oldVal = 0;
        if (vce.getNewValue() != null) {
            //QuotDocId , RefPoDocId , SoGrpId
            if (vce.getNewValue() != vce.getOldValue()) {
                newVal = Integer.parseInt(vce.getNewValue().toString());

                if (newVal.intValue() == PO_BASIS_TEMPLATE) { //template
                    curr.setAttribute("QuotDocId", null);
                    curr.setAttribute("SoGrpId", null);
                    curr.setAttribute("RefPoDocId", null);
                } else if (newVal.intValue() == PO_BASIS_SUGGORDER) { //suggested
                    curr.setAttribute("QuotDocId", null);
                    curr.setAttribute("RefPoDocId", null);
                    curr.setAttribute("TmplDocId", null);
                } else if (newVal.intValue() == PO_BASIS_QUOT) { //quotation
                    curr.setAttribute("SoGrpId", null);
                    curr.setAttribute("RefPoDocId", null);
                    curr.setAttribute("TmplDocId", null);
                } else if (newVal.intValue() == PO_BASIS_PREVPO) { //prev po
                    curr.setAttribute("SoGrpId", null);
                    curr.setAttribute("QuotDocId", null);
                    curr.setAttribute("RefPoDocId", null);
                    curr.setAttribute("TmplDocId", null);
                } else if (newVal.intValue() == PO_BASIS_RATECONTRCT) { //RC
                    curr.setAttribute("QuotDocId", null);
                    curr.setAttribute("SoGrpId", null);
                    curr.setAttribute("TmplDocId", null);
                }
            }
        }
    }

    public void setDisclosedItem(RichShowDetail disclosedItem) {
        this.disclosedItem = disclosedItem;
    }

    public RichShowDetail getDisclosedItem() {
        return disclosedItem;
    }

    public void setShowFields(Boolean showFields) {
        this.showFields = showFields;
    }

    public Boolean getShowFields() {
        //  Object mo = RequestContext.getCurrentInstance().getPageFlowScope().get("AddEditMode");
        /*  if(mo!=null){
           if(mo.toString().equalsIgnoreCase("A")){
               return true;
           }
           else if(mo.toString().equalsIgnoreCase("E") ){
               return true;
           }
       } */
        return showFields;
    }

    /**
     *   Action to perform when Edit Button is pressed
     * */

    public void editPoAction(ActionEvent actionEvent) {

        Row rr = getAm().getMmDrftPo().getCurrentRow();
        String doc_txn_id = rr.getAttribute("DocId").toString();
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        Integer pending = null;

        String wf_id = "W034";
        Integer level = 0;
        String action = "I";
        String remark = "A";
        //  System.out.println(sloc_id + "---" + org_id + "---" + cld_id + "-----" + doc_id + "----" + doc_txn_id);
        //   am.getDBTransaction().commit();
        /*   pending =
            Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_DOC_WF_CUR_USR(?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                       cld_id,
                                                                                                                       org_id,
                                                                                                                       PO_DOC_NO,
                                                                                                                       doc_txn_id }).toString());
       */ //   System.out.println(pending + "--------" + usr_id);

        OperationBinding chkUsr = getBindings().getOperationBinding("pendingPOCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_id);
        chkUsr.getParamsMap().put("CldId", cld_id);
        chkUsr.getParamsMap().put("OrgId", org_id);
        chkUsr.getParamsMap().put("PoDocNo", PO_DOC_NO);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }

        System.out.println(usr_id + "pending user id=" + pending);

        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_id) == 0) {

            setShowFields(true);
            disclosedItem.setDisclosed(true);
            ViewObject voPo = getAm().getMmDrftPo();
            Row currPo = voPo.getCurrentRow();
            currPo.setAttribute("PoMode", PO_MODE_INCOMPLETE);
            poDtBind.setDisabled(false);
            RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "E");

            if (currPo.getAttribute("PoType").equals(491)) {
            } else {
                RichPanelTabbed richPanelTabbed = getPanelTabed();
                for (UIComponent child : richPanelTabbed.getChildren()) {
                    RichShowDetailItem sdi = (RichShowDetailItem)child;
                    //pt1:drMM1:2:sdi1
                    if (sdi.getClientId().equals(supAndItmTab.getClientId())) {
                        sdi.setDisclosed(true);
                        setShowItemsTab("true");
                        setShowDlvSchdlTab("true");
                        setShowPaymentSchdlTab("true");
                        setShowTnAgreeTab("true");

                    } else {
                        sdi.setDisclosed(false);
                    }
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabed);
        } else { //pending != usr_id
            Row[] usrRows = getAm().getLovUsrId().getFilteredRows("UsrId", pending);
            StringBuffer usrName = new StringBuffer("");
            if (usrRows.length > 0) {
                usrName = new StringBuffer(usrRows[0].getAttribute("UsrName").toString());
                usrName = new StringBuffer("[").append(usrName).append("]");
            }

            String msg2 =
                "This Purchase Order is pending at other user " + usrName + " for approval, You can not edit this.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }
    }

    /**
     * VCE for PO Type -
     * */

    public void poTypeVCE(ValueChangeEvent vce) {
        Object vco = vce.getNewValue();
        PurOrderAMImpl am = getAm();
        ViewObject po = am.getMmDrftPo();
        Row currRow = po.getCurrentRow();
        currRow.setAttribute("EoId", null);
        currRow.setAttribute("PoBasis", null);
        eoNameTransBind.setValue("");
        poBasisBinding.setValue("");
        poBasisBinding.setValue(null);

        //  currRow.setAttribute("TransEoId", "");
        currRow.setAttribute("TransEoId", null);
        currRow.setAttribute("EntityId", 1);
        currRow.setAttribute("TaxRuleFlg", "P");
        currRow.setAttribute("TaxAfterDiscFlg", "Y");
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        System.out.println("VCO=" + vco);
        if (vco != null && vco.equals(491)) {
            System.out.println("Open Order");
            /*   setShowItemsTab("false");
                 setShowDlvSchdlTab("false");
                 setShowPaymentSchdlTab("false");
                 setShowTnAgreeTab("true"); */
            //currRow.setAttribute("PoBasis", 181);
            currRow.setAttribute("OpenOrdBasis", null);
            currRow.setAttribute("OpenOrdVal", 0);
        } else {
            System.out.println("Not an Open Order");
            /*  setShowItemsTab("true");
                 setShowDlvSchdlTab("false");
                 setShowPaymentSchdlTab("false");
                 setShowTnAgreeTab("false"); */
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(eoNameTransBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poBasisBinding);
        /*     if (vco != null) {
            Integer poType = Integer.parseInt(vco.toString());

            if (poType == PO_TYPE_DIRECT) { //direct po

                //  currRow.setAttribute("PoBasis",null);
                currRow.setAttribute("PoBasis", "");
                currRow.setAttribute("QuotDocId", "");
                // currRow.setAttribute("QuotDocId", null);
                currRow.setAttribute("SoGrpId", "");
                //   currRow.setAttribute("SoGrpId",null);
                currRow.setAttribute("RefPoDocId", "");
                //   currRow.setAttribute("RefPoDocId", null);
                currRow.setAttribute("TmplDocId", "");
                //  currRow.setAttribute("TmplDocId",null);
            } else if (poType == PO_TYPE_SCDLPO) { //scheduled po
                currRow.setAttribute("PoBasis", "");
                //   currRow.setAttribute("PoBasis",null);
                currRow.setAttribute("QuotDocId", "");
                //  currRow.setAttribute("QuotDocId", null);
                currRow.setAttribute("SoGrpId", "");
                // currRow.setAttribute("SoGrpId",null);
                currRow.setAttribute("RefPoDocId", "");
                //   currRow.setAttribute("RefPoDocId", null);
                currRow.setAttribute("TmplDocId", "");
                //   currRow.setAttribute("TmplDocId",null);

            } else if (poType == PO_TYPE_STD) { //standard po
                currRow.setAttribute("PoBasis", "");
                //currRow.setAttribute("PoBasis",null);
                currRow.setAttribute("QuotDocId", "");
                // currRow.setAttribute("QuotDocId", null);
                currRow.setAttribute("SoGrpId", "");
                //  currRow.setAttribute("SoGrpId",null);
                currRow.setAttribute("RefPoDocId", "");
                //   currRow.setAttribute("RefPoDocId", null);
                currRow.setAttribute("TmplDocId", "");
                //  currRow.setAttribute("TmplDocId",null);

            } else if (poType == PO_TYPE_RC) { //rc po
                currRow.setAttribute("PoBasis", "");
                //currRow.setAttribute("PoBasis",null);
                currRow.setAttribute("QuotDocId", "");
                //  currRow.setAttribute("QuotDocId", null);
                currRow.setAttribute("SoGrpId", "");
                //  currRow.setAttribute("SoGrpId",null);
                currRow.setAttribute("RefPoDocId", "");
                //  currRow.setAttribute("RefPoDocId", null);
                currRow.setAttribute("TmplDocId", "");
                //  currRow.setAttribute("TmplDocId",null);

            }

            currRow.setAttribute("TaxRuleFlg", "P");
            currRow.setAttribute("TaxAfterDiscFlg", "Y");
        } */
        if (currRow.getAttribute("OrgId") == null) {

            currRow.setAttribute("OrgId", OrgId);
        }
    }


    public void setTaxPopUp(RichPopup taxPopUp) {
        this.taxPopUp = taxPopUp;
    }

    public RichPopup getTaxPopUp() {
        return taxPopUp;
    }

    public void doneAction(ActionEvent actionEvent) {

        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        Integer UserId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String OrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");

        String DocId = getAm().getMmDrftPo().getCurrentRow().getAttribute("DocId").toString();
        String ItmId = getAm().getMmDrftPoItm().getCurrentRow().getAttribute("ItmId").toString();
        taxPopUp.hide();
    }


    public void addOtherChargesButton(ActionEvent actionEvent) {
        /*  OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters1");
        operationBinding.execute();
        ViewObject v1 = getAm().getMmDrftPoOc();
        if (operationBinding.getErrors().size() == 0) {
            ArrayList lst = new ArrayList(1);
            lst.add(v1.getCurrentRow().getKey());
            getOcTableBind().setActiveRowKey(lst);
        } */
        Integer coaId = null;
        Integer ocDesc = null;
        String ocType = "A";
        Number ocAmtSp = new Number(0);
        System.out.println("Coa Id=" + getAm().getMmDrftPo().getCurrentRow().getAttribute("TransCoaIdOC"));
        if (getAm().getMmDrftPo().getCurrentRow().getAttribute("TransCoaIdOC") != null)
            coaId = (Integer)getAm().getMmDrftPo().getCurrentRow().getAttribute("TransCoaIdOC");
        else {
            //Please select COA
            FacesMessage message = new FacesMessage("Please select COA Name.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(getCoaNmBind().getClientId(), message);
            //  return null;
        }
        System.out.println("Coa Id is not null");
        if (getAm().getMmDrftPo().getCurrentRow().getAttribute("TransOcDesc") != null) {
            ocDesc = (Integer)getAm().getMmDrftPo().getCurrentRow().getAttribute("TransOcDesc");
        } else {
            //Please select Description
            FacesMessage message = new FacesMessage("Please select Other Charge Description.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(getTransOcDescBind().getClientId(), message);
            // return null;
        }
        //ocType="A";

        /*  if(getAm().getMmDrftPo().getCurrentRow().getAttribute("TrandOcType")!=null)
            ocType = (String)getAm().getMmDrftPo().getCurrentRow().getAttribute("TrandOcType");
        else
        {
                FacesMessage message =new FacesMessage("Please select Transaction Type.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            return null;
            } */

        if (getAm().getMmDrftPo().getCurrentRow().getAttribute("TransOcAmt") != null) {
            if (((Number)getAm().getMmDrftPo().getCurrentRow().getAttribute("TransOcAmt")).compareTo(new Number(0)) >
                0) {
                ocAmtSp = (Number)getAm().getMmDrftPo().getCurrentRow().getAttribute("TransOcAmt");
            } else {
                FacesMessage message = new FacesMessage("Amount can not be Zero.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(getTransAmtBind().getClientId(), message);
                //  return null;
            }
        } else {
            //Please enter OC Amt
            FacesMessage message = new FacesMessage("Please Enter Other Charge Amount.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(getTransAmtBind().getClientId(), message);
            //return null;
        }
        if (coaId != null && ocAmtSp != null && ocAmtSp.compareTo(new Number(0)) > 0) {
            //Check for duplicate
            RowQualifier rq = new RowQualifier(getAm().getMmDrftPoOc());
            rq.setWhereClause("CldId='" + getAm().getMmDrftPo().getCurrentRow().getAttribute("CldId") +
                              "' and SlocId=" + getAm().getMmDrftPo().getCurrentRow().getAttribute("SlocId") +
                              " and OrgId='" + getAm().getMmDrftPo().getCurrentRow().getAttribute("OrgId") +
                              "' and DocId='" + getAm().getMmDrftPo().getCurrentRow().getAttribute("DocId") +
                              "' and CoaId=" + coaId + "");
            Row fr[] = getAm().getMmDrftPoOc().getFilteredRows(rq);
            adfLog.info("row found inquotation po oc  " + fr.length);
            if (fr.length > 0) {
                //Duplicate COA found
                FacesMessage message = new FacesMessage("COA already exist.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(getCoaNmBind().getClientId(), message);
                // return null;
            } else {
                Number convfctr = new Number(1);
                if (getAm().getMmDrftPo().getCurrentRow().getAttribute("CurrConvFctr") != null)
                    convfctr = (Number)getAm().getMmDrftPo().getCurrentRow().getAttribute("CurrConvFctr");
                Row r = getAm().getMmDrftPoOc().createRow();
                r.setAttribute("CldId", getAm().getMmDrftPo().getCurrentRow().getAttribute("CldId"));
                r.setAttribute("SlocId", getAm().getMmDrftPo().getCurrentRow().getAttribute("SlocId"));
                r.setAttribute("OrgId", getAm().getMmDrftPo().getCurrentRow().getAttribute("OrgId"));
                r.setAttribute("DocId", getAm().getMmDrftPo().getCurrentRow().getAttribute("DocId"));
                r.setAttribute("CoaId", coaId);
                r.setAttribute("OcDesc", ocDesc);
                r.setAttribute("TranType", ocType);
                r.setAttribute("OcAmtSp", ocAmtSp);
                r.setAttribute("TranspageCoaNm", getAm().getMmDrftPo().getCurrentRow().getAttribute("TransCoaOC"));
                r.setAttribute("OcAmtBs", ocAmtSp.multiply(convfctr));
                getAm().getMmDrftPoOc().insertRow(r);
                System.out.println("Row inserted");
                System.out.println("Values set to null");
                /*   ArrayList lst = new ArrayList(1);
        lst.add(r.getKey());
        getOcTableBind().setActiveRowKey(lst); */
                // return null;
            }
            coaNmBind.setValue(null);
            // ocDescBinding.setValue(null);
            transAmtBind.setValue(new Number(0));
            getAm().getMmDrftPo().getCurrentRow().setAttribute("TransCoaOC", null);
            //getAm().getMmQuot1().getCurrentRow().setAttribute("TransOcDesc",null);
            getAm().getMmDrftPo().getCurrentRow().setAttribute("TransCoaIdOC", null);
            getAm().getMmDrftPo().getCurrentRow().setAttribute("TransOcAmt", new Number(0));


        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);

    }

    /**
     *  DL for Other Charges
     * */

    public void otherChargesDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            ViewObject oc = getAm().getMmDrftPoOc();
            ViewObject po = getAm().getMmDrftPo();
            //System.out.println("OC--" + oc.getAllRowsInRange().length);
            Number amt = new Number(0);
            Number ocamt = new Number(0);
            Number zero = new Number(0);
            for (Row curr : oc.getAllRowsInRange()) {
                ocamt = (Number)curr.getAttribute("OcAmtBs");

                if (ocamt == null) {
                    ocamt = new Number(0);
                }
                if ("A".equalsIgnoreCase(curr.getAttribute("TranType").toString())) {
                    amt = amt.add(ocamt);
                } else if ("S".equalsIgnoreCase(curr.getAttribute("TranType").toString())) {
                    amt = amt.subtract(ocamt);
                }
            }
            oc.executeQuery();

            Number ocAmtTot = amt.add((Number)sumitemSpBind.getValue());
            if (zero.compareTo(ocAmtTot) >= 0) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.407']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                po.getCurrentRow().setAttribute("TransSumOcAmt", amt);
            }


            /*     //start
                   Number price=new Number();
                                  Number ordQty=new Number();
                                  Number disc=new Number();
                                  Number tax=new Number();
                                  Number cost=new Number();
                                  Number totalCost=new Number(0);
                                  Number totalOc=amt;
                                  Number ocPercTc=new Number(0);
                                  //get Total Cost
                                  RowSetIterator itmItr=getAm().getMmDrftPoItm().createRowSetIterator(null);
                                  while(itmItr.hasNext())
                                  {
                                      Row itmR=itmItr.next();
                                   price=zero;
                                   ordQty=zero;
                                   disc=zero;
                                      tax=zero;
                                      cost=zero;
                                      if(itmR.getAttribute("ItmPrice")!=null)
                                   price = (Number)itmR.getAttribute("ItmPrice");
                                      if(itmR.getAttribute("OrdQty")!=null)
                                   ordQty = (Number)itmR.getAttribute("OrdQty");
                                      if( itmR.getAttribute("TotDiscAmtSp"))
                                   disc = (Number)itmR.getAttribute("TotDiscAmtSp");
                                      if(itmR.getAttribute("TransItemTaxAmt")!=null)
                                   tax = (Number)itmR.getAttribute("TransItemTaxAmt");
                                  cost=(price.multiply(ordQty)).subtract(disc).add(tax);
                                  totalCost=totalCost.add(cost);
                                      }
                                  itmItr.closeRowSetIterator();

                                  ocPercTc=totalOc.multiply(new Number(100)).divide(totalCost);
            RowSetIterator rsi=getAm().getMmDrftPoItm().createRowSetIterator(null);
                           while(rsi.hasNext())
                           {
                               Row itmRow=rsi.next();

                               }
                                   */
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(transSumOcAmt);
        } else if (dialogEvent.getOutcome().name().equals("cancel")) {

        }
        coaNmBind.setValue(null);
        transAmtBind.setValue(new Number(0));
        getAm().getMmDrftPo().getCurrentRow().setAttribute("TransCoaOC", null);
        getAm().getMmDrftPo().getCurrentRow().setAttribute("TransOcAmt", new Number(0));

    }

    public void selectOcLink(ActionEvent actionEvent) {
        showPopup(ocPopUp, true);
    }

    public void setOcPopUp(RichPopup ocPopUp) {
        this.ocPopUp = ocPopUp;
    }

    public RichPopup getOcPopUp() {
        return ocPopUp;
    }

    /**
     *    VCE for Oc Amount Sp field
     * */

    public void ocAmtSpVCE(ValueChangeEvent vce) {
        Number amtSp = new Number(0);
        Number amtBs = new Number(0);
        Number currency = new Number(1);
        if (vce.getNewValue() != null) {
            amtSp = (Number)vce.getNewValue();
        }
        if (getAm().getMmDrftPo().getCurrentRow().getAttribute("CurrConvFctr") != null) {
            currency = (Number)getAm().getMmDrftPo().getCurrentRow().getAttribute("CurrConvFctr");
        }

        amtBs = amtSp.multiply(currency);

        Row currRow = getAm().getMmDrftPoOc().getCurrentRow();

        currRow.setAttribute("OcAmtBs", amtBs);
        currRow.setAttribute("TransOcAmtBs", amtBs);

    }

    /**
     *  delete Oc Action
     * */

    public void delOcAction(ActionEvent actionEvent) {
        ViewObject oc = getAm().getMmDrftPoOc();
        Row currRw = oc.getCurrentRow();
        if (currRw != null) {
            currRw.remove();
        }
        oc.executeQuery();
        //oc.clearCache();
    }

    /**
     *  Method To add Tax for item.
     * */

    public String taxItemAddAction() {
        adfLog.info("add Tax for item ::::::::::::::: ");
        ViewObjectImpl po = getAm().getMmDrftPo();
        Row poRow = po.getCurrentRow();

        ViewObject poVo = getAm().getMmDrftPoItm();
        Row itmCurr = poVo.getCurrentRow();
        if (itmCurr != null) {
            if (itmCurr.getAttribute("ItmPrice") != null && itmCurr.getAttribute("ItmPrice") != new Number(0) &&
                itmCurr.getAttribute("OrdQty") != null && itmCurr.getAttribute("OrdQty") != new Number(0) &&
                ((Number)itmPriceBind.getValue()).compareTo(zero) == 1 &&
                ((Number)itmQtyBind.getValue()).compareTo(zero) == 1) {

                String flg = (String)itmCurr.getAttribute("TransTaxExmptFlg");
                if (flg.equals("N")) {
                    ViewObject vo = getAm().getMmDrftPo();
                    String docId = (vo.getCurrentRow().getAttribute("DocId")).toString();
                    ViewObjectImpl trVo = getAm().getMmDrftPoTr1();
                    RowQualifier rqtr = new RowQualifier(trVo);
                    String itmId = itmCurr.getAttribute("ItmId").toString();
                    String itmuom = itmCurr.getAttribute("ItmUom").toString();
                    rqtr.setWhereClause("DocId='" + docId + "' AND ItmId='" + itmId + "' and ItmUom='" + itmuom + "'");
                    System.out.println("DocId=" + docId + " and  ItmId=" + itmId + "' and ItmUom='" + itmuom + "'");
                    Row[] r = trVo.getFilteredRows(rqtr);
                    System.out.println("No. of rows in tr of above ids=" + r.length);
                    String tflg = poVo.getCurrentRow().getAttribute("TransTaxChangedFlg").toString();
                    // System.out.println("Flag is="+tflg);


                    if (r.length > 0) {
                        itmtaxamt1 = new Number(Integer.parseInt(r[0].getAttribute("TaxableAmt").toString()));
                        taxRuleitem = (Integer)r[0].getAttribute("TaxRuleId");
                        if (r[0].getAttribute("TaxExmptFlg") !=
                            null) { // 12341 change for tax exmpt flg null when rate contranct thourch quotation.19-09-14
                            adfLog.info("::::::   " + r[0].getAttribute("TaxExmptFlg"));
                            taxExmpt = r[0].getAttribute("TaxExmptFlg").toString();
                        } else {
                            adfLog.info("tax TaxExmptFlg nulll    ::::::::");
                        }
                        if (r[0].getAttribute("TaxRuleFlg") != null) {
                            adfLog.info("::::::   " + r[0].getAttribute("TaxRuleFlg"));
                            taxruleflg = r[0].getAttribute("TaxRuleFlg").toString();
                        } else {
                            adfLog.info("tax TaxRuleFlg nulll    ::::::::");
                        }
                    }


                    if (r.length > 0 && tflg.equalsIgnoreCase("N")) {
                        showPopup(taxPopUp, true);
                    } else {

                        //remove tax if r.length>0 and tflg is "Y"
                        if (r.length > 0 && tflg.equalsIgnoreCase("Y")) {
                            ViewObjectImpl voline = getAm().getMmDrftPoTrLines1();

                            RowSetIterator rql = voline.createRowSetIterator(null);
                            while (rql.hasNext()) {
                                Row frline = rql.next();
                                if (frline.getAttribute("DocId").toString().equalsIgnoreCase(docId) &&
                                    frline.getAttribute("ItmId").toString().equalsIgnoreCase(itmId)) {
                                    //       System.out.println("Removing TRline="+frline.getKey());
                                    //    frline.remove();
                                }
                            }
                            rql.closeRowSetIterator();
                            voline.executeQuery();


                            RowSetIterator rstr = trVo.createRowSetIterator(null);
                            while (rstr.hasNext()) {
                                Row rtr = rstr.next();
                                if (rtr.getAttribute("DocId").toString().equalsIgnoreCase(docId) &&
                                    rtr.getAttribute("ItmId").toString().equalsIgnoreCase(itmId) &&
                                    rtr.getAttribute("ItmUom").toString().equalsIgnoreCase(itmuom)) {
                                    //   System.out.println("Removing TR="+rtr.getKey());
                                    rtr.remove();
                                }
                            }
                            rstr.closeRowSetIterator();
                            trVo.executeQuery();

                        }

                        OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters6");
                        operationBinding.execute();
                        adfLog.info("call tax  popup");
                        showPopup(taxPopUp, true);
                    }
                } else {
                    FacesMessage message = new FacesMessage("Tax is not Applicable on this Item");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }

            } else {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.408']}"));
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
        return null;
    }


    /**
     * Method to add Tax for the PO.
     * */
    public void addPoTaxAction(ActionEvent actionEvent) {
        //  System.out.println("Add tax for po");
        adfLog.info(" inside addPoTaxAction ::::::::::::::::::::::::: ");
        ViewObject vo = getAm().getMmDrftPo();
        String docId = (vo.getCurrentRow().getAttribute("DocId")).toString();
        ViewObjectImpl trVo = getAm().getMmDrftPoTr();
        RowQualifier rqtr = new RowQualifier(trVo);
        rqtr.setWhereClause("DocId='" + docId + "' and TaxRuleFlg='P'");
        ViewObject trlinevo = getAm().getMmDrftPoTrLines();
        Row[] r = trVo.getFilteredRows(rqtr);
        ViewObjectImpl itmvo = getAm().getMmDrftPoItm();
        String transflg = itmvo.getCurrentRow().getAttribute("TransTaxChangedFlg").toString();
        vo.getCurrentRow().setAttribute("TransRadio", "All");
        //check flag of yellow
        if (r.length > 0) {
            taxRule = (Integer)r[0].getAttribute("TaxRuleId");
            vo.getCurrentRow().setAttribute("transTaxRuleId", taxRule);

            RowQualifier radiotr = new RowQualifier(trVo);
            radiotr.setWhereClause("DocId='" + docId + "' and TaxRuleFlg='I'");
            Row[] radiorw = trVo.getFilteredRows(radiotr);
            if (radiorw.length > 0)
                vo.getCurrentRow().setAttribute("TransRadio", "Exclude");
            else
                vo.getCurrentRow().setAttribute("TransRadio", "All");
        }

        if (r.length > 0 && transflg.equalsIgnoreCase("N")) {
            taxRule = (Integer)r[0].getAttribute("TaxRuleId");
            showPopup(powiseSelectTaxPop, true);
        } else {
            //Remove all rows from tr
            adfLog.info("Inside  else ::::::::::::");
            RowSetIterator rsi = trVo.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row rw = rsi.next();
                if (rw.getAttribute("DocId").toString().equalsIgnoreCase(docId) &&
                    rw.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("P")) {
                }
            }
            rsi.closeRowSetIterator();
            trVo.executeQuery();
            adfLog.info("before show popup ::::::::::::");
            showPopup(powiseSelectTaxPop, true);
        }
    }


    public void taxCalcDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            AdfFacesContext.getCurrentInstance().addPartialTarget(poTaxAmtBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(resetTaxItemBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(resetTaxPOBind);
        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTaxAmtBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(resetTaxItemBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(resetTaxPOBind);
        }
    }


    /**
     * VCE for Tax value selected.-Func called MM.PKG_MM_PO.ins_drft_po_tr_lines
     * */

    public void taxRuleVCE(ValueChangeEvent vce) throws SQLException {
        if (vce.getNewValue() != null) {
            Integer taxid = Integer.parseInt(vce.getNewValue().toString());

            Row trcurr = getAm().getMmDrftPoTr1().getCurrentRow();
            Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();


            String p_doc_id = trcurr.getAttribute("DocId").toString();
            String p_po_item_id = null;
            String p_po_item_uom = null;
            Number p_curr_fctr = new Number(1);
            String taxexm = "N";
            if (trcurr.getAttribute("ItmId") != null) {
                p_po_item_id = trcurr.getAttribute("ItmId").toString();
            }
            if (trcurr.getAttribute("TaxExmptFlg") != null) {
                taxexm = trcurr.getAttribute("TaxExmptFlg").toString();
            }
            if (trcurr.getAttribute("ItmUom") != null) {
                p_po_item_uom = trcurr.getAttribute("ItmUom").toString();
            }
            Number p_taxable_amount = (Number)trcurr.getAttribute("TaxableAmt");
            trcurr.setAttribute("TaxRuleFlg", "I");
            if (getAm().getMmDrftPo().getCurrentRow().getAttribute("CurrConvFctr") != null) {
                p_curr_fctr = (Number)getAm().getMmDrftPo().getCurrentRow().getAttribute("CurrConvFctr");
            }
            System.out.println("Tax rule flg=I");
            BigDecimal ret =
                (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                               new Object[] { p_sloc_id, p_cldId, p_hoOrgId, p_org_id, p_doc_id,
                                                              p_po_item_id, p_po_item_uom, taxid, p_user_id,
                                                              p_taxable_amount, "I", p_curr_fctr, taxexm });


            Number retVal = zero;
            if (ret != null) {
                try {
                    retVal = new Number(ret);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Number retVal = new Number(ret);
            String flg = "N";
            Number zero = new Number(0);
            getAm().getMmDrftPoTrLines1().executeQuery();
            if (trcurr.getAttribute("TaxAmt") != null)
                flg = trcurr.getAttribute("TaxAmt").toString();
            if (flg.equals("Y")) {
                trcurr.setAttribute("TaxAmt", zero);
                getAm().getMmDrftPo().getCurrentRow().setAttribute("TransTotalTax", zero);
            } else {
                trcurr.setAttribute("TaxAmt", retVal);
                //   if (p_tax_rule_flg.equalsIgnoreCase("I")) {
                getAm().getMmDrftPoItm().getCurrentRow().setAttribute("TransItemTaxAmt", retVal);
                /*  } else if (p_tax_rule_flg.equalsIgnoreCase("P")) {
                getAm().getMmDrftPo().getCurrentRow().setAttribute("TransTotalTax", retVal);
            } */
            }
        }
    }

    public void taxPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) throws SQLException {

        //if no tax was applied than delete current applied tax here.. and do not create new. else inset into tr rule line function will delete those rows itself.
        if (taxRuleitem == null) {
            //  System.out.println("No tax was applied before");
            ViewObjectImpl tr = getAm().getMmDrftPoTr1();
            String docId = tr.getCurrentRow().getAttribute("DocId").toString();
            ViewObjectImpl trline = getAm().getMmDrftPoTrLinesVO1();
            ViewObjectImpl poitm = getAm().getMmDrftPoItm();
            /*  ViewObjectImpl povo=getAm().getMmDrftPo();
    RowSetIterator rti = trline.createRowSetIterator(null); */
            String itm = null;
            String itmuom = null;

            itm = (String)poitm.getCurrentRow().getAttribute("ItmId");
            itmuom = (String)poitm.getCurrentRow().getAttribute("ItmUom");
            //  System.out.println("ItmId="+itm);
            /*   while (rti.hasNext()) {
        Row rwtrline = rti.next();
        if(rwtrline.getAttribute("DocId")!=null && tr.getCurrentRow().getAttribute("DocId")!=null && itm != null && itmuom!=null)
        if(rwtrline.getAttribute("DocId").toString().equalsIgnoreCase(tr.getCurrentRow().getAttribute("DocId").toString()) && rwtrline.getAttribute("ItmId").toString().equalsIgnoreCase(itm) && rwtrline.getAttribute("ItmUom").toString().equalsIgnoreCase(itmuom))
        {
       // System.out.println("Row in trline remove="+rwtrline.getKey());
       // rwtrline.remove();
        }
    }
            rti.closeRowSetIterator(); */
            trline.executeQuery();
            getAm().getMmDrftPoTrLines().executeQuery();
            getAm().getMmDrftPoTrLines1().executeQuery();


            RowSetIterator rtr = tr.createRowSetIterator(null);
            while (rtr.hasNext()) {
                Row trow = rtr.next();
                if (trow.getAttribute("DocId").toString().equalsIgnoreCase(docId) &&
                    trow.getAttribute("ItmId").toString().equalsIgnoreCase(itm) &&
                    trow.getAttribute("ItmUom").toString().equalsIgnoreCase(itmuom)) {
                    //    System.out.println("removing row from tr="+trow.getKey());
                    trow.remove();
                }
            }

            rtr.closeRowSetIterator();
            tr.executeQuery();
            getAm().getMmDrftPoTr1().executeQuery();

        }
        TaxRuleId = 0;
        taxableAmount = new Number(0);
        Integer taxid1 = null;
        Number itmtaxamt = null;
        String taxflg = "N";
        String taxexmpt = "N";


        //create tax rule as itemise
        if (taxRuleitem != null) {
            //     System.out.println("TaxRuleItem="+taxRuleitem);
            //click on Select tax Itemwise
            ViewObjectImpl po2 = getAm().getMmDrftPo();
            ViewObject poVo2 = getAm().getMmDrftPoItm();
            Row itmCurr2 = poVo2.getCurrentRow();
            ViewObjectImpl trVo2 = getAm().getMmDrftPoTr1();
            String itmIds = itmCurr2.getAttribute("ItmId").toString();
            if (itmCurr2 != null) {
                if (itmCurr2.getAttribute("ItmPrice") != null || itmCurr2.getAttribute("ItmPrice") != new Number(0) ||
                    itmCurr2.getAttribute("OrdQty") != null || itmCurr2.getAttribute("OrdQty") != new Number(0)) {
                    ViewObject vo2 = getAm().getMmDrftPo();
                    String docId2 = (vo2.getCurrentRow().getAttribute("DocId")).toString();
                    RowQualifier rqtr2 = new RowQualifier(trVo2);
                    ViewObject trlineVo = getAm().getMmDrftPoTrLinesVO1();
                    String itmId2 = itmCurr2.getAttribute("ItmId").toString();
                    String itmuom2 = itmCurr2.getAttribute("ItmUom").toString();
                    rqtr2.setWhereClause("DocId='" + docId2 + "' AND ItmId='" + itmId2 + "' and ItmUom='" + itmuom2 +
                                         "'");
                    Row[] r2 = trVo2.getFilteredRows(rqtr2);
                    //  System.out.println("No of row in TR for this item and doc="+r2.length);
                    if (r2.length > 0) {
                        // System.out.println("row in TR for this item and docid is avaliabale");

                        RowSetIterator rs1in = trlineVo.createRowSetIterator(null);
                        while (rs1in.hasNext()) {
                            Row row = rs1in.next();
                            if (row.getAttribute("DocId") != null) {
                                if (row.getAttribute("DocId").toString().equalsIgnoreCase(docId2) &&
                                    row.getAttribute("ItmId").toString().equalsIgnoreCase(itmId2) &&
                                    row.getAttribute("ItmUom").toString().equalsIgnoreCase(itmuom2))
                                    System.out.println("Row in trline for this doc is removed for this doc and item id=" +
                                                       row.getKey());
                                //  row.remove();
                            }
                        }
                        rs1in.closeRowSetIterator();
                        trlineVo.executeQuery();
                        getAm().getMmDrftPoTrLines().executeQuery();
                        getAm().getMmDrftPoTrLines1().executeQuery();
                        RowSetIterator rstritm = trVo2.createRowSetIterator(null);
                        while (rstritm.hasNext()) {
                            Row rtrs = rstritm.next();
                            if (rtrs.getAttribute("DocId").toString().equalsIgnoreCase(docId2) &&
                                rtrs.getAttribute("ItmId").toString().equalsIgnoreCase(itmId2) &&
                                rtrs.getAttribute("ItmUom").toString().equalsIgnoreCase(itmuom2)) {
                                System.out.println("Removing row from Tr=" + rtrs.getKey());
                                rtrs.remove();
                            }
                        }
                        rstritm.closeRowSetIterator();
                        trVo2.executeQuery();
                        getAm().getMmDrftPoTr().executeQuery();
                    }


                    //    System.out.println("Now row in TR for this item and docid is not avaliabale");
                    OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters6");
                    operationBinding.execute();

                }
            }
            Row curr1 = trVo2.getCurrentRow();
            curr1.setAttribute("DocId", po2.getCurrentRow().getAttribute("DocId"));
            curr1.setAttribute("TaxRuleFlg", "I");
            curr1.setAttribute("ItmId", itmIds);
            curr1.setAttribute("TaxableAmt", itmtaxamt1);
            curr1.setAttribute("TaxExmptFlg", taxExmpt);
            //   System.out.println("New row created in tr and doc set");
            taxid1 = taxRuleitem;
            itmtaxamt = itmtaxamt1;
            itmtaxamt1 = null;
            taxRuleitem = null;
            taxflg = "N";
            taxexmpt = taxExmpt;
            taxExmpt = "N";
        }


        //value change listener--------
        if (taxid1 != null) {
            Row trcurr1 = getAm().getMmDrftPoTr1().getCurrentRow();
            trcurr1.setAttribute("TaxRuleId", taxid1);
            trcurr1.setAttribute("TaxRuleFlg", taxruleflg);
            Integer p_sloc_id1 = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            Integer p_user_id1 = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            String p_org_id1 = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String p_hoOrgId1 = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId1 = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();


            String p_doc_id1 = trcurr1.getAttribute("DocId").toString();
            String p_po_item_id1 = null;
            String p_po_item_uom1 = null;
            Number p_curr_fctr1 = new Number(1);
            if (trcurr1.getAttribute("ItmId") != null) {
                p_po_item_id1 = trcurr1.getAttribute("ItmId").toString();
                p_po_item_uom1 = trcurr1.getAttribute("ItmUom").toString();
            }
            //  System.out.println("Tax id at last="+taxid1+" and taxable amt="+itmtaxamt+" and item id="+p_po_item_id1);
            //Number p_taxable_amount1 = (Number)trcurr1.getAttribute("TaxableAmt");
            Number p_taxable_amount1 = itmtaxamt;
            String p_tax_rule_flg1 = taxruleflg;
            if (getAm().getMmDrftPo().getCurrentRow().getAttribute("CurrConvFctr") != null) {
                p_curr_fctr1 = (Number)getAm().getMmDrftPo().getCurrentRow().getAttribute("CurrConvFctr");
            }

            //code for check duplicate record
            ViewObjectImpl lineimpl = getAm().getMmDrftPoTrLinesVO1();
            RowQualifier rq = new RowQualifier(lineimpl);
            rq.setWhereClause("CldId='" + p_cldId1 + "' and SlocId=" + p_sloc_id1 + " and OrgId='" + p_org_id1 +
                              "' and DocId='" + p_doc_id1 + "' and ItmId='" + p_po_item_id1 + "' and TaxRuleId=" +
                              taxid1);
            //  System.out.println("Querry executed to count row in trline");
            //  System.out.println("CldId='"+p_cldId1+"' and SlocId="+p_sloc_id1+" and OrgId='"+p_org_id1+"' and DocId='"+p_doc_id1+"' and ItmId='"+p_po_item_id1+"' and TaxRuleId="+taxid1);
            Row[] linerowcount = lineimpl.getFilteredRows(rq);
            System.out.println("no. of rows in trline for same data=" + linerowcount.length);

            lineimpl.executeQuery();
            System.out.println("Tax rule flg=" + p_tax_rule_flg1);
            BigDecimal ret1 =
                (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                               new Object[] { p_sloc_id1, p_cldId1, p_hoOrgId1, p_org_id1, p_doc_id1,
                                                              p_po_item_id1, p_po_item_uom1, taxid1, p_user_id1,
                                                              p_taxable_amount1, p_tax_rule_flg1, p_curr_fctr1,
                                                              taxexmpt });
            System.out.println("tax amt=" + ret1);


            Number retVal1 = zero;
            if (ret1 != null) {
                try {
                    retVal1 = new Number(ret1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            // Number retVal1 = new Number(ret1);
            Number zero = new Number(0);
            trcurr1.setAttribute("TaxExmptFlg", taxflg);
            if (taxflg.equals("Y")) {
                trcurr1.setAttribute("TaxAmt", zero);
                getAm().getMmDrftPo().getCurrentRow().setAttribute("TransTotalTax", zero);
            } else {
                trcurr1.setAttribute("TaxAmt", retVal1);
                if (p_tax_rule_flg1.equalsIgnoreCase("I")) {
                    getAm().getMmDrftPoItm().getCurrentRow().setAttribute("TransItemTaxAmt", retVal1);
                } else if (p_tax_rule_flg1.equalsIgnoreCase("P")) {
                    getAm().getMmDrftPo().getCurrentRow().setAttribute("TransTotalTax", retVal1);
                }
            }

        }

        getAm().getMmDrftPo().executeQuery();
        getAm().getMmDrftPoTr1().executeQuery();
        getAm().getMmDrftPoTrLines1().executeQuery();
        getAm().getMmDrftPoTr().executeQuery();
        getAm().getMmDrftPoTrLines().executeQuery();
        System.out.println("Exit from cancel");
        //-------------


        AdfFacesContext.getCurrentInstance().addPartialTarget(poTaxAmtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBsBind);

    }


    public void setTaxRuleId(Integer TaxRuleId) {
        this.TaxRuleId = TaxRuleId;
    }

    public Integer getTaxRuleId() {
        return TaxRuleId;
    }


    /**
     * Method to add Terms and Agreements
     * */
    public void addTncAction(ActionEvent actionEvent) {
        ViewObject v1 = getAm().getMmDrftPoTnc();
        getAm().getLovTncId().executeQuery();
        OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters4");
        operationBinding.execute();
        getAm().getMmDrftPoTnc().getCurrentRow().setAttribute("TncId", null);

        if (operationBinding.getErrors().size() == 0) {
            /*   ArrayList lst = new ArrayList(1);
            lst.add(v1.getCurrentRow().getKey());
            getTncTableBind().setActiveRowKey(lst); */
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(termsAndAggTab);
    }


    /**
     * Method for saving the selected delivery schedule
     * */
    public void saveScheduleAction(ActionEvent actionEvent) {
        ViewObject dlvVo = getAm().getDlvSchdlBalanceView(); //from
        ViewObjectImpl mmdlvVo = getAm().getMmDrftPoDlvSchdl(); //to
        ViewObject mmPo = getAm().getMmDrftPo();
        ViewObjectImpl mmDrftItm = getAm().getMmDrftPoItm();
        // oracle.jbo.domain.Date d = (oracle.jbo.domain.Date)Date.getCurrentDate();
        Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Row poCurr = mmPo.getCurrentRow();
        RowSetIterator rsi = dlvVo.createRowSetIterator(null);
        if (poCurr.getAttribute("TransDlvDate") != null) {
            while (rsi.hasNext()) {
                Row cur = rsi.next();
                //    Object o = cur.getAttribute("TransSelectChkbx");
                Number dlvQty = new Number(0);
                if (cur.getAttribute("SchdlQty") != null)
                    dlvQty = (Number)cur.getAttribute("SchdlQty");
                if (dlvQty.compareTo(new Number(0)) > 0) {
                    //TransWhIdDlv
                    //  if (cur.getAttribute("SchdlQty") != null) {

                    String WhChk = resolvEl("#{pageFlowScope.GLBL_ORG_WH_CHK1}").toString();

                    if (WhChk.equals("N"))
                        poCurr.setAttribute("TransWhIdDlv", "1");
                    if (poCurr.getAttribute("TransWhIdDlv") != null) {
                        Date dt = (Date)dateDlvSchdl.getValue();
                        System.out.println("--date---" + dt);

                        /**Code Changed For Duplicate Validation 04-05-2013 @Ashish Kumar*/
                        String itmid = cur.getAttribute("ItemId").toString();
                        String itmuom = cur.getAttribute("ItmUom").toString();
                        System.out.println("ItemId--->" + itmid);
                        String whId = poCurr.getAttribute("TransWhIdDlv").toString();
                        RowQualifier rq = new RowQualifier((ViewObjectImpl)mmdlvVo);
                        rq.setWhereClause("ItmId='" + itmid + "' and ItmUom='" + itmuom + "' And DlvDt='" + dt +
                                          "' And WhId='" + whId + "'");
                        System.out.println("Row qul-->" + rq.getExprStr());
                        Row[] filteredRows = mmdlvVo.getFilteredRows(rq);
                        System.out.println("Filetr row count-->" + filteredRows.length);
                        if (filteredRows.length > 0) {
                            FacesMessage errMsg = new FacesMessage(resolvEl("#{bundle['MSG.410']}"));
                            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext ctx = FacesContext.getCurrentInstance();
                            ctx.addMessage(null, errMsg);
                            break;
                        } else {
                            rq.setWhereClause(null);

                            /** code to set delevery schedule no */
                            System.out.println("Delivery date=" + dt);
                            RowQualifier dsnrq = new RowQualifier(mmdlvVo);
                            dsnrq.setWhereClause("DlvDt='" + dt + "' And WhId='" + whId + "'");
                            Row[] dsnrow = mmdlvVo.getFilteredRows(dsnrq);
                            Integer dlvschdlno = 1;
                            System.out.println("No of rows for delv in same date=" + dsnrow.length);
                            if (dsnrow.length > 0)
                                dlvschdlno = (Integer)dsnrow[0].getAttribute("DlvSchdlNo");
                            else {
                                RowSetIterator dsnRS = mmdlvVo.createRowSetIterator(null);
                                while (dsnRS.hasNext()) {
                                    Row curdsn = dsnRS.next();
                                    if (curdsn.getAttribute("DlvSchdlNo") != null) {
                                        if ((Integer)curdsn.getAttribute("DlvSchdlNo") >= dlvschdlno) {
                                            dlvschdlno = (Integer)curdsn.getAttribute("DlvSchdlNo") + 1;
                                        }
                                    }
                                }
                                mmdlvVo.closeRowSetIterator();
                            }
                            /**Code ended for delevery schedule no*/
                            System.out.println("Delevery Schedule no=" + dlvschdlno);
                            Row dlvRow = mmdlvVo.createRow();
                            dlvRow.setAttribute("DocId", cur.getAttribute("DocId"));
                            dlvRow.setAttribute("SlocId", cur.getAttribute("SlocId"));
                            dlvRow.setAttribute("OrgId", cur.getAttribute("OrgId"));
                            dlvRow.setAttribute("ItmId", cur.getAttribute("ItemId"));
                            dlvRow.setAttribute("DlvDt", dt);
                            System.out.println("Scheduled Qty=" + cur.getAttribute("SchdlQty"));
                            dlvRow.setAttribute("DlvQty", cur.getAttribute("SchdlQty"));
                            dlvRow.setAttribute("BalQty", cur.getAttribute("SchdlQty"));
                            dlvRow.setAttribute("TmpRcptQty", new Number(0));
                            dlvRow.setAttribute("DlvMode", poCurr.getAttribute("TransDlvMode"));
                            dlvRow.setAttribute("WhId", poCurr.getAttribute("TransWhIdDlv"));
                            dlvRow.setAttribute("TotQty", cur.getAttribute("OrdQty"));

                            dlvRow.setAttribute("UsrIdCreate", p_user_id);
                            dlvRow.setAttribute("EntityId", new Integer(1));
                            dlvRow.setAttribute("DlvAddsId", poCurr.getAttribute("TransWhAddrID"));
                            dlvRow.setAttribute("DlvSchdlNo", dlvschdlno);
                            dlvRow.setAttribute("ItmUom", cur.getAttribute("ItmUom"));
                            RowQualifier rqitm = new RowQualifier(mmDrftItm);
                            rqitm.setWhereClause("CldId='" + cur.getAttribute("CldId") + "' and SlocId=" +
                                                 cur.getAttribute("SlocId") + " and OrgId='" +
                                                 cur.getAttribute("OrgId") + "' and ItmId='" +
                                                 cur.getAttribute("ItemId") + "' and ItmUom='" +
                                                 cur.getAttribute("ItmUom") + "'");
                            Row[] itmrow = mmDrftItm.getFilteredRows(rqitm);
                            if (itmrow.length > 0) {
                                dlvRow.setAttribute("TlrncDaysVal", poCurr.getAttribute("TlrncDays"));
                                dlvRow.setAttribute("TlrncQtyType", itmrow[0].getAttribute("TlrncQtyType"));
                                dlvRow.setAttribute("TlrncQtyVal", itmrow[0].getAttribute("TlrncQtyVal"));

                            }
                        }
                    } else {
                        FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.411']}"));
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }


                }
            }

            rsi.closeRowSetIterator();
            mmdlvVo.executeQuery();
            getAm().getDBTransaction().validate();
            getAm().getDBTransaction().postChanges();
            dlvVo.executeQuery();

            /*     DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("DlvSchdlBalanceViewIterator");
            parentIter.executeQuery(); */


            //set back to null
            /* if (Integer.parseInt(poCurr.getAttribute("PoType").toString()) == PO_TYPE_DIRECT) {
                mmdlvVo.setRangeSize(-1);
                Row[] rr = mmdlvVo.getAllRowsInRange();
                Date odt = null;
                if (rr.length > 0) {
                    odt = (Date)rr[0].getAttribute("DlvDt");
                }
                poCurr.setAttribute("TransDlvDate", odt);
            } else {
                poCurr.setAttribute("TransDlvDate", null);
                dateDlvSchdl.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(dateDlvSchdl);
            } */
            poCurr.setAttribute("TransDlvDate", null);
            dateDlvSchdl.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dateDlvSchdl);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poDlvSchlTable);


        } else {
            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.414']}"));
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(dateDlvSchdl.getClientId(), message);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(poDlvSchlTable);
    }

    public void setDlvSchdlTable(RichTable dlvSchdlTable) {
        this.dlvSchdlTable = dlvSchdlTable;
    }

    public RichTable getDlvSchdlTable() {
        return dlvSchdlTable;
    }

    public void schdlQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number qty = new Number(0);
        if (object != null) {
            qty = (Number)object;
        }
        //  Row currRow = getAm().getDlvSchdlBalanceView().getCurrentRow();
        // Number blnqty = (Number)currRow.getAttribute("BalanceQty");

        Number blnqty = (Number)balanceQtyBind.getValue();
        //System.out.println("Value from binding is-->"+blnqty+"and currect scdl qty is---->"+qty+"and from currenct row---->"+currRow.getAttribute("BalanceQty"));
        Number zero = new Number(0);

        if (qty.compareTo(zero) == 0) {
            String msg2 = resolvEl("#{bundle['MSG.337']}");
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        } else if (blnqty.compareTo(qty) == -1) {
            String msg2 = resolvEl("#{bundle['MSG.417']}");
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }
    }

    public void setDiscPopUp(RichPopup discPopUp) {
        this.discPopUp = discPopUp;
    }

    public RichPopup getDiscPopUp() {
        return discPopUp;
    }

    /**
     * Method to add Discount on the PO
     * */
    public void addPoDiscountAction(ActionEvent actionEvent) {
        if (getAm().getMmDrftPo().getCurrentRow().getAttribute("DiscVal") != null)
            poDiscount = (Number)getAm().getMmDrftPo().getCurrentRow().getAttribute("DiscVal");

        if (getAm().getMmDrftPo().getCurrentRow().getAttribute("DiscType") != null)
            poDiscType = getAm().getMmDrftPo().getCurrentRow().getAttribute("DiscType").toString();
        //  poDiscount=(Number)poDiscAmtBind.getValue();
        showPopup(discPopUp, true);
    }

    /**
     *  Method to save the Payment scheduled to the MM$DRFTPO$PMTSCHDL table
     * */

    public void DonePmtSchdlAction(ActionEvent actionEvent) {
        ViewObject pmtVo = getAm().getMmDrftPoPmtSchdl();
        ViewObject mmPo = getAm().getMmDrftPo();

        Row currPo = mmPo.getCurrentRow();

        if (currPo.getAttribute("TransPmtDate") != null) {
            Date dt = (Date)currPo.getAttribute("TransPmtDate");

            Date poDt = (Date)poDtBind.getValue();
            AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
            adfFacesContext.addPartialTarget(transPmtAdvFlgBind);
            String flg = transPmtAdvFlgBind.getValue().toString();

            if ("true".equalsIgnoreCase(flg) &&
                poDt.compareTo(dt) == -1) { //if PO Date is less than Payment Date & Advance Flag is checked.
                String msg2 = resolvEl("#{bundle['MSG.420']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message2);
                // throw new ValidatorException(message2);
            } else if ("false".equalsIgnoreCase(flg) &&
                       poDt.compareTo(dt) > 0) { //if PO Date is more than Payment Date & Advance Flag is unchecked.
                String msg2 = resolvEl("#{bundle['MSG.421']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message2);

            } else if (currPo.getAttribute("TransPmtAmt") != null) {
                Number amt = (Number)currPo.getAttribute("TransPmtAmt");
                Number zero = new Number(0);
                if (amt.compareTo(zero) == 1) {

                    Object fl = currPo.getAttribute("TransPmtAdvFlg");
                    String adv = "N";
                    if (fl != null) {
                        adv = currPo.getAttribute("TransPmtAdvFlg").toString();
                    }

                    Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
                    Number totAmt = (Number)currPo.getAttribute("TransTotalPoCostSp");
                    Integer payMode = PO_PAYMODE_CHEQUE;
                    if (currPo.getAttribute("TransPmtPayMode") != null) {
                        payMode = Integer.parseInt(currPo.getAttribute("TransPmtPayMode").toString());
                    }
                    if (dt != null && (amt != null || amt.compareTo(zero) == 1)) {
                        Row pmtCurr = pmtVo.createRow();
                        pmtCurr.setAttribute("PayDt", dt);
                        pmtCurr.setAttribute("PayAmt", amt);
                        pmtCurr.setAttribute("DocId", currPo.getAttribute("DocId"));
                        pmtCurr.setAttribute("EntityId", new Integer(1));
                        pmtCurr.setAttribute("OrgId", currPo.getAttribute("OrgId"));
                        pmtCurr.setAttribute("SlocId", currPo.getAttribute("SlocId"));
                        pmtCurr.setAttribute("TotAmt", totAmt);
                        pmtCurr.setAttribute("UsrIdCreate", p_user_id);
                        pmtCurr.setAttribute("AdvFlg", adv);
                        pmtCurr.setAttribute("PayMode", payMode);
                    }

                    Number num = (Number)currPo.getAttribute("TransSumPoPmtAmt");
                    Number su = (Number)currPo.getAttribute("TransTotalPoCostSp");
                    Number remain = su.subtract(num);
                    System.out.println("difference remaining=" + remain);
                    System.out.println("Add Edit mode=" + resolvEl("#{pageFlowScope.AddEditMode}"));
                    System.out.println("PaymentSchedule=" + showPaymentSchdlTab);
                    if (remain.compareTo(zero) == 0) {
                        currPo.setAttribute("TransPmtAmt", new Number(0));
                    } else if (remain.compareTo(zero) == 1) {
                        currPo.setAttribute("TransPmtAmt", remain);
                    }

                    currPo.setAttribute("TransPmtDate", null);
                    currPo.setAttribute("TransPmtAdvFlg", "N");

                    AdfFacesContext.getCurrentInstance().addPartialTarget(goToTandCButton);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(transPmtAmt);
                } else {
                    String msg2 = resolvEl("#{bundle['MSG.265']}");
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message2);
                }
            } else {
                String msg2 = resolvEl("#{bundle['MSG.422']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message2);
            }
        } else {
            String msg2 = resolvEl("#{bundle['MSG.423']}");
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
    }

    /**
     * Method for deleting the already saved Payment schedule.
     * */
    public void deletePmtSchdlAction(ActionEvent actionEvent) {
        ViewObject pmtVo = getAm().getMmDrftPoPmtSchdl();
        Row curr = pmtVo.getCurrentRow();
        if (curr != null) {
            curr.remove();
        }
        pmtVo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(transPmtAmt);

    }

    public void transPmtDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Date pmtDate = (Date)object;

    }

    public void transPmtAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number pmtAmt = (Number)object;
        if (pmtAmt == null) {

        } else {
            ViewObject drftPo = getAm().getMmDrftPo();

            Object obj = drftPo.getCurrentRow().getAttribute("TransSumPoPmtAmt");

            Number poAmtSp = (Number)drftPo.getCurrentRow().getAttribute("TransTotalPoCostSp");
            Number ptAmt = new Number(0); //sum of pmt amt
            // Number sumAmt=new Number(0);
            Number newAmt = new Number(0);
            if (obj != null) {
                ptAmt = (Number)obj;
            }
            System.out.println("total amount for PO(TransTotalPoCostSp)=" + poAmtSp);
            System.out.println("Amount paid(TransSumPoPmtAmt)=" + obj);
            System.out.println("Amount paying now=" + pmtAmt);
            newAmt = pmtAmt.add(ptAmt);
            if (newAmt.compareTo(poAmtSp) == 1) {
                String msg2 = resolvEl("#{bundle['MSG.424']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            System.out.println("Exit from transPmtAmtValidator");
        }
    }

    public void setTransPmtAmt(RichInputText transPmtAmt) {
        this.transPmtAmt = transPmtAmt;
    }

    public RichInputText getTransPmtAmt() {
        return transPmtAmt;
    }

    public void selectAllChkBxVCE(ValueChangeEvent valueChangeEvent) {

        Boolean bool = (Boolean)valueChangeEvent.getNewValue();
        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("DlvSchdlBalanceViewIterator");

        if (bool == null) {
            bool = false;
        }
        ViewObject dlvVo = getAm().getDlvSchdlBalanceView();
        RowSetIterator rsi = parentIter.getRowSetIterator();
        if (bool == true) {
            while (rsi.hasNext()) {
                rsi.next().setAttribute("TransSelectChkbx", true);
            }
        } else {
            while (rsi.hasNext()) {
                rsi.next().setAttribute("TransSelectChkbx", false);
            }
        }

        rsi.closeRowSetIterator();
        dlvVo.executeQuery();
        parentIter.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(dlvSchdlTable);
    }


    public void transCheckBxSchdlVCE(ValueChangeEvent valueChangeEvent) {
        Row curr = getAm().getMmDrftPo().getCurrentRow();
        Row schdlRow = getAm().getDlvSchdlBalanceView().getCurrentRow();
        /*  if(valueChangeEvent.getNewValue()!=null){
         String chkbx=valueChangeEvent.getNewValue().toString();
         System.out.println("chkbx:"+chkbx);
         if(Integer.parseInt(curr.getAttribute("PoType").toString())==PO_TYPE_DIRECT){
            //for directPO
            if("true".equalsIgnoreCase(chkbx)){
            Number quant=(Number)schdlRow.getAttribute("BalanceQty");
            schdlRow.setAttribute("SchdlQty", quant);
            System.out.println("quant:"+quant);
           }
         }

        } */
    }

    /**
     *  VCE for Previous PO selection lov -populate the items
     * */
    public void prevPoSelectVCE(ValueChangeEvent valueChangeEvent) {
        Object prev = valueChangeEvent.getNewValue();
        //MM.populate_from_prev_po (p_doc_id varchar2 , p_ref_doc_id varchar2 , p_usr_id number)
        if (prev != null) {
            ViewObject drftPo = getAm().getMmDrftPo();
            ViewObject drftPoItm = getAm().getMmDrftPoItm();
            Row curr = drftPo.getCurrentRow();
            String p_doc_id = curr.getAttribute("DocId").toString();
            String p_ref_doc_id = prev.toString();
            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();

            //MM.populate_from_prev_po (p_slocid in number,
            //                                p_orgid  in varchar2,
            //                                p_doc_id in varchar2 ,
            //                                p_ref_doc_id in varchar2 ,
            //                                p_usr_id in number)
            if (curr.getAttribute("PoType").equals(172) && curr.getAttribute("PoBasis").equals(490)) {
                RowQualifier rq = new RowQualifier(getAm().getMmDrftPo1());
                rq.setWhereClause("CldId='" + p_cldId + "' and OrgId='" + p_org_id + "' and SlocId=" + SlocId +
                                  " and DocId='" + p_ref_doc_id + "'");
                Row r[] = getAm().getMmDrftPo1().getFilteredRows(rq);
                if (r.length > 0) {
                    curr.setAttribute("EoId", r[0].getAttribute("EoId"));
                    curr.setAttribute("BillAddsId", r[0].getAttribute("BillAddsId"));
                    curr.setAttribute("CurrIdSp", r[0].getAttribute("CurrIdSp"));
                    curr.setAttribute("CurrConvFctr", r[0].getAttribute("CurrConvFctr"));
                }
            } else {
                String retval =
                    (String)callStoredFunction(STRING, "MM.PKG_MM_PO.populate_from_prev_po(?,?,?,?,?,?)", new Object[] { SlocId,
                                                                                                                         p_cldId,
                                                                                                                         p_org_id,
                                                                                                                         p_doc_id,
                                                                                                                         p_ref_doc_id,
                                                                                                                         p_user_id });
                //  System.out.println("populate from prevpo ---retval:" + retval);
                /*  if("Y".equalsIgnoreCase(retval)){
            curr.setAttribute("TaxRuleFlg", taxRuleFlg);
            curr.setAttribute("TaxAfterDiscFlg", taxAfterDiscFlg);
        } */
                if (("Y".equalsIgnoreCase(retval)) &&
                    Integer.parseInt(curr.getAttribute("PoBasis").toString()) == PO_BASIS_RATECONTRCT) {
                    RowSetIterator rsi = drftPoItm.createRowSetIterator(null);
                    Number zero = new Number(0);
                    while (rsi.hasNext()) {
                        Row rItm = rsi.next();
                        rItm.setAttribute("OrdQty", zero);
                    }
                    rsi.closeRowSetIterator();
                }

                getAm().getMmDrftPoItm().executeQuery();
                getAm().getMmDrftPoDlvSchdl().executeQuery();
                getAm().getMmDrftPoOc().executeQuery();
                getAm().getMmDrftPoPmtSchdl().executeQuery();
                getAm().getMmDrftPoTnc().executeQuery();
                getAm().getMmDrftPoTr().executeQuery();
                getAm().getMmDrftPoTrLines().executeQuery();
            }
            // System.out.println(ViewObject.QUERY_MODE_SCAN_VIEW_ROWS);
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumDiscValue);
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoNameTransBind);

        }
    }

    /**
     * VCE for Quotation selected from LOV- to populate the items
     * */

    public void quotDocIdVCE(ValueChangeEvent valueChangeEvent) {
        try {
            Object quot = valueChangeEvent.getNewValue();
            // System.out.println("In quotDoc VCE");
            if (quot == null) {
                RowSetIterator rsiItm = getAm().getMmDrftPoItm().createRowSetIterator(null);
                while (rsiItm.hasNext()) {
                    rsiItm.next().remove();
                }
                rsiItm.closeRowSetIterator();
            } else if (quot != null) {


                ViewObject drftPo = getAm().getMmDrftPo();
                Row curr = drftPo.getCurrentRow();
                // System.out.println("Quot=" + quot.toString());
                Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
                Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
                String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
                String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();

                String p_quot_id = quot.toString();
                String p_drft_doc_id = curr.getAttribute("DocId").toString();
                /*  MM.populate_from_quot_po (p_slocid in number,
                                           p_orgid  in varchar2,
                                           p_quot_id in varchar2 ,
                                           p_drft_doc_id in varchar2,
                                           p_usr_id in number)  */
                String retval =
                    (String)callStoredFunction(STRING, "MM.PKG_MM_PO.populate_from_quot_po(?,?,?,?,?,?)", new Object[] { SlocId,
                                                                                                                         p_cldId,
                                                                                                                         p_org_id,
                                                                                                                         p_quot_id,
                                                                                                                         p_drft_doc_id,
                                                                                                                         p_user_id });

                // System.out.println("---itm dis-" + curr.getAttribute("TransSumItmDiscount"));

                /*   ViewObject lovquot=getAm().getLovQuotDocId();
            lovquot.getFilteredRows("", p_quot_id);
             */
                /*  if(curr.getAttribute("TaxRuleFlg")==null){
                curr.setAttribute("TaxRuleFlg","P");
            }else{
                if("Q".equalsIgnoreCase(curr.getAttribute("TaxRuleFlg").toString())){
                    curr.setAttribute("TaxRuleFlg","P");
                }
            } */
                getAm().getMmDrftPoItm().executeQuery();
                getAm().getMmDrftPoDlvSchdl().executeQuery();
                getAm().getMmDrftPoOc().executeQuery();
                getAm().getMmDrftPoPmtSchdl().executeQuery();
                getAm().getMmDrftPoTnc().executeQuery();
                getAm().getMmDrftPoTr().executeQuery();
                getAm().getMmDrftPoTrLines().executeQuery();
                //getAm().getMmDrftPo().executeQuery();
                //getAm().getDBTransaction().postChanges();
                /*   DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("MmDrftPoIterator");
         parentIter.executeQuery();
         parentIter.allowsRefreshControl() */
                AdfFacesContext.getCurrentInstance().addPartialTarget(sumDiscValue);
                AdfFacesContext.getCurrentInstance().addPartialTarget(eoNameTransBind);
            }
        } catch (Exception e) {
            System.out.println("Exception is----->" + e.getMessage());
        }
    }

    /**
     * VCE for selected Template - populate the items
     * */

    public void tmplDocIdVCE(ValueChangeEvent valueChangeEvent) {
        Object tmpl = valueChangeEvent.getNewValue();
        if (tmpl != null) {
            ViewObject drftPo = getAm().getMmDrftPo();
            Row curr = drftPo.getCurrentRow();

            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();

            String p_tmpl_doc_id = tmpl.toString();
            String p_drft_doc_id = curr.getAttribute("DocId").toString();
            //MM.populate_from_tmpl_po (p_slocid in number,
            //                                 p_orgid  in varchar2,
            //                                 p_tmpl_doc_id in varchar2 ,
            //                                 p_drft_doc_id in varchar2,
            //                                 p_usr_id in number
            String retval =
                (String)callStoredFunction(STRING, "MM.PKG_MM_PO.populate_from_tmpl_po(?,?,?,?,?,?)", new Object[] { SlocId,
                                                                                                                     p_cldId,
                                                                                                                     p_org_id,
                                                                                                                     p_tmpl_doc_id,
                                                                                                                     p_drft_doc_id,
                                                                                                                     p_user_id });

            getAm().getMmDrftPoItm().executeQuery();
            getAm().getMmDrftPoDlvSchdl().executeQuery();
            getAm().getMmDrftPoOc().executeQuery();
            getAm().getMmDrftPoPmtSchdl().executeQuery();
            getAm().getMmDrftPoTnc().executeQuery();
            getAm().getMmDrftPoTr().executeQuery();
            getAm().getMmDrftPoTrLines().executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumDiscValue);
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoNameTransBind);
        }
    }

    /**
     * Save as template action
     * */

    public void saveAsTempAction(ActionEvent actionEvent) {
        Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
        System.out.println("SAT--" + getAm().getDBTransaction().isDirty());
        ViewObject drftPo = getAm().getMmDrftPo();
        Row curr = drftPo.getCurrentRow();
        String p_doc_id = curr.getAttribute("DocId").toString();
        if (getAm().getDBTransaction().isDirty() == false) {
            //MM.save_as_template_po (p_sloc_id number , p_org_id varchar2 , p_doc_id varchar2 , p_usr_id number)
            String retval =
                (String)callStoredFunction(STRING, "MM.PKG_MM_PO.save_as_template_po(?,?,?,?,?)", new Object[] { SlocId,
                                                                                                                 p_cldId,
                                                                                                                 p_org_id,
                                                                                                                 p_doc_id,
                                                                                                                 p_user_id });

            if ("Y".equalsIgnoreCase(retval)) {
                String msg2 = resolvEl("#{bundle['MSG.426']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, message2);
                getAm().getDBTransaction().commit();
            }

        }
    }


    public void setSumDiscValue(RichInputText sumDiscValue) {
        this.sumDiscValue = sumDiscValue;
    }

    public RichInputText getSumDiscValue() {
        return sumDiscValue;
    }

    /**
     * Method to poulate the suggested suppliers
     * */
    public void suggestSupplierAction(ActionEvent actionEvent) {

        ViewObject poItm = getAm().getMmDrftPoItm();
        //PROCEDURE PR_GET_SUPPLIERS (P_SLOCID IN NUMBER, P_ORGID IN VARCHAR2, P_USRID IN NUMBER, P_STRING IN VARCHAR2);
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();

        RowSetIterator rsi = poItm.createRowSetIterator(null);
        String itmNameConcat = "";
        while (rsi.hasNext()) {
            Row rw = rsi.next();
            if (rw.getAttribute("ItmId") != null) {
                itmNameConcat = itmNameConcat + "'" + rw.getAttribute("ItmId").toString() + "',";
            }
        }
        rsi.closeRowSetIterator();
        String strToProc = itmNameConcat.substring(0, itmNameConcat.length() - 1);
        //System.out.println(p_user_id + "----" + p_org_id + "---" + SlocId + "---" + strToProc.length() +"---ITM NAMES-->" + strToProc);
        try {
            callSimpleStoredProcedure("MM.PKG_MM_PO.PR_GET_SUPPLIERS(?,?,?,?,?,?)",
                                      new Object[] { SlocId, p_cldId, p_hoOrgId, p_org_id, p_user_id, strToProc });
        } catch (Exception jbo) {
            System.out.println("Exception :" + jbo.getMessage());
            BindingContext bctx = BindingContext.getCurrent();
            ((DCBindingContainer)bctx.getCurrentBindingsEntry()).reportException(jbo);

        }

        getAm().getMmTmpPoSuggSupp2().executeQuery();
        eoNameTransBind.setDisabled(true);
        showPopup(suggestSupPopUp, true);

    }

    /**
     * To show the History for the selected item
     * */

    public void itemHistoryAction(ActionEvent actionEvent) {

        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
        Date date = new Date();
        date = (Date)date.getCurrentDate();
        Row currPo = getAm().getMmDrftPo().getCurrentRow();
        if (currPo.getAttribute("PoDt") != null) {
            Date dt = (Date)currPo.getAttribute("PoDt");
            Integer fyid =
                (Integer)(callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] { p_cldId,
                                                                                                        p_org_id,
                                                                                                        dt }));
            Row currItm = getAm().getMmDrftPoItm().getCurrentRow();
            if (currItm.getAttribute("ItmId") != null) {
                //apply criteria of doc id
                String Doc = getAm().getMmDrftPo().getCurrentRow().getAttribute("DocId").toString();
                ViewObject vo = getAm().getMmPendOrdVw();
                ViewCriteria vc = getAm().getMmPendOrdVw().getViewCriteria("MmPendOrdVwVOCriteria");
                vo.applyViewCriteria(vc);
                vo.setNamedWhereClauseParam("itmIdBindvar", currItm.getAttribute("ItmId"));
                vo.executeQuery();
                showPopup(itmHistPopUp, true);
                String itmId = currItm.getAttribute("ItmId").toString();
                callStoredFunctionHistory("MM.PKG_MM_STK.GET_STK(?,?,?,?,?,?,?,?,?,?)",
                                          new Object[] { SlocId, p_cldId, itmId, fyid, p_org_id, date, null });
            } else {
                String msg2 = resolvEl("#{bundle['MSG.310']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message2);
            }
        } else {
            String msg2 = "Select PO Date";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
    }


    public void setAvl_stk(BigDecimal Avl_stk) {
        this.Avl_stk = Avl_stk;
    }

    public BigDecimal getAvl_stk() {
        return Avl_stk;
    }

    public void setReq_stk(BigDecimal Req_stk) {
        this.Req_stk = Req_stk;
    }

    public BigDecimal getReq_stk() {
        return Req_stk;
    }

    public void setOrd_stk(BigDecimal Ord_stk) {
        this.Ord_stk = Ord_stk;
    }

    public BigDecimal getOrd_stk() {
        return Ord_stk;
    }

    public void setItmHistPopUp(RichPopup itmHistPopUp) {
        this.itmHistPopUp = itmHistPopUp;
    }

    public RichPopup getItmHistPopUp() {
        return itmHistPopUp;
    }

    public void setItmTableBind(RichTable itmTableBind) {
        this.itmTableBind = itmTableBind;
    }

    public RichTable getItmTableBind() {
        return itmTableBind;
    }

    public void itmIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            /* Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
            String itmName = object.toString();
            ViewObject v = getAm().getMmDrftPoItm();
            String itmDesc = null;
            int totalCount = v.getRowCount(); //get ALL rows
            int rangeSize = v.getRangeSize(); //all in range
            v.setRangeSize(totalCount);
            Row[] rArray = v.getAllRowsInRange();

            //check for duplicate rows
            Row cRow = v.getCurrentRow();
            int count = 0;
            String currName = "";

            for (Row r : rArray) {

                if (!r.equals(cRow)) {
                    try {
                        currName = r.getAttribute("ItmId").toString();
                    } catch (NullPointerException npe) {
                        // System.out.println("NPE:" + npe);
                        currName = "";
                    }
                    adfLog.info(" itm id ::::: "+currName);
                    if (currName != null) {
                        ViewObjectImpl itmNewVo = getAm().getLovItmIdNew();
                            itmNewVo.setNamedWhereClauseParam("CldIdBind", p_cldId);
                            itmNewVo.setNamedWhereClauseParam("SlocIdBind", SlocId);
                            itmNewVo.setNamedWhereClauseParam("HoOrgIdBind", p_hoOrgId);
                            itmNewVo.setNamedWhereClauseParam("OrgIdBind", p_org_id);
                            itmNewVo.setNamedWhereClauseParam("ItmIdBind", currName);
                            itmNewVo.executeQuery();
                        Row[] xx = itmNewVo.getFilteredRows("ItmId", currName);
                        adfLog.info("     validator    "+xx.length);
                        if (xx.length > 0) {
                            itmDesc = xx[0].getAttribute("ItmDesc").toString();
                        }
                        if (itmName.equalsIgnoreCase(itmDesc)) {
                            count = count + 1;
                        }
                    }

                }

            }
            v.setRangeSize(rangeSize); */ //set to original range

            /*  if (count > 0) {
                String msg2 = resolvEl("#{bundle['MSG.427']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }*/
        } else {
            /*  FacesMessage message = new FacesMessage("Item Name is required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(itmIdBind.getClientId(), message); */
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvEl("#{bundle['MSG.430']}"), null));
        }
    }

    public void itmQuantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        Number val = (Number)object; //change BL 2/5
        Row curRow = getAm().getMmDrftPo().getCurrentRow(); //change BL 2/5
        Row itmCurRow = getAm().getMmDrftPoItm().getCurrentRow(); //change BL 2/5
        Integer basis = Integer.parseInt(curRow.getAttribute("PoBasis").toString()); //chage BL 2/5

        if (val != null) {
            // val = (Number)object;


            if (basis != PO_BASIS_RATECONTRCT) { //dont check for rate contract

                Number currFactor = new Number(1);
                Number price = new Number(0);
                /*     if(itmCurRow.getAttribute("TransCurrConvFctr")!=null){
                currFactor=(Number)itmCurRow.getAttribute("TransCurrConvFctr");
            }
         */
                //System.out.println(currFactor+"<--qty curr->"+itmCurRow.getAttribute("TransCurrConvFctr"));
                if (curRow.getAttribute("CurrConvFctr") != null) {
                    currFactor = (Number)curRow.getAttribute("CurrConvFctr");
                }
                //  System.out.println(currFactor+"<--qty curr->");
                if (itmPriceBind.getValue() != null) {
                    price = (Number)itmPriceBind.getValue();
                }

                Number tot = val.multiply(price.multiply(currFactor));
                Boolean totFlag = isPrecisionValid(26, 6, tot);

                if (totFlag.equals(false)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.405']}"), null));
                }
                if (itmIdBind.getValue() != null) {
                    Number zero = new Number(0);
                    if (val.compareTo(zero) == -1 || val.compareTo(zero) == 0) {
                        System.out.println("Vlaue 0 or less");
                        String msg2 = resolvEl("#{bundle['MSG.337']}"); // uncomment this --SM01/07
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                    }
                }
                String flag = "Y";
                Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
                String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
                String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
                Row currPo = getAm().getMmDrftPo().getCurrentRow();
                if (currPo.getAttribute("PoDt") != null) {
                    Date dt = (Date)currPo.getAttribute("PoDt");
                    Integer fyid =
                        (Integer)(callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] { p_cldId,
                                                                                                                p_org_id,
                                                                                                                dt }));
                    Row poItmRow = getAm().getMmDrftPoItm().getCurrentRow();
                    String p_quot_item_id = null;
                    if (poItmRow.getAttribute("ItmId") != null) {
                        p_quot_item_id = poItmRow.getAttribute("ItmId").toString();
                        if (p_quot_item_id != null && val != null) {
                            flag =
(String)callStoredFunction(Types.VARCHAR, "MM.PKG_MM_STK.IS_ORD_QTY_VALID(?,?,?,?,?,?,?)",
                           new Object[] { p_sloc_id, p_cldId, p_hoOrgId, p_org_id, p_quot_item_id, fyid, val });
                        }

                        if (flag.equalsIgnoreCase("N")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          resolvEl("#{bundle['MSG.306']}"), null));

                        }
                    }
                } else {
                    System.out.println("date not selected");
                    FacesMessage message = new FacesMessage("Select PO Date.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
                System.out.println("Check if Open Order");
                if (basis.toString().equals("490")) {
                    System.out.println("Open Order Selectd");
                    RowQualifier rq = new RowQualifier(getAm().getMmDrftPo1());
                    rq.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + p_sloc_id + " and OrgId='" + p_org_id +
                                      "' and DocId='" +
                                      getAm().getMmDrftPo().getCurrentRow().getAttribute("RefPoDocId") + "'");
                    Row[] r = getAm().getMmDrftPo1().getFilteredRows(rq);
                    if (r.length > 0 &&
                        (r[0].getAttribute("OpenOrdBasis").toString().equals("487") || r[0].getAttribute("OpenOrdBasis").toString().equals("489"))) {
                        BigDecimal retval =
                            (BigDecimal)(callStoredFunction(NUMBER, "MM.MM_GET_OPEN_ORD_VAL(?,?,?,?)", new Object[] { p_cldId,
                                                                                                                      p_sloc_id,
                                                                                                                      p_org_id,
                                                                                                                      getAm().getMmDrftPo().getCurrentRow().getAttribute("RefPoDocId") }));
                        Number rval = new Number(-1);
                        if (retval != null)
                            try {
                                rval = new Number(retval);
                            } catch (SQLException e) {
                                System.out.println("error in cast=" + e);
                            }
                        Number poQty = new Number(0);
                        Number poAmt = new Number(0);
                        Row[] fr =
                            getAm().getMmDrftPoItm().getFilteredRows("DocId", getAm().getMmDrftPo().getCurrentRow().getAttribute("DocId"));
                        for (Row frow : fr) {
                            Number ord = new Number(0);
                            if (frow.getAttribute("OrdQty") != null)
                                ord = (Number)frow.getAttribute("OrdQty");
                            Number amt = new Number(0);
                            if (frow.getAttribute("ItmPrice") != null)
                                amt = (Number)frow.getAttribute("ItmPrice");
                            poAmt = poAmt.add(ord.multiply(amt));
                            poQty = poQty.add(ord);
                        }
                        Number currRowQty = new Number(0);
                        if (getAm().getMmDrftPoItm().getCurrentRow().getAttribute("OrdQty") != null)
                            currRowQty = (Number)getAm().getMmDrftPoItm().getCurrentRow().getAttribute("OrdQty");

                        if (r[0].getAttribute("OpenOrdBasis").toString().equals("487")) {
                            if ((poQty.subtract(currRowQty).add(val)).compareTo(rval) > 0) {
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                              "PO Quantity exceeds remaining Open Order Quantity limit.[" +
                                                                              rval + "]", null));
                            }
                        } else if (r[0].getAttribute("OpenOrdBasis").toString().equals("489")) {
                            if ((poAmt.subtract(currRowQty.multiply(price)).add(val.multiply(price))).compareTo(rval) >
                                0) {
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                              "PO Amount exceeds remaining Open Order Amount limit.[" +
                                                                              rval + "]", null));
                            }
                        }

                    }

                }
            }
        } else if (val == null && basis == PO_BASIS_RATECONTRCT) { //change BL 2/5
            String msg2 = resolvEl("Invalid Quantity Value.");
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }
    }

    public void discountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number n = (Number)object;
        ViewObject vo = getAm().getMmDrftPoItm();
        Row rw = vo.getCurrentRow();
        Number itmPrice = new Number(0);
        Number quotQty = new Number(0);
        Number hundrd = new Number(100);
        Number zero = new Number(0);
        if (n.compareTo(zero) == 1) {
            if (itmPriceBind.getValue() != null) {
                itmPrice = (Number)itmPriceBind.getValue();
            }

            if (itmQtyBind.getValue() != null) {
                quotQty = (Number)itmQtyBind.getValue();
            }

            String discType = discTypeForItemBind.getValue().toString();
            //rw.getAttribute("DiscType").toString();

            Number total = (itmPrice).multiply(quotQty);

            if (discType.equals("A") && n.compareTo(total) == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.262']}") + total, null));
            }
            if (discType.equals("P") && (n.compareTo(hundrd) == 1 || n.compareTo(hundrd) == 0)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.303']}"), null));
            }
        } else if (n.compareTo(zero) == -1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvEl("#{bundle['MSG.305']}"), null));
        }
    }

    public void setDisHeader(Boolean disHeader) {
        this.disHeader = disHeader;
    }

    public Boolean getDisHeader() {
        ViewObject vo = getAm().getMmDrftPoItm();
        ViewObject votnc = getAm().getMmDrftPoTnc();

        if (vo.getCurrentRow() != null || votnc.getEstimatedRowCount() > 0) {
            return true;
        } else
            return false;
        //return disHeader;
    }

    public void itmDiscTypeVCE(ValueChangeEvent valueChangeEvent) {
        Row currRow = getAm().getMmDrftPoItm().getCurrentRow();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmDiscAmtBind);
        //if validation fails
        //itmDiscAmtBind.setValue(0);
        //currRow.setAttribute("DiscVal", new Number(0));
    }

    public void setSuggestSupPopUp(RichPopup suggestSupPopUp) {
        this.suggestSupPopUp = suggestSupPopUp;
    }

    public RichPopup getSuggestSupPopUp() {
        return suggestSupPopUp;
    }

    public void selectSuggSupplierDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("ok")) {
            eoNameTransBind.setDisabled(false);
            ViewObject sugSupVo = getAm().getMmTmpPoSuggSupp2(); //use sugg sup vo
            ViewObject poVo = getAm().getMmDrftPo();
            Row currPo = poVo.getCurrentRow();
            // RowSetIterator rsi= sugSupVo.createRowSetIterator(null);
            Row rw = sugSupVo.getCurrentRow();
            //    while(rsi.hasNext()){
            //         Row rw=rsi.next();
            //        System.out.println("--check--"+rw.getAttribute("TransCheckForSup"));
            //          if(rw.getAttribute("TransCheckForSup")!=null){ //make a transient
            //              if("Y".equalsIgnoreCase(rw.getAttribute("TransCheckForSup").toString())){
            if (rw != null) {
                currPo.setAttribute("TransBillAddsId", "");
                currPo.setAttribute("EoId", rw.getAttribute("EoId"));
                currPo.setAttribute("TransEoId", rw.getAttribute("EoNm"));


                currPo.setAttribute("CurrConvFctr", rw.getAttribute("ConvFctr"));
                currPo.setAttribute("CurrIdSp", rw.getAttribute("SuppCurrId"));

                getAm().getLovSuppAddress().executeQuery();
                currPo.setAttribute("BillAddsId", rw.getAttribute("DfltBillAddsId"));
                //    System.out.println("Supp Details--"+rw.getAttribute("EoId")+"----"+rw.getAttribute("EoNm")+"----"+rw.getAttribute("ConvFctr")+"----"+rw.getAttribute("SuppCurrId")+"---"+rw.getAttribute("DfltBillAddsId"));
            }

            RowSetIterator rsi = sugSupVo.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row rwSug = rsi.next();
                rwSug.remove();
            }

            rsi.closeRowSetIterator();
            sugSupVo.executeQuery();
            ///getAm().getMmDrftPo().executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoNameTransBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind); //change BL 2/5
        } else if (dialogEvent.getOutcome().name().equals("Cancel")) {
            eoNameTransBind.setDisabled(false);

            ViewObject sugSupVo = getAm().getMmTmpPoSuggSupp();
            RowSetIterator rsi = sugSupVo.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row rw = rsi.next();
                rw.remove();
            }
            rsi.closeRowSetIterator();
            sugSupVo.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoNameTransBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind); //change BL 2/5
        }
    }

    public void transSuggSuppChkBxVCE(ValueChangeEvent valueChangeEvent) {
        /*  Object vce=valueChangeEvent.getNewValue();
       ViewObject voSugSup= getAm().getMmTmpPoSuggSupp2();
       //RowSetIterator rsi= voSugSup.createRowSetIterator(null);
      // DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("MmTmpPoSuggSuppIterator");

       Row currRow=voSugSup.getCurrentRow();
       RowSetIterator rsi= voSugSup.createRowSetIterator(null);
       while(rsi.hasNext()){
           Row r=rsi.next();
           System.out.println("r-"+r);
           if(r.equals(currRow)){
               r.setAttribute("TransCheckForSup", "Y");
               System.out.println("1--"+r.getAttribute("EoId").toString());
          //     checkBxSugSup.setValue("Y");
           }else{

               r.setAttribute("TransCheckForSup", "N");
               System.out.println("2--"+r.getAttribute("EoId").toString());
           }
       }

       rsi.closeRowSetIterator();
       voSugSup.executeQuery();
       AdfFacesContext.getCurrentInstance().addPartialTarget(checkBxSugSup); */

    }

    /**
     * VCE - on selecting  the item
     * */
    public void itmNameVCE(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            /**
             * code for setting UomConvFctr @nit
             * */
            Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
            // ViewObjectImpl vitm = getAm().getLovItmId();
            ViewObjectImpl vitm = getAm().getLovItmDesc();
            vitm.setNamedWhereClauseParam("CldIdBind", p_cldId);
            vitm.setNamedWhereClauseParam("SlocIdBind", pslocId);
            vitm.setNamedWhereClauseParam("HoOrgIdBind", p_hoOrgId);
            vitm.setNamedWhereClauseParam("OrgIdBind", pOrgId);
            vitm.setNamedWhereClauseParam("ItmDescBind", vce.getNewValue());
            vitm.executeQuery();
            RowQualifier rqitm = new RowQualifier(vitm);
            rqitm.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and HoOrgId='" + p_hoOrgId +
                                 "' and ItmDesc='" + vce.getNewValue() + "'");

            Row ritm[] = vitm.getFilteredRows(rqitm);
            adfLog.info(ritm.length + "   Query is=" + rqitm.getExprStr());
            String itm = null;
            String uomBs = null;
            String uomPur = null;
            if (ritm.length > 0) {
                itm = (String)ritm[0].getAttribute("ItmId");
                adfLog.info("Item id=" + itm);
                getAm().getMmDrftPoItm().getCurrentRow().setAttribute("ItmId", itm);
                uomBs = (String)ritm[0].getAttribute("UomBasic");
                adfLog.info("uom basic=" + uomBs);
                uomPur = (String)ritm[0].getAttribute("UomPur");
                adfLog.info("uom Purchase=" + uomPur);
                getAm().getMmDrftPoItm().getCurrentRow().setAttribute("ItmUom", uomPur);
            }
            //  System.out.println("Orgid="+getAm().getMmDrftPoItm().getCurrentRow().getAttribute("OrgId"));
            ViewObjectImpl voUomVw = getAm().getLovUomVw1();
            /*  voUomVw.setNamedWhereClauseParam("itmBindVar", itm);
            voUomVw.executeQuery();  */
            adfLog.info("No. of rows in uomvw=" + voUomVw.getEstimatedRowCount());
            RowQualifier rquom = new RowQualifier(voUomVw);
            rquom.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and OrgId='" + pOrgId +
                                 "' and HoOrgId='" + p_hoOrgId + "' and ItmId='" + itm + "' and UomIdSrc='" + uomBs +
                                 "' and UomIdDest='" + uomPur + "'");
            adfLog.info("lovuom query=" + rquom.getExprStr());
            Row ruom[] = voUomVw.getFilteredRows(rquom);
            adfLog.info("No.of filtered rows in uom=" + ruom.length);
            Number convFctr = new Number(1);
            if (ruom.length > 0)
                convFctr = (Number)ruom[0].getAttribute("ConvFctr");
            getAm().getMmDrftPoItm().getCurrentRow().setAttribute("UomConvFctr", convFctr);
            adfLog.info("Conversion factor for this=" + convFctr);

            AdfFacesContext.getCurrentInstance().addPartialTarget(suggestedSuppBtnBind);
            String itmName = vce.getNewValue().toString();
            ViewObject v = getAm().getMmDrftPoItm();

            String itmDesc = null;
            int totalCount = v.getRowCount(); //get ALL rows
            int rangeSize = v.getRangeSize(); //all in range
            v.setRangeSize(totalCount);
            Row[] rArray = v.getAllRowsInRange();

            //check for duplicate rows
            Row cRow = v.getCurrentRow();
            int count = 0;
            String currName = "";

            for (Row r : rArray) {

                if (!r.equals(cRow)) {
                    try {
                        currName = r.getAttribute("ItmId").toString();
                    } catch (NullPointerException npe) {
                        // System.out.println("NPE:" + npe);
                        currName = "";
                    }
                    if (currName != null) {
                        ViewObjectImpl itmNewVo = getAm().getLovItmIdNew();
                        itmNewVo.setNamedWhereClauseParam("CldIdBind", p_cldId);
                        itmNewVo.setNamedWhereClauseParam("SlocIdBind", pslocId);
                        itmNewVo.setNamedWhereClauseParam("HoOrgIdBind", p_hoOrgId);
                        itmNewVo.setNamedWhereClauseParam("OrgIdBind", pOrgId);
                        itmNewVo.setNamedWhereClauseParam("ItmIdBind", currName);
                        itmNewVo.executeQuery();


                        // Row[] xx = getAm().getLovItmId().getFilteredRows("ItmId", currName);
                        Row[] xx = itmNewVo.getFilteredRows("ItmId", currName);
                        adfLog.info("row lenght in methd  " + xx.length);
                        if (xx.length > 0) {
                            itmDesc = xx[0].getAttribute("ItmDesc").toString();
                        }
                        if (itmName.equalsIgnoreCase(itmDesc)) {
                            count = count + 1;
                        }
                    }

                }

            }
            v.setRangeSize(rangeSize); //set to original range
            Row rw = getAm().getMmDrftPoItm().getCurrentRow();
            Object obj = getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId");


            //   Row[] filteredRows = getAm().getLovItmId().getFilteredRows("ItmDesc", vce.getNewValue().toString());
            ViewObjectImpl vitm1 = getAm().getLovItmDesc();
            vitm1.setNamedWhereClauseParam("CldIdBind", p_cldId);
            vitm1.setNamedWhereClauseParam("SlocIdBind", pslocId);
            vitm1.setNamedWhereClauseParam("HoOrgIdBind", p_hoOrgId);
            vitm1.setNamedWhereClauseParam("OrgIdBind", pOrgId);
            vitm1.setNamedWhereClauseParam("ItmDescBind", vce.getNewValue());
            vitm1.executeQuery();
            Row[] filteredRows = vitm1.getFilteredRows("ItmDesc", vce.getNewValue().toString());
            adfLog.info(" lenth in 3rd change  " + filteredRows.length);
            String itmId = null;
            if (filteredRows.length > 0) {
                itmId = filteredRows[0].getAttribute("ItmId").toString();
            }

            //Check org mm prf for price policy
            String policy = "N";
            Row[] prfrow = getAm().getOrgMmPrf1().getFilteredRows("OrgId", pOrgId);
            if (prfrow.length > 0)
                if (prfrow[0].getAttribute("ApplyPriceFrmPolicy") != null)
                    policy = (String)prfrow[0].getAttribute("ApplyPriceFrmPolicy");

            Row r[] = getAm().getMmDrftPoItm().getFilteredRows("ItmId", itmId);
            System.out.println("No. of rows of same item=" + r.length);
            if (r.length == 1) {
                if (obj != null) {
                    String eoId = obj.toString();
                    ViewObjectImpl voi = getAm().getLovItmIdEo();
                    RowQualifier rq = new RowQualifier(voi);
                    rq.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and OrgId='" + pOrgId +
                                      "' and EoId=" + eoId + " and ItmId='" + itmId + "' and Actv='Y'");
                    Row[] rows = voi.getFilteredRows(rq);
                    System.out.println("Row length---" + rows.length);
                    if (rows.length == 0) {
                        showPopup(itmLinkSupPopUp, true);
                    }
                }
                itmPoBind.processUpdates(FacesContext.getCurrentInstance());
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmUomBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);

            }
            if (policy.equals("N")) {
                Object eo = getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId");
                if (eo != null) {
                    String eoId = eo.toString();
                    ViewObjectImpl voiagain = getAm().getLovItmIdEo();
                    voiagain.executeQuery();
                    RowQualifier rqagain = new RowQualifier(voiagain);
                    rqagain.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and OrgId='" + pOrgId +
                                           "' and EoId=" + eoId + " and ItmId='" + itmId + "' and Actv='Y'");
                    System.out.println(rqagain.getExprStr());
                    Row[] rowsagain = voiagain.getFilteredRows(rqagain);
                    System.out.println("No. of filtr row=" + rowsagain.length);
                    if (rowsagain.length > 0) {
                        System.out.println("ItmPriceBs for supplier=" + rowsagain[0].getAttribute("ItmPriceUomBs"));
                        Number price = new Number(0);
                        if (rowsagain[0].getAttribute("ItmPriceUomBs") != null)
                            price = (Number)rowsagain[0].getAttribute("ItmPriceUomBs");
                        getAm().getMmDrftPoItm().getCurrentRow().setAttribute("ItmPrice", price.multiply(convFctr));
                        AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);
                        //  getAm().getMmDrftPoItm().getCurrentRow().setAttribute("ItmPriceBs",price);
                    }
                }
            } else if (getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId") != null) {

                Object eo = getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId");

                ViewObjectImpl voi = getAm().getLovItmIdEo();
                RowQualifier rq = new RowQualifier(voi);
                rq.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and OrgId='" + pOrgId +
                                  "' and EoId=" + eo + " and ItmId='" + itmId + "' and Actv='Y'");
                Row[] rows = voi.getFilteredRows(rq);
                System.out.println("Row length---" + rows.length);
                if (rows.length > 0) {
                    BigDecimal retval =
                        (BigDecimal)(callStoredFunction(NUMBER, "APP.APP_GET_POLICY_PRICE(?,?,?,?,?,?,?)",
                                                        new Object[] { p_cldId, pslocId, p_hoOrgId, pOrgId, eo, itmId,
                                                                       "PO" }));
                    System.out.println("Policy retrun=" + retval);
                    if (retval.compareTo(new BigDecimal(-1)) == 0) {
                        String msg = "Price Policy for this Item is not valid.Please check Policy.";
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else if (retval.compareTo(new BigDecimal(0)) == 0) {
                    } else {
                        getAm().getMmDrftPoItm().getCurrentRow().setAttribute("ItmPrice",
                                                                              (retval).multiply(new BigDecimal(convFctr.toString())));
                        AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);

                    }
                }
            }
        }
        System.out.println("exiting vce");
    }

    public void setItmLinkSupPopUp(RichPopup itmLinkSupPopUp) {
        this.itmLinkSupPopUp = itmLinkSupPopUp;
    }

    public RichPopup getItmLinkSupPopUp() {
        return itmLinkSupPopUp;
    }

    /**
     * DL action for linkage of selected item to the selected supplier - MM.PKG_MM_EOITM.INS_EO_ITM
     * */

    public void itmLinkDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            Row poRow = getAm().getMmDrftPo().getCurrentRow();
            Row poItmRow = getAm().getMmDrftPoItm().getCurrentRow();

            //P_SLOCID   IN MM.MM$EO$ITM.SLOC_ID%TYPE,
            //                         P_ORGID    IN MM.MM$EO$ITM.ORG_ID%TYPE,
            //                         P_EOID     IN MM.MM$EO$ITM.EO_ID%TYPE,
            //                         P_ITMID    IN MM.MM$EO$ITM.ITM_ID%TYPE,
            //                         P_ITMUOM   IN MM.MM$EO$ITM.ITM_UOM%TYPE,
            //                         P_PRICE    IN MM.MM$EO$ITM.ITM_PRICE%TYPE,
            //                         P_QTY      IN MM.MM$EO$ITM.ORD_QTY%TYPE,
            //                         P_DISC_TYP IN MM.MM$EO$ITM.DISC_TYPE%TYPE,
            //                         P_DISC_VAL IN MM.MM$EO$ITM.DISC_VAL%TYPE,
            //                         P_TL_TYP   IN MM.MM$EO$ITM.TLRNC_TYPE%TYPE,
            //                         P_TL_VAL   IN MM.MM$EO$ITM.TLRNC_VAL%TYPE,
            //                         P_LEADTIME IN MM.MM$EO$ITM.LEAD_TIME%TYPE,
            //                         P_USRID    IN MM.MM$EO$ITM.USR_ID_CREATE%TYPE,
            //                         P_USR_DT   IN MM.MM$EO$ITM.USR_ID_CREATE_DT%TYPE,
            //                         P_ENTID    IN MM.MM$EO$ITM.ENTITY_ID%TYPE)
            Number zero = new Number(0);
            Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();

            Integer pEoId = Integer.parseInt(poRow.getAttribute("EoId").toString());
            String pItmId = poItmRow.getAttribute("ItmId").toString();
            String pItmUom = poItmRow.getAttribute("ItmUom").toString();
            Number pPrice = zero;
            Number pQty = zero;
            String pDiscType = "A";
            Number pDiscVal = zero;
            String pTlrncType = "A";
            Number pTlrncVal = zero;
            String pLeadTime = null;
            if (poItmRow.getAttribute("ItmPrice") != null) {
                pPrice = (Number)poItmRow.getAttribute("ItmPrice");
            }
            if (poItmRow.getAttribute("OrdQty") != null) {
                pQty = (Number)poItmRow.getAttribute("OrdQty");
            }
            if (poItmRow.getAttribute("DiscType") != null) {
                pDiscType = poItmRow.getAttribute("DiscType").toString();
            }

            if (poItmRow.getAttribute("DiscVal") != null) {
                pDiscVal = (Number)poItmRow.getAttribute("DiscVal");
            }
            if (poItmRow.getAttribute("TlrncQtyType") != null) {
                pTlrncType = poItmRow.getAttribute("TlrncQtyType").toString();
            }

            if (poItmRow.getAttribute("TlrncQtyVal") != null) {
                pTlrncVal = (Number)poItmRow.getAttribute("TlrncQtyVal");
            }
            Integer pUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            oracle.jbo.domain.Date pdateCurr = (oracle.jbo.domain.Date)new oracle.jbo.domain.Date().getCurrentDate();

            Integer retval =
                Integer.parseInt(callStoredFunction(INTEGER, "MM.MM_INS_EO_ITM_AUTO(?,?,?,?,?,?,?,?,?,?,?)",
                                                    new Object[] { p_cldId, pslocId, p_hoOrgId, pOrgId, pEoId,
                                                                   poRow.getAttribute("CurrIdSp"), pItmId, pItmUom,
                                                                   pUsrId, 18504,
                                                                   poRow.getAttribute("DocId") }).toString());

            /*   Integer retval =
                Integer.parseInt(callStoredFunction(INTEGER, "MM.PKG_MM_EOITM.INS_EO_ITM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                    new Object[] { pslocId,p_cldId, pOrgId, pEoId, pItmId, pItmUom, pPrice,
                                                                   pQty, pDiscType, pDiscVal, pTlrncType, pTlrncVal,
                                                                   pLeadTime, pUsrId, pdateCurr, 1 }).toString());
             */
            Row[] prfrow = getAm().getOrgMmPrf1().getFilteredRows("OrgId", pOrgId);
            String policy = "N";
            if (prfrow.length > 0)
                if (prfrow[0].getAttribute("ApplyPriceFrmPolicy") != null)
                    policy = (String)prfrow[0].getAttribute("ApplyPriceFrmPolicy");
            String itmId = (String)getAm().getMmDrftPoItm().getCurrentRow().getAttribute("ItmId");
            Number convFctr = (Number)getAm().getMmDrftPoItm().getCurrentRow().getAttribute("UomConvFctr");
            if (policy.equals("N")) {
                Object eo = getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId");
                if (eo != null) {
                    String eoId = eo.toString();
                    ViewObjectImpl voiagain = getAm().getLovItmIdEo();
                    voiagain.executeQuery();
                    RowQualifier rqagain = new RowQualifier(voiagain);
                    rqagain.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and OrgId='" + pOrgId +
                                           "' and EoId=" + eoId + " and ItmId='" + itmId + "' and Actv='Y'");
                    System.out.println(rqagain.getExprStr());
                    Row[] rowsagain = voiagain.getFilteredRows(rqagain);
                    System.out.println("No. of filtr row=" + rowsagain.length);
                    if (rowsagain.length > 0) {
                        System.out.println("ItmPriceBs for supplier=" + rowsagain[0].getAttribute("ItmPriceUomBs"));
                        System.out.println("uomconvfctr for this unit=" + (convFctr));
                        Number price = new Number(0);
                        if (rowsagain[0].getAttribute("ItmPriceUomBs") != null)
                            price = (Number)rowsagain[0].getAttribute("ItmPriceUomBs");
                        getAm().getMmDrftPoItm().getCurrentRow().setAttribute("ItmPrice", price.multiply(convFctr));
                    }
                }
            } else if (getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId") != null) {
                ViewObjectImpl voiagain = getAm().getLovItmIdEo();
                voiagain.executeQuery();
                Object eo = getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId");
                BigDecimal retval1 =
                    (BigDecimal)(callStoredFunction(NUMBER, "APP.APP_GET_POLICY_PRICE(?,?,?,?,?,?,?)", new Object[] { p_cldId,
                                                                                                                      pslocId,
                                                                                                                      p_hoOrgId,
                                                                                                                      pOrgId,
                                                                                                                      eo,
                                                                                                                      itmId,
                                                                                                                      "PO" }));
                if (retval1 != null) {
                    if (retval1.compareTo(new BigDecimal(-1)) == 0) {
                        String msg = "Policy for this Item not Define.";
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        getAm().getMmDrftPoItm().getCurrentRow().setAttribute("ItmPrice",
                                                                              (retval1).multiply(new BigDecimal(convFctr.toString())));
                        AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);

                    }

                }
            } else {

            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);

            //  getAm().getMmDrftPoItm().getCurrentRow().setAttribute("ItmPriceBs",price);


            //System.out.println("retval in link"+retval);
        } else if (dialogEvent.getOutcome().name().equals("no")) {
            PurOrderAMImpl am = (PurOrderAMImpl)resolvElDC("PurOrderAMDataControl");
            Row poItmRow = am.getMmDrftPoItm().getCurrentRow();
            //  poItmRow.remove();
            OperationBinding operationBinding = getBindings().getOperationBinding("Delete");
            operationBinding.execute();
            OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute");
            operationBinding1.execute();

            disclosedItem.setDisclosed(false);

            AdfFacesContext.getCurrentInstance().addPartialTarget(disclosedItem);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        }


    }

    public void setCheckBxSugSup(RichSelectBooleanCheckbox checkBxSugSup) {
        this.checkBxSugSup = checkBxSugSup;
    }

    public RichSelectBooleanCheckbox getCheckBxSugSup() {
        return checkBxSugSup;
    }

    public void setDateDlvSchdl(RichInputDate dateDlvSchdl) {
        this.dateDlvSchdl = dateDlvSchdl;
    }

    public RichInputDate getDateDlvSchdl() {
        return dateDlvSchdl;
    }


    public void goToDlvSchdlAction(ActionEvent actionEvent) {
        PurOrderAMImpl am = getAm();
        ViewObject po = am.getMmDrftPo();
        ViewObject poItm = am.getMmDrftPoItm();
        Row currRow = po.getCurrentRow();
        currRow.setAttribute("PoMode", PO_MODE_INCOMPLETE);
        Integer poType = Integer.parseInt(currRow.getAttribute("PoType").toString());
        System.out.println("PO TYPE=" + poType);
        Number TransTotalPoCostSp = null;

        if (po.getCurrentRow().getAttribute("TransTotalPoCostSp") != null) {
            TransTotalPoCostSp = (Number)po.getCurrentRow().getAttribute("TransTotalPoCostSp");
        }
        if (TransTotalPoCostSp.compareTo(new Number(0)) == -1) {
            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.436']}"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (poType.compareTo(PO_TYPE_RC) != 0) {
            RichPanelTabbed richPanelTabbed = getPanelTabed();
            //    System.out.println("For OTHER THAN RC---- go to dlv scdl----");
            System.out.println("1");
            String dlvChanged = "N";
            String ItmNames = "";
            for (UIComponent child : richPanelTabbed.getChildren()) {
                RichShowDetailItem sdi = (RichShowDetailItem)child;
                //  System.out.println("client id : " + sdi.getClientId());
                //      System.out.println(dlvryTab.getClientId() + "-- binding client id-" + supAndItmTab.getClientId());
                //pt1:drMM1:2:sdi2
                System.out.println("2");
                if (sdi.getClientId().equals(dlvryTab.getClientId())) {
                    System.out.println("3");
                    Number taxableAmt = new Number(0);
                    ViewObject trPo = am.getMmDrftPoTr();

                    Row[] filterdRw = trPo.getFilteredRows("DocId", currRow.getAttribute("DocId").toString());

                    if (filterdRw.length > 0) {
                        taxableAmt = (Number)(filterdRw[0].getAttribute("TaxableAmt"));
                    }
                    String taxruleFlg = "P";
                    String taxAfterDiscFlg = "Y";
                    if (currRow.getAttribute("TaxRuleFlg") != null) {
                        taxruleFlg = currRow.getAttribute("TaxRuleFlg").toString();
                    }
                    if (currRow.getAttribute("TaxAfterDiscFlg") != null) {
                        taxAfterDiscFlg = currRow.getAttribute("TaxAfterDiscFlg").toString();
                    }
                    Number taxOnAmount = (Number)currRow.getAttribute("TransSumItmAmtSpTax");
                    System.out.println(taxruleFlg + "--TransSumItmAmtSpTax-----" + taxOnAmount +
                                       "----TaxAfterDiscFlg----" + taxAfterDiscFlg + "----TaxableAmount-------" +
                                       taxableAmt);
                    //check if tax needs to be reapplied.
                    RowSetIterator rsi = poItm.createRowSetIterator(null);
                    Number poDiscAmt = (Number)currRow.getAttribute("TransPoDiscountAmt");
                    Number taxOnAmountAfterDisc = taxOnAmount.subtract(poDiscAmt);
                    System.out.println("taxOnAmountAfterDisc----" + taxOnAmountAfterDisc);
                    String appTax = "N";
                    String itmNameConcat = "";
                    while (rsi.hasNext()) {
                        Row rw = rsi.next();
                        if (rw.getAttribute("TransTaxChangedFlg") != null) {
                            if ("Y".equalsIgnoreCase(rw.getAttribute("TransTaxChangedFlg").toString())) {
                                appTax = "Y";
                                if (rw.getAttribute("TransItemName") != null) {
                                    itmNameConcat = itmNameConcat + rw.getAttribute("TransItemName").toString() + ",";
                                }
                            }
                        }
                    }
                    rsi.closeRowSetIterator();

                    if (currRow.getAttribute("EoId") != null) {
                        if (currRow.getAttribute("PoDt") == null) {
                            oracle.jbo.domain.Date dateCurr =
                                (oracle.jbo.domain.Date)new oracle.jbo.domain.Date().getCurrentDate();
                            currRow.setAttribute("PoDt", dateCurr);
                        }
                        Date dt = new Date(currRow.getAttribute("PoDt").toString());
                        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
                        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
                        Integer fyid =
                            (Integer)(callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] { cldId,
                                                                                                                    OrgId,
                                                                                                                    dt }));
                        if (fyid > 0) {
                            currRow.setAttribute("FyId", fyid);
                            adfLog.info(currRow.getAttribute("BillAddsId") + "   ads   Supplier id=" +
                                        currRow.getAttribute("EoId"));

                            if (poItm.getAllRowsInRange().length > 0) { // check if taxable amount changed
                                System.out.println("4");
                                if ("Y".equalsIgnoreCase(appTax)) {

                                    FacesMessage message =
                                        new FacesMessage(resolvEl("#{bundle['MSG.292']}") + "(" + itmNameConcat.substring(0,
                                                                                                                          itmNameConcat.length() -
                                                                                                                          1) +
                                                         resolvEl("#{bundle['MSG.293']}"));
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                } else {
                                    System.out.println("5");
                                    adfLog.info(currRow.getAttribute("BillAddsId") + "Supplier id=" +
                                                currRow.getAttribute("EoId"));
                                    Integer eo = (Integer)currRow.getAttribute("EoId");
                                    String billaddid = null;
                                    String taxafterFlag = "Y";
                                    if (currRow.getAttribute("TaxAfterDiscFlg") != null) {
                                        taxafterFlag = currRow.getAttribute("TaxAfterDiscFlg").toString();
                                    }
                                    if (currRow.getAttribute("BillAddsId") != null) {
                                        billaddid = currRow.getAttribute("BillAddsId").toString();
                                    }

                                    if (currRow.getAttribute("PoId") == null) {
                                        System.out.println("6");

                                        String docId = currRow.getAttribute("DocId").toString();
                                        Integer p_slocid =
                                            Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
                                        String p_OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
                                        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
                                        String tableName = "MM$DRFT$PO";

                                        //    Integer fyid=Integer.parseInt(currRow.getAttribute("FyId").toString());
                                        String poId =
                                            (String)(callStoredFunction(Types.VARCHAR, "MM.fn_mm_gen_id(?,?,?,?,?,?)",
                                                                        new Object[] { p_slocid, p_cldId, p_OrgId,
                                                                                       tableName, null, fyid }));

                                        Date frmDt = null;

                                        if (currRow.getAttribute("ValidFrmDt") != null)
                                            frmDt = (Date)currRow.getAttribute("ValidFrmDt");
                                        Date toDt = null;
                                        if (currRow.getAttribute("ValidToDt") != null)
                                            toDt = (Date)currRow.getAttribute("ValidToDt");
                                        String discType = (String)currRow.getAttribute("DiscType");
                                        Number discVal = (Number)currRow.getAttribute("DiscVal");
                                        Number currConvFctr = new Number(0);
                                        Integer currId = null;
                                        currId = (Integer)currRow.getAttribute("CurrIdSp");
                                        currConvFctr = (Number)currRow.getAttribute("CurrConvFctr");


                                        System.out.println("currConvFctr=" + currConvFctr);
                                        currRow.setAttribute("PoId", poId);
                                        currRow.setAttribute("DiscType", discType);
                                        currRow.setAttribute("DiscVal", discVal);
                                        currRow.setAttribute("ValidFrmDt", frmDt);
                                        currRow.setAttribute("ValidToDt", toDt);
                                        currRow.setAttribute("EoId", eo);
                                        currRow.setAttribute("BillAddsId", billaddid);
                                        currRow.setAttribute("CurrIdSp", currId);
                                        currRow.setAttribute("CurrConvFctr", currConvFctr);
                                        currRow.setAttribute("TaxAfterDiscFlg", taxafterFlag);
                                    }


                                    adfLog.info(currRow.getAttribute("BillAddsId") + "Supplier id=" +
                                                currRow.getAttribute("EoId"));
                                    System.out.println("7");
                                    Number amtBs = (Number)currRow.getAttribute("TransSumItmAmtBs");
                                    Number amtSp = (Number)currRow.getAttribute("TransSumItmAmtSp");

                                    currRow.setAttribute("PoAmtBs", amtBs);
                                    currRow.setAttribute("PoAmtSp", amtSp);
                                    adfLog.info(currRow.getAttribute("BillAddsId") + "Supplier id=" +
                                                currRow.getAttribute("EoId"));
                                    System.out.println("PO amt set");
                                    /**
 * check trline before commit
 */
                                    ViewObjectImpl drftPoTrLinesx = getAm().getMmDrftPoTrLines();
                                    ViewObjectImpl drftPox = getAm().getMmDrftPo();
                                    RowSetIterator rsitrx = drftPoTrLinesx.createRowSetIterator(null);
                                    while (rsitrx.hasNext()) {
                                        System.out.println("in rs of itrline");
                                        Row rx = rsitrx.next();
                                        if (rx.getAttribute("DocId").toString().equalsIgnoreCase(drftPox.getCurrentRow().getAttribute("DocId").toString())) {
                                            System.out.println("Rows Remaining in TRLines=" +
                                                               rx.getAttribute("RowID"));
                                        }
                                    }
                                    rsitrx.closeRowSetIterator();
                                    System.out.println("rowset closed");
                                    /* System.out.println("QUERY --- po---"+getAm().getMmDrftPo().getQuery());
                                System.out.println("QUERY --- poItm---"+getAm().getMmDrftPoItm().getQuery());
                                System.out.println("QUERY --- poOc---"+getAm().getMmDrftPoOc().getQuery());
                                System.out.println("QUERY --- potr---"+getAm().getMmDrftPoTr().getQuery());
                                System.out.println("QUERY --- potrline---"+getAm().getMmDrftPoTrLines().getQuery());
                                System.out.println("QUERY --- podlv---"+getAm().getMmDrftPoDlvSchdl().getQuery());
                                System.out.println("QUERY --- popmt---"+getAm().getMmDrftPoPmtSchdl().getQuery()); */
                                    adfLog.info(currRow.getAttribute("BillAddsId") + "Supplier id=" +
                                                currRow.getAttribute("EoId"));
                                    OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                                    operationBinding.execute();
                                    if (operationBinding.getErrors() != null)
                                        System.out.println("Error in commit" + operationBinding.getErrors());
                                    else
                                        System.out.println("Data Commited");
                                    OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute");
                                    operationBinding1.execute();
                                    System.out.println("execute error=" + operationBinding1.getErrors());
                                    // getAm().getMmPendOrdVw().executeQuery();
                                    OperationBinding ob1 = getBindings().getOperationBinding("Execute2");
                                    ob1.execute();
                                    System.out.println("execute2 error=" + ob1.getErrors());
                                    //  getAm().getDlvSchdlBalanceView().executeQuery();
                                    OperationBinding ob2 = getBindings().getOperationBinding("Execute3");
                                    ob2.execute();
                                    System.out.println("execute3 error=" + ob2.getErrors());

                                    if (operationBinding.getErrors().isEmpty()) {
                                        System.out.println("8");
                                        sdi.setDisclosed(true);
                                        setShowDlvSchdlTab("true");
                                        setShowItemsTab("false"); //for items tab
                                        setShowPortAndContnrTab("false");
                                        disclosedItem.setDisclosed(false);
                                        setShowFields(false);
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabed);
                                        ViewObject voPmt = getAm().getMmDrftPoPmtSchdl();
                                        if (voPmt.getEstimatedRowCount() > 0) {
                                            Row poRw = getAm().getMmDrftPo().getCurrentRow();
                                            Number pmtAmt = (Number)poRw.getAttribute("TransSumPoPmtAmt");
                                            Number poCost = (Number)poRw.getAttribute("TransTotalPoCostSp");

                                            if (pmtAmt.compareTo(poCost) == 1 || pmtAmt.compareTo(poCost) == -1) {
                                                //delete if any change
                                                System.out.println("9");

                                                RowSetIterator rsiPmt = voPmt.createRowSetIterator(null);
                                                while (rsiPmt.hasNext()) {
                                                    Row r = rsiPmt.next();
                                                    r.remove();
                                                }
                                                rsiPmt.closeRowSetIterator();
                                                //    System.out.println("reconfigure payment Schedule");

                                            }
                                        }
                                        ViewObject voPoItm = getAm().getMmDrftPoItm();
                                        ViewObject voPoDlv = getAm().getMmDrftPoDlvSchdl();
                                        RowSetIterator itmRsi = voPoItm.createRowSetIterator(null);
                                        System.out.println("10");
                                        while (itmRsi.hasNext()) {
                                            Row curr = itmRsi.next();
                                            String itmId = curr.getAttribute("ItmId").toString();
                                            Number qty = (Number)curr.getAttribute("OrdQty");

                                            Row[] dlvRows = voPoDlv.getFilteredRows("ItmId", itmId);
                                            Number sumqty = new Number(0);
                                            if (dlvRows.length > 0) {
                                                System.out.println("11");
                                                for (Row rw : dlvRows) {
                                                    sumqty = sumqty.add((Number)rw.getAttribute("DlvQty"));
                                                }
                                                System.out.println("12");
                                                RowSetIterator rsiPoDlv =
                                                    getAm().getMmDrftPoDlvSchdl().createRowSetIterator(null);
                                                if (qty.compareTo(sumqty) == 1 || qty.compareTo(sumqty) == -1) {
                                                    while (rsiPoDlv.hasNext()) {
                                                        Row rr = rsiPoDlv.next();
                                                        if ((rr.getAttribute("ItmId").toString()).equalsIgnoreCase(itmId)) {
                                                            rr.remove();
                                                            dlvChanged = "Y";
                                                        }
                                                    }
                                                }
                                                rsiPoDlv.closeRowSetIterator();
                                            }
                                        }
                                        itmRsi.closeRowSetIterator();
                                        OperationBinding ob4 = getBindings().getOperationBinding("Execute4");
                                        ob4.execute();
                                        OperationBinding ob5 = getBindings().getOperationBinding("Execute5");
                                        ob5.execute();
                                        OperationBinding exPo = getBindings().getOperationBinding("Execute");
                                        exPo.execute();
                                        /*  System.out.println("QUERY --- po---"+getAm().getMmDrftPo().getQuery());
                                    System.out.println("QUERY --- poItm---"+getAm().getMmDrftPoItm().getQuery());
                                    System.out.println("QUERY --- poOc---"+getAm().getMmDrftPoOc().getQuery());
                                    System.out.println("QUERY --- potr---"+getAm().getMmDrftPoTr().getQuery());
                                    System.out.println("QUERY --- potrline---"+getAm().getMmDrftPoTrLines().getQuery());
                                    System.out.println("QUERY --- podlv---"+getAm().getMmDrftPoDlvSchdl().getQuery());
                                    System.out.println("QUERY --- popmt---"+getAm().getMmDrftPoPmtSchdl().getQuery());
                                     */

                                        OperationBinding comOb = getBindings().getOperationBinding("Commit");
                                        comOb.execute();


                                        OperationBinding ob3 = getBindings().getOperationBinding("Execute3");
                                        ob3.execute();

                                        poDtBind.setDisabled(true); //for podate en/dis--08Apr

                                        AdfFacesContext.getCurrentInstance().addPartialTarget(saveAsButton);
                                        if ("Y".equalsIgnoreCase(dlvChanged)) {
                                            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.440']}"));
                                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message);
                                        }

                                    }

                                }
                            } else {
                                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.442']}"));
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }
                        } else {
                            FacesMessage message = new FacesMessage("Date not in Valid Financial Year");
                            message.setSeverity(FacesMessage.SEVERITY_WARN);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.444']}"));
                        message.setSeverity(FacesMessage.SEVERITY_WARN);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                } else {

                    sdi.setDisclosed(false);
                }
            }
        } else if (poType.compareTo(PO_TYPE_RC) == 0) { // for RC PO
            System.out.println("13"); //   System.out.println("For RC---- go to Terms and Agreemnt----");
            RichPanelTabbed richPanelTabbed = getPanelTabed();
            String dlvChanged = "N";
            String ItmNames = "";
            for (UIComponent child : richPanelTabbed.getChildren()) {
                RichShowDetailItem sdi = (RichShowDetailItem)child;
                System.out.println(termsAndAggTab.getClientId() + "--client id" + sdi.getClientId());
                //pt1:drMM1:2:sdi4
                if (sdi.getClientId().equals(termsAndAggTab.getClientId())) {
                    System.out.println("14");
                    Number taxableAmt = new Number(0);
                    ViewObject trPo = am.getMmDrftPoTr();

                    Row[] filterdRw = trPo.getFilteredRows("DocId", currRow.getAttribute("DocId").toString());

                    if (filterdRw.length > 0) {
                        taxableAmt = (Number)(filterdRw[0].getAttribute("TaxableAmt"));
                    }
                    String taxruleFlg = "P";
                    String taxAfterDiscFlg = "Y";
                    if (currRow.getAttribute("TaxRuleFlg") != null) {
                        taxruleFlg = currRow.getAttribute("TaxRuleFlg").toString();
                    }
                    if (currRow.getAttribute("TaxAfterDiscFlg") != null) {
                        taxAfterDiscFlg = currRow.getAttribute("TaxAfterDiscFlg").toString();
                    }
                    Number taxOnAmount = (Number)currRow.getAttribute("TransSumItmAmtSpTax");
                    /*     System.out.println(taxruleFlg + "TransSumItmAmtSpTax-----" + taxOnAmount + "----TaxAfterDiscFlg----" +
                                       taxAfterDiscFlg + "----TaxableAmount-------" + taxableAmt);
                   */ //check if tax needs to be reapplied.
                    RowSetIterator rsi = poItm.createRowSetIterator(null);
                    String appTax = "N";
                    String itmNameConcat = "";
                    while (rsi.hasNext()) {
                        Row rw = rsi.next();
                        if (rw.getAttribute("TransTaxChangedFlg") != null) {
                            if ("Y".equalsIgnoreCase(rw.getAttribute("TransTaxChangedFlg").toString())) {
                                appTax = "Y";
                                if (rw.getAttribute("TransItemName") != null) {
                                    itmNameConcat = itmNameConcat + rw.getAttribute("TransItemName").toString() + ",";
                                }
                            }
                        }
                    }
                    rsi.closeRowSetIterator();
                    System.out.println("15");
                    String docId = currRow.getAttribute("DocId").toString();
                    Integer p_slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
                    String p_OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
                    String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
                    Date dt = (Date)currRow.getAttribute("PoDt");
                    Integer fyid =
                        (Integer)(callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] { p_cldId,
                                                                                                                p_OrgId,
                                                                                                                dt }));
                    if (fyid > 0) {
                        currRow.setAttribute("FyId", fyid);
                        if (currRow.getAttribute("EoId") != null) {

                            if (poItm.getAllRowsInRange().length > 0) { // check if taxable amount changed
                                System.out.println("16");
                                if (currRow.getAttribute("PoId") == null) {
                                    String billadds = null;
                                    if (currRow.getAttribute("BillAddsId") != null) {
                                        billadds = currRow.getAttribute("BillAddsId").toString();
                                    }
                                    Integer eo = (Integer)currRow.getAttribute("EoId");
                                    Date frmDt = null;

                                    if (currRow.getAttribute("ValidFrmDt") != null)
                                        frmDt = (Date)currRow.getAttribute("ValidFrmDt");
                                    Date toDt = null;
                                    if (currRow.getAttribute("ValidToDt") != null)
                                        toDt = (Date)currRow.getAttribute("ValidToDt");
                                    Number currConvFctr = new Number(0);
                                    Integer currId = null;
                                    currId = (Integer)currRow.getAttribute("CurrIdSp");
                                    currConvFctr = (Number)currRow.getAttribute("CurrConvFctr");
                                    System.out.println("currConvFctr=" + currConvFctr);
                                    String BinId = null;
                                    try {
                                        BinId =
                                                (String)(callStoredFunction(Types.VARCHAR, "MM.fn_mm_gen_id(?,?,?,?,?,?)",
                                                                            new Object[] { p_slocid, p_cldId, p_OrgId,
                                                                                           "MM$DRFT$PO", null,
                                                                                           fyid }));
                                        //  (String)(callStoredFunction(Types.VARCHAR, "MM.pkg_mm_gen.generate_id (?,?,?,?)", new Object[] { p_slocid,p_cldId,p_OrgId,"MM$DRFT$PO" }));

                                    } catch (Exception e) {
                                        System.out.println("Exception in PO generation--" + e);
                                    }
                                    currRow.setAttribute("PoId", BinId);
                                    if (currRow.getAttribute("PoDt") == null) {
                                        oracle.jbo.domain.Date dateCurr =
                                            (oracle.jbo.domain.Date)new oracle.jbo.domain.Date().getCurrentDate();
                                        currRow.setAttribute("PoDt", dateCurr);
                                    }
                                    adfLog.info(eo + " eoid " + billadds + " billadds   " + currId + "  currid  " +
                                                currConvFctr + "  currConvFctr " + frmDt + " frmDt " + toDt +
                                                "  toDt ");
                                    currRow.setAttribute("EoId", eo);
                                    currRow.setAttribute("ValidFrmDt", frmDt);
                                    currRow.setAttribute("ValidToDt", toDt);
                                    currRow.setAttribute("BillAddsId", billadds);
                                    currRow.setAttribute("CurrIdSp", currId);
                                    currRow.setAttribute("CurrConvFctr", currConvFctr);

                                }

                                Number amtBs = (Number)currRow.getAttribute("TransSumItmAmtBs");
                                Number amtSp = (Number)currRow.getAttribute("TransSumItmAmtSp");

                                currRow.setAttribute("PoAmtBs", amtBs);
                                currRow.setAttribute("PoAmtSp", amtSp);

                                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                                operationBinding.execute();
                                OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute");
                                operationBinding1.execute();

                                OperationBinding ob2 = getBindings().getOperationBinding("Execute2");
                                ob2.execute();

                                OperationBinding ob3 = getBindings().getOperationBinding("Execute3");
                                ob3.execute();

                                if (operationBinding.getErrors().isEmpty()) {
                                    System.out.println("17");
                                    sdi.setDisclosed(true);
                                    setShowTnAgreeTab("true");
                                    setShowItemsTab("false"); //for items tab
                                    setShowPaymentSchdlTab("false");
                                    setShowDlvSchdlTab("false");
                                    setShowPortAndContnrTab("false");
                                    disclosedItem.setDisclosed(false);
                                    setShowFields(false);
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabed);
                                    poDtBind.setDisabled(true);
                                    getAm().getDBTransaction().commit();
                                    OperationBinding ob4 = getBindings().getOperationBinding("Execute4");
                                    ob4.execute();
                                    ob3.execute();
                                    OperationBinding ob5 = getBindings().getOperationBinding("Execute5");
                                    ob5.execute();

                                }

                            } else {
                                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.442']}"));
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }

                        } else {
                            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.444']}"));
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage("PO Date is not in Valid Financial Year.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                } else {
                    sdi.setDisclosed(false);
                }
            }

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabed);
    }

    public void setPanelTabed(RichPanelTabbed panelTabed) {
        this.panelTabed = panelTabed;
    }

    public RichPanelTabbed getPanelTabed() {
        return panelTabed;
    }

    public void setDlvryTab(RichShowDetailItem dlvryTab) {
        this.dlvryTab = dlvryTab;
    }

    public RichShowDetailItem getDlvryTab() {
        return dlvryTab;
    }

    public void goToPaymentSchdlAction(ActionEvent actionEvent) {
       
       
        RichPanelTabbed richPanelTabbed = getPanelTabed();

        for (UIComponent child : richPanelTabbed.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem)child;
            //pt1:drMM1:2:sdi3
            if (sdi.getClientId().equals(paySchdlTab.getClientId())) {
                setShowItemsTab("false");
                setShowDlvSchdlTab("false");
                setShowPaymentSchdlTab("true");
                // setShowTnAgreeTab("false");
                setShowPortAndContnrTab("false");
                sdi.setDisclosed(true);
                getAm().getDBTransaction().commit();
                if (getAm().getDBTransaction().isDirty() == false) {
                    getAm().getMmDrftPo().getCurrentRow().setAttribute("TransDlvDate", null);
                    getAm().getMmDrftPo().getCurrentRow().setAttribute("TransWhIdDlv", "");
                    getAm().getMmDrftPo().getCurrentRow().setAttribute("TransDlvMode", "");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dateDlvSchdl);
                }
                // setShowPaymentSchdlTab("false");
            } else {
                sdi.setDisclosed(false);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabed);
    }

    public void goToTermsAction(ActionEvent actionEvent) {
        PurOrderAMImpl am = getAm();
        ViewObject po = am.getMmDrftPo();
        ViewObject poItm = am.getMmDrftPoItm();
        Row currRow = po.getCurrentRow();
        currRow.setAttribute("PoMode", PO_MODE_INCOMPLETE);
        Integer poType = Integer.parseInt(currRow.getAttribute("PoType").toString());
        System.out.println("PO TYPE=" + poType);
        System.out.println("Inside goToTermsAction :::::::  ");
        if(poType.compareTo(PO_TYPE_IMPORT)==0){
            adfLog.info(" if part PO Type than Import");
        RichPanelTabbed richPanelTabbed = getPanelTabed();
        for (UIComponent child : richPanelTabbed.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem)child;
            //pt1:drMM1:2:sdi4
            if (sdi.getClientId().equals(portsContnrTab.getClientId())) {
                sdi.setDisclosed(true);
                setShowItemsTab("false");
                setShowDlvSchdlTab("false");
                setShowPaymentSchdlTab("false");
                setShowTnAgreeTab("false");
                setShowPortAndContnrTab("true");
              

                //OperationBinding ob4 = getBindings().getOperationBinding("Execute4");
               // ob4.execute();


                /*   OperationBinding ope=getBindings().getOperationBinding("setCurrentRowOnPO");
                ope.execute();
                System.out.println("Error on setCurrentRowOnPO="+ope.getErrors());
                 */

                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                try {
                    operationBinding.execute();
                    //   System.out.println("error on commit="+operationBinding.getErrors());
                } catch (oracle.jbo.TxnValException e) {
                    System.out.println("Error on Commit=" + e.getRowDefFullName());
                }
            } else {
                sdi.setDisclosed(false);
            }
        }
        }else{
            adfLog.info(" else part PO Type other than Import");
            RichPanelTabbed richPanelTabbed = getPanelTabed();
            for (UIComponent child : richPanelTabbed.getChildren()) {
                RichShowDetailItem sdi = (RichShowDetailItem)child;
                //pt1:drMM1:2:sdi4
                if (sdi.getClientId().equals(termsAndAggTab.getClientId())) {
                    sdi.setDisclosed(true);
                    setShowItemsTab("false");
                    setShowDlvSchdlTab("false");
                    setShowPaymentSchdlTab("false");
                    setShowTnAgreeTab("true");
                    setShowPortAndContnrTab("false");

                    OperationBinding ob4 = getBindings().getOperationBinding("Execute4");
                    ob4.execute();


                    /*   OperationBinding ope=getBindings().getOperationBinding("setCurrentRowOnPO");
                    ope.execute();
                    System.out.println("Error on setCurrentRowOnPO="+ope.getErrors());
                     */

                    OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                    try {
                        operationBinding.execute();
                        //   System.out.println("error on commit="+operationBinding.getErrors());
                    } catch (oracle.jbo.TxnValException e) {
                        System.out.println("Error on Commit=" + e.getRowDefFullName());
                    }
                } else {
                    sdi.setDisclosed(false);
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabed);
    }
    
    public void goToTermsActionFromPorts(ActionEvent actionEvent) {
        adfLog.info(" from port part PO Type other than Import");
        
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        ViewObjectImpl poVo = getAm().getMmDrftPo();
        Row poR = poVo.getCurrentRow();
        
        
        
        Integer tableCount = portDetailTableBind.getRowCount();
        if(tableCount>0){ 
        Date lastEtdDate =null;
        ViewObjectImpl portvo = getAm().getMmDrftPoPorts();
        RowQualifier rq = new RowQualifier(portvo);
        rq.setWhereClause("CldId = '"+cld_id+"' and SlocId = "+sloc_id+" and OrgId = '"+OrgId+"' and DocId ='"+poR.getAttribute("DocId").toString()+"' and TrvlSeq = "+tableCount+" ");
        Row rr[] = portvo.getFilteredRows(rq);
        adfLog.info(rq.getExprStr()+"   filter port :::   "+rr.length);
        if(rr.length>0){
            lastEtdDate= (Date)rr[0].getAttribute("Etd");
        }
        if(lastEtdDate!=null){
                String msg2 = "ETD date must be null for last Travel Sequence . "+tableCount;
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, message2);
                return ;
            }
       
                
        RichPanelTabbed richPanelTabbed = getPanelTabed();        
        for (UIComponent child : richPanelTabbed.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem)child;
            //pt1:drMM1:2:sdi4
            if (sdi.getClientId().equals(termsAndAggTab.getClientId())) {
                sdi.setDisclosed(true);
                setShowItemsTab("false");
                setShowDlvSchdlTab("false");
                setShowPaymentSchdlTab("false");
                setShowTnAgreeTab("true");
                setShowPortAndContnrTab("false");

                OperationBinding ob4 = getBindings().getOperationBinding("Execute4");
                ob4.execute();


                /*   OperationBinding ope=getBindings().getOperationBinding("setCurrentRowOnPO");
                ope.execute();
                System.out.println("Error on setCurrentRowOnPO="+ope.getErrors());
                 */

                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                try {
                    operationBinding.execute();
                    //   System.out.println("error on commit="+operationBinding.getErrors());
                } catch (oracle.jbo.TxnValException e) {
                    System.out.println("Error on Commit=" + e.getRowDefFullName());
                }
            } else {
                sdi.setDisclosed(false);
            }
        }
        }else{
            RichPanelTabbed richPanelTabbed = getPanelTabed();        
            for (UIComponent child : richPanelTabbed.getChildren()) {
                RichShowDetailItem sdi = (RichShowDetailItem)child;
                //pt1:drMM1:2:sdi4
                if (sdi.getClientId().equals(termsAndAggTab.getClientId())) {
                    sdi.setDisclosed(true);
                    setShowItemsTab("false");
                    setShowDlvSchdlTab("false");
                    setShowPaymentSchdlTab("false");
                    setShowTnAgreeTab("true");
                    setShowPortAndContnrTab("false");

                    OperationBinding ob4 = getBindings().getOperationBinding("Execute4");
                    ob4.execute();


                    /*   OperationBinding ope=getBindings().getOperationBinding("setCurrentRowOnPO");
                    ope.execute();
                    System.out.println("Error on setCurrentRowOnPO="+ope.getErrors());
                     */

                    OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                    try {
                        operationBinding.execute();
                        //   System.out.println("error on commit="+operationBinding.getErrors());
                    } catch (oracle.jbo.TxnValException e) {
                        System.out.println("Error on Commit=" + e.getRowDefFullName());
                    }
                } else {
                    sdi.setDisclosed(false);
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabed);
    }

    public void setShowItemsTab(String showItemsTab) {
        this.showItemsTab = showItemsTab;
    }

    public String getShowItemsTab() {
        return showItemsTab;
    }

    public void setShowDlvSchdlTab(String showDlvSchdlTab) {
        this.showDlvSchdlTab = showDlvSchdlTab;
    }

    public String getShowDlvSchdlTab() {
        return showDlvSchdlTab;
    }

    public void setShowPaymentSchdlTab(String showPaymentSchdlTab) {
        this.showPaymentSchdlTab = showPaymentSchdlTab;
    }

    public String getShowPaymentSchdlTab() {
        return showPaymentSchdlTab;
    }


    public void setTncTableBind(RichTable tncTableBind) {
        this.tncTableBind = tncTableBind;
    }

    public RichTable getTncTableBind() {
        return tncTableBind;
    }

    public void setShowTnAgreeTab(String showTnAgreeTab) {
        this.showTnAgreeTab = showTnAgreeTab;
    }

    public String getShowTnAgreeTab() {
        return showTnAgreeTab;
    }

    public void delDlvrySchdlAction(ActionEvent actionEvent) {
        ViewObjectImpl mmdlvVo = getAm().getMmDrftPoDlvSchdl();
        RowQualifier dsnrq = new RowQualifier(mmdlvVo);
        Row currRow = mmdlvVo.getCurrentRow();
        dsnrq.setWhereClause("DlvDt='" + currRow.getAttribute("DlvDt") + "' And WhId='" +
                             currRow.getAttribute("WhId") + "'");
        Row[] dsnrow = mmdlvVo.getFilteredRows(dsnrq);
        if (dsnrow.length > 1) {
            currRow.remove();
        } else {
            Integer delDlvNo = (Integer)currRow.getAttribute("DlvSchdlNo");
            currRow.remove();
            //Update Schedule no. for remaining Rows.
            RowSetIterator rsi = mmdlvVo.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row itrRow = rsi.next();
                Integer no = new Integer(1);
                if (itrRow.getAttribute("DlvSchdlNo") != null)
                    no = (Integer)itrRow.getAttribute("DlvSchdlNo");
                if (no > delDlvNo)
                    itrRow.setAttribute("DlvSchdlNo", no - 1);
            }
            rsi.closeRowSetIterator();
        }

        getAm().getDBTransaction().postChanges();
        getAm().getDlvSchdlBalanceView().executeQuery();
        getAm().getMmDrftPoDlvSchdl().executeQuery();

    }

    public void setEoNameTransBind(RichInputListOfValues eoNameTransBind) {
        this.eoNameTransBind = eoNameTransBind;
    }

    public RichInputListOfValues getEoNameTransBind() {
        return eoNameTransBind;
    }

    public void setTransTotalPoCostBind(RichInputText transTotalPoCostBind) {
        this.transTotalPoCostBind = transTotalPoCostBind;
    }

    public RichInputText getTransTotalPoCostBind() {
        return transTotalPoCostBind;
    }

    public void setTransTotalPoCostBsBind(RichInputText transTotalPoCostBsBind) {
        this.transTotalPoCostBsBind = transTotalPoCostBsBind;
    }

    public RichInputText getTransTotalPoCostBsBind() {
        return transTotalPoCostBsBind;
    }

    public void setSupAndItmTab(RichShowDetailItem supAndItmTab) {
        this.supAndItmTab = supAndItmTab;
    }

    public RichShowDetailItem getSupAndItmTab() {
        return supAndItmTab;
    }

    public void setTermsAndAggTab(RichShowDetailItem termsAndAggTab) {
        this.termsAndAggTab = termsAndAggTab;
    }

    public RichShowDetailItem getTermsAndAggTab() {
        return termsAndAggTab;
    }

    public void setPaySchdlTab(RichShowDetailItem paySchdlTab) {
        this.paySchdlTab = paySchdlTab;
    }

    public RichShowDetailItem getPaySchdlTab() {
        return paySchdlTab;
    }

    public void commitButton(ActionEvent actionEvent) {
        ViewObject po = getAm().getMmDrftPo();
        int dlvRows = getAm().getDlvSchdlBalanceView().getAllRowsInRange().length;
        Row currPo = getAm().getMmDrftPo().getCurrentRow();

        MmDrftPoVORowImpl rowImpl = (MmDrftPoVORowImpl)getAm().getMmDrftPo().getCurrentRow();


        Object obj1 = currPo.getAttribute("TransSumPoPmtAmt");
        Object obj2 = currPo.getAttribute("TransTotalPoCostSp");
        Object obj3 = currPo.getAttribute("TransTotalPoCostBs");
        Object obj4 = currPo.getAttribute("TransCurrencyDesc");

        Number zero = new Number(0);

        Number poCost = zero;
        Number poPmtAmt = zero;
        Number poCostBs = zero;
        String spCur = "INR";

        if (obj1 != null) {
            poCost = (Number)obj2;
        }
        if (obj2 != null) {
            poPmtAmt = (Number)obj1;
        }
        if (obj3 != null) {
            poCostBs = (Number)obj3;
        }
        if (obj4 != null) {
            spCur = obj4.toString();
        }

        // System.out.println(spCur+"---"+poCost+"---POCOST----POPMTAMT--"+poPmtAmt+"---dlvrws--"+dlvRows);

        // if(dlvRows==0 && poCost.compareTo(zero)==1 && poPmtAmt.compareTo(zero)==1 && poCost.compareTo(poPmtAmt)==0){
        //  System.out.println("In sAVE MSG");
        // currPo.setAttribute("PoMode", "D");

        RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "V");
        rowImpl.setPoMode(PO_MODE_DRAFT);
        getAm().getMmDrftPo().executeQuery();
        getAm().getDBTransaction().commit();

        // }
    }

    public void ocAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number ob = (Number)object;
        Number zero = new Number(0);
        ViewObjectImpl quot = getAm().getMmDrftPo();
        ViewObjectImpl quotOc = getAm().getMmDrftPoOc();
        Row quotOcrow = quotOc.getCurrentRow();
        Row row = quot.getCurrentRow();
        String tranType = quotOcrow.getAttribute("TranType").toString();
        Number totalquotSpAmt = (Number)sumitemSpBind.getValue();
        System.out.println("TOtal cost>>>" + totalquotSpAmt + "oc>>" + ob);
        Number currFactor = new Number(1);
        if (row.getAttribute("CurrConvFctr") != null) {
            currFactor = (Number)row.getAttribute("CurrConvFctr");
        }

        Number tot = ob.multiply(currFactor);
        Boolean qtyFlag = isPrecisionValid(26, 6, ob);
        Boolean totFlag = isPrecisionValid(26, 6, tot);


        if (object != null) {
            if (tranType.equalsIgnoreCase("S") && ob.compareTo(totalquotSpAmt) == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.446']}"), null));
            }
            Number result =
                (totalquotSpAmt.subtract(ob)).subtract((Number)poWiseDiscount.getValue()).add((Number)poTaxAmtBind.getValue());
            System.out.println(tranType + "result-- " + result);
            if (tranType.equalsIgnoreCase("S") && result.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.448']}"), null));
            }

            if (totFlag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.405']}"), null));
            }

            if (ob.compareTo(zero) != 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.265']}"), null));

            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvEl("#{bundle['MSG.265']}"), null));

        }


    }

    public void itmPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //System.out.println("--itmIdBind.getValue()-"+itmIdBind.getValue());
        if (object != null) {
            Number ob = (Number)object;
            Number zero = new Number(0);

            ViewObjectImpl po = getAm().getMmDrftPo();
            Row row = po.getCurrentRow();

            Number currFactor = new Number(1);
            Number qty = new Number(0);

            if (row.getAttribute("CurrConvFctr") != null) {
                currFactor = (Number)row.getAttribute("CurrConvFctr");
            }
            if (itmQtyBind.getValue() != null) {
                qty = (Number)itmQtyBind.getValue();
            }
            Number tot = ob.multiply(qty).multiply(currFactor);
            Boolean totFlag = isPrecisionValid(26, 6, tot);

            if (totFlag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.405']}"), null));
            }
adfLog.info(" ob.compareTo(zero) :::: "+ob.compareTo(zero));
            if (ob.compareTo(zero) != 1 && itmIdBind.getValue() != null) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.315']}"), null));

            }
        } else if (object != null && itmIdBind.getValue() != null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvEl("#{bundle['MSG.315']}"), null));

        }
    }

    public void setItmIdBind(RichInputListOfValues itmIdBind) {
        this.itmIdBind = itmIdBind;
    }

    public RichInputListOfValues getItmIdBind() {
        return itmIdBind;
    }

    public void setTransSchdlQuantity(RichInputText transSchdlQuantity) {
        this.transSchdlQuantity = transSchdlQuantity;
    }

    public RichInputText getTransSchdlQuantity() {
        return transSchdlQuantity;
    }

    public void setFrmDateBind(RichInputDate frmDateBind) {
        this.frmDateBind = frmDateBind;
    }

    public RichInputDate getFrmDateBind() {
        return frmDateBind;
    }

    public void frmDateVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(toDateBind);
    }

    public void setToDateBind(RichInputDate toDateBind) {
        this.toDateBind = toDateBind;
    }

    public RichInputDate getToDateBind() {
        return toDateBind;
    }


    public void setItmUomBind(RichSelectOneChoice itmUomBind) {
        this.itmUomBind = itmUomBind;
    }

    public RichSelectOneChoice getItmUomBind() {
        return itmUomBind;
    }

    public void tlrncDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer ob = Integer.parseInt(object.toString());
            Integer zero = new Integer(0);
            if (ob.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.452']}"), null));

            }
        }
    }

    public void setItmPriceBind(RichInputText itmPriceBind) {
        this.itmPriceBind = itmPriceBind;
    }

    public RichInputText getItmPriceBind() {
        return itmPriceBind;
    }

    public void setItmQtyBind(RichInputText itmQtyBind) {
        this.itmQtyBind = itmQtyBind;
    }

    public RichInputText getItmQtyBind() {
        return itmQtyBind;
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void setOcTableBind(RichTable ocTableBind) {
        this.ocTableBind = ocTableBind;
    }

    public RichTable getOcTableBind() {
        return ocTableBind;
    }

    public void supplierVCE(ValueChangeEvent valueChangeEvent) {
        policyNm = null;
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
        String supName = valueChangeEvent.getNewValue().toString();
        Row[] suprows = getAm().getLovEoId().getFilteredRows("EoNm", supName);
        itmNameForLink = "";
        String name = null;
        if (suprows.length > 0) {
            name = suprows[0].getAttribute("EoId").toString();
        }
        // String name=getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId").toString();
        if (name != null) {
            Row[] prfrow = getAm().getOrgMmPrf1().getFilteredRows("OrgId", pOrgId);
            String policy = "N";
            if (prfrow.length > 0)
                if (prfrow[0].getAttribute("ApplyPriceFrmPolicy") != null)
                    policy = (String)prfrow[0].getAttribute("ApplyPriceFrmPolicy");
            if (policy.equals("N")) {
                policyNm = null;
            } else {
                RowQualifier rq = new RowQualifier(getAm().getAppEoPricePlc());
                rq.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + p_sloc_id + " and HoOrgId='" + p_hoOrgId +
                                  "' and EoId=" + name);
                Row[] r = getAm().getAppEoPricePlc().getFilteredRows(rq);
                if (r.length > 0) {
                    policyNm = (String)r[0].getAttribute("PlcNm");
                    getAm().getMmDrftPo().getCurrentRow().setAttribute("PlcId", r[0].getAttribute("PlcId"));
                } else {
                    policyNm = null;
                }
            }


            if (suprows.length > 0) {
                Integer currId = (Integer)suprows[0].getAttribute("SuppCurrId");
                Number currConv = (Number)suprows[0].getAttribute("ConvFctr");
                String billaddds = null;
                Row poRow = getAm().getMmDrftPo().getCurrentRow();
                if (suprows[0].getAttribute("DfltBillAdds") != null) {
                    billaddds = (String)suprows[0].getAttribute("DfltBillAdds");
                    poRow.setAttribute("BillAddsId", billaddds);
                }

                poRow.setAttribute("CurrIdSp", currId);
                poRow.setAttribute("CurrConvFctr", currConv);
                System.out.println("CurrId=" + currId);
            }

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(eoIdDbBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
    }

    public void setItmNameForLink(String itmNameForLink) {
        this.itmNameForLink = itmNameForLink;
    }

    public String getItmNameForLink() {
        return itmNameForLink;
    }

    public void setLinkSupPopUp(RichPopup linkSupPopUp) {
        this.linkSupPopUp = linkSupPopUp;
    }

    public RichPopup getLinkSupPopUp() {
        return linkSupPopUp;
    }

    public void supplierLinkDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            Row poRow = getAm().getMmDrftPo().getCurrentRow();
            Row poItmRow = getAm().getMmDrftPoItm().getCurrentRow();
            Number zero = new Number(0);

            Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            Integer pEoId = Integer.parseInt(poRow.getAttribute("EoId").toString());
            Integer pUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();

            oracle.jbo.domain.Date pdateCurr = (oracle.jbo.domain.Date)new oracle.jbo.domain.Date().getCurrentDate();

            Number pPrice = zero;
            Number pQty = zero;
            String pDiscType = "A";
            Number pDiscVal = zero;
            String pTlrncType = "A";
            Number pTlrncVal = zero;
            String pLeadTime = null;

            RowSetIterator rsi = getAm().getMmDrftPoItm().createRowSetIterator(null);
            //  String link="N";
            while (rsi.hasNext()) {
                Row r = rsi.next();
                ViewObjectImpl voi = getAm().getLovItmIdEo();
                RowQualifier rq = new RowQualifier(voi);

                String pItmId = r.getAttribute("ItmId").toString();
                String pItmUom = r.getAttribute("ItmUom").toString();

                if (r.getAttribute("ItmPrice") != null) {
                    pPrice = (Number)r.getAttribute("ItmPrice");
                }
                if (r.getAttribute("OrdQty") != null) {
                    pQty = (Number)r.getAttribute("OrdQty");
                }
                if (r.getAttribute("DiscType") != null) {
                    pDiscType = r.getAttribute("DiscType").toString();
                }

                if (r.getAttribute("DiscVal") != null) {
                    pDiscVal = (Number)r.getAttribute("DiscVal");
                }
                if (r.getAttribute("TlrncQtyType") != null) {
                    pTlrncType = r.getAttribute("TlrncQtyType").toString();
                }

                if (r.getAttribute("TlrncQtyVal") != null) {
                    pTlrncVal = (Number)r.getAttribute("TlrncQtyVal");
                }

                if (r.getAttribute("ItmId") != null) {
                    rq.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and OrgId='" + pOrgId +
                                      "' and EoId=" + pEoId + " and ItmId='" + r.getAttribute("ItmId").toString() +
                                      "' and Actv='Y'");
                    Row[] rows = voi.getFilteredRows(rq);
                    //System.out.println("Row length---"+rows.length);
                    if (rows.length == 0) {
                        //     link="Y";
                        Integer retval =
                            Integer.parseInt(callStoredFunction(INTEGER, "MM.MM_INS_EO_ITM_AUTO(?,?,?,?,?,?,?,?,?,?,?)",
                                                                new Object[] { p_cldId, pslocId, p_hoOrgId, pOrgId,
                                                                               pEoId, poRow.getAttribute("CurrIdSp"),
                                                                               pItmId, pItmUom, pUsrId, 18504,
                                                                               poRow.getAttribute("DocId") }).toString());
                        /*            Integer.parseInt(callStoredFunction(INTEGER, "MM.PKG_MM_EOITM.INS_EO_ITM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                new Object[] { pslocId,p_cldId, pOrgId, pEoId, pItmId, pItmUom,
                                                                               pPrice, pQty, pDiscType, pDiscVal,
                                                                               pTlrncType, pTlrncVal, pLeadTime,
                                                                               pUsrId, pdateCurr, 1 }).toString()); */
                        System.out.println("retval in link" + retval);


                    }
                    Object eo = getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId");
                    Number convFctr = (Number)r.getAttribute("UomConvFctr");
                    if (eo != null) {
                        String eoId = eo.toString();
                        ViewObjectImpl voiagain = getAm().getLovItmIdEo();
                        voiagain.executeQuery();
                        RowQualifier rqagain = new RowQualifier(voiagain);
                        rqagain.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and OrgId='" +
                                               pOrgId + "' and EoId=" + eoId + " and ItmId='" + pItmId +
                                               "' and Actv='Y'");
                        System.out.println(rqagain.getExprStr());
                        Row[] rowsagain = voiagain.getFilteredRows(rqagain);
                        System.out.println("No. of filtr row=" + rowsagain.length);
                        if (rowsagain.length > 0) {
                            System.out.println("ItmPriceBs for supplier=" +
                                               rowsagain[0].getAttribute("ItmPriceUomBs"));
                            Number price = new Number(0);
                            if (rowsagain[0].getAttribute("ItmPriceUomBs") != null)
                                price = (Number)rowsagain[0].getAttribute("ItmPriceUomBs");
                            r.setAttribute("ItmPrice", price.multiply(convFctr));
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);

                            //  getAm().getMmDrftPoItm().getCurrentRow().setAttribute("ItmPriceBs",price);
                        }
                    }

                }
            }

            rsi.closeRowSetIterator();

            //P_SLOCID   IN MM.MM$EO$ITM.SLOC_ID%TYPE,
            //                         P_ORGID    IN MM.MM$EO$ITM.ORG_ID%TYPE,
            //                         P_EOID     IN MM.MM$EO$ITM.EO_ID%TYPE,
            //                         P_ITMID    IN MM.MM$EO$ITM.ITM_ID%TYPE,
            //                         P_ITMUOM   IN MM.MM$EO$ITM.ITM_UOM%TYPE,
            //                         P_PRICE    IN MM.MM$EO$ITM.ITM_PRICE%TYPE,
            //                         P_QTY      IN MM.MM$EO$ITM.ORD_QTY%TYPE,
            //                         P_DISC_TYP IN MM.MM$EO$ITM.DISC_TYPE%TYPE,
            //                         P_DISC_VAL IN MM.MM$EO$ITM.DISC_VAL%TYPE,
            //                         P_TL_TYP   IN MM.MM$EO$ITM.TLRNC_TYPE%TYPE,
            //                         P_TL_VAL   IN MM.MM$EO$ITM.TLRNC_VAL%TYPE,
            //                         P_LEADTIME IN MM.MM$EO$ITM.LEAD_TIME%TYPE,
            //                         P_USRID    IN MM.MM$EO$ITM.USR_ID_CREATE%TYPE,
            //                         P_USR_DT   IN MM.MM$EO$ITM.USR_ID_CREATE_DT%TYPE,
            //                         P_ENTID    IN MM.MM$EO$ITM.ENTITY_ID%TYPE)

            /*  String pItmId = poItmRow.getAttribute("ItmId").toString();
            String pItmUom = poItmRow.getAttribute("ItmUom").toString();
            */


            /*   System.out.println("--" + pslocId + "---" + pOrgId + "--" + pEoId + "-----" + pItmId + "-----" + pItmUom +
                               "----" + pPrice + "----" + pQty + "----" + pDiscType + "---" + pDiscVal + "---" +
                               pTlrncType + "---" + pTlrncVal + "----" + pLeadTime + "---" + pUsrId + "---" +
                               pdateCurr); */

        } else if (dialogEvent.getOutcome().name().equals("no")) {
            eoNameTransBind.setValue("");
            //remove supplier if not present in app$eo$itm
            Row po = getAm().getMmDrftPo().getCurrentRow();
            String name = po.getAttribute("EoId").toString();
            po.setAttribute("EoId", null);
            po.setAttribute("TransEoId", "");
            po.setAttribute("CurrIdSp", 73);
            po.setAttribute("CurrConvFctr", 1);
            po.setAttribute("BillAddsId", "");
            po.setAttribute("TransBillAddsId", "");

            /*   RowSetIterator rsi= getAm().getMmDrftPoItm().createRowSetIterator(null);
            String link="N";
            while(rsi.hasNext()){
                Row r=rsi.next();
                ViewObjectImpl voi = getAm().getLovItmIdEo();
                RowQualifier rq = new RowQualifier(voi);
                //String itmId=rw.getAttribute("TransItemId").toString();
                // System.out.println("ItmId"+itmId);
                String itmNameForLink="";
                if(r.getAttribute("ItmId")!=null){
                     rq.setWhereClause("EoId='" + name + "' and ItmId='" + r.getAttribute("ItmId").toString() + "' and Actv='Y'");
                     Row[] rows = voi.getFilteredRows(rq);
                    //System.out.println("Row length---"+rows.length);
                    if (rows.length == 0) {
                            r.remove();
                    }
                }
            }

            rsi.closeRowSetIterator();
            getAm().getMmDrftPoItm().executeQuery();

            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind); */
            disclosedItem.setDisclosed(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(disclosedItem);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoNameTransBind);
        }
    }

    public void setSumitemSpBind(RichInputText sumitemSpBind) {
        this.sumitemSpBind = sumitemSpBind;
    }

    public RichInputText getSumitemSpBind() {
        return sumitemSpBind;
    }

    public void setSumgroupBind(RichPanelGroupLayout sumgroupBind) {
        this.sumgroupBind = sumgroupBind;
    }

    public RichPanelGroupLayout getSumgroupBind() {
        return sumgroupBind;
    }

    public void discountPOValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number hund = new Number(100);
        Number zero = new Number(0);
        Number val = (Number)object;
        Boolean okFlag = isPrecisionValid(26, 6, val);
        String discTyp = discType.getValue().toString();
        Number totcost =((Number)totalPoCastBeforeTax.getValue());
        adfLog.info(totcost+"   totcost  :::: "+val);
        if (okFlag == false) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvEl("#{bundle['MSG.405']}"), null));
        }

        if (discTyp.equalsIgnoreCase("P")) {
            if (val.compareTo(hund) >= 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.455']}"), null));

            } else if (val.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.456']}"), null));
            }
        } else if (discTyp.equalsIgnoreCase("A")) {
            if (val.compareTo(totalPoCastBeforeTax.getValue()) >= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.457']}") +
                                                              sumitemSpBind.getValue(), null));

            }
            if (((Number)totalPoCastBeforeTax.getValue()).compareTo(zero) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.458']}"), null));

            }

        }
    }

    public void setDiscType(RichSelectOneRadio discType) {
        this.discType = discType;
    }

    public RichSelectOneRadio getDiscType() {
        return discType;
    }

    public void setPoDlvSchlTable(RichTable poDlvSchlTable) {
        this.poDlvSchlTable = poDlvSchlTable;
    }

    public RichTable getPoDlvSchlTable() {
        return poDlvSchlTable;
    }

    public void setEnableSaveAs(Boolean enableSaveAs) {
        this.enableSaveAs = enableSaveAs;
    }

    public Boolean getEnableSaveAs() {
        //AdfFacesContext.getCurrentInstance().addPartialTarget(saveAsButton);
        //    System.out.println("getAm().getDBTransaction().isDirty()"+getAm().getDBTransaction().isDirty());
        return getAm().getDBTransaction().isDirty();
    }


    public void selectSupplierValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String eoName = (String)object;
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
        String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");

        Integer p_eoId = null;
        Object convfctr = null;
        if (eoName != null) {
            Row[] xx = getAm().getLovEoId().getFilteredRows("EoNm", eoName);
            if (xx.length > 0) {
                p_eoId = Integer.parseInt(xx[0].getAttribute("EoId").toString());
                convfctr = xx[0].getAttribute("ConvFctr");

                //  System.out.println("p_eoId------"+p_eoId);
                String flag = "Y";
                if (p_eoId != null) {
                    if (convfctr != null) {
                        flag =
(String)callStoredFunction(Types.VARCHAR, "APP.PKG_APP_EO.IS_SUPP_VALID(?,?,?,?,?)", new Object[] { p_sloc_id, p_cldId,
                                                                                                    p_hoOrgId, pOrgId,
                                                                                                    p_eoId });

                        // System.out.println("supplier flag------------"+flag);
                        if (flag.equalsIgnoreCase("B")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          resolvEl("#{bundle['MSG.459']}"), null));
                        } else if (flag.equalsIgnoreCase("H")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          resolvEl("#{bundle['MSG.460']}"), null));
                        } else {
                            /*  Row[] prfrow=getAm().getOrgMmPrf1().getFilteredRows("OrgId",pOrgId);
                    String policy="N";
                    if( prfrow.length>0)
                                  if(prfrow[0].getAttribute("ApplyPriceFrmPolicy")!=null)
                                  policy = (String)prfrow[0].getAttribute("ApplyPriceFrmPolicy");
                              if(policy.equals("N"))
                              {
                              }else{
              RowQualifier rq=new RowQualifier(getAm().getAppEoPricePlc());
                rq.setWhereClause("CldId='"+p_cldId+"' and SlocId="+p_sloc_id+" and HoOrgId='"+p_hoOrgId+"' and EoId="+p_eoId);
                Row[] r=    getAm().getAppEoPricePlc().getFilteredRows(rq);
                if(r.length>0)
                {
                }
                else
                {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Price Policy is not defined for this Supplier.",
                                                                      null));
                    }
                } */
                        }
                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Currency Rate is not defined for this Supplier.",
                                                                      null));
                    }
                }
            }
        }
    }

    public void uomItmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String uomId = (String)object;
        ViewObjectImpl quotItm = getAm().getMmDrftPoItm();
        Row quotItmRow = quotItm.getCurrentRow();
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
        String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");

        String p_quot_item_id = null;
        if (quotItmRow.getAttribute("ItmId") != null) {
            p_quot_item_id = quotItmRow.getAttribute("ItmId").toString();
        }
        String flag = "Y";
        if (uomId != null) {
            flag =
(String)callStoredFunction(Types.VARCHAR, "APP.PKG_UOM.IS_UOM_SELECTABLE(?,?,?,?,?)", new Object[] { p_sloc_id,
                                                                                                     p_cldId,
                                                                                                     p_hoOrgId,
                                                                                                     p_quot_item_id,
                                                                                                     uomId });
        }
        //   System.out.println("FLAG_UOM--"+flag);
        if (flag.equalsIgnoreCase("N")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvEl("#{bundle['MSG.461']}"), null));
        }
    }

    public void poDiscountDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            Number zero = new Number(0);
            String discTypePo = "A";
            Number discValPo = zero;
            Number sumItmAmt = zero;
            Number totDiscAmtSum = zero;
            Number totaldiscSum = zero;
            Number diff = zero;
            //     Number discPerc=zero;

            Row currPo = getAm().getMmDrftPo().getCurrentRow();
            if (currPo.getAttribute("DiscType") != null)
                discTypePo = (String)currPo.getAttribute("DiscType");
            if (currPo.getAttribute("DiscVal") != null)
                discValPo = (Number)currPo.getAttribute("DiscVal");

            RowSetIterator rsiItm = getAm().getMmDrftPoItm().createRowSetIterator(null);
            while (rsiItm.hasNext()) {
                Row r = rsiItm.next();
                if (r.getAttribute("TransItmAmtSp") != null) //Item amount without tax
                    sumItmAmt = sumItmAmt.add((Number)r.getAttribute("TransItmAmtSp"));
            }
            rsiItm.closeRowSetIterator();


            Number amt = zero;
            Number itmAmtInPer = zero;
            Number discAmt = zero;
            Number discAmtItm = zero;
            RowSetIterator rsiItmPer = getAm().getMmDrftPoItm().createRowSetIterator(null);
            while (rsiItmPer.hasNext()) {
                amt = zero;
                discAmt = zero;
                itmAmtInPer = zero;
                discAmtItm = zero;
                Row r = rsiItmPer.next();

                if (r.getAttribute("TransItmAmtSp") != null) //Item amount without tax
                    amt = ((Number)r.getAttribute("TransItmAmtSp"));


                if (discTypePo.equals("A")) {
                    currPo.setAttribute("DiscAmtSp", discValPo);
                    itmAmtInPer =
                            new Number((amt.multiply(new Number(100)).divide(sumItmAmt)).round(6)); //Item amount in percent of total amount
                    adfLog.info("itmAmtInPer  ::::::::    " + itmAmtInPer);
                    discAmt =
                            new Number(((discValPo.multiply(itmAmtInPer)).divide(new Number(100))).round(6)); //discount amount for an item
                    adfLog.info("discAmt  ::::::::    " + discAmt);
                } else {
                    discAmt =
                            new Number(((amt.multiply(discValPo)).divide(new Number(100))).round(6)); //if discount in percent then calculate discount amount.
                    adfLog.info("else discAmt  ::::::::    " + discAmt);
                    currPo.setAttribute("DiscAmtSp", discAmt);
                }

                if (r.getAttribute("DiscAmtSp") != null)
                    discAmtItm = (Number)r.getAttribute("DiscAmtSp");

                r.setAttribute("TotDiscAmtSp", discAmtItm.add(discAmt));

                totDiscAmtSum = totDiscAmtSum.add((Number)r.getAttribute("TotDiscAmtSp"));

                adfLog.info("totDiscAmtSum    :::::::  " + totDiscAmtSum);
            }
            rsiItmPer.closeRowSetIterator();
            Number sumItmDisc = zero;
            if (currPo.getAttribute("TransSumItmDiscount") != null)
                sumItmDisc = (Number)currPo.getAttribute("TransSumItmDiscount");
            Number poDiscAmt = zero;
            if (currPo.getAttribute("TransPoDiscountAmt") != null)
                poDiscAmt = (Number)currPo.getAttribute("TransPoDiscountAmt");
            totaldiscSum = sumItmDisc.add(poDiscAmt);
            diff = totaldiscSum.subtract(totDiscAmtSum);
            adfLog.info(diff + "  ::::  diff:: " + totaldiscSum);
            Row itmRow[] = getAm().getMmDrftPoItm().getAllRowsInRange();
            if (itmRow.length > 0) {
                Number totdisc = zero;
                Integer len = (itmRow.length) - 1;
                Row r = itmRow[len];
                if (r.getAttribute("TotDiscAmtSp") != null)
                    totdisc = (Number)r.getAttribute("TotDiscAmtSp");
                r.setAttribute("TotDiscAmtSp", totdisc.add(diff));
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(poWiseDiscount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            //  System.out.println("Discount--"+poDiscount);
            /*    getAm().getMmDrftPo().getCurrentRow().setAttribute("DiscVal", poDiscount);
           */
            AdfFacesContext.getCurrentInstance().addPartialTarget(poWiseDiscount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
        }
    }

    public void setPoWiseDiscount(RichInputText poWiseDiscount) {
        this.poWiseDiscount = poWiseDiscount;
    }

    public RichInputText getPoWiseDiscount() {
        return poWiseDiscount;
    }

    public void setPoDiscount(Number poDiscount) {
        this.poDiscount = poDiscount;
    }

    public Number getPoDiscount() {
        return poDiscount;
    }

    public void poDiscountCancelListener(PopupCanceledEvent popupCanceledEvent) {
        PurOrderAMImpl aMImpl = getAm();
        Row cur = aMImpl.getMmDrftPo().getCurrentRow();
        System.out.println("This is poDiscountCancelListener");
        // if(poWiseDiscount.isValid()==false){
        cur.setAttribute("DiscType", poDiscType);
        cur.setAttribute("DiscVal", poDiscount);

        //}
        AdfFacesContext.getCurrentInstance().addPartialTarget(poWiseDiscount);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
    }

    public void setTransSumOcAmt(RichInputText transSumOcAmt) {
        this.transSumOcAmt = transSumOcAmt;
    }

    public RichInputText getTransSumOcAmt() {
        return transSumOcAmt;
    }

    public void poDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Date currDt = (Date)Date.getCurrentDate();
        Date poDate = (Date)object;
        String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String pSloc = (String)resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}");
        Object oDt =
            callStoredFunction(Types.VARCHAR, "APP.PKG_APP.GET_FY_STAT_FOR_TXN(?,?,?)", new Object[] { pSloc, pOrgId,
                                                                                                       poDate });
        if (oDt == null) {
            String msg2 = "Financial Year is not defined.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        } else if ("Y".equalsIgnoreCase(oDt.toString())) {
            //  java.sql.Date s =(java.sql.Date)oDt;
            //   Date strtDate = new Date(s);
            //System.out.println(s + "Start Date of FY:" + (Date)strtDate);
            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");

            /*    if (strtDate != null) {
                if (poDate.compareTo(strtDate) == -1) {
          */String msg2 = resolvEl("#{bundle['MSG.462']}");
            FacesMessage message2 = new FacesMessage(msg2);
            // message2.setDetail(resolvEl("#{bundle['MSG.463']}") + s);
            message2.setDetail(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }

        if (getAm().getMmDrftPo().getCurrentRow().getAttribute("PoBasis") != null &&
            getAm().getMmDrftPo().getCurrentRow().getAttribute("PoBasis").toString().equals("490") && object != null) {


            OperationBinding checkOp = getBindings().getOperationBinding("checkOpenPurchaseDateValidity");
            checkOp.getParamsMap().put("opDate", poDate);
            checkOp.execute();
            Integer ret = 0;
            if (checkOp.getResult() != null) {
                ret = Integer.parseInt(checkOp.getResult().toString());
            }
            adfLog.info(ret.compareTo(new Integer(1)) + " ret    :::::::::::::::     " + ret);
            if (ret.compareTo(new Integer(1)) == 0 || ret.compareTo(new Integer(2)) == 0) {
                String msg2 = "PO date must be between from and To date for selected open purchase order.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }


        }
    }


    public void setItmDiscAmtBind(RichInputText itmDiscAmtBind) {
        this.itmDiscAmtBind = itmDiscAmtBind;
    }

    public RichInputText getItmDiscAmtBind() {
        return itmDiscAmtBind;
    }

    public void setSaveAsButton(RichCommandButton saveAsButton) {
        this.saveAsButton = saveAsButton;
    }

    public RichCommandButton getSaveAsButton() {
        return saveAsButton;
    }

    /**
     * To set the Address based on the selected supplier - on taskflow return
     * */

    public String setEoAddsTable() {
        System.out.println("inside task flow method");
        //  INS_EO_ADDS(P_SLOCID     IN NUMBER,
        //  P_EOID       IN APP.APP$EO.EO_ID%TYPE,
        // P_ADDSID     IN APP.APP$ADDS$BK.ADDS_ID%TYPE,
        // P_ADDS_TYPE  IN VARCHAR2,
        // P_USRID      IN NUMBER)
        ViewObjectImpl po = getAm().getMmDrftPo();
        Row poRow = po.getCurrentRow();
        Integer p_UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();


        String p_adds_id = (String)poRow.getAttribute("BillAddsId");
        //  System.out.println("adds value---:" + poRow.getAttribute("BillAddsId"));
        Integer p_eo_id = null;
        if (poRow.getAttribute("EoId") != null) {
            p_eo_id = Integer.parseInt(poRow.getAttribute("EoId").toString());
        }
        /*  System.out.println("p_UsrId---" + p_UsrId + "p_sloc_id---" + p_sloc_id + "p_adds_id---" + p_adds_id +
                           "eo----" + p_eo_id);
        */if (p_eo_id != null && p_adds_id != null) {
            BigDecimal addsRetVal =
                (BigDecimal)callStoredFunction(NUMBER, "APP.PKG_APP_EO.INS_EO_ADDS(?,?,?,?,?,?,?)", new Object[] { p_sloc_id,
                                                                                                                   p_cldId,
                                                                                                                   p_hoOrgId,
                                                                                                                   p_eo_id,
                                                                                                                   p_adds_id,
                                                                                                                   "B",
                                                                                                                   p_UsrId });
            // System.out.println("addsRetVal   :" + addsRetVal);
        }

        return "goToTab";
    }

    public void setDiscTypeForItemBind(RichSelectOneRadio discTypeForItemBind) {
        this.discTypeForItemBind = discTypeForItemBind;
    }

    public RichSelectOneRadio getDiscTypeForItemBind() {
        return discTypeForItemBind;
    }

    public void poDiscTypeVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(poDiscAmtBind);
        poDiscAmtBind.validate(FacesContext.getCurrentInstance());
    }

    public void setPoDiscAmtBind(RichInputText poDiscAmtBind) {
        this.poDiscAmtBind = poDiscAmtBind;
    }

    public RichInputText getPoDiscAmtBind() {
        return poDiscAmtBind;
    }

    public void setPoDiscType(String poDiscType) {
        this.poDiscType = poDiscType;
    }

    public String getPoDiscType() {
        return poDiscType;
    }

    public void poDateVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            Date poDt = (Date)vce.getNewValue();
            ViewObject pmtVo = getAm().getMmDrftPoPmtSchdl();
            RowSetIterator rsi = pmtVo.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row pmtRw = rsi.next();
                if (pmtRw.getAttribute("AdvFlg").toString().equalsIgnoreCase("Y")) {
                    if (poDt.compareTo((Date)pmtRw.getAttribute("PayDt")) == -1) {
                        pmtRw.setAttribute("AdvFlg", "N");
                    }
                }
            }

            rsi.closeRowSetIterator();
            pmtVo.executeQuery();

            /*  ViewObject dlvVo=getAm().getMmDrftPoDlvSchdl();
            RowSetIterator rs= dlvVo.createRowSetIterator(null);
            while(rs.hasNext()){
                Row ri=rs.next();
                if(poDt.compareTo((Date)ri.getAttribute("DlvDt"))){
                    s
                }
            } */
        }
    }

    public void setPoDtBind(RichInputDate poDtBind) {
        this.poDtBind = poDtBind;
    }

    public RichInputDate getPoDtBind() {
        return poDtBind;
    }

    public void setPoTaxAmtBind(RichInputText poTaxAmtBind) {
        this.poTaxAmtBind = poTaxAmtBind;
    }

    public RichInputText getPoTaxAmtBind() {
        return poTaxAmtBind;
    }

    public void setTransPmtAdvFlgBind(RichSelectBooleanCheckbox transPmtAdvFlgBind) {
        this.transPmtAdvFlgBind = transPmtAdvFlgBind;
    }

    public RichSelectBooleanCheckbox getTransPmtAdvFlgBind() {
        return transPmtAdvFlgBind;
    }

    public void transPmtAdvFlgVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(pmtDateBind);
    }

    public void setPmtDateBind(RichInputDate pmtDateBind) {
        this.pmtDateBind = pmtDateBind;
    }

    public RichInputDate getPmtDateBind() {
        return pmtDateBind;
    }

    public void setTaxableAmount(Number taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public Number getTaxableAmount() {
        return taxableAmount;
    }

    public void setSuggestedSuppBtnBind(RichCommandButton suggestedSuppBtnBind) {
        this.suggestedSuppBtnBind = suggestedSuppBtnBind;
    }

    public RichCommandButton getSuggestedSuppBtnBind() {
        return suggestedSuppBtnBind;
    }

    /**Method to validate payment date for Duplicate(Same Date) 04-04-2013 @Ashish Kumar*/
    public void pmtDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Date payDt = (Date)object;
        ViewObject dlvScd = getAm().getMmDrftPoPmtSchdl();
        Row rows[] = dlvScd.getFilteredRows("PayDt", payDt);
        System.out.println("Total Rows with same date---->" + rows.length);
        if (rows.length > 0) {
            FacesMessage duplDate = new FacesMessage(resolvEl("#{bundle['MSG.464']}"));
            duplDate.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(duplDate);
        }

    }

    public void dlvDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Date dlvDate = (Date)object;
        //System.out.println("Inside delivery Validator");
        ViewObjectImpl mmdlvVo = getAm().getMmDrftPoDlvSchdl();
        Row curRow = mmdlvVo.getCurrentRow();
        if (curRow != null) {
            System.out.println("Itmid is--->" + curRow.getAttribute("ItmId"));
            if (curRow.getAttribute("ItmId") != null) {
                String itmid = curRow.getAttribute("ItmId").toString();
                RowQualifier rq = new RowQualifier((ViewObjectImpl)mmdlvVo);
                rq.setWhereClause("ItmId='" + itmid + "' And DlvDt=" + dlvDate);
                System.out.println("Row qul-->" + rq.getExprStr());
                Row rows[] = mmdlvVo.getFilteredRows(rq);
                System.out.println("Total Rows with same date---->" + rows.length);
                if (rows.length > 0) {
                    FacesMessage duplDate = new FacesMessage(resolvEl("#{bundle['MSG.410']}"));
                    duplDate.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(duplDate);
                }
            }
        }
    }

    public void setBalanceQtyBind(RichInputText balanceQtyBind) {
        this.balanceQtyBind = balanceQtyBind;
    }

    public RichInputText getBalanceQtyBind() {
        return balanceQtyBind;
    }

    public void suggSupllierCancelListener(PopupCanceledEvent popupCanceledEvent) {
        eoNameTransBind.setDisabled(false);

        ViewObject sugSupVo = getAm().getMmTmpPoSuggSupp();
        RowSetIterator rsi = sugSupVo.createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row rw = rsi.next();
            rw.remove();
        }
        rsi.closeRowSetIterator();
        sugSupVo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(eoNameTransBind); //change BL 2/5

    }

    /*   private String addMode="E";
    public void okbuttonAction(ActionEvent actionEvent) {
       System.out.println("itmIdBind.isValid()"+itmIdBind.isValid());
            itmTableBind.setRowSelection("single");
            addMode="E";

    }

    public void setAddMode(String addMode) {
        this.addMode = addMode;
    }

    public String getAddMode() {
        return addMode;
    }

    public void cancelOkItmAction(ActionEvent actionEvent) {
        PurOrderAMImpl am = (PurOrderAMImpl)resolvElDC("PurOrderAMDataControl");
        Row poItmRow = am.getMmDrftPoItm().getCurrentRow();
        //  poItmRow.remove();
        OperationBinding operationBinding = getBindings().getOperationBinding("Delete");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
    } */

    public void setDisclosedSupplier(RichShowDetail disclosedSupplier) {
        this.disclosedSupplier = disclosedSupplier;
    }

    public RichShowDetail getDisclosedSupplier() {
        return disclosedSupplier;
    }

    public void setEoIdDbBind(RichInputText eoIdDbBind) {
        this.eoIdDbBind = eoIdDbBind;
    }

    public RichInputText getEoIdDbBind() {
        return eoIdDbBind;
    }


    public String saveAndForwardAction() {

        /**   get PO MODE.. if EDIT , same as savePO action ..
         *    if Mode='V' , first check pending user .. then call functions directly.
         **/
        Object mod = RequestContext.getCurrentInstance().getPageFlowScope().get("AddEditMode");
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        PurOrderAMImpl poAM = getAm();
        ViewObject po = poAM.getMmDrftPo();
        Row currPo = po.getCurrentRow();
        String poDocId = po.getCurrentRow().getAttribute("DocId").toString();
        Integer pending = null;
        System.out.println("IN SAVE AND FRWD FUNC:" + poDocId + "--" + mod);
        if (mod != null) {
            if ("V".equalsIgnoreCase(mod.toString())) {
                OperationBinding chkUsr = getBindings().getOperationBinding("pendingPOCheck");
                chkUsr.getParamsMap().put("SlocId", sloc_id);
                chkUsr.getParamsMap().put("CldId", cld_id);
                chkUsr.getParamsMap().put("OrgId", pOrgId);
                chkUsr.getParamsMap().put("PoDocNo", PO_DOC_NO);
                chkUsr.execute();

                if (chkUsr.getResult() != null) {
                    pending = Integer.parseInt(chkUsr.getResult().toString());
                }
                System.out.println("PENDING_USR:" + pending + "---CURRENT_USR:" + usr_id);
                if (pending.compareTo(usr_id) != 0) {
                    Row[] usrRows = getAm().getLovUsrId().getFilteredRows("UsrId", pending);
                    StringBuffer usrName = new StringBuffer("");
                    if (usrRows.length > 0) {
                        usrName = new StringBuffer(usrRows[0].getAttribute("UsrName").toString());
                        usrName = new StringBuffer("[").append(usrName).append("]");
                    }

                    String msg2 =
                        "This Purchase Order is pending at other user " + usrName + " for approval, You cannot Forward/Approve this.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);

                } else {
                    /* PurOrderAMImpl poAM = getAm();
                ViewObject po = poAM.getMmDrftPo();
                Row currPo = po.getCurrentRow();
                String poDocId=po.getCurrentRow().getAttribute("DocId").toString(); */

                    String wf_id = "W034";
                    String action = "I";
                    String remark = "A";
                    Number amount11 = (Number)currPo.getAttribute("PoAmtBs");

                    OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                    WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                    WfnoOp.getParamsMap().put("cld_id", cld_id);
                    WfnoOp.getParamsMap().put("org_id", pOrgId);
                    WfnoOp.getParamsMap().put("doc_no", PO_DOC_NO);
                    WfnoOp.execute();

                    String WfNum = null;
                    Integer level = 0;
                    Integer res = -1;

                    if (WfnoOp.getResult() != null) {
                        WfNum = WfnoOp.getResult().toString();
                    }
                    if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {
                        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                        usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                        usrLevelOp.getParamsMap().put("CldId", cld_id);
                        usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                        usrLevelOp.getParamsMap().put("usr_id", usr_id);
                        usrLevelOp.getParamsMap().put("WfNum", WfNum);
                        usrLevelOp.getParamsMap().put("PoDocId", PO_DOC_NO);
                        usrLevelOp.execute();
                        level = -1;
                        if (usrLevelOp.getResult() != null) {
                            if (usrLevelOp.getResult() != null)
                                level = Integer.parseInt(usrLevelOp.getResult().toString());
                        }
                        if (level == -1) {
                            FacesMessage message =
                                new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else {
                            OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                            insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                            insTxnOp.getParamsMap().put("cld_id", cld_id);
                            insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                            insTxnOp.getParamsMap().put("PO_DOC_NO", PO_DOC_NO);
                            insTxnOp.getParamsMap().put("WfNum", WfNum);
                            insTxnOp.getParamsMap().put("poDocId", poDocId);
                            insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                            insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                            insTxnOp.getParamsMap().put("levelFrm", level);
                            insTxnOp.getParamsMap().put("levelTo", level);
                            insTxnOp.getParamsMap().put("action", action);
                            insTxnOp.getParamsMap().put("remark", remark);
                            insTxnOp.getParamsMap().put("amount", amount11);
                            insTxnOp.getParamsMap().put("post", "S");
                            insTxnOp.execute();

                            if (insTxnOp.getResult() != null) {
                                res = Integer.parseInt(insTxnOp.getResult().toString());
                            }
                            if (res == 1) {
                                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                                operationBinding.execute();
                            } else {
                                System.out.println("error during insert to WF");
                            }
                            System.out.println(level + "level--res" + res);
                            return "goToWf";
                        }
                    } else {
                        FacesMessage message = new FacesMessage("Workflow not Defined for PO.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
            } else if ("A".equalsIgnoreCase(mod.toString()) || "E".equalsIgnoreCase(mod.toString())) {
                setShowItemsTab("false");
                setShowDlvSchdlTab("false");
                setShowPaymentSchdlTab("false");
                setShowTnAgreeTab("false");

                int dlvRows = getAm().getDlvSchdlBalanceView().getAllRowsInRange().length;
                //  System.out.println("No. of DlvRows:"+dlvRows);

                Object obj1 = currPo.getAttribute("TransSumPoPmtAmt");
                Object obj2 = currPo.getAttribute("TransTotalPoCostSp");
                Object obj3 = currPo.getAttribute("TransTotalPoCostBs");
                Object obj4 = currPo.getAttribute("TransCurrencyDesc");

                Number zero = new Number(0);

                Number poCost = zero;
                Number poPmtAmt = zero;
                Number poCostBs = zero;
                String spCur = "INR";

                if (obj1 != null) {
                    poCost = (Number)obj2;
                }
                if (obj2 != null) {
                    poPmtAmt = (Number)obj1;
                }
                if (obj3 != null) {
                    poCostBs = (Number)obj3;
                }
                if (obj4 != null) {
                    spCur = obj4.toString();
                }

                // System.out.println(spCur+"---"+poCost+"---POCOST----POPMTAMT--"+poPmtAmt+"---dlvrws--"+dlvRows);

                if (dlvRows == 0 && poCost.compareTo(zero) == 1 && poPmtAmt.compareTo(zero) == 1 &&
                    poCost.compareTo(poPmtAmt) == 0) {
                    //  System.out.println("In sAVE MSG");
                    currPo.setAttribute("PoMode", PO_MODE_DRAFT);
                    // getAm().getDBTransaction().postChanges();
                    //   System.out.println("First Eo Id is----->" + po.getCurrentRow().getAttribute("EoId"));
                    RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "V");


                    // System.out.println("Eo Id is----->" + po.getCurrentRow().getAttribute("EoId"));
                    RichPanelTabbed richPanelTabbed = getPanelTabed();
                    DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("MmDrftPoIterator");
                    Key parentKey = parentIter.getCurrentRow().getKey();
                    //    System.out.println("Parent key is---->"+parentKey);
                    //                            parentIter.executeQuery();
                    /*   OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute1");
                    operationBinding1.execute(); */
                    OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                    operationBinding.execute();

                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    if (operationBinding.getErrors().isEmpty()) {
                        /**
                        *  Insert entry into WF items..
                      * */


                        String action = "I";
                        String remark = "A";

                        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                        WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                        WfnoOp.getParamsMap().put("cld_id", cld_id);
                        WfnoOp.getParamsMap().put("org_id", pOrgId);
                        WfnoOp.getParamsMap().put("doc_no", PO_DOC_NO);
                        WfnoOp.execute();
                        String WfNum = null;
                        Integer level = -1;
                        if (WfnoOp.getResult() != null) {
                            WfNum = WfnoOp.getResult().toString();
                        }
                        if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {
                            OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                            usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                            usrLevelOp.getParamsMap().put("CldId", cld_id);
                            usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                            usrLevelOp.getParamsMap().put("usr_id", usr_id);
                            usrLevelOp.getParamsMap().put("WfNum", WfNum);
                            usrLevelOp.getParamsMap().put("PoDocId", PO_DOC_NO);
                            usrLevelOp.execute();

                            if (usrLevelOp.getResult() != null) {
                                level = Integer.parseInt(usrLevelOp.getResult().toString());
                            }
                            if (level == -1) {
                                FacesMessage message =
                                    new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            } else {
                                // String WfNum=getWf_no(sloc_id,cld_id,OrgId,PO_DOC_NO);
                                /* Integer level =Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_USR_LEVEL(?,?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                                cld_id,
                                                                                                                                OrgId,
                                                                                                                                usr_id,
                                                                                                                                WfNum,
                                                                                                                                PO_DOC_NO }).toString());


                     */System.out.println(sloc_id + "-SAS---" + cld_id + "---" + pOrgId + "--WFNUM--" + WfNum +
                                          "------" + "-----" + usr_id + "--" + usr_id + "---" + level + "---" +
                                          action + "---" + remark + "---");

                                Integer res = -1;
                                Number amount11 = (Number)currPo.getAttribute("PoAmtBs");
                                /*    Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                new Object[] { sloc_id, cld_id, OrgId, PO_DOC_NO, WfNum, poDocId,
                                                                               usr_id, usr_id, level, level, action, remark,
                                                                               new Number(0) }).toString());
                         */

                                OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                                insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                                insTxnOp.getParamsMap().put("cld_id", cld_id);
                                insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                                insTxnOp.getParamsMap().put("PO_DOC_NO", PO_DOC_NO);
                                insTxnOp.getParamsMap().put("WfNum", WfNum);
                                insTxnOp.getParamsMap().put("poDocId", poDocId);
                                insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                                insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                                insTxnOp.getParamsMap().put("levelFrm", level);
                                insTxnOp.getParamsMap().put("levelTo", level);
                                insTxnOp.getParamsMap().put("action", action);
                                insTxnOp.getParamsMap().put("remark", remark);
                                insTxnOp.getParamsMap().put("amount", amount11);
                                insTxnOp.getParamsMap().put("post", "S");
                                insTxnOp.execute();

                                if (insTxnOp.getResult() != null) {
                                    res = Integer.parseInt(insTxnOp.getResult().toString());
                                }
                                if (res == 1) {
                                    //  OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                                    operationBinding.execute();
                                } else {
                                    System.out.println("error during insert to WF");
                                }


                                System.out.println("res in save PO else :" + res);
                                for (UIComponent child : richPanelTabbed.getChildren()) {
                                    RichShowDetailItem sdi = (RichShowDetailItem)child;
                                    //pt1:drMM1:2:sdi1
                                    if (sdi.getClientId().equals(supAndItmTab.getClientId())) {
                                        setShowItemsTab("false");
                                        setShowDlvSchdlTab("false");
                                        setShowPaymentSchdlTab("false");
                                        setShowTnAgreeTab("false");
                                        sdi.setDisclosed(true);
                                        /*  String mode="D";
                            currPo.setAttribute("PoMode", mode);
                            */
                                        // setShowPaymentSchdlTab("false");
                                    } else {
                                        sdi.setDisclosed(false);
                                    }
                                }
                                return "goToWf";
                            }
                        } else {
                            FacesMessage message = new FacesMessage("Workflow not Defined for PO.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    } else {
                        System.out.println("----error during commit-----");
                    }
                } else if (getShowDlvSchdlTab().equalsIgnoreCase("false") &&
                           getShowItemsTab().equalsIgnoreCase("false") &&
                           getShowPaymentSchdlTab().equalsIgnoreCase("false")) {
                    System.out.println("in save else--");
                    RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "V");
                    currPo.setAttribute("PoMode", PO_MODE_DRAFT);

                    //getAm().getDBTransaction().postChanges();
                    // getAm().getMmDrftPo().executeQuery();
                    // getAm().getDBTransaction().commit();
                    DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("MmDrftPoIterator");
                    Key parentKey = parentIter.getCurrentRow().getKey();
                    System.out.println("Parent key is---->" + parentKey);


                    OperationBinding operationBinding2 = getBindings().getOperationBinding("Execute");
                    operationBinding2.execute();
                    /*  OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute1");
                    operationBinding1.execute(); */

                    OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                    operationBinding.execute();
                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    if (operationBinding.getErrors().isEmpty()) {
                        /**
                           *  Insert entry into WF items..
                         * */

                        String wf_id = "W034";
                        String action = "I";
                        String remark = "A";
                        //  String WfNum=getWf_no(sloc_id,cld_id,OrgId,PO_DOC_NO);
                        String WfNum = "";
                        Integer level = 0;

                        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                        WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                        WfnoOp.getParamsMap().put("cld_id", cld_id);
                        WfnoOp.getParamsMap().put("org_id", pOrgId);
                        WfnoOp.getParamsMap().put("doc_no", PO_DOC_NO);
                        WfnoOp.execute();

                        if (WfnoOp.getResult() != null) {
                            WfNum = WfnoOp.getResult().toString();
                        }

                        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                        usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                        usrLevelOp.getParamsMap().put("CldId", cld_id);
                        usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                        usrLevelOp.getParamsMap().put("usr_id", usr_id);
                        usrLevelOp.getParamsMap().put("WfNum", WfNum);
                        usrLevelOp.getParamsMap().put("PoDocId", PO_DOC_NO);
                        usrLevelOp.execute();

                        if (usrLevelOp.getResult() != null) {
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                        }

                        System.out.println(sloc_id + "-WFFFF---" + cld_id + "---" + pOrgId + "----" + "----" + wf_id +
                                           "------" + "-----" + usr_id + "--" + usr_id + "---" + level + "---" +
                                           action + "---" + remark + "---");

                        Integer res = -1;
                        Number amount11 = (Number)currPo.getAttribute("PoAmtBs");
                        /*  Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                   new Object[] { sloc_id, cld_id, OrgId, PO_DOC_NO, WfNum, poDocId,
                                                                                  usr_id, usr_id, level, level, action, remark,
                                                                                  new Number(0) }).toString());
                         */
                        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                        insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                        insTxnOp.getParamsMap().put("cld_id", cld_id);
                        insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                        insTxnOp.getParamsMap().put("PO_DOC_NO", PO_DOC_NO);
                        insTxnOp.getParamsMap().put("WfNum", WfNum);
                        insTxnOp.getParamsMap().put("poDocId", poDocId);
                        insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                        insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                        insTxnOp.getParamsMap().put("levelFrm", level);
                        insTxnOp.getParamsMap().put("levelTo", level);
                        insTxnOp.getParamsMap().put("action", action);
                        insTxnOp.getParamsMap().put("remark", remark);
                        insTxnOp.getParamsMap().put("amount", amount11);
                        insTxnOp.getParamsMap().put("post", "S");
                        insTxnOp.execute();

                        if (insTxnOp.getResult() != null) {
                            res = Integer.parseInt(insTxnOp.getResult().toString());
                        }


                        if (res == 1) {
                            //  OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                            operationBinding.execute();
                        } else {
                            System.out.println("error during insert to WF");
                        }

                        RichPanelTabbed richPanelTabbed = getPanelTabed();

                        for (UIComponent child : richPanelTabbed.getChildren()) {
                            RichShowDetailItem sdi = (RichShowDetailItem)child;
                            //pt1:drMM1:2:sdi1
                            if (sdi.getClientId().equals(supAndItmTab.getClientId())) {
                                setShowItemsTab("false");
                                setShowDlvSchdlTab("false");
                                setShowPaymentSchdlTab("false");
                                setShowTnAgreeTab("false");
                                sdi.setDisclosed(true);
                                // setShowPaymentSchdlTab("false");
                            } else {
                                sdi.setDisclosed(false);
                            }
                        }

                        return "goToWf";
                    } else {
                        System.out.println("----error during commit-----");
                    }
                } else if (dlvRows != 0) {
                    currPo.setAttribute("PoMode", PO_MODE_INCOMPLETE);
                    // getAm().getDBTransaction().postChanges();
                    // getAm().getMmDrftPo().executeQuery();
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.404']}"));
                    message.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else if (poCost.compareTo(poPmtAmt) == 1 || poCost.compareTo(poPmtAmt) == -1) {
                    currPo.setAttribute("PoMode", PO_MODE_INCOMPLETE);
                    // getAm().getDBTransaction().postChanges();
                    // getAm().getMmDrftPo().executeQuery();
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.406']}"));
                    message.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }

            }

        }
        return null;
    }

    /*   public void setItmPoBind(RichSelectOneChoice itmPoBind) {
        this.itmPoBind = itmPoBind;
    }

    public RichSelectOneChoice getItmPoBind() {
        return itmPoBind;
    } */

    public void setAmendPopUp(RichPopup amendPopUp) {
        this.amendPopUp = amendPopUp;
    }

    public RichPopup getAmendPopUp() {
        return amendPopUp;
    }

    public void amendDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {


        }
    }

    public void amendPoAction(ActionEvent actionEvent) {

        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();


        Row currDoc = getAm().getMmDrftPo().getCurrentRow();
        String refDocId = currDoc.getAttribute("DocId").toString();
        //Check if PO is being used in GE or Receipt
        String retval =
            (String)callStoredFunction(STRING, "MM.MM_IS_PO_AMMENDABLE(?,?,?,?)", new Object[] { p_cldId, p_sloc_id,
                                                                                                 p_org_id, refDocId });
        if (retval != null && retval.toString().equals("Y")) {
            Integer refAmdNo = 0;
            if (currDoc.getAttribute("AmdNo") != null) {
                refAmdNo = Integer.parseInt(currDoc.getAttribute("AmdNo").toString());
            }

            Date refAmdDt = (Date)Date.getCurrentDate();

            OperationBinding usrLevelOp = getBindings().getOperationBinding("Createwithparameters5");
            usrLevelOp.getParamsMap().put("SlocId", p_sloc_id);
            usrLevelOp.getParamsMap().put("CldId", p_cldId);
            usrLevelOp.getParamsMap().put("OrgId", p_org_id);
            usrLevelOp.getParamsMap().put("UsrIdCreate", p_user_id);
            usrLevelOp.getParamsMap().put("UsrIdCreateDt", currDoc.getAttribute("UsrIdCreateDt"));
            usrLevelOp.getParamsMap().put("EoId", currDoc.getAttribute("EoId"));
            usrLevelOp.getParamsMap().put("AuthPoNo", currDoc.getAttribute("AuthPoNo"));
            usrLevelOp.getParamsMap().put("AmdNo", refAmdNo + 1);
            usrLevelOp.getParamsMap().put("AmdDt", Date.getCurrentDate());
            usrLevelOp.getParamsMap().put("DocDt", currDoc.getAttribute("DocDt"));
            usrLevelOp.getParamsMap().put("PoDt", currDoc.getAttribute("PoDt"));
            usrLevelOp.getParamsMap().put("PoType", currDoc.getAttribute("PoType"));
            usrLevelOp.getParamsMap().put("PoBasis", currDoc.getAttribute("PoBasis"));
            usrLevelOp.getParamsMap().put("BillAddsId", currDoc.getAttribute("BillAddsId"));
            usrLevelOp.getParamsMap().put("CurrIdSp", currDoc.getAttribute("CurrIdSp"));
            usrLevelOp.getParamsMap().put("CurrConvFctr", currDoc.getAttribute("CurrConvFctr"));
            usrLevelOp.getParamsMap().put("TaxRuleFlg", currDoc.getAttribute("TaxRuleFlg"));
            usrLevelOp.getParamsMap().put("DiscType", currDoc.getAttribute("DiscType"));
            usrLevelOp.getParamsMap().put("DiscVal", currDoc.getAttribute("DiscVal"));
            usrLevelOp.getParamsMap().put("PoAmtBs", currDoc.getAttribute("PoAmtBs"));
            usrLevelOp.getParamsMap().put("PoAmtSp", currDoc.getAttribute("PoAmtSp"));
            usrLevelOp.getParamsMap().put("PoStatus", currDoc.getAttribute("PoStatus"));
            usrLevelOp.getParamsMap().put("TaxAfterDiscFlg", currDoc.getAttribute("TaxAfterDiscFlg"));
            if (currDoc.getAttribute("OrigDocId") == null) {
                usrLevelOp.getParamsMap().put("OrigDocId", refDocId);
            } else {
                usrLevelOp.getParamsMap().put("OrigDocId", currDoc.getAttribute("OrigDocId"));
            }
            usrLevelOp.getParamsMap().put("ValidFrmDt", currDoc.getAttribute("ValidFrmDt"));
            usrLevelOp.getParamsMap().put("ValidToDt", currDoc.getAttribute("ValidToDt"));
            usrLevelOp.getParamsMap().put("TlrncDays", currDoc.getAttribute("TlrncDays"));
            usrLevelOp.getParamsMap().put("SplitPo", currDoc.getAttribute("SplitPo"));
            usrLevelOp.getParamsMap().put("SplitPoDocId", currDoc.getAttribute("SplitPoDocId"));
            usrLevelOp.getParamsMap().put("TlrncQtyType", currDoc.getAttribute("TlrncQtyType"));
            usrLevelOp.getParamsMap().put("TlrncQtyVal", currDoc.getAttribute("TlrncQtyVal"));
            usrLevelOp.getParamsMap().put("QuotDocId", currDoc.getAttribute("QuotDocId"));
            usrLevelOp.getParamsMap().put("RefPoDocId", currDoc.getAttribute("RefPoDocId"));
            usrLevelOp.getParamsMap().put("TmplDocId", currDoc.getAttribute("TmplDocId"));
            usrLevelOp.getParamsMap().put("SoGrpId", currDoc.getAttribute("SoGrpId"));
            usrLevelOp.getParamsMap().put("PlcId", currDoc.getAttribute("PlcId"));
            usrLevelOp.getParamsMap().put("Remarks", currDoc.getAttribute("Remarks"));
            usrLevelOp.execute();

            Row newCurrRow = getAm().getMmDrftPo().getCurrentRow(); // check if new Doc Id generated and fetch
            String newDocId = newCurrRow.getAttribute("DocId").toString();
            System.out.println(refDocId + "--OldDocId---Supp=" + currDoc.getAttribute("EoId").toString() +
                               "--DOCIDNEW--" + newDocId + "---Org=" + p_org_id);
            //237 -Amended --52
            DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("MmDrftPoIterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            System.out.println("Parent key is---->" + parentKey);
            OperationBinding callAmdPo = getBindings().getOperationBinding("amendPo");
            callAmdPo.getParamsMap().put("P_CLDID", p_cldId);
            callAmdPo.getParamsMap().put("p_orgid", p_org_id);
            callAmdPo.getParamsMap().put("p_slocid", p_sloc_id);
            callAmdPo.getParamsMap().put("p_doc_id", newDocId);
            callAmdPo.getParamsMap().put("p_ref_doc_id", refDocId);
            callAmdPo.getParamsMap().put("p_usr_id", p_user_id);
            callAmdPo.execute();

            if (callAmdPo.getResult() != null) {
                String returnPar = callAmdPo.getResult().toString();
                System.out.println("CallAmdPo return val---" + returnPar);
            }

            getAm().getMmDrftPoItm().executeQuery();
            getAm().getMmDrftPoDlvSchdl().executeQuery();
            getAm().getMmDrftPoOc().executeQuery();
            getAm().getMmDrftPoPmtSchdl().executeQuery();
            getAm().getMmDrftPoTnc().executeQuery();
            getAm().getMmDrftPoTr().executeQuery();
            getAm().getMmDrftPoTrLines().executeQuery();

            RequestContext.getCurrentInstance().getPageFlowScope().put("AddEditMode", "A");
            System.out.println("Mode has been changed to Add");
            refreshPage();
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoNameTransBind);
        } else if (retval != null && retval.toString().equals("N")) {
            FacesMessage message = new FacesMessage("This PO has been Transferred to Store,Can not Ammend.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else {
            FacesMessage message = new FacesMessage("Something went wrong.Contact to ESS.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
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

    public void TolarenceTypeChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            PurOrderAMImpl am = getAm();
            ViewObject poItm = am.getMmDrftPoItm();
            ViewObject po = am.getMmDrftPo();
            RowSetIterator rsi = poItm.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row nxt = rsi.next();
                nxt.setAttribute("TlrncQtyType", valueChangeEvent.getNewValue());
                if (po.getCurrentRow().getAttribute("TlrncQtyVal") != null)
                    nxt.setAttribute("TlrncQtyVal", po.getCurrentRow().getAttribute("TlrncQtyVal"));
                else
                    nxt.setAttribute("TlrncQtyVal", 0);
            }
        }
    }


    public void tolarenceQtyChangeListener(ValueChangeEvent valueChangeEvent) {

        PurOrderAMImpl am = getAm();
        ViewObject poItm = am.getMmDrftPoItm();
        ViewObject po = am.getMmDrftPo();
        RowSetIterator rsi = poItm.createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row nxt = rsi.next();
            nxt.setAttribute("TlrncQtyType", po.getCurrentRow().getAttribute("TlrncQtyType"));
            if (valueChangeEvent.getNewValue() != null)
                nxt.setAttribute("TlrncQtyVal", valueChangeEvent.getNewValue());
            else
                nxt.setAttribute("TlrncQtyVal", 0);
        }

    }


    public void resetTaxDialogListener(DialogEvent dialogEvent) throws SQLException {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            PurOrderAMImpl am = getAm();
            ViewObjectImpl poItm = am.getMmDrftPoItm();
            ViewObjectImpl poTaxVo = am.getMmDrftPoTr();
            ViewObjectImpl poTaxLineVo = am.getMmDrftPoTrLinesVO1();
            poTaxLineVo.executeQuery();
            RowQualifier rqTax = new RowQualifier(poTaxVo);
            Row[] rsi1 = null;
            if (resetflg.equals("P")) {
                System.out.println("Reset PO Wise Tax");
                // rsi1 = poItm.getFilteredRows("TransTaxRuleFlg","P");
                rsi1 = poItm.getAllRowsInRange();
            } else if (resetflg.equals("I")) {
                System.out.println("Reset Item Wise Tax");
                // rsi1=  poItm.getFilteredRows("TransTaxRuleFlg","I");
                if (poItm.getCurrentRow() != null) {
                    System.out.println("Current row is not null");
                    String itm = (String)poItm.getCurrentRow().getAttribute("ItmId");
                    String uom = (String)poItm.getCurrentRow().getAttribute("ItmUom");
                    RowQualifier rq = new RowQualifier(poItm);
                    rq.setWhereClause("ItmId='" + itm + "' and ItmUom='" + uom + "'");
                    rsi1 = poItm.getFilteredRows(rq);
                } else
                    System.out.println("Current row is  null");
            }
            Integer leng = -1;

            if (rsi1 != null && rsi1.length > 0) {
                leng = rsi1.length - 1;
                System.out.println("Length=" + rsi1.length);
            }
            while (leng >= 0) {
                System.out.println("for leng=" + leng);
                Row nxt = rsi1[leng];
                if (nxt.getAttribute("ItmId") != null && nxt.getAttribute("ItmUom") != null) {

                    RowQualifier rq = new RowQualifier(poItm);
                    rq.setWhereClause("ItmId='" + nxt.getAttribute("ItmId") + "' and ItmUom='" +
                                      nxt.getAttribute("ItmUom") + "'");
                    Row[] itmrow = poItm.getFilteredRows(rq);
                    if (itmrow.length > 0) {
                        rqTax.setWhereClause("DocId= '" + nxt.getAttribute("DocId").toString() + "' and ItmId= '" +
                                             nxt.getAttribute("ItmId").toString() + "' and ItmUom= '" +
                                             nxt.getAttribute("ItmUom").toString() + "'");
                        Row[] rTax = poTaxVo.getFilteredRows(rqTax);

                        if (rTax.length > 0) {

                            RowQualifier rqTaxLine = new RowQualifier(poTaxLineVo);
                            rqTaxLine.setWhereClause("DocId='" + nxt.getAttribute("DocId").toString() +
                                                     "' and ItmId='" + nxt.getAttribute("ItmId").toString() +
                                                     "' and TaxRuleId=" + rTax[0].getAttribute("TaxRuleId") +
                                                     " and ItmUom='" + nxt.getAttribute("ItmUom").toString() + "'");

                            Row[] rTaxLine = poTaxLineVo.getFilteredRows(rqTaxLine);

                            if (rTaxLine.length > 0) {
                                for (Row rTl : rTaxLine) {

                                    rTl.remove();
                                }
                            }

                            rTax[0].remove();
                        }
                    }
                }
                leng--;
            }
            poItm.executeQuery();
            poTaxVo.executeQuery();
            poTaxLineVo.executeQuery();
            am.getMmDrftPoTrLines().executeQuery();
            OperationBinding operationBinding = getBindings().getOperationBinding("Execute");
            operationBinding.execute();

            // getAm().getDBTransaction().postChanges();
            /*  PurOrderAMImpl am = getAm();
        ViewObject po=am.getMmDrftPo();
        ViewObject poitm=am.getMmDrftPoItm();
       String docId = (String)po.getCurrentRow().getAttribute("DocId");
       String flg = (String)po.getCurrentRow().getAttribute("TaxRuleFlg");
        ViewObjectImpl potr=am.getMmDrftPoTr();

         if(resetflg.equals("I"))
        {
         String docid=poitm.getCurrentRow().getAttribute("DocId").toString();
         String itmid=poitm.getCurrentRow().getAttribute("ItmId").toString();
         String uom=poitm.getCurrentRow().getAttribute("ItmUom").toString();

            }

            ViewObjectImpl potrline=am.getMmDrftPoTrLines();
            Row[] liner= potrline.getFilteredRows("DocId",docId);
            Integer linel=-1;
            if(liner.length>0)
                linel=liner.length-1;
            while(linel>=0)
            {
                liner[linel].remove();
                linel--;
                }
     RowQualifier rq=new RowQualifier(potr);
     if(flg.equals("P"))
     rq.setWhereClause("DocId='"+docId+"' and ItmId='ALL'");
     else
     {
         String itm = (String)poitm.getCurrentRow().getAttribute("ItmId");
             rq.setWhereClause("DocId='"+docId+"' and ItmId='"+itm+"'");
         }
     Row[] trr=potr.getFilteredRows(rq);
     linel=-1;
     if(trr.length>0)
         linel=trr.length-1;
         while(linel>=0)
         {
             trr[linel].remove();
             linel--;
             }*/
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(poTaxAmtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBsBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxOnItmBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(priceInBhdBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(priceInInrBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(resetTaxItemBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(resetTaxPOBind);
    }

    public void setTaxOnItmBind(RichInputText taxOnItmBind) {
        this.taxOnItmBind = taxOnItmBind;
    }

    public RichInputText getTaxOnItmBind() {
        return taxOnItmBind;
    }

    public void setPriceInBhdBind(RichInputText priceInBhdBind) {
        this.priceInBhdBind = priceInBhdBind;
    }

    public RichInputText getPriceInBhdBind() {
        return priceInBhdBind;
    }

    public void setPriceInInrBind(RichInputText priceInInrBind) {
        this.priceInInrBind = priceInInrBind;
    }

    public RichInputText getPriceInInrBind() {
        return priceInInrBind;
    }

    public void setResetTaxPOBind(RichCommandImageLink resetTaxPOBind) {
        this.resetTaxPOBind = resetTaxPOBind;
    }

    public RichCommandImageLink getResetTaxPOBind() {
        return resetTaxPOBind;
    }

    public void setResetTaxItemBind(RichCommandImageLink resetTaxItemBind) {
        this.resetTaxItemBind = resetTaxItemBind;
    }

    public RichCommandImageLink getResetTaxItemBind() {
        return resetTaxItemBind;
    }

    public void itmUomValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (getAm().getMmDrftPo().getCurrentRow().getAttribute("PoBasis").equals(490)) {
                String uomInRef = null;
                Row r[] =
                    getAm().getMmDrftPo().getFilteredRows("DocId", getAm().getMmDrftPo().getCurrentRow().getAttribute("RefPoDocId"));
                if (r.length > 0)
                    uomInRef = (String)r[0].getAttribute("OpenOrdUom");

                if (uomInRef != null && !object.equals(uomInRef)) {
                    String uomRefDesc = null;
                    Row fr[] = this.getAm().getLovItmUom().getFilteredRows("UomId", uomInRef);
                    System.out.println("No. of filtered rows from unit=" + fr.length);
                    if (fr.length > 0)
                        uomRefDesc = (String)fr[0].getAttribute("UomDesc");

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Unit must be same to Unit define in Open Order [" +
                                                                  uomRefDesc + "].", null));

                }
            }
            ViewObject v = getAm().getMmDrftPoItm();
            String itmDesc = null;
            int totalCount = v.getRowCount(); //get ALL rows
            int rangeSize = v.getRangeSize(); //all in range
            v.setRangeSize(totalCount);
            Row[] rArray = v.getAllRowsInRange();
            String itmUom = object.toString();
            String itmName = (String)v.getCurrentRow().getAttribute("ItmId");
            //  System.out.println("Itm="+itmName);
            //  System.out.println("uom="+itmUom);
            //check for duplicate rows
            Row cRow = v.getCurrentRow();
            int count = 0;
            String currName = "";
            String currUnit = "";
            for (Row r : rArray) {

                if (!r.equals(cRow)) {
                    try {
                        currName = r.getAttribute("ItmId").toString();
                        currUnit = r.getAttribute("ItmUom").toString();
                        //   System.out.println("CurrName="+currName);
                        //   System.out.println("CurrUnit="+currUnit);
                    } catch (NullPointerException npe) {
                        // System.out.println("NPE:" + npe);
                        currName = "";
                        currUnit = "";
                    }
                    if (currName != null && currUnit != null) {
                        /*  Row[] xx = getAm().getLovItmId().getFilteredRows("ItmId", currName);
                        if (xx.length > 0) {
                            itmDesc = xx[0].getAttribute("ItmDesc").toString();
                        }*/

                        if (itmName.equalsIgnoreCase(currName) && itmUom.equalsIgnoreCase(currUnit)) {
                            count = count + 1;
                        }
                    }

                }

            }
            v.setRangeSize(rangeSize); //set to original range

            if (count > 0) {
                /*    String msg2 = resolvEl("Unit with selected item is already exist.");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);*/
            }
        } else {
            /*  FacesMessage message = new FacesMessage("Item Name is required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(itmIdBind.getClientId(), message); */
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select Unit for Item", null));
        }
    }


    public void poTaxExemptedVCE(ValueChangeEvent valueChangeEvent) {

        Row trcurr = getAm().getMmDrftPoTr().getCurrentRow();
        ViewObject trline = getAm().getMmDrftPoTrLines();
        Number tax = new Number(0);

        if (trcurr.getAttribute("TaxAmt") != null) {
            if (valueChangeEvent.getNewValue().equals(true)) {
                trcurr.setAttribute("TaxAmt", 0);
                getAm().getMmDrftPo().getCurrentRow().setAttribute("TransTotalTax", 0);
            } else {
                RowSetIterator rsi = trline.createRowSetIterator(null);
                while (rsi.hasNext()) {
                    Row r = rsi.next();
                    if (r.getAttribute("TaxAmtSp") != null)
                        tax = tax.add((Number)(r.getAttribute("TaxAmtSp")));
                }
                trcurr.setAttribute("TaxAmt", tax);
                getAm().getMmDrftPo().getCurrentRow().setAttribute("TransTotalTax", tax);

            }
        } else {
            //taxamt is null mean tax is not applied hence this checkbox must be disable
        }
    }

    public void poWiseSelectTaxVCE(ValueChangeEvent valueChangeEvent) {
        //   System.out.println("Tax Id="+ valueChangeEvent.getNewValue());
    }

    public void setPowiseSelectTaxPop(RichPopup powiseSelectTaxPop) {
        this.powiseSelectTaxPop = powiseSelectTaxPop;
    }

    public RichPopup getPowiseSelectTaxPop() {
        return powiseSelectTaxPop;
    }

    public void applyTaxPoWiseDL(DialogEvent dialogEvent) {
        /*   ViewObjectImpl dpTr = getAm().getMmDrftPoTr();
        ViewObjectImpl dpTrLn = getAm().getMmDrftPoTrLines();
        if (dialogEvent.getOutcome().name().equals("ok")) {
            String taxid=null;
          if(selectTaxPoBind.getValue()!=null)
          taxid= selectTaxPoBind.getValue().toString();
          else
          {
                  FacesMessage message = new FacesMessage("Select Tax.");
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                  FacesContext fc = FacesContext.getCurrentInstance();
                  fc.addMessage(selectTaxPoBind.getClientId(), message);
              }
        String flg=applyTaxRadioBind.getValue().toString();

        }*/
    }

    public void setApplyTaxRadioBind(RichSelectOneRadio applyTaxRadioBind) {
        this.applyTaxRadioBind = applyTaxRadioBind;
    }

    public RichSelectOneRadio getApplyTaxRadioBind() {
        return applyTaxRadioBind;
    }

    public void setSelectTaxPoBind(RichSelectOneChoice selectTaxPoBind) {
        this.selectTaxPoBind = selectTaxPoBind;
    }

    public RichSelectOneChoice getSelectTaxPoBind() {
        return selectTaxPoBind;
    }

    public void applyTaxButtonAL(ActionEvent actionEvent) throws SQLException {

        // System.out.println("new Taxrule="+selectTaxPoBind.getValue());
        ViewObjectImpl povo = getAm().getMmDrftPo();
        //   System.out.println("Tax rule according to po="+povo.getCurrentRow().getAttribute("transTaxRuleId"));
        ViewObjectImpl itmvo = getAm().getMmDrftPoItm();
        ViewObjectImpl trVo = getAm().getMmDrftPoTr();
        ViewObjectImpl trLine = getAm().getMmDrftPoTrLines();
        ViewObjectImpl trVonew = getAm().getMmDrftPoTr1();
        ViewObjectImpl trLinenew = getAm().getMmDrftPoTrLines1();
        ViewObjectImpl trLinevo1 = getAm().getMmDrftPoTrLinesVO1();
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
        String taxid = null;
        if (selectTaxPoBind.getValue() != null) {
            taxid = selectTaxPoBind.getValue().toString();
            String radioflg = povo.getCurrentRow().getAttribute("TransRadio").toString();
            String docId = povo.getCurrentRow().getAttribute("DocId").toString();
            //   System.out.println("docid="+docId);
            if (radioflg.equals("All")) {
                //    System.out.println("Condition for radio=all is true");
                String removedItmid = null;
                String removedItmUom = null;
                String removedExmptflg = null;
                //add rows in tr according to itemvo
                RowSetIterator rsitm = itmvo.createRowSetIterator(null);
                while (rsitm.hasNext()) {
                    Row rw = rsitm.next();
                    if (rw.getAttribute("DocId").toString().equalsIgnoreCase(docId)) {
                        //     System.out.println("Row matched with docid="+rw.getAttribute("DocId"));
                        String itmdocId = rw.getAttribute("DocId").toString();
                        String itmId = rw.getAttribute("ItmId").toString();
                        String itmUom = rw.getAttribute("ItmUom").toString();
                        removedItmid = null;
                        removedItmUom = null;
                        removedExmptflg = null;

                        //remove this row from tr
                        RowSetIterator rsitr = trVo.createRowSetIterator(null);
                        while (rsitr.hasNext()) {
                            Row trrw = rsitr.next();
                            if (trrw.getAttribute("DocId").toString().equalsIgnoreCase(docId)) {
                                //     System.out.println("in tr row matched to doc");
                                if (trrw.getAttribute("ItmId").toString().equalsIgnoreCase(itmId) &&
                                    trrw.getAttribute("ItmUom").toString().equalsIgnoreCase(itmUom)) {
                                    removedItmid = trrw.getAttribute("ItmId").toString();
                                    removedItmUom = trrw.getAttribute("ItmUom").toString();
                                    removedExmptflg = trrw.getAttribute("TaxExmptFlg").toString();
                                    RowSetIterator itrline = trLinevo1.createRowSetIterator(null);
                                    while (itrline.hasNext()) {
                                        Row trlinerow = itrline.next();
                                        if (trlinerow.getAttribute("DocId").toString().equals(docId) &&
                                            trlinerow.getAttribute("ItmId").toString().equals(itmId) &&
                                            trlinerow.getAttribute("ItmUom").toString().equals(itmUom))
                                        /* && trlinerow.getAttribute("TaxRuleId").toString().equals(trrw.getAttribute("TaxRuleId") )*/ {
                                            // trlinerow.remove();
                                        }
                                    }

                                    itrline.closeRowSetIterator();
                                    trLinevo1.executeQuery();
                                    trLine.executeQuery();
                                    trLinenew.executeQuery();

                                    trrw.remove();
                                }

                            }
                        }
                        rsitr.closeRowSetIterator();
                        trVo.executeQuery();
                        trVonew.executeQuery();

                        //check if tax should be applied on this item or not.
                        String ex = "Y";
                        if (removedExmptflg != null)
                            ex = removedExmptflg;
                        else if (rw.getAttribute("TransTaxExmptFlg") != null)
                            ex = (String)rw.getAttribute("TransTaxExmptFlg");
                        if (ex.equals("Y")) {
                        } else {

                            Row newrow = trVo.createRow();
                            //       System.out.println("new row created in tr");
                            String taxruleflg = "P";
                            Number p_curr_fctr = new Number(1);
                            if (povo.getCurrentRow().getAttribute("CurrConvFctr") != null) {
                                p_curr_fctr = (Number)povo.getCurrentRow().getAttribute("CurrConvFctr");
                            }
                            newrow.setAttribute("ItmId", itmId);
                            newrow.setAttribute("ItmUom", itmUom);
                            newrow.setAttribute("TaxRuleId", selectTaxPoBind.getValue());
                            newrow.setAttribute("TaxRuleFlg", taxruleflg);

                            if (removedExmptflg != null)
                                newrow.setAttribute("TaxExmptFlg", removedExmptflg);
                            else
                                newrow.setAttribute("TaxExmptFlg", rw.getAttribute("TransTaxExmptFlg"));

                            newrow.setAttribute("UsrIdCreate", p_user_id);
                            //TaxableAmt
                            String afterdisc = povo.getCurrentRow().getAttribute("TaxAfterDiscFlg").toString();
                            if (afterdisc.equals("N"))
                                newrow.setAttribute("TaxableAmt", rw.getAttribute("TransItemAmtWoutDc"));
                            else
                                newrow.setAttribute("TaxableAmt", rw.getAttribute("TransItmAmtSp"));

                            Number p_taxable_amount = (Number)newrow.getAttribute("TaxableAmt");
                            System.out.println("Tax rule flg=" + taxruleflg);
                            //create trlines
                            BigDecimal ret =
                                (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                               new Object[] { p_sloc_id, p_cldId, p_hoOrgId, p_org_id,
                                                                              docId, itmId, itmUom, taxid, p_user_id,
                                                                              p_taxable_amount, taxruleflg,
                                                                              p_curr_fctr,
                                                                              newrow.getAttribute("TaxExmptFlg") });


                            Number retV = zero;
                            if (ret != null) {
                                try {
                                    retV = new Number(ret);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            adfLog.info(ret + "  tax return value for all item  " + retV);
                            newrow.setAttribute("TaxAmt", retV);
                            getAm().getMmDrftPo().getCurrentRow().setAttribute("TransTotalTax", retV);
                            trVo.insertRow(newrow);
                            //   System.out.println("Row inserted");
                        }
                    }
                }
                rsitm.closeRowSetIterator();
                trVo.executeQuery();
                trLine.executeQuery();
                trVonew.executeQuery();
                trLinenew.executeQuery();
                trLinevo1.executeQuery();
                //   System.out.println("mission Successfull");
            } else {
                //Tax for Exclude condition
                String removedItmid = null;
                String removedItmUom = null;
                String removedExmptflg = null;
                //add rows in tr according to itemvo
                RowSetIterator rsitm = itmvo.createRowSetIterator(null);
                while (rsitm.hasNext()) {
                    Row rw = rsitm.next();
                    if (rw.getAttribute("DocId").toString().equalsIgnoreCase(docId)) {
                        String itmdocId = rw.getAttribute("DocId").toString();
                        String itmId = rw.getAttribute("ItmId").toString();
                        String itmUom = rw.getAttribute("ItmUom").toString();
                        removedItmid = null;
                        removedItmUom = null;
                        removedExmptflg = null;

                        //remove this row from tr
                        RowSetIterator rsitr = trVo.createRowSetIterator(null);
                        while (rsitr.hasNext()) {
                            Row trrw = rsitr.next();
                            if (trrw.getAttribute("DocId").toString().equalsIgnoreCase(docId)) {
                                if (trrw.getAttribute("ItmId").toString().equalsIgnoreCase(itmId) &&
                                    trrw.getAttribute("ItmUom").toString().equalsIgnoreCase(itmUom) &&
                                    trrw.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("P")) {
                                    removedItmid = trrw.getAttribute("ItmId").toString();
                                    removedItmUom = trrw.getAttribute("ItmUom").toString();
                                    removedExmptflg = trrw.getAttribute("TaxExmptFlg").toString();

                                    /*  RowSetIterator itrline=trLinevo1.createRowSetIterator(null);
                                                          while(itrline.hasNext())
                                                          {
                                                              Row trlinerow=itrline.next();
                                                           if(trlinerow.getAttribute("DocId").toString().equals(docId)
                                                               && trlinerow.getAttribute("ItmId").toString().equals(itmId)
                                                               && trlinerow.getAttribute("ItmUom").toString().equals(itmUom)
                                                               && trlinerow.getAttribute("TaxRuleId").toString().equals(trrw.getAttribute("TaxRuleId")))
                                                           {
                                                                  // trlinerow.remove();
                                                               }
                                                              }

                                                                itrline.closeRowSetIterator(); */
                                    trLinevo1.executeQuery();
                                    trLine.executeQuery();
                                    trLinenew.executeQuery();

                                    trrw.remove();
                                }

                            }
                        }
                        rsitr.closeRowSetIterator();
                        trVo.executeQuery();
                        trVonew.executeQuery();

                        RowQualifier rqtr = new RowQualifier(trVo);
                        rqtr.setWhereClause("DocId='" + itmdocId + "' and ItmId='" + itmId + "' and ItmUom='" +
                                            itmUom + "'");
                        Row[] fritm = trVo.getFilteredRows(rqtr);
                        if (fritm.length == 0) {
                            //check if tax should be applied on this item or not.
                            String ex = "Y";
                            if (removedExmptflg != null)
                                ex = removedExmptflg;
                            else if (rw.getAttribute("TransTaxExmptFlg") != null)
                                ex = (String)rw.getAttribute("TransTaxExmptFlg");
                            if (ex.equals("Y")) {
                            } else {
                                Row newrow = trVo.createRow();

                                String taxruleflg = povo.getCurrentRow().getAttribute("TaxRuleFlg").toString();
                                Number p_curr_fctr = new Number(1);
                                if (povo.getCurrentRow().getAttribute("CurrConvFctr") != null) {
                                    p_curr_fctr = (Number)povo.getCurrentRow().getAttribute("CurrConvFctr");
                                }
                                newrow.setAttribute("ItmId", itmId);
                                newrow.setAttribute("ItmUom", itmUom);
                                newrow.setAttribute("TaxRuleId", selectTaxPoBind.getValue());
                                newrow.setAttribute("TaxRuleFlg", taxruleflg);

                                if (removedExmptflg != null)
                                    newrow.setAttribute("TaxExmptFlg", removedExmptflg);
                                else
                                    newrow.setAttribute("TaxExmptFlg", rw.getAttribute("TransTaxExmptFlg"));
                                String taxexm = (String)newrow.getAttribute("TaxExmptFlg");
                                newrow.setAttribute("UsrIdCreate", p_user_id);
                                //TaxableAmt
                                String afterdisc = povo.getCurrentRow().getAttribute("TaxAfterDiscFlg").toString();
                                if (afterdisc.equals("N"))
                                    newrow.setAttribute("TaxableAmt", rw.getAttribute("TransItemAmtWoutDc"));
                                else
                                    newrow.setAttribute("TaxableAmt", rw.getAttribute("TransItmAmtSp"));

                                Number p_taxable_amount = (Number)newrow.getAttribute("TaxableAmt");
                                System.out.println("Tax rule flg=" + taxruleflg);
                                //create trlines
                                BigDecimal ret =
                                    (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                   new Object[] { p_sloc_id, p_cldId, p_hoOrgId,
                                                                                  p_org_id, docId, itmId, itmUom,
                                                                                  taxid, p_user_id, p_taxable_amount,
                                                                                  taxruleflg, p_curr_fctr, taxexm });


                                Number retVN = zero;
                                if (ret != null) {
                                    try {
                                        retVN = new Number(ret);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                newrow.setAttribute("TaxAmt", retVN);
                                trVo.insertRow(newrow);
                            }
                        }
                    }
                }
                rsitm.closeRowSetIterator();
                trVo.executeQuery();
                trLine.executeQuery();
                trVonew.executeQuery();
                trLinenew.executeQuery();
                trLinevo1.executeQuery();
            }
            powiseSelectTaxPop.hide();
        } else {
            FacesMessage message = new FacesMessage("Select TaxRule !!!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(selectTaxPoBind.getClientId(), message);
        }


    }

    public void powiseCancelButton(ActionEvent actionEvent) {
        powiseSelectTaxPop.hide();
    }

    public void taxResetonItem(ActionEvent actionEvent) {
        System.out.println("Setting reset flg to I");
        resetflg = "I";
        showPopup(resettaxPop, true);
    }

    public void resetTaxonPo(ActionEvent actionEvent) {
        System.out.println("Setting reset flg to P");
        resetflg = "P";
        showPopup(resettaxPop, true);
    }

    public void itmDlvCheckBoxVCL(ValueChangeEvent valueChangeEvent) {
        /*  System.out.println("New value of item cb="+valueChangeEvent.getNewValue());
       Row porow=getAm().getMmDrftPo().getCurrentRow();
        if(valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true"))
        {
            Boolean flg=true;
                Row curr=getAm().getDlvSchdlBalanceView().getCurrentRow();
            //code to check for all items
            RowSetIterator rsi=getAm().getDlvSchdlBalanceView().createRowSetIterator(null);
            while(rsi.hasNext())
            {
                Row r=rsi.next();
                if(curr.equals(r))
                {
                System.out.println("this row is Current row="+r.getAttribute("ItemId"));
                }
                else
                {
                    System.out.println("For item id "+r.getAttribute("ItemId")+" cb is="+r.getAttribute("TransSelectChkbx"));
                    if(r.getAttribute("TransSelectChkbx").toString().equals("N"))
                    {
                        flg=false;
                    break;
                    }
                    }
                }
            rsi.closeRowSetIterator();
            System.out.println("Flage is="+flg);
            if(flg.toString().equalsIgnoreCase("true"))
            {
                porow.setAttribute("TransSelectAllCheckBox","Y");
                selectAllCB.setValue(true);
            }
            else{
                porow.setAttribute("TransSelectAllCheckBox","N");
                selectAllCB.setValue(false);
            }
            }
        else{
        porow.setAttribute("TransSelectAllCheckBox","N");
            selectAllCB.setValue(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(selectAllCB); */
    }

    public void selectAllDlvCheckBoxVCL(ValueChangeEvent valueChangeEvent) {
        /*   System.out.println("New value="+valueChangeEvent.getNewValue());
       if(valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true"))
       {
       //code to select all items
       RowSetIterator rsi=getAm().getDlvSchdlBalanceView().createRowSetIterator(null);
       while(rsi.hasNext())
       {
            Row r=rsi.next();
           System.out.println("Setting Y to item="+r.getAttribute("ItemId"));
            r.setAttribute("TransSelectChkbx","Y");
          //  selectitmCB.setValue(true);
           }
       rsi.closeRowSetIterator();
           }
       else
       {
           //code to deselect all items
           RowSetIterator rsi=getAm().getDlvSchdlBalanceView().createRowSetIterator(null);
           while(rsi.hasNext())
           {
               Row r=rsi.next();
               System.out.println("Setting N to item="+r.getAttribute("ItemId"));
               r.setAttribute("TransSelectChkbx","N");
               //selectitmCB.setValue(false);
               }
           rsi.closeRowSetIterator();
           }
       AdfFacesContext.getCurrentInstance().addPartialTarget(selectitmCB); */
    }

    public void setSelectitmCB(RichSelectBooleanCheckbox selectitmCB) {
        this.selectitmCB = selectitmCB;
    }

    public RichSelectBooleanCheckbox getSelectitmCB() {
        return selectitmCB;
    }

    public void setSelectAllCB(RichSelectBooleanCheckbox selectAllCB) {
        this.selectAllCB = selectAllCB;
    }

    public RichSelectBooleanCheckbox getSelectAllCB() {
        return selectAllCB;
    }

    public void setVisAfterAuth(String visAfterAuth) {
        this.visAfterAuth = visAfterAuth;
    }

    public String getVisAfterAuth() {
        if (getAm().getMmDrftPo().getCurrentRow() != null) {
            if (getAm().getMmDrftPo().getCurrentRow().getAttribute("PoMode").toString().equals("231")) {
                Row r = getAm().getMmDrftPo().getCurrentRow();
                Integer SlocId = (Integer)r.getAttribute("SlocId");
                String CldId = (String)r.getAttribute("CldId");
                String OrgId = (String)r.getAttribute("OrgId");
                Integer typeId = (Integer)r.getAttribute("PoType");
                String wfnum = getAm().getWfNo(SlocId, CldId, OrgId, 18504);
                /*   if(getAm().getMmDrftPo().getCurrentRow().getAttribute("UsrIdMod")!=null)
        usrmod=getAm().getMmDrftPo().getCurrentRow().getAttribute("UsrIdMod").toString(); */
                String usrlogin = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
                Integer usrIdCrt = (Integer)r.getAttribute("UsrIdCreate");
                Object ret =
                    callStoredFunction(NUMBER, "APP.WF_GET_USR_LEVEL(?,?,?,?,?,?,?)", new Object[] { SlocId, CldId,
                                                                                                     OrgId, usrlogin,
                                                                                                     wfnum, 18504,
                                                                                                     typeId });

                if (ret != null && !(ret.equals(0))) {
                    Object top =
                        callStoredFunction(NUMBER, "APP.WF_GET_TOP_LVL(?,?,?,?)", new Object[] { SlocId, CldId, OrgId,
                                                                                                 wfnum });

                    if ((top).equals(ret) || usrIdCrt.equals(usrlogin)) {

                        Row currDoc = getAm().getMmDrftPo().getCurrentRow();
                        String refDocId = currDoc.getAttribute("DocId").toString();
                        //Check if PO is being used in GE or Receipt
                        String retval =
                            (String)callStoredFunction(STRING, "MM.MM_IS_PO_AMMENDABLE(?,?,?,?)", new Object[] { CldId,
                                                                                                                 SlocId,
                                                                                                                 OrgId,
                                                                                                                 refDocId });

                        System.out.println("RetVal is ammend=" + retval);
                        if (retval != null && retval.toString().equals("Y"))
                            return "C";
                        else if (retval != null && retval.toString().equals("N"))
                            return "S";
                        else
                            return "N";
                        /*  RowSetIterator rsitm=getAm().getMmDrftPoItm().createRowSetIterator(null);
                Number pend=new Number(0);
                Number ord=new Number(0);
                System.out.println("No. of rows in Item="+rsitm.getRowCount());
        while(rsitm.hasNext())
        {
            Row ritm=rsitm.next();
            pend = (Number)ritm.getAttribute("BalQty");
            ord = (Number)ritm.getAttribute("OrdQty");
            System.out.println("Doc="+ritm.getAttribute("DocId")+" and Ord="+ord+" and Bal="+pend);
        if(pend.compareTo(ord) != 0)
        {
            return "S";
            }
            }
        rsitm.closeRowSetIterator();
        return "C"; */
                    }
                }
            }
        }
        return "N";
        //return visAfterAuth;
    }

    public void shortCloseDL(DialogEvent dialogEvent) {
        /*  if (dialogEvent.getOutcome().name().equals("ok"))
        {
            System.out.println("variable="+getVisAfterAuth());
        if(getVisAfterAuth().equals("C"))
          getAm().getMmDrftPo().getCurrentRow().setAttribute("PoStatus", 220);
        else if(getVisAfterAuth().equals("S"))
        getAm().getMmDrftPo().getCurrentRow().setAttribute("PoStatus", 219);
        OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
        operationBinding.execute();
         String CldId = (String)getAm().getMmDrftPo().getCurrentRow().getAttribute("CldId");
            Integer SlocId = (Integer)getAm().getMmDrftPo().getCurrentRow().getAttribute("SlocId");
            String OrgId = (String)getAm().getMmDrftPo().getCurrentRow().getAttribute("OrgId");
            Integer FyId = (Integer)getAm().getMmDrftPo().getCurrentRow().getAttribute("FyId");
            String DocId = (String)getAm().getMmDrftPo().getCurrentRow().getAttribute("DocId");
            Object ret =
                (Object)callStoredFunction(NUMBER, "MM.MM_UPDT_ORD_STK(?,?,?,?,?)",
                                               new Object[] { CldId,SlocId,OrgId, FyId, DocId});
            OperationBinding operationBindings = getBindings().getOperationBinding("Commit");
            operationBindings.execute();
        }   */
    }

    public static void setResetflg(String resetflg) {
        PurOrderAddEditBean.resetflg = resetflg;
    }

    public static String getResetflg() {
        return resetflg;
    }

    public void setResettaxPop(RichPopup resettaxPop) {
        this.resettaxPop = resettaxPop;
    }

    public RichPopup getResettaxPop() {
        return resettaxPop;
    }

    public void itmUnitVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
            ViewObjectImpl po = getAm().getMmDrftPo();
            ViewObjectImpl itm = getAm().getMmDrftPoItm();
            String itmId = (String)itm.getCurrentRow().getAttribute("ItmId");
            String uomDest = (String)valueChangeEvent.getNewValue();
            System.out.println("Dest unit=" + uomDest);
            String uomSrc = (String)itm.getCurrentRow().getAttribute("ItmUomBs");
            System.out.println("Uom Src=" + uomSrc);

            Number convFctr = new Number(1);
            /* ViewObjectImpl voUomVw=getAm().getLovUomVw1();
            RowQualifier rquom=new RowQualifier(voUomVw);
            rquom.setWhereClause("CldId='"+p_cldId+"' and SlocId="+pslocId+" and OrgId='"+pOrgId+"' and HoOrgId='"+p_hoOrgId+"' and ItmId='"+itmId+"' and UomIdSrc='"+uomSrc+"' and UomIdDest='"+uomDest+"'");
               Row ruom[]=voUomVw.getFilteredRows(rquom);
                       if(ruom.length>0)
                           convFctr = (Number)ruom[0].getAttribute("ConvFctr");
                       getAm().getMmDrftPoItm().getCurrentRow().setAttribute("UomConvFctr",convFctr);
                       System.out.println("Conversion factor for this UOM="+convFctr); */
            BigDecimal o =
                (BigDecimal)(callStoredFunction(Types.NUMERIC, "APP.FN_GET_UOM_CONV_FCTR(?,?,?,?,?,?)", new Object[] { pslocId,
                                                                                                                       p_cldId,
                                                                                                                       pOrgId,
                                                                                                                       itmId,
                                                                                                                       uomSrc,
                                                                                                                       uomDest }));


            try {
                convFctr = new Number(o);
            } catch (SQLException e) {
                System.out.println("error =" + e);
            }
            System.out.println("Conversion factor for this UOM=" + convFctr);
            getAm().getMmDrftPoItm().getCurrentRow().setAttribute("UomConvFctr", convFctr);

            Row rw = itm.getCurrentRow();
            Object obj = getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId");
            Row[] prfrow = getAm().getOrgMmPrf1().getFilteredRows("OrgId", pOrgId);
            String policy = "N";
            if (prfrow.length > 0)
                if (prfrow[0].getAttribute("ApplyPriceFrmPolicy") != null)
                    policy = (String)prfrow[0].getAttribute("ApplyPriceFrmPolicy");
            if (policy.equals("N")) {
                if (obj != null) {
                    String eoId = obj.toString();
                    ViewObjectImpl voi = getAm().getLovItmIdEo();
                    RowQualifier rqagain = new RowQualifier(voi);
                    rqagain.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and OrgId='" + pOrgId +
                                           "' and EoId=" + eoId + " and ItmId='" + itmId + "' and Actv='Y'");
                    Row[] rowsagain = voi.getFilteredRows(rqagain);
                    if (rowsagain.length > 0) {
                        System.out.println("ItmPrice=" + rowsagain[0].getAttribute("ItmPriceUomBs"));
                        Number price = new Number(0);
                        if (rowsagain[0].getAttribute("ItmPriceUomBs") != null)
                            price = (Number)rowsagain[0].getAttribute("ItmPriceUomBs");
                        getAm().getMmDrftPoItm().getCurrentRow().setAttribute("ItmPrice", price.multiply(convFctr));
                    }
                }
            } else if (getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId") != null) {
                Object eo = getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId");
                BigDecimal retval =
                    (BigDecimal)(callStoredFunction(NUMBER, "APP.APP_GET_POLICY_PRICE(?,?,?,?,?,?,?)", new Object[] { p_cldId,
                                                                                                                      pslocId,
                                                                                                                      p_hoOrgId,
                                                                                                                      pOrgId,
                                                                                                                      eo,
                                                                                                                      itmId,
                                                                                                                      "PO" }));
                if (retval.compareTo(new BigDecimal(-1)) == 0) {
                    String msg = "Policy for this Item not Define.";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    getAm().getMmDrftPoItm().getCurrentRow().setAttribute("ItmPrice",
                                                                          (retval).multiply(new BigDecimal(convFctr.toString())));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);

                }

            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);
    }

    public void setPoBasisBinding(RichSelectOneChoice poBasisBinding) {
        this.poBasisBinding = poBasisBinding;
    }

    public RichSelectOneChoice getPoBasisBinding() {
        return poBasisBinding;
    }

    public void discValItmVCE(ValueChangeEvent valueChangeEvent) {
        Number oldDisc = new Number(0);
        Number oldtotDisc = new Number(0);
        Number newDisc = new Number(0);
        Number newDiscVal = new Number(0);
        Number ordQty = new Number(0);
        Number itmPrice = new Number(0);
        String discType = "A";
        Row poItmRow = getAm().getMmDrftPoItm().getCurrentRow();
        if (poItmRow.getAttribute("ItmPrice") != null)
            itmPrice = (Number)poItmRow.getAttribute("ItmPrice");
        if (poItmRow.getAttribute("OrdQty") != null)
            ordQty = (Number)poItmRow.getAttribute("OrdQty");
        if (poItmRow.getAttribute("DiscType") != null)
            discType = (String)poItmRow.getAttribute("DiscType");
        if (poItmRow.getAttribute("DiscVal") != null)
            oldDisc = (Number)poItmRow.getAttribute("DiscVal");
        System.out.println("Discount value=" + oldDisc);
        if (poItmRow.getAttribute("TotDiscAmtSp") != null)
            oldtotDisc = (Number)poItmRow.getAttribute("TotDiscAmtSp");

        if (valueChangeEvent.getNewValue() != null)
            newDisc = (Number)valueChangeEvent.getNewValue();
        if (discType.equals("A")) {
            newDiscVal = newDisc;
        } else if (discType.equals("P")) {
            newDiscVal = new Number(((itmPrice.multiply(ordQty).multiply(newDisc)).divide(new Number(100))).round(6));
        }
        System.out.println("new disc amt=" + newDiscVal);
        poItmRow.setAttribute("DiscAmtSp", newDiscVal);
        poItmRow.setAttribute("TransDiscAmt", newDiscVal);
        // poItmRow.setAttribute("DiscVal",newDiscVal);
        poItmRow.setAttribute("TransItmAmtSp", itmPrice.multiply(ordQty).subtract(newDiscVal));
        System.out.println("Disc Amt set=" + poItmRow.getAttribute("DiscAmtSp") + " and " +
                           poItmRow.getAttribute("TransDiscAmt"));
        //Reset Total Discount Amount
        RowSetIterator rowsetitr = getAm().getMmDrftPoItm().createRowSetIterator(null);
        while (rowsetitr.hasNext()) {
            Row r = rowsetitr.next();
            /*    if(r.getAttribute("DiscVal")!=null)
           r.setAttribute("TotDiscAmtSp",r.getAttribute("DiscVal"));
            else
                r.setAttribute("TotDiscAmtSp",new Number(0));   */
            if (r.getAttribute("DiscAmtSp") != null)
                r.setAttribute("TotDiscAmtSp", r.getAttribute("DiscAmtSp"));
            else
                r.setAttribute("TotDiscAmtSp", new Number(0));

        }
        rowsetitr.closeRowSetIterator();


        //  oldtotDisc.subtract(oldDisc).add(newDiscVal);
        Number zero = new Number(0);
        String discTypePo = "A";
        Number discValPo = zero;
        Number sumItmAmt = zero;
        //     Number discPerc=zero;

        Row currPo = getAm().getMmDrftPo().getCurrentRow();
        if (currPo.getAttribute("DiscType") != null)
            discTypePo = (String)currPo.getAttribute("DiscType");

        if (currPo.getAttribute("DiscVal") != null)
            discValPo = (Number)currPo.getAttribute("DiscVal");
        RowSetIterator rsiItm = getAm().getMmDrftPoItm().createRowSetIterator(null);
        while (rsiItm.hasNext()) {
            Row r = rsiItm.next();
            if (r.getAttribute("TransItmAmtSp") != null) //Item amount without tax
                sumItmAmt = sumItmAmt.add((Number)r.getAttribute("TransItmAmtSp"));
        }
        rsiItm.closeRowSetIterator();
        Number amt = zero;
        Number itmAmtInPer = zero;
        Number discAmt = zero;
        Number discAmtItm = zero;
        RowSetIterator rsiItmPer = getAm().getMmDrftPoItm().createRowSetIterator(null);
        while (rsiItmPer.hasNext()) {
            amt = zero;
            discAmt = zero;
            itmAmtInPer = zero;
            discAmtItm = zero;
            Row r = rsiItmPer.next();

            if (r.getAttribute("TransItmAmtSp") != null) //Item amount without tax
                amt = ((Number)r.getAttribute("TransItmAmtSp"));
            System.out.println("Item amount without tax=" + amt);
            if (discTypePo.equals("A") && sumItmAmt.compareTo(zero) != 0) {
                itmAmtInPer = amt.multiply(new Number(100)).divide(sumItmAmt); //Item amount in percent of total amount
                discAmt = (discValPo.multiply(itmAmtInPer)).divide(new Number(100)); //discount amount for an item
            } else {
                discAmt =
                        (amt.multiply(discValPo)).divide(new Number(100)); //if discount in percent then calculate discount amount.
                System.out.println("Discamt=" + discAmt);
            }

            if (r.getAttribute("DiscAmtSp") != null)
                discAmtItm = (Number)r.getAttribute("DiscAmtSp");
            System.out.println("disc amt itemwise=" + discAmtItm);
            r.setAttribute("TotDiscAmtSp", discAmtItm.add(discAmt));
            System.out.println("total Disc amt sp=" + discAmtItm.add(discAmt));
        }
        rsiItmPer.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(poWiseDiscount);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
    }

    public void setGoToTandCButton(RichCommandButton goToTandCButton) {
        this.goToTandCButton = goToTandCButton;
    }

    public RichCommandButton getGoToTandCButton() {
        return goToTandCButton;
    }


    public DnDAction tncDropListener(DropEvent dropEvent) {
        ViewObjectImpl v1 = getAm().getMmDrftPoTnc();
        ViewObject v2 = getAm().getMmDrftPo();
        Row curr = v2.getCurrentRow();
        Integer tncid = (Integer)getAm().getLovTncId().getCurrentRow().getAttribute("TncId");
        adfLog.info("tnc id  =:::  " + tncid);
        //getAm().getLovTncId().executeQuery();
        RowQualifier rq = new RowQualifier(v1);
        rq.setWhereClause("CldId='" + curr.getAttribute("CldId") + "' and SlocId=" + curr.getAttribute("SlocId") +
                          " and OrgId='" + curr.getAttribute("OrgId") + "' and DocId='" + curr.getAttribute("DocId") +
                          "' and TncId=" + tncid + "");
        Row fr[] = v1.getFilteredRows(rq);
        if (fr.length > 0) {
            FacesMessage message = new FacesMessage("Duplicate Record Exist.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            /*   OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters4");
            operationBinding.execute();         */
            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            Row r = getAm().getMmDrftPoTnc().createRow();
            System.out.println("TncId=" + tncid);
            r.setAttribute("CldId", curr.getAttribute("CldId"));
            r.setAttribute("SlocId", curr.getAttribute("SlocId"));
            r.setAttribute("OrgId", curr.getAttribute("OrgId"));
            r.setAttribute("DocId", curr.getAttribute("DocId"));
            r.setAttribute("TncId", tncid);
            r.setAttribute("UsrIdCreate", p_user_id);
            System.out.println("Inserting row key=" + r.getKey());
            getAm().getMmDrftPoTnc().insertRow(r);
            getAm().getMmDrftPoTnc1().executeQuery();
            /*  ArrayList lst = new ArrayList(1);
            lst.add(v1.getCurrentRow().getKey());
            getTncTableBind().setActiveRowKey(lst);  */

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(termsAndAggTab);
        return DnDAction.NONE;
    }

    public void delTermsAction(ActionEvent actionEvent) {
        ViewObject vo = getAm().getMmDrftPoTnc();
        Row rowTnC = vo.getCurrentRow();
        System.out.println("Row key to delete=" + rowTnC.getKey());
        OperationBinding obdel = getBindings().getOperationBinding("Delete1");
        obdel.execute();
        vo.executeQuery();
        OperationBinding ob = getBindings().getOperationBinding("Execute6");
        ob.execute();
        getAm().getMmDrftPoTnc1().executeQuery();
        vo.executeQuery();
        getAm().getLovTncId().executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tncTableBind);

    }

    public void autoFillAmount(ActionEvent actionEvent) {
        Row porow = getAm().getMmDrftPo().getCurrentRow();
        Row tncrow = getAm().getMmDrftPoTnc().getCurrentRow();
        porow.setAttribute("TransPmtAmt", porow.getAttribute("TransTotalPoCostSp"));
        AdfFacesContext.getCurrentInstance().addPartialTarget(transPmtAmt);

    }

    public void openOrdValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number qty = new Number(0);
        if (object != null) {
            qty = (Number)object;
            Number zero = new Number(0);
            if (qty.compareTo(zero) < 0) {
                String msg2 = resolvEl("#{bundle['MSG.337']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
        }
        /*  else
        {
            if(getAm().getMmDrftPo().getCurrentRow().getAttribute("PoType").equals(491))
            {
                    String msg2 = resolvEl("#{bundle['MSG.337']}");
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            }
       */

    }

    public void setPolicyNm(String policyNm) {
        this.policyNm = policyNm;
    }

    public String getPolicyNm() {
        return policyNm;
    }

    public String goPolicyAL() {
        policyId = null;
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
        RowQualifier rq = new RowQualifier(getAm().getAppEoPricePlc());
        rq.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + p_sloc_id + " and HoOrgId='" + p_hoOrgId +
                          "' and EoId=" + getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId"));
        Row[] r = getAm().getAppEoPricePlc().getFilteredRows(rq);
        if (r.length > 0) {
            policyId = (String)r[0].getAttribute("PlcId");
        } else {
            policyId = null;
        }
        return "gotoPolicy";
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        /* String wf=null;
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String p_cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                         WfnoOp.getParamsMap().put("sloc_id", p_sloc_id);
                         WfnoOp.getParamsMap().put("cld_id", p_cldId);
                         WfnoOp.getParamsMap().put("org_id", pOrgId);
                         WfnoOp.getParamsMap().put("doc_no", PO_DOC_NO);
                         WfnoOp.execute();
                         if(WfnoOp.getErrors().size()==0)
                           wf = (String)WfnoOp.getResult();
       return wf;*/
        return wfId;
    }

    public void setCurrIdBinding(RichInputListOfValues currIdBinding) {
        this.currIdBinding = currIdBinding;
    }

    public RichInputListOfValues getCurrIdBinding() {
        return currIdBinding;
    }

    public void cancelScPoDL(ActionEvent actionEvent) {
        shortClosePoPopup.hide();
    }

    public void setShortClosePoPopup(RichPopup shortClosePoPopup) {
        this.shortClosePoPopup = shortClosePoPopup;
    }

    public RichPopup getShortClosePoPopup() {
        return shortClosePoPopup;
    }

    public String shortClosePoPopupAL() {

        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        PurOrderAMImpl poAM = getAm();
        ViewObject po = poAM.getMmDrftPo();
        Row currPo = po.getCurrentRow();
        String poDocId = po.getCurrentRow().getAttribute("DocId").toString();
        Integer pending = null;
        String action = "I";
        String remark = "A";
        Number amount11 = (Number)currPo.getAttribute("PoAmtBs");
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingPOCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_id);
        chkUsr.getParamsMap().put("CldId", cld_id);
        chkUsr.getParamsMap().put("OrgId", pOrgId);
        chkUsr.getParamsMap().put("PoDocNo", PO_DOC_NO);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }

        if (pending.compareTo(usr_id) != 0 && pending.compareTo(-1) != 0) {
            Row[] usrRows = getAm().getLovUsrId().getFilteredRows("UsrId", pending);
            StringBuffer usrName = new StringBuffer("");
            if (usrRows.length > 0) {
                usrName = new StringBuffer(usrRows[0].getAttribute("UsrName").toString());
                usrName = new StringBuffer("[").append(usrName).append("]");
            }

            String msg2 =
                "This Purchase Order is pending at other user " + usrName + " for approval, You cannot Cancel this.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            shortClosePoPopup.hide();

            return null;
        } else {

            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("sloc_id", sloc_id);
            WfnoOp.getParamsMap().put("cld_id", cld_id);
            WfnoOp.getParamsMap().put("org_id", pOrgId);
            WfnoOp.getParamsMap().put("doc_no", PO_DOC_NO);
            WfnoOp.execute();

            String WfNum = null;
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
                usrLevelOp.getParamsMap().put("PoDocId", PO_DOC_NO);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    if (usrLevelOp.getResult() != null)
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                if (level == -1) {
                    shortClosePoPopup.hide();
                    FacesMessage message =
                        new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    shortClosePoPopup.hide();

                    return null;
                } else {
                    OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                    insTxnOp.getParamsMap().put("cld_id", cld_id);
                    insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                    insTxnOp.getParamsMap().put("PO_DOC_NO", PO_DOC_NO);
                    insTxnOp.getParamsMap().put("WfNum", WfNum);
                    insTxnOp.getParamsMap().put("poDocId", poDocId);
                    insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                    insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                    insTxnOp.getParamsMap().put("levelFrm", level);
                    insTxnOp.getParamsMap().put("levelTo", level);
                    insTxnOp.getParamsMap().put("action", action);
                    insTxnOp.getParamsMap().put("remark", remark);
                    insTxnOp.getParamsMap().put("amount", amount11);
                    insTxnOp.getParamsMap().put("post", "S");
                    insTxnOp.execute();

                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                    }
                    if (res == 1) {
                        currPo.setAttribute("PoStatus", 700);
                        OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                        operationBinding.execute();
                    } else {
                        shortClosePoPopup.hide();
                        System.out.println("error during insert to WF");
                        return null;
                    }
                    shortClosePoPopup.hide();
                    System.out.println(level + "level--res" + res);
                    return "goToWf";
                }
            }
        }
        shortClosePoPopup.hide();
        return null;
    }

    public void prevPoSelectValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (getAm().getMmDrftPo().getCurrentRow().getAttribute("PoBasis") != null &&
            getAm().getMmDrftPo().getCurrentRow().getAttribute("PoBasis").toString().equals("490") && object != null) {
            Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}").toString();
            String poRefDocId = object.toString();
            adfLog.info("p_cldId    " + p_cldId + " pslocId  " + pslocId + "  pOrgId " + pOrgId + " poRefDocId " +
                        poRefDocId);


            OperationBinding checkOp = getBindings().getOperationBinding("checkOpenPurchaseValidity");
            checkOp.getParamsMap().put("orgId", pOrgId);
            checkOp.getParamsMap().put("cldId", p_cldId);
            checkOp.getParamsMap().put("slocId", pslocId);
            checkOp.getParamsMap().put("refDocId", poRefDocId);
            checkOp.execute();
            Integer ret = 0;
            if (checkOp.getResult() != null) {
                ret = Integer.parseInt(checkOp.getResult().toString());
            }
            System.out.println(ret.compareTo(new Integer(1)) + " ret    :::::::::::::::     " + ret);
            if (ret.compareTo(new Integer(1)) == 0) {
                String msg2 = "Order has been expired from this Open Order.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            } else {
                System.out.println("valid po :::::::::: ");
            }


            BigDecimal retval =
                (BigDecimal)(callStoredFunction(NUMBER, "MM.MM_GET_OPEN_ORD_VAL(?,?,?,?)", new Object[] { p_cldId,
                                                                                                          pslocId,
                                                                                                          pOrgId,
                                                                                                          poRefDocId }));
            adfLog.info("retval   " + retval);
            Number val = new Number(0);
            if (retval != null)
                try {
                    val = new Number(retval);
                } catch (SQLException e) {
                    System.out.println("error in cast=" + e);
                }
            if (val.compareTo(new Number(0)) <= 0) {
                String msg2 = "Order has been completed from this Open Order.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
        }
    }

    public void pricePolicyVCE(ValueChangeEvent valueChangeEvent) throws SQLException {
        if (valueChangeEvent.getNewValue() != null) {
            String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
            Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            RowQualifier rq = new RowQualifier(getAm().getLovAppPricePlc());
            String plcId = null;
            rq.setWhereClause("CldId='" + cld_id + "' and SlocId=" + sloc_id + " and HoOrgId='" + hoOrg_id +
                              "' and PlcNm='" + valueChangeEvent.getNewValue() + "'");
            Row[] r = getAm().getLovAppPricePlc().getFilteredRows(rq);
            if (r.length > 0)
                plcId = (String)r[0].getAttribute("PlcId");

            System.out.println("function parameters are eoid=" +
                               getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId") + " and plcId=" + plcId);

            System.out.println("No. of rows in plc before insert=" + getAm().getAppEoPricePlc().getRowCount());

            BigDecimal ret =
                (BigDecimal)callStoredFunction(NUMBER, "APP.FN_INS_EO_PRICE_PLC(?,?,?,?,?,?)", new Object[] { cld_id,
                                                                                                              sloc_id,
                                                                                                              hoOrg_id,
                                                                                                              getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId"),
                                                                                                              plcId,
                                                                                                              usr_id });
            Number retVal = new Number(ret);
            System.out.println("Return value=" + retVal);

            getAm().getAppEoPricePlc().executeQuery();
            getAm().getMmDrftPo().getCurrentRow().getAttribute("TransEoPolicyId");
            System.out.println("transEoPolicyId =" +
                               getAm().getMmDrftPo().getCurrentRow().getAttribute("TransEoPolicyId"));
            System.out.println("No. of rows in plc after insert=" + getAm().getAppEoPricePlc().getRowCount());
        }
    }

    public void setItmPoBind(RichInputText itmPoBind) {
        this.itmPoBind = itmPoBind;
    }

    public RichInputText getItmPoBind() {
        return itmPoBind;
    }

    public void setCoaNmBind(RichInputListOfValues coaNmBind) {
        this.coaNmBind = coaNmBind;
    }

    public RichInputListOfValues getCoaNmBind() {
        return coaNmBind;
    }

    public void setTransOcDescBind(RichSelectOneChoice transOcDescBind) {
        this.transOcDescBind = transOcDescBind;
    }

    public RichSelectOneChoice getTransOcDescBind() {
        return transOcDescBind;
    }

    public void setTransAmtBind(RichInputText transAmtBind) {
        this.transAmtBind = transAmtBind;
    }

    public RichInputText getTransAmtBind() {
        return transAmtBind;
    }

    public void uploadFromExcel(ActionEvent actionEvent) {
        try {

            if (file == null || file.getInputStream() == null) {
                FacesMessage message = new FacesMessage("Please Select a File..");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }
            InputStream inputStream = file.getInputStream();
            String fileName = file.getFilename();
            int pos = fileName.lastIndexOf(".");
            String fileType = fileName.substring(pos + 1, fileName.length());
            System.out.println("File Type is:  " + fileType);
            System.out.println("File Name is:  " + file.getFilename());

            if (!fileType.equalsIgnoreCase("xls")) {
                FacesMessage message = new FacesMessage("Please Select a xls File Type !!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }

            /*  if (true)
                return; */
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet at = workbook.getSheetAt(0);
            if (at != null) {
                int rowCount = at.getPhysicalNumberOfRows();
                System.out.println("Row count: " + rowCount);
                PurOrderAMImpl aMImpl = this.getAm();
                ViewObjectImpl drftPoItm = aMImpl.getMmDrftPoItm();
                List<List> list = new ArrayList<List>();
                outer:
                for (int i = 1; i < rowCount; i++) {
                    HSSFRow fRow = at.getRow(i);
                    if (fRow != null) {
                        // getBindings().getOperationBinding("Createwithparameters").execute();
                        // MmDrftPoItmVORowImpl currentRow = (MmDrftPoItmVORowImpl)drftPoItm.getCurrentRow();
                        if (fRow.getPhysicalNumberOfCells() < 6) {
                            continue outer;
                        }
                        List mylist = new ArrayList();
                        inner:
                        for (int j = 0; j < 6; j++) {
                            Cell cell = fRow.getCell(j);
                            if (cell == null) {
                                continue outer;
                            }
                            if (j == 0 && cell.getCellType() != 1) {
                                continue outer;
                            }
                            if (j == 1 && cell.getCellType() != 1) {
                                continue outer;
                            }
                            if (j == 2 && cell.getCellType() != 0) {
                                continue outer;
                            }
                            if (j == 3 && cell.getCellType() != 0) {
                                continue outer;
                            }
                            if (j == 4 &&
                                (cell.getCellType() != 1 || !(cell.getStringCellValue().equals("P") || cell.getStringCellValue().equals("A")))) {
                                continue outer;
                            }
                            if (j == 5 && cell.getCellType() != 0) {
                                continue outer;
                            }
                            switch (j) {
                            case 0:
                                String flag = checkItmId(cell.getStringCellValue());
                                if (flag.equalsIgnoreCase("N")) {
                                    continue outer;
                                }
                                
                                mylist.add(cell.getStringCellValue());
                                break;
                            case 1:
                                String chFlag =
                                    checkUOMId(fRow.getCell(0).getStringCellValue(), cell.getStringCellValue());
                                if (chFlag.equalsIgnoreCase("N")) {
                                    continue outer;
                                }
                                mylist.add(cell.getStringCellValue());
                                break;
                            case 2:
                                try {
                                    adfLog.info("cell.getNumericCellValue() 33 :::::"+cell.getNumericCellValue());
                                    if (cell.getNumericCellValue() <= 0) {//if (cell.getNumericCellValue() <= 0) 
                                        continue outer;
                                    }
                                    mylist.add(new oracle.jbo.domain.Number(cell.getNumericCellValue()));
                                } catch (Exception e) {
                                    System.out.println("Exception occur...");
                                    e.printStackTrace();
                                    continue outer;
                                }
                                break;
                            case 3:
                                try {
                                    adfLog.info("cell.getNumericCellValue()  :::::"+cell.getNumericCellValue());
                                    if (cell.getNumericCellValue() <= 0) { //if (cell.getNumericCellValue() <= 0)
                                        continue outer;
                                    }
                                    String check =
                                        checkOrdQuantity(fRow.getCell(0).getStringCellValue(), new oracle.jbo.domain.Number(cell.getNumericCellValue()));
                                    if (check.equalsIgnoreCase("N")) {
                                        continue outer;
                                    }
                                    mylist.add(new oracle.jbo.domain.Number(cell.getNumericCellValue()));
                                } catch (Exception e) {
                                    System.out.println("Exception occur...");
                                    e.printStackTrace();
                                    continue outer;
                                }
                                break;
                            case 4:
                                mylist.add(cell.getStringCellValue());
                                break;

                            case 5:
                                try {
                                    if (cell.getNumericCellValue() < 0 ||
                                        (fRow.getCell(4).getStringCellValue().equalsIgnoreCase("P") &&
                                         cell.getNumericCellValue() > 100)) {
                                        continue outer;
                                    }
                                    /* if (fRow.getCell(4).getStringCellValue().equalsIgnoreCase("P") &&
                                        cell.getNumericCellValue() > 100) {
                                        continue outer;
                                    } */
                                    mylist.add(new oracle.jbo.domain.Number(cell.getNumericCellValue()));
                                } catch (Exception e) {
                                    System.out.println("Exception occur...");
                                    e.printStackTrace();
                                    continue outer;
                                }
                                break;
                            }
                            //      mylist.add(ce)
                        }
                        String itmName = fRow.getCell(0).getStringCellValue();
                        String itemUnit = fRow.getCell(1).getStringCellValue();
                        callForSuppFun(itmName, itemUnit);
                        list.add(mylist);
                    }
                }

                //System.out.println("the list is:\n  " + list);
                ListIterator<List> itr = list.listIterator();
                System.out.println("total no of Rows are: " + list.size());
                /* while (itr.hasNext()) {
                    List list_2 = itr.next();
                    System.out.println("list is: " + list_2);
                } */
                ListIterator<List> iterator = list.listIterator();
                while (iterator.hasNext()) {
                    List list_2 = iterator.next();
                    String result = checkForDuplicateItmId((String)list_2.get(0));
                    if(result.equalsIgnoreCase("Y")) {
                        continue;
                    }
                    if(((String)list_2.get(4)).equals("A")) {
                        Number price = (Number)list_2.get(2);
                        Number qun = (Number)list_2.get(3);
                        Number disc = (Number)list_2.get(5);
                        Number res = (Number)price.mul(qun);
                        if(res.compareTo(disc) <0)
                            continue ;
                    }
                    if(((String)list_2.get(4)).equals("P")) {
                        Number price = (Number)list_2.get(2);
                        Number qun = (Number)list_2.get(3);
                        Number disc = (Number)list_2.get(5);
                        Number res = (Number)price.mul(qun);
                        Number disAmt = (Number)(((Number)res.multiply(disc)).divide(new Number(100))).round(6);
                        if(res.compareTo(disAmt) <0)
                            continue ;
                    }
                    getBindings().getOperationBinding("Createwithparameters").execute();
                    MmDrftPoItmVORowImpl currentRow = (MmDrftPoItmVORowImpl)drftPoItm.getCurrentRow();
                    adfLog.info((Number)list_2.get(2)+  "  Createwithparameters :::: "+(Number)list_2.get(3));
                    currentRow.setItmId((String)list_2.get(0));
                    currentRow.setItmUom((String)list_2.get(1));
                   // currentRow.setItmUomBs((String)list_2.get(1));
                     this.setItmProperty((String)list_2.get(0));            // set itm property like base unit ,Unit class and tax exmpted flag for each item.
                    currentRow.setItmPrice((Number)list_2.get(2));
                    currentRow.setOrdQty((Number)list_2.get(3));
                    currentRow.setDiscType((String)list_2.get(4));
                    currentRow.setDiscVal((Number)list_2.get(5));
                    if(((Number)list_2.get(5)).compareTo(new Number(0))==1){
                        adfLog.info("  discount  greater than zero");
                        this.checkDiscountValue((Number)list_2.get(5));
                    }
                }
                /*  outer:
                for (int i = 1; i < rowCount; i++) {
                    HSSFRow fRow = at.getRow(i);
                    if (fRow != null) {
                       // getBindings().getOperationBinding("Createwithparameters").execute();
                       // MmDrftPoItmVORowImpl currentRow = (MmDrftPoItmVORowImpl)drftPoItm.getCurrentRow();
                        inner:
                        for (int j = 0; j < 6; j++) {
                            Cell cell = fRow.getCell(j);
                            if (cell != null) {
                                switch (j) {
                                case 0:
                               //     currentRow.setItmId(cell.getStringCellValue());
                                    break;
                                case 1:
                              //      currentRow.setItmUom(cell.getStringCellValue());
                                    break;
                                case 2:
                                    try {
                             //           currentRow.setItmPrice(new oracle.jbo.domain.Number(cell.getNumericCellValue()));
                                    } catch (Exception e) {
                                        System.out.println("Exception occur...");
                                        e.printStackTrace();
                                    }
                                    break;
                                case 3:
                                    try {
                              //          currentRow.setOrdQty(new oracle.jbo.domain.Number(cell.getNumericCellValue()));
                                    } catch (Exception e) {
                                        System.out.println("Exception occur... 2");
                                        e.printStackTrace();
                                    }
                                    break;
                                case 4:
                              //      currentRow.setDiscType(cell.getStringCellValue());
                                    break;
                                case 5:
                                    try {
                             //           currentRow.setDiscVal(new oracle.jbo.domain.Number(cell.getNumericCellValue()));
                                    } catch (Exception e) {
                                        System.out.println("Exception occur 3");
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                            }
                            //      System.out.println(cell);
                        }
                    }
                    //  System.out.println("\n");
                } */
                drftPoItm.executeQuery();
                //     Set<Object> set = new LinkedHashSet<Object>();

            } else {
                FacesMessage message = new FacesMessage("Please Select a Filled work Sheet !!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        setFile(null);
        execlUploadPopup.hide();
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    private String checkItmId(String itmId) {
        String flag = "N";
        //        FN_MM_CHK_ORG_ITM_ID
        //callStoredFunction(Types.VARCHAR, FN_MM_CHK_ORG_ITM_ID, bindVars)
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));

        Object check =
            callStoredFunction(Types.VARCHAR, "MM.FN_MM_CHK_ORG_ITM_ID (?,?,?,?,?)", new Object[] { cld_id, sloc_id,
                                                                                                    hoOrg_id, OrgId,
                                                                                                    itmId });
        if (check != null) {
            flag = check.toString();
        }
        return flag;
    }

    private void callForSuppFun(String itmId, String itmUOM) {
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));

        ViewObjectImpl drftPo = getAm().getMmDrftPo();
        MmDrftPoVORowImpl drftPoview = (MmDrftPoVORowImpl)drftPo.getCurrentRow();
        Integer eoId = drftPoview.getEoId();
        if (eoId != null) {
            ViewObjectImpl voi = getAm().getLovItmIdEo();
            RowQualifier rq = new RowQualifier(voi);
            rq.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and OrgId='" + pOrgId +
                              "' and EoId=" + eoId + " and ItmId='" + itmId + "' and Actv='Y'");
            Row[] rows = voi.getFilteredRows(rq);
            adfLog.info("Row length---" + rows.length);
            if (rows.length == 0) {
                adfLog.info("Supplier  is not supported.. ");
                Integer retval =
                    Integer.parseInt(callStoredFunction(INTEGER, "MM.MM_INS_EO_ITM_AUTO(?,?,?,?,?,?,?,?,?,?,?)",
                                                        new Object[] { p_cldId, pslocId, p_hoOrgId, pOrgId, eoId,
                                                                       drftPoview.getCurrIdSp(), itmId, itmUOM, usr_id,
                                                                       18504, drftPoview.getDocId() }).toString());

            }
        }
    }

    private String checkOrdQuantity(String itmId, Number val) {
        String flag = "N";
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));

        ViewObjectImpl drftPo = getAm().getMmDrftPo();
        MmDrftPoVORowImpl drftPoview = (MmDrftPoVORowImpl)drftPo.getCurrentRow();
       adfLog.info("Fy id is:  " + drftPoview.getFyId() + "\t" + itmId + "\t" + val + "\t" + p_hoOrgId + "\t" +
                           p_cldId + "\t" + p_sloc_id + "\t" + p_org_id);
        Object check =
            callStoredFunction(Types.VARCHAR, "MM.PKG_MM_STK.IS_ORD_QTY_VALID(?,?,?,?,?,?,?)", new Object[] { p_sloc_id,
                                                                                                              p_cldId,
                                                                                                              p_hoOrgId,
                                                                                                              p_org_id,
                                                                                                              itmId,
                                                                                                              get_Org_FyId(p_cldId,
                                                                                                                           p_org_id,
                                                                                                                           drftPoview.getPoDt()),
                                                                                                              val });
        System.out.println("Check is: " + check);

        if (check != null) {
            flag = check.toString();
        }
        return flag;
    }

    private String checkUOMId(String itmId, String UOMID) {
        String flag = "N";
        //        FN_MM_CHK_ORG_ITM_ID
        //callStoredFunction(Types.VARCHAR, FN_MM_CHK_ORG_ITM_ID, bindVars)
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));

        Object check =
            callStoredFunction(Types.VARCHAR, "MM.FN_MM_CHK_ORG_ITM_UOM (?,?,?,?,?,?)", new Object[] { cld_id, sloc_id,
                                                                                                       hoOrg_id, OrgId,
                                                                                                       itmId, UOMID });
        if (check != null) {
            flag = check.toString();
        }
        return flag;
    }

    private Integer get_Org_FyId(String cld_id, String OrgId, Date dt) {
        Integer fyid =
            (Integer)(callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] { cld_id, OrgId,
                                                                                                    dt }));
        return fyid;
    }

    public void sampleDownloadAction(FacesContext facesContext, OutputStream outputStream) throws IOException {
        // Add event code here...
        //Object execute = getBindings().getOperationBinding("getPath").execute();
        
           // String path = new String((StringBuffer)execute);
            String path = getPath();
            File file = new File(path + "\\POSample.xls");
            InputStream in = new FileInputStream(file);
            byte b[] = new byte[in.available()];
            in.read(b);
            outputStream.write(b);
            outputStream.flush();
            outputStream.close();
        
    }

    public String getPath() {
        //System.out.println(getparamSlocId() + "");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String path =
            ((String)callStoredFunction(Types.VARCHAR, " APP.FN_GET_APP_DOC_ATTACH_PATH(?)",
                                                        new Object[] { sloc_id }));
        //System.out.println("path " + path);
        if (path != null) {
            // System.out.println("return path when fucntion called sucessfully");
            return path;
        } else {
            //System.out.println("return when fucntion not called successfully");
            return "D:\\DOCS";
        }
    }
    
    private  String checkForDuplicateItmId(String itmId) {
        String flag = "N";
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        ViewObjectImpl drftPo = getAm().getMmDrftPo();
        MmDrftPoVORowImpl drftPoview = (MmDrftPoVORowImpl)drftPo.getCurrentRow();
        
        ViewObjectImpl drftPoItm = this.getAm().getMmDrftPoItm();
        RowQualifier rq = new RowQualifier(drftPoItm);
        rq.setWhereClause("CldId = '"+cld_id+"' and SlocId= "+sloc_id+" and OrgId = '"+OrgId+"' and DocId = '"+drftPoview.getDocId()+"' and ItmId = '"+itmId+"'");
        adfLog.info("filter is:  "+rq.getExprStr());
        Row[] filteredRows = drftPoItm.getFilteredRows(rq);
        adfLog.info("filetered rows are: "+filteredRows.length);
        if(filteredRows.length>0)
        {
            flag = "Y";
        }
        return flag;
    }
    
    private void checkDiscountValue(Number discVal){
        Number oldDisc = new Number(0);
        Number oldtotDisc = new Number(0);
        Number newDisc = new Number(0);
        Number newDiscVal = new Number(0);
        Number ordQty = new Number(0);
        Number itmPrice = new Number(0);
        String discType = "A";
        Row poItmRow = getAm().getMmDrftPoItm().getCurrentRow();
        if (poItmRow.getAttribute("ItmPrice") != null)
            itmPrice = (Number)poItmRow.getAttribute("ItmPrice");
        if (poItmRow.getAttribute("OrdQty") != null)
            ordQty = (Number)poItmRow.getAttribute("OrdQty");
        if (poItmRow.getAttribute("DiscType") != null)
            discType = (String)poItmRow.getAttribute("DiscType");
        if (poItmRow.getAttribute("DiscVal") != null)
            oldDisc = (Number)poItmRow.getAttribute("DiscVal");
        System.out.println("Discount value=" + oldDisc);
        if (poItmRow.getAttribute("TotDiscAmtSp") != null)
            oldtotDisc = (Number)poItmRow.getAttribute("TotDiscAmtSp");

        if (discVal!= null)
            newDisc = (Number)discVal;
        if (discType.equals("A")) {
            newDiscVal = newDisc;
        } else if (discType.equals("P")) {
            newDiscVal = new Number(((itmPrice.multiply(ordQty).multiply(newDisc)).divide(new Number(100))).round(6));
        }
        System.out.println("new disc amt=" + newDiscVal);
        poItmRow.setAttribute("DiscAmtSp", newDiscVal);
        poItmRow.setAttribute("TransDiscAmt", newDiscVal);
        // poItmRow.setAttribute("DiscVal",newDiscVal);
        poItmRow.setAttribute("TransItmAmtSp", itmPrice.multiply(ordQty).subtract(newDiscVal));
        System.out.println("Disc Amt set=" + poItmRow.getAttribute("DiscAmtSp") + " and " +
                           poItmRow.getAttribute("TransDiscAmt"));
        //Reset Total Discount Amount
        RowSetIterator rowsetitr = getAm().getMmDrftPoItm().createRowSetIterator(null);
        while (rowsetitr.hasNext()) {
            Row r = rowsetitr.next();
            /*    if(r.getAttribute("DiscVal")!=null)
           r.setAttribute("TotDiscAmtSp",r.getAttribute("DiscVal"));
            else
                r.setAttribute("TotDiscAmtSp",new Number(0));   */
            if (r.getAttribute("DiscAmtSp") != null)
                r.setAttribute("TotDiscAmtSp", r.getAttribute("DiscAmtSp"));
            else
                r.setAttribute("TotDiscAmtSp", new Number(0));

        }
        rowsetitr.closeRowSetIterator();


        //  oldtotDisc.subtract(oldDisc).add(newDiscVal);
        Number zero = new Number(0);
        String discTypePo = "A";
        Number discValPo = zero;
        Number sumItmAmt = zero;
        //     Number discPerc=zero;

        Row currPo = getAm().getMmDrftPo().getCurrentRow();
        if (currPo.getAttribute("DiscType") != null)
            discTypePo = (String)currPo.getAttribute("DiscType");

        if (currPo.getAttribute("DiscVal") != null)
            discValPo = (Number)currPo.getAttribute("DiscVal");
        RowSetIterator rsiItm = getAm().getMmDrftPoItm().createRowSetIterator(null);
        while (rsiItm.hasNext()) {
            Row r = rsiItm.next();
            if (r.getAttribute("TransItmAmtSp") != null) //Item amount without tax
                sumItmAmt = sumItmAmt.add((Number)r.getAttribute("TransItmAmtSp"));
        }
        rsiItm.closeRowSetIterator();
        Number amt = zero;
        Number itmAmtInPer = zero;
        Number discAmt = zero;
        Number discAmtItm = zero;
        RowSetIterator rsiItmPer = getAm().getMmDrftPoItm().createRowSetIterator(null);
        while (rsiItmPer.hasNext()) {
            amt = zero;
            discAmt = zero;
            itmAmtInPer = zero;
            discAmtItm = zero;
            Row r = rsiItmPer.next();

            if (r.getAttribute("TransItmAmtSp") != null) //Item amount without tax
                amt = ((Number)r.getAttribute("TransItmAmtSp"));
            System.out.println("Item amount without tax=" + amt);
            if (discTypePo.equals("A") && sumItmAmt.compareTo(zero) != 0) {
                itmAmtInPer = amt.multiply(new Number(100)).divide(sumItmAmt); //Item amount in percent of total amount
                discAmt = (discValPo.multiply(itmAmtInPer)).divide(new Number(100)); //discount amount for an item
            } else {
                discAmt =
                        (amt.multiply(discValPo)).divide(new Number(100)); //if discount in percent then calculate discount amount.
                System.out.println("Discamt=" + discAmt);
            }

            if (r.getAttribute("DiscAmtSp") != null)
                discAmtItm = (Number)r.getAttribute("DiscAmtSp");
            System.out.println("disc amt itemwise=" + discAmtItm);
            r.setAttribute("TotDiscAmtSp", discAmtItm.add(discAmt));
            System.out.println("total Disc amt sp=" + discAmtItm.add(discAmt));
        }
        rsiItmPer.closeRowSetIterator();
       /*  AdfFacesContext.getCurrentInstance().addPartialTarget(poWiseDiscount);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind); */
        }


    public String importItmFrmExcelAction() {
        Row rowPo = getAm().getMmDrftPo().getCurrentRow();
        //Object obj = rowPo.getAttribute("PoBasis");

            if (rowPo.getAttribute("EoId") != null) {
                if (rowPo.getAttribute("CurrIdSp") != null) {
                    if (rowPo.getAttribute("CurrConvFctr") != null) {
                
                showPopup(execlUploadPopup, true);
        } else {
            String msg2 = "Currency not valid or Conversion for this currency is not define.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(currIdBinding.getClientId(), message2);
        }
        } else {
        String msg2 = "Please select Supplier Currency.";
        FacesMessage message2 = new FacesMessage(msg2);
        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(currIdBinding.getClientId(), message2);
        }
        } else {
        String msg2 = "Please select Supplier.";
        FacesMessage message2 = new FacesMessage(msg2);
        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(eoNameTransBind.getClientId(), message2);
        }
                    
        return null;
    }

    public void setExeclUploadPopup(RichPopup execlUploadPopup) {
        this.execlUploadPopup = execlUploadPopup;
    }

    public RichPopup getExeclUploadPopup() {
        return execlUploadPopup;
    }
    
    public void setItmProperty(String itmId){
        Row poItmRow = getAm().getMmDrftPoItm().getCurrentRow();
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        if(itmId!=null){
        ViewObjectImpl voItmN = getAm().getLovItmIdNew();
            voItmN.setNamedWhereClauseParam("CldIdBind", cld_id);
            voItmN.setNamedWhereClauseParam("SlocIdBind", sloc_id);
            voItmN.setNamedWhereClauseParam("HoOrgIdBind", hoOrg_id);
            voItmN.setNamedWhereClauseParam("OrgIdBind", OrgId);
            voItmN.setNamedWhereClauseParam("ItmIdBind", itmId);
            voItmN.executeQuery();
            Row []rr = voItmN.getFilteredRows("ItmId", itmId);
            if(rr.length>0){
                if(rr[0].getAttribute("UomBasic")!=null)
                    poItmRow.setAttribute("ItmUomBs", rr[0].getAttribute("UomBasic").toString());
                if(rr[0].getAttribute("UomClass")!=null)
                  poItmRow.setAttribute("TransUomClass", Integer.parseInt(rr[0].getAttribute("UomClass").toString()));
                if(rr[0].getAttribute("TaxExmptFlg")!=null)
                    poItmRow.setAttribute("TransTaxExmptFlg", rr[0].getAttribute("TaxExmptFlg").toString());
                
            }
        
        }
    }

    public void selectAllDlvrSchlQty(ActionEvent actionEvent) {
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        Row currDPO = getAm().getMmDrftPo().getCurrentRow();
      ViewObjectImpl dlvbalView = getAm().getDlvSchdlBalanceView();
      String docID = currDPO.getAttribute("DocId").toString();
      RowQualifier rqBal = new RowQualifier(dlvbalView);
      rqBal.setWhereClause("CldId = '"+cld_id+"' and OrgId = '"+OrgId+"' and SlocId = "+sloc_id+" and DocId = '"+docID+"' ");
      Row []rr = dlvbalView.getFilteredRows(rqBal);
      adfLog.info(rqBal.getExprStr()+"  length    "+rr.length);
      if(rr.length>0){
          for(Row r : rr){
              if(r.getAttribute("BalanceQty")!=null){
                  adfLog.info(r.getAttribute("BalanceQty")+" balqty  "+r.getAttribute("ItemId")+"       ItmId ");
                  r.setAttribute("SchdlQty", (Number)r.getAttribute("BalanceQty"));
              }
          }
      }
      
    }

    public void addContainerDetail(ActionEvent actionEvent) {
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        ViewObjectImpl poVo = getAm().getMmDrftPo();
        Row poR = poVo.getCurrentRow();
        
        if (poR.getAttribute("TransContnrNm") == null) {
            String msg2 = "Container Name is Required.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(contnrNameBind.getClientId(), message2);
            return ;
        }
        if(poR.getAttribute("TransContnrNm") != null){
           OperationBinding portDulpChk = getBindings().getOperationBinding("isContainerNameDuplicate");
            portDulpChk.getParamsMap().put("cntnrName", poR.getAttribute("TransContnrNm").toString());
             portDulpChk.execute(); 
             String dup ="N";
             if(portDulpChk.getResult()!=null){
                 adfLog.info(dup+"  dup   "+portDulpChk.getResult().toString());
                 dup=portDulpChk.getResult().toString();
             }
             
             if("Y".equalsIgnoreCase(dup)){
                 String msg2 = "Duplicate Cantainer Name .";
                 FacesMessage message2 = new FacesMessage(msg2);
                 message2.setSeverity(FacesMessage.SEVERITY_WARN);
                 FacesContext.getCurrentInstance().addMessage(null, message2);
                 return ;
             }
            
        }
        
        if(poR.getAttribute("TransContnrQty") == null){
            String msg2 = "Quantity is Required .";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(contnrQtyBind.getClientId(), message2);
            return ;
        }
        if(poR.getAttribute("TransContnrSize") == null){
            String msg2 = "Container size is Required .";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(contnrSizeBind.getClientId(), message2);
            return ;
        }
        
        if(poR.getAttribute("TransContnrQty") != null){
            Number qty = (Number)poR.getAttribute("TransContnrQty");
            if(qty.compareTo(zero) <= 0){
            
            String msg2 = "Quantity must be greater than zero  .";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(contnrQtyBind.getClientId(), message2);
            return ;
        }
        }     
        
        OperationBinding addContnrOp = getBindings().getOperationBinding("addContainerDetails");
         addContnrOp.execute();
    }

    public void addPortsDetails(ActionEvent actionEvent) {
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        ViewObjectImpl poVo = getAm().getMmDrftPo();
        Row poR = poVo.getCurrentRow();
     
        Integer trvlSeqNo =0;
        Integer tableCount = portDetailTableBind.getRowCount();
        trvlSeqNo =tableCount+1;
        adfLog.info(trvlSeqNo+"  trvlSeqNo befor count ::::: "+tableCount);
        if (poR.getAttribute("TransPortsNm") == null) {
            String msg2 = "Port Name is Required.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, message2);
            return ;
        }
        if(poR.getAttribute("TransPortsNm") != null){
           OperationBinding portDulpChk = getBindings().getOperationBinding("isPartNameDuplicate");
            portDulpChk.getParamsMap().put("porName", poR.getAttribute("TransPortsNm").toString());
             portDulpChk.execute(); 
             String dup ="N";
             if(portDulpChk.getResult()!=null){
                 adfLog.info(dup+"  dup   "+portDulpChk.getResult().toString());
                 dup=portDulpChk.getResult().toString();
             }
             
             if("Y".equalsIgnoreCase(dup)){
                 String msg2 = "Duplicate Port Name .";
                 FacesMessage message2 = new FacesMessage(msg2);
                 message2.setSeverity(FacesMessage.SEVERITY_WARN);
                 FacesContext.getCurrentInstance().addMessage(null, message2);
                 return ;
             }
            
        }
            
            
        if(tableCount==0){
            if(etaDateBind.getValue()!=null){
                String msg2 = "ETA date for first port must be blank.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(etaDateBind.getClientId(), message2);
                return ;
            }
            if(etdDateBind.getValue()==null){
                String msg2 = "ETD date for first port Required.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(etdDateBind.getClientId(), message2);
                return ;
            }
            if(etdDateBind.getValue()!=null){
                
                Date poDate = (Date)poR.getAttribute("PoDt");
                Date poDateBind = (Date)poDtBind.getValue();
                Date etdDate = (Date)etdDateBind.getValue();
                adfLog.info(poDate+" poDate "+poDateBind+"  poDateBind    "+etdDate+" etdDate  "+etdDate.compareTo(poDate)+"   etdDate.compareTo(poDateBind)   "+etdDate.compareTo(poDateBind));
                if(etdDate.compareTo(poDate) < 0 || etdDate.compareTo(poDateBind) < 0){
                    String msg2 = "ETD date must be greater than or equals PO date.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext.getCurrentInstance().addMessage(etdDateBind.getClientId(), message2);
                    return ;
                }
                
            }
        }
        if(tableCount > 0){
            
         
            Date lastEtdDate1 =null;
            ViewObjectImpl portvo1 = getAm().getMmDrftPoPorts();
            RowQualifier rq1 = new RowQualifier(portvo1);
            rq1.setWhereClause("CldId = '"+cld_id+"' and SlocId = "+sloc_id+" and OrgId = '"+OrgId+"' and DocId ='"+poR.getAttribute("DocId").toString()+"' and TrvlSeq = "+tableCount+" ");
            Row rr1[] = portvo1.getFilteredRows(rq1);
            adfLog.info(rq1.getExprStr()+"   filter port :::   "+rr1.length);
            if(rr1.length>0){
                lastEtdDate1= (Date)rr1[0].getAttribute("Etd");
            }
            if(lastEtdDate1==null){
                    String msg2 = "Last Travel Sequence ETD date is Destination .You cant add more port Details . ";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext.getCurrentInstance().addMessage(null, message2);
                    return ;
                }
            
            if(etaDateBind.getValue()==null){
                String msg2 = "ETA date is Required.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(etaDateBind.getClientId(), message2);
                return ;
            }
            
            
            if(etaDateBind.getValue()!=null){
                Date etaDate = (Date)etaDateBind.getValue();
                Date lastEtdDate =null;
                ViewObjectImpl portvo = getAm().getMmDrftPoPorts();
                RowQualifier rq = new RowQualifier(portvo);
                rq.setWhereClause("CldId = '"+cld_id+"' and SlocId = "+sloc_id+" and OrgId = '"+OrgId+"' and DocId ='"+poR.getAttribute("DocId").toString()+"' and TrvlSeq = "+tableCount+" ");
                Row rr[] = portvo.getFilteredRows(rq);
                adfLog.info(rq.getExprStr()+"   filter port :::   "+rr.length);
                if(rr.length>0){
                    lastEtdDate= (Date)rr[0].getAttribute("Etd");
                }
                if(lastEtdDate!=null){
                    adfLog.info(etaDate+"   etaDatet :::   "+lastEtdDate);
                    if(etaDate.compareTo(lastEtdDate) < 0){
                        String msg2 = "ETA date must be greater than or equals last Travel Sequence  ETD date.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_WARN);
                        FacesContext.getCurrentInstance().addMessage(etaDateBind.getClientId(), message2);
                        return ;
                    }
                }
            }
            
            
            if(etdDateBind.getValue()!=null && etaDateBind.getValue()!=null){
                Date poDate = (Date)poR.getAttribute("PoDt");
                Date poDateBind = (Date)poDtBind.getValue();
                Date etdDate = (Date)etdDateBind.getValue();
                Date etaDate = (Date)etaDateBind.getValue();
                adfLog.info(poDate+" poDate "+poDateBind+"  poDateBind    "+etdDate+" etdDate  "+etaDate+" etaDate  "+etdDate.compareTo(etaDate));
                if(etdDate.compareTo(etaDate) < 0){
                    String msg2 = "ETD date must be greater than or equals ETA date.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext.getCurrentInstance().addMessage(null, message2);
                    return ;
                }
            }
            
            
            
            
            
        }
        adfLog.info(trvlSeqNo+"  trvlSeqNo ::::: "+tableCount);
        addPortsDetails(trvlSeqNo);
        //OperationBinding addPortOp = getBindings().getOperationBinding("addPortsDetails");
       // addPortOp.getParamsMap().put("trvlSeqNo", trvlSeqNo);
       // addPortOp.execute();
        
       
        
        
        
    }
    
    public void addPortsDetails(Integer trvlSeqNo) {
        adfLog.info(trvlSeqNo+" trvlSeqNo:::::::::");
        ViewObjectImpl poVo = getAm().getMmDrftPo();
        Row poR = poVo.getCurrentRow();
        if (poR.getAttribute("TransPortsNm") != null) {
            ViewObjectImpl voport = getAm().getLovPortId();
            String portId = null;
            Row[] rr = voport.getFilteredRows("PortNm", poR.getAttribute("TransPortsNm").toString());
            if (rr.length > 0) {
                portId = rr[0].getAttribute("PortId").toString();
            }
            if (portId != null) {
                adfLog.info(" port no not null  :::::::::");
                BindingContainer bindings = getBindings();
                DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("MmDrftPoPortsIterator");
                //dciter.clearForRecreate();
                //access the underlying RowSetIterator
                RowSetIterator rsi = dciter.getRowSetIterator();
                // rsi.reset();
                //get handle to the last row
                Row lastRow = rsi.last();
                //obtain the index of the last row
                int lastRowIndex = rsi.getRangeIndexOf(lastRow);
                //create a new row
                Row newRow = rsi.createRow();
                //initialize the row
                newRow.setNewRowState(Row.STATUS_INITIALIZED);
                //add row to last index + 1 so it becomes last in the range set
                 
                rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
                newRow.setAttribute("PortId", portId);
                newRow.setAttribute("TrvlSeq", trvlSeqNo);
                newRow.setAttribute("Eta", poR.getAttribute("TransEtaDate"));
                newRow.setAttribute("Etd", poR.getAttribute("TransEtdDate"));
                newRow.setAttribute("Remarks", poR.getAttribute("TransPortRemarks"));
                //make row the current row so it is displayed correctly
                rsi.setCurrentRow(newRow);
                adfLog.info("ROw created and insert  :::::::::");
               /*  Row portsR = getAm().getMmDrftPoPorts().createRow();
                portsR.setAttribute("PortId", portId);
                portsR.setAttribute("TrvlSeq", trvlSeqNo);
                portsR.setAttribute("Eta", poR.getAttribute("TransEtaDate"));
                portsR.setAttribute("Etd", poR.getAttribute("TransEtdDate"));
                portsR.setAttribute("Remarks", poR.getAttribute("TransPortRemarks"));
                getAm().getMmDrftPoPorts().insertRow(portsR); */
            }
        }
        poR.setAttribute("TransPortsNm", null);
        poR.setAttribute("TransEtaDate", null);
        poR.setAttribute("TransEtdDate", null);
        poR.setAttribute("TransPortRemarks", null);
    }


    

    public void setPortsContnrTab(RichShowDetailItem portsContnrTab) {
        this.portsContnrTab = portsContnrTab;
    }

    public RichShowDetailItem getPortsContnrTab() {
        return portsContnrTab;
    }

    public void setShowPortAndContnrTab(String showPortAndContnrTab) {
        this.showPortAndContnrTab = showPortAndContnrTab;
    }

    public String getShowPortAndContnrTab() {
        return showPortAndContnrTab;
    }

    public void setTotalPoCastBeforeTax(RichInputText totalPoCastBeforeTax) {
        this.totalPoCastBeforeTax = totalPoCastBeforeTax;
    }

    public RichInputText getTotalPoCastBeforeTax() {
        return totalPoCastBeforeTax;
    }

    public void setPortDetailTableBind(RichTable portDetailTableBind) {
        this.portDetailTableBind = portDetailTableBind;
    }

    public RichTable getPortDetailTableBind() {
        return portDetailTableBind;
    }

    public void setEtaDateBind(RichInputDate etaDateBind) {
        this.etaDateBind = etaDateBind;
    }

    public RichInputDate getEtaDateBind() {
        return etaDateBind;
    }

    public void setEtdDateBind(RichInputDate etdDateBind) {
        this.etdDateBind = etdDateBind;
    }

    public RichInputDate getEtdDateBind() {
        return etdDateBind;
    }

    public void deletePortDetails(ActionEvent actionEvent) {
        Integer sequenceNo = 0;
        RichCommandImageLink ob = (RichCommandImageLink)actionEvent.getSource();
        ob.getAttributes().get("trvlSeqNO");
        sequenceNo = (Integer)ob.getAttributes().get("trvlSeqNO");
        Integer tableCount = portDetailTableBind.getRowCount();
        adfLog.info(sequenceNo+"  sequenceNo  "+tableCount);
        adfLog.info(" delete port no condition ::::: ");
        if(tableCount==sequenceNo){
            adfLog.info(" inside if condition ::::: ");
        OperationBinding operationBinding = getBindings().getOperationBinding("Delete3");
        operationBinding.execute();
        }else{
            adfLog.info(" inside else condition ::::: ");
            String msg2 = "Sequential Delete Allow . Fisrt delete last Travel Sequence  ::";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
            
        }
    }

    public void deleteContainerDetail(ActionEvent actionEvent) {
        OperationBinding operationBinding = getBindings().getOperationBinding("Delete2");
        operationBinding.execute();
    }

    public void setContnrNameBind(RichInputText contnrNameBind) {
        this.contnrNameBind = contnrNameBind;
    }

    public RichInputText getContnrNameBind() {
        return contnrNameBind;
    }

    public void setContnrSizeBind(RichInputText contnrSizeBind) {
        this.contnrSizeBind = contnrSizeBind;
    }

    public RichInputText getContnrSizeBind() {
        return contnrSizeBind;
    }

    public void setContnrQtyBind(RichInputText contnrQtyBind) {
        this.contnrQtyBind = contnrQtyBind;
    }

    public RichInputText getContnrQtyBind() {
        return contnrQtyBind;
    }
}
