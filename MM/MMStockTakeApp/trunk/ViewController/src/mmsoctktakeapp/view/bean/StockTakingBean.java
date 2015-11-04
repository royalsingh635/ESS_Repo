package mmsoctktakeapp.view.bean;


import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

import mmsoctktakeapp.model.services.BarCodeData;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.nav.RichGoImageLink;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;
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
    private RichLink addlot;
    private RichLink saveLot;
    private RichLink addBin;
    private RichLink saveBin;
    private RichLink addSr;
    private RichLink saveSr;
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
    private RichLink additmBinding;
    private RichInputListOfValues itmnamebinding;
    private RichSelectOneChoice itmwhbinding;
    private RichSelectBooleanCheckbox selectallbinding;
    private RichPanelLabelAndMessage itemIdbinding;
    private RichOutputText itemidbinding;
    private RichLink okbuttonbinding;
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
    private RichLink saveDataUpdate;
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
    private RichLink editDataUpdate;
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
    //    private List<BarCodeData> list = new LinkedList<BarCodeData>();
    private List<BarCodeData> list = new ArrayList<BarCodeData>();
    private RichInputText whIdBind;
    private RichTable barcodeDataTableBind;
    private RichInputText barCodeItmIdBind;
    private RichInputText barCodeItmNmBind;
    private RichInputText barCodeWhNmBind;
    private RichInputText barcodeBinNmBind;
    private RichInputText barcodeBinIdBind;
    private RichInputText barcodeLotIdBind;
    private boolean load = false;
    private RichPopup barcodePopBind;
    private boolean isUpdated = false;
    private RichInputText barcodeSrIdBind;
    private RichInputText bcRwkQtyBind;
    private RichInputText bcPhyQtyBind;
    private RichInputText bcScrpQtyBind;
    private UploadedFile file;
    private RichInputFile inputFileBind;

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setList(List<BarCodeData> list) {
        this.list = list;
    }

    public List<BarCodeData> getList() {
        if (load == true) {
            Object obj = getBindings().getOperationBinding("getList").execute();
            if (obj != null) {
                list = (List<BarCodeData>) obj;
                load = false;
            }
        }
        return list;
    }

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

    public void setIsbin(String isbin) {
        this.isbin = isbin;
    }

    public String getIsbin() {
        return isbin;
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
        Integer st = (Integer) chkst.getResult();
        if (st == 354) {
            System.out.println("------------------------------=1");
            String stockno = (String) getBindings().getOperationBinding("genStkTkNo").execute();

            OperationBinding opc = getBindings().getOperationBinding("Commit");
            opc.execute();
            OperationBinding opce = getBindings().getOperationBinding("executeVos");
            opce.execute();

            mode = "SI";

            // String msg = "Saved Successfully.";
            String msg = resolvElDCMsg("#{bundle['MSG.227']}").toString();


            FacesMessage message2 = new FacesMessage(msg);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);

        } else {
            System.out.println("------------------------------=2");
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
                System.out.println("------------------------------=3");
                OperationBinding checkDU = getBindings().getOperationBinding("checkDataUpdated");
                checkDU.execute();
                String du = checkDU.getResult().toString();
                if (du.equals("0")) {
                    System.out.println("------------------------------=4");
                    msgonpop = false; //  Physical Quantity is not given for some Items.
                    showPopup(popupOnPage, true);
                } else if (du.equals("1")) {
                    System.out.println("------------------------------=5");
                    msgonpop = true; //Data Updated for all Items
                    showPopup(tempOrFinalPopup, true);

                }
                // showPopup(popupOnPage,true);


            } else {
                System.out.println("------------------------------=6");
                // String msg = ""This Slip is already saved and pending at other user for approval."";
                String msg = resolvElDCMsg("#{bundle['MSG.2262']}").toString();

                FacesMessage message2 = new FacesMessage(msg);
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
                // String msg = ("You are not valid user to edit this slip."";
                String msg1 = resolvElDCMsg("#{bundle['MSG.2268']}").toString();
                FacesMessage msg = new FacesMessage(msg1);
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
                (Integer) BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("generateFyId").execute();
            if (fyid > 0) {
                if (itmnamebinding.getValue() != null) {
                    if (selectallbinding.getValue().toString() != "true") {
                        if (itmwhbinding.getValue() != null) {
                            String itmId = (String) itemidbinding.getValue();
                            String whId = (String) itmwhbinding.getValue();
                            System.out.println("Value of WHID(one)::::::::::" + whId);
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
                            String stockno = (String) getBindings().getOperationBinding("genStkTkNo").execute();
                            OperationBinding opce = getBindings().getOperationBinding("executeVos");
                            opce.execute();
                            System.out.println("Stock number:" + stockno);
                            System.out.println("execute Vo");
                            itmwhbinding.setValue("");
                            itmnamebinding.setValue("");
                            itmwhbinding.setValue(null);
                            itmnamebinding.setValue(null);
                            // itmwhbinding.setDisabled(false);
                            mode = "AI";
                        } else {
                            // String msg = "Select Warehouse.";
                            String msg1 = resolvElDCMsg("#{bundle['MSG.800']}").toString();
                            FacesMessage msg = new FacesMessage(msg1);
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext ctx = FacesContext.getCurrentInstance();
                            ctx.addMessage(itmwhbinding.getClientId(), msg);
                        }
                    } else {
                        //System.out.println("in else condition(all warehouse)");
                        String itmId = (String) itemidbinding.getValue();
                        String whId = (String) itmwhbinding.getValue();
                        System.out.println("Value of WHID(all)::::::::::" + whId);
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
                        String stockno = (String) getBindings().getOperationBinding("genStkTkNo").execute();
                        System.out.println("Stock take no=" + stockno);
                        OperationBinding opce = getBindings().getOperationBinding("executeVos");
                        opce.execute();
                        System.out.println("Executed VO");

                    }
                    //                    }
                } else {
                    // String msg = Please Select Item.;
                    String msg1 = resolvElDCMsg("#{bundle['MSG.2488']}").toString();
                    FacesMessage msg = new FacesMessage(msg1);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(itmnamebinding.getClientId(), msg);
                }
            } else {
                // String msg ="Selected Date is not in Valid Financial Year.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.2489']}").toString();
                FacesMessage msg = new FacesMessage(msg1);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(dateBinding.getClientId(), msg);
            }
        } else {
            // String msg ="Please Select Date.";
            String msg1 = resolvElDCMsg("#{bundle['MSG.2490']}").toString();
            FacesMessage msg = new FacesMessage(msg1);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(dateBinding.getClientId(), msg);
        }
    }

    public void profileChangeListener(ValueChangeEvent valueChangeEvent) {
        //  whIdBinding.setValue(null);
        OperationBinding op = getBindings().getOperationBinding("profileCriteriaChange");
        op.getParamsMap().put("profile", valueChangeEvent.getNewValue());
        Crtype = (String) op.execute();
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
                String issr = (String) opitmsr.getResult();

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
        String sr = (String) opitmsr.execute();
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

    public void setAddlot(RichLink addlot) {
        this.addlot = addlot;
    }

    public RichLink getAddlot() {
        return addlot;
    }

    public void setSaveLot(RichLink saveLot) {

        this.saveLot = saveLot;
    }

    public RichLink getSaveLot() {
        return saveLot;
    }

    public void setAddBin(RichLink addBin) {
        this.addBin = addBin;
    }

    public RichLink getAddBin() {
        return addBin;
    }

    public void setSaveBin(RichLink saveBin) {
        this.saveBin = saveBin;
    }

    public RichLink getSaveBin() {
        return saveBin;
    }

    public void setAddSr(RichLink addSr) {
        this.addSr = addSr;
    }

    public RichLink getAddSr() {
        return addSr;
    }

    public void setSaveSr(RichLink saveSr) {
        this.saveSr = saveSr;
    }

    public RichLink getSaveSr() {
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
        System.out.println("warehouse:" + whIdBinding.getValue());
        if (whIdBinding.getValue() != null) {
            System.out.println("date:" + dateBinding.getValue());
            if (dateBinding.getValue() != null) {

                Integer fyid =
                    (Integer) BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("generateFyId").execute();
                System.out.println("Financial Year id :" + fyid);
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
                    String stockno = (String) getBindings().getOperationBinding("genStkTkNo").execute();
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
                    // String msg ="Selected Date is not in valid financial year.";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.2489']}").toString();
                    FacesMessage msg = new FacesMessage(msg1);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(dateBinding.getClientId(), msg);
                }
            } else {
                //String msg="select Date";
                FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.2265']}").toString());
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(dateBinding.getClientId(), msg);

            }

        } else {
            // String msg="Select Warehouse."
            FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.800']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(whIdBinding.getClientId(), msg);
        }

    }

    public void setAdditmBinding(RichLink additmBinding) {
        this.additmBinding = additmBinding;
    }

    public RichLink getAdditmBinding() {
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

    public void setOkbuttonbinding(RichLink okbuttonbinding) {
        this.okbuttonbinding = okbuttonbinding;
    }

    public RichLink getOkbuttonbinding() {
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
        System.out.println(" Value of valueChangeEvent:" + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "") {
            String itm = (String) valueChangeEvent.getNewValue();
            System.out.println("Itm in searchItemChange:" + itm);
            OperationBinding opitmsr = getBindings().getOperationBinding("checkItmidSerialize");
            opitmsr.getParamsMap().put("ItmId", itm);
            opitmsr.execute();
            String is = (String) opitmsr.getResult();
            System.out.println("Result is:" + is);
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
        System.out.println("Binding WareHouse:" + searchWhBinding.getValue());
        System.out.println("Binding Item:" + searchItmBinding.getValue());
        if (searchWhBinding.getValue() != null && searchWhBinding.getValue() != "") {
            if (searchItmBinding.getValue() != null && searchItmBinding.getValue() != "") {
                OperationBinding opd = getBindings().getOperationBinding("searchItems");
                opd.execute();
                //System.out.println("update item value:"+searchItmBinding.getValue());
            } else {
                // String msg="Please Select Item.";
                FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.2488']}").toString());
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(searchItmBinding.getClientId(), msg);
            }
        } else {
            // String msg="Please Select Warehouse."
            FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1915']}").toString());
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

    public void setSaveDataUpdate(RichLink saveDataUpdate) {
        this.saveDataUpdate = saveDataUpdate;
    }

    public RichLink getSaveDataUpdate() {
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

    public void setEditDataUpdate(RichLink editDataUpdate) {
        this.editDataUpdate = editDataUpdate;
    }

    public RichLink getEditDataUpdate() {
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

                    //String msg2 = "This Slip is pending at other user , You can not Edit this.";
                    String msg = resolvElDCMsg("#{bundle['MSG.2266']}").toString();
                    FacesMessage message2 = new FacesMessage(msg);
                    message2.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                }
            else {
                //   String msg2 = "Something went Wrong.Please Contact ESS!";
                String msg = resolvElDCMsg("#{bundle['MSG.2267']}").toString();

                FacesMessage message2 = new FacesMessage(msg);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
        } else {
            //String msg2 = "This Slip is pending at other user , You can not Edit this.";
            String msg = resolvElDCMsg("#{bundle['MSG.2266']}").toString();
            FacesMessage message2 = new FacesMessage(msg);
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
                        // String msg2 = "This Slip is pending at other user for approval, You can not forward this.";
                        String msg = resolvElDCMsg("#{bundle['MSG.1054']}").toString();
                        FacesMessage message2 = new FacesMessage(msg);
                        message2.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2);
                        return null;
                    }
                } else {
                    //String msg2 = "Something went wrong (user level in workflow).Please contact to ESS!";
                    String msg = resolvElDCMsg("#{bundle['MSG.2269']}").toString();
                    FacesMessage message2 = new FacesMessage(msg);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                    return null;
                }


            } else {
                //String msg2 = "Workflow for this Document is not Created.";
                String msg = resolvElDCMsg("#{bundle['MSG.2270']}").toString();
                FacesMessage message2 = new FacesMessage(msg);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            }
        }


        else {
            //not saved slip
            //            String msg2 =
            //                "This Slip is not saved or Physical Quantity for all items is not given, You can not forward this.";
            String msg = resolvElDCMsg("#{bundle['MSG.2271']}").toString();
            FacesMessage message2 = new FacesMessage(msg);
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
            //  String msg="Saved Successfully"

            FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.227']}").toString());
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
            flg = (Boolean) cs.getResult();
        if (flg == true) {
            String stockno = (String) getBindings().getOperationBinding("genStkTkNo").execute();
            OperationBinding opc = getBindings().getOperationBinding("Commit");
            opc.execute();
            OperationBinding opce = getBindings().getOperationBinding("executeVos");
            opce.execute();
            mode = "SF";
            // String msg2 = "Freezed Successfully.Now You can not Change Items.";
            String msg = resolvElDCMsg("#{bundle['MSG.2491']}").toString();
            FacesMessage message2 = new FacesMessage(msg);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);

        } else {
            // String msg2 = "Can not Freeze this time.Add Some items First.";
            String msg = resolvElDCMsg("#{bundle['MSG.2273']}").toString();
            FacesMessage message2 = new FacesMessage(msg);
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
        // String msg = "Saved Duccessfully";
        //String msg = resolvElDCMsg("#{bundle['MSG.2262']}").toString();
        FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.227']}").toString());
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
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1649']}").toString(), null));

            } else {
                System.out.println("set 2");

                srScrpQty.processUpdates(facesContext.getCurrentInstance());
                srRwkQty.processUpdates(facesContext.getCurrentInstance());

                OperationBinding op = getBindings().getOperationBinding("ChkSumForPhySr");
                op.getParamsMap().put("Phy", object);
                op.execute();
                if (op.getErrors().size() == 0) {
                    Boolean flg = (Boolean) op.getResult();
                    if (flg == false) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvElDCMsg("#{bundle['MSG.1650']}").toString(),
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
                String msg2 = resolvElDCMsg("#{bundle['MSG.1649']}").toString();

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));

            } else {
                System.out.println("in else of SrScrpQtyValidator");
                OperationBinding op = getBindings().getOperationBinding("ChkSumForScrpSr");
                op.getParamsMap().put("Scrp", object);
                op.execute();
                if (op.getErrors().size() == 0) {
                    Boolean flg = (Boolean) op.getResult();
                    if (flg == false) {
                        String msg2 = resolvElDCMsg("#{bundle['MSG.1651']}").toString();

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
                String msg2 = resolvElDCMsg("#{bundle['MSG.1649']}").toString();

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {
                System.out.println("SrRwkQtyValidator ");
                OperationBinding op = getBindings().getOperationBinding("ChkSumForRwkSr");
                op.getParamsMap().put("Rwk", object);
                op.execute();
                if (op.getErrors().size() == 0) {
                    Boolean flg = (Boolean) op.getResult();
                    if (flg == false) {
                        String msg2 = resolvElDCMsg("#{bundle['MSG.1652']}").toString();
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
                                                                  resolvElDCMsg("#{bundle['MSG.1107']}").toString(),
                                                                  null));
                }
            }
        }
    }

    public void binScrpQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*  binPhyQty.processUpdates(facesContext.getCurrentInstance());
        binRwkQty.processUpdates(facesContext.getCurrentInstance()); */
        if (object != null) {
            if (new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) < 0) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.1653']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {

                Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.1107']}").toString(),
                                                                  null));
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
                String msg2 = resolvElDCMsg("#{bundle['MSG.1653']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {

                Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.1107']}").toString(),
                                                                  null));
                }
            }
        } else {
            OperationBinding op = getBindings().getOperationBinding("setDiffQtybyRwkForBin");
            op.getParamsMap().put("Rwk", object);
            op.execute();
        }

    }

    public void lotPhyQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*  lotScrpQty.processUpdates(facesContext.getCurrentInstance());
        lotRwkQty.processUpdates(facesContext.getCurrentInstance()); */
        if (object != null) {
            if (new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) < 0) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.1654']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {

                Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.1107']}").toString(),
                                                                  null));
                }
            }
        } else {
            OperationBinding op = getBindings().getOperationBinding("setDiffQtyForLot");
            op.getParamsMap().put("Phy", object);
            op.execute();
        }

    }

    public void lotScrpQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*   lotPhyQty.processUpdates(facesContext.getCurrentInstance());
        lotRwkQty.processUpdates(facesContext.getCurrentInstance()); */
        if (object != null) {
            if (new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) < 0) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.1654']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {

                Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.1107']}").toString(),
                                                                  null));
                }
            }
        } else {
            OperationBinding op = getBindings().getOperationBinding("setDiffQtybyScrpForLot");
            op.getParamsMap().put("Scrp", object);
            op.execute();
        }

    }

    public void lotRwkQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*   lotPhyQty.processUpdates(facesContext.getCurrentInstance());
        lotRwkQty.processUpdates(facesContext.getCurrentInstance()); */
        if (object != null) {
            if (new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) < 0) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.1654']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            } else {

                Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.1107']}").toString(),
                                                                  null));
                }
            }
        } else {
            OperationBinding op = getBindings().getOperationBinding("setDiffQtybyRwkForLot");
            op.getParamsMap().put("Rwk", object);
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

    public void addToListAction(ActionEvent actionEvent) {
        //whIdBind
        /* BarCodeData b =
            new BarCodeData(whIdBind.getValue().toString(), barCodeWhNmBind.getValue().toString(),
                            barCodeItmIdBind.getValue().toString(), barCodeItmNmBind.getValue().toString(),
                            barcodeBinIdBind.getValue().toString(), barcodeBinNmBind.getValue().toString(),
                            barcodeLotIdBind.getValue().toString());
        boolean add = list.add(b);
        System.out.println("add: " + add); */
        int checkForValidData = checkForValidData();
        if (checkForValidData != 0)
            return;
        if (barcodeSrIdBind.getValue() != null) {
            Object object = bcRwkQtyBind.getValue();
            Object object_2 = bcPhyQtyBind.getValue();
            Object object_3 = bcScrpQtyBind.getValue();

            oracle.jbo.domain.Number phyQty = new oracle.jbo.domain.Number(0);
            oracle.jbo.domain.Number rwk = new oracle.jbo.domain.Number(0);
            oracle.jbo.domain.Number scrp = new oracle.jbo.domain.Number(0);
            if (object != null) {
                rwk = (oracle.jbo.domain.Number) object;
            }
            if (object_2 != null) {
                phyQty = (oracle.jbo.domain.Number) object_2;
            }
            if (object_3 != null) {
                scrp = (oracle.jbo.domain.Number) object_3;
            }
            oracle.jbo.domain.Number tot = phyQty.add(scrp).add(rwk);
            if (tot.compareTo(1) > 0) {
                // String msg = "Total Quantity must be One for serialized item.";
                String msg = resolvElDCMsg("#{bundle['MSG.2492']}").toString();
                FacesMessage message2 = new FacesMessage(msg);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return;
            }
        }

        /*  if (true)
            return; */
        Object op = getBindings().getOperationBinding("addToBarcodeList").execute();
        if (op != null) {
            Map map = (Map) op;
            BarCodeData data =
                new BarCodeData((String) map.get("WhId"), (String) map.get("WhNm"), (String) map.get("ItmId"),
                                (String) map.get("ItmNm"), (String) map.get("BinId"), (String) map.get("BinNm"),
                                (String) map.get("LotId"), (String) map.get("Uom"), (String) map.get("SerFlg"),
                                (String) map.get("SrNo"), (oracle.jbo.domain.Number) map.get("PhyQty"),
                                (oracle.jbo.domain.Number) map.get("RwkQty"),
                                (oracle.jbo.domain.Number) map.get("ScrpQty"));
            if (list.contains(data)) {
                if (data.getSerFlg().equalsIgnoreCase("y")) {
                    // String msg = "Duplicate Data !!";
                    String msg = resolvElDCMsg("#{bundle['MSG.2493']}").toString();
                    FacesMessage message2 = new FacesMessage(msg);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(barcodeSrIdBind.getClientId(), message2);
                } else {
                    /*  FacesMessage message2 = new FacesMessage("Duplicate Data !!");
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2); */
                    int binarySearch = Collections.binarySearch(list, data);
                    System.out.println("Binary search index is: " + binarySearch);
                    BarCodeData barCodeData = list.get(binarySearch);
                    barCodeData.setScrpQty(barCodeData.getScrpQty().add(data.getScrpQty()));
                    barCodeData.setRwkQty(barCodeData.getRwkQty().add(data.getRwkQty()));
                    barCodeData.setPhyQty(barCodeData.getPhyQty().add(data.getPhyQty()));
                }
                //     return;
            } else {
                isUpdated = true;
                list.add(data);
                Collections.sort(list);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeDataTableBind);
    }

    public int checkForValidData() {
        int x = 0;
        Object execute = getBindings().getOperationBinding("chkBcFieldValid").execute();
        if (execute != null) {
            x = (Integer) execute;
        }
        System.out.println("val:: " + x);
        String msg = "";
        String clientId = null;
        switch (x) {
        case 1:
            msg = "Please enter warehouse";
            clientId = whIdBind.getClientId();
            break;
        case 2:
            msg = "Please enter Item";
            clientId = barCodeItmIdBind.getClientId();
            break;
        case 3:
            msg = "Please enter lot";
            clientId = barcodeLotIdBind.getClientId();
            break;
        case 4:
            msg = "Please enter bin";
            clientId = barcodeBinIdBind.getClientId();
            break;
        case 5:
            msg = "please enter Serial";
            clientId = barcodeSrIdBind.getClientId();
            break;
        }
        if (x > 0) {
            FacesMessage fms = new FacesMessage(msg);
            fms.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(clientId, fms);
            //   throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, clientId));
        }

        return x;
    }

    public void setWhIdBind(RichInputText whIdBind) {
        this.whIdBind = whIdBind;
    }

    public RichInputText getWhIdBind() {
        return whIdBind;
    }

    public void setBarcodeDataTableBind(RichTable barcodeDataTableBind) {
        this.barcodeDataTableBind = barcodeDataTableBind;
    }

    public RichTable getBarcodeDataTableBind() {
        return barcodeDataTableBind;
    }

    public void setBarCodeItmIdBind(RichInputText barCodeItmIdBind) {
        this.barCodeItmIdBind = barCodeItmIdBind;
    }

    public RichInputText getBarCodeItmIdBind() {
        return barCodeItmIdBind;
    }

    public void setBarCodeItmNmBind(RichInputText barCodeItmNmBind) {
        this.barCodeItmNmBind = barCodeItmNmBind;
    }

    public RichInputText getBarCodeItmNmBind() {
        return barCodeItmNmBind;
    }

    public void setBarCodeWhNmBind(RichInputText barCodeWhNmBind) {
        this.barCodeWhNmBind = barCodeWhNmBind;
    }

    public RichInputText getBarCodeWhNmBind() {
        return barCodeWhNmBind;
    }

    public void setBarcodeBinNmBind(RichInputText barcodeBinNmBind) {
        this.barcodeBinNmBind = barcodeBinNmBind;
    }

    public RichInputText getBarcodeBinNmBind() {
        return barcodeBinNmBind;
    }

    public void setBarcodeBinIdBind(RichInputText barcodeBinIdBind) {
        this.barcodeBinIdBind = barcodeBinIdBind;
    }

    public RichInputText getBarcodeBinIdBind() {
        return barcodeBinIdBind;
    }

    public void setBarcodeLotIdBind(RichInputText barcodeLotIdBind) {
        this.barcodeLotIdBind = barcodeLotIdBind;
    }

    public RichInputText getBarcodeLotIdBind() {
        return barcodeLotIdBind;
    }

    public void rowDeleteAction(ActionEvent actionEvent) {
        // Add event code here...
        Object data = barcodeDataTableBind.getSelectedRowData();
        BarCodeData barCode = (BarCodeData) data;
        list.remove(barCode);
    }

    public void barcodeDialogListener(DialogEvent dialogEvent) {
        System.out.println("in dialog 1");
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            System.out.println("in dialog 2");
            if (isUpdated) {
                OperationBinding binding = getBindings().getOperationBinding("insertToTable");
                binding.getParamsMap().put("BarData", list);
                binding.execute();
            }
            System.out.println("in dialog");
            getBindings().getOperationBinding("updateTableFromBC").execute();
        }
    }

    public void setBarcodePopBind(RichPopup barcodePopBind) {
        this.barcodePopBind = barcodePopBind;
    }

    public RichPopup getBarcodePopBind() {
        return barcodePopBind;
    }

    public void showBarcodePopAction(ActionEvent actionEvent) {
        load = true;
        showPopup(barcodePopBind, true);
    }

    public void setBarcodeSrIdBind(RichInputText barcodeSrIdBind) {
        this.barcodeSrIdBind = barcodeSrIdBind;
    }

    public RichInputText getBarcodeSrIdBind() {
        return barcodeSrIdBind;
    }

    public void phyQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            oracle.jbo.domain.Number n = (oracle.jbo.domain.Number) object;
            if (n.compareTo(0) < 0) {
                // String msg = "Qunatity can not be negative.";
                String msg = resolvElDCMsg("#{bundle['MSG.2282']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
            if (!is) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1107']}").toString(), null));
            }
        }
    }

    public void bcrwkQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            oracle.jbo.domain.Number n = (oracle.jbo.domain.Number) object;
            if (n.compareTo(0) < 0) {
                // String msg = "Qunatity can not be negative.";
                String msg = resolvElDCMsg("#{bundle['MSG.2282']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
            if (!is) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1107']}").toString(), null));
            }

        }
    }

    public void bcScrapQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            oracle.jbo.domain.Number n = (oracle.jbo.domain.Number) object;
            if (n.compareTo(0) < 0) {
                // String msg = "Qunatity can not be negative.";
                String msg = resolvElDCMsg("#{bundle['MSG.2282']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            Boolean is = isPrecisionValid(26, 6, new BigDecimal(object.toString()));
            if (!is) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1107']}").toString(), null));
            }
        }
    }

    public void setBcRwkQtyBind(RichInputText bcRwkQtyBind) {
        this.bcRwkQtyBind = bcRwkQtyBind;
    }

    public RichInputText getBcRwkQtyBind() {
        return bcRwkQtyBind;
    }

    public void setBcPhyQtyBind(RichInputText bcPhyQtyBind) {
        this.bcPhyQtyBind = bcPhyQtyBind;
    }

    public RichInputText getBcPhyQtyBind() {
        return bcPhyQtyBind;
    }

    public void setBcScrpQtyBind(RichInputText bcScrpQtyBind) {
        this.bcScrpQtyBind = bcScrpQtyBind;
    }

    public RichInputText getBcScrpQtyBind() {
        return bcScrpQtyBind;
    }

    public void uploadFileAction(ActionEvent actionEvent) {
        // Add event code here... application/csv
        if (file != null) {
            System.out.println("---filetype--- " + file.getContentType());
            if ((file.getContentType().equalsIgnoreCase("application/csv")) ||
                (file.getContentType().equalsIgnoreCase("text/csv"))) {
                try {
                    InputStream input = file.getInputStream();
                    Scanner in = new Scanner(input);
                    /* String clause =
                        "CldId = '?' and SlocId = ? and OrgId = '?' and  DocId = '?' and WhId = '?' and ItmId = '?' and SrNo = '?' and LotId = '?' and BinId= '?' ";
                     */
                    if (in.hasNextLine())
                        in.nextLine();

                    while (in.hasNextLine()) {
                        String line = in.nextLine();
                        String clause =
                            "CldId = '?' and SlocId = ? and OrgId = '?' and  DocId = '?' and WhId = '?' and ItmId = '?' and SrNo = '?' and LotId = '?' and BinId= '?' ";

                        //              System.out.println("---------------value are-- " + line + "   " + line.contains("@"));
                        String[] split = line.split(",");
                        if (split.length < 12)
                            continue;
                        for (int i = 0, count = 0; i < 9; i++, count++) {
                            int pos = clause.indexOf("?");
                            String name = "";
                            switch (i) {
                            case 2:
                                count++;
                                break;
                            case 7:
                                count += 4;
                                break;
                            }
                            /*  if (split.length == 12) {
                                name = "0";
                            } else {
                                name = split[count];
                            } */
                            ///change to remove special character
                            if (split.length == 12) {
                                name = "0";
                            } else if (i == 0) {
                                name = split[0].toString().replace("@", "");
                            } else if (i == 2) {
                                name = split[2].toString().replace("@", "");
                            } else {
                                name = split[count];
                            }
                            ///
                            //            System.out.println("-name = " + name);
                            //                System.out.println("---------clause--- " + pos + " " + clause + " contains " +
                            //                                 clause.contains("@"));
                            clause = clause.substring(0, pos) + name + clause.substring(pos + 1, clause.length());
                        }
                        OperationBinding binding = getBindings().getOperationBinding("updatebcFromCsv");
                        binding.getParamsMap().put("query", clause);
                        binding.getParamsMap().put("pqty", new oracle.jbo.domain.Number(split[9])); //PHY QTY
                        binding.getParamsMap().put("rqty", new oracle.jbo.domain.Number(split[10])); //RWA
                        binding.getParamsMap().put("sqty", new oracle.jbo.domain.Number(split[11])); // SCRAP
                        binding.getParamsMap().put("unit", split[8]); // UNIT
                        Object execute = binding.execute();

                        //      System.out.println("------------gettting qty ------phy qty " + split[9] + " rwk qty " +
                        //                       split[10] + " scrp " + split[11]);
                    }
                    //System.out.println("clause is: " + clause);

                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                OperationBinding opc = getBindings().getOperationBinding("Execute");
                opc.execute();
                load = true;
            } else {
                // please select a csv file format ..
                // String msg = "Please select csv File Format !!";
                String msg = resolvElDCMsg("#{bundle['MSG.2494']}").toString();
                FacesMessage fms = new FacesMessage(msg);
                fms.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(inputFileBind.getClientId(), fms);
            }
        }

        setFile(null);

    }

    public void setInputFileBind(RichInputFile inputFileBind) {
        this.inputFileBind = inputFileBind;
    }

    public RichInputFile getInputFileBind() {
        return inputFileBind;
    }

    public void exportDataAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void exportDataDAL(FacesContext facesContext, OutputStream outputStream) throws IOException {
        OperationBinding op = this.getBindings().getOperationBinding("filterStkTakeEprtVO");
        Object obj = op.execute();
        System.out.println("------>>>>> " + obj);
        byte[] b = new byte[0];
        if (obj != null) {
            b = obj.toString().getBytes();
        }
        outputStream.write(b);
        outputStream.close();
        facesContext.responseComplete();
    }
}
