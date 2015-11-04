package trnpvhclprofileapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;

public class TrnpVhclProfileBean {
    private RichInputText vhcRegcInactvRsnBind;
    private RichInputText vhclDescInatcvRsnBind;
    private RichSelectBooleanCheckbox vhclRegActvBind;
    private RichSelectBooleanCheckbox vhclDescActvBind;
    private RichPanelFormLayout vhclRegDtlFormBind;

    public TrnpVhclProfileBean() {
    }

    public void createInsertVhclPrfAL(ActionEvent actionEvent) {

        RequestContext.getCurrentInstance().getPageFlowScope().put("MODE", "E");
        ADFBeanUtils.findOperation("CreateInsertVhclPrf").execute();
    }

    public void editVhclPrfAL(ActionEvent actionEvent) {
        OperationBinding pendingAtOp = ADFBeanUtils.getOperationBinding("chkPendingUsr");
        pendingAtOp.execute();
        Integer pendingAt = (Integer) pendingAtOp.getResult();
        if (pendingAt.equals(-1) || pendingAt.equals(EbizParams.GLBL_APP_USR()))
            RequestContext.getCurrentInstance().getPageFlowScope().put("MODE", "E");
        else {
            OperationBinding usrNmOp = ADFBeanUtils.getOperationBinding("getUsrNm");
            usrNmOp.getParamsMap().put("usrId", pendingAt);
            usrNmOp.execute();
            String usrNm = "Anonymous.";
            if (usrNmOp.getResult() != null)
                usrNm = (String) usrNmOp.getResult();
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1163"),
                                                    ADFModelUtils.resolvRsrc("MSG.1294")+" [ " + usrNm + "] "+ADFModelUtils.resolvRsrc("MSG.879"),
                                                    FacesMessage.SEVERITY_INFO);
        }
    }

    public void saveVhclPrfAL(ActionEvent actionEvent) {

        OperationBinding updtItmId = ADFBeanUtils.getOperationBinding("updtItmId");
        updtItmId.execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("MODE", "V");

        String action = "I";
        String remark = "A";

        OperationBinding WfnoOp = ADFBeanUtils.findOperation("getWfNo");
        WfnoOp.execute();

        String WfNum = null;
        Integer level = 0;
        Integer res = -1;

        if (WfnoOp.getResult() != null) {
            WfNum = WfnoOp.getResult().toString();
        }
        if (WfNum != null) {
            OperationBinding usrLevelOp = ADFBeanUtils.findOperation("currUsrLvl");
            usrLevelOp.getParamsMap().put("WfNum", WfNum);
            usrLevelOp.execute();
            level = -1;
            if (usrLevelOp.getResult() != null) {
                if (usrLevelOp.getResult() != null)
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
            }
            if (level == -1) {
                ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1506"),
                                                        " ", FacesMessage.SEVERITY_ERROR);
            } else {
                OperationBinding insTxnOp = ADFBeanUtils.findOperation("insWfTxn");
                insTxnOp.getParamsMap().put("wfId", WfNum);
                insTxnOp.getParamsMap().put("lvlFrm", level);
                insTxnOp.getParamsMap().put("lvlTo", level);
                insTxnOp.getParamsMap().put("action", action);
                insTxnOp.getParamsMap().put("remark", remark);
                insTxnOp.getParamsMap().put("amount", 0);
                insTxnOp.execute();

                if (insTxnOp.getResult() != null) {
                    res = Integer.parseInt(insTxnOp.getResult().toString());
                }
                if (res == 1) {
                    OperationBinding operationBinding = ADFBeanUtils.findOperation("Commit");
                    operationBinding.execute();

                    //ADFBeanUtils.findOperation("Commit").execute();

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
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"), " ",
                                                    FacesMessage.SEVERITY_ERROR);

        }

    }

    public String saveAndForwardVhclPrfAL() {

        OperationBinding updtItmId = ADFBeanUtils.getOperationBinding("updtItmId");
        updtItmId.execute();
        OperationBinding operationBinding = ADFBeanUtils.findOperation("Commit");
        operationBinding.execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("MODE", "V");
        String action = "I";
        String remark = "A";

        OperationBinding WfnoOp = ADFBeanUtils.findOperation("getWfNo");
        WfnoOp.execute();

        String WfNum = null;
        Integer level = 0;
        Integer res = -1;

        if (WfnoOp.getResult() != null) {

            WfNum = WfnoOp.getResult().toString();

        }

        System.out.println("WfNoOp :: " + WfnoOp.getResult());
        System.out.println("WorkFlowId :: " + WfNum);

        if (WfNum != null) {

            OperationBinding usrLevelOp = ADFBeanUtils.findOperation("currUsrLvl");
            usrLevelOp.getParamsMap().put("WfNum", WfNum);
            usrLevelOp.execute();
            level = -1;

            if (usrLevelOp.getResult() != null) {

                level = Integer.parseInt(usrLevelOp.getResult().toString());
            }

            System.out.println("Level ::" + level);

            if (level == -1) {

                ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1506"), " ",
                                                        FacesMessage.SEVERITY_ERROR);

            } else {
                OperationBinding insTxnOp = ADFBeanUtils.findOperation("insWfTxn");
                insTxnOp.getParamsMap().put("wfId", WfNum);
                insTxnOp.getParamsMap().put("lvlFrm", level);
                insTxnOp.getParamsMap().put("lvlTo", level);
                insTxnOp.getParamsMap().put("action", action);
                insTxnOp.getParamsMap().put("remark", remark);
                insTxnOp.getParamsMap().put("amount", 0);
                insTxnOp.execute();

                if (insTxnOp.getResult() != null) {
                    res = Integer.parseInt(insTxnOp.getResult().toString());
                }

                System.out.println("Workflow insert return=" + res);

                if (res != null && res == 1) {
                    OperationBinding operationcomm = ADFBeanUtils.findOperation("Commit");
                    operationcomm.execute();
                    System.out.println("Before return statement in Bean Action !!");
                    return "workflow";
                } else {
                    System.out.println("error during insert to WF");
                }
                System.out.println(level + "level--res" + res);
            }

        } else {

            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"), " ",
                                                    FacesMessage.SEVERITY_ERROR);

        }

        return "null";

    }

    public String backButtonAction() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("MODE", "V");
        ADFBeanUtils.findOperation("Rollback").execute();

        return "back";

    }

    public void createInsertVhclRegDtlAL(ActionEvent actionEvent) {

        RequestContext.getCurrentInstance().getPageFlowScope().put("MODE", "E");
        ADFBeanUtils.findOperation("CreateInsertVhclRegDtl").execute();
        ADFBeanUtils.findOperation("createSlNo").execute();
    }

    public void deleteVhclRegDtlAL(ActionEvent actionEvent) {

        ADFBeanUtils.findOperation("DeleteVhclRegDtl").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(vhclRegDtlFormBind);
        //actionEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        vhclRegDtlFormBind.processUpdates(FacesContext.getCurrentInstance());
        
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        
        ViewHandler vh = context.getApplication().getViewHandler();
        UIViewRoot page = vh.createView(context, viewId);
        
    }

    public void vhclRegNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        String vhclRegNo = (String) object;

        System.out.println("inside validator :: "+object);

        if (vhclRegNo != null && vhclRegNo.length() > 0) {
            
            if(vhclRegNo.matches("^[a-zA-Z0-9]*$"))
            {            
                OperationBinding op = BindingContext.getBindingsEntryOnRequest().getOperationBinding("vhclRegNoDuplicate");
                op.getParamsMap().put("vhclRegNo", vhclRegNo);
                op.execute();
                Boolean result = (Boolean) op.getResult();
    
                System.out.println("Result in Bean ::" + result);
    
                if (result != null) {
    
                    if (result)
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.1924")+"\n"+ADFModelUtils.resolvRsrc("MSG.1924"),
                                                                      null));
                }
            }
            else
            {
                  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.110")+"\n"+ADFModelUtils.resolvRsrc("MSG.110"),
                                                                      null));
            }
            

        } else {
            System.out.println("inside vhcl null condition :: "+vhclRegNo);
            /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          ADFModelUtils.resolvRsrc("MSG.1950"), null)); */
        }

        ////uIComponent.processUpdates(facesContext);

    }

    public void vhclDescValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        System.out.println("vhclDesc value in Bean :: " + object);

        if (object != null) {
            OperationBinding op = ADFBeanUtils.findOperation("vhclDescDuplicate");
            op.getParamsMap().put("vhclDesc", object);
            op.execute();
            Boolean result = (Boolean) op.getResult();

            System.out.println("Result in Bean ::" + result);

            if (result != null) {

                if (result)
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                   ADFModelUtils.resolvRsrc("MSG.375"),
                                                                  null));
            }
        }
        else
        {
                    /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                   ADFModelUtils.resolvRsrc("MSG.508")+"\n"+ADFModelUtils.resolvRsrc("MSG.1951"),
                                                                  null)); */
        }
        
        //uIComponent.processUpdates(facesContext);
    }

    public void priceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            Number price = (Number) object;

            if (price.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1058"), null));
            }

            if (!(ADFBeanUtils.isPrecisionValid(26, 6, price))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }

        } else {
            //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,ADFModelUtils.resolvRsrc("MSG.997"), null));
        }

        //uIComponent.processUpdates(facesContext);
    }

    public void mileageEmptyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            Number mileageEmpty = (Number) object;

            if (mileageEmpty.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1058"), null));
            }

            if (!(ADFBeanUtils.isPrecisionValid(26, 6, mileageEmpty))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }

        }

        //uIComponent.processUpdates(facesContext);
    }

    public void mileageLoadValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            Number mileageEmpty = (Number) object;

            if (mileageEmpty.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1058"), null));
            }

            if (!(ADFBeanUtils.isPrecisionValid(26, 6, mileageEmpty))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }
        }

        //uIComponent.processUpdates(facesContext);
    }

    public void capacityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            Number capacity = (Number) object;

            if (capacity.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1058"), null));
            }

            if (!(ADFBeanUtils.isPrecisionValid(26, 6, capacity))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }

        }

        //uIComponent.processUpdates(facesContext);
    }

    public void fullTankValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            Number fullTank = (Number) object;

            if (fullTank.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1058"), null));
            }

            if (!(ADFBeanUtils.isPrecisionValid(26, 6,fullTank))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }

        }

        //uIComponent.processUpdates(facesContext);
    }

    public void grossWeightValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            Number grossWeight = (Number) object;

            if (grossWeight.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1058"), null));
            }

            if (!(ADFBeanUtils.isPrecisionValid(26, 6, grossWeight))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }

        }

        //uIComponent.processUpdates(facesContext);
    }

    /*     public void itemSrNoVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

        System.out.println("Vce new Value is " + vce.getNewValue());
    } */


    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }


    public void engineNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding chkDupli = ADFBeanUtils.findOperation("chkDupliEngineNo");
            chkDupli.getParamsMap().put("engineNo", object);
            chkDupli.execute();
            if (chkDupli.getResult() != null && chkDupli.getResult().toString().equals("N")) {
            } else
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1952"), null));
        }

        //uIComponent.processUpdates(facesContext);
    }

    public void chasisNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("Inside Chasis no. validator");
        if (object != null && object.toString().length() > 0) {
            OperationBinding chkDupli = ADFBeanUtils.findOperation("chkDupliChasisNo");
            chkDupli.getParamsMap().put("chasisNo", object);
            chkDupli.execute();
            System.out.println("Result=" + chkDupli.getResult());
            if (chkDupli.getResult() != null && chkDupli.getResult().toString().equals("N")) {
            } else
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1953"), null));
        }

        //uIComponent.processUpdates(facesContext);
    }

    public void slNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        //uIComponent.processUpdates(facesContext);
        
        System.out.println("serialNo value in Bean :: " + object);

        if (object != null) {
            OperationBinding op = ADFBeanUtils.findOperation("slNoDuplicate");
            op.getParamsMap().put("slNo", object);
            op.execute();
            Boolean result = (Boolean) op.getResult();

            System.out.println("Result in Bean ::" + result);

            if (result != null) {

                if (result)
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.812"),null));
            }
        }

        //AdfFacesContext.getCurrentInstance().addPartialTarget(reg);
    }

    public void setVhcRegcInactvRsnBind(RichInputText vhcRegcInactvRsnBind) {
        this.vhcRegcInactvRsnBind = vhcRegcInactvRsnBind;
    }

    public RichInputText getVhcRegcInactvRsnBind() {
        return vhcRegcInactvRsnBind;
    }

    public void setVhclDescInatcvRsnBind(RichInputText vhclDescInatcvRsnBind) {
        this.vhclDescInatcvRsnBind = vhclDescInatcvRsnBind;
    }

    public RichInputText getVhclDescInatcvRsnBind() {
        return vhclDescInatcvRsnBind;
    }

    public void setVhclRegActvBind(RichSelectBooleanCheckbox vhclRegActvBind) {
        this.vhclRegActvBind = vhclRegActvBind;
    }

    public RichSelectBooleanCheckbox getVhclRegActvBind() {
        return vhclRegActvBind;
    }

    public void setVhclDescActvBind(RichSelectBooleanCheckbox vhclDescActvBind) {
        this.vhclDescActvBind = vhclDescActvBind;
    }

    public RichSelectBooleanCheckbox getVhclDescActvBind() {
        return vhclDescActvBind;
    }

    public void vhclDescActvVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(vhclDescInatcvRsnBind);
    }

    public void vhclRegActvVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(vhcRegcInactvRsnBind);
    }

    public void setVhclRegDtlFormBind(RichPanelFormLayout vhclRegDtlFormBind) {
        this.vhclRegDtlFormBind = vhclRegDtlFormBind;
    }

    public RichPanelFormLayout getVhclRegDtlFormBind() {
        return vhclRegDtlFormBind;
    }

    /*     public void slNoVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("Inside slNoVCL :: "+valueChangeEvent.getNewValue());
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    } */

    public void capacityWtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            Number capacity = (Number) object;

            if (capacity.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1058"), null));
            }

            if (!(ADFBeanUtils.isPrecisionValid(26, 6, capacity))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }

        }

        //uIComponent.processUpdates(facesContext);

    }
}
