package applcparam.view;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichColumn;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.ColumnSelectionEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.SelectionEvent;


public class LCParamBean {

    String Mode = "V";
    String ButtonMode = "V";
    private RichColumn lcTableBind;
    private RichTable lcParamTableBind;
    Timestamp currDate;
    private RichInputDate paramDtBindVar;
    private RichInputText paramCHBindVar;
    private RichInputText paramNoBindVar;
    private RichSelectOneRadio paramBlBindVar;
    private RichInputDate inactvDtBindVar;
    private RichInputText inactvReasonBindVar;
    
    public void setCurrDate(Timestamp currDate) {
        this.currDate = currDate;
    }

    public Timestamp getCurrDate() {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        try {
           // Date d = new Date(t.dateValue().toString());
            t = new Timestamp(t.dateValue()+" 00:00:00");
            System.out.println("Value of Date-->"+t);
        } catch (SQLException e) {
        }


       currDate=t;
        return currDate;
    }

    public void setButtonMode(String ButtonMode) {
        this.ButtonMode = ButtonMode;
    }

    public String getButtonMode() {
        return ButtonMode;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        return Mode;
    }

    public LCParamBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void LCParamAddACL(ActionEvent actionEvent) {
        System.out.println("InAdd");
        oracle.binding.OperationBinding _addLCparam = getBindings().getOperationBinding("CreateInsert");
        _addLCparam.execute();
        oracle.binding.OperationBinding _setParamId = getBindings().getOperationBinding("setNewParamId");
        _setParamId.execute();
        Mode = "A";
        ButtonMode = "S";

    }

    public void LCParamEditACL(ActionEvent actionEvent) {
        // Add event code here...
        Mode = "E";
        ButtonMode = "S";
    }

    public void LCParamSaveACL(ActionEvent actionEvent) {
        /* * 1 : Param Name is not def
        * 2 : Param Basis is not def
        * 3 : Param Type is not def
        * 4 : Date invalid.
        * 5 : Character Invalid.
        * 6 : Number Invalid.
        * 7 : Boolean Invalid.
        * 8 : Inactive Date not defined.
        * 9 : Inactive Reason not defined.
        * 10 : Saved Sucessfully. */
        OperationBinding binding = ADFBeanUtils.getOperationBinding("saveLCParam");
        binding.execute();
        Object object = binding.getResult();
        Integer i = (object == null ? -1 : (Integer) object);
        switch (i) {
        case 1:
            {
                ADFModelUtils.showFacesMessage("Parameter Name is not defined !", "Please define Parameter Name.",
                                               FacesMessage.SEVERITY_FATAL, null);
                break;
            }
        case 2:
            {
                ADFModelUtils.showFacesMessage("Parameter Basis is not defined !", "Please define Parameter Basis.",
                                               FacesMessage.SEVERITY_FATAL, null);
                break;
            }
        case 3:
            {
                ADFModelUtils.showFacesMessage("Parameter Type is not defined !", "Please define Parameter Type.",
                                               FacesMessage.SEVERITY_FATAL, null);
                break;
            }
        case 4:
            {
                ADFModelUtils.showFacesMessage("Value for Parameter Type Date is not defined !",
                                               "Please define a Default Date !", FacesMessage.SEVERITY_FATAL, null);
                break;
            }
        case 5:
            {
                ADFModelUtils.showFacesMessage("Value for Parameter Type Character is not defined !",
                                               "Please define a Default Character value !", FacesMessage.SEVERITY_FATAL,
                                               null);
                break;
            }
        case 6:
            {
                ADFModelUtils.showFacesMessage("Value for Parameter Type Number is not defined or is Invalid !",
                                               "Please define a Default Number value !", FacesMessage.SEVERITY_FATAL,
                                               null);
                break;
            }
        case 7:
            {
                ADFModelUtils.showFacesMessage("Value for Parameter Type Boolean is not defined !",
                                               "Please define a Default Boolean value !", FacesMessage.SEVERITY_FATAL,
                                               null);
                break;
            }
        case 8:
            {
                ADFModelUtils.showFacesMessage("Inactive Date is Required if Parameter is Inactive !",
                                               "Please define Inactive Date.", FacesMessage.SEVERITY_FATAL, null);
                break;
            }
        case 9:
            {
                ADFModelUtils.showFacesMessage("Inactive Reason is Required if Parameter is Inactive !",
                                               "Please define Inactive Reason.", FacesMessage.SEVERITY_FATAL, null);
                break;
            }

        case 10:
            {
                ADFModelUtils.showFacesMessage("Record saved sucessfully !", "", FacesMessage.SEVERITY_INFO,
                                               null);
                Mode = "V";
                ButtonMode = "V";
                break;

            }
        }

    }

    public void LCParamCancelACL(ActionEvent actionEvent) {
        // Add event code here...

        oracle.binding.OperationBinding _cancelLCparam = getBindings().getOperationBinding("Rollback");
        _cancelLCparam.execute();
        Mode = "V";
        ButtonMode = "V";
    }

    public void LCParamSearchVCL(ActionEvent actionEvent) {
        oracle.binding.OperationBinding _searchLCparam = getBindings().getOperationBinding("searchLCParam");
        _searchLCparam.execute();
    }

    public void LCParamResetVCL(ActionEvent actionEvent) {
        oracle.binding.OperationBinding _searchLCparam = getBindings().getOperationBinding("resetLCParam");
        _searchLCparam.execute();
    }

    public void setLcTableBind(RichColumn lcTableBind) {
        this.lcTableBind = lcTableBind;
    }

    public RichColumn getLcTableBind() {
        return lcTableBind;
    }

    public void setLcParamTableBind(RichTable lcParamTableBind) {
        this.lcParamTableBind = lcParamTableBind;
    }

    public RichTable getLcParamTableBind() {
        return lcParamTableBind;
    }

    public void addOrgToLCACL(ActionEvent actionEvent) {
        /*  OperationBinding binding = ADFBeanUtils.getOperationBinding("CreateInsert1");
        binding.execute(); */
        OperationBinding bindingSave = ADFBeanUtils.getOperationBinding("addOrgToLCParam");
        bindingSave.execute();
    }

    public void AttachOrgDuplicateRecord(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String orgId = object.toString();
            OperationBinding orgAttach = ADFBeanUtils.getOperationBinding("duplicateOrgAttach");
            orgAttach.getParamsMap().put("OrgId", orgId);
            orgAttach.execute();
            Integer res = (Integer)orgAttach.getResult();
            if(res.equals(1)){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Duplicate Entry For Organisation !!!!",null));
            }


        }

    }

    public void paramNmCheckValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String pName = object.toString();
            OperationBinding dupParam = ADFBeanUtils.getOperationBinding("duplicateParamName");
            dupParam.getParamsMap().put("ParamNm", pName);
            dupParam.execute();
            Integer res = (Integer)dupParam.getResult();
            if(res.equals(1)){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Duplicate Entry For Parameter Name !!!",null));
            }
        }

    }

    public void AttachOrgDeleteACL(ActionEvent actionEvent) {
        // Add event code here...Delete
        OperationBinding orgDel = ADFBeanUtils.getOperationBinding("Delete");
        orgDel.execute();
    }


    public void setParamDtBindVar(RichInputDate paramDtBindVar) {
        this.paramDtBindVar = paramDtBindVar;
    }

    public RichInputDate getParamDtBindVar() {
        return paramDtBindVar;
    }

    public void paramTypeVCL(ValueChangeEvent vce) {
        // Add event code here...
     
        if(vce.getNewValue()!=null){
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            Integer typeVar = (Integer)vce.getNewValue();
            if(typeVar.equals(756)){
                AdfFacesContext.getCurrentInstance().addPartialTarget(paramDtBindVar);
            }
            else if(typeVar.equals(757)){
                AdfFacesContext.getCurrentInstance().addPartialTarget(paramCHBindVar);
            }
            else if(typeVar.equals(758)){
                AdfFacesContext.getCurrentInstance().addPartialTarget(paramNoBindVar);
            }
            else if(typeVar.equals(759)){
                AdfFacesContext.getCurrentInstance().addPartialTarget(paramBlBindVar);
            }
        }
    }

    public void setParamCHBindVar(RichInputText paramCHBindVar) {
        this.paramCHBindVar = paramCHBindVar;
    }

    public RichInputText getParamCHBindVar() {
        return paramCHBindVar;
    }

    public void setParamNoBindVar(RichInputText paramNoBindVar) {
        this.paramNoBindVar = paramNoBindVar;
    }

    public RichInputText getParamNoBindVar() {
        return paramNoBindVar;
    }

    public void setParamBlBindVar(RichSelectOneRadio paramBlBindVar) {
        this.paramBlBindVar = paramBlBindVar;
    }

    public RichSelectOneRadio getParamBlBindVar() {
        return paramBlBindVar;
    }

    public void activeVCL(ValueChangeEvent vce) {
        // Add event code here...
        if(vce.getNewValue()!=null){
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            String actv = vce.getNewValue().toString();
            if(actv.equalsIgnoreCase("Y")){
                setInactvDtBindVar(null);
                setInactvReasonBindVar(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactvDtBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactvReasonBindVar);
            }
            else{
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactvDtBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactvReasonBindVar);
            }
        }
    }

    public void setInactvDtBindVar(RichInputDate inactvDtBindVar) {
        this.inactvDtBindVar = inactvDtBindVar;
    }

    public RichInputDate getInactvDtBindVar() {
        return inactvDtBindVar;
    }

    public void setInactvReasonBindVar(RichInputText inactvReasonBindVar) {
        this.inactvReasonBindVar = inactvReasonBindVar;
    }

    public RichInputText getInactvReasonBindVar() {
        return inactvReasonBindVar;
    }

   


    
}
