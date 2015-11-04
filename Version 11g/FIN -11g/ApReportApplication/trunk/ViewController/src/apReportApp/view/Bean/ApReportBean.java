package apReportApp.view.Bean;


import java.text.SimpleDateFormat;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import javax.swing.text.DateFormatter;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.nav.RichGoLink;

import oracle.jbo.domain.Date;

import org.jfree.xml.factory.objects.SimpleDateFormatObjectDescription;

public class ApReportBean {
    private Boolean CreditorAgeingCons_Link=false;
    private Boolean CreditorAgeingConsSum_Link=false;
    private Boolean supplierAgeing_Link = false;
    private Boolean statement_Link = false;
    private Boolean pendingAdvance_Link = false;
    private Boolean pendingBills_Link = false;
    private Boolean coaReport_Link = false;
    private Boolean billKnockoff_Link = false;
    private Boolean topNCreditors = false;
    private Boolean consolidate_Statement_Link = false;
    private Boolean creditorsAgeingSummary_Link = false;

private Boolean CreditorAgeingConsSum=false;
    private Boolean CreditorAgeingCons=false;
    private Boolean supplierAgeing = false;
    private Boolean statement = false;
    private Boolean pendingAdvance = false;
    private Boolean pendingBills = false;
    private Boolean coaReport = false;
    private Boolean billKnockoff = false;
    private Boolean topNCreditorsVal = false;
    private Boolean consolidate_Statement = false;
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

    public ApReportBean() {
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
            }else{
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


        if (creditorsAgeingSummary == true) {

            if (asOnDateBinding.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }  
            else{
                setCreditorsAgeingSummary_Link(true);

            }
        }
            else {
            setCreditorsAgeingSummary_Link(false);
        }
        if (CreditorAgeingCons == true) {

             if (asOnDateBinding.getValue() == null) {
                 FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                 message.setSeverity(FacesMessage.SEVERITY_ERROR);
                 FacesContext fc = FacesContext.getCurrentInstance();
                 fc.addMessage(null, message);
             }else{
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
             }else{
                setCreditorAgeingConsSum_Link(true);
             }
         } else {
             setCreditorAgeingConsSum_Link(false);
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
             System.out.println("As on Date if nullss-->"+asOnDate);
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


    public void setAsOnDateBinding(RichInputDate asOnDateBinding) {
        this.asOnDateBinding = asOnDateBinding;
    }

    public RichInputDate getAsOnDateBinding() {
        return asOnDateBinding;
    }

    public void CreditorAgeingConsVCL(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            CreditorAgeingCons= true;

        } else {
            CreditorAgeingCons = false;
        }
    }

    public void creditorAgeingConsSumVCL(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            CreditorAgeingConsSum= true;

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
}
