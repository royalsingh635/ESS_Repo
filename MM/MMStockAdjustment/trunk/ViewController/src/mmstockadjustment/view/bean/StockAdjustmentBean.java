package mmstockadjustment.view.bean;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;

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

import mmstockadjustment.model.service.MMStockAdjustmentAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectItem;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class StockAdjustmentBean {

    boolean headerReadonly = false;
    boolean childReadonly = false;
    boolean itemVisible = true;
    boolean flag = true;
    boolean itemDisable = false;
    boolean whdisable = false;
    boolean isAdjtType = true;

    private RichSelectOneChoice refDoc;
    private UIXSwitcher bindSwitcher;
    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(StockAdjustmentBean.class);
    private String facetValue = "defaultFacet";
    // private RichPanelFormLayout lotItmPfl;
    private RichPopup popUpBind;
    private RichSelectBooleanCheckbox bindLotSrCheckBox;
    private RichSelectBooleanCheckbox bindSrCheckBox;
    private RichPopup popUpBind1;
    private RichOutputText bindAdjtQty;
    private RichInputListOfValues bindItmDesc;


    private boolean createbtn = false;
    private boolean editbtn = false;
    private boolean savebtn = true;
    private boolean wfbtn = false;
    private String wfNoVar = null;


    private boolean itmDisable = true;
    private String checkAdjType = "false";

    private String mode = null;
    private String stockType = "AD";
    private String stockStatus = "PD";
    private String docTxnId = null;
    private RichSelectOneRadio groupItmBind;


    private boolean itmAddBtn = false;
    private boolean grpAddBtn = true;
    private RichSelectOneChoice bindSearchItem;
    private RichPanelGroupLayout lotItmPfl;
    private RichTable bindItemTable;
    private RichInputText bindAdjtQuantity;
    private RichSelectOneChoice bindAdjtType;
    private RichInputText bindAdjtRemark;
    private RichPanelFormLayout bindFormLayout;

    private boolean msgDisplay = false;
    private RichInputListOfValues bindItmGrp;
    private RichSelectItem grpBind;
    private RichSelectItem itmBind;
    private RichShowDetailItem firstTabBindVar;
    private RichPopup popupVoucherId;
    private RichInputText voucherIdtxt;

    private String var1 = "E";
    private RichCommandImageLink addBtnBinding;
    private RichShowDetailItem secondTabBind;

    public StockAdjustmentBean() {

        facetValue = "defaultFacet";
        mode = (String) evaluateEL("#{pageFlowScope.ADD_EDIT_MODE}");

        if (mode.equals("A")) {
            headerReadonly = false;

            //----------------------------------buttons
            createbtn = true;
            editbtn = true;
            wfbtn = true;
            savebtn = false;

        }

        else if (mode.equals("E")) {
            OperationBinding op = getBindings().getOperationBinding("getCurrentRowStockType");
            op.execute();
            stockType = (String) op.getResult();

            op = getBindings().getOperationBinding("getCurrentRowStockStatus");
            op.execute();
            stockStatus = (String) op.getResult();

            if (stockType.equals("ST") && stockStatus.equals("PD")) {
                itemVisible = false;
                childReadonly = true;
            }

            else if (stockType.equals("AD") && stockStatus.equals("PD")) {
                itemVisible = false;
                childReadonly = true;
            }

            else if (stockType.equals("ST") && stockStatus.equals("SU")) {
                itemVisible = false;
                childReadonly = true;
            }

            else if (stockType.equals("AD") && stockStatus.equals("SU")) {
                itemVisible = false;
                childReadonly = true;
            }

            itemDisable = true;
            headerReadonly = true;


        }
    }

    public void setRefDoc(RichSelectOneChoice refDoc) {
        this.refDoc = refDoc;
    }

    public RichSelectOneChoice getRefDoc() {
        return refDoc;
    }

    public void StockTypeListener(ValueChangeEvent valueChangeEvent) {

        Integer stkType = (Integer) valueChangeEvent.getNewValue();
        if (stkType == 350) {
            itemVisible = false;
        } else {
            itemVisible = true;
            itemDisable = true;
        }
        refDoc.setValue(null);
        OperationBinding op = getBindings().getOperationBinding("cleanUpStkItmVo");
        op.execute();
        refDoc.processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(refDoc);
    }

    public void addItm(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("addItems");
        op.execute();

    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addNewItem(ActionEvent actionEvent) {
        // Add event code here...
        //System.out.println("Value of checkAdjType::" + checkAdjType);
        MMStockAdjustmentAMImpl am = (MMStockAdjustmentAMImpl) resolvElDC("MMStockAdjustmentAMDataControl");
        Integer count = (Integer) am.getSTKADJTITM2().getRowCount();

        if (count > 0) {
            //System.out.println(" Value of bindAdjtType::::" + bindAdjtType.getValue());
            if (bindAdjtType.getValue() == "" || bindAdjtType.getValue() == null) {
                FacesMessage message = new FacesMessage(evaluateEL("Adjustment type is required.").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(bindAdjtType.getClientId(), message);
                return;
            }
        }

        var1 = "D";
        itemDisable = false;
        whdisable = true;
        OperationBinding op = getBindings().getOperationBinding("CreateInsert");
        op.execute();
        if (itmDisable == false) {
            itmDisable = true;
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtQuantity);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtRemark);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtType);
        }
        addItm(actionEvent);
        //checkAdjType = "true";
    }

    public void cancelIemAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("deleteAllAdjustments");
        op.execute();
        var1 = "E";
        itemDisable = true;
        itmDisable = false;
        groupItmBind.setValue("Itm");
        bindItmGrp.setValue("");
        grpAddBtn = true;
    }

    public String createNewItem() {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("CreateInsert2");
        op.execute();
        headerReadonly = false;
        facetValue = "defaultFacet";
        itemVisible = true;
        childReadonly = false;

        createbtn = true;
        editbtn = true;
        wfbtn = true;
        savebtn = false;

        return null;
    }

    public String copyAllItems() {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("copyAllItemByGroup");
        op.execute();
        Boolean b = (Boolean) op.getResult();

        if (b) {
            itmDisable = false;
            isAdjtType = false;
            adfLog.info("----------inside copyALlItems Action--- " + b + " " + itmDisable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtQuantity);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtRemark);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtType);
        }
        adfLog.info("----------outside false copyALlItems Action--- " + b + " " + itmDisable);

        itemDisable = true;
        whdisable = true;
        return null;
    }

    public void stockAdjustmentListener(DisclosureEvent disclosureEvent) {
        /*   OperationBinding chkType = getBindings().getOperationBinding("chkAjdType");
                    chkType.execute();
                    adfLog.info("------------getting value-stockAdjustmentListener----------- "+chkType.getResult());
                    if(chkType.getResult() != null && chkType.getResult().toString().equals("Y")){
                        FacesMessage message = new FacesMessage("Adjustment Type is Required.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(bindAdjtType.toString(), message);
                        firstTabBindVar.setDisclosed(false);
                        return ;

                    }else{
                        isAdjtType = true;
                        AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtType);
                    } */
        if (disclosureEvent.isExpanded()) {
            OperationBinding chkType = getBindings().getOperationBinding("chkAjdType");
            chkType.execute();
            adfLog.info("------------getting value-stockAdjustmentListener----------- " + chkType.getResult());
            if (chkType.getResult() != null && chkType.getResult().toString().equals("Y")) {
                FacesMessage message = new FacesMessage("Some Item required Adjustment Type");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(bindAdjtType.toString(), message);
                firstTabBindVar.setDisclosed(true);
                adfLog.info("isdisclod first and sec " + firstTabBindVar.isDisclosed() + " " +
                            secondTabBind.isDisclosed());
                AdfFacesContext.getCurrentInstance().addPartialTarget(firstTabBindVar);
                secondTabBind.setDisclosed(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(firstTabBindVar);

                return;
            } else {
                isAdjtType = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtType);
            }


            OperationBinding op = getBindings().getOperationBinding("refreshTrnsVo");
            op.execute();

            op = getBindings().getOperationBinding("setTransVo");
            op.getParamsMap().put("mode", mode);
            op.getParamsMap().put("stockType", stockType);
            op.execute();

            setFacetValue("defaultFacet");
            facetValue = "defaultFacet";
            msgDisplay = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindSwitcher);
        }
    }

    public String createLotAction() {
        // Add event code here...

        OperationBinding op = getBindings().getOperationBinding("CreateInsert1");
        op.execute();
        return null;
    }

    public void deleteLotAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("Delete1");
        op.execute();

    }

    public void updateBinByLotListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("updateBinByLot");
        op.execute();
    }

    public void setBindSwitcher(UIXSwitcher bindSwitcher) {
        this.bindSwitcher = bindSwitcher;
    }

    public UIXSwitcher getBindSwitcher() {
        return bindSwitcher;
    }

    public void setFacetValue(String facetValue) {
        this.facetValue = facetValue;
    }

    public String getFacetValue() {
        return facetValue;
    }

    public String searchAction() {
        // Add event code here...

        if (bindSearchItem.getValue() == null || bindSearchItem.getValue().toString().length() <= 0) {
            FacesMessage message = new FacesMessage(evaluateEL("#{bundle['MSG.586']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(bindSearchItem.getClientId(), message);
        }

        else {
            OperationBinding op = getBindings().getOperationBinding("findFacet");
            op.execute();

            String flag = (String) op.getResult();
            setFacetValue(flag);
            facetValue = flag;

            if (facetValue.equals("ViewFacet")) {
                op = getBindings().getOperationBinding("executeAdjtViewObject");
                op.execute();
            }

            else {
                /*   op=  getBindings().getOperationBinding("executeViewObject");
                op.getParamsMap().put("voName",flag);
                op.execute();
             */

                op = getBindings().getOperationBinding("executeViewObject_1");
                op.getParamsMap().put("voName", flag);
                op.execute();
                if (op.getResult() != null) {
                    Object obj = op.getResult();
                    Integer rply = (Integer) obj;

                    if (rply == 0) {
                        msgDisplay = true;
                    } else {
                        msgDisplay = false;
                    }
                } else {
                    msgDisplay = false;

                }
            }


            op = getBindings().getOperationBinding("getAdjustableQty");
            op.execute();
            Object ob = op.getResult();
            if (ob != null) {
                bindAdjtQty.setValue(ob);
            } else {
                bindAdjtQty.setValue("0");
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(lotItmPfl);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindSwitcher);
        }
        return null;
    }


    public void findFacetListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

    }

    public void LotAdjustValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        OperationBinding op = getBindings().getOperationBinding("addRowInAdjstLot");
        op.getParamsMap().put("oldValue", valueChangeEvent.getOldValue());
        op.getParamsMap().put("newValue", valueChangeEvent.getNewValue());
        op.execute();


    }

    public void SrNoListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean flag = (Boolean) valueChangeEvent.getNewValue();
        if (flag) {
            OperationBinding op = getBindings().getOperationBinding("isSrQuantityExceed");
            op.execute();
            Boolean validate = (Boolean) op.getResult();
            if (validate) {
                showPopup(popUpBind1, true);
                bindSrCheckBox.setValue(false);
            } else {
                op = getBindings().getOperationBinding("addRowInAdjtSr");
                op.execute();
            }
        } else {
            OperationBinding op = getBindings().getOperationBinding("subRowInAdjtSr");
            op.execute();
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(bindSrCheckBox);

    }

    public String refreshUserSRVoAction() {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("refreshUserSrVOAction");
        op.execute();
        return null;
    }

    public String SaveAction() {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("validateAdjtForm");
        op.execute();
        String flag = (String) op.getResult();
        if (flag.equals("MISS_MATCH")) {
            FacesMessage message = new FacesMessage(evaluateEL("#{bundle['LBL.2017']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return "Error";
        }

        else if (flag.equals("CHANGE_QTY_TYPE")) {
            FacesMessage message = new FacesMessage(evaluateEL("#{bundle['MSG.608']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return "Error";
        }

        else if (flag.equals("COMMIT")) {

            OperationBinding opsaved = getBindings().getOperationBinding("CheckSaved");
            opsaved.execute();
            String checksav = opsaved.getResult().toString();
            if (checksav.equals("-1")) {
                //----------------------------------------------------WORK FLOW BEGIN------------------------------------------------------------------------------


                String OrgId = evaluateEL("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
                Integer SlocId = Integer.parseInt(evaluateEL("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
                String CldId = evaluateEL("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
                String WfNo = "0";
                String UsrId = evaluateEL("#{pageFlowScope.GLBL_APP_USR}").toString();
                Integer level = 0;
                String action = "I";
                String remark = "A";
                Integer res = -1;
                //get Wf Id
                OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                WfnoOp.getParamsMap().put("SlocId", SlocId);
                WfnoOp.getParamsMap().put("CldId", CldId);
                WfnoOp.getParamsMap().put("OrgId", OrgId);
                WfnoOp.getParamsMap().put("DocNo", 18517);
                WfnoOp.execute();
                if (WfnoOp.getResult() != null) {
                    WfNo = WfnoOp.getResult().toString();
                }
                //  System.out.println("WfNo="+WfNo);

                //get user level
                OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", SlocId);
                usrLevelOp.getParamsMap().put("CldId", CldId);
                usrLevelOp.getParamsMap().put("OrgId", OrgId);
                usrLevelOp.getParamsMap().put("UsrId", UsrId);
                usrLevelOp.getParamsMap().put("WfNo", WfNo);
                usrLevelOp.getParamsMap().put("DocNo", 18517);
                usrLevelOp.execute();
                if (usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                //     System.out.println("Level="+level);

                //insert into txn
                OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                insTxnOp.getParamsMap().put("SlocId", SlocId);
                insTxnOp.getParamsMap().put("CldId", CldId);
                insTxnOp.getParamsMap().put("OrgId", OrgId);
                insTxnOp.getParamsMap().put("DocNo", 18517);
                insTxnOp.getParamsMap().put("WfNo", WfNo);
                insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                insTxnOp.getParamsMap().put("levelFrm", level);
                insTxnOp.getParamsMap().put("levelTo", level);
                insTxnOp.getParamsMap().put("action", action);
                insTxnOp.getParamsMap().put("remark", remark);
                insTxnOp.getParamsMap().put("amount", 0);
                insTxnOp.execute();

                if (insTxnOp.getResult() != null) {
                    res = Integer.parseInt(insTxnOp.getResult().toString());
                }
                //------------------------------------------------------------------------WORK FLOW END-------------------------------------------------------------
            } ///Check Saved End
            op = getBindings().getOperationBinding("beforeCommitAction");
            op.execute();
            op = getBindings().getOperationBinding("Commit");
            op.execute();
            afterCommitAction();

            FacesMessage message = new FacesMessage(evaluateEL("#{bundle['MSG.91']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);


            firstTabBindVar.setDisclosed(true);
        }

        return null;
    }

    public void BinAdjustmentChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...


        OperationBinding op = getBindings().getOperationBinding("addRowInAdjstBin");
        op.getParamsMap().put("oldValue", valueChangeEvent.getOldValue());
        op.getParamsMap().put("newValue", valueChangeEvent.getNewValue());
        op.execute();
    }

    public void LotSrValueChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean flag = (Boolean) valueChangeEvent.getNewValue();
        if (flag) {
            OperationBinding op = getBindings().getOperationBinding("isLotSrQuantityExceed");
            op.execute();
            Boolean validate = (Boolean) op.getResult();
            if (validate) {
                bindLotSrCheckBox.setValue(false);
                showPopup(popUpBind, true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(bindLotSrCheckBox);
                AdfFacesContext.getCurrentInstance().addPartialTarget(popUpBind);

            } else {
                op = getBindings().getOperationBinding("addRowInAdjtLotSr");
                op.execute();
            }
        } else {
            OperationBinding op = getBindings().getOperationBinding("subRowInAdjtLotSr");
            op.execute();
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(bindLotSrCheckBox);
        AdfFacesContext.getCurrentInstance().addPartialTarget(popUpBind);
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


    public void setPopUpBind(RichPopup popUpBind) {
        this.popUpBind = popUpBind;
    }

    public RichPopup getPopUpBind() {
        return popUpBind;
    }

    public void OkDialogListener(DialogEvent dialogEvent) {

        bindLotSrCheckBox.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindLotSrCheckBox);
        AdfFacesContext.getCurrentInstance().addPartialTarget(popUpBind);

    }

    public void setBindLotSrCheckBox(RichSelectBooleanCheckbox bindLotSrCheckBox) {
        this.bindLotSrCheckBox = bindLotSrCheckBox;
    }

    public RichSelectBooleanCheckbox getBindLotSrCheckBox() {
        return bindLotSrCheckBox;
    }

    public void setBindSrCheckBox(RichSelectBooleanCheckbox bindSrCheckBox) {
        this.bindSrCheckBox = bindSrCheckBox;
    }

    public RichSelectBooleanCheckbox getBindSrCheckBox() {
        return bindSrCheckBox;
    }

    public void setPopUpBind1(RichPopup popUpBind1) {
        this.popUpBind1 = popUpBind1;
    }

    public RichPopup getPopUpBind1() {
        return popUpBind1;
    }

    public void LotValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        if (object != null) {
            BigDecimal bcNumber = (BigDecimal) object;
            if (bcNumber.compareTo(BigDecimal.ZERO) != 0) {
                Number n = null;

                try {
                    n = new Number(bcNumber);
                } catch (SQLException e) {
                }

                if (n.compareTo(oracle.jbo.domain.Number.zero()) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  (String) evaluateEL("#{bundle['MSG.553']}"), null));
                }
                if (n != null) {
                    boolean valid = isPrecisionValid(26, 6, n);
                    if (!valid) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      (String) evaluateEL("#{bundle['MSG.57']}"),
                                                                      null));
                    }

                    BigDecimal bd = (BigDecimal) object;
                    OperationBinding op = getBindings().getOperationBinding("isLotQtyValid");
                    op.getParamsMap().put("obj", bd);
                    op.execute();
                    Boolean b = (Boolean) op.getResult();
                    if (b) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      evaluateEL("#{bundle['MSG.609']}").toString(),
                                                                      null));
                    }
                }
            }
        }
    }


    public void BinValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            BigDecimal bcNumber = (BigDecimal) object;

            if (bcNumber.compareTo(BigDecimal.ZERO) != 0) {
                Number n = null;
                try {
                    n = new Number(bcNumber);
                } catch (SQLException e) {
                }

                if (n.compareTo(oracle.jbo.domain.Number.zero()) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  (String) evaluateEL("#{bundle['MSG.553']}"), null));
                }
                if (n != null) {
                    boolean valid = isPrecisionValid(26, 6, n);
                    if (!valid) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      (String) evaluateEL("#{bundle['MSG.57']}"),
                                                                      null));
                    }
                }
                BigDecimal bd = (BigDecimal) object;
                OperationBinding op = getBindings().getOperationBinding("isBinQtyValid");
                op.getParamsMap().put("obj", bd);
                op.execute();
                Boolean b = (Boolean) op.getResult();
                if (b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  evaluateEL("#{bundle['MSG.609']}").toString(), null));
                }
            }
        }
    }

    public void setBindAdjtQty(RichOutputText bindAdjtQty) {
        this.bindAdjtQty = bindAdjtQty;
    }

    public RichOutputText getBindAdjtQty() {
        return bindAdjtQty;
    }

    public String refreshTransactionVo() {
        // Add event code here...

        OperationBinding op = getBindings().getOperationBinding("refreshTrnsVo");
        op.execute();
        return null;
    }

    public void QtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        ADFLogger _logger = ADFLogger.createADFLogger(getClass());


        if (object == null || object.toString().length() <= 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          (String) evaluateEL("#{bundle['MSG.572']}"), null));
        } else {
            BigDecimal bcNumber = (BigDecimal) object;
            Number n = null;
            try {
                n = new Number(bcNumber);
            } catch (SQLException e) {
            }

            if (n != null) {
                boolean valid = isPrecisionValid(26, 6, n);
                if (!valid) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  (String) evaluateEL("#{bundle['MSG.57']}"), null));
                }
            }
            BigDecimal bd = (BigDecimal) object;
            OperationBinding op = getBindings().getOperationBinding("isStkItmSummQty");
            op.getParamsMap().put("obj", bd);
            bindAdjtType.processUpdates(facesContext);
            op.getParamsMap().put("type", (String) bindAdjtType.getValue());
            op.execute();
            BigDecimal b = (BigDecimal) op.getResult();


            //System.out.println("Quantity : "+b  +"  ZERO : "+BigDecimal.ZERO + "Compare : "+b.compareTo(BigDecimal.ZERO));

            if (b.compareTo(BigDecimal.ZERO) >= 0 && ((String) bindAdjtType.getValue()).equals("S")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "You Can not Subtract more than " + b + " Quantity.",
                                                              null));
            }

        }

    }


    public String commitAction() {
        // Add event code here...
        return null;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setItemDisable(boolean itemDisable) {
        this.itemDisable = itemDisable;
    }

    public boolean isItemDisable() {
        return itemDisable;
    }

    public void itemChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        Object object = valueChangeEvent.getNewValue();
        adfLog.info("-------inside valuechange listener------ " + object);
        if (object != null) {
            var1 = "E";
            String itmName = object.toString();
            OperationBinding op = getBindings().getOperationBinding("duplicateValue");
            op.getParamsMap().put("itmName", itmName);
            op.execute();
            Boolean isError = (Boolean) op.getResult();
            adfLog.info("-------inside valuechange listener1------ " + isError);
            if (isError) {
                var1 = "D";
                String msg = evaluateEL("#{bundle['MSG.427']}").toString();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(bindItmDesc.getClientId(), message);
            } else {
                isAdjtType = false;
                itmDisable = false;
                itemDisable = true;
            }

        } else {
            FacesMessage message = new FacesMessage("Please Enter the Item.");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        adfLog.info("-------inside valuechange listener last------ ");
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindItmDesc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindItemTable);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(bindItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addBtnBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtQuantity);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtRemark);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtType);

    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void ItemDescValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //       adfLog.info("-----------------inside validator------ " + object);
        if (object != null) {
            String itmName = object.toString();
            OperationBinding op = getBindings().getOperationBinding("duplicateValue");
            op.getParamsMap().put("itmName", itmName);
            op.execute();
            Boolean isError = (Boolean) op.getResult();
            adfLog.info("-----------------inside validator1------ " + isError);
            if (isError) {
                adfLog.info("-----------------inside validator2------ " + isError);
                FacesMessage message2 = new FacesMessage(evaluateEL("#{bundle['MSG.427']}").toString());
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            adfLog.info("-----------------inside validator3------ " + isError);
        }

    }

    public void setBindItmDesc(RichInputListOfValues bindItmDesc) {
        this.bindItmDesc = bindItmDesc;
    }

    public RichInputListOfValues getBindItmDesc() {
        return bindItmDesc;
    }


    public void setItemVisible(boolean itemVisible) {
        this.itemVisible = itemVisible;
    }

    public boolean isItemVisible() {
        return itemVisible;
    }

    public void copyItemsFrmStkTk(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("copyAllItemByStkTakenId");
        op.getParamsMap().put("txnId", valueChangeEvent.getNewValue());
        op.execute();
    }

    public String rollBackAction() {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("Rollback");
        op.execute();
        return "returnBack";
    }


    public void setHeaderReadonly(boolean headerReadonly) {
        this.headerReadonly = headerReadonly;
    }

    public boolean isHeaderReadonly() {
        return headerReadonly;
    }

    public static Object evaluateEL(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
        return exp.getValue(elContext);
    }


    public void setChildReadonly(boolean childReadonly) {
        this.childReadonly = childReadonly;
    }

    public boolean isChildReadonly() {
        return childReadonly;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }


    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getStockStatus() {
        return stockStatus;
    }


    private String SaveAndForward() {

        OperationBinding opr = getBindings().getOperationBinding("refreshTrnsVo");
        opr.execute();

        opr = getBindings().getOperationBinding("setTransVo_");
        opr.getParamsMap().put("mode", mode);
        opr.getParamsMap().put("stockType", stockType);
        opr.execute();


        OperationBinding op = getBindings().getOperationBinding("validateAdjtForm");
        op.execute();
        String flag = (String) op.getResult();
        if (flag.equals("MISS_MATCH")) {
            FacesMessage message = new FacesMessage(evaluateEL("#{bundle['LBL.2017']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return "Error";
        }

        else if (flag.equals("CHANGE_QTY_TYPE")) {
            FacesMessage message = new FacesMessage(evaluateEL("#{bundle['MSG.608']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return "Error";
        }

        else if (flag.equals("COMMIT")) {

            OperationBinding opsaved = getBindings().getOperationBinding("CheckSaved");
            opsaved.execute();
            String checksav = opsaved.getResult().toString();
            if (checksav.equals("-1")) {
                //----------------------------------------------------WORK FLOW BEGIN------------------------------------------------------------------------------


                String OrgId = evaluateEL("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
                Integer SlocId = Integer.parseInt(evaluateEL("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
                String CldId = evaluateEL("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
                String WfNo = "0";
                String UsrId = evaluateEL("#{pageFlowScope.GLBL_APP_USR}").toString();
                Integer level = 0;
                String action = "I";
                String remark = "A";
                Integer res = -1;
                //get Wf Id
                OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                WfnoOp.getParamsMap().put("SlocId", SlocId);
                WfnoOp.getParamsMap().put("CldId", CldId);
                WfnoOp.getParamsMap().put("OrgId", OrgId);
                WfnoOp.getParamsMap().put("DocNo", 18517);
                WfnoOp.execute();
                if (WfnoOp.getResult() != null) {
                    WfNo = WfnoOp.getResult().toString();
                }
                //  System.out.println("WfNo="+WfNo);

                //get user level
                OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", SlocId);
                usrLevelOp.getParamsMap().put("CldId", CldId);
                usrLevelOp.getParamsMap().put("OrgId", OrgId);
                usrLevelOp.getParamsMap().put("UsrId", UsrId);
                usrLevelOp.getParamsMap().put("WfNo", WfNo);
                usrLevelOp.getParamsMap().put("DocNo", 18517);
                usrLevelOp.execute();
                if (usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                //     System.out.println("Level="+level);

                //insert into txn
                OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                insTxnOp.getParamsMap().put("SlocId", SlocId);
                insTxnOp.getParamsMap().put("CldId", CldId);
                insTxnOp.getParamsMap().put("OrgId", OrgId);
                insTxnOp.getParamsMap().put("DocNo", 18517);
                insTxnOp.getParamsMap().put("WfNo", WfNo);
                insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                insTxnOp.getParamsMap().put("levelFrm", level);
                insTxnOp.getParamsMap().put("levelTo", level);
                insTxnOp.getParamsMap().put("action", action);
                insTxnOp.getParamsMap().put("remark", remark);
                insTxnOp.getParamsMap().put("amount", 0);
                insTxnOp.execute();

                if (insTxnOp.getResult() != null) {
                    res = Integer.parseInt(insTxnOp.getResult().toString());
                }
                //------------------------------------------------------------------------WORK FLOW END-------------------------------------------------------------
            } ///Check Saved End
            op = getBindings().getOperationBinding("beforeCommitAction");
            op.execute();
            op = getBindings().getOperationBinding("Commit");
            op.execute();
            afterCommitAction();
        }

        return "SUCCESS";
    }


    public String workFlowActionUpdate() {
        // Add event code here...
        String paramOrgId = evaluateEL("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String paramCldId = evaluateEL("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer paramSlocId = Integer.parseInt(evaluateEL("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

        String WfNo = null;
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", paramSlocId);
        WfnoOp.getParamsMap().put("CldId", paramCldId);
        WfnoOp.getParamsMap().put("OrgId", paramOrgId);
        WfnoOp.getParamsMap().put("DocNo", 18517);
        WfnoOp.execute();
        if (WfnoOp.getResult() != null) {
            WfNo = WfnoOp.getResult().toString();
        }
        RequestContext.getCurrentInstance().getPageFlowScope().put("WF_ID_PARAM", WfNo);

        String TxnId = null;
        OperationBinding gettxn = getBindings().getOperationBinding("getTxnId");
        gettxn.execute();


        if (gettxn.getResult() != null) {
            TxnId = gettxn.getResult().toString();
            docTxnId = TxnId;
        }


        OperationBinding opr = getBindings().getOperationBinding("isInvcAuth");
        opr.execute();
        Object obj = opr.getResult();
        if (obj != null) {
            Boolean b = (Boolean) obj;
            if (b) {
                String msg2 = evaluateEL("#{bundle['LBL.3109']}").toString();
                //String msg2 ="Invoice Already Approved.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            }
        }


        String saved = SaveAndForward();
        if (saved != null && saved.equalsIgnoreCase("Error")) {
            return null;
        }

        String rply = saveAndForwardAction();
        if (rply.startsWith("PENDING")) {
            Integer index = rply.indexOf("^");
            String msg2 = evaluateEL("#{bundle['MSG.1054']}").toString();

            gettxn = getBindings().getOperationBinding("getUserName");
            gettxn.getParamsMap().put("userId", Integer.parseInt(rply.substring(index + 1)));
            gettxn.execute();

            //String msg2 = "This Slip is pending at other user.";
            FacesMessage message2 = new FacesMessage(msg2 + "   [ User : " + gettxn.getResult() + " ]");
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }

        /*    else if(rply.equalsIgnoreCase("SUCCESS"))
        {
            OperationBinding saveOp = getBindings().getOperationBinding("Commit");
            saveOp.execute();
            return "toWf";
        }
      */
        else if (rply.equalsIgnoreCase("ALREADY_SAVED")) {
            return "toWf";
        }

        else {
            // String msg2 = "There is Some Problem in WF Please Contact to ESS INDIA.";

            String msg2 = evaluateEL("#{bundle['MSG.1159']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }

    }


    public String saveAndForwardAction() {

        /*    Integer paramUsrId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId= resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
      */
        String paramOrgId = evaluateEL("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String paramCldId = evaluateEL("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer paramSlocId = Integer.parseInt(evaluateEL("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String WfNo = "0";
        String UsrId = evaluateEL("#{pageFlowScope.GLBL_APP_USR}").toString();
        Integer level = 0;
        String action = "I";
        String remark = "A";
        Integer res = -1;
        String currUser = evaluateEL("#{pageFlowScope.GLBL_APP_USR}").toString();


        OperationBinding opsaved = getBindings().getOperationBinding("CheckSaved");
        opsaved.execute();
        String checksav = opsaved.getResult().toString();
        OperationBinding opce = getBindings().getOperationBinding("refreshVos");
        opce.execute();


        if (checksav.equals("1")) {

            OperationBinding opr = getBindings().getOperationBinding("pendingCheck");
            opr.getParamsMap().put("SlocId", paramSlocId);
            opr.getParamsMap().put("CldId", paramCldId);
            opr.getParamsMap().put("OrgId", paramOrgId);
            opr.getParamsMap().put("DocNo", 18517);
            opr.execute();

            Object pendUser = opr.getResult();
            if (pendUser != null) {
                if (!currUser.equals(pendUser.toString())) {
                    return "PENDING" + "^" + pendUser.toString();
                }
            }

            return "ALREADY_SAVED";
        }


        /*

        //get Wf Id
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", paramSlocId);
        WfnoOp.getParamsMap().put("CldId", paramCldId);
        WfnoOp.getParamsMap().put("OrgId", paramOrgId);
        WfnoOp.getParamsMap().put("DocNo", 18517);
        WfnoOp.execute();
        if(WfnoOp.getResult()!=null){
        WfNo= WfnoOp.getResult().toString();
        }

        //get user level
       OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
       usrLevelOp.getParamsMap().put("SlocId", paramSlocId);
       usrLevelOp.getParamsMap().put("CldId", paramCldId);
       usrLevelOp.getParamsMap().put("OrgId", paramOrgId);
       usrLevelOp.getParamsMap().put("UsrId", UsrId);
       usrLevelOp.getParamsMap().put("WfNo", WfNo);
       usrLevelOp.getParamsMap().put("DocNo", 18517);
       usrLevelOp.execute();
       if(usrLevelOp.getResult()!=null){
       level= Integer.parseInt(usrLevelOp.getResult().toString());
       }


        //insert into wf$txn
     OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
     insTxnOp.getParamsMap().put("SlocId", paramSlocId);
     insTxnOp.getParamsMap().put("CldId", paramCldId);
     insTxnOp.getParamsMap().put("OrgId", paramOrgId);
     insTxnOp.getParamsMap().put("DocNo", 18517);
     insTxnOp.getParamsMap().put("WfNo", WfNo);
     insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
     insTxnOp.getParamsMap().put("usr_idTo", UsrId);
     insTxnOp.getParamsMap().put("levelFrm", level);
     insTxnOp.getParamsMap().put("levelTo", level);
     insTxnOp.getParamsMap().put("action", action);
     insTxnOp.getParamsMap().put("remark", remark);
     insTxnOp.getParamsMap().put("amount", 0);
     insTxnOp.execute();

    if(insTxnOp.getResult()!=null){
    res= Integer.parseInt(insTxnOp.getResult().toString());
    }*/

        return "FAILURE";
    }

    public void setDocTxnId(String docTxnId) {
        this.docTxnId = docTxnId;
    }

    public String getDocTxnId() {
        return docTxnId;
    }

    public String editAction() {
        // Add event code here...
        String OrgId = evaluateEL("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(evaluateEL("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = evaluateEL("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

        String currUser = evaluateEL("#{pageFlowScope.GLBL_APP_USR}").toString();

        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", SlocId);
        chkUsr.getParamsMap().put("CldId", CldId);
        chkUsr.getParamsMap().put("OrgId", OrgId);
        chkUsr.getParamsMap().put("DocNo", 18517);
        chkUsr.execute();
        Object pendUser = (Object) chkUsr.getResult();


        if (pendUser != null) {
            //|| "-1".equalsIgnoreCase(pendUser.toString())
            if (currUser.equals(pendUser.toString())) {
                childReadonly = false;

                itmDisable = false;
                if (stockType.equals("AD") && stockStatus.equals("PD")) {
                    itemVisible = true;
                    //----------------------------------buttons
                    createbtn = true;
                    editbtn = true;
                    wfbtn = true;
                    savebtn = false;
                }
            } else {

                OperationBinding opr = getBindings().getOperationBinding("isInvcAuth");
                opr.execute();
                Object obj = opr.getResult();
                if (obj != null) {
                    Boolean b = (Boolean) obj;
                    if (b) {
                        String msg2 = "Adjustment Already Approved.";
                        //evaluateEL("#{bundle['LBL.3109']}").toString();
                        //String msg2 ="Invoice Already Approved.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2);
                        return null;
                    }
                }

                OperationBinding gettxn = getBindings().getOperationBinding("getUserName");
                gettxn.getParamsMap().put("userId", Integer.parseInt(pendUser.toString()));
                gettxn.execute();
                System.out.println("User Name::::" + gettxn.getResult());
                //String msg2 = "This Slip is pending at other user.";

                String msg2 = evaluateEL("#{bundle['MSG.612']}").toString();
                FacesMessage message2 = new FacesMessage(msg2 + "   [ User : " + gettxn.getResult() + " ]");
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
        }

        return null;
    }


    public void afterCommitAction() {
        OperationBinding refVo = getBindings().getOperationBinding("refreshVos");
        refVo.execute();

        childReadonly = true;
        headerReadonly = true;
        itemVisible = false;

        //----------------------------------buttons
        createbtn = false;
        editbtn = false;
        wfbtn = false;
        savebtn = true;
    }


    public void setCreatebtn(boolean createbtn) {
        this.createbtn = createbtn;
    }

    public boolean isCreatebtn() {
        return createbtn;
    }

    public void setEditbtn(boolean editbtn) {
        this.editbtn = editbtn;
    }

    public boolean isEditbtn() {
        return editbtn;
    }

    public void setSavebtn(boolean savebtn) {
        this.savebtn = savebtn;
    }

    public boolean isSavebtn() {
        return savebtn;
    }

    public void setWfbtn(boolean wfbtn) {
        this.wfbtn = wfbtn;
    }

    public boolean isWfbtn() {
        return wfbtn;
    }

    public void groupItemChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        String selectType = (String) valueChangeEvent.getNewValue();

        // System.out.println(itemDisable);


        if (selectType.equals("Grp")) {
            itmAddBtn = true;
            grpAddBtn = false;
            bindItmGrp.setValue("");
        }

        //      else if(selectType.equals("Grp") && !itemDisable)
        //     {groupItmBind.setValue("Itm");}

        if (selectType.equals("Itm")) {

            itmAddBtn = false;
            grpAddBtn = true;
            bindItmGrp.setValue("");
        }

    }

    public void setIsAdjtType(boolean isAdjtType) {
        this.isAdjtType = isAdjtType;
    }

    public boolean isIsAdjtType() {
        return isAdjtType;
    }

    public void setGroupItmBind(RichSelectOneRadio groupItmBind) {
        this.groupItmBind = groupItmBind;
    }

    public RichSelectOneRadio getGroupItmBind() {
        return groupItmBind;
    }

    public void setItmAddBtn(boolean itmAddBtn) {
        this.itmAddBtn = itmAddBtn;
    }

    public boolean isItmAddBtn() {
        return itmAddBtn;
    }

    public void setGrpAddBtn(boolean grpAddBtn) {
        this.grpAddBtn = grpAddBtn;
    }

    public boolean isGrpAddBtn() {
        return grpAddBtn;
    }

    public void setBindSearchItem(RichSelectOneChoice bindSearchItem) {
        this.bindSearchItem = bindSearchItem;
    }

    public RichSelectOneChoice getBindSearchItem() {
        return bindSearchItem;
    }

    public void setWhdisable(boolean whdisable) {
        this.whdisable = whdisable;
    }

    public boolean isWhdisable() {
        return whdisable;
    }

    public void setLotItmPfl(RichPanelGroupLayout lotItmPfl) {
        this.lotItmPfl = lotItmPfl;
    }

    public RichPanelGroupLayout getLotItmPfl() {
        return lotItmPfl;
    }

    public void setBindItemTable(RichTable bindItemTable) {
        this.bindItemTable = bindItemTable;
    }

    public RichTable getBindItemTable() {
        return bindItemTable;
    }

    public void setItmDisable(boolean itmDisable) {
        this.itmDisable = itmDisable;
    }

    public boolean isItmDisable() {
        return itmDisable;
    }

    public void setBindAdjtQuantity(RichInputText bindAdjtQuantity) {
        this.bindAdjtQuantity = bindAdjtQuantity;
    }

    public RichInputText getBindAdjtQuantity() {
        return bindAdjtQuantity;
    }

    public void setBindAdjtType(RichSelectOneChoice bindAdjtType) {
        this.bindAdjtType = bindAdjtType;
    }

    public RichSelectOneChoice getBindAdjtType() {
        return bindAdjtType;
    }

    public void setBindAdjtRemark(RichInputText bindAdjtRemark) {
        this.bindAdjtRemark = bindAdjtRemark;
    }

    public RichInputText getBindAdjtRemark() {
        return bindAdjtRemark;
    }

    public void callAdjtTypeListener(ValueChangeEvent valueChangeEvent) {
        //   isAdjtType = true;
        bindAdjtQuantity.processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtQuantity);
        //  AdfFacesContext.getCurrentInstance().addPartialTarget(bindFormLayout);

        //  AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtRemark);
        if (bindItmGrp.getValue() != null) {

        } else {
            isAdjtType = true;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindAdjtType);
    }

    public void setBindFormLayout(RichPanelFormLayout bindFormLayout) {
        this.bindFormLayout = bindFormLayout;
    }

    public RichPanelFormLayout getBindFormLayout() {
        return bindFormLayout;
    }

    public void setMsgDisplay(boolean msgDisplay) {
        this.msgDisplay = msgDisplay;
    }

    public boolean isMsgDisplay() {
        return msgDisplay;
    }

    public void setBindItmGrp(RichInputListOfValues bindItmGrp) {
        this.bindItmGrp = bindItmGrp;
    }

    public RichInputListOfValues getBindItmGrp() {
        return bindItmGrp;
    }

    public void setGrpBind(RichSelectItem grpBind) {
        this.grpBind = grpBind;
    }

    public RichSelectItem getGrpBind() {
        return grpBind;
    }

    public void setItmBind(RichSelectItem itmBind) {
        this.itmBind = itmBind;
    }

    public RichSelectItem getItmBind() {
        return itmBind;
    }


    public void setWfNoVar(String wfNoVar) {
        this.wfNoVar = wfNoVar;
    }

    public String getWfNoVar() {
        return wfNoVar;
    }

    public void setFirstTabBindVar(RichShowDetailItem firstTabBindVar) {
        this.firstTabBindVar = firstTabBindVar;
    }

    public RichShowDetailItem getFirstTabBindVar() {
        return firstTabBindVar;
    }

    public void setPopupVoucherId(RichPopup popupVoucherId) {
        this.popupVoucherId = popupVoucherId;
    }

    public RichPopup getPopupVoucherId() {
        return popupVoucherId;
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            MMStockAdjustmentAMImpl am = (MMStockAdjustmentAMImpl) resolvElDC("MMStockAdjustmentAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            return st.getObject(1);

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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


    public void setVoucherIdtxt(RichInputText voucherIdtxt) {
        this.voucherIdtxt = voucherIdtxt;
    }

    public RichInputText getVoucherIdtxt() {
        return voucherIdtxt;
    }


    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar1() {
        return var1;
    }

    public void setAddBtnBinding(RichCommandImageLink addBtnBinding) {
        this.addBtnBinding = addBtnBinding;
    }

    public RichCommandImageLink getAddBtnBinding() {
        return addBtnBinding;
    }

    public void setSecondTabBind(RichShowDetailItem secondTabBind) {
        this.secondTabBind = secondTabBind;
    }

    public RichShowDetailItem getSecondTabBind() {
        return secondTabBind;
    }
}
