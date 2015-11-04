package mmsoctktakeapp.view.bean;


import java.math.BigDecimal;

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
import oracle.adf.view.rich.component.rich.nav.RichGoImageLink;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.render.ClientEvent;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.component.rich.output.RichPanelCollection;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class StockTakingBean {
    private RichInputListOfValues profileBinding;
    private RichSelectOneChoice criteriaBinding;
    private String Crtype = null;
    private RichPanelGroupLayout binPanelGroup;
    private RichPanelGroupLayout serialPanelGroup;
    private RichSelectOneChoice itemIdBind;
    private String mode = "VW";
    private RichCommandImageLink addlot;
    private RichCommandImageLink saveLot;
    private RichCommandImageLink addBin;
    private RichCommandImageLink saveBin;
    private RichCommandImageLink addSr;
    private RichCommandImageLink saveSr;
    private RichSelectOneChoice whIdBinding;
    private RichInputDate dateBinding;
    private Boolean swh;
    private Boolean sitm;
    private Boolean slot;
    private Boolean sbin;
    private Boolean ssr;
    private Boolean cstVis;
    private Boolean estVis;
    private Boolean sduVis;
    private Boolean eduVis;
    private String TxnId = "";
    private Boolean msgonpop = true;
    private Boolean fwdVis;
    private RichCommandImageLink additmBinding;
    private RichInputListOfValues itmnamebinding;
    private RichSelectOneChoice itmwhbinding;
    private RichSelectBooleanCheckbox selectallbinding;
    private RichPanelLabelAndMessage itemIdbinding;
    private RichOutputText itemidbinding;
    private RichCommandImageLink okbuttonbinding;
    private Boolean dt;
    private Boolean prf;
    private RichSelectOneChoice searchWhBinding;
    private RichSelectOneChoice searchItmBinding;
    private RichSelectOneChoice searchLotBinding;
    private RichSelectOneChoice searchBinBinding;
    private RichSelectOneChoice searchSrBinding;
    private RichPanelCollection searchSrResult;
    private RichPanelCollection searchBinResult;
    private RichColumn searchSrBinColumn;
    private RichColumn searchBinBinColumn;
    private RichPanelCollection searchLotResult;
    private RichCommandImageLink saveDataUpdate;
    private RichColumn binDiffQtyBinding;
    private RichPanelCollection srByBinBinding;
    private RichPanelCollection srByLotBinding;
    private RichPanelCollection binByLotBinding;
    private Boolean srBin;
    private Boolean srLot;
    private Boolean binLot;
    private Boolean dut;
    private RichInputText binPhyQty;
    private RichInputText srPhyQty;
    private RichInputText lotPhyQty;
    private RichCommandImageLink editDataUpdate;
    private RichPopup popupOnPage;
    String isbin = "Y";
    private RichInputText srScrpQty;
    private RichInputText srRwkQty;
    private RichInputText binScrpQty;
    private RichInputText binRwkQty;
    private RichInputText lotScrpQty;
    private RichInputText lotRwkQty;
    private RichPopup tempOrFinalPopup;
    private RichGoImageLink stockTakeDetailLinkBinding;
    private RichGoImageLink stockTakeSummaryLinkBinding;
    private RichTable stkUpdtTblBind;

    public StockTakingBean() {

        if (resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}") != null)
            isbin = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}").toString();
        if (isbin.equals("N")) {
            srBin = false;
            binLot = false;
            srLot = true;

        } else {
            srBin = true;
            binLot = true;
            srLot = false;

        }
        if (resolvEl("#{pageFlowScope.ModeForTaking}") != null)
            mode = (resolvEl("#{pageFlowScope.ModeForTaking}").toString());


    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addHeaderButton(ActionEvent actionEvent) {
        getBindings().getOperationBinding("CreateInsert").execute();
        getBindings().getOperationBinding("setDfltPrf").execute();

        mode = "CS";
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);

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

    public void saveButton(ActionEvent actionEvent) {
        OperationBinding chkst = getBindings().getOperationBinding("CheckStatus");
        chkst.execute();
        Integer st = (Integer)chkst.getResult();
        if (st == 354) {
            String stockno = (String)getBindings().getOperationBinding("genStkTkNo").execute();

            OperationBinding opc = getBindings().getOperationBinding("Commit");
            opc.execute();
            OperationBinding opce = getBindings().getOperationBinding("executeVos");
            opce.execute();

            mode = "SI";
            String msg2 = "Saved Successfully.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);

        } else {

            Integer pending = null;
            String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
            chkUsr.getParamsMap().put("SlocId", SlocId);
            chkUsr.getParamsMap().put("CldId", CldId);
            chkUsr.getParamsMap().put("OrgId", OrgId);
            chkUsr.getParamsMap().put("DocNo", 18518);
            chkUsr.execute();

            if (chkUsr.getResult() != null) {
                pending = Integer.parseInt(chkUsr.getResult().toString());
            }
            if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(UsrId) == 0) {
                OperationBinding checkDU = getBindings().getOperationBinding("checkDataUpdated");
                checkDU.execute();
                String du = checkDU.getResult().toString();
                if (du.equals("0")) {
                    msgonpop = false; //  Physical Quantity is not given for some Items.
                    showPopup(popupOnPage, true);
                } else if (du.equals("1")) {
                    msgonpop = true; //Data Updated for all Items
                    showPopup(tempOrFinalPopup, true);

                }
                // showPopup(popupOnPage,true);


            } else {
                String msg2 = "This Slip is already saved and pending at other user for approval.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
        }
    }

    public void cancelButton(ActionEvent actionEvent) {
        //getBindings().getOperationBinding("Rollback").execute();
    }

    public void editButton(ActionEvent actionEvent) {

        OperationBinding chkUseValid = getBindings().getOperationBinding("chkValidUser");
        chkUseValid.execute();
        if (chkUseValid.getResult() != null) {
            if (chkUseValid.getResult().toString().equals("Y"))
                mode = "EDI";
            else {
                FacesMessage msg = new FacesMessage("You are not valid user to edit this slip.");
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
            }
        }

        /*   cst=true;
     est=true; */
        if (criteriaBinding.getValue().toString().equals("364")) { /* okw=true; ai=false; */
        } else {
            /*  ai=true;
     okw=false; */
        }
        if (criteriaBinding.getValue().toString().equals("365")) {

        } else {

        }
        mode = "EDI";

    }

    public void addItemButton(ActionEvent actionEvent) {
        if (dateBinding.getValue() != null && dateBinding.getValue() != "") {
            Integer fyid =
                (Integer)BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("generateFyId").execute();
            if (fyid > 0) {
                if (itmnamebinding.getValue() != null) {
                    if (selectallbinding.getValue().toString() != "true") {
                        if (itmwhbinding.getValue() != null) {
                            String itmId = (String)itemidbinding.getValue();
                            String whId = (String)itmwhbinding.getValue();
                            OperationBinding binding = getBindings().getOperationBinding("setItmfronewarehouse");
                            binding.getParamsMap().put("itm", itmId);
                            binding.getParamsMap().put("wh", whId);
                            binding.execute();
                        //                            op.getParamsMap().put("wh", whId);
//                            OperationBinding op = getBindings().getOperationBinding("setitemforonewarehouse");
//                            op.getParamsMap().put("itm", itmId);
//                            op.getParamsMap().put("wh", whId);
//                            op.execute();
//                            OperationBinding opl = getBindings().getOperationBinding("setlotforonewarehouse");
//                            opl.getParamsMap().put("itm", itmId);
//                            opl.getParamsMap().put("wh", whId);
//                            opl.execute();
//                            String isbin = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}").toString();
//                            if (isbin.equals("Y")) {
//                                OperationBinding opb = getBindings().getOperationBinding("setbinforonewarehouse");
//                                opb.getParamsMap().put("itm", itmId);
//                                opb.getParamsMap().put("wh", whId);
//                                opb.execute();
//
//
//                                OperationBinding opitmsr = getBindings().getOperationBinding("checkItmidSerialize");
//                                opitmsr.getParamsMap().put("ItmId", itmId);
//                                opitmsr.execute();
//                                String issr = (String)opitmsr.getResult();
//
//                                if (issr.equalsIgnoreCase("Y")) {
//                                    OperationBinding opsb = getBindings().getOperationBinding("setsrfrombinforonewh");
//                                    opsb.getParamsMap().put("itm", itmId);
//                                    opsb.getParamsMap().put("wh", whId);
//                                    opsb.execute();
//                                }
//
//                                String stockno = (String)getBindings().getOperationBinding("genStkTkNo").execute();
//                                System.out.println("Stock Take Number:"+stockno);
//                                OperationBinding opce = getBindings().getOperationBinding("executeVos");
//                                System.out.println("execute vo");
//                                opce.execute();
//
//                            } else {
//                                OperationBinding opitmsr = getBindings().getOperationBinding("checkItmidSerialize");
//                                opitmsr.getParamsMap().put("ItmId", itmId);
//                                String sr = (String)opitmsr.execute();
//                                if (sr.equals("Y")) {
//                                    OperationBinding opsl = getBindings().getOperationBinding("setsrfromlotforonewh");
//                                    opsl.getParamsMap().put("itm", itmId);
//                                    opsl.getParamsMap().put("wh", whId);
//                                    opsl.execute();
//                                }
//
//                            }
//                               String stockno = (String)getBindings().getOperationBinding("genStkTkNo").execute();
//                            System.out.println("StockTake NO:"+stockno);
//                              OperationBinding opc=getBindings().getOperationBinding("Commit");
//                              opc.execute(); 
                            String stockno = (String)getBindings().getOperationBinding("genStkTkNo").execute();
                            OperationBinding opce = getBindings().getOperationBinding("executeVos");
                            opce.execute();
                            System.out.println("Stock number:"+stockno);
                            System.out.println("execute Vo");
                            itmwhbinding.setValue("");
                            itmnamebinding.setValue("");
                            itmwhbinding.setValue(null);
                            itmnamebinding.setValue(null);
                            // itmwhbinding.setDisabled(false);
                            mode = "AI";
                        } else {
                            FacesMessage msg = new FacesMessage(resolvElDCMsg("Select Warehouse.").toString());
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext ctx = FacesContext.getCurrentInstance();
                            ctx.addMessage(itmwhbinding.getClientId(), msg);
                        }
                    } else {
                           //System.out.println("in else condition(all warehouse)");
                           String itmId = (String)itemidbinding.getValue();
                           String whId = (String)itmwhbinding.getValue();
                          OperationBinding op = getBindings().getOperationBinding("setItmForAllWareHouse");
                          op.getParamsMap().put("itm", itmId);
                          op.getParamsMap().put("wh", whId);
                          op.execute();
//                        OperationBinding opl = getBindings().getOperationBinding("setLotForAllWh");
//                        opl.getParamsMap().put("itm", itmId);
//                        opl.execute();
//                        String isbin = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}").toString();
//                        if (isbin.equals("Y")) {
//                            OperationBinding opb = getBindings().getOperationBinding("setBinforAllWh");
//                            opb.getParamsMap().put("itm", itmId);
//                            opb.execute();
//
//                            OperationBinding opitmsr = getBindings().getOperationBinding("checkItmidSerialize");
//                            opitmsr.getParamsMap().put("ItmId", itmId);
//                            opitmsr.execute();
//                            String sr = (String)opitmsr.getResult();
//                            if (sr.equals("Y")) {
//                                OperationBinding opsb = getBindings().getOperationBinding("setSrfromBinForallWh");
//                                opsb.getParamsMap().put("itm", itmId);
//                                opsb.execute();
//                            }
//
//                        } else {
//                            OperationBinding opitmsr = getBindings().getOperationBinding("checkItmidSerialize");
//                            opitmsr.getParamsMap().put("ItmId", itmId);
//                            String sr = (String)opitmsr.execute();
//                            if (sr.equals("Y")) {
//                                OperationBinding opsl = getBindings().getOperationBinding("setSrFromLotforallWh");
//                                opsl.getParamsMap().put("itm", itmId);
//                                opsl.execute();
//                            }
//
                        

                        itmwhbinding.setValue("");
                        itmnamebinding.setValue("");
                        itmwhbinding.setValue(null);
                        itmnamebinding.setValue(null);
                        // itmwhbinding.setDisabled(false);
                        mode = "AI";
                        selectallbinding.setValue(false);
                        /*  String stockno = (String)getBindings().getOperationBinding("genStkTkNo").execute();
                  OperationBinding opc=getBindings().getOperationBinding("Commit");
                  opc.execute();  */
                        String stockno = (String)getBindings().getOperationBinding("genStkTkNo").execute();
                        System.out.println("Stock take no="+stockno);
                        OperationBinding opce = getBindings().getOperationBinding("executeVos");
                        opce.execute();
                        System.out.println("Executed VO");

                    }
//                    }
                    }else {
                    FacesMessage msg = new FacesMessage(resolvElDCMsg("Please Select Item.").toString());
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(itmnamebinding.getClientId(), msg);
                }
            } else {
                FacesMessage msg =
                    new FacesMessage(resolvElDCMsg("Selected Date is not in Valid Financial Year.").toString());
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(dateBinding.getClientId(), msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(resolvElDCMsg("Please Select Date.").toString());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(dateBinding.getClientId(), msg);
        }
    }
    
    public void profileChangeListener(ValueChangeEvent valueChangeEvent) {
        //  whIdBinding.setValue(null);
        OperationBinding op = getBindings().getOperationBinding("profileCriteriaChange");
        op.getParamsMap().put("profile", valueChangeEvent.getNewValue());
        Crtype = (String)op.execute();
        if (Crtype != null) {
            if (Crtype.equals("365")) {

                AdfFacesContext.getCurrentInstance().addPartialTarget(itmnamebinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(selectallbinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmwhbinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(additmBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(whIdBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(okbuttonbinding);

            } else {

                AdfFacesContext.getCurrentInstance().addPartialTarget(itmnamebinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(selectallbinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmwhbinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(additmBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(whIdBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(okbuttonbinding);
                // System.out.println("Itemwise");
            }
        } else {
            System.out.println("Criteria null!!!!!");
        }

    }


    public void criteriaChangeListener(ValueChangeEvent valueChangeEvent) {
    }

    public void setProfileBinding(RichInputListOfValues profileBinding) {
        this.profileBinding = profileBinding;
    }

    public RichInputListOfValues getProfileBinding() {
        return profileBinding;
    }

    public void setCriteriaBinding(RichSelectOneChoice criteriaBinding) {
        this.criteriaBinding = criteriaBinding;
    }

    public RichSelectOneChoice getCriteriaBinding() {
        return criteriaBinding;
    }

    public void setCrtype(String Crtype) {
        this.Crtype = Crtype;
    }

    public String getCrtype() {
        return Crtype;
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = null;
        if (valueExp.getValue(elContext) != null)
            msg = valueExp.getValue(elContext).toString();
        else
            msg = null;
        return msg;
    }

    public void panelTabStockDiscloserListener(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            // saveDataUpdate.setVisible(true);
            String isbin = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}").toString();
            if (searchItmBinding.getValue() != null && searchItmBinding.getValue().toString().length() > 0) {
                OperationBinding opitmsr = getBindings().getOperationBinding("checkItmidSerialize");
                opitmsr.getParamsMap().put("ItmId", searchItmBinding.getValue());
                opitmsr.execute();
                String issr = (String)opitmsr.getResult();

                if (isbin.equals("Y")) {
                    if (issr.equals("Y")) {
                        searchLotBinding.setValue(null);
                        searchBinBinding.setValue(null);
                        searchSrBinding.setValue(null);

                        searchBinBinding.setVisible(true);
                        searchSrBinding.setVisible(true);
                        searchSrBinColumn.setVisible(true);
                        searchBinBinColumn.setVisible(true);
                        searchSrResult.setVisible(true);
                        searchBinResult.setVisible(false);
                        searchLotBinding.setVisible(true);
                        searchLotResult.setVisible(false);
                    } else {
                        searchLotBinding.setValue(null);
                        searchBinBinding.setValue(null);
                        searchSrBinding.setValue(null);

                        searchBinBinding.setVisible(true);
                        searchSrBinding.setVisible(false);
                        searchSrBinColumn.setVisible(true);
                        searchBinBinColumn.setVisible(true);
                        searchSrResult.setVisible(false);
                        searchBinResult.setVisible(true);
                        searchLotBinding.setVisible(true);
                        searchLotResult.setVisible(false);
                    }
                } else if (isbin.equals("N")) {
                    if (issr.equals("Y")) {
                        searchLotBinding.setValue(null);
                        searchBinBinding.setValue(null);
                        searchSrBinding.setValue(null);

                        searchBinBinding.setVisible(false);
                        searchSrBinding.setVisible(true);
                        searchSrBinColumn.setVisible(false);
                        searchBinBinColumn.setVisible(false);
                        searchSrResult.setVisible(true);
                        searchBinResult.setVisible(false);
                        searchLotBinding.setVisible(true);
                        searchLotResult.setVisible(false);
                    } else {
                        searchLotBinding.setValue(null);
                        searchBinBinding.setValue(null);
                        searchSrBinding.setValue(null);


                        searchBinBinding.setVisible(false);
                        searchSrBinding.setVisible(false);
                        searchSrBinColumn.setVisible(false);
                        searchBinBinColumn.setVisible(false);
                        searchSrResult.setVisible(false);
                        searchBinResult.setVisible(false);
                        searchLotBinding.setVisible(true);
                        searchLotResult.setVisible(true);
                    }
                }
            } else {
                //search itm is null at disclose
                if (isbin.equals("Y")) {
                    searchBinBinding.setVisible(true);
                    searchSrBinding.setVisible(true);
                    searchSrBinColumn.setVisible(true);
                    searchBinBinColumn.setVisible(true);
                    searchSrResult.setVisible(true);
                    searchBinResult.setVisible(false);
                    searchLotBinding.setVisible(true);
                    searchLotResult.setVisible(false);
                } else {
                    searchBinBinding.setValue(null);
                    searchBinBinding.setVisible(false);
                    searchSrBinding.setVisible(true);
                    searchSrBinColumn.setVisible(false);
                    searchBinBinColumn.setVisible(false);
                    searchSrResult.setVisible(true);
                    searchBinResult.setVisible(false);
                    searchLotBinding.setVisible(true);
                    searchLotResult.setVisible(false);
                }
            }
        } else {
            //  saveDataUpdate.setVisible(false);
        }

    }

    public void saveLotActionListener(ActionEvent actionEvent) {

        /* OperationBinding opitm=getBindings().getOperationBinding("addSummLotToTakeLot");
        opitm.execute();*/
    }

    public void setBinPanelGroup(RichPanelGroupLayout binPanelGroup) {
        this.binPanelGroup = binPanelGroup;
    }

    public RichPanelGroupLayout getBinPanelGroup() {
        return binPanelGroup;
    }

    public void setSerialPanelGroup(RichPanelGroupLayout serialPanelGroup) {
        this.serialPanelGroup = serialPanelGroup;
    }

    public RichPanelGroupLayout getSerialPanelGroup() {
        return serialPanelGroup;
    }

    public void itemIdChangeLis(ValueChangeEvent valueChangeEvent) {
        OperationBinding opitmsr = getBindings().getOperationBinding("checkItmSerialize");
        opitmsr.getParamsMap().put("ItmId", valueChangeEvent.getNewValue());
        String sr = (String)opitmsr.execute();
        if (sr.equals("Y")) {
            serialPanelGroup.setVisible(true);
        } else if (sr.equals("N")) {
            serialPanelGroup.setVisible(false);
        }


    }

    public void setItemIdBind(RichSelectOneChoice itemIdBind) {
        this.itemIdBind = itemIdBind;
    }

    public RichSelectOneChoice getItemIdBind() {
        return itemIdBind;
    }

    public void backButton(ActionEvent actionEvent) {
        //show popup for confirmation
        getBindings().getOperationBinding("Rollback").execute();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void saveLotsactionlistener(ActionEvent actionEvent) {
        getBindings().getOperationBinding("Commit").execute();

    }

    public void saveBinActionListener(ActionEvent actionEvent) {
        getBindings().getOperationBinding("Commit").execute();


    }

    public void srSaveListener(ActionEvent actionEvent) {
        getBindings().getOperationBinding("Commit").execute();

    }

    public void addLotListener(ActionEvent actionEvent) {
        getBindings().getOperationBinding("CreateInsert2").execute();

    }

    public void addBinActionListener(ActionEvent actionEvent) {
        getBindings().getOperationBinding("CreateInsert3").execute();

    }

    public void srAddListener(ActionEvent actionEvent) {
        getBindings().getOperationBinding("CreateInsert4").execute();
    }

    public void setAddlot(RichCommandImageLink addlot) {
        this.addlot = addlot;
    }

    public RichCommandImageLink getAddlot() {
        return addlot;
    }

    public void setSaveLot(RichCommandImageLink saveLot) {

        this.saveLot = saveLot;
    }

    public RichCommandImageLink getSaveLot() {
        return saveLot;
    }

    public void setAddBin(RichCommandImageLink addBin) {
        this.addBin = addBin;
    }

    public RichCommandImageLink getAddBin() {
        return addBin;
    }

    public void setSaveBin(RichCommandImageLink saveBin) {
        this.saveBin = saveBin;
    }

    public RichCommandImageLink getSaveBin() {
        return saveBin;
    }

    public void setAddSr(RichCommandImageLink addSr) {
        this.addSr = addSr;
    }

    public RichCommandImageLink getAddSr() {
        return addSr;
    }

    public void setSaveSr(RichCommandImageLink saveSr) {
        this.saveSr = saveSr;
    }

    public RichCommandImageLink getSaveSr() {
        return saveSr;
    }

    public void setWhIdBinding(RichSelectOneChoice whIdBinding) {
        this.whIdBinding = whIdBinding;
    }

    public RichSelectOneChoice getWhIdBinding() {
        return whIdBinding;
    }

    public void setDateBinding(RichInputDate dateBinding) {
        this.dateBinding = dateBinding;
    }

    public RichInputDate getDateBinding() {
        return dateBinding;
    }

    public void whChangeListener(ValueChangeEvent valueChangeEvent) {

    }

    public void okWhListener(ActionEvent actionEvent) {
        System.out.println("warehouse:"+whIdBinding.getValue());
        if (whIdBinding.getValue() != null) {
        System.out.println("date:"+dateBinding.getValue());
            if (dateBinding.getValue() != null) {
               
                Integer fyid =
                    (Integer)BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("generateFyId").execute();
                System.out.println("Financial Year id :"+fyid);
                if (fyid > 0)

                {

                    //for itm
                    OperationBinding opitm = getBindings().getOperationBinding("setItemAcctoWh");
                    opitm.getParamsMap().put("WhId", whIdBinding.getValue());
                    opitm.execute();

                    //for lot
//                    OperationBinding laiop = getBindings().getOperationBinding("setLotAcctoItem");
//                    laiop.getParamsMap().put("Wh", whIdBinding.getValue());
//                    laiop.execute();
//
//                    //for bin
//                    String isbin = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}").toString();
//                    if (isbin.equals("Y")) {
//                        OperationBinding timtobin = getBindings().getOperationBinding("setBinAcctoLot");
//                        timtobin.getParamsMap().put("whbin", whIdBinding.getValue());
//                        timtobin.execute();
//
//                        //for sr by bin
//                        OperationBinding bintosr = getBindings().getOperationBinding("setSrAcctoBin");
//                        bintosr.getParamsMap().put("whsr", whIdBinding.getValue());
//                        bintosr.execute();
//                    }
//
//                    else {
//                        //for sr by lot
//                        OperationBinding lottosr = getBindings().getOperationBinding("setSrAcctoLot");
//                        lottosr.getParamsMap().put("whsr", whIdBinding.getValue());
//                        lottosr.execute();
//                    }
                  String stockno = (String)getBindings().getOperationBinding("genStkTkNo").execute();
                    System.out.println("Stkno=" + stockno);
                    OperationBinding opce = getBindings().getOperationBinding("executeVos");
                    opce.execute();
                    System.out.println("Error on exe=" + opce.getErrors());
                    System.out.println("VO executed");
                    whIdBinding.setValue(null);
                    whIdBinding.setValue("");
                    whIdBinding.resetValue();
                    mode = "OKW";
                } else {
                    FacesMessage msg =
                        new FacesMessage(resolvElDCMsg("Selected Date is not in valid financial year.").toString());
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(dateBinding.getClientId(), msg);
                }
            } else {
                FacesMessage msg = new FacesMessage(resolvElDCMsg("Select Date.").toString());
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(dateBinding.getClientId(), msg);

            }

        } else {
            FacesMessage msg = new FacesMessage(resolvElDCMsg("Select Warehouse.").toString());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(whIdBinding.getClientId(), msg);
        }

    }

    public void setAdditmBinding(RichCommandImageLink additmBinding) {
        this.additmBinding = additmBinding;
    }

    public RichCommandImageLink getAdditmBinding() {
        return additmBinding;
    }

    public void setItmnamebinding(RichInputListOfValues itmnamebinding) {
        this.itmnamebinding = itmnamebinding;
    }

    public RichInputListOfValues getItmnamebinding() {
        return itmnamebinding;
    }

    public void setItmwhbinding(RichSelectOneChoice itmwhbinding) {
        this.itmwhbinding = itmwhbinding;
    }

    public RichSelectOneChoice getItmwhbinding() {
        return itmwhbinding;
    }

    public void setSelectallbinding(RichSelectBooleanCheckbox selectallbinding) {
        this.selectallbinding = selectallbinding;
    }

    public RichSelectBooleanCheckbox getSelectallbinding() {
        return selectallbinding;
    }

    public void selectallChangeListener(ValueChangeEvent valueChangeEvent) {

        /*  if(valueChangeEvent.getNewValue().toString().equals("true"))
        {
        itmwhbinding.setDisabled(true);
        }
        else
        {
         itmwhbinding.setDisabled(false);
        }
         */

    }

    public void setItemIdbinding(RichPanelLabelAndMessage itemIdbinding) {
        this.itemIdbinding = itemIdbinding;
    }

    public RichPanelLabelAndMessage getItemIdbinding() {
        return itemIdbinding;
    }

    public void setItemidbinding(RichOutputText itemidbinding) {
        this.itemidbinding = itemidbinding;
    }

    public RichOutputText getItemidbinding() {
        return itemidbinding;
    }

    public void itmidChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setOkbuttonbinding(RichCommandImageLink okbuttonbinding) {
        this.okbuttonbinding = okbuttonbinding;
    }

    public RichCommandImageLink getOkbuttonbinding() {
        return okbuttonbinding;
    }

    /*  public void setBst(Boolean bst) {
        this.bst = bst;
    }

    public Boolean getBst() {
        return bst;
    }

    public void setOkw(Boolean okw) {
        this.okw = okw;
    }

    public Boolean getOkw() {
        return okw;
    } */

    public void setDt(Boolean dt) {
        this.dt = dt;
    }

    public Boolean getDt() {
        return dt;
    }

    public void setPrf(Boolean prf) {
        this.prf = prf;
    }

    public Boolean getPrf() {
        return prf;
    }

    public void deleteItemActionListener(ActionEvent actionEvent) {
        //call delete function from amimpl    deleteItem()
        OperationBinding opd = getBindings().getOperationBinding("deleteItem");
        opd.execute();
        OperationBinding opce = getBindings().getOperationBinding("executeVos");
        opce.execute();

    }

    public void setSwh(Boolean swh) {
        this.swh = swh;
    }

    public Boolean getSwh() {
        return swh;
    }

    public void setSitm(Boolean sitm) {
        this.sitm = sitm;
    }

    public Boolean getSitm() {
        return sitm;
    }

    public void setSlot(Boolean slot) {
        this.slot = slot;
    }

    public Boolean getSlot() {
        return slot;
    }

    public void setSbin(Boolean sbin) {
        this.sbin = sbin;
    }

    public Boolean getSbin() {
        return sbin;
    }

    public void setSsr(Boolean ssr) {
        this.ssr = ssr;
    }

    public Boolean getSsr() {
        return ssr;
    }

    public void searchWhChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "") {
        } else {
            OperationBinding opd = getBindings().getOperationBinding("setwhenwhnull");
            opd.execute();
        }
    }

    public void searchItemChange(ValueChangeEvent valueChangeEvent) {
        /*  OperationBinding opdr=getBindings().getOperationBinding("resetItemSearch");
        opdr.execute(); */
        OperationBinding opdr = getBindings().getOperationBinding("setBindonTabDisc");
        opdr.execute();
        System.out.println(" Value of valueChangeEvent:"+valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "") {
            String itm = (String)valueChangeEvent.getNewValue();
            System.out.println("Itm in searchItemChange:"+itm);
            OperationBinding opitmsr = getBindings().getOperationBinding("checkItmidSerialize");
            opitmsr.getParamsMap().put("ItmId", itm);
            opitmsr.execute();
            String is = (String)opitmsr.getResult();
            System.out.println("Result is:"+is);
            if (is.equals("Y")) {
                String isbin = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}").toString();
                if (isbin.equals("Y")) {
                    searchLotBinding.setValue(null);
                    searchBinBinding.setValue(null);
                    searchSrBinding.setValue(null);

                    searchBinBinding.setVisible(true);
                    searchSrBinding.setVisible(true);
                    searchSrBinColumn.setVisible(true);
                    searchBinBinColumn.setVisible(true);
                    searchSrResult.setVisible(true);
                    searchBinResult.setVisible(false);
                    searchLotBinding.setVisible(true);
                    searchLotResult.setVisible(false);
                    /*  searchSrBinding.setVisible(true);
                             searchBinBinding.setVisible(true);
                             searchSrResult.setVisible(true);
                             searchBinResult.setVisible(false);
                             searchLotResult.setVisible(false); */
                } else {
                    searchLotBinding.setValue(null);
                    searchBinBinding.setValue(null);
                    searchSrBinding.setValue(null);

                    searchBinBinding.setVisible(false);
                    searchSrBinding.setVisible(true);
                    searchSrBinColumn.setVisible(false);
                    searchBinBinColumn.setVisible(false);
                    searchSrResult.setVisible(true);
                    searchBinResult.setVisible(false);
                    searchLotBinding.setVisible(true);
                    searchLotResult.setVisible(false);
                    /* searchBinBinding.setVisible(false);
                                     searchSrBinding.setVisible(true);
                                     searchSrResult.setVisible(true);
                                     searchBinResult.setVisible(false);
                                     searchLotResult.setVisible(false); */
                }
            } else {

                String isbin = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}").toString();
                if (isbin.equals("Y")) {
                    searchLotBinding.setValue(null);
                    searchBinBinding.setValue(null);
                    searchSrBinding.setValue(null);

                    searchBinBinding.setVisible(true);
                    searchSrBinding.setVisible(false);
                    searchSrBinColumn.setVisible(true);
                    searchBinBinColumn.setVisible(true);
                    searchSrResult.setVisible(false);
                    searchBinResult.setVisible(true);
                    searchLotBinding.setVisible(true);
                    searchLotResult.setVisible(false);
                    /*  searchSrBinding.setVisible(false);
                searchBinBinding.setVisible(true);
                searchSrResult.setVisible(false);
                searchBinResult.setVisible(true);
                searchLotResult.setVisible(false); */
                } else {
                    searchLotBinding.setValue(null);
                    searchBinBinding.setValue(null);
                    searchSrBinding.setValue(null);

                    searchBinBinding.setVisible(false);
                    searchSrBinding.setVisible(false);
                    searchSrBinColumn.setVisible(false);
                    searchBinBinColumn.setVisible(false);
                    searchSrResult.setVisible(false);
                    searchBinResult.setVisible(false);
                    searchLotBinding.setVisible(true);
                    searchLotResult.setVisible(true);
                    /*  searchSrBinding.setVisible(false);
                    searchSrResult.setVisible(false);
                    searchBinBinding.setVisible(false);
                    searchBinResult.setVisible(false);
                    searchLotResult.setVisible(true); */
                }
            }
            OperationBinding opd = getBindings().getOperationBinding("setwhenitmnull");
            opd.execute();
        } else {
            OperationBinding opd = getBindings().getOperationBinding("setwhenitmnull");
            opd.execute();
        }
    }

    public void searchLotChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "") {
            OperationBinding opd = getBindings().getOperationBinding("setwhenlotnull");
            opd.execute();
        } else {
            OperationBinding opd = getBindings().getOperationBinding("setwhenlotnull");
            opd.execute();
        }
    }

    public void searchBinChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "") {
            OperationBinding opd = getBindings().getOperationBinding("setwhenbinnull");
            opd.execute();
        } else {
            OperationBinding opd = getBindings().getOperationBinding("setwhenbinnull");
            opd.execute();
        }
    }

    public void searchSrChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "") {
            OperationBinding opd = getBindings().getOperationBinding("setwhensrnull");
            opd.execute();
        } else {
            OperationBinding opd = getBindings().getOperationBinding("setwhensrnull");
            opd.execute();
        }
    }

    public void setSearchWhBinding(RichSelectOneChoice searchWhBinding) {
        this.searchWhBinding = searchWhBinding;
    }

    public RichSelectOneChoice getSearchWhBinding() {
        return searchWhBinding;
    }

    public void setSearchItmBinding(RichSelectOneChoice searchItmBinding) {
        this.searchItmBinding = searchItmBinding;
    }

    public RichSelectOneChoice getSearchItmBinding() {
        return searchItmBinding;
    }

    public void setSearchLotBinding(RichSelectOneChoice searchLotBinding) {
        this.searchLotBinding = searchLotBinding;
    }

    public RichSelectOneChoice getSearchLotBinding() {
        return searchLotBinding;
    }

    public void setSearchBinBinding(RichSelectOneChoice searchBinBinding) {
        this.searchBinBinding = searchBinBinding;
    }

    public RichSelectOneChoice getSearchBinBinding() {
        return searchBinBinding;
    }

    public void setSearchSrBinding(RichSelectOneChoice searchSrBinding) {
        this.searchSrBinding = searchSrBinding;
    }

    public RichSelectOneChoice getSearchSrBinding() {
        return searchSrBinding;
    }

    public void searchItmButtonListener(ActionEvent actionEvent) {
        System.out.println("Binding WareHouse:"+searchWhBinding.getValue());
        System.out.println("Binding Item:"+searchItmBinding.getValue());
        if (searchWhBinding.getValue() != null && searchWhBinding.getValue() != "") {
            if (searchItmBinding.getValue() != null && searchItmBinding.getValue() != "") {
                OperationBinding opd = getBindings().getOperationBinding("searchItems");
                opd.execute();
                //System.out.println("update item value:"+searchItmBinding.getValue());
            } else {
                FacesMessage msg = new FacesMessage(resolvElDCMsg("Please Select Item.").toString());
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(searchItmBinding.getClientId(), msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(resolvElDCMsg("Please Select Warehouse.").toString());
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(searchWhBinding.getClientId(), msg);
        }

    }

    public void resetItmSearchButtonlistener(ActionEvent actionEvent) {
        OperationBinding opdr = getBindings().getOperationBinding("setBindonTabDisc");
        opdr.execute();
        OperationBinding opd = getBindings().getOperationBinding("resetItemSearch");
        opd.execute();
        searchSrResult.setVisible(true);
        searchBinResult.setVisible(false);
        searchLotResult.setVisible(false);
    }

    public void setSearchSrResult(RichPanelCollection searchSrResult) {
        this.searchSrResult = searchSrResult;
    }

    public RichPanelCollection getSearchSrResult() {
        return searchSrResult;
    }

    public void setSearchBinResult(RichPanelCollection searchBinResult) {
        this.searchBinResult = searchBinResult;
    }

    public RichPanelCollection getSearchBinResult() {
        return searchBinResult;
    }

    public void setSearchSrBinColumn(RichColumn searchSrBinColumn) {
        this.searchSrBinColumn = searchSrBinColumn;
    }

    public RichColumn getSearchSrBinColumn() {
        return searchSrBinColumn;
    }

    public void setSearchBinBinColumn(RichColumn searchBinBinColumn) {
        this.searchBinBinColumn = searchBinBinColumn;
    }

    public RichColumn getSearchBinBinColumn() {
        return searchBinBinColumn;
    }

    public void setSearchLotResult(RichPanelCollection searchLotResult) {
        this.searchLotResult = searchLotResult;
    }

    public RichPanelCollection getSearchLotResult() {
        return searchLotResult;
    }

    public void setSaveDataUpdate(RichCommandImageLink saveDataUpdate) {
        this.saveDataUpdate = saveDataUpdate;
    }

    public RichCommandImageLink getSaveDataUpdate() {
        return saveDataUpdate;
    }

    public void BinPhyQtyChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding op = getBindings().getOperationBinding("setDiffQtyForBin");
            op.getParamsMap().put("Phy", valueChangeEvent.getNewValue());
            op.execute();

        }
    }

    public void setBinDiffQtyBinding(RichColumn binDiffQtyBinding) {
        this.binDiffQtyBinding = binDiffQtyBinding;
    }

    public RichColumn getBinDiffQtyBinding() {
        return binDiffQtyBinding;
    }

    public void lotPhyQtyChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            OperationBinding op = getBindings().getOperationBinding("setDiffQtyForLot");
            op.getParamsMap().put("Phy", valueChangeEvent.getNewValue());
            op.execute();
        }
    }

    public void setSrByBinBinding(RichPanelCollection srByBinBinding) {
        this.srByBinBinding = srByBinBinding;
    }

    public RichPanelCollection getSrByBinBinding() {
        return srByBinBinding;
    }

    public void setSrByLotBinding(RichPanelCollection srByLotBinding) {
        this.srByLotBinding = srByLotBinding;
    }

    public RichPanelCollection getSrByLotBinding() {
        return srByLotBinding;
    }

    public void setBinByLotBinding(RichPanelCollection binByLotBinding) {
        this.binByLotBinding = binByLotBinding;
    }

    public RichPanelCollection getBinByLotBinding() {
        return binByLotBinding;
    }

    public void setSrBin(Boolean srBin) {
        this.srBin = srBin;
    }

    public Boolean getSrBin() {
        return srBin;
    }

    public void setSrLot(Boolean srLot) {
        this.srLot = srLot;
    }

    public Boolean getSrLot() {
        return srLot;
    }

    public void setBinLot(Boolean binLot) {
        this.binLot = binLot;
    }

    public Boolean getBinLot() {
        return binLot;
    }

    public void srPhyQtyChangeListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("in vcl");
         if (valueChangeEvent.getNewValue() != null) {
            OperationBinding op = getBindings().getOperationBinding("setDiffQtyForSr");
            op.getParamsMap().put("Phy", valueChangeEvent.getNewValue());
            op.execute();
        } 
    }

      public void setDut(Boolean dut) {
        this.dut = dut;
    }

    public Boolean getDut() {
        return dut;
    } 

    public void setBinPhyQty(RichInputText binPhyQty) {
        this.binPhyQty = binPhyQty;
    }

    public RichInputText getBinPhyQty() {
        return binPhyQty;
    }

    public void setSrPhyQty(RichInputText srPhyQty) {
        this.srPhyQty = srPhyQty;
    }

    public RichInputText getSrPhyQty() {
        return srPhyQty;
    }

    public void setLotPhyQty(RichInputText lotPhyQty) {
        this.lotPhyQty = lotPhyQty;
    }

    public RichInputText getLotPhyQty() {
        return lotPhyQty;
    }

    public void setCstVis(Boolean cstVis) {
        this.cstVis = cstVis;
    }

    public Boolean getCstVis() {
        return cstVis;
    }

    public void setEstVis(Boolean estVis) {
        this.estVis = estVis;
    }

    public Boolean getEstVis() {
        return estVis;
    }

    public void setSduVis(Boolean sduVis) {
        this.sduVis = sduVis;
    }

    public Boolean getSduVis() {
        return sduVis;
    }

    public void setEduVis(Boolean eduVis) {
        this.eduVis = eduVis;
    }

    public Boolean getEduVis() {
        return eduVis;
    }

    public void setEditDataUpdate(RichCommandImageLink editDataUpdate) {
        this.editDataUpdate = editDataUpdate;
    }

    public RichCommandImageLink getEditDataUpdate() {
        return editDataUpdate;
    }

    public void editDataUpdateListener(ActionEvent actionEvent) {
        Integer pending = null;
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", SlocId);
        chkUsr.getParamsMap().put("CldId", CldId);
        chkUsr.getParamsMap().put("OrgId", OrgId);
        chkUsr.getParamsMap().put("DocNo", 18518);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(UsrId) == 0) {
            OperationBinding chkValid = getBindings().getOperationBinding("chkValidUser");
            chkValid.execute();
            if (chkValid.getResult() != null)
                if (chkValid.getResult().toString().equals("Y"))
                    mode = "EDF";
                else {
                    String msg2 = "This Slip is pending at other user , You can not Edit this.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                }
            else {
                String msg2 = "Something went Wrong.Please Contact ESS!";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
        } else {
            String msg2 = "This Slip is pending at other user , You can not Edit this.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }


    }

    public String forwardToWf() {
        //check if slip is saved or not
        OperationBinding opsaved = getBindings().getOperationBinding("CheckSaved");
        opsaved.execute();
        String checksav = opsaved.getResult().toString();
        if (checksav.equals("Saved")) {
            //saved already
            OperationBinding save = getBindings().getOperationBinding("Commit");
            save.execute();
            OperationBinding opce = getBindings().getOperationBinding("executeVos");
            opce.execute();
            /*  binPhyQty.setReadOnly(true);
                   lotPhyQty.setReadOnly(true);
                   srPhyQty.setReadOnly(true);
                srRwkQty.setReadOnly(true);
                srScrpQty.setReadOnly(true);
                binRwkQty.setReadOnly(true);
                binScrpQty.setReadOnly(true);
                lotRwkQty.setReadOnly(true);
                lotScrpQty.setReadOnly(true); */
            String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            String WfNo = "0";
            Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            Integer level = 0;
            String action = "I";
            String remark = "A";
            Integer res = -1;
            //get Wf Id
            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("SlocId", SlocId);
            WfnoOp.getParamsMap().put("CldId", CldId);
            WfnoOp.getParamsMap().put("OrgId", OrgId);
            WfnoOp.getParamsMap().put("DocNo", 18518);
            WfnoOp.execute();
            if (WfnoOp.getResult() != null) {
                WfNo = WfnoOp.getResult().toString();
            }
            //   System.out.println("WfNo="+WfNo);
            if (WfNo != null && !"0".equalsIgnoreCase(WfNo)) {

                //get user level

                OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", SlocId);
                usrLevelOp.getParamsMap().put("CldId", CldId);
                usrLevelOp.getParamsMap().put("OrgId", OrgId);
                usrLevelOp.getParamsMap().put("UsrId", UsrId);
                usrLevelOp.getParamsMap().put("WfNo", WfNo);
                usrLevelOp.getParamsMap().put("DocNo", 18518);
                usrLevelOp.execute();
                if (usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                //   System.out.println("Level="+level);
                if (level >= 0) {
                    //insert into txn
                    OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("SlocId", SlocId);
                    insTxnOp.getParamsMap().put("CldId", CldId);
                    insTxnOp.getParamsMap().put("OrgId", OrgId);
                    insTxnOp.getParamsMap().put("DocNo", 18518);
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
                    //             System.out.println("Res="+res);
                    OperationBinding save1 = getBindings().getOperationBinding("Commit");
                    save1.execute();


                    //get txn to pass in wf
                    OperationBinding gettxn = getBindings().getOperationBinding("getTxnId");
                    gettxn.execute();
                    if (gettxn.getResult() != null) {
                        TxnId = gettxn.getResult().toString();
                        System.out.println("Txn=" + TxnId);
                    }

                    OperationBinding settxn = getBindings().getOperationBinding("settxnidlast");
                    settxn.getParamsMap().put("txn", TxnId);
                    settxn.execute();


                    //Check Pending
                    Integer pending = null;

                    OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
                    chkUsr.getParamsMap().put("SlocId", SlocId);
                    chkUsr.getParamsMap().put("CldId", CldId);
                    chkUsr.getParamsMap().put("OrgId", OrgId);
                    chkUsr.getParamsMap().put("DocNo", 18518);
                    chkUsr.execute();

                    if (chkUsr.getResult() != null) {
                        pending = Integer.parseInt(chkUsr.getResult().toString());
                        //       System.out.println("Pending"+pending);
                    }
                    if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(UsrId) == 0) {
                        return "toWf";
                    } else {
                        String msg2 = "This Slip is pending at other user for approval, You can not forward this.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2);
                        return null;
                    }
                } else {
                    String msg2 = "Something went wrong (user level in workflow).Please contact to ESS!";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                    return null;
                }


            } else {
                String msg2 = "Workflow for this Document is not Created.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            }
        }


        else {
            //not saved slip
            String msg2 =
                "This Slip is not saved or Physical Quantity for all items is not given, You can not forward this.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }

    }


    public void setTxnId(String TxnId) {
        this.TxnId = TxnId;
    }

    public String getTxnId() {
        return TxnId;
    }

    public void setMsgonpop(Boolean msgonpop) {
        this.msgonpop = msgonpop;
    }

    public Boolean getMsgonpop() {
        return msgonpop;
    }

    public void dialoglistener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(Outcome.ok)) {
            if (msgonpop == false) {
                //do not change status
                OperationBinding save = getBindings().getOperationBinding("Commit");
                save.execute();
                OperationBinding opce = getBindings().getOperationBinding("executeVos");
                opce.execute();
                mode = "SF";

            }
            String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            String WfNo = "0";
            String UsrId = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
            Integer level = 0;
            String action = "I";
            String remark = "A";
            Integer res = -1;
            //get Wf Id
            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("SlocId", SlocId);
            WfnoOp.getParamsMap().put("CldId", CldId);
            WfnoOp.getParamsMap().put("OrgId", OrgId);
            WfnoOp.getParamsMap().put("DocNo", 18518);
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
            usrLevelOp.getParamsMap().put("DocNo", 18518);
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
            insTxnOp.getParamsMap().put("DocNo", 18518);
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
            OperationBinding save1 = getBindings().getOperationBinding("Commit");
            save1.execute();
            OperationBinding updtQty = getBindings().getOperationBinding("updateQuantity");
            updtQty.execute();
            /*  if(updtQty.getResult()!=null)
              {
                  System.out.println("InstStkTake="+updtQty.getResult());
                  if((Integer)updtQty.getResult())
                  } */
            OperationBinding save2 = getBindings().getOperationBinding("Commit");
            save2.execute();
            FacesMessage msg = new FacesMessage(resolvElDCMsg("Saved Successfully").toString());
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);

        }
    }

    public void setPopupOnPage(RichPopup popupOnPage) {
        this.popupOnPage = popupOnPage;
    }

    public RichPopup getPopupOnPage() {
        return popupOnPage;
    }


    public void setFwdVis(Boolean fwdVis) {
        this.fwdVis = fwdVis;
    }

    public Boolean getFwdVis() {
        return fwdVis;
    }

    public void scrpQtyCLforSr(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            OperationBinding op = getBindings().getOperationBinding("setDiffQtybyScrpForSr");
            op.getParamsMap().put("Scrp", valueChangeEvent.getNewValue());
            op.execute();

        }
    }

    public void rWkQtyCLforSr(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            OperationBinding op = getBindings().getOperationBinding("setDiffQtybyRwkForSr");
            op.getParamsMap().put("Rwk", valueChangeEvent.getNewValue());
            op.execute();

        }
    }

    public void scrpQtyCLforLot(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            OperationBinding op = getBindings().getOperationBinding("setDiffQtybyScrpForLot");
            op.getParamsMap().put("Scrp", valueChangeEvent.getNewValue());
            op.execute();

        }
    }

    public void rwkQtyCLforLot(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            OperationBinding op = getBindings().getOperationBinding("setDiffQtybyRwkForLot");
            op.getParamsMap().put("Rwk", valueChangeEvent.getNewValue());
            op.execute();

        }
    }

    public void rWkQtyCLforBin(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding op = getBindings().getOperationBinding("setDiffQtybyRwkForBin");
            op.getParamsMap().put("Rwk", valueChangeEvent.getNewValue());
            op.execute();
        }
    }

    public void scrpQtyCLforBin(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
System.out.println(" scrpQtyCLforBin");
             OperationBinding op = getBindings().getOperationBinding("setDiffQtybyScrpForBin");
            op.getParamsMap().put("Scrp", valueChangeEvent.getNewValue());
            op.execute(); 

        }
    }

    public void freezeAL(ActionEvent actionEvent) {
        //save and freeze
        OperationBinding cs = getBindings().getOperationBinding("ChangeStatusFreeze");
        cs.execute();
        Boolean flg = false;
        if (cs.getErrors().size() == 0)
            flg = (Boolean)cs.getResult();
        if (flg == true) {
            String stockno = (String)getBindings().getOperationBinding("genStkTkNo").execute();
            OperationBinding opc = getBindings().getOperationBinding("Commit");
            opc.execute();
            OperationBinding opce = getBindings().getOperationBinding("executeVos");
            opce.execute();
            mode = "SF";
            String msg2 = "Freezed Successfull.Now You can not Change Items.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);

        } else {
            String msg2 = "Can not Freeze this time.Add Some items First.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }
    }

    public void setSrScrpQty(RichInputText srScrpQty) {
        this.srScrpQty = srScrpQty;
    }

    public RichInputText getSrScrpQty() {
        return srScrpQty;
    }

    public void setSrRwkQty(RichInputText srRwkQty) {
        this.srRwkQty = srRwkQty;
    }

    public RichInputText getSrRwkQty() {
        return srRwkQty;
    }

    public void setBinScrpQty(RichInputText binScrpQty) {
        this.binScrpQty = binScrpQty;
    }

    public RichInputText getBinScrpQty() {
        return binScrpQty;
    }

    public void setBinRwkQty(RichInputText binRwkQty) {
        this.binRwkQty = binRwkQty;
    }

    public RichInputText getBinRwkQty() {
        return binRwkQty;
    }

    public void setLotScrpQty(RichInputText lotScrpQty) {
        this.lotScrpQty = lotScrpQty;
    }

    public RichInputText getLotScrpQty() {
        return lotScrpQty;
    }

    public void setLotRwkQty(RichInputText lotRwkQty) {
        this.lotRwkQty = lotRwkQty;
    }

    public RichInputText getLotRwkQty() {
        return lotRwkQty;
    }

    public void saveTempAL(ActionEvent actionEvent) {
        OperationBinding save = getBindings().getOperationBinding("Commit");
        save.execute();
        OperationBinding opce = getBindings().getOperationBinding("executeVos");
        opce.execute();
        mode = "SF";
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String WfNo = "0";
        String UsrId = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
        Integer level = 0;
        String action = "I";
        String remark = "A";
        Integer res = -1;
        //get Wf Id
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", SlocId);
        WfnoOp.getParamsMap().put("CldId", CldId);
        WfnoOp.getParamsMap().put("OrgId", OrgId);
        WfnoOp.getParamsMap().put("DocNo", 18518);
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
        usrLevelOp.getParamsMap().put("DocNo", 18518);
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
        insTxnOp.getParamsMap().put("DocNo", 18518);
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
        OperationBinding save1 = getBindings().getOperationBinding("Commit");
        save1.execute();
        OperationBinding updtQty = getBindings().getOperationBinding("updateQuantity");
        updtQty.execute();
        OperationBinding save2 = getBindings().getOperationBinding("Commit");
        save2.execute();
        FacesMessage msg = new FacesMessage(resolvElDCMsg("Saved Successfully").toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null, msg);
        tempOrFinalPopup.hide();

    }

    public void saveFinalAL(ActionEvent actionEvent) {
        OperationBinding cs = getBindings().getOperationBinding("ChangeStatus");
        cs.execute();
        OperationBinding save = getBindings().getOperationBinding("Commit");
        save.execute();
        OperationBinding opce = getBindings().getOperationBinding("executeVos");
        opce.execute();
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String WfNo = "0";
        String UsrId = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
        Integer level = 0;
        String action = "I";
        String remark = "A";
        Integer res = -1;
        //get Wf Id
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", SlocId);
        WfnoOp.getParamsMap().put("CldId", CldId);
        WfnoOp.getParamsMap().put("OrgId", OrgId);
        WfnoOp.getParamsMap().put("DocNo", 18518);
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
        usrLevelOp.getParamsMap().put("DocNo", 18518);
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
        insTxnOp.getParamsMap().put("DocNo", 18518);
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
        OperationBinding save1 = getBindings().getOperationBinding("Commit");
        save1.execute();
        OperationBinding updtQty = getBindings().getOperationBinding("updateQuantity");
        updtQty.execute();
        OperationBinding save2 = getBindings().getOperationBinding("Commit");
        save2.execute();
        FacesMessage msg = new FacesMessage(resolvElDCMsg("Saved Successfully").toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null, msg);

        mode = "SF";
        tempOrFinalPopup.hide();
    }

    public void setTempOrFinalPopup(RichPopup tempOrFinalPopup) {
        this.tempOrFinalPopup = tempOrFinalPopup;
    }

    public RichPopup getTempOrFinalPopup() {
        return tempOrFinalPopup;
    }

    public void tempOrFinalPopupCL(PopupCanceledEvent popupCanceledEvent) {
        tempOrFinalPopup.hide();
    }

    public void SrPhyQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (!(new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) == 0 ||
                  new BigDecimal(object.toString()).compareTo(BigDecimal.ONE) == 0)) {
                System.out.println("set 1");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity must be 1 or 0",
                                                              null));

            } else {
                System.out.println("set 2");
                
                       srScrpQty.processUpdates(facesContext.getCurrentInstance());
        srRwkQty.processUpdates(facesContext.getCurrentInstance());

                OperationBinding op = getBindings().getOperationBinding("ChkSumForPhySr");
                op.getParamsMap().put("Phy", object);
                op.execute();
                if (op.getErrors().size() == 0) {
                    Boolean flg = (Boolean)op.getResult();
                    if (flg == false) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Total of Physical,Scrap and Reworkable Quantity could be 1.",
                                                                      null));
                    }
                } 
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(stkUpdtTblBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srRwkQty);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srScrpQty);
            
        }
    }

    public void SrScrpQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
      
        if (object != null) {
            if (!(new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) == 0 ||
                  new BigDecimal(object.toString()).compareTo(BigDecimal.ONE) == 0)) {
                String msg2 = "Quantity must be 1 or 0";

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));

            } else {
                System.out.println("in else of SrScrpQtyValidator");
               OperationBinding op = getBindings().getOperationBinding("ChkSumForScrpSr");
                op.getParamsMap().put("Scrp", object);
                op.execute();
                if (op.getErrors().size() == 0) {
                    Boolean flg = (Boolean)op.getResult();
                    if (flg == false) {
                        String msg2 = "Sum of Physical,Scrap,Reworkable Quantity should be 1.";

                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
                    }
                } 
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(stkUpdtTblBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srRwkQty);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srPhyQty);
            
            //srRwkQty.processUpdates(facesContext.getCurrentInstance());
            //srPhyQty.processUpdates(facesContext.getCurrentInstance());
        }
    }

    public void SrRwkQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       // srScrpQty.processUpdates(facesContext.getCurrentInstance());
      //  srPhyQty.processUpdates(facesContext.getCurrentInstance());
        if (object != null) {
            if (!(new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) == 0 ||
                  new BigDecimal(object.toString()).compareTo(BigDecimal.ONE) == 0)) {
            System.out.println("SrRwkQtyValidator in if");
                String msg2 = "Quantity must be 1 or 0";

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {
                System.out.println("SrRwkQtyValidator ");
                OperationBinding op = getBindings().getOperationBinding("ChkSumForRwkSr");
                op.getParamsMap().put("Rwk", object);
                op.execute();
                if (op.getErrors().size() == 0) {
                    Boolean flg = (Boolean)op.getResult();
                    if (flg == false) {
                        String msg2 = "Sum of Physical,Scrap and Reworkable Quantity should be 1.";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
                    }
                } 
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(stkUpdtTblBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srScrpQty);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srPhyQty);
            
        }
    }


    public void binPhyQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        /*  binScrpQty.processUpdates(facesContext.getCurrentInstance());
        binRwkQty.processUpdates(facesContext.getCurrentInstance()); */

        if (object != null) {
            if (new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) < 0) {
                String msg2 = "Quantity must be >=0";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {

                Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            }
        }
    }

    public void binScrpQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*  binPhyQty.processUpdates(facesContext.getCurrentInstance());
        binRwkQty.processUpdates(facesContext.getCurrentInstance()); */
        if (object != null) {
            if (new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) < 0) {
                String msg2 = "Quantity must be >0";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {

                Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            }

            /*  else
                {
                OperationBinding op=getBindings().getOperationBinding("setDiffQtybyScrpForBin");
                      op.getParamsMap().put("Scrp",object);
                     op.execute();
                }   */
        }
    }

    public void binRwkQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*    binScrpQty.processUpdates(facesContext.getCurrentInstance());
        binPhyQty.processUpdates(facesContext.getCurrentInstance()); */
        if (object != null) {
            if (new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) < 0) {
                String msg2 = "Quantity must be >0";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {

                Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            }
        }
             else
                {
                OperationBinding op=getBindings().getOperationBinding("setDiffQtybyRwkForBin");
                      op.getParamsMap().put("Rwk",object);
                     op.execute();
                } 
        
    }

    public void lotPhyQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*  lotScrpQty.processUpdates(facesContext.getCurrentInstance());
        lotRwkQty.processUpdates(facesContext.getCurrentInstance()); */
        if (object != null) {
            if (new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) < 0) {
                String msg2 = "Quantity must be >=0";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {

                Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            }
        }
              else
                {
                OperationBinding op=getBindings().getOperationBinding("setDiffQtyForLot");
                      op.getParamsMap().put("Phy",object);
                     op.execute();
                } 
    
    }

    public void lotScrpQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*   lotPhyQty.processUpdates(facesContext.getCurrentInstance());
        lotRwkQty.processUpdates(facesContext.getCurrentInstance()); */
        if (object != null) {
            if (new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) < 0) {
                String msg2 = "Quantity must be >=0";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {

                Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            }
        }
             else
                {
                OperationBinding op=getBindings().getOperationBinding("setDiffQtybyScrpForLot");
                      op.getParamsMap().put("Scrp",object);
                     op.execute();
                } 
    
    }

    public void lotRwkQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*   lotPhyQty.processUpdates(facesContext.getCurrentInstance());
        lotRwkQty.processUpdates(facesContext.getCurrentInstance()); */
        if (object != null) {
            if (new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) < 0) {
                String msg2 = "Quantity must be >=0";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {

                Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            }
        }
              else
                {
                OperationBinding op=getBindings().getOperationBinding("setDiffQtybyRwkForLot");
                      op.getParamsMap().put("Rwk",object);
                     op.execute();
                } 
    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, BigDecimal total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void setStockTakeDetailLinkBinding(RichGoImageLink stockTakeDetailLinkBinding) {
        this.stockTakeDetailLinkBinding = stockTakeDetailLinkBinding;
    }

    public RichGoImageLink getStockTakeDetailLinkBinding() {
        return stockTakeDetailLinkBinding;
    }

    public void setStockTakeSummaryLinkBinding(RichGoImageLink stockTakeSummaryLinkBinding) {
        this.stockTakeSummaryLinkBinding = stockTakeSummaryLinkBinding;
    }

    public RichGoImageLink getStockTakeSummaryLinkBinding() {
        return stockTakeSummaryLinkBinding;
    }

    public void setStkUpdtTblBind(RichTable stkUpdtTblBind) {
        this.stkUpdtTblBind = stkUpdtTblBind;
    }

    public RichTable getStkUpdtTblBind() {
        return stkUpdtTblBind;
    }
}
