package mmgatepass.view.bean;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AddEditGatePassBean {


    private String mode = resolvEl("#{pageFlowScope.Param_Page_Mode}").toString();
    private RichSelectOneChoice whBinding;
    private RichSelectOneChoice docTypeBinding;
    private RichInputListOfValues docSrcBinding;
    private RichInputListOfValues addBinding;
    private RichInputText addressBinding;
    private RichSelectBooleanCheckbox dispatchCBBinding;
    private RichInputText recvQtyBinding;
    private RichInputText rcvQtyrcptBinding;
    private RichInputText srNoBinding;
    private RichPopup newSrPop;
    private Number zero = new Number(0);
    private RichPopup stkDetailPop;
    private RichTable rcptTblBind;
    private RichInputText cancelGpBinding;
    private RichButton cancelBtnGpBinding;
    private RichPanelLabelAndMessage srcNoBind;

    public AddEditGatePassBean() {
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void createButtonAL(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("CreateInsert");
        op.execute();
        mode = "C";
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addItemsAL(ActionEvent actionEvent) {
        if (whBinding.getValue() != null && !(whBinding.getValue().toString().equals(""))) {
            if (docTypeBinding.getValue() != null && !(docTypeBinding.getValue().toString().equals(""))) {
                if (docSrcBinding.getValue() != null && !(docSrcBinding.getValue().toString().equals(""))) {
                    //check for duplicate docNo
                    //  System.out.println("Check for dupli");
                    OperationBinding chk = getBindings().getOperationBinding("ChkDupli");
                    chk.execute();
                    String dupli = "N";
                    if (chk.getErrors().size() == 0) {
                        dupli = chk.getResult().toString();
                        if (dupli.equals("N")) {
                            System.out.println("Not duplicate. Address id=" + addBinding.getValue() + " and desc=" +
                                               addressBinding.getValue());
                            //   if (addBinding.getValue() != null && !(addBinding.getValue().toString().equals(""))) {
                            //   System.out.println("generating fy id");
                            OperationBinding genFy = getBindings().getOperationBinding("GenerateFyId");
                            genFy.execute();
                            if (genFy.getErrors().size() != 0)
                                System.out.println("Error on fy is=" + genFy.getErrors());
                            OperationBinding gpNo = getBindings().getOperationBinding("generateGPNo");
                            gpNo.execute();
                            if (gpNo.getErrors().size() != 0)
                                System.out.println("Error on Gpno is=" + gpNo.getErrors());
                            OperationBinding additm = getBindings().getOperationBinding("AddItems");
                            additm.execute();
                            if (additm.getErrors().size() != 0) {
                                System.out.println("Error on add item is=" + additm.getErrors());
                            } else {
                                // mode="A";
                            }
                            /*  } else {
                                //null msg for address
                                System.out.println("address is null");
                                //  String msg="Supplier Address required.";
                                String msg = resolvElDCMsg("#{bundle['MSG.2816']}").toString();
                                FacesMessage message = new FacesMessage(msg);
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            } */ //commented 10/20/15
                        } else {
                            //  String msg="Document already exist in Gatepass.";
                            String msg = resolvElDCMsg("#{bundle['MSG.2817']}").toString();
                            FacesMessage message = new FacesMessage(msg);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(docSrcBinding.getClientId(), message);
                        }

                    } else {
                        //msg for duplicate doc no
                        //  String msg="Document No. is not Valid";
                        String msg = resolvElDCMsg("#{bundle['MSG.807']}").toString();
                        FacesMessage message = new FacesMessage("Document No. is not Valid");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(docSrcBinding.getClientId(), message);
                    }
                } else {
                    //doc src null
                    //  String msg="Source Document No. required.";
                    String msg = resolvElDCMsg("#{bundle['MSG.808']}").toString();
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(docSrcBinding.getClientId(), message);
                }
            } else {
                //null doc type
                //  String msg="Document Type required.";
                String msg = resolvElDCMsg("#{bundle['MSG.809']}").toString();
                FacesMessage message = new FacesMessage("Document Type required.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(docTypeBinding.getClientId(), message);
            }
        } else {
            //msg for null wh
            //  String msg="Warehouse required.";
            String msg = resolvElDCMsg("#{bundle['MSG.713']}").toString();
            FacesMessage message = new FacesMessage("Warehouse required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(whBinding.getClientId(), message);
        }
    }

    public void editGPAL(ActionEvent actionEvent) {
        if (dispatchCBBinding.getValue().toString().equals("true"))
            mode = "ED";
        else
            mode = "E";
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void saveGPAL(ActionEvent actionEvent) {
        rcvQtyrcptBinding.setReadOnly(true);
        OperationBinding chkOutst = getBindings().getOperationBinding("ChkAndUpdtOutStatDt");
        chkOutst.execute();
        OperationBinding chkInst = getBindings().getOperationBinding("ChkInStat");
        chkInst.execute();
        String chk = "N";
        if (chkInst.getErrors().size() == 0 && chkInst.getResult() != null)
            chk = (String) chkInst.getResult();
        if (chk.equals("N")) {
            OperationBinding upRetQty = getBindings().getOperationBinding("updateInStat");
            upRetQty.execute();
        }
        OperationBinding op = getBindings().getOperationBinding("Commit");
        op.execute();
        mode = "V";
        //  String msg="Saved Successfully";
        String msg = resolvElDCMsg("#{bundle['MSG.227']}").toString();
        FacesMessage message = new FacesMessage(msg);
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);

    }

    public void cancelAL(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("Rollback");
        op.execute();

    }

    public void setWhBinding(RichSelectOneChoice whBinding) {
        this.whBinding = whBinding;
    }

    public RichSelectOneChoice getWhBinding() {
        return whBinding;
    }

    public void setDocTypeBinding(RichSelectOneChoice docTypeBinding) {
        this.docTypeBinding = docTypeBinding;
    }

    public RichSelectOneChoice getDocTypeBinding() {
        return docTypeBinding;
    }

    public void setDocSrcBinding(RichInputListOfValues docSrcBinding) {
        this.docSrcBinding = docSrcBinding;
    }

    public RichInputListOfValues getDocSrcBinding() {
        return docSrcBinding;
    }

    public void setAddBinding(RichInputListOfValues addBinding) {
        this.addBinding = addBinding;
    }

    public RichInputListOfValues getAddBinding() {
        return addBinding;
    }

    public void setAddressBinding(RichInputText addressBinding) {
        this.addressBinding = addressBinding;
    }

    public RichInputText getAddressBinding() {
        return addressBinding;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void retQtyValidator(ValueChangeEvent valueChangeEvent) {
        /* if(valueChangeEvent.getNewValue()!=null && (!valueChangeEvent.getNewValue().equals("")) )
    {
        Number ret = (Number)valueChangeEvent.getNewValue();
        if(ret.compareTo(new Number(0))>=0)
        {
        OperationBinding chk=getBindings().getOperationBinding("CheckQty");
        chk.getParamsMap().put("qty", ret);
        chk.execute();
        String ch="N";
        if(chk.getErrors().size()==0)
            chk.getResult();
        ch=chk.getResult().toString();
        if(ch.equals("Y"))
        {
        OperationBinding op=getBindings().getOperationBinding("updateTotRetQty");
        op.getParamsMap().put("qty", ret);
        op.execute();
        }
        else
        {
                FacesMessage message = new FacesMessage("Invalid Quantity.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(whBinding.getClientId(), message);
            }
        }
        else
        {
                FacesMessage message = new FacesMessage("Quantity must be greater than zero.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(whBinding.getClientId(), message);
            }
    } */
    }

    public void setDispatchCBBinding(RichSelectBooleanCheckbox dispatchCBBinding) {
        this.dispatchCBBinding = dispatchCBBinding;
    }

    public RichSelectBooleanCheckbox getDispatchCBBinding() {
        return dispatchCBBinding;
    }

    public void docSrcVCL(ValueChangeEvent valueChangeEvent) {
        //remove history if exist
        OperationBinding removeRcpt = getBindings().getOperationBinding("RemoveItmRcptRows");
        removeRcpt.execute();
        //remove rows from Item if Exist
        OperationBinding remove = getBindings().getOperationBinding("RemoveItmRows");
        remove.execute();
        //clear row of header.
        OperationBinding clear = getBindings().getOperationBinding("ClearHeader");
        clear.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(srcNoBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docSrcBinding);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(srcNoBind);
        docSrcBinding.setValue(null);
        addressBinding.setValue(null);
        addBinding.setValue(null);
        mode = "C";
    }

    public void docNoVCL(ValueChangeEvent valueChangeEvent) {


        //remove history if exist
        OperationBinding removeRcpt = getBindings().getOperationBinding("RemoveItmRcptRows");
        removeRcpt.execute();
        //remove rows from Item if Exist
        OperationBinding remove = getBindings().getOperationBinding("RemoveItmRows");
        remove.execute();
        mode = "C";

    }

    public void warehouseVCL(ValueChangeEvent valueChangeEvent) {
        //remove history & stk if exist
        OperationBinding removeRcpt = getBindings().getOperationBinding("RemoveItmRcptRows");
        removeRcpt.execute();
        //remove rows from Item if Exist
        OperationBinding remove = getBindings().getOperationBinding("RemoveItmRows");
        remove.execute();
        //clear row of header.
        OperationBinding clear = getBindings().getOperationBinding("ClearHeader");
        clear.execute();
        docSrcBinding.setValue(null);
        addressBinding.setValue(null);
        docTypeBinding.setValue(null);
        addBinding.setValue(null);
        mode = "C";
    }

    public void retQtyVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void enterRcvQty(ClientEvent clientEvent) {

        if (recvQtyBinding.getValue() != null && (!recvQtyBinding.getValue().equals(""))) {
            Number ret = (Number) recvQtyBinding.getValue();
            if (ret.compareTo(new Number(0)) >= 0) {
                OperationBinding chk = getBindings().getOperationBinding("CheckQty");
                chk.getParamsMap().put("qty", ret);
                chk.execute();
                String ch = "N";
                if (chk.getErrors().size() == 0)
                    chk.getResult();
                ch = chk.getResult().toString();
                if (ch.equals("Y")) {
                    OperationBinding op = getBindings().getOperationBinding("updateTotRetQty");
                    op.getParamsMap().put("qty", ret);
                    op.execute();
                    recvQtyBinding.setValue(null);
                } else {
                    //  String msg="Invalid Quantity.";
                    String msg = resolvElDCMsg("#{bundle['MSG.336']}").toString();
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(recvQtyBinding.getClientId(), message);
                }
            } else {
                //  String msg="Quantity must be greater than zero.";
                String msg = resolvElDCMsg("#{bundle['MSG.2509']}").toString();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(recvQtyBinding.getClientId(), message);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTblBind);
    }

    public void setRecvQtyBinding(RichInputText recvQtyBinding) {
        this.recvQtyBinding = recvQtyBinding;
    }

    public RichInputText getRecvQtyBinding() {
        return recvQtyBinding;
    }

    public void delItmRcptAL(ActionEvent actionEvent) {
        OperationBinding del = getBindings().getOperationBinding("DelItmRecpt");
        del.execute();
    }


    public void rcptQtyVCL(ValueChangeEvent valueChangeEvent) {
        Number zero = new Number(0);
        Number oldQty = zero;
        Number newQty = zero;
        Number diff = zero;
        if (valueChangeEvent.getNewValue() != null) {
            newQty = (Number) valueChangeEvent.getNewValue();
            if (newQty.compareTo(zero) > 0) {
                if (valueChangeEvent.getOldValue() != null)
                    oldQty = (Number) valueChangeEvent.getOldValue();
                newQty = (Number) valueChangeEvent.getNewValue();
                diff = newQty.subtract(oldQty);
                OperationBinding updt = getBindings().getOperationBinding("updateQty");
                updt.getParamsMap().put("diff", diff);
                updt.execute();
            } else {
                //  String msg="Quantity must be greater than zero.";
                String msg = resolvElDCMsg("#{bundle['MSG.2509']}").toString();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(rcvQtyrcptBinding.getClientId(), message);
            }
        } else {
            //  String msg="Quantity can not be null.";
            String msg = resolvElDCMsg("#{bundle['MSG.811']}").toString();
            FacesMessage message = new FacesMessage("Quantity can not be null.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(rcvQtyrcptBinding.getClientId(), message);
        }

    }

    public void setRcvQtyrcptBinding(RichInputText rcvQtyrcptBinding) {
        this.rcvQtyrcptBinding = rcvQtyrcptBinding;
    }

    public RichInputText getRcvQtyrcptBinding() {
        return rcvQtyrcptBinding;
    }

    public void SerialNoEnteredListener(ClientEvent clientEvent) {
        if (srNoBinding.getValue() != null) {
            //check quantity
            OperationBinding chk = getBindings().getOperationBinding("CheckQty");
            chk.getParamsMap().put("qty", new Number(1));
            chk.execute();
            String ch = "N";
            if (chk.getErrors().size() == 0)
                chk.getResult();
            ch = chk.getResult().toString();
            if (ch.equals("Y")) {
                //chk duplicate srno in stk
                OperationBinding chkdupliSr = getBindings().getOperationBinding("ChkDupliSrno");
                chkdupliSr.getParamsMap().put("srno", srNoBinding.getValue().toString());
                chkdupliSr.execute();
                String chkdupl = "N";
                if (chkdupliSr.getErrors().size() == 0)
                    chkdupl = (String) chkdupliSr.getResult();
                if (chkdupl.equals("N")) {
                    //is this a new SrNo or Old
                    OperationBinding chkNew = getBindings().getOperationBinding("ChkIfNewSrno");
                    chkNew.getParamsMap().put("srno", srNoBinding.getValue().toString());
                    chkNew.execute();
                    String chkifnew = "N";
                    if (chkNew.getErrors().size() == 0)
                        chkifnew = (String) chkNew.getResult();
                    if (chkifnew.equals("Y")) {
                        showPopup(newSrPop, true);
                        srNoBinding.setValue(null);
                    } else {
                        //add one qty to itmrcpt
                        OperationBinding op = getBindings().getOperationBinding("updateTotRetQty");
                        op.getParamsMap().put("qty", new Number(1));
                        op.execute();
                        srNoBinding.setValue(null);
                    }
                } else {
                    //  String msg="Duplicate Value for this Serial No.";
                    String msg = resolvElDCMsg("#{bundle['MSG.812']}").toString();
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(srNoBinding.getClientId(), message);
                }
            } else {
                //  String msg="Invalid Quantity.";
                String msg = resolvElDCMsg("#{bundle['MSG.336']}").toString();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(srNoBinding.getClientId(), message);
            }
        } else {
            //  String msg="Invalid Quantity.";
            String msg = resolvElDCMsg("#{bundle['MSG.2818']}").toString();
            FacesMessage message = new FacesMessage("Either Select or Enter New Serial No.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(srNoBinding.getClientId(), message);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTblBind);
    }

    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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

    public void setSrNoBinding(RichInputText srNoBinding) {
        this.srNoBinding = srNoBinding;
    }

    public RichInputText getSrNoBinding() {
        return srNoBinding;
    }

    public void addSrNoPopUpDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().toString().equalsIgnoreCase("yes")) {
            //add one qty to itmrcpt
            OperationBinding op = getBindings().getOperationBinding("updateTotRetQty");
            op.getParamsMap().put("qty", new Number(1));
            op.execute();
        }
    }

    public void setNewSrPop(RichPopup newSrPop) {
        this.newSrPop = newSrPop;
    }

    public RichPopup getNewSrPop() {
        return newSrPop;
    }

    public void setZero(Number zero) {
        this.zero = zero;
    }

    public Number getZero() {
        return zero;
    }

    public void editQtyinTabAL(ActionEvent actionEvent) {
        rcvQtyrcptBinding.setReadOnly(false);
    }


    public void rcvQtyValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number qty = (Number) object;
            if (qty.compareTo(new Number(0)) > 0) {
            } else {
                //  String msg="Quantity must be greater than 0.";
                String msg = resolvElDCMsg("#{bundle['MSG.337']}").toString();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(rcvQtyrcptBinding.getClientId(), message);
            }
        } else {
            //  String msg="Quantity can not be null.";
            String msg = resolvElDCMsg("#{bundle['MSG.2845']}").toString();
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(rcvQtyrcptBinding.getClientId(), message);
        }
    }


    public void setStkDetailPop(RichPopup stkDetailPop) {
        this.stkDetailPop = stkDetailPop;
    }

    public RichPopup getStkDetailPop() {
        return stkDetailPop;
    }

    public void viewStkAL(ActionEvent actionEvent) {
        OperationBinding exe = getBindings().getOperationBinding("ExecuteVo");
        exe.execute();
        showPopup(stkDetailPop, true);
    }


    public void setRcptTblBind(RichTable rcptTblBind) {
        this.rcptTblBind = rcptTblBind;
    }

    public RichTable getRcptTblBind() {
        return rcptTblBind;
    }
    private static ClientEvent clientEvent;

    public void InsertRcptAction(ActionEvent actionEvent) {
        enterRcvQty(clientEvent);
    }

    public void cancelGatePassAction(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("cancelGatepassAll");
        obind.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelGpBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelBtnGpBinding);

    }

    public void setCancelGpBinding(RichInputText cancelGpBinding) {
        this.cancelGpBinding = cancelGpBinding;
    }

    public RichInputText getCancelGpBinding() {
        return cancelGpBinding;
    }

    public void setCancelBtnGpBinding(RichButton cancelBtnGpBinding) {
        this.cancelBtnGpBinding = cancelBtnGpBinding;
    }

    public RichButton getCancelBtnGpBinding() {
        return cancelBtnGpBinding;
    }

    public void setSrcNoBind(RichPanelLabelAndMessage srcNoBind) {
        this.srcNoBind = srcNoBind;
    }

    public RichPanelLabelAndMessage getSrcNoBind() {
        return srcNoBind;
    }
}
