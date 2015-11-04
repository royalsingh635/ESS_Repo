package slsdocumentconfirmation.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

public class SalesCreateBean {
    private String mode = "V";
    private RichInputListOfValues docIdBind;
    private RichTable docConfTablBinding;

    /**
     * The Value of mode is V and E
     * V=View Mode
     * E=Edit Mode
     * */


    public SalesCreateBean() {
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    /**
     *This code is use to Disable the Button
     * @param actionEvent
     */
    public void addAction(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
        createDocConf();
    }

    public void editACTION(ActionEvent actionEvent) {
        this.mode = "E";
    }

    public void saveACTION(ActionEvent actionEvent) {
        this.mode = "V";
        OperationBinding binding = ADFBeanUtils.getOperationBinding("Commit");
        binding.execute();
        if (binding.getErrors().isEmpty()) {
            mode = "V";
            ADFModelUtils.showFormattedFacesMessage((ADFModelUtils.resolvRsrc("MSG.2670")),             //Record Save Successfully!
                                                    (ADFModelUtils.resolvRsrc("MSG.2670")),            //Record Save Successfully!
                                                    FacesMessage.SEVERITY_INFO);
        }
    }


    /**
     * cancelACTION is to Perform the Operation to Cancel work
     * @return
     */
    public String cancelACTION() {
        this.mode = "V";
        OperationBinding binding = ADFBeanUtils.getOperationBinding("Rollback");
        binding.execute();
        return "backTOslsDocumentConfirmationTF";
    }

    /**
     *backACTION is to perform the Operation to go previous stage
     * @param actionEvent
     */


    /**
     *
     */
    public void createDocConf() {
        this.mode = "E";
        System.out.println("createDocConf--------------->");

    }

    public String backAction() {
        this.mode = "E";
        return "backTOslsDocumentConfirmationTF";
    }

    public void addSrcDetails(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addSrcDetailsMethod");
        binding.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer)o);
        
        if(i.equals(1)){
            ADFModelUtils.showFacesMessage((ADFModelUtils.resolvRsrc("MSG.2671")),               //Document Confirmation Type is not selected !
                                                    (ADFModelUtils.resolvRsrc("MSG.2672")),       //Please select Document Confirmation Type !
                                                    FacesMessage.SEVERITY_ERROR,getComponentCliendIdFromId(actionEvent.getComponent(),"soc1"));
        }else if(i.equals(2)){
            ADFModelUtils.showFacesMessage((ADFModelUtils.resolvRsrc("MSG.2673")),                    //Document is not selected !
                                                    (ADFModelUtils.resolvRsrc("MSG.2674")),           //Please select a Document !
                                                    FacesMessage.SEVERITY_ERROR,getComponentCliendIdFromId(actionEvent.getComponent(),"transSrcDispIdId"));
        }else if(i.equals(3)){
            ADFModelUtils.showFacesMessage((ADFModelUtils.resolvRsrc("MSG.2675")),        //Duplicate Record Exist for the Selected Document Type!
                                                    (ADFModelUtils.resolvRsrc("MSG.2676")),         //Please Select Another Document Type and Id !
                                                    FacesMessage.SEVERITY_ERROR,getComponentCliendIdFromId(actionEvent.getComponent(),"transSrcDispIdId"));
        }else if(i.equals(-1)){
            ADFModelUtils.showFormattedFacesMessage((ADFModelUtils.resolvRsrc("MSG.2677")),         //There have been some error while adding document in AMImpl !
                                                    " ", FacesMessage.SEVERITY_ERROR);
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(docConfTablBinding);

        /* OperationBinding checkBind = ADFBeanUtils.getOperationBinding("checkForDuplicateEntry");
        checkBind.execute();
        Boolean resCheck = (Boolean) checkBind.getResult();
        System.out.println("resCheck :::: "+resCheck);
        if (resCheck.equals(true)) {
            
        } else if(resCheck.equals(false)) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("addSrcDetailsMethod");
            binding.execute();
            
            Boolean res = (Boolean) binding.getResult();
            System.out.println("res:::: "+res);
            if (res) {

            } else {
                ADFModelUtils.showFormattedFacesMessage("There Must be some error in addSrcDetailsMethod in AMImpl !",
                                                        " ", FacesMessage.SEVERITY_ERROR);
            }
        } */
    }
    public String getComponentCliendIdFromId(UIComponent comp, String id) {
        if (comp != null) {
            StringBuilder clientId = new StringBuilder(comp.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            return clientId.append(id).toString();
        } else {
            return null;
        }
    }

    public void setDocIdBind(RichInputListOfValues docIdBind) {
        this.docIdBind = docIdBind;
    }

    public RichInputListOfValues getDocIdBind() {
        return docIdBind;
    }

    public void confTypeVCL(ValueChangeEvent vce) {
        if(vce.getNewValue()!=null){
            docIdBind.setValue(null);
        }
    }

    public void setDocConfTablBinding(RichTable docConfTablBinding) {
        this.docConfTablBinding = docConfTablBinding;
    }

    public RichTable getDocConfTablBinding() {
        return docConfTablBinding;
    }
}
