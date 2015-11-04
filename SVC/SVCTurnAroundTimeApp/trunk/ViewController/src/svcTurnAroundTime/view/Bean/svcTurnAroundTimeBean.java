package svcTurnAroundTime.view.Bean;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import javax.print.attribute.standard.Severity;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.server.JboPrecisionScaleValidator;

import svcTurnAroundTime.view.Utils.ADFUtils;

public class svcTurnAroundTimeBean {
    OperationBinding ob = null;
    private RichTable escHcyTableBind;
    private RichInputText svcLocationTotalTimeBind;
    private RichSelectOneChoice svcLocationUnitBind;
    private RichSelectOneChoice svcEntityUnitBind;
    private RichSelectOneChoice svcDefectUnitBind;
    private RichSelectOneChoice svcEscUnitBind;
    private StringBuffer mode = new StringBuffer("V");
    private StringBuffer editModeLoc = new StringBuffer();
    private RichInputText svcLocationTimeBind;
    private RichInputText svcEntityTimeBind;
    private RichInputText svcEnTimeBind;
    private RichInputText svcEscTmeBind;
    private RichInputText svcEntityTotalTimeBind;
    private RichInputText svcDefectsTotalTimeBind;
    private RichInputText svcEscTotalTimeBind;
    private RichPopup svcEntityPopUp;
    private RichPopup svcLocationPopUp;
    private RichPopup svcDefectsPopUp;
    private RichPanelGroupLayout searchLocationBind;
    private RichInputListOfValues locationValueBind;


    public svcTurnAroundTimeBean() {
    }

    public void addTatLocactionACE(ActionEvent actionEvent) {
        // CreateWithParamsToTATLoc
        this.setMode(new StringBuffer("A"));
        ob = ADFUtils.findOperation("CreateWithParamsToTATLoc");
        ob.execute();
        //this.setEditModeLoc(new StringBuffer("V"));
    }

    public void onSaveACE(ActionEvent actionEvent) {
      
        if(locationValueBind.getValue() != null){
            ob = ADFUtils.findOperation("CommitTAT");
            ob.execute();

            ADFUtils.showFacesMsg(resolvElDCMsg("#{bundle['MSG.818']}").toString(),
                                  resolvElDCMsg("#{bundle['MSG.818']}").toString(), FacesMessage.SEVERITY_INFO, null);
            this.setEditModeLoc(new StringBuffer("V"));
            setMode(new StringBuffer("V")); 
        }else {
            ADFUtils.showFacesMsg("Error", "Location Name is Required.", FacesMessage.SEVERITY_ERROR,null);
        }
      

    }

    public void addDefectsACE(ActionEvent actionEvent) {
        // CreateWithParamsToTATDefect
        this.setMode(new StringBuffer("A"));
        ob = ADFUtils.findOperation("CreateWithParamsToTATDefect");
        ob.execute();
    }

    public void addEntityACE(ActionEvent actionEvent) {
        // CreateWithParamsToTATEntity
        this.setMode(new StringBuffer("A"));
        ob = ADFUtils.findOperation("CreateWithParamsToTATEntity");
        ob.execute();

    }

    public void addEscTimeACE(ActionEvent actionEvent) {
        // CreateWithParamsToEscTime
        //setParamForESLevel
        // ob = ADFUtils.findOperation("CreateWithParamsToEscTime");
        this.setMode(new StringBuffer("A"));
        ob = ADFUtils.findOperation("setParamForESCLevel");
        ob.execute();
    }

    public void addEscHcyACE(ActionEvent actionEvent) {
        // CreateWithParamsToEscHcy
        this.setMode(new StringBuffer("A"));
        ob = ADFUtils.findOperation("CreateWithParamsToEscHcy");
        ob.execute();
    }

    public void deleteEscHcyACE(ActionEvent actionEvent) {
        // DeleteEscHcy

        ob = ADFUtils.findOperation("DeleteEscHcy");
        ob.execute();

    }

    public void addEscHcyUserVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(escHcyTableBind);
    }

    public void setEscHcyTableBind(RichTable escHcyTableBind) {
        this.escHcyTableBind = escHcyTableBind;
    }

    public RichTable getEscHcyTableBind() {
        return escHcyTableBind;
    }

    /**
     * Method to check duplicate value for rows within an iterator
     *
     * **/
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

    public void escHcyUserVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            if (duplicateValue("svcEscHcyVO1Iterator", "TranUsrNme",
                               object)) {
                //System.out.println("Inside of orgchk validator !!");
                //JSFUtils.addFacesErrorMessage("Duplicate User is Selected !");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1608']}").toString(), null));
            }
        }

    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);

    }


    public void escTimeVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1605']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
            // else if (Integer.parseInt(value.toString()) < 0) {
            else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1604']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }


    }

    public void escLevelVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer value = (Integer) (object);
            Boolean isValid = isPrecisionValid2(5, 0, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1605']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
            // else if (Integer.parseInt(value.toString()) < 0) {
            else if (value < 0) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1604']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }


    }

    public Boolean isPrecisionValid2(Integer precision, Integer scale, Integer total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);

    }

    public void svcDefectNameVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            // System.out.println("Inside of orgchk validator !!");
            if (duplicateValue("svcTatDefectVOIterator", "TransDefectNme", object)) {

                //  JSFUtils.addFacesErrorMessage("Duplicate Defect is Selected !");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1607']}").toString(), null));
            }
        }


    }

    public void svcDefectTimeVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1605']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
            // else if (Integer.parseInt(value.toString()) < 0) {
            else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1604']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }


    }

    public void svcEntityTimeVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1605']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
            // else if (Integer.parseInt(value.toString()) < 0) {
            else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1604']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }

    }

    public void svcEntityEmployeeVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            if (duplicateValue("svcTatEntityVOIterator", "TransEmpNme",
                               object)) {
                //System.out.println("Inside of orgchk validator !!");
                // JSFUtils.addFacesErrorMessage("Duplicate Entity is Selected !");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1606']}").toString(), null));
            }
        }


    }

    public void svcLocationTimeVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1605']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
            // else if (Integer.parseInt(value.toString()) < 0) {
            else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1604']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }

    }

    public void svcDeleteLocationACE(ActionEvent actionEvent) {
        System.out.println("Delete Location !");
        ob = ADFUtils.findOperation("DeleteLocation");
        ob.execute();
        this.setMode(new StringBuffer("E"));
        this.svcLocationPopUp.hide();
    }

    public void svcDeleteEscTimeACE(ActionEvent actionEvent) {

        ob = ADFUtils.findOperation("getEscLevel");
        ob.execute();
        if (ob.getResult() == 1) {

            ob = ADFUtils.findOperation("DeleteEscTime");
            ob.execute();
            this.setMode(new StringBuffer("E"));

        } else {
            ADFUtils.showFacesMsg(resolvElDCMsg("#{bundle['MSG.1609']}").toString(),
                                  resolvElDCMsg("#{bundle['MSG.1609']}").toString(), FacesMessage.SEVERITY_INFO, null);
        }
    }

    public void svcDeleteDefectsACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("DeleteDefects");
        ob.execute();
        this.setMode(new StringBuffer("E"));
        this.svcDefectsPopUp.hide();
    }

    public void svcDeleteEntityACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("DeleteEntity");
        ob.execute();
        this.setMode(new StringBuffer("E"));
        this.svcEntityPopUp.hide();
    }

    public void setSvcLocationTotalTimeBind(RichInputText svcLocationTotalTimeBind) {
        this.svcLocationTotalTimeBind = svcLocationTotalTimeBind;
    }

    public RichInputText getSvcLocationTotalTimeBind() {
        return svcLocationTotalTimeBind;
    }
    Integer TotalTimevalue = 0;

    public void setTotalTimevalue(Integer TotalTimevalue) {
        this.TotalTimevalue = TotalTimevalue;
    }

    public Integer getTotalTimevalue() {
        return TotalTimevalue;
    }

    public void svcLocationTimeVCE(ValueChangeEvent vce) {

        // Integer value = Integer.parseInt(vce.getNewValue().toString());
        Number value = new Number(0);

        //try {
        if (vce.getNewValue() != null) {
            value = (Number) vce.getNewValue();
            // } catch (SQLException e) {
            // }
            System.out.println("value is " + value);
            Integer unit = Integer.parseInt(getSvcLocationUnitBind().getValue().toString());

            if (value != null) {
                ob = ADFUtils.findOperation("getTotalDays");
                ob.getParamsMap().put("time", value);
                ob.getParamsMap().put("PrdUnit", unit);
                ob.execute();
                if (ob.getResult() != null) {
                    setAttrsVal("svcTatLocVOIterator", "TatDays", ob.getResult());
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(svcLocationTotalTimeBind);
        }
    }

    public void setSvcLocationUnitBind(RichSelectOneChoice svcLocationUnitBind) {
        this.svcLocationUnitBind = svcLocationUnitBind;
    }

    public RichSelectOneChoice getSvcLocationUnitBind() {
        return svcLocationUnitBind;
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

            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }


    public void svcEntityTimeVCE(ValueChangeEvent vce) {

        //Integer value = Integer.parseInt(vce.getNewValue().toString());
        Number value = new Number(0);

        //try {
        if (vce.getNewValue() != null) {
            value = (Number) vce.getNewValue();
            Integer unit = Integer.parseInt(getSvcEntityUnitBind().getValue().toString());

            if (value != null) {
                ob = ADFUtils.findOperation("getTotalDays");
                ob.getParamsMap().put("time", value);
                ob.getParamsMap().put("PrdUnit", unit);
                ob.execute();

                if (ob.getResult() != null) {
                    setAttrsVal("svcTatEntityVOIterator", "TatDays", ob.getResult());
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(svcEntityTotalTimeBind);
        }

    }

    public void setSvcEntityUnitBind(RichSelectOneChoice svcEntityUnitBind) {
        this.svcEntityUnitBind = svcEntityUnitBind;
    }

    public RichSelectOneChoice getSvcEntityUnitBind() {
        return svcEntityUnitBind;
    }

    public void setSvcDefectUnitBind(RichSelectOneChoice svcDefectUnitBind) {
        this.svcDefectUnitBind = svcDefectUnitBind;
    }

    public RichSelectOneChoice getSvcDefectUnitBind() {
        return svcDefectUnitBind;
    }

    public void svcDefectTimeVCE(ValueChangeEvent vce) {

        // Integer value = Integer.parseInt(vce.getNewValue().toString());
        Number value = new Number(0);

        //try {
        if (vce.getNewValue() != null) {
            value = (Number) vce.getNewValue();
            Integer unit = Integer.parseInt(getSvcDefectUnitBind().getValue().toString());

            if (value != null) {
                ob = ADFUtils.findOperation("getTotalDays");
                ob.getParamsMap().put("time", value);
                ob.getParamsMap().put("PrdUnit", unit);
                ob.execute();

                if (ob.getResult() != null) {
                    setAttrsVal("svcTatDefectVOIterator", "TatDays", ob.getResult());
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(svcDefectsTotalTimeBind);
        }
    }

    public void svcEscTimeVCE(ValueChangeEvent vce) {
        //Integer value = Integer.parseInt(vce.getNewValue().toString());
        Number value = new Number(0);

        //try {
        if (vce.getNewValue() != null) {
            value = (Number) vce.getNewValue();
            Integer unit = Integer.parseInt(getSvcEscUnitBind().getValue().toString());
            if (value != null) {
                ob = ADFUtils.findOperation("getTotalDays");
                ob.getParamsMap().put("time", value);
                ob.getParamsMap().put("PrdUnit", unit);
                ob.execute();

                if (ob.getResult() != null) {
                    setAttrsVal("svcEscTimeVO1Iterator", "TatDays", ob.getResult());
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(svcEscTotalTimeBind);
        }
    }

    public void setSvcEscUnitBind(RichSelectOneChoice svcEscUnitBind) {
        this.svcEscUnitBind = svcEscUnitBind;
    }

    public RichSelectOneChoice getSvcEscUnitBind() {
        return svcEscUnitBind;
    }

    public void editLocationACE(ActionEvent actionEvent) {
        this.setMode(new StringBuffer("E"));

    }

    public void setEditModeLoc(StringBuffer editModeLoc) {
        this.editModeLoc = editModeLoc;
    }

    public StringBuffer getEditModeLoc() {
        return editModeLoc;
    }

    public void setMode(StringBuffer mode) {
        this.mode = mode;
    }

    public StringBuffer getMode() {
        return mode;
    }

    public void svcTatLocationUnitVCE(ValueChangeEvent vce) {
        Integer unit = Integer.parseInt(vce.getNewValue().toString());
        if (getSvcLocationTimeBind().getValue() != null) {
            // Integer value = Integer.parseInt(getSvcLocationTimeBind().getValue().toString());
            oracle.jbo.domain.Number value = new oracle.jbo.domain.Number(0);

            value = (Number) getSvcLocationTimeBind().getValue();

            if (unit != null) {
                ob = ADFUtils.findOperation("getTotalDays");
                ob.getParamsMap().put("time", value);
                ob.getParamsMap().put("PrdUnit", unit);
                ob.execute();
                if (ob.getResult() != null) {
                    System.out.println(" val set");

                    setAttrsVal("svcTatLocVOIterator", "TatDays", ob.getResult());
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(svcLocationTotalTimeBind);

    }

    public void setSvcLocationTimeBind(RichInputText svcLocationTimeBind) {
        this.svcLocationTimeBind = svcLocationTimeBind;
    }

    public RichInputText getSvcLocationTimeBind() {
        return svcLocationTimeBind;
    }

    public void svcEntityUnitVCE(ValueChangeEvent vce) {
        Integer unit = Integer.parseInt(vce.getNewValue().toString());
        if (getSvcEntityTimeBind().getValue() != null) {
            // Integer value = Integer.parseInt(getSvcEntityTimeBind().getValue().toString());
            oracle.jbo.domain.Number value = new oracle.jbo.domain.Number(0);

            value = (Number) getSvcEntityTimeBind().getValue();
            if (value != null) {
                ob = ADFUtils.findOperation("getTotalDays");
                ob.getParamsMap().put("time", value);
                ob.getParamsMap().put("PrdUnit", unit);
                ob.execute();

                if (ob.getResult() != null) {
                    setAttrsVal("svcTatEntityVOIterator", "TatDays", ob.getResult());
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(svcEntityTotalTimeBind);
    }

    public void setSvcEntityTimeBind(RichInputText svcEntityTimeBind) {
        this.svcEntityTimeBind = svcEntityTimeBind;
    }

    public RichInputText getSvcEntityTimeBind() {
        return svcEntityTimeBind;
    }

    public void svcDefectsUnitVCE(ValueChangeEvent vce) {
        Integer unit = Integer.parseInt(vce.getNewValue().toString());
        if (getSvcEnTimeBind().getValue() != null) {
            // Integer value = Integer.parseInt(getSvcEnTimeBind().getValue().toString());
            oracle.jbo.domain.Number value = new oracle.jbo.domain.Number(0);

            value = (Number) getSvcEnTimeBind().getValue();
            if (value != null) {
                ob = ADFUtils.findOperation("getTotalDays");
                ob.getParamsMap().put("time", value);
                ob.getParamsMap().put("PrdUnit", unit);
                ob.execute();

                if (ob.getResult() != null) {
                    setAttrsVal("svcTatDefectVOIterator", "TatDays", ob.getResult());
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(svcDefectsTotalTimeBind);
    }

    public void setSvcEnTimeBind(RichInputText svcEnTimeBind) {
        this.svcEnTimeBind = svcEnTimeBind;
    }

    public RichInputText getSvcEnTimeBind() {
        return svcEnTimeBind;
    }

    public void svcEscUnitVCE(ValueChangeEvent vce) {
        Integer unit = Integer.parseInt(vce.getNewValue().toString());
        if (getSvcEscTmeBind().getValue() != null) {
            // Integer value = Integer.parseInt(getSvcEscTmeBind().getValue().toString());
            oracle.jbo.domain.Number value = new oracle.jbo.domain.Number(0);

            value = (Number) getSvcEscTmeBind().getValue();
            if (value != null) {
                ob = ADFUtils.findOperation("getTotalDays");
                ob.getParamsMap().put("time", value);
                ob.getParamsMap().put("PrdUnit", unit);
                ob.execute();
                if (ob.getResult() != null) {
                    setAttrsVal("svcEscTimeVO1Iterator", "TatDays", ob.getResult());
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(svcEscTotalTimeBind);
    }

    public void setSvcEscTmeBind(RichInputText svcEscTmeBind) {
        this.svcEscTmeBind = svcEscTmeBind;
    }

    public RichInputText getSvcEscTmeBind() {
        return svcEscTmeBind;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void svcLocationVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            /*  if (duplicateValue("svcTatLocVOIterator", "TransLocId", object)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1603']}").toString(), null));
            } */


            /*    OperationBinding op = ADFUtils.findOperation("validateLocation");
            op.execute();
            if(op.getResult()!= null){
                System.out.println("------------------"+op.getResult());
                if(op.getResult().toString().equals("Y")){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.1603']}").toString(), null));


                }
            } */
            String locNm = object.toString();
            OperationBinding op = ADFUtils.findOperation("validateLoc");
            op.getParamsMap().put("name", locNm);
            op.execute();

            if (op.getResult() != null) {
                System.out.println("------------------" + op.getResult());
                if (op.getResult().toString().equals("Y")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.1603']}").toString(),
                                                                  null));


                }
            }

        }

    }

    public void setSvcEntityTotalTimeBind(RichInputText svcEntityTotalTimeBind) {
        this.svcEntityTotalTimeBind = svcEntityTotalTimeBind;
    }

    public RichInputText getSvcEntityTotalTimeBind() {
        return svcEntityTotalTimeBind;
    }

    public void setSvcDefectsTotalTimeBind(RichInputText svcDefectsTotalTimeBind) {
        this.svcDefectsTotalTimeBind = svcDefectsTotalTimeBind;
    }

    public RichInputText getSvcDefectsTotalTimeBind() {
        return svcDefectsTotalTimeBind;
    }

    public void setSvcEscTotalTimeBind(RichInputText svcEscTotalTimeBind) {
        this.svcEscTotalTimeBind = svcEscTotalTimeBind;
    }

    public RichInputText getSvcEscTotalTimeBind() {
        return svcEscTotalTimeBind;
    }

    public void onCancelACE(ActionEvent actionEvent) {
        // Rollback
        ob = ADFUtils.findOperation("Rollback");
        ob.execute();
        setMode(new StringBuffer("V"));
    }

    public void deleteLocationDL(DialogEvent de) {
        if (de.getOutcome() == DialogEvent.Outcome.ok) {
            ob = ADFUtils.findOperation("DeleteLocation");
            ob.execute();
            this.setMode(new StringBuffer("E"));
        }
    }

    public void deleteEntityDL(DialogEvent de) {
        if (de.getOutcome() == DialogEvent.Outcome.ok) {
            ob = ADFUtils.findOperation("DeleteEntity");
            ob.execute();
            this.setMode(new StringBuffer("E"));
        }
    }

    public void deleteDefectsDL(DialogEvent de) {
        // Add event code here...
        if (de.getOutcome() == DialogEvent.Outcome.ok) {
            ob = ADFUtils.findOperation("DeleteDefects");
            ob.execute();
            this.setMode(new StringBuffer("E"));
        }
    }

    public void deleteEscDL(DialogEvent de) {
        if (de.getOutcome() == DialogEvent.Outcome.ok) {
            ob = ADFUtils.findOperation("DeleteLocation");
            ob.execute();
            this.setMode(new StringBuffer("E"));
        }
    }

    public void setSvcEntityPopUp(RichPopup svcEntityPopUp) {
        this.svcEntityPopUp = svcEntityPopUp;
    }

    public RichPopup getSvcEntityPopUp() {
        return svcEntityPopUp;
    }

    public void setSvcLocationPopUp(RichPopup svcLocationPopUp) {
        this.svcLocationPopUp = svcLocationPopUp;
    }

    public RichPopup getSvcLocationPopUp() {
        return svcLocationPopUp;
    }

    public void setSvcDefectsPopUp(RichPopup svcDefectsPopUp) {
        this.svcDefectsPopUp = svcDefectsPopUp;
    }

    public RichPopup getSvcDefectsPopUp() {
        return svcDefectsPopUp;
    }

    public void setSearchLocationBind(RichPanelGroupLayout searchLocationBind) {
        this.searchLocationBind = searchLocationBind;
    }

    public RichPanelGroupLayout getSearchLocationBind() {
        return searchLocationBind;
    }

    public void setLocationValueBind(RichInputListOfValues locationValueBind) {
        this.locationValueBind = locationValueBind;
    }

    public RichInputListOfValues getLocationValueBind() {
        return locationValueBind;
    }

    public void LocationIdVCL(ValueChangeEvent valueChangeEvent) {
        /*  if (valueChangeEvent != null) {
            String locNm = (String) valueChangeEvent.getNewValue();
            OperationBinding op = ADFUtils.findOperation("validateLoc");
            op.getParamsMap().put("name", locNm);
            op.execute();

            if (op.getResult() != null) {
                System.out.println("------------------" + op.getResult());
                if (op.getResult().toString().equals("Y")) {
                    ADFUtils.showFacesMsg(null, "Location Name is already exist.", FacesMessage.SEVERITY_ERROR, null);
                    return;

                }
            }
        } */
    }
}
