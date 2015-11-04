package mmquotation.view.bean;

//import mmquotation.view.bean.ADFUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import mmquotation.model.service.QuotationAMImpl;
import mmquotation.model.views.MmQuotVORowImpl;

import oracle.adf.model.BindingContext;
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
import oracle.adf.view.rich.component.rich.layout.RichShowDetail;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
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
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class QuotationBean implements Serializable {
    private Integer taxRule = null;
    private Integer TexRuleId = 0;
    private Number taxableAmount = new Number(0);
    private Number itmtaxamt1 = new Number(0);
    private String taxExmpt = "N";
    private String taxruleflg = "I";
    private Integer taxRuleitem = null;
    private boolean form_readOnly = true;
    private boolean itmForm_readOnly = true;
    private RichSelectOneChoice refDOcIdBind;
    private String resetTaxType = "I";
    private RichShowDetail viewItmDtlBind;
    private RichPopup taxPopUp;
    private String mode = modeGet();
    private RichInputText ocAmtBsBind;
    private RichTable taxTrLineBind;
    private RichTable itmTableBind;
    private RichPopup discountPopup;
    private Integer texruleid = 0;
    private String taxexmpt = "N";
    private Number taxAbleAmoutForChange = new Number(0);
    private RichInputText quotQty;
    private RichInputText itmPrice;
    private RichSelectOneRadio discType;
    private RichInputText totalOnDiscQuotVal;
    private RichSelectOneRadio tlrnDiscType;
    private RichSelectOneRadio quotDiscType;
    private RichSelectOneRadio quotSourceBind;
    private RichInputText tlrncQty;
    private RichPopup ocPopUp;
    private String itmNameForLink = "";
    private RichTable tncTableBind;
    private RichPopup itmLinkSupPopUp;
    private static int INTEGER = Types.NUMERIC;
    private RichCommandImageLink otherIfnoTabBind;
    private RichShowDetailItem otherInformationTabBind;
    private RichSelectBooleanCheckbox itmTableCheckBoxBind;
    private RichInputText quotAmtSpBind;
    private RichInputText ocAmtSpBind;
    private RichInputText sumitemSpBind;
    private RichTable ocTableBind;
    private RichInputText sumItmTaxTotAmt;
    private String disableSelectTax = "M";
    private RichInputText itmBaseAmt;
    private RichShowDetailItem supplierTabBind;
    private String rfqDocidDisble;
    private RichInputListOfValues eoNameTransBind;
    private RichPanelGroupLayout sumgroupBind;
    private RichInputText eoIdDbBind;
    private RichPopup linkSupPopUp;
    //   private RichInputListOfValues inputRefDOcIdBind;
    private Integer Docid = 18503;
    private Integer DocTypeId = 0;
    private String wf_no = getWfId();
    private static final int VARCHAR = Types.VARCHAR;
    private RichInputListOfValues transRfqIdBindVar;
    private RichInputDate quotDateBind;
    private RichSelectOneChoice taxRuleQuotBinding;
    private RichSelectOneRadio taxRadioBinding;
    private RichPopup quotTaxPopupBind;
    private String tmpMode = "V";
    private RichSelectOneChoice taxRuleIdBind;


    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(QuotationBean.class);
    private RichPopup resetTaxItmQuotPopup;
    private RichShowDetailItem termsAndAggTab;
    private RichInputListOfValues coaNmBinding;
    private RichSelectOneChoice ocDescBinding;
    private RichInputText ocAmtSpBinding;
    private RichSelectBooleanCheckbox taxAftrDiscChk;
    private RichInputText getTaxAmtBind;
    private RichInputText taxAmtBind2;
    private RichCommandLink applyTaxBind;
    private RichCommandLink resetTaxBind;
    private RichInputText quotWiseDiscBind;
    private RichInputText discAmtValBind;
    private RichInputText ovrAllDiscBind;
    private RichInputText totalDiscBind;
    private RichSelectOneChoice stusQty;
    private RichInputText itmAmtSpBind;
    private RichInputText transItmAmtSp;
    private RichInputText totDiscAmtSpBind;
    private RichInputText discAmtSpBind;
    private RichInputText filePathBind;
    private List<UploadedFile> UploadedFile;
    private RichInputText fileBindName;
    private RichTable imgTableBind;
    private RichPopup histPopBind;

    private BigDecimal totStk;
    private BigDecimal resStk;
    private BigDecimal ordStk;
    private RichInputText convFactBind;
    private RichTable flxTableBind;

    public void setTotStk(BigDecimal totStk) {
        this.totStk = totStk;
    }

    public BigDecimal getTotStk() {
        return totStk;
    }

    public void setResStk(BigDecimal resStk) {
        this.resStk = resStk;
    }

    public BigDecimal getResStk() {
        return resStk;
    }

    public void setOrdStk(BigDecimal ordStk) {
        this.ordStk = ordStk;
    }

    public BigDecimal getOrdStk() {
        return ordStk;
    }

    public void setUploadedFile(List<UploadedFile> UploadedFile) {
        this.UploadedFile = UploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return UploadedFile;
    }

    public QuotationBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public QuotationAMImpl getAm() {
        return (QuotationAMImpl) resolvElDC("QuotationAMDataControl");
    }

    public String getWfId() {
        try {
            Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
            String p_org_id = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            return callStoredFunction(VARCHAR, "APP.WF_GET_ID(?,?,?,?,?)", new Object[] {
                                      p_sloc_id, CldID, p_org_id, Docid, DocTypeId
        }).toString();
        } catch (Exception e) {
            return "0";
        }
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

    public void create(ActionEvent actionEvent) {
        UploadedFile = null;
        String refDocId = null;
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
        String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        ViewObject quotvo = getAm().getMmQuot1();
        if (quotvo.getCurrentRow().getAttribute("RefDocId") != null) {
            adfLog.info("date : " + quotvo.getCurrentRow().getAttribute("QuotDt"));
            ViewObjectImpl rfqvo = getAm().getLovrfqId();
            rfqvo.setNamedWhereClauseParam("SlocIdBind", P_SLOCID);
            rfqvo.setNamedWhereClauseParam("CldidBind", CldID);
            rfqvo.setNamedWhereClauseParam("OrgIdBind", P_ORGID);
            rfqvo.setNamedWhereClauseParam("BindQuotDt", quotvo.getCurrentRow().getAttribute("QuotDt"));
            rfqvo.executeQuery();
            Row[] rr1 = rfqvo.getFilteredRows("DocId", refDocId);
            if (rr1.length > 0) {
                Integer i = Integer.parseInt(rr1[0].getAttribute("RfqStatus").toString());
                adfLog.info("total no. of rows found " + rr1.length + " " + i);
                if (i == 153) {
                    System.out.println("This RFQ already closed ");
                } else {
                    refDocId = quotvo.getCurrentRow().getAttribute("RefDocId").toString();
                }

            }

        }

        supplierTabBind.setDisclosed(true);
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "E");
        setMode("A");
        tmpMode = "C";
        //refDOcIdBind.setDisabled(false);
        setRfqDocidDisble("O");
        rfqDocidDisble = "O";
        if (refDocId != null) {
            quotvo.getCurrentRow().setAttribute("RefDocId", refDocId);
            String docIdNew = refDocId;
            System.out.println("docIdNew    ---" + docIdNew);
            ViewObject vo = getAm().getMmQuotItm();


            RowQualifier rfqf = new RowQualifier(getAm().getLovRfqItm1());
            rfqf.setWhereClause("DocId='" + docIdNew + "' and CldId='" + CldID + "' and SlocId=" + P_SLOCID +
                                " and OrgId='" + P_ORGID + "'");

            Row row[] = getAm().getLovRfqItm1().getFilteredRows(rfqf);


            for (Row r : row) {
                System.out.println("create row--------------");
                Row row1 = vo.createRow();
                row1.setAttribute("ItmId", r.getAttribute("ItmId"));
                row1.setAttribute("QuotQty", r.getAttribute("RfqQty"));
                row1.setAttribute("ItmUom", r.getAttribute("ItmUom"));
                vo.insertRow(row1);
                vo.executeQuery();

            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotSourceBind);

        }
    }


    public void editQuotation(ActionEvent actionEvent) {
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
        String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
        Row currRw = getAm().getMmQuot1().getCurrentRow();
        String P_DOCID = currRw.getAttribute("DocId").toString();

        Integer pending = Integer.parseInt(callStoredFunction(NUMBER, "APP.WF_GET_CUR_USR(?,?,?,?,?,?)", new Object[] {
                                                              P_SLOCID, CldID, P_ORGID, Docid, P_DOCID, DocTypeId
        }).toString());
        System.out.println("Pending at=" + pending);
        System.out.println("UsrId=" + p_user_id);
        if (pending != p_user_id) {
            String name = "Anonymous";
            Row r[] = getAm().getLovUsrId().getFilteredRows("UsrId", pending);
            System.out.println("No of rows=" + r.length);
            if (r.length > 0)
                name = (String) r[0].getAttribute("UsrName");
            FacesMessage message =
                new FacesMessage("This document is pending at [" + name + "] .You can not edit this.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else {
            String flag = (String) callStoredFunction(Types.VARCHAR, "MM.PKG_MM_QUOT.IS_QUOT_DELETABLE(?,?,?,?)", new Object[] {
                                                      P_SLOCID, CldID, P_ORGID, P_DOCID
            });
            if (flag.equalsIgnoreCase("N")) {
                // FacesMessage message = new FacesMessage("Quotation is posted. Can't  be edited.");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.391']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            } else {
                setMode("A");
                tmpMode = "E";
                //  refDOcIdBind.setDisabled(false);
                this.getViewItmDtlBind().setDisclosed(true);
                setRfqDocidDisble("O");
                rfqDocidDisble = "O";
            }
        }
    }

    public void rfqIdValueChange(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            this.getViewItmDtlBind().setDisclosed(true);
            QuotationAMImpl am = (QuotationAMImpl) resolvElDC("QuotationAMDataControl");
            ViewObjectImpl quot = am.getMmQuot1();
            Row quotRow = quot.getCurrentRow();
            String rfqIdnew = vce.getNewValue().toString();
            System.out.println("New rfq id=" + rfqIdnew);
            System.out.println("RowCount=" + am.getLovrfqId().getEstimatedRowCount());

            Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            //  RowQualifier rfqf=new RowQualifier( getAm().getLovRfqItm1());
            // rfqf.setWhereClause("DocId='"+docIdNew+"' and CldId='"+CldID+"' and SlocId="+P_SLOCID+" and OrgId='"+P_ORGID+"'");
            adfLog.info("date : " + quotRow.getAttribute("QuotDt"));
            ViewObjectImpl rfqvo = getAm().getLovrfqId();
            rfqvo.setNamedWhereClauseParam("SlocIdBind", P_SLOCID);
            rfqvo.setNamedWhereClauseParam("CldidBind", CldID);
            rfqvo.setNamedWhereClauseParam("OrgIdBind", P_ORGID);
            rfqvo.setNamedWhereClauseParam("BindQuotDt", quotRow.getAttribute("QuotDt"));
            rfqvo.executeQuery();

            RowQualifier rfq = new RowQualifier(getAm().getLovrfqId());
            rfq.setWhereClause("RfqId='" + rfqIdnew + "' and CldId='" + CldID + "' and SlocId=" + P_SLOCID +
                               " and OrgId='" + P_ORGID + "'");
            Row[] filteredRows = am.getLovrfqId().getFilteredRows(rfq);
            System.out.println("RowCount=" + am.getLovrfqId().getEstimatedRowCount());
            System.out.println("filteredRows.length   " + filteredRows.length);
            String docIdNew = null;
            if (filteredRows.length > 0) {
                docIdNew = filteredRows[0].getAttribute("DocId").toString();
            }
            // String rfqIdOld=vce.getOldValue().toString();
            System.out.println("docIdNew    ---" + docIdNew);
            ViewObject vo = am.getMmQuotItm();
            // ViewObjectImpl quot = am.getMmQuot1();
            ViewObjectImpl quotTnc = am.getMmQuotTnc();
            // Row quotRow = quot.getCurrentRow();
            if (vce.getNewValue() != null) {
                MmQuotVORowImpl v = (MmQuotVORowImpl) am.getMmQuot1().getCurrentRow();
                DCIteratorBinding mmquotItm = (DCIteratorBinding) getBindings().get("MmQuotItmIterator");
                RowSetIterator rsi = mmquotItm.getRowSetIterator();
                if (rsi.getAllRowsInRange().length > 0) {
                    for (Row rr : rsi.getAllRowsInRange()) {
                        rr.remove();
                    }
                }
                rsi.closeRowSetIterator();
            }

            if (quotRow.getAttribute("EoId") != null) {
                System.out.println("------------------------------q");
                quotRow.setAttribute("EoIdSwitcherLov_Trans", null);
                quotRow.setAttribute("CurrIdSp", null);
                quotRow.setAttribute("EoId", null);
                quotRow.setAttribute("CurrConvFctr", null);
                quotRow.setAttribute("BillAddsId", null);
            }

            RowQualifier rfqf = new RowQualifier(getAm().getLovRfqItm1());
            rfqf.setWhereClause("DocId='" + docIdNew + "' and CldId='" + CldID + "' and SlocId=" + P_SLOCID +
                                " and OrgId='" + P_ORGID + "'");
            Row row[] = am.getLovRfqItm1().getFilteredRows(rfqf);

            //  Integer P_SLOCID = (Integer)getAm().getMmQuot1().getCurrentRow().getAttribute("SlocId");
            //  String P_ORGID = (String)getAm().getMmQuot1().getCurrentRow().getAttribute("OrgId");
            // String CldID = (String)getAm().getMmQuot1().getCurrentRow().getAttribute("CldId");
            String hoOrg = (String) getAm().getMmQuot1().getCurrentRow().getAttribute("TransHoOrgId");
            for (Row r : row) {
                System.out.println("create row--------------");
                Row row1 = vo.createRow();
                row1.setAttribute("ItmId", r.getAttribute("ItmId"));
                row1.setAttribute("QuotQty", r.getAttribute("RfqQty"));
                row1.setAttribute("ItmUom", r.getAttribute("ItmUom"));
                //CODE TO SET BASE UOM AND UOMCONVFCTR
                String uombs = (String) r.getAttribute("ItmUom");
                Number convFctr = new Number(1);
                getAm().getLovItmIdForCode().setNamedWhereClauseParam("cldBind", CldID);
                getAm().getLovItmIdForCode().setNamedWhereClauseParam("slocBind", P_SLOCID);
                getAm().getLovItmIdForCode().setNamedWhereClauseParam("orgBind", P_ORGID);
                getAm().getLovItmIdForCode().setNamedWhereClauseParam("hoOrgBind", hoOrg);
                getAm().getLovItmIdForCode().setNamedWhereClauseParam("itmBind", r.getAttribute("ItmId"));
                getAm().getLovItmIdForCode().setNamedWhereClauseParam("itmDescBind", null);
                getAm().getLovItmIdForCode().executeQuery();
                RowQualifier rq = new RowQualifier(getAm().getLovItmIdForCode());
                rq.setWhereClause("CldId='" + CldID + "' and SlocId=" + P_SLOCID + " and OrgId='" + P_ORGID +
                                  "' and HoOrgId='" + hoOrg + "' and ItmId='" + r.getAttribute("ItmId") + "'");
                Row fr[] = getAm().getLovItmIdForCode().getFilteredRows(rq);
                if (fr.length > 0) {
                    if (fr[0].getAttribute("UomBasic") != null)
                        uombs = (String) fr[0].getAttribute("UomBasic");
                }
                row1.setAttribute("ItmUomBs", uombs);
                getAm().getLovUomVw1().setNamedWhereClauseParam("cldBind", CldID);
                getAm().getLovUomVw1().setNamedWhereClauseParam("slocBind", P_SLOCID);
                getAm().getLovUomVw1().setNamedWhereClauseParam("orgBind", P_ORGID);
                getAm().getLovUomVw1().setNamedWhereClauseParam("hoOrgBind", hoOrg);
                getAm().getLovUomVw1().setNamedWhereClauseParam("itmIdBind", r.getAttribute("ItmId"));
                getAm().getLovUomVw1().executeQuery();
                RowQualifier rquom = new RowQualifier(getAm().getLovUomVw1());
                rquom.setWhereClause("CldId='" + CldID + "' and SlocId=" + P_SLOCID + " and OrgId='" + P_ORGID +
                                     "' and HoOrgId='" + hoOrg + "' and ItmId='" + r.getAttribute("ItmId") +
                                     "' and UomIdSrc='" + uombs + "' and UomIdDest='" + r.getAttribute("ItmUom") + "'");
                Row ruom[] = getAm().getLovUomVw1().getFilteredRows(rquom);
                if (ruom.length > 0)
                    if (ruom[0].getAttribute("ConvFctr") != null)
                        convFctr = (Number) ruom[0].getAttribute("ConvFctr");
                row1.setAttribute("UomConvFctr", convFctr);
                vo.insertRow(row1);
                vo.executeQuery();

            }
            RowQualifier rfqtnc = new RowQualifier(getAm().getLovRfqTnc());
            rfqtnc.setWhereClause("DocId='" + docIdNew + "' and CldId='" + CldID + "' and SlocId=" + P_SLOCID +
                                  " and OrgId='" + P_ORGID + "'");
            Row rr1[] = am.getLovRfqTnc().getFilteredRows(rfqtnc);
            for (Row rr : rr1) {
                Row tnc = quotTnc.createRow();
                tnc.setAttribute("TncId", rr.getAttribute("TncId"));
                quotTnc.insertRow(tnc);
            }
            try{
                adfLog.info(CldID+"  cld id "+P_SLOCID+"  sloc id  "+P_ORGID+"  org "+quotRow.getAttribute("DocId")+"  doc id quot "+docIdNew+" doc Id rfq");
            Object retval =
                callStoredFunction(INTEGER, "MM.MM_INS_QUOT_FLX_FRM_RFQ(?,?,?,?,?)", new Object[] {
                                                    CldID, P_SLOCID, P_ORGID, quotRow.getAttribute("DocId").toString(),docIdNew 
            });
                adfLog.info("retval  "+retval);
                getAm().getMmQuotFlxVO1().executeQuery();
                adfLog.info(" end function ");
                
            }catch(Exception e){
                e.printStackTrace();
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(quotSourceBind);
            this.getViewItmDtlBind().setDisclosed(true);
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

    public void setForm_readOnly(boolean form_readOnly) {
        this.form_readOnly = form_readOnly;
    }

    public boolean isForm_readOnly() {
        return form_readOnly;
    }

    public void cancel(ActionEvent actionEvent) {

    }

    public String dateValidator(Timestamp curr, Timestamp Todt) {
        java.sql.Date strtDt = null;
        java.sql.Date endDt = null;
        try {
            strtDt = curr.dateValue();
            endDt = Todt.dateValue();

        } catch (SQLException e) {
            System.out.println("Error in cast date" + e);
            return "N";
        }

        if (strtDt.compareTo(endDt) > 0) {
            if (strtDt.toString().equals(endDt.toString())) {
                //ok
            } else {
                return "N";
            }
        }


        return null;
    }

    public void save(ActionEvent actionEvent) {
        UploadedFile = null;
        if (convFactBind.getValue() == null || convFactBind.getValue().equals(-1)) {
            String msg2 = "Conversion Factor is not define for this Supplier. \nCan not Proceed !!";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return;
        }

        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
        String p_org_id = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
        String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        QuotationAMImpl am = (QuotationAMImpl) resolvElDC("QuotationAMDataControl");
        ViewObjectImpl qout = am.getMmQuot1();
        ViewObject quotItm = am.getMmQuotItm();
        ViewObject quotTr = am.getMmQuotTr();
        // ViewObjectImpl quotView = am.getMmQuotVO1();
        Row currRow = qout.getCurrentRow();
        Row currItmRow = quotItm.getCurrentRow();


        Number taxableAmt = new Number(0);
        Row[] filterdRw = quotTr.getFilteredRows("DocId", currRow.getAttribute("DocId").toString());
        String doc_txn_id = currRow.getAttribute("DocId").toString();
        if (filterdRw.length > 0) {
            taxableAmt = (Number) (filterdRw[0].getAttribute("TaxableAmt"));
        }

        String taxruleFlg = "Q";
        String taxAfterDiscFlg = "Y";
        if (currRow.getAttribute("TaxRuleFlg") != null) {
            taxruleFlg = currRow.getAttribute("TaxRuleFlg").toString();
        }
        if (currRow.getAttribute("TaxAfterDiscFlg") != null) {
            taxAfterDiscFlg = currRow.getAttribute("TaxAfterDiscFlg").toString();
        }
        Number taxOnAmount = new Number(0);
        if (taxAfterDiscFlg.equalsIgnoreCase("Y")) {
            taxOnAmount = (Number) currRow.getAttribute("QuotAmtSp_Trans");
        } else if (taxAfterDiscFlg.equalsIgnoreCase("N")) {
            taxOnAmount = (Number) currRow.getAttribute("SumItmAmtSp_Trans");
        }

        /** check for all item and unit not null
         * */
        RowSetIterator rsi4 = quotItm.createRowSetIterator(null);

        Integer countForItmUnit = 0;
        while (rsi4.hasNext()) {
            Row rw1 = rsi4.next();
            if (rw1.getAttribute("ItmId") == null || rw1.getAttribute("ItmUom") == null) {
                countForItmUnit = countForItmUnit + 1;
            }
        }
        rsi4.closeRowSetIterator();
        adfLog.info("countForItmUnit  " + countForItmUnit);
        if (countForItmUnit > 0) {
            FacesMessage message = new FacesMessage("Item or unit is not selected . Please check and retry.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return;
        }

        /** check for all item price greater than zero
         * */
        RowSetIterator rsi1 = quotItm.createRowSetIterator(null);
        String itmName = "";
        String itmNameUom = "";
        int count = 0;
        int countUom = 0;
        Number zero = new Number(0);
        while (rsi1.hasNext()) {
            Row rw1 = rsi1.next();
            if (rw1.getAttribute("ItmPrice") != null) {
                Number price = (Number) rw1.getAttribute("ItmPrice");
                if (zero.compareTo(price) == 0) {
                    count = count + 1;
                    if (rw1.getAttribute("ItemId_Trans") != null) {
                        itmName = itmName + rw1.getAttribute("ItemId_Trans").toString() + ",";
                    }
                }
                if (rw1.getAttribute("ItmUom") == null) {
                    countUom = countUom + 1;
                    if (rw1.getAttribute("ItemId_Trans") != null) {
                        itmNameUom = itmNameUom + rw1.getAttribute("ItemId_Trans").toString() + ",";
                    }
                }
            }
        }
        rsi1.closeRowSetIterator();
        /** check for item wise taxable amount change or no
       * */
        RowSetIterator rsi = quotItm.createRowSetIterator(null);
        String appTax = "N";
        String itmNameConcat = "";
        while (rsi.hasNext()) {
            Row rw = rsi.next();
            if (rw.getAttribute("TransTaxChangedFlg") != null) {
                if ("Y".equalsIgnoreCase(rw.getAttribute("TransTaxChangedFlg").toString())) {
                    appTax = "Y";
                    if (rw.getAttribute("ItemId_Trans") != null) {
                        itmNameConcat = itmNameConcat + rw.getAttribute("ItemId_Trans").toString() + ",";
                    }
                }
            }
        }
        rsi.closeRowSetIterator();
        adfLog.info(" apply tax flag in save action    " + appTax);
        Integer sourceId = Integer.parseInt(currRow.getAttribute("QuotSource_trans").toString());
        if (sourceId == 158 && currRow.getAttribute("RefDocId") == null) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.392']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        Integer fyid = -1;
        if (currRow.getAttribute("QuotDt") != null) {
            Date dt = new Date(currRow.getAttribute("QuotDt").toString());
            System.out.println("Quot date=" + dt);
            String dtok = "N";
            if (currRow.getAttribute("RefDocId") != null) {
                adfLog.info("date : " + currRow.getAttribute("QuotDt"));
                ViewObjectImpl rfqvo = getAm().getLovrfqId();
                rfqvo.setNamedWhereClauseParam("SlocIdBind", p_sloc_id);
                rfqvo.setNamedWhereClauseParam("CldidBind", CldID);
                rfqvo.setNamedWhereClauseParam("OrgIdBind", p_org_id);
                rfqvo.setNamedWhereClauseParam("BindQuotDt", currRow.getAttribute("QuotDt"));
                rfqvo.executeQuery();
                Row[] r = getAm().getLovrfqId().getFilteredRows("DocId", currRow.getAttribute("RefDocId"));
                System.out.println("No. of rows=" + r.length);
                Date rfqdt = (Date) r[0].getAttribute("DocDt");
                System.out.println("RfqDate=" + rfqdt);
                java.sql.Date strtDt = null;
                java.sql.Date endDt = null;
                try {
                    strtDt = rfqdt.dateValue(); // rfq date
                    endDt = dt.dateValue(); //sqldate

                } catch (Exception e) {
                    System.out.println("Error in cast date" + e);
                }
                adfLog.info(rfqdt + "  rfqdt " + dt + "   dt  " + strtDt + " strtDt " + endDt);
                //  if(rfqdt.toString().equals(dt.toString()))
                if (strtDt.toString().equals(endDt.toString())) {
                    System.out.println("Both date are same");
                    dtok = "Y";
                } else {
                    System.out.println("both not same but");

                    if (rfqdt.compareTo(dt) <= 0)
                        dtok = "Y";
                    else
                        dtok = "N";
                    System.out.println("Compare=" + rfqdt.compareTo(dt));
                }
            } else
                dtok = "Y";
            if (dtok.equals("Y")) {


                System.out.println("Not null date");
                String OrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
                String CldId = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

                fyid = (Integer) (callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] {
                                                     CldId, OrgId, dt
                }));
                System.out.println("Fyid=" + fyid);
                if (fyid > 0) {
                    System.out.println("Fyid for save=" + fyid);
                    currRow.setAttribute("FyId", fyid);
                    Number amtSpCheck = (Number) currRow.getAttribute("QuotAmtSpAftrTax_Trans");
                    if (zero.compareTo(amtSpCheck) > 0) {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.393']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else if (currRow.getAttribute("EoId") != null) {
                        if (quotItm.getAllRowsInRange().length > 0) {
                            /*  if ("Q".equalsIgnoreCase(taxruleFlg) && taxableAmt.compareTo(taxOnAmount) != 0 && filterdRw.length > 0) {
                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.394']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
            }else */if ("Y".equalsIgnoreCase(appTax)) {

                                FacesMessage message =
                                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.292']}").toString() + " ( " +
                                                     itmNameConcat.substring(0, itmNameConcat.length() - 1) +
                                                     resolvElDCMsg("#{bundle['MSG.293']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);

                            } /*  else if(countUom > 0){
                FacesMessage message =
                    new FacesMessage(" ( "+itmNameUom.substring(0, itmNameUom.length()-1)+" ) Unit is required.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            } */ else if (count > 0) {

                                FacesMessage message =
                                    new FacesMessage(" ( " + itmName.substring(0, itmName.length() - 1) + " )" +
                                                     resolvElDCMsg("#{bundle['MSG.315']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);

                            } else {
                                //QuotAmtSp_Trans
                                Number amtBs = (Number) currRow.getAttribute("QuotAmtBs_Trans");
                                Number amtSp = (Number) currRow.getAttribute("QuotAmtSpAftrTax_Trans");
                                Number cstBfrTax = (Number) currRow.getAttribute("QuotAmtSp_Trans");
                                Number totalSpAmt = (Number) currRow.getAttribute("QuotAmtSp");
                                String quotId = currRow.getAttribute("QuotId").toString();
                                Boolean amtSpFlag = isPrecisionValid(26, 6, amtSp);
                                if (amtSpFlag.equals(false)) {
                                    FacesMessage message =
                                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.395']}").toString());
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                } else if (amtSp.compareTo(new Number(0)) <= 0 ||
                                           cstBfrTax.compareTo(new Number(0)) <= 0) {
                                    FacesMessage message =
                                        new FacesMessage("Amount for Quotation must be Greater than Zero.");
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                } else {

                                    ///  check for duplicate quotation
                                    Integer eoID = Integer.parseInt(currRow.getAttribute("EoId").toString());
                                    String quotID = (String) currRow.getAttribute("QuotId").toString();
                                    /* String dup = "false";
                    RowSetIterator rit = qout.createRowSetIterator(null);
                    while(rit.hasNext())
                    {
                    Row rw = rit.next();
                        if(rw != currRow){
                            if(eoID == rw.getAttribute("EoId") && quotID.equalsIgnoreCase(rw.getAttribute("QuotId").toString())){
                                dup = "true";
                                System.out.println("-----bharat----------");
                            }
                        }
                    }
                    rit.closeRowSetIterator(); */

                                    qout.setRangeSize(-1);
                                    qout.getAllRowsInRange();
                                    RowQualifier rowQualifier = new RowQualifier(qout);
                                    // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
                                    rowQualifier.setWhereClause(" EoId = " + eoID + " and QuotId = '" + quotID + "'");
                                    Row[] rows = qout.getFilteredRows(rowQualifier);
                                    System.out.println("-----------" + rowQualifier.getExprStr());
                                    System.out.println("rows.length    " + rows.length);
                                    // System.out.println("value of sup----"+dup);
                                    if (rows.length > 1) {
                                        FacesMessage message =
                                            new FacesMessage(resolvElDCMsg("#{bundle['MSG.46']}").toString());
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    } else {
                                        System.out.println("----------save---------");
                                        currRow.setAttribute("QuotAmtBs", amtBs);
                                        currRow.setAttribute("QuotAmtSp", amtSp);
                                        //  generate FY Id

                                        if (currRow.getAttribute("QuotDt") != null) {
                                            Integer fyId =
                                                (Integer) (callStoredFunction(Types.INTEGER,
                                                                              "APP.GET_ORG_FY_ID (?,?,?)", new Object[] {
                                                                              CldID, p_org_id,
                                                                              currRow.getAttribute("QuotDt")
                                            }));
                                            System.out.println(":: -FY RETURNED- ::" + fyId);
                                            if (mode.equalsIgnoreCase("A")) {
                                                // _log.info("Set FyId in case mode is add");

                                                System.out.println("RCpt Date" + currRow.getAttribute("QuotDt"));
                                                if (fyId > 0) {
                                                    currRow.setAttribute("FyId", fyId);
                                                }
                                            }
                                        }
                                        BindingContainer bindings4 = getBindings();
                                        DCIteratorBinding parentIter =
                                            (DCIteratorBinding) bindings4.get("MmQuot1Iterator");
                                        Key parentKey = parentIter.getCurrentRow().getKey();
                                        getAm().getMmQuotItm().executeQuery();
                                        BindingContainer bindings = getBindings();
                                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                        operationBinding.execute();
                                        supplierTabBind.setDisclosed(true);
                                        // refDOcIdBind.setDisabled(true);
                                        setRfqDocidDisble("P");


                                        Integer level = -1;
                                        if (wf_no != null) {
                                            String action = "I";
                                            String remark = "Initiate";
                                            level =
                                                Integer.parseInt(callStoredFunction(NUMBER,
                                                                                    "APP.WF_GET_USR_LEVEL(?,?,?,?,?,?,?)", new Object[] {
                                                                                    p_sloc_id, CldID, p_org_id,
                                                                                    p_user_id, wf_no, Docid, DocTypeId
                                            }).toString());

                                            if (level > -1) {
                                                Integer res =
                                                    Integer.parseInt(callStoredFunction(NUMBER,
                                                                                        "APP.WF_INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                                                        p_sloc_id, CldID, p_org_id,
                                                                                        Docid, DocTypeId, wf_no,
                                                                                        doc_txn_id, p_user_id,
                                                                                        p_user_id, level, level, action,
                                                                                        remark, amtBs
                                                }).toString());
                                                System.out.println("Res in Save=" + res);

                                                quotTr.executeQuery();

                                                BindingContainer bindingsS = getBindings();
                                                OperationBinding operationBindingS =
                                                    bindings.getOperationBinding("Commit");
                                                operationBindingS.execute();

                                                if (operationBindingS.getErrors().size() != 0)
                                                    System.out.println("Error on save=" +
                                                                       operationBindingS.getErrors());

                                                if (parentIter.getRowSetIterator().getRow(parentKey) != null) { //check condition else gives exception in add mode.
                                                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                                                }
                                                this.itmForm_readOnly = true;
                                                this.form_readOnly = true;
                                                setMode("V");
                                                tmpMode = "V";
                                                StringBuilder msg =
                                                    new StringBuilder("<html><body><p>" +
                                                                      resolvElDCMsg("#{bundle['LBL.784']}").toString() +
                                                                      " : <b>" + quotId + "</b>" +
                                                                      resolvElDCMsg("#{bundle['MSG.396']}").toString() +
                                                                      "</p>");
                                                msg.append("<p>" + resolvElDCMsg("#{bundle['MSG.299']}").toString() +
                                                           "<b>" + amtSp + "</b></p>");
                                                msg.append("<p>" + resolvElDCMsg("#{bundle['MSG.300']}").toString() +
                                                           "<b>" + amtBs + "</b></p>");
                                                msg.append("</body></html>");
                                                FacesMessage message2 = new FacesMessage(msg.toString());
                                                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                                                FacesContext fc = FacesContext.getCurrentInstance();
                                                fc.addMessage(null, message2);
                                            } else {
                                                FacesMessage message =
                                                    new FacesMessage("Something went wrong(User Level in Workflow).Contact to ESS!");
                                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                FacesContext fc = FacesContext.getCurrentInstance();
                                                fc.addMessage(null, message);
                                            }
                                        } else {
                                            FacesMessage message =
                                                new FacesMessage("Workflow for Quotation has not been Defined for current Organisation.");
                                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message);
                                        }
                                    }
                                    rowQualifier.setWhereClause(null);
                                    //  qout.executeQuery();
                                }
                            }
                        } else if (sourceId == 159) {
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.397']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.398']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                } else {
                    FacesMessage message = new FacesMessage("Quotation Date is not Valid.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            } else {
                FacesMessage message = new FacesMessage("Quotation Date must be on or after RFQ Date.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage("Quotation Date is Required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void setItmForm_readOnly(boolean itmForm_readOnly) {
        this.itmForm_readOnly = itmForm_readOnly;
    }

    public boolean isItmForm_readOnly() {
        Object mo = RequestContext.getCurrentInstance().getPageFlowScope().get("Add_Edit_Mode");
        return itmForm_readOnly;
    }

    public void editItm(ActionEvent actionEvent) {
        this.form_readOnly = true;
        this.itmForm_readOnly = false;
        setMode("A");

        this.getViewItmDtlBind().setDisclosed(true);
    }

    public void createItm(ActionEvent actionEvent) {
        if (convFactBind.getValue() == null || convFactBind.getValue().equals(-1)) {
            String msg2 = "Conversion Factor is not define for this Supplier. \nCan not Proceed !!";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return;
        }
        Integer tblCont = itmTableBind.getRowCount();
        adfLog.info("tblCont:" + tblCont);
        if (tblCont.compareTo(new Integer(0)) == 0 && flxTableBind.getRowCount() == 0) {
            System.out.println("call getFlexiFildMethod from Amimpl");
            OperationBinding op1 = getBindings().getOperationBinding("getFlexiFieldFilter");
            op1.execute();
        }
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        ViewObjectImpl quotItm = getAm().getMmQuotItm();
        Row quotItmRow = quotItm.getCurrentRow();
        quotItmRow.setAttribute("MarkedForDeleteItm", "N");
        itmTableCheckBoxBind.setValue("N");
        itmTableCheckBoxBind.setSelected(false);
        this.form_readOnly = true;
        this.itmForm_readOnly = false;
        setMode("A");
        this.getViewItmDtlBind().setDisclosed(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableCheckBoxBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);


    }

    public void quotSourceVCL(ValueChangeEvent vce) {
        Integer qSource = Integer.parseInt(vce.getNewValue().toString());
        if (qSource == 159) {
            setRfqDocidDisble("P");
            //  refDOcIdBind.setDisabled(true);
            ///  addItmButtonBind.setDisabled(false);
        } else if (qSource == 158) {
            setRfqDocidDisble("O");
            //refDOcIdBind.setDisabled(false);
            // addItmButtonBind.setDisabled(true);
            // }
        }
    }

    public void setRefDOcIdBind(RichSelectOneChoice refDOcIdBind) {
        this.refDOcIdBind = refDOcIdBind;
    }

    public RichSelectOneChoice getRefDOcIdBind() {
        return refDOcIdBind;
    }


    public void createTNC(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tncTableBind);
    }

    public void deleteTNC(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        operationBinding.execute();
    }

    public void setViewItmDtlBind(RichShowDetail viewItmDtlBind) {
        this.viewItmDtlBind = viewItmDtlBind;
    }

    public RichShowDetail getViewItmDtlBind() {
        return viewItmDtlBind;
    }

    public void addNewAdds(ActionEvent actionEvent) {
        otherInformationTabBind.setDisclosed(true);
    }

    public void setTaxPopUp(RichPopup taxPopUp) {
        this.taxPopUp = taxPopUp;
    }

    public RichPopup getTaxPopUp() {
        return taxPopUp;
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

    private static int NUMBER = Types.NUMERIC;

    public String modeGet() {
        return (String) resolveElExp("#{pageFlowScope.Add_Edit_Mode}");
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        if (mode == null || mode.equals("")) {
            return modeGet();
        } else {
            return mode;
        }
    }

    public void OcAmtSpVCL(ValueChangeEvent vCE) {
        Number AmSp = new Number(0);
        Number AmBs = new Number(0);
        Number currOcSp = (Number) vCE.getNewValue();
        if (currOcSp != null) {
            AmSp = currOcSp;
        }
        Number currency = (Number) getAm().getMmQuot1().getCurrentRow().getAttribute("CurrConvFctr");
        Number totSp = (Number) AmSp.multiply(currency).round(6); //2/11

        Row currRow = getAm().getMmQuotOc().getCurrentRow();
        currRow.setAttribute("OcAmtBs", totSp);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocAmtBsBind);
    }

    public void OtherChargesDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            ViewObject oc = getAm().getMmQuotOc();
            ViewObject quot = getAm().getMmQuot1();
            Number amt = new Number(0);
            Number ocamt = new Number(0);
            for (Row curr : oc.getAllRowsInRange()) {
                ocamt = (Number) curr.getAttribute("OcAmtSp");
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
            System.out.println("oc total amt-----a:" + amt);

            Number totalquotSpAmt = (Number) sumitemSpBind.getValue();
            System.out.println("totalquotSpAmt       " + totalquotSpAmt);
            Number ocAmtScore = amt.add(totalquotSpAmt);
            Number zero = new Number(0);
            if (zero.compareTo(ocAmtScore) > 0) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.302']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else {
                quot.getCurrentRow().setAttribute("SumOcAmt_Trans", amt);
            }
        }
    }

    public String addOCAction() {
        /* ViewObjectImpl v = getAm().getMmQuotOc();
        System.out.println("oc add");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert3");
        operationBinding.execute();
        if (operationBinding.getErrors().size() == 0) {
            ArrayList lst = new ArrayList(1);
            lst.add(v.getCurrentRow().getKey());
            getOcTableBind().setActiveRowKey(lst);
        }
         */
        Integer coaId = null;
        Integer ocDesc = null;
        String ocType = null;
        Number ocAmtSp = new Number(0);
        System.out.println("Coa Id=" + getAm().getMmQuot1().getCurrentRow().getAttribute("TransCoaId"));
        if (getAm().getMmQuot1().getCurrentRow().getAttribute("TransCoaId") != null)
            coaId = (Integer) getAm().getMmQuot1().getCurrentRow().getAttribute("TransCoaId");
        else {
            //Please select COA
            FacesMessage message = new FacesMessage("Please select COA Name.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(coaNmBinding.getClientId(), message);
            return null;
        }
        System.out.println("Coa Id is not null");
        if (getAm().getMmQuot1().getCurrentRow().getAttribute("TransOcDesc") != null)
            ocDesc = (Integer) getAm().getMmQuot1().getCurrentRow().getAttribute("TransOcDesc");
        else {
            //Please select Description
            FacesMessage message = new FacesMessage("Please select Other Charge Description.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(ocDescBinding.getClientId(), message);
            return null;
        }


        if (getAm().getMmQuot1().getCurrentRow().getAttribute("TrandOcType") != null)
            ocType = (String) getAm().getMmQuot1().getCurrentRow().getAttribute("TrandOcType");
        else {
            ocType = "A";
            /*  FacesMessage message =new FacesMessage("Please select Transaction Type.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);  */
            // return null;
        }

        if (getAm().getMmQuot1().getCurrentRow().getAttribute("TransOcAmtSp") != null) {
            if (((Number) getAm().getMmQuot1().getCurrentRow().getAttribute("TransOcAmtSp")).compareTo(new Number(0)) >
                0) {
                ocAmtSp = (Number) getAm().getMmQuot1().getCurrentRow().getAttribute("TransOcAmtSp");
            } else {
                FacesMessage message = new FacesMessage("Amount must be positive");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(ocAmtSpBinding.getClientId(), message);
                return null;
            }
        } else {
            //Please enter OC Amt
            FacesMessage message = new FacesMessage("Please Enter Other Charge Amount.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(ocAmtSpBinding.getClientId(), message);
            return null;
        }

        //Check for duplicate
        RowQualifier rq = new RowQualifier(getAm().getMmQuotOc());
        rq.setWhereClause("CldId='" + getAm().getMmQuot1().getCurrentRow().getAttribute("CldId") + "' and SlocId=" +
                          getAm().getMmQuot1().getCurrentRow().getAttribute("SlocId") + " and OrgId='" +
                          getAm().getMmQuot1().getCurrentRow().getAttribute("OrgId") + "' and DocId='" +
                          getAm().getMmQuot1().getCurrentRow().getAttribute("DocId") + "' and CoaId=" + coaId + "");
        Row fr[] = getAm().getMmQuotOc().getFilteredRows(rq);
        if (fr.length > 0) {
            //Duplicate COA found
            FacesMessage message = new FacesMessage("COA already exist.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(coaNmBinding.getClientId(), message);
            return null;
        }
        Number convfctr = new Number(1);
        if (getAm().getMmQuot1().getCurrentRow().getAttribute("CurrConvFctr") != null)
            convfctr = (Number) getAm().getMmQuot1().getCurrentRow().getAttribute("CurrConvFctr");
        Row r = getAm().getMmQuotOc().createRow();
        r.setAttribute("CldId", getAm().getMmQuot1().getCurrentRow().getAttribute("CldId"));
        r.setAttribute("SlocId", getAm().getMmQuot1().getCurrentRow().getAttribute("SlocId"));
        r.setAttribute("OrgId", getAm().getMmQuot1().getCurrentRow().getAttribute("OrgId"));
        r.setAttribute("DocId", getAm().getMmQuot1().getCurrentRow().getAttribute("DocId"));
        r.setAttribute("CoaId", coaId);
        r.setAttribute("OcDesc", ocDesc);
        r.setAttribute("TranType", ocType);
        r.setAttribute("OcAmtSp", ocAmtSp);
        r.setAttribute("OcAmtBs", (Number) ocAmtSp.multiply(convfctr).round(6));
        getAm().getMmQuotOc().insertRow(r);
        System.out.println("Row inserted");
        coaNmBinding.setValue(null);
        // ocDescBinding.setValue(null);
        ocAmtSpBinding.setValue(null);
        getAm().getMmQuot1().getCurrentRow().setAttribute("TransCoaNm", null);
        //getAm().getMmQuot1().getCurrentRow().setAttribute("TransOcDesc",null);
        getAm().getMmQuot1().getCurrentRow().setAttribute("TransCoaId", null);
        getAm().getMmQuot1().getCurrentRow().setAttribute("TransOcAmtSp", null);
        System.out.println("Values set to null");
        /*   ArrayList lst = new ArrayList(1);
        lst.add(r.getKey());
        getOcTableBind().setActiveRowKey(lst); */
        return null;
    }

    public void setOcAmtBsBind(RichInputText ocAmtBsBind) {
        this.ocAmtBsBind = ocAmtBsBind;
    }

    public RichInputText getOcAmtBsBind() {
        return ocAmtBsBind;
    }

    public void deleteOcCharges(ActionEvent actionEvent) {
        ViewObject oc = getAm().getMmQuotOc();
        Row currRw = oc.getCurrentRow();
        if (currRw != null) {
            currRw.remove();
        }
        oc.executeQuery();
    }

    public String taxItmAddAction() {

        System.out.println("add Tax for item");
        // ViewObjectImpl po = getAm().getMmQuot1();
        //  Row poRow = po.getCurrentRow();

        adfLog.info(taxAftrDiscChk.getValue() + " current tax exempted flag is ");
        ViewObject poVo = getAm().getMmQuotItm();
        Row itmCurr = poVo.getCurrentRow();
        if (itmCurr != null) {
            if (itmCurr.getAttribute("ItmPrice") != null || itmCurr.getAttribute("ItmPrice") != new Number(0) ||
                itmCurr.getAttribute("OrdQty") != null || itmCurr.getAttribute("OrdQty") != new Number(0)) {

                String flg = (String) itmCurr.getAttribute("TransTaxExmptFlg");
                if (flg.equals("N")) {
                    ViewObject vo = getAm().getMmQuot1();
                    String docId = (vo.getCurrentRow().getAttribute("DocId")).toString();
                    ViewObjectImpl trVo = getAm().getMmQuotTr1();
                    RowQualifier rqtr = new RowQualifier(trVo);
                    String itmId = itmCurr.getAttribute("ItmId").toString();
                    String itmuom = itmCurr.getAttribute("ItmUom").toString();
                    rqtr.setWhereClause("DocId='" + docId + "' AND ItmId='" + itmId + "' and ItmUom='" + itmuom + "'");
                    System.out.println("DocId=" + docId + " and  ItmId=" + itmId + "' and ItmUom='" + itmuom + "'");
                    Row[] r = trVo.getFilteredRows(rqtr);
                    System.out.println("No. of rows in tr of above ids=" + r.length);
                    String tflg = poVo.getCurrentRow().getAttribute("TransTaxChangedFlg").toString();
                    // System.out.println("Flag is="+tflg);

                    itmtaxamt1 = new Number(0);
                    taxRuleitem = null;
                    taxExmpt = "N";
                    taxruleflg = "I";


                    if (r.length > 0) {
                        itmtaxamt1 = new Number((Number) r[0].getAttribute("TaxableAmt"));
                        taxRuleitem = (Integer) r[0].getAttribute("TaxRuleId");
                        taxExmpt = r[0].getAttribute("TaxExmptFlg").toString();
                        taxruleflg = r[0].getAttribute("TaxRuleFlg").toString();
                        System.out.println("itmtaxamt1=" + itmtaxamt1 + " taxRuleitem=" + taxRuleitem);
                    }


                    if (r.length > 0 && tflg.equalsIgnoreCase("N")) {
                        showPopup(taxPopUp, true);
                    } else {

                        //remove tax if r.length>0 and tflg is "Y"
                        if (r.length > 0 && tflg.equalsIgnoreCase("Y")) {
                            ViewObjectImpl voline = getAm().getMmQuotTrLines2();

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
                        //      adfLog.info("current tax exempted flag is ");

                        OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters2");
                        operationBinding.execute();
                        //        adfLog.info("current tax exempted flag is ");
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

    /** For Tax Cancel if user not change tax.
     * */

    public void taxPopupCancelList(PopupCanceledEvent popupCanceledEvent) throws SQLException {
        /*
         if(taxruleid == 0)
         {
           ViewObjectImpl tr = getAm().getMmQuotTr();

               ViewObjectImpl trline = getAm().getMmQuotTrLines();
                 RowSetIterator rti = trline.createRowSetIterator(null);
                 while(rti.hasNext())
                 {
                    Row rwtrline = rti.next();
                     rwtrline.remove();
             }
               tr.getCurrentRow().remove();
             }

         else if(taxruleid != 0)
         {

                 Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
                 Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
                 String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
                  String CldID = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                  String p_ho_org_id = (String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");

                 ViewObjectImpl tr = getAm().getMmQuotTr();
                 ViewObjectImpl quot = getAm().getMmQuot1();
                 Row quotRow = quot.getCurrentRow();
                 Row trRow = tr.getCurrentRow();
                 String p_doc_id = trRow.getAttribute("DocId").toString();
                 String p_quot_item_id = null;
                if(trRow.getAttribute("ItmId")!=null){
                     p_quot_item_id = trRow.getAttribute("ItmId").toString();
                 }
                String p_quot_item_uom=null;
                  if(trRow.getAttribute("ItmUom")!=null){
                       p_quot_item_uom = trRow.getAttribute("ItmUom").toString();
                   }
                  Number p_curr_Fact = new Number(1);
                  if(quotRow.getAttribute("CurrConvFctr")!=null){
                      p_curr_Fact = (Number)quotRow.getAttribute("CurrConvFctr");
                  }

                 //Number p_taxable_amount =(Number)trRow.getAttribute("TaxableAmt");
                 Number p_taxable_amount = taxAbleAmoutForChange;    // change for cancel tax 16/04
                 String p_tax_rule_flg =trRow.getAttribute("TaxRuleFlg").toString();
           //      System.out.println("sloc="+p_sloc_id+" cld="+ CldID+" hoOrg="+ p_ho_org_id+" org="+p_org_id+" docid="+p_doc_id+" item="+p_quot_item_id+" uom="+p_quot_item_uom+" rule="+taxruleid+" usr="+p_user_id+" taxableamt="+p_taxable_amount+" taxruleflg="+p_tax_rule_flg+" currconvfctr="+p_curr_Fact+" taxexmpt="+taxexmpt);
                 BigDecimal taxAmt=(BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_QUOT.ins_quot_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{p_sloc_id, CldID , p_ho_org_id , p_org_id,p_doc_id,p_quot_item_id,p_quot_item_uom,taxruleid,p_user_id,p_taxable_amount,p_tax_rule_flg,p_curr_Fact,taxexmpt});
                 Number ret=new Number(taxAmt);
                 trRow.setAttribute("TaxAmt", ret);
                 trRow.setAttribute("TaxRuleId", taxruleid);
                 trRow.setAttribute("TaxableAmt", taxAbleAmoutForChange);
                 trRow.setAttribute("TaxExmptFlg", taxexmpt);
                 ViewObjectImpl trLine = getAm().getMmQuotTrLines();
                 trLine.executeQuery();
              }
         taxruleid = 0;
         taxAbleAmoutForChange = new Number(0);
         AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
         setDisableSelectTax("M"); */


        //if no tax was applied than delete current applied tax here.. and do not create new. else inset into tr rule line function will delete those rows itself.
        if (taxRuleitem == null) {
            //  System.out.println("No tax was applied before");
            ViewObjectImpl tr = getAm().getMmQuotTr1();
            String docId = tr.getCurrentRow().getAttribute("DocId").toString();
            ViewObjectImpl trline = getAm().getMmQuotTrLines1();
            ViewObjectImpl poitm = getAm().getMmQuotItm();

            String itm = null;
            String itmuom = null;

            itm = (String) poitm.getCurrentRow().getAttribute("ItmId");
            itmuom = (String) poitm.getCurrentRow().getAttribute("ItmUom");
            trline.executeQuery();
            getAm().getMmQuotTrLines().executeQuery();
            getAm().getMmQuotTrLines2().executeQuery();


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
            getAm().getMmQuotTr1().executeQuery();

        }
        TexRuleId = 0;
        taxableAmount = new Number(0);
        Integer taxid1 = null;
        Number itmtaxamt = null;
        String taxflg = "N";
        String taxexmpt = "N";


        //create tax rule as itemise
        if (taxRuleitem != null) {
            //     System.out.println("TaxRuleItem="+taxRuleitem);
            //click on Select tax Itemwise
            ViewObjectImpl po2 = getAm().getMmQuot1();
            ViewObject poVo2 = getAm().getMmQuotItm();
            Row itmCurr2 = poVo2.getCurrentRow();
            ViewObjectImpl trVo2 = getAm().getMmQuotTr1();
            String itmIds = itmCurr2.getAttribute("ItmId").toString();
            if (itmCurr2 != null) {
                if (itmCurr2.getAttribute("ItmPrice") != null || itmCurr2.getAttribute("ItmPrice") != new Number(0) ||
                    itmCurr2.getAttribute("OrdQty") != null || itmCurr2.getAttribute("OrdQty") != new Number(0)) {
                    ViewObject vo2 = getAm().getMmQuot1();
                    String docId2 = (vo2.getCurrentRow().getAttribute("DocId")).toString();
                    RowQualifier rqtr2 = new RowQualifier(trVo2);
                    ViewObject trlineVo = getAm().getMmQuotTrLines1();
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
                        getAm().getMmQuotTrLines().executeQuery();
                        getAm().getMmQuotTrLines2().executeQuery();
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
                        getAm().getMmQuotTr().executeQuery();
                    }


                    //    System.out.println("Now row in TR for this item and docid is not avaliabale");
                    OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters2");
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
            Row trcurr1 = getAm().getMmQuotTr1().getCurrentRow();
            trcurr1.setAttribute("TaxRuleId", taxid1);
            trcurr1.setAttribute("TaxRuleFlg", taxruleflg);
            Integer p_sloc_id1 = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            Integer p_user_id1 = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
            String p_org_id1 = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String p_hoOrgId1 = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId1 = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();


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
            if (getAm().getMmQuot1().getCurrentRow().getAttribute("CurrConvFctr") != null) {
                p_curr_fctr1 = (Number) getAm().getMmQuot1().getCurrentRow().getAttribute("CurrConvFctr");
            }

            //code for check duplicate record
            ViewObjectImpl lineimpl = getAm().getMmQuotTrLines1();
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
            /*  BigDecimal ret1 =
       (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                      new Object[] { p_sloc_id1,p_cldId1,p_hoOrgId1, p_org_id1, p_doc_id1, p_po_item_id1,p_po_item_uom1,taxid1,
                                                     p_user_id1, p_taxable_amount1, p_tax_rule_flg1,
                                                     p_curr_fctr1,taxexmpt }); */
            //            BigDecimal ret1 =
            //                (BigDecimal) callStoredFunction(NUMBER, "MM.PKG_MM_QUOT.ins_quot_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
            //                                                p_sloc_id1, p_cldId1, p_hoOrgId1, p_org_id1, p_doc_id1, p_po_item_id1,
            //                                                p_po_item_uom1, taxid1, p_user_id1, p_taxable_amount1, p_tax_rule_flg1,
            //                                                p_curr_fctr1, taxexmpt
            //            });

            BigDecimal ret1 =
                (BigDecimal) callStoredFunction(NUMBER, "MM.MM_INS_QUOT_TR_LINES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                p_sloc_id1, p_cldId1, p_hoOrgId1, p_org_id1, p_doc_id1, p_po_item_id1,
                                                p_po_item_uom1, taxid1, p_user_id1, p_taxable_amount1, p_tax_rule_flg1,
                                                p_curr_fctr1, taxexmpt, "N"
            });


            System.out.println("tax amt=" + ret1);
            Number retVal1 = new Number(ret1);
            Number zero = new Number(0);
            trcurr1.setAttribute("TaxExmptFlg", taxflg);
            if (taxflg.equals("Y")) {
                trcurr1.setAttribute("TaxAmt", zero);
                getAm().getMmQuot1().getCurrentRow().setAttribute("SumTaxAmtItm_Trans", zero);
            } else {
                trcurr1.setAttribute("TaxAmt", new Number(retVal1.round(6)));
                if (p_tax_rule_flg1.equalsIgnoreCase("I")) {
                    getAm().getMmQuotItm().getCurrentRow().setAttribute("TaxAmtItm_Trans",
                                                                        new Number(retVal1.round(6)));
                } else if (p_tax_rule_flg1.equalsIgnoreCase("Q")) {
                    getAm().getMmQuot1().getCurrentRow().setAttribute("SumTaxAmtItm_Trans",
                                                                      new Number(retVal1.round(6)));
                }
            }

        }

        // getAm().getMmQuot1().executeQuery();
        getAm().getMmQuotTr1().executeQuery();
        getAm().getMmQuotTrLines2().executeQuery();
        getAm().getMmQuotTr().executeQuery();
        getAm().getMmQuotTrLines().executeQuery();
        System.out.println("Exit from cancel");
        //-------------


        //  AdfFacesContext.getCurrentInstance().addPartialTarget(poTaxAmtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBind);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(transTotalPoCostBsBind);

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

    public String addTaxQuotAction() {
        /* ViewObjectImpl quot = getAm().getMmQuot1();
        ViewObjectImpl quotItm = getAm().getMmQuotItm();
        Row quotItmRow = quotItm.getCurrentRow();
        Row quotRow = quot.getCurrentRow();
        Number zero= new Number(0);
        Number price = (Number)quotItmRow.getAttribute("ItmPrice");
        Number qty = (Number)quotItmRow.getAttribute("QuotQty");
        if(quotRow.getAttribute("EoId") == null){
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.398']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }else if(zero.compareTo(price) == 0){
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.399']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }else if(zero.compareTo(qty) == 0){
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.400']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        }else{



     //   String TaxAfterDiscFlg = quotRow.getAttribute("TaxAfterDiscFlg").toString();
            Boolean isExistQuot=false;
            Boolean isExistItm=false;
        String docId =(String)quotRow.getAttribute("DocId");
        ViewObjectImpl trTemp = getAm().getMmQuotTr();
            Integer ruleid=null;
        RowQualifier rqQuot=new RowQualifier(trTemp);
            rqQuot.setWhereClause("DocId='"+docId+"' and TaxRuleFlg='Q'");
           Row[] rquot= trTemp.getFilteredRows(rqQuot);
            if(rquot.length>0)
            {
              isExistQuot=true;
              ruleid=Integer.parseInt(rquot[0].getAttribute("TaxRuleId").toString());
                }

        RowQualifier rqItm=new RowQualifier(trTemp);
            rqItm.setWhereClause("DocId='"+docId+"' and TaxRuleFlg='I'");
           Row[] ritm= trTemp.getFilteredRows(rqItm);
            if(ritm.length>0)
              isExistItm=true;


             if(isExistQuot==true)
             {
                 quotRow.setAttribute("TransTaxRuldId", ruleid);
                 if(isExistItm==true)
                     quotRow.setAttribute("TransRadioFlg", "Exclude");
                 else
                     quotRow.setAttribute("TransRadioFlg","All");
             }
            else
             {
                 quotRow.setAttribute("TransTaxRuldId", null);
                // taxRuleQuotBinding.setValue();
                     if(isExistItm==true)
                         quotRow.setAttribute("TransRadioFlg","Exclude");
                     else
                         quotRow.setAttribute("TransRadioFlg","All");
             }
        setDisableSelectTax("N");
        showPopup(quotTaxPopupBind, true);
    } */

        //  System.out.println("Add tax for po");
        ViewObject vo = getAm().getMmQuot1();
        String docId = (vo.getCurrentRow().getAttribute("DocId")).toString();
        ViewObjectImpl trVo = getAm().getMmQuotTr();
        RowQualifier rqtr = new RowQualifier(trVo);
        rqtr.setWhereClause("DocId='" + docId + "' and TaxRuleFlg='Q'");
        ViewObject trlinevo = getAm().getMmQuotTrLines();
        Row[] r = trVo.getFilteredRows(rqtr);
        ViewObjectImpl itmvo = getAm().getMmQuotItm();
        String transflg = itmvo.getCurrentRow().getAttribute("TransTaxChangedFlg").toString();
        vo.getCurrentRow().setAttribute("TransRadioFlg", "All");
        taxRule = null;
        //check flag of yellow
        if (r.length > 0) {
            taxRule = (Integer) r[0].getAttribute("TaxRuleId");
            vo.getCurrentRow().setAttribute("TransTaxRuldId", taxRule);

            RowQualifier radiotr = new RowQualifier(trVo);
            radiotr.setWhereClause("DocId='" + docId + "' and TaxRuleFlg='I'");
            Row[] radiorw = trVo.getFilteredRows(radiotr);
            if (radiorw.length > 0)
                vo.getCurrentRow().setAttribute("TransRadioFlg", "Exclude");
            else
                vo.getCurrentRow().setAttribute("TransRadioFlg", "All");
        }

        if (r.length > 0 && transflg.equalsIgnoreCase("N")) {
            taxRule = (Integer) r[0].getAttribute("TaxRuleId");
            showPopup(quotTaxPopupBind, true);
        } else {
            //Remove all rows from tr
            RowSetIterator rsi = trVo.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row rw = rsi.next();
                if (rw.getAttribute("DocId").toString().equalsIgnoreCase(docId) &&
                    rw.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("Q")) {
                }
            }
            rsi.closeRowSetIterator();
            trVo.executeQuery();
            showPopup(quotTaxPopupBind, true);
        }
        return null;
    }

    public void taxRuleIdVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            Integer taxid = Integer.parseInt(vce.getNewValue().toString());

            Row trcurr = getAm().getMmQuotTr1().getCurrentRow();
            Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
            String p_org_id = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            trcurr.setAttribute("CldId", p_cldId);
            trcurr.setAttribute("SlocId", p_sloc_id);
            trcurr.setAttribute("OrgId", p_org_id);
            trcurr.setAttribute("DocId", getAm().getMmQuot1().getCurrentRow().getAttribute("DocId"));
            trcurr.setAttribute("ItmId", getAm().getMmQuotItm().getCurrentRow().getAttribute("ItmId"));
            trcurr.setAttribute("ItmUom", getAm().getMmQuotItm().getCurrentRow().getAttribute("ItmUom"));
            System.out.println("Setting attribute TaxRuleId on VCL=" + taxid);
            trcurr.setAttribute("TaxRuleId", taxid);
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
            Number p_taxable_amount = (Number) trcurr.getAttribute("TaxableAmt");
            trcurr.setAttribute("TaxRuleFlg", "I");
            if (getAm().getMmQuot1().getCurrentRow().getAttribute("CurrConvFctr") != null) {
                p_curr_fctr = (Number) getAm().getMmQuot1().getCurrentRow().getAttribute("CurrConvFctr");
            }
            System.out.println("Tax rule flg=I");
            /*  BigDecimal ret =
               (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                              new Object[] { p_sloc_id,p_cldId,p_hoOrgId, p_org_id, p_doc_id, p_po_item_id,p_po_item_uom,taxid,
                                                             p_user_id, p_taxable_amount, "I",
                                                             p_curr_fctr,taxexm }); */
            //            BigDecimal ret =
            //                (BigDecimal) callStoredFunction(NUMBER, "MM.PKG_MM_QUOT.ins_quot_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
            //                                                p_sloc_id, p_cldId, p_hoOrgId, p_org_id, p_doc_id, p_po_item_id,
            //                                                p_po_item_uom, taxid, p_user_id, p_taxable_amount, "I", p_curr_fctr,
            //                                                taxexm
            //            });

            BigDecimal ret =
                (BigDecimal) callStoredFunction(NUMBER, "MM.MM_INS_QUOT_TR_LINES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                p_sloc_id, p_cldId, p_hoOrgId, p_org_id, p_doc_id, p_po_item_id,
                                                p_po_item_uom, taxid, p_user_id, p_taxable_amount, "I", p_curr_fctr,
                                                taxexm, "N"
            });

            Number retVal = new Number(0);
            try {
                retVal = new Number(ret);
            } catch (SQLException e) {
                System.out.println("Error while convert BigDecimal to number");
            }
            String flg = "N";
            Number zero = new Number(0);
            System.out.println("Key of TR1 1545=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());

            getAm().getMmQuotTrLines1().executeQuery();
            System.out.println("Key of TR1 1548=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());

            getAm().getMmQuotTrLines2().executeQuery();
            System.out.println("Key of TR1 1551=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());

            if (trcurr.getAttribute("TaxAmt") != null)
                flg = trcurr.getAttribute("TaxAmt").toString();
            if (flg.equals("Y")) {
                trcurr.setAttribute("TaxAmt", zero);
                getAm().getMmQuot1().getCurrentRow().setAttribute("QuotTaxAmt_Trans", zero);
            } else {
                trcurr.setAttribute("TaxAmt", retVal);
                getAm().getMmQuotItm().getCurrentRow().setAttribute("TaxAmtItm_Trans", retVal);
            }

            System.out.println("Key of TR1 1566=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());
            // this.getAm().getMmQuotTr().executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTrLineBind);
            System.out.println("Key of TR1 1569=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());
            trcurr.setAttribute("TaxRuleId", taxid);
            System.out.println("Key of TR1 1571=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());

        }

    }


    public void taxRuleIdNewVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
            String p_org_id = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            String taxid1 = vce.getNewValue().toString();


            Integer taxid = null;
            ViewObjectImpl votax = getAm().getLovTaxRuleId();
            RowQualifier rqtax = new RowQualifier(votax);
            rqtax.setWhereClause("TaxHoOrgId ='" + p_hoOrgId + "' and TaxRuleSlocId = " + p_sloc_id +
                                 " and TaxCldId ='" + p_cldId + "' and TaxRuleDesc = '" + taxid1 + "' ");
            Row[] rr = getAm().getLovTaxRuleId().getFilteredRows(rqtax);
            adfLog.info(rqtax.getExprStr() + " row lenth  " + rr.length);
            if (rr.length > 0) {
                taxid = Integer.parseInt(rr[0].getAttribute("TaxRuleId").toString());
            }
            adfLog.info("tax rul id");

            if (taxid != null) {
                Row trcurr = getAm().getMmQuotTr1().getCurrentRow();

                trcurr.setAttribute("CldId", p_cldId);
                trcurr.setAttribute("SlocId", p_sloc_id);
                trcurr.setAttribute("OrgId", p_org_id);
                trcurr.setAttribute("DocId", getAm().getMmQuot1().getCurrentRow().getAttribute("DocId"));
                trcurr.setAttribute("ItmId", getAm().getMmQuotItm().getCurrentRow().getAttribute("ItmId"));
                trcurr.setAttribute("ItmUom", getAm().getMmQuotItm().getCurrentRow().getAttribute("ItmUom"));
                System.out.println("Setting attribute TaxRuleId on VCL=" + taxid);
                trcurr.setAttribute("TaxRuleId", taxid);
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
                Number p_taxable_amount = (Number) trcurr.getAttribute("TaxableAmt");
                trcurr.setAttribute("TaxRuleFlg", "I");
                if (getAm().getMmQuot1().getCurrentRow().getAttribute("CurrConvFctr") != null) {
                    p_curr_fctr = (Number) getAm().getMmQuot1().getCurrentRow().getAttribute("CurrConvFctr");
                }
                adfLog.info("Tax rule flg=I");
                /*  BigDecimal ret =
                (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                               new Object[] { p_sloc_id,p_cldId,p_hoOrgId, p_org_id, p_doc_id, p_po_item_id,p_po_item_uom,taxid,
                                                              p_user_id, p_taxable_amount, "I",
                                                              p_curr_fctr,taxexm }); */
                //                BigDecimal ret =
                //                    (BigDecimal) callStoredFunction(NUMBER,
                //                                                    "MM.PKG_MM_QUOT.ins_quot_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                //                                                    p_sloc_id, p_cldId, p_hoOrgId, p_org_id, p_doc_id, p_po_item_id,
                //                                                    p_po_item_uom, taxid, p_user_id, p_taxable_amount, "I", p_curr_fctr,
                //                                                    taxexm
                //                });

                BigDecimal ret =
                    (BigDecimal) callStoredFunction(NUMBER, "MM.MM_INS_QUOT_TR_LINES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                    p_sloc_id, p_cldId, p_hoOrgId, p_org_id, p_doc_id, p_po_item_id,
                                                    p_po_item_uom, taxid, p_user_id, p_taxable_amount, "I", p_curr_fctr,
                                                    taxexm, "N"
                });

                Number retVal = new Number(0);
                try {
                    retVal = new Number(ret);
                } catch (SQLException e) {
                    adfLog.info("Error while convert BigDecimal to number");
                }
                String flg = "N";
                Number zero = new Number(0);
                adfLog.info("Key of TR1 1545=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());

                getAm().getMmQuotTrLines1().executeQuery();
                adfLog.info("Key of TR1 1548=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());

                getAm().getMmQuotTrLines2().executeQuery();
                adfLog.info("Key of TR1 1551=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());

                if (trcurr.getAttribute("TaxAmt") != null)
                    flg = trcurr.getAttribute("TaxAmt").toString();
                if (flg.equals("Y")) {
                    trcurr.setAttribute("TaxAmt", zero);
                    getAm().getMmQuot1().getCurrentRow().setAttribute("QuotTaxAmt_Trans", zero);
                } else {
                    adfLog.info("taxable amt value is " + retVal + " after round off " + retVal.round(6));
                    trcurr.setAttribute("TaxAmt", new Number(retVal.round(6)));
                    getAm().getMmQuotItm().getCurrentRow().setAttribute("TaxAmtItm_Trans", new Number(retVal.round(6)));
                }

                adfLog.info("Key of TR1 1566=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());
                // this.getAm().getMmQuotTr().executeQuery();
                AdfFacesContext.getCurrentInstance().addPartialTarget(taxTrLineBind);
                adfLog.info("Key of TR1 1569=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());
                trcurr.setAttribute("TaxRuleId", taxid);
                adfLog.info("Key of TR1 1571=" + this.getAm().getMmQuotTr1().getCurrentRow().getKey());

            }
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

    public void setTaxTrLineBind(RichTable taxTrLineBind) {
        this.taxTrLineBind = taxTrLineBind;
    }

    public RichTable getTaxTrLineBind() {
        return taxTrLineBind;
    }

    public void taxDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            if (getAm().getMmQuotTrLines2().getCurrentRow() != null) {
                if (getAm().getMmQuotTr1().getCurrentRow() != null) {
                    getAm().getMmQuotTr1().getCurrentRow().setAttribute("TaxRuleId",
                                                                        getAm().getMmQuotTrLines2().getCurrentRow().getAttribute("TaxRuleId"));
                }
            }

            System.out.println("Key of TR1 in ok=" + getAm().getMmQuotTr1().getCurrentRow().getKey());
            //  getAm().getMmQuotTr().executeQuery();
            //  setDisableSelectTax("M");
            // AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);


        }
    }

    public void setItmTableBind(RichTable itmTableBind) {
        this.itmTableBind = itmTableBind;
    }

    public RichTable getItmTableBind() {
        return itmTableBind;
    }

    public void setDiscountPopup(RichPopup discountPopup) {
        this.discountPopup = discountPopup;
    }

    public RichPopup getDiscountPopup() {
        return discountPopup;
    }

    public String quotDiscount() {

        showPopup(discountPopup, true);
        return null;
    }

    public void discountDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            quotQty.processUpdates(FacesContext.getCurrentInstance());
            //itmPrice.processUpdates(FacesContext.getCurrentInstance());
            itmAmtSpBind.processUpdates(FacesContext.getCurrentInstance());
            transItmAmtSp.processUpdates(FacesContext.getCurrentInstance());
            totDiscAmtSpBind.processUpdates(FacesContext.getCurrentInstance());
            OperationBinding obind = getBindings().getOperationBinding("discountoverAll");
            obind.execute();
            /** code need to be add through binding and code transfer to amimpl*/
            /*
               Number sumItmDisc = zero;
              if (currPo.getAttribute("SumDiscAmt_Trans") != null)
                sumItmDisc = (Number)currPo.getAttribute("SumDiscAmt_Trans");
            Number poDiscAmt = zero;
            if (currPo.getAttribute("DiscVal_Trans") != null)
                poDiscAmt = (Number)currPo.getAttribute("DiscVal_Trans");
            totaldiscSum = sumItmDisc.add(poDiscAmt);
            diff = totaldiscSum.subtract(totDiscAmtSum);
            adfLog.info(diff + "  ::::  diff:: " + totaldiscSum);
            Row itmRow[] = getAm().getMmQuotItm().getAllRowsInRange();
            if (itmRow.length > 0) {
                Number totdisc = zero;
                Integer len = (itmRow.length) - 1;
                Row r = itmRow[len];
                if (r.getAttribute("TotDiscAmtSp") != null)
                    totdisc = (Number)r.getAttribute("TotDiscAmtSp");
                r.setAttribute("TotDiscAmtSp", totdisc.add(diff));
            }//quotAmtSpBind */
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotWiseDiscBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAmtSpBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ovrAllDiscBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalDiscBind);


        }
    }


    public void deleteSelectedItm(ActionEvent actionEvent) {
        // BindingContainer bindings = getBindings();
        // DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmQuotItmIterator");
        //  Key parentKey = parentIter.getCurrentRow().getKey();
        QuotationAMImpl am = getAm();
        ViewObject quotItm = am.getMmQuotItm();
        ViewObjectImpl quotTr = am.getMmQuotTr();
        ViewObjectImpl quotTrLine = am.getMmQuotTrLines();
        RowQualifier rqTax = new RowQualifier(quotTr);
        RowQualifier rqTaxLine = new RowQualifier(quotTrLine);
        //  Row rr =quotItm.getCurrentRow();
        RowSetIterator rsi = quotItm.createRowSetIterator(null);
        int count = 0;
        while (rsi.hasNext()) {
            Row nxt = rsi.next();
            Object mfd = nxt.getAttribute("MarkedForDeleteItm");
            System.out.println("mfd--->" + mfd);
            if (mfd != null) {
                // Boolean b = (Boolean)mfd;
                String b = (String) mfd.toString();
                if (b.equalsIgnoreCase("Y") || b.equalsIgnoreCase("true")) {
                    count = count + 1;

                }
            }
        }
        rsi.closeRowSetIterator();
        System.out.println("Count to del=" + count);
        if (count > 0) {

            RowSetIterator rsi1 = quotItm.createRowSetIterator(null);

            while (rsi1.hasNext()) {
                Row nxt = rsi1.next();
                if (nxt.getAttribute("MarkedForDeleteItm") != null) {
                    if (nxt.getAttribute("MarkedForDeleteItm").toString().equalsIgnoreCase("Y") ||
                        nxt.getAttribute("MarkedForDeleteItm").toString().equalsIgnoreCase("true")) {
                        if (nxt.getAttribute("ItmId") != null && nxt.getAttribute("ItmUom") != null) {
                            rqTax.setWhereClause("DocId= '" + nxt.getAttribute("DocId").toString() + "' and ItmId= '" +
                                                 nxt.getAttribute("ItmId").toString() + "' and ItmUom='" +
                                                 nxt.getAttribute("ItmUom").toString() + "'");
                            Row[] rTax = quotTr.getFilteredRows(rqTax);
                            //       System.out.println("rTax.length---"+rTax.length);
                            if (rTax.length >
                                0) {
                                //            System.out.println("del tax line on item del");
                                rqTaxLine.setWhereClause("DocId= '" + nxt.getAttribute("DocId").toString() +
                                                         "' and ItmId= '" + nxt.getAttribute("ItmId").toString() +
                                                         "' and TaxRuleId= '" + rTax[0].getAttribute("TaxRuleId") +
                                                         "' and ItmUom='" + nxt.getAttribute("ItmUom").toString() +
                                                         "'");
                                Row[] rTaxLine = quotTrLine.getFilteredRows(rqTaxLine);
                                //           System.out.println("rTaxLine.length---"+rTaxLine.length);
                                if (rTaxLine.length > 0) {
                                    for (Row rT1 : rTaxLine) {
                                        rT1.remove();
                                    }
                                }
                                rTax[0].remove();
                            }
                        }

                        nxt.remove();
                    }
                }
            }

            rsi1.closeRowSetIterator();
            quotTrLine.executeQuery();
            quotTr.executeQuery();
            //  getAm().getMmQuotItm().setWhereClause(null);
            getAm().getMmQuotItm().executeQuery();
            OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute1");
            operationBinding1.execute();


        } else {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_WARN, resolvElDCMsg("#{bundle['MSG.270']}").toString(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }

        // rr.setAttribute("MarkedForDeleteItm", "N");
        /*  itmTableCheckBoxBind.setValue("N");
        itmTableCheckBoxBind.setSelected(false); */
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableCheckBoxBind);
    }

    public void setTexruleid(Integer texruleid) {
        this.texruleid = texruleid;
    }

    public Integer getTexruleid() {
        return texruleid;
    }

    public String cancelBackToSearch() {
        /* BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute(); */
        UploadedFile = null;
        this.itmForm_readOnly = true;
        this.form_readOnly = true;
        setMode("");
        // refDOcIdBind.setDisabled(false);
        setRfqDocidDisble("");
        otherInformationTabBind.setDisclosed(false);
        supplierTabBind.setDisclosed(true);
        return "goToSearch";
    }

    public void dltDiscValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        //Number n = (Number) object;
        if (object != null) {
            Number n = new Number(0);
            if (object != null) {
                n = (Number) object;
            }
            Number totol = (Number) ((Number) itmPrice.getValue()).multiply((Number) quotQty.getValue());
            Number zero = new Number(0);
            Number hundrd = new Number(100);
            if (discType.getValue().equals("A") && n.compareTo(totol) == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.401']}").toString() + "" +
                                                              totol, null));
            } else if (n.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.305']}").toString(), null));
            }
            if (discType.getValue().equals("P") && n.compareTo(hundrd) == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.303']}").toString(), null));
            }
        }
    }


    public void quotDiscValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number n = (Number) object;
        if (n == null) {
            n = new Number(0);
        }
        Number totol =
            (Number) ((Number) ((Number) totalOnDiscQuotVal.getValue()).minus((Number) sumItmTaxTotAmt.getValue())).round(6);
        Number zero = new Number(0);
        Number hundrd = new Number(100);
        if (quotDiscType.getValue() != null && quotDiscType.getValue().equals("A") && n.compareTo(totol) == 1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.401']}").toString() + "" + totol,
                                                          null));
        } else if (n.compareTo(zero) == -1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.305']}").toString(), null));
        }
        if (quotDiscType.getValue() != null && quotDiscType.getValue().equals("P") && n.compareTo(hundrd) == 1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.303']}").toString(), null));
        }
    }

    public void tlrncValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number n = (Number) object;
        Number zero = new Number(0);
        Number hundrd = new Number(100);
        if (tlrnDiscType.getValue().equals("P") && n.compareTo(hundrd) == 1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.303']}").toString(), null));
        } else if (n.compareTo(zero) == -1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.305']}").toString(), null));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(tlrncQty);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tlrnDiscType);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
    }

    public void setQuotQty(RichInputText quotQty) {
        this.quotQty = quotQty;
    }

    public RichInputText getQuotQty() {
        return quotQty;
    }

    public void setItmPrice(RichInputText itmPrice) {
        this.itmPrice = itmPrice;
    }

    public RichInputText getItmPrice() {
        return itmPrice;
    }

    public void setDiscType(RichSelectOneRadio discType) {
        this.discType = discType;
    }

    public RichSelectOneRadio getDiscType() {
        return discType;
    }

    public void deleteQuotation(ActionEvent actionEvent) {

    }

    public void unitRateValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number n = (Number) object;
        //System.out.println("N ="+n);
        if (n.compareTo(new Number(0)) < 0) {
            System.out.println("n is either 0 or -ve");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Rate must be Greater than Zero.", null));
        } else if (n.compareTo(new Number(0)) == 0) {
            ViewObjectImpl quot = getAm().getMmQuotItm();
            Row quotRow = quot.getCurrentRow();
            if (quotRow.getAttribute("ItmId") != null)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Rate must be Greater than Zero.", null));
        } else {
            ViewObjectImpl quot = getAm().getMmQuot1();
            Row quotRow = quot.getCurrentRow();
            Number currFact = new Number(1);
            if (quotRow.getAttribute("CurrConvFctr") != null) {
                currFact = (Number) quotRow.getAttribute("CurrConvFctr");
            }

            Number tot = (Number) n.multiply((Number) quotQty.getValue()).multiply(currFact).round(6);
            Number zero = new Number(0);
            Boolean totFlag = isPrecisionValid(26, 6, tot);
            if (n.compareTo(zero) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Price can not be less than Zero.", null));
            }
            if (getAm().getMmQuotItm().getCurrentRow().getAttribute("ItmId") != null && n.compareTo(zero) == 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Price should be greater than Zero.", null));

            if (totFlag.toString().equals("false")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.405']}").toString(), null));
            }
        }
    }

    /** Validation for Unit Quantity value for Detail table must be greater than zero
     * */

    public void qtyVatidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number n = (Number) object;
        Number zero = new Number(0);
        Number currFact = new Number(1);
        ViewObjectImpl quot = getAm().getMmQuot1();
        ViewObjectImpl quotItm = getAm().getMmQuotItm();
        Row quotItmRow = quotItm.getCurrentRow();
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String p_org_id = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
        Row quotRow = quot.getCurrentRow();
        String p_quot_item_id = null;
        if (quotItmRow.getAttribute("ItmId") != null) {
            p_quot_item_id = quotItmRow.getAttribute("ItmId").toString();
        }
        String flag = "Y";

        if (quotRow.getAttribute("CurrConvFctr") != null) {
            currFact = (Number) quotRow.getAttribute("CurrConvFctr");
        }
        Number tot = (Number) n.multiply((Number) itmPrice.getValue()).multiply(currFact).round(6);
        Boolean totFlag = isPrecisionValid(26, 6, tot);
        if (n.compareTo(zero) == -1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.337']}").toString(), null));
        }

        if (quotItmRow.getAttribute("ItmId") != null) {
            if (n.compareTo(zero) == 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.337']}").toString(), null));
            if (totFlag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.405']}").toString(), null));
            }
        }
    }

    public void itemValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /**------------For duplicate input item validation-----------------------*/
        /*      String inputItm=(String)object;
        String itmDesc=null;
        ViewObjectImpl v = getAm().getMmQuotItm();
         int totalCount=v.getRowCount();  //get ALL rows
         int rangeSize=v.getRangeSize(); //all in range
         v.setRangeSize(totalCount);
         Row[] rArray=v.getAllRowsInRange();
         Row cRow=v.getCurrentRow();
         int count=0;
         String itemIdCur="";
         for(Row r:rArray){
             if(!r.equals(cRow)){
                 try{
                         itemIdCur=r.getAttribute("ItmId").toString();
                    }
                 catch(NullPointerException npe){
                    itemIdCur="";
                    }
                 if(itemIdCur!=null){
                        Row [] xx=getAm().getLovItmId().getFilteredRows("ItmId", itemIdCur);
                        if(xx.length>0){
                        itmDesc= xx[0].getAttribute("ItmDesc").toString();
                     }
                     if(inputItm.equalsIgnoreCase(itmDesc)){
                         count=count+1;
                  }
              }

          }
        }
        v.setRangeSize(rangeSize);  //set to original range
                if(count>0){
                         String msg2=resolvElDCMsg("#{bundle['MSG.46']}").toString();
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                  }
              */



    }

    public void discTypeVCL(ValueChangeEvent vce) {
        Number zero = new Number(0);
        ViewObject voItm = getAm().getMmQuotItm();
        Row rr = voItm.getCurrentRow();
        rr.setAttribute("DiscVal", zero);
        if (vce.getNewValue() != null) {
            quotQty.processUpdates(FacesContext.getCurrentInstance());
            //itmPrice.processUpdates(FacesContext.getCurrentInstance());
            itmAmtSpBind.processUpdates(FacesContext.getCurrentInstance());
            transItmAmtSp.processUpdates(FacesContext.getCurrentInstance());
            totDiscAmtSpBind.processUpdates(FacesContext.getCurrentInstance());
            discAmtSpBind.processUpdates(FacesContext.getCurrentInstance());
            if (discAmtValBind.getValue() != null && discAmtValBind.getValue().toString().length() > 0) {
                OperationBinding obind1 = getBindings().getOperationBinding("itmWiseDiscAmt");
                obind1.getParamsMap().put("value", zero);
                obind1.execute();
                OperationBinding bind = getBindings().getOperationBinding("discountAmountset");
                bind.getParamsMap().put("value", zero);
                bind.getParamsMap().put("type", "Q");
                bind.execute();
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(quotWiseDiscBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAmtSpBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ovrAllDiscBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalDiscBind);

        }

    }

    public void quotDiscTypeVCL(ValueChangeEvent vce) {
        Number zero = new Number(0);
        ViewObject voQuot = getAm().getMmQuot1();
        Row rr = voQuot.getCurrentRow();
        rr.setAttribute("DiscVal", zero);

    }

    public void setTotalOnDiscQuotVal(RichInputText totalOnDiscQuotVal) {
        this.totalOnDiscQuotVal = totalOnDiscQuotVal;
    }

    public RichInputText getTotalOnDiscQuotVal() {
        return totalOnDiscQuotVal;
    }


    public void setTlrnDiscType(RichSelectOneRadio tlrnDiscType) {
        this.tlrnDiscType = tlrnDiscType;
    }

    public RichSelectOneRadio getTlrnDiscType() {
        return tlrnDiscType;
    }

    public void setQuotDiscType(RichSelectOneRadio quotDiscType) {
        this.quotDiscType = quotDiscType;
    }

    public RichSelectOneRadio getQuotDiscType() {
        return quotDiscType;
    }

    public void quotIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String quotId = object.toString();
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
                    closeFg = true; /**1.no. of closed brackets will not be more than open brackets at any given time.*/
                }
            }

            /**2.if openB=0 then no. of closing and opening brackets equal ||
                   * 3.opening bracket must always come before closing brackets
                   * 4.closing brackets must not come before first occurrence of openning bracket
                   */
            if (openB != closeB || closeFg == true || (quotId.lastIndexOf("(") > quotId.lastIndexOf(")")) ||
                (quotId.indexOf(")") < quotId.indexOf("("))) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            /**5.check for empty brackets
                    */
            if (quotId.contains("()")) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**check for to dot not comes together
                    */
            /* if(quotId.contains("..")){
                               String msg2="Consecutive dots .. value not allowed together.";
                               FacesMessage message2 = new FacesMessage(msg2);
                               message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                               throw new ValidatorException(message2);
                       }  */
            /**check for to dot not comes together
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
                String msg2 = resolvElDCMsg("#{bundle['MSG.59']}").toString();
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
            ViewObject v = getAm().getMmQuot1();

            //check for valid country name.. Starts with character. can have dots .No two dots can be adjacent.
            String expression =
                "^(?:(?>[A-Za-z0-9 )]+)(?:\\_|\\-|\\(|\\.|\\)|\\:|\\#|\\/|\\\\(?!\\_|\\-|\\:|\\#|\\/|\\\\|$))?)*$";
            CharSequence inputStr = quotId;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.199']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }

        }
    }

    public String deleteQuot() {
        String returnVal = null;
        Row currRw = getAm().getMmQuot1().getCurrentRow();
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
        String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String P_DOCID = currRw.getAttribute("DocId").toString();
        String flag = (String) callStoredFunction(Types.VARCHAR, "MM.PKG_MM_QUOT.IS_QUOT_DELETABLE(?,?,?,?)", new Object[] {
                                                  P_SLOCID, CldID, P_ORGID, P_DOCID
        });
        if (flag.equalsIgnoreCase("N")) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.409']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else if (flag.equalsIgnoreCase("Y")) {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
            operationBinding.execute();
            BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding1 = bindings1.getOperationBinding("Commit");
            operationBinding1.execute();
            BindingContainer bindings2 = getBindings();
            OperationBinding operationBinding2 = bindings2.getOperationBinding("Execute");
            operationBinding2.execute();
            getAm().getQuotSearch1().executeQuery();
            setMode("");
            returnVal = "goToSearch";
        }


        return returnVal;
    }

    public void setQuotSourceBind(RichSelectOneRadio quotSourceBind) {
        this.quotSourceBind = quotSourceBind;
    }

    public RichSelectOneRadio getQuotSourceBind() {
        return quotSourceBind;
    }

    public void setTlrncQty(RichInputText tlrncQty) {
        this.tlrncQty = tlrncQty;
    }

    public RichInputText getTlrncQty() {
        return tlrncQty;
    }

    public void tlrncTypeVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(tlrncQty);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tlrnDiscType);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
    }

    public String AddOC() {
        ViewObjectImpl quot = getAm().getMmQuot1();
        ViewObjectImpl quotItm = getAm().getMmQuotItm();
        Row quotItmRow = quotItm.getCurrentRow();
        Row quotRow = quot.getCurrentRow();
        Number zero = new Number(0);
        Number price = zero;
        Number qty = zero;
        if (quotItmRow != null) {
            price = (Number) quotItmRow.getAttribute("ItmPrice");
            qty = (Number) quotItmRow.getAttribute("QuotQty");
        }

        if (quotRow.getAttribute("EoId") == null) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.398']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (zero.compareTo(price) == 0 && quotItmRow != null) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.399']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (zero.compareTo(qty) == 0 && quotItmRow != null) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.400']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else {
            showPopup(ocPopUp, true);
        }
        return null;
    }

    public void setOcPopUp(RichPopup ocPopUp) {
        this.ocPopUp = ocPopUp;
    }

    public RichPopup getOcPopUp() {
        return ocPopUp;
    }

    public void setTncTableBind(RichTable tncTableBind) {
        this.tncTableBind = tncTableBind;
    }

    public RichTable getTncTableBind() {
        return tncTableBind;
    }

    public void itmNameVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {

            /**
             * code for setting UomConvFctr @nit
             * */
            Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String pOrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            getAm().getLovItmIdForCode().setNamedWhereClauseParam("cldBind", p_cldId);
            getAm().getLovItmIdForCode().setNamedWhereClauseParam("slocBind", pslocId);
            getAm().getLovItmIdForCode().setNamedWhereClauseParam("orgBind", pOrgId);
            getAm().getLovItmIdForCode().setNamedWhereClauseParam("hoOrgBind", p_hoOrgId);
            getAm().getLovItmIdForCode().setNamedWhereClauseParam("itmBind", null);
            getAm().getLovItmIdForCode().setNamedWhereClauseParam("itmDescBind", vce.getNewValue());
            getAm().getLovItmIdForCode().executeQuery();
            RowQualifier rqitm = new RowQualifier(getAm().getLovItmIdForCode());
            rqitm.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and HoOrgId='" + p_hoOrgId +
                                 "' and ItmDesc='" + vce.getNewValue() + "'");
            System.out.println("Query is=" + rqitm.getExprStr());
            Row ritm[] = getAm().getLovItmIdForCode().getFilteredRows(rqitm);
            String itm = null;
            String uomBs = null;
            String uomPur = null;
            if (ritm.length > 0) {
                itm = (String) ritm[0].getAttribute("ItmId");
                getAm().getMmQuotItm().getCurrentRow().setAttribute("ItmId", itm);
                uomBs = (String) ritm[0].getAttribute("UomBasic");
                System.out.println("uom basic=" + uomBs);
                uomPur = (String) ritm[0].getAttribute("UomPur");
                System.out.println("uom Purchase=" + uomPur);
                getAm().getMmQuotItm().getCurrentRow().setAttribute("ItmUom", uomPur);
            }
            System.out.println("Orgid=" + getAm().getMmQuotItm().getCurrentRow().getAttribute("OrgId"));
            getAm().getLovUomVw1().setNamedWhereClauseParam("cldBind", p_cldId);
            getAm().getLovUomVw1().setNamedWhereClauseParam("slocBind", pslocId);
            getAm().getLovUomVw1().setNamedWhereClauseParam("orgBind", pOrgId);
            getAm().getLovUomVw1().setNamedWhereClauseParam("hoOrgBind", p_hoOrgId);
            getAm().getLovUomVw1().setNamedWhereClauseParam("itmIdBind", itm);
            getAm().getLovUomVw1().executeQuery();
            //  ViewObjectImpl voUomVw=getAm().getLovUomVw1();
            RowQualifier rquom = new RowQualifier(getAm().getLovUomVw1());
            rquom.setWhereClause("CldId='" + p_cldId + "' and SlocId=" + pslocId + " and OrgId='" + pOrgId +
                                 "' and HoOrgId='" + p_hoOrgId + "' and ItmId='" + itm + "' and UomIdSrc='" + uomBs +
                                 "' and UomIdDest='" + uomPur + "'");
            System.out.println("lovuom query=" + rquom.getExprStr());
            Row ruom[] = getAm().getLovUomVw1().getFilteredRows(rquom);
            Number convFctr = new Number(0);
            if (ruom.length > 0)
                convFctr = (Number) ruom[0].getAttribute("ConvFctr");
            getAm().getMmQuotItm().getCurrentRow().setAttribute("UomConvFctr", convFctr);
            System.out.println("Conversion factor for this=" + convFctr);
            /** Check If Item linked to Supplier or not*/

            Object obj = getAm().getMmQuot1().getCurrentRow().getAttribute("EoId");
            //Row[] filteredRows = getAm().getLovItmId().getFilteredRows("ItmDesc", vce.getNewValue().toString());
            String itmId = null;
            String itmUom = null;
            itmId = (String) getAm().getMmQuotItm().getCurrentRow().getAttribute("ItmId");
            itmUom = (String) getAm().getMmQuotItm().getCurrentRow().getAttribute("ItmUom");
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
            if (obj != null) {
                RowQualifier rqchk = new RowQualifier(getAm().getMmQuotItm());
                rqchk.setWhereClause("ItmId='" + itmId + "' and ItmUom='" + itmUom + "'");
                if ((itmId == null && itmUom == null) || getAm().getMmQuotItm().getFilteredRows(rqchk).length > 1) {
                    System.out.println("Already exist");
                } else {
                    System.out.println("check item to EO");
                    String eoId = obj.toString();
                    getAm().getLovItmIdEo().executeQuery();
                    ViewObjectImpl voi = getAm().getLovItmIdEo();
                    RowQualifier rq = new RowQualifier(voi);
                    //String itmId=rw.getAttribute("TransItemId").toString();
                    // System.out.println("ItmId"+itmId);
                    rq.setWhereClause("EoId='" + eoId + "' and ItmId='" + itmId + "'");
                    Row[] rows = voi.getFilteredRows(rq);
                    //System.out.println("Row length---"+rows.length);
                    if (rows.length == 0) {
                        showPopup(itmLinkSupPopUp, true);
                    }
                }
            }
        }

    }

    public void setItmLinkSupPopUp(RichPopup itmLinkSupPopUp) {
        this.itmLinkSupPopUp = itmLinkSupPopUp;
    }

    public RichPopup getItmLinkSupPopUp() {
        return itmLinkSupPopUp;
    }


    public void itmLinkDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            Row poRow = getAm().getMmQuot1().getCurrentRow();
            Row poItmRow = getAm().getMmQuotItm().getCurrentRow();
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
            String pOrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            String pHoOrgId = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            Integer pEoId = Integer.parseInt(poRow.getAttribute("EoId").toString());
            String docId = poRow.getAttribute("DocId").toString();
            String pItmUom = poItmRow.getAttribute("ItmUom").toString();
            Integer currIsSp = Integer.parseInt(poRow.getAttribute("CurrIdSp").toString());
            Number pPrice = zero;
            Number pQty = zero;
            String pDiscType = "A";
            Number pDiscVal = zero;
            String pTlrncType = "A";
            Number pTlrncVal = zero;
            String pItmId = null;
            if (poItmRow.getAttribute("ItmId") != null) {
                pItmId = poItmRow.getAttribute("ItmId").toString();
            }
            System.out.println("item id " + pItmId);
            String pLeadTime = null;
            if (poItmRow.getAttribute("ItmPrice") != null) {
                pPrice = (Number) poItmRow.getAttribute("ItmPrice");
            }
            if (poItmRow.getAttribute("QuotQty") != null) {
                pQty = (Number) poItmRow.getAttribute("QuotQty");
            }
            if (poItmRow.getAttribute("DiscType") != null) {
                pDiscType = poItmRow.getAttribute("DiscType").toString();
            }

            if (poItmRow.getAttribute("DiscVal") != null) {
                pDiscVal = (Number) poItmRow.getAttribute("DiscVal");
            }
            if (poItmRow.getAttribute("TlrncQtyType") != null) {
                pTlrncType = poItmRow.getAttribute("TlrncQtyType").toString();
            }

            if (poItmRow.getAttribute("TlrncQtyVal") != null) {
                pTlrncVal = (Number) poItmRow.getAttribute("TlrncQtyVal");
            }
            Integer pUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
            oracle.jbo.domain.Date pdateCurr = (oracle.jbo.domain.Date) new oracle.jbo.domain.Date().getCurrentDate();
            /*   System.out.println("--" + pslocId + "---" + pOrgId + "--" + pEoId + "-----" + pItmId + "-----" + pItmUom +
                               "----" + pPrice + "----" + pQty + "----" + pDiscType + "---" + pDiscVal + "---" +
                               pTlrncType + "---" + pTlrncVal + "----" + pLeadTime + "---" + pUsrId + "---" +
                               pdateCurr); */
            Integer retval =
                Integer.parseInt(callStoredFunction(INTEGER, "MM.MM_INS_EO_ITM_AUTO(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                    CldID, pslocId, pHoOrgId, pOrgId, pEoId, currIsSp, pItmId, pItmUom,
                                                    pUsrId, 18503, docId
            }).toString());
            adfLog.info("retval in link" + retval);
            getAm().getDBTransaction().postChanges();
        }


    }

    public void setOtherIfnoTabBind(RichCommandImageLink otherIfnoTabBind) {
        this.otherIfnoTabBind = otherIfnoTabBind;
    }

    public RichCommandImageLink getOtherIfnoTabBind() {
        return otherIfnoTabBind;
    }

    public void setOtherInformationTabBind(RichShowDetailItem otherInformationTabBind) {
        this.otherInformationTabBind = otherInformationTabBind;
    }

    public RichShowDetailItem getOtherInformationTabBind() {
        return otherInformationTabBind;
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void qtyItmVCL(ValueChangeEvent vce) {
        // Number newqty = (Number) vce.getNewValue();
        if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0) {
            quotQty.processUpdates(FacesContext.getCurrentInstance());
            //itmPrice.processUpdates(FacesContext.getCurrentInstance());
            itmAmtSpBind.processUpdates(FacesContext.getCurrentInstance());
            transItmAmtSp.processUpdates(FacesContext.getCurrentInstance());
            totDiscAmtSpBind.processUpdates(FacesContext.getCurrentInstance());
            discAmtSpBind.processUpdates(FacesContext.getCurrentInstance());
            if (discAmtValBind.getValue() != null && discAmtValBind.getValue().toString().length() > 0) {
                OperationBinding obind1 = getBindings().getOperationBinding("itmWiseDiscAmt");
                obind1.getParamsMap().put("value", (Number) discAmtValBind.getValue());
                obind1.execute();
            }
            OperationBinding bind = getBindings().getOperationBinding("discountAmountset");
            bind.getParamsMap().put("value", (Number) vce.getNewValue());
            bind.getParamsMap().put("type", "Q");
            bind.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotWiseDiscBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAmtSpBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ovrAllDiscBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalDiscBind);

        }
        //  if(newqty != null){

        /*  Number tot = newqty.multiply((Number)itmPrice.getValue());
        Boolean qtyFlag = isPrecisionValid(26, 6, newqty);
        Boolean totFlag = isPrecisionValid(26, 6, tot);
      if(totFlag.equals(false)){ */
        /*  String Defaultmsg = resolvEl("#{bundle['MM.InvalidPrecision266.Validation.Calculate.Msg']}");
           // String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
            FacesMessage msg = new FacesMessage(Defaultmsg);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(quotQty.getClientId(), msg); */
        //  }
        /* ViewObjectImpl quot = getAm().getMmQuot1();
      ViewObjectImpl quotItm = getAm().getMmQuotItm();
      Row quotItmRow = quotItm.getCurrentRow();
      Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
      String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
      Row quotRow = quot.getCurrentRow();
      String p_quot_item_id = null;
      if(quotItmRow.getAttribute("ItmId")!=null){
          p_quot_item_id = quotItmRow.getAttribute("ItmId").toString();
      }
      String flag = "Y";
      if(p_quot_item_id !=null && newqty != null){
          flag=(String)callStoredFunction(Types.VARCHAR, "MM.PKG_MM_STK.IS_ORD_QTY_VALID(?,?,?,?)", new Object[]{p_sloc_id,p_org_id,p_quot_item_id,newqty});
          }
          if(flag.equalsIgnoreCase("N")){
              FacesMessage message = new FacesMessage("Order Quantity exceeds maximum Quantity defined for the item.");
              message.setSeverity(FacesMessage.SEVERITY_WARN);
              FacesContext fc = FacesContext.getCurrentInstance();
              fc.addMessage(quotQty.getClientId(), message);
          }
        } */
    }

    public void priceItmVCL(ValueChangeEvent vce) {
        /* Number newPrice = (Number)vce.getNewValue();
    Number tot = newPrice.multiply((Number)quotQty.getValue());
    Boolean itmFlag = isPrecisionValid(26, 6, newPrice);
    Boolean totFlag = isPrecisionValid(26, 6, tot);
        if(totFlag.equals(false)){

    } */
        //ovrAllDiscBind

        /*   UIComponent c=vce.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance()); */
        //  itmAmtSpBind.getContext().processUpdates();
        itmPrice.processUpdates(FacesContext.getCurrentInstance());
        itmAmtSpBind.processUpdates(FacesContext.getCurrentInstance());
        transItmAmtSp.processUpdates(FacesContext.getCurrentInstance());
        totDiscAmtSpBind.processUpdates(FacesContext.getCurrentInstance());

        if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0) {
            if (discAmtValBind.getValue() != null && discAmtValBind.getValue().toString().length() > 0) {
                OperationBinding obind1 = getBindings().getOperationBinding("itmWiseDiscAmt");
                obind1.getParamsMap().put("value", (Number) discAmtValBind.getValue());
                obind1.execute();
            }
            OperationBinding bind = getBindings().getOperationBinding("discountAmountset");
            bind.getParamsMap().put("value", (Number) vce.getNewValue());
            bind.getParamsMap().put("type", "P");
            bind.execute();
            /*  OperationBinding obind=getBindings().getOperationBinding("discountoverAll");
            obind.execute(); */
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotWiseDiscBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAmtSpBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ovrAllDiscBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalDiscBind);
        }
    }

    public void setItmTableCheckBoxBind(RichSelectBooleanCheckbox itmTableCheckBoxBind) {
        this.itmTableCheckBoxBind = itmTableCheckBoxBind;
    }

    public RichSelectBooleanCheckbox getItmTableCheckBoxBind() {
        return itmTableCheckBoxBind;
    }

    public void ocSpValuevalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number orderQtyVal = (Number) object;
        Number zero = new Number(0);

        ViewObjectImpl quot = getAm().getMmQuot1();
        ViewObjectImpl quotOc = getAm().getMmQuotOc();
        // Row quotOcrow = quotOc.getCurrentRow();
        Row row = quot.getCurrentRow();
        String tranType = row.getAttribute("TrandOcType").toString();
        Number totalquotSpAmt = (Number) sumitemSpBind.getValue();
        Number currFact = new Number(1);
        if (row.getAttribute("CurrConvFctr") != null) {
            currFact = (Number) row.getAttribute("CurrConvFctr");
        }

        Number tot = (Number) orderQtyVal.multiply(currFact).round(6);
        Boolean qtyFlag = isPrecisionValid(26, 6, orderQtyVal);
        Boolean totFlag = isPrecisionValid(26, 6, tot);
        if (tranType.equalsIgnoreCase("S") && orderQtyVal.compareTo(totalquotSpAmt) == 1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.302']}").toString(), null));
        }
        if (orderQtyVal.compareTo(zero) <= 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.412']}").toString(), null));

        }
        if (totFlag.equals(false)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.405']}").toString(), null));
        }
    }

    public void setQuotAmtSpBind(RichInputText quotAmtSpBind) {
        this.quotAmtSpBind = quotAmtSpBind;
    }

    public RichInputText getQuotAmtSpBind() {
        return quotAmtSpBind;
    }

    public void setOcAmtSpBind(RichInputText ocAmtSpBind) {
        this.ocAmtSpBind = ocAmtSpBind;
    }

    public RichInputText getOcAmtSpBind() {
        return ocAmtSpBind;
    }

    public void ocPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {

        /*  ViewObject oc =getAm().getMmQuotOc();
        Row currRw = oc.getCurrentRow();

        if(currRw.getAttribute("CoaId") == null){
            currRw.remove();
        }
        oc.executeQuery(); */
    }

    public void setSumitemSpBind(RichInputText sumitemSpBind) {
        this.sumitemSpBind = sumitemSpBind;
    }

    public RichInputText getSumitemSpBind() {
        return sumitemSpBind;
    }

    public void setOcTableBind(RichTable ocTableBind) {
        this.ocTableBind = ocTableBind;
    }

    public RichTable getOcTableBind() {
        return ocTableBind;
    }

    public void eoIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String eoName = (String) object;
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String p_org_id = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
        String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer p_eoId = null;
        if (eoName != null) {
            Row[] xx = getAm().getLovEoId1().getFilteredRows("EoNm", eoName);
            if (xx.length > 0) {
                p_eoId = Integer.parseInt(xx[0].getAttribute("EoId").toString());
            }
            String flag = "Y";
            if (p_eoId != null) {
                flag = (String) callStoredFunction(Types.VARCHAR, "APP.PKG_APP_EO.IS_SUPP_VALID(?,?,?,?,?)", new Object[] {
                                                   p_sloc_id, CldID, null, p_org_id, p_eoId
                });
            }
            if (flag.equalsIgnoreCase("B")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.340']}").toString(), null));
            } else if (flag.equalsIgnoreCase("H")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.341']}").toString(), null));
            }

        }
    }

    /*  public void uomValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
      String uomId = (String)object;
        ViewObjectImpl quotItm = getAm().getMmQuotItm();
        Row quotItmRow = quotItm.getCurrentRow();
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String p_quot_item_id = null;
        if(quotItmRow.getAttribute("ItmId")!=null){
            p_quot_item_id = quotItmRow.getAttribute("ItmId").toString();
        }
        String flag = "Y";
    if(uomId != null){
        flag=(String)callStoredFunction(Types.VARCHAR, "APP.PKG_UOM.IS_UOM_SELECTABLE(?,?,?)", new Object[]{p_sloc_id,p_quot_item_id,uomId});
    }
    System.out.println("uom flag-----"+flag);
        if(flag.equalsIgnoreCase("N")){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unit can't be selected as no conversion factor is defined.", null));
        }
    }*/

    public void supplierVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotSourceBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotQty);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmPrice);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmBaseAmt);
    }

    /* public void addOCActionEvent(ActionEvent actionEvent) {
        System.out.println("oc add");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert3");
        operationBinding.execute();
    } */

    public void setSumItmTaxTotAmt(RichInputText sumItmTaxTotAmt) {
        this.sumItmTaxTotAmt = sumItmTaxTotAmt;
    }

    public RichInputText getSumItmTaxTotAmt() {
        return sumItmTaxTotAmt;
    }

    public String setEoAddsTable() {
        System.out.println("inside task flow method");
        //  INS_EO_ADDS(P_SLOCID     IN NUMBER,
        //  P_EOID       IN APP.APP$EO.EO_ID%TYPE,
        // P_ADDSID     IN APP.APP$ADDS$BK.ADDS_ID%TYPE,
        // P_ADDS_TYPE  IN VARCHAR2,
        // P_USRID      IN NUMBER)
        ViewObjectImpl quot = getAm().getMmQuot1();
        Row quotRow = quot.getCurrentRow();
        Integer p_UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_adds_id = (String) quotRow.getAttribute("BillAddsId");
        System.out.println("adds value---:" + quotRow.getAttribute("BillAddsId"));
        Integer p_eo_id = null;
        if (quotRow.getAttribute("EoId") != null) {
            p_eo_id = Integer.parseInt(quotRow.getAttribute("EoId").toString());
        }
        System.out.println("p_UsrId---" + p_UsrId + "p_sloc_id---" + p_sloc_id + "p_adds_id---" + p_adds_id + "eo----" +
                           p_eo_id + "CldID     " + CldID);
        if (p_eo_id != null && p_adds_id != null) {
            BigDecimal addsRetVal = (BigDecimal) callStoredFunction(NUMBER, "APP.PKG_APP_EO.INS_EO_ADDS(?,?,?,?,?,?,?)", new Object[] {
                                                                    p_sloc_id, CldID, p_hoOrgId, p_eo_id, p_adds_id,
                                                                    "B", p_UsrId
            });
            System.out.println("addsRetVal   :" + addsRetVal);
        }

        return "goToTab";

    }

    public void billingAddsReturnListener(ReturnEvent returnEvent) {
        System.out.println("inside return");
        ViewObjectImpl quot = getAm().getMmQuot1();
        Row quotRow = quot.getCurrentRow();
        System.out.println("adds value---:" + quotRow.getAttribute("BillAddsId"));
    }

    /* public void quotDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Date currDt= (Date)Date.getCurrentDate();
        Date poDate=(Date)object;
        String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");

       java.sql.Date s=(java.sql.Date)callStoredFunction(Types.DATE,"APP.PKG_APP.GET_ORG_FY_START_DATE(?,?,?)", new Object[]{currDt,pOrgId,"FY"});
       Date strtDate=new Date(s);
       System.out.println("Start Date of FY:"+strtDate );
       if(strtDate!=null){
        if(poDate.compareTo(strtDate)==-1){
           String msg2 = resolvElDCMsg("#{bundle['MSG.415']}").toString();
           FacesMessage message2 = new FacesMessage(msg2);
           message2.setDetail(resolvElDCMsg("#{bundle['MSG.416']}").toString());
           message2.setSeverity(FacesMessage.SEVERITY_ERROR);
           throw new ValidatorException(message2);
        }
       }
    } */


    public void quotDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Date currDt = (Date) Date.getCurrentDate();
        Date formDate = (Date) object;

        String pOrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
        String pSloc = (String) resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}");
        String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Object oDt = callStoredFunction(Types.VARCHAR, "APP.PKG_APP.GET_FY_STAT_FOR_TXN(?,?,?)", new Object[] {
                                        pSloc, pOrgId, formDate
        });
        Integer fyId = (Integer) (callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] {
                                                     CldID, pOrgId, formDate
        }));

        if (oDt == null) {
            String msg2 = "Financial Year is not defined.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        } else if ("Y".equalsIgnoreCase(oDt.toString())) {
            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
            String msg2 = resolvEl("#{bundle['MSG.415']}");
            FacesMessage message2 = new FacesMessage(msg2);
            // message2.setDetail(resolvEl("#{bundle['MSG.463']}") + s);
            message2.setDetail(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }
    }

    public void ocTypeVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocAmtSpBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocAmtSpBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocPopUp);
    }


    public void setTaxAbleAmoutForChange(Number taxAbleAmoutForChange) {
        this.taxAbleAmoutForChange = taxAbleAmoutForChange;
    }

    public Number getTaxAbleAmoutForChange() {
        return taxAbleAmoutForChange;
    }

    public void setDisableSelectTax(String disableSelectTax) {
        this.disableSelectTax = disableSelectTax;
    }

    public String getDisableSelectTax() {
        return disableSelectTax;
    }

    public void retrieveDeleteRfqItem(ActionEvent actionEvent) {
        ViewObjectImpl quot = getAm().getMmQuot1();
        Row quotRow = quot.getCurrentRow();
        ViewObject vo = getAm().getMmQuotItm();
        // Row quotItmRow =vo.getCurrentRow();
        // String docId = (String)quotItmRow.getAttribute("DocId");
        String rfqId = (String) quotRow.getAttribute("RefDocId");
        System.out.println("rfq id ---" + rfqId);
        //Row row[]=  getAm().getLovRfqItm1().getFilteredRows("DocId", rfqId);
        if (rfqId == null) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.418']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            RowSetIterator rsi = vo.createRowSetIterator(null);
            String itmNameConcat = "";
            String strToProc = "";
            while (rsi.hasNext()) {
                Row rw = rsi.next();
                if (rw.getAttribute("ItmId") != null) {
                    itmNameConcat = itmNameConcat + "'" + rw.getAttribute("ItmId").toString() + "',";
                }
            }
            rsi.closeRowSetIterator();
            if (itmNameConcat.length() > 1)
                strToProc = itmNameConcat.substring(0, itmNameConcat.length() - 1);
            System.out.println("---" + strToProc);
            // if(strToProc.length()>1){

            QuotationAMImpl aMImpl = getAm();
            if (strToProc.length() > 1) {
                System.out.println("1");
                aMImpl.getLovRfqItm1().setWhereClause("DOC_ID = '" + rfqId + "' and ITM_ID not in (" + strToProc + ")");
                aMImpl.getLovRfqItm1().executeQuery();
            } else {
                System.out.println("2");
                aMImpl.getLovRfqItm1().setWhereClause("DOC_ID = '" + rfqId + "'");
                aMImpl.getLovRfqItm1().executeQuery();
            }


            Row[] itmRows = aMImpl.getLovRfqItm1().getAllRowsInRange();

            System.out.println(aMImpl.getLovRfqItm1().getRowCount() + "-COUNT-" + itmRows.length);
            if (aMImpl.getLovRfqItm1().getRowCount() > 0) {
                RowSetIterator rrssi = aMImpl.getLovRfqItm1().createRowSetIterator(null);

                while (rrssi.hasNext()) {
                    Row x = rrssi.next();
                    System.out.println("Values--" + x.getAttribute("ItmId"));
                    Row row1 = vo.createRow();
                    row1.setAttribute("ItmId", x.getAttribute("ItmId"));
                    row1.setAttribute("QuotQty", x.getAttribute("RfqQty"));
                    row1.setAttribute("ItmUom", x.getAttribute("ItmUom"));
                    vo.insertRow(row1);
                    vo.executeQuery();
                }
                rrssi.closeRowSetIterator();

            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.419']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }

            aMImpl.getLovRfqItm1().setWhereClause(null);
            aMImpl.getLovRfqItm1().executeQuery();

        }
    }

    public void setItmBaseAmt(RichInputText itmBaseAmt) {
        this.itmBaseAmt = itmBaseAmt;
    }

    public RichInputText getItmBaseAmt() {
        return itmBaseAmt;
    }

    public void setSupplierTabBind(RichShowDetailItem supplierTabBind) {
        this.supplierTabBind = supplierTabBind;
    }

    public RichShowDetailItem getSupplierTabBind() {
        return supplierTabBind;
    }

    public void OtherInfoDL(DisclosureEvent disclosureEvent) {
        UploadedFile = null;
        if (disclosureEvent.isExpanded()) {
            if (refDOcIdBind.isDisabled()) {
                // if(inputRefDOcIdBind.isDisabled()){

            } else {
                ///  refDOcIdBind.setDisabled(true);
                setRfqDocidDisble("P");
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(refDOcIdBind);
            //  AdfFacesContext.getCurrentInstance().addPartialTarget(inputRefDOcIdBind);
        }

    }

    public void TncDL(DisclosureEvent disclosureEvent) {
        UploadedFile = null;
        if (disclosureEvent.isExpanded()) {
            if (refDOcIdBind.isDisabled()) {
                // if(inputRefDOcIdBind.isDisabled()){

            } else {
                setRfqDocidDisble("P");
                ///  refDOcIdBind.setDisabled(true);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(refDOcIdBind);
            // AdfFacesContext.getCurrentInstance().addPartialTarget(inputRefDOcIdBind);
        }

    }

    public void SupplierTbDL(DisclosureEvent disclosureEvent) {
        UploadedFile = null;
        if (disclosureEvent.isExpanded()) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(refDOcIdBind);
            if (Integer.parseInt(quotSourceBind.getValue().toString()) == 159) {
                /// refDOcIdBind.setDisabled(true);
                setRfqDocidDisble("P");
            } else {
                System.out.println("ddddddddddd");
                setRfqDocidDisble("O");
                ///refDOcIdBind.setDisabled(false);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(refDOcIdBind);
            //  AdfFacesContext.getCurrentInstance().addPartialTarget(inputRefDOcIdBind);
        }

    }

    public void callToSearchBean() {
        /*
        setRfqDocidDisble("O");
        System.out.println("----------"+getRfqDocidDisble()); */
    }

    public void callFromViewSearchBean() {
        setMode("V");
        /*  setRfqDocidDisble("P");
        System.out.println("1----------"+getRfqDocidDisble()); */
    }

    public void setRfqDocidDisble(String rfqDocidDisble) {
        this.rfqDocidDisble = rfqDocidDisble;
    }

    public String getRfqDocidDisble() {
        return rfqDocidDisble;
    }

    public void supplierVCE(ValueChangeEvent valueChangeEvent) {

    }

    public void supplierLinkDialogListener(DialogEvent dialogEvent) {
        System.out.println("before yes");
        if (dialogEvent.getOutcome().name().equals("yes")) {
            System.out.println("inside           yes");
            Row poRow = getAm().getMmQuot1().getCurrentRow();
            Row poItmRow = getAm().getMmQuot1().getCurrentRow();
            Number zero = new Number(0);

            Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String pOrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            Integer pEoId = Integer.parseInt(poRow.getAttribute("EoId").toString());
            Integer pUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
            String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            oracle.jbo.domain.Date pdateCurr = (oracle.jbo.domain.Date) new oracle.jbo.domain.Date().getCurrentDate();

            Number pPrice = zero;
            Number pQty = zero;
            String pDiscType = "A";
            Number pDiscVal = zero;
            String pTlrncType = "A";
            Number pTlrncVal = zero;
            String pLeadTime = null;

            RowSetIterator rsi = getAm().getMmQuotItm().createRowSetIterator(null);
            //  String link="N";
            while (rsi.hasNext()) {
                Row r = rsi.next();
                ViewObjectImpl voi = getAm().getLovItmIdEo();
                RowQualifier rq = new RowQualifier(voi);

                String pItmId = r.getAttribute("ItmId").toString();
                String pItmUom = r.getAttribute("ItmUom").toString();

                if (r.getAttribute("ItmPrice") != null) {
                    pPrice = (Number) r.getAttribute("ItmPrice");
                }
                if (r.getAttribute("QuotQty") != null) {
                    pQty = (Number) r.getAttribute("QuotQty");
                }
                if (r.getAttribute("DiscType") != null) {
                    pDiscType = r.getAttribute("DiscType").toString();
                }

                if (r.getAttribute("DiscVal") != null) {
                    pDiscVal = (Number) r.getAttribute("DiscVal");
                }
                if (r.getAttribute("TlrncQtyType") != null) {
                    pTlrncType = r.getAttribute("TlrncQtyType").toString();
                }

                if (r.getAttribute("TlrncQtyVal") != null) {
                    pTlrncVal = (Number) r.getAttribute("TlrncQtyVal");
                }

                if (r.getAttribute("ItmId") != null) {
                    rq.setWhereClause("EoId='" + pEoId + "' and ItmId='" + r.getAttribute("ItmId").toString() +
                                      "' and Actv='Y'");
                    Row[] rows = voi.getFilteredRows(rq);
                    //System.out.println("Row length---"+rows.length);
                    if (rows.length ==
                        0) {
                        //     link="Y";
                        Integer retval =
             Integer.parseInt(callStoredFunction(INTEGER, "MM.PKG_MM_EOITM.INS_EO_ITM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                 pslocId, CldID, pOrgId, pEoId, pItmId, pItmUom, pPrice, pQty,
                                                 pDiscType, pDiscVal, pTlrncType, pTlrncVal, pLeadTime, pUsrId,
                                                 pdateCurr, 1
                        }).toString());
                        //System.out.println("retval in link"+retval);
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
            Row po = getAm().getMmQuot1().getCurrentRow();
            String name = po.getAttribute("EoId").toString();
            po.setAttribute("EoId", null);
            po.setAttribute("EoIdSwitcherLov_Trans", "");
            po.setAttribute("CurrIdSp", null);
            po.setAttribute("CurrConvFctr", 0);
            po.setAttribute("BillAddsId", "");
            po.setAttribute("BillAdds_Trans", "");

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
            //  disclosedItem.setDisclosed(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
            // AdfFacesContext.getCurrentInstance().addPartialTarget(disclosedItem);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoNameTransBind);
        }
    }

    public void setItmNameForLink(String itmNameForLink) {
        this.itmNameForLink = itmNameForLink;
    }

    public String getItmNameForLink() {
        return itmNameForLink;
    }

    public void setEoNameTransBind(RichInputListOfValues eoNameTransBind) {
        this.eoNameTransBind = eoNameTransBind;
    }

    public RichInputListOfValues getEoNameTransBind() {
        return eoNameTransBind;
    }

    public void setSumgroupBind(RichPanelGroupLayout sumgroupBind) {
        this.sumgroupBind = sumgroupBind;
    }

    public RichPanelGroupLayout getSumgroupBind() {
        return sumgroupBind;
    }

    public void setEoIdDbBind(RichInputText eoIdDbBind) {
        this.eoIdDbBind = eoIdDbBind;
    }

    public RichInputText getEoIdDbBind() {
        return eoIdDbBind;
    }

    public void setLinkSupPopUp(RichPopup linkSupPopUp) {
        this.linkSupPopUp = linkSupPopUp;
    }

    public RichPopup getLinkSupPopUp() {
        return linkSupPopUp;
    }

    public void supplierValueCL(ValueChangeEvent valueChangeEvent) {
        //if(valueChangeEvent.getNewValue()!=null){
        AdfFacesContext.getCurrentInstance().addPartialTarget(applyTaxBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(resetTaxBind);
        //}
        System.out.println("inside VCL-------");
        String supName = valueChangeEvent.getNewValue().toString();
        Row[] suprows = getAm().getLovEoId1().getFilteredRows("EoNm", supName);
        itmNameForLink = "";
        String name = null;
        if (suprows.length > 0) {
            name = suprows[0].getAttribute("EoId").toString();
        }

        if (valueChangeEvent.getNewValue() != null) {
            adfLog.info("Call eo " + valueChangeEvent.getNewValue());
            OperationBinding binding = getBindings().getOperationBinding("changeProspectToSupplier");
            binding.getParamsMap().put("EoNm", valueChangeEvent.getNewValue().toString());
            binding.execute();
        }
        // String name=getAm().getMmDrftPo().getCurrentRow().getAttribute("EoId").toString();
        if (name != null) {
            RowSetIterator rsi = getAm().getMmQuotItm().createRowSetIterator(null);
            String link = "N";
            while (rsi.hasNext()) {
                Row r = rsi.next();
                ViewObjectImpl voi = getAm().getLovItmIdEo();
                RowQualifier rq = new RowQualifier(voi);
                //String itmId=rw.getAttribute("TransItemId").toString();
                // System.out.println("ItmId"+itmId);
                //  String itmNameForLink="";
                if (r.getAttribute("ItmId") != null) {
                    rq.setWhereClause("EoId='" + name + "' and ItmId='" + r.getAttribute("ItmId").toString() +
                                      "' and Actv='Y'");
                    Row[] rows = voi.getFilteredRows(rq);
                    //System.out.println("Row length---"+rows.length);
                    if (rows.length == 0) {
                        link = "Y";
                        itmNameForLink = itmNameForLink + "'" + r.getAttribute("ItemId_Trans").toString() + "',";

                    }
                }
            }
            System.out.println("Items--" + itmNameForLink);
            if (link.equalsIgnoreCase("Y")) {
                System.out.println("inside popup calling");
                showPopup(linkSupPopUp, true);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotSourceBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotQty);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmPrice);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmBaseAmt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(eoIdDbBind);
    }

    /* public void setInputRefDOcIdBind(RichInputListOfValues inputRefDOcIdBind) {
        this.inputRefDOcIdBind = inputRefDOcIdBind;
    }

    public RichInputListOfValues getInputRefDOcIdBind() {
        return inputRefDOcIdBind;
    }  */


    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setDocid(Integer Docid) {
        this.Docid = Docid;
    }

    public Integer getDocid() {
        return Docid;
    }

    public void setWf_no(String wf_no) {
        this.wf_no = wf_no;
    }

    public String getWf_no() {
        return wf_no;
    }

    public String saveandforward() {
        UploadedFile = null;
        if (convFactBind.getValue() == null || convFactBind.getValue().equals(-1)) {
            String msg2 = "Conversion Factor is not define for this Supplier. \nCan not Proceed !!";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }

        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
        String p_org_id = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
        String CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        QuotationAMImpl am = (QuotationAMImpl) resolvElDC("QuotationAMDataControl");
        ViewObjectImpl qout = am.getMmQuot1();
        ViewObject quotItm = am.getMmQuotItm();
        ViewObject quotTr = am.getMmQuotTr();
        // ViewObjectImpl quotView = am.getMmQuotVO1();
        Row currRow = qout.getCurrentRow();
        Row currItmRow = quotItm.getCurrentRow();
        Row[] filterdRw = quotTr.getFilteredRows("DocId", currRow.getAttribute("DocId").toString());
        String doc_txn_id = currRow.getAttribute("DocId").toString();
        RequestContext.getCurrentInstance().getPageFlowScope().put("DOC_ID", doc_txn_id);
        Number taxableAmt = new Number(0);

        if (filterdRw.length > 0) {
            taxableAmt = (Number) (filterdRw[0].getAttribute("TaxableAmt"));
        }

        String taxruleFlg = "Q";
        String taxAfterDiscFlg = "Y";
        if (currRow.getAttribute("TaxRuleFlg") != null) {
            taxruleFlg = currRow.getAttribute("TaxRuleFlg").toString();
        }
        if (currRow.getAttribute("TaxAfterDiscFlg") != null) {
            taxAfterDiscFlg = currRow.getAttribute("TaxAfterDiscFlg").toString();
        }
        Number taxOnAmount = new Number(0);
        if (taxAfterDiscFlg.equalsIgnoreCase("Y")) {
            taxOnAmount = (Number) currRow.getAttribute("QuotAmtSp_Trans");
        } else if (taxAfterDiscFlg.equalsIgnoreCase("N")) {
            taxOnAmount = (Number) currRow.getAttribute("SumItmAmtSp_Trans");
        }

        // check for all item price greater than zero


        /** check for all item and unit not null
         * */
        RowSetIterator rsi4 = quotItm.createRowSetIterator(null);

        Integer countForItmUnit = 0;
        while (rsi4.hasNext()) {
            Row rw1 = rsi4.next();
            if (rw1.getAttribute("ItmId") == null || rw1.getAttribute("ItmUom") == null) {
                countForItmUnit = countForItmUnit + 1;
            }
        }
        rsi4.closeRowSetIterator();
        adfLog.info("countForItmUnit  " + countForItmUnit);
        if (countForItmUnit > 0) {
            FacesMessage message = new FacesMessage("Item or unit is not selected . Please check and retry.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }


        RowSetIterator rsi1 = quotItm.createRowSetIterator(null);
        String itmName = "";
        String itmNameUom = "";
        int count = 0;
        int countUom = 0;
        Number zero = new Number(0);
        while (rsi1.hasNext()) {
            Row rw1 = rsi1.next();
            if (rw1.getAttribute("ItmPrice") != null) {
                Number price = (Number) rw1.getAttribute("ItmPrice");
                if (zero.compareTo(price) == 0) {
                    count = count + 1;
                    if (rw1.getAttribute("ItemId_Trans") != null) {
                        itmName = itmName + rw1.getAttribute("ItemId_Trans").toString() + ",";
                    }
                }
                if (rw1.getAttribute("ItmUom") == null) {
                    countUom = countUom + 1;
                    if (rw1.getAttribute("ItemId_Trans") != null) {
                        itmNameUom = itmNameUom + rw1.getAttribute("ItemId_Trans").toString() + ",";
                    }
                }
            }
        }
        rsi1.closeRowSetIterator();
        // check for item wise taxable amount change or no

        RowSetIterator rsi = quotItm.createRowSetIterator(null);
        String appTax = "N";
        String itmNameConcat = "";
        while (rsi.hasNext()) {
            Row rw = rsi.next();
            if (rw.getAttribute("TransTaxChangedFlg") != null) {
                if ("Y".equalsIgnoreCase(rw.getAttribute("TransTaxChangedFlg").toString())) {
                    appTax = "Y";
                    if (rw.getAttribute("ItemId_Trans") != null) {
                        itmNameConcat = itmNameConcat + rw.getAttribute("ItemId_Trans").toString() + ",";
                    }
                }
            }
        }
        rsi.closeRowSetIterator();
        Integer sourceId = Integer.parseInt(currRow.getAttribute("QuotSource_trans").toString());
        if (sourceId == 158 && currRow.getAttribute("RefDocId") == null) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.392']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        Number amtSpCheck = (Number) currRow.getAttribute("QuotAmtSpAftrTax_Trans");
        Number amtBfrTax = (Number) currRow.getAttribute("QuotAmtSp_Trans");

        if (zero.compareTo(amtSpCheck) >= 0 || zero.compareTo(amtBfrTax) >= 0) {
            FacesMessage message = new FacesMessage("Total amount for Quotation must be Greater than Zero.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }
        String dtok = "N";
        if (currRow.getAttribute("QuotDt") != null) {
            Date dt = new Date(currRow.getAttribute("QuotDt").toString());
            Integer fyId = 0;
            if (dt != null) {

                fyId = (Integer) (callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] {
                                                     currRow.getAttribute("CldId"), currRow.getAttribute("OrgId"), dt
                }));
                if (fyId > 0) {
                    currRow.setAttribute("FyId", fyId);
                }

            }
            dtok = "Y";
            if (currRow.getAttribute("RefDocId") != null) {
                adfLog.info("date : " + currRow.getAttribute("QuotDt"));
                ViewObjectImpl rfqvo = getAm().getLovrfqId();
                rfqvo.setNamedWhereClauseParam("SlocIdBind", p_sloc_id);
                rfqvo.setNamedWhereClauseParam("CldidBind", CldID);
                rfqvo.setNamedWhereClauseParam("OrgIdBind", p_org_id);
                rfqvo.setNamedWhereClauseParam("BindQuotDt", currRow.getAttribute("QuotDt"));
                rfqvo.executeQuery();
                Row[] r = getAm().getLovrfqId().getFilteredRows("DocId", currRow.getAttribute("RefDocId"));
                Date rfqdt = (Date) r[0].getAttribute("DocDt");
                java.sql.Date strtDt = null;
                java.sql.Date endDt = null;
                try {
                    strtDt = rfqdt.dateValue(); // rfq date
                    endDt = dt.dateValue(); //sqldate

                } catch (Exception e) {
                    System.out.println("Error in cast date" + e);
                }
                adfLog.info(rfqdt + "  rfqdt " + dt + "   dt  " + strtDt + " strtDt " + endDt);
                if (strtDt.toString().equals(endDt.toString()))
                // if(rfqdt.toString().equals(dt.toString()))
                {
                    dtok = "Y";
                } else {

                    if (rfqdt.compareTo(dt) <= 0) {
                        dtok = "Y";
                    } else
                        dtok = "N";

                }
            }
        }
        if (dtok.equals("N")) {
            FacesMessage message = new FacesMessage("Quotation Date can be On or After RFQ Date.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (currRow.getAttribute("EoId") != null) {
            if (quotItm.getAllRowsInRange().length > 0) {
                /*   if ("Q".equalsIgnoreCase(taxruleFlg) && taxableAmt.compareTo(taxOnAmount) != 0 && filterdRw.length > 0) {
                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.394']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
            }else */if ("Y".equalsIgnoreCase(appTax)) {

                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.292']}").toString() + " ( " +
                                         itmNameConcat.substring(0, itmNameConcat.length() - 1) +
                                         resolvElDCMsg("#{bundle['MSG.293']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);

                } else if (count > 0) {

                    FacesMessage message =
                        new FacesMessage(" ( " + itmName.substring(0, itmName.length() - 1) + " )" +
                                         resolvElDCMsg("#{bundle['MSG.315']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);

                } else {

                    Number amtBs = (Number) currRow.getAttribute("QuotAmtBs_Trans");
                    Number amtSp = (Number) currRow.getAttribute("QuotAmtSpAftrTax_Trans");
                    Number totalSpAmt = (Number) currRow.getAttribute("QuotAmtSp");
                    String quotId = currRow.getAttribute("QuotId").toString();
                    Boolean amtSpFlag = isPrecisionValid(26, 6, amtSp);
                    if (amtSpFlag.equals(false)) {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.395']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {

                        ///  check for duplicate quotation
                        Integer eoID = Integer.parseInt(currRow.getAttribute("EoId").toString());
                        String quotID = (String) currRow.getAttribute("QuotId").toString();

                        qout.setRangeSize(-1);
                        qout.getAllRowsInRange();
                        RowQualifier rowQualifier = new RowQualifier(qout);
                        // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
                        rowQualifier.setWhereClause(" EoId = " + eoID + " and QuotId = '" + quotID + "'");
                        Row[] rows = qout.getFilteredRows(rowQualifier);
                        System.out.println("-----------" + rowQualifier.getExprStr());
                        System.out.println("rows.length    " + rows.length);
                        // System.out.println("value of sup----"+dup);
                        if (rows.length > 1) {
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.46']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else {
                            System.out.println("----------save---------");
                            currRow.setAttribute("QuotAmtBs", amtBs);
                            currRow.setAttribute("QuotAmtSp", amtSp);
                            BindingContainer bindings4 = getBindings();
                            DCIteratorBinding parentIter = (DCIteratorBinding) bindings4.get("MmQuot1Iterator");
                            Key parentKey = parentIter.getCurrentRow().getKey();
                            getAm().getMmQuotItm().executeQuery();
                            BindingContainer bindings = getBindings();


                            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                            operationBinding.execute();


                            supplierTabBind.setDisclosed(true);
                            // refDOcIdBind.setDisabled(true);
                            setRfqDocidDisble("P");


                            String action = "I";
                            String remark = "Initiate";
                            if (wf_no != null && !"0".equalsIgnoreCase(wf_no)) {
                                Integer level = -1;
                                level =
                                    Integer.parseInt(callStoredFunction(NUMBER, "APP.WF_GET_USR_LEVEL(?,?,?,?,?,?,?)", new Object[] {
                                                                        p_sloc_id, CldID, p_org_id, p_user_id, wf_no,
                                                                        Docid, DocTypeId
                                }).toString());
                                if (level > -1) {
                                    System.out.println(p_sloc_id + " " + CldID + " " + p_org_id + " " + Docid + " " +
                                                       wf_no + " " + doc_txn_id + " " + p_user_id + " " + p_user_id +
                                                       " " + level + " " + level + " " + action + " " + remark + " " +
                                                       amtBs);

                                    Integer res =
                                        Integer.parseInt(callStoredFunction(NUMBER,
                                                                            "APP.WF_INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                                            p_sloc_id, CldID, p_org_id, Docid,
                                                                            DocTypeId, wf_no, doc_txn_id, p_user_id,
                                                                            p_user_id, level, level, action, remark,
                                                                            amtBs
                                    }).toString());
                                    System.out.println("res in fwd=" + res);
                                    OperationBinding operationBindinger = bindings.getOperationBinding("Commit");
                                    operationBindinger.execute();
                                    this.itmForm_readOnly = true;
                                    this.form_readOnly = true;
                                    setMode("V");
                                    tmpMode = "V";
                                    return "wfServ";
                                    /*  if(parentIter.getRowSetIterator().getRow(parentKey)!=null){  //check condition else gives exception in add mode.
                                 parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                            }


                      this.itmForm_readOnly=true;
                      this.form_readOnly=true;
                      setMode("V");
                        StringBuilder msg=new StringBuilder("<html><body><p>"+resolvElDCMsg("#{bundle['LBL.784']}").toString()+" : <b>"+quotId+ "</b>"+ resolvElDCMsg("#{bundle['MSG.396']}").toString()+"</p>");
                        msg.append("<p>"+resolvElDCMsg("#{bundle['MSG.299']}").toString()+"<b>"+amtSp+"</b></p>");
                        msg.append("<p>"+ resolvElDCMsg("#{bundle['MSG.300']}").toString()+"<b>"+amtBs+"</b></p>");
                        msg.append("</body></html>");  */
                                    /* FacesMessage message2 = new FacesMessage(msg.toString());
                        message2.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null,message2);*/
                                } else {
                                    FacesMessage message2 =
                                        new FacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!");
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message2);
                                }
                            } else {
                                FacesMessage message2 =
                                    new FacesMessage("Workflow not Defined for current Organisation.");
                                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message2);
                            }
                        }

                        rowQualifier.setWhereClause(null);
                        //  qout.executeQuery();
                    }


                }
            } else if (sourceId == 159) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.397']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.398']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

        return null;
    }

    public void setTransRfqIdBindVar(RichInputListOfValues transRfqIdBindVar) {
        this.transRfqIdBindVar = transRfqIdBindVar;
    }

    public RichInputListOfValues getTransRfqIdBindVar() {
        return transRfqIdBindVar;
    }

    public void setQuotDateBind(RichInputDate quotDateBind) {
        this.quotDateBind = quotDateBind;
    }

    public RichInputDate getQuotDateBind() {
        return quotDateBind;
    }

    public void uomValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...












































































    }

    public void quotTaxRuleVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void applyTaxButtonAL(ActionEvent actionEvent) throws SQLException {


        // System.out.println("new Taxrule="+selectTaxPoBind.getValue());
        ViewObjectImpl povo = getAm().getMmQuot1();
        //   System.out.println("Tax rule according to po="+povo.getCurrentRow().getAttribute("transTaxRuleId"));
        ViewObjectImpl itmvo = getAm().getMmQuotItm();
        ViewObjectImpl trVo = getAm().getMmQuotTr();
        ViewObjectImpl trLine = getAm().getMmQuotTrLines();
        ViewObjectImpl trVonew = getAm().getMmQuotTr1();
        ViewObjectImpl trLinenew = getAm().getMmQuotTrLines2();
        ViewObjectImpl trLinevo1 = getAm().getMmQuotTrLines1();
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
        String p_org_id = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String taxid = null;
        if (taxRuleQuotBinding.getValue() != null) {
            taxid = taxRuleQuotBinding.getValue().toString();
            String radioflg = povo.getCurrentRow().getAttribute("TransRadioFlg").toString();
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
                                            trlinerow.getAttribute("ItmUom").toString().equals(itmUom)
                                            /* && trlinerow.getAttribute("TaxRuleId").toString().equals(trrw.getAttribute("TaxRuleId") )*/) {
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
                            ex = (String) rw.getAttribute("TransTaxExmptFlg");
                        if (ex.equals("Y")) {
                        } else {
                            Row newrow = trVo.createRow();
                            //       System.out.println("new row created in tr");
                            String taxruleflg = "Q";
                            Number p_curr_fctr = new Number(1);
                            if (povo.getCurrentRow().getAttribute("CurrConvFctr") != null) {
                                p_curr_fctr = (Number) povo.getCurrentRow().getAttribute("CurrConvFctr");
                            }
                            newrow.setAttribute("ItmId", itmId);
                            newrow.setAttribute("ItmUom", itmUom);
                            newrow.setAttribute("TaxRuleId", taxRuleQuotBinding.getValue());
                            newrow.setAttribute("TaxRuleFlg", taxruleflg);

                            if (removedExmptflg != null)
                                newrow.setAttribute("TaxExmptFlg", removedExmptflg);
                            else
                                newrow.setAttribute("TaxExmptFlg", rw.getAttribute("TransTaxExmptFlg"));

                            newrow.setAttribute("UsrIdCreate", p_user_id);
                            //TaxableAmt
                            String afterdisc = povo.getCurrentRow().getAttribute("TaxAfterDiscFlg").toString();
                            if (afterdisc.equals("N"))
                                newrow.setAttribute("TaxableAmt", rw.getAttribute("ItmAmtSpBeforeDisc_Trans"));
                            else
                                newrow.setAttribute("TaxableAmt", rw.getAttribute("ItmAmtSp_Trans"));

                            Number p_taxable_amount = (Number) newrow.getAttribute("TaxableAmt");
                            System.out.println("Tax rule flg=" + taxruleflg);
                            //create trlines
                            BigDecimal ret =
                                (BigDecimal) callStoredFunction(NUMBER,
                                                                "MM.MM_INS_QUOT_TR_LINES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                                p_sloc_id, p_cldId, p_hoOrgId, p_org_id, docId, itmId,
                                                                itmUom, taxid, p_user_id, p_taxable_amount, taxruleflg,
                                                                p_curr_fctr, newrow.getAttribute("TaxExmptFlg"), "N"
                            });

                            //                            BigDecimal ret =
                            //                                (BigDecimal) callStoredFunction(NUMBER,
                            //                                                                "MM.PKG_MM_QUOT.ins_quot_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                            //                                                                p_sloc_id, p_cldId, p_hoOrgId, p_org_id, docId, itmId,
                            //                                                                itmUom, taxid, p_user_id, p_taxable_amount, taxruleflg,
                            //                                                                p_curr_fctr, newrow.getAttribute("TaxExmptFlg")
                            //                            });

                            /*  (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                      new Object[] { p_sloc_id,p_cldId,p_hoOrgId, p_org_id, docId, itmId,itmUom,taxid,
                                                                                     p_user_id, p_taxable_amount, taxruleflg,
                                                                                     p_curr_fctr,removedExmptflg}); */
                            adfLog.info("return value of function is " + ret);
                            Number temp = new Number(0);
                            if (ret != null) {
                                try {
                                    temp = new Number(ret);
                                } catch (Exception e) {
                                    System.out.println("exception in this record " + e);
                                }
                            }
                            adfLog.info("taxable amount " + temp + " after rount off " + temp.round(6));
                            newrow.setAttribute("TaxAmt", new Number(temp.round(6)));
                            getAm().getMmQuot1().getCurrentRow().setAttribute("SumTaxAmtItm_Trans",
                                                                              new Number(temp.round(6)));
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
                                    trrw.getAttribute("TaxRuleFlg").toString().equalsIgnoreCase("Q")) {
                                    removedItmid = trrw.getAttribute("ItmId").toString();
                                    removedItmUom = trrw.getAttribute("ItmUom").toString();
                                    removedExmptflg = trrw.getAttribute("TaxExmptFlg").toString();


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
                        rqtr.setWhereClause("DocId='" + itmdocId + "' and ItmId='" + itmId + "' and ItmUom='" + itmUom +
                                            "'");
                        Row[] fritm = trVo.getFilteredRows(rqtr);
                        if (fritm.length == 0) {
                            //check if tax should be applied on this item or not.
                            String ex = "Y";
                            if (removedExmptflg != null)
                                ex = removedExmptflg;
                            else if (rw.getAttribute("TransTaxExmptFlg") != null)
                                ex = (String) rw.getAttribute("TransTaxExmptFlg");
                            if (ex.equals("Y")) {
                            } else {
                                Row newrow = trVo.createRow();

                                String taxruleflg = povo.getCurrentRow().getAttribute("TaxRuleFlg").toString();
                                Number p_curr_fctr = new Number(1);
                                if (povo.getCurrentRow().getAttribute("CurrConvFctr") != null) {
                                    p_curr_fctr = (Number) povo.getCurrentRow().getAttribute("CurrConvFctr");
                                }
                                newrow.setAttribute("ItmId", itmId);
                                newrow.setAttribute("ItmUom", itmUom);
                                newrow.setAttribute("TaxRuleId", taxRuleQuotBinding.getValue());
                                newrow.setAttribute("TaxRuleFlg", taxruleflg);

                                if (removedExmptflg != null)
                                    newrow.setAttribute("TaxExmptFlg", removedExmptflg);
                                else
                                    newrow.setAttribute("TaxExmptFlg", rw.getAttribute("TransTaxExmptFlg"));
                                String taxexm = (String) newrow.getAttribute("TaxExmptFlg");
                                newrow.setAttribute("UsrIdCreate", p_user_id);
                                //TaxableAmt
                                String afterdisc = povo.getCurrentRow().getAttribute("TaxAfterDiscFlg").toString();
                                if (afterdisc.equals("N"))
                                    newrow.setAttribute("TaxableAmt", rw.getAttribute("ItmAmtSpBeforeDisc_Trans"));
                                else
                                    newrow.setAttribute("TaxableAmt", rw.getAttribute("ItmAmtSp_Trans"));

                                Number p_taxable_amount = (Number) newrow.getAttribute("TaxableAmt");
                                System.out.println("Tax rule flg=" + taxruleflg);
                                //create trlines
                                BigDecimal ret =
                                    (BigDecimal) callStoredFunction(NUMBER,
                                                                    "MM.MM_INS_QUOT_TR_LINES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                                    p_sloc_id, p_cldId, p_hoOrgId, p_org_id, docId,
                                                                    itmId, itmUom, taxid, p_user_id, p_taxable_amount,
                                                                    taxruleflg, p_curr_fctr, taxexm, "N"
                                });

                                //                                BigDecimal ret =
                                //                                    (BigDecimal) callStoredFunction(NUMBER,
                                //                                                                    "MM.PKG_MM_QUOT.ins_quot_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                //                                                                    p_sloc_id, p_cldId, p_hoOrgId, p_org_id, docId,
                                //                                                                    itmId, itmUom, taxid, p_user_id, p_taxable_amount,
                                //                                                                    taxruleflg, p_curr_fctr, taxexm
                                //                                });
                                /*  (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                          new Object[] { p_sloc_id,p_cldId,p_hoOrgId, p_org_id, docId, itmId,itmUom,taxid,
                                                                                         p_user_id, p_taxable_amount, taxruleflg,
                                                                                         p_curr_fctr,taxexm }); */

                                adfLog.info("return value of function is " + ret);
                                Number temp = new Number(0);
                                if (ret != null) {
                                    try {
                                        temp = (Number) (new Number(ret)).round(6);
                                    } catch (Exception e) {
                                        System.out.println("exception in this record " + e);
                                    }
                                }

                                newrow.setAttribute("TaxAmt", temp);
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
            quotTaxPopupBind.hide();
        } else {
            FacesMessage message = new FacesMessage("Select TaxRule !!!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(taxRuleQuotBinding.getClientId(), message);
        }


    }

    public void quotTaxCancelButton(ActionEvent actionEvent) {
        quotTaxPopupBind.hide();
    }

    public void applyTaxQuotDL(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public void setTaxRuleQuotBinding(RichSelectOneChoice taxRuleQuotBinding) {
        this.taxRuleQuotBinding = taxRuleQuotBinding;
    }

    public RichSelectOneChoice getTaxRuleQuotBinding() {
        return taxRuleQuotBinding;
    }

    public void setTaxRadioBinding(RichSelectOneRadio taxRadioBinding) {
        this.taxRadioBinding = taxRadioBinding;
    }

    public RichSelectOneRadio getTaxRadioBinding() {
        return taxRadioBinding;
    }

    public void setQuotTaxPopupBind(RichPopup quotTaxPopupBind) {
        this.quotTaxPopupBind = quotTaxPopupBind;
    }

    public RichPopup getQuotTaxPopupBind() {
        return quotTaxPopupBind;
    }

    public void resetItemTaxAL(ActionEvent actionEvent) {
        resetTaxType = "I";
        adfLog.info("resetItemTaxAL  resetTaxType " + resetTaxType);
        showPopup(resetTaxItmQuotPopup, true);
    }

    public void setResetTaxType(String resetTaxType) {
        this.resetTaxType = resetTaxType;
    }

    public String getResetTaxType() {
        return resetTaxType;
    }

    public void resetTaxDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            QuotationAMImpl am = getAm();
            ViewObjectImpl poItm = am.getMmQuotItm();
            ViewObjectImpl poTaxVo = am.getMmQuotTr();
            ViewObjectImpl poTaxLineVo = am.getMmQuotTrLines1();
            poTaxLineVo.executeQuery();
            adfLog.info("tax xhk fla " + am.getMmQuot1().getCurrentRow().getAttribute("TaxAfterDiscFlg"));
            RowQualifier rqTax = new RowQualifier(poTaxVo);
            Row[] rsi1 = null;
            adfLog.info("resetTaxType    " + resetTaxType);
            if (resetTaxType.equals("Q")) {
                adfLog.info("Reset PO Wise Tax");
                // rsi1 = poItm.getFilteredRows("TransTaxRuleFlg","P");
                rsi1 = poItm.getAllRowsInRange();
            } else if (resetTaxType.equals("I")) {
                adfLog.info("Reset Item Wise Tax");
                // rsi1=  poItm.getFilteredRows("TransTaxRuleFlg","I");
                if (poItm.getCurrentRow() != null) {
                    adfLog.info("Current row is not null");
                    String itm = (String) poItm.getCurrentRow().getAttribute("ItmId");
                    String uom = (String) poItm.getCurrentRow().getAttribute("ItmUom");
                    RowQualifier rq = new RowQualifier(poItm);
                    rq.setWhereClause("ItmId='" + itm + "' and ItmUom='" + uom + "'");
                    rsi1 = poItm.getFilteredRows(rq);
                } else
                    adfLog.info("Current row is  null");
            }
            Integer leng = -1;
            adfLog.info("rsi1     " + rsi1);
            if (rsi1 != null && rsi1.length > 0) {
                leng = rsi1.length - 1;
                adfLog.info("Length=" + rsi1.length);
            }
            adfLog.info("  lenghth     " + leng);
            while (leng >= 0) {
                adfLog.info("for leng=" + leng);
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

            poTaxVo.executeQuery();
            poTaxLineVo.executeQuery();
            am.getMmQuotTrLines().executeQuery();
            am.getDBTransaction().postChanges();

            adfLog.info("tax after discount flag " + am.getMmQuot1().getCurrentRow().getAttribute("TaxAfterDiscFlg"));
            System.out.println("tax after discount flag " +
                               am.getMmQuot1().getCurrentRow().getAttribute("TaxAfterDiscFlg"));
        }
        if (getAm().getMmQuot1().getCurrentRow().getAttribute("TaxAfterDiscFlg") != null) {
            String flg = getAm().getMmQuot1().getCurrentRow().getAttribute("TaxAfterDiscFlg").toString();
            if ("Y".equalsIgnoreCase(flg)) {
                getAm().getMmQuot1().getCurrentRow().setAttribute("TaxAfterDiscFlg", flg);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxAftrDiscChk);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        if (getAm().getMmQuot1().getCurrentRow().getAttribute("TaxAfterDiscFlg") != null) {
            String flg = getAm().getMmQuot1().getCurrentRow().getAttribute("TaxAfterDiscFlg").toString();
            if ("Y".equalsIgnoreCase(flg)) {
                getAm().getMmQuot1().getCurrentRow().setAttribute("TaxAfterDiscFlg", flg);
            }
        }

    }

    public void resetTaxQuotationAL(ActionEvent actionEvent) {
        resetTaxType = "Q";
        adfLog.info("resetTaxQuotationAL  resetTaxType " + resetTaxType);
        showPopup(resetTaxItmQuotPopup, true);
    }

    public void setTmpMode(String tmpMode) {
        this.tmpMode = tmpMode;
    }

    public String getTmpMode() {
        return tmpMode;
    }

    public void quotTaxExemptedVCE(ValueChangeEvent valueChangeEvent) {
        Row trcurr = getAm().getMmQuotTr().getCurrentRow();
        ViewObject trline = getAm().getMmQuotTrLines();
        Number tax = new Number(0);

        if (trcurr != null && trcurr.getAttribute("TaxAmt") != null) {
            if (valueChangeEvent.getNewValue().equals(true)) {
                trcurr.setAttribute("TaxAmt", 0);
                getAm().getMmQuot1().getCurrentRow().setAttribute("QuotTaxAmt_Trans", 0);
            } else {
                RowSetIterator rsi = trline.createRowSetIterator(null);
                while (rsi.hasNext()) {
                    Row r = rsi.next();
                    if (r.getAttribute("TaxAmtSp") != null)
                        tax = tax.add((Number) (r.getAttribute("TaxAmtSp")));
                }
                trcurr.setAttribute("TaxAmt", tax);
                getAm().getMmQuot1().getCurrentRow().setAttribute("QuotTaxAmt_Trans", tax);

            }
        } else {
            System.out.println("Current ROw found Null value");
            //taxamt is null mean tax is not applied hence this checkbox must be disable
        }
    }

    public void setTaxRuleIdBind(RichSelectOneChoice taxRuleIdBind) {
        this.taxRuleIdBind = taxRuleIdBind;
    }

    public RichSelectOneChoice getTaxRuleIdBind() {
        return taxRuleIdBind;
    }

    public void itmTableRowSelectionListener(SelectionEvent selectionEvent) {
        /*    ADFUtil.invokeEL("#{bindings.MmQuotItm.collectionModel.makeCurrent}", new Class[] {SelectionEvent.class},
                                new Object[] { selectionEvent });
        getAm().getMmQuotTr().executeQuery();
       getAm().getMmQuotTr1().executeQuery();
     //  System.out.println("RowSelected->execute VOs-> Taxruleid="+taxRuleIdBind.getValue());
         */
    }


    public void setResetTaxItmQuotPopup(RichPopup resetTaxItmQuotPopup) {
        this.resetTaxItmQuotPopup = resetTaxItmQuotPopup;
    }

    public RichPopup getResetTaxItmQuotPopup() {
        return resetTaxItmQuotPopup;
    }

    public DnDAction tncDropListener(DropEvent dropEvent) {
        ViewObjectImpl v1 = getAm().getMmQuotTnc();
        ViewObject v2 = getAm().getMmQuot1();
        Row curr = v2.getCurrentRow();
        Integer tncid = (Integer) getAm().getLovTNC().getCurrentRow().getAttribute("TncId");
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
            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
            Row r = getAm().getMmQuotTnc().createRow();
            System.out.println("TncId=" + tncid);
            r.setAttribute("CldId", curr.getAttribute("CldId"));
            r.setAttribute("SlocId", curr.getAttribute("SlocId"));
            r.setAttribute("OrgId", curr.getAttribute("OrgId"));
            r.setAttribute("DocId", curr.getAttribute("DocId"));
            r.setAttribute("TncId", tncid);
            r.setAttribute("UsrIdCreate", p_user_id);
            System.out.println("Inserting row key=" + r.getKey());
            getAm().getMmQuotTnc().insertRow(r);
            getAm().getMmQuotTnc().executeQuery();
            /*  ArrayList lst = new ArrayList(1);
            lst.add(v1.getCurrentRow().getKey());
            getTncTableBind().setActiveRowKey(lst);  */

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(termsAndAggTab);
        return DnDAction.NONE;
    }

    public void setTermsAndAggTab(RichShowDetailItem termsAndAggTab) {
        this.termsAndAggTab = termsAndAggTab;
    }

    public RichShowDetailItem getTermsAndAggTab() {
        return termsAndAggTab;
    }

    public void setCoaNmBinding(RichInputListOfValues coaNmBinding) {
        this.coaNmBinding = coaNmBinding;
    }

    public RichInputListOfValues getCoaNmBinding() {
        return coaNmBinding;
    }

    public void setOcDescBinding(RichSelectOneChoice ocDescBinding) {
        this.ocDescBinding = ocDescBinding;
    }

    public RichSelectOneChoice getOcDescBinding() {
        return ocDescBinding;
    }

    public void setOcAmtSpBinding(RichInputText ocAmtSpBinding) {
        this.ocAmtSpBinding = ocAmtSpBinding;
    }

    public RichInputText getOcAmtSpBinding() {
        return ocAmtSpBinding;
    }

    public void setTaxAftrDiscChk(RichSelectBooleanCheckbox taxAftrDiscChk) {
        this.taxAftrDiscChk = taxAftrDiscChk;
    }

    public RichSelectBooleanCheckbox getTaxAftrDiscChk() {
        return taxAftrDiscChk;
    }

    public void setGetTaxAmtBind(RichInputText getTaxAmtBind) {
        this.getTaxAmtBind = getTaxAmtBind;
    }

    public RichInputText getGetTaxAmtBind() {
        return getTaxAmtBind;
    }

    public void setTaxAmtBind2(RichInputText taxAmtBind2) {
        this.taxAmtBind2 = taxAmtBind2;
    }

    public RichInputText getTaxAmtBind2() {
        return taxAmtBind2;
    }

    public String gettaxAmt() {
        Number i = new Number(0);
        String flag = "N";
        adfLog.info("now the value is " + i);
        if (getTaxAmtBind2().getValue() != null) {

            i = (Number) getTaxAmtBind2().getValue();
            if (i.compareTo(new Number(0)) == 0)
                adfLog.info("now the value is " + i);
            flag = "Y";
        }
        return flag;
    }

    public void setApplyTaxBind(RichCommandLink applyTaxBind) {
        this.applyTaxBind = applyTaxBind;
    }

    public RichCommandLink getApplyTaxBind() {
        return applyTaxBind;
    }

    public void setResetTaxBind(RichCommandLink resetTaxBind) {
        this.resetTaxBind = resetTaxBind;
    }

    public RichCommandLink getResetTaxBind() {
        return resetTaxBind;
    }

    public void itmDiscountVCE(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding obind = getBindings().getOperationBinding("itmWiseDiscAmt");
            obind.getParamsMap().put("value", (Number) valueChangeEvent.getNewValue());
            obind.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAmtSpBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(sumgroupBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotWiseDiscBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ovrAllDiscBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalDiscBind);
        }


    }

    public void setQuotWiseDiscBind(RichInputText quotWiseDiscBind) {
        this.quotWiseDiscBind = quotWiseDiscBind;
    }

    public RichInputText getQuotWiseDiscBind() {
        return quotWiseDiscBind;
    }

    public void setDiscAmtValBind(RichInputText discAmtValBind) {
        this.discAmtValBind = discAmtValBind;
    }

    public RichInputText getDiscAmtValBind() {
        return discAmtValBind;
    }

    public void setOvrAllDiscBind(RichInputText ovrAllDiscBind) {
        this.ovrAllDiscBind = ovrAllDiscBind;
    }

    public RichInputText getOvrAllDiscBind() {
        return ovrAllDiscBind;
    }

    public void setTotalDiscBind(RichInputText totalDiscBind) {
        this.totalDiscBind = totalDiscBind;
    }

    public RichInputText getTotalDiscBind() {
        return totalDiscBind;
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


    public Date getCurrDateDt() {
        // Date dt =(Date)new Date().getCurrentDate();
        adfLog.info("date quot " + quotDateBind.getValue());
        Date dt = (Date) quotDateBind.getValue();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt.dateValue());
        cal.add(Calendar.DATE, 0);
        java.util.Date dtt = cal.getTime();
        String dtStr = dateFormat.format(dtt);
        oracle.jbo.domain.Date daTime = null;
        try {
            java.util.Date date2 = dateFormat.parse(dtStr);
            java.sql.Date sqldate = new java.sql.Date(date2.getTime());
            daTime = new oracle.jbo.domain.Date(sqldate);
            System.out.println("daTime " + daTime);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        adfLog.info(daTime + "date quot " + quotDateBind.getValue());
        return daTime;
    }

    public void DlvdysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        adfLog.info("current object days" + object);
        if (object != null) {
            Integer i = new Integer(0);
            Integer obj = (Integer) object;
            if (obj.compareTo(i) ==
                -1) {
                //                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value must be greater than 0",
                //                                                              null));

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.653']}").toString(), null));
            }

        }

    }

    public java.util.Date getCurrentTime() {
        return (new java.util.Date(System.currentTimeMillis() + 86400));
    }


    public void setStusQty(RichSelectOneChoice stusQty) {
        this.stusQty = stusQty;
    }

    public RichSelectOneChoice getStusQty() {
        return stusQty;
    }

    public void setItmAmtSpBind(RichInputText itmAmtSpBind) {
        this.itmAmtSpBind = itmAmtSpBind;
    }

    public RichInputText getItmAmtSpBind() {
        return itmAmtSpBind;
    }

    public void setTransItmAmtSp(RichInputText transItmAmtSp) {
        this.transItmAmtSp = transItmAmtSp;
    }

    public RichInputText getTransItmAmtSp() {
        return transItmAmtSp;
    }

    public void setTotDiscAmtSpBind(RichInputText totDiscAmtSpBind) {
        this.totDiscAmtSpBind = totDiscAmtSpBind;
    }

    public RichInputText getTotDiscAmtSpBind() {
        return totDiscAmtSpBind;
    }

    public void setDiscAmtSpBind(RichInputText discAmtSpBind) {
        this.discAmtSpBind = discAmtSpBind;
    }

    public RichInputText getDiscAmtSpBind() {
        return discAmtSpBind;
    }

    public void refreshRemkAction(DisclosureEvent disclosureEvent) {
        // Add event code here...
        OperationBinding binding = getBindings().getOperationBinding("filterRmkAndDocVo");
        binding.execute();
    }

    public void fileDownloadAction(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        try {
            FileInputStream fin = new FileInputStream(filePathBind.getValue().toString());
            byte b[] = new byte[fin.available()];
            fin.read(b);
            outputStream.write(b);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void setFilePathBind(RichInputText filePathBind) {
        this.filePathBind = filePathBind;
    }

    public RichInputText getFilePathBind() {
        return filePathBind;
    }

    public String uploadFiles() throws Exception {
        System.out.println("---------1");
        String path = "";
        String extn = "";
        List<UploadedFile> files = this.getUploadedFile();
        InputStream in = null;
        FileOutputStream out = null;
        if (files != null) {
            System.out.println("---------2");
            for (int i = 0; i < files.size(); i++) {
                try {

                    System.out.println("---------------------3");
                    //get file extension
                    extn = files.get(i).getFilename().substring(files.get(i).getFilename().lastIndexOf("."));

                    //Add files to the Table
                    OperationBinding op = getBindings().getOperationBinding("createAttchmntRow");
                    //  op = ADFUtils.findOperation("createAttchmntRow");
                    op.getParamsMap().put("fileNm", files.get(i).getFilename());
                    op.getParamsMap().put("contentTyp", files.get(i).getContentType());
                    op.getParamsMap().put("extn", extn);
                    op.execute();

                    if (op.getResult() != null) {
                        path = op.getResult().toString();
                    }
                    System.out.println("extn " + extn);
                    //write files in the file system.

                    in = files.get(i).getInputStream();
                    System.out.println(files.get(i).getInputStream());

                    System.out.println("write file at " + path + extn);
                    out = new FileOutputStream(path + extn);
                    byte[] buffer = new byte[8192];
                    int bytesRead = 0;

                    while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                        out.write(buffer, 0, bytesRead);
                        // out.flush();
                    }
                    out.flush();
                    UploadedFile = null;
                    //call commit after checking all validations
                    OperationBinding obCommit = getBindings().getOperationBinding("Commit");
                    obCommit.execute();
                    ///   ADFUtils.findOperation("Commit").execute();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    UploadedFile = null;
                }
            }
        }
        System.out.println("--------------------5");
        //remove the files to prepare for new uploads
        setUploadedFile(null);
        OperationBinding obExecute = getBindings().getOperationBinding("Execute2");
        obExecute.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(imgTableBind);
        //ADFUtils.findOperation("Execute5").execute();
        return "null";
    }

    public void downloadFileListener(FacesContext facesContext, OutputStream outputStream) {
        //Read file from particular path, path bind is binding of table field that contains path
        //File filed = new File(pathPgBind.getValue().toString());
        // String path = JSFUtils.resolveExpressionAsString("#{row.AttchFlPath}");
        RichInputText bind = this.getFileBindName();
        if (bind != null) {
            System.out.println("come ----- 1");
            String path = bind.getValue().toString();

            System.out.println("path is:  " + path);
            File file = new File(path);
            FileInputStream fis = null;
            byte[] b;
            try {
                fis = new FileInputStream(file);
                int n;
                while ((n = fis.available()) > 0) {
                    b = new byte[n];
                    int result = fis.read(b);
                    outputStream.write(b, 0, b.length);
                    if (result == -1)
                        break;
                }
                outputStream.flush();
            } catch (IOException e) {
                //    JSFUtils.addFacesErrorMessage("Problem in downloading a file");
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setFileBindName(RichInputText fileBindName) {
        this.fileBindName = fileBindName;
    }

    public RichInputText getFileBindName() {
        return fileBindName;
    }

    public void deleteAttachAction(ActionEvent actionEvent) {
        // Add event code here...
        getBindings().getOperationBinding("Delete2").execute();
        getBindings().getOperationBinding("Commit").execute();
        getBindings().getOperationBinding("Execute2").execute();
        UploadedFile = null;
    }

    public void setImgTableBind(RichTable imgTableBind) {
        this.imgTableBind = imgTableBind;
    }

    public RichTable getImgTableBind() {
        return imgTableBind;
    }

    public void viewHistoryAction(ActionEvent ae) {
        // Add event code here...  histPopBind
        showPopup(histPopBind, true);
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
        String p_org_id = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Date date = new Date();
        date = (Date) date.getCurrentDate();

        Row currPo = getAm().getMmQuot1().getCurrentRow();
        if (currPo.getAttribute("DocDt") != null) {
            Date dt = (Date) currPo.getAttribute("DocDt");
            Integer fyid = (Integer) (callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] {
                                                         p_cldId, p_org_id, dt
            }));
            //getAm().getMmQuotItm()
            Row currItm = getAm().getMmQuotItm().getCurrentRow();
            if (currItm.getAttribute("ItmId") != null) {
                //apply criteria of doc id
                /*  String Doc = getAm().getMmDrftPo().getCurrentRow().getAttribute("DocId").toString();
                    ViewObject vo = getAm().getMmPendOrdVw();
                    ViewCriteria vc = getAm().getMmPendOrdVw().getViewCriteria("MmPendOrdVwVOCriteria");
                    vo.applyViewCriteria(vc);
                    vo.setNamedWhereClauseParam("itmIdBindvar", currItm.getAttribute("ItmId"));
                    vo.executeQuery(); */
                String itmId = currItm.getAttribute("ItmId").toString();
                callStoredFunctionHistory("MM.PKG_MM_STK.GET_STK(?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                          SlocId, p_cldId, itmId, fyid, p_org_id, date, null
                });
            } else {
                String msg2 = resolvEl("#{bundle['MSG.310']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message2);
            }
        } else {
            String msg2 = resolvElDCMsg("Select Quoatation Date").toString(); //Select PO Date
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
    }


    public void setHistPopBind(RichPopup histPopBind) {
        this.histPopBind = histPopBind;
    }

    public RichPopup getHistPopBind() {
        return histPopBind;
    }

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

                setTotStk((BigDecimal) st.getObject(5));
                setResStk((BigDecimal) (st.getObject(6)));
                setOrdStk((BigDecimal) st.getObject(7));
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

    public void setConvFactBind(RichInputText convFactBind) {
        this.convFactBind = convFactBind;
    }

    public RichInputText getConvFactBind() {
        return convFactBind;
    }

    public void setFlxTableBind(RichTable flxTableBind) {
        this.flxTableBind = flxTableBind;
    }

    public RichTable getFlxTableBind() {
        return flxTableBind;
    }
}
