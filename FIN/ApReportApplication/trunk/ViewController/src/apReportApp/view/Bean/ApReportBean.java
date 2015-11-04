package apReportApp.view.Bean;


import adf.utils.bean.ADFBeanUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import javax.sql.DataSource;

import javax.swing.text.DateFormatter;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.nav.RichGoLink;

import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichMessage;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Date;

import org.jfree.xml.factory.objects.SimpleDateFormatObjectDescription;

public class ApReportBean {
    private Boolean CreditorAgeingCons_Link = false;
    private Boolean CreditorAgeingConsSum_Link = false;

    private Boolean CreditorAgeingConsBktUsr_Link = false;
    private Boolean CreditorAgeingConsSumBktUsr_Link = false;

    private Boolean supplierAgeing_Link = false;
    private Boolean statement_Link = false;
    private Boolean pendingAdvance_Link = false;
    private Boolean pendingBills_Link = false;
    private Boolean billKnockoff_Link = false;
    private Boolean topNCreditors = false;
    private Boolean creditorsAgeingSummary_Link = false;
    private Boolean genericReport_Link = false;

    private Boolean genericReport = false;
    private Boolean CreditorAgeingConsSum = false;
    private Boolean CreditorAgeingConsSumBktUsr = false;
    private Boolean creditorsAgeingSummaryBktUsr = false;

    private Boolean CreditorAgeingCons = false;
    private Boolean supplierAgeing = false;
    private Boolean statement = false;
    private Boolean pendingAdvance = false;
    private Boolean pendingBills = false;
    private Boolean billKnockoff = false;
    private Boolean topNCreditorsVal = false;
    private Boolean creditorsAgeingSummary = false;


    private RichInputDate startDate;
    private RichInputDate endDate;
    private RichSelectOneRadio voucherTypeVLC;
    private String valueofN = null;
    private String postvalue = "P";
    private String dateForAgeing = "V";
    private RichSelectOneRadio dateForAgeingBV;
    private String amountRangeSelectionFor = "O";
    private String amountRangeForVLC = "L";
    private String asOnDate = null;

    private String dateRangeForRanking = "V";
    private String valueBasedON = "O";
    private String currencyGrouping = "T";
    private RichInputDate asOnDateBinding;
    private RichSelectOneRadio bucketFor;
    private RichInputListOfValues reportNameTransPgBind;
    private RichSelectBooleanCheckbox genericReportCBPgBind;
    private RichGoLink genericReportPgBind;
    private RichMessage messageInfoPgBind;
    private RichInputText range1PgBind;
    private RichInputText range2PgBind;
    private RichInputText range3PgBind;
    private RichInputText range4PgBind;
    private RichInputText range5PgBind;
    private RichLink saveLinkPgBind;

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

    PreparedStatement pstmt = null;

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

    public void setCreditorAgeingConsSumBktUsr(Boolean CreditorAgeingConsSumBktUsr) {
        this.CreditorAgeingConsSumBktUsr = CreditorAgeingConsSumBktUsr;
    }

    public Boolean getCreditorAgeingConsSumBktUsr() {
        return CreditorAgeingConsSumBktUsr;
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

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    public void setCreditorAgeingConsBktUsr_Link(Boolean CreditorAgeingConsBktUsr_Link) {
        this.CreditorAgeingConsBktUsr_Link = CreditorAgeingConsBktUsr_Link;
    }

    public Boolean getCreditorAgeingConsBktUsr_Link() {
        return CreditorAgeingConsBktUsr_Link;
    }

    public void setCreditorAgeingConsSumBktUsr_Link(Boolean CreditorAgeingConsSumBktUsr_Link) {
        this.CreditorAgeingConsSumBktUsr_Link = CreditorAgeingConsSumBktUsr_Link;
    }

    public Boolean getCreditorAgeingConsSumBktUsr_Link() {
        return CreditorAgeingConsSumBktUsr_Link;
    }

    public void setCreditorsAgeingSummaryBktUsr(Boolean creditorsAgeingSummaryBktUsr) {
        this.creditorsAgeingSummaryBktUsr = creditorsAgeingSummaryBktUsr;
    }

    public Boolean getCreditorsAgeingSummaryBktUsr() {
        return creditorsAgeingSummaryBktUsr;
    }

    public PreparedStatement getPstmt() {
        return pstmt;
    }
    CallableStatement cstmt = null;

    public ApReportBean() {
        this.fetchData();
        System.out.println("Fetch method called...");
    }

    public void supplierAgeingChangeListner(ValueChangeEvent vcesa) {
        String a = vcesa.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            supplierAgeing = true;

        } else {
            supplierAgeing = false;
        }

    }

    public void statementChangeListner(ValueChangeEvent vcest) {
        String a = vcest.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            statement = true;

        } else {
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

    public void billKnockoffChangeListener(ValueChangeEvent vcebko) {
        String a = vcebko.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            billKnockoff = true;

        } else {
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
        if (voucherTypeVLC.getValue() != null) {
            postvalue = voucherTypeVLC.getValue().toString();
        } else {
            postvalue = "P";
        }
        if (supplierAgeing == true) {
            if (asOnDateBinding.getValue() == null) {
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


        if (creditorsAgeingSummary == true) {
            if (asOnDateBinding.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                setCreditorsAgeingSummary_Link(true);
            }
        } else {
            setCreditorsAgeingSummary_Link(false);
        }

        if (creditorsAgeingSummaryBktUsr == true) {
            if (asOnDateBinding.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                setCreditorAgeingConsBktUsr_Link(true);
            }
        } else {
            setCreditorAgeingConsBktUsr_Link(false);
        }

        if (CreditorAgeingCons == true) {
            if (asOnDateBinding.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                setCreditorAgeingCons_Link(true);
            }
        } else {
            setCreditorAgeingCons_Link(false);
        }

        if (CreditorAgeingConsSum == true) {
            if (asOnDateBinding.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                setCreditorAgeingConsSum_Link(true);
            }
        } else {
            setCreditorAgeingConsSum_Link(false);
        }

        if (CreditorAgeingConsSumBktUsr == true) {
            if (asOnDateBinding.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                setCreditorAgeingConsSumBktUsr_Link(true);
            }
        } else {
            setCreditorAgeingConsSumBktUsr_Link(false);
        }


        if (genericReport) {
            setGenericReport_Link(true);
        } else {
            setGenericReport_Link(false);
        }

        return null;
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

    public void CreditorsAgeingSummChangeListner(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            creditorsAgeingSummary = true;

        } else {
            creditorsAgeingSummary = false;
        }
    }

    public void setCreditorsAgeingSummary(Boolean creditorsAgeingSummary) {
        this.creditorsAgeingSummary = creditorsAgeingSummary;
    }

    public Boolean getCreditorsAgeingSummary() {
        return creditorsAgeingSummary;
    }

    public void setCreditorsAgeingSummary_Link(Boolean creditorsAgeingSummary_Link) {
        this.creditorsAgeingSummary_Link = creditorsAgeingSummary_Link;
    }

    public Boolean getCreditorsAgeingSummary_Link() {
        return creditorsAgeingSummary_Link;
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

    public void setVoucherTypeVLC(RichSelectOneRadio voucherTypeVLC) {
        this.voucherTypeVLC = voucherTypeVLC;
    }

    public RichSelectOneRadio getVoucherTypeVLC() {
        return voucherTypeVLC;
    }

    public String test() {
        //  System.out.println(voucherTypeVLC.getValue()+"----value---");

        return null;
    }

    public void setPostvalue(String postvalue) {
        this.postvalue = postvalue;
    }

    public String getPostvalue() {
        return postvalue;
    }

    public void voucherTypevlcChangeListner(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            //System.out.println(valueChangeEvent.getNewValue());
            setPostvalue(valueChangeEvent.getNewValue().toString());
            postvalue = valueChangeEvent.getNewValue().toString();
            // System.out.println(postvalue+"------postvalue");
        } else {
            postvalue = "P";
        }

    }

    public void dateForAgeingVLC(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            setDateForAgeing(valueChangeEvent.getNewValue().toString());
            dateForAgeing = valueChangeEvent.getNewValue().toString();
            //System.out.println(dateForAgeing + "------------------------" );
        } else {
            dateForAgeing = "V";
        }
    }

    public void amountRangeSelectionFor(ValueChangeEvent valueChangeEvent) {

    }

    public void amountRangeSelectionForVLC(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            amountRangeSelectionFor = valueChangeEvent.getNewValue().toString();
            //System.out.println(amountRangeSelectionFor + "---------------amountRangeSelectionFor");

        } else {
            amountRangeSelectionFor = "O";
        }
    }

    public void amountRangeForVLC(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            amountRangeForVLC = valueChangeEvent.getNewValue().toString();
            //System.out.println(amountRangeForVLC + "--------------amountRangeForVLC");
        } else {
            amountRangeForVLC = "L";
            // System.out.println("line select "+ amountRangeForVLC);
        }
    }

    public void setDateForAgeing(String dateForAgeing) {

        this.dateForAgeing = dateForAgeing;
    }

    public String getDateForAgeing() {
        return dateForAgeing;
    }

    public void setDateForAgeingBV(RichSelectOneRadio dateForAgeingBV) {
        this.dateForAgeingBV = dateForAgeingBV;
    }

    public RichSelectOneRadio getDateForAgeingBV() {
        return dateForAgeingBV;
    }

    public void setAmountRangeSelectionFor(String amountRangeSelectionFor) {
        this.amountRangeSelectionFor = amountRangeSelectionFor;
    }

    public String getAmountRangeSelectionFor() {
        return amountRangeSelectionFor;
    }

    public void setAmountRangeForVLC(String amountRangeForVLC) {
        this.amountRangeForVLC = amountRangeForVLC;
    }

    public String getAmountRangeForVLC() {
        return amountRangeForVLC;
    }


    public void asOnDateVLCChangeListner(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            asOnDate = valueChangeEvent.getNewValue().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            asOnDate = sdf.format(valueChangeEvent.getNewValue());
            System.out.println(asOnDate + "-----------------asOndate");
        } else {
            asOnDate = Date.getCurrentDate().toString();
            System.out.println("As on Date if nullss-->" + asOnDate);
        }
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
    }

    public String getAsOnDate() {
        return asOnDate;
    }

    public void dateRangeForRankingVLC(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            dateRangeForRanking = valueChangeEvent.getNewValue().toString();
            // System.out.println(getDateRangeForRanking() + "----------------getDateRangeForRanking");
        } else {
            dateRangeForRanking = "V";
        }
    }

    public void valueBasedOnVLC(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            valueBasedON = valueChangeEvent.getNewValue().toString();
            // System.out.println(valueBasedON + "----------------valueBasedON");
        } else {
            valueBasedON = "O";

        }
    }


    public void setDateRangeForRanking(String dateRangeForRanking) {
        this.dateRangeForRanking = dateRangeForRanking;
    }

    public String getDateRangeForRanking() {
        return dateRangeForRanking;
    }

    public void currencyGroupingVLC(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            currencyGrouping = valueChangeEvent.getNewValue().toString();
            //System.out.println(currencyGrouping + "-------------currencyGrouping");

        } else {
            currencyGrouping = "T";
        }
    }

    public void setValueBasedON(String valueBasedON) {
        this.valueBasedON = valueBasedON;
    }

    public String getValueBasedON() {
        return valueBasedON;
    }

    public void setCurrencyGrouping(String currencyGrouping) {
        this.currencyGrouping = currencyGrouping;
    }

    public String getCurrencyGrouping() {
        return currencyGrouping;
    }


    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public void setAsOnDateBinding(RichInputDate asOnDateBinding) {
        this.asOnDateBinding = asOnDateBinding;
        if (this.asOnDateBinding.getValue() == null || this.asOnDateBinding.getValue().toString().equals("")) {
            this.asOnDateBinding.setValue(new Date().getCurrentDate());
        }
    }

    public RichInputDate getAsOnDateBinding() {
        return asOnDateBinding;
    }

    public void CreditorAgeingConsVCL(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            CreditorAgeingCons = true;

        } else {
            CreditorAgeingCons = false;
        }
    }

    public void creditorAgeingConsSumVCL(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            CreditorAgeingConsSum = true;

        } else {
            CreditorAgeingConsSum = false;
        }

    }

    public void setCreditorAgeingConsSum(Boolean CreditorAgeingConsSum) {
        this.CreditorAgeingConsSum = CreditorAgeingConsSum;
    }

    public Boolean getCreditorAgeingConsSum() {
        return CreditorAgeingConsSum;
    }

    public void setCreditorAgeingCons(Boolean CreditorAgeingCons) {
        this.CreditorAgeingCons = CreditorAgeingCons;
    }

    public Boolean getCreditorAgeingCons() {
        return CreditorAgeingCons;
    }

    public void setCreditorAgeingCons_Link(Boolean CreditorAgeingCons_Link) {
        this.CreditorAgeingCons_Link = CreditorAgeingCons_Link;
    }

    public Boolean getCreditorAgeingCons_Link() {
        return CreditorAgeingCons_Link;
    }

    public void setCreditorAgeingConsSum_Link(Boolean CreditorAgeingConsSum_Link) {
        this.CreditorAgeingConsSum_Link = CreditorAgeingConsSum_Link;
    }

    public Boolean getCreditorAgeingConsSum_Link() {
        return CreditorAgeingConsSum_Link;
    }

    public void setBucketFor(RichSelectOneRadio bucketFor) {
        this.bucketFor = bucketFor;
    }

    public RichSelectOneRadio getBucketFor() {
        return bucketFor;
    }

    public void setReportNameTransPgBind(RichInputListOfValues reportNameTransPgBind) {
        this.reportNameTransPgBind = reportNameTransPgBind;
    }

    public RichInputListOfValues getReportNameTransPgBind() {
        return reportNameTransPgBind;
    }

    public void setGenericReportCBPgBind(RichSelectBooleanCheckbox genericReportCBPgBind) {
        this.genericReportCBPgBind = genericReportCBPgBind;
    }

    public RichSelectBooleanCheckbox getGenericReportCBPgBind() {
        return genericReportCBPgBind;
    }

    public void setGenericReportPgBind(RichGoLink genericReportPgBind) {
        this.genericReportPgBind = genericReportPgBind;
    }

    public RichGoLink getGenericReportPgBind() {
        return genericReportPgBind;
    }

    public void setGenericReport_Link(Boolean genericReport_Link) {
        this.genericReport_Link = genericReport_Link;
    }

    public Boolean getGenericReport_Link() {
        return genericReport_Link;
    }

    public void setGenericReport(Boolean genericReport) {
        this.genericReport = genericReport;
    }

    public Boolean getGenericReport() {
        return genericReport;
    }

    public void ReportNameTransVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            System.out.println("Inside VCL");
            genericReportCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(genericReportCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(messageInfoPgBind);

            genericReport = false;
            setGenericReport_Link(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(genericReportPgBind);
        }
    }

    public void genericReportVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (reportNameTransPgBind.getValue() != null &&
                    reportNameTransPgBind.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    genericReport = true;
                } else {
                    System.out.println("Inside false");
                    genericReportCBPgBind.setValue("fasle");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(genericReportCBPgBind);

                    genericReport = false;

                    FacesMessage message = new FacesMessage("Please Select Report Name..!!!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(reportNameTransPgBind.getClientId(), message);
                }
            } else {
                genericReport = false;
            }
        } else {
            genericReport = false;
        }
    }

    public void setMessageInfoPgBind(RichMessage messageInfoPgBind) {
        this.messageInfoPgBind = messageInfoPgBind;
    }

    public RichMessage getMessageInfoPgBind() {
        return messageInfoPgBind;
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

    /** This method is called for saving data of ageing bucket. **/
    public void saveData() throws NamingException, SQLException {
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        int userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String bucketFor = "S";

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
                "where cld_id = ? and sloc_id = ? and org_id = ? and usr_id = ? and bkt_for='C'";
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

    public void CreditorsAgeingSummBktUsrChangeListner(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            creditorsAgeingSummaryBktUsr = true;

        } else {
            creditorsAgeingSummaryBktUsr = false;
        }
    }

    public void creditorAgeingConsSumBktUsrVCL(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            CreditorAgeingConsSumBktUsr = true;

        } else {
            CreditorAgeingConsSumBktUsr = false;
        }
    }
    // For report secrity User and Report Id 
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
