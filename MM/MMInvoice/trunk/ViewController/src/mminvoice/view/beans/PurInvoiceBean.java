package mminvoice.view.beans;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandToolbarButton;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

/**
 * Purchase Invoice Managed Bean.
 * Contains all the business logic for Invoice functionalities.
 * @author SM,NK and AK
 * Dated -25/10/2013
 * */
public class PurInvoiceBean {
    private static ADFLogger _log = ADFLogger.createADFLogger(PurInvoiceBean.class);
    private Integer amt_digit = Integer.parseInt(resolvElAmt("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());
    private RichInputText rcptDocIdVar;
    private RichPopup poTaxPopUp;
    private RichPopup itmTaxPopUp;
    private RichPopup poOcPopup;
    private RichInputText taxableAmtItm;
    private RichSelectOneChoice poTaxRule;
    private RichTable poItemTable;
    private RichTable ocTableBind;
    private RichTable poTableBind;
    private RichInputText rcptTotalVar;
    private RichPopup itmTdsPopUp;
    private RichInputText tdsOnAmtItm;
    private RichInputDate paymentDateVar;
    private RichSelectOneChoice paymentModeVar;
    private RichInputText paymentAmtVar;
    private RichSelectBooleanCheckbox paymentAdvFlgVar;
    private RichTable paymentSchdTblVar;
    private RichOutputText totalAdjtAmtBindVar;
    private RichInputText bindAdjAmtVar;
    private RichPopup duplicateDatePopUpVar;
    private Date date = (Date) Date.getCurrentDate();
    private RichOutputText totalPayAmtBindVar;
    private RichSelectOneChoice invcTypeBindVar;
    private RichInputComboboxListOfValues invcSuppBindVar;
    private RichPopup removeTrItmPopUp;
    private RichPanelFormLayout removeTdsPopUp;
    private RichPopup removePopUpTds;
    private RichInputText taxableAmtBindVar;
    private RichTable taxTableBindVar;
    private RichInputText tdsAmountBindVar;
    private RichTable tdsTableBindVar;
    private RichInputText currencyConFctBindVar;
    private String wfId;
    private String currencyName;
    private String suppCurrencyName;
    private RichSelectOneChoice payModeBindVar;
    private RichPopup removePOTaxBindVar;
    private RichInputListOfValues invcSuppBindVar1;
    private RichPopup removePopUpPaymentSchedule;
    private RichCommandImageLink saveForwardBtn;
    private RichShowDetailItem bindInvoiceTabVar;
    private RichInputComboboxListOfValues bindTransDocVar;

    private boolean showSuppTrfInvc = false;
    private boolean showSuppInvc = false;
    private boolean showcompInvc = true;
    private RichInputDate srchFrmDt;
    private RichSelectOneRadio bindOperationVar;
    private RichInputText bindAmtAdjtVar;
    private RichCommandToolbarButton bindVarContinueBtn;
    private RichCommandButton bindVarApplyBtn;
    private RichTable piTableBind;
    private RichTable selectedTblBind;
    private RichSelectOneChoice srcOrgBindVar;
    private RichCommandToolbarButton bindVarGoBackBtn;

    private String mode = "A";
    private RichInputListOfValues bindCoaNm;
    private RichInputText bindSpAmt;
    private RichInputDate srchInvFrmDt;
    private RichSelectOneRadio bindOperationVar1;
    private RichInputText bindAmtAdjtVar1;
    private RichTable selectedTblBind1;
    private RichTable piTableBind1;
    private RichCommandButton bindVarApplyBtn1;
    private RichInputText fileNamepath;
    private RichInputText taxableItmAmtBind;
    private RichInputText taxableItmAmtSpBind;
    private RichSelectOneChoice invcCrtdDate;
    private RichInputDate invcCrtDateBind;
    private RichInputText oldRcptQtyBind;
    private RichPanelGroupLayout panelGrpSrcBind;
    private RichPanelGroupLayout panelGrpHeaderBind;
    private RichSelectOneChoice tdsRuleIdBind;
    private RichPopup invcCnclPopupBind;
    private List<UploadedFile> uploadedFile;
    private RichInputText fileBindName;
    private String topWFUsrLevel = "N";
    private RichTable attachTableBind;
    private RichLink deleteLinkBind;

    public void setTopWFUsrLevel(String topWFUsrLevel) {
        this.topWFUsrLevel = topWFUsrLevel;
    }

    public void setUploadedFile(List<UploadedFile> uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return uploadedFile;
    }
    // Connection con = DriverManager.getConnection("");

    public PurInvoiceBean() {
    }

    public void setRcptDocIdVar(RichInputText rcptDocIdVar) {
        this.rcptDocIdVar = rcptDocIdVar;
    }

    public RichInputText getRcptDocIdVar() {
        return rcptDocIdVar;
    }


    public void setPoTaxPopUp(RichPopup poTaxPopUp) {
        this.poTaxPopUp = poTaxPopUp;
    }

    public RichPopup getPoTaxPopUp() {
        return poTaxPopUp;
    }

    public void setItmTaxPopUp(RichPopup itmTaxPopUp) {
        this.itmTaxPopUp = itmTaxPopUp;
    }

    public RichPopup getItmTaxPopUp() {
        return itmTaxPopUp;
    }


    public void cancelPoTaxAction(ActionEvent actionEvent) {
        poTaxPopUp.hide();
    }

    public void poOcAction(ActionEvent actionEvent) {
        /*    Integer type = Integer.parseInt(invcTypeBindVar.getValue().toString());
            if(type == 456)
                {
                    OperationBinding chkTaxOp = getBindings().getOperationBinding("checkTaxRuleApplied");
                    chkTaxOp.execute();
                    Boolean b =(Boolean)chkTaxOp.getResult();
                    if(!b)
                    {
                            FacesMessage message2 = new FacesMessage("For Same Organisation,Other Charges can not be apply.");
                            message2.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message2);
                            return;
                    }
                } */
        showPopup(poOcPopup, true);
    }

    public void setPoOcPopup(RichPopup poOcPopup) {
        this.poOcPopup = poOcPopup;
    }

    public RichPopup getPoOcPopup() {
        return poOcPopup;
    }

    public void setTaxableAmtItm(RichInputText taxableAmtItm) {
        this.taxableAmtItm = taxableAmtItm;
    }


    public void setPoTaxRule(RichSelectOneChoice poTaxRule) {
        this.poTaxRule = poTaxRule;
    }

    public RichSelectOneChoice getPoTaxRule() {
        return poTaxRule;
    }

    public void setPoItemTable(RichTable poItemTable) {
        this.poItemTable = poItemTable;
    }

    public RichTable getPoItemTable() {
        return poItemTable;
    }

    public RichInputText getTaxableAmtItm() {
        return taxableAmtItm;
    }

    public void setOcTableBind(RichTable ocTableBind) {
        this.ocTableBind = ocTableBind;
    }

    public RichTable getOcTableBind() {
        return ocTableBind;
    }

    public void setPoTableBind(RichTable poTableBind) {
        this.poTableBind = poTableBind;
    }

    public RichTable getPoTableBind() {
        return poTableBind;
    }

    public void setRcptTotalVar(RichInputText rcptTotalVar) {
        this.rcptTotalVar = rcptTotalVar;
    }

    public void setItmTdsPopUp(RichPopup itmTdsPopUp) {
        this.itmTdsPopUp = itmTdsPopUp;
    }

    public RichPopup getItmTdsPopUp() {
        return itmTdsPopUp;
    }

    public void setTdsOnAmtItm(RichInputText tdsOnAmtItm) {
        this.tdsOnAmtItm = tdsOnAmtItm;
    }

    public RichInputText getTdsOnAmtItm() {
        return tdsOnAmtItm;
    }

    public RichInputText getRcptTotalVar() {
        return rcptTotalVar;
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

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public void addReceiptAction(ActionEvent actionEvent) {
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

        if (invcTypeBindVar.getValue() == null) {
            return;
        }

        else if (invcTypeBindVar.getValue() != null && invcTypeBindVar.getValue().toString().length() <= 0) {
            return;
        }

        else if (invcSuppBindVar1.getValue() == null && invcTypeBindVar.getValue().toString().equals("455")) {

            return;
        }

        else if (invcSuppBindVar1.getValue() != null && invcSuppBindVar1.getValue().toString().length() <= 0 &&
                 invcTypeBindVar.getValue().toString().equals("455")) {
            return;
        }


        /**
         * Populate data from receipt in case of purchase invoice
         * */
        if (Integer.valueOf(invcTypeBindVar.getValue().toString()) == 455) {

            OperationBinding geteo = getBindings().getOperationBinding("getCoaForEo");
            geteo.getParamsMap().put("hoOrgId", paramHoOrgId);
            geteo.getParamsMap().put("eoName", invcSuppBindVar1.getValue().toString());
            geteo.execute();
            // System.out.println("OUT : "+geteo.getResult());
            if (geteo.getResult() != null) {
                Integer coaId = (Integer) geteo.getResult();
                if (coaId < 0) {
                    String msg = resolvEl("#{bundle['LBL.3107']}").toString();
                    //Cannot select this supplier.Error in COA (Multiple).
                    showFacesMessage(msg, "E", false, "F");
                    return;
                } else {
                    OperationBinding setCoa = getBindings().getOperationBinding("setcoaIdForInvc");
                    setCoa.getParamsMap().put("coa", coaId);
                    setCoa.execute();
                }
            } else {
                return;
            }

            OperationBinding getSuppDoc = getBindings().getOperationBinding("checkSuppDoc");
            getSuppDoc.execute();
            if (getSuppDoc.getResult() != null) {
                if ((Integer) getSuppDoc.getResult() == 0) {
                    //Supplier document not received. can not proceed.
                    String msg = resolvEl("#{bundle['MSG.2216']}").toString();
                    showFacesMessage(msg, "E", false, "F");
                    return;
                }
            } else {
                return;
            }

            // System.out.println("Populate Receipt");
            OperationBinding popInvOp = getBindings().getOperationBinding("populateInvoice");
            popInvOp.getParamsMap().put("CldId", paramCldId);
            popInvOp.getParamsMap().put("SlocId", paramSlocId);
            popInvOp.getParamsMap().put("OrgId", paramOrgId);
            popInvOp.getParamsMap().put("rcptId", null);
            popInvOp.getParamsMap().put("UsrId", paramUsrId);
            popInvOp.execute();
            if (popInvOp.getResult() != null) {
                if (Integer.parseInt(popInvOp.getResult().toString()) == 1) {
                    _log.info("Invoice Populated");
                    OperationBinding exItmOp = getBindings().getOperationBinding("Execute");
                    exItmOp.execute();
                    OperationBinding exSrOp = getBindings().getOperationBinding("Execute1");
                    exSrOp.execute();
                    OperationBinding exdltOp = getBindings().getOperationBinding("Execute3");
                    exdltOp.execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);

                } else if (Integer.parseInt(popInvOp.getResult().toString()) == -2) {
                    //Please Select the Document.
                    showFacesMessage(resolvEl("#{bundle['MSG.1161']}"), "I", false, "F");
                }
            } else {
                _log.info("Invoice Population error occurred.");
            }
        }

        /**populate data in case of material in case of receipt trasnfer order*/

        if (Integer.valueOf(invcTypeBindVar.getValue().toString()) == 738 && srcOrgBindVar.getValue() != null) {
            OperationBinding operationBinding = getBindings().getOperationBinding("setCurrencyFactor");
            operationBinding.getParamsMap().put("OrgId", srcOrgBindVar.getValue().toString());
            operationBinding.execute();
            Object obj = operationBinding.getResult();

            if (obj != null) {
                if (obj.toString().equals("-1")) { //  String msg=resolvEl("#{bundle['LBL.3327']}");
                    //String msg ="Currency Factor Not Found against this Organisation.Please Check In Organisation SetUp.";
                    String msg = resolvEl("#{bundle['LBL.3329']}");
                    showFacesMessage(msg, "E", false, "F");
                    return;
                }
                if (obj.toString().equals("-2")) {
                    String msg = resolvEl("#{bundle['LBL.3327']}");
                    //   String msg = "Transfer A/c Not Found against this Organisation.Please Check In MM Profile.";
                    showFacesMessage(msg, "E", false, "F");
                    return;
                }
            } else {
                //  String msg=resolvEl("#{bundle['LBL.3330']}");
                String msg = "Can not proceed further!!.";
                showFacesMessage(msg, "E", false, "F");
                return;
            }


            OperationBinding popInvOp = getBindings().getOperationBinding("populateInvoice");
            popInvOp.getParamsMap().put("CldId", paramCldId);
            popInvOp.getParamsMap().put("SlocId", paramSlocId);
            popInvOp.getParamsMap().put("OrgId", paramOrgId);
            popInvOp.getParamsMap().put("rcptId", null);
            popInvOp.getParamsMap().put("UsrId", paramUsrId);
            popInvOp.execute();
            if (popInvOp.getResult() != null) {
                if (Integer.parseInt(popInvOp.getResult().toString()) == 1) {
                    _log.info("Invoice Populated By Invoice");
                    OperationBinding exItmOp = getBindings().getOperationBinding("Execute");
                    exItmOp.execute();
                    OperationBinding exSrOp = getBindings().getOperationBinding("Execute1");
                    exSrOp.execute();
                    OperationBinding exdltOp = getBindings().getOperationBinding("Execute3");
                    exdltOp.execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);

                } else if (Integer.parseInt(popInvOp.getResult().toString()) == -2) {
                    //Please Select the Document.
                    showFacesMessage(resolvEl("#{bundle['MSG.1161']}"), "I", false, "F");
                }
            } else {
                _log.info("Invoice Population error occurred.");
            }
        }

        /**
         * Populate data from material issue and transfer order
         * */
        if (Integer.valueOf(invcTypeBindVar.getValue().toString()) == 456 && srcOrgBindVar.getValue() != null) {
            OperationBinding operationBinding = getBindings().getOperationBinding("setCurrencyFactor");
            operationBinding.getParamsMap().put("OrgId", srcOrgBindVar.getValue().toString());
            operationBinding.execute();
            Object obj = operationBinding.getResult();

            if (obj != null) {
                if (obj.toString().equals("-1")) { //
                    //                    String msg ="Currency Factor Not Found against this Organisation.Please Check In Organisation SetUp.";
                    String msg = resolvEl("#{bundle['LBL.3329']}");
                    showFacesMessage(msg, "E", false, "F");
                    return;
                }
                if (obj.toString().equals("-2")) {
                    // String msg = "Transfer A/c Not Found against this Organisation.Please Check In MM Profile.";
                    String msg = resolvEl("#{bundle['LBL.3327']}");
                    showFacesMessage(msg, "E", false, "F");
                    return;
                }
            } else {
                String msg = resolvEl("#{bundle['LBL.3330']}");
                //  String msg = "Can not proceed further!!.";
                showFacesMessage(msg, "E", false, "F");
                return;
            }


            OperationBinding popInvOp = getBindings().getOperationBinding("populateInvoiceByIssue");
            popInvOp.getParamsMap().put("CldId", paramCldId);
            popInvOp.getParamsMap().put("SlocId", paramSlocId);
            popInvOp.getParamsMap().put("OrgId", paramOrgId);
            popInvOp.getParamsMap().put("rcptId", null);
            popInvOp.getParamsMap().put("UsrId", paramUsrId);
            popInvOp.execute();
            if (popInvOp.getResult() != null) {
                if (Integer.parseInt(popInvOp.getResult().toString()) == 1) {
                    _log.info("Invoice Populated By Invoice");
                    OperationBinding exItmOp = getBindings().getOperationBinding("Execute");
                    exItmOp.execute();
                    OperationBinding exSrOp = getBindings().getOperationBinding("Execute1");
                    exSrOp.execute();
                    OperationBinding exdltOp = getBindings().getOperationBinding("Execute3");
                    exdltOp.execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);

                } else if (Integer.parseInt(popInvOp.getResult().toString()) == -2) {
                    //Please Select the Document.
                    showFacesMessage(resolvEl("#{bundle['MSG.1161']}"), "I", false, "F");
                }
            } else {
                _log.info("Invoice Population error occurred.");
            }
        }

        /**
         * Populate invoice data from cash purchase order
         * */
        //When User Select Cash Purchase
        if (Integer.valueOf(invcTypeBindVar.getValue().toString()) == 457) {
            /**commented because cpo transfer in to purchase order*/

            /*
            OperationBinding popInvOp = getBindings().getOperationBinding("populateInvoiceByCashPurchase");
            popInvOp.getParamsMap().put("CldId", paramCldId);
            popInvOp.getParamsMap().put("SlocId", paramSlocId);
            popInvOp.getParamsMap().put("OrgId", paramOrgId);
            popInvOp.getParamsMap().put("rcptId", null);
            popInvOp.getParamsMap().put("UsrId", paramUsrId);
            popInvOp.execute(); */
            OperationBinding popInvOp = getBindings().getOperationBinding("populateInvoice");
            popInvOp.getParamsMap().put("CldId", paramCldId);
            popInvOp.getParamsMap().put("SlocId", paramSlocId);
            popInvOp.getParamsMap().put("OrgId", paramOrgId);
            popInvOp.getParamsMap().put("rcptId", null);
            popInvOp.getParamsMap().put("UsrId", paramUsrId);
            popInvOp.execute();
            if (popInvOp.getResult() != null) {
                if (Integer.parseInt(popInvOp.getResult().toString()) == 1) {
                    _log.info("Invoice Populated By Invoice");
                    OperationBinding exItmOp = getBindings().getOperationBinding("Execute");
                    exItmOp.execute();
                    OperationBinding exSrOp = getBindings().getOperationBinding("Execute1");
                    exSrOp.execute();
                    OperationBinding exdltOp = getBindings().getOperationBinding("Execute3");
                    exdltOp.execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);

                } else if (Integer.parseInt(popInvOp.getResult().toString()) == -2) {
                    //Please Select the Document.
                    showFacesMessage(resolvEl("#{bundle['MSG.1161']}"), "I", false, "F");
                } else if (Integer.parseInt(popInvOp.getResult().toString()) == -3) {
                    //Please Select the Document.
                    //Receipt has not same Currency or COA.
                    showFacesMessage(resolvEl("#{bundle['MSG.2129']}"), "I", false, "F");
                }
            } else {
                _log.info("Invoice Population error occurred.");
            }
        }

        /**
         * Populate invoice data for service item from purchase order
         */
        if (Integer.valueOf(invcTypeBindVar.getValue().toString()) == 737) {

            OperationBinding geteo = getBindings().getOperationBinding("getCoaForEo");
            geteo.getParamsMap().put("hoOrgId", paramHoOrgId);
            geteo.getParamsMap().put("eoName", invcSuppBindVar1.getValue().toString());
            geteo.execute();
            // System.out.println("OUT : "+geteo.getResult());
            if (geteo.getResult() != null) {
                Integer coaId = (Integer) geteo.getResult();
                if (coaId < 0) {
                    String msg = resolvEl("#{bundle['LBL.3107']}").toString();
                    /**
                               * Cannot select this supplier.Error in COA (Multiple).
                               * */
                    showFacesMessage(msg, "E", false, "F");
                    return;
                } else {
                    OperationBinding setCoa = getBindings().getOperationBinding("setcoaIdForInvc");
                    setCoa.getParamsMap().put("coa", coaId);
                    setCoa.execute();
                }
            } else {
                return;
            }
            //POPULATE PO FOR SERVICE ITEMS
            OperationBinding popInvOp = getBindings().getOperationBinding("populateSrvcInvoice");
            popInvOp.execute();

        }

        /**
         * Populate invoice data from purchase order for consumable items
         * */
        if (Integer.valueOf(invcTypeBindVar.getValue().toString()) == 739) {

            OperationBinding geteo = getBindings().getOperationBinding("getCoaForEo");
            geteo.getParamsMap().put("hoOrgId", paramHoOrgId);
            geteo.getParamsMap().put("eoName", invcSuppBindVar1.getValue().toString());
            geteo.execute();
            // System.out.println("OUT : "+geteo.getResult());
            if (geteo.getResult() != null) {
                Integer coaId = (Integer) geteo.getResult();
                if (coaId < 0) {
                    String msg = resolvEl("#{bundle['LBL.3107']}").toString();
                    /**
                               * Cannot select this supplier.Error in COA (Multiple).
                               * */
                    showFacesMessage(msg, "E", false, "F");
                    return;
                } else {
                    OperationBinding setCoa = getBindings().getOperationBinding("setcoaIdForInvc");
                    setCoa.getParamsMap().put("coa", coaId);
                    setCoa.execute();
                }
            } else {
                return;
            }
            //POPULATE PO FOR SERVICE ITEMS
            OperationBinding popInvOp = getBindings().getOperationBinding("populateConsumabeInvoice");
            popInvOp.execute();

        }
        OperationBinding opr = getBindings().getOperationBinding("getTotalAmt");
        opr.execute();
        Object obj = opr.getResult();

        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
    }


    public String saveInvAction() {

        /*    Integer paramUsrId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId= resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
      */
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

        String WfNo = "0";
        String UsrId = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
        Integer level = 0;
        String action = "I";
        String remark = "A";
        Integer res = -1;

        Boolean isSaved = Boolean.TRUE;
        if (invcTypeBindVar.getValue().toString().equals("455") ||
            invcTypeBindVar.getValue().toString().equals("457") ||
            invcTypeBindVar.getValue().toString().equals("492") ||
            invcTypeBindVar.getValue().toString().equals("737") ||
            invcTypeBindVar.getValue().toString().equals("739")) //Check In case of Purchase Invoice
        {
            OperationBinding chkOp = getBindings().getOperationBinding("checkAmount");
            chkOp.execute();
            _log.info("in the check amt error is " + chkOp.getErrors());
            isSaved = (Boolean) chkOp.getResult();
            if (!isSaved) {
                String msg2 = resolvEl("#{bundle['LBL.3108']}");
                // String msg2 = "Amount Mismatch.(Total Payment Schedule Amount Should be Equal to Total Invoice Amount)";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            }
        }

        //get Wf Id
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", paramSlocId);
        WfnoOp.getParamsMap().put("CldId", paramCldId);
        WfnoOp.getParamsMap().put("OrgId", paramOrgId);
        WfnoOp.getParamsMap().put("DocNo", 18521);
        WfnoOp.execute();
        if (WfnoOp.getResult() != null) {
            WfNo = WfnoOp.getResult().toString();
        }

        //get user level
        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
        usrLevelOp.getParamsMap().put("SlocId", paramSlocId);
        usrLevelOp.getParamsMap().put("CldId", paramCldId);
        usrLevelOp.getParamsMap().put("OrgId", paramOrgId);
        usrLevelOp.getParamsMap().put("UsrId", UsrId);
        usrLevelOp.getParamsMap().put("WfNo", WfNo);
        usrLevelOp.getParamsMap().put("DocNo", 18521);
        usrLevelOp.execute();
        if (usrLevelOp.getResult() != null) {
            level = Integer.parseInt(usrLevelOp.getResult().toString());
        }

        //---------------------------------------------------------------------------CHANGE HERE(IF CONDITION ADDED)--------------------------------------------------------------
        String currUser = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
        OperationBinding opsaved = getBindings().getOperationBinding("CheckSaved");
        opsaved.execute();
        String checksav = opsaved.getResult().toString();

        if (checksav.equals("-1")) {
            //insert into txn
            OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
            insTxnOp.getParamsMap().put("SlocId", paramSlocId);
            insTxnOp.getParamsMap().put("CldId", paramCldId);
            insTxnOp.getParamsMap().put("OrgId", paramOrgId);
            insTxnOp.getParamsMap().put("DocNo", 18521);
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

        }

        //------------------------------------------------------------------------------WF END-----------------------------------------------------------------
        OperationBinding doOpr = getBindings().getOperationBinding("doBeforeCommit");
        doOpr.getParamsMap().put("SlocId", paramSlocId);
        doOpr.getParamsMap().put("CldId", paramCldId);
        doOpr.getParamsMap().put("OrgId", paramOrgId);
        doOpr.execute();


        OperationBinding genInvOp = getBindings().getOperationBinding("getInvNo");
        genInvOp.getParamsMap().put("SlocId", paramSlocId);
        genInvOp.getParamsMap().put("CldId", paramCldId);
        genInvOp.getParamsMap().put("OrgId", paramOrgId);
        genInvOp.execute();
        Object obj = genInvOp.getResult();

        /*    Boolean isSaved=Boolean.TRUE;
        if(invcTypeBindVar.getValue().toString().equals("455"))  //Check In case of Purchase Invoice
        {
            OperationBinding chkOp = getBindings().getOperationBinding("checkAmount");
            chkOp.execute();
            isSaved=(Boolean)chkOp.getResult();
        }
    */
        if (isSaved) {
            OperationBinding saveOp = getBindings().getOperationBinding("Commit");
            saveOp.execute();
            bindInvoiceTabVar.setDisclosed(true);
            bindTransDocVar.setValue("");
            RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "V");
            String msg1 = resolvEl("#{bundle['LBL.3001']}");
            String msg2 = resolvEl("#{bundle['LBL.3105']}");
            //String msg2 = "Purchase Invoice "+ obj +" Saved Successfully.";
            FacesMessage message2 = new FacesMessage(msg1 + " " + obj + " " + msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {
            String msg2 = resolvEl("#{bundle['LBL.3108']}");
            // String msg2 = "Amount Mismatch.(Total Payment Schedule Amount Should be Equal to Total Invoice Amount)";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }
        return null;
    }

    public void poTaxAction(ActionEvent actionEvent) {

        /*   Integer type = Integer.parseInt(invcTypeBindVar.getValue().toString());
        if(type == 456)
        {
            OperationBinding chkTaxOp = getBindings().getOperationBinding("checkTaxRuleApplied");
            chkTaxOp.execute();
            Boolean b =(Boolean)chkTaxOp.getResult();
            if(!b)
            {
                    FacesMessage message2 = new FacesMessage("For Same Organisation,Tax Can not be apply.");
                    message2.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                    return;
            }
        } */

        showPopup(poTaxPopUp, true);
    }

    public void itmTaxAction(ActionEvent actionEvent) {

        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer type = Integer.parseInt(invcTypeBindVar.getValue().toString());

        OperationBinding istaxApply = getBindings().getOperationBinding("isTaxApplicable");
        istaxApply.execute();
        String exmtdflag = null;
        if (istaxApply.getErrors().isEmpty()) {
            exmtdflag = istaxApply.getResult().toString();
        }
        if ("Y".equalsIgnoreCase(exmtdflag)) {
            //Tax is not Applicable on this item
            FacesMessage message2 = new FacesMessage(resolvEl("#{bundle['MSG.1689']}").toString());
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {
            OperationBinding chkTaxOp = getBindings().getOperationBinding("checkTaxPresent");
            chkTaxOp.getParamsMap().put("SlocId", paramSlocId);
            chkTaxOp.getParamsMap().put("CldId", paramCldId);
            chkTaxOp.getParamsMap().put("OrgId", paramOrgId);
            chkTaxOp.execute();
            previousValue = 0;
            if ("Y".equalsIgnoreCase(chkTaxOp.getResult().toString())) {

                OperationBinding opr = getBindings().getOperationBinding("getTrRuleId");
                opr.execute();
                Integer reply = (Integer) opr.getResult();

                if (reply != null && reply > 0) {
                    previousValue = reply;
                }

                showPopup(itmTaxPopUp, true);

            } else {

                OperationBinding crTaxOp = getBindings().getOperationBinding("Createwithparameters");
                crTaxOp.getParamsMap().put("TaxableAmtSp", (Number) taxableItmAmtSpBind.getValue());
                crTaxOp.execute();

                showPopup(itmTaxPopUp, true);
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
    }

    private Object previousValue = null;

    public void itmTaxRuleVCE(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            OperationBinding appTaxOp = getBindings().getOperationBinding("applyTaxRule");
            appTaxOp.getParamsMap().put("cldId", paramCldId);
            appTaxOp.getParamsMap().put("slocId", paramSlocId);
            appTaxOp.getParamsMap().put("orgId", paramOrgId);
            appTaxOp.getParamsMap().put("ruleId", (Integer) valueChangeEvent.getNewValue());
            //appTaxOp.getParamsMap().put("ruleId",(Integer)valueChangeEvent.getNewValue());
            appTaxOp.getParamsMap().put("type", "I");
            appTaxOp.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(taxableAmtBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTableBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);

        }
    }

    public void tdsRuleVCE(ValueChangeEvent valueChangeEvent) {
        _log.info("currnet tds amount is " + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

            /*  if(valueChangeEvent.getNewValue()!=valueChangeEvent.getOldValue()){

            } */

            OperationBinding appTaxOp = getBindings().getOperationBinding("applyTdsRule");
            appTaxOp.getParamsMap().put("cldId", paramCldId);
            appTaxOp.getParamsMap().put("slocId", paramSlocId);
            appTaxOp.getParamsMap().put("orgId", paramOrgId);
            appTaxOp.getParamsMap().put("ruleId", (Integer) valueChangeEvent.getNewValue());
            //appTaxOp.getParamsMap().put("ruleId",(Integer)valueChangeEvent.getNewValue());
            appTaxOp.getParamsMap().put("type", "I");
            appTaxOp.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(tdsAmountBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tdsTableBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
        }
    }

    public void applyPoTaxAction(ActionEvent actionEvent) {
        //call function to populate tax for all the rows if tax selected.
        //   _log.info("----------potax rule " + poTaxRule.getValue());
        if (poTaxRule.getValue() != null && !poTaxRule.getValue().equals("")) {
            _log.info("----------potax rule-" + poTaxRule.getValue() + "-");
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

            Integer poTax = (Integer.parseInt(poTaxRule.getValue().toString()));

            _log.info("Rule Id=" + poTax);
            OperationBinding appTaxOp = getBindings().getOperationBinding("applyTaxRule");
            appTaxOp.getParamsMap().put("cldId", paramCldId);
            appTaxOp.getParamsMap().put("slocId", paramSlocId);
            appTaxOp.getParamsMap().put("orgId", paramOrgId);
            appTaxOp.getParamsMap().put("ruleId", poTax);
            appTaxOp.getParamsMap().put("taxableAmt", new Number(0));
            appTaxOp.getParamsMap().put("type", "ALL");
            appTaxOp.execute();
            _log.info("-------------after applyTaxRule execute???????????????????/");
            OperationBinding exeOp = getBindings().getOperationBinding("Execute");
            exeOp.execute();
            if (exeOp.getErrors().isEmpty()) {
                poTaxPopUp.hide();
                AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
            }
        } else {
            showFacesMessage("Please Select Tax Rule", "E", false, "F");
        }
    }


    public void itemTaxDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
            OperationBinding exeOp = getBindings().getOperationBinding("Execute");
            exeOp.execute();
            OperationBinding exeOp1 = getBindings().getOperationBinding("Execute1");
            exeOp1.execute();
        }
    }

    public void poTaxDialogListener(DialogEvent dialogEvent) {

        if (poTaxRule.getValue() != null) {
            /* String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer paramSlocId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            Integer poTax=(Integer.parseInt(poTaxRule.getValue().toString()));
            _log.info("Rule Id="+poTax);
            OperationBinding appTaxOp = getBindings().getOperationBinding("applyTaxRule");
            appTaxOp.getParamsMap().put("cldId",paramCldId);
            appTaxOp.getParamsMap().put("slocId",paramSlocId);
            appTaxOp.getParamsMap().put("orgId",paramOrgId);
            appTaxOp.getParamsMap().put("ruleId",poTax);
            appTaxOp.getParamsMap().put("taxableAmt",new Number(0));
            appTaxOp.getParamsMap().put("type","ALL");
            appTaxOp.execute();
            OperationBinding exeOp = getBindings().getOperationBinding("Execute");
            exeOp.execute(); */



        }
    }


    public void addPoOcAction(ActionEvent actionEvent) {

        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
        operationBinding.execute();
        /*   if (operationBinding.getErrors().size() == 0) {
            ArrayList lst = new ArrayList(1);

            OperationBinding opB = getBindings().getOperationBinding("ocCurrentRowKey");
            opB.execute();

            lst.add(opB.getResult().toString());
            getOcTableBind().setActiveRowKey(lst);
        }  */

        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
    }


    public void poOcDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
            OperationBinding operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute5");
            operationBinding1.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
        }
    }

    public void taxExemptedVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if ("true".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
                OperationBinding appTaxOp = getBindings().getOperationBinding("setTrExempted");
                appTaxOp.getParamsMap().put("Flg", "Y");
                appTaxOp.execute();
            } else {
                OperationBinding appTaxOp = getBindings().getOperationBinding("setTrExempted");
                appTaxOp.getParamsMap().put("Flg", "N");
                appTaxOp.execute();
            }
        }
    }

    public void itmTdsAction(ActionEvent actionEvent) {
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        /*
        Integer type = Integer.parseInt(invcTypeBindVar.getValue().toString());
          if(type == 456)
                {
                    OperationBinding chkTaxOp = getBindings().getOperationBinding("checkTaxRuleApplied");
                    chkTaxOp.execute();
                    Boolean b =(Boolean)chkTaxOp.getResult();
                    if(!b)
                    {
                            FacesMessage message2 = new FacesMessage("For Same Organisation,Tds can not be apply.");
                            message2.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message2);
                            return;
                    }
                }
         */
        //change taxabe amt-->tdsable amt
        OperationBinding chkTdsOp = getBindings().getOperationBinding("checkTdsPresent");
        chkTdsOp.getParamsMap().put("SlocId", paramSlocId);
        chkTdsOp.getParamsMap().put("CldId", paramCldId);
        chkTdsOp.getParamsMap().put("OrgId", paramOrgId);
        chkTdsOp.execute();
        previousValue = 0;
        if ("Y".equalsIgnoreCase(chkTdsOp.getResult().toString())) {
            OperationBinding opr = getBindings().getOperationBinding("getTdsRuleId");
            opr.execute();
            Integer reply = (Integer) opr.getResult();
            _log.info("current tds rule id is " + reply);
            if (reply != null && reply > 0) {
                previousValue = reply;
            }

            showPopup(itmTdsPopUp, true);

        } else {
            _log.info("create with param 2performs  " + tdsOnAmtItm.getValue());
            if (tdsOnAmtItm.getValue() != null && tdsOnAmtItm.getValue().toString().length() > 0) {
                OperationBinding crTdsOp = getBindings().getOperationBinding("Createwithparameters2");
                crTdsOp.getParamsMap().put("TdsOnAmtSp", (Number) tdsOnAmtItm.getValue());
                crTdsOp.execute();
            }
            //TransTdsOnAmt
            showPopup(itmTdsPopUp, true);
        }

    }


    public void populateInvcCalc(ActionEvent actionEvent) {

        // System.out.println("populated Invoice Calculation..");

        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Object obj = invcTypeBindVar.getValue();

        OperationBinding popCalc = getBindings().getOperationBinding("populateCalculations");
        popCalc.getParamsMap().put("p_cld_id", paramCldId);
        popCalc.getParamsMap().put("p_sloc_id", paramSlocId);
        popCalc.getParamsMap().put("p_org_id", paramOrgId);
        popCalc.getParamsMap().put("p_usr_id", paramUsrId);
        popCalc.execute();
        String flag = (String) popCalc.getResult();
        //OperationBinding exeCalc = getBindings().getOperationBinding("Execute2");
        //exeCalc.execute();

    }

    public void supplierVCE(ValueChangeEvent valueChangeEvent) {
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");

        if (valueChangeEvent.getNewValue() != null) {
            paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            OperationBinding geteo = getBindings().getOperationBinding("getCoaForEo");
            geteo.getParamsMap().put("hoOrgId", paramHoOrgId);
            geteo.getParamsMap().put("eoName", valueChangeEvent.getNewValue().toString());
            geteo.execute();


            Object coa = geteo.getResult();
            if (coa != null) {
                Integer coaid = Integer.parseInt(coa.toString());
                if (coaid > 0) {
                    _log.info("Set COA" + coaid);
                    OperationBinding setCoa = getBindings().getOperationBinding("setcoaIdForInvc");
                    setCoa.getParamsMap().put("coa", coaid);
                    setCoa.execute();
                }
            }
        }
        //getBindings().getOperationBinding("setDefaultAddress").execute();
        OperationBinding clrOpr = getBindings().getOperationBinding("cleanUpOnInvcCreate");
        clrOpr.execute();
        getBindings().getOperationBinding("Execute4").execute();
        bindOperationVar.setValue("Amt");
        bindAmtAdjtVar.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(currencyConFctBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindOperationVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindAmtAdjtVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindVarContinueBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindVarApplyBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(selectedTblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(piTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindVarGoBackBtn);
    }

    public void supplierValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        _log.info("supplier object value is " + object);
        if (object != null && object.toString().length() > 0) {
            String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            _log.info("supplier object value length is  " + object.toString().length());

            OperationBinding geteo = getBindings().getOperationBinding("getCoaForEo");
            geteo.getParamsMap().put("hoOrgId", paramHoOrgId);
            geteo.getParamsMap().put("eoName", object.toString());
            geteo.execute();

            if (geteo.getResult() != null) {
                Integer coaid = Integer.parseInt(geteo.getResult().toString());

                // System.out.println("Coa : "+coaid);

                StringBuilder sb = new StringBuilder(resolvEl("#{bundle['LBL.3106']}"));
                if (coaid < 0) {
                    if (coaid.equals(-2)) {
                        //showFacesMessage(sb.toString(), "E", false, "F");
                        //coa is not defined for this supplier in this organisation
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['MSG.2130']}").toString(),
                                                                      null));

                    } else if (coaid.equals(-3)) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['LBL.3107']}").toString(),
                                                                      null));

                        /* String msg=resolvEl("#{bundle['LBL.3107']}").toString();
                    //Cannot select this supplier.Error in COA (Multiple).
                    showFacesMessage(msg, "E", false, "F"); */
                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['LBL.3107']}").toString(),
                                                                      null));

                    }
                }
            }
        }
    }

    public void addPaymentSchdlAction(ActionEvent actionEvent) {

        if (paymentDateVar.getValue() == null) {

            // String msg2 = "Please specify Payment Date.";
            String msg2 = resolvEl("#{bundle['MSG.423']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);

        } else if (paymentAmtVar.getValue() == null) {

            // String msg2 = "Please specify Payment amount.";
            String msg2 = resolvEl("#{bundle['MSG.422']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        } else {
            if (rcptTotalVar.getValue() != null && paymentDateVar.getValue() != null) {

                Timestamp date = (Timestamp) paymentDateVar.getValue();
                Number num = (Number) paymentAmtVar.getValue();
                if (num.compareTo(0) <= 0) {
                    return;
                }

                OperationBinding ob = getBindings().getOperationBinding("paymentDateValidation");
                ob.getParamsMap().put("paydt", date);
                Object ret = ob.execute();
                if (ret != null) {
                    String flg = (String) ret;

                    if ("Y".equalsIgnoreCase(flg)) {

                        ob = getBindings().getOperationBinding("chkValidation");
                        ob.getParamsMap().put("amt", paymentAmtVar.getValue());
                        Boolean isValid = (Boolean) ob.execute();

                        if (!isValid) {
                            String msg2 = resolvEl("#{bundle['MSG.981']}").toString();
                            FacesMessage message2 = new FacesMessage(msg2);
                            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, message2);
                            return;
                        }

                        showPopup(duplicateDatePopUpVar, true);

                    } else {
                        Number totalPayAmt = (Number) rcptTotalVar.getValue();
                        OperationBinding donePaySchdlAction = getBindings().getOperationBinding("donePaySchdlAction");
                        donePaySchdlAction.getParamsMap().put("totAmt",
                                                              totalPayAmt); //.setScale(amt_digit, RoundingMode.HALF_UP));
                        Object obj = paymentModeVar.getValue();
                        Integer paymode = 0;
                        if (obj != null && String.valueOf(obj).length() > 0) {
                            if (((Integer) paymentModeVar.getValue()) > 0) {
                                paymode = (Integer) paymentModeVar.getValue();
                            }
                        }

                        donePaySchdlAction.getParamsMap().put("paymentMode", paymode);
                        donePaySchdlAction.execute();

                        OperationBinding calc = getBindings().getOperationBinding("getTotalPaymentSchdAmount");
                        calc.execute();
                        totalPayAmtBindVar.setValue(calc.getResult());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(totalPayAmtBindVar);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentSchdTblVar);
                    }
                }

            }
        }

        if (invcTypeBindVar.getValue().toString().equals("455") ||
            invcTypeBindVar.getValue().toString().equals("492")) //Check In case of Purchase Invoice
        {
            OperationBinding opr = getBindings().getOperationBinding("getPaySchdRowCount");
            opr.execute();
            Object obj = opr.getResult();
            if (obj != null) {
                Integer count = (Integer) obj;
                if (count <= 0) {
                    saveForwardBtn.setDisabled(true);
                } else {
                    saveForwardBtn.setDisabled(false);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveForwardBtn);
        }
    }

    public void populateInvcAdjt(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding addInvcAdj = getBindings().getOperationBinding("insertAdjustmentLines");
        addInvcAdj.execute();
    }

    public void postChangeAL(ActionEvent actionEvent) {

        OperationBinding popCalc = getBindings().getOperationBinding("postChng");
        popCalc.execute();
        // Add event code here...
    }

    public void setPaymentDateVar(RichInputDate paymentDateVar) {
        this.paymentDateVar = paymentDateVar;
    }

    public RichInputDate getPaymentDateVar() {
        return paymentDateVar;
    }

    public void setPaymentModeVar(RichSelectOneChoice paymentModeVar) {
        this.paymentModeVar = paymentModeVar;
    }

    public RichSelectOneChoice getPaymentModeVar() {
        return paymentModeVar;
    }

    public void setPaymentAmtVar(RichInputText paymentAmtVar) {
        this.paymentAmtVar = paymentAmtVar;
    }

    public RichInputText getPaymentAmtVar() {
        return paymentAmtVar;
    }

    public void setPaymentAdvFlgVar(RichSelectBooleanCheckbox paymentAdvFlgVar) {
        this.paymentAdvFlgVar = paymentAdvFlgVar;
    }

    public RichSelectBooleanCheckbox getPaymentAdvFlgVar() {
        return paymentAdvFlgVar;
    }

    public void setPaymentSchdTblVar(RichTable paymentSchdTblVar) {
        this.paymentSchdTblVar = paymentSchdTblVar;
    }

    public RichTable getPaymentSchdTblVar() {
        return paymentSchdTblVar;
    }

    public Object resolvElAmt(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object digit = 2;
        if (valueExp != null) {
            digit = valueExp.getValue(elContext);
        }
        if (digit == null) {
            digit = 2;
        }
        return digit;
    }

    public void deletePaymentScheduleAL(ActionEvent actionEvent) {
        // Add event code here...
        showPopup(removePopUpPaymentSchedule, Boolean.TRUE);
    }

    public void PaymentSchdDisLstner(DisclosureEvent disclosureEvent) {
        // Add event code here...
        if (disclosureEvent.isExpanded()) {
            OperationBinding InvCalc = getBindings().getOperationBinding("setPaymentAmount");
            InvCalc.execute();

            OperationBinding calc = getBindings().getOperationBinding("getTotalPaymentSchdAmount");
            calc.execute();
            totalPayAmtBindVar.setValue(calc.getResult());
            InvcCalculationDisclosureListener(disclosureEvent);
        }

    }


    //delete or reset Other Charges.
    public void deleteOCAL(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding operationBinding = getBindings().getOperationBinding("deleteOtherCharges");
        operationBinding.execute();
        /*     AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
        OperationBinding exec = getBindings().getOperationBinding("Execute1");
        exec.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
    */
    }

    public void adjustmentValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number number = (Number) object;

            if (number.compareTo(new Number(0)) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.553']}"), null));
            }

            if (!isPrecisionValid(26, 6, number)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.57']}"), null));
            }

            OperationBinding AdjtCalc = getBindings().getOperationBinding("isAdjustmentValidate");
            AdjtCalc.getParamsMap().put("num", number);
            AdjtCalc.execute();

            String retValue = (String) AdjtCalc.getResult();

            //Adjustment Amount Exceeds OutStanding Amount(SP);
            String msg = resolvEl("#{bundle['LBL.3110']}");
            String msg1 = resolvEl("#{bundle['MSG.1160']}");
            //Total Adjustemnt Amount Exceeds Total Amount to be Adjusted.
            if (retValue.equalsIgnoreCase("GL_VALID_FAILS")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }

            else if (retValue.equalsIgnoreCase("TOTAL_VALID_FAILS")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            }

            else if (retValue.equalsIgnoreCase("VALID_OK")) {
                ;
            }
        }

    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void AdjtAmtApVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        Number adjAmt = new Number(0);
        Object obj = valueChangeEvent.getNewValue();


        Object adjtAmt = bindAdjAmtVar.getValue();
        if (adjAmt != null) {
            adjAmt = (Number) adjtAmt;
        }

        OperationBinding countCalc = getBindings().getOperationBinding("getPaySchdRowCount");
        countCalc.execute();
        Object ob = countCalc.getResult();
        Integer count = 0;
        if (ob != null) {
            count = (Integer) ob;
        }

        if (count > 1) {
            String msg2 = resolvEl("#{bundle['MSG.1158']}");
            //String msg2 = "Changes in Adjustment will clear Payment Schedule.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message2);
            OperationBinding delOpr = getBindings().getOperationBinding("deletePaymentSchduleAll");
            delOpr.execute();
        }


        if (obj != null) {
            Number number = (Number) obj;
            OperationBinding AdjtCalc = getBindings().getOperationBinding("setAdjustmentBSAmount");
            AdjtCalc.getParamsMap().put("AdjtSpAmt", adjAmt);
            AdjtCalc.execute();
            totalAdjtAmtBindVar.setValue(AdjtCalc.getResult());

        } else {
            OperationBinding AdjtCalc = getBindings().getOperationBinding("setAdjustmentBSAmount");
            AdjtCalc.getParamsMap().put("AdjtSpAmt", new Number(0));
            AdjtCalc.execute();
            totalAdjtAmtBindVar.setValue(AdjtCalc.getResult());
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(totalAdjtAmtBindVar);

        if (invcTypeBindVar.getValue().toString().equals("455") ||
            invcTypeBindVar.getValue().toString().equals("492")) //Check In case of Purchase Invoice
        {
            OperationBinding opr = getBindings().getOperationBinding("getPaySchdRowCount");
            opr.execute();
            Object object = opr.getResult();
            if (object != null) {
                Integer cnt = (Integer) object;
                if (cnt <= 0) {
                    saveForwardBtn.setDisabled(true);
                } else {
                    saveForwardBtn.setDisabled(false);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveForwardBtn);
        }

    }

    public void setTotalAdjtAmtBindVar(RichOutputText totalAdjtAmtBindVar) {
        this.totalAdjtAmtBindVar = totalAdjtAmtBindVar;
    }

    public RichOutputText getTotalAdjtAmtBindVar() {
        return totalAdjtAmtBindVar;
    }

    public void setBindAdjAmtVar(RichInputText bindAdjAmtVar) {
        this.bindAdjAmtVar = bindAdjAmtVar;
    }

    public RichInputText getBindAdjAmtVar() {
        return bindAdjAmtVar;
    }

    public void setDuplicateDatePopUpVar(RichPopup duplicateDatePopUpVar) {
        this.duplicateDatePopUpVar = duplicateDatePopUpVar;
    }

    public RichPopup getDuplicateDatePopUpVar() {
        return duplicateDatePopUpVar;
    }

    public void addPaymentScheduleDL(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("yes")) {
            Timestamp date = (Timestamp) paymentDateVar.getValue();
            Number amt = (Number) paymentAmtVar.getValue();
            OperationBinding paymentOp = getBindings().getOperationBinding("appendPaymentAmtOnSameDt");
            paymentOp.getParamsMap().put("amt", amt);
            paymentOp.getParamsMap().put("date", date);
            paymentOp.execute();
            OperationBinding calc = getBindings().getOperationBinding("getTotalPaymentSchdAmount");
            calc.execute();
            totalPayAmtBindVar.setValue(calc.getResult());
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalPayAmtBindVar);

        }
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setTotalPayAmtBindVar(RichOutputText totalPayAmtBindVar) {
        this.totalPayAmtBindVar = totalPayAmtBindVar;
    }

    public RichOutputText getTotalPayAmtBindVar() {
        return totalPayAmtBindVar;
    }

    public void AdjustmentDetailLstnr(DisclosureEvent disclosureEvent) {
        // Add event code here...
        OperationBinding calc = getBindings().getOperationBinding("getTotalAdjustedAmount");
        calc.execute();
        totalAdjtAmtBindVar.setValue(calc.getResult());
        AdfFacesContext.getCurrentInstance().addPartialTarget(totalAdjtAmtBindVar);

        InvcCalculationDisclosureListener(disclosureEvent);
    }

    public void editActionListener(ActionEvent actionEvent) {
        // Add event code here...
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String currUser = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();

        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", SlocId);
        chkUsr.getParamsMap().put("CldId", CldId);
        chkUsr.getParamsMap().put("OrgId", OrgId);
        chkUsr.getParamsMap().put("DocNo", 18521);
        chkUsr.execute();
        Object pendUser = (Object) chkUsr.getResult();

        OperationBinding obind11 = getBindings().getOperationBinding("getUserName");
        obind11.getParamsMap().put("userId", Integer.parseInt(pendUser.toString()));
        obind11.execute();
        String UserName = null;
        if (obind11.getErrors().isEmpty()) {
            UserName = obind11.getResult().toString();
        }
        if (pendUser != null) {
            if (currUser.equals(pendUser.toString())) {
                RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "E");
            } else {
                String msg2 =
                    resolvEl("#{bundle['MSG.612']}").toString() + " " + resolvEl("#{bundle['MSG.1294']}").toString() +
                    " [" + UserName + "]";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
        }
    }

    public String wFAction() {
        // Add event code here...
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));


        Boolean isSaved = Boolean.TRUE;
        if (invcTypeBindVar.getValue().toString().equals("455") ||
            invcTypeBindVar.getValue().toString().equals("457") ||
            invcTypeBindVar.getValue().toString().equals("492") ||
            invcTypeBindVar.getValue().toString().equals("737") ||
            invcTypeBindVar.getValue().toString().equals("739")) //Check In case of Purchase Invoice
        {
            OperationBinding chkOp = getBindings().getOperationBinding("checkAmount");
            chkOp.execute();
            isSaved = (Boolean) chkOp.getResult();
            if (!isSaved) {
                String msg2 = resolvEl("#{bundle['LBL.3108']}");
                // String msg2 = "Amount Mismatch.(Total Payment Schedule Amount Should be Equal to Total Invoice Amount)";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            }
        }

        String WfNo = null;
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", paramSlocId);
        WfnoOp.getParamsMap().put("CldId", paramCldId);
        WfnoOp.getParamsMap().put("OrgId", paramOrgId);
        WfnoOp.getParamsMap().put("DocNo", 18521);
        WfnoOp.execute();
        if (WfnoOp.getResult() != null) {
            WfNo = WfnoOp.getResult().toString();
        }
        setWfId(WfNo);
        RequestContext.getCurrentInstance().getPageFlowScope().put("WF_ID", WfNo);

        OperationBinding opr = getBindings().getOperationBinding("isInvcAuth");
        opr.execute();
        Object obj = opr.getResult();
        if (obj != null) {
            Boolean b = (Boolean) obj;
            if (b) {
                String msg2 = resolvEl("#{bundle['LBL.3109']}").toString();
                //String msg2 ="Invoice Already Approved.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            }
        }

        String rply = saveAndForwardAction();
        if (rply.equalsIgnoreCase("PENDING")) {
            String msg2 = resolvEl("#{bundle['MSG.1054']}").toString();
            //String msg2 = "This Slip is pending at other user.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }

        else if (rply.equalsIgnoreCase("SUCCESS")) {
            OperationBinding attchChkOp = getBindings().getOperationBinding("chkAttchRqd");
            attchChkOp.execute();
            if (attchChkOp.getResult() != null && attchChkOp.getResult().toString().equalsIgnoreCase("N")) {
                _log.info("inside DObEFORE COMMIT N true---------------");
                OperationBinding doOpr = getBindings().getOperationBinding("doBeforeCommit");
                doOpr.getParamsMap().put("SlocId", paramSlocId);
                doOpr.getParamsMap().put("CldId", paramCldId);
                doOpr.getParamsMap().put("OrgId", paramOrgId);
                doOpr.execute();
            }
            OperationBinding genInvOp = getBindings().getOperationBinding("getInvNo");
            genInvOp.getParamsMap().put("SlocId", paramSlocId);
            genInvOp.getParamsMap().put("CldId", paramCldId);
            genInvOp.getParamsMap().put("OrgId", paramOrgId);
            genInvOp.execute();

            OperationBinding saveOp = getBindings().getOperationBinding("Commit");
            saveOp.execute();
            if (attchChkOp.getResult() != null && attchChkOp.getResult().toString().equalsIgnoreCase("Y")) {
                //Please upload file from File Uploading tab, then you can proceed.
                String msg = resolvEl("#{bundle['MSG.2128']}").toString();
                showFacesMessage(msg, "I", false, "F");
                RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "V");
                _log.info("inside req attch true---------------");
                return null;
            } else {

                return "goToWF";
            }
        }

        else if (rply.equalsIgnoreCase("ALREADY_SAVED")) {
            OperationBinding attchChkOp = getBindings().getOperationBinding("chkAttchRqd");
            attchChkOp.execute();
            if (attchChkOp.getResult() != null && attchChkOp.getResult().toString().equalsIgnoreCase("Y")) {
                //Please upload file from File Uploading tab, then you can proceed.
                String msg = resolvEl("#{bundle['MSG.2128']}").toString();
                showFacesMessage(msg, "I", false, "F");
                RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "V");
                _log.info("inside req attch true---------------already save...");
                return null;
            } else {
                return "goToWF";
            }
            //return "goToWF";
        }

        else {
            // String msg2 = "There is Some Problem in WF Please Contact to ESS INDIA.";

            String msg2 = resolvEl("#{bundle['MSG.1159']}");
            ;
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
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String WfNo = "0";
        String UsrId = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
        Integer level = 0;
        String action = "I";
        String remark = "A";
        Integer res = -1;
        String currUser = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
        OperationBinding opsaved = getBindings().getOperationBinding("CheckSaved");
        opsaved.execute();
        String checksav = opsaved.getResult().toString();
        if (checksav.equals("1")) {

            OperationBinding opr = getBindings().getOperationBinding("pendingCheck");
            opr.getParamsMap().put("SlocId", paramSlocId);
            opr.getParamsMap().put("CldId", paramCldId);
            opr.getParamsMap().put("OrgId", paramOrgId);
            opr.getParamsMap().put("DocNo", 18521);
            opr.execute();

            Object pendUser = opr.getResult();
            if (pendUser != null) {
                if (!currUser.equals(pendUser.toString())) {
                    //return "ALREADY_SAVED";
                    return "PENDING";
                }
            }
            // return "PENDING";
            return "ALREADY_SAVED";
        }


        //get Wf Id
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", paramSlocId);
        WfnoOp.getParamsMap().put("CldId", paramCldId);
        WfnoOp.getParamsMap().put("OrgId", paramOrgId);
        WfnoOp.getParamsMap().put("DocNo", 18521);
        WfnoOp.execute();
        if (WfnoOp.getResult() != null) {
            WfNo = WfnoOp.getResult().toString();
        }

        if (WfNo != null && !"0".equalsIgnoreCase(WfnoOp.getResult().toString())) //get user level
        {
            OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
            usrLevelOp.getParamsMap().put("SlocId", paramSlocId);
            usrLevelOp.getParamsMap().put("CldId", paramCldId);
            usrLevelOp.getParamsMap().put("OrgId", paramOrgId);
            usrLevelOp.getParamsMap().put("UsrId", UsrId);
            usrLevelOp.getParamsMap().put("WfNo", WfNo);
            usrLevelOp.getParamsMap().put("DocNo", 18521);
            usrLevelOp.execute();
            if (usrLevelOp.getResult() != null) {
                level = Integer.parseInt(usrLevelOp.getResult().toString());
            }

            if (level > -1) {
                //insert into wf$txn
                OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                insTxnOp.getParamsMap().put("SlocId", paramSlocId);
                insTxnOp.getParamsMap().put("CldId", paramCldId);
                insTxnOp.getParamsMap().put("OrgId", paramOrgId);
                insTxnOp.getParamsMap().put("DocNo", 18521);
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
                return "SUCCESS";
            } else {
                //Something went wrong (User level in Workflow).Please Contact to ESS!
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1875']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            /*   FacesMessage message2 = new FacesMessage("Workflow not Defined for current Organisation.");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2); */
            //Workflow not Defined for this Document.
            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1486']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);


        }
        return null;

    }

    public String createNewInvc() {
        // Add event code here...
        RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "A");
        OperationBinding Op = getBindings().getOperationBinding("cleanUpOnInvcCreate");
        Op.execute();
        getBindings().getOperationBinding("refreshAllLovs").execute();
        showcompInvc = true;
        showSuppInvc = false;
        showSuppTrfInvc = false;
        bindTransDocVar.processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindTransDocVar);

        return "CreateInvc";
    }

    public void setInvcTypeBindVar(RichSelectOneChoice invcTypeBindVar) {
        this.invcTypeBindVar = invcTypeBindVar;
    }

    public RichSelectOneChoice getInvcTypeBindVar() {
        return invcTypeBindVar;
    }

    public void setInvcSuppBindVar(RichInputComboboxListOfValues invcSuppBindVar) {
        this.invcSuppBindVar = invcSuppBindVar;
    }

    public RichInputComboboxListOfValues getInvcSuppBindVar() {
        return invcSuppBindVar;
    }

    public void resetTaxAction(ActionEvent actionEvent) {
        // Add event code here...
        showPopup(removePOTaxBindVar, Boolean.TRUE);
    }

    public void setRemoveTrItmPopUp(RichPopup removeTrItmPopUp) {
        this.removeTrItmPopUp = removeTrItmPopUp;
    }

    public RichPopup getRemoveTrItmPopUp() {
        return removeTrItmPopUp;
    }

    public void removeTrFromItmAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding Op = getBindings().getOperationBinding("deleteTrFromItm");
            Op.execute();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
            OperationBinding operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
        }

    }

    public void callPopRemovePopUp(ActionEvent actionEvent) {
        // Add event code here...
        showPopup(removeTrItmPopUp, Boolean.TRUE);

    }

    public void removeTdsFromItm(ActionEvent actionEvent) {
        // Add event code here...
        //AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
        showPopup(removePopUpTds, Boolean.TRUE);
    }

    public void removeTdsFromItmAction(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding Op = getBindings().getOperationBinding("deleteTdsFromItm");
            Op.execute();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
            OperationBinding operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
        }
    }

    public void setRemoveTdsPopUp(RichPanelFormLayout removeTdsPopUp) {
        this.removeTdsPopUp = removeTdsPopUp;
    }

    public RichPanelFormLayout getRemoveTdsPopUp() {
        return removeTdsPopUp;
    }

    public void setRemovePopUpTds(RichPopup removePopUpTds) {
        this.removePopUpTds = removePopUpTds;
    }

    public RichPopup getRemovePopUpTds() {
        return removePopUpTds;
    }

    public void setPreviousValue(Object previousValue) {
        this.previousValue = previousValue;
    }

    public Object getPreviousValue() {
        return previousValue;
    }

    public void trCanceledListenerAction(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        OperationBinding opr = getBindings().getOperationBinding("getTrRuleId");
        opr.execute();
        Integer reply = (Integer) opr.getResult();

        if (previousValue != null && ((Integer) previousValue) != reply &&
            Integer.parseInt(previousValue.toString()) > 0) {
            //System.out.println("Cancellation   :  "+previousValue);
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

            OperationBinding appTaxOp = getBindings().getOperationBinding("applyTaxRule");
            appTaxOp.getParamsMap().put("cldId", paramCldId);
            appTaxOp.getParamsMap().put("slocId", paramSlocId);
            appTaxOp.getParamsMap().put("orgId", paramOrgId);
            appTaxOp.getParamsMap().put("ruleId", previousValue);
            appTaxOp.getParamsMap().put("type", "I");
            appTaxOp.execute();

            OperationBinding exeOp = getBindings().getOperationBinding("Execute");
            exeOp.execute();
        }

        else if (previousValue != null && ((Integer) previousValue) == 0) {
            OperationBinding Op = getBindings().getOperationBinding("deleteTrFromItm");
            Op.execute();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
            OperationBinding operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);

        } else {
            ;
        }
    }

    public void InvcCalculationDisclosureListener(DisclosureEvent disclosureEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
        OperationBinding operationBinding = getBindings().getOperationBinding("Execute1");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
    }

    public void setTaxableAmtBindVar(RichInputText taxableAmtBindVar) {
        this.taxableAmtBindVar = taxableAmtBindVar;
    }

    public RichInputText getTaxableAmtBindVar() {
        return taxableAmtBindVar;
    }

    public void setTaxTableBindVar(RichTable taxTableBindVar) {
        this.taxTableBindVar = taxTableBindVar;
    }

    public RichTable getTaxTableBindVar() {
        return taxTableBindVar;
    }

    public void setTdsAmountBindVar(RichInputText tdsAmountBindVar) {
        this.tdsAmountBindVar = tdsAmountBindVar;
    }

    public RichInputText getTdsAmountBindVar() {
        return tdsAmountBindVar;
    }

    public void setTdsTableBindVar(RichTable tdsTableBindVar) {
        this.tdsTableBindVar = tdsTableBindVar;
    }

    public RichTable getTdsTableBindVar() {
        return tdsTableBindVar;
    }

    public void itemTdsDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            if (tdsRuleIdBind.getValue() != null && tdsRuleIdBind.getValue().toString().length() > 0) {

            } else {
                // showFacesMessage(msg, "E", false, "F");
                //showFacesMessage("Date must be greater than invoice date", "E", false, "V", null);
                //"TDS Rule is required"
                showFacesMessage(resolvEl("#{bundle['MSG.2131']}").toString(), "E", false, "F",
                                 tdsRuleIdBind.getClientId());
                return;
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
            OperationBinding exeOp = getBindings().getOperationBinding("Execute");
            exeOp.execute();
            OperationBinding exeOp1 = getBindings().getOperationBinding("Execute1");
            exeOp1.execute();
        }
    }

    public void tdsCanceledListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...

        OperationBinding opr = getBindings().getOperationBinding("getTdsRuleId");
        opr.execute();
        Integer reply = (Integer) opr.getResult();

        if (previousValue != null && ((Integer) previousValue) != reply &&
            Integer.parseInt(previousValue.toString()) > 0) {
            // System.out.println("Cancellation   :  "+previousValue);
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

            OperationBinding appTaxOp = getBindings().getOperationBinding("applyTdsRule");
            appTaxOp.getParamsMap().put("cldId", paramCldId);
            appTaxOp.getParamsMap().put("slocId", paramSlocId);
            appTaxOp.getParamsMap().put("orgId", paramOrgId);
            appTaxOp.getParamsMap().put("ruleId", previousValue);
            appTaxOp.getParamsMap().put("type", "I");
            appTaxOp.execute();

            OperationBinding exeOp = getBindings().getOperationBinding("Execute");
            exeOp.execute();
        }

        else if (previousValue != null && ((Integer) previousValue) == 0) {
            OperationBinding Op = getBindings().getOperationBinding("deleteTdsFromItm");
            Op.execute();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
            OperationBinding operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);

        } else {
            ;
        }
    }

    public void setCurrencyConFctBindVar(RichInputText currencyConFctBindVar) {
        this.currencyConFctBindVar = currencyConFctBindVar;
    }

    public RichInputText getCurrencyConFctBindVar() {
        return currencyConFctBindVar;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        OperationBinding operationBinding = getBindings().getOperationBinding("getOrgBsCurrency");
        operationBinding.execute();
        Object obj = operationBinding.getResult();
        return (String) obj;
    }

    public void setSuppCurrencyName(String suppCurrencyName) {
        this.suppCurrencyName = suppCurrencyName;
    }

    public String getSuppCurrencyName() {
        OperationBinding operationBinding = getBindings().getOperationBinding("getSupplierBsCurrency");
        operationBinding.execute();
        Object obj = operationBinding.getResult();
        return (String) obj;
    }

    public void setPayModeBindVar(RichSelectOneChoice payModeBindVar) {
        this.payModeBindVar = payModeBindVar;
    }

    public RichSelectOneChoice getPayModeBindVar() {
        return payModeBindVar;
    }

    public void setRemovePOTaxBindVar(RichPopup removePOTaxBindVar) {
        this.removePOTaxBindVar = removePOTaxBindVar;
    }

    public RichPopup getRemovePOTaxBindVar() {
        return removePOTaxBindVar;
    }

    public void removeTaxFromPO(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding Op = getBindings().getOperationBinding("deleteTrFromSrc");
            Op.execute();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
            OperationBinding operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
        }

    }

    public void setInvcSuppBindVar1(RichInputListOfValues invcSuppBindVar1) {
        this.invcSuppBindVar1 = invcSuppBindVar1;
    }

    public RichInputListOfValues getInvcSuppBindVar1() {
        return invcSuppBindVar1;
    }

    public void paymentScheduleDL(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding delPaySchd = getBindings().getOperationBinding("deletePaymentSchedule");
            delPaySchd.execute();
        }

        if (invcTypeBindVar.getValue().toString().equals("455") ||
            invcTypeBindVar.getValue().toString().equals("492")) //Check In case of Purchase Invoice
        {
            OperationBinding opr = getBindings().getOperationBinding("getPaySchdRowCount");
            opr.execute();
            Object obj = opr.getResult();
            if (obj != null) {
                Integer count = (Integer) obj;
                if (count <= 0) {
                    saveForwardBtn.setDisabled(true);
                } else {
                    saveForwardBtn.setDisabled(false);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveForwardBtn);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentAmtVar); //paymentSchdTblVar
        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentSchdTblVar);
    }

    public void setRemovePopUpPaymentSchedule(RichPopup removePopUpPaymentSchedule) {
        this.removePopUpPaymentSchedule = removePopUpPaymentSchedule;
    }

    public RichPopup getRemovePopUpPaymentSchedule() {
        return removePopUpPaymentSchedule;
    }

    public void OtherChargesValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number num = (Number) object;
            if (num.compareTo(new Number(0)) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.553']}"), null));
            }
        }
    }

    public void totalPayAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number num = (Number) object;
            if (num.compareTo(new Number(0)) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.553']}"), null));
            }
        }
    }

    public void setCurrencyFactor(ValueChangeEvent valueChangeEvent) {
        _log.info("current value change value is " + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            OperationBinding operationBinding = getBindings().getOperationBinding("setCurrencyFactor");
            operationBinding.getParamsMap().put("OrgId", String.valueOf(valueChangeEvent.getNewValue()));
            operationBinding.execute();
        }
    }

    public void cleanUpAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        OperationBinding operationBinding = getBindings().getOperationBinding("cleanUp");
        operationBinding.execute();
        //invcType
        Object obj = valueChangeEvent.getNewValue();
        if (obj != null) {
            OperationBinding setobind = getBindings().getOperationBinding("addinvcType");
            setobind.getParamsMap().put("invcType", (Integer) obj);
            setobind.execute();
        }

        _log.info("invc type value is " + obj);
        if (obj != null) {
            Integer type = Integer.parseInt(obj.toString());

            if (type == 455 || type == 492 || type == 737 || type == 739) {
                invcSuppBindVar1.setVisible(true);
                // invcSuppBindVar1.setDisabled(false);
            }

            else {
                invcSuppBindVar1.setVisible(false);
                // invcSuppBindVar1.setDisabled(true);
            }


            if (type == 492) //for Supplementary Invoice
            {
                showSuppInvc = true;
                showcompInvc = false;
                showSuppTrfInvc = false;
                AdfFacesContext.getCurrentInstance().addPartialTarget(invcSuppBindVar1);
            } else if (type == 716) //for Supplementary Transfer Invoice
            {
                showSuppTrfInvc = true;
                showSuppInvc = false;
                showcompInvc = false;
                AdfFacesContext.getCurrentInstance().addPartialTarget(invcSuppBindVar1);
            } else {
                showSuppInvc = false;
                showSuppTrfInvc = false;
                showcompInvc = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(invcSuppBindVar1);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(invcSuppBindVar1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srcOrgBindVar);
    }

    public void setOrganisationCurrFact(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        _log.info("organisation id is " + object);
        if (object != null && object.toString().length() > 0) {
            // RichSelectOneChoice selectedValue = (RichSelectOneChoice)uIComponent;
            // selectedValue.setValue(object);
            // setSrcOrgBindVar(selectedValue);
            OperationBinding operationBinding = getBindings().getOperationBinding("setCurrencyFactor");
            operationBinding.getParamsMap().put("OrgId", String.valueOf(object));
            operationBinding.execute();
            _log.info("s org id i" + operationBinding.getErrors());

            Object obj = operationBinding.getResult();
            if (obj.toString().equals("-1")) {
                //Currency Factor Not Found against this Organisation.Please Check In Organisation SetUp.
                String msg = resolvEl("#{bundle['LBL.3329']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            if (obj.toString().equals("-2")) {
                // "Transfer A/c Not Found against this Organisation.Please Check In MM Profile."
                String msg = resolvEl("#{bundle['LBL.3327']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    public void setSaveForwardBtn(RichCommandImageLink saveForwardBtn) {
        this.saveForwardBtn = saveForwardBtn;
    }

    public RichCommandImageLink getSaveForwardBtn() {
        return saveForwardBtn;
    }

    public void setBindInvoiceTabVar(RichShowDetailItem bindInvoiceTabVar) {
        this.bindInvoiceTabVar = bindInvoiceTabVar;
    }

    public RichShowDetailItem getBindInvoiceTabVar() {
        return bindInvoiceTabVar;
    }

    public void setBindTransDocVar(RichInputComboboxListOfValues bindTransDocVar) {
        this.bindTransDocVar = bindTransDocVar;
    }

    public RichInputComboboxListOfValues getBindTransDocVar() {
        return bindTransDocVar;
    }

    public String printReportAction() {
        // Add event code here...
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        OperationBinding operationBinding = getBindings().getOperationBinding("getCurrentRow");
        operationBinding.execute();
        Object obj = operationBinding.getResult();
        if (obj == null) {
            return null;
        }


        Row row = (Row) obj;
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            request.setAttribute("cldId", paramCldId);
            request.setAttribute("orgId", paramOrgId);
            request.setAttribute("slocId", paramSlocId);
            request.setAttribute("docId", row.getAttribute("DocId"));
            request.setAttribute("fyId", row.getAttribute("FyId"));
            request.setAttribute("InvcNo", row.getAttribute("InvcNo"));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/mminvoicereportservlet");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.responseComplete();
        }
        return null;
    }

    public void searchPurchaseInvoiceAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding chkOpr = getBindings().getOperationBinding("checkEoExist");
        chkOpr.execute();
        Boolean ob = (Boolean) chkOpr.getResult();
        if (ob != null && ob == false) {
            String msg = resolvEl("#{bundle['MSG.660']}");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return;
        }

        ob = null;
        chkOpr = getBindings().getOperationBinding("checkCurrencyExist");
        chkOpr.execute();
        ob = (Boolean) chkOpr.getResult();
        if (ob != null && ob == false) {
            String msg = resolvEl("#{bundle['LBL.3332']}");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return;
        }

        if (ob == null) {
            return;
        }

        OperationBinding exOpr = getBindings().getOperationBinding("executePurchaseInvoiceVO");
        exOpr.execute();
    }

    public String addNewPurchaseInvoice() {
        // Add event code here...
        // System.out.println("hi....");

        OperationBinding chkOpr = getBindings().getOperationBinding("checkValidation");
        chkOpr.execute();

        Boolean b = (Boolean) chkOpr.getResult();

        if (!b) {
            String msg = resolvEl("#{bundle['LBL.3334']}");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }

        OperationBinding exOpr = getBindings().getOperationBinding("selectedPurchaseInvoiceAction");
        exOpr.execute();
        return null;
    }

    public void priceDifferenceValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        /*   Object obj = valueChangeEvent.getNewValue();
    Number num = (Number)obj;
    OperationBinding exOpr = getBindings().getOperationBinding("setDifferencePrice");
    exOpr.getParamsMap().put("newPrice", num);
    exOpr.execute();  */
        AdfFacesContext.getCurrentInstance().addPartialTarget(selectedTblBind);
    }

    public void newPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number number = (Number) object;

            if (!isPrecisionValid(26, 6, number)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.57']}"), null));
            }

            if (number.compareTo(Number.zero()) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.553']}"), null));
            }

            Number obj = (Number) uIComponent.getAttributes().get("bindOldPriceVar");
            Number oldPrice = null;
            Number newPrice = null;
            oldPrice = new Number(obj);
            newPrice = number;

            if (newPrice.compareTo(oldPrice) == -1) {
                String msg = resolvEl("#{bundle['MSG.1301']}");
                // "New Price should be greater than Old Price."
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }

    }

    public void insertItemInPIAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding exOpr = null;

        exOpr = getBindings().getOperationBinding("checkItemSelectedPI");
        exOpr.execute();
        Boolean b = (Boolean) exOpr.getResult();

        if (!b) {
            String msg = resolvEl("#{bundle['LBL.3337']}"); //atleast one item should have new price
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return;
        }

        exOpr = getBindings().getOperationBinding("insertNewPurchaseInvoice");
        exOpr.execute();

        getBindings().getOperationBinding("Execute1").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);

        showcompInvc = true;
        showSuppInvc = false;
        showSuppTrfInvc = false;
    }

    public void setShowSuppInvc(boolean showSuppInvc) {
        this.showSuppInvc = showSuppInvc;
    }

    public boolean isShowSuppInvc() {
        return showSuppInvc;
    }

    public void setShowcompInvc(boolean showcompInvc) {
        this.showcompInvc = showcompInvc;
    }

    public boolean isShowcompInvc() {
        return showcompInvc;
    }

    public void setSrchFrmDt(RichInputDate srchFrmDt) {
        this.srchFrmDt = srchFrmDt;
    }

    public RichInputDate getSrchFrmDt() {
        return srchFrmDt;
    }

    public void setBindOperationVar(RichSelectOneRadio bindOperationVar) {
        this.bindOperationVar = bindOperationVar;
    }

    public RichSelectOneRadio getBindOperationVar() {
        return bindOperationVar;
    }

    public void setBindAmtAdjtVar(RichInputText bindAmtAdjtVar) {
        this.bindAmtAdjtVar = bindAmtAdjtVar;
    }

    public RichInputText getBindAmtAdjtVar() {
        return bindAmtAdjtVar;
    }

    public void applyAmtPerAction(ActionEvent actionEvent) {
        // Add event code here...

        Object obj = bindOperationVar.getValue();
        Object amt = bindAmtAdjtVar.getValue();
        String msg = resolvEl("#{bundle['MSG.977']}");
        if (amt == null) {
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return;
        }
        if (amt != null && amt.toString().length() <= 0) {
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return;
        }

        if (obj != null) {
            String str = (String) obj;

            if (str.equalsIgnoreCase("Amt")) {
                BigDecimal bd = (BigDecimal) amt;
                OperationBinding exOpr = getBindings().getOperationBinding("setAmtInSelectedPI");
                try {
                    exOpr.getParamsMap().put("amount", new Number(bd));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                exOpr.execute();

            }

            else if (str.equalsIgnoreCase("Per")) {
                BigDecimal bd = (BigDecimal) amt;
                // System.out.println(amt +" Amount");
                OperationBinding exOpr = getBindings().getOperationBinding("setPerInSelectedPI");
                try {
                    exOpr.getParamsMap().put("per", new Number(bd));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                exOpr.execute();
            }
        }
    }

    public void amountPerValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        if (object != null) {
            BigDecimal bd = (BigDecimal) object;
            // Number num = new Number(bd)
            /*  if(!isPrecisionValid(26,6,new Number(bd)))
                   {throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.57']}"),null));}
          */
            if (bd.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.253']}"), null));
            }
        }
    }

    public void setBindVarContinueBtn(RichCommandToolbarButton bindVarContinueBtn) {
        this.bindVarContinueBtn = bindVarContinueBtn;
    }

    public RichCommandToolbarButton getBindVarContinueBtn() {
        return bindVarContinueBtn;
    }

    public void setBindVarApplyBtn(RichCommandButton bindVarApplyBtn) {
        this.bindVarApplyBtn = bindVarApplyBtn;
    }

    public RichCommandButton getBindVarApplyBtn() {
        return bindVarApplyBtn;
    }

    public void setPiTableBind(RichTable piTableBind) {
        this.piTableBind = piTableBind;
    }

    public RichTable getPiTableBind() {
        return piTableBind;
    }

    public void setSelectedTblBind(RichTable selectedTblBind) {
        this.selectedTblBind = selectedTblBind;
    }

    public RichTable getSelectedTblBind() {
        return selectedTblBind;
    }

    public void setSrcOrgBindVar(RichSelectOneChoice srcOrgBindVar) {
        this.srcOrgBindVar = srcOrgBindVar;
    }

    public RichSelectOneChoice getSrcOrgBindVar() {
        return srcOrgBindVar;
    }

    public void goBackAction(ActionEvent actionEvent) {
        // Add event code here...cleanSelectedInvoiceVo
        OperationBinding exOpr = getBindings().getOperationBinding("cleanSelectedInvoiceVo");
        exOpr.execute();
        bindOperationVar.setValue("Amt");
        bindAmtAdjtVar.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindOperationVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindAmtAdjtVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindVarContinueBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindVarApplyBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(selectedTblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(piTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindVarGoBackBtn);
    }

    public void setBindVarGoBackBtn(RichCommandToolbarButton bindVarGoBackBtn) {
        this.bindVarGoBackBtn = bindVarGoBackBtn;
    }

    public RichCommandToolbarButton getBindVarGoBackBtn() {
        return bindVarGoBackBtn;
    }

    public void ocValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
    }

    public void currencyValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        OperationBinding clrOpr = getBindings().getOperationBinding("cleanUpOnInvcCreate");
        clrOpr.execute();
        bindTransDocVar.setValue(null);
        getBindings().getOperationBinding("Execute4").execute();
        bindOperationVar.setValue("Amt");
        bindAmtAdjtVar.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(currencyConFctBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindOperationVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindAmtAdjtVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindVarContinueBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindVarApplyBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(selectedTblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(piTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindVarGoBackBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindTransDocVar);
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void addOcAction(ActionEvent actionEvent) {
        // Add event code here...
        Object coaNm = bindCoaNm.getValue();
        Object spAmt = bindSpAmt.getValue();

        if (coaNm == null || coaNm.toString().trim().length() <= 0) {
            FacesMessage message = new FacesMessage("Please enter COA.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(bindCoaNm.getClientId(), message);
            return;
        }

        if (spAmt == null || ((Number) spAmt).compareTo(0) <= 0) {
            //Amount Should not be less than or equal to Zero.
            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.2132']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(bindSpAmt.getClientId(), message);
            return;
        }

        OperationBinding Opr = getBindings().getOperationBinding("checkDuplicateCoa");
        Opr.execute();

        Boolean b = (Boolean) Opr.getResult();

        if (b) {
            //COA already exists.
            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.2133']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(bindCoaNm.getClientId(), message);
            return;
        }
        getBindings().getOperationBinding("addCoa").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
    }

    public void setBindCoaNm(RichInputListOfValues bindCoaNm) {
        this.bindCoaNm = bindCoaNm;
    }

    public RichInputListOfValues getBindCoaNm() {
        return bindCoaNm;
    }

    public void setBindSpAmt(RichInputText bindSpAmt) {
        this.bindSpAmt = bindSpAmt;
    }

    public RichInputText getBindSpAmt() {
        return bindSpAmt;
    }

    public void searchMMTransferInvoiceAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding exOpr = getBindings().getOperationBinding("executeTransferInvoiceVO");
        exOpr.execute();
    }

    public String addNewTransferInvoice() {
        // Add event code here...
        OperationBinding chkOpr = getBindings().getOperationBinding("checkValidation1");
        chkOpr.execute();

        Boolean b = (Boolean) chkOpr.getResult();

        if (!b) {
            String msg = resolvEl("#{bundle['LBL.3334']}");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }
        OperationBinding exOpr = getBindings().getOperationBinding("selectedTransferInvoiceAction");
        exOpr.execute();
        return null;
    }

    public void TrfGoBackInvoice(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding exOpr = getBindings().getOperationBinding("cleanSelectedTrfInvoiceVo");
        exOpr.execute();
    }

    public void setSrchInvFrmDt(RichInputDate srchInvFrmDt) {
        this.srchInvFrmDt = srchInvFrmDt;
    }

    public RichInputDate getSrchInvFrmDt() {
        return srchInvFrmDt;
    }

    public void setBindOperationVar1(RichSelectOneRadio bindOperationVar1) {
        this.bindOperationVar1 = bindOperationVar1;
    }

    public RichSelectOneRadio getBindOperationVar1() {
        return bindOperationVar1;
    }

    public void setBindAmtAdjtVar1(RichInputText bindAmtAdjtVar1) {
        this.bindAmtAdjtVar1 = bindAmtAdjtVar1;
    }

    public RichInputText getBindAmtAdjtVar1() {
        return bindAmtAdjtVar1;
    }

    public void setSelectedTblBind1(RichTable selectedTblBind1) {
        this.selectedTblBind1 = selectedTblBind1;
    }

    public RichTable getSelectedTblBind1() {
        return selectedTblBind1;
    }

    public void setPiTableBind1(RichTable piTableBind1) {
        this.piTableBind1 = piTableBind1;
    }

    public RichTable getPiTableBind1() {
        return piTableBind1;
    }

    public void setBindVarApplyBtn1(RichCommandButton bindVarApplyBtn1) {
        this.bindVarApplyBtn1 = bindVarApplyBtn1;
    }

    public RichCommandButton getBindVarApplyBtn1() {
        return bindVarApplyBtn1;
    }

    public void applyAmtPerAction1(ActionEvent actionEvent) {
        // Add event code here...
        Object obj = bindOperationVar1.getValue();
        Object amt = bindAmtAdjtVar1.getValue();
        String msg = resolvEl("#{bundle['MSG.977']}");
        if (amt == null) {
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return;
        }
        if (amt != null && amt.toString().length() <= 0) {
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return;
        }

        if (obj != null) {
            String str = (String) obj;

            if (str.equalsIgnoreCase("Amt")) {
                BigDecimal bd = (BigDecimal) amt;
                OperationBinding exOpr = getBindings().getOperationBinding("setAmtInSelectedPI1");
                try {
                    exOpr.getParamsMap().put("amount", new Number(bd));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                exOpr.execute();

            }

            else if (str.equalsIgnoreCase("Per")) {
                BigDecimal bd = (BigDecimal) amt;
                // System.out.println(amt +" Amount");
                OperationBinding exOpr = getBindings().getOperationBinding("setPerInSelectedPI1");
                try {
                    exOpr.getParamsMap().put("per", new Number(bd));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                exOpr.execute();
            }
        }
    }

    public void amountPerValidator1(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        if (object != null) {
            BigDecimal bd = (BigDecimal) object;
            // Number num = new Number(bd)

            /*  if(!isPrecisionValid(26,6,new Number(bd)))
                   {throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.57']}"),null));}
          */

            if (bd.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.253']}"), null));
            }
        }
    }

    public void setShowSuppTrfInvc(boolean showSuppTrfInvc) {
        this.showSuppTrfInvc = showSuppTrfInvc;
    }

    public boolean isShowSuppTrfInvc() {
        return showSuppTrfInvc;
    }

    public void insertItmTrfAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding exOpr = null;

        String temp = null;
        exOpr = getBindings().getOperationBinding("checkItemSelectedTI");
        exOpr.execute();
        if (exOpr.getErrors().isEmpty()) {
            temp = exOpr.getResult().toString();
        }
        _log.info("value of result is " + temp);
        if (temp.equalsIgnoreCase("N")) {
            String msg = resolvEl("#{bundle['LBL.3337']}"); //atleast one item should have new price
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return;
        }

        /*  exOpr = getBindings().getOperationBinding("insertNewPurchaseInvoice");
        exOpr.execute(); */

        //insertNewPurTransferInvc

        exOpr = getBindings().getOperationBinding("insertNewPurTransferInvc");
        exOpr.execute();
        _log.info("now tab is shifted ");

        getBindings().getOperationBinding("Execute1").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelGrpSrcBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelGrpHeaderBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindInvoiceTabVar);
        //bindInvoiceTabVar
        showcompInvc = true;
        showSuppInvc = false;
        showSuppTrfInvc = false;
    }

    public void viewFileAction(FacesContext facesContext, OutputStream outputStream) {
        RichInputText bind = this.getFileNamepath();
        if (bind != null) {
            System.out.println("come ----- 1");
            String path = bind.getValue().toString();

            System.out.println("path is:  " + path);
            try {
                FileInputStream in = new FileInputStream(path);
                System.out.println("Available bytes are:  " + in.available());
                if (in.available() <= 0) {
                    System.out.println("came in unavailable!");
                    //  There is no data in the File !!
                    FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.2134']}").toString());
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return;
                }
                byte b[] = new byte[in.available()];
                int i = in.read(b);
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
                in.close();
                facesContext.getCurrentInstance().responseComplete();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                FacesMessage msg =
                    new FacesMessage(resolvEl("#{bundle['MSG.2135']}").toString()); //File Not Found in the Directory
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (IOException e) {
                System.out.println("IO Exception occur------");
                FacesMessage msg =
                    new FacesMessage(resolvEl("#{bundle['MSG.2136']}").toString()); //The File is corrupted!!
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void setFileNamepath(RichInputText fileNamepath) {
        this.fileNamepath = fileNamepath;
    }

    public RichInputText getFileNamepath() {
        return fileNamepath;
    }

    public void setTaxableItmAmtBind(RichInputText taxableItmAmtBind) {
        this.taxableItmAmtBind = taxableItmAmtBind;
    }

    public RichInputText getTaxableItmAmtBind() {
        return taxableItmAmtBind;
    }

    public void setTaxableItmAmtSpBind(RichInputText taxableItmAmtSpBind) {
        this.taxableItmAmtSpBind = taxableItmAmtSpBind;
    }

    public RichInputText getTaxableItmAmtSpBind() {
        return taxableItmAmtSpBind;
    }

    public void itmTaxRule(ActionEvent actionEvent) {
        // Add event code here...
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        OperationBinding appTaxOp = getBindings().getOperationBinding("applyTaxRule");
        appTaxOp.getParamsMap().put("cldId", paramCldId);
        appTaxOp.getParamsMap().put("slocId", paramSlocId);
        appTaxOp.getParamsMap().put("orgId", paramOrgId);
        appTaxOp.getParamsMap().put("ruleId", new Integer(0));
        //appTaxOp.getParamsMap().put("ruleId",(Integer)valueChangeEvent.getNewValue());
        appTaxOp.getParamsMap().put("type", "A");
        appTaxOp.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(taxableAmtBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxTableBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
    }

    public String dateValidator(Timestamp curr, Timestamp Todt) {
        java.sql.Date strtDt = null;
        java.sql.Date endDt = null;
        try {
            strtDt = curr.dateValue();
            endDt = Todt.dateValue();

        } catch (SQLException e) {
            System.out.println("Error in cast date" + e);
            return "N";
        }

        if (strtDt.compareTo(endDt) > 0) {
            if (strtDt.toString().equals(endDt.toString())) {
                //ok
            } else {
                return "N";
            }
        }

        return null;
    }

    public void pmtDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        _log.info("object value is " + object + "prdfrm value is " + invcCrtDateBind.getValue());
        if (object != null && invcCrtDateBind.getValue() != null &&
            invcCrtDateBind.getValue().toString().length() > 0) {
            Timestamp currDt = (Timestamp) object;

            Timestamp Todt = (Timestamp) invcCrtDateBind.getValue();
            String dateValid = dateValidator(Todt, currDt);
            _log.info("date vlidator is " + dateValid);
            java.sql.Date viewDt = null;
            try {
                viewDt = Todt.dateValue();
            } catch (Exception e) {
                _log.info("exception found in conversion" + e);
            }
            if ("Y".equalsIgnoreCase(dateValid)) {

            } else if ("N".equalsIgnoreCase(dateValid)) {
                showFacesMessage(resolvEl("#{bundle['MSG.2137']}").toString(), "E", false, "V",
                                 null); //Date must be greater than invoice date
            }

        }

    }

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     *      clientId : client id for UI component
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg, String clientId) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(clientId, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }


    public void setInvcCrtdDate(RichSelectOneChoice invcCrtdDate) {
        this.invcCrtdDate = invcCrtdDate;
    }

    public RichSelectOneChoice getInvcCrtdDate() {
        return invcCrtdDate;
    }

    public void setInvcCrtDateBind(RichInputDate invcCrtDateBind) {
        this.invcCrtDateBind = invcCrtDateBind;
    }

    public RichInputDate getInvcCrtDateBind() {
        return invcCrtDateBind;
    }

    public void setOldRcptQtyBind(RichInputText oldRcptQtyBind) {
        this.oldRcptQtyBind = oldRcptQtyBind;
    }

    public RichInputText getOldRcptQtyBind() {
        return oldRcptQtyBind;
    }

    public void newRcptQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (invcTypeBindVar.getValue() != null && getOldRcptQtyBind().getValue() != null && object != null) {
            Integer invctype = Integer.parseInt(invcTypeBindVar.getValue().toString());
            if (invctype.compareTo(492) == 0) {
                Number oldPrice = (Number) getOldRcptQtyBind().getValue();
                Number obj = (Number) object;

                if (obj.compareTo(new Number(0)) == 1) {
                    /*  if(obj.compareTo(oldPrice)==0 || obj.compareTo(oldPrice)==-1){

                   }else{
                       throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Quantity Should be less than or equals to old quantity",null));}
                      // showFacesMessage("Quantity must be less than or equals to Receipt quantity", "E", "V", typFlg);
                   } */



                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.474']}").toString(),
                                                                  null)); //"Quantity should be greater than zero"
                }
            }
        }

    }

    public void assblAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number obj = (Number) object;
            _log.info(obj + "current amount and comparison is " + obj.compareTo(new Number(0)) + "    " +
                      taxableItmAmtSpBind.getValue());
            if (obj.compareTo(new Number(0)) == 1 || obj.compareTo(new Number(0)) == 0) {

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.412']}").toString(),
                                                              null)); //Amount should be greater than zero
            }
        }
    }

    public void setPanelGrpSrcBind(RichPanelGroupLayout panelGrpSrcBind) {
        this.panelGrpSrcBind = panelGrpSrcBind;
    }

    public RichPanelGroupLayout getPanelGrpSrcBind() {
        return panelGrpSrcBind;
    }

    public void setPanelGrpHeaderBind(RichPanelGroupLayout panelGrpHeaderBind) {
        this.panelGrpHeaderBind = panelGrpHeaderBind;
    }

    public RichPanelGroupLayout getPanelGrpHeaderBind() {
        return panelGrpHeaderBind;
    }

    public void dblcClickEventStop(ClientEvent clientEvent) {
        _log.info("no need to perform any task on double click");
    }

    public String costCenterItmAction() {

        OperationBinding ccFinalize = getBindings().getOperationBinding("ccFinalizedCheck");
        ccFinalize.execute();
        if (ccFinalize.getResult() != null) {
            if (ccFinalize.getResult().toString().equalsIgnoreCase("N")) {
                //Profit Centre Profile is not setup properly.
                showFacesMessage(resolvEl("#{bundle['MSG.2455']}").toString(), "I", false, "F", null);
                return null;
            }
        }

        OperationBinding binding =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            // BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("updateCostCenterAmt").execute();
            return "costCenter";
        } else {
            return null;
        }
    }

    public void setTdsRuleIdBind(RichSelectOneChoice tdsRuleIdBind) {
        this.tdsRuleIdBind = tdsRuleIdBind;
    }

    public RichSelectOneChoice getTdsRuleIdBind() {
        return tdsRuleIdBind;
    }

    public String invoiceCancelAction() {
        OperationBinding binding =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("chkCancelInvc");
        binding.execute();
        System.out.println("resutl cancel--------" + binding.getResult().toString());
        Integer retrn = 0;
        if (binding.getResult() != null) {
            retrn = (Integer) binding.getResult();
            if (retrn == 1) {
                showPopup(invcCnclPopupBind, true);
                return null;
            } else {
                showFacesMessage(resolvEl("#{bundle['MSG.2138']}").toString(), "I", false,
                                 "F"); //You can not cancel this Invoice.
                return null;
            }
        }

        showPopup(invcCnclPopupBind, true);
        return null;
    }

    public void setInvcCnclPopupBind(RichPopup invcCnclPopupBind) {
        this.invcCnclPopupBind = invcCnclPopupBind;
    }

    public RichPopup getInvcCnclPopupBind() {
        return invcCnclPopupBind;
    }

    public String invoiceCancellationOkAction() {
        //System.out.println(" inside-------------------------------------");
        /*    Integer paramUsrId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId= resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        */
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String WfNo = "0";
        String UsrId = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
        Integer level = 0;
        String action = "I";
        String remark = "A";
        Integer res = -1;
        String currUser = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();

        OperationBinding opr = getBindings().getOperationBinding("pendingCheck");
        opr.getParamsMap().put("SlocId", paramSlocId);
        opr.getParamsMap().put("CldId", paramCldId);
        opr.getParamsMap().put("OrgId", paramOrgId);
        opr.getParamsMap().put("DocNo", 18521);
        opr.execute();
        //      System.out.println("0---------------------");
        //    System.out.println("----------------"+opr.getResult().toString());
        Object pendUser = opr.getResult();
        /* if (pendUser != null) {
                System.out.println("----------------------->");
                if (!currUser.equals(pendUser.toString())) {
                    System.out.println("---------------------->>>");
                    //return "ALREADY_SAVED";
                    return "PENDING";
                }
            } */
        // return "PENDING";
        //get Wf Id
        //  System.out.println("01---------------------");
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", paramSlocId);
        WfnoOp.getParamsMap().put("CldId", paramCldId);
        WfnoOp.getParamsMap().put("OrgId", paramOrgId);
        WfnoOp.getParamsMap().put("DocNo", 18521);
        WfnoOp.execute();
        //System.out.println("1---------------------");
        //System.out.println(" wfno result------------------"+  WfnoOp.execute());
        if (WfnoOp.getResult() != null) {
            WfNo = WfnoOp.getResult().toString();
            //  System.out.println("wf no-----------------------"+WfNo);
        }
        //System.out.println("outside-------------wf no-----------------------"+WfNo);
        // System.out.println("2---------------------");
        if (WfNo != null && !"0".equalsIgnoreCase(WfnoOp.getResult().toString())) //get user level
        {
            //            System.out.println("3---------------------");
            //            System.out.println("outside-------------wf no not nulll-----------------------"+WfNo);
            OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
            usrLevelOp.getParamsMap().put("SlocId", paramSlocId);
            usrLevelOp.getParamsMap().put("CldId", paramCldId);
            usrLevelOp.getParamsMap().put("OrgId", paramOrgId);
            usrLevelOp.getParamsMap().put("UsrId", UsrId);
            usrLevelOp.getParamsMap().put("WfNo", WfNo);
            usrLevelOp.getParamsMap().put("DocNo", 18521);
            usrLevelOp.execute();
            if (usrLevelOp.getResult() != null) {
                level = Integer.parseInt(usrLevelOp.getResult().toString());
                //                System.out.println(" level --"+level);
            }
            //            System.out.println("4---------------------");
            if (level > -1) {
                //insert into wf$txn
                OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                insTxnOp.getParamsMap().put("SlocId", paramSlocId);
                insTxnOp.getParamsMap().put("CldId", paramCldId);
                insTxnOp.getParamsMap().put("OrgId", paramOrgId);
                insTxnOp.getParamsMap().put("DocNo", 18521);
                insTxnOp.getParamsMap().put("WfNo", WfNo);
                insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                insTxnOp.getParamsMap().put("levelFrm", level);
                insTxnOp.getParamsMap().put("levelTo", level);
                insTxnOp.getParamsMap().put("action", action);
                insTxnOp.getParamsMap().put("remark", remark);
                insTxnOp.getParamsMap().put("amount", 0);
                insTxnOp.execute();
                //                System.out.println(" inside level >-1 -----------------");
                if (insTxnOp.getResult() != null) {
                    res = Integer.parseInt(insTxnOp.getResult().toString());
                }
                invcCnclPopupBind.hide();
                return "cancelToWf";
            } else {
                //Something went wrong (User level in Workflow).Please Contact to ESS!
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1875']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            //            System.out.println("5---------------------");
            /* FacesMessage message2 = new FacesMessage("Workflow not Defined for current Organisation.");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2); */
            //Workflow not Defined for this Document.
            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1486']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);


        }
        return null;
    }

    public void PopInvoiceCancel(ActionEvent actionEvent) {
        invcCnclPopupBind.hide();
    }

    public String fileUploadAction() throws Exception {
        String path = "";
        String extn = "";
        List<UploadedFile> files = this.getUploadedFile();
        InputStream in = null;
        FileOutputStream out = null;
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                try {
                    _log.info("----file size-------------- " + files.size());
                    //get file extension
                    extn = files.get(i).getFilename().substring(files.get(i).getFilename().lastIndexOf("."));
                    _log.info("------filename--- " + files.get(i).getFilename());
                    //Add files to the Table
                    OperationBinding op = getBindings().getOperationBinding("createAttchmntRow");
                    //  op = ADFUtils.findOperation("createAttchmntRow");
                    op.getParamsMap().put("fileNm", files.get(i).getFilename());
                    op.getParamsMap().put("contentTyp", files.get(i).getContentType());
                    op.getParamsMap().put("extn", extn);
                    op.execute();

                    if (op.getResult() != null) {
                        path = op.getResult().toString();
                    }
                    System.out.println("extn " + extn);
                    //write files in the file system.

                    in = files.get(i).getInputStream();
                    System.out.println(files.get(i).getInputStream());

                    System.out.println("write file at " + path + extn);
                    out = new FileOutputStream(path + extn);
                    byte[] buffer = new byte[8192];
                    int bytesRead = 0;

                    while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                        out.write(buffer, 0, bytesRead);
                        // out.flush();
                    }
                    out.flush();

                    //call commit after checking all validations
                    _log.info("---before commit-------------");
                    OperationBinding obCommit = getBindings().getOperationBinding("Commit");
                    obCommit.execute();
                    _log.info("---After commit-------------");
                    ///   ADFUtils.findOperation("Commit").execute();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                }
            }
        }
        //remove the files to prepare for new uploads
        _log.info("----------------last moment----------------");
        setUploadedFile(null);
        /*   if (attachTableBind.getRowCount() > 0) {
            _log.info("----------table rowcount > 0--------------");
            deleteLinkBind.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(deleteLinkBind);
        }
       */
        _log.info("----------------last moment----------------111");
        //AdfFacesContext.getCurrentInstance().addPartialTarget(attachTableBind);

        return null;
    }

    public void downloadActionListener(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void deleteDwnLoadAction(ActionEvent actionEvent) {
        //       String path = null;
        //     _log.info("inside null delet------------000000>>>>>>>>>>");
        /*    Object pathObj = actionEvent.getComponent().getAttributes().get("dispFilePath");
        Object key = actionEvent.getComponent().getAttributes().get("rowKey");

        _log.info("inside null delet------------111000000>>>>>>>>>> " + pathObj + " " + key);
        if (pathObj != null) {
            path = actionEvent.getComponent().getAttributes().get("dispFilePath").toString();

            //path = pathObj.toString();
            _log.info("add file path is " + path);
            File f = new File(path);


            try {
                boolean success = f.delete();
                _log.info("success-------------" + success);
                if (success) {
                    _log.info("File Deletedod");
                    OperationBinding obDelete = getBindings().getOperationBinding("Delete");
                    obDelete.execute();
                    OperationBinding obCommit = getBindings().getOperationBinding("Commit");
                    obCommit.execute();
                    OperationBinding obExecute = getBindings().getOperationBinding("Execute6");
                    obExecute.execute();
                } else {
                    _log.info("file not deleted --- " + success);
                }
            } catch (Exception x) {
                System.err.format("%s: no such" + " file or directory%n", path);
            }
        } */

        //change code due to page hang when deleting.
        if (attachTableBind.getRowCount() == 0) {
            _log.info("----------table rowcount 0--------------");
            return;
        }

        OperationBinding obDelete = getBindings().getOperationBinding("deleteAttachRow");
        obDelete.execute();
        OperationBinding obCommit = getBindings().getOperationBinding("Commit");
        obCommit.execute();
        OperationBinding obExecute = getBindings().getOperationBinding("Execute6");
        obExecute.execute();
        _log.info("----------after deleted--------------");
        /*  if (attachTableBind.getRowCount() == 0) {
            _log.info("----------table rowcount 0--------------");
            deleteLinkBind.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(deleteLinkBind);
        } */
    }

    public void dowloadMethod(FacesContext facesContext, OutputStream outputStream) {
        //Read file from particular path, path bind is binding of table field that contains path
        //File filed = new File(pathPgBind.getValue().toString());
        // String path = JSFUtils.resolveExpressionAsString("#{row.AttchFlPath}");
        RichInputText bind = this.getFileBindName();
        if (bind != null) {
            System.out.println("come ----- 1");
            String path = bind.getValue().toString();

            System.out.println("path is:  " + path);
            File file = new File(path);
            FileInputStream fis = null;
            byte[] b;
            try {
                fis = new FileInputStream(file);
                int n;
                while ((n = fis.available()) > 0) {
                    b = new byte[n];
                    int result = fis.read(b);
                    outputStream.write(b, 0, b.length);
                    if (result == -1)
                        break;
                }
                outputStream.flush();
            } catch (IOException e) {
                //    JSFUtils.addFacesErrorMessage("Problem in downloading a file");
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void setFileBindName(RichInputText fileBindName) {
        this.fileBindName = fileBindName;
    }

    public RichInputText getFileBindName() {
        return fileBindName;
    }

    //get Top WF usr level
    public String getTopWFUsrLevel() {
        OperationBinding op = getBindings().getOperationBinding("checkWFUsrLevel");
        op.execute();
        String retn = "N";
        if (op.getResult() != null) {
            retn = op.getResult().toString();
            _log.info("---return ---" + retn);
            return retn;
        } else
            return retn;
    }
    //apply default tds
    public void defaultTDSAction(ActionEvent actionEvent) {
        OperationBinding tdsOp = getBindings().getOperationBinding("applyDefaultTDS");
        tdsOp.execute();
        if (tdsOp.getResult() != null) {
            if (tdsOp.getResult().toString().equalsIgnoreCase("N")) {
                //Default TDS not defined

                showFacesMessage(resolvEl("#{bundle['MSG.2223']}").toString(), "E", false, "F");
                return;
            } else if (tdsOp.getResult().toString().equalsIgnoreCase("Y")) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
                AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
                OperationBinding exeOp = getBindings().getOperationBinding("ExecuteInvcItm1");
                //    exeOp.execute();
                OperationBinding exeOp1 = getBindings().getOperationBinding("Execute1");
                exeOp1.execute();
                OperationBinding exeOp2 = getBindings().getOperationBinding("Execute");
                exeOp2.execute();
                //Defualt TDS applied succesfully.
                //    System.out.println("executed tds line table-----------------??????????????????????");
                showFacesMessage(resolvEl("#{bundle['MSG.2224']}").toString(), "I", false, "F");
                //return;
            } else if (tdsOp.getResult().toString().equalsIgnoreCase("S")) {
                showFacesMessage("Function Return null values........", "E", false, "F");
                return;
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(poItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptTotalVar);
    }

    public void extDocDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            Timestamp ts = (Timestamp) object;
            if (invcCrtDateBind.getValue() != null) {
                _log.info(ts + " -----------test--------- " + invcCrtDateBind.getValue());
                if (ts.toString().compareTo(invcCrtDateBind.getValue().toString()) == 1) {
                    showFacesMessage("Date must be before or on " + invcCrtDateBind.getValue(), "E", false, "V");
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    //custome query Listener to filter data in LIKE mode
    //InvcNo
    //ExtDocNo
    public void suppTableCustomQueyListener(QueryEvent queryEvent) {

        FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor) queryEvent.getDescriptor();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
            Map m = queryDescriptor.getFilterCriteria();
            //Set keys = m.keySet();
            Map modified = new HashMap();
            Map modifiedReset = new HashMap();
            /*   for (Iterator i = keys.iterator(); i.hasNext();) {
                String key = (String) i.next();
                String value = (String) m.get(key);
                String value1 = (String) m.get(key);
                if (value != null && !value.equals("")) {
                    value = "%" + value + "%";
                }
            // modified.put(key, value);
            //modifiedReset.put(key, value1);
            //  _log.info("suppTableCustomQueyListener----  " + key + " = " + value + " " + value1);
            // }

     */if (m.get("InvcNo") != null && !m.get("InvcNo").equals("")) {

                String invoiceNo = (String) m.get("InvcNo");

                if (invoiceNo != null && !invoiceNo.equals("")) {
                    invoiceNo = "%" + invoiceNo + "%";
                    modified.put("InvcNo", invoiceNo);
                    modifiedReset.put("InvcNo", m.get("InvcNo"));
                }
                _log.info("suppTableCustomQueyListener----   " + invoiceNo + " ");
            }

            if (m.get("ExtDocNo") != null && !m.get("ExtDocNo").equals("")) {
                String extDocNo = (String) m.get("ExtDocNo");
                if (extDocNo != null && !extDocNo.equals("")) {
                    extDocNo = "%" + extDocNo + "%";
                    modified.put("ExtDocNo", extDocNo);
                    modifiedReset.put("ExtDocNo", m.get("ExtDocNo"));
                }
                _log.info("suppTableCustomQueyListener----   " + " " + extDocNo);
            }

            queryDescriptor.getFilterCriteria().clear();

            this.invokeMethodExpression("#{bindings.SrchPurInvcTrans1Query.processQuery}", Object.class,
                                        QueryEvent.class, queryEvent);
            queryDescriptor.setFilterCriteria(modified);
            this.invokeMethodExpression("#{bindings.SrchPurInvcTrans1Query.processQuery}", Object.class,
                                        QueryEvent.class, queryEvent);
            queryDescriptor.getFilterCriteria().clear();
            queryDescriptor.setFilterCriteria(modifiedReset);

        }
    }

    public Object invokeMethodExpression(String expr, Class returnType, Class[] argTypes, Object[] args) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elctx = fc.getELContext();
        ExpressionFactory elFactory = fc.getApplication().getExpressionFactory();
        MethodExpression methodExpr = elFactory.createMethodExpression(elctx, expr, returnType, argTypes);
        return methodExpr.invoke(elctx, args);
    }

    public Object invokeMethodExpression(String expr, Class returnType, Class argType, Object argument) {
        return invokeMethodExpression(expr, returnType, new Class[] { argType }, new Object[] { argument });
    }

    public void setAttachTableBind(RichTable attachTableBind) {
        this.attachTableBind = attachTableBind;
    }

    public RichTable getAttachTableBind() {
        return attachTableBind;
    }

    public void setDeleteLinkBind(RichLink deleteLinkBind) {
        this.deleteLinkBind = deleteLinkBind;
    }

    public RichLink getDeleteLinkBind() {
        return deleteLinkBind;
    }

    public String getWfHistryView() {
        OperationBinding wfFlowView = getBindings().getOperationBinding("viewWFData");
        wfFlowView.execute();
        if (wfFlowView.getResult() != null) {
            return wfFlowView.getResult().toString();
        } else {
            return "Document Not In WF";
        }
    }
}

