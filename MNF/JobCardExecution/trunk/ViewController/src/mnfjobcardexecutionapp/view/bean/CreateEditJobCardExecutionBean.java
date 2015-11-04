package mnfjobcardexecutionapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.bean.StaticValue;
import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import mnfjobcardexecutionapp.view.Utils.ADFUtils;

import mnfjobcardexecutionapp.view.Utils.JSFUtils;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandNavigationItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class CreateEditJobCardExecutionBean {
    OperationBinding ob = null;
    private String jeMode = resolvEl("#{pageFlowScope.JobCardExecutionMode}").toString();
    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(CreateEditJobCardExecutionBean.class);
    private RichInputDate sourceDateBindVar;
    private String searchValue = "";
    private Key key = null;
    private String facetName = "Lot";
    private RichInputDate sourceStartDateBindVar;
    private RichInputDate sourceEndDateBindVar;
    private RichPanelFormLayout parameterFormBinding;
    private RichTable paramTableBindVar;
    private RichOutputText bindParamType;
    private RichTable downtimeParamTableBindVar;
    private RichInputText costingParamValBind;
    private RichSelectOneChoice jeBasisBind;
    private RichInputText outputItemId;
    private RichInputText outputItemName;
    private RichInputText outputItemQty;
    private RichInputText outputItemActQty;
    private RichSelectOneChoice sourceWarehouseBindVar;
    private RichSelectOneChoice sourceRequirementAreaBindVar;
    private RichTable costingParamTableBindVar;
    private RichInputText itemBracketBindVar;
    private String workflowId = "";
    private RichTable barcodeBindVar;
    private RichInputDate startDateBind;
    private RichInputDate downtimeStartDateBind;
    private RichPanelFormLayout headerFormBind;
    public Boolean jrcId = true;
    public Boolean opId = true;
    private RichPopup selectSrNoPopUpBind;
    private RichPopup selectLotPopUpBind;
    private RichPopup selectLotBinPopUpBind;
    private RichOutputText bindStockAvailable;
    private RichInputText bindActualItemQuantity;
    private RichTable inputItmTableBind;
    private RichPanelFormLayout operationOutputFormBind;
    private RichOutputText bindActQty;
    private RichOutputText bindTotalStock;
    private RichOutputText binditmQtyIssue;
    private RichInputListOfValues operationLOVBind;
    private RichPanelBox operationPanelboxBind;
    private RichLink shadowLinkBind;
    private RichInputText serialNoBindVar;
    private RichInputText lotIdInSerial;
    private String LotNo = "";
    private String LotQty = "";
    private RichPopup bindAddOutputItmLotPopUp;
    private RichTable lotTableBind;
    private RichInputText bindLotId;
    private RichInputText bindLotQty;
    private RichTable serialTableBind;
    private String itmSerialized = "";
    private RichShowDetailItem bindOutputStockTab;
    private RichPanelTabbed panelTabBinding;
    private RichOutputText bindItemLotId;
    private RichPanelFormLayout itemSerialFormBind;
    public Integer serialNo = 0;
    private RichButton bindAddLotBtn;
    private RichButton bindSrNoBtn;
    private RichOutputText bindIssueActQty;
    private RichCommandNavigationItem viewLotBind;
    private RichCommandNavigationItem viewBinBind;
    private RichCommandNavigationItem viewSerialBind;
    private boolean releaseFlag = false;
    private RichShowDetailItem bindOperationItemTab;
    private RichOutputText bindItmIssueType;
    private RichOutputText bindStockAvailAtShopFloor;
    private RichPopup selectItemManualPopUpBind;
    private RichPopup bindSelectLotManualPopUp;
    private RichPopup bindSelectLotBinManualPopUp;
    private RichInputListOfValues paramNm;
    private RichTable lotTbl;
    private RichPanelBox lotPanelBox;
    private UIXSwitcher lotSwitcher;
    private RichSelectOneRadio storeTrfRB;
    private RichSelectOneChoice radioBtnVal;
    private RichTable binTbl;
    private RichTable srTbl;
    private String chkPopulateItm = "N";
    private RichOutputText pendingQty;
    public Timestamp ts= new Timestamp(System.currentTimeMillis());
    private RichInputDate expirydateBind;
    private String qcChkFlg="N";

    public void setQcChkFlg(String qcChkFlg) {
        this.qcChkFlg = qcChkFlg;
    }

    public String getQcChkFlg() {
                OperationBinding ob1 = ADFBeanUtils.findOperation("ChkQcRequired");
                ob1.execute();
                String result=(String)ob1.getResult();
                adfLog.info("value of result in getter::"+result);
                return result;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public Timestamp getTs() {
        return StaticValue.getTruncatedCurrDt();
        //return ts;
    }


    public void setChkPopulateItm(String chkPopulateItm) {
        this.chkPopulateItm = chkPopulateItm;
    }

    public String getChkPopulateItm() {
        return chkPopulateItm;
    }

    public CreateEditJobCardExecutionBean() {
    }


    public void setSerialNo(Integer serialNo) throws SQLException {
        Number qty = (Number) this.outputItemActQty.getValue();
        this.serialNo = qty.floor().intValue();
    }

    public Integer getSerialNo() throws SQLException {
        Number qty = (Number) this.outputItemActQty.getValue();
        return qty.floor().intValue();
    }

    public void setFacetName(String facetName) {
        this.facetName = facetName;
    }

    public String getFacetName() {
        return facetName;
    }


    public void setReleaseFlag(boolean releaseFlag) {
        this.releaseFlag = releaseFlag;
    }

    public boolean isReleaseFlag() {
        return releaseFlag;
    }

    public void setItmSerialized(String itmSerialized) {
        this.itmSerialized = itmSerialized;
    }

    public String getItmSerialized() {
        //ob = ADFUtils.findOperation("ChkOutputItmSerialized");
        ob = ADFBeanUtils.findOperation("ChkOutputItmSerialized");
        ob.execute();
        System.out.println("Output Item Ser : " + ob.getResult().toString());
        return ob.getResult().toString();
    }

    public void setLotNo(String LotNo) {
        this.LotNo = LotNo;
    }

    public String getLotNo() {
        return LotNo;
    }

    public void setLotQty(String LotQty) {
        this.LotQty = LotQty;
    }

    public String getLotQty() {
        return LotQty;
    }

    public void setJrcId(Boolean jrcId) {
        this.jrcId = jrcId;
    }

    public Boolean getJrcId() {
        return jrcId;
    }

    public void setOpId(Boolean opId) {
        this.opId = opId;
    }

    public Boolean getOpId() {
        return opId;
    }

    public void setJeMode(String jeMode) {
        this.jeMode = jeMode;
    }

    public String getJeMode() {
        return jeMode;
    }


    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public Object resolvEl(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public void addActionListener(ActionEvent actionEvent) {
        RequestContext.getCurrentInstance().getPageFlowScope().put("JobCardExecutionMode", "C");
        this.setJeMode("C");
        ADFBeanUtils.findOperation("CreateWithParamsJe").execute();
        
        
    }

    public void editActionListener(ActionEvent actionEvent) {
        this.setJeMode("E");
    }

    public void saveActionListener(ActionEvent actionEvent) {
        Integer chkcc = Integer.parseInt(resolvEl("#{pageFlowScope.CHECK_CC}").toString());
        
        //ADFBeanUtils.findOperation("Commit").execute();
        
        if (CheckLotEntry_Func("CheckforOutputItmLot").equals("checked")) {
            Object obJC = ADFBeanUtils.findIterator("MnfJe1Iterator").getCurrentRow().getAttribute("transJcId");
            Object obRC = ADFBeanUtils.findIterator("MnfJe1Iterator").getCurrentRow().getAttribute("transRcId");
            Object obOP =
                ADFBeanUtils.findIterator("MnfJe1Iterator").getCurrentRow().getAttribute("transOperationDesc");
            ob = ADFBeanUtils.findOperation("QcCheck");
            ob.execute();
            Object obj1 = ob.getResult();
            ob = ADFBeanUtils.findOperation("mnfTomm");
            ob.execute();
            Object obj2 = ob.getResult();
            //Object obj2 = 1;
            //System.out.println("QC Req : "+obj1.toString()+"     "+"Commit or Not : "+Integer.parseInt(obj2.toString()));
            if (Integer.parseInt(obj2.toString()) == 1) {
                if (jeBasisBind.getValue().equals(109)) {
                    if (obJC != null) {
                        if (this.getChkPopulateItm().equals("N")) {
                            StringBuilder message = new StringBuilder();
                            message.append("Please Populate Operation Items.");
                            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(),
                                                                    FacesMessage.SEVERITY_INFO);
                            setDisclosedFirstTab(bindOperationItemTab);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(bindOperationItemTab);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                        } else {
                            //if (obOP != null) {
                            //callWorkFlow();
            //Code added by Chirag..
                    String Schk = (String) getItmSerialized();
                    if (Schk.equals("Y")) {
                     if(serializedQuantityValidation().equalsIgnoreCase("true"))
                   {
                     ob = ADFBeanUtils.findOperation("deleteStkLot");
                      ob.execute();
                    }else{
                    adfLog.info(" in the else block....");
                    return;
                }
            }
//                            if (releaseFlag) {
//                                System.out.println("inside releasing :" + releaseFlag);
//                                //ADFBeanUtils.findOperation("releaseJE").execute();
//                            }
                            setAttrsVal("MnfJe1Iterator", "JeMode", 43);
                            if (obj1.toString().equals("Y")) {
                                ob = ADFBeanUtils.findOperation("changeStatus");
                                ob.getParamsMap().put("value", 119);
                                ob.execute();
                                setAttrsVal("MnfJe1Iterator", "JeStat", 118);
                            } else {
                                ob = ADFBeanUtils.findOperation("changeStatus");
                                ob.getParamsMap().put("value", 107);
                                ob.execute();
                                setAttrsVal("MnfJe1Iterator", "JeStat", 74);//change the status of Job Execution to Released in case of not Qc Check
                            }
//                            ob = ADFBeanUtils.findOperation("releaseJE");
//                            ob.execute();
                            ADFBeanUtils.findOperation("Commit").execute();
                            this.setJeMode("V");
                            //JSFUtils.addFacesInformationMessage("Record Saved Successfully!");
                            setDisclosedFirstTab(bindOperationItemTab);
                            if(chkcc!=null && chkcc.compareTo(new Integer(0))==1){
                                adfLog.info("in the iff loop jc::"+chkcc);
                            ADFBeanUtils.findOperation("sendDateFromTempCcToSlsSoCc").execute();
                            }
//                            String Schk = (String) getItmSerialized();
//                            if (Schk.equals("Y")) {
//                                if(serializedQuantityValidation().equalsIgnoreCase("true"))
//                                {
//                                ob = ADFBeanUtils.findOperation("deleteStkLot");
//                                ob.execute();
//                                }else{
//                                    adfLog.info(" in the else block....");
//                                    return;
//                                }
//                            }
                            adfLog.info("Status Update is called");
                            ob = ADFBeanUtils.findOperation("statusUpdate");
                            ob.execute();
                           OperationBinding ob1 = ADFBeanUtils.findOperation("insertFinanaceEntry");
                           ob1.execute();
                            adfLog.info("value of voucher id:::"+ob1.getResult());
                            int out = Integer.parseInt(ob.getResult().toString());
                            if ( out != -1) {
                                JSFUtils.addFacesInformationMessage("Record Saved Successfully!!");
                                ADFBeanUtils.findOperation("Commit").execute();
                            }
                            //                } else {
                            //                    setOpId(false);
                            //                    showFacesMsg("Please select Operation!! ", " ", FacesMessage.SEVERITY_ERROR, null);
                            //                }
                        }
                    } else {
                        setJrcId(false);
                        showFacesMsg("Please select Source Id!! ", " ", FacesMessage.SEVERITY_ERROR, null);
                    }
                } else if (jeBasisBind.getValue().equals(110)) {
                    if (obRC != null) {
                        if (this.getChkPopulateItm().equals("N")) {
                            StringBuilder message = new StringBuilder();
                            message.append("Please Populate Operation Items.");
                            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(),
                                                                    FacesMessage.SEVERITY_INFO);
                            setDisclosedFirstTab(bindOperationItemTab);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(bindOperationItemTab);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                        } else {
                            //if (obOP != null) {
                            //callWorkFlow();
                            if (releaseFlag) {
                                //ADFBeanUtils.findOperation("releaseJE").execute();
                            }
                            //Code added by chirag
                            String Schk = (String) getItmSerialized();
                            if (Schk.equals("Y")) {
                             if(serializedQuantityValidation().equalsIgnoreCase("true"))
                            {
                             ob = ADFBeanUtils.findOperation("deleteStkLot");
                              ob.execute();
                            }else{
                            adfLog.info(" in the else block....");
                            return;
                            }
                            }
                            setAttrsVal("MnfJe1Iterator", "JeMode", 43);
                            if (obj1.toString().equals("Y")) {
                                ob = ADFBeanUtils.findOperation("changeStatus");
                                ob.getParamsMap().put("value", 119);
                                ob.execute();
                                setAttrsVal("MnfJe1Iterator", "JeStat", 118);
                            } else {
                                ob = ADFBeanUtils.findOperation("changeStatus");
                                ob.getParamsMap().put("value", 107);
                                ob.execute();
                                setAttrsVal("MnfJe1Iterator", "JeStat", 74);//Change Status of Job Execution to released.
                            }
//                            ob = ADFBeanUtils.findOperation("releaseJE");
//                            ob.execute();
                            ADFBeanUtils.findOperation("Commit").execute();
                            this.setJeMode("V");
                            JSFUtils.addFacesInformationMessage("Record Saved Successfully!");
                                if(chkcc!=null && chkcc.compareTo(new Integer(0))==1){
                                    adfLog.info("in the iff loop rc::"+chkcc);
                                ADFBeanUtils.findOperation("sendDateFromTempCcToSlsSoCc").execute();
                                }
                            setDisclosedFirstTab(bindOperationItemTab);
//                            String Schk = (String) getItmSerialized();
//                            if (Schk.equals("Y")) {
//                                adfLog.info("in the block Route Card::"+Schk);
//                                ob = ADFBeanUtils.findOperation("deleteStkLot");
//                                ob.execute();
//
//                            }
                            adfLog.info("asdsdasdsa");
                            ob = ADFBeanUtils.findOperation("statusUpdate");
                            ob.execute();
                            OperationBinding ob1 = ADFBeanUtils.findOperation("insertFinanaceEntry");
                            ob1.execute();
                            adfLog.info("value of voucher id:::"+ob1.getResult());
                            int out = Integer.parseInt(ob.getResult().toString());
                            if (out != -1) {
                                ADFBeanUtils.findOperation("Commit").execute();
                            }
                            //                } else {
                            //                    setOpId(false);
                            //                    showFacesMsg("Please select Operation!! ", " ", FacesMessage.SEVERITY_ERROR, null);
                            //                }

                        }
                    } else {
                        setJrcId(false);
                        showFacesMsg("Please select Source Id!! ", " ", FacesMessage.SEVERITY_ERROR, null);
                    }
                } else if ("N".equals(dataExists("MnfJeOpItm1Iterator"))) {
                    showFacesMsg("Items Required ", "Job Execution cannot be saved without any item!!. ",
                                 FacesMessage.SEVERITY_ERROR, null);
                } else {
                }
            } else {
                showFacesMsg("Unable to update the record Contact ESS ", "", FacesMessage.SEVERITY_ERROR, null);
            }
        } else {
            StringBuilder message = new StringBuilder();
            message.append("<P>Please Fill All the lot Quantity for Output Item:</P><BR>");
            //message.append(output);
            ADFModelUtils.showFormattedFacesMessage("Job Card Execution", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
            //setDisclosedFirstTab(bindOperationItemTab);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(bindOperationItemTab);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
        }
        
        
    }

    private String dataExists(String iter) {

        DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countVal = 0;

        while (rSetIter.hasNext()) {
            r = rSetIter.next();

            countVal = countVal + 1;

        }
        rSetIter.closeRowSetIterator();
        return countVal == 0 ? "N" : "Y";
    }

    public void searchOpItems(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void jeBasisValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public String cancelActionEvent() {
        this.setJeMode("D");
        ADFBeanUtils.findOperation("Rollback").execute();
        return "back";
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void jobCardValueChangeListener(ValueChangeEvent valueChangeEvent) {
        ActionEvent a = new ActionEvent(shadowLinkBind);
        a.queue();
        linkActionListener(a);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDateBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceStartDateBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceEndDateBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceWarehouseBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceRequirementAreaBindVar);
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void routeCardValueChangeListener(ValueChangeEvent valueChangeEvent) {
        ActionEvent a = new ActionEvent(shadowLinkBind);
        a.queue();
        linkActionListener(a);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDateBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceStartDateBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceEndDateBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceWarehouseBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceRequirementAreaBindVar);
    }

    public void setSourceDateBindVar(RichInputDate sourceDateBindVar) {
        this.sourceDateBindVar = sourceDateBindVar;
    }

    public RichInputDate getSourceDateBindVar() {
        return sourceDateBindVar;
    }

    public void setSourceStartDateBindVar(RichInputDate sourceStartDateBindVar) {
        this.sourceStartDateBindVar = sourceStartDateBindVar;
    }

    public RichInputDate getSourceStartDateBindVar() {
        return sourceStartDateBindVar;
    }

    public void setSourceEndDateBindVar(RichInputDate sourceEndDateBindVar) {
        this.sourceEndDateBindVar = sourceEndDateBindVar;
    }

    public RichInputDate getSourceEndDateBindVar() {
        return sourceEndDateBindVar;
    }

    public void setParameterFormBinding(RichPanelFormLayout parameterFormBinding) {
        this.parameterFormBinding = parameterFormBinding;
    }

    public RichPanelFormLayout getParameterFormBinding() {
        return parameterFormBinding;
    }

    public void setParamTableBindVar(RichTable paramTableBindVar) {
        this.paramTableBindVar = paramTableBindVar;
    }

    public RichTable getParamTableBindVar() {
        return paramTableBindVar;
    }

    public void paramSetValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBindVar);
    }

    public void parameterValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBindVar);
    }

    public void parameterValValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBindVar);
    }

    public void addParam1ActionListener(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateWithParams").execute();
    }

    public void addParam2ActionListener(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateWithParams2").execute();
    }

    public void addParam3ActionListener(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateWithParams3").execute();
    }

    public void paramValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            oracle.jbo.domain.Number zero = new oracle.jbo.domain.Number(0);
            Boolean isPerc = isPrecisionValid(5, 2, value);
            if (isPerc == false) {
                FacesMessage message = new FacesMessage("Precision must be like (5,2)!!!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
            if (value.compareTo(0) < 0 || value.compareTo(100) > 0) {
                FacesMessage message = new FacesMessage("Precision must match the percentage i.e. between 0-100!!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "This field is mandatory!!",
                                                          null));
        }
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void paramDescValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            ob = ADFBeanUtils.findOperation("chkDuplicate");
            ob.getParamsMap().put("tab", "overheadParam");
            ob.getParamsMap().put("val", object.toString());
            ob.execute();
            if (ob.getResult() != null && ob.getResult().toString().equalsIgnoreCase("true")) {
                String msg = "Already Attached parameter selected!!!";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "This field is mandatory!!",
                                                          null));
        }
    }

    public void paramSetValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "This field is mandatory!!",
                                                          null));
        }
    }

    /**
     * Function to check precision of Number fields
     */
    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void setBindParamType(RichOutputText bindParamType) {
        this.bindParamType = bindParamType;
    }

    public RichOutputText getBindParamType() {
        return bindParamType;
    }

    public void downTimeParamSetVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(downtimeParamTableBindVar);
    }

    public void endDateVCE(FacesContext facesContext, UIComponent uIComponent, Object object) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(downtimeParamTableBindVar);
    }

    public void EndDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Date d1 = null;
            Date d2 = null;
            Timestamp t1 = null;
            Timestamp t2 = null;
            try {
                t1 = new Timestamp(downtimeStartDateBind.getValue().toString());
                t2 = (Timestamp) object;

                d1 = t1.dateValue();
                d2 = t2.dateValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (t1 != null && t2 != null && d1 != null && d2 != null && t2.compareTo(t1) < 0 &&
                !d1.toString().equals(d2.toString())) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "End Date can not be less than the Start Date!!", null));
            }
        } 
//        else {
//            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "This field is mandatory!!",
//                                                          null));
//        }
    }

    public void remarksValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Entered value is having useless blank spaces!!", null));
            }
        }
    }

    public void downtimeParamSetValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "This field is mandatory!!",
                                                          null));
        }
    }

    public void downTimeParamVCE(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(downtimeParamTableBindVar);
    }

    public void setDowntimeParamTableBindVar(RichTable downtimeParamTableBindVar) {
        this.downtimeParamTableBindVar = downtimeParamTableBindVar;
    }

    public RichTable getDowntimeParamTableBindVar() {
        return downtimeParamTableBindVar;
    }

    public void setCostingParamValBind(RichInputText costingParamValBind) {
        this.costingParamValBind = costingParamValBind;
    }

    public RichInputText getCostingParamValBind() {
        return costingParamValBind;
    }

    public void setJeBasisBind(RichSelectOneChoice jeBasisBind) {
        this.jeBasisBind = jeBasisBind;
    }

    public RichSelectOneChoice getJeBasisBind() {
        return jeBasisBind;
    }

    public void setOutputItemId(RichInputText outputItemId) {
        this.outputItemId = outputItemId;
    }

    public RichInputText getOutputItemId() {
        return outputItemId;
    }

    public void setOutputItemName(RichInputText outputItemName) {
        this.outputItemName = outputItemName;
    }

    public RichInputText getOutputItemName() {
        return outputItemName;
    }

    public void setOutputItemQty(RichInputText outputItemQty) {
        this.outputItemQty = outputItemQty;
    }

    public RichInputText getOutputItemQty() {
        return outputItemQty;
    }

    public void setOutputItemActQty(RichInputText outputItemActQty) {
        this.outputItemActQty = outputItemActQty;
    }

    public RichInputText getOutputItemActQty() {
        return outputItemActQty;
    }

    public void operationsValueChangeListener(ValueChangeEvent valueChangeEvent) {
        Object obJC = ADFBeanUtils.findIterator("MnfJe1Iterator").getCurrentRow().getAttribute("transJcId");
        Object obRC = ADFBeanUtils.findIterator("MnfJe1Iterator").getCurrentRow().getAttribute("transRcId");
        if (jeBasisBind.getValue().equals(109)) {
            if (obJC != null) {
            } else {
                setJrcId(false);
                showFacesMsg("Please select Source Id!! ", " ", FacesMessage.SEVERITY_ERROR, null);
            }
        } else if (jeBasisBind.getValue().equals(110)) {
            if (obRC != null) {
            } else {
                setJrcId(false);
                showFacesMsg("Please select Source Id!! ", " ", FacesMessage.SEVERITY_ERROR, null);
            }
        } else {
            setJrcId(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemId);
            AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemName);
            AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemQty);
            AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemActQty);
        }
    }

    public void setSourceWarehouseBindVar(RichSelectOneChoice sourceWarehouseBindVar) {
        this.sourceWarehouseBindVar = sourceWarehouseBindVar;
    }

    public RichSelectOneChoice getSourceWarehouseBindVar() {
        return sourceWarehouseBindVar;
    }

    public void setSourceRequirementAreaBindVar(RichSelectOneChoice sourceRequirementAreaBindVar) {
        this.sourceRequirementAreaBindVar = sourceRequirementAreaBindVar;
    }

    public RichSelectOneChoice getSourceRequirementAreaBindVar() {
        return sourceRequirementAreaBindVar;
    }

    public void costingParamValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(costingParamTableBindVar);
    }

    public void costingParamNameValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(costingParamTableBindVar);
    }

    public void costingParamSetValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(costingParamTableBindVar);
    }

    public void setCostingParamTableBindVar(RichTable costingParamTableBindVar) {
        this.costingParamTableBindVar = costingParamTableBindVar;
    }

    public RichTable getCostingParamTableBindVar() {
        return costingParamTableBindVar;
    }

    public void itemBracketValidator(FacesContext facesContext, UIComponent uIComponent,
                                     Object object) throws SQLException {
        String string = (String) object;
        if (string.length() > 0) {
            if (object != null) {
                Number val = new Number(object);
                Boolean isValid = isPrecisionValid(10, 0, val);
                if (isValid == false) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Fractional Values are not allowed!!", null));
                }
                if (val.compareTo(0) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Negative numbers were not allowed.", null));
                }
            }
        }
    }

    public void setItemBracketBindVar(RichInputText itemBracketBindVar) {
        this.itemBracketBindVar = itemBracketBindVar;
    }

    public RichInputText getItemBracketBindVar() {
        return itemBracketBindVar;
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void generateBarcodeActionListener(ActionEvent actionEvent) {
        try {
            int val = Integer.parseInt(this.itemBracketBindVar.getValue().toString());
            ob = ADFBeanUtils.findOperation("generateBarcode");
            ob.getParamsMap().put("itmQty", val);
            ob.execute();
        } catch (Exception ex) {
            showFacesMessage("Only Numaric values allowed.", "I", false, "F", null);
        }
    }

    /*----------------------Call WorkFlow Function----------------------*/
    public void callWorkFlow() {
        ob = ADFBeanUtils.findOperation("getWfId");
        ob.execute();
        if (ob.getResult() != null) {
            setWorkflowId((String) ob.getResult());
        }
        ADFBeanUtils.findOperation("callWorkFlow").execute();
    }

    public void actQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value must be greater then Zero.", null));
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Please select Actual Quantity!!", null));
        }

    }

    public void setBarcodeBindVar(RichTable barcodeBindVar) throws SQLException {
        this.barcodeBindVar = barcodeBindVar;
    }

    public RichTable getBarcodeBindVar() {
        return barcodeBindVar;
    }

    public void itemBracketValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeBindVar);
    }

    public void saveAndForwardActionListener(ActionEvent actionEvent) {
    }

    public void jobCardValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select Job Card first!!",
                                                          null));
        }
    }

    public void routeCardValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Please select Route Card first!!", null));
        }
    }

    public void endDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Date d1 = null;
            Date d2 = null;
            Timestamp t1 = null;
            Timestamp t2 = null;
            try {
                t1 = new Timestamp(startDateBind.getValue().toString());
                t2 = (Timestamp) object;

                d1 = t1.dateValue();
                d2 = t2.dateValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (t1 != null && t2 != null && d1 != null && d2 != null && t2.compareTo(t1) < 0 &&
                !d1.toString().equals(d2.toString())) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "End Date can not be less than the Start Date!!", null));
            }
        }
    }

    public void setStartDateBind(RichInputDate startDateBind) {
        this.startDateBind = startDateBind;
    }

    public RichInputDate getStartDateBind() {
        return startDateBind;
    }

    public void operationValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Please select Operation first!!", null));
        }
    }

    public void costingParamSetVaidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select Parameter Set!!",
                                                          null));
        }
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void costingParamValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            ob = ADFBeanUtils.findOperation("chkDuplicate");
            ob.getParamsMap().put("tab", "costingParam");
            ob.getParamsMap().put("val", object.toString());
            ob.execute();
            adfLog.info("Costing Parameter Tab:::"+ob.getResult());
            if (ob.getResult() != null && ob.getResult().toString().equalsIgnoreCase("true")) {
                String msg = "Already attached Parameter Selected!!!";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select Parameter!!",
                                                          null));
        }
    }

    public void costingValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            oracle.jbo.domain.Number zero = new oracle.jbo.domain.Number(0);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision must be like (26,6)!!!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
            if (value.compareTo(0) < 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "This field is mandatory!!",
                                                          null));
        }
    }

    public void downtimeStartDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...







    }

    public void setDowntimeStartDateBind(RichInputDate downtimeStartDateBind) {
        this.downtimeStartDateBind = downtimeStartDateBind;
    }

    public RichInputDate getDowntimeStartDateBind() {
        return downtimeStartDateBind;
    }

    public void showFacesMsg(String msgHdr, String msgDtl, javax.faces.application.FacesMessage.Severity msgSeverity,
                             String clientId) {
        FacesMessage msg = new FacesMessage(msgHdr);
        msg.setDetail(msgDtl);
        msg.setSeverity(msgSeverity);
        FacesContext.getCurrentInstance().addMessage(clientId, msg);
    }

    public void setHeaderFormBind(RichPanelFormLayout headerFormBind) {
        this.headerFormBind = headerFormBind;
    }

    public RichPanelFormLayout getHeaderFormBind() {
        return headerFormBind;
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void populateItemActionListener(ActionEvent actionEvent) {

        setChkPopulateItm("Y");
       
        //chkPopulateItm = "Y";
        Object obJC = ADFBeanUtils.findIterator("MnfJe1Iterator").getCurrentRow().getAttribute("transJcId");
        Object obRC = ADFBeanUtils.findIterator("MnfJe1Iterator").getCurrentRow().getAttribute("transRcId");
        Object obOP = ADFBeanUtils.findIterator("MnfJe1Iterator").getCurrentRow().getAttribute("transOperationDesc");
        if (jeBasisBind.getValue().equals(109)) {
            if (obJC != null) {
                ob = ADFBeanUtils.findOperation("jeSrcInsertion");
                ob.getParamsMap().put("basis", 109);
                ob.getParamsMap().put("docId", obJC.toString());
                ob.execute();
                if (obOP != null) {
                    setJrcId(true);
                    ADFBeanUtils.findOperation("insertOpItm").execute();
                   
                } else {
                    setOpId(false);
                    showFacesMsg("Please select Operation!! ", " ", FacesMessage.SEVERITY_ERROR, null);
                }
            } else {
                setJrcId(false);
                showFacesMsg("Please select Source Id!! ", " ", FacesMessage.SEVERITY_ERROR, null);
            }
        }
        if (jeBasisBind.getValue().equals(110)) {
            setAttrsVal("MnfJe1Iterator", "OutptItmId", (String) getAttrsVal("MnfJe1Iterator", "TransOutputItm"));
            setAttrsVal("MnfJe1Iterator", "OutptItmQty", getAttrsVal("MnfJe1Iterator", "TransOutputQty"));
            if (obRC != null) {
                //                if (obOP != null) {
                ob = ADFBeanUtils.findOperation("jeSrcInsertion");
                ob.getParamsMap().put("basis", 110);
                ob.getParamsMap().put("docId", obRC.toString());
                ob.execute();
                setJrcId(true);
                ADFBeanUtils.findOperation("insertOpItm").execute();
                
                //                } else {
                //                    setOpId(false);
                //                    showFacesMsg("Please select Operation!! ", " ", FacesMessage.SEVERITY_ERROR, null);
                //                }
            } else {
                setJrcId(false);
                showFacesMsg("Please select Source Id!! ", " ", FacesMessage.SEVERITY_ERROR, null);
            }
        }
        
        Integer chkcc = Integer.parseInt(resolvEl("#{pageFlowScope.CHECK_CC}").toString());
        if(chkcc!=null && chkcc.compareTo(new Integer(0))>=0)
        ADFBeanUtils.findOperation("updateCostCenterAmt").execute();
    }

    public void downtimeDeleteActionListener(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void selectLotBinActionListener(ActionEvent actionEvent) {
        Number stock = (Number) bindStockAvailable.getValue();
        Number avail = (Number) bindActualItemQuantity.getValue();
        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {
            ADFBeanUtils.findOperation("FilterBinWise").execute();
            showPopup(selectLotBinPopUpBind, true);
        }
    }

    public void selectLotActionListener(ActionEvent actionEvent) {
        Number stock = (Number) bindStockAvailable.getValue();
        Number avail = (Number) bindActualItemQuantity.getValue();
        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {
            ADFBeanUtils.findOperation("FilterLotWise").execute();
            showPopup(selectLotPopUpBind, true);
        }
    }

    public void selectItemActionListener(ActionEvent actionEvent) {
        Number stock = (Number) bindStockAvailable.getValue();
        Number avail = (Number) bindActualItemQuantity.getValue();
        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {
            ADFBeanUtils.findOperation("FilterSrNo").execute();
            showPopup(selectSrNoPopUpBind, true);
        }
    }

    public void AutoIssueActionListener(ActionEvent actionEvent) {
        Number stock = (Number) bindStockAvailable.getValue();
        Number avail = (Number) bindActualItemQuantity.getValue();
        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {
            ADFBeanUtils.findOperation("AutoIssueItem").execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotSwitcher);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanelBox);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTbl);
    }

    public void setSelectSrNoPopUpBind(RichPopup selectSrNoPopUpBind) {
        this.selectSrNoPopUpBind = selectSrNoPopUpBind;
    }

    public RichPopup getSelectSrNoPopUpBind() {
        return selectSrNoPopUpBind;
    }

    public void setSelectLotPopUpBind(RichPopup selectLotPopUpBind) {
        this.selectLotPopUpBind = selectLotPopUpBind;
    }

    public RichPopup getSelectLotPopUpBind() {
        return selectLotPopUpBind;
    }

    public void setSelectLotBinPopUpBind(RichPopup selectLotBinPopUpBind) {
        this.selectLotBinPopUpBind = selectLotBinPopUpBind;
    }

    public RichPopup getSelectLotBinPopUpBind() {
        return selectLotBinPopUpBind;
    }

    public void setBindStockAvailable(RichOutputText bindStockAvailable) {
        this.bindStockAvailable = bindStockAvailable;
    }

    public RichOutputText getBindStockAvailable() {
        return bindStockAvailable;
    }

    public void setBindActualItemQuantity(RichInputText bindActualItemQuantity) {
        this.bindActualItemQuantity = bindActualItemQuantity;
    }

    public RichInputText getBindActualItemQuantity() {
        return bindActualItemQuantity;
    }

    /**
     * Method to open a popup using binding.
     */
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

    public void searchItemActionListener(ActionEvent actionEvent) {
        String itmName =
            getAttrsVal("DualItemNameSearchView1Iterator", "transItemId") != null ?
            getAttrsVal("DualItemNameSearchView1Iterator", "transItemId").toString() : "";
        if (itmName != null) {
            setSearchValue(itmName);
            DCIteratorBinding it = ADFBeanUtils.findIterator("MnfJeOpItm1Iterator");
            RowSetIterator rsi = it.getRowSetIterator();
            RowKeySet oldSelection = inputItmTableBind.getSelectedRowKeys();

            if (rsi.first() != null) {
                Row r = rsi.first();
                while (rsi.hasNext() && getKey() == null && (!matchFoundInput(r, oldSelection))) {
                    r = rsi.next();
                }

            }
        }
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    private boolean matchFoundInput(Row r, RowKeySet oldSelection) {
        setKey(null);
        ArrayList list = new ArrayList(1);
        RowKeySetImpl newSelection = new RowKeySetImpl();
        Key key = null;
        String rowValue = r.getAttribute("ItmId").toString();
        if (rowValue.toString().contains(searchValue)) {
            key = r.getKey();
            list.add(key);
            inputItmTableBind.setActiveRowKey(list);
            newSelection.add(list);
            makeCurrentInput(inputItmTableBind, newSelection, oldSelection);
            return true;
        } else {

        }
        return false;

    }

    private void makeCurrentInput(RichTable inputItmTableBind, RowKeySet newCurrentRow, RowKeySet oldCurrentRow) {
        SelectionEvent selectionEvent = new SelectionEvent(oldCurrentRow, newCurrentRow, inputItmTableBind);
        selectionEvent.queue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBind);
    }

    public void setInputItmTableBind(RichTable inputItmTableBind) {
        this.inputItmTableBind = inputItmTableBind;
    }

    public RichTable getInputItmTableBind() {
        return inputItmTableBind;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public void setOperationOutputFormBind(RichPanelFormLayout operationOutputFormBind) {
        this.operationOutputFormBind = operationOutputFormBind;
    }

    public RichPanelFormLayout getOperationOutputFormBind() {
        return operationOutputFormBind;
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void downtimeParamValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String value = (String) object;
            System.out.println("Value is : " + value);
            int flag = 1;
            if (value != null) {
                System.out.println("If");
                OperationBinding binding =
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("chkNmDuplicate");
                binding.getParamsMap().put("val", value);
                binding.execute();
                flag = (Integer) binding.getResult();
                System.out.println("In Bean" + flag);
                if (flag == 1) {
                    showFacesMessage("Duplicate Value.", "E", false, "V", null);
                }
            }
        } else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }
        //        if (object != null) {
        //            ob = ADFBeanUtils.findOperation("chkDuplicate");
        //            ob.getParamsMap().put("tab", "downTimeParam");
        //            ob.getParamsMap().put("val", object.toString());
        //            ob.execute();
        //            if (ob.getResult() != null && ob.getResult().toString().equalsIgnoreCase("true")) {
        //                System.out.println("before throwing error");
        //                String msg = "Already attached Parameter Selected!!";
        //                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        //            }
        //        } else {
        //            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mandatory Field", null));
        //        }
    }

    public void srViewDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJeOpItm1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();

            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalIssueQtySr");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                System.out.println("Issue Qty : " + issQty);
                if (bindActQty.getValue() != null) {
                    pickQty = (Number) bindActQty.getValue();
                    System.out.println("Pick Qty : " + pickQty);
                }

                if (issQty.compareTo(0) == 0) {
                    JSFUtils.addFacesErrorMessage("Please select quantity to issue");
                } else if (issQty.compareTo(pickQty) != 0) {
                    JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
                } else {
                    ADFBeanUtils.findOperation("InsertIntoPickItmSr").execute();
                    //ADFBeanUtils.findOperation("lotExecute").execute();
                    //ADFBeanUtils.findOperation("binExecute").execute();
                    //ADFBeanUtils.findOperation("srExecute").execute();
                    //ADFBeanUtils.findOperation("itmExecute").execute();

                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotSwitcher);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanelBox);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTbl);
    }

    public void setBindActQty(RichOutputText bindActQty) {
        this.bindActQty = bindActQty;
    }

    public RichOutputText getBindActQty() {
        return bindActQty;
    }

    public void lotVwDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJeOpItm1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalIssueQtyLot");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                if (binditmQtyIssue.getValue() != null) {
                    pickQty = (Number) binditmQtyIssue.getValue();
                }
                if (issQty.compareTo(0) == 0) {
                    JSFUtils.addFacesErrorMessage("Please select quantity to issue");
                } else if (issQty.compareTo(pickQty) != 0) {
                    JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
                } else {
                    System.out.println("Before Insert : ============================");
                    ADFBeanUtils.findOperation("InsertIntoJeItmLot").execute();

                    //ADFBeanUtils.findOperation("lotExecute").execute();
                    //ADFBeanUtils.findOperation("binExecute").execute();
                    //ADFBeanUtils.findOperation("srExecute").execute();
                    //ADFBeanUtils.findOperation("itmExecute").execute();

                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                    System.out.println("End of lotVwDialogListener Function : ====");
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotSwitcher);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanelBox);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTbl);
    }

    public void issueQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number issueQty = (Number) object;
            Number lotQty = new Number(0);
            if (bindTotalStock.getValue() != null) {
                lotQty = (Number) bindTotalStock.getValue();
                if (issueQty.compareTo(lotQty) > 0) {
                    throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                  "Issue Quantity can not be more than available quantity"));
                }
            }
            if (issueQty.compareTo(0) == -1) {
                throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                              "Issue Quantity can not be negative"));
            }
        }
    }

    public void setBindTotalStock(RichOutputText bindTotalStock) {
        this.bindTotalStock = bindTotalStock;
    }

    public RichOutputText getBindTotalStock() {
        return bindTotalStock;
    }

    public void setBinditmQtyIssue(RichOutputText binditmQtyIssue) {
        this.binditmQtyIssue = binditmQtyIssue;
    }

    public RichOutputText getBinditmQtyIssue() {
        return binditmQtyIssue;
    }

    /**
     * Method to set value for an attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * @param for attribute value
     * **/
    public void setAttrsVal(String iter, String attrs, Object val) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                dcIter.getCurrentRow().setAttribute(attrs, val);

        }
    }

    /**
     * Method to get value of attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * **/
    public Object getAttrsVal(String iter, String attrs) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }

    public void setOperationLOVBind(RichInputListOfValues operationLOVBind) {
        this.operationLOVBind = operationLOVBind;
    }

    public RichInputListOfValues getOperationLOVBind() {
        return operationLOVBind;
    }

    public void setOperationPanelboxBind(RichPanelBox operationPanelboxBind) {
        this.operationPanelboxBind = operationPanelboxBind;
    }

    public RichPanelBox getOperationPanelboxBind() {
        return operationPanelboxBind;
    }

    public void linkActionListener(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("setOperationValues").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(operationLOVBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemId);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemName);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemQty);
    }

    public void setShadowLinkBind(RichLink shadowLinkBind) {
        this.shadowLinkBind = shadowLinkBind;
    }

    public RichLink getShadowLinkBind() {
        return shadowLinkBind;
    }

    public void costingRowDeleteActionListener(ActionEvent actionEvent) {
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("cosDelKey");
        RowSetIterator rSetIter = ADFBeanUtils.findIterator("MNFJeCostingParam1Iterator").getRowSetIterator();
        rSetIter.setCurrentRow(rSetIter.getRow(key));
        rSetIter.removeCurrentRow();
    }

    public void downtimeRowDeleteActionListener(ActionEvent actionEvent) {
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("downDelKey");
        RowSetIterator rSetIter = ADFBeanUtils.findIterator("MNFJeDowntimeParam1Iterator").getRowSetIterator();
        rSetIter.setCurrentRow(rSetIter.getRow(key));
        rSetIter.removeCurrentRow();
    }

    public void overheadRowDeleteActionListener(ActionEvent actionEvent) {
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("delKey");
        RowSetIterator rSetIter = ADFBeanUtils.findIterator("MnfJeParam1Iterator").getRowSetIterator();
        rSetIter.setCurrentRow(rSetIter.getRow(key));
        rSetIter.removeCurrentRow();
    }

    /*------------------------------Add Lot For Output Item----------------------------*/
    public void AddOutputItemLotActionListener(ActionEvent actionEvent) throws SQLException {
        DCIteratorBinding iter = ADFBeanUtils.findIterator("MNFJeItmStkLot1Iterator");
        Row[] arIR = iter.getAllRowsInRange();
        Integer Ri = (Integer) arIR.length;
        if (Ri == 0) {
            ob = ADFBeanUtils.findOperation("GetlotNo");
            ob.execute();
            String lotNo = (String) ob.getResult();
            this.setLotNo(lotNo);
            Number qty = (Number) this.outputItemActQty.getValue();
            Integer qtyInInt = qty.floor().intValue();
            this.setLotQty(qtyInInt.toString());
//            OperationBinding op1=ADFBeanUtils.findOperation("createStkItmRow");
//            op1.execute();
            showPopup(bindAddOutputItmLotPopUp, true);
            ADFBeanUtils.findOperation("ExecuteLot").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSerialFormBind);
        }

    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void AddOutputItemSerialActionListener(ActionEvent actionEvent) {
        if (serialNoBindVar.getValue() == null || serialNoBindVar.getValue().toString().equals("")) {
            Add_Faces_Message_RichInputText(serialNoBindVar, "Please Enter Serial Number.");
        } else {
            DCIteratorBinding iter = ADFBeanUtils.findIterator("MNFJeItmStkSerial1Iterator");
            Row[] arIR = iter.getAllRowsInRange();
            int val = arIR.length;
            Number Ri = new Number(val);
            Number Qtyi = (Number) ((Number) this.outputItemActQty.getValue()).round(2);
            if (Ri.compareTo(Qtyi) == 0) {
                StringBuilder message = new StringBuilder();
                message.append("<P>No. of Serial No Exceeds the Quantity.</P><BR>");
                ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_INFO);
            } else {
                System.out.println("Add Action Start");
                ob = ADFBeanUtils.findOperation("AddSerialEntry");
                ob.getParamsMap().put("lotId", this.lotIdInSerial.getValue().toString());
                ob.getParamsMap().put("serial", this.serialNoBindVar.getValue().toString());
                ob.execute();
                //ADFBeanUtils.findOperation("ExecuteSerial").execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(serialTableBind);
                serialNoBindVar.setSubmittedValue(null);
                serialNoBindVar.resetValue();
            }
        }
    }

    public void Add_Faces_Message_RichInputText(RichInputText S2, String M2) {
        FacesMessage Message = new FacesMessage(M2);
        Message.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(S2.getClientId(), Message);
    }

    public void setSerialNoBindVar(RichInputText serialNoBindVar) {
        this.serialNoBindVar = serialNoBindVar;
    }

    public RichInputText getSerialNoBindVar() {
        return serialNoBindVar;
    }

    public void setLotIdInSerial(RichInputText lotIdInSerial) {
        this.lotIdInSerial = lotIdInSerial;
    }

    public RichInputText getLotIdInSerial() {
        return lotIdInSerial;
    }

    public void setBindAddOutputItmLotPopUp(RichPopup bindAddOutputItmLotPopUp) {
        this.bindAddOutputItmLotPopUp = bindAddOutputItmLotPopUp;
    }

    public RichPopup getBindAddOutputItmLotPopUp() {
        return bindAddOutputItmLotPopUp;
    }

   // @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void addOutputItmLotActionListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            ob = ADFBeanUtils.findOperation("AddLotEntry");
            ob.getParamsMap().put("lotId", this.bindLotId.getValue().toString());
            ob.getParamsMap().put("lotQty",
                                  (this.bindLotQty.getValue() == null ? 0 : this.bindLotQty.getValue().toString()));
            ob.getParamsMap().put("expdt",
                                  (this.expirydateBind.getValue() == null ? null : this.expirydateBind.getValue()));
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotIdInSerial);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindSrNoBtn);
            AdfFacesContext.getCurrentInstance().addPartialTarget(serialNoBindVar);
        }
    }

    public void setLotTableBind(RichTable lotTableBind) {
        this.lotTableBind = lotTableBind;
    }

    public RichTable getLotTableBind() {
        return lotTableBind;
    }

    public void setBindLotId(RichInputText bindLotId) {
        this.bindLotId = bindLotId;
    }

    public RichInputText getBindLotId() {
        return bindLotId;
    }

    public void setBindLotQty(RichInputText bindLotQty) {
        this.bindLotQty = bindLotQty;
    }

    public RichInputText getBindLotQty() {
        return bindLotQty;
    }

    public void setSerialTableBind(RichTable serialTableBind) {
        this.serialTableBind = serialTableBind;
    }

    public RichTable getSerialTableBind() {
        return serialTableBind;
    }

    public boolean ReleaseJobCard() throws SQLException {
        DCIteratorBinding lotIters = ADFBeanUtils.findIterator("MnfJeOpItmLot1Iterator");
        DCIteratorBinding binIters = ADFBeanUtils.findIterator("MnfJeOpItmBin1Iterator");
        DCIteratorBinding srIters = ADFBeanUtils.findIterator("MnfJeOpItmSr1Iterator");
        Row[] lotRows = lotIters.getAllRowsInRange();
        Row[] binRows = binIters.getAllRowsInRange();
        Row[] srRows = srIters.getAllRowsInRange();
        Integer countLot = (Integer) lotRows.length;
        Integer countbin = (Integer) binRows.length;
        Integer countSr = (Integer) srRows.length;
        if (countLot == 0 && countbin == 0 && countSr == 0) {
            String msg2 = "<html><body> <p>Please Enter Lot/Bin/Serial details for Input Items..!!</p></body></html>";
            setDisclosedFirstTab(bindOperationItemTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindOperationItemTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
            showFacesMsg(msg2, msg2, FacesMessage.SEVERITY_ERROR, null);
            return false;
        }
        DCIteratorBinding iters = ADFBeanUtils.findIterator("MNFJeItmStkLot1Iterator");
        Row[] arIRs = iters.getAllRowsInRange();
        Integer Ris = (Integer) arIRs.length;
        if (Ris == 0) {
            String msg2 = "<html><body> <p>Please Enter Lot details for Output Item..!!</p></body></html>";
            setDisclosedFirstTab(bindOutputStockTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindOutputStockTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
            showFacesMsg(msg2, msg2, FacesMessage.SEVERITY_ERROR, null);
            return false;
        } else {
            String Schk = (String) getItmSerialized();
            if (Schk.equals("Y")) {
                DCIteratorBinding iter = ADFBeanUtils.findIterator("MNFJeItmStkSerial1Iterator");
                Row[] arIR = iter.getAllRowsInRange();
                Integer Ri = (Integer) arIR.length;
                Number qty = (Number) this.outputItemActQty.getValue();
                Integer Qtyi = qty.floor().intValue(); //Integer.parseInt(this.outputItemActQty.getValue().toString());
                if (Ri == Qtyi) {
                    setReleaseFlag(true);
                    return true;
                } else {
                    String msg2 = "<html><body> <p>Item is Serialized. Please Enter Serial's.</p></body></html>";
                    setDisclosedFirstTab(bindOutputStockTab);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindOutputStockTab);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                    showFacesMsg(msg2, msg2, FacesMessage.SEVERITY_ERROR, null);
                    return false;
                }
            } else {
                setReleaseFlag(true);
                return true;
            }
        }
    }

    public void setBindOutputStockTab(RichShowDetailItem bindOutputStockTab) {
        this.bindOutputStockTab = bindOutputStockTab;
    }

    public RichShowDetailItem getBindOutputStockTab() {
        return bindOutputStockTab;
    }

    /*--------------------------Method for disclosed panel tab--------------------*/
    public void setDisclosedFirstTab(RichShowDetailItem tabBind) {
        RichPanelTabbed richPanelTabbed = getPanelTabBinding();
        for (UIComponent child : richPanelTabbed.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem) child;
            if (sdi.getClientId().equals(tabBind.getClientId())) {
                sdi.setDisclosed(true);
            } else {
                sdi.setDisclosed(false);
            }
        }
    }

    public void setPanelTabBinding(RichPanelTabbed panelTabBinding) {
        this.panelTabBinding = panelTabBinding;
    }

    public RichPanelTabbed getPanelTabBinding() {
        return panelTabBinding;
    }

    public String saveAndForwardAction() throws SQLException {

        Object obJC = ADFBeanUtils.findIterator("MnfJe1Iterator").getCurrentRow().getAttribute("transJcId");
        Object obRC = ADFBeanUtils.findIterator("MnfJe1Iterator").getCurrentRow().getAttribute("transRcId");
        Object obOP = ADFBeanUtils.findIterator("MnfJe1Iterator").getCurrentRow().getAttribute("transOperationDesc");
        if (jeBasisBind.getValue().equals(109)) {
            if (obJC != null) {
                if (obOP != null) {
                    callWorkFlow();
                    this.setJeMode("V");
                    ADFBeanUtils.findOperation("Commit").execute();
                    return "workflow";
                } else {
                    setOpId(false);
                    showFacesMsg("Please select Operation!! ", " ", FacesMessage.SEVERITY_ERROR, null);
                }
            } else {
                setJrcId(false);
                showFacesMsg("Please select Source Id!! ", " ", FacesMessage.SEVERITY_ERROR, null);
            }
        } else if (jeBasisBind.getValue().equals(110)) {
            if (obRC != null) {
                if (obOP != null) {
                    callWorkFlow();
                    this.setJeMode("V");
                    ADFBeanUtils.findOperation("Commit").execute();
                    return "workflow";
                } else {
                    setOpId(false);
                    showFacesMsg("Please select Operation!! ", " ", FacesMessage.SEVERITY_ERROR, null);
                }
            } else {
                setJrcId(false);
                showFacesMsg("Please select Source Id!! ", " ", FacesMessage.SEVERITY_ERROR, null);
            }
        } else if ("N".equals(dataExists("MnfJeOpItm1Iterator"))) {
            showFacesMsg("Items Required ", "Job Execution cannot be saved without any item!!. ",
                         FacesMessage.SEVERITY_ERROR, null);
        } else {
        }
        return null;
    }

    public void setBindItemLotId(RichOutputText bindItemLotId) {
        this.bindItemLotId = bindItemLotId;
    }

    public RichOutputText getBindItemLotId() {
        return bindItemLotId;
    }

    public void setItemSerialFormBind(RichPanelFormLayout itemSerialFormBind) {
        this.itemSerialFormBind = itemSerialFormBind;
    }

    public RichPanelFormLayout getItemSerialFormBind() {
        return itemSerialFormBind;
    }

    public void srNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*         if (object != null) {
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator("MNFJeItmStkSerial1Iterator");
            ViewObject vo = dcIter.getViewObject();
            RowSetIterator rSetIter = vo.createRowSetIterator(null);
            Row r = null;
            Integer countVal = 0;
            while (rSetIter.hasNext()) {
                r = rSetIter.next();
                if (object.equals(r.getAttribute("SrNo"))) {
                    countVal = countVal + 1;
                }
            }
            rSetIter.closeRowSetIterator();
            Row currentRow = vo.getCurrentRow();
            if(currentRow!=null){
                if (object.equals(currentRow.getAttribute("SrNo"))) {
                    countVal = countVal - 1;
                }
            }
            if (countVal == 1){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Serial entered!!!", null));
            }
            else {}
        }
        else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "This field is mandatory!!", null));
        } */
        String srNo = (String) object;
        if (srNo.length() > 0) {
            ob = ADFBeanUtils.findOperation("chkSrDuplicate");
            ob.getParamsMap().put("val", object.toString());
            ob.execute();
            int i = (Integer) ob.getResult();
            System.out.println("Return Value :  " + i);
            if (i == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Serial Name already exist.",
                                                              null));
            }
        }
    }

    public void setBindAddLotBtn(RichButton bindAddLotBtn) {
        this.bindAddLotBtn = bindAddLotBtn;
    }

    public RichButton getBindAddLotBtn() {
        return bindAddLotBtn;
    }

    public void setBindSrNoBtn(RichButton bindSrNoBtn) {
        this.bindSrNoBtn = bindSrNoBtn;
    }

    public RichButton getBindSrNoBtn() {
        return bindSrNoBtn;
    }

    public void selectLotBinActionListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJeOpItm1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();

            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalIssueQtyBin");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                if (bindIssueActQty.getValue() != null) {
                    pickQty = (Number) bindIssueActQty.getValue();
                }
                if (issQty.compareTo(0) == 0) {
                    JSFUtils.addFacesErrorMessage("Please select quantity to issue");
                } else if (issQty.compareTo(pickQty) != 0) {
                    JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
                } else {
                    ADFBeanUtils.findOperation("InsertIntoJeItmLotBin").execute();
                    //ADFBeanUtils.findOperation("lotExecute").execute();
                    //ADFBeanUtils.findOperation("binExecute").execute();
                    //ADFBeanUtils.findOperation("srExecute").execute();
                    //ADFBeanUtils.findOperation("itmExecute").execute();

                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotSwitcher);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanelBox);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTbl);
    }

    public void setBindIssueActQty(RichOutputText bindIssueActQty) {
        this.bindIssueActQty = bindIssueActQty;
    }

    public RichOutputText getBindIssueActQty() {
        return bindIssueActQty;
    }

    public void setViewLotBind(RichCommandNavigationItem viewLotBind) {
        this.viewLotBind = viewLotBind;
    }

    public RichCommandNavigationItem getViewLotBind() {
        return viewLotBind;
    }

    public void setViewBinBind(RichCommandNavigationItem viewBinBind) {
        this.viewBinBind = viewBinBind;
    }

    public RichCommandNavigationItem getViewBinBind() {
        return viewBinBind;
    }

    public void setViewSerialBind(RichCommandNavigationItem viewSerialBind) {
        this.viewSerialBind = viewSerialBind;
    }

    public RichCommandNavigationItem getViewSerialBind() {
        return viewSerialBind;
    }

    public void viewSerialActionListener(ActionEvent actionEvent) {
        lotSwitcher.setFacetName("SrNo");
        //this.setFacetName("SrNo");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(srTbl);
        //ADFBeanUtils.findOperation("srExecute").execute();
    }

    public void viewBinActionListener(ActionEvent actionEvent) {
        lotSwitcher.setFacetName("Bin");
        //this.setFacetName("Bin");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        //ADFBeanUtils.findOperation("binExecute").execute();
    }

    public void viewLotActionListener(ActionEvent actionEvent) {
        lotSwitcher.setFacetName("Lot");
        //this.setFacetName("Lot");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        //ADFBeanUtils.findOperation("lotExecute").execute();
    }

    public void releaseActionListener(ActionEvent actionEvent) throws SQLException {
        this.setJeMode("R");
        boolean flag = ReleaseJobCard();
    }

    public void setBindOperationItemTab(RichShowDetailItem bindOperationItemTab) {
        this.bindOperationItemTab = bindOperationItemTab;
    }

    public RichShowDetailItem getBindOperationItemTab() {
        return bindOperationItemTab;
    }

    public void setBindItmIssueType(RichOutputText bindItmIssueType) {
        this.bindItmIssueType = bindItmIssueType;
    }

    public RichOutputText getBindItmIssueType() {
        return bindItmIssueType;
    }

    public void autoIssueManualActionListener(ActionEvent actionEvent) {
        Number stock = (Number) bindStockAvailAtShopFloor.getValue();
        Number avail = (Number) bindActualItemQuantity.getValue();
        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock in Shop Floor.");
        } else {
            ADFBeanUtils.findOperation("AutoIssueItem").execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotSwitcher);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanelBox);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTbl);
    }

    public void selectItemManualActionListener(ActionEvent actionEvent) {
        Number stock = (Number) bindStockAvailAtShopFloor.getValue();
        Number avail = (Number) bindActualItemQuantity.getValue();
        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock in Shop Floor.");
        } else {
            ADFBeanUtils.findOperation("FilterSrNoManually").execute();
            showPopup(selectItemManualPopUpBind, true);
        }
    }

    public void selectLotManualActionListener(ActionEvent actionEvent) {
        System.out.println("Inner selectLotManualActionListener");
        Number stock = (Number) bindStockAvailAtShopFloor.getValue();
        Number avail = (Number) bindActualItemQuantity.getValue();
        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock in Shop Floor.");
        } else {
            ADFBeanUtils.findOperation("FilterLotWiseManually").execute();
            showPopup(bindSelectLotManualPopUp, true);
        }
        ADFBeanUtils.findOperation("lotExecute").execute();
    }

    public void selectLotBinManualActionListener(ActionEvent actionEvent) {
        Number stock = (Number) bindStockAvailAtShopFloor.getValue();
        Number avail = (Number) bindActualItemQuantity.getValue();
        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock in Shop Floor.");
        } else {
            ADFBeanUtils.findOperation("FilterBinWiseManually").execute();
            showPopup(bindSelectLotBinManualPopUp, true);
        }
    }

    public void selectItemManuallyDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJeOpItm1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();

            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalManualIssueQtySr");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                System.out.println("Issue Qty : " + issQty);
                if (bindActQty.getValue() != null) {
                    pickQty = (Number) bindActQty.getValue();
                    System.out.println("Pick Qty : " + pickQty);
                }

                if (issQty.compareTo(0) == 0) {
                    JSFUtils.addFacesErrorMessage("Please select quantity to issue");
                } else if (issQty.compareTo(pickQty) != 0) {
                    JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
                } else {
                    ADFBeanUtils.findOperation("InsertIntoPickItmSrFromRqmtVw").execute();

                    //ADFBeanUtils.findOperation("lotExecute").execute();
                    //ADFBeanUtils.findOperation("binExecute").execute();
                    //ADFBeanUtils.findOperation("srExecute").execute();
                    //ADFBeanUtils.findOperation("itmExecute").execute();

                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotSwitcher);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanelBox);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTbl);
    }

    public void setBindStockAvailAtShopFloor(RichOutputText bindStockAvailAtShopFloor) {
        this.bindStockAvailAtShopFloor = bindStockAvailAtShopFloor;
    }

    public RichOutputText getBindStockAvailAtShopFloor() {
        return bindStockAvailAtShopFloor;
    }

    public void setSelectItemManualPopUpBind(RichPopup selectItemManualPopUpBind) {
        this.selectItemManualPopUpBind = selectItemManualPopUpBind;
    }

    public RichPopup getSelectItemManualPopUpBind() {
        return selectItemManualPopUpBind;
    }

    public void selectLotManualDialogListener(DialogEvent dialogEvent) {
        System.out.println("Inner selectLotManualDialogListener");
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJeOpItm1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            System.out.println("Key Value : " + parentKey);
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalManualIssueQtyLot");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                if (binditmQtyIssue.getValue() != null) {
                    pickQty = (Number) binditmQtyIssue.getValue();
                }
                if (issQty.compareTo(0) == 0) {
                    JSFUtils.addFacesErrorMessage("Please select quantity to issue");
                } else if (issQty.compareTo(pickQty) != 0) {
                    JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
                } else {
                    ADFBeanUtils.findOperation("InsertIntoJeItmLotFromRqmtView").execute();
                    //ADFBeanUtils.findOperation("itmExecute").execute();
                    // ADFBeanUtils.findOperation("lotExecute").execute();
                    // ADFBeanUtils.findOperation("binExecute").execute();
                    // ADFBeanUtils.findOperation("srExecute").execute();


                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                    System.out.println("End selectLotManualDialogListener");
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotSwitcher);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanelBox);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTbl);
    }

    public void setBindSelectLotManualPopUp(RichPopup bindSelectLotManualPopUp) {
        this.bindSelectLotManualPopUp = bindSelectLotManualPopUp;
    }

    public RichPopup getBindSelectLotManualPopUp() {
        return bindSelectLotManualPopUp;
    }

    public void selectLotBinManualActionListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJeOpItm1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();

            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalManualIssueQtyBin");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                if (bindIssueActQty.getValue() != null) {
                    pickQty = (Number) bindIssueActQty.getValue();
                }
                if (issQty.compareTo(0) == 0) {
                    JSFUtils.addFacesErrorMessage("Please select quantity to issue");
                } else if (issQty.compareTo(pickQty) != 0) {
                    JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
                } else {
                    ADFBeanUtils.findOperation("InsertIntoJeItmLotBinFromRqmtVw").execute();
                    //ADFBeanUtils.findOperation("lotExecute").execute();
                    //ADFBeanUtils.findOperation("binExecute").execute();
                    //ADFBeanUtils.findOperation("srExecute").execute();
                    //ADFBeanUtils.findOperation("itmExecute").execute();

                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotSwitcher);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanelBox);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTbl);
    }

    public void setBindSelectLotBinManualPopUp(RichPopup bindSelectLotBinManualPopUp) {
        this.bindSelectLotBinManualPopUp = bindSelectLotBinManualPopUp;
    }

    public RichPopup getBindSelectLotBinManualPopUp() {
        return bindSelectLotBinManualPopUp;
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
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
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

    public void setParamNm(RichInputListOfValues paramNm) {
        this.paramNm = paramNm;
    }

    public RichInputListOfValues getParamNm() {
        return paramNm;
    }

    public void setLotTbl(RichTable lotTbl) {
        this.lotTbl = lotTbl;
    }

    public RichTable getLotTbl() {
        return lotTbl;
    }

    public void setLotPanelBox(RichPanelBox lotPanelBox) {
        this.lotPanelBox = lotPanelBox;
    }

    public RichPanelBox getLotPanelBox() {
        return lotPanelBox;
    }

    public void setLotSwitcher(UIXSwitcher lotSwitcher) {
        this.lotSwitcher = lotSwitcher;
    }

    public UIXSwitcher getLotSwitcher() {
        return lotSwitcher;
    }

    public void setStoreTrfRB(RichSelectOneRadio storeTrfRB) {
        this.storeTrfRB = storeTrfRB;
    }

    public RichSelectOneRadio getStoreTrfRB() {
        return storeTrfRB;
    }

    public void setStocktrfAction(DisclosureEvent disclosureEvent) {
        adfLog.info("value of modeL::"+resolvEl("#{pageFlowScope.JobCardExecutionMode}").toString());
        if(resolvEl("#{pageFlowScope.JobCardExecutionMode}").toString().equals("C")){
            adfLog.info("in the iff loop");
        storeTrfRB.setValue("MOVE TO STORE");
        }
        if(jeMode.equals("V")){
        ADFBeanUtils.findOperation("filterCurrentR").execute();
        String output = CheckLotEntry_Func("CheckforInputItmLot");
        if (output.equals("checked")) {
            //        if(storeTrfRB.getValue()==null){
            //        storeTrfRB.setValue("MOVE TO STORE");
            //        }
            if (radioBtnVal.getValue() == null) {
                System.out.println("In Null");
                storeTrfRB.setValue("MOVE TO STORE");
            } else {
                if (radioBtnVal.getValue() == 122){
                    System.out.println("In 122");
                    storeTrfRB.setValue("MOVE TO NEXT PROCESS");
                }else{
                    System.out.println("In 123");
                    storeTrfRB.setValue("MOVE TO STORE");
                }
            }
        } else {
            StringBuilder message = new StringBuilder();
            message.append("<P>Please Fill All the lot Quantity for :</P><BR>");
            message.append(output);
            ADFModelUtils.showFormattedFacesMessage("Job Card Execution", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
            setDisclosedFirstTab(bindOperationItemTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindOperationItemTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
              }
        }
    }

    public void setRadioBtnVal(RichSelectOneChoice radioBtnVal) {
        this.radioBtnVal = radioBtnVal;
    }

    public RichSelectOneChoice getRadioBtnVal() {
        return radioBtnVal;
    }

    public void setBinTbl(RichTable binTbl) {
        this.binTbl = binTbl;
    }

    public RichTable getBinTbl() {
        return binTbl;
    }

    public void setSrTbl(RichTable srTbl) {
        this.srTbl = srTbl;
    }

    public RichTable getSrTbl() {
        return srTbl;
    }

    public String CheckLotEntry_Func(String type) {
        ob = ADFBeanUtils.findOperation(type);
        ob.execute();
        Object oj = ob.getResult();
        System.out.println("Valid or Not : " + oj.toString());
        return oj.toString();
    }

    public void createQcParamAction(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateWithParamsQC").execute();
    }

    public void setPendingQty(RichOutputText pendingQty) {
        this.pendingQty = pendingQty;
    }

    public RichOutputText getPendingQty() {
        return pendingQty;
    }

    public String headCostCenterAction() {
        OperationBinding binding =  ADFBeanUtils.findOperation("chkCcApplicableOrNot");
                binding.execute();
                if (binding.getResult() != null && (Boolean) binding.getResult()) {
                    return "headCc";
                } else {
                    return null;
                }
    }

    public void setExpirydateBind(RichInputDate expirydateBind) {
        this.expirydateBind = expirydateBind;
    }

    public RichInputDate getExpirydateBind() {
        return expirydateBind;
    }
    public String serializedQuantityValidation()
    {           
                Integer Qtyi=new Integer(0);
                DCIteratorBinding iter = ADFBeanUtils.findIterator("MNFJeItmStkSerial1Iterator");
                Row[] arIR = iter.getAllRowsInRange();
                Integer Ri = (Integer) arIR.length;
                Number qty = (Number) this.outputItemActQty.getValue();
            try{
                 Qtyi = qty.floor().intValue(); //Integer.parseInt(this.outputItemActQty.getValue().toString());
                
            }        catch(Exception e) {
            e.printStackTrace();
            String msg2 = "<html><body> <p>Error Occur Please Contact to Ess.</p></body></html>";
            showFacesMsg(msg2, msg2, FacesMessage.SEVERITY_ERROR, null);
            
        }
            if (Ri == Qtyi) {
                    //setReleaseFlag(true);
                    return "true";
                } else {
                    String msg2 = "<html><body> <p>Item is Serialized. Please Enter Serial's.</p></body></html>";
//                    setDisclosedFirstTab(bindOutputStockTab);
//                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindOutputStockTab);
//                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                    showFacesMsg(msg2, msg2, FacesMessage.SEVERITY_ERROR, null);
                    return "false";
                }
    
        
    }

}
