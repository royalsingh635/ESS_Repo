package slspackmasterapp.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class SlsPackMasterBean {
    private StringBuffer form_mode = new StringBuffer("V");
    private RichPopup deletePopUpBVar;
    private RichTable packTableBVar;
    private RichInputText pckNmBVar;
    private RichPanelFormLayout addFrmBVar;
    private RichSelectOneChoice unitBVar;
    private RichInputListOfValues packNmBVar;
    private RichInputText widthbVar;
    private RichInputText lngthBVar;
    private RichInputText hightBVar;
    private RichSelectOneChoice unitBBVar;
    private RichInputText packHtBVar;
    private RichInputText packLntBVar;
    private RichInputText packWidthBVar;

    public SlsPackMasterBean() {
    }

    /**
     * ActionListener to add Pack
     * @param actionEvent
     */
    public void addPackACTION(ActionEvent actionEvent) {

        executeOnCreate(actionEvent);
        ////If it is not there then Too many objects found error is displayed

        form_mode = new StringBuffer("E");
        this.getBindings().getOperationBinding("CreateInsert").execute();
        this.getBindings().getOperationBinding("getAndSetPackId").execute();
    }

    /**
     * ActionListener to edit Pack
     * @param actionEvent
     */
    public void editPackACTION(ActionEvent actionEvent) {


        System.out.println("Inside Ok..");
        OperationBinding op = getBindings().getOperationBinding("isProfileUsed");
        op.execute();
        System.out.println("value return by the function is=" + op.getResult());
        String retnval = (String)op.getResult();
        System.out.println("Return valie  " + retnval);
        if (op.getResult() != null) {
            if (retnval.equals("N")) {
                form_mode = new StringBuffer("E");
                System.out.println("this item is deleted");
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1178']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }

    }

    /**
     * ActionListener to cancel Pack
     * @param actionEvent
     */
    public void cancelPackACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("Rollback").execute();
        form_mode = new StringBuffer("V");
    }

    /**
     * ActionListener to delete Pack
     * @param actionEvent
     */
    public void deletePackACTION(ActionEvent actionEvent) {
        System.out.println("When Clock On Delete link call popup");
        showPopup(deletePopUpBVar, true);

    }

    /**
     * ActionListener to save Pack
     * @param actionEvent
     */
    public void savePackACTION(ActionEvent actionEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(unitBVar);
        /*  System.out.println("value unit " + unitBVar.getValue());
        String unit = (String)unitBVar.getValue(); //
        OperationBinding clUtn = getBindings().getOperationBinding("validUnit");
        clUtn.execute();
        System.out.println("rtn value " + clUtn.getResult() + " unit " + unit);
        if (clUtn.getResult() != null) {
            String rtnVal = (String)clUtn.getResult();
            if ("Y".equalsIgnoreCase(rtnVal)) {
                this.getBindings().getOperationBinding("Commit").execute();
                form_mode = new StringBuffer("V");
            } else */if (packWidthBVar.getValue() == null) {
            System.out.println("set2");
            FacesMessage message = new FacesMessage("Enter Width");
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(packWidthBVar.getClientId(), message);
        } else if (packLntBVar.getValue() == null) {
            System.out.println("set3");
            FacesMessage message = new FacesMessage("Enter Length");
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(packLntBVar.getClientId(), message);
        } else if (packHtBVar.getValue() == null) {
            System.out.println("set21");
            FacesMessage message = new FacesMessage("Enter Height");
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(packHtBVar.getClientId(), message);
        } else if (unitBVar.getValue() == null) {
            System.out.println("set1");
            FacesMessage message = new FacesMessage("Enter Unit");
            message.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(unitBVar.getClientId(), message);
        } else {
            System.out.println("set 2121212");
            this.getBindings().getOperationBinding("Commit").execute();
            form_mode = new StringBuffer("V");
        }
        /*  } else {
            FacesMessage message = new FacesMessage("Something went wrong .Please Create New Pack Profile");
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } */


    }

    /**
     *  method to get Bindings
     */
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setForm_mode(StringBuffer form_mode) {
        this.form_mode = form_mode;
    }

    public StringBuffer getForm_mode() {
        return form_mode;
    }


    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    /**
     * Validator to check for duplicate pack name
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void packetNameVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            //String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*"; //String with one space in middle

            String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*([ ])*"; //String with space in middle and end
            CharSequence inputStr = object.toString();
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            //String error = "Special Character Not Allowed";//MSG.1056


            if (matcher.matches()) {

                OperationBinding binding = this.getBindings().getOperationBinding("isPackNmValid");
                binding.getParamsMap().put("PackNm", new StringBuffer(object.toString()));
                if (binding.execute().equals((Object)false)) {
                    // FacesMessage message = new FacesMessage("Pack Name already exist!");
                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.1057']}").toString()); //Pack Name already exist!
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1056']}").toString(),
                                                              null)); //Special Character Not Allowed
            }


        }
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    /**
     * Validator to check negative value of fields
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void negativeNumberVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            int i = ((Number)object).compareTo(0);
            boolean b = isPrecisionValid(26, 6, (Number)object);
            System.out.println("b=" + b);
            if (i == -1 || i == 0) {
                //FacesMessage message = new FacesMessage("Invalid number!", "Please enter value greater than zero!");
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1058']}").toString()); //Invalid number!", "Please enter value greater than zero!
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else if (!b) {
                FacesMessage message =
                    new FacesMessage("Invalid Precision(26,6)"); //Invalid number!", "Please enter value greater than zero!
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

        }
    }

    private void showPopup(RichPopup pop, boolean visible) {
        try {

            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteListener(DialogEvent dialogEvent) {
        System.out.println("Inside Popup ok Dialogue--" + dialogEvent.getOutcome());
        if (dialogEvent.getOutcome().name().equals("ok")) {
            System.out.println("Inside Ok..");
            OperationBinding op = getBindings().getOperationBinding("isProfileUsed");
            op.execute();
            System.out.println("value return by the function is=" + op.getResult());
            String retnval = (String)op.getResult();
            if (op.getResult() != null) {
                if (retnval.equals("N")) {
                    form_mode = new StringBuffer("V");
                    System.out.println("this item is deleted");
                    this.getBindings().getOperationBinding("Delete").execute();
                    this.getBindings().getOperationBinding("Commit").execute();
                    this.getBindings().getOperationBinding("Execute").execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(addFrmBVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(pckNmBVar); ///
                    AdfFacesContext.getCurrentInstance().addPartialTarget(packTableBVar);

                } else {
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1179']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }
        }
    }

    public void setDeletePopUpBVar(RichPopup deletePopUpBVar) {
        this.deletePopUpBVar = deletePopUpBVar;
    }

    public RichPopup getDeletePopUpBVar() {
        return deletePopUpBVar;
    }

    public void setPackTableBVar(RichTable packTableBVar) {
        this.packTableBVar = packTableBVar;
    }

    public RichTable getPackTableBVar() {
        return packTableBVar;
    }

    public void setPckNmBVar(RichInputText pckNmBVar) {
        this.pckNmBVar = pckNmBVar;
    }

    public RichInputText getPckNmBVar() {
        return pckNmBVar;
    }

    public void setAddFrmBVar(RichPanelFormLayout addFrmBVar) {
        this.addFrmBVar = addFrmBVar;
    }

    public RichPanelFormLayout getAddFrmBVar() {
        return addFrmBVar;
    }

    public void searchAL(ActionEvent actionEvent) {
        OperationBinding op = this.getBindings().getOperationBinding("searchAction");
        op.execute();
    }

    public void resetAL(ActionEvent actionEvent) {
        OperationBinding opp = this.getBindings().getOperationBinding("setTblBlankOnRstClick");
        opp.execute();
        /*   OperationBinding op = this.getBindings().getOperationBinding("resetAction");
        op.execute();  */

    }

    public void executeOnCreate(ActionEvent actionEvent) {
        OperationBinding op = this.getBindings().getOperationBinding("resetAction");
        op.execute();
    }

    public void setUnitBVar(RichSelectOneChoice unitBVar) {
        this.unitBVar = unitBVar;
    }

    public RichSelectOneChoice getUnitBVar() {
        return unitBVar;
    }

    public void setPackNmBVar(RichInputListOfValues packNmBVar) {
        this.packNmBVar = packNmBVar;
    }

    public RichInputListOfValues getPackNmBVar() {
        return packNmBVar;
    }

    public void setWidthbVar(RichInputText widthbVar) {
        this.widthbVar = widthbVar;
    }

    public RichInputText getWidthbVar() {
        return widthbVar;
    }

    public void setLngthBVar(RichInputText lngthBVar) {
        this.lngthBVar = lngthBVar;
    }

    public RichInputText getLngthBVar() {
        return lngthBVar;
    }

    public void setHightBVar(RichInputText hightBVar) {
        this.hightBVar = hightBVar;
    }

    public RichInputText getHightBVar() {
        return hightBVar;
    }

    public void setUnitBBVar(RichSelectOneChoice unitBBVar) {
        this.unitBBVar = unitBBVar;
    }

    public RichSelectOneChoice getUnitBBVar() {
        return unitBBVar;
    }

    public void setPackHtBVar(RichInputText packHtBVar) {
        this.packHtBVar = packHtBVar;
    }

    public RichInputText getPackHtBVar() {
        return packHtBVar;
    }

    public void setPackLntBVar(RichInputText packLntBVar) {
        this.packLntBVar = packLntBVar;
    }

    public RichInputText getPackLntBVar() {
        return packLntBVar;
    }

    public void setPackWidthBVar(RichInputText packWidthBVar) {
        this.packWidthBVar = packWidthBVar;
    }

    public RichInputText getPackWidthBVar() {
        return packWidthBVar;
    }
}
