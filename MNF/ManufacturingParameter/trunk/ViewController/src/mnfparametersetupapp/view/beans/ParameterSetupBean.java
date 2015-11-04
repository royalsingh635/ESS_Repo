package mnfparametersetupapp.view.beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import mnfparametersetupapp.view.utilis.ADFUtils;

import mnfparametersetupapp.view.utilis.JSFUtils;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;

import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.rules.JboPrecisionScaleValidator;


public class ParameterSetupBean {

    //various screen mode V=View, E=Edit, C=Create

    String modeParam = "V";
    String modeSet = "V";
    String modeOrg = "V";
    private RichPanelSplitter mainPanelSplitterCalpse;

    OperationBinding ob = null;
    private RichShowDetailItem accordianDisp;
    private RichSelectOneChoice selectParamSetType = null;
    private RichInputDate setDateBindVar;
    private RichInputText setResnBindVar;
    private RichSelectBooleanCheckbox setActiveBindVar;
    private RichSelectBooleanCheckbox parmActivBindVar;
    private RichInputDate paramInactivDtBindVar;
    private RichInputText paramInactivResnBindVar;
    private RichSelectBooleanCheckbox paramSetActiveBindVar;
    private RichSelectBooleanCheckbox orgParamActiveBindVar;
    private RichInputDate orgParamInctivDTBindVar;
    private RichInputText orgParamInavtiveResnBindVar;
    private RichSelectBooleanCheckbox replicateAllOrgBind;
    private RichSelectOneChoice paramSetTypeBind;
    private RichPopup popUpBindVar;
    private RichTable orgTableBindVar;
    private RichInputText setNameBindVar;
    private RichInputText paramNameBindVar;
    private RichSelectOneChoice paramValTypBindVar;
    private RichInputText paramValueBindVar;
    private RichTable paramTableBindVar;
    private RichInputText paramValuesBind;
 


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public ParameterSetupBean() {
    }

    /**
     * Add New Parameter method
     */
    public void addNewParamACE(@SuppressWarnings("unused") ActionEvent actionEvent) {

        setModeParam("C");
        ADFUtils.findOperation("CreateInsertMnfParam").execute();
        setModeSet("V");
        getAccordianDisp().setDisclosed(true); 
    }

    /**
     * Edit Parameter method
     */

    public void editParamACE(@SuppressWarnings("unused") ActionEvent actionEvent) {

        setModeParam("E");
        setModeSet("V");
        getAccordianDisp().setDisclosed(true);
    }

    /**
     * Cancel event for  Parameter method
     */

    public void cancelParamACE(@SuppressWarnings("unused") ActionEvent actionEvent) {
        setModeParam("V");
        setModeOrg("V");
        ADFUtils.findOperation("Rollback").execute();
    }

    /**
     * Save changes to Parameter method
     */
    public void saveParamACE(@SuppressWarnings("oracle.jdeveloper.java.unused-parameter") ActionEvent actionEvent) {

        ob = ADFUtils.findOperation("checkDuplicatParamName");
        ob.execute();


        if (ob.getResult().toString().equals("Y")) {
            JSFUtils.addFacesErrorMessage("Parameter Name already exist!");
        } else {
            ADFUtils.findOperation("replicateOrgForParam").execute();
            ADFUtils.findOperation("beforeSave").execute();            
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Record saved sucessfully!!");
            setModeParam("V");
            setModeOrg("V");
        }

    }

    /**
     * Add New Parameter Set method
     */
    public void addNewParamSetACE(@SuppressWarnings("unused") ActionEvent actionEvent) {

        ADFUtils.findOperation("CreateWithParamsSetParam").execute();
        setModeSet("C");
        setModeParam("V");
    }

    /**
     * Edit Parameter Set method
     */
    public void editParamSetACE(@SuppressWarnings("unused") ActionEvent actionEvent) {
        setModeSet("E");
        setModeParam("V");
    }

    /**
     * Cancel event for Parameter Set method
     */
    public void cancelParamSetACE(@SuppressWarnings("unused") ActionEvent actionEvent) {
        setModeSet("V");
        ADFUtils.findOperation("Rollback").execute();
    }

    /**
     * Save changes to Parameter Set method
     */
    public void saveParamSetACE(@SuppressWarnings("unused") ActionEvent actionEvent) {

        ob = ADFUtils.findOperation("checkDuplicatSetName");
        ob.execute();


        if (ob.getResult().toString().equals("Y")) {
            JSFUtils.addFacesErrorMessage("Parameter Set Name already exist!");

        } else {
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Record saved sucessfully!");
            setModeSet("V");
        }

    }

    /**
     * Valid values for each parameter validation method
     */
    public void isPrecisionValid(@SuppressWarnings("unused") FacesContext facesContext,
                                 @SuppressWarnings("unused") UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer setpar = (Integer) paramSetTypeBind.getValue();
            //System.out.println(setpar + " parameter set type");
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) object;
            Boolean isValid = isPrecisionValueValid(26, 6, value);
            Boolean percValid = isPrecisionValueValid(5, 2, value);
            if (setpar == 37) {
                if (isValid == false) {
                    FacesMessage message = new FacesMessage("Precision upto 20.6 digit only!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
                if (value.compareTo(0) < 0) {
                    FacesMessage message = new FacesMessage("Value must not be negative");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }

            } else if (setpar == 39) {

                if (value.compareTo(0) < 0 || value.compareTo(100) > 0) {
                    FacesMessage message = new FacesMessage("Value range must be between 0 and 100");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }

                if (percValid == false) {
                    FacesMessage message = new FacesMessage("Precision upto 2.2 digit only!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }

            } else {
                if (isValid == false) {
                    FacesMessage message = new FacesMessage("Precision upto 6 digit only!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            }
        }
    }

    /**
     * Precision check method
     */
    public Boolean isPrecisionValueValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    /**
     * Active and Inactive change refresh event method for parameter
     */
    public void paramActivVCE(@SuppressWarnings("unused") ValueChangeEvent valueChangeEvent) {
        if (parmActivBindVar.isSelected()) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(paramInactivResnBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(paramInactivDtBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBindVar);
            this.paramInactivResnBindVar.setRequired(false);
            this.paramInactivDtBindVar.setValue(null);
            this.paramInactivResnBindVar.setValue(null);

        } else {
            AdfFacesContext.getCurrentInstance().addPartialTarget(paramInactivResnBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(paramInactivDtBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBindVar);
            this.paramInactivDtBindVar.setValue(new oracle.jbo.domain.Timestamp(new java.util.Date()));
            this.paramInactivResnBindVar.setValue(null);
            this.paramInactivResnBindVar.setRequired(true);
        }
    }

    /**
     * Active and Inactive change refresh event method for Parameter set
     */
    public void parmSetActivVCE(@SuppressWarnings("unused") ValueChangeEvent valueChangeEvent) {
        if (paramSetActiveBindVar.isSelected()) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(setResnBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(setDateBindVar);
            this.setResnBindVar.setRequired(false);
            this.setDateBindVar.setValue(null);
            this.setResnBindVar.setValue(null);
        } else {
            AdfFacesContext.getCurrentInstance().addPartialTarget(setResnBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(setDateBindVar);
            this.setDateBindVar.setValue(new oracle.jbo.domain.Timestamp(new java.util.Date()));
            this.setResnBindVar.setRequired(true);
            this.setResnBindVar.setValue(null);
        }
    }

    /**
     * Active and Inactive change refresh event method for Organization
     */
    public void orgSelectActiveVCE(@SuppressWarnings("unused") ValueChangeEvent valueChangeEvent) {
        if (orgParamActiveBindVar.isSelected()) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgParamInavtiveResnBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgParamInctivDTBindVar);
            this.orgParamInavtiveResnBindVar.setRequired(false);
            this.orgParamInctivDTBindVar.setValue(null);
            this.orgParamInavtiveResnBindVar.setValue(null);
        } else {
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgParamInavtiveResnBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgParamInctivDTBindVar);
            this.orgParamInctivDTBindVar.setValue(new oracle.jbo.domain.Timestamp(new java.util.Date()));
            this.orgParamInavtiveResnBindVar.setRequired(true);
            this.orgParamInavtiveResnBindVar.setValue(null);
        }

    }

    /**
     * attaching single organization method
     */
    public void attachOrganizationACE(@SuppressWarnings("unused") ActionEvent actionEvent) {
        setModeOrg("A");
        ADFUtils.findOperation("CreateInsertMNFOrgParam").execute();
    }

    /**
     * duplicate org exist check method
     */
    public void checkOrgExistenceValidator(@SuppressWarnings("unused") FacesContext facesContext,
                                           @SuppressWarnings("unused") UIComponent uIComponent, Object object) {
        if (object != null) {

            if (duplicateValue("OrgMnfParam2Iterator", "OrgId", object)) {
                FacesMessage message = new FacesMessage("Organization already exist!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    /**
     * duplicate org exist check method
     */
    private boolean duplicateValue(String iter, String attrsNm, Object val) {

        DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countVal = 0;

        while (rSetIter.hasNext()) {
            r = rSetIter.next();
            // System.out.println("attrsNm "+r.getAttribute(attrsNm));

            if (r.getAttribute(attrsNm) != null && val.equals(r.getAttribute(attrsNm))) {
                countVal = countVal + 1;
            }
        }
        rSetIter.closeRowSetIterator();

        //exclude count from current row
        Row currentRow = dcIter.getCurrentRow();

        if (currentRow.getAttribute(attrsNm) != null && val.equals(currentRow.getAttribute(attrsNm))) {
            countVal = countVal - 1;
        }

        return countVal == 1 ? true : false;
    }

    public void removeRow(String iterName, Key key) {

        if (iterName != null && key != null) {
            ViewObject vo = ADFUtils.findIterator(iterName).getViewObject();
            Row row = vo.getRow(key);
            if (row != null)
                row.remove();
        }
    }

    public void singleOrgDeleteACE(ActionEvent actionEvent) {
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("orgRowKey");
        removeRow("OrgMnfParam2Iterator", key);
    }

    /**
     * Replicate all the organization method
     */
    public void replicateAllDLE(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            ADFUtils.findOperation("replicateOrgForParam").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgTableBindVar);
        }
    }

    /**
     * Replicate all the organization popup show method
     */
    public void replicateAllOrgACE(@SuppressWarnings("unused") ActionEvent actionEvent) {
        ADFUtils.showPopup(popUpBindVar, true);

    }

    /**
     * refresh org to table method
     */
    public void selectOrgVCE(@SuppressWarnings("unused") ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgTableBindVar);
    }

    /**
     * no extra space check method
     */
    public void validCharValidator(@SuppressWarnings("unused") FacesContext facesContext,
                                   @SuppressWarnings("unused") UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                FacesMessage message = new FacesMessage("Entered Set Name has useless white spaces!!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    /**
     * no extra space check method
     */
    public void setReasonValidCharValidator(@SuppressWarnings("unused") FacesContext facesContext,
                                            @SuppressWarnings("unused") UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                FacesMessage message = new FacesMessage("Entered Reason has useless white spaces!!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    /**
     * no extra space check method
     */
    public void paramReasonValidCharValidator(@SuppressWarnings("unused") FacesContext facesContext,
                                              @SuppressWarnings("unused") UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                FacesMessage message = new FacesMessage("Entered Reason has useless white spaces!!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    /**
     * no extra space check method
     */
    public void orgReasonValidCharValidator(@SuppressWarnings("unused") FacesContext facesContext,
                                            @SuppressWarnings("unused") UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                FacesMessage message = new FacesMessage("Entered Reason has useless white spaces!!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    /**
     * no extra space check method
     */
    public void paramValidCharValidator(@SuppressWarnings("unused") FacesContext facesContext,
                                        @SuppressWarnings("unused") UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                FacesMessage message = new FacesMessage("Entered Parameter Description has useless white spaces!!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }


    public void setOrgTableBindVar(RichTable orgTableBindVar) {
        this.orgTableBindVar = orgTableBindVar;
    }

    public RichTable getOrgTableBindVar() {
        return orgTableBindVar;
    }

    public void setParamNameBindVar(RichInputText paramNameBindVar) {
        this.paramNameBindVar = paramNameBindVar;
    }

    public RichInputText getParamNameBindVar() {
        return paramNameBindVar;
    }

    public void setSetNameBindVar(RichInputText setNameBindVar) {
        this.setNameBindVar = setNameBindVar;
    }

    public RichInputText getSetNameBindVar() {
        return setNameBindVar;
    }

    public void setReplicateAllOrgBind(RichSelectBooleanCheckbox replicateAllOrgBind) {
        this.replicateAllOrgBind = replicateAllOrgBind;
    }

    public RichSelectBooleanCheckbox getReplicateAllOrgBind() {
        return replicateAllOrgBind;
    }

    public void setParamSetTypeBind(RichSelectOneChoice paramSetTypeBind) {
        this.paramSetTypeBind = paramSetTypeBind;
    }

    public RichSelectOneChoice getParamSetTypeBind() {
        return paramSetTypeBind;
    }

    public void setPopUpBindVar(RichPopup popUpBindVar) {
        this.popUpBindVar = popUpBindVar;
    }

    public RichPopup getPopUpBindVar() {
        return popUpBindVar;
    }

    public void setParamSetActiveBindVar(RichSelectBooleanCheckbox paramSetActiveBindVar) {
        this.paramSetActiveBindVar = paramSetActiveBindVar;
    }

    public RichSelectBooleanCheckbox getParamSetActiveBindVar() {
        return paramSetActiveBindVar;
    }

    public void setOrgParamActiveBindVar(RichSelectBooleanCheckbox orgParamActiveBindVar) {
        this.orgParamActiveBindVar = orgParamActiveBindVar;
    }

    public RichSelectBooleanCheckbox getOrgParamActiveBindVar() {
        return orgParamActiveBindVar;
    }


    public void setOrgParamInctivDTBindVar(RichInputDate orgParamInctivDTBindVar) {
        this.orgParamInctivDTBindVar = orgParamInctivDTBindVar;
    }

    public RichInputDate getOrgParamInctivDTBindVar() {
        return orgParamInctivDTBindVar;
    }

    public void setOrgParamInavtiveResnBindVar(RichInputText orgParamInavtiveResnBindVar) {
        this.orgParamInavtiveResnBindVar = orgParamInavtiveResnBindVar;
    }

    public RichInputText getOrgParamInavtiveResnBindVar() {
        return orgParamInavtiveResnBindVar;
    }

    public void setModeOrg(String modeOrg) {
        this.modeOrg = modeOrg;
    }

    public String getModeOrg() {
        return modeOrg;
    }

    public void setSetDateBindVar(RichInputDate setDateBindVar) {
        this.setDateBindVar = setDateBindVar;
    }

    public RichInputDate getSetDateBindVar() {
        return setDateBindVar;
    }

    public void setSetResnBindVar(RichInputText setResnBindVar) {
        this.setResnBindVar = setResnBindVar;
    }

    public RichInputText getSetResnBindVar() {
        return setResnBindVar;
    }

    public void setSetActiveBindVar(RichSelectBooleanCheckbox setActiveBindVar) {
        this.setActiveBindVar = setActiveBindVar;
    }

    public RichSelectBooleanCheckbox getSetActiveBindVar() {
        return setActiveBindVar;
    }

    public void setParmActivBindVar(RichSelectBooleanCheckbox parmActivBindVar) {
        this.parmActivBindVar = parmActivBindVar;
    }

    public RichSelectBooleanCheckbox getParmActivBindVar() {
        return parmActivBindVar;
    }

    public void setParamInactivDtBindVar(RichInputDate paramInactivDtBindVar) {
        this.paramInactivDtBindVar = paramInactivDtBindVar;
    }

    public RichInputDate getParamInactivDtBindVar() {
        return paramInactivDtBindVar;
    }

    public void setParamInactivResnBindVar(RichInputText paramInactivResnBindVar) {
        this.paramInactivResnBindVar = paramInactivResnBindVar;
    }

    public RichInputText getParamInactivResnBindVar() {
        return paramInactivResnBindVar;
    }

    public void setModeParam(String modeParam) {
        this.modeParam = modeParam;
    }

    public String getModeParam() {
        return modeParam;
    }

    public void setModeSet(String modeSet) {
        this.modeSet = modeSet;
    }

    public String getModeSet() {
        return modeSet;
    }

    public void setMainPanelSplitterCalpse(RichPanelSplitter mainPanelSplitterCalpse) {
        this.mainPanelSplitterCalpse = mainPanelSplitterCalpse;
    }

    public RichPanelSplitter getMainPanelSplitterCalpse() {
        return mainPanelSplitterCalpse;
    }

    public void setAccordianDisp(RichShowDetailItem accordianDisp) {
        this.accordianDisp = accordianDisp;
    }

    public RichShowDetailItem getAccordianDisp() {
        return accordianDisp;
    }

    public void setSelectParamSetType(RichSelectOneChoice selectParamSetType) {
        this.selectParamSetType = selectParamSetType;
    }

    public RichSelectOneChoice getSelectParamSetType() {
        return selectParamSetType;
    }

    public void setParamValTypBindVar(RichSelectOneChoice paramValTypBindVar) {
        this.paramValTypBindVar = paramValTypBindVar;
    }

    public RichSelectOneChoice getParamValTypBindVar() {
        return paramValTypBindVar;
    }

    public void setParamValueBindVar(RichInputText paramValueBindVar) {
        this.paramValueBindVar = paramValueBindVar;
    }

    public RichInputText getParamValueBindVar() {
        return paramValueBindVar;
    }

    public void uomVCE(@SuppressWarnings("unused") ValueChangeEvent valueChangeEvent) {
       AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBindVar);
    }

    public void ParamValueVCE(@SuppressWarnings("unused") ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBindVar);
    }

    public void setParamTableBindVar(RichTable paramTableBindVar) {
        this.paramTableBindVar = paramTableBindVar;
    }

    public RichTable getParamTableBindVar() {
        return paramTableBindVar;
    }

    public void paramTypeVCE(@SuppressWarnings("unused") ValueChangeEvent valueChangeEvent) {
        if(this.paramValTypBindVar.getValue().equals(112)){
            paramValuesBind.setValue(new oracle.jbo.domain.Number(1));
            setAttrsVal("MnfParam2Iterator","ParamUom", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(paramValuesBind);
        }     
    }

    public void setParamValuesBind(RichInputText paramValuesBind) {
        this.paramValuesBind = paramValuesBind;
    }

    public RichInputText getParamValuesBind() {
        return paramValuesBind;
    }
    /**
        * Method to set value for an attribute of current row for an Iterator
        * @param iter for iterator name
        * @param for attribute name
        * @param for attribute value
        * **/
       public void setAttrsVal(String iter, String attrs, Object val) {

           if (iter != null && attrs != null) {
               DCIteratorBinding dcIter = ADFUtils.findIterator(iter);

               if (dcIter.getCurrentRow() != null)
                   dcIter.getCurrentRow().setAttribute(attrs, val);

           }
       }

       /**
        * Method to get value of attribute of current row for an Iterator
        * @param iter for iterator name
        * @param for attribute name
        * **/
       public Object getAttrsVal(String iter, String attrs) {

           if (iter != null && attrs != null) {
               DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
              // System.out.println("dcIter " + dcIter.getEstimatedRowCount());
               if (dcIter.getCurrentRow() != null)
                   return dcIter.getCurrentRow().getAttribute(attrs);

           }
           return null;
       }

    public void paramSetTypeVCE(ValueChangeEvent valueChangeEvent) {
        setAttrsVal("MnfParamSet1Iterator","ParamSetCoaId",null);
       // setAttrsVal("MnfParam2Iterator","ParamCoaId",null);
    }
}
