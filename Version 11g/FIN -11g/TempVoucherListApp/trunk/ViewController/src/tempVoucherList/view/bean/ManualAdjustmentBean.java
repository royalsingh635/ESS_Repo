package tempVoucherList.view.bean;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import tempVoucherList.view.bean.ADFUtil.*;

import javax.faces.event.ValueChangeEvent;


import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;


import org.apache.myfaces.trinidad.event.SelectionEvent;



public class ManualAdjustmentBean {
   
    private RichPopup advancePopupBinding;
    private Number zero=new Number(0);
    private RichTable dtlTableBinding;
    private RichSelectBooleanCheckbox entityBinding;
    private RichOutputText totalTrxAmountBinding;
    private RichPopup unpostedPopup;
    private RichPopup postedAmountBinding;
    private RichPopup unpostedDetailBinding;
    private RichPanelFormLayout formbinding;
    private RichPopup postedAdvpopupBinding;
    private Number baseAmount=new Number(0);
    private RichTable adjTableBinding;

    public ManualAdjustmentBean() {
    }

    public String FitlerArapWithDaysAndType() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("fetchAdjustmentDataForManual");
        binding.execute();
        OperationBinding bindin1 = bc.getOperationBinding("filterArapShuttle");
        System.out.println("entityBinding.getValue() = "+entityBinding.getValue());
        if (entityBinding.getValue()!= null) {
            if (entityBinding.getValue().toString().equalsIgnoreCase("false")) {
                bindin1.getParamsMap().put("Ext1", "N");
            } else {
                bindin1.getParamsMap().put("Ext1", "Y");

            }
        }else{
            bindin1.getParamsMap().put("Ext1", "N");

        }
        bindin1.execute();
        OperationBinding createOpBAddress = ADFUtil.getBindings().getOperationBinding("setAdjustmentSummaryCol");
        createOpBAddress.execute();
        return null;
    }

  
    

    public String openShuttleToFilter() {

        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("setCheckFlagOnArap");
        binding.execute();
        ADFUtil.showPopup(advancePopupBinding,true);
        return null;
    }


    
   
    public String backAction() {
        AdfFacesContext.getCurrentInstance().addPartialTarget(adjTableBinding);
        ViewObject lines = ADFUtil.getAM().findViewObject("TvouLinesLnk");
        if(lines!=null){
            Row currentRow = lines.getCurrentRow();
            if(currentRow!=null){
                Number TvouCc = (Number)currentRow.getAttribute("TvouCc");
                setBaseAmount(((Number)totalTrxAmountBinding.getValue()).multiply(TvouCc));
            }
        }
        return "back";
    }

  


   

    public String InsertIntoTvouAdjDtl() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("insertIntoTvouAdjAutoDtl");
        binding.execute();
        advancePopupBinding.hide();
        adjustAction();
        return null;
    }

    public String checkAllAdvances() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("checkAllAdvances");
        binding.execute();
        return null;
    }

    public String unCheckAllAdvances() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("unCheckAllAdvances");
        binding.execute();
        return null;
    }


    public String selectAllAdvanceInAdvanceTable() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("selectAllAdvanceInAdjDtl");
        binding.execute();      
        return null;
    }

    public String refreshDetails() {
        OperationBinding createOpBAddress = ADFUtil.getBindings().getOperationBinding("refreshAdjustedAmount");
        createOpBAddress.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(formbinding);
        return null;
    }

    public String adjustAction() {
        OperationBinding createOpBAddress = ADFUtil.getBindings().getOperationBinding("adjustAmount");
        createOpBAddress.execute();
        Object object = createOpBAddress.getResult();
        if(object!=null){
            ADFUtil.showFacesMsg("Not Enough Balance To Adjust", "Please Enter Amount less than Balance Amount", FacesMessage.SEVERITY_ERROR, null);
        }
        return null;
    }
   
    public void transactionAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            Number amount = (Number)object;
            if (!ADFUtil.isPrecisionValid(26, 6, (Number)object)) {
                System.out.println("invalid precisoion");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid precision/Scale! Please Enter Valid Amount.",
                                                              null));

            } else if (amount.compareTo(0) < 0) {
            
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter Amount more than 0",
                                                              null));
           
            } else {
                
                ViewObject TvouAdjAutoVO = ADFUtil.getAM().findViewObject("TvouAdjAutoVO");
                Row currentRow = TvouAdjAutoVO.getCurrentRow();
                if (currentRow != null) {
                    Number TvouCurrCcTrx = (Number)currentRow.getAttribute("TvouCurrCcTrx");
                    Number TvouCcCurr = (Number)currentRow.getAttribute("TvouCcCurr");
                    amount = ADFUtil.roundOffAmt((amount.multiply(TvouCurrCcTrx)).divide(TvouCcCurr));
                  /*   System.out.println(" TvouCurrCcTrx = " + TvouCurrCcTrx + "TvouCcCurr = " + TvouCcCurr +
                                       " amount = " + amount); */
                    Number TvouRadjAmtAdj = (Number)currentRow.getAttribute("TvouRadjAmtAdj");
                    Number TvouAdjAmtOT = (Number)currentRow.getAttribute("TvouAdjAmtOT");
                    Number rembal = TvouAdjAmtOT.subtract(TvouRadjAmtAdj);
                   /*  System.out.println("TvouRadjAmtAdj = " + TvouRadjAmtAdj + " TvouAdjAmtOT = " + TvouAdjAmtOT +
                                       " rembal = " + rembal); */
                    if (amount.compareTo(rembal) > 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Enter Transaction Amount less than Balance Amount",
                                                                      null));

                    } else
                        AdfFacesContext.getCurrentInstance().addPartialTarget(totalTrxAmountBinding);
                }
            }
        }

    }

    public void InvoiceTrxAmtVCE(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        if (valueChangeEvent.getNewValue() != null) {
            
            BindingContainer bc=ADFUtil.getBindings();
            OperationBinding binding = bc.getOperationBinding("thisAdjAmount");
            binding.getParamsMap().put("amount", valueChangeEvent.getNewValue());
            binding.execute();


        }
    }

    public void tvouAdjSelectionListner(SelectionEvent selectionEvent) {
        ADFUtil.invokeEL("#{bindings.TvouAdjAutoVO.collectionModel.makeCurrent}",new Class[]{SelectionEvent.class},new Object[]{selectionEvent});
        BindingContainer bc=ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("refreshDetailAdjustedAmountValue");
        binding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlTableBinding);

    }
   
    public void dtlTransactionAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number amount = (Number)object;
        
        if(object!= null){
                            if(!ADFUtil.isPrecisionValid(26, 6, (Number)object)){
                                System.out.println("invalid precisoion");
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Precision/Scale! Please Enter Valid Amount.",null));

                            }
           else if(amount.compareTo(0)<0){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Enter Amount more than 0",null));
            }
            else {
                  Number tvouCurrCc = (Number)uIComponent.getAttributes().get("TvouCcCurr");
                                System.out.println("");
                  Number tvouCurrCcTrx = (Number)uIComponent.getAttributes().get("TvouCurrCcTrx");
                  amount=ADFUtil.roundOffAmt((amount.multiply(tvouCurrCcTrx)).divide(tvouCurrCc));      
/*                   System.out.println("tvouCurrCc = "+tvouCurrCc+" tvouCurrCcTrx = "+tvouCurrCcTrx+"  amount = "+amount); */

                                
                Number TotalAdjustedAmount = (Number)uIComponent.getAttributes().get("totalAdjustedAmount");
                  Number outStandingAmount = (Number)uIComponent.getAttributes().get("outStandingAmount");
                 Number adjustedAmount = (Number)uIComponent.getAttributes().get("adjustedAmount");
                Number rembal=outStandingAmount.subtract(TotalAdjustedAmount.subtract(adjustedAmount));
/*                                 System.out.println("outStandingAmount = "+outStandingAmount+" TotalAdjustedAmount = "+TotalAdjustedAmount+" adjustedAmount = "+" rembal = "+rembal); */
                    if(amount.compareTo(rembal)>0){
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Not Enough Balance",null));

                }
              }
            }  
        
    }

 
    public String filterArapWithEntityWise() {
        Object object = entityBinding.getValue();
        System.out.println("Enitty  Wise ="+object);
        if(object!= null){
            String Entity = object.toString();
            BindingContainer bc=ADFUtil.getBindings();
            OperationBinding bindin1 = bc.getOperationBinding("filterArapShuttle");
            if(Entity.equalsIgnoreCase("true")){
                bindin1.getParamsMap().put("Ext1", "Y");

            }else {
                bindin1.getParamsMap().put("Ext1", "N");

            }
            bindin1.execute();

        }
        return null;
    }

    

    public void deleteAdjDtlAction(ActionEvent actionEvent) {
        String TvouRadjDispId = (String)actionEvent.getComponent().getAttributes().get("TvouRadjDispId");        //
        BindingContainer bc=ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("deleteFromDetail");
        binding.getParamsMap().put("displayId", TvouRadjDispId);
        binding.execute();

    }

    public String openUnpostedVoucherDetails() {

        BindingContainer bc=ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("filterUnpostedVoucherDetail");
        binding.execute();
        ADFUtil.showPopup(unpostedPopup, true);
        return null;
    }

   
    public String openPostedVoucherDetail() {
        BindingContainer bc=ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("filterPostedVoucherDetail");
        binding.execute();
        ADFUtil.showPopup(postedAmountBinding, true);     
        return null;
    }
    public void unpostedAmountDetail(ActionEvent actionEvent) {
                BindingContainer bc=ADFUtil.getBindings();
                OperationBinding binding = bc.getOperationBinding("filterUnpostedAdvVoucherDetail");
                binding.execute();
                ADFUtil.showPopup(unpostedDetailBinding, true);  
            }
    public String postedAdvDetail() {
        BindingContainer bc=ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("filterPostedAdvVoucherDetail");
        binding.execute();
        ADFUtil.showPopup(postedAdvpopupBinding, true);     
        return null;
    }

    public void setUnpostedPopup(RichPopup unpostedPopup) {
        this.unpostedPopup = unpostedPopup;
    }

    public RichPopup getUnpostedPopup() {
        return unpostedPopup;
    }
    public void setTotalTrxAmountBinding(RichOutputText totalTrxAmountBinding) {
        this.totalTrxAmountBinding = totalTrxAmountBinding;
    }

    public RichOutputText getTotalTrxAmountBinding() {
        return totalTrxAmountBinding;
    }
    public void setPostedAmountBinding(RichPopup postedAmountBinding) {
        this.postedAmountBinding = postedAmountBinding;
    }

    public RichPopup getPostedAmountBinding() {
        return postedAmountBinding;
    }

    
    public void setUnpostedDetailBinding(RichPopup unpostedDetailBinding) {
        this.unpostedDetailBinding = unpostedDetailBinding;
    }

    public RichPopup getUnpostedDetailBinding() {
        return unpostedDetailBinding;
    }

  

    public void setFormbinding(RichPanelFormLayout formbinding) {
        this.formbinding = formbinding;
    }

    public RichPanelFormLayout getFormbinding() {
        return formbinding;
    }
  
    public void setPostedAdvpopupBinding(RichPopup postedAdvpopupBinding) {
        this.postedAdvpopupBinding = postedAdvpopupBinding;
    }

    public RichPopup getPostedAdvpopupBinding() {
        return postedAdvpopupBinding;
    }

    public void setBaseAmount(Number baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Number getBaseAmount() {
        return baseAmount;
    }

    public void setAdjTableBinding(RichTable adjTableBinding) {
        this.adjTableBinding = adjTableBinding;
    }

    public RichTable getAdjTableBinding() {
        return adjTableBinding;
    }
    public void setZero(Number zero) {
        this.zero = zero;
    }

    public Number getZero() {
        return zero;
    }

    public void setDtlTableBinding(RichTable dtlTableBinding) {
        this.dtlTableBinding = dtlTableBinding;
    }

    public RichTable getDtlTableBinding() {
        return dtlTableBinding;
    }

    public void setEntityBinding(RichSelectBooleanCheckbox entityBinding) {
        this.entityBinding = entityBinding;
    }

    public RichSelectBooleanCheckbox getEntityBinding() {
        return entityBinding;
    }
    public void setAdvancePopupBinding(RichPopup advancePopupBinding) {
        this.advancePopupBinding = advancePopupBinding;
    }

    public RichPopup getAdvancePopupBinding() {
        return advancePopupBinding;
    }
}
