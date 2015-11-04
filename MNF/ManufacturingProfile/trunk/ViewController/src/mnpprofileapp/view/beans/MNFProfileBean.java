package mnpprofileapp.view.beans;

import java.util.List;

import java.util.ListIterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import mnpprofileapp.view.utilis.ADFUtils;

import mnpprofileapp.view.utilis.JSFUtils;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;
import oracle.binding.BindingContainer;

public class MNFProfileBean {

    //V = view Mode, C= Create, E= Edit
    private String Mode = "V";
    private String checkRequr = "N";
    private String checkRequr2 = "N";

    public void setCheckRequr2(String checkRequr2) {
        this.checkRequr2 = checkRequr2;
    }

    public String getCheckRequr2() {
        return checkRequr2;
    }

    public void setCheckRequr(String checkRequr) {
        this.checkRequr = checkRequr;
    }

    public String getCheckRequr() {
        return checkRequr;
    }

    private RichSelectBooleanCheckbox allJcFutDt;
    private RichSelectBooleanCheckbox allJcPrvDt;
    private RichInputText jcFutDays;
    private RichInputText jcPrevDays;


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public MNFProfileBean() {
    }

    public void editACE(ActionEvent actionEvent) {
        System.out.println("ediut sdfdf");
        setMode("E");
    }

    public void cancelACE(ActionEvent actionEvent) {

        ADFUtils.findOperation("Rollback").execute();
        setMode("V");

        ///System.out.println("Error " + ob.getErrors());
    }

    public void addACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("CreateMNFProfile").execute();
        ADFUtils.findOperation("CreateInsert").execute();
        setMode("C");

    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        return Mode;
    }

    public void saveACE(ActionEvent actionEvent) {
        
        ADFUtils.findOperation("setValuesForUnselected").execute();
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Record saved sucessfully!");
        setMode("V");

    }

    public void setAllJcFutDt(RichSelectBooleanCheckbox allJcFutDt) {
        this.allJcFutDt = allJcFutDt;
    }

    public RichSelectBooleanCheckbox getAllJcFutDt() {
        return allJcFutDt;
    }

    public void setAllJcPrvDt(RichSelectBooleanCheckbox allJcPrvDt) {
        this.allJcPrvDt = allJcPrvDt;
    }

    public RichSelectBooleanCheckbox getAllJcPrvDt() {
        return allJcPrvDt;
    }

    public void allJcFutDtVCE(ValueChangeEvent valueChangeEvent) {
        if (allJcFutDt.isSelected()) {
            setCheckRequr("Y");
            this.jcFutDays.setRequired(true);
        } else {
            this.jcFutDays.setRequired(false);
            this.jcFutDays.setValue(null);
            //System.out.println(jcFutDays.getValue()+ "  fut days");
            setCheckRequr("N");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent());
    }

    public void allJcPrevDtVCE(ValueChangeEvent valueChangeEvent) {
        if (allJcPrvDt.isSelected()) {
           // setCheckRequr2("Y");
            this.jcPrevDays.setRequired(true);
        } else {
            this.jcPrevDays.setRequired(false);
            this.jcPrevDays.setValue(null);
           // System.out.println(jcPrevDays.getValue()+ "  prev days");
            //setCheckRequr2("N");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent());

    }

    public void setJcFutDays(RichInputText jcFutDays) {
        this.jcFutDays = jcFutDays;
    }

    public RichInputText getJcFutDays() {
        return jcFutDays;
    }

    public void setJcPrevDays(RichInputText jcPrevDays) {
        this.jcPrevDays = jcPrevDays;
    }

    public RichInputText getJcPrevDays() {
        return jcPrevDays;
    }

    public void futureDaysValidater(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer fudays = (Integer) object;
            if (fudays.compareTo(0) <= 0) {
                FacesMessage message = new FacesMessage("Feature Days must be more than zero (0)");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }

    }

    public void previousDaysValidater(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer prdays = (Integer) object;
            if (prdays.compareTo(0) <= 0) {
                FacesMessage message = new FacesMessage("Previous Days must be more than zero (0)");
                //Future Days entered for Joc Card must be more than zero
                // FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.994']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }

    }
}
