package slsreptemplateapp.view.bean;

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

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SlsRepTemplateBean {
    public String mode="E";
    private RichSelectOneRadio rdioButtonBind;
    private RichPanelGroupLayout panelBind;
    private RichTable dtlBind;
    private RichPanelFormLayout panelFormBind;
    private RichTable mastrtblBind;
    private RichInputListOfValues tmptNmBind;
    private RichInputText tmpNmBind;
    private RichCommandImageLink addButtonBind;
    private RichCommandImageLink editButtonBind;
    private RichCommandImageLink saveButtonBind;
    private RichCommandImageLink cancelButtonBind;
    private RichCommandImageLink searchButtonBind;
    private RichCommandImageLink resetButtonBind;
    private RichCommandImageLink backbuttonBind;

    public SlsRepTemplateBean() {
    }
    
    public BindingContainer getBinding() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void searchTemplateAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("searchTemplate").execute();
    }

    public void resetTemplateAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("resetTemplate").execute();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void AddTemplateAction(ActionEvent actionEvent) {
        System.out.println("Came in method..........");
        setMode("E");
        
            rdioButtonBind.setValue("all");
            resetDetail();
        getBinding().getOperationBinding("Createwithparameters").execute();
        getBinding().getOperationBinding("saveTemplate").execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(mastrtblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tmptNmBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rdioButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tmpNmBind);
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(addButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(editButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(resetButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rdioButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(backbuttonBind);
    }

    public void editTemplateAction(ActionEvent actionEvent) {
        System.out.println("Came in method..........");
        if(!((rdioButtonBind.getValue()).equals("all")))
        {
            System.out.println("Came in IF ................");
            rdioButtonBind.setValue("all");
            resetDetail();
        }
        setMode("E");
    }

    public void saveTemplateAction(ActionEvent actionEvent) {
        OperationBinding binding = getBinding().getOperationBinding("getDtlRowCount");
        binding.execute();
        Integer number = Integer.parseInt(binding.getResult().toString());
        if(number == 0) {
            //FacesMessage message = new FacesMessage("Please Select Atleast One Parameter Type !!!");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1157']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        else
        {
            getBinding().getOperationBinding("Commit").execute();
            getBinding().getOperationBinding("Execute").execute();
            setMode("V");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.818']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(mastrtblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tmptNmBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rdioButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tmpNmBind);
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(addButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(editButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(resetButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rdioButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(backbuttonBind);
    }

    public void cancelTemplateAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("Rollback").execute();
        setMode("V");
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlBind);
        //refreshPage();
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(mastrtblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tmptNmBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rdioButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tmpNmBind);
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(addButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(editButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(resetButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rdioButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(backbuttonBind);
    }

    public void orgAddAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("Createwithparameters1").execute();
        getBinding().getOperationBinding("Execute").execute();
    }

    public void custAddAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("Createwithparameters4").execute();
        getBinding().getOperationBinding("Execute").execute();
    }

    public void itmAddAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("Createwithparameters5").execute();
        
        getBinding().getOperationBinding("Execute").execute();
        
    }

    public void catgryAddAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("Createwithparameters6").execute();
        
        getBinding().getOperationBinding("Execute").execute();
    }

    public void slsManAddAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("Createwithparameters7").execute();
        getBinding().getOperationBinding("Execute").execute();
    }

    public void itmGrpAddAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("Createwithparameters2").execute();
        getBinding().getOperationBinding("Execute").execute();
    }

    public void statusAddAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("Createwithparameters3").execute();
        getBinding().getOperationBinding("Execute").execute();
    }
    public void resetDetail() {
        OperationBinding binding = getBinding().getOperationBinding("filterTemplateDetail");
        binding.getParamsMap().put("val", 0);
        binding.execute();
    }
    public void currAddAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("Createwithparameters8").execute();
        getBinding().getOperationBinding("Execute").execute();
    }

    public void deleteDetailAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("Delete").execute();
        getBinding().getOperationBinding("Execute").execute();
    }

    public void radioButtonFilterAction(ValueChangeEvent ve) {
        ve.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String val = ve.getNewValue().toString();
        Integer num=0;
        
        if(val.equals("cust")) {
            num = 471;
        }
        else if(val.equals("itm")) {
            num = 474;
        }
        else if(val.equals("slsMan")) {
            num = 475;
        }
        else if(val.equals("curr")) {
            num = 478;
        }
        else if(val.equals("stat")) {
            num = 473;
        }
        else if(val.equals("org")) {
            num = 472;
        }
        else if(val.equals("grp")) {
            num = 476;
        }
        else if(val.equals("ctgry")) {
            num = 477;
        }
        OperationBinding binding = getBinding().getOperationBinding("filterTemplateDetail");
        binding.getParamsMap().put("val", num);
        binding.execute();
    }

    public void setRdioButtonBind(RichSelectOneRadio rdioButtonBind) {
        this.rdioButtonBind = rdioButtonBind;
    }

    public RichSelectOneRadio getRdioButtonBind() {
        return rdioButtonBind;
    }

    public void orgValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("Value of Object is: "+object+"----------");
        OperationBinding binding = getBinding().getOperationBinding("validateOrg");
        binding.getParamsMap().put("orgId", object.toString());
        Object execute = binding.execute();
        Boolean flag = (Boolean)execute;
        if(flag == false) {
            System.out.println("-------------------------Same value Found-----------------");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg(),null));
        }
    }

    public void ItemGroupValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("Group iD is:  "+object);
        
        OperationBinding binding = getBinding().getOperationBinding("validateitmGrp");
        binding.getParamsMap().put("grpId", object.toString());
        Object execute = binding.execute();
        Boolean flag = (Boolean)binding.getResult();
        System.out.println("Result is:   "+flag+">>>>>>>>>>");
        if(flag == false) {
            System.out.println("-------------------------Same value Found-----------------");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg(),null));
        }
    }

    public void statusValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        OperationBinding binding = getBinding().getOperationBinding("validateStatus");
        binding.getParamsMap().put("stusId", object.toString());
        Object execute = binding.execute();
        Boolean flag = (Boolean)execute;
        if(flag == false) {
            System.out.println("-------------------------Same value Found-----------------");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg(),null));
        }
    }

    public void customerValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        OperationBinding binding = getBinding().getOperationBinding("validateCutomer");
        binding.getParamsMap().put("name", object.toString());
        binding.execute();
        boolean flag = (Boolean)binding.getResult();
        System.out.println("Value of flag is: "+flag);
            if(flag == false) {
                System.out.println("-------------------------Same value Found-----------------");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg(),null));
            }
    }

    public void categoryValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        OperationBinding binding = getBinding().getOperationBinding("validateCategory");
        binding.getParamsMap().put("name", object.toString());
        binding.execute();
        boolean flag = (Boolean)binding.getResult();
        System.out.println("Value of flag is: "+flag);
            if(flag == false) {
                System.out.println("-------------------------Same value Found-----------------");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg(),null));
            }
    }

    public void currencyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here... validateCurrency
        OperationBinding binding = getBinding().getOperationBinding("validateCurrency");
        binding.getParamsMap().put("name", object.toString());
        binding.execute();
        boolean flag = (Boolean)binding.getResult();
        System.out.println("Value of flag is: "+flag);
            if(flag == false) {
                System.out.println("-------------------------Same value Found-----------------");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg(),null));
            }
    }

    public void salesManValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here... 
        OperationBinding binding = getBinding().getOperationBinding("validateSalesMan");
        binding.getParamsMap().put("name", object.toString());
        binding.execute();
        boolean flag = (Boolean)binding.getResult();
        System.out.println("Value of flag is: "+flag);
            if(flag == false) {
                System.out.println("-------------------------Same value Found-----------------");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg(),null));
            }
    }

    public void itemValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...  
        
        OperationBinding binding = getBinding().getOperationBinding("validateItem");
        binding.getParamsMap().put("name", object.toString());
        binding.execute();
        boolean flag = (Boolean)binding.getResult();
        System.out.println("Value of flag is: "+flag);
            if(flag == false) {
                System.out.println("-------------------------Same value Found-----------------");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg(),null));
            }
    }

    public void templateNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        OperationBinding binding = getBinding().getOperationBinding("validateTemplateName");
        binding.getParamsMap().put("name", object.toString());
        binding.execute();
        boolean flag = (Boolean)binding.getResult();
        System.out.println("Value of flag is: "+flag);
            if(flag == false) {
                System.out.println("-------------------------Same value Found-----------------");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg(),null));
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
       
    public String errMsg() {
        return resolvElDCMsg("#{bundle['MSG.1156']}").toString();
    }
    
    
    protected void refreshPage() {
    FacesContext fctx = FacesContext.getCurrentInstance();
    String refreshpage = fctx.getViewRoot().getViewId();
    ViewHandler ViewH = fctx.getApplication().getViewHandler();
    UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
    UIV.setViewId(refreshpage);
    fctx.setViewRoot(UIV);
    }

    public void setPanelBind(RichPanelGroupLayout panelBind) {
        this.panelBind = panelBind;
    }

    public RichPanelGroupLayout getPanelBind() {
        return panelBind;
    }

    public void setDtlBind(RichTable dtlBind) {
        this.dtlBind = dtlBind;
    }

    public RichTable getDtlBind() {
        return dtlBind;
    }

    public void editTempAction(ActionEvent actionEvent) {
        System.out.println("------------------------------1----------------2");
       rdioButtonBind.setValue("all");
       resetDetail();
        setMode("E");
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(mastrtblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tmptNmBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rdioButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tmpNmBind);
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(addButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(editButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(resetButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rdioButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(backbuttonBind);
    }

    public void setPanelFormBind(RichPanelFormLayout panelFormBind) {
        this.panelFormBind = panelFormBind;
    }

    public RichPanelFormLayout getPanelFormBind() {
        return panelFormBind;
    }

    public void setMastrtblBind(RichTable mastrtblBind) {
        this.mastrtblBind = mastrtblBind;
    }

    public RichTable getMastrtblBind() {
        return mastrtblBind;
    }

    public void setTmptNmBind(RichInputListOfValues tmptNmBind) {
        this.tmptNmBind = tmptNmBind;
    }

    public RichInputListOfValues getTmptNmBind() {
        return tmptNmBind;
    }

    public void setTmpNmBind(RichInputText tmpNmBind) {
        this.tmpNmBind = tmpNmBind;
    }

    public RichInputText getTmpNmBind() {
        return tmpNmBind;
    }

    public void setAddButtonBind(RichCommandImageLink addButtonBind) {
        this.addButtonBind = addButtonBind;
    }

    public RichCommandImageLink getAddButtonBind() {
        return addButtonBind;
    }

    public void setEditButtonBind(RichCommandImageLink editButtonBind) {
        this.editButtonBind = editButtonBind;
    }

    public RichCommandImageLink getEditButtonBind() {
        return editButtonBind;
    }

    public void setSaveButtonBind(RichCommandImageLink saveButtonBind) {
        this.saveButtonBind = saveButtonBind;
    }

    public RichCommandImageLink getSaveButtonBind() {
        return saveButtonBind;
    }

    public void setCancelButtonBind(RichCommandImageLink cancelButtonBind) {
        this.cancelButtonBind = cancelButtonBind;
    }

    public RichCommandImageLink getCancelButtonBind() {
        return cancelButtonBind;
    }

    public void setSearchButtonBind(RichCommandImageLink searchButtonBind) {
        this.searchButtonBind = searchButtonBind;
    }

    public RichCommandImageLink getSearchButtonBind() {
        return searchButtonBind;
    }

    public void setResetButtonBind(RichCommandImageLink resetButtonBind) {
        this.resetButtonBind = resetButtonBind;
    }

    public RichCommandImageLink getResetButtonBind() {
        return resetButtonBind;
    }

    public void setBackbuttonBind(RichCommandImageLink backbuttonBind) {
        this.backbuttonBind = backbuttonBind;
    }

    public RichCommandImageLink getBackbuttonBind() {
        return backbuttonBind;
    }
}
