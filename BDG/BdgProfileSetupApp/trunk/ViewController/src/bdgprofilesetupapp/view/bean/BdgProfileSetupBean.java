package bdgprofilesetupapp.view.bean;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class BdgProfileSetupBean {

    String mode = "V";
    private RichSelectOneChoice bdgTypeBinding;
    private RichSelectOneChoice bdgMthdBinding;
    private RichSelectOneChoice multiUsrAccsBinding;
    private RichSelectOneChoice rvsnMthdBinding;
    private RichSelectOneChoice distMthdBinding;
    private RichSelectOneChoice docIdBinding;
    private RichSelectOneChoice docStructBinding;
    private RichSelectOneChoice docStructDpndBinding;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public BdgProfileSetupBean() {
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public BindingContainer getbinding() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String addProfileAction() {
        Object sloc = resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");
        Object User = resolvEl("#{pageFlowScope.GLBL_APP_USR}");
        Object Cld = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Object Org = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");

        System.out.println("In bean---" + "cld--" + Cld + " Sloc--" + sloc + "  org--" + Org + " User----" + User);
        OperationBinding op = getbinding().getOperationBinding("checkPrf");
        op.getParamsMap().put("sloc", sloc);
        op.getParamsMap().put("Org", Org);
        op.getParamsMap().put("Cld", Cld);
        op.getParamsMap().put("User", User);
        op.execute();
        if (op.getResult() != null && op.getErrors().isEmpty()) {
            Integer result = Integer.parseInt(op.getResult().toString());

            if (result == 1) {
                this.mode = "A";
            }
        }
        return null;
    }

    public String editProfileAction() {
        //getbinding().getOperationBinding("refreshVo").execute();
        this.mode = "E";
        return null;
    }

    public String saveProfileAction() {
        if (bdgTypeBinding.getValue() != null && bdgTypeBinding.getValue().toString().length() > 0) {
            if (bdgMthdBinding.getValue() != null && bdgMthdBinding.getValue().toString().length() > 0) {
                if (multiUsrAccsBinding.getValue() != null && multiUsrAccsBinding.getValue().toString().length() > 0) {
                    if (rvsnMthdBinding.getValue() != null && rvsnMthdBinding.getValue().toString().length() > 0) {
                        if (distMthdBinding.getValue() != null && distMthdBinding.getValue().toString().length() > 0) {
                            OperationBinding op = getbinding().getOperationBinding("Commit");
                            op.execute();
                            FacesMessage message = new FacesMessage("Profile saved successfully.");
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                            this.mode = "V";
                        } else {
                            FacesMessage message = new FacesMessage("Please Select Distribution Method.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(distMthdBinding.getClientId(), message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage("Please Select Revision Method.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(rvsnMthdBinding.getClientId(), message);
                    }
                } else {
                    FacesMessage message = new FacesMessage("Please Select value for Multi User Access.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(multiUsrAccsBinding.getClientId(), message);
                }
            } else {
                FacesMessage message = new FacesMessage("Please Select Budget Method.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(bdgMthdBinding.getClientId(), message);
            }
        } else {
            FacesMessage message = new FacesMessage("Please Select Budget Type.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(bdgTypeBinding.getClientId(), message);
        }
        return null;
    }

    public String cancelProfileAction() {
        OperationBinding op = getbinding().getOperationBinding("Rollback");
        op.execute();
        this.mode = "V";
        return null;
    }

    public void setBdgTypeBinding(RichSelectOneChoice bdgTypeBinding) {
        this.bdgTypeBinding = bdgTypeBinding;
    }

    public RichSelectOneChoice getBdgTypeBinding() {
        return bdgTypeBinding;
    }

    public void setBdgMthdBinding(RichSelectOneChoice bdgMthdBinding) {
        this.bdgMthdBinding = bdgMthdBinding;
    }

    public RichSelectOneChoice getBdgMthdBinding() {
        return bdgMthdBinding;
    }

    public void setMultiUsrAccsBinding(RichSelectOneChoice multiUsrAccsBinding) {
        this.multiUsrAccsBinding = multiUsrAccsBinding;
    }

    public RichSelectOneChoice getMultiUsrAccsBinding() {
        return multiUsrAccsBinding;
    }

    public void setRvsnMthdBinding(RichSelectOneChoice rvsnMthdBinding) {
        this.rvsnMthdBinding = rvsnMthdBinding;
    }

    public RichSelectOneChoice getRvsnMthdBinding() {
        return rvsnMthdBinding;
    }

    public void setDistMthdBinding(RichSelectOneChoice distMthdBinding) {
        this.distMthdBinding = distMthdBinding;
    }

    public RichSelectOneChoice getDistMthdBinding() {
        return distMthdBinding;
    }

    public void docIdVCL(ValueChangeEvent valueChangeEvent) {
        OperationBinding opFilter = getbinding().getOperationBinding("filterDocStructVO");
        opFilter.getParamsMap().put("docId", valueChangeEvent.getNewValue());
        opFilter.execute();
    }

    public String addStructAction() {

        /* OperationBinding opCreate = getbinding().getOperationBinding("CreateInsert");
        opCreate.execute(); */
        OperationBinding opSetAtt = getbinding().getOperationBinding("attributeSetter");
        opSetAtt.execute();
        if (opSetAtt.getErrors().size() == 0 && opSetAtt.getResult() != null) {
            if (opSetAtt.getResult().toString().equals("DocId")) {
                //Please Select Document.
                FacesMessage message = new FacesMessage("Please Select Document.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(docIdBinding.getClientId(), message);
            } else if (opSetAtt.getResult().toString().equals("DocStruct")) {
                //Please Select Structure.
                FacesMessage message = new FacesMessage("Please Select Document.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(docStructBinding.getClientId(), message);
            } else if (opSetAtt.getResult().toString().equals("DupliDocStruct")) {
                //Duplicate DocStruct.
                FacesMessage message = new FacesMessage("Duplicate Structure for Document.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(docStructBinding.getClientId(), message);
            } else if (opSetAtt.getResult().toString().equals("Y")) {
                //Added Successfully.
            } else {
                //something went wrong
                FacesMessage message = new FacesMessage("Something went wrong.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage("Something went wrong.\nError Report:" + opSetAtt.getErrors());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        return null;
    }

    public void structValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*  if (object != null && object.toString().length() > 0) {
            OperationBinding opDupli = getbinding().getOperationBinding("chkDupliStruct");
            opDupli.getParamsMap().put("structId", object);
            opDupli.execute();
            if (opDupli.getErrors() != null || !(opDupli.getResult() != null)) {
                throw new ValidatorException(new FacesMessage("Something went wrong.\nError:" + opDupli.getErrors() +
                                                              " Result:" + opDupli.getResult()));
            } else if (opDupli.getResult().toString().equals("Y")) {
                throw new ValidatorException(new FacesMessage("Duplicate Structure for this Document."));
            }
        } */



    }

    public void setDocIdBinding(RichSelectOneChoice docIdBinding) {
        this.docIdBinding = docIdBinding;
    }

    public RichSelectOneChoice getDocIdBinding() {
        return docIdBinding;
    }

    public void setDocStructBinding(RichSelectOneChoice docStructBinding) {
        this.docStructBinding = docStructBinding;
    }

    public RichSelectOneChoice getDocStructBinding() {
        return docStructBinding;
    }

    public void setDocStructDpndBinding(RichSelectOneChoice docStructDpndBinding) {
        this.docStructDpndBinding = docStructDpndBinding;
    }

    public RichSelectOneChoice getDocStructDpndBinding() {
        return docStructDpndBinding;
    }

    public String deleteStructAction() {
        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding prfIterator = (DCIteratorBinding) dcBindings.get("OrgBdgPrfDocDtlIterator");
        int removeLvl = Integer.parseInt(prfIterator.getCurrentRow().getAttribute("DocLevel").toString());
        prfIterator.getCurrentRow().remove();
        Row[] rows = prfIterator.getViewObject().getAllRowsInRange();
        for (Row r : rows) {
            int level = 0;
            if (r.getAttribute("DocLevel") != null)
                level = Integer.parseInt(r.getAttribute("DocLevel").toString());
            if (level > removeLvl) {
                r.setAttribute("DocLevel", level - 1);
            }
        }
        prfIterator.executeQuery();
        return null;
    }
}
