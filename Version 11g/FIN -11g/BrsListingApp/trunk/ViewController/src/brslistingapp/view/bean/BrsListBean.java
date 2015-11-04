package brslistingapp.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

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

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichGoLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class BrsListBean {
    private RichPopup editPopBinding;
    Key key = null;
    String vouid = null;
    String revVouid = null;
    private RichSelectOneChoice statusonPopupBind;
    private RichInputText glAmtFrmValidator;
    private RichInputText glamttobinding;
    private RichGoLink cheqPrintBinding;
    private boolean cheqPrint = false;
    private boolean cheqdetail = false;
    private RichGoLink cheqDeatailBinding;
    private RichPopup chequeprintPopBinding;
    private RichPopup chequeDetailPopBinding;
    private RichInputText vouIdBinding;
    private RichInputText instrumentNmBinding;
    private RichCommandImageLink editBtnBinding;
    private RichInputText statusBinding;
    private String mode = "v";
    private RichInputDate statusDateOnPopBinding;
    private RichInputText instrumentNoOnPopBinding;
    private RichInputDate instrumentDateOnPopBinding;
    private RichCommandImageLink goToRevVouLinkBinding;
    private RichSelectBooleanCheckbox consolidatedChkBind;


    public BrsListBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void searchActionlistener(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("search");
        op.execute();
    }

    public void resetButton(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("resetbutton");
        op.execute();
    }
    private Object instrumntNo;
    private Object instrDate;

    public void editStatusButtonAL(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("chkRevVouStatus");
        binding.execute();
        //Getting binding result returned from AM.
        String retParamVal = binding.getResult().toString();
        System.out.println("Param Value______________________"+binding.getResult().toString());
        if (retParamVal.toString().equalsIgnoreCase("Y")) {
            System.out.println("Voucher is avialable for Reversal...!");

            String rslt = null;
            //String msg="The Voucher selected is being referenced in Interim Voucher(s) for Adjustment, hence can not be edited. De link this voucher in respective Interim Vouchers and then proceed with the current operation !!";
            String msg = resolvElDCMsg("#{bundle['MSG.1233']}").toString();
            OperationBinding op = getBindings().getOperationBinding("getCurrentRowKey");
            op.execute();

            if (op.getErrors().size() == 0 && op.getResult() != null) {
                key = (Key)op.getResult();
                System.out.println("Key is=" + key);
            }
            getBindings().getOperationBinding("getValue").execute();
            OperationBinding opp = getBindings().getOperationBinding("chekVoucher");
            opp.execute();
            if (opp.getErrors().isEmpty() && opp.getResult() != null) {
                rslt = opp.getResult().toString();
                System.out.println("chek voucher resut--" + rslt);
                if (rslt.equalsIgnoreCase("N")) {
                    showPopup(editPopBinding, true);
                } else {
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            } else {
                System.out.println(opp.getErrors());
            }

        } else if (retParamVal.equalsIgnoreCase("NA")) {
            String errMsg = "Can't Reverse...! AS IT IS A VOUCHER REFERENCED/ADJUSTED BY ANOTHER VOUCHER.";
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            

        } else if (retParamVal.equalsIgnoreCase("NR")) {
            String errMsg = "Can't Reverse...! AS IT IS A REVERSED VOUCHER.";
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else if (retParamVal.equalsIgnoreCase("NS")) {
            String errMsg = "Can't Reverse...! AS IT IS A VOUCHER GENERATED FROM ANOTHER voucher.";
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else if (retParamVal.equalsIgnoreCase("NO")) {
            String errMsg = "Can't Reverse...! AS THIS VOUCHER HAS NOT BEEN CREATED IN FINANCE";
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else if (retParamVal.equalsIgnoreCase("N") || retParamVal.equalsIgnoreCase("E")) {
            String errMsg = "Error Occured...!Unable to Reverse the voucher...! Contact ESS...!!!";
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (retParamVal.equalsIgnoreCase("NM")) {
            String errMsg = "Can't Reverse...! AS IT IS A MIGRATED VOUCHER";
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (retParamVal.equalsIgnoreCase("NP")) {
            String errMsg = "Can't Reverse...! AS IT IS AS IT IS A OPENING VOUCHER";
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (retParamVal.equalsIgnoreCase("NPM")) {
            String errMsg = "Can't Reverse...! AS IT IS A MIGRATED OPENING VOUCHER";
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
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

    public void setEditPopBinding(RichPopup editPopBinding) {
        this.editPopBinding = editPopBinding;
    }

    public RichPopup getEditPopBinding() {
        return editPopBinding;
    }

    public void editDialougeListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            //  if (statusonPopupBind.getValue() != "" && statusonPopupBind.getValue()!=null) {
            if (statusonPopupBind.getValue() != null) {
                if (statusonPopupBind.getValue() != "") {
                    if (instrumentNoOnPopBinding.getValue() != null &&
                        instrumentNoOnPopBinding.getValue().toString().length() > 0) {
                        if (instrumentDateOnPopBinding.getValue() != null &&
                            instrumentDateOnPopBinding.getValue().toString().length() > 0) {
                            Integer status = (Integer)statusonPopupBind.getValue();
                            System.out.println("Status is ---------" + status);
                            OperationBinding opcall = getBindings().getOperationBinding("callmethod");
                            opcall.getParamsMap().put("status", status);
                            opcall.execute();

                            if (opcall.getErrors().size() == 0) {
                                OperationBinding op = getBindings().getOperationBinding("setCurrentRow");
                                op.getParamsMap().put("key", key);
                                op.execute();
                                if (op.getErrors().size() == 0 && opcall.getResult() != null) {
                                    System.out.println("Executed Successfully");

                                    String msg = opcall.getResult().toString();

                                    AdfFacesContext.getCurrentInstance().addPartialTarget(editBtnBinding);
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(statusBinding);
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(goToRevVouLinkBinding);
                                    System.out.println(msg);
                                    FacesMessage message = new FacesMessage(msg);
                                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);

                                } else
                                    System.out.println("Error is=" + op.getErrors());
                            } else {
                                FacesMessage message = new FacesMessage(opcall.getErrors().toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }
                        } else {
                            //String msg="Instrument Date is mandatory!";
                            String msg = resolvElDCMsg("#{bundle['MSG.1234']}").toString();
                            FacesMessage message = new FacesMessage(msg);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(instrumentDateOnPopBinding.getClientId(), message);
                        }
                    } else {
                        // String msg="Instrument No. is mandatory!";
                        String msg = resolvElDCMsg("#{bundle['MSG.1235']}").toString();
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(instrumentNoOnPopBinding.getClientId(), message);
                    }
                }
            }
        }
    }

    public void setStatusonPopupBind(RichSelectOneChoice statusonPopupBind) {
        this.statusonPopupBind = statusonPopupBind;
    }

    public RichSelectOneChoice getStatusonPopupBind() {
        return statusonPopupBind;
    }

    public void cancleListenerPop(PopupCanceledEvent popupCanceledEvent) {
        OperationBinding roll = getBindings().getOperationBinding("Rollback");
        roll.execute();
        OperationBinding op = getBindings().getOperationBinding("setCurrentRow");
        op.getParamsMap().put("key", key);
        op.execute();
        if (op.getErrors().size() == 0)
            System.out.println("Executed Successfully");
        else
            System.out.println("Error is=" + op.getErrors());
        System.out.println("Cancelled");
    }

    public String gotoGL() {
        OperationBinding op = getBindings().getOperationBinding("getVouId");
        op.execute();
        System.out.println("insilde go to gl+++++");
        if (op.getErrors().size() == 0 && op.getResult() != null) {
            vouid = (String)op.getResult();
            System.out.println("gotogl vouid--------" + vouid);
        }
        if (vouid != null) {
            System.out.println("not null voudid----" + vouid);
            return "GoToGl";
        } else
            return null;
    }


    public void setVouid(String vouid) {
        this.vouid = vouid;
    }

    public String getVouid() {
        return vouid;
    }

    public void editonrytclick(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setGlAmtFrmValidator(RichInputText glAmtFrmValidator) {
        this.glAmtFrmValidator = glAmtFrmValidator;
    }

    public RichInputText getGlAmtFrmValidator() {
        return glAmtFrmValidator;
    }

    public void glAmtToValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        glAmtFrmValidator.processUpdates(facesContext.getCurrentInstance());
        if (object != null) {
            if (((Number)object).compareTo(0) < 0) {
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //                     "Invalid Amount!,Can't be negaive", null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1105']}").toString(),
                                                              null));
            }
            Number amt = (Number)object;
            Boolean is = isPrecisionValid(26, 6, amt);
            if (is.toString().equalsIgnoreCase("true")) {
                if (glAmtFrmValidator.getValue() != null) {
                    Number amtfrm = (Number)glAmtFrmValidator.getValue();
                    if (amt.compareTo(amtfrm) < 0) {
                        // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        //                                             "Invalid Amount Range.", null));
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvElDCMsg("#{bundle['MSG.1106']}").toString(),
                                                                      null));
                    }
                }
            } else {
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                //null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1107']}").toString(),
                                                              null));
            }
        }
    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void glAmtFrmValidaotr(FacesContext facesContext, UIComponent uIComponent, Object object) {
        glamttobinding.processUpdates(facesContext.getCurrentInstance());
        if (object != null) {
            if (((Number)object).compareTo(0) < 0) {
                // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //                 "Invalid Amount!,Can't be negaive", null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1105']}").toString(),
                                                              null));
            }
            Number amt = (Number)object;
            Boolean is = isPrecisionValid(26, 6, amt);
            if (is.toString().equalsIgnoreCase("true")) {
                if (glamttobinding.getValue() != null) {
                    Number amtto = (Number)glamttobinding.getValue();
                    if (amt.compareTo(amtto) > 0) {
                        // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        //                                             "Invalid Amount Range.", null));
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvElDCMsg("#{bundle['MSG.1106']}").toString(),
                                                                      null));
                    }
                }
            } else {
                // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1107']}").toString(),
                                                              null));

            }
        }
    }

    public void setGlamttobinding(RichInputText glamttobinding) {
        this.glamttobinding = glamttobinding;
    }

    public RichInputText getGlamttobinding() {
        return glamttobinding;
    }

    /*  public String genrateBtnAction() {
        cheqPrint = true;
        cheqdetail = true;
        return null;
    }*/

    public void setCheqPrintBinding(RichGoLink cheqPrintBinding) {
        this.cheqPrintBinding = cheqPrintBinding;
    }

    public RichGoLink getCheqPrintBinding() {
        return cheqPrintBinding;
    }

    public void setCheqDeatailBinding(RichGoLink cheqDeatailBinding) {
        this.cheqDeatailBinding = cheqDeatailBinding;
    }

    public RichGoLink getCheqDeatailBinding() {
        return cheqDeatailBinding;
    }

    public void setCheqPrint(boolean cheqPrint) {
        this.cheqPrint = cheqPrint;
    }

    public boolean isCheqPrint() {
        return cheqPrint;
    }

    public void setCheqdetail(boolean cheqdetail) {
        this.cheqdetail = cheqdetail;
    }

    public boolean isCheqdetail() {
        return cheqdetail;
    }

    public void setChequeprintPopBinding(RichPopup chequeprintPopBinding) {
        this.chequeprintPopBinding = chequeprintPopBinding;
    }

    public RichPopup getChequeprintPopBinding() {
        return chequeprintPopBinding;
    }

    public void chequePrintBtnAL(ActionEvent actionEvent) {
        Integer result = (Integer)getBindings().getOperationBinding("setSysDate").execute();
        if (result == 1) {
            this.mode = "false";
            showPopup(chequeprintPopBinding, true);
        }
        if (result == -1) {
            FacesMessage message = new FacesMessage("something went wrong in date selection contact ESS!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void chequePrintCancelPop(PopupCanceledEvent popupCanceledEvent) {
        getBindings().getOperationBinding("chequePrintCancelpop").execute();
    }


    public void chequeDetailBtnAL(ActionEvent actionEvent) {
        Integer result = (Integer)getBindings().getOperationBinding("setSysDate").execute();
        if (result == 1) {
            this.mode = "false";
            showPopup(chequeDetailPopBinding, true);

        }
        if (result == -1) {
            FacesMessage message = new FacesMessage("Something Went Wrong In Date Selection");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

    }

    public void setChequeDetailPopBinding(RichPopup chequeDetailPopBinding) {
        this.chequeDetailPopBinding = chequeDetailPopBinding;
    }

    public RichPopup getChequeDetailPopBinding() {
        return chequeDetailPopBinding;
    }

    public void chequeDetailCancelPop(PopupCanceledEvent popupCanceledEvent) {
        getBindings().getOperationBinding("chequePrintCancelpop").execute();
    }

    /*  public void voucherNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String value = (String)object;
              if ((value == null) || value.length() == 0) {
                  return;
              }
              String expression = "[A-Za-z0-9]*";
              CharSequence inputStr = value;
              Pattern pattern = Pattern.compile(expression);
              Matcher matcher = pattern.matcher(inputStr);
              String error = "Special character not allowed";
              if (matcher.matches()) {
              } else {

                  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
              }
    }*/

    public void spaceInputValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String value = object.toString();
            String expression = "^\\S+(\\s\\S+)*$";
            CharSequence inputStr = value.trim();
            AdfFacesContext.getCurrentInstance().addPartialTarget(vouIdBinding);
            System.out.println("trimed value is-->>" + inputStr);
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            // String msg="Input is not proper!";
            String msg = resolvElDCMsg("#{bundle['MSG.1231']}").toString();
            if (matcher.matches()) {
                vouIdBinding.setValue(inputStr);
                System.out.println("final set value-->>" + inputStr);
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
            }
        }

    }

    public void setVouIdBinding(RichInputText vouIdBinding) {
        this.vouIdBinding = vouIdBinding;
    }

    public RichInputText getVouIdBinding() {
        return vouIdBinding;
    }

    public void instrumentNamevalidaotr(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String value = object.toString();
            String expression = "^\\S+(\\s\\S+)*$";
            CharSequence inputStr = value.trim();
            AdfFacesContext.getCurrentInstance().addPartialTarget(instrumentNmBinding);
            System.out.println("trimed value is-->>" + inputStr);
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            //String msg="Input is not proper!";
            String msg = resolvElDCMsg("#{bundle['MSG.1231']}").toString();
            if (matcher.matches()) {
                instrumentNmBinding.setValue(inputStr);
                System.out.println("final set value-->>" + inputStr);
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
            }
        }

    }

    public void setInstrumentNmBinding(RichInputText instrumentNmBinding) {
        this.instrumentNmBinding = instrumentNmBinding;
    }

    public RichInputText getInstrumentNmBinding() {
        return instrumentNmBinding;
    }

    public String goToRevVouGL() {
        OperationBinding binding = getBindings().getOperationBinding("getRevVouId");
        binding.execute();
        if (binding.getErrors().size() == 0 && binding.getResult() != null) {
            revVouid = binding.getResult().toString();
        }
        if (revVouid != null) {
            System.out.println("not null rev vou--->" + revVouid);
            return "GoToRevGl";

        } else
            System.out.println("rev gl me nhi gya");
        return null;
    }

    public void setRevVouid(String revVouid) {
        this.revVouid = revVouid;
    }

    public String getRevVouid() {
        return revVouid;
    }

    public void setEditBtnBinding(RichCommandImageLink editBtnBinding) {
        this.editBtnBinding = editBtnBinding;
    }

    public RichCommandImageLink getEditBtnBinding() {
        return editBtnBinding;
    }

    public void setStatusBinding(RichInputText statusBinding) {
        this.statusBinding = statusBinding;
    }

    public RichInputText getStatusBinding() {
        return statusBinding;
    }

    public void showRegisterLinkAL(ActionEvent actionEvent) {
        this.mode = "true";
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void glAmounToVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void glAmtFrmVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void showChequePrintAL(ActionEvent actionEvent) {
        this.mode = "true";
    }


    public void chequePrintComonVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.mode = "false";
        AdfFacesContext.getCurrentInstance().addPartialTarget(cheqPrintBinding);
    }

    public void chequeRegisterComonDateVCL(ValueChangeEvent valueChangeEvent) {
        this.mode = "false";
        AdfFacesContext.getCurrentInstance().addPartialTarget(cheqDeatailBinding);
    }

    public void chequeRegComAmtVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.mode = "false";
        AdfFacesContext.getCurrentInstance().addPartialTarget(cheqDeatailBinding);
    }

    public void chqueRegStatusVCL(ValueChangeEvent valueChangeEvent) {
        this.mode = "false";
        AdfFacesContext.getCurrentInstance().addPartialTarget(cheqDeatailBinding);
    }

    public void chequeRegInstrTypVCL(ValueChangeEvent valueChangeEvent) {
        this.mode = "false";
        AdfFacesContext.getCurrentInstance().addPartialTarget(cheqDeatailBinding);
    }

    public void chequeRegCoaVCL(ValueChangeEvent valueChangeEvent) {
        this.mode = "false";
        AdfFacesContext.getCurrentInstance().addPartialTarget(cheqDeatailBinding);
    }

    public void chequeRegCurrencyVCL(ValueChangeEvent valueChangeEvent) {
        this.mode = "false";
        AdfFacesContext.getCurrentInstance().addPartialTarget(cheqDeatailBinding);
    }

    public void statusDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Date dt = (Date)object;
            //String msg="Enter a valid date, should be greater than or equal to Instrument Date & can't be greater than current date!";
            String msg = resolvElDCMsg("#{bundle['MSG.1232']}").toString();
            OperationBinding op = getBindings().getOperationBinding("checkStatusDate");
            op.getParamsMap().put("newdt", dt);
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("y")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void statusOnPopUpVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("Inside Status Value change before reset");

        getBindings().getOperationBinding("resetvalue").execute();

        System.out.println("Inside Status Value change aft reset");
        AdfFacesContext.getCurrentInstance().addPartialTarget(statusonPopupBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(statusDateOnPopBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(instrumentNoOnPopBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(instrumentDateOnPopBinding);
    }

    public void setStatusDateOnPopBinding(RichInputDate statusDateOnPopBinding) {
        this.statusDateOnPopBinding = statusDateOnPopBinding;
    }

    public RichInputDate getStatusDateOnPopBinding() {
        return statusDateOnPopBinding;
    }

    public void setInstrumentNoOnPopBinding(RichInputText instrumentNoOnPopBinding) {
        this.instrumentNoOnPopBinding = instrumentNoOnPopBinding;
    }

    public RichInputText getInstrumentNoOnPopBinding() {
        return instrumentNoOnPopBinding;
    }

    public void setInstrumentDateOnPopBinding(RichInputDate instrumentDateOnPopBinding) {
        this.instrumentDateOnPopBinding = instrumentDateOnPopBinding;
    }

    public RichInputDate getInstrumentDateOnPopBinding() {
        return instrumentDateOnPopBinding;
    }

    public void setGoToRevVouLinkBinding(RichCommandImageLink goToRevVouLinkBinding) {
        this.goToRevVouLinkBinding = goToRevVouLinkBinding;
    }

    public RichCommandImageLink getGoToRevVouLinkBinding() {
        return goToRevVouLinkBinding;
    }


    public void setConsolidatedChkBind(RichSelectBooleanCheckbox consolidatedChkBind) {
        this.consolidatedChkBind = consolidatedChkBind;
    }

    public RichSelectBooleanCheckbox getConsolidatedChkBind() {
        return consolidatedChkBind;
    }
}
