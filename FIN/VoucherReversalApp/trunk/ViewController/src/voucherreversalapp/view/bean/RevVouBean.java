/** @Author Sudhanshu Raj. **/

package voucherreversalapp.view.bean;

import com.sun.corba.se.spi.protocol.RetryType;

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

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;


public class RevVouBean {
    private String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
 //   private String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
    private Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
    private RichInputDate fromDtPgBind;
    private RichInputDate toDtPgBind;
    private RichInputText vouNoPgBind;
    private RichInputListOfValues coaNmPgBind;
    private RichSelectOneChoice vouTypePgBind;
    private RichInputText coaIdPgBind;
    private RichInputText cogIdPgBind;
    private RichInputListOfValues cogNamePgBind;
    private RichInputText glVouIdBind;
    private RichSelectBooleanCheckbox chkBxBind;
    private RichTable dtlPgBind;


    public RevVouBean() {
    }

    /** For Getting Binding Container for calling Client Interface method. **/

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /** For Search Action **/
    public void searchVoucher(ActionEvent actionEvent) {
        System.out.println("--------search in bean-------");
        OperationBinding binding = getBindings().getOperationBinding("searchAcion");
        binding.getParamsMap().put("vouType", vouTypePgBind.getValue());
        binding.getParamsMap().put("VouId", vouNoPgBind.getValue());
        binding.getParamsMap().put("frmDt", fromDtPgBind.getValue());
        binding.getParamsMap().put("toDate", toDtPgBind.getValue());
        binding.getParamsMap().put("coaId", coaIdPgBind.getValue());
        //    System.out.println("coa ID++++++++"+coaNmPgBind.getValue());

        binding.getParamsMap().put("cogId", cogIdPgBind.getValue());
        //  System.out.println("cog ID++++++++"+cogIdPgBind.getValue());

        binding.execute();
    }

    /** For Reset Action **/
    public void resetAction(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("resetAction");
        binding.execute();
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

    /** For Reverse Voucher called from AM **/
    public void reverseVoucher(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("reverseVoucher");
        binding.execute();
        System.out.println("reverseVoucher");

    }

    /** Calling validate function from AM. **/
    public void revVouValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Object vouIdAttr = uIComponent.getAttributes().get("vouIdAttr");
                if (object.toString().equalsIgnoreCase("true")) {
                System.out.println("Object Value..............." + object.toString());
                //   validateVoucherFunc
                OperationBinding op = getBindings().getOperationBinding("checkVoucherDate");
                op.execute();
                System.out.println("reult retun by the fuunvtion is="+op.getResult());
            if(op.getResult()!=null) {
                if(op.getResult().toString().equalsIgnoreCase("Y")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Voucher reversal is not allowed.Either financial year is not defined or Financial Year is ended for this voucher for reversal. "));
                }
            }
                
                OperationBinding binding = getBindings().getOperationBinding("validateVoucherFunc");
                binding.getParamsMap().put("vouIdAttr", vouIdAttr);
                binding.execute();
                

                System.out.println("binding result---------------" + binding.getResult());
                String retParamVal = binding.getResult().toString();
                System.out.println("Params in Bean_______________" + retParamVal);

                if (retParamVal != null) {
                    if (retParamVal.toString().equalsIgnoreCase("Y")) {
                        System.out.println("Voucher is avialable for Reversal...!");

                    } else if (retParamVal.equalsIgnoreCase("NA")) {
                        chkBxBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(chkBxBind);
                        String errMsg = "Can't Reverse...! AS IT IS A VOUCHER REFERENCED/ADJUSTED BY ANOTHER VOUCHER.";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));
                        
                    } else if (retParamVal.equalsIgnoreCase("NR")) {
                        chkBxBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(chkBxBind);
                        String errMsg = "Can't Reverse...! AS IT IS A REVERSED VOUCHER.";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));
                        
                    } else if (retParamVal.equalsIgnoreCase("NS")) {
                        chkBxBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(chkBxBind);
                        String errMsg = "Can't Reverse...! AS IT IS A VOUCHER GENERATED FROM ANOTHER voucher.";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));
                        
                    } else if (retParamVal.equalsIgnoreCase("NO")) {
                        chkBxBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(chkBxBind);
                        String errMsg = "Can't Reverse...! AS THIS VOUCHER HAS NOT BEEN CREATED IN FINANCE";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));

                    } else if (retParamVal.equalsIgnoreCase("N") || retParamVal.equalsIgnoreCase("E")) {
                        chkBxBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(chkBxBind);
                        String errMsg = "Error Occured...!Unable to Reverse the voucher...! Contact ESS...!!!";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));
                        
                    } else if (retParamVal.equalsIgnoreCase("NM")) {
                        chkBxBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(chkBxBind);
                        String errMsg = "Can't Reverse...! AS IT IS A MIGRATED VOUCHER";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));
                        
                    }else if (retParamVal.equalsIgnoreCase("NP")) {
                        chkBxBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(chkBxBind);
                        String errMsg = "Can't Reverse...! AS IT IS AS IT IS A OPENING VOUCHER";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));
                        
                    }else if (retParamVal.equalsIgnoreCase("NPM")) {
                        chkBxBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(chkBxBind);
                        String errMsg = "Can't Reverse...! AS IT IS A MIGRATED OPENING VOUCHER";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errMsg, null));
                        
                    }
                }
            }
        }
    }

    /** Fro Calling Application Module in Bean. **/

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    /** For Calling EL in Bean. **/

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }


    public void setCldId(String cldId) {
        this.cldId = cldId;
    }

    public String getCldId() {
        /** Getting page flow value from Task Flow. **/
        if (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}") != null) {
            return resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        } else
            return cldId;
    }

    /* public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgId() {
        if (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}") != null) {
            return resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        } else
            return orgId;
    } */

    public void setSlocId(Integer slocId) {
        this.slocId = slocId;
    }

    public Integer getSlocId() {
        /** Getting page flow value from Task Flow. **/
        if (resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}") != null) {
            return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        } else
            return slocId;
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

    public void setVouNoPgBind(RichInputText vouNoPgBind) {
        this.vouNoPgBind = vouNoPgBind;
    }

    public RichInputText getVouNoPgBind() {
        return vouNoPgBind;
    }

    public void setCoaNmPgBind(RichInputListOfValues coaNmPgBind) {
        this.coaNmPgBind = coaNmPgBind;
    }

    public RichInputListOfValues getCoaNmPgBind() {
        return coaNmPgBind;
    }

    public void setVouTypePgBind(RichSelectOneChoice vouTypePgBind) {
        this.vouTypePgBind = vouTypePgBind;
    }

    public RichSelectOneChoice getVouTypePgBind() {
        return vouTypePgBind;
    }

    public void setCoaIdPgBind(RichInputText coaIdPgBind) {
        this.coaIdPgBind = coaIdPgBind;
    }

    public RichInputText getCoaIdPgBind() {
        return coaIdPgBind;
    }

    public void setCogIdPgBind(RichInputText cogIdPgBind) {
        this.cogIdPgBind = cogIdPgBind;
    }

    public RichInputText getCogIdPgBind() {
        return cogIdPgBind;
    }

    public void setCogNamePgBind(RichInputListOfValues cogNamePgBind) {
        this.cogNamePgBind = cogNamePgBind;
    }

    public RichInputListOfValues getCogNamePgBind() {
        return cogNamePgBind;
    }


    public void setGlVouIdBind(RichInputText glVouIdBind) {
        this.glVouIdBind = glVouIdBind;
    }

    public RichInputText getGlVouIdBind() {
        return glVouIdBind;
    }


    public void setChkBxBind(RichSelectBooleanCheckbox chkBxBind) {
        this.chkBxBind = chkBxBind;
    }

    public RichSelectBooleanCheckbox getChkBxBind() {
        return chkBxBind;
    }

    public void setDtlPgBind(RichTable dtlPgBind) {
        this.dtlPgBind = dtlPgBind;
    }

    public RichTable getDtlPgBind() {
        return dtlPgBind;
    }
}
