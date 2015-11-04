/**
* @author : Sudhanshu Raj.
**/

package svcreportapp.view.Bean;

import adf.utils.bean.ADFBeanUtils;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;


import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.output.RichMessage;

import oracle.binding.BindingContainer;

import oracle.adf.model.BindingContext;


import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;


public class ServiceReportBean {
    private RichInputDate startDtPgBind;
    private RichInputDate endDtPgBind;

    private RichLink svcGoLinkPgBind;
    private RichSelectBooleanCheckbox invChkPgBind;

    private RichLink invGoLinkPgBind;
    private RichSelectBooleanCheckbox svcConChkPgBind;

    private RichSelectBooleanCheckbox scmChkPgBind;
    private RichLink scmChkGoLinkPgBind;

    private RichSelectBooleanCheckbox woChkPgBind;
    private RichLink woGoLinkPgBind;

    private RichSelectBooleanCheckbox scmCalTracChkPgBind;
    private RichLink scmCalTracGoLink;

    private RichInputListOfValues scContrtPgBind;
    private RichSelectBooleanCheckbox scSvcExecChkPgBind;
    private RichLink scSvcExecGoLinkPgBind;


    private boolean ServiceContrct_link = false;
    private boolean ServiceContrctTrack_link = false;


    private boolean ServiceExecutive_link = false;
    private boolean ServiceContrat = false;
    private boolean ServiceExecutive = false;
    private boolean ServiceContractTract = false;

    private boolean Invoice_Link = false;
    private boolean Invoice = false;

    private boolean SvcCalMng = false;
    private boolean SvcCalMng_Link = false;
    private boolean SvcCalTack = false;
    private boolean SvcCalTrack_Link = false;

    private boolean GenericReport = false;
    private boolean GenericReport_GoLink = false;


    private boolean WorkOrder = false;
    private boolean WorkOrder_Link = false;
    private RichSelectBooleanCheckbox scContractTrackChkPgBind;
    private RichLink scContractTrackGoLinkPgBind;
    private RichMessage rptInfoOnPopUpPgBind;
    private RichLink genericRptNmGoLinkPgBind;
    private RichInputListOfValues reportNameTransPgBind;
    private RichSelectBooleanCheckbox genericReportCBPgBind;

    public void setServiceExecutive_link(boolean ServiceExecutive_link) {
        this.ServiceExecutive_link = ServiceExecutive_link;
    }

    public boolean isServiceExecutive_link() {
        return ServiceExecutive_link;
    }

    public void setServiceExecutive(boolean ServiceExecutive) {
        this.ServiceExecutive = ServiceExecutive;
    }

    public boolean isServiceExecutive() {
        return ServiceExecutive;
    }

    public void setInvoice_Link(boolean Invoice_Link) {
        this.Invoice_Link = Invoice_Link;
    }

    public void setSvcCalMng(boolean SvcCalMng) {
        this.SvcCalMng = SvcCalMng;
    }

    public void setServiceContrctTrack_link(boolean ServiceContrctTrack_link) {
        this.ServiceContrctTrack_link = ServiceContrctTrack_link;
    }

    public boolean isServiceContrctTrack_link() {
        return ServiceContrctTrack_link;
    }

    public void setServiceContractTract(boolean ServiceContractTract) {
        this.ServiceContractTract = ServiceContractTract;
    }

    public boolean isServiceContractTract() {
        return ServiceContractTract;
    }


    public boolean isSvcCalMng() {
        return SvcCalMng;
    }

    public void setSvcCalMng_Link(boolean SvcCalMng_Link) {
        this.SvcCalMng_Link = SvcCalMng_Link;
    }

    public boolean isSvcCalMng_Link() {
        return SvcCalMng_Link;
    }

    public boolean isInvoice_Link() {
        return Invoice_Link;
    }

    public void setInvoice(boolean Invoice) {
        this.Invoice = Invoice;
    }

    public boolean isInvoice() {
        return Invoice;
    }

    public void setServiceContrct_link(boolean ServiceContrct_link) {
        this.ServiceContrct_link = ServiceContrct_link;
    }

    public boolean isServiceContrct_link() {
        return ServiceContrct_link;
    }

    public void setSvcCalTack(boolean SvcCalTack) {
        this.SvcCalTack = SvcCalTack;
    }

    public boolean isSvcCalTack() {
        return SvcCalTack;
    }

    public void setSvcCalTrack_Link(boolean SvcCalTrack_Link) {
        this.SvcCalTrack_Link = SvcCalTrack_Link;
    }

    public boolean isSvcCalTrack_Link() {
        return SvcCalTrack_Link;
    }

    public void setServiceContrat(boolean ServiceContrat) {
        this.ServiceContrat = ServiceContrat;
    }

    public boolean isServiceContrat() {
        return ServiceContrat;
    }


    public ServiceReportBean() {
    }

    /** For from Date & To Date validation. */
    public boolean chkBoxValidation() {
        /*  System.out.println("start date------>>>"+startDate.getValue());
                System.out.println("End date------>>>"+endDate.getValue()); */
        if (startDtPgBind.getValue() == null || startDtPgBind.getValue().toString().length() <= 0) {
            return true;
        } else if (endDtPgBind.getValue() == null || endDtPgBind.getValue().toString().length() <= 0) {
            return true;
        }

        else {
            return false;
        }
    }

    public Object resolvEl(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext).toString();

        return Message;
    }

    public void setStartDtPgBind(RichInputDate startDtPgBind) {
        this.startDtPgBind = startDtPgBind;
    }

    public RichInputDate getStartDtPgBind() {
        return startDtPgBind;
    }

    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void resetAction(ActionEvent actionEvent) {
        getBindings().getOperationBinding("reset").execute();
    }

    public void setEndDtPgBind(RichInputDate endDtPgBind) {
        this.endDtPgBind = endDtPgBind;
    }

    public RichInputDate getEndDtPgBind() {
        return endDtPgBind;
    }

    public void serviceConChkVce(ValueChangeEvent vce) {
        String value = vce.getNewValue().toString();

        if (value.equalsIgnoreCase("TRUE")) {
            if (!chkBoxValidation()) {
                ServiceContrat = true;

            } else {
                FacesMessage message =
                    new FacesMessage("You have not entered Start Date or End Date. Please Select the date.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDtPgBind.getValue() != null && startDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDtPgBind.getClientId(), message);
                }
                if (endDtPgBind.getValue() != null && endDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDtPgBind.getClientId(), message);
                }
            }
            // svcConChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(svcConChkPgBind);
            /* AdfFacesContext.getCurrentInstance().addPartialTarget(startDtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind); */
        } else {
            ServiceContrat = false;
        }
    }

    public void svcExecutiveChkVCE(ValueChangeEvent vce) {
        String value = vce.getNewValue().toString();

        if (value.equalsIgnoreCase("TRUE")) {
            if (!chkBoxValidation()) {
                ServiceExecutive = true;

            } else {
                FacesMessage message =
                    new FacesMessage("You have not entered Start Date or End Date. Please Select the date.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDtPgBind.getValue() != null && startDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDtPgBind.getClientId(), message);
                }
                if (endDtPgBind.getValue() != null && endDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDtPgBind.getClientId(), message);
                }
            }
            // svcConChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(scSvcExecChkPgBind);
            /* AdfFacesContext.getCurrentInstance().addPartialTarget(startDtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind); */
        } else {
            ServiceExecutive = false;
        }
    }

    public void setSvcConChkPgBind(RichSelectBooleanCheckbox svcConChkPgBind) {
        this.svcConChkPgBind = svcConChkPgBind;
    }

    public RichSelectBooleanCheckbox getSvcConChkPgBind() {
        return svcConChkPgBind;
    }

    public void generateReportAction(ActionEvent actionEvent) {
        if (ServiceContrat == true && this.getSvcConChkPgBind().isSelected()) {
            setServiceContrct_link(true);
        } else {
            setServiceContrct_link(false);
        }
        if (ServiceExecutive == true && this.getScSvcExecChkPgBind().isSelected()) {
            setServiceExecutive_link(true);
        } else {
            setServiceExecutive_link(false);
        }
        if (ServiceContractTract == true && this.getScContractTrackChkPgBind().isSelected()) {
            setServiceContrctTrack_link(true);
        } else {
            setServiceContrctTrack_link(false);
        }
        if (Invoice == true && this.getInvChkPgBind().isSelected()) {
            setInvoice_Link(true);
        } else {
            setInvoice_Link(false);
        }

        if (SvcCalMng == true && this.getScmChkPgBind().isSelected()) {
            setSvcCalMng_Link(true);
        } else {
            setSvcCalMng_Link(false);
        }
        if (SvcCalTack == true && this.getScmCalTracChkPgBind().isSelected()) {
            setSvcCalTrack_Link(true);
        } else {
            setSvcCalTrack_Link(false);
        }

        if (WorkOrder == true && this.getWoChkPgBind().isSelected()) {
            setWorkOrder_Link(true);
        } else {
            setWorkOrder_Link(false);
        }
    }

    public void setWorkOrder(boolean WorkOrder) {
        this.WorkOrder = WorkOrder;
    }

    public boolean isWorkOrder() {
        return WorkOrder;
    }

    public void setWorkOrder_Link(boolean WorkOrder_Link) {
        this.WorkOrder_Link = WorkOrder_Link;
    }

    public boolean isWorkOrder_Link() {
        return WorkOrder_Link;
    }

    public void startDtVCE(ValueChangeEvent vce) {
        Object obj = vce.getNewValue();
        this.chngComponentsValues();

        if (obj == null) {
            svcConChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(svcConChkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(svcGoLinkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind);
            setServiceContrct_link(false);
            ServiceContrat = false;

            scSvcExecChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(scSvcExecChkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(scSvcExecGoLinkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind);
            setServiceContrct_link(false);
            ServiceExecutive = false;

            scContractTrackChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getScContractTrackChkPgBind());
            AdfFacesContext.getCurrentInstance().addPartialTarget(scContractTrackGoLinkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind);
            this.ServiceContractTract = false;

            invChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invChkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invGoLinkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind);
            setInvoice_Link(false);
            this.Invoice = false;

            scmChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(scmChkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(scmChkGoLinkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind);
            setSvcCalMng_Link(false);
            this.SvcCalMng = false;

            scmCalTracChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(scmCalTracChkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(scmChkGoLinkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind);
            setSvcCalMng_Link(false);
            this.SvcCalTack = false;

            woChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(woChkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(woGoLinkPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind);
            setWorkOrder_Link(false);
            this.WorkOrder = false;

        } else {
            if (vce != null) {
                if (vce.getNewValue() != null) {
                    endDtPgBind.setValue(null);

                }
            }
        }
    }

    /** For Component Changing. **/
    public void chngComponentsValues() {
        svcConChkPgBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getSvcConChkPgBind());
        AdfFacesContext.getCurrentInstance().addPartialTarget(svcGoLinkPgBind);
        this.ServiceContrat = false;

        scSvcExecChkPgBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getScSvcExecChkPgBind());
        AdfFacesContext.getCurrentInstance().addPartialTarget(scSvcExecGoLinkPgBind);
        this.ServiceExecutive = false;

        scContractTrackChkPgBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getScContractTrackChkPgBind());
        AdfFacesContext.getCurrentInstance().addPartialTarget(scContractTrackGoLinkPgBind);
        this.ServiceContractTract = false;


        invChkPgBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getInvChkPgBind());
        AdfFacesContext.getCurrentInstance().addPartialTarget(invGoLinkPgBind);
        this.Invoice = false;

        scmChkPgBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getScmChkPgBind());
        AdfFacesContext.getCurrentInstance().addPartialTarget(scmChkGoLinkPgBind);
        this.SvcCalMng = false;

        scmCalTracChkPgBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(scmCalTracChkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(scmChkGoLinkPgBind);
        this.SvcCalTack = false;

        woChkPgBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getWoChkPgBind());
        AdfFacesContext.getCurrentInstance().addPartialTarget(woGoLinkPgBind);
        this.WorkOrder = false;

    }

    public void setSvcGoLinkPgBind(RichLink svcGoLinkPgBind) {
        this.svcGoLinkPgBind = svcGoLinkPgBind;
    }

    public RichLink getSvcGoLinkPgBind() {
        return svcGoLinkPgBind;
    }

    public void endDtVCE(ValueChangeEvent vce) {
        Object obj = vce.getNewValue();
        this.chngComponentsValues();

        if (obj == null) {
            svcConChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getSvcConChkPgBind());
            setServiceContrct_link(false);
            ServiceContrat = false;

            /* invChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getInvChkPgBind());
            setInvoice_Link(false);
            Invoice = false; */
        }
    }

    public void setInvChkPgBind(RichSelectBooleanCheckbox invChkPgBind) {
        this.invChkPgBind = invChkPgBind;
    }

    public RichSelectBooleanCheckbox getInvChkPgBind() {
        return invChkPgBind;
    }

    public void setInvGoLinkPgBind(RichLink invGoLinkPgBind) {
        this.invGoLinkPgBind = invGoLinkPgBind;
    }

    public RichLink getInvGoLinkPgBind() {
        return invGoLinkPgBind;
    }

    public void invoiceChkVCE(ValueChangeEvent vce) {
        String value = vce.getNewValue().toString();

        if (value.equalsIgnoreCase("TRUE")) {
            if (!chkBoxValidation()) {
                Invoice = true;

            } else {
                FacesMessage message =
                    new FacesMessage("You have not entered Start Date or End Date. Please Select the date.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDtPgBind.getValue() != null && startDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDtPgBind.getClientId(), message);
                }
                if (endDtPgBind.getValue() != null && endDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDtPgBind.getClientId(), message);
                }
            }
            //invChkPgBind.setValue(false);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(invChkPgBind);
            /* AdfFacesContext.getCurrentInstance().addPartialTarget(startDtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind); */
        } else {
            Invoice = false;
        }
    }

    public void setScmChkPgBind(RichSelectBooleanCheckbox scmChkPgBind) {
        this.scmChkPgBind = scmChkPgBind;
    }

    public RichSelectBooleanCheckbox getScmChkPgBind() {
        return scmChkPgBind;
    }

    public void setScmChkGoLinkPgBind(RichLink scmChkGoLinkPgBind) {
        this.scmChkGoLinkPgBind = scmChkGoLinkPgBind;
    }

    public RichLink getScmChkGoLinkPgBind() {
        return scmChkGoLinkPgBind;
    }

    public void setWoChkPgBind(RichSelectBooleanCheckbox woChkPgBind) {
        this.woChkPgBind = woChkPgBind;
    }

    public RichSelectBooleanCheckbox getWoChkPgBind() {
        return woChkPgBind;
    }

    public void setWoGoLinkPgBind(RichLink woGoLinkPgBind) {
        this.woGoLinkPgBind = woGoLinkPgBind;
    }

    public RichLink getWoGoLinkPgBind() {
        return woGoLinkPgBind;
    }

    public void scmChkVCE(ValueChangeEvent vce) {
        String value = vce.getNewValue().toString();

        if (value.equalsIgnoreCase("TRUE")) {
            if (!chkBoxValidation()) {
                SvcCalMng = true;

            } else {
                FacesMessage message =
                    new FacesMessage("You have not entered Start Date or End Date. Please Select the date.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDtPgBind.getValue() != null && startDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDtPgBind.getClientId(), message);
                }
                if (endDtPgBind.getValue() != null && endDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDtPgBind.getClientId(), message);
                }
            }

        } else {
            SvcCalMng = false;
        }
    }

    public void scmCallTrackVCE(ValueChangeEvent vce) {
        String value = vce.getNewValue().toString();

        if (value.equalsIgnoreCase("TRUE")) {
            if (!chkBoxValidation()) {
                SvcCalTack = true;

            } else {
                FacesMessage message =
                    new FacesMessage("You have not entered Start Date or End Date. Please Select the date.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDtPgBind.getValue() != null && startDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDtPgBind.getClientId(), message);
                }
                if (endDtPgBind.getValue() != null && endDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDtPgBind.getClientId(), message);
                }
            }

        } else {
            SvcCalTack = false;
        }
    }

    public void woChkVCE(ValueChangeEvent vce) {
        String value = vce.getNewValue().toString();

        if (value.equalsIgnoreCase("TRUE")) {
            if (!chkBoxValidation()) {
                WorkOrder = true;

            } else {
                FacesMessage message =
                    new FacesMessage("You have not entered Start Date or End Date. Please Select the date.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDtPgBind.getValue() != null && startDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDtPgBind.getClientId(), message);
                }
                if (endDtPgBind.getValue() != null && endDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDtPgBind.getClientId(), message);

                }
            }
            //invChkPgBind.setValue(false);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(invChkPgBind);
            /* AdfFacesContext.getCurrentInstance().addPartialTarget(startDtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind); */
        } else {
            WorkOrder = false;
        }
    }

    public void setScContrtPgBind(RichInputListOfValues scContrtPgBind) {
        this.scContrtPgBind = scContrtPgBind;
    }

    public RichInputListOfValues getScContrtPgBind() {
        return scContrtPgBind;
    }

    public void setScSvcExecChkPgBind(RichSelectBooleanCheckbox scSvcExecChkPgBind) {
        this.scSvcExecChkPgBind = scSvcExecChkPgBind;
    }

    public RichSelectBooleanCheckbox getScSvcExecChkPgBind() {
        return scSvcExecChkPgBind;
    }

    public void setScSvcExecGoLinkPgBind(RichLink scSvcExecGoLinkPgBind) {
        this.scSvcExecGoLinkPgBind = scSvcExecGoLinkPgBind;
    }

    public RichLink getScSvcExecGoLinkPgBind() {
        return scSvcExecGoLinkPgBind;
    }


    public void setScmCalTracChkPgBind(RichSelectBooleanCheckbox scmCalTracChkPgBind) {
        this.scmCalTracChkPgBind = scmCalTracChkPgBind;
    }

    public RichSelectBooleanCheckbox getScmCalTracChkPgBind() {
        return scmCalTracChkPgBind;
    }

    public void setScmCalTracGoLink(RichLink scmCalTracGoLink) {
        this.scmCalTracGoLink = scmCalTracGoLink;
    }

    public RichLink getScmCalTracGoLink() {
        return scmCalTracGoLink;
    }

    public void setScContractTrackChkPgBind(RichSelectBooleanCheckbox scContractTrackChkPgBind) {
        this.scContractTrackChkPgBind = scContractTrackChkPgBind;
    }

    public RichSelectBooleanCheckbox getScContractTrackChkPgBind() {
        return scContractTrackChkPgBind;
    }

    public void setScContractTrackGoLinkPgBind(RichLink scContractTrackGoLinkPgBind) {
        this.scContractTrackGoLinkPgBind = scContractTrackGoLinkPgBind;
    }

    public RichLink getScContractTrackGoLinkPgBind() {
        return scContractTrackGoLinkPgBind;
    }

    public void scContractTrackVCE(ValueChangeEvent vce) {
        String value = vce.getNewValue().toString();

        if (value.equalsIgnoreCase("TRUE")) {
            if (!chkBoxValidation()) {
                ServiceContractTract = true;

            } else {
                FacesMessage message =
                    new FacesMessage("You have not entered Start Date or End Date. Please Select the date.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (startDtPgBind.getValue() != null && startDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(startDtPgBind.getClientId(), message);
                }
                if (endDtPgBind.getValue() != null && endDtPgBind.getValue().toString().length() != 0) {
                } else {
                    fc.addMessage(endDtPgBind.getClientId(), message);
                }
            }
            // svcConChkPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(scContractTrackChkPgBind);
            /* AdfFacesContext.getCurrentInstance().addPartialTarget(startDtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDtPgBind); */
        } else {
            ServiceContractTract = false;
        }
    }

    public void setRptInfoOnPopUpPgBind(RichMessage rptInfoOnPopUpPgBind) {
        this.rptInfoOnPopUpPgBind = rptInfoOnPopUpPgBind;
    }

    public RichMessage getRptInfoOnPopUpPgBind() {
        return rptInfoOnPopUpPgBind;
    }

    public void downLoadRptVCEChk(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (reportNameTransPgBind.getValue() != null &&
                            reportNameTransPgBind.getValue().toString().length() > 0) {
                            System.out.println("inside true");
                            GenericReport = true;
                        } else {
                            System.out.println("Inside false");
                            genericReportCBPgBind.setValue("fasle");
                            AdfFacesContext.getCurrentInstance().addPartialTarget(genericReportCBPgBind);

                            GenericReport = false;

                            FacesMessage message = new FacesMessage("Please Select Report Name..!!!");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(reportNameTransPgBind.getClientId(), message);
                        }
                    } else {
                        GenericReport = false;
                    }
                } else {
                    GenericReport = false;
                }

    }

    public void setGenericRptNmGoLinkPgBind(RichLink genericRptNmGoLinkPgBind) {
        this.genericRptNmGoLinkPgBind = genericRptNmGoLinkPgBind;
    }

    public RichLink getGenericRptNmGoLinkPgBind() {
        return genericRptNmGoLinkPgBind;
    }

    public void setGenericReport(boolean GenericReport) {
        this.GenericReport = GenericReport;
    }

    public boolean isGenericReport() {
        return GenericReport;
    }

    public void setGenericReport_GoLink(boolean GenericReport_GoLink) {
        this.GenericReport_GoLink = GenericReport_GoLink;
    }

    public boolean isGenericReport_GoLink() {
        return GenericReport_GoLink;
    }

    public void setReportNameTransPgBind(RichInputListOfValues reportNameTransPgBind) {
        this.reportNameTransPgBind = reportNameTransPgBind;
    }

    public RichInputListOfValues getReportNameTransPgBind() {
        return reportNameTransPgBind;
    }

    public void setGenericReportCBPgBind(RichSelectBooleanCheckbox genericReportCBPgBind) {
        this.genericReportCBPgBind = genericReportCBPgBind;
    }

    public RichSelectBooleanCheckbox getGenericReportCBPgBind() {
        return genericReportCBPgBind;
    }

    public void genericRptNmVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
                    //System.out.println("Inside VCL");
                    genericReportCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(genericReportCBPgBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rptInfoOnPopUpPgBind);

                    GenericReport = false;
                    setGenericReport_GoLink(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(genericReportCBPgBind);
                }
    }
    public Map getUsrRptVisible() {
    return new HashMap<Integer, Boolean>() {
        @Override
        public Boolean get(Object key) {
            if (key != null) {
                Boolean retVal = false;
                OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkUsrRptSecAccess");
                opChk.getParamsMap().put("rptId", key);
                opChk.execute();
                if (opChk.getResult() != null)
                    retVal = (Boolean) opChk.getResult();
                return retVal;
            } else
                return true;
        }
    };
    }
}
