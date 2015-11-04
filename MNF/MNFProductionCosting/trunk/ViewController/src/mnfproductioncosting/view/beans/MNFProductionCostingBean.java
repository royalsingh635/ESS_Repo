package mnfproductioncosting.view.beans;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;
import adf.utils.ebiz.WorkFlowUtils;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;

public class MNFProductionCostingBean {
    
    private String mode ="V";
    private RichInputDate startDateBind;
    private RichInputDate endDateBind;
    
    private String WfId;
    private RichInputListOfValues coANameBind;
    private RichInputText bindAmountValue;
    private RichTable allCOADetailTableBind;
    private RichButton populateBtnBind;
    private RichSelectBooleanCheckbox selectAllJEBind;
    private RichButton resetDataBind;
    private RichTable coADetailTableBind;
   

    public void setWfId(String WfId) {
        this.WfId = WfId;
    }

    public String getWfId() {
        return WfId;
    }

    /**
     * mode:
     * E : Add/Edit Mode
     * V : View Mode
     */

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
    
    public void setModetoEdit()
    {
        System.out.println("******** set mode to edit*******");
        this.setMode("E");
    }

    public MNFProductionCostingBean() {
    }


    public void SearchAL(ActionEvent actionEvent) {
        // Add event code here...
        
        System.out.println("in search action listner");
        ADFBeanUtils.findOperation("JobExecutionDetail").execute();
        
    }

    public void addAL(ActionEvent actionEvent) {
        this.setMode("E");
        System.out.println("in add action listner");
        ADFBeanUtils.findOperation("CreateInsert").execute();
    }

  
    public void editAL(ActionEvent actionEvent) {
        OperationBinding pendingAtOp = ADFBeanUtils.getOperationBinding("chkPendingUsr");
        pendingAtOp.execute();
        Integer pendingAt = (Integer) pendingAtOp.getResult();
        if (pendingAt.equals(-1) || pendingAt.equals(EbizParams.GLBL_APP_USR()))
          this.setMode("E");
        else {
            OperationBinding usrNmOp = ADFBeanUtils.getOperationBinding("getUsrNm");
            usrNmOp.getParamsMap().put("usrId", pendingAt);
            usrNmOp.execute();
            String usrNm = "Anonymous.";
            if (usrNmOp.getResult() != null)
                usrNm = (String) usrNmOp.getResult();
            ADFModelUtils.showFormattedFacesMessage("Production Costing is pending at another user.", 
                                                    "Document is pending at ["+usrNm+"].",
                                                    FacesMessage.SEVERITY_INFO);
        }
        
            
    }
    
    public void setStartDateBind(RichInputDate startDateBind) {
        this.startDateBind = startDateBind;
    }

    public RichInputDate getStartDateBind() {
        return startDateBind;
    }

    public void setEndDateBind(RichInputDate endDateBind) {
        this.endDateBind = endDateBind;
    }

    public RichInputDate getEndDateBind() {
        return endDateBind;
    }

    public void costCalcActionListener(ActionEvent actionEvent) {
        System.out.println("Calc is called");
        OperationBinding selectBind= ADFBeanUtils.getOperationBinding("checkValueInJEDetail");
        selectBind.execute();
        Boolean object =(Boolean) selectBind.getResult();
        System.out.println("Result is *****" +object);
        if(object){
            System.out.println("in if*****");
            ADFBeanUtils.getOperationBinding("getCost").execute(); 
            ADFBeanUtils.findOperation("delprdcstfrmsrc").execute(); 
            resetDataBind.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(resetDataBind);
        }
        else {
            System.out.println("in else ************");
            ADFModelUtils.showFormattedFacesMessage("Please select atleast One JE.", " ",
                                                    FacesMessage.SEVERITY_INFO);
        }   
        
    }

    public void saveAL(ActionEvent actionEvent) {
        System.out.println("in save action listner"); 
        
        OperationBinding ob=ADFBeanUtils.findOperation("validateAtSaveAndForward");
        ob.execute();
        Object result = ob.getResult();
        if(result.equals("N")) {
            ADFModelUtils.showFormattedFacesMessage("No Record in COA Details Table so Save can not be done.", " ",
                                                        FacesMessage.SEVERITY_ERROR); 
        }
        else {
            OperationBinding binding0 = ADFBeanUtils.findOperation("insertIntoWF");
            binding0.execute();
            Object res = binding0.getResult();
            Integer j = (res == null ? -1 : (Integer)res);
            if (j == 1) {
                OperationBinding commit = ADFBeanUtils.findOperation("Commit");
                commit.execute();
                System.out.println("******************" + commit.getErrors().isEmpty());
                if (commit.getErrors().isEmpty()) {
                    ADFModelUtils.showFormattedFacesMessage("Record has been Saved Successfully.", " ",
                                                            FacesMessage.SEVERITY_INFO);
                    setMode("V");
                }
            }
        }
        
    }
    
    /**** call workflow Id Function *****/
    
    public void callWfId() {
        OperationBinding ob = ADFBeanUtils.findOperation("getWfId");
        ob.execute();
        String wId = (String) ob.getResult();
         
        setWfId(wId);
    }

    public String saveAndForwardAction()  
    {    
        System.out.println("in save and forward action listner"); 
        String wfret = null;  
       
        
        OperationBinding ob=ADFBeanUtils.findOperation("validateAtSaveAndForward");
        ob.execute();
        Object res = ob.getResult();
        if(res.equals("N")) {
            ADFModelUtils.showFormattedFacesMessage("No Record in COA Details Table so SaveAndForward can not be done.", " ",
                                                        FacesMessage.SEVERITY_ERROR); 
            return null;
        }
        else {
            ADFBeanUtils.findOperation("delprdcstfrmsrc").execute();
            
            OperationBinding binding = ADFBeanUtils.findOperation("insertIntoWF");
            binding.execute();
            Object object = binding.getResult();
            Integer i = (object == null ? -1 : (Integer)object);
            if(i == 1){
                this.setMode("V");  
                callWfId();
                OperationBinding commitO = ADFBeanUtils.findOperation("Commit");
                commitO.execute();
                if(commitO.getErrors().isEmpty()){
                      /*   ADFModelUtils.showFormattedFacesMessage("Record has been Saved Successfully.", " ",
                                                                FacesMessage.SEVERITY_INFO);    */
                    setMode("V");
                    wfret = "WF";
                } 
            }  
        }
        
        return wfret;       
    }

    public void addCoADetailAL(ActionEvent actionEvent) {
        System.out.println("in Add CoaDetail action listner");
        Integer value=(Integer)ADFBeanUtils.findOperation("addCoaDetail").execute();
        System.out.println("value is?????????????" +value);
         if(value==1) { 
            FacesMessage Message1 = new FacesMessage("COA Name field cannot be blank");
                    Message1.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc1 = FacesContext.getCurrentInstance();
                    fc1.addMessage(coANameBind.getClientId(), Message1);
        }
             if(value==4) { 
                FacesMessage Message2 = new FacesMessage("COA filed name can not be duplicate");
                        Message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc2 = FacesContext.getCurrentInstance();
                        fc2.addMessage(coANameBind.getClientId(), Message2);
             }
             if(value==2) {
            FacesMessage Message3 = new FacesMessage("Amount field cannot be blank");
                    Message3.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc3 = FacesContext.getCurrentInstance();
                    fc3.addMessage(bindAmountValue.getClientId(), Message3);
             }
    }
    public void deleteCOADetailAL(ActionEvent actionEvent) {
        System.out.println("in Delete CoaDetail action listner");
        OperationBinding ob1 = ADFBeanUtils.findOperation("Delete");
        ob1.execute();
        OperationBinding ob3 = ADFBeanUtils.findOperation("getAllCOADetailVO");
        ob3.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(allCOADetailTableBind);
        
        ADFBeanUtils.findOperation("Execute").execute();

    }

    public String populateDataAC() {
        System.out.println("in populatedata action");
        OperationBinding binding = ADFBeanUtils.findOperation("populateData");
        binding.execute();
        
        Integer value=(Integer)binding.getResult();
        if(value==1) {
            
            FacesMessage Message = new FacesMessage("StartDate field cannot be blank");
                    Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(startDateBind.getClientId(), Message);
        }
        else if(value==2) {

            FacesMessage Message = new FacesMessage("EndDate field cannot be blnak");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(endDateBind.getClientId(), Message);
        }
        resetDataBind.setDisabled(false);
        populateBtnBind.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(resetDataBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(populateBtnBind);
        return null;
    }
    
    public void resetDataACL(ActionEvent actionEvent) {
        System.out.println("in resetdata actionlistner");
        OperationBinding binding = ADFBeanUtils.findOperation("resetData");
        binding.execute();
        populateBtnBind.setDisabled(false);
        resetDataBind.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(populateBtnBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(resetDataBind);
    }
    /**
     * Function to check precision of Number fields
     */
    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }
      
    public void amountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
     oracle.jbo.domain.Number amt=StaticValue.NUMBER_ZERO;
     
        if(object !=null){
            oracle.jbo.domain.Number amount=(oracle.jbo.domain.Number)object;
        
        if (!isPrecisionValid(26, 6, amount)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid Precision. Please enter valid precision(26, 6).",
                                                          null));
        }
        if (amount.compareTo(0) <= 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Value must be greater then Zero.", null));
        }
            if(amount.compareTo(amt)== -1){
                
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                                     "Amount can't be negative", null));
            }     
    }
 }

    public void setCoANameBind(RichInputListOfValues coANameBind) {
        this.coANameBind = coANameBind;
    }

    public RichInputListOfValues getCoANameBind() {
        return coANameBind;
    }

    public void setBindAmountValue(RichInputText bindAmountValue) {
        this.bindAmountValue = bindAmountValue;
    }

    public RichInputText getBindAmountValue() {
        return bindAmountValue;
    }


    public void startDateVCL(ValueChangeEvent valueChangeEvent) {
       AdfFacesContext.getCurrentInstance().addPartialTarget(endDateBind);
    }

    public void setAllCOADetailTableBind(RichTable allCOADetailTableBind) {
        this.allCOADetailTableBind = allCOADetailTableBind;
    }

    public RichTable getAllCOADetailTableBind() {
        return allCOADetailTableBind;
    }

    public void setPopulateBtnBind(RichButton populateBtnBind) {
        this.populateBtnBind = populateBtnBind;
    }

    public RichButton getPopulateBtnBind() {
        return populateBtnBind;
    }

    public void setSelectAllJEBind(RichSelectBooleanCheckbox selectAllJEBind) {
        this.selectAllJEBind = selectAllJEBind;
    }

    public RichSelectBooleanCheckbox getSelectAllJEBind() {
        return selectAllJEBind;
    }

    public void transCOANameId_attributeChangeListener(AttributeChangeEvent attributeChangeEvent) {
        // Add event code here...
    }


    public void setResetDataBind(RichButton resetDataBind) {
        this.resetDataBind = resetDataBind;
    }

    public RichButton getResetDataBind() {
        return resetDataBind;
    }

    public void setCoADetailTableBind(RichTable coADetailTableBind) {
        this.coADetailTableBind = coADetailTableBind;
    }

    public RichTable getCoADetailTableBind() {
        return coADetailTableBind;
    }


    public void CheckVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != null){
            AdfFacesContext.getCurrentInstance().addPartialTarget(populateBtnBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(resetDataBind);
        }
    }
}
