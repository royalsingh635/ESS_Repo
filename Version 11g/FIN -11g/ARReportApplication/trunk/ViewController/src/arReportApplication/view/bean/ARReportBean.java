package arReportApplication.view.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;


import oracle.jbo.domain.Date;



public class ARReportBean {
    private Boolean DebtorAgeingCons_link=false;
    private Boolean DebtorAgeingConsSum_Link=false;
    private Boolean supplierAgeing_Link=false;
    private Boolean statement_Link=false;
    private Boolean pendingAdvance_Link=false;
    private Boolean pendingBills_Link=false;
    private Boolean coaReport_Link=false;
    private Boolean billKnockoff_Link=false;
    private Boolean topNCreditors=false;
    private Boolean consolidate_Statement_Link=false;
    private Boolean DebtorAgeingSummary_Link=false;
    
    private Boolean DebtorAgeingCons=false;
    private Boolean DebtorAgeingConsSum=false;
    private Boolean supplierAgeing=false;
    private Boolean statement=false;
    private Boolean pendingAdvance=false;
    private Boolean pendingBills=false;
    private Boolean coaReport=false;
    private Boolean billKnockoff=false;
    private Boolean topNCreditorsVal=false;
    private Boolean consolidate_Statement=false;
    private RichInputDate startDate;
    private RichInputDate endDate;
    private Boolean DebtorAgeingSummary=false;
   private RichSelectOneRadio vouchers;
   private String vouchertype = "P";
   private String dateselect ="V";
   private String LineSel = "L";
   private String AmtSel = "O";
    private RichSelectBooleanCheckbox supplierAgeingBV;
    private RichInputDate asOnDateBV;
    private String AsOnDate=null;
    
    private String CurrencyChange = "T";
    private  String AmountSelect = "O";
    private RichSelectBooleanCheckbox debtorAgeingBV;
    private RichSelectBooleanCheckbox debtorAgeingConsBindVar;
    private RichSelectBooleanCheckbox debtorAgeingConsSumBindVar;
    private RichSelectOneRadio batchFor;

    public void supplierAgeingChangeListner(ValueChangeEvent vcesa) {
        String a=vcesa.getNewValue().toString();
        
        if (a.equalsIgnoreCase("true")){
                    supplierAgeingBV.setValue(true);
                       // System.out.println("a----->"+a);
                    if (a.equalsIgnoreCase("true")){
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
      String a=vcest.getNewValue().toString();
      
      if (a.equalsIgnoreCase("true")){
          statement=true;
     
      }
      else {
          statement = false;
      }
      
    }
    public void pendingAdvanceChangeListner(ValueChangeEvent vcepa) {
      String a=vcepa.getNewValue().toString();
      
      if (a.equalsIgnoreCase("true")){
          pendingAdvance=true;
     
      }
      else {
          pendingAdvance = false;
      }
      
    }
    public void pendingBillsChangeListner(ValueChangeEvent vcepb) {
      String a=vcepb.getNewValue().toString();
     
      if (a.equalsIgnoreCase("true")){
          pendingBills=true;
     
      }
      else {
          pendingBills = false;
      }
      
    }
    public void Consolidate_Statement_ChangeListener(ValueChangeEvent vcecs) {
        String a=vcecs.getNewValue().toString();
      
        if (a.equalsIgnoreCase("true")){
            consolidate_Statement=true;
        
        }
        else {
            consolidate_Statement = false;
        }
    }
    public void coaChangeListener(ValueChangeEvent vcecoa) {
        String a=vcecoa.getNewValue().toString();
       
        if (a.equalsIgnoreCase("true")){
            coaReport=true;
        
        }
        else {
            coaReport = false;
        }
    }
    
    public void billKnockoffChangeListener(ValueChangeEvent vcebko) {
        String a=vcebko.getNewValue().toString();
        
        if (a.equalsIgnoreCase("true")){
            billKnockoff=true;
        
        }
        else {
            billKnockoff = false;
        }
    }

    public void topNCreditors(ValueChangeEvent vcetnc ) {
        String a=vcetnc.getNewValue().toString();
       
        if (a.equalsIgnoreCase("true")){
            topNCreditorsVal=true;
        
        }
        else {
            topNCreditorsVal = false;
        }
    }

    public String GenerateReportAction() {
        
        
        
        if(supplierAgeing==true){
           // System.out.println("inside supplierAgeing==true ");
            if(asOnDateBV.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }else{
                    setSupplierAgeing_Link(true); 
                 }
            }
        else{
            setSupplierAgeing_Link(false);
         }
        
        
        
        if(statement==true){
            if(startDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message); 
            }else{
                if(endDate.getValue()==null) {
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message); 
                }else{
                    setStatement_Link(true);
                 }
             }
            
        }else{
            setStatement_Link(false);
         }
        if(pendingAdvance==true){
           
            
            if(startDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            else{
                
            
            if(endDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            else{
                setPendingAdvance_Link(true);
            }
            }
        }
        else{
            setPendingAdvance_Link(false);
        }
        
        
        if(pendingBills==true){
        
            if(startDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            else{
                
            
            if(endDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            else{
                setPendingBills_Link(true);
            }
            }
            
        }
        else{
            setPendingBills_Link(false);
        }
        
        
        if(coaReport==true){
        

            
            if(startDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            else{
                
            
            if(endDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            else{
                setCoaReport_Link(true);
            }
            }
           
        }
        else{
            setCoaReport_Link(false);
        }
        
        
        if(billKnockoff==true){
        
        
            if(startDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            else{
                
            
            if(endDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message); 
            }
            else{
                setBillKnockoff_Link(true);
            }
            }
            
            
            
            
        }
        else{
            setBillKnockoff_Link(false);
        }
        
        
        if(topNCreditorsVal==true){
        
        
            if(startDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message); 
            }
            else{
                
            
            if(endDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            else{
                setTopNCreditors(true);
            }
            }
           
        }
        else{
            setTopNCreditors(false);
        }
        
        
        if(consolidate_Statement==true){
        
            if(startDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.290']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message); 
            }
            else{
                
            
            if(endDate.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.291']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message); 
            }
            else{
                setConsolidate_Statement_Link(true);
            }
            }
           
        }
        else{
            setConsolidate_Statement_Link(false);
        }
        
        
        
        
        
        if(DebtorAgeingSummary==true){
        
            if(asOnDateBV.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message); 
            }
            
            else{
                setDebtorAgeingSummary_Link(true);
            }
            
           
        }
        else{
            setDebtorAgeingSummary_Link(false);
        }
        
        if(DebtorAgeingCons==true){
           // System.out.println("inside supplierAgeing==true ");
            if(asOnDateBV.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }else{
                    setDebtorAgeingCons_link(true); 
                 }
            }
        else{
            setDebtorAgeingCons_link(false);
         }
        if(DebtorAgeingConsSum==true){
           // System.out.println("inside supplierAgeing==true ");
            if(asOnDateBV.getValue()==null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1142']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }else{
                    setDebtorAgeingConsSum_Link(true); 
                 }
            }
        else{
            setDebtorAgeingConsSum_Link(false);
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

    public void debtorAgeingSummCjangeLst(ValueChangeEvent vcesa) {
        /* String a=vce.getNewValue().toString();
        if (a.equalsIgnoreCase("true")){
            DebtorAgeingSummary_Link = true;
        
        }
        else {
            DebtorAgeingSummary_Link = false;
        } */
        String a=vcesa.getNewValue().toString();
        
        if (a.equalsIgnoreCase("true")){
                    debtorAgeingBV.setValue(true);
                       // System.out.println("a----->"+a);
                    if (a.equalsIgnoreCase("true")){
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

     public void voucherTypeVLC(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue()!=null){
            vouchertype = valueChangeEvent.getNewValue().toString();
            //System.out.println(vouchertype);
            }
        else{
            vouchertype = "P";
        }
    } 

    public void dateForAgeingValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue()!= null){ 
            dateselect = valueChangeEvent.getNewValue().toString();
            //System.out.println("dateselect-----------"+dateselect);
            
                   }
        else{
            dateselect = "V";
            }
    }

    public void amtRangeSelectionForValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){
        AmtSel = valueChangeEvent.getNewValue().toString();
        //System.out.println("AmtSel---------"+AmtSel);
        }
          else{
            AmtSel = "O";
            }
    }

    public void amtRangeForValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){
        LineSel = valueChangeEvent.getNewValue().toString();
       // System.out.println("linesel---------"+LineSel);
        }
          else{
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
        if (valueChangeEvent.getNewValue()!=null){
            AmountSelect = valueChangeEvent.getNewValue().toString();
        }
        else{
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

    public void setAsOnDateBV(RichInputDate asOnDateBV) {
        this.asOnDateBV = asOnDateBV;
    }

    public RichInputDate getAsOnDateBV() {
        return asOnDateBV;
    }

    public void setAsOnDate(String AsOnDate) {
        this.AsOnDate = AsOnDate;
    }

    public String getAsOnDate() {   
            return AsOnDate;
    }

    public void asOnDateValueChangeListner(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue()!=null){
            AsOnDate = valueChangeEvent.getNewValue().toString();
            SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
            AsOnDate = sdf.format(valueChangeEvent.getNewValue());
            //System.out.println(AsOnDate+"--------------");
            
        }
        else {
            AsOnDate = Date.getCurrentDate().toString();
            //System.out.println(AsOnDate+"when null-----------");
            
            }
    }

    public void setLineSel(String LineSel) {
        this.LineSel = LineSel;
    }

    public String getLineSel() {
        return LineSel;
    }

    public void currencyGroupingValuechangeListner(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue()!=null){
            CurrencyChange = valueChangeEvent.getNewValue().toString();
           // System.out.println(CurrencyChange + "----------------currrencychange");
        
        }
        else{
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
        String a=vce.getNewValue().toString();
        
        if (a.equalsIgnoreCase("true")){
                    debtorAgeingConsBindVar.setValue(true);
                       // System.out.println("a----->"+a);
                    if (a.equalsIgnoreCase("true")){
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
        String a=vce.getNewValue().toString();
        
        if (a.equalsIgnoreCase("true")){
                    debtorAgeingConsSumBindVar.setValue(true);
                       // System.out.println("a----->"+a);
                    if (a.equalsIgnoreCase("true")){
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
}
