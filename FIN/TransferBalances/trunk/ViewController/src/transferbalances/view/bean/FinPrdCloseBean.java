package transferbalances.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class FinPrdCloseBean {
    private RichPopup popupBind;
    private RichInputDate currentDateBinding;
    private RichInputDate endDateBinding;
    private RichSelectOneChoice transferTypeBinding;
    private RichPanelFormLayout alertFormBinding;
    private RichInputText bindCount1;
    private RichInputText bindCount2;
    private RichInputText bindCount3;
    private RichPopup teansferBalPopUpBind;
    private RichPanelBox nextYrpanelBoxBind;

    public FinPrdCloseBean() {
    }

    private String visible = "N";
    private String mode = "V";

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getVisible() {
        return visible;
    }


    public String okOnPopup() {
        // Add event code here...
        return "trf";
    }

    public void setPopupBind(RichPopup popupBind) {
        this.popupBind = popupBind;
    }

    public RichPopup getPopupBind() {
        return popupBind;
    }

    public String TransferBalanceCheck() {

        System.out.println("transferTypeBinding.getValue() = " + transferTypeBinding.getValue());

        if (transferTypeBinding.getValue() != null && !transferTypeBinding.getValue().equals("")) {
            System.out.println("valu of closing type is==" + transferTypeBinding.getValue());
            System.out.println("valure on template is=" + bindCount1.getValue());
            System.out.println("value in batch is=" + bindCount2.getValue());
            System.out.println("value on pdc clearing is=" + bindCount3.getValue());
            if ((Integer.parseInt(bindCount2.getValue().toString()) != 0 ||
                 Integer.parseInt(bindCount3.getValue().toString()) != 0) &&
                transferTypeBinding.getValue().equals("F")) {
                System.out.println("voucher pending in batch and PDC clearing");
                ADFModelUtils.showFacesMessage("Vouchers are pending for current financial year in Batch Payment or PDC Clearing.So you can not close the current financial year",
                                               null, FacesMessage.SEVERITY_ERROR, null);


            } else if (Integer.parseInt(bindCount1.getValue().toString()) != 0 &&
                       transferTypeBinding.getValue().equals("F")) {
                System.out.println("voucher pending in interim voucher");
                showPopup(teansferBalPopUpBind, true);
            } else {
                closeFYCCheck();
            }
        } else {
            ADFModelUtils.showFacesMessage("Select Atleast one Closing Type", null, FacesMessage.SEVERITY_ERROR, null);
        }

        mode = "V";
        return null;
    }

    public String CloseFinancialYear() {
        // Add event code here...
        //closeFinancialYear
        OperationBinding op = ADFBeanUtils.getOperationBinding("closeFinancialYear");
        op.execute();
        Object res = op.getResult();
        if (res != null) {
            if (res.equals("Y")) {
                ADFModelUtils.showFacesMessage("Transaction Executed Successfully", null, FacesMessage.SEVERITY_INFO,
                                               null);

            }
        }
        visible = "N";
        mode = "A";
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindCount1);
        return null;
    }


    public void closingTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            System.out.println("endDateBinding =" + endDateBinding + " currentDateBinding = " +
                               currentDateBinding.getValue());

            if (endDateBinding.getValue() != null && currentDateBinding.getValue() != null) {

                if (currentDateBinding.getValue().toString().compareTo(endDateBinding.getValue().toString()) < 0 &&
                    object.equals("F")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Cannot perform operation  before last date of Financial year!",
                                                                  null));
                } else if (object.equals("V") || object.equals("T")) {
                    BindingContainer bc = ADFBeanUtils.getBindingContainer();
                    JUCtrlListBinding vouTypeList = (JUCtrlListBinding) bc.get("LovVouTypeVO1");
                    Row[] allRowsInRange = vouTypeList.getAllRowsInRange();
                    System.out.println("allRowsInRange = " + allRowsInRange.length);
                    if (allRowsInRange.length == 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "You can only perform Full Closing as All type of vouchers are already closed in Correponding Financial year!",
                                                                      null));

                    }
                }

            }
        }

    }

    public void setCurrentDateBinding(RichInputDate currentDateBinding) {
        this.currentDateBinding = currentDateBinding;
    }

    public RichInputDate getCurrentDateBinding() {
        return currentDateBinding;
    }

    public void setEndDateBinding(RichInputDate endDateBinding) {
        this.endDateBinding = endDateBinding;
    }

    public RichInputDate getEndDateBinding() {
        return endDateBinding;
    }


    public void setTransferTypeBinding(RichSelectOneChoice transferTypeBinding) {
        this.transferTypeBinding = transferTypeBinding;
    }

    public RichSelectOneChoice getTransferTypeBinding() {
        return transferTypeBinding;
    }


    public void setAlertFormBinding(RichPanelFormLayout alertFormBinding) {
        this.alertFormBinding = alertFormBinding;
    }

    public RichPanelFormLayout getAlertFormBinding() {
        return alertFormBinding;
    }

    public void setBindCount1(RichInputText bindCount1) {
        this.bindCount1 = bindCount1;
    }

    public RichInputText getBindCount1() {
        return bindCount1;
    }

    public void setBindCount2(RichInputText bindCount2) {
        this.bindCount2 = bindCount2;
    }

    public RichInputText getBindCount2() {
        return bindCount2;
    }

    public void setBindCount3(RichInputText bindCount3) {
        this.bindCount3 = bindCount3;
    }

    public RichInputText getBindCount3() {
        return bindCount3;
    }

    public void closeFYCCheck() {
        BindingContainer bc = ADFBeanUtils.getBindingContainer();
        JUCtrlListBinding vouTypeList = (JUCtrlListBinding) bc.get("LovVouTypeVO1");
        Object[] al = vouTypeList.getSelectedValues();
        System.out.println("in lese conditoin iuss-==" + transferTypeBinding.getValue());
        if ((transferTypeBinding.getValue().equals("V") && al.length > 0) ||
            (!transferTypeBinding.getValue().equals("V"))) {
            OperationBinding op = ADFBeanUtils.getOperationBinding("preFinancialYearClosingCheck");
            op.execute();
            Object res = op.getResult();
            System.out.println("result = " + res);
            if (res != null) {
                String result = (String) res;
                //   String result ="transfer" ;

                if (result.equalsIgnoreCase("no")) {
                    String msg = (String) ADFModelUtils.resolvEl("#{bundle['MSG.377']}");
                    ADFModelUtils.showFacesMessage(msg, null, FacesMessage.SEVERITY_ERROR, null);
                } else if (result.equalsIgnoreCase("generate&close")) {
                    /*  ADFModelUtils.showFacesMessage(" Now you can close financial year.",
                                                   null, FacesMessage.SEVERITY_INFO, null);
                    */setVisible("Y");
                } else if (result.equalsIgnoreCase("close")) {
                    ADFModelUtils.showFacesMessage("Net Balance is 0. Now you can close financial year", null,
                                                   FacesMessage.SEVERITY_INFO, null);
                    setVisible("Y");
                } else {
                    //transfer
                    //  ADFBeanUtils.showPopup(popupBind, true);
                    ADFModelUtils.showFacesMessage("Multiple Reserved Account Exists!", null,
                                                   FacesMessage.SEVERITY_ERROR, null);
                }
            }
        } else {
            ADFModelUtils.showFacesMessage("Select Atleast one Voucher for Voucher Type Closing", null,
                                           FacesMessage.SEVERITY_ERROR, null);

        }
    }

    public void setTeansferBalPopUpBind(RichPopup teansferBalPopUpBind) {
        this.teansferBalPopUpBind = teansferBalPopUpBind;
    }

    public RichPopup getTeansferBalPopUpBind() {
        return teansferBalPopUpBind;
    }

    public void fycConfrmDailoglistener(DialogEvent dialogEvent) {
        closeFYCCheck();
        AdfFacesContext.getCurrentInstance().addPartialTarget(nextYrpanelBoxBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transferTypeBinding);
    }

    public void fycPopCancelListener(PopupCanceledEvent popupCanceledEvent) {
        System.out.println("in cancel listeener of dialog");
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

    public void setNextYrpanelBoxBind(RichPanelBox nextYrpanelBoxBind) {
        this.nextYrpanelBoxBind = nextYrpanelBoxBind;
    }

    public RichPanelBox getNextYrpanelBoxBind() {
        return nextYrpanelBoxBind;
    }
}
