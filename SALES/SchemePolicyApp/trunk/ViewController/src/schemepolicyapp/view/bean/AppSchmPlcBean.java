package schemepolicyapp.view.bean;

import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class AppSchmPlcBean {
    public String AddMode = "A";
    public String ViewMode = "V";
    public String delMode="D";
    private RichTable tabBind;
    private RichSelectOneChoice catgIdBind;
    private RichInputListOfValues custNmBind;
    private RichInputListOfValues execNmBind;
    private RichInputText schmIdBind;
    private RichInputListOfValues schmnmTrans;


    public void setDelMode(String delMode) {
        this.delMode = delMode;
    }

    public String getDelMode() {
        return delMode;
    }

    public AppSchmPlcBean() {
    }

    private BindingContainer getBinding() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void searchActionListener(ActionEvent actionEvent) {
        getBinding().getOperationBinding("SearchMethod").execute();
    }

    public void resetActionListener(ActionEvent actionEvent) {
        getBinding().getOperationBinding("ResetMethod").execute();
    }

    public void schemeBasisVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            getBinding().getOperationBinding("addEditFilterForAppSchmPlcVo").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
           AdfFacesContext.getCurrentInstance().addPartialTarget(catgIdBind) ;
            AdfFacesContext.getCurrentInstance().addPartialTarget(custNmBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(execNmBind);
        }
    }

    public void customerNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            getBinding().getOperationBinding("addEditFilterForAppSchmPlcVo").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schmIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(catgIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schmnmTrans);
        }
    }

    public void categoryVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            getBinding().getOperationBinding("addEditFilterForAppSchmPlcVo").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schmIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(catgIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schmnmTrans);
           
            
        }
    }

    public void execNmVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            getBinding().getOperationBinding("addEditFilterForAppSchmPlcVo").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schmIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(catgIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schmnmTrans);
        }
    }

    public void schemeNmVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            getBinding().getOperationBinding("addEditFilterForAppSchmPlcVo").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schmIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(catgIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schmnmTrans);
            
        }
    }

    public void addAction(ActionEvent actionEvent) {
        AddMode = "V";
        ViewMode = "A";

    }

    public void editAction(ActionEvent actionEvent) {
        AddMode = "V";
        ViewMode = "A";
        delMode="A";


    }

    public void saveAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("executeAppSchmQueryVo").execute();
        getBinding().getOperationBinding("Commit").execute();
        ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2838"), ADFModelUtils.resolvRsrc("MSG.2840"), FacesMessage.SEVERITY_INFO); //Your Changes have been Successfully  done !!  //Data Have Been Saved Successfully!
        AddMode = "A";
        ViewMode = "V";
        delMode="D";
    }

    public void cancelAction(ActionEvent actionEvent) {
        getBinding().getOperationBinding("Rollback").execute();
        AddMode = "A";
        ViewMode = "V";
        delMode="D";
    }

    public void setAddMode(String AddMode) {
        this.AddMode = AddMode;
    }

    public String getAddMode() {
        return AddMode;
    }

    public void setViewMode(String ViewMode) {
        this.ViewMode = ViewMode;
    }

    public String getViewMode() {
        return ViewMode;
    }

    public void UpdateAttachedPolicy(ActionEvent actionEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
           AdfFacesContext.getCurrentInstance().addPartialTarget(catgIdBind) ;
            AdfFacesContext.getCurrentInstance().addPartialTarget(custNmBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(execNmBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(schmIdBind);
           AdfFacesContext.getCurrentInstance().addPartialTarget(schmnmTrans);
        OperationBinding checkBind = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("mandatoryCheck");
        checkBind.execute();
        Boolean check=(Boolean)checkBind.getResult();
        System.out.println("REs is from Madatory check :::: "+check);
        if(check.equals(false)){
            OperationBinding ob = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("UpdatePolicy");
            ob.execute();
            Boolean res = (Boolean) ob.getResult();
            if (res.equals(true)) {

            } else {
                System.out.println("Error Occured!!!!");
               
            }
        }
        else{
           // ADFModelUtils.showFormattedFacesMessage("The Required Field's Can not be Empty!", "Please Select the Field's to attach Policy!", FacesMessage.SEVERITY_ERROR);
        }
       
       
     
    }

    public void deleteMethod(ActionEvent actionEvent) {
        OperationBinding ob = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("deletePolicy");
        ob.execute();
        System.out.println("ob.getResult():::: "+ob.getResult());
        Boolean res = (Boolean) ob.getResult();
        if (res.equals(true)) {

        } else {
            AddMode = "A";
            ViewMode = "V";
            delMode="D";
            System.out.println("Error Occured!!!!");
        }
    }

    public void setTabBind(RichTable tabBind) {
        this.tabBind = tabBind;
    }

    public RichTable getTabBind() {
        return tabBind;
    }

    public void setCatgIdBind(RichSelectOneChoice catgIdBind) {
        this.catgIdBind = catgIdBind;
    }

    public RichSelectOneChoice getCatgIdBind() {
        return catgIdBind;
    }

    public void setCustNmBind(RichInputListOfValues custNmBind) {
        this.custNmBind = custNmBind;
    }

    public RichInputListOfValues getCustNmBind() {
        return custNmBind;
    }

    public void setExecNmBind(RichInputListOfValues execNmBind) {
        this.execNmBind = execNmBind;
    }

    public RichInputListOfValues getExecNmBind() {
        return execNmBind;
    }

    public void setSchmIdBind(RichInputText schmIdBind) {
        this.schmIdBind = schmIdBind;
    }

    public RichInputText getSchmIdBind() {
        return schmIdBind;
    }

    public void setSchmnmTrans(RichInputListOfValues schmnmTrans) {
        this.schmnmTrans = schmnmTrans;
    }

    public RichInputListOfValues getSchmnmTrans() {
        return schmnmTrans;
    }
}
