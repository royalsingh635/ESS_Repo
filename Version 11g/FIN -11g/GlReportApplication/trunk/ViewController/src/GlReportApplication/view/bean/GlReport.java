package GlReportApplication.view.bean;

import GlReportApplication.model.module.GlReportAMImpl;

import GlReportApplication.model.view.CoaLOVRowImpl;

import java.math.BigDecimal;

import java.util.Locale;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;


import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichGoLink;

import oracle.binding.BindingContainer;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.DisclosureEvent;


public class GlReport {
    private Boolean generalLedger_Link = false;
    private Boolean generalLedgerConsolidate_Link = false;
    private Boolean trialBalance_Link = false;
    private Boolean trialBalanceConsolidate_Link = false;
    private Boolean printVoucher_Link = false;
    private Boolean printVoucherConsolidate_Link = false;
    private Boolean profitNLoss_Link = false;
    private Boolean bankCashBook_Link = false;
    private Boolean BalanceSheetH_Link = false;
    private Boolean BalanceSheetV_Link = false;
    private Boolean BalanceSheetConH_Link = false;
    private Boolean BalanceSheetConV_Link = false;
    private Boolean ProfitNLossH_Link = false;
    private Boolean ProfitNLossV_Link = false;
    private Boolean ProfitNLossConH_Link = false;
    private Boolean ProfitNLossConV_Link = false;
    private Boolean Budget_Link = false;
    private Boolean TaxCoaWise_Link = false;
    private Boolean TaxTaxwise_Link = false;
    private Boolean TdsCoaWise_Link = false;
    private Boolean TdsTaxWise_Link = false;
    private Boolean ComparisonSheet_Link = false;
    private Boolean AdviceDetails_Link = false;


    private Boolean generalLedger = false;
    private Boolean generalLedgerConsolidate = false;
    private Boolean trialBalance = false;
    private Boolean trialBalanceConsolidate = false;
    private Boolean printVoucher = false;
    private Boolean printVoucherConsolidate = false;
    private RichInputDate startDate;
    private RichInputDate endDate;
    private Boolean profitNLoss = false;
    private Boolean bankCashBook = false;
    private RichInputDate asOnDate;
    private Boolean BalanceSheetH = false;
    private Boolean BalanceSheetV = false;
    private Boolean BalanceSheetConH = false;
    private Boolean BalanceSheetConV = false;
    private Boolean ProfitNLossH = false;
    private Boolean ProfitNLossV = false;
    private Boolean ProfitNLossConH = false;
    private Boolean ProfitNLossConV = false;
    private Boolean Budget = false;
    private Boolean TaxCoaWise = false;
    private Boolean TaxTaxwise = false;
    private Boolean TdsCoaWise = false;
    private Boolean TdsTaxWise = false;
    private Boolean ComparisonSheet = false;
    private Boolean AdviceDetails = false;


    private RichSelectBooleanCheckbox generalLedgerCB;
    private RichSelectBooleanCheckbox trialBalanceCB;
    private RichSelectBooleanCheckbox trialBalanceConCB;
    private RichSelectBooleanCheckbox blConH_CB;
    private RichSelectBooleanCheckbox blConV_CB;
    private RichSelectBooleanCheckbox blH_CB;
    private RichSelectBooleanCheckbox blV_CB;
    private RichSelectBooleanCheckbox pnLConH_CB;
    private RichSelectBooleanCheckbox pnLConV_CB;
    private RichSelectBooleanCheckbox pnLH_CB;
    private RichSelectBooleanCheckbox pnLV_CB;
    private RichSelectBooleanCheckbox generalLedgerCon_CB;
    private Integer count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
    private RichSelectOneRadio postFlagValBindVar;
    private RichGoLink gllink_Bvar;
    private RichGoLink balancStV_Link_Bvar;
    private RichGoLink balancStH_Link_Bvar;
    private RichGoLink blncStConH_Bvar;
    private RichGoLink balancStConV_Link_Bvar;
    private RichGoLink pnLV_BVar;
    private RichGoLink pnLH_BVar;
    private RichGoLink pnLConV_BVar;
    private RichGoLink pnLConH_BVar;
    private RichGoLink printVocher_pgBind;
    private RichGoLink dayBook_PgBind;
    private RichSelectBooleanCheckbox printVoucherCB;
    private RichSelectBooleanCheckbox daybookCB;
    private RichSelectOneChoice orgid;
    //private RichSelectOneChoice voucherNumber;
    private RichSelectOneChoice voucherTypePgBind;
    private RichSelectOneChoice bookTypePgBind;
    private RichSelectBooleanCheckbox budgetCB;
    private RichSelectBooleanCheckbox taxCoaWiseCB;
    private RichSelectBooleanCheckbox taxTaxWiseCB;
    private RichSelectBooleanCheckbox tdsCoaWiseCB;
    private RichSelectBooleanCheckbox tdsTaxWiseCB;
    private RichSelectOneChoice sheetTypePgBind;
    private RichSelectBooleanCheckbox comparisonSheetCBPgBind;
    private RichGoLink comparisonSheetLinkPgBind;
    private RichSelectBooleanCheckbox adviceDetailCB;
    private RichGoLink otherReportVL;
    private RichSelectBooleanCheckbox otherReportCB;
    private RichSelectOneRadio fileTypePgBind;
    private RichSelectOneRadio firstRadioGroupPgBind;
    private RichSelectOneRadio secondRadioGroupPgBind;
    
    
    GlReportAMImpl am =  (GlReportAMImpl)resolvElDC("GlReportAMDataControl");
    private RichSelectBooleanCheckbox productionBookCB;
    private RichGoLink productionBookLnk;
    private RichGoLink bnkSummaryLnk;
    private RichSelectBooleanCheckbox bnkSummaryCB;
  
    private RichSelectBooleanCheckbox bookTypeWiseSummaryBnd;
    private RichGoLink bookTypeWiseSummaryLnk;
    private RichInputListOfValues coaName;
    private RichSelectBooleanCheckbox bookConsolidatedPgBind;
    private RichGoLink gnBookConLnk;
    private RichSelectBooleanCheckbox bookSummaryConsolBind;
    private RichGoLink bookSummaryConLnk;
    private RichSelectOneChoice fromVoucherBind;
    private RichSelectOneChoice toVoucherBind;
    //private RichSelectOneChoice toVoucher;
    private RichSelectBooleanCheckbox glUSerCB;
    private RichGoLink userGlLink;
    private Boolean userLink=false;
    private RichSelectBooleanCheckbox productionBookCBBind;
    private RichGoLink productionBookLDeatilnk;
    private RichInputListOfValues voucherNumber;
    private RichInputListOfValues toVoucher;
    private RichSelectOneChoice adjTypeBind;


    public GlReport() {


    }


    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public void generalLedgerChangeListener(ValueChangeEvent vcegl) {
        String a = vcegl.getNewValue().toString();
        //System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());
        if (a.equalsIgnoreCase("true")) {
            if (!checkBoxValidation()) {
                generalLedger = true;
            } else {
                FacesMessage message =
                    new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDate.getClientId(), message);
                }
                if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDate.getClientId(), message);
                }
                //set chaeckbox  false
                generalLedgerCB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(generalLedgerCB);
            }
        } else {
            generalLedger = false;
        }
    }


    public void generalLedgerConsolidateChangeListener(ValueChangeEvent vceglc) {
        String a = vceglc.getNewValue().toString();
        /* System.out.println("general check box value----------->>>>>>>>"+a);
        System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());
     */
        if (a.equalsIgnoreCase("true")) {
            if (!checkBoxValidation()) {
                generalLedgerConsolidate = true;
            } else {
                generalLedgerCon_CB.setValue(false);
                //System.out.println("enter into else part");
                FacesMessage message =
                    new FacesMessage("Please Have Not Entered Start Date Or End Date...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDate.getClientId(), message);
                }
                if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDate.getClientId(), message);
                }
                //set chaeckbox  false
                generalLedgerCon_CB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(generalLedgerCon_CB);
            }
        } else {
            generalLedgerConsolidate = false;
        }
    }

    public void trialBalanceChangeListner(ValueChangeEvent vcetb) {
        String a = vcetb.getNewValue().toString();
        //System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());
        if (a.equalsIgnoreCase("true")) {
            if (!checkBoxValidation()) {
                trialBalance = true;
            } else {
                FacesMessage message =
                    new FacesMessage("Please Have Not Entered Start Date Or End Date...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDate.getClientId(), message);
                }
                if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDate.getClientId(), message);
                }
                //set chaeckbox  false
                trialBalanceCB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(trialBalanceCB);
            }
        } else {
            trialBalance = false;
        }
    }


    public void trialBalanceConsolidate(ValueChangeEvent vcetbc) {
        String a = vcetbc.getNewValue().toString();
        // System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());

        if (a.equalsIgnoreCase("true")) {
            if (!checkBoxValidation()) {
                trialBalanceConsolidate = true;
            } else {
                FacesMessage message =
                    new FacesMessage("Please Have Not Entered Start Date Or End Date...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDate.getClientId(), message);
                }
                if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDate.getClientId(), message);
                }
                //set chaeckbox  false
                trialBalanceConCB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(trialBalanceConCB);


            }

        } else {
            trialBalanceConsolidate = false;

        }
    }


    public void printVoucher(ValueChangeEvent vcepv) {
        String a = vcepv.getNewValue().toString();

        if (a.equalsIgnoreCase("true")) {
            if (!checkBoxValidation()) {
                printVoucher = true;
            } else {
                FacesMessage message =
                    new FacesMessage("Please Have Not Entered Start Date Or End Date...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            printVoucher = false;
        }
    }


    public void printVoucherConsolidate(ValueChangeEvent vcevc) {
        String a = vcevc.getNewValue().toString();
        if (a.equalsIgnoreCase("true")) {
            printVoucherConsolidate = true;

        } else {
            printVoucherConsolidate = false;
        }

    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String GenerateReportAction() {
        //System.out.println("postflag value------>>>>"+postFlagValBindVar.getValue());
        System.out.println("showing selected Report");
        if (generalLedger == true || this.getGeneralLedgerCB().isSelected()) {
            setGeneralLedger_Link(true);
            
        } else {
            setGeneralLedger_Link(false);
        }


        if (generalLedgerConsolidate == true) {
            setGeneralLedgerConsolidate_Link(true);


        } else {
            setGeneralLedgerConsolidate_Link(false);
        }


        if (trialBalance == true) {

            setTrialBalance_Link(true);

        } else {
            setTrialBalance_Link(false);
        }


        if (trialBalanceConsolidate == true) {
            setTrialBalanceConsolidate_Link(true);
        } else {
            setTrialBalanceConsolidate_Link(false);
        }


        if (printVoucher == true && postFlagValBindVar.getValue().toString().equalsIgnoreCase("P")) {
            setPrintVoucher_Link(true);
        } else {
            setPrintVoucher_Link(false);
        }


        if (printVoucherConsolidate == true) {
            setPrintVoucherConsolidate_Link(true);
        } else {
            setPrintVoucherConsolidate_Link(false);
        }

        if ((Boolean)this.otherReportCB.getValue()){
            this.otherReportVL.setVisible(true);
        } else {
            this.otherReportVL.setVisible(false);
        }

        if (profitNLoss == true) {
            setProfitNLoss_Link(true);
        } else {
            setProfitNLoss_Link(false);
        }


        if (bankCashBook == true) {
            setBankCashBook_Link(true);
        } else {
            setBankCashBook_Link(false);
        }

        if (BalanceSheetConH == true) {
            setBalanceSheetConH_Link(true);
        } else {
            setBalanceSheetConH_Link(false);
        }

        if (BalanceSheetConV == true) {
            setBalanceSheetConV_Link(true);
        } else {
            setBalanceSheetConV_Link(false);
        }
        if (BalanceSheetH == true) {
            setBalanceSheetH_Link(true);
        } else {
            setBalanceSheetH_Link(false);
        }
        if (BalanceSheetV == true) {
            setBalanceSheetV_Link(true);
        } else {
            setBalanceSheetV_Link(false);
        }
        if (ProfitNLossConH == true) {
            setProfitNLossConH_Link(true);
        } else {
            setProfitNLossConH_Link(false);
        }
        if (ProfitNLossConV == true) {
            setProfitNLossConV_Link(true);
        } else {
            setProfitNLossConV_Link(false);
        }
        if (ProfitNLossV == true) {
            setProfitNLossV_Link(true);
        } else {
            setProfitNLossV_Link(false);
        }
        if (ProfitNLossH == true) {
            setProfitNLossH_Link(true);
        } else {
            setProfitNLossH_Link(false);
        }

        if (Budget == true) {
            setBudget_Link(true);
        } else {
            setBudget_Link(false);
        }

        if (TaxCoaWise == true) {
            setTaxCoaWise_Link(true);
        } else {
            setTaxCoaWise_Link(false);
        }

        if (TaxTaxwise == true) {
            setTaxTaxwise_Link(true);
        } else {
            setTaxTaxwise_Link(false);
        }

        if (TdsCoaWise == true) {
            setTdsCoaWise_Link(true);
        } else {
            setTdsCoaWise_Link(false);
        }

        if (TdsTaxWise == true) {
            setTdsTaxWise_Link(true);
        } else {
            setTdsTaxWise_Link(false);
        }

        if (ComparisonSheet == true) {
            setComparisonSheet_Link(true);
        } else {
            setComparisonSheet_Link(false);
        }

        if (AdviceDetails == true) {
            setAdviceDetails_Link(true);
        } else {
            setAdviceDetails_Link(false);
        }
        if(this.productionBookCB.isSelected()){
            this.productionBookLnk.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.productionBookLnk);
        }else{
            this.productionBookLnk.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.productionBookLnk);
        }
        
        if(this.bnkSummaryCB.isSelected()){
            this.bnkSummaryLnk.setVisible(true);
        }else{
            this.bnkSummaryLnk.setVisible(false);
        }
        
        if(this.bookTypeWiseSummaryBnd.isSelected()){
            this.bookTypeWiseSummaryLnk.setVisible(true);
        }else{
            this.bookTypeWiseSummaryLnk.setVisible(false);
        }
        
        if(this.bookConsolidatedPgBind.isSelected()){
            this.gnBookConLnk.setVisible(true);
        }else{
            this.gnBookConLnk.setVisible(false);
        }
        
        
        if(this.bookConsolidatedPgBind.isSelected()){
            this.gnBookConLnk.setVisible(true);
        }else{
            this.gnBookConLnk.setVisible(false);
        }
        
        if(this.bookSummaryConsolBind.isSelected()){
            this.bookSummaryConLnk.setVisible(true);
        }else{
            this.bookSummaryConLnk.setVisible(false);
        }

        if(this.glUSerCB.isSelected()){
            this.userGlLink.setVisible(true);
        }else{
            this.userGlLink.setVisible(false);
        }
        
        if(this.productionBookCBBind.isSelected()){
            this.productionBookLDeatilnk.setVisible(true);
        }else{
            this.productionBookLDeatilnk.setVisible(false); 
        }

        return null;
    }


    public void profitnLoss(ValueChangeEvent vce) {

        String a = vce.getNewValue().toString();
        if (a.equalsIgnoreCase("true")) {
            profitNLoss = true;

        } else {
            profitNLoss = false;
        }
    }

    public void bankCashBook(ValueChangeEvent vce) {
        String a = vce.getNewValue().toString();
        //System.out.println("a======" + a);
        if (a.equalsIgnoreCase("true")) {
            bankCashBook = true;

        } else {
            bankCashBook = false;
        }
    }

    public boolean checkBoxValidation() {
        /*  System.out.println("start date------>>>"+startDate.getValue());
        System.out.println("End date------>>>"+endDate.getValue()); */
        if (startDate.getValue() == null || startDate.getValue().toString().length() <= 0) {
            return true;
        } else if (endDate.getValue() == null || endDate.getValue().toString().length() <= 0) {
            return true;
        }

        else {
            return false;
        }

    }


    public void balanceSheetConH_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean b = (Boolean)valueChangeEvent.getNewValue();
        if (b) {

            if (asOnDate.getValue() != null && asOnDate.getValue().toString().length() != 0) {
                BalanceSheetConH = true;
            } else {
                FacesMessage message = new FacesMessage("You Have not Enterd ASONDATE...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(asOnDate.getClientId(), message);

                //set chaeckbox  false
                blConH_CB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(blConH_CB);
            }
        }

        else {
            BalanceSheetConH = false;
        }
    }


    public void balanceSheetConV_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean b = (Boolean)valueChangeEvent.getNewValue();
        if (b) {

            if (asOnDate.getValue() != null && asOnDate.getValue().toString().length() > 0) {
                BalanceSheetConV = true;
            } else {
                FacesMessage message = new FacesMessage("You Have not Enterd ASONDATE...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(asOnDate.getClientId(), message);

                //set chaeckbox  false
                blConV_CB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(blConV_CB);
            }
        }

        else {
            BalanceSheetConV = false;
        }

    }

    public void balanceSheetH_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean b = (Boolean)valueChangeEvent.getNewValue();
        if (b) {

            if (asOnDate.getValue() != null && asOnDate.getValue().toString().length() > 0) {
                BalanceSheetH = true;
            } else {
                FacesMessage message = new FacesMessage("You Have not Enterd ASONDATE...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(asOnDate.getClientId(), message);

                //set chaeckbox  false
                blH_CB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(blH_CB);
            }
        } else {
            BalanceSheetH = false;
        }
    }

    public void balanceSheetV_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean b = (Boolean)valueChangeEvent.getNewValue();
        if (b) {

            if (asOnDate.getValue() != null && asOnDate.getValue().toString().length() > 0) {
                BalanceSheetV = true;
            } else {
                FacesMessage message = new FacesMessage("You Have not Enterd ASONDATE...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(asOnDate.getClientId(), message);

                //set chaeckbox  false
                blV_CB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(blV_CB);
            }
        } else {
            BalanceSheetV = false;
        }
    }

    public void profitNLossConH_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean b = (Boolean)valueChangeEvent.getNewValue();
        if (b) {

            if (asOnDate.getValue() != null && asOnDate.getValue().toString().length() > 0) {
                ProfitNLossConH = true;
            } else {
                FacesMessage message = new FacesMessage("You Have not Enterd ASONDATE...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(asOnDate.getClientId(), message);

                //set chaeckbox  false
                pnLConH_CB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(pnLConH_CB);
            }
        }

        else {
            ProfitNLossConH = false;
        }
    }

    public void profitNLossConV_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean b = (Boolean)valueChangeEvent.getNewValue();
        if (b) {

            if (asOnDate.getValue() != null && asOnDate.getValue().toString().length() > 0) {
                ProfitNLossConV = true;
            } else {
                FacesMessage message = new FacesMessage("You Have not Enterd ASONDATE...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(asOnDate.getClientId(), message);

                //set chaeckbox  false
                pnLConV_CB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(pnLConV_CB);
            }
        }

        else {
            ProfitNLossConV = false;
        }
    }

    public void profitNLossH_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean b = (Boolean)valueChangeEvent.getNewValue();
        if (b) {
            if (asOnDate.getValue() != null && asOnDate.getValue().toString().length() > 0) {
                ProfitNLossH = true;
            } else {
                FacesMessage message = new FacesMessage("You Have not Enterd ASONDATE...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(asOnDate.getClientId(), message);

                //set chaeckbox  false
                pnLH_CB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(pnLH_CB);
            }
        } else {
            ProfitNLossH = false;
        }
    }

    public void profitNLossV_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean b = (Boolean)valueChangeEvent.getNewValue();
        if (b) {

            if (asOnDate.getValue() != null && asOnDate.getValue().toString().length() > 0) {
                ProfitNLossV = true;
            } else {
                FacesMessage message = new FacesMessage("You Have not Enterd ASONDATE...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(asOnDate.getClientId(), message);

                //set chaeckbox  false
                pnLV_CB.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(pnLV_CB);
            }
        } else {
            ProfitNLossV = false;
        }
    }


    public void setGeneralLedger_Link(Boolean generalLedger_Link) {
        this.generalLedger_Link = generalLedger_Link;
    }

    public Boolean getGeneralLedger_Link() {
        return generalLedger_Link;
    }

    public void setGeneralLedgerConsolidate_Link(Boolean generalLedgerConsolidate_Link) {
        this.generalLedgerConsolidate_Link = generalLedgerConsolidate_Link;
    }

    public Boolean getGeneralLedgerConsolidate_Link() {
        return generalLedgerConsolidate_Link;
    }

    public void setTrialBalance_Link(Boolean trialBalance_Link) {
        this.trialBalance_Link = trialBalance_Link;
    }

    public Boolean getTrialBalance_Link() {
        return trialBalance_Link;
    }

    public void setTrialBalanceConsolidate_Link(Boolean trialBalanceConsolidate_Link) {
        this.trialBalanceConsolidate_Link = trialBalanceConsolidate_Link;
    }

    public Boolean getTrialBalanceConsolidate_Link() {
        return trialBalanceConsolidate_Link;
    }

    public void setPrintVoucher_Link(Boolean printVoucher_Link) {
        this.printVoucher_Link = printVoucher_Link;
    }

    public Boolean getPrintVoucher_Link() {
        return printVoucher_Link;
    }


    public void setPrintVoucherConsolidate_Link(Boolean printVoucherConsolidate_Link) {
        this.printVoucherConsolidate_Link = printVoucherConsolidate_Link;
    }

    public Boolean getPrintVoucherConsolidate_Link() {
        return printVoucherConsolidate_Link;
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

    public void setProfitNLoss_Link(Boolean profitNLoss_Link) {
        this.profitNLoss_Link = profitNLoss_Link;
    }

    public Boolean getProfitNLoss_Link() {
        return profitNLoss_Link;
    }


    public void setBankCashBook_Link(Boolean bankCashBook_Link) {
        this.bankCashBook_Link = bankCashBook_Link;
    }

    public Boolean getBankCashBook_Link() {
        return bankCashBook_Link;
    }

    public void setBankCashBook(Boolean bankCashBook) {
        this.bankCashBook = bankCashBook;
    }

    public Boolean getBankCashBook() {
        return bankCashBook;
    }

    public void setAsOnDate(RichInputDate asOnDate) {
        this.asOnDate = asOnDate;
    }

    public RichInputDate getAsOnDate() {
        return asOnDate;
    }

    public void setBalanceSheetH(Boolean BalanceSheetH) {
        this.BalanceSheetH = BalanceSheetH;
    }

    public Boolean getBalanceSheetH() {
        return BalanceSheetH;
    }

    public void setBalanceSheetV(Boolean BalanceSheetV) {
        this.BalanceSheetV = BalanceSheetV;
    }

    public Boolean getBalanceSheetV() {
        return BalanceSheetV;
    }

    public void setBalanceSheetConH(Boolean BalanceSheetConH) {
        this.BalanceSheetConH = BalanceSheetConH;
    }

    public Boolean getBalanceSheetConH() {
        return BalanceSheetConH;
    }

    public void setBalanceSheetConV(Boolean BalanceSheetConV) {
        this.BalanceSheetConV = BalanceSheetConV;
    }

    public Boolean getBalanceSheetConV() {
        return BalanceSheetConV;
    }

    public void setProfitNLossH(Boolean ProfitNLossH) {
        this.ProfitNLossH = ProfitNLossH;
    }

    public Boolean getProfitNLossH() {
        return ProfitNLossH;
    }

    public void setProfitNLossV(Boolean ProfitNLossV) {
        this.ProfitNLossV = ProfitNLossV;
    }

    public Boolean getProfitNLossV() {
        return ProfitNLossV;
    }

    public void setProfitNLossConH(Boolean ProfitNLossConH) {
        this.ProfitNLossConH = ProfitNLossConH;
    }

    public Boolean getProfitNLossConH() {
        return ProfitNLossConH;
    }

    public void setProfitNLossConV(Boolean ProfitNLossConV) {
        this.ProfitNLossConV = ProfitNLossConV;
    }

    public Boolean getProfitNLossConV() {
        return ProfitNLossConV;
    }

    public void setGeneralLedgerCB(RichSelectBooleanCheckbox generalLedgerCB) {
        this.generalLedgerCB = generalLedgerCB;
    }

    public RichSelectBooleanCheckbox getGeneralLedgerCB() {
        return generalLedgerCB;
    }

    public void setTrialBalanceCB(RichSelectBooleanCheckbox trialBalanceCB) {
        this.trialBalanceCB = trialBalanceCB;
    }

    public RichSelectBooleanCheckbox getTrialBalanceCB() {
        return trialBalanceCB;
    }

    public void setTrialBalanceConCB(RichSelectBooleanCheckbox trialBalanceConCB) {
        this.trialBalanceConCB = trialBalanceConCB;
    }

    public RichSelectBooleanCheckbox getTrialBalanceConCB() {
        return trialBalanceConCB;
    }

    public void setBlConH_CB(RichSelectBooleanCheckbox blConH_CB) {
        this.blConH_CB = blConH_CB;
    }

    public RichSelectBooleanCheckbox getBlConH_CB() {
        return blConH_CB;
    }

    public void setBlConV_CB(RichSelectBooleanCheckbox blConV_CB) {
        this.blConV_CB = blConV_CB;
    }

    public RichSelectBooleanCheckbox getBlConV_CB() {
        return blConV_CB;
    }

    public void setBlH_CB(RichSelectBooleanCheckbox blH_CB) {
        this.blH_CB = blH_CB;
    }

    public RichSelectBooleanCheckbox getBlH_CB() {
        return blH_CB;
    }

    public void setBlV_CB(RichSelectBooleanCheckbox blV_CB) {
        this.blV_CB = blV_CB;
    }

    public RichSelectBooleanCheckbox getBlV_CB() {
        return blV_CB;
    }

    public void setPnLConH_CB(RichSelectBooleanCheckbox pnLConH_CB) {
        this.pnLConH_CB = pnLConH_CB;
    }

    public RichSelectBooleanCheckbox getPnLConH_CB() {
        return pnLConH_CB;
    }

    public void setPnLConV_CB(RichSelectBooleanCheckbox pnLConV_CB) {
        this.pnLConV_CB = pnLConV_CB;
    }

    public RichSelectBooleanCheckbox getPnLConV_CB() {
        return pnLConV_CB;
    }

    public void setPnLH_CB(RichSelectBooleanCheckbox pnLH_CB) {
        this.pnLH_CB = pnLH_CB;
    }

    public RichSelectBooleanCheckbox getPnLH_CB() {
        return pnLH_CB;
    }

    public void setPnLV_CB(RichSelectBooleanCheckbox pnLV_CB) {
        this.pnLV_CB = pnLV_CB;
    }

    public RichSelectBooleanCheckbox getPnLV_CB() {
        return pnLV_CB;
    }

    public void setBalanceSheetH_Link(Boolean BalanceSheetH_Link) {
        this.BalanceSheetH_Link = BalanceSheetH_Link;
    }

    public Boolean getBalanceSheetH_Link() {
        return BalanceSheetH_Link;
    }

    public void setBalanceSheetV_Link(Boolean BalanceSheetV_Link) {
        this.BalanceSheetV_Link = BalanceSheetV_Link;
    }

    public Boolean getBalanceSheetV_Link() {
        return BalanceSheetV_Link;
    }

    public void setBalanceSheetConH_Link(Boolean BalanceSheetConH_Link) {
        this.BalanceSheetConH_Link = BalanceSheetConH_Link;
    }

    public Boolean getBalanceSheetConH_Link() {
        return BalanceSheetConH_Link;
    }

    public void setBalanceSheetConV_Link(Boolean BalanceSheetConV_Link) {
        this.BalanceSheetConV_Link = BalanceSheetConV_Link;
    }

    public Boolean getBalanceSheetConV_Link() {
        return BalanceSheetConV_Link;
    }

    public void setProfitNLossH_Link(Boolean ProfitNLossH_Link) {
        this.ProfitNLossH_Link = ProfitNLossH_Link;
    }

    public Boolean getProfitNLossH_Link() {
        return ProfitNLossH_Link;
    }

    public void setProfitNLossV_Link(Boolean ProfitNLossV_Link) {
        this.ProfitNLossV_Link = ProfitNLossV_Link;
    }

    public Boolean getProfitNLossV_Link() {
        return ProfitNLossV_Link;
    }

    public void setProfitNLossConH_Link(Boolean ProfitNLossConH_Link) {
        this.ProfitNLossConH_Link = ProfitNLossConH_Link;
    }

    public Boolean getProfitNLossConH_Link() {
        return ProfitNLossConH_Link;
    }

    public void setProfitNLossConV_Link(Boolean ProfitNLossConV_Link) {
        this.ProfitNLossConV_Link = ProfitNLossConV_Link;
    }

    public Boolean getProfitNLossConV_Link() {
        return ProfitNLossConV_Link;
    }

    public void setGeneralLedgerCon_CB(RichSelectBooleanCheckbox generalLedgerCon_CB) {
        this.generalLedgerCon_CB = generalLedgerCon_CB;
    }

    public RichSelectBooleanCheckbox getGeneralLedgerCon_CB() {
        return generalLedgerCon_CB;
    }


    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {

        return count;
    }

    public void starDate_ValuechangeListener(ValueChangeEvent vce) {
        // Add event code here...
        Object o = vce.getNewValue();
        this.chngComponentsValues();

        if (o == null) {
            
         //  am.setAttribute(o,2);
          
            generalLedgerCB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(generalLedgerCB);

            setGeneralLedger_Link(false);


            generalLedgerCon_CB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(generalLedgerCon_CB);

            trialBalanceCB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(trialBalanceCB);

            trialBalanceConCB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(trialBalanceConCB);

            setGeneralLedgerConsolidate_Link(false);


            setTrialBalance_Link(false);


            setTrialBalanceConsolidate_Link(false);


            generalLedger = false;
            generalLedgerConsolidate = false;
            trialBalance = false;
            trialBalanceConsolidate = false;

        }

        if (vce != null) {
            if (vce.getNewValue() != null) {
                endDate.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(endDate);
            }
        }

    }

    public void endDate_ValuechangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Object o = valueChangeEvent.getNewValue();
        this.chngComponentsValues();
        
        if (o == null) {
            
           // am.setAttribute(o,3);
            generalLedgerCB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(generalLedgerCB);

            setGeneralLedger_Link(false);

            generalLedgerCon_CB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(generalLedgerCon_CB);

            trialBalanceCB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(trialBalanceCB);

            trialBalanceConCB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(trialBalanceConCB);

            setGeneralLedgerConsolidate_Link(false);
            setTrialBalance_Link(false);
            setTrialBalanceConsolidate_Link(false);
            generalLedger = false;
            generalLedgerConsolidate = false;
            trialBalance = false;
            trialBalanceConsolidate = false;
        }


    }

    public void asOnDate_ValuechangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Object o = valueChangeEvent.getNewValue();
        if (o == null) {
            setBalanceSheetConH_Link(false);
            setBalanceSheetConV_Link(false);
            setBalanceSheetH_Link(false);
            setBalanceSheetV_Link(false);
            setProfitNLossConH_Link(false);
            setProfitNLossConV_Link(false);
            setProfitNLossV_Link(false);
            setProfitNLossH_Link(false);
            blConH_CB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(blConH_CB);

            blConV_CB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(blConV_CB);

            blH_CB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(blH_CB);

            blV_CB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(blV_CB);

            pnLConH_CB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pnLConH_CB);

            pnLConV_CB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pnLConV_CB);

            pnLH_CB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pnLH_CB);

            pnLV_CB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pnLV_CB);

            BalanceSheetConH = false;
            BalanceSheetConV = false;
            BalanceSheetH = false;
            BalanceSheetV = false;
            ProfitNLossConH = false;
            ProfitNLossConV = false;
            ProfitNLossV = false;
            ProfitNLossH = false;
        }

    }


    public void setPostFlagValBindVar(RichSelectOneRadio postFlagValBindVar) {
        this.postFlagValBindVar = postFlagValBindVar;
    }

    public RichSelectOneRadio getPostFlagValBindVar() {
        return postFlagValBindVar;
    }

    public void setGllink_Bvar(RichGoLink gllink_Bvar) {
        this.gllink_Bvar = gllink_Bvar;
    }

    public RichGoLink getGllink_Bvar() {
        return gllink_Bvar;
    }

    public void setBalancStV_Link_Bvar(RichGoLink balancStV_Link_Bvar) {
        this.balancStV_Link_Bvar = balancStV_Link_Bvar;
    }

    public RichGoLink getBalancStV_Link_Bvar() {
        return balancStV_Link_Bvar;
    }

    public void setBalancStH_Link_Bvar(RichGoLink balancStH_Link_Bvar) {
        this.balancStH_Link_Bvar = balancStH_Link_Bvar;
    }

    public RichGoLink getBalancStH_Link_Bvar() {
        return balancStH_Link_Bvar;
    }

    public void setBlncStConH_Bvar(RichGoLink blncStConH_Bvar) {
        this.blncStConH_Bvar = blncStConH_Bvar;
    }

    public RichGoLink getBlncStConH_Bvar() {
        return blncStConH_Bvar;
    }

    public void setBalancStConV_Link_Bvar(RichGoLink balancStConV_Link_Bvar) {
        this.balancStConV_Link_Bvar = balancStConV_Link_Bvar;
    }

    public RichGoLink getBalancStConV_Link_Bvar() {
        return balancStConV_Link_Bvar;
    }

    public void setPnLV_BVar(RichGoLink pnLV_BVar) {
        this.pnLV_BVar = pnLV_BVar;
    }

    public RichGoLink getPnLV_BVar() {
        return pnLV_BVar;
    }

    public void setPnLH_BVar(RichGoLink pnLH_BVar) {
        this.pnLH_BVar = pnLH_BVar;
    }

    public RichGoLink getPnLH_BVar() {
        return pnLH_BVar;
    }

    public void setPnLConV_BVar(RichGoLink pnLConV_BVar) {
        this.pnLConV_BVar = pnLConV_BVar;
    }

    public RichGoLink getPnLConV_BVar() {
        return pnLConV_BVar;
    }

    public void setPnLConH_BVar(RichGoLink pnLConH_BVar) {
        this.pnLConH_BVar = pnLConH_BVar;
    }

    public RichGoLink getPnLConH_BVar() {
        return pnLConH_BVar;
    }

    public void printVoucher_valueChangeListener(ValueChangeEvent valueChangeEvent) {
        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        //System.out.println("Print Voucher Value is "+value);

        if (value != null) {
            String a = valueChangeEvent.getNewValue().toString();

            // System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());
            if (a.equalsIgnoreCase("true")) {
                if (!checkBoxValidation()) {
                    //printVocher_pgBind.setVisible(true);
                    this.printVoucher = true;
                    // voucherTypePgBind.setDisabled(false);
                } else {
                    FacesMessage message =
                        new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(startDate.getClientId(), message);
                    }
                    if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(endDate.getClientId(), message);
                    }
                    //set chaeckbox  false
                    printVoucherCB.setValue(false);
                }
            } else {
                //printVocher_pgBind.setVisible(false);
                this.printVoucher = false;
                //voucherTypePgBind.setDisabled(true);
            }
            if (postFlagValBindVar.getValue().toString().equalsIgnoreCase("A")) {
                FacesMessage message =
                    new FacesMessage("","Please Select PostFlag Type As Posted In Case of PrintVoucher");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(postFlagValBindVar.getClientId(), message);
                
                this.printVoucher = false;
                printVoucherCB.setValue(false);

                AdfFacesContext.getCurrentInstance().addPartialTarget(printVoucherCB);
            }


        } else{
            //printVocher_pgBind.setVisible(false);
            this.printVoucher = false;
        //voucherTypePgBind.setDisabled(true);
        }

    }

    public void dayBook_valueChangeListener(ValueChangeEvent valueChangeEvent) {
        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        System.out.println(value);
        if (value) {
            String a = valueChangeEvent.getNewValue().toString();


            if (a.equalsIgnoreCase("true")) {

                if (!checkBoxValidation()) {
                    if (bookTypePgBind.getValue() != null && !bookTypePgBind.getValue().toString().equals("")) {

                        dayBook_PgBind.setVisible(true);

                    } else {

                        daybookCB.setValue(false);

                        FacesMessage message = new FacesMessage("Please select Book Type");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(bookTypePgBind.getClientId(), message);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(daybookCB);
                    }


                } else {


                    FacesMessage message =
                        new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(startDate.getClientId(), message);
                    }
                    if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(endDate.getClientId(), message);
                    }

                    daybookCB.setValue(false);

                }
            } else {


                dayBook_PgBind.setVisible(false);

            }
        } else {


            dayBook_PgBind.setVisible(false);

        }
    }

    public void setPrintVocher_pgBind(RichGoLink printVocher_pgBind) {
        this.printVocher_pgBind = printVocher_pgBind;
    }

    public RichGoLink getPrintVocher_pgBind() {
        return printVocher_pgBind;
    }

    public void setDayBook_PgBind(RichGoLink dayBook_PgBind) {
        this.dayBook_PgBind = dayBook_PgBind;
    }

    public RichGoLink getDayBook_PgBind() {
        return dayBook_PgBind;
    }

    public void setPrintVoucherCB(RichSelectBooleanCheckbox printVoucherCB) {
        this.printVoucherCB = printVoucherCB;
    }

    public RichSelectBooleanCheckbox getPrintVoucherCB() {
        return printVoucherCB;
    }

    public void setDaybookCB(RichSelectBooleanCheckbox daybookCB) {
        this.daybookCB = daybookCB;
    }

    public RichSelectBooleanCheckbox getDaybookCB() {
        return daybookCB;
    }

    public void voucherTypeValueChangeListener(ValueChangeEvent valueChangeEvent) {

        // System.out.println("Voucher Type Value is "+valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null) {
            // System.out.println("Voucher Type Value is "+valueChangeEvent.getNewValue());
            /*   System.out.println("check box value is "+postFlagValBindVar.getValue());
            System.out.println("start date value is "+startDate.getValue());
            System.out.println("End Date Value is "+endDate.getValue());
            System.out.println("Organisation Id is "+orgid.getValue());
            System.out.println("--------------------"); */
            
            
            /*ViewObjectImpl coaLOV = am.getCoaLOV();

            RowSet rowSet = coaLOV.getRowSet();
            rowSet.setNamedWhereClauseParam("VoucherType", postFlagValBindVar.getValue());
            rowSet.setNamedWhereClauseParam("StartDate", startDate.getValue());
            rowSet.setNamedWhereClauseParam("EndDate", endDate.getValue());
            rowSet.setNamedWhereClauseParam("OraganisationName", orgid.getValue());
            rowSet.executeQuery();
             */
            
            //valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            
            am.setAttribute(valueChangeEvent.getNewValue(),1);
            if (valueChangeEvent.getNewValue() != null) {
                voucherNumber.setValue("");
                toVoucher.setValue("");
            }
            adviceDetailCB.setValue(false);

            // For showing Adjustment Type LOV Conditionally.
            if (voucherTypePgBind.getValue() != null) {
                if (voucherTypePgBind.getValue().toString().length() > 0 &&
                    Integer.parseInt(voucherTypePgBind.getValue().toString()) == 15) {
                    adjTypeBind.setVisible(Boolean.TRUE);
                    adjTypeBind.setValue(2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(adjTypeBind);

                } else {
                    adjTypeBind.setValue(null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(adjTypeBind);
                    adjTypeBind.setVisible(Boolean.FALSE);
                }
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(adviceDetailCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.voucherNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.toVoucher);

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

    public void setOrgid(RichSelectOneChoice orgid) {
        this.orgid = orgid;
    }

    public RichSelectOneChoice getOrgid() {
        return orgid;
    }

    /*   public void setVoucherNumber(RichSelectOneChoice voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public RichSelectOneChoice getVoucherNumber() {
        return voucherNumber;
    } */

    public void bookType_valueChangeListener(ValueChangeEvent vce) {
        if (vce.getNewValue() == null || vce.getNewValue().toString().equals("")) {
            if (dayBook_PgBind.isVisible()) {
                dayBook_PgBind.setVisible(false);

                if (daybookCB.getValue().toString().equalsIgnoreCase("true")) {
                    daybookCB.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(daybookCB);
                }

            }
        }

    }

    public void setVoucherTypePgBind(RichSelectOneChoice voucherTypePgBind) {
        this.voucherTypePgBind = voucherTypePgBind;
    }

    public RichSelectOneChoice getVoucherTypePgBind() {
        return voucherTypePgBind;
    }

    public void setBookTypePgBind(RichSelectOneChoice bookTypePgBind) {
        this.bookTypePgBind = bookTypePgBind;
    }

    public RichSelectOneChoice getBookTypePgBind() {
        return bookTypePgBind;
    }

    public void CoaNmValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // System.out.println("Value is "+valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null) {
            // System.out.println("Coa Name value is "+valueChangeEvent.getNewValue());
        }
    }

    public void BudgetValueChangeListener(ValueChangeEvent vceg) {
        String a = vceg.getNewValue().toString();
        //System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());
        if (a.equalsIgnoreCase("true")) {
            if (!checkBoxValidation()) {
                Budget = true;
            } else {
                FacesMessage message =
                    new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDate.getClientId(), message);
                }
                if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDate.getClientId(), message);
                }
                //set chaeckbox  false
                budgetCB.setValue(false);
            }
        } else {
            Budget = false;
        }
    }

    public void setBudgetCB(RichSelectBooleanCheckbox budgetCB) {
        this.budgetCB = budgetCB;
    }

    public RichSelectBooleanCheckbox getBudgetCB() {
        return budgetCB;
    }

    public void setBudget_Link(Boolean Budget_Link) {
        this.Budget_Link = Budget_Link;
    }

    public Boolean getBudget_Link() {
        return Budget_Link;
    }

    public void setBudget(Boolean Budget) {
        this.Budget = Budget;
    }

    public Boolean getBudget() {
        return Budget;
    }

    public void setTaxCoaWiseCB(RichSelectBooleanCheckbox taxCoaWiseCB) {
        this.taxCoaWiseCB = taxCoaWiseCB;
    }

    public RichSelectBooleanCheckbox getTaxCoaWiseCB() {
        return taxCoaWiseCB;
    }

    public void setTaxTaxWiseCB(RichSelectBooleanCheckbox taxTaxWiseCB) {
        this.taxTaxWiseCB = taxTaxWiseCB;
    }

    public RichSelectBooleanCheckbox getTaxTaxWiseCB() {
        return taxTaxWiseCB;
    }

    public void setTdsCoaWiseCB(RichSelectBooleanCheckbox tdsCoaWiseCB) {
        this.tdsCoaWiseCB = tdsCoaWiseCB;
    }

    public RichSelectBooleanCheckbox getTdsCoaWiseCB() {
        return tdsCoaWiseCB;
    }

    public void setTdsTaxWiseCB(RichSelectBooleanCheckbox tdsTaxWiseCB) {
        this.tdsTaxWiseCB = tdsTaxWiseCB;
    }

    public RichSelectBooleanCheckbox getTdsTaxWiseCB() {
        return tdsTaxWiseCB;
    }

    public void setTaxCoaWise_Link(Boolean TaxCoaWise_Link) {
        this.TaxCoaWise_Link = TaxCoaWise_Link;
    }

    public Boolean getTaxCoaWise_Link() {
        return TaxCoaWise_Link;
    }

    public void setTaxTaxwise_Link(Boolean TaxTaxwise_Link) {
        this.TaxTaxwise_Link = TaxTaxwise_Link;
    }

    public Boolean getTaxTaxwise_Link() {
        return TaxTaxwise_Link;
    }

    public void setTdsCoaWise_Link(Boolean TdsCoaWise_Link) {
        this.TdsCoaWise_Link = TdsCoaWise_Link;
    }

    public Boolean getTdsCoaWise_Link() {
        return TdsCoaWise_Link;
    }

    public void setTdsTaxWise_Link(Boolean TdsTaxWise_Link) {
        this.TdsTaxWise_Link = TdsTaxWise_Link;
    }

    public Boolean getTdsTaxWise_Link() {
        return TdsTaxWise_Link;
    }

    public void setTaxCoaWise(Boolean TaxCoaWise) {
        this.TaxCoaWise = TaxCoaWise;
    }

    public Boolean getTaxCoaWise() {
        return TaxCoaWise;
    }

    public void setTaxTaxwise(Boolean TaxTaxwise) {
        this.TaxTaxwise = TaxTaxwise;
    }

    public Boolean getTaxTaxwise() {
        return TaxTaxwise;
    }

    public void setTdsCoaWise(Boolean TdsCoaWise) {
        this.TdsCoaWise = TdsCoaWise;
    }

    public Boolean getTdsCoaWise() {
        return TdsCoaWise;
    }

    public void setTdsTaxWise(Boolean TdsTaxWise) {
        this.TdsTaxWise = TdsTaxWise;
    }

    public Boolean getTdsTaxWise() {
        return TdsTaxWise;
    }

    public void TaxCoaWiseChangeListener(ValueChangeEvent vceg) {
        String a = vceg.getNewValue().toString();
        //System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());
        if (a.equalsIgnoreCase("true")) {
            if (!checkBoxValidation()) {
                TaxCoaWise = true;
            } else {
                FacesMessage message =
                    new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDate.getClientId(), message);
                }
                if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDate.getClientId(), message);
                }
                //set chaeckbox  false
                taxCoaWiseCB.setValue(false);
            }
        } else {
            TaxCoaWise = false;
        }
    }

    public void TaxTaxWiseChangeListener(ValueChangeEvent vceg) {
        String a = vceg.getNewValue().toString();
        //System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());
        if (a.equalsIgnoreCase("true")) {
            if (!checkBoxValidation()) {
                TaxTaxwise = true;
            } else {
                FacesMessage message =
                    new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDate.getClientId(), message);
                }
                if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDate.getClientId(), message);
                }
                //set chaeckbox  false
                taxTaxWiseCB.setValue(false);
            }
        } else {
            TaxTaxwise = false;
        }
    }

    public void TdsCoaWiseChangeListener(ValueChangeEvent vceg) {
        String a = vceg.getNewValue().toString();
        //System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());
        if (a.equalsIgnoreCase("true")) {
            if (!checkBoxValidation()) {
                TdsCoaWise = true;
            } else {
                FacesMessage message =
                    new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDate.getClientId(), message);
                }
                if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDate.getClientId(), message);
                }
                //set chaeckbox  false
                tdsCoaWiseCB.setValue(false);
            }
        } else {
            TdsCoaWise = false;
        }
    }

    public void TdsTaxWiseChangeLisener(ValueChangeEvent vceg) {
        String a = vceg.getNewValue().toString();
        //System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());
        if (a.equalsIgnoreCase("true")) {
            if (!checkBoxValidation()) {
                TdsTaxWise = true;
            } else {
                FacesMessage message =
                    new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDate.getClientId(), message);
                }
                if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDate.getClientId(), message);
                }
                //set chaeckbox  false
                tdsTaxWiseCB.setValue(false);
            }
        } else {
            TdsTaxWise = false;
        }
    }

    public void setSheetTypePgBind(RichSelectOneChoice sheetTypePgBind) {
        this.sheetTypePgBind = sheetTypePgBind;
    }

    public RichSelectOneChoice getSheetTypePgBind() {
        return sheetTypePgBind;
    }

    public void SheetTypeValueChangeListener(ValueChangeEvent vce) {


        if (vce.getNewValue() == null || vce.getNewValue().toString().equals("")) {
            if (ComparisonSheet) {
                comparisonSheetLinkPgBind.setVisible(false);

                if (comparisonSheetCBPgBind.getValue().toString().equalsIgnoreCase("true")) {
                    comparisonSheetCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(comparisonSheetCBPgBind);
                }

            }
        }
    }


    public void comparisonSheetCBValueChangeListener(ValueChangeEvent valueChangeEvent) {
        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        if (value) {
            String a = valueChangeEvent.getNewValue().toString();


            if (a.equalsIgnoreCase("true")) {


                if (!checkBoxValidation()) {

                    if (sheetTypePgBind.getValue() != null && !sheetTypePgBind.getValue().toString().equals("")) {

                        ComparisonSheet = true;
                    } else {

                        comparisonSheetCBPgBind.setValue(false);

                        FacesMessage message = new FacesMessage("Please select Sheet Type...!!!");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(sheetTypePgBind.getClientId(), message);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(comparisonSheetCBPgBind);
                    }


                } else {


                    FacesMessage message =
                        new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(startDate.getClientId(), message);
                    }
                    if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(endDate.getClientId(), message);
                    }

                    comparisonSheetCBPgBind.setValue(false);

                }
            } else {


                comparisonSheetLinkPgBind.setVisible(false);

            }
        } else {


            ComparisonSheet = false;

        }
    }

    public void setComparisonSheetCBPgBind(RichSelectBooleanCheckbox comparisonSheetCBPgBind) {
        this.comparisonSheetCBPgBind = comparisonSheetCBPgBind;
    }

    public RichSelectBooleanCheckbox getComparisonSheetCBPgBind() {
        return comparisonSheetCBPgBind;
    }

    public void setComparisonSheet(Boolean ComparisonSheet) {
        this.ComparisonSheet = ComparisonSheet;
    }

    public Boolean getComparisonSheet() {
        return ComparisonSheet;
    }

    public void setComparisonSheet_Link(Boolean ComparisonSheet_Link) {
        this.ComparisonSheet_Link = ComparisonSheet_Link;
    }

    public Boolean getComparisonSheet_Link() {
        return ComparisonSheet_Link;
    }

    public void setComparisonSheetLinkPgBind(RichGoLink comparisonSheetLinkPgBind) {
        this.comparisonSheetLinkPgBind = comparisonSheetLinkPgBind;
    }

    public RichGoLink getComparisonSheetLinkPgBind() {
        return comparisonSheetLinkPgBind;
    }
    /*  public void getFyDate() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
    } */

    public void setAdviceDetails(Boolean AdviceDetails) {
        this.AdviceDetails = AdviceDetails;
    }

    public Boolean getAdviceDetails() {
        return AdviceDetails;
    }

    public void setAdviceDetails_Link(Boolean AdviceDetails_Link) {
        this.AdviceDetails_Link = AdviceDetails_Link;
    }

    public Boolean getAdviceDetails_Link() {
        return AdviceDetails_Link;
    }

    public void AdviceDetail_Vce(ValueChangeEvent vceg) {
        /*  System.out.println("vocher number is=" + voucherNumber.getValue());
        System.out.println("before con valoue of radio button is=" + postFlagValBindVar.getValue());
        System.out.println("voucher type is =" + voucherTypePgBind.getValue()); */
        String a = vceg.getNewValue().toString();
        // System.out.println("VALUE IS-"+a);
        //System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());
        if (a.equalsIgnoreCase("true")) {

            if (!checkBoxValidation()) {
                /*  System.out.println("Voucher value " + voucherTypePgBind.getValue().toString().equalsIgnoreCase("2"));
                System.out.println("valoue of radio button is=" +
                                   postFlagValBindVar.getValue().toString().equalsIgnoreCase("P")); */

                if (voucherTypePgBind.getValue() != null &&
                    (postFlagValBindVar.getValue().toString().equalsIgnoreCase("P") ||
                     postFlagValBindVar.getValue().toString().equalsIgnoreCase("A"))) {
                    if (voucherTypePgBind.getValue().toString().equalsIgnoreCase("2")) {
                        //  System.out.println("all conditon aree truueeee");
                        if (voucherNumber.getValue() != null && !voucherNumber.getValue().toString().equals("")) {
                            //  System.out.println("both conditon for voucher no is true");
                            // System.out.println("Set 3");
                            AdviceDetails = true;
                        } else {
                            //System.out.println("set 4");
                            FacesMessage message = new FacesMessage("Please select Voucher Number");
                            message.setSeverity(FacesMessage.SEVERITY_WARN);
                            FacesContext.getCurrentInstance().addMessage(voucherNumber.getClientId(), message);

                            adviceDetailCB.setValue(false);

                            AdfFacesContext.getCurrentInstance().addPartialTarget(adviceDetailCB);
                        }
                    } else if (voucherTypePgBind.getValue().toString().equalsIgnoreCase("3") &&
                               postFlagValBindVar.getValue().toString().equalsIgnoreCase("P")) {
                        if (voucherNumber.getValue() != null && !voucherNumber.getValue().toString().equals("")) {
                            //  System.out.println("both conditon for voucher no is true");
                            // System.out.println("Set 3");
                            AdviceDetails = true;
                        } else {
                            //System.out.println("set 4");
                            FacesMessage message = new FacesMessage("Please select Voucher Number");
                            message.setSeverity(FacesMessage.SEVERITY_WARN);
                            FacesContext.getCurrentInstance().addMessage(voucherNumber.getClientId(), message);

                            adviceDetailCB.setValue(false);

                            AdfFacesContext.getCurrentInstance().addPartialTarget(adviceDetailCB);
                        }
                    } else if (voucherTypePgBind.getValue().toString().equalsIgnoreCase("3") &&
                               postFlagValBindVar.getValue().toString().equalsIgnoreCase("A")) {
                        // System.out.println("not allow this plzzz another try");
                        FacesMessage message =
                            new FacesMessage("Please Select Postflag As Posted in Case Of Bank Reciept Voucher");
                        message.setSeverity(FacesMessage.SEVERITY_WARN);
                        FacesContext.getCurrentInstance().addMessage(voucherTypePgBind.getClientId(), message);

                        adviceDetailCB.setValue(false);

                        AdfFacesContext.getCurrentInstance().addPartialTarget(adviceDetailCB);
                    } else if (!voucherTypePgBind.getValue().toString().equalsIgnoreCase("3") &&
                               !voucherTypePgBind.getValue().toString().equalsIgnoreCase("2") &&
                               postFlagValBindVar.getValue().toString().equalsIgnoreCase("P")) {
                        if (voucherNumber.getValue() != null && !voucherNumber.getValue().toString().equals("")) {
                            //  System.out.println("both conditon for voucher no is true");
                            // System.out.println("Set 3");
                            AdviceDetails = true;
                        } else {
                            //System.out.println("set 4");
                            FacesMessage message = new FacesMessage("Please select Voucher Number");
                            message.setSeverity(FacesMessage.SEVERITY_WARN);
                            FacesContext.getCurrentInstance().addMessage(voucherNumber.getClientId(), message);

                            adviceDetailCB.setValue(false);

                            AdfFacesContext.getCurrentInstance().addPartialTarget(adviceDetailCB);
                        }
                    }

                    else {
                        //    System.out.println("set 5");
                        FacesMessage message =
                            new FacesMessage("Please select Voucher Type as Bank Payment Voucher or Bank Receipt Voucher");
                        message.setSeverity(FacesMessage.SEVERITY_WARN);
                        FacesContext.getCurrentInstance().addMessage(voucherTypePgBind.getClientId(), message);

                        adviceDetailCB.setValue(false);

                        AdfFacesContext.getCurrentInstance().addPartialTarget(adviceDetailCB);

                    }
                    // }
                }

            } else {
                FacesMessage message =
                    new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDate.getClientId(), message);
                }
                if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDate.getClientId(), message);
                }
                //set chaeckbox  false
                adviceDetailCB.setValue(false);
            }
        } else {
            AdviceDetails = false;
        }
    }

    public void setAdviceDetailCB(RichSelectBooleanCheckbox adviceDetailCB) {
        this.adviceDetailCB = adviceDetailCB;
    }

    public RichSelectBooleanCheckbox getAdviceDetailCB() {
        return adviceDetailCB;
    }


    public void VoucherStatus_Vce(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            voucherTypePgBind.setValue(null);
            bookTypePgBind.setValue(null);

            AdfFacesContext.getCurrentInstance().addPartialTarget(voucherTypePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bookTypePgBind);


            //setting checkbox values to null-------------

            generalLedgerCB.setValue(false);
            trialBalanceCB.setValue(false);
            trialBalanceConCB.setValue(false);
            blConH_CB.setValue(false);
            blConV_CB.setValue(false);
            blH_CB.setValue(false);
            blV_CB.setValue(false);
            pnLConH_CB.setValue(false);
            pnLConV_CB.setValue(false);
            pnLH_CB.setValue(false);
            pnLV_CB.setValue(false);
            generalLedgerCon_CB.setValue(false);
            printVoucherCB.setValue(false);
            daybookCB.setValue(false);
            budgetCB.setValue(false);
            taxCoaWiseCB.setValue(false);
            taxTaxWiseCB.setValue(false);
            tdsCoaWiseCB.setValue(false);
            tdsTaxWiseCB.setValue(false);
            comparisonSheetCBPgBind.setValue(false);
            adviceDetailCB.setValue(false);


            //Partial Target for check boxes------------

            AdfFacesContext.getCurrentInstance().addPartialTarget(generalLedgerCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(trialBalanceCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(trialBalanceConCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(blConH_CB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(blConV_CB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(blH_CB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(blV_CB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pnLConH_CB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pnLConV_CB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pnLH_CB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pnLV_CB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(generalLedgerCon_CB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(printVoucherCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(daybookCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(budgetCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxCoaWiseCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTaxWiseCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tdsCoaWiseCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tdsTaxWiseCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(comparisonSheetCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(adviceDetailCB);


        }
    }

    public void postFlagVCL(ValueChangeEvent valueChangeEvent) {
       /*  if (valueChangeEvent.getNewValue() != null) {
            adviceDetailCB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(adviceDetailCB);
        }
        */
        voucherNumber.setValue("");
        toVoucher.setValue("");
        this.chngComponentsValues();
        //printVocher_pgBind.setVisible(false);
        this.printVoucher = false;

    }

    public void voucherNumberVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            adviceDetailCB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.toVoucher);
            AdfFacesContext.getCurrentInstance().addPartialTarget(adviceDetailCB);
        }
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, BigDecimal total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void AmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            BigDecimal val = (BigDecimal)object;

            if (val.compareTo(new BigDecimal(0)) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.553']}").toString(), null));
            }

            /* if (val.compareTo(0) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.87']}").toString(), null));

            } */

            if (!isPrecisionValid(15, 2, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.56']}").toString(),
                                                              null)); //Invalid Precision(15,2)
            }

        }


    }

    public void AmountToValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            BigDecimal val = (BigDecimal)object;

            if (val.compareTo(new BigDecimal(0)) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.553']}").toString(), null));
            }

            /* if (val.compareTo(0) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.87']}").toString(), null));

            } */

            if (!isPrecisionValid(15, 2, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.56']}").toString(),
                                                              null)); //Invalid Precision(15,2)
            }

        }
    }

    public void setOtherReportVL(RichGoLink otherReportVL) {
        this.otherReportVL = otherReportVL;
    }

    public RichGoLink getOtherReportVL() {
        return otherReportVL;
    }

    public void setOtherReportCB(RichSelectBooleanCheckbox otherReportCB) {
        this.otherReportCB = otherReportCB;
    }

    public RichSelectBooleanCheckbox getOtherReportCB() {
        return otherReportCB;
    }

    public void otherReportVC(ValueChangeEvent valueChangeEvent) {

        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        //System.out.println("Print Voucher Value is "+value);

        if (value != null) {
            String a = valueChangeEvent.getNewValue().toString();

            // System.out.println("Date validation method worked----------->>>>>>>>"+checkBoxValidation());
            if (a.equalsIgnoreCase("true")) {
                if (!checkBoxValidation()) {
                    otherReportVL.setVisible(true);
                    // voucherTypePgBind.setDisabled(false);
                } else {
                    FacesMessage message =
                        new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(startDate.getClientId(), message);
                    }
                    if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(endDate.getClientId(), message);
                    }
                    //set chaeckbox  false
                    otherReportCB.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(otherReportCB);
                }
            } else {
                otherReportVL.setVisible(false);

            }

        } else
            otherReportVL.setVisible(false);

        //this.otherReportVL.setVisible(false);
    }

    /**
     * Code which will set all fields set by default
     */
    public void chngComponentsValues() {
        generalLedgerCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(generalLedgerCB);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gllink_Bvar);
        generalLedgerCon_CB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(generalLedgerCon_CB);
        trialBalanceCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(trialBalanceCB);
        trialBalanceConCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(trialBalanceConCB);
        printVoucherCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(printVoucherCB);
        adviceDetailCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(adviceDetailCB);
        otherReportCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(otherReportCB);
        taxCoaWiseCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxCoaWiseCB);
        taxTaxWiseCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxTaxWiseCB);
        tdsCoaWiseCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tdsCoaWiseCB);
        tdsTaxWiseCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tdsTaxWiseCB);
        daybookCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(daybookCB);
        comparisonSheetCBPgBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(comparisonSheetCBPgBind);
        blH_CB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(blH_CB);
        blV_CB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(blV_CB);
        blConV_CB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(blConV_CB);
        blConH_CB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(blConH_CB);
        pnLH_CB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pnLH_CB);
        pnLV_CB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pnLV_CB);
        pnLConV_CB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pnLConV_CB);
        pnLConH_CB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pnLConH_CB);
        budgetCB.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(budgetCB);
        
        this.generalLedger = false;
        this.generalLedgerConsolidate = false;
        this.trialBalance = false;
        this.trialBalanceConsolidate = false;
        this.printVoucher = false;
        this.printVoucherConsolidate = false;
        this.profitNLoss = false;
        this.bankCashBook = false;
        this.BalanceSheetConH = false;
        this.BalanceSheetConV = false;
        this.BalanceSheetH = false;
        this.BalanceSheetV = false;
        this.ProfitNLossConH = false;
        this.ProfitNLossConV = false;
        this.ProfitNLossV = false;
        this.ProfitNLossH = false;
        this.Budget = false;
        this.TaxCoaWise = false;
        this.TaxTaxwise = false;
        this.TdsCoaWise = false;
        this.TdsTaxWise = false;
        this.ComparisonSheet = false;
        this.AdviceDetails = false;
        
    }

    public void generalLedgerDL(DisclosureEvent dE) {
        
            this.chngComponentsValues();
        
    }

    public void StatutoryDL(DisclosureEvent dE) {
        
            this.chngComponentsValues();
            if(dE.isExpanded()){
            this.GenerateReportAction();
            }
        
    }

    public void DayBookDL(DisclosureEvent dE) {
        
            this.chngComponentsValues();
        if(dE.isExpanded()){
        this.GenerateReportAction();
        }
        
    }

    public void BsOrPnlDL(DisclosureEvent dE) {
        
            this.chngComponentsValues();
        if(dE.isExpanded()){
        this.GenerateReportAction();
        }
        
    }

    public void BudgetDL(DisclosureEvent dE) {
        
            this.chngComponentsValues();
        if(dE.isExpanded()){
        this.GenerateReportAction();
        }
        
    }

    public void setFileTypePgBind(RichSelectOneRadio fileTypePgBind) {
        this.fileTypePgBind = fileTypePgBind;
    }

    public RichSelectOneRadio getFileTypePgBind() {
        return fileTypePgBind;
    }

    public void firstRadioGroupVCL(ValueChangeEvent vce) {
        if(vce!= null){
            if(vce.getNewValue() != null){
                this.secondRadioGroupPgBind.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(secondRadioGroupPgBind);
                
                fileTypePgBind.setValue(vce.getNewValue());
                AdfFacesContext.getCurrentInstance().addPartialTarget(fileTypePgBind);
            }
        }
    }

    public void secondRadioGroupVCL(ValueChangeEvent vce) {
        if(vce!= null){
            if(vce.getNewValue() != null){
                this.firstRadioGroupPgBind.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(firstRadioGroupPgBind);
                
                fileTypePgBind.setValue(vce.getNewValue());
                AdfFacesContext.getCurrentInstance().addPartialTarget(fileTypePgBind);
            }
        }
    }

    public void setFirstRadioGroupPgBind(RichSelectOneRadio firstRadioGroupPgBind) {
        this.firstRadioGroupPgBind = firstRadioGroupPgBind;
    }

    public RichSelectOneRadio getFirstRadioGroupPgBind() {
        return firstRadioGroupPgBind;
    }

    public void setSecondRadioGroupPgBind(RichSelectOneRadio secondRadioGroupPgBind) {
        this.secondRadioGroupPgBind = secondRadioGroupPgBind;
    }

    public RichSelectOneRadio getSecondRadioGroupPgBind() {
        return secondRadioGroupPgBind;
    }

    public void setProductionBookCB(RichSelectBooleanCheckbox productionBookCB) {
        this.productionBookCB = productionBookCB;
    }

    public RichSelectBooleanCheckbox getProductionBookCB() {
        return productionBookCB;
    }

    public void proctionBookVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){ 
            Boolean b=(Boolean) valueChangeEvent.getNewValue();
            if(!b){
                this.productionBookLnk.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.productionBookLnk);
            }
        }  
    }

    public void setProductionBookLnk(RichGoLink productionBookLnk) {
        this.productionBookLnk = productionBookLnk;
    }

    public RichGoLink getProductionBookLnk() {
        return productionBookLnk;
    }

    public void bankSummaryVCE(ValueChangeEvent valueChangeEvent) {
        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        // System.out.println(value);
        if (value) {
            String a = valueChangeEvent.getNewValue().toString();


            if (a.equalsIgnoreCase("true")) {

                if (!checkBoxValidation()) {
                    if (bookTypePgBind.getValue() != null && !bookTypePgBind.getValue().toString().equals("")) {

                        this.bookTypeWiseSummaryLnk.setVisible(true);
                    } else {

                        this.bookTypeWiseSummaryBnd.setValue(false);

                        FacesMessage message = new FacesMessage("Please select Book Type");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(bookTypePgBind.getClientId(), message);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(daybookCB);
                    }


                } else {


                    FacesMessage message =
                        new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(startDate.getClientId(), message);
                    }
                    if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(endDate.getClientId(), message);
                    }

                    this.bookTypeWiseSummaryBnd.setValue(false);

                }
            } else {


                this.bookTypeWiseSummaryLnk.setVisible(false);

            }
        } else {


            this.bookTypeWiseSummaryLnk.setVisible(false);

        }
    }

    public void setBnkSummaryLnk(RichGoLink bnkSummaryLnk) {
        this.bnkSummaryLnk = bnkSummaryLnk;
    }

    public RichGoLink getBnkSummaryLnk() {
        return bnkSummaryLnk;
    }

    public void setBnkSummaryCB(RichSelectBooleanCheckbox bnkSummaryCB) {
        this.bnkSummaryCB = bnkSummaryCB;
    }

    public RichSelectBooleanCheckbox getBnkSummaryCB() {
        return bnkSummaryCB;
    }


    public void BookTypeWiseSummaryVCE(ValueChangeEvent valueChangeEvent) {
        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        // System.out.println(value);
        if (value) {
            String a = valueChangeEvent.getNewValue().toString();


            if (a.equalsIgnoreCase("true")) {

                if (!checkBoxValidation()) {
                   

                        this.bnkSummaryLnk.setVisible(true);
                        this.userLink=true;


                } else {


                    FacesMessage message =
                        new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(startDate.getClientId(), message);
                    }
                    if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(endDate.getClientId(), message);
                    }

                    this.bnkSummaryCB.setValue(false);

                }
            } else {


                this.bnkSummaryLnk.setVisible(false);

            }
        } else {


            this.bnkSummaryLnk.setVisible(false);

        }
    }

    public void setBookTypeWiseSummaryBnd(RichSelectBooleanCheckbox bookTypeWiseSummaryBnd) {
        this.bookTypeWiseSummaryBnd = bookTypeWiseSummaryBnd;
    }

    public RichSelectBooleanCheckbox getBookTypeWiseSummaryBnd() {
        return bookTypeWiseSummaryBnd;
    }

    public void setBookTypeWiseSummaryLnk(RichGoLink bookTypeWiseSummaryLnk) {
        this.bookTypeWiseSummaryLnk = bookTypeWiseSummaryLnk;
    }

    public RichGoLink getBookTypeWiseSummaryLnk() {
        return bookTypeWiseSummaryLnk;
    }

    public void listenNaChange(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.coaName.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.coaName);
    }

    public void listenCongChange(ValueChangeEvent valueChangeEvent) {
        
        //  System.out.println("in  listener");

        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.coaName.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.coaName);
        
        // For clear From Voucher No. data.
        if (valueChangeEvent.getNewValue() != null) {
            voucherNumber.setValue("");
            toVoucher.setValue("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(voucherNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(toVoucher);
    }
    }

    public void setCoaName(RichInputListOfValues coaName) {
        this.coaName = coaName;
    }

    public RichInputListOfValues getCoaName() {
        return coaName;
    }

    public void bookSummaryConsolidated(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
    }

    public void BookSummaryCosolidated(ValueChangeEvent valueChangeEvent) {
        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        //  System.out.println(value);
        if (value) {
            String a = valueChangeEvent.getNewValue().toString();


            if (a.equalsIgnoreCase("true")) {

                if (!checkBoxValidation()) {
                    if (bookTypePgBind.getValue() != null && !bookTypePgBind.getValue().toString().equals("")) {

                        this.gnBookConLnk.setVisible(true);
                    } else {

                        this.bookConsolidatedPgBind.setValue(false);

                        FacesMessage message = new FacesMessage("Please select Book Type");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(bookTypePgBind.getClientId(), message);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(daybookCB);
                    }


                } else {


                    FacesMessage message =
                        new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(startDate.getClientId(), message);
                    }
                    if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(endDate.getClientId(), message);
                    }

                    this.bookConsolidatedPgBind.setValue(false);

                }
            } else {


                this.gnBookConLnk.setVisible(false);

            }
        } else {


            this.gnBookConLnk.setVisible(false);

        }
    }

    public void setBookConsolidatedPgBind(RichSelectBooleanCheckbox bookConsolidatedPgBind) {
        this.bookConsolidatedPgBind = bookConsolidatedPgBind;
    }

    public RichSelectBooleanCheckbox getBookConsolidatedPgBind() {
        return bookConsolidatedPgBind;
    }

    public void setGnBookConLnk(RichGoLink gnBookConLnk) {
        this.gnBookConLnk = gnBookConLnk;
    }

    public RichGoLink getGnBookConLnk() {
        return gnBookConLnk;
    }

    public void dayBookConsolVCE(ValueChangeEvent valueChangeEvent) {
        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        //  System.out.println(value);
        if (value) {
            String a = valueChangeEvent.getNewValue().toString();


            if (a.equalsIgnoreCase("true")) {

                if (!checkBoxValidation()) {
                    if (bookTypePgBind.getValue() != null && !bookTypePgBind.getValue().toString().equals("")) {

                        this.bookSummaryConLnk.setVisible(true);
                    } else {

                        this.bookSummaryConsolBind.setValue(false);

                        FacesMessage message = new FacesMessage("Please select Book Type");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(bookTypePgBind.getClientId(), message);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(daybookCB);
                    }


                } else {


                    FacesMessage message =
                        new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(startDate.getClientId(), message);
                    }
                    if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(endDate.getClientId(), message);
                    }

                    this.bookSummaryConsolBind.setValue(false);

                }
            } else {


                this.bookSummaryConLnk.setVisible(false);

            }
        } else {


            this.bookSummaryConLnk.setVisible(false);

        }
    }

    public void setBookSummaryConsolBind(RichSelectBooleanCheckbox bookSummaryConsolBind) {
        this.bookSummaryConsolBind = bookSummaryConsolBind;
    }

    public RichSelectBooleanCheckbox getBookSummaryConsolBind() {
        return bookSummaryConsolBind;
    }

    public void setBookSummaryConLnk(RichGoLink bookSummaryConLnk) {
        this.bookSummaryConLnk = bookSummaryConLnk;
    }

    public RichGoLink getBookSummaryConLnk() {
        return bookSummaryConLnk;
    }

    public void FromvoucherChangeListener(ValueChangeEvent valueChangeEvent) {
        this.toVoucher.setValue(null);
        this.voucherNumber.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.toVoucher);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.voucherNumber);
    }

    public void setFromVoucherBind(RichSelectOneChoice fromVoucherBind) {
        this.fromVoucherBind = fromVoucherBind;
    }

    public RichSelectOneChoice getFromVoucherBind() {
        return fromVoucherBind;
    }

    public void toVoucherVCE(ValueChangeEvent valueChangeEvent) {

    }

    public void setToVoucherBind(RichSelectOneChoice toVoucherBind) {
        this.toVoucherBind = toVoucherBind;
    }

    public RichSelectOneChoice getToVoucherBind() {
        return toVoucherBind;
    }

    public void toVoucherCahngeEvent(ValueChangeEvent valueChangeEvent) {

    }

    /* public void setToVoucher(RichSelectOneChoice toVoucher) {
        this.toVoucher = toVoucher;
    }

    public RichSelectOneChoice getToVoucher() {
        return toVoucher;
    } */


    public void setGlUSerCB(RichSelectBooleanCheckbox glUSerCB) {
        this.glUSerCB = glUSerCB;
    }

    public RichSelectBooleanCheckbox getGlUSerCB() {
        return glUSerCB;
    }

    public void glUSerVCL(ValueChangeEvent valueChangeEvent) {
        Boolean value = (Boolean)valueChangeEvent.getNewValue();
        //  System.out.println(value);
        if (value) {
            String a = valueChangeEvent.getNewValue().toString();


            if (a.equalsIgnoreCase("true")) {

                if (!checkBoxValidation()) {
                   

                    this.userGlLink.setVisible(true);
                      

                } else {


                    FacesMessage message =
                        new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    if (startDate.getValue() != null && startDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(startDate.getClientId(), message);
                    }
                    if (endDate.getValue() != null && endDate.getValue().toString().length() != 0) {
                    } else {
                        fc.addMessage(endDate.getClientId(), message);
                    }

                    this.glUSerCB.setValue(false);

                }
            } else {


                this.userGlLink.setVisible(false);

            }
        } else {


              this.userGlLink.setVisible(false);

        }
    }

    public void setUserGlLink(RichGoLink userGlLink) {
        this.userGlLink = userGlLink;
    }

    public RichGoLink getUserGlLink() {
        return userGlLink;
    }

    public void setUserLink(Boolean userLink) {
        this.userLink = userLink;
    }

    public Boolean getUserLink() {
        return userLink;
    }

    public void produtionBookDetailCB(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){ 
            Boolean b=(Boolean) valueChangeEvent.getNewValue();
            if(!b){
                this.productionBookLnk.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.productionBookLDeatilnk);
            }
        }  
    }

    public void setProductionBookCBBind(RichSelectBooleanCheckbox productionBookCBBind) {
        this.productionBookCBBind = productionBookCBBind;
    }

    public RichSelectBooleanCheckbox getProductionBookCBBind() {
        return productionBookCBBind;
    }

    public void setProductionBookLDeatilnk(RichGoLink productionBookLDeatilnk) {
        this.productionBookLDeatilnk = productionBookLDeatilnk;
    }

    public RichGoLink getProductionBookLDeatilnk() {
        return productionBookLDeatilnk;
    }

    public void setVoucherNumber(RichInputListOfValues voucherNumber) {
        this.voucherNumber = voucherNumber;
}

    public RichInputListOfValues getVoucherNumber() {
        return voucherNumber;
    }

    public void setToVoucher(RichInputListOfValues toVoucher) {
        this.toVoucher = toVoucher;
    }

    public RichInputListOfValues getToVoucher() {
        return toVoucher;
    }

    public void setAdjTypeBind(RichSelectOneChoice adjTypeBind) {
        this.adjTypeBind = adjTypeBind;
    }

    public RichSelectOneChoice getAdjTypeBind() {
        return adjTypeBind;
    }
}
