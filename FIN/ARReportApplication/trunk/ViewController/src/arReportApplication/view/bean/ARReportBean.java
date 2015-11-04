package arReportApplication.view.bean;

import adf.utils.bean.ADFBeanUtils;

import arReportApplication.model.services.ARReportsAMImpl;

import arReportApplication.model.views.AddsValForStrucLOVImpl;

import com.tangosol.dev.compiler.java.BooleanExpression;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;

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

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import javax.sql.DataSource;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;


import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;


public class ARReportBean {
    private Boolean DebtorAgeingCons_link = false;
    private Boolean DebtorAgeingConsSum_Link = false;
    private Boolean supplierAgeing_Link = false;
    private Boolean statement_Link = false;
    private Boolean pendingAdvance_Link = false;
    private Boolean pendingBills_Link = false;
    private Boolean coaReport_Link = false;
    private Boolean billKnockoff_Link = false;
    private Boolean topNCreditors = false;
    private Boolean consolidate_Statement_Link = false;
    private Boolean DebtorAgeingSummary_Link = false;
    private Boolean DownloadReportLink = false;
    private Boolean DebtorAgeingCons = false;
    private Boolean DebtorAgeingConsSum = false;
    private Boolean supplierAgeing = false;
    private Boolean statement = false;
    private Boolean pendingAdvance = false;
    private Boolean pendingBills = false;
    private Boolean coaReport = false;
    private Boolean billKnockoff = false;
    private Boolean topNCreditorsVal = false;
    private Boolean consolidate_Statement = false;
    private RichInputDate startDate;
    private RichInputDate endDate;
    private Boolean DebtorAgeingSummary = false;
    private Boolean DownloadReport = false;
    private String check;
    private RichSelectOneRadio vouchers;
    private String vouchertype = "P";
    private String dateselect = "V";
    private String LineSel = "L";
    private String AmtSel = "O";
    private RichSelectBooleanCheckbox supplierAgeingBV;
    private RichInputDate asOnDateBV;
    private String AsOnDate = null;

    private String CurrencyChange = "T";
    private String AmountSelect = "O";

    private Boolean DebtorAgeingConsSumBktUsr = false;
    private Boolean DebtorAgeingSummaryBktUsr = false;

    private Boolean DebtorAgeingConsBktUsr_Link = false;
    private Boolean DebtorAgeingConsSumBktUsr_Link = false;


    private RichSelectBooleanCheckbox debtorAgeingBV;
    private RichSelectBooleanCheckbox debtorAgeingConsBindVar;
    private RichSelectBooleanCheckbox debtorAgeingConsSumBindVar;
    private RichSelectOneRadio batchFor;
    private RichPanelGroupLayout structPnlGrpBind;
    private RichTable strucTabBind;
    private RichOutputText locIdPgBind;
    private RichSelectBooleanCheckbox chkPgBind;
    private RichSelectBooleanCheckbox downloadedRptCBPgBind;
    private RichLink downloadedRptLink;
    private RichInputListOfValues rptNmeTransPgLOVBind;
    private RichSelectBooleanCheckbox marginCBPgBind;


    CallableStatement cstmt = null;
    PreparedStatement pstmt = null;

    private Integer range1 = 0;
    private Integer range2 = 0;
    private Integer range3 = 0;
    private Integer range4 = 0;
    private Integer range5 = 0;

    private int rangeVal1;
    private int rangeVal2;
    private int rangeVal3;
    private int rangeVal4;
    private int rangeVal5;
    private RichInputText range1PgBind;
    private RichInputText range2PgBind;
    private RichInputText range3PgBind;
    private RichInputText range4PgBind;
    private RichInputText range5PgBind;
    private RichLink saveLinkPgBind;
    private RichSelectBooleanCheckbox arStChkPgBind;
    private RichSelectBooleanCheckbox arBillKnockOffChkBind;

    public ARReportBean() {
        this.fetchData();
        System.out.println("Fetch method called...");

    }

    public void setRange1(Integer range1) {
        this.range1 = range1;
    }

    public Integer getRange1() {
        return range1;
    }

    public void setRange2(Integer range2) {
        this.range2 = range2;
    }

    public Integer getRange2() {
        return range2;
    }

    public void setRange3(Integer range3) {
        this.range3 = range3;
    }

    public Integer getRange3() {
        return range3;
    }

    public void setRange4(Integer range4) {
        this.range4 = range4;
    }

    public Integer getRange4() {
        return range4;
    }

    public void setRange5(Integer range5) {
        this.range5 = range5;
    }

    public Integer getRange5() {
        return range5;
    }

    public void setRangeVal1(int rangeVal1) {
        this.rangeVal1 = rangeVal1;
    }

    public int getRangeVal1() {
        return rangeVal1;
    }

    public void setRangeVal2(int rangeVal2) {
        this.rangeVal2 = rangeVal2;
    }

    public int getRangeVal2() {
        return rangeVal2;
    }

    public void setRangeVal3(int rangeVal3) {
        this.rangeVal3 = rangeVal3;
    }

    public int getRangeVal3() {
        return rangeVal3;
    }

    public void setRangeVal4(int rangeVal4) {
        this.rangeVal4 = rangeVal4;
    }

    public int getRangeVal4() {
        return rangeVal4;
    }

    public void setRangeVal5(int rangeVal5) {
        this.rangeVal5 = rangeVal5;
    }

    public int getRangeVal5() {
        return rangeVal5;
    }

    public void setDebtorAgeingConsSumBktUsr(Boolean DebtorAgeingConsSumBktUsr) {
        this.DebtorAgeingConsSumBktUsr = DebtorAgeingConsSumBktUsr;
    }

    public Boolean getDebtorAgeingConsSumBktUsr() {
        return DebtorAgeingConsSumBktUsr;
    }

    public void setDebtorAgeingSummaryBktUsr(Boolean DebtorAgeingSummaryBktUsr) {
        this.DebtorAgeingSummaryBktUsr = DebtorAgeingSummaryBktUsr;
    }

    public Boolean getDebtorAgeingSummaryBktUsr() {
        return DebtorAgeingSummaryBktUsr;
    }

    public void setDebtorAgeingConsBktUsr_Link(Boolean DebtorAgeingConsBktUsr_Link) {
        this.DebtorAgeingConsBktUsr_Link = DebtorAgeingConsBktUsr_Link;
    }

    public Boolean getDebtorAgeingConsBktUsr_Link() {
        return DebtorAgeingConsBktUsr_Link;
    }

    public void setDebtorAgeingConsSumBktUsr_Link(Boolean DebtorAgeingConsSumBktUsr_Link) {
        this.DebtorAgeingConsSumBktUsr_Link = DebtorAgeingConsSumBktUsr_Link;
    }

    public Boolean getDebtorAgeingConsSumBktUsr_Link() {
        return DebtorAgeingConsSumBktUsr_Link;
    }


    public void supplierAgeingChangeListner(ValueChangeEvent vcesa) {
        String a = vcesa.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            supplierAgeingBV.setValue(true);
            // System.out.println("a----->"+a);
            if (a.equalsIgnoreCase("true")) {
                supplierAgeing = true;
                //System.out.println(supplierAgeing);

            }
        }

        else {
            supplierAgeing = false;
            //System.out.println(supplierAgeing);
        }
    }

    public void statementChangeListner(ValueChangeEvent vcest) {
        String a = vcest.getNewValue().toString();
        Object flag = vouchers.getValue();
        if (a.equalsIgnoreCase("true") && flag.toString().equalsIgnoreCase("P")) {
            statement = true;
        } else {
            arStChkPgBind.setValue(Boolean.FALSE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(arStChkPgBind);
            statement = false;
        }

    }

    public void pendingAdvanceChangeListner(ValueChangeEvent vcepa) {
        String a = vcepa.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            pendingAdvance = true;

        } else {
            pendingAdvance = false;
        }

    }

    public void pendingBillsChangeListner(ValueChangeEvent vcepb) {
        String a = vcepb.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            pendingBills = true;

        } else {
            pendingBills = false;
        }

    }

    public void Consolidate_Statement_ChangeListener(ValueChangeEvent vcecs) {
        String a = vcecs.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            consolidate_Statement = true;

        } else {
            consolidate_Statement = false;
        }
    }

    public void coaChangeListener(ValueChangeEvent vcecoa) {
        String a = vcecoa.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            coaReport = true;

        } else {
            coaReport = false;
        }
    }

    public void billKnockoffChangeListener(ValueChangeEvent vcebko) {
        String a = vcebko.getNewValue().toString();
        Object flag = vouchers.getValue();
        if (a.equalsIgnoreCase("true") && flag.toString().equalsIgnoreCase("P")) {
            billKnockoff = true;
        } else {
            arBillKnockOffChkBind.setValue(Boolean.FALSE);
            billKnockoff = false;
        }
    }

    public void topNCreditors(ValueChangeEvent vcetnc) {
        String a = vcetnc.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            topNCreditorsVal = true;

        } else {
            topNCreditorsVal = false;
        }
    }

    public String GenerateReportAction() {


        if (supplierAgeing == true) {
            // System.out.println("inside supplierAgeing==true ");
            if (asOnDateBV.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                setSupplierAgeing_Link(true);
            }
        } else {
            setSupplierAgeing_Link(false);
        }


        if (statement == true) {
            if (startDate.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                if (endDate.getValue() == null) {
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    setStatement_Link(true);
                    if (marginCBPgBind.getValue() != null) {
                        if (marginCBPgBind.getValue().equals(true)) {
                            check = "Y";
                        } else {
                            check = "N";
                        }
                    }
                }
            }

        } else {
            setStatement_Link(false);
        }
        if (pendingAdvance == true) {


            if (startDate.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {


                if (endDate.getValue() == null) {
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    setPendingAdvance_Link(true);
                }
            }
        } else {
            setPendingAdvance_Link(false);
        }


        if (pendingBills == true) {

            if (startDate.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {


                if (endDate.getValue() == null) {
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    setPendingBills_Link(true);
                }
            }

        } else {
            setPendingBills_Link(false);
        }


        if (coaReport == true) {


            if (startDate.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {


                if (endDate.getValue() == null) {
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    setCoaReport_Link(true);
                }
            }

        } else {
            setCoaReport_Link(false);
        }


        if (billKnockoff == true) {
            if (startDate.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                if (endDate.getValue() == null) {
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    setBillKnockoff_Link(true);
                }
            }


        } else {
            setBillKnockoff_Link(false);
        }


        if (topNCreditorsVal == true) {


            if (startDate.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {


                if (endDate.getValue() == null) {
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    setTopNCreditors(true);
                }
            }

        } else {
            setTopNCreditors(false);
        }


        if (consolidate_Statement == true) {

            if (startDate.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {


                if (endDate.getValue() == null) {
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    setConsolidate_Statement_Link(true);
                }
            }

        } else {
            setConsolidate_Statement_Link(false);
        }


        if (DebtorAgeingSummary == true) {

            if (asOnDateBV.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }

            else {
                setDebtorAgeingSummary_Link(true);
            }


        } else {
            setDebtorAgeingSummary_Link(false);
        }

        if (DebtorAgeingCons == true) {
            // System.out.println("inside supplierAgeing==true ");
            if (asOnDateBV.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                setDebtorAgeingCons_link(true);
            }
        } else {
            setDebtorAgeingCons_link(false);
        }
        if (DebtorAgeingConsSum == true) {
            // System.out.println("inside supplierAgeing==true ");
            if (asOnDateBV.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                setDebtorAgeingConsSum_Link(true);
            }
        } else {
            setDebtorAgeingConsSum_Link(false);
        }
        if (DownloadReport == true) {
            // System.out.println("inside supplierAgeing==true ");

            setDownloadReportLink(true);

        } else {
            setDownloadReportLink(false);
        }
        if (DebtorAgeingSummaryBktUsr == true) {
            if (asOnDateBV.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                setDebtorAgeingConsBktUsr_Link(true);
            }
        } else {
            setDebtorAgeingConsBktUsr_Link(false);
        }


        if (DebtorAgeingConsSumBktUsr == true) {
            if (asOnDateBV.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                setDebtorAgeingConsSumBktUsr_Link(true);
            }
        } else {
            setDebtorAgeingConsSumBktUsr_Link(false);
        }


        return null;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getCheck() {
        return check;
    }

    public void setDownloadReportLink(Boolean DownloadReportLink) {
        this.DownloadReportLink = DownloadReportLink;
    }

    public Boolean getDownloadReportLink() {
        return DownloadReportLink;
    }


    public void setSupplierAgeing_Link(Boolean supplierAgeing_Link) {
        this.supplierAgeing_Link = supplierAgeing_Link;
    }

    public Boolean getSupplierAgeing_Link() {
        return supplierAgeing_Link;
    }

    public void setStatement_Link(Boolean statement_Link) {
        this.statement_Link = statement_Link;
    }

    public Boolean getStatement_Link() {
        return statement_Link;
    }

    public void setPendingAdvance_Link(Boolean pendingAdvance_Link) {
        this.pendingAdvance_Link = pendingAdvance_Link;
    }

    public Boolean getPendingAdvance_Link() {
        return pendingAdvance_Link;
    }

    public void setPendingBills_Link(Boolean pendingBills_Link) {
        this.pendingBills_Link = pendingBills_Link;
    }

    public Boolean getPendingBills_Link() {
        return pendingBills_Link;
    }

    public void setCoaReport_Link(Boolean coaReport_Link) {
        this.coaReport_Link = coaReport_Link;
    }

    public Boolean getCoaReport_Link() {
        return coaReport_Link;
    }

    public void setBillKnockoff_Link(Boolean billKnockoff_Link) {
        this.billKnockoff_Link = billKnockoff_Link;
    }

    public Boolean getBillKnockoff_Link() {
        return billKnockoff_Link;
    }

    public void setTopNCreditors(Boolean topNCreditors) {
        this.topNCreditors = topNCreditors;
    }

    public Boolean getTopNCreditors() {
        return topNCreditors;
    }

    public void setConsolidate_Statement_Link(Boolean consolidate_Statement_Link) {
        this.consolidate_Statement_Link = consolidate_Statement_Link;
    }

    public Boolean getConsolidate_Statement_Link() {
        return consolidate_Statement_Link;
    }

    public void setStartDate(RichInputDate startDate) {
        this.startDate = startDate;
    }

    public RichInputDate getStartDate() {
        return startDate;
    }

    public void setEndDate(RichInputDate endDate) {
        this.endDate = endDate;
    }

    public RichInputDate getEndDate() {
        return endDate;
    }

    public void debtorAgeingSummCjangeLst(ValueChangeEvent vcesa) {
        /* String a=vce.getNewValue().toString();
        if (a.equalsIgnoreCase("true")){
            DebtorAgeingSummary_Link = true;

        }
        else {
            DebtorAgeingSummary_Link = false;
        } */
        String a = vcesa.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            debtorAgeingBV.setValue(true);
            // System.out.println("a----->"+a);
            if (a.equalsIgnoreCase("true")) {
                DebtorAgeingSummary = true;
                //System.out.println(supplierAgeing);

            }
        }

        else {
            DebtorAgeingSummary = false;
            //System.out.println(supplierAgeing);
        }
    }

    public void setDebtorAgeingSummary_Link(Boolean DebtorAgeingSummary_Link) {
        this.DebtorAgeingSummary_Link = DebtorAgeingSummary_Link;
    }

    public Boolean getDebtorAgeingSummary_Link() {
        return DebtorAgeingSummary_Link;
    }

    public void setDebtorAgeingSummary(Boolean DebtorAgeingSummary) {
        this.DebtorAgeingSummary = DebtorAgeingSummary;
    }

    public Boolean getDebtorAgeingSummary() {
        return DebtorAgeingSummary;
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

    /* public Object resolvEl(String data) {

        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext).toString();

        return Message;
    }  */

    public void voucherTypeVLC(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            vouchertype = valueChangeEvent.getNewValue().toString();
            //System.out.println(vouchertype);
        } else {
            vouchertype = "P";
        }
    }

    public void dateForAgeingValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            dateselect = valueChangeEvent.getNewValue().toString();
            //System.out.println("dateselect-----------"+dateselect);

        } else {
            dateselect = "V";
        }
    }

    public void amtRangeSelectionForValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            AmtSel = valueChangeEvent.getNewValue().toString();
            //System.out.println("AmtSel---------"+AmtSel);
        } else {
            AmtSel = "O";
        }
    }

    public void amtRangeForValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            LineSel = valueChangeEvent.getNewValue().toString();
            // System.out.println("linesel---------"+LineSel);
        } else {
            LineSel = "L";
        }
    }

    public void setVouchers(RichSelectOneRadio vouchers) {
        this.vouchers = vouchers;
    }

    public RichSelectOneRadio getVouchers() {
        return vouchers;
    }

    public void setVouchertype(String vouchertype) {
        this.vouchertype = vouchertype;
    }

    public String getVouchertype() {
        return vouchertype;
    }

    public void dateBasisForRankingVLC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void valueBasedOnVLC(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            AmountSelect = valueChangeEvent.getNewValue().toString();
        } else {
            AmountSelect = "O";
        }
    }

    public void setDateselect(String dateselect) {
        this.dateselect = dateselect;
    }

    public String getDateselect() {
        return dateselect;
    }

    public void setAmtSel(String AmtSel) {
        this.AmtSel = AmtSel;
    }

    public String getAmtSel() {
        return AmtSel;
    }

    public void setSupplierAgeingBV(RichSelectBooleanCheckbox supplierAgeingBV) {
        this.supplierAgeingBV = supplierAgeingBV;
    }

    public RichSelectBooleanCheckbox getSupplierAgeingBV() {
        return supplierAgeingBV;
    }

    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public void setAsOnDateBV(RichInputDate asOnDateBV) {
        this.asOnDateBV = asOnDateBV;
        if (this.asOnDateBV.getValue() == null || this.asOnDateBV.getValue().toString().equals("")) {
            this.asOnDateBV.setValue(new Date().getCurrentDate());
        }
    }

    public RichInputDate getAsOnDateBV() {
        /* RichInputDate dt=new RichInputDate();
        System.out.println("RICH INPUT DATE : "+ dt);
        return (RichInputDate) (asOnDateBV == null ? dt : asOnDateBV); */
        return asOnDateBV;
    }

    public void setAsOnDate(String AsOnDate) {
        this.AsOnDate = AsOnDate;
    }

    public String getAsOnDate() {

        return AsOnDate;
    }

    public void asOnDateValueChangeListner(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            AsOnDate = valueChangeEvent.getNewValue().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            AsOnDate = sdf.format(valueChangeEvent.getNewValue());
            // System.out.println(AsOnDate+"--------------");

        } else {
            AsOnDate = Date.getCurrentDate().toString();
            // System.out.println(AsOnDate+"when null-----------");

        }
    }

    public void setLineSel(String LineSel) {
        this.LineSel = LineSel;
    }

    public String getLineSel() {
        return LineSel;
    }

    public void currencyGroupingValuechangeListner(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            CurrencyChange = valueChangeEvent.getNewValue().toString();
            // System.out.println(CurrencyChange + "----------------currrencychange");

        } else {
            CurrencyChange = "T";
        }
    }

    public void setCurrencyChange(String CurrencyChange) {
        this.CurrencyChange = CurrencyChange;
    }

    public String getCurrencyChange() {
        return CurrencyChange;
    }

    public void setAmountSelect(String AmountSelect) {
        this.AmountSelect = AmountSelect;
    }

    public String getAmountSelect() {
        return AmountSelect;
    }


    public void setDebtorAgeingBV(RichSelectBooleanCheckbox debtorAgeingBV) {
        this.debtorAgeingBV = debtorAgeingBV;
    }

    public RichSelectBooleanCheckbox getDebtorAgeingBV() {
        return debtorAgeingBV;
    }

    public void DebtorAgeingConsVCL(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            debtorAgeingConsBindVar.setValue(true);
            // System.out.println("a----->"+a);
            if (a.equalsIgnoreCase("true")) {
                DebtorAgeingCons = true;
                //System.out.println(supplierAgeing);

            }
        }

        else {
            DebtorAgeingCons = false;
            //System.out.println(supplierAgeing);
        }
    }

    public void setDebtorAgeingConsBindVar(RichSelectBooleanCheckbox debtorAgeingConsBindVar) {
        this.debtorAgeingConsBindVar = debtorAgeingConsBindVar;
    }

    public RichSelectBooleanCheckbox getDebtorAgeingConsBindVar() {
        return debtorAgeingConsBindVar;
    }

    public void DebtorAgeingConsSumVCL(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            debtorAgeingConsSumBindVar.setValue(true);
            // System.out.println("a----->"+a);
            if (a.equalsIgnoreCase("true")) {
                DebtorAgeingConsSum = true;
                //System.out.println(supplierAgeing);

            }
        }

        else {
            DebtorAgeingConsSum = false;
            //System.out.println(supplierAgeing);
        }
    }

    public void setDebtorAgeingConsSumBindVar(RichSelectBooleanCheckbox debtorAgeingConsSumBindVar) {
        this.debtorAgeingConsSumBindVar = debtorAgeingConsSumBindVar;
    }

    public RichSelectBooleanCheckbox getDebtorAgeingConsSumBindVar() {
        return debtorAgeingConsSumBindVar;
    }

    public void setDebtorAgeingCons(Boolean DebtorAgeingCons) {
        this.DebtorAgeingCons = DebtorAgeingCons;
    }

    public Boolean getDebtorAgeingCons() {
        return DebtorAgeingCons;
    }

    public void setDebtorAgeingConsSum(Boolean DebtorAgeingConsSum) {
        this.DebtorAgeingConsSum = DebtorAgeingConsSum;
    }

    public Boolean getDebtorAgeingConsSum() {
        return DebtorAgeingConsSum;
    }

    public void setDebtorAgeingCons_link(Boolean DebtorAgeingCons_link) {
        this.DebtorAgeingCons_link = DebtorAgeingCons_link;
    }

    public Boolean getDebtorAgeingCons_link() {
        return DebtorAgeingCons_link;
    }

    public void setDebtorAgeingConsSum_Link(Boolean DebtorAgeingConsSum_Link) {
        this.DebtorAgeingConsSum_Link = DebtorAgeingConsSum_Link;
    }

    public Boolean getDebtorAgeingConsSum_Link() {
        return DebtorAgeingConsSum_Link;
    }

    public void setBatchFor(RichSelectOneRadio batchFor) {
        this.batchFor = batchFor;
    }

    public RichSelectOneRadio getBatchFor() {
        return batchFor;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void onStructChangeVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("onStructureChange");
            binding.getParamsMap().put("structId", valueChangeEvent.getNewValue().toString());
            binding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(structPnlGrpBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(strucTabBind);
        }
    }

    public void setStructPnlGrpBind(RichPanelGroupLayout structPnlGrpBind) {
        this.structPnlGrpBind = structPnlGrpBind;
    }

    public RichPanelGroupLayout getStructPnlGrpBind() {
        return structPnlGrpBind;
    }

    public void setStrucTabBind(RichTable strucTabBind) {
        this.strucTabBind = strucTabBind;
    }

    public RichTable getStrucTabBind() {
        return strucTabBind;
    }

    public void changeLocIdVCL(ValueChangeEvent valueChangeEvent) {
        String locId = null;
        ARReportsAMImpl am = (ARReportsAMImpl) resolvElDC("ARReportsAMDataControl");
        (am.getAddsValForStrucLOV1()).rowUpdate = "Y";

        if (valueChangeEvent.getNewValue() != null &&
            ((valueChangeEvent.getNewValue().toString()).trim()).length() > 0) {
            System.out.println("New Value is " + valueChangeEvent.getNewValue().toString());

            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            OperationBinding op = this.getBindings().getOperationBinding("setParentLocId");

            op.getParamsMap().put("parentLocId", valueChangeEvent.getNewValue().toString());
            op.execute();

            ViewObjectImpl vo = am.getAddsValForStrucLOV1();
            locId = vo.getCurrentRow().getAttribute("LocId").toString();
            System.out.println("LOC ID___________________________" + locId);

            AdfFacesContext.getCurrentInstance().addPartialTarget(strucTabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(locIdPgBind);
        } else if (((valueChangeEvent.getNewValue().toString()).trim()).length() == 0) {
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

            /* OperationBinding op = this.getBindings().getOperationBinding("setChildInNullCase");
            op.execute(); */
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.strucTabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(locIdPgBind);
            ;
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

    public void setLocIdPgBind(RichOutputText locIdPgBind) {
        this.locIdPgBind = locIdPgBind;
    }

    public RichOutputText getLocIdPgBind() {
        return locIdPgBind;
    }

    public void chkAddsVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equals(Boolean.TRUE)) {
                FacesContext fctx = FacesContext.getCurrentInstance();
                String refreshpage = fctx.getViewRoot().getViewId();
                ViewHandler ViewH = fctx.getApplication().getViewHandler();
                UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
                UIV.setViewId(refreshpage);
                fctx.setViewRoot(UIV);
            }
            /* getBindings().getOperationBinding("chkOperation").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(chkPgBind); */
            AdfFacesContext.getCurrentInstance().addPartialTarget(strucTabBind);
        }
    }

    public void setChkPgBind(RichSelectBooleanCheckbox chkPgBind) {
        this.chkPgBind = chkPgBind;
    }

    public RichSelectBooleanCheckbox getChkPgBind() {
        return chkPgBind;
    }

    public void listenAddressChangeVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setChild").execute();
        if (chkPgBind.getValue() != null) {
            chkPgBind.setValue(Boolean.FALSE);

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(chkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(strucTabBind);
    }

    public void setDownloadedRptCBPgBind(RichSelectBooleanCheckbox downloadedRptCBPgBind) {
        this.downloadedRptCBPgBind = downloadedRptCBPgBind;
    }

    public RichSelectBooleanCheckbox getDownloadedRptCBPgBind() {
        return downloadedRptCBPgBind;
    }

    public void downloadedRptCBVCL(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            downloadedRptCBPgBind.setValue(true);
            // System.out.println("a----->"+a);
            if (a.equalsIgnoreCase("true")) {
                DownloadReport = true;
                //System.out.println(supplierAgeing);

            }
        }

        else {
            DownloadReport = false;
            //System.out.println(supplierAgeing);
        }
    }

    public void setDownloadedRptLink(RichLink downloadedRptLink) {
        this.downloadedRptLink = downloadedRptLink;
    }

    public RichLink getDownloadedRptLink() {
        return downloadedRptLink;
    }

    public void setRptNmeTransPgLOVBind(RichInputListOfValues rptNmeTransPgLOVBind) {
        this.rptNmeTransPgLOVBind = rptNmeTransPgLOVBind;
    }

    public RichInputListOfValues getRptNmeTransPgLOVBind() {
        return rptNmeTransPgLOVBind;
    }

    public void setMarginCBPgBind(RichSelectBooleanCheckbox marginCBPgBind) {
        this.marginCBPgBind = marginCBPgBind;
    }

    public RichSelectBooleanCheckbox getMarginCBPgBind() {
        return marginCBPgBind;
    }

    public void marginCBVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    /** This method is called for fetching ageing save data. called in constructor. **/

    public void fetchData() {
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        int userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/FINDS");
            Connection conn = ds.getConnection();
            //   stmt = conn.createStatement();
            String selectStmt =
                "select usr_id, BKT_DAYS_TO, BKT_RANGE2, BKT_RANGE3, BKT_RANGE4, BKT_RANGE5 from mm.mm$ag$bkt " +
                "where cld_id = ? and sloc_id = ? and org_id = ? and usr_id = ? and BKT_FOR='C'";
            pstmt = conn.prepareStatement(selectStmt);
            pstmt.setString(1, cldId);
            pstmt.setInt(2, slocId);
            pstmt.setString(3, orgId);
            pstmt.setInt(4, userId);
            System.out.println("The SQL query is: " + selectStmt + "--" + orgId + userId);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("The records selected are:");
            int rowCount = 0;
            while (rs.next()) { // Move the cursor to the next row
                userId = rs.getInt("usr_id");
                range1 = rs.getInt("BKT_DAYS_TO");
                range2 = rs.getInt("BKT_RANGE2");
                range3 = rs.getInt("BKT_RANGE3");
                range4 = rs.getInt("BKT_RANGE4");
                range5 = rs.getInt("BKT_RANGE5");
                System.out.println(range1 + ", " + userId + ", " + range2 + "," + range3 + "," + range4 + "," + range5);
                ++rowCount;
            }
            System.out.println("Total number of records = " + rowCount);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveData() throws NamingException, SQLException {
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        int userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String bucketFor = "C";

        rangeVal1 = Integer.parseInt(range1PgBind.getValue().toString());
        rangeVal2 = Integer.parseInt(range2PgBind.getValue().toString());
        rangeVal3 = Integer.parseInt(range3PgBind.getValue().toString());
        rangeVal4 = Integer.parseInt(range4PgBind.getValue().toString());
        rangeVal5 = Integer.parseInt(range5PgBind.getValue().toString());

        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/FINDS");
        Connection conn = ds.getConnection();

        try {
            cstmt = conn.prepareCall("{?=call MM.MM_INS_AG_BKT(?,?,?,?,?,?,?,?,?,?)}");
            cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
            cstmt.setString(2, cldId);
            cstmt.setInt(3, slocId);
            cstmt.setString(4, orgId);
            cstmt.setInt(5, userId);
            cstmt.setString(6, bucketFor);
            cstmt.setInt(7, rangeVal1);
            cstmt.setInt(8, rangeVal2);
            cstmt.setInt(9, rangeVal3);
            cstmt.setInt(10, rangeVal4);
            cstmt.setInt(11, rangeVal5);

            System.out.println("inside save data insert values--" + cldId + slocId + orgId + userId + rangeVal1 +
                               rangeVal2 + rangeVal3 + rangeVal4 + rangeVal5 + bucketFor);
            cstmt.executeUpdate();
            conn.setAutoCommit(true);

            System.out.println("After execute.");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cstmt != null) {
                    cstmt.close();
                }
                /*    if( conn!=null){
                                  conn.close();
                                  }
                            */
            } catch (Exception ex) {
            }
        }

    }

    /** Used for saving ageing details in MM$AG$BKT table. **/
    public void saveAgeing(ActionEvent actionEvent) throws NamingException, SQLException {
        rangeVal1 = Integer.parseInt(range1PgBind.getValue().toString());
        rangeVal2 = Integer.parseInt(range2PgBind.getValue().toString());
        rangeVal3 = Integer.parseInt(range3PgBind.getValue().toString());
        rangeVal4 = Integer.parseInt(range4PgBind.getValue().toString());
        rangeVal5 = Integer.parseInt(range5PgBind.getValue().toString());

        if (rangeVal1 < rangeVal2) {
            if ((rangeVal2 < rangeVal3) && (rangeVal2 < rangeVal4) && (rangeVal2 < rangeVal5)) {
                if ((rangeVal3 < rangeVal4) && (rangeVal3 < rangeVal5)) {
                    if (rangeVal4 < rangeVal5) {
                        this.saveData();
                        FacesMessage message = new FacesMessage("Ageing Bucket updated successfully...!");
                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                        saveLinkPgBind.setDisabled(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);

                    } else {
                        System.out.println("Inside Else...");
                        AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(range2PgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(range3PgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(range4PgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(range5PgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);

                        FacesMessage message =
                            new FacesMessage("Please enter a valid value, greater than initial value and less than next value.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(range2PgBind.getClientId(), message);
                        saveLinkPgBind.setDisabled(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                        saveLinkPgBind.setDisabled(false);

                    }
                } else {
                    System.out.println("Inside Else...");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(range2PgBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(range3PgBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(range4PgBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(range5PgBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);

                    FacesMessage message =
                        new FacesMessage("Please enter a valid value, greater than initial value and less than next value.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(range2PgBind.getClientId(), message);
                    saveLinkPgBind.setDisabled(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                    saveLinkPgBind.setDisabled(false);
                }
            } else {
                System.out.println("Inside Else...");
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range2PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range3PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range4PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range5PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);

                FacesMessage message =
                    new FacesMessage("Please enter a valid value, greater than initial value and less than next value.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(range2PgBind.getClientId(), message);
                saveLinkPgBind.setDisabled(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                saveLinkPgBind.setDisabled(false);

            }
        } else {
            System.out.println("Inside Else...");
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range2PgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range3PgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range4PgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range5PgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);

            FacesMessage message =
                new FacesMessage("Please enter a valid value, greater than initial value and less than next value.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(range2PgBind.getClientId(), message);
            saveLinkPgBind.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
            saveLinkPgBind.setDisabled(false);

        }

    }

    public void setRange1PgBind(RichInputText range1PgBind) {
        this.range1PgBind = range1PgBind;
    }

    public RichInputText getRange1PgBind() {
        return range1PgBind;
    }

    public void setRange2PgBind(RichInputText range2PgBind) {
        this.range2PgBind = range2PgBind;
    }

    public RichInputText getRange2PgBind() {
        return range2PgBind;
    }

    public void setRange3PgBind(RichInputText range3PgBind) {
        this.range3PgBind = range3PgBind;
    }

    public RichInputText getRange3PgBind() {
        return range3PgBind;
    }

    public void setRange4PgBind(RichInputText range4PgBind) {
        this.range4PgBind = range4PgBind;
    }

    public RichInputText getRange4PgBind() {
        return range4PgBind;
    }

    public void setRange5PgBind(RichInputText range5PgBind) {
        this.range5PgBind = range5PgBind;
    }

    public RichInputText getRange5PgBind() {
        return range5PgBind;
    }

    public void debtorAgSummBktUsrVCE(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            DebtorAgeingSummaryBktUsr = true;

        } else {
            DebtorAgeingSummaryBktUsr = false;
        }
    }

    public void debtorAgSummConsBktUsrVCE(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            DebtorAgeingConsSumBktUsr = true;

        } else {
            DebtorAgeingConsSumBktUsr = false;
        }
    }

    public void setSaveLinkPgBind(RichLink saveLinkPgBind) {
        this.saveLinkPgBind = saveLinkPgBind;
    }

    public RichLink getSaveLinkPgBind() {
        return saveLinkPgBind;
    }

    public void range1VCL(ValueChangeEvent valueChangeEvent) {
        saveLinkPgBind.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(range2PgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(range3PgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(range4PgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(range5PgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
    }

    public void range2VCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (range2PgBind.getValue().toString() != null) {

                AdfFacesContext.getCurrentInstance().addPartialTarget(range1PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range3PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range4PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range5PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);

                int rangeVal2Bind = Integer.parseInt(range2PgBind.getValue().toString());
                int rangeVal1Bind = Integer.parseInt(range1PgBind.getValue().toString());
                int rangeVal3Bind = Integer.parseInt(range3PgBind.getValue().toString());
                int rangeVal4Bind = Integer.parseInt(range4PgBind.getValue().toString());
                int rangeVal5Bind = Integer.parseInt(range5PgBind.getValue().toString());

                if ((rangeVal2Bind > rangeVal1Bind) && (rangeVal2Bind < rangeVal3Bind) &&
                    (rangeVal2Bind < rangeVal4Bind) && (rangeVal2Bind < rangeVal5Bind)) {
                    rangeVal2 = Integer.parseInt(range2PgBind.getValue().toString());
                    saveLinkPgBind.setDisabled(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                    System.out.println("ok--" + rangeVal2Bind + "--" + rangeVal1Bind + "--" + rangeVal2 + "=" +
                                       rangeVal3Bind);
                } else {
                    FacesMessage message =
                        new FacesMessage("Please enter a valid value, greater than initial value and less than next value.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(range2PgBind.getClientId(), message);
                    saveLinkPgBind.setDisabled(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                }
            }
        }
    }

    public void range3VCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (range3PgBind.getValue().toString() != null) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(range2PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range1PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range4PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range5PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);

                int rangeVal2Bind = Integer.parseInt(range2PgBind.getValue().toString());
                int rangeVal3Bind = Integer.parseInt(range3PgBind.getValue().toString());
                int rangeVal4Bind = Integer.parseInt(range4PgBind.getValue().toString());
                int rangeVal1Bind = Integer.parseInt(range1PgBind.getValue().toString());
                int rangeVal5Bind = Integer.parseInt(range5PgBind.getValue().toString());

                if ((rangeVal3Bind > rangeVal2Bind) && (rangeVal3Bind > rangeVal1Bind) &&
                    (rangeVal3Bind < rangeVal4Bind) && (rangeVal3Bind < rangeVal5Bind)) {
                    rangeVal3 = rangeVal3Bind;
                    saveLinkPgBind.setDisabled(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                    System.out.println("ok--" + rangeVal3Bind + "--" + rangeVal2Bind + "--" + rangeVal3);
                } else {
                    FacesMessage message =
                        new FacesMessage("Please enter a valid value, greater than initial value and less than next value.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(range3PgBind.getClientId(), message);
                    saveLinkPgBind.setDisabled(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                }
            }
        }

    }

    public void range4VCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (range4PgBind.getValue().toString() != null) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(range2PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range3PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range1PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range5PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);

                int rangeVal4Bind = Integer.parseInt(range4PgBind.getValue().toString());
                int rangeVal3Bind = Integer.parseInt(range3PgBind.getValue().toString());
                int rangeVal5Bind = Integer.parseInt(range5PgBind.getValue().toString());
                int rangeVal2Bind = Integer.parseInt(range2PgBind.getValue().toString());
                int rangeVal1Bind = Integer.parseInt(range1PgBind.getValue().toString());

                if ((rangeVal4Bind > rangeVal3Bind) && (rangeVal4Bind > rangeVal2Bind) &&
                    (rangeVal4Bind > rangeVal1Bind) && (rangeVal4Bind < rangeVal5Bind)) {
                    rangeVal4 = rangeVal4Bind;
                    saveLinkPgBind.setDisabled(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                    System.out.println("ok--" + rangeVal4Bind + "--" + rangeVal3Bind + "--" + rangeVal4);
                } else {
                    FacesMessage message =
                        new FacesMessage("Please enter a valid value, greater than initial value less than next value.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(range4PgBind.getClientId(), message);
                    saveLinkPgBind.setDisabled(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                }
            }
        }
    }

    public void range5VCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (range4PgBind.getValue().toString() != null) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(range2PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range3PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range4PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(range1PgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);

                int rangeVal5Bind = Integer.parseInt(range5PgBind.getValue().toString());
                int rangeVal4Bind = Integer.parseInt(range4PgBind.getValue().toString());
                int rangeVal3Bind = Integer.parseInt(range3PgBind.getValue().toString());
                int rangeVal2Bind = Integer.parseInt(range2PgBind.getValue().toString());
                int rangeVal1Bind = Integer.parseInt(range1PgBind.getValue().toString());
                if ((rangeVal5Bind > rangeVal4Bind) && (rangeVal5Bind > rangeVal3Bind) &&
                    (rangeVal5Bind > rangeVal2Bind) && (rangeVal5Bind > rangeVal1Bind)) {
                    rangeVal5 = rangeVal5Bind;
                    saveLinkPgBind.setDisabled(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                    System.out.println("ok--" + rangeVal5Bind + "--" + rangeVal4Bind + "--" + rangeVal5);
                } else {
                    FacesMessage message =
                        new FacesMessage("Please enter a valid value, greater than initial value less than next value.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(range5PgBind.getClientId(), message);
                    saveLinkPgBind.setDisabled(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkPgBind);
                }
            }
        }
    }


    public void debtorStatementValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Object postedFalg = vouchers.getValue();
        if (postedFalg.toString().equalsIgnoreCase("A")) {
            //System.out.println("Voucher Posted Falg is_____ "+postedFalg.toString());
            statement = false;
            arStChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(arStChkPgBind);
            FacesMessage message =
                new FacesMessage("Report can't be generated on Voucher Type All.Please select Voucher Type Posted to Continue...");
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(vouchers.getClientId(), message);
        }

    }

    public void setArStChkPgBind(RichSelectBooleanCheckbox arStChkPgBind) {
        this.arStChkPgBind = arStChkPgBind;
    }

    public RichSelectBooleanCheckbox getArStChkPgBind() {
        return arStChkPgBind;
    }

    public void arBillKnockOffValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Object flag = vouchers.getValue();
        if (flag.toString().equalsIgnoreCase("A")) {
            billKnockoff = false;
            arBillKnockOffChkBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(arBillKnockOffChkBind);
            /* FacesMessage message =
                    new FacesMessage("Invoice Knock-Off can't be generated on Voucher Type All.Please select Voucher Type Posted to Continue...");
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(vouchers.getClientId(), message); */
        }

    }

    public void setArBillKnockOffChkBind(RichSelectBooleanCheckbox arBillKnockOffChkBind) {
        this.arBillKnockOffChkBind = arBillKnockOffChkBind;
    }

    public RichSelectBooleanCheckbox getArBillKnockOffChkBind() {
        return arBillKnockOffChkBind;
    }

    public Map getUsrRptVisible() {
        return new HashMap<Integer, Boolean>() {
            @Override
            public Boolean get(Object key) {
                if (key != null) {
                    Boolean retVal = false;
                    OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkUsrRptSecAccess");
                    opChk.getParamsMap().put("rptId", key);
                    opChk.execute();
                    if (opChk.getResult() != null)
                        retVal = (Boolean) opChk.getResult();
                    return retVal;
                } else
                    return true;
            }
        };
    }
}
