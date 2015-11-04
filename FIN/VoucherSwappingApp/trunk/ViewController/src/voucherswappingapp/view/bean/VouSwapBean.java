package voucherswappingapp.view.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class VouSwapBean {
    private RichSelectOneChoice vouTypePgBind;
    private RichInputText vouNoPgBind;
    private RichInputDate fromDtPgBind;
    private RichInputDate toDtPgBind;
    private RichInputListOfValues cogIdPgBind;
    private RichInputText coaIdPgBind;
    private RichInputText toCoaIdBind;
    private RichTable dtlPgBind;
    private RichSelectBooleanCheckbox chkBxBind;
    private RichInputListOfValues coaNmPgBind;
    private RichPanelFormLayout searchPanelBind;
    private RichPanelFormLayout toCoaFrmBind;

    public VouSwapBean() {
    }

    /** For Getting Binding Container for calling Client Interface method. **/

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void searchAction(ActionEvent actionEvent) {
        Object coaObj = coaNmPgBind.getValue();
        Object vouTypeObj = vouTypePgBind.getValue();

        if (coaObj == null || coaObj.toString().length() <= 0 || vouTypeObj == null ||
            vouTypeObj.toString().length() <= 0) {
            System.out.println("Chart Of Account/Voucher Type is Required.");
            FacesMessage fm = new FacesMessage("Chart Of Account and Voucher Type must be selected.");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        } else {
            System.out.println("--------search in bean-------");
            OperationBinding binding = getBindings().getOperationBinding("searchAcion");
            binding.getParamsMap().put("vouType", vouTypePgBind.getValue());
            binding.getParamsMap().put("VouId", vouNoPgBind.getValue());
            binding.getParamsMap().put("frmDt", fromDtPgBind.getValue());
            binding.getParamsMap().put("toDate", toDtPgBind.getValue());
            binding.getParamsMap().put("coaId", coaIdPgBind.getValue());

            //    System.out.println("coa ID++++++++"+coaNmPgBind.getValue());

            // binding.getParamsMap().put("cogId", cogIdPgBind.getValue());
            //  System.out.println("cog ID++++++++"+cogIdPgBind.getValue());

            binding.execute();
            //        coaNmPgBind.setDisabled(Boolean.TRUE);
        }
    }

    public void setVouTypePgBind(RichSelectOneChoice vouTypePgBind) {
        this.vouTypePgBind = vouTypePgBind;
    }

    public RichSelectOneChoice getVouTypePgBind() {
        return vouTypePgBind;
    }

    public void setVouNoPgBind(RichInputText vouNoPgBind) {
        this.vouNoPgBind = vouNoPgBind;
    }

    public RichInputText getVouNoPgBind() {
        return vouNoPgBind;
    }

    public void setFromDtPgBind(RichInputDate fromDtPgBind) {
        this.fromDtPgBind = fromDtPgBind;
    }

    public RichInputDate getFromDtPgBind() {
        return fromDtPgBind;
    }

    public void setToDtPgBind(RichInputDate toDtPgBind) {
        this.toDtPgBind = toDtPgBind;
    }

    public RichInputDate getToDtPgBind() {
        return toDtPgBind;
    }


    public void setCogIdPgBind(RichInputListOfValues cogIdPgBind) {
        this.cogIdPgBind = cogIdPgBind;
    }

    public RichInputListOfValues getCogIdPgBind() {
        return cogIdPgBind;
    }

    public void setCoaIdPgBind(RichInputText coaIdPgBind) {
        this.coaIdPgBind = coaIdPgBind;
    }

    public RichInputText getCoaIdPgBind() {
        return coaIdPgBind;
    }

    public void setToCoaIdBind(RichInputText toCoaIdBind) {
        this.toCoaIdBind = toCoaIdBind;
    }

    public RichInputText getToCoaIdBind() {
        return toCoaIdBind;
    }

    /** For selectAllVou Voucher called from AM **/
    public void selectAllVou(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("selectAllVou");
        binding.execute();
    }

    /** For deselectAllVou Voucher called from AM **/
    public void deselectAllVou(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("deselectAllVoucher");
        binding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlPgBind);

        chkBxBind.setValue("N");
        AdfFacesContext.getCurrentInstance().addPartialTarget(chkBxBind);

    }

    public void setDtlPgBind(RichTable dtlPgBind) {
        this.dtlPgBind = dtlPgBind;
    }

    public RichTable getDtlPgBind() {
        return dtlPgBind;
    }

    public void setChkBxBind(RichSelectBooleanCheckbox chkBxBind) {
        this.chkBxBind = chkBxBind;
    }

    public RichSelectBooleanCheckbox getChkBxBind() {
        return chkBxBind;
    }

    public void swapAccountAction(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("swapVoucher");
        binding.execute();
        System.out.println("swapVoucher");
    }

    public void resetAction(ActionEvent actionEvent) {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPanelBind); //resetting search panel.
        resetValueInputItems(fctx, toCoaFrmBind); //resetting To COA panle.
        OperationBinding binding = getBindings().getOperationBinding("resetAction");
        binding.execute();
        //coaNmPgBind.setDisabled(Boolean.FALSE);
    }

    /** For resetting all UI Componenet by getting UI Comp Id. **/
    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }


    public void setCoaNmPgBind(RichInputListOfValues coaNmPgBind) {
        this.coaNmPgBind = coaNmPgBind;
    }

    public RichInputListOfValues getCoaNmPgBind() {
        return coaNmPgBind;
    }

    public void setSearchPanelBind(RichPanelFormLayout searchPanelBind) {
        this.searchPanelBind = searchPanelBind;
    }

    public RichPanelFormLayout getSearchPanelBind() {
        return searchPanelBind;
    }

    /* public void setCoaIdVCE(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getNewValue();
        if(valueChangeEvent.getNewValue()!=null){
            OperationBinding binding = getBindings().getOperationBinding("setCoaName");
            binding.getParamsMap().put("CoaNm", valueChangeEvent.getNewValue().toString());
            binding.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaIdPgBind);
    } */

    public void setToCoaFrmBind(RichPanelFormLayout toCoaFrmBind) {
        this.toCoaFrmBind = toCoaFrmBind;
    }

    public RichPanelFormLayout getToCoaFrmBind() {
        return toCoaFrmBind;
    }
}
