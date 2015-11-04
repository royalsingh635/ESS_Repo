package brslistingapp.view.bean;

import brslistingapp.model.service.BrsListingAppAMImpl;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
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
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichGoLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
//import oracle.jbo.domain.Date;

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
    private Number Difference;
    private String getBalances;
    private RichPanelFormLayout bankInforFormBind;
    private RichPopup warningPopup;
    private RichTable insDetailTableBind;

    public void setGetBalances(String getBalances) {
        this.getBalances = getBalances;
    }

    public String getGetBalances() {
        return getBalances;
    }

    public void setDifference(Number Difference) {
        this.Difference = Difference;
    }

    public Number getDifference() {
        return Difference;
    }
    private RichInputText instrumentNmBinding;
    private RichCommandImageLink editBtnBinding;
    private RichInputText statusBinding;
    private String mode = "v";
    private RichInputDate statusDateOnPopBinding;
    private RichInputText instrumentNoOnPopBinding;
    private RichInputDate instrumentDateOnPopBinding;
    private RichCommandImageLink goToRevVouLinkBinding;
    private RichSelectBooleanCheckbox consolidatedChkBind;
    private RichSelectOneRadio fileTypeRadioBind;
    private RichSelectOneRadio fileTypeRadioChqBind;
    private RichTable bankStatusTableBind;
    private RichInputText resultBindingBean;


    public BrsListBean() {
        /* Object updatedRows = resolvElO("#{pageFlowScope.UPDATED_ROWS}");
        System.out.println("rows in bean :" + updatedRows); */
    }

    public Object resolvElO(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        //     Object Message = valueExp.getValue(elContext).toString();

        //   return Message.toString();
        return valueExp.getValue(elContext);
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
        System.out.println("Param Value______________________" + binding.getResult().toString());
        if (retParamVal.toString().equalsIgnoreCase("Y")) {
            System.out.println("Voucher is avialable for Reversal...!");

            String rslt = null;
            //String msg="The Voucher selected is being referenced in Interim Voucher(s) for Adjustment, hence can not be edited. De link this voucher in respective Interim Vouchers and then proceed with the current operation !!";
            String msg = resolvElDCMsg("#{bundle['MSG.1233']}").toString();
            OperationBinding op = getBindings().getOperationBinding("getCurrentRowKey");
            op.execute();

            if (op.getErrors().size() == 0 && op.getResult() != null) {
                key = (Key) op.getResult();
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
            String errMsg =
                (String) resolvEl("#{bundle['MSG.2614']}");  //Can't Reverse...! AS IT IS A VOUCHER REFERENCED/ADJUSTED BY ANOTHER VOUCHER.
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);


        } else if (retParamVal.equalsIgnoreCase("NR")) {
            String errMsg = (String) resolvEl("#{bundle['MSG.2615']}"); //Can't Reverse, as it is a reversed Voucher
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else if (retParamVal.equalsIgnoreCase("NS")) {
            String errMsg = (String) resolvEl("#{bundle['MSG.2616']}");  //Can't Reverse, as it is a voucher generated from another voucher.
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else if (retParamVal.equalsIgnoreCase("NO")) {
            String errMsg = (String) resolvEl("#{bundle['MSG.2617']}");  //Can't Reverse, as this voucher has not been created.
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else if (retParamVal.equalsIgnoreCase("N") || retParamVal.equalsIgnoreCase("E")) {
            String errMsg = (String) resolvEl("#{bundle['MSG.2618']}");  //Error occurred in reversing voucher. Contact ESS.
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (retParamVal.equalsIgnoreCase("NM")) {
            String errMsg = (String) resolvEl("#{bundle['MSG.2619']}");   //Can't reverse as it is a migrated voucher.
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (retParamVal.equalsIgnoreCase("NP")) {
            String errMsg = (String) resolvEl("#{bundle['MSG.2620']}");  //Can't reverse as it is an Opening Voucher.
            FacesMessage message = new FacesMessage(errMsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (retParamVal.equalsIgnoreCase("NPM")) {
            String errMsg = (String) resolvEl("#{bundle['MSG.2621']}"); //Can't reverse as it is a Migrated Opening Voucher.
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
        showPopup(warningPopup, true);
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
            vouid = (String) op.getResult();
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
            if (((Number) object).compareTo(0) <
                0) {
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //                     "Invalid Amount!,Can't be negaive", null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1105']}").toString(), null));
            }
            Number amt = (Number) object;
            Boolean is = isPrecisionValid(26, 6, amt);
            if (is.toString().equalsIgnoreCase("true")) {
                if (glAmtFrmValidator.getValue() != null) {
                    Number amtfrm = (Number) glAmtFrmValidator.getValue();
                    if (amt.compareTo(amtfrm) <
                        0) {
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
                                                              resolvElDCMsg("#{bundle['MSG.1107']}").toString(), null));
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
            if (((Number) object).compareTo(0) <
                0) {
                // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //                 "Invalid Amount!,Can't be negaive", null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1105']}").toString(), null));
            }
            Number amt = (Number) object;
            Boolean is = isPrecisionValid(26, 6, amt);
            if (is.toString().equalsIgnoreCase("true")) {
                if (glamttobinding.getValue() != null) {
                    Number amtto = (Number) glamttobinding.getValue();
                    if (amt.compareTo(amtto) >
                        0) {
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
                                                              resolvElDCMsg("#{bundle['MSG.1107']}").toString(), null));

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
        Integer result = (Integer) getBindings().getOperationBinding("setSysDate").execute();
        if (result == 1) {
            this.mode = "false";
            showPopup(chequeprintPopBinding, true);
        }
        if (result == -1) {
            String msg = (String) resolvEl("#{bundle['MSG.1697']}");  //Something went wrong.Contact ESS
            String msg1 = (String) resolvEl("#{bundle['MSG.2622']}");    //Issues in Date Selection
            FacesMessage message = new FacesMessage(msg + msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void chequePrintCancelPop(PopupCanceledEvent popupCanceledEvent) {
        getBindings().getOperationBinding("chequePrintCancelpop").execute();
    }


    public void chequeDetailBtnAL(ActionEvent actionEvent) {
        Integer result = (Integer) getBindings().getOperationBinding("setSysDate").execute();
        if (result == 1) {
            this.mode = "false";
            showPopup(chequeDetailPopBinding, true);

        }
        if (result == -1) {
            String msg = (String) resolvEl("#{bundle['MSG.1697']}");  //Something went wrong.Contact ESS
            String msg1 = (String) resolvEl("#{bundle['MSG.2622']}");    //Issues in Date Selection
            FacesMessage message = new FacesMessage(msg + msg1);
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
        if (object != null && object.toString().length() > 0) {
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
        if (object != null && object.toString().length() > 0) {
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

    public void chequePrintComonVCL1(ValueChangeEvent valueChangeEvent) {
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
            Object dt = object;
            Object check = fyDateCheck(object);
            if (check.equals("Y")) {
                String msg = (String) resolvEl("#{bundle['MSG.2623']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg,
                                                              null));  //"Transaction is not allowed for this date"

            } else {
                //String msg="Enter a valid date, should be greater than or equal to Instrument Date & can't be greater than current date!";
                String msg = resolvElDCMsg("#{bundle['MSG.1232']}").toString();
                OperationBinding op = getBindings().getOperationBinding("checkStatusDate");
                op.getParamsMap().put("newdt", dt.toString());
                op.execute();
                if (op.getErrors().isEmpty() && op.getResult() != null) {
                    String rslt = op.getResult().toString();
                    if (rslt.equalsIgnoreCase("y")) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
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

    public void setFileTypeRadioBind(RichSelectOneRadio fileTypeRadioBind) {
        this.fileTypeRadioBind = fileTypeRadioBind;
    }

    public RichSelectOneRadio getFileTypeRadioBind() {
        return fileTypeRadioBind;
    }

    public void setFileTypeRadioChqBind(RichSelectOneRadio fileTypeRadioChqBind) {
        this.fileTypeRadioChqBind = fileTypeRadioChqBind;
    }

    public RichSelectOneRadio getFileTypeRadioChqBind() {
        return fileTypeRadioChqBind;
    }

    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }


    /** For Exporting data in Excel.*/

    public void exportToExcelActionListener(FacesContext facesContext, OutputStream outputStream) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);

        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i <= 15; i++) {
            HSSFCell cell = createRow.createCell(i);

            switch (i) {
            case 0:
                cell.setCellValue("STATUS");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("GL_INSTRUMENT_STATUS");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("VOUCHER_NO");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("VOUCHER_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("VOUCHER_DATE");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("INSTRUMENT_NO");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("INSTRUMENT_STATUS_DATE");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("INSTRUMENT_DATE");
                cell.setCellStyle(cellStyle);
                break;
            case 8:
                cell.setCellValue("NAME_ON_INSTRUMENT");
                cell.setCellStyle(cellStyle);
                break;
            case 9:
                cell.setCellValue("BANK_COA_NAME");
                cell.setCellStyle(cellStyle);
                break;
            case 10:
                cell.setCellValue("AMOUNT_SPECIFIC");
                cell.setCellStyle(cellStyle);
                break;
            case 11:
                cell.setCellValue("CURRENCY");
                cell.setCellStyle(cellStyle);
                break;
            case 12:
                cell.setCellValue("REVERSE_VOUCHER");
                cell.setCellStyle(cellStyle);
                break;
            case 13:
                cell.setCellValue("NARRATION");
                cell.setCellStyle(cellStyle);
                break;
            case 14:
                cell.setCellValue("GL_SL_NO");
                cell.setCellStyle(cellStyle);
                break;
            case 15:
                cell.setCellValue("GL_CHQ_SL_NO");
                cell.setCellStyle(cellStyle);
                break;
            }
        }
        String CldId = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

        String OrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String HoOrgId = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItr = (DCIteratorBinding) dcBindings.get("GlLineInstrumnt1Iterator");
        ViewObjectImpl object = (ViewObjectImpl) dcItr.getViewObject();
        RowQualifier rq = new RowQualifier(object);

        rq.setWhereClause("GlCldId='" + CldId + "' and GlSlocId=" + SlocId + " and GlHoOrgId='" + HoOrgId +
                          "' and GlOrgId='" + OrgId + "'");

        Row[] fr = object.getFilteredRows(rq); //100
        RowSetIterator itr = object.createRowSetIterator(null);
        Row[] filteredRowsRec = itr.getFilteredRows("GlInstrmntStat", 0); //80
        Row[] filteredRowsRei = itr.getFilteredRows("GlInstrmntStat", 110);
        Row[] filteredRowsRev = itr.getFilteredRows("GlInstrmntStat", 186);

        System.out.println("Filtered rows for Received ______" + filteredRowsRec.length);
        System.out.println("Filtered rows for Re-Issued______" + filteredRowsRei.length);
        System.out.println("Filtered rows for Re-Validate______" + filteredRowsRev.length);


        int rownum = 1;

        for (Row next : filteredRowsRec) {
            HSSFRow row = sheet.createRow(rownum++);
            Object statusObj = next.getAttribute("Status");
            Object instStatObj = next.getAttribute("GlInstrmntStat");
            Object vouDispNoObj = next.getAttribute("DocTxnIdDisp");
            Object vouIdObj = next.getAttribute("GlVouId");

            Object vouDtObj = next.getAttribute("GlVouDt");
            if (next.getAttribute("GlVouDt") != null && next.getAttribute("GlVouDt").toString().length() > 0) {
                vouDtObj = getConvertTimeStampToStr(next.getAttribute("GlVouDt").toString());
            }

            Object instNoObj = next.getAttribute("GlInstrmntNo");

            Object instStatDtObj = next.getAttribute("GlInstrmntStatDt");
            if (next.getAttribute("GlInstrmntStatDt") != null &&
                next.getAttribute("GlInstrmntStatDt").toString().length() > 0) {
                instStatDtObj = getConvertTimeStampToStr(next.getAttribute("GlInstrmntStatDt").toString());

            }

            Object instDtObj = next.getAttribute("GlInstrmntDt");
            if (next.getAttribute("GlInstrmntDt") != null &&
                next.getAttribute("GlInstrmntDt").toString().length() > 0) {
                instDtObj = getConvertTimeStampToStr(next.getAttribute("GlInstrmntDt").toString());
            }


            Object instCoaNmObj = next.getAttribute("GlInstrmntNm");
            Object bankCoaNmObj = next.getAttribute("BankCoaNm");
            Object amtSpObj = next.getAttribute("GlAmtSp");
            Object currNmObj = next.getAttribute("Currency");
            Object revVouObj = next.getAttribute("DocTxnIdDispRev");
            Object narrObj = next.getAttribute("Narration");
            Object slNoObj = next.getAttribute("GlSlNo");
            Object chkSlNoObj = next.getAttribute("GlChqSlNo");

            StringBuilder status =
                (statusObj == null ? new StringBuilder("") : new StringBuilder(statusObj.toString()));
            StringBuilder instStat =
                (instStatObj == null ? new StringBuilder("") : new StringBuilder(instStatObj.toString()));
            StringBuilder vouDispNo =
                (vouDispNoObj == null ? new StringBuilder("") : new StringBuilder(vouDispNoObj.toString()));
            StringBuilder vouId = (vouIdObj == null ? new StringBuilder("") : new StringBuilder(vouIdObj.toString()));
            StringBuilder vouDt = (vouDtObj == null ? new StringBuilder("") : new StringBuilder(vouDtObj.toString()));
            StringBuilder instNo =
                (instNoObj == null ? new StringBuilder("") : new StringBuilder(instNoObj.toString()));
            StringBuilder instStatDt =
                (instStatDtObj == null ? new StringBuilder("") : new StringBuilder(instStatDtObj.toString()));
            StringBuilder instDt =
                (instDtObj == null ? new StringBuilder("") : new StringBuilder(instDtObj.toString()));
            StringBuilder instCoaNm =
                (instCoaNmObj == null ? new StringBuilder("") : new StringBuilder(instCoaNmObj.toString()));
            StringBuilder bankCoaNm =
                (bankCoaNmObj == null ? new StringBuilder("") : new StringBuilder(bankCoaNmObj.toString()));
            StringBuilder amtSp = (amtSpObj == null ? new StringBuilder("") : new StringBuilder(amtSpObj.toString()));
            StringBuilder currNm =
                (currNmObj == null ? new StringBuilder("") : new StringBuilder(currNmObj.toString()));
            StringBuilder revVou =
                (revVouObj == null ? new StringBuilder("") : new StringBuilder(revVouObj.toString()));
            StringBuilder narr = (narrObj == null ? new StringBuilder("") : new StringBuilder(narrObj.toString()));
            StringBuilder slNo = (slNoObj == null ? new StringBuilder("") : new StringBuilder(slNoObj.toString()));
            StringBuilder chkSlNo =
                (chkSlNoObj == null ? new StringBuilder("") : new StringBuilder(chkSlNoObj.toString()));
            StringBuilder blank = new StringBuilder("");

            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            Cell cell4 = row.createCell(4);
            Cell cell5 = row.createCell(5);
            Cell cell6 = row.createCell(6);
            Cell cell7 = row.createCell(7);
            Cell cell8 = row.createCell(8);
            Cell cell9 = row.createCell(9);
            Cell cell10 = row.createCell(10);
            Cell cell11 = row.createCell(11);
            Cell cell12 = row.createCell(12);
            Cell cell13 = row.createCell(13);
            Cell cell14 = row.createCell(14);
            Cell cell15 = row.createCell(15);

            cell0.setCellValue(status.toString());
            if (instStatObj != null && instStatObj.toString().length() > 0) {
                cell1.setCellValue(new Double(instStatObj.toString()));
            } else {
                cell1.setCellValue(blank.toString());
            }

            cell2.setCellValue(vouDispNo.toString());
            cell3.setCellValue(vouId.toString());
            cell4.setCellValue(vouDt.toString());
            cell5.setCellValue(instNo.toString());
            cell6.setCellValue(instStatDt.toString());
            cell7.setCellValue(instDt.toString());


            cell8.setCellValue(instCoaNm.toString());

            cell9.setCellValue(bankCoaNm.toString());


            if (amtSpObj != null && amtSpObj.toString().length() > 0) {
                cell10.setCellValue(new Double(amtSpObj.toString()));
            } else {
                cell10.setCellValue(blank.toString());
            }

            cell11.setCellValue(currNm.toString());
            cell12.setCellValue(revVou.toString());
            cell13.setCellValue(narr.toString());

            if (slNoObj != null && slNoObj.toString().length() > 0) {
                cell14.setCellValue(new Double(slNoObj.toString()));

            } else {
                cell14.setCellValue(blank.toString());
            }

            if (chkSlNoObj != null && chkSlNoObj.toString().length() > 0) {
                cell15.setCellValue(new Double(chkSlNoObj.toString()));

            } else {
                cell15.setCellValue(blank.toString());
            }


            System.out.println("Row added ___________________");
            for (int i = 0; i <= 15; i++) {
                sheet.autoSizeColumn(i);
            }
        }


        // int rownum = 1;

        for (Row next : filteredRowsRev) {
            HSSFRow row = sheet.createRow(rownum++);
            Object statusObj = next.getAttribute("Status");
            Object instStatObj = next.getAttribute("GlInstrmntStat");
            Object vouDispNoObj = next.getAttribute("DocTxnIdDisp");
            Object vouIdObj = next.getAttribute("GlVouId");

            Object vouDtObj = next.getAttribute("GlVouDt");
            if (next.getAttribute("GlVouDt") != null && next.getAttribute("GlVouDt").toString().length() > 0) {
                vouDtObj = getConvertTimeStampToStr(next.getAttribute("GlVouDt").toString());
            }

            Object instNoObj = next.getAttribute("GlInstrmntNo");

            Object instStatDtObj = next.getAttribute("GlInstrmntStatDt");
            if (next.getAttribute("GlInstrmntStatDt") != null &&
                next.getAttribute("GlInstrmntStatDt").toString().length() > 0) {
                instStatDtObj = getConvertTimeStampToStr(next.getAttribute("GlInstrmntStatDt").toString());

            }

            Object instDtObj = next.getAttribute("GlInstrmntDt");
            if (next.getAttribute("GlInstrmntDt") != null &&
                next.getAttribute("GlInstrmntDt").toString().length() > 0) {
                instDtObj = getConvertTimeStampToStr(next.getAttribute("GlInstrmntDt").toString());
            }


            Object instCoaNmObj = next.getAttribute("GlInstrmntNm");
            Object bankCoaNmObj = next.getAttribute("BankCoaNm");
            Object amtSpObj = next.getAttribute("GlAmtSp");
            Object currNmObj = next.getAttribute("Currency");
            Object revVouObj = next.getAttribute("DocTxnIdDispRev");
            Object narrObj = next.getAttribute("Narration");
            Object slNoObj = next.getAttribute("GlSlNo");
            Object chkSlNoObj = next.getAttribute("GlChqSlNo");

            StringBuilder status =
                (statusObj == null ? new StringBuilder("") : new StringBuilder(statusObj.toString()));
            StringBuilder instStat =
                (instStatObj == null ? new StringBuilder("") : new StringBuilder(instStatObj.toString()));
            StringBuilder vouDispNo =
                (vouDispNoObj == null ? new StringBuilder("") : new StringBuilder(vouDispNoObj.toString()));
            StringBuilder vouId = (vouIdObj == null ? new StringBuilder("") : new StringBuilder(vouIdObj.toString()));
            StringBuilder vouDt = (vouDtObj == null ? new StringBuilder("") : new StringBuilder(vouDtObj.toString()));
            StringBuilder instNo =
                (instNoObj == null ? new StringBuilder("") : new StringBuilder(instNoObj.toString()));
            StringBuilder instStatDt =
                (instStatDtObj == null ? new StringBuilder("") : new StringBuilder(instStatDtObj.toString()));
            StringBuilder instDt =
                (instDtObj == null ? new StringBuilder("") : new StringBuilder(instDtObj.toString()));
            StringBuilder instCoaNm =
                (instCoaNmObj == null ? new StringBuilder("") : new StringBuilder(instCoaNmObj.toString()));
            StringBuilder bankCoaNm =
                (bankCoaNmObj == null ? new StringBuilder("") : new StringBuilder(bankCoaNmObj.toString()));
            StringBuilder amtSp = (amtSpObj == null ? new StringBuilder("") : new StringBuilder(amtSpObj.toString()));
            StringBuilder currNm =
                (currNmObj == null ? new StringBuilder("") : new StringBuilder(currNmObj.toString()));
            StringBuilder revVou =
                (revVouObj == null ? new StringBuilder("") : new StringBuilder(revVouObj.toString()));
            StringBuilder narr = (narrObj == null ? new StringBuilder("") : new StringBuilder(narrObj.toString()));
            StringBuilder slNo = (slNoObj == null ? new StringBuilder("") : new StringBuilder(slNoObj.toString()));
            StringBuilder chkSlNo =
                (chkSlNoObj == null ? new StringBuilder("") : new StringBuilder(chkSlNoObj.toString()));
            StringBuilder blank = new StringBuilder("");

            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            Cell cell4 = row.createCell(4);
            Cell cell5 = row.createCell(5);
            Cell cell6 = row.createCell(6);
            Cell cell7 = row.createCell(7);
            Cell cell8 = row.createCell(8);
            Cell cell9 = row.createCell(9);
            Cell cell10 = row.createCell(10);
            Cell cell11 = row.createCell(11);
            Cell cell12 = row.createCell(12);
            Cell cell13 = row.createCell(13);
            Cell cell14 = row.createCell(14);
            Cell cell15 = row.createCell(15);

            cell0.setCellValue(status.toString());
            if (instStatObj != null && instStatObj.toString().length() > 0) {
                cell1.setCellValue(new Double(instStatObj.toString()));
            } else {
                cell1.setCellValue(blank.toString());
            }

            cell2.setCellValue(vouDispNo.toString());
            cell3.setCellValue(vouId.toString());
            cell4.setCellValue(vouDt.toString());
            cell5.setCellValue(instNo.toString());
            cell6.setCellValue(instStatDt.toString());
            cell7.setCellValue(instDt.toString());


            cell8.setCellValue(instCoaNm.toString());

            cell9.setCellValue(bankCoaNm.toString());


            if (amtSpObj != null && amtSpObj.toString().length() > 0) {
                cell10.setCellValue(new Double(amtSpObj.toString()));
            } else {
                cell10.setCellValue(blank.toString());
            }

            cell11.setCellValue(currNm.toString());
            cell12.setCellValue(revVou.toString());
            cell13.setCellValue(narr.toString());

            if (slNoObj != null && slNoObj.toString().length() > 0) {
                cell14.setCellValue(new Double(slNoObj.toString()));

            } else {
                cell14.setCellValue(blank.toString());
            }

            if (chkSlNoObj != null && chkSlNoObj.toString().length() > 0) {
                cell15.setCellValue(new Double(chkSlNoObj.toString()));

            } else {
                cell15.setCellValue(blank.toString());
            }


            System.out.println("Row added for SECOND ___________________");
            for (int i = 0; i <= 15; i++) {
                sheet.autoSizeColumn(i);
            }
        }


        /*for (int rei = 0; filteredRowsRei.length > rei; rei++) { */
        //System.out.println("IN third for lopp______________-");
        // int rownum = 1;

        for (Row next : filteredRowsRei) {
            HSSFRow row = sheet.createRow(rownum++);
            Object statusObj = next.getAttribute("Status");
            Object instStatObj = next.getAttribute("GlInstrmntStat");
            Object vouDispNoObj = next.getAttribute("DocTxnIdDisp");
            Object vouIdObj = next.getAttribute("GlVouId");

            Object vouDtObj = next.getAttribute("GlVouDt");
            if (next.getAttribute("GlVouDt") != null && next.getAttribute("GlVouDt").toString().length() > 0) {
                vouDtObj = getConvertTimeStampToStr(next.getAttribute("GlVouDt").toString());
            }

            Object instNoObj = next.getAttribute("GlInstrmntNo");

            Object instStatDtObj = next.getAttribute("GlInstrmntStatDt");
            if (next.getAttribute("GlInstrmntStatDt") != null &&
                next.getAttribute("GlInstrmntStatDt").toString().length() > 0) {
                instStatDtObj = getConvertTimeStampToStr(next.getAttribute("GlInstrmntStatDt").toString());

            }

            Object instDtObj = next.getAttribute("GlInstrmntDt");
            if (next.getAttribute("GlInstrmntDt") != null &&
                next.getAttribute("GlInstrmntDt").toString().length() > 0) {
                instDtObj = getConvertTimeStampToStr(next.getAttribute("GlInstrmntDt").toString());
            }


            Object instCoaNmObj = next.getAttribute("GlInstrmntNm");
            Object bankCoaNmObj = next.getAttribute("BankCoaNm");
            Object amtSpObj = next.getAttribute("GlAmtSp");
            Object currNmObj = next.getAttribute("Currency");
            Object revVouObj = next.getAttribute("DocTxnIdDispRev");
            Object narrObj = next.getAttribute("Narration");
            Object slNoObj = next.getAttribute("GlSlNo");
            Object chkSlNoObj = next.getAttribute("GlChqSlNo");

            StringBuilder status =
                (statusObj == null ? new StringBuilder("") : new StringBuilder(statusObj.toString()));
            StringBuilder instStat =
                (instStatObj == null ? new StringBuilder("") : new StringBuilder(instStatObj.toString()));
            StringBuilder vouDispNo =
                (vouDispNoObj == null ? new StringBuilder("") : new StringBuilder(vouDispNoObj.toString()));
            StringBuilder vouId = (vouIdObj == null ? new StringBuilder("") : new StringBuilder(vouIdObj.toString()));
            StringBuilder vouDt = (vouDtObj == null ? new StringBuilder("") : new StringBuilder(vouDtObj.toString()));
            StringBuilder instNo =
                (instNoObj == null ? new StringBuilder("") : new StringBuilder(instNoObj.toString()));
            StringBuilder instStatDt =
                (instStatDtObj == null ? new StringBuilder("") : new StringBuilder(instStatDtObj.toString()));
            StringBuilder instDt =
                (instDtObj == null ? new StringBuilder("") : new StringBuilder(instDtObj.toString()));
            StringBuilder instCoaNm =
                (instCoaNmObj == null ? new StringBuilder("") : new StringBuilder(instCoaNmObj.toString()));
            StringBuilder bankCoaNm =
                (bankCoaNmObj == null ? new StringBuilder("") : new StringBuilder(bankCoaNmObj.toString()));
            StringBuilder amtSp = (amtSpObj == null ? new StringBuilder("") : new StringBuilder(amtSpObj.toString()));
            StringBuilder currNm =
                (currNmObj == null ? new StringBuilder("") : new StringBuilder(currNmObj.toString()));
            StringBuilder revVou =
                (revVouObj == null ? new StringBuilder("") : new StringBuilder(revVouObj.toString()));
            StringBuilder narr = (narrObj == null ? new StringBuilder("") : new StringBuilder(narrObj.toString()));
            StringBuilder slNo = (slNoObj == null ? new StringBuilder("") : new StringBuilder(slNoObj.toString()));
            StringBuilder chkSlNo =
                (chkSlNoObj == null ? new StringBuilder("") : new StringBuilder(chkSlNoObj.toString()));
            StringBuilder blank = new StringBuilder("");

            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            Cell cell4 = row.createCell(4);
            Cell cell5 = row.createCell(5);
            Cell cell6 = row.createCell(6);
            Cell cell7 = row.createCell(7);
            Cell cell8 = row.createCell(8);
            Cell cell9 = row.createCell(9);
            Cell cell10 = row.createCell(10);
            Cell cell11 = row.createCell(11);
            Cell cell12 = row.createCell(12);
            Cell cell13 = row.createCell(13);
            Cell cell14 = row.createCell(14);
            Cell cell15 = row.createCell(15);

            cell0.setCellValue(status.toString());
            if (instStatObj != null && instStatObj.toString().length() > 0) {
                cell1.setCellValue(new Double(instStatObj.toString()));
            } else {
                cell1.setCellValue(blank.toString());
            }

            cell2.setCellValue(vouDispNo.toString());
            cell3.setCellValue(vouId.toString());
            cell4.setCellValue(vouDt.toString());
            cell5.setCellValue(instNo.toString());
            cell6.setCellValue(instStatDt.toString());
            cell7.setCellValue(instDt.toString());


            cell8.setCellValue(instCoaNm.toString());

            cell9.setCellValue(bankCoaNm.toString());


            if (amtSpObj != null && amtSpObj.toString().length() > 0) {
                cell10.setCellValue(new Double(amtSpObj.toString()));
            } else {
                cell10.setCellValue(blank.toString());
            }

            cell11.setCellValue(currNm.toString());
            cell12.setCellValue(revVou.toString());
            cell13.setCellValue(narr.toString());

            if (slNoObj != null && slNoObj.toString().length() > 0) {
                cell14.setCellValue(new Double(slNoObj.toString()));

            } else {
                cell14.setCellValue(blank.toString());
            }

            if (chkSlNoObj != null && chkSlNoObj.toString().length() > 0) {
                cell15.setCellValue(new Double(chkSlNoObj.toString()));

            } else {
                cell15.setCellValue(blank.toString());
            }


            System.out.println("Row added ___________________");
            for (int i = 0; i <= 15; i++) {
                sheet.autoSizeColumn(i);
            }
        }

        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    /** Converting date into String.**/
    private String getConvertTimeStampToStr(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;
        try {
            dt = format.parse(str);
        } catch (ParseException e) {
            System.out.println("Exception Caught=" + e.getStackTrace());
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-YYY");
        return newFormat.format(dt);
    }

    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        BrsListingAppAMImpl am = (BrsListingAppAMImpl) resolvElDC("BrsListingAppAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            System.out.println(st.getObject(1));
            return st.getObject(1);

        } catch (SQLException e) {
            e.printStackTrace();
            int end = e.getMessage().indexOf("\n");
            System.out.println("e.getMessage() = " + e.getMessage());
            String message = e.getMessage().substring(11, end);

            FacesMessage msg = new FacesMessage(message);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e);
                }
            }
        }
    }

    public Object fyDateCheck(Object dt) {
        String CldId = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

        String OrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Object allow = callStoredFunction2(Types.VARCHAR, "APP.FN_GET_FY_STAT_FOR_TXN(?,?,?)", new Object[] {
                                           CldId, OrgId, dt
        });
        System.out.println("allow = " + allow);
        return allow;
    }

    public void InstrumentDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            //  Date dt =  object;
            Object check = fyDateCheck(object);
            if (check.equals("Y")) {
                String msg = (String) resolvEl("#{bundle['MSG.2623']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null));
                                                              //ransaction is not allowed for this date.

            }
        }
    }


    public void bankNameValueChangeListener(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //below one line code for Process refresh
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            BindingContainer Bc = getBindings();
            OperationBinding OP = Bc.getOperationBinding("addBookBalance");
            OP.execute();
            Difference = (Number) OP.getResult();
            AdfFacesContext.getCurrentInstance().addPartialTarget(bankStatusTableBind);
        }
    }

    public void setBankStatusTableBind(RichTable bankStatusTableBind) {
        this.bankStatusTableBind = bankStatusTableBind;
    }

    public RichTable getBankStatusTableBind() {
        return bankStatusTableBind;
    }

    /*  public String TotalAddMethod() {
        System.out.println("TotalAddMethodbefore contner==");
        BindingContainer Bc = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding OP = Bc.getOperationBinding("addBookBalance");
        System.out.println("TotalAddMethod after contner==");
        OP.execute();
        System.out.println("OP.getResult()==" + OP.getResult());
        Number var = (Number) OP.getResult();
        String.valueOf(var);
        this.getResultBindingBean().setValue(var);
        return null;
    } */

    public void setResultBindingBean(RichInputText resultBindingBean) {
        this.resultBindingBean = resultBindingBean;
    }

    public RichInputText getResultBindingBean() {
        return resultBindingBean;
    }

    public void setBankInforFormBind(RichPanelFormLayout bankInforFormBind) {
        this.bankInforFormBind = bankInforFormBind;
    }

    public RichPanelFormLayout getBankInforFormBind() {
        return bankInforFormBind;
    }

    public void setWarningPopup(RichPopup warningPopup) {
        this.warningPopup = warningPopup;
    }

    public RichPopup getWarningPopup() {
        return warningPopup;
    }

    public void warningPopUpDialogListener(DialogEvent dialogEvent) {
      if (dialogEvent.getOutcome().name().equals("ok")) {
             if (statusonPopupBind.getValue() != null) {
                if (statusonPopupBind.getValue() != "") {
                    if (instrumentNoOnPopBinding.getValue() != null &&
                        instrumentNoOnPopBinding.getValue().toString().length() > 0) {
                        if (instrumentDateOnPopBinding.getValue() != null &&
                            instrumentDateOnPopBinding.getValue().toString().length() > 0) {
                            Integer status = (Integer) statusonPopupBind.getValue();
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
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(bankStatusTableBind);
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
            OperationBinding op = getBindings().getOperationBinding("refreshBankStatus");
            op.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(bankStatusTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bankInforFormBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(insDetailTableBind);
        } 
    }

    public void warningpopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
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
        AdfFacesContext.getCurrentInstance().addPartialTarget(insDetailTableBind);
    }

    public void setInsDetailTableBind(RichTable insDetailTableBind) {
        this.insDetailTableBind = insDetailTableBind;
    }

    public RichTable getInsDetailTableBind() {
        return insDetailTableBind;
    }
}


