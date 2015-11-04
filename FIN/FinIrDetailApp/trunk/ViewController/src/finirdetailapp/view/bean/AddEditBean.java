package finirdetailapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import static adf.utils.ebiz.EbizParams.GLBL_APP_USR;
import adf.utils.model.ADFModelUtils;

import java.sql.Timestamp;

import java.util.List;

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

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.OperationBinding;

public class AddEditBean {
    private String mode = "V";
    private RichPanelTabbed tabBind;
    private RichInputText totAmtSpBind;
    private String wfId = null;
    private RichInputDate reqDtBind;
    private RichSelectOneRadio reqTypBind;
    private RichSelectOneChoice eotypBind;
    private RichTable expTabBind;
    private RichPopup coaBalBinding;
    private RichPopup historyDispPopup;

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    public void setMode(String maode) {
        this.mode = maode;
    }

    public String getMode() {
        return mode;
    }

    public AddEditBean() {
    }

    public void expenseAddAction(ActionEvent actionEvent) {
        // Add event code here...
        ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(expTabBind);
    }

    public void AddAction(ActionEvent actionEvent) {
        // Add event code here...
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
        mode = "A";
    }

    public void editAction(ActionEvent actionEvent) {
        OperationBinding pendingAtOp = ADFBeanUtils.getOperationBinding("chkPendingUsr");
        pendingAtOp.execute();
        Integer pendingAt = (Integer) pendingAtOp.getResult();
        if (pendingAt.equals(-1) || pendingAt.equals(EbizParams.GLBL_APP_USR()))
            mode = "E";
        else {
            OperationBinding usrNmOp = ADFBeanUtils.getOperationBinding("getUsrNm");
            usrNmOp.getParamsMap().put("usrId", pendingAt);
            usrNmOp.execute();
            String usrNm = "Anonymous.";
            if (usrNmOp.getResult() != null)
                usrNm = (String) usrNmOp.getResult();
            /*  ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("#{bundle['MSG.1163']}"),
                                                            ADFModelUtils.resolvRsrc("#{bundle['MSG.1294']}") + " [ " + usrNm +
                                                            "]. ", FacesMessage.SEVERITY_INFO);*/
            ADFModelUtils.showFormattedFacesMessage(" ", ADFModelUtils.resolvRsrc("MSG.1294") + usrNm + "].",
                                                    FacesMessage.SEVERITY_INFO);
        }
    }

    public void saveAction(ActionEvent actionEvent) {
        // Add event code here...
        boolean res = saveValidations();
        if (res == false)
            return;
        System.out.println("commit");
        ADFBeanUtils.getOperationBinding("updateIrId").execute();
        /* if (mode.equalsIgnoreCase("A")) {
            ADFBeanUtils.getOperationBinding("updateIrId").execute();
        } */
        ADFBeanUtils.getOperationBinding("Commit").execute();
        //  ADFBeanUtils.getOperationBinding("Commit").execute();
        mode = "V";
        //entry in WF
        Object execute1 = ADFBeanUtils.getOperationBinding("getWFId").execute();
        System.out.println("------------1");
        if (execute1 != null) {
            System.out.println("------------2");
            wfId = execute1.toString();
            OperationBinding binding = ADFBeanUtils.getOperationBinding("getNextUsrLevel");
            binding.getParamsMap().put("wfId", wfId);
            Object execute_2 = binding.execute();
            System.out.println("------------3");
            if (execute_2 != null) {
                System.out.println("------------4");
                System.out.println("000----------------");
                OperationBinding binding1 = ADFBeanUtils.getOperationBinding("insertIntoWFTxn");
                binding1.getParamsMap().put("wfId", wfId);
                binding1.getParamsMap().put("level", Integer.parseInt(execute_2.toString()));
                Object execute_3 = binding1.execute();
                ADFBeanUtils.getOperationBinding("Commit").execute();
            }
        } else {
            System.out.println("please attach document in workflow");
            String msg = (String) resolvEl("#{bundle['MSG.2750']}");
            showMessage(msg, FacesMessage.SEVERITY_INFO);
        }
    }

    public void saveAndForwardAction(ActionEvent actionEvent) {
        // Add event code here...
        mode = "V";
    }

    public String cancelAction() {
        // Add event code here...
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        mode = "V";
        return "backToPage";
    }

    public void setModeAdd() {
        mode = "A";
        // Add event code here...
    }

    public void setModeView() {
        mode = "V";
        // Add event code here...
    }

    public void eoNmChangeAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ADFBeanUtils.getOperationBinding("getCurrConVal").execute();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(reqDtBind);
        //  ADFBeanUtils.getOperationBinding("setCOAIdFromEoId").execute();
    }

    public void addPayDtlAction(ActionEvent actionEvent) {
        // Add event code here...
        ADFBeanUtils.getOperationBinding("CreateInsert2").execute();
        //   ADFBeanUtils.getOperationBinding("AddInsNo").execute();
        ADFBeanUtils.getOperationBinding("reAssignInsNo").execute();
    }

    public void expAmountChangeAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(totAmtSpBind);
    }

    public void setTabBind(RichPanelTabbed tabBind) {
        this.tabBind = tabBind;
    }

    public RichPanelTabbed getTabBind() {
        return tabBind;
    }

    public void entityTypChangeAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        //tabBind.get
        Object newValue = valueChangeEvent.getNewValue();
        System.out.println("New val is: " + newValue);

        //    UIXIterator uiXIterator = this.getTabBind();

        if (newValue != null) {
            List<UIComponent> children = tabBind.getChildren();
            for (UIComponent c : children) {
                RichShowDetailItem sdi = (RichShowDetailItem) c;
                System.out.println("Value of sdi = " + sdi.getText());
                String id = sdi.getId();
                System.out.println("");
                System.out.println("condition : " +
                                   ((Integer.parseInt(newValue.toString()) == 68 ||
                                     Integer.parseInt(newValue.toString()) == 69) && id.equalsIgnoreCase("tab2")));
                if ((Integer.parseInt(newValue.toString()) == 68 || Integer.parseInt(newValue.toString()) == 69) &&
                    id.equalsIgnoreCase("tab2")) {
                    sdi.setDisclosed(true);
                    sdi.setDisabled(false);
                } else if ((Integer.parseInt(newValue.toString()) == 80 ||
                            Integer.parseInt(newValue.toString()) == 79) && id.equalsIgnoreCase("tab1")) {
                    sdi.setDisclosed(true);
                    sdi.setDisabled(false);
                } else {
                    sdi.setDisabled(true);
                    sdi.setDisclosed(false);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        }

    }

    public void payDeletAction(ActionEvent actionEvent) {
        // Add event code here...
        ADFBeanUtils.getOperationBinding("Delete1").execute();
        ADFBeanUtils.getOperationBinding("Execute").execute();
        ADFBeanUtils.getOperationBinding("delInsNo").execute();
    }

    public void expenseeDeleteAction(ActionEvent actionEvent) {
        // Add event code here...
        //ADFBeanUtils.getOperationBinding("deleteExpTable").execute();
        ADFBeanUtils.getOperationBinding("Delete").execute();

        //  ADFBeanUtils.getOperationBinding("Execute2").execute();
    }

    public void setTotAmtSpBind(RichInputText totAmtSpBind) {
        this.totAmtSpBind = totAmtSpBind;
    }

    public RichInputText getTotAmtSpBind() {
        return totAmtSpBind;
    }

    public void payAmtChnageAction(ValueChangeEvent vce) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(totAmtSpBind);
    }

    public String saveAndForward() {
        boolean res = saveValidations();
        if (res == false)
            return null;
        /*
        if (mode.equalsIgnoreCase("A")) {
            ADFBeanUtils.getOperationBinding("updateIrId").execute();
        } */
        ADFBeanUtils.getOperationBinding("updateIrId").execute();
        ADFBeanUtils.getOperationBinding("Commit").execute();
        // ADFBeanUtils.getOperationBinding("Execute").execute();
        mode = "V";
        Object execute = ADFBeanUtils.getOperationBinding("getCurrUsr").execute();
        if (execute != null) {
            int lvl = Integer.parseInt(execute.toString());
            int curUsr = GLBL_APP_USR();
            if (!(lvl == -1 || curUsr == lvl)) {
                System.out.println("Document is pending for another user !!");
                String msg = (String) resolvEl("#{bundle['MSG.2747']}");
                showMessage(msg, FacesMessage.SEVERITY_ERROR);
            } else {
                Object execute1 = ADFBeanUtils.getOperationBinding("getWFId").execute();
                System.out.println("------------1");
                if (execute1 != null) {
                    System.out.println("------------2");
                    wfId = execute1.toString();
                    OperationBinding binding = ADFBeanUtils.getOperationBinding("getNextUsrLevel");
                    binding.getParamsMap().put("wfId", wfId);
                    Object execute_2 = binding.execute();
                    System.out.println("------------3");
                    if (execute_2 != null) {
                        System.out.println("------------4");
                        System.out.println("000----------------");
                        OperationBinding binding1 = ADFBeanUtils.getOperationBinding("insertIntoWFTxn");
                        binding1.getParamsMap().put("wfId", wfId);
                        binding1.getParamsMap().put("level", Integer.parseInt(execute_2.toString()));
                        Object execute_3 = binding1.execute();
                        ADFBeanUtils.getOperationBinding("Commit").execute();
                        setValuesInIrHist();
                        return "gotowf";
                    }
                } else {
                    System.out.println("please attach document in workflow");
                    String msg = (String) resolvEl("#{bundle['MSG.2750']}");
                    showMessage(msg, FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        return null;
    }

    public void setValuesInIrHist() {
        ADFBeanUtils.getOperationBinding("setValuesInIrHistTable").execute();

    }

    public void showMessage(String msg, FacesMessage.Severity ser) {
        FacesMessage message2 = new FacesMessage(msg);
        message2.setSeverity(ser);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message2);
    }

    public void showMessage(String msg, FacesMessage.Severity ser, String clientId) {
        FacesMessage message2 = new FacesMessage(msg);
        message2.setSeverity(ser);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(clientId, message2);
    }

    public boolean saveValidations() {

        System.out.println("Inside saveValidation method !!");
        Object obj = ADFBeanUtils.getOperationBinding("checkForEntry").execute();
        if (obj != null && obj.equals(false)) {
            System.out.println("Inside object false ccondition !!");
            if (eotypBind.getValue() != null && (eotypBind.getValue().equals(68) || eotypBind.getValue().equals(69))) {
                showMessage(resolvEl("#{bundle['MSG.2751']}").toString(),
                            FacesMessage.SEVERITY_INFO); //Please Enter Pay Detail.
            } else {
                showMessage(resolvEl("#{bundle['MSG.2752']}"),
                            FacesMessage.SEVERITY_INFO); //Please Enter Expense Detail
            }
            return false;
        }

        Object obj1 = ADFBeanUtils.getOperationBinding("chkDocument").execute();
        if (obj1 != null && obj1.equals(false)) {
            showMessage(resolvEl("#{bundle['MSG.2753']}").toString(),
                        FacesMessage.SEVERITY_ERROR); //Please Enter Document
            return false;
        }

        if (eotypBind.getValue() != null && (eotypBind.getValue().equals(80) || eotypBind.getValue().equals(79)) &&
            reqTypBind.getValue() == null) {
            showMessage(resolvEl("#{bundle['MSG.2754']}").toString(), FacesMessage.SEVERITY_ERROR,
                        reqTypBind.getClientId()); //Please Select Request Typ
            return false;
        }
        return true;
    }

    public void dueDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null && reqDtBind.getValue() != null) {
            Timestamp dueDt = (Timestamp) object;
            Timestamp reqDt = (Timestamp) reqDtBind.getValue();
            long dif = ((dueDt.getTime() - reqDt.getTime()) / (24 * 60 * 60 * 1000));
            if (dif < -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                              resolvEl("#{bundle['MSG.2755']}"))); //Due Date can not be less than Request Date
            }
        }
    }

    public void setReqDtBind(RichInputDate reqDtBind) {
        this.reqDtBind = reqDtBind;
    }

    public RichInputDate getReqDtBind() {
        return reqDtBind;
    }

    public void expenseValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("isDuplicateExpNm");
            binding.getParamsMap().put("name", object.toString());
            Object obj = binding.execute();
            if (obj != null && obj.equals(true)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                              resolvEl("#{bundle['MSG.2757']}").toString())); //Duplicate Entry
            }
        }
    }

    public void setReqTypBind(RichSelectOneRadio reqTypBind) {
        this.reqTypBind = reqTypBind;
    }

    public RichSelectOneRadio getReqTypBind() {
        return reqTypBind;
    }

    public void setEotypBind(RichSelectOneChoice eotypBind) {
        this.eotypBind = eotypBind;
    }

    public RichSelectOneChoice getEotypBind() {
        return eotypBind;
    }

    public void payDtlAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            oracle.jbo.domain.Number n = (oracle.jbo.domain.Number) object;
            if (n.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                              resolvEl("#{bundle['MSG.2140']").toString()));
                //  Amount should be Greater than Zero
            }
        }
    }

    public void expDtlAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        if (object != null) {
            oracle.jbo.domain.Number n = (oracle.jbo.domain.Number) object;
            if (n.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                              resolvEl("#{bundle['MSG.2140']").toString()));
                //  Amount should be Greater than Zero
            }
        }

    }

    public void dateChaneAction(ValueChangeEvent vce) {
        // Add event code here...
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void coaChangeAction(ValueChangeEvent vce) {
        // Add event code here...
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(expTabBind);
    }

    public void setExpTabBind(RichTable expTabBind) {
        this.expTabBind = expTabBind;
    }

    public RichTable getExpTabBind() {
        return expTabBind;
    }

    public String showCOABalanceACTION() {
        System.out.println("Link Action Listener");
        // ADFBeanUtils.showPopup(coaBalBinding, true);
        return null;
    }

    public void setCoaBalBinding(RichPopup coaBalBinding) {
        this.coaBalBinding = coaBalBinding;
    }

    public RichPopup getCoaBalBinding() {
        return coaBalBinding;
    }

    public void coaBalPopFetchListener(PopupFetchEvent popupFetchEvent) {
        System.out.println("Popup Fetch Listener");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("showCoaBalance");
        binding.execute();
    }

    /**Written by savi*/

    public void historyActionListener(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("historyView");
        binding.execute();
        ADFBeanUtils.showPopup(historyDispPopup, true);
    }

    public void setHistoryDispPopup(RichPopup historyDispPopup) {
        this.historyDispPopup = historyDispPopup;
    }

    public RichPopup getHistoryDispPopup() {
        return historyDispPopup;
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


}
