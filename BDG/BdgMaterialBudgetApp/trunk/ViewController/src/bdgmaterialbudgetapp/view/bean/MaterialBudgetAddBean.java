package bdgmaterialbudgetapp.view.bean;

import adf.utils.model.ADFModelUtils;

import bdgmaterialbudgetapp.view.utils.ADFUtils;
import bdgmaterialbudgetapp.view.utils.JSFUtils;

import java.sql.Date;
import java.sql.SQLException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MaterialBudgetAddBean {
    String CldId = (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
    String HoOrgId = (resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
    String OrgId = (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
    Integer SlocId = (Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
    Integer UsrId = (Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
    Integer DocNo = 34003;
    Integer DocType = 0;
    String mode = (resolvEl("#{pageFlowScope.PARAM_PAGE_MODE}").toString());
    private String selectedTab = "itemTab";

    private RichSelectOneRadio selectionBasisBinding;
    private RichPopup eoBdgListPopUp;
    private RichInputDate prdStDtBinding;
    private RichInputDate prdEnDtBinding;
    private RichInputDate fyEndDtBinding;
    private RichPopup addAnotherSetPopBinding;
    private RichPopup manualItemPopUpPgBind;
    private UIXSwitcher distributionTabPgBind;
    private RichInputText itemAmtSpPgBind;
    private RichInputText itemPricePgBind;
    private RichInputText itemQtyPgBind;
    private RichInputText convRatePgBind;
    private RichPanelTabbed rightPanelTabPgBind;
    private RichTable materialBdgSummTablePgBind;
    private RichInputText distributeItmAmtPgBind;
    private RichInputText otherExpItmAmtPgBind;
    private RichOutputText amtNotationDescPgBind;
    private RichPanelStretchLayout panelStretchPgBind;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public MaterialBudgetAddBean() {
    }

    public String addAction() {
        OperationBinding op = ADFUtils.findOperation("CreateInsert");
        op.execute();
        if (op.getErrors().size() == 0) {
            OperationBinding opDocId = ADFUtils.findOperation("genDocTxnId");
            opDocId.getParamsMap().put("cldId", CldId);
            opDocId.getParamsMap().put("slocId", SlocId);
            opDocId.getParamsMap().put("orgId", OrgId);
            opDocId.getParamsMap().put("usrId", UsrId);
            opDocId.getParamsMap().put("docId", DocNo);
            opDocId.getParamsMap().put("docTypeId", DocType);
            opDocId.execute();
            if (opDocId.getErrors().size() == 0)
                mode = "A";
            else {
                JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.1478")+"\n Error: " + opDocId.getErrors());
                //JSFUtils.addFacesErrorMessage("Something went wrong. \nError: " + opDocId.getErrors());
                return null;
            }
        } else {
                JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.1478")+"\n Error: " + op.getErrors());
                //JSFUtils.addFacesErrorMessage("Something went wrong. \nError: " + op.getErrors());
                return null;
        }
        return null;
    }

    public Object resolvEl(String data) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, data, Object.class);
        return exp.getValue(elContext);
    }

    public String editButtonAction() {
        //check for pending
        Integer pending = null;

        OperationBinding chkUsr = ADFUtils.findOperation("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", SlocId);
        chkUsr.getParamsMap().put("CldId", CldId);
        chkUsr.getParamsMap().put("OrgId", OrgId);
        chkUsr.getParamsMap().put("DocNo", DocNo);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }
        System.out.println("Pending at=" + pending);
        if (pending.compareTo(-1) != 0 && pending.compareTo(UsrId) != 0) {
            OperationBinding gtUsrNm = ADFUtils.findOperation("getUsrName");
            gtUsrNm.getParamsMap().put("usrId", pending);
            gtUsrNm.execute();
            StringBuffer usrName = new StringBuffer("Anonymous");
            if (gtUsrNm.getResult() != null)
                usrName = new StringBuffer("[").append(gtUsrNm.getResult()).append("]");
                    
            String msg2 = ADFModelUtils.resolvRsrc("MSG.1294")+ " [ " + usrName + "] "+ ADFModelUtils.resolvRsrc("MSG.879");
                //"Material Budget Set is pending at other user " + usrName + " for approval, You cannot Edit this.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        } else {
            showPopup(addAnotherSetPopBinding, true);
            return null;
        }

    }

    public String saveButtonAction() {

        OperationBinding opDispId = ADFUtils.findOperation("genDispId");
        opDispId.getParamsMap().put("cldId", CldId);
        opDispId.getParamsMap().put("slocId", SlocId);
        opDispId.getParamsMap().put("orgId", OrgId);
        opDispId.getParamsMap().put("usrId", UsrId);
        opDispId.getParamsMap().put("docId", DocNo);
        opDispId.getParamsMap().put("docTypeId", DocType);
        opDispId.execute();
        if (opDispId.getErrors().size() == 0) {
            OperationBinding op = ADFUtils.findOperation("Commit");
            op.execute();
            if (op.getErrors().size() == 0) {
                mode = "V";
                JSFUtils.addFacesInformationMessage(ADFModelUtils.resolvRsrc("MSG.91"));
                //JSFUtils.addFacesInformationMessage("Record Saved Successfully.");


                //Workflow Start
                String action = "I";
                String remark = "A";

                OperationBinding WfnoOp = ADFUtils.findOperation("getWfNo");
                WfnoOp.getParamsMap().put("sloc_id", SlocId);
                WfnoOp.getParamsMap().put("cld_id", CldId);
                WfnoOp.getParamsMap().put("org_id", OrgId);
                WfnoOp.getParamsMap().put("doc_no", DocNo);
                WfnoOp.execute();

                String WfNum = null;
                Integer level = 0;
                Integer res = -1;

                if (WfnoOp.getResult() != null) {
                    WfNum = WfnoOp.getResult().toString();
                }
                if (WfNum != null) {
                    OperationBinding usrLevelOp = ADFUtils.findOperation("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", SlocId);
                    usrLevelOp.getParamsMap().put("CldId", CldId);
                    usrLevelOp.getParamsMap().put("OrgId", OrgId);
                    usrLevelOp.getParamsMap().put("usr_id", UsrId);
                    usrLevelOp.getParamsMap().put("WfNum", WfNum);
                    usrLevelOp.getParamsMap().put("DocNo", DocNo);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        if (usrLevelOp.getResult() != null)
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                    }
                    if (level == -1) {
                        FacesMessage message = 
                            new FacesMessage( ADFModelUtils.resolvRsrc("MSG.1294"));
                            //new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        OperationBinding insTxnOp = ADFUtils.findOperation("insIntoTxn");
                        insTxnOp.getParamsMap().put("sloc_id", SlocId);
                        insTxnOp.getParamsMap().put("cld_id", CldId);
                        insTxnOp.getParamsMap().put("pOrgId", OrgId);
                        insTxnOp.getParamsMap().put("DOC_NO", DocNo);
                        insTxnOp.getParamsMap().put("WfNum", WfNum);
                        insTxnOp.getParamsMap().put("poDocId", null);
                        insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                        insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                        insTxnOp.getParamsMap().put("levelFrm", level);
                        insTxnOp.getParamsMap().put("levelTo", level);
                        insTxnOp.getParamsMap().put("action", action);
                        insTxnOp.getParamsMap().put("remark", remark);
                        insTxnOp.getParamsMap().put("amount", 0);
                        insTxnOp.getParamsMap().put("post", "S");
                        insTxnOp.execute();

                        if (insTxnOp.getResult() != null) {
                            res = Integer.parseInt(insTxnOp.getResult().toString());
                        }
                        if (res == 1) {
                            OperationBinding operationBinding = ADFUtils.findOperation("Commit");
                            operationBinding.execute();

                        } else {
                            System.out.println("error during insert to WF");
                        }
                        System.out.println(level + "level--res" + res);
                    }
                } /* else {
                    FacesMessage message = new FacesMessage("Workflow not Defined for this Document.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } */


                //Workflow end

            } else {
                JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.1478")+"\n Error: " + op.getErrors());
                //JSFUtils.addFacesErrorMessage("Something went wrong. \nError: " + opDocId.getErrors());
                return null;
            }
        } else {
                JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.1478")+"\n Error: " + opDispId.getErrors());
                //JSFUtils.addFacesErrorMessage("Something went wrong. \nError: " + opDocId.getErrors());
                return null;
        }

        return null;
    }

    public String cancelButtonAction() {
        OperationBinding op = ADFUtils.findOperation("Rollback");
        op.execute();
        if (op.getErrors().size() == 0) {
            mode = "V";
        } else {
                JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.1478")+"\n Error: " + op.getErrors());
                //JSFUtils.addFacesErrorMessage("Something went wrong. \nError: " + opDocId.getErrors());
                return null;
        }
        return "back";
    }

    public String addItemAction() {
        OperationBinding opItm = ADFUtils.findOperation("CreateInsertItm");
        opItm.execute();
        if (opItm.getErrors().size() == 0) {
            OperationBinding opSetDtl = ADFUtils.findOperation("setItmDetail");
            opSetDtl.execute();

            showPopup(manualItemPopUpPgBind, true);
        } else {
                JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.1478")+"\n Error: " + opItm.getErrors());
                //JSFUtils.addFacesErrorMessage("Something went wrong. \nError: " + opDocId.getErrors());
                return null;
        }
        return null;
    }


    public String addExpenseAction() {
        OperationBinding opExp = ADFUtils.findOperation("CreateInsertExpense");
        opExp.execute();
        if (opExp.getErrors().size() == 0) {
            OperationBinding opExpDtl = ADFUtils.findOperation("setOcExpDetail");
            opExpDtl.execute();

            for (UIComponent child : this.getRightPanelTabPgBind().getChildren()) {
                RichShowDetailItem sdi = (RichShowDetailItem) child;
                if (sdi.getId().equalsIgnoreCase("sdi2")) {
                    sdi.setDisclosed(true);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(rightPanelTabPgBind);
        } else {
                JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.1478")+"\n Error: " + opExp.getErrors());
                //JSFUtils.addFacesErrorMessage("Something went wrong. \nError: " + opDocId.getErrors());
                return null;
        }
        return null;
    }

    public String deleteExpAction() {
        OperationBinding opDel = ADFUtils.findOperation("DeleteExp");
        opDel.execute();

        return null;
    }

    public void periodStrtDtVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            java.sql.Date stDt = null;
            try {
                stDt = ((Timestamp) valueChangeEvent.getNewValue()).dateValue();
            } catch (SQLException e) {
                JSFUtils.addFacesErrorMessage("Something went wrong. \nError" + e.getStackTrace());
            }
            OperationBinding opFyIdDt = ADFUtils.findOperation("settingFinancialYear");
            opFyIdDt.getParamsMap().put("stDt", stDt);
            opFyIdDt.execute();
        }
    }

    public String populateItemAction() {
        // check for all validation then populate item from sales budget and BOM
        /* if (prdStDtBinding.getValue() != null && prdStDtBinding.getValue().toString().length() > 0 &&
            prdEnDtBinding.getValue() != null && prdEnDtBinding.getValue().toString().length() > 0) {
            OperationBinding opFilter = ADFUtils.findOperation("filterEmployeeFromView");
            java.sql.Date stDt = null;
            java.sql.Date endDt = null;
            try {
                stDt = ((Timestamp) prdStDtBinding.getValue()).dateValue();
                endDt = ((Timestamp) prdEnDtBinding.getValue()).dateValue();
            } catch (SQLException e) {
                JSFUtils.addFacesErrorMessage("Something went wrong. \nError" + e.getStackTrace());
            }
            opFilter.getParamsMap().put("stDt", stDt);
            opFilter.getParamsMap().put("endDt", endDt);
            opFilter.execute(); */

        /*   if (selectionBasisBinding.getValue() != null && selectionBasisBinding.getValue().toString().equals("S")) {
                System.out.println("Selective");
                // In Case of Manual Selection Basis show popup to select Employee.
                showPopup(eoBdgListPopUp, true);
            } else if (selectionBasisBinding.getValue() != null &&
                       selectionBasisBinding.getValue().toString().equals("C")) { */
        OperationBinding opDelItm = ADFUtils.findOperation("deleteBomBasisItem");
        opDelItm.execute();

        /*  OperationBinding opAdd = ADFUtils.findOperation("addSlsEoToMtlEo");
                opAdd.getParamsMap().put("selBasis", "C");
                opAdd.execute(); */


        OperationBinding op = ADFUtils.findOperation("populateItmFromBOM");
        op.getParamsMap().put("selBasisParam", "C");
        op.execute();
        /*  } else {
                System.out.println("Null value=" + selectionBasisBinding.getValue());
            } */
        // }
        return null;
    }

    public void setSelectionBasisBinding(RichSelectOneRadio selectionBasisBinding) {
        this.selectionBasisBinding = selectionBasisBinding;
    }

    public RichSelectOneRadio getSelectionBasisBinding() {
        return selectionBasisBinding;
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

    public void setEoBdgListPopUp(RichPopup eoBdgListPopUp) {
        this.eoBdgListPopUp = eoBdgListPopUp;
    }

    public RichPopup getEoBdgListPopUp() {
        return eoBdgListPopUp;
    }

    public void eoBudgetSelectPopupDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding opAdd = ADFUtils.findOperation("addSlsEoToMtlEo");
            opAdd.getParamsMap().put("selBasis", "S");
            opAdd.execute();

            OperationBinding opDelItm = ADFUtils.findOperation("deleteBomBasisItem");
            opDelItm.execute();

            OperationBinding op = ADFUtils.findOperation("populateItmFromBOM");
            op.getParamsMap().put("selBasisParam", "S");
            op.execute();
        }
    }

    public void setPrdStDtBinding(RichInputDate prdStDtBinding) {
        this.prdStDtBinding = prdStDtBinding;
    }

    public RichInputDate getPrdStDtBinding() {
        return prdStDtBinding;
    }

    public void setPrdEnDtBinding(RichInputDate prdEnDtBinding) {
        this.prdEnDtBinding = prdEnDtBinding;
    }

    public RichInputDate getPrdEnDtBinding() {
        return prdEnDtBinding;
    }

    public void prdStrtDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            java.sql.Date stDt = null;
            java.sql.Date currDt = new Date(System.currentTimeMillis());
            System.out.println("Current Dt=" + currDt);
            try {
                stDt = ((Timestamp) object).dateValue();
            } catch (SQLException e) {
                JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.1478")+"\n Error: " + e.getStackTrace());
                //JSFUtils.addFacesErrorMessage("Something went wrong. \nError: " + opDocId.getErrors());
            }
            if (currDt.compareTo(stDt) > 0) {
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2347")));
                //throw new ValidatorException(new FacesMessage("Budget Start Date should be After Current Date."));

            }

            OperationBinding opFyIdDt = ADFUtils.findOperation("validateFinancialYear");
            opFyIdDt.getParamsMap().put("stDt", stDt);
            opFyIdDt.execute();
            if (opFyIdDt.getErrors().size() == 0 && opFyIdDt.getResult() != null &&
                opFyIdDt.getResult().toString().equals("Y")) {
                OperationBinding opChkExist = ADFUtils.findOperation("chkDtExistInPeriod");
                opChkExist.getParamsMap().put("validDt", stDt);
                opChkExist.execute();
                if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null &&
                    opChkExist.getResult().toString().equals("N")) {
                } else if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null) {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2319")+" - "+
                                                                  opChkExist.getResult()));
                    /* throw new ValidatorException(new FacesMessage("Budget for the Selected Date is already exist - " +
                                                                  opChkExist.getResult())); */
                }
            } else {
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2264")));
                //throw new ValidatorException(new FacesMessage("Please select Date from Valid Financial Year"));
            }
        }

    }

    public void prdEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            java.sql.Date fyEndDt = null;
            if (prdStDtBinding.getValue() != null && prdStDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) prdStDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2348")));
                        //throw new ValidatorException(new FacesMessage("Budget End Date can not be Before Budget Start Date."));
                    }
                }

                OperationBinding opFyIdDt = ADFUtils.findOperation("validateFinancialYear");
                opFyIdDt.getParamsMap().put("stDt", endDt);
                opFyIdDt.execute();
                if (opFyIdDt.getErrors().size() == 0 && opFyIdDt.getResult() != null &&
                    opFyIdDt.getResult().toString().equals("Y")) {
                } else {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.521") +" - "+ADFModelUtils.resolvRsrc("MSG.2349")));
                    //throw new ValidatorException(new FacesMessage("Invalid Date - Please Check Financial Year"));
                }
            }

            if (fyEndDtBinding.getValue() != null && fyEndDtBinding.getValue().toString().length() > 0) {
                try {
                    fyEndDt = ((Timestamp) fyEndDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (endDt.compareTo(fyEndDt) > 0) {
                    if (endDt.toString().equals(fyEndDt.toString())) {
                    } else {
                        throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2350")));
                        //throw new ValidatorException(new FacesMessage("Budget End Date can not be After Financial Year End Date."));
                    }
                }
            }

            try {
                endDt = ((Timestamp) object).dateValue();
            } catch (SQLException e) {
                System.out.println(e.getStackTrace());
            }
            OperationBinding opChkExist = ADFUtils.findOperation("chkDtExistInPeriod");
            opChkExist.getParamsMap().put("validDt", endDt);
            opChkExist.execute();
            if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null &&
                opChkExist.getResult().toString().equals("N")) {
            } else if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null) {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2319")+" - "+
                                                                  opChkExist.getResult()));
                    /* throw new ValidatorException(new FacesMessage("Budget for the Selected Date is already exist - " +
                                                                  opChkExist.getResult())); */
            }


        }
    }

    public void itmIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding opChkDupli = ADFUtils.findOperation("validateDuplicateItmId");
            opChkDupli.getParamsMap().put("itmDesc", object);
            opChkDupli.execute();
            if (opChkDupli.getErrors().size() == 0 && opChkDupli.getResult() != null &&
                opChkDupli.getResult().toString().equals("Y")) {
            } else {
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2351")));
                //throw new ValidatorException(new FacesMessage("Item from same Source has been added already."));
            }
        }

    }

    public void itmPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1048")));
                    //throw new ValidatorException(new FacesMessage("Price should be greater than Zero."));
                }
            } else
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1107")));
                //throw new ValidatorException(new FacesMessage("Invalid Precision(26,6)."));

        }
    }

    /**
     *      Method to Apply Precision Validation(P,S,V)
     *      P- Precision
     *      S- Scale
     *      V- Value to validate
     * */
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void itmQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.474")));
                    //throw new ValidatorException(new FacesMessage("Quantity should be greater than Zero."));
                }
            } else
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1107")));
                //throw new ValidatorException(new FacesMessage("Invalid Precision(26,6)."));

        }

    }

    public void coaNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding opChkDupli = ADFUtils.findOperation("validateDuplicateCoaNm");
            opChkDupli.getParamsMap().put("coaNm", object);
            opChkDupli.execute();
            if (opChkDupli.getErrors().size() == 0 && opChkDupli.getResult() != null &&
                opChkDupli.getResult().toString().equals("Y")) {
            } else {
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2133")));
                //throw new ValidatorException(new FacesMessage("COA already exist."));
            }
        }

    }

    public void amtSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.412")));
                    //throw new ValidatorException(new FacesMessage("Amount should be greater than Zero."));
                }
            } else
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1107")));
                //throw new ValidatorException(new FacesMessage("Invalid Precision(26,6)."));

        }

    }

    public void orgIdVCL(ValueChangeEvent valueChangeEvent) {
        prdStDtBinding.setValue(null);
        prdEnDtBinding.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(prdStDtBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(prdEnDtBinding);
    }

    public void setFyEndDtBinding(RichInputDate fyEndDtBinding) {
        this.fyEndDtBinding = fyEndDtBinding;
    }

    public RichInputDate getFyEndDtBinding() {
        return fyEndDtBinding;
    }

    public void addAnotherSetPopDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Yes")) {
            OperationBinding opAmend = ADFUtils.findOperation("amendCurrBdgt");
            opAmend.execute();
            mode = "A";
        } else if (dialogEvent.getOutcome().name().equalsIgnoreCase("No")) {
            mode = "A";
        }
        refreshPage();
    }


    public void setAddAnotherSetPopBinding(RichPopup addAnotherSetPopBinding) {
        this.addAnotherSetPopBinding = addAnotherSetPopBinding;
    }

    public RichPopup getAddAnotherSetPopBinding() {
        return addAnotherSetPopBinding;
    }

    public void amendBdgPopCancelListener(PopupCanceledEvent popupCanceledEvent) {
        mode = "A";
        refreshPage();
    }


    protected void refreshPage() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String refreshpage = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
        UIV.setViewId(refreshpage);
        fctx.setViewRoot(UIV);
    }


    public void transSelectCbVCE(ValueChangeEvent valueChangeEvent) {
        System.out.println("New Value is=" + valueChangeEvent.getNewValue());
    }


    public void excptItmVwDiscloseListener(DisclosureEvent disclosureEvent) {
        System.out.println("IsExpended=" + disclosureEvent.isExpanded());
        if (disclosureEvent.isExpanded()) {
            OperationBinding opEx = ADFUtils.findOperation("updateExceptionalItm");
            opEx.execute();
        }
    }

    public String saveAndFwdAction() {

        OperationBinding opDispId = ADFUtils.findOperation("genDispId");
        opDispId.getParamsMap().put("cldId", CldId);
        opDispId.getParamsMap().put("slocId", SlocId);
        opDispId.getParamsMap().put("orgId", OrgId);
        opDispId.getParamsMap().put("usrId", UsrId);
        opDispId.getParamsMap().put("docId", DocNo);
        opDispId.getParamsMap().put("docTypeId", DocType);
        opDispId.execute();
        if (opDispId.getErrors().size() == 0) {
            OperationBinding op = ADFUtils.findOperation("Commit");
            op.execute();
            if (op.getErrors().size() == 0) {
                mode = "V";
                //  JSFUtils.addFacesInformationMessage("Record Saved Successfully.");


                //Workflow Start
                String action = "I";
                String remark = "A";

                OperationBinding WfnoOp = ADFUtils.findOperation("getWfNo");
                WfnoOp.getParamsMap().put("sloc_id", SlocId);
                WfnoOp.getParamsMap().put("cld_id", CldId);
                WfnoOp.getParamsMap().put("org_id", OrgId);
                WfnoOp.getParamsMap().put("doc_no", DocNo);
                WfnoOp.execute();

                String WfNum = null;
                Integer level = 0;
                Integer res = -1;

                if (WfnoOp.getResult() != null) {
                    WfNum = WfnoOp.getResult().toString();
                }
                if (WfNum != null) {
                    OperationBinding usrLevelOp = ADFUtils.findOperation("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", SlocId);
                    usrLevelOp.getParamsMap().put("CldId", CldId);
                    usrLevelOp.getParamsMap().put("OrgId", OrgId);
                    usrLevelOp.getParamsMap().put("usr_id", UsrId);
                    usrLevelOp.getParamsMap().put("WfNum", WfNum);
                    usrLevelOp.getParamsMap().put("DocNo", DocNo);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        if (usrLevelOp.getResult() != null)
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                    }
                    if (level == -1) {
                        FacesMessage message = 
                            new FacesMessage( ADFModelUtils.resolvRsrc("MSG.1294"));
                            //new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        OperationBinding insTxnOp = ADFUtils.findOperation("insIntoTxn");
                        insTxnOp.getParamsMap().put("sloc_id", SlocId);
                        insTxnOp.getParamsMap().put("cld_id", CldId);
                        insTxnOp.getParamsMap().put("pOrgId", OrgId);
                        insTxnOp.getParamsMap().put("DOC_NO", DocNo);
                        insTxnOp.getParamsMap().put("WfNum", WfNum);
                        insTxnOp.getParamsMap().put("poDocId", null);
                        insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                        insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                        insTxnOp.getParamsMap().put("levelFrm", level);
                        insTxnOp.getParamsMap().put("levelTo", level);
                        insTxnOp.getParamsMap().put("action", action);
                        insTxnOp.getParamsMap().put("remark", remark);
                        insTxnOp.getParamsMap().put("amount", 0);
                        insTxnOp.getParamsMap().put("post", "S");
                        insTxnOp.execute();

                        if (insTxnOp.getResult() != null) {
                            res = Integer.parseInt(insTxnOp.getResult().toString());
                        }
                        if (res == 1) {
                            OperationBinding operationBinding = ADFUtils.findOperation("Commit");
                            operationBinding.execute();
                            return "goToWf";
                        } else {
                            System.out.println("error during insert to WF");
                        }
                        System.out.println(level + "level--res" + res);
                    }
                } else {
                    FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"));
                    //FacesMessage message = new FacesMessage("Workflow not Defined for this Document.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }


                //Workflow end

            } else {
                JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.1478")+"\n Error: " + op.getErrors());
                //JSFUtils.addFacesErrorMessage("Something went wrong. \nError: " + opDocId.getErrors());
                return null;
            }
        } else {
                JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.1478")+"\n Error: " + opDispId.getErrors());
                //JSFUtils.addFacesErrorMessage("Something went wrong. \nError: " + opDocId.getErrors());
            return null;
        }

        return null;
    }

    public void itemNameVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void processDtlDistrributionAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("distributeItemDetailPeriodWise").execute();
    }

    public void distributionDetailFL(PopupFetchEvent popupFetchEvent) {
        ADFUtils.findOperation("filterDistributionData").execute();
    }


    public void manualItemOkAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("postDataIntoTable").execute();

        manualItemPopUpPgBind.hide();

        AdfFacesContext.getCurrentInstance().addPartialTarget(materialBdgSummTablePgBind);
    }

    public void manualItemCancelAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("removeDataFromTable").execute();

        manualItemPopUpPgBind.hide();
    }

    public void setManualItemPopUpPgBind(RichPopup manualItemPopUpPgBind) {
        this.manualItemPopUpPgBind = manualItemPopUpPgBind;
    }

    public RichPopup getManualItemPopUpPgBind() {
        return manualItemPopUpPgBind;
    }

    public void itemAmtSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) < 0) {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.412")));
                    //throw new ValidatorException(new FacesMessage("Amount should be greater than Zero."));
                }
            } else
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1107")));
                //throw new ValidatorException(new FacesMessage("Invalid Precision(26,6)."));

        }
    }

    public void itemAmtSpVCL(ValueChangeEvent vce) {
        //vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("changeItemAmtBs");
            ob.getParamsMap().put("val", vce.getNewValue());
            ob.execute();
        }
    }

    public void itemTabExpandDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            this.selectedTab = "itemTab";
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(distributionTabPgBind);
    }

    public void othersTabExpandDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            this.selectedTab = "othersTab";
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(distributionTabPgBind);
    }

    public void setDistributionTabPgBind(UIXSwitcher distributionTabPgBind) {
        this.distributionTabPgBind = distributionTabPgBind;
    }

    public UIXSwitcher getDistributionTabPgBind() {
        return distributionTabPgBind;
    }

    public void othersExpAmtSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) < 0) {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.412")));
                    //throw new ValidatorException(new FacesMessage("Amount should be greater than Zero."));
                }
            } else
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1107")));
                //throw new ValidatorException(new FacesMessage("Invalid Precision(26,6)."));
        }
    }

    public void othersExpAmtSpVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("updateDistribAmt");
            ob.getParamsMap().put("val", vce.getNewValue());
            ob.execute();
        }
    }

    public void proceessOthersAmtDistribAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("distributeExpDetailPeriodWise").execute();
    }

    public void setItemAmtSpPgBind(RichInputText itemAmtSpPgBind) {
        this.itemAmtSpPgBind = itemAmtSpPgBind;
    }

    public RichInputText getItemAmtSpPgBind() {
        return itemAmtSpPgBind;
    }

    public void itemQtyVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (itemPricePgBind.getValue() != null && convRatePgBind.getValue() != null) {
                OperationBinding ob = ADFUtils.findOperation("updateItemAmtBasedOnSel");
                ob.getParamsMap().put("qty", vce.getNewValue());
                ob.getParamsMap().put("amtSp", itemPricePgBind.getValue());
                ob.getParamsMap().put("confctr", convRatePgBind.getValue());

                ob.execute();
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
    }

    public void itemPriceVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (itemQtyPgBind.getValue() != null && convRatePgBind.getValue() != null) {
                OperationBinding ob = ADFUtils.findOperation("updateItemAmtBasedOnSel");
                ob.getParamsMap().put("qty", itemQtyPgBind.getValue());
                ob.getParamsMap().put("amtSp", vce.getNewValue());
                ob.getParamsMap().put("confctr", convRatePgBind.getValue());

                ob.execute();
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
    }

    public void setItemPricePgBind(RichInputText itemPricePgBind) {
        this.itemPricePgBind = itemPricePgBind;
    }

    public RichInputText getItemPricePgBind() {
        return itemPricePgBind;
    }

    public void setItemQtyPgBind(RichInputText itemQtyPgBind) {
        this.itemQtyPgBind = itemQtyPgBind;
    }

    public RichInputText getItemQtyPgBind() {
        return itemQtyPgBind;
    }

    public void setConvRatePgBind(RichInputText convRatePgBind) {
        this.convRatePgBind = convRatePgBind;
    }

    public RichInputText getConvRatePgBind() {
        return convRatePgBind;
    }

    public void currencyLOVVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

        if (vce.getNewValue() != null) {
            if (itemQtyPgBind.getValue() != null && convRatePgBind.getValue() != null &&
                itemPricePgBind.getValue() != null) {
                OperationBinding ob = ADFUtils.findOperation("updateItemAmtBasedOnSel");
                ob.getParamsMap().put("qty", itemQtyPgBind.getValue());
                ob.getParamsMap().put("amtSp", itemPricePgBind.getValue());
                ob.getParamsMap().put("confctr", convRatePgBind.getValue());

                ob.execute();
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
    }

    public void setRightPanelTabPgBind(RichPanelTabbed rightPanelTabPgBind) {
        this.rightPanelTabPgBind = rightPanelTabPgBind;
    }

    public RichPanelTabbed getRightPanelTabPgBind() {
        return rightPanelTabPgBind;
    }

    public void setMaterialBdgSummTablePgBind(RichTable materialBdgSummTablePgBind) {
        this.materialBdgSummTablePgBind = materialBdgSummTablePgBind;
    }

    public RichTable getMaterialBdgSummTablePgBind() {
        return materialBdgSummTablePgBind;
    }

    public void itemAmtSpNotaValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) < 0) {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.412")));
                    //throw new ValidatorException(new FacesMessage("Amount should be greater than Zero."));
                }
            } else
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1107")));
                //throw new ValidatorException(new FacesMessage("Invalid Precision(26,6)."));

        }
    }

    public void itemAmtSpNotaVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            Number notaAmt = (Number) JSFUtils.resolveExpression("#{pageFlowScope.P_AMT_NOTATION}");
            notaAmt = notaAmt.multiply((Number) vce.getNewValue());

            distributeItmAmtPgBind.setValue(notaAmt);

            OperationBinding ob = ADFUtils.findOperation("changeItemAmtBs");
            ob.getParamsMap().put("val", notaAmt);
            ob.execute();
        }
    }

    public void setDistributeItmAmtPgBind(RichInputText distributeItmAmtPgBind) {
        this.distributeItmAmtPgBind = distributeItmAmtPgBind;
    }

    public RichInputText getDistributeItmAmtPgBind() {
        return distributeItmAmtPgBind;
    }

    public void setOtherExpItmAmtPgBind(RichInputText otherExpItmAmtPgBind) {
        this.otherExpItmAmtPgBind = otherExpItmAmtPgBind;
    }

    public RichInputText getOtherExpItmAmtPgBind() {
        return otherExpItmAmtPgBind;
    }

    public void amtSpNotaValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.412")));
                    //throw new ValidatorException(new FacesMessage("Amount should be greater than Zero."));
                }
            } else
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1107")));
                //throw new ValidatorException(new FacesMessage("Invalid Precision(26,6)."));

        }
    }

    public void amtSpNotaVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            Number notaAmt = (Number) JSFUtils.resolveExpression("#{pageFlowScope.P_AMT_NOTATION}");
            notaAmt = notaAmt.multiply((Number) vce.getNewValue());

            otherExpItmAmtPgBind.setValue(notaAmt);
        }
    }

    public void amountNotationVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

            RequestContext.getCurrentInstance().getPageFlowScope().put("P_AMT_NOTATION", vce.getNewValue());

            Number val = (Number) vce.getNewValue();
            if (val.compareTo(1) > 0) {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "Y");
            } else {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "N");
            }
            RequestContext.getCurrentInstance().getPageFlowScope().put("DISP_AMT_NOTATION",
                                                                       amtNotationDescPgBind.getValue());
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelStretchPgBind);

    }

    public void setAmtNotationDescPgBind(RichOutputText amtNotationDescPgBind) {
        this.amtNotationDescPgBind = amtNotationDescPgBind;
    }

    public RichOutputText getAmtNotationDescPgBind() {
        return amtNotationDescPgBind;
    }

    public void setPanelStretchPgBind(RichPanelStretchLayout panelStretchPgBind) {
        this.panelStretchPgBind = panelStretchPgBind;
    }

    public RichPanelStretchLayout getPanelStretchPgBind() {
        return panelStretchPgBind;
    }

    public BindingContainer getBinding() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void expenseDetailEditAction(ActionEvent actionEvent) {
        // Add event code here...
        //
        Object execute = ADFUtils.findOperation("setCurrentRowToEdit").execute();
        if (execute != null) {

            DCIteratorBinding parentIter = (DCIteratorBinding) getBinding().get("BdgMtlBudgetDtlIterator");
            // Key parentKey = parentIter.getCurrentRow().getKey();
            Key key = (Key) execute;

            parentIter.setCurrentRowWithKey(null);
            parentIter.setCurrentRowWithKey(key.toStringFormat(true));
            //     Object[] keyValues = key.getKeyValues();
            //   parentIter.setCurrentRowWithKeyValue(keyValues.toString());
        }
        showPopup(manualItemPopUpPgBind, true);
    }

    public void expenseDetailDeleteAction(ActionEvent actionEvent) {

        ADFUtils.findOperation("deleteExpenseDetailAction").execute();
    }

    public String prodDistrbCcAction() {
        OperationBinding binding = ADFUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "prdDistrbCc";
        } else {
            return null;
        }
    }

    public String otherChargesCcAction() {
        OperationBinding binding = ADFUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            ADFUtils.findOperation("setDfltLvlVlsToNull").execute();

            return "otherChargesCc";
        } else {
            return null;
        }
    }

    public String dfltCcValuesAction() {
        ADFUtils.findOperation("insertCcAmtAfterPopulateDistrib").execute();

        return null;
    }
}
