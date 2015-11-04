package trnploadingorderapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

public class LoadingOrderAddBean {
    private RichPopup productDtlPopupPgBind;
    private RichInputText rateFldPgBind;
    private RichInputText valueFldPgBind;
    private RichInputText amtSpFldPgBind;
    private RichInputText amtBsFldPgBind;
    private RichInputText itemWtFldPgBind;
    private RichInputText itemRateFldPgBind;
    private RichInputText itemAmtSpFldPgBind;
    private RichInputText itemAmtBsFldPgBind;

    private String cldId = EbizParams.GLBL_APP_CLD_ID();
    private String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
    private String orgId = EbizParams.GLBL_APP_USR_ORG();
    private Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
    private Integer usrId = EbizParams.GLBL_APP_USR();
    private Integer docNo = 24255;
    private Integer docType = 0;
    private String mode = (String) ADFModelUtils.resolvEl("#{pageFlowScope.P_MODE}");
    private RichPopup otherChargesPopupPgBind;
    private RichTable otherChgsTablePgBind;
    private RichPanelFormLayout headFormPgBind;
    private RichPopup applyTaxPopUpPgBind;
    private RichSelectOneChoice taxRuleIdPgBind;
    private RichOutputText deptPointPgBind;
    private RichOutputText arrivalPntPgBind;
    private RichInputText paymentNoPgBind;
    private RichInputText accountNoPgBind;
    private RichInputText convFctrPgBind;
    private RichTable routeDtlTablePgBind;
    private RichTable itemDtlTablePgBind;
    private RichPanelFormLayout routeDtlFormPgBind;
    private RichInputDate deptDatePgBind;
    private RichInputDate arrivalDatePgBind;
    private RichOutputText deptDateForDupChkPgBind;
    private RichSelectOneChoice loStatusPgBind;
    private RichSelectOneChoice vehicletypBind;
    private RichInputListOfValues customerNameBinding;
    private RichSelectOneChoice loSrcBasisBind;
    private String pass = "N";
    private RichInputListOfValues searchShipBind;
    protected Number vhclWt = null;
    protected Number allItmWt = null;
    private RichOutputText allItmWtBind;
    private RichOutputText vhclWtBind;
    private RichInputText orderWtBind;
    private RichInputText vehicleWtBind;
    private RichInputText expctdOrderWtBind;

    public LoadingOrderAddBean() {
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void addButtonAL(ActionEvent actionEvent) {
        mode = "C";
        ADFBeanUtils.findOperation("CreateInsert").execute();
    }

    public void editButtonAL(ActionEvent actionEvent) {

        if (loStatusPgBind.getValue() != null && Integer.parseInt(loStatusPgBind.getValue().toString()) != 3) {

            //check for pending
            Integer pending = null;

            OperationBinding chkUsr = ADFBeanUtils.findOperation("pendingCheck");
            chkUsr.getParamsMap().put("SlocId", slocId);
            chkUsr.getParamsMap().put("CldId", cldId);
            chkUsr.getParamsMap().put("OrgId", orgId);
            chkUsr.getParamsMap().put("DocNo", docNo);
            chkUsr.execute();

            if (chkUsr.getResult() != null) {
                pending = Integer.parseInt(chkUsr.getResult().toString());
            }
            System.out.println("Pending at=" + pending);
            if (pending.compareTo(-1) != 0 && pending.compareTo(usrId) != 0) {
                OperationBinding gtUsrNm = ADFBeanUtils.findOperation("getUsrName");
                gtUsrNm.getParamsMap().put("usrId", pending);
                gtUsrNm.execute();
                StringBuffer usrName = new StringBuffer("Anonymous");
                if (gtUsrNm.getResult() != null)
                    usrName = new StringBuffer("[").append(gtUsrNm.getResult()).append("]");
                String msg2 = ADFModelUtils.resolvRsrc("MSG.1294") + " [ " + usrName + "] "+ ADFModelUtils.resolvRsrc("MSG.879");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else {
                mode = "E";
            }
        } else {
            ADFModelUtils.showFormattedFacesMessage( ADFModelUtils.resolvRsrc("MSG.3136"),
                                                    ADFModelUtils.resolvRsrc("MSG.2028"),
                                                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void saveButtonAL(ActionEvent actionEvent) {
        if (callMethodsBeforeSave()) {
            OperationBinding op = ADFBeanUtils.findOperation("Commit");
            op.execute();
            if (op.getErrors().size() == 0) {
                mode = "V";
                //  JSFUtils.addFacesInformationMessage("Record Saved Successfully.");

                //Workflow Start
                String action = "I";
                String remark = "A";

                OperationBinding WfnoOp = ADFBeanUtils.findOperation("getWfNo");
                WfnoOp.getParamsMap().put("sloc_id", slocId);
                WfnoOp.getParamsMap().put("cld_id", cldId);
                WfnoOp.getParamsMap().put("org_id", orgId);
                WfnoOp.getParamsMap().put("doc_no", docNo);
                WfnoOp.execute();

                String wfNum = null;
                Integer level = 0;
                Integer res = -1;

                if (WfnoOp.getResult() != null) {
                    wfNum = WfnoOp.getResult().toString();
                }
                if (wfNum != null) {
                    OperationBinding usrLevelOp = ADFBeanUtils.findOperation("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", slocId);
                    usrLevelOp.getParamsMap().put("CldId", cldId);
                    usrLevelOp.getParamsMap().put("OrgId", orgId);
                    usrLevelOp.getParamsMap().put("usr_id", usrId);
                    usrLevelOp.getParamsMap().put("WfNum", wfNum);
                    usrLevelOp.getParamsMap().put("DocNo", docNo);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        if (usrLevelOp.getResult() != null)
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                    }
                    if (level == -1) {
                        FacesMessage message =
                            new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1506"));
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        OperationBinding insTxnOp = ADFBeanUtils.findOperation("insIntoTxn");
                        insTxnOp.getParamsMap().put("sloc_id", slocId);
                        insTxnOp.getParamsMap().put("cld_id", cldId);
                        insTxnOp.getParamsMap().put("pOrgId", orgId);
                        insTxnOp.getParamsMap().put("DOC_NO", docNo);
                        insTxnOp.getParamsMap().put("WfNum", wfNum);
                        insTxnOp.getParamsMap().put("poDocId", null);
                        insTxnOp.getParamsMap().put("usr_idFrm", usrId);
                        insTxnOp.getParamsMap().put("usr_idTo", usrId);
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
                            OperationBinding operationBinding = ADFBeanUtils.findOperation("Commit");
                            operationBinding.execute();
                        } else {
                            System.out.println("error during insert to WF");
                        }
                        System.out.println(level + "level--res" + res);
                    }
                }
                //Workflow end
                ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.91"), ADFModelUtils.resolvRsrc("MSG.91"),
                                                        FacesMessage.SEVERITY_INFO);
            } else {
                FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1478"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
    }

    private Boolean callMethodsBeforeSave() {
        Boolean val = true;

        OperationBinding ob = ADFBeanUtils.findOperation("chkMandatoryTripDtls");
        ob.execute();

        if (ob.getResult() != null && !(Boolean) ob.getResult()) {
            val = false;
        } else {
            OperationBinding chkPrdBind = ADFBeanUtils.findOperation("chkPrdFldsMandatoryEnteredOrNot");
            chkPrdBind.execute();
            if (chkPrdBind.getResult() != null && !(Boolean) chkPrdBind.getResult()) {
                val = false;
            }
        }

        if (val) {
            ADFBeanUtils.findOperation("getDfltTaxRuleId").execute();
        }

        return val;
    }

    public String saveAndFrwdAction() {

        System.out.println("Pass value :: "+pass);

        if(pass.equalsIgnoreCase("Y"))
        {
            System.out.println("Inside pass Y condition !!");
            
            if (callMethodsBeforeSave()) {
                
                //ADFBeanUtils.findOperation("Commit");
                mode = "V";
                
                OperationBinding prodDtlOp = ADFBeanUtils.findOperation("chkProdDtlRow");
                prodDtlOp.execute();

                Boolean prodDtlRowCount = (Boolean) prodDtlOp.getResult();
                System.out.println("Returned Value =" + prodDtlRowCount);

                OperationBinding shipRowOp = ADFBeanUtils.findOperation("chkShipmentRow");
                shipRowOp.execute();

                Boolean shipmentRowCount = (Boolean) shipRowOp.getResult();
                System.out.println("Returned Value =" + shipmentRowCount);

                if ((prodDtlRowCount != null) && (shipmentRowCount != null)) {

                    if (!prodDtlRowCount) {
                        //show msg

                        /* ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1192"),
                                                                " ", FacesMessage.SEVERITY_WARN); */
                    } /*  else if (!shipmentRowCount) {
                        //show msg

                        ADFModelUtils.showFormattedFacesMessage("You cannot forward without adding atleast 1 shipment ! ",
                                                                " ", FacesMessage.SEVERITY_WARN);
                    } */ else {

                        OperationBinding op = ADFBeanUtils.findOperation("Commit");
                        op.execute();
                        if (op.getErrors().size() == 0) {
                            mode = "V";
                            //  JSFUtils.addFacesInformationMessage("Record Saved Successfully.");

                            //Workflow Start
                            String action = "I";
                            String remark = "A";

                            OperationBinding WfnoOp = ADFBeanUtils.findOperation("getWfNo");
                            WfnoOp.getParamsMap().put("sloc_id", slocId);
                            WfnoOp.getParamsMap().put("cld_id", cldId);
                            WfnoOp.getParamsMap().put("org_id", orgId);
                            WfnoOp.getParamsMap().put("doc_no", docNo);
                            WfnoOp.execute();

                            String wfNum = null;
                            Integer level = 0;
                            Integer res = -1;

                            if (WfnoOp.getResult() != null) {
                                wfNum = WfnoOp.getResult().toString();
                            }
                            if (wfNum != null) {
                                OperationBinding usrLevelOp = ADFBeanUtils.findOperation("getUsrLvl");
                                usrLevelOp.getParamsMap().put("SlocId", slocId);
                                usrLevelOp.getParamsMap().put("CldId", cldId);
                                usrLevelOp.getParamsMap().put("OrgId", orgId);
                                usrLevelOp.getParamsMap().put("usr_id", usrId);
                                usrLevelOp.getParamsMap().put("WfNum", wfNum);
                                usrLevelOp.getParamsMap().put("DocNo", docNo);
                                usrLevelOp.execute();
                                level = -1;
                                if (usrLevelOp.getResult() != null) {
                                    if (usrLevelOp.getResult() != null)
                                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                                }
                                if (level == -1) {
                                    FacesMessage message =
                                        new FacesMessage( ADFModelUtils.resolvRsrc("MSG.1506"));
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                } else {
                                    OperationBinding insTxnOp = ADFBeanUtils.findOperation("insIntoTxn");
                                    insTxnOp.getParamsMap().put("sloc_id", slocId);
                                    insTxnOp.getParamsMap().put("cld_id", cldId);
                                    insTxnOp.getParamsMap().put("pOrgId", orgId);
                                    insTxnOp.getParamsMap().put("DOC_NO", docNo);
                                    insTxnOp.getParamsMap().put("WfNum", wfNum);
                                    insTxnOp.getParamsMap().put("poDocId", null);
                                    insTxnOp.getParamsMap().put("usr_idFrm", usrId);
                                    insTxnOp.getParamsMap().put("usr_idTo", usrId);
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
                                        OperationBinding operationBinding = ADFBeanUtils.findOperation("Commit");
                                        operationBinding.execute();
                                        return "workFlow";
                                    } else {
                                        System.out.println("error during insert to WF");
                                    }
                                    System.out.println(level + "level--res" + res);
                                }
                            } else {
                                FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"));
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }
                            //Workflow end

                        } else {
                            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1478"));
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    }
                }
            }    
        }
        else
        {
            System.out.println("inside pass N condition !!");
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1686"), ADFModelUtils.resolvRsrc("MSG.2078"), FacesMessage.SEVERITY_ERROR);
            //ADFModelUtils.showFormattedFacesMessage("Amount can nto be zero", "Amount should be greater than zero ", FacesMessage.SEVERITY_ERROR);    
        }
        
        return null;
    }

    public String cancelButtonAction() {
        mode = "V";
        ADFBeanUtils.findOperation("Rollback").execute();

        return "back";
    }

    public void addTripDtlAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateInsert1").execute();
    }

    public void deleteTripDtlAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("deleteDataFromChildTable").execute();
    }

    public void addShipAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("insertDataIntoLoShipDtl").execute();
        searchShipBind.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchShipBind);
    }

    public void deleteShipAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("deleteDataFromLoShip").execute();
    }

    public void addCustNmAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("insertDateIntoEo").execute();
    }

    public void deleteCustAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("deleteRouteFromEo").execute();
    }

    public void setProductDtlPopupPgBind(RichPopup productDtlPopupPgBind) {
        this.productDtlPopupPgBind = productDtlPopupPgBind;
    }

    public RichPopup getProductDtlPopupPgBind() {
        return productDtlPopupPgBind;
    }

    public void updatePopupAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("updateItemValsOnRoute").execute();

        /* 
        OperationBinding ob = ADFBeanUtils.findOperation("chkCurrentRowPrdFldsMandEnterOrNot");
        ob.execute();

        if (ob.getResult() != null && (Boolean) ob.getResult()) {

            OperationBinding getWt = ADFBeanUtils.findOperation("getAllItemsWeight");
            getWt.execute();
            
            if(getWt.getResult() != null && !getWt.getResult().toString().equals("0"))
                allItmWt = (Number) getWt.getResult();
            
            System.out.println("All items Weight :: "+allItmWt); 
        }
            
        */
            
            productDtlPopupPgBind.hide();

       // AdfFacesContext.getCurrentInstance().addPartialTarget(rateFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orderWtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(expctdOrderWtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(valueFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtSpFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtBsFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlTablePgBind);
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setVhclWt(Number vhclWt) {
        this.vhclWt = vhclWt;
    }

    public Number getVhclWt() {
        return vhclWt;
    }

    public void setAllItmWt(Number allItmWt) {
        this.allItmWt = allItmWt;
    }

    public Number getAllItmWt() {
        return allItmWt;
    }

    public void rateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
            }
        }
    }

    public void rateVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (valueFldPgBind.getValue() != null) {
                OperationBinding ob = ADFBeanUtils.findOperation("setRouteAmtValues");
                ob.getParamsMap().put("rate", vce.getNewValue());
                ob.getParamsMap().put("value", valueFldPgBind.getValue());

                ob.execute();
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtSpFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtBsFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlTablePgBind);
    }

    public void totValValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
            }
        }
    }

    public void totValueVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (rateFldPgBind.getValue() != null) {
                OperationBinding ob = ADFBeanUtils.findOperation("setRouteAmtValues");
                ob.getParamsMap().put("rate", rateFldPgBind.getValue());
                ob.getParamsMap().put("value", vce.getNewValue());

                ob.execute();
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtSpFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtBsFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlTablePgBind);
    }

    public void fuelQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                               ADFModelUtils.resolvRsrc("MSG.198"), null));
            }
        }
    }

    public void setRateFldPgBind(RichInputText rateFldPgBind) {
        this.rateFldPgBind = rateFldPgBind;
    }

    public RichInputText getRateFldPgBind() {
        return rateFldPgBind;
    }

    public void setValueFldPgBind(RichInputText valueFldPgBind) {
        this.valueFldPgBind = valueFldPgBind;
    }

    public RichInputText getValueFldPgBind() {
        return valueFldPgBind;
    }

    public void setAmtSpFldPgBind(RichInputText amtSpFldPgBind) {
        this.amtSpFldPgBind = amtSpFldPgBind;
    }

    public RichInputText getAmtSpFldPgBind() {
        return amtSpFldPgBind;
    }

    public void setAmtBsFldPgBind(RichInputText amtBsFldPgBind) {
        this.amtBsFldPgBind = amtBsFldPgBind;
    }

    public RichInputText getAmtBsFldPgBind() {
        return amtBsFldPgBind;
    }

    public void itemWtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                               ADFModelUtils.resolvRsrc("MSG.198"), null));
            }
        }
    }

    public void itemWtVCL(ValueChangeEvent vce) {
        /*   if (vce.getNewValue() != null) {
            if (itemRateFldPgBind.getValue() != null) {
                OperationBinding ob = ADFBeanUtils.findOperation("setItmAmtValues");
                ob.getParamsMap().put("rate", itemRateFldPgBind.getValue());
                ob.getParamsMap().put("weight", vce.getNewValue());

                ob.execute();
            }
        } */
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDtlTablePgBind);

        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setItemWtFldPgBind(RichInputText itemWtFldPgBind) {
        this.itemWtFldPgBind = itemWtFldPgBind;
    }

    public RichInputText getItemWtFldPgBind() {
        return itemWtFldPgBind;
    }

    public void itemRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                               ADFModelUtils.resolvRsrc("MSG.198"), null));
            }
        }
    }

    public void itemRateVCL(ValueChangeEvent vce) {
        /*  if (vce.getNewValue() != null) {
            if (itemWtFldPgBind.getValue() != null) {
                OperationBinding ob = ADFBeanUtils.findOperation("setItmAmtValues");
                ob.getParamsMap().put("rate", vce.getNewValue());
                ob.getParamsMap().put("weight", itemWtFldPgBind.getValue());

                ob.execute();
            }
        } */
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDtlTablePgBind);

        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setItemRateFldPgBind(RichInputText itemRateFldPgBind) {
        this.itemRateFldPgBind = itemRateFldPgBind;
    }

    public RichInputText getItemRateFldPgBind() {
        return itemRateFldPgBind;
    }

    public void setItemAmtSpFldPgBind(RichInputText itemAmtSpFldPgBind) {
        this.itemAmtSpFldPgBind = itemAmtSpFldPgBind;
    }

    public RichInputText getItemAmtSpFldPgBind() {
        return itemAmtSpFldPgBind;
    }

    public void setItemAmtBsFldPgBind(RichInputText itemAmtBsFldPgBind) {
        this.itemAmtBsFldPgBind = itemAmtBsFldPgBind;
    }

    public RichInputText getItemAmtBsFldPgBind() {
        return itemAmtBsFldPgBind;
    }

    public void addAdvanceDtlAL(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.findOperation("chkAdvCoaNameOpenedOrNor");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            ADFBeanUtils.findOperation("addAdvanceDtl").execute();
        }
    }

    public void deleteAdvanceAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("Delete1").execute();
    }

    public void addOCAL(ActionEvent actionEvent) {
        OperationBinding addOc = ADFBeanUtils.findOperation("CreateInsertOC");
        addOc.execute();
        OperationBinding populateOc = ADFBeanUtils.findOperation("addOcFromVW");
        populateOc.execute();

    }

    public void populateRouteDtlAL(ActionEvent actionEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(customerNameBinding);
        ADFBeanUtils.findOperation("fetchRouteDtlData").execute();
        ADFBeanUtils.findOperation("setSingleCustByDflt").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtBsFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtSpFldPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlFormPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlTablePgBind);
    }

    public void setOtherChargesPopupPgBind(RichPopup otherChargesPopupPgBind) {
        this.otherChargesPopupPgBind = otherChargesPopupPgBind;
    }

    public RichPopup getOtherChargesPopupPgBind() {
        return otherChargesPopupPgBind;
    }

    public void otherChargesOkAL(ActionEvent actionEvent) {
        otherChargesPopupPgBind.hide();
    }

    public void showOtherChargesAL(ActionEvent actionEvent) {
        ADFBeanUtils.showPopup(otherChargesPopupPgBind, true);
    }

    public void otherChgsAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                               ADFModelUtils.resolvRsrc("MSG.198"), null));
            }
        }
    }

    public void setOtherChgsTablePgBind(RichTable otherChgsTablePgBind) {
        this.otherChgsTablePgBind = otherChgsTablePgBind;
    }

    public RichTable getOtherChgsTablePgBind() {
        return otherChgsTablePgBind;
    }

    public void othersChgsAmtVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(otherChgsTablePgBind);
    }

    public void lrDispIdVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(headFormPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlFormPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlTablePgBind);
    }

    public void lrDispIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding ob = ADFBeanUtils.findOperation("chkLrDispIdPresentOrNot");
            ob.getParamsMap().put("lrDispId", object);
            ob.execute();

            if (ob.getResult() != null && ob.getResult().toString().length() > 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1699"),
                                                              null));
            }
        }
    }

    public void setHeadFormPgBind(RichPanelFormLayout headFormPgBind) {
        this.headFormPgBind = headFormPgBind;
    }

    public RichPanelFormLayout getHeadFormPgBind() {
        return headFormPgBind;
    }

    public void setApplyTaxPopUpPgBind(RichPopup applyTaxPopUpPgBind) {
        this.applyTaxPopUpPgBind = applyTaxPopUpPgBind;
    }

    public RichPopup getApplyTaxPopUpPgBind() {
        return applyTaxPopUpPgBind;
    }

    public void applyTaxButtonAL(ActionEvent actionEvent) {
        if (taxRuleIdPgBind.getValue() != null) {
            OperationBinding ob = ADFBeanUtils.findOperation("insertIntoSlsTrnpTrAndTrLines");
            ob.getParamsMap().put("taxRuleId", taxRuleIdPgBind.getValue());
            ob.execute();
            applyTaxPopUpPgBind.hide();
        }
    }

    public void cancelTacButtonAL(ActionEvent actionEvent) {
        applyTaxPopUpPgBind.hide();
    }

    public void showTaxPopupAL(ActionEvent actionEvent) {
        ADFBeanUtils.showPopup(applyTaxPopUpPgBind, true);
    }

    public void setTaxRuleIdPgBind(RichSelectOneChoice taxRuleIdPgBind) {
        this.taxRuleIdPgBind = taxRuleIdPgBind;
    }

    public RichSelectOneChoice getTaxRuleIdPgBind() {
        return taxRuleIdPgBind;
    }

    public void custNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if ((object != null) && (object.toString().length() > 0)) {
            OperationBinding ob = ADFBeanUtils.findOperation("chkCustNameExistOrNot");
            ob.getParamsMap().put("custName", object);
            ob.execute();

            if (ob.getResult() != null && !(Boolean) ob.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1699"), null));
            }
            
           if(this.loSrcBasisBind.getValue() != null && this.loSrcBasisBind.getValue().toString().length() > 0)
           {
                if(this.loSrcBasisBind.getValue().toString().equalsIgnoreCase("39"))
                {
                }
                else
                {
                    System.out.println("Inside else condition of Rate Contract Validator :: "+this.loSrcBasisBind.getValue().toString());
                    
                    String custName = (String) object;
                                OperationBinding op = (OperationBinding) ADFBeanUtils.findOperation("chkCustRateCntrct");
                                op.getParamsMap().put("custName", custName);
                                op.execute();
                                Boolean result = (Boolean) op.getResult();

                                if (result != null) {
                                    if (!result)
                                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                                      ADFModelUtils.resolvRsrc("MSG.1993"),
                                                                                      null));
                                }    
                }
            }
            
        }
    }

    public void custRateCntrctValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        if(this.loSrcBasisBind.getValue() != null && this.loSrcBasisBind.getValue().toString().length() > 0)
        {
             if(this.loSrcBasisBind.getValue().toString().equalsIgnoreCase("39"))
             {
             }
             else
             {
                if ((object != null) && (object.toString().length() > 0) && vehicletypBind.getValue()!=null && vehicletypBind.getValue().toString().length()>0 ) {
                    
                    System.out.println("Value of vehicleTypBind :: "+vehicletypBind.getValue());
                    String custName = (String) object;
                    OperationBinding op = (OperationBinding) ADFBeanUtils.findOperation("chkCustRateCntrct");
                    op.getParamsMap().put("custName", custName);
                    op.execute();
                    Boolean result = (Boolean) op.getResult();
                    
                    System.out.println("Result in bean :: "+result);
        
                    if (result != null) {
                        if (!result)
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                           ADFModelUtils.resolvRsrc("MSG.1993"),
                                                                          null));
                    }
        
                }else
                {
                   System.out.println("Either Name or Vehicle Type is null"); 
                 }
             }
        }
    }
    
    public void documentChkValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding ob = ADFBeanUtils.findOperation("chkDocSrcExistOrNot");
            ob.getParamsMap().put("docSrcNm", object);
            ob.execute();

            if (ob.getResult() != null && !(Boolean) ob.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1699"), null));
            }
        }
    }

    public void deptPointValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (arrivalPntPgBind.getValue() != null) {
                OperationBinding binding = ADFBeanUtils.findOperation("getLocIdFromName");
                binding.getParamsMap().put("locNm", object);
                binding.execute();

                if (binding.getResult() != null) {
                    OperationBinding ob = ADFBeanUtils.findOperation("chkRouteDtlExistOrNot");
                    ob.getParamsMap().put("deptId", binding.getResult());
                    ob.getParamsMap().put("arrId", arrivalPntPgBind.getValue());

                    ob.execute();

                    if (ob.getResult() != null && !(Boolean) ob.getResult()) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1699"),
                                                                      null));
                    }
                }
            }
        }
    }

    public void arrivalPointValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (deptPointPgBind.getValue() != null) {
                OperationBinding binding = ADFBeanUtils.findOperation("getLocIdFromName");
                binding.getParamsMap().put("locNm", object);
                binding.execute();

                if (binding.getResult() != null) {
                    OperationBinding ob = ADFBeanUtils.findOperation("chkRouteDtlExistOrNot");
                    ob.getParamsMap().put("deptId", deptPointPgBind.getValue());
                    ob.getParamsMap().put("arrId", binding.getResult());

                    ob.execute();

                    if (ob.getResult() != null && !(Boolean) ob.getResult()) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1699"),
                                                                      null));
                    }
                }
            }
        }
    }

    public void setDeptPointPgBind(RichOutputText deptPointPgBind) {
        this.deptPointPgBind = deptPointPgBind;
    }

    public RichOutputText getDeptPointPgBind() {
        return deptPointPgBind;
    }

    public void setArrivalPntPgBind(RichOutputText arrivalPntPgBind) {
        this.arrivalPntPgBind = arrivalPntPgBind;
    }

    public RichOutputText getArrivalPntPgBind() {
        return arrivalPntPgBind;
    }

    public void paymentModeVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentNoPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(accountNoPgBind);
    }

    public void setPaymentNoPgBind(RichInputText paymentNoPgBind) {
        this.paymentNoPgBind = paymentNoPgBind;
    }

    public RichInputText getPaymentNoPgBind() {
        return paymentNoPgBind;
    }

    public void setAccountNoPgBind(RichInputText accountNoPgBind) {
        this.accountNoPgBind = accountNoPgBind;
    }

    public RichInputText getAccountNoPgBind() {
        return accountNoPgBind;
    }

    public void multiCustSelVCL(ValueChangeEvent vce) {
        ADFBeanUtils.findOperation("resetCustFlds").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(headFormPgBind);
    }

    public void currNameSelVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(convFctrPgBind);
    }

    public void setConvFctrPgBind(RichInputText convFctrPgBind) {
        this.convFctrPgBind = convFctrPgBind;
    }

    public RichInputText getConvFctrPgBind() {
        return convFctrPgBind;
    }

    public void departureDtVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlFormPgBind);
    }

    public void arrivalDtVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFBeanUtils.findOperation("setDeptDateForNextSlNo");
            ob.getParamsMap().put("arrivalDate", vce.getNewValue());
            ob.execute();
        }

        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlTablePgBind);
    }

    public void setRouteDtlTablePgBind(RichTable routeDtlTablePgBind) {
        this.routeDtlTablePgBind = routeDtlTablePgBind;
    }

    public RichTable getRouteDtlTablePgBind() {
        return routeDtlTablePgBind;
    }

    public void setItemDtlTablePgBind(RichTable itemDtlTablePgBind) {
        this.itemDtlTablePgBind = itemDtlTablePgBind;
    }

    public RichTable getItemDtlTablePgBind() {
        return itemDtlTablePgBind;
    }

    public void deptDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (arrivalDatePgBind.getValue() != null) {
                Timestamp t3 = (Timestamp) object;
                Timestamp t4 = (Timestamp) arrivalDatePgBind.getValue();

                Date d3 = new Date(System.currentTimeMillis());
                Date d4 = new Date(System.currentTimeMillis());
                try {
                    d3 = t3.dateValue();
                    d4 = t4.dateValue();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //System.out.println("T4 Compare to T3 "+t4.compareTo(t3));
                if (t4.compareTo(t3) < 0 && !d3.toString().equals(d4.toString())) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.2031") + d4, null));
                }
            }


            if (deptDateForDupChkPgBind.getValue() != null) {
                Timestamp t1 = (Timestamp) deptDateForDupChkPgBind.getValue();
                Timestamp t2 = (Timestamp) object;

                Date d1 = new Date(System.currentTimeMillis());
                Date d2 = new Date(System.currentTimeMillis());
                try {
                    d1 = t1.dateValue();
                    d2 = t2.dateValue();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //System.out.println("T2 Compare to T1 "+t2.compareTo(t1));
                if (t2.compareTo(t1) < 0 && !d1.toString().equals(d2.toString())) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.2031")+ d1, null));
                }
            }
        }
    }

    public void arrivalDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (deptDatePgBind.getValue() != null) {
                Timestamp t1 = (Timestamp) deptDatePgBind.getValue();
                Timestamp t2 = (Timestamp) object;

                Date d1 = new Date(System.currentTimeMillis());
                Date d2 = new Date(System.currentTimeMillis());
                try {
                    d1 = t1.dateValue();
                    d2 = t2.dateValue();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //System.out.println("T2 Compare to T1 "+t2.compareTo(t1));
                if (t2.compareTo(t1) < 0 && !d1.toString().equals(d2.toString())) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.2032"),
                                                                  null));
                }
            }
        }
    }

    public void setRouteDtlFormPgBind(RichPanelFormLayout routeDtlFormPgBind) {
        this.routeDtlFormPgBind = routeDtlFormPgBind;
    }

    public RichPanelFormLayout getRouteDtlFormPgBind() {
        return routeDtlFormPgBind;
    }

    public void setDeptDatePgBind(RichInputDate deptDatePgBind) {
        this.deptDatePgBind = deptDatePgBind;
    }

    public RichInputDate getDeptDatePgBind() {
        return deptDatePgBind;
    }

    public void setArrivalDatePgBind(RichInputDate arrivalDatePgBind) {
        this.arrivalDatePgBind = arrivalDatePgBind;
    }

    public RichInputDate getArrivalDatePgBind() {
        return arrivalDatePgBind;
    }

    public void setDeptDateForDupChkPgBind(RichOutputText deptDateForDupChkPgBind) {
        this.deptDateForDupChkPgBind = deptDateForDupChkPgBind;
    }

    public RichOutputText getDeptDateForDupChkPgBind() {
        return deptDateForDupChkPgBind;
    }

    public void setLoStatusPgBind(RichSelectOneChoice loStatusPgBind) {
        this.loStatusPgBind = loStatusPgBind;
    }

    public RichSelectOneChoice getLoStatusPgBind() {
        return loStatusPgBind;
    }

    public void loBasisVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(headFormPgBind);
    }

    public void orderTypeVCL(ValueChangeEvent vce) {
        ADFBeanUtils.findOperation("resetTrnspFlds").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(headFormPgBind);
    }

    public void routeNameVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(headFormPgBind);
    }

    public void rateFactorVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlFormPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlTablePgBind);
    }

    public void rateBasisVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlFormPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeDtlTablePgBind);
    }

    public void vehicleTypeVCL(ValueChangeEvent vce) {
        ADFBeanUtils.findOperation("resetVehicleNoFlds").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(headFormPgBind);
       
    }

    public void setVehicletypBind(RichSelectOneChoice vehicletypBind) {
        this.vehicletypBind = vehicletypBind;
    }

    public RichSelectOneChoice getVehicletypBind() {
        return vehicletypBind;
    }

    public void setCustomerNameBinding(RichInputListOfValues customerNameBinding) {
        this.customerNameBinding = customerNameBinding;
    }

    public RichInputListOfValues getCustomerNameBinding() {
        return customerNameBinding;
    }

    public void setLoSrcBasisBind(RichSelectOneChoice loSrcBasisBind) {
        this.loSrcBasisBind = loSrcBasisBind;
    }

    public RichSelectOneChoice getLoSrcBasisBind() {
        return loSrcBasisBind;
    }

    public void saveAndFwdAL(ActionEvent actionEvent) {
        
        DCBindingContainer dcBind = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcVO = dcBind.findIteratorBinding("SlsTrnpLoRouteDtlVO1Iterator");
        
        Row[] allRouteRows = dcVO.getAllRowsInRange();
        
        for(Row r : allRouteRows)
        {
            Number totalAmt = (Number) r.getAttribute("AmtSp");
            
            if(totalAmt != null)
            {
                if(totalAmt.compareTo(0) <= 0)
                {
                    pass = "N";
                    break;
                }   
                else
                    pass = "Y";
            }
            else
                pass ="N";
        }
        
        System.out.println("Pass value in AL :: "+pass);
        
    }

    public void setSearchShipBind(RichInputListOfValues searchShipBind) {
        this.searchShipBind = searchShipBind;
    }

    public RichInputListOfValues getSearchShipBind() {
        return searchShipBind;
    }

    public void vhclNoVCL(ValueChangeEvent valueChangeEvent) {
        
        /*     
        if(valueChangeEvent.getNewValue() != null)
        {
            OperationBinding ob = ADFBeanUtils.findOperation("getVehicleWeight");
            ob.getParamsMap().put("vhclNo", valueChangeEvent.getNewValue().toString());
            ob.execute();
            
            if(ob.getResult() != null && !ob.getResult().equals("0"))
                 vhclWt = (Number) ob.getResult();
            
        } */
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(vehicleWtBind);
    }

    public void setAllItmWtBind(RichOutputText allItmWtBind) {
        this.allItmWtBind = allItmWtBind;
    }

    public RichOutputText getAllItmWtBind() {
        return allItmWtBind;
    }

    public void setVhclWtBind(RichOutputText vhclWtBind) {
        this.vhclWtBind = vhclWtBind;
    }

    public RichOutputText getVhclWtBind() {
        return vhclWtBind;
    }

    public void setOrderWtBind(RichInputText orderWtBind) {
        this.orderWtBind = orderWtBind;
    }

    public RichInputText getOrderWtBind() {
        return orderWtBind;
    }

    public void setVehicleWtBind(RichInputText vehicleWtBind) {
        this.vehicleWtBind = vehicleWtBind;
    }

    public RichInputText getVehicleWtBind() {
        return vehicleWtBind;
    }

    public void setExpctdOrderWtBind(RichInputText expctdOrderWtBind) {
        this.expctdOrderWtBind = expctdOrderWtBind;
    }

    public RichInputText getExpctdOrderWtBind() {
        return expctdOrderWtBind;
    }
}
