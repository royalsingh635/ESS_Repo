package hcmsalaryreportapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class HcmSalaryReportBean {

    Boolean disPaySlip = true;
    Boolean disSalReg = true;
    Boolean disEsiSlip = true;
    Boolean disPfSlip = true;
    Boolean disEmpAtt = true;
    Boolean disDownRept = true;
    Boolean disSalStruct = true;
    Boolean attenval = true;
    Boolean disidreg = true;
    Boolean disctc = true;
    Boolean disPfDet = true;
    Boolean disEsiDet = true;
    Boolean disDailyHours = true;
    Boolean disMonthlySumm = true;
    Boolean disLeaveDet = true;
    Boolean disnhif = true;
    Boolean disnssf = true;
    Boolean disbankCbBind = true;
    Boolean dismpesaCbBind = true;
    Boolean dispayeReturn = true;
    Boolean disEmpList = true;
    Boolean disLeaveBalSummary = true;
    Boolean disHolidayList = true;
    Boolean disSuperAnnuationSummary = true;
    Boolean disLWFReport = true;
    Boolean disBankLetter = true;
    Boolean disSuperAnnuData = true;
    Boolean disGratuity = true;

    Boolean diserlyDepCompWise = true;
    Boolean diserlyDepEmpWise = true;
    Boolean disfullNFinal = true;
    Boolean dislateArrCompWise = true;
    Boolean dislateArrEmpWise = true;
    Boolean dismusterCompWise = true;
    private RichSelectBooleanCheckbox salaryCompnentWiseSummCBBind;

    public void setDisSalaryCompnentWiseSumm(Boolean disSalaryCompnentWiseSumm) {
        this.disSalaryCompnentWiseSumm = disSalaryCompnentWiseSumm;
    }

    public Boolean getDisSalaryCompnentWiseSumm() {
        return disSalaryCompnentWiseSumm;
    }
    Boolean disSalaryCompnentWiseSumm =true;
    
    private RichLink erlyDepartrCBBind;


    public void setDiserlyDepCompWise(Boolean diserlyDepCompWise) {
        this.diserlyDepCompWise = diserlyDepCompWise;
    }

    public Boolean getDiserlyDepCompWise() {
        return diserlyDepCompWise;
    }

    public void setDiserlyDepEmpWise(Boolean diserlyDepEmpWise) {
        this.diserlyDepEmpWise = diserlyDepEmpWise;
    }

    public Boolean getDiserlyDepEmpWise() {
        return diserlyDepEmpWise;
    }

    public void setDisfullNFinal(Boolean disfullNFinal) {
        this.disfullNFinal = disfullNFinal;
    }

    public Boolean getDisfullNFinal() {
        return disfullNFinal;
    }

    public void setDislateArrCompWise(Boolean dislateArrCompWise) {
        this.dislateArrCompWise = dislateArrCompWise;
    }

    public Boolean getDislateArrCompWise() {
        return dislateArrCompWise;
    }

    public void setDislateArrEmpWise(Boolean dislateArrEmpWise) {
        this.dislateArrEmpWise = dislateArrEmpWise;
    }

    public Boolean getDislateArrEmpWise() {
        return dislateArrEmpWise;
    }

    public void setDismusterCompWise(Boolean dismusterCompWise) {
        this.dismusterCompWise = dismusterCompWise;
    }

    public Boolean getDismusterCompWise() {
        return dismusterCompWise;
    }


    public void setDisSuperAnnuData(Boolean disSuperAnnuData) {
        this.disSuperAnnuData = disSuperAnnuData;
    }

    public Boolean getDisSuperAnnuData() {
        return disSuperAnnuData;
    }

    public void setDisGratuity(Boolean disGratuity) {
        this.disGratuity = disGratuity;
    }

    public Boolean getDisGratuity() {
        return disGratuity;
    }

    private RichSelectBooleanCheckbox superAnnuDataCBBind;

    public void setDisLWFReport(Boolean disLWFReport) {
        this.disLWFReport = disLWFReport;
    }

    public Boolean getDisLWFReport() {
        return disLWFReport;
    }

    public void setDisBankLetter(Boolean disBankLetter) {
        this.disBankLetter = disBankLetter;
    }

    public Boolean getDisBankLetter() {
        return disBankLetter;
    }


    private RichInputDate strtDtBinding;
    private RichSelectBooleanCheckbox paySlipCB;
    private RichSelectBooleanCheckbox salRegBinding;
    private RichSelectBooleanCheckbox esiCBBinding;
    private RichSelectBooleanCheckbox pfCbBinding;
    private RichSelectOneChoice crtBinding;
    private RichInputListOfValues crtValBinding;
    private RichInputDate endDtBinding;
    private RichSelectBooleanCheckbox downReptCBBind;
    private RichInputListOfValues rptNmLOVBind;
    private RichSelectBooleanCheckbox empAttCBBind;
    private RichSelectBooleanCheckbox salStructCBBind;
    private RichPopup mailPopBinding;
    private RichSelectManyChoice allEmpNameListBinding;
    private RichInputListOfValues empNmBinding;
    private RichSelectBooleanCheckbox idCBBind;
    private RichSelectBooleanCheckbox ctcCBBind;
    private RichSelectBooleanCheckbox esiDetCBBind;
    private RichSelectBooleanCheckbox pfDetCBBind;
    private RichSelectBooleanCheckbox dailyCBBind;
    private RichSelectBooleanCheckbox monthlySummCBBInd;
    private RichSelectBooleanCheckbox leaveDetCBBind;
    private RichSelectBooleanCheckbox nhifBind;
    private RichSelectBooleanCheckbox nssfBind;
    private RichSelectBooleanCheckbox bankCbBind;
    private RichSelectBooleanCheckbox mpesaCbBind;
    private RichSelectBooleanCheckbox payeReturn;
    private RichSelectBooleanCheckbox empListCBBind;
    private RichSelectBooleanCheckbox holidayListCBBind;
    private RichSelectBooleanCheckbox supeAnnuationSummCBBind;
    private RichSelectBooleanCheckbox lwfCBBind;
    private RichSelectBooleanCheckbox bankLetterCBBind;
    private RichLink superAnnuCBBind;
    private RichSelectBooleanCheckbox gratuityCBBind;


    private RichSelectBooleanCheckbox erlyDepCompWiseCBBind;
    private RichSelectBooleanCheckbox erlyDepEmpWiseCBBind;
    private RichSelectBooleanCheckbox fullNFinalCBBind;
    private RichSelectBooleanCheckbox lateArrCompWiseCBBind;
    private RichSelectBooleanCheckbox lateArrEmpWiseCBBind;
    private RichSelectBooleanCheckbox musterCompWiseCBBind;


    public HcmSalaryReportBean() {
        setCheck();
    }
    Boolean[] repCheck = new Boolean[10];

    public void setRepCheck(Boolean[] repCheck) {
        this.repCheck = repCheck;
    }

    public Boolean[] getRepCheck() {
        return repCheck;
    }
    Boolean pf = false;
    Boolean esi = false;
    Boolean nhif = false;


    public void setDispayeReturn(Boolean dispayeReturn) {
        this.dispayeReturn = dispayeReturn;
    }

    public Boolean getDispayeReturn() {
        return dispayeReturn;
    }
    Boolean nssf = false;
    Boolean mPesa = false;
    Boolean BankCash = false;
    Boolean PayeRet = false;


    public void setCheck() {
        OperationBinding clearListob = ADFBeanUtils.getOperationBinding("checkBox");
        clearListob.execute();
        repCheck = (Boolean[]) clearListob.getResult();
        pf = repCheck[0];
        esi = repCheck[1];
        nhif = repCheck[2];
        nssf = repCheck[3];
        mPesa = repCheck[4];
        BankCash = repCheck[5];
        PayeRet = repCheck[6];

    }

    public void setPayeRet(Boolean PayeRet) {
        this.PayeRet = PayeRet;
    }

    public Boolean getPayeRet() {
        return PayeRet;
    }


    public void setMPesa(Boolean mPesa) {
        this.mPesa = mPesa;
    }

    public Boolean getMPesa() {
        return mPesa;
    }

    public void setBankCash(Boolean BankCash) {
        this.BankCash = BankCash;
    }

    public Boolean getBankCash() {
        return BankCash;
    }

    public void setPf(Boolean pf) {
        this.pf = pf;
    }

    public Boolean getPf() {
        return pf;
    }

    public void setEsi(Boolean esi) {
        this.esi = esi;
    }

    public Boolean getEsi() {
        return esi;
    }

    public void setNhif(Boolean nhif) {
        this.nhif = nhif;
    }

    public Boolean getNhif() {
        return nhif;
    }

    public void setNssf(Boolean nssf) {
        this.nssf = nssf;
    }

    public Boolean getNssf() {
        return nssf;
    }

    public void setDisbankCbBind(Boolean disbankCbBind) {
        this.disbankCbBind = disbankCbBind;
    }

    public Boolean getDisbankCbBind() {
        return disbankCbBind;
    }

    public void setDismpesaCbBind(Boolean dismpesaCbBind) {
        this.dismpesaCbBind = dismpesaCbBind;
    }

    public Boolean getDismpesaCbBind() {
        return dismpesaCbBind;
    }

    public void setDisSuperAnnuationSummary(Boolean disSuperAnnuationSummary) {
        this.disSuperAnnuationSummary = disSuperAnnuationSummary;
    }

    public Boolean getDisSuperAnnuationSummary() {
        return disSuperAnnuationSummary;
    }


    public void setDisLeaveBalSummary(Boolean disLeaveBalSummary) {
        this.disLeaveBalSummary = disLeaveBalSummary;
    }

    public Boolean getDisLeaveBalSummary() {
        return disLeaveBalSummary;
    }
    private RichSelectBooleanCheckbox leaveBalSumCBBind;

    public void setDisEmpList(Boolean disEmpList) {
        this.disEmpList = disEmpList;
    }

    public Boolean getDisEmpList() {
        return disEmpList;
    }

    public void setDisHolidayList(Boolean disHolidayList) {
        this.disHolidayList = disHolidayList;
    }

    public Boolean getDisHolidayList() {
        return disHolidayList;
    }


    public String generateReportAction() {
        if (crtBinding.getValue() != null && crtBinding.getValue().toString().length() > 0) {
            if (crtValBinding.getValue() != null && crtValBinding.getValue().toString().length() > 0) {
                //if(strtDtBinding.getValue()!= null && strtDtBinding.getValue().toString().length()>0){
                //  if(endDtBinding.getValue()!= null && endDtBinding.getValue().toString().length()>0){

                if (paySlipCB.getValue() != null && paySlipCB.getValue().toString().equals("true"))
                    disPaySlip = false;
                else
                    disPaySlip = true;
                if (salRegBinding.getValue() != null && salRegBinding.getValue().toString().equals("true"))
                    disSalReg = false;
                else
                    disSalReg = true;
                if (esiCBBinding.getValue() != null && esiCBBinding.getValue().toString().equals("true"))
                    disEsiSlip = false;
                else
                    disEsiSlip = true;
                if (pfCbBinding.getValue() != null && pfCbBinding.getValue().toString().equals("true"))
                    disPfSlip = false;
                else
                    disPfSlip = true;
                if (payeReturn.getValue() != null && payeReturn.getValue().toString().equals("true"))
                    dispayeReturn = false;
                else
                    dispayeReturn = true;
               
                if (empAttCBBind.getValue() != null && empAttCBBind.getValue().toString().equals("true"))
                {
                    if (strtDtBinding.getValue() != null && strtDtBinding.getValue().toString().length() > 0) {
                        if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0) {
                            System.out.println("before call function");
                            ADFBeanUtils.getOperationBinding("insertDataByDailyHoursLink").execute();
                            disEmpAtt = false;
                        } else {
                            FacesMessage message = new FacesMessage("Please Select End Date.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(endDtBinding.getClientId(), message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage("Please Select Start Date.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(strtDtBinding.getClientId(), message);
                    }

                    
                } else {
                    disEmpAtt = true;
                }
                
                
                
                
                if (downReptCBBind.getValue() != null && downReptCBBind.getValue().toString().equals("true"))
                    disDownRept = false;
                else
                    disDownRept = true;
                if (salStructCBBind.getValue() != null && salStructCBBind.getValue().toString().equals("true"))
                    disSalStruct = false;
                else
                    disSalStruct = true;
                if (idCBBind.getValue() != null && idCBBind.getValue().toString().equals("true"))
                    disidreg = false;
                else
                    disidreg = true;
                if (ctcCBBind.getValue() != null && ctcCBBind.getValue().toString().equals("true"))
                    disctc = false;
                else
                    disctc = true;
                if (esiDetCBBind.getValue() != null && esiDetCBBind.getValue().toString().equals("true"))
                    disEsiDet = false;
                else
                    disEsiDet = true;
                if (pfDetCBBind.getValue() != null && pfDetCBBind.getValue().toString().equals("true"))
                    disPfDet = false;
                else
                    disPfDet = true;
                if (dailyCBBind.getValue() != null && dailyCBBind.getValue().toString().equals("true")) {
                    if (strtDtBinding.getValue() != null && strtDtBinding.getValue().toString().length() > 0) {
                        if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0) {
                            ADFBeanUtils.getOperationBinding("insertDataByDailyHoursLink").execute();
                            disDailyHours = false;
                        } else {
                            FacesMessage message = new FacesMessage("Please Select End Date.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(endDtBinding.getClientId(), message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage("Please Select Start Date.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(strtDtBinding.getClientId(), message);
                    }
                    
                } else
                    disDailyHours = true;
                
                
                if (monthlySummCBBInd.getValue() != null && monthlySummCBBInd.getValue().toString().equals("true"))
                    disMonthlySumm = false;
                else
                    disMonthlySumm = true;
                if (nhifBind.getValue() != null && nhifBind.getValue().toString().equals("true"))
                    disnhif = false;
                else
                    disnhif = true;
                if (nssfBind.getValue() != null && nssfBind.getValue().toString().equals("true"))
                    disnssf = false;
                else
                    disnssf = true;
                if (leaveDetCBBind.getValue() != null && leaveDetCBBind.getValue().toString().equals("true"))
                    disLeaveDet = false;
                else
                    disLeaveDet = true;
                if (mpesaCbBind.getValue() != null && mpesaCbBind.getValue().toString().equals("true"))
                    dismpesaCbBind = false;
                else
                    dismpesaCbBind = true;
                if (bankCbBind.getValue() != null && bankCbBind.getValue().toString().equals("true"))
                    disbankCbBind = false;
                else
                    disbankCbBind = true;

                //EmployeeList
                if (empListCBBind.getValue() != null && empListCBBind.getValue().toString().equals("true"))
                    disEmpList = false;
                else
                    disEmpList = true;

                //Holidaylist
                if (holidayListCBBind.getValue() != null && holidayListCBBind.getValue().toString().equals("true"))
                    disHolidayList = false;
                else
                    disHolidayList = true;

                //Leave Balance Summary
                if (leaveBalSumCBBind.getValue() != null && leaveBalSumCBBind.getValue().toString().equals("true"))
                    disLeaveBalSummary = false;
                else
                    disLeaveBalSummary = true;


                // For SuperAnnuation Summary

                if (supeAnnuationSummCBBind.getValue() != null &&
                    supeAnnuationSummCBBind.getValue().toString().equals("true"))
                    disSuperAnnuationSummary = false;
                else
                    disSuperAnnuationSummary = true;

                // For LWF Report

                if (lwfCBBind.getValue() != null && lwfCBBind.getValue().toString().equals("true"))
                    disLWFReport = false;
                else
                    disLWFReport = true;

                //For Bank Letter
                if (bankLetterCBBind.getValue() != null && bankLetterCBBind.getValue().toString().equals("true"))
                    disBankLetter = false;
                else
                    disBankLetter = true;
                //For SuperAnuation Data
                if (superAnnuDataCBBind.getValue() != null && superAnnuDataCBBind.getValue().toString().equals("true"))
                    disSuperAnnuData = false;
                else
                    disSuperAnnuData = true;

                // For Gratuity Report
                if (gratuityCBBind.getValue() != null && gratuityCBBind.getValue().toString().equals("true"))
                    disGratuity = false;
                else
                    disGratuity = true;

                //Early Departure Employee Wise
                if (erlyDepCompWiseCBBind.getValue() != null &&
                    erlyDepCompWiseCBBind.getValue().toString().equals("true"))
                    diserlyDepCompWise = false;
                else
                    diserlyDepCompWise = true;

                // Full and final
                if (fullNFinalCBBind.getValue() != null && fullNFinalCBBind.getValue().toString().equals("true"))
                    disfullNFinal = false;
                else
                    disfullNFinal = true;

                // Late Arrival Compny wise
                if (lateArrCompWiseCBBind.getValue() != null &&lateArrCompWiseCBBind.getValue().toString().equals("true"))
                {
                    if (strtDtBinding.getValue() != null && strtDtBinding.getValue().toString().length() > 0) {
                        if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0) {
                            ADFBeanUtils.getOperationBinding("insertDataByDailyHoursLink").execute();
                            dislateArrCompWise = false;
                        } else {
                            FacesMessage message = new FacesMessage("Please Select End Date.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(endDtBinding.getClientId(), message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage("Please Select Start Date.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(strtDtBinding.getClientId(), message);
                    }

                  
                  
                }
               else
                    dislateArrCompWise = true;

                // Late Arrival Employee Wise (Not in Use)

                if (lateArrEmpWiseCBBind.getValue() != null &&
                    lateArrEmpWiseCBBind.getValue().toString().equals("true"))
                    dislateArrEmpWise = false;
                else
                    dislateArrEmpWise = true;

                //Muster Report Compny wise
                if (musterCompWiseCBBind.getValue() != null &&
                    musterCompWiseCBBind.getValue().toString().equals("true"))
                {   
                    if (strtDtBinding.getValue() != null && strtDtBinding.getValue().toString().length() > 0) {
                        if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0) {
                            ADFBeanUtils.getOperationBinding("insertDataByDailyHoursLink").execute();
                            dismusterCompWise = false;
                        } else {
                            FacesMessage message = new FacesMessage("Please Select End Date.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(endDtBinding.getClientId(), message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage("Please Select Start Date.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(strtDtBinding.getClientId(), message);
                    }

                      
                }
                else
                    dismusterCompWise = true;
                
                
                //Salary Component Wise Summary
                if (salaryCompnentWiseSummCBBind.getValue() != null &&
                   salaryCompnentWiseSummCBBind.getValue().toString().equals("true"))
                    disSalaryCompnentWiseSumm = false;
                else
                    disSalaryCompnentWiseSumm = true;
                         


                /* }
            else
            {
                    FacesMessage message = new FacesMessage("Please Select End Date.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(endDtBinding.getClientId(), message);
                }
        }
           else
            {
                        FacesMessage message = new FacesMessage("Please Select Start Date.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(strtDtBinding.getClientId(), message);
                }*/
            } else {
                FacesMessage message = new FacesMessage("Please Select Criteria Value.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(crtValBinding.getClientId(), message);
            }
        } else {
            FacesMessage message = new FacesMessage("Please Select Criteria.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(crtBinding.getClientId(), message);
        }
        return null;
    }

    public void setDisnhif(Boolean disnhif) {
        this.disnhif = disnhif;
    }

    public Boolean getDisnhif() {
        return disnhif;
    }

    public void setDisnssf(Boolean disnssf) {
        this.disnssf = disnssf;
    }

    public Boolean getDisnssf() {
        return disnssf;
    }

    public void setDisLeaveDet(Boolean disLeaveDet) {
        this.disLeaveDet = disLeaveDet;
    }

    public Boolean getDisLeaveDet() {
        return disLeaveDet;
    }

    public void setDisEmpAtt(Boolean disEmpAtt) {
        this.disEmpAtt = disEmpAtt;
    }

    public Boolean getDisEmpAtt() {
        return disEmpAtt;
    }

    public void setDisDownRept(Boolean disDownRept) {
        this.disDownRept = disDownRept;
    }

    public Boolean getDisDownRept() {
        return disDownRept;
    }


    public void endDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (strtDtBinding.getValue() != null && strtDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) strtDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        FacesMessage message = new FacesMessage("End Date can not be before Start Date.");
                        throw new ValidatorException(message);
                    }
                }
            }
        }
    }

    public void setSalRegBinding(RichSelectBooleanCheckbox salRegBinding) {
        this.salRegBinding = salRegBinding;
    }

    public RichSelectBooleanCheckbox getSalRegBinding() {
        return salRegBinding;
    }

    public void setEsiCBBinding(RichSelectBooleanCheckbox esiCBBinding) {
        this.esiCBBinding = esiCBBinding;
    }

    public RichSelectBooleanCheckbox getEsiCBBinding() {
        return esiCBBinding;
    }

    public void setPfCbBinding(RichSelectBooleanCheckbox pfCbBinding) {
        this.pfCbBinding = pfCbBinding;
    }

    public RichSelectBooleanCheckbox getPfCbBinding() {
        return pfCbBinding;
    }

    public void setCrtBinding(RichSelectOneChoice crtBinding) {
        this.crtBinding = crtBinding;
    }

    public RichSelectOneChoice getCrtBinding() {
        return crtBinding;
    }

    public void setCrtValBinding(RichInputListOfValues crtValBinding) {
        this.crtValBinding = crtValBinding;
    }

    public RichInputListOfValues getCrtValBinding() {
        return crtValBinding;
    }

    public void setEndDtBinding(RichInputDate endDtBinding) {
        this.endDtBinding = endDtBinding;
    }

    public RichInputDate getEndDtBinding() {
        return endDtBinding;
    }

    public void setDisSalReg(Boolean disSalReg) {
        this.disSalReg = disSalReg;
    }

    public Boolean getDisSalReg() {
        return disSalReg;
    }

    public void setDisEsiSlip(Boolean disEsiSlip) {
        this.disEsiSlip = disEsiSlip;
    }

    public Boolean getDisEsiSlip() {
        return disEsiSlip;
    }

    public void setDisPfSlip(Boolean disPfSlip) {
        this.disPfSlip = disPfSlip;
    }

    public Boolean getDisPfSlip() {
        return disPfSlip;
    }

    public void setDisPaySlip(Boolean disPaySlip) {
        this.disPaySlip = disPaySlip;
    }

    public Boolean getDisPaySlip() {
        return disPaySlip;
    }

    public void setPaySlipCB(RichSelectBooleanCheckbox paySlipCB) {
        this.paySlipCB = paySlipCB;
    }

    public RichSelectBooleanCheckbox getPaySlipCB() {
        return paySlipCB;
    }

    public void setStrtDtBinding(RichInputDate strtDtBinding) {
        this.strtDtBinding = strtDtBinding;
    }

    public RichInputDate getStrtDtBinding() {
        return strtDtBinding;
    }

    public void setDownReptCBBind(RichSelectBooleanCheckbox downReptCBBind) {
        this.downReptCBBind = downReptCBBind;
    }

    public RichSelectBooleanCheckbox getDownReptCBBind() {
        return downReptCBBind;
    }

    public void DownReptCBVCL(ValueChangeEvent valueChangeEvent) {

        JUCtrlListBinding listBindings = (JUCtrlListBinding) ADFBeanUtils.getBindingContainer().get("AllEmpNmForMail1");
        listBindings.clearSelectedIndices();
        OperationBinding clearListob = ADFBeanUtils.getOperationBinding("clearEmployeMailList");
        clearListob.execute();
        ADFBeanUtils.getOperationBinding("Execute").execute();
        empNmBinding.setValue("");
    }

    public void setRptNmLOVBind(RichInputListOfValues rptNmLOVBind) {
        this.rptNmLOVBind = rptNmLOVBind;
    }

    public RichInputListOfValues getRptNmLOVBind() {
        return rptNmLOVBind;
    }

    public void setEmpAttCBBind(RichSelectBooleanCheckbox empAttCBBind) {
        this.empAttCBBind = empAttCBBind;
    }

    public RichSelectBooleanCheckbox getEmpAttCBBind() {
        return empAttCBBind;
    }

    public void setSalStructCBBind(RichSelectBooleanCheckbox salStructCBBind) {
        this.salStructCBBind = salStructCBBind;
    }

    public RichSelectBooleanCheckbox getSalStructCBBind() {
        return salStructCBBind;
    }

    public void setDisSalStruct(Boolean disSalStruct) {
        this.disSalStruct = disSalStruct;
    }

    public Boolean getDisSalStruct() {
        return disSalStruct;
    }

    public void CriteriaLOVVCL(ValueChangeEvent vce) {

        JUCtrlListBinding listBindings = (JUCtrlListBinding) ADFBeanUtils.getBindingContainer().get("AllEmpNmForMail1");
        listBindings.clearSelectedIndices();
        OperationBinding clearListob = ADFBeanUtils.getOperationBinding("clearEmployeMailList");
        clearListob.execute();
        ADFBeanUtils.getOperationBinding("Execute").execute();
    }

    public void CrtIdVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equals("54")) {
                attenval = true;
            } else {
                attenval = false;
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(empAttCBBind);
            System.out.println(vce.getNewValue());
            System.out.println("empatt" + disEmpAtt);

            System.out.println(attenval);

        }

        JUCtrlListBinding listBindings = (JUCtrlListBinding) ADFBeanUtils.getBindingContainer().get("AllEmpNmForMail1");
        listBindings.clearSelectedIndices();
        OperationBinding clearListob = ADFBeanUtils.getOperationBinding("clearEmployeMailList");
        clearListob.execute();
        ADFBeanUtils.getOperationBinding("Execute").execute();
    }

    public void setAttenval(Boolean attenval) {
        this.attenval = attenval;
    }

    public Boolean getAttenval() {
        return attenval;
    }


    public void openMailPop(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("setCriteraValuesInLov");
        ob.execute();
        //showPopup(mailPopBinding, true);
    }

    public void setMailPopBinding(RichPopup mailPopBinding) {
        this.mailPopBinding = mailPopBinding;
    }

    public RichPopup getMailPopBinding() {
        return mailPopBinding;
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

    public void sendMailToSelectedEmployee(ActionEvent actionEvent) {
        StringBuilder msg = null;
        JUCtrlListBinding listBindings = (JUCtrlListBinding) ADFBeanUtils.getBindingContainer().get("AllEmpNmForMail1");
        Object str[] = listBindings.getSelectedValues();
        ArrayList list = new ArrayList();
        if (str != null && str.length > 0) {
            for (int i = 0; i < str.length; i++) {
                list.add(str[i]);
            }
            OperationBinding ob = ADFBeanUtils.getOperationBinding("sendMailToEmployee");
            ob.getParamsMap().put("empCodeList", list);
            ob.execute();
            if (ob.getResult() != null && ob.getErrors().isEmpty()) {

                HashMap<String, ArrayList> empDetail = (HashMap<String, ArrayList>) ob.getResult();
                if (empDetail.size() > 0) {
                    if (empDetail.containsKey("successResult"))
                        System.out.println(empDetail.get("successResult"));
                    if (empDetail.containsKey("fail")) {
                        ArrayList failureList = empDetail.get("fail");
                        if (failureList != null && failureList.size() > 0) {

                            msg =
                                new StringBuilder("<html><body><b><p style='color:red'>Email not send to the following  recipient.Please check the records.</p></b>");
                            msg.append("<ul>");
                            ListIterator li = failureList.listIterator();
                            while (li.hasNext()) {
                                msg.append("<li> <b>" + li.next().toString() + "</b></li>");
                            }
                        } else {
                            msg =
                                new StringBuilder("<html><body><b><p style='color:green'>Mail send successfully to all recipient.</p></b>");
                            msg.append("<ul>");
                        }
                    }

                    msg.append("</body></html>");
                    FacesMessage facmsg = new FacesMessage(msg.toString());
                    FacesContext.getCurrentInstance().addMessage(null, facmsg);
                    listBindings.clearSelectedIndices();
                    OperationBinding clearListob = ADFBeanUtils.getOperationBinding("clearEmployeMailList");
                    clearListob.execute();
                    ADFBeanUtils.getOperationBinding("Execute").execute();

                }
            }
        } else {
            msg =
                new StringBuilder("<html><body><b><p style='color:red'>Missing Mail Recipient.Not able to send mail.</p></b>");
            msg.append("<ul>");
            msg.append("</body></html>");
            FacesMessage facmsg = new FacesMessage(msg.toString());
            FacesContext.getCurrentInstance().addMessage(null, facmsg);
        }

        //mailPopBinding.hide();
    }

    public void closeMailPopup(ActionEvent actionEvent) {
        OperationBinding clearListob = ADFBeanUtils.getOperationBinding("clearEmployeMailList");
        clearListob.execute();
        ADFBeanUtils.getOperationBinding("Execute").execute();
        mailPopBinding.hide();
    }

    public void setAllEmpNameListBinding(RichSelectManyChoice allEmpNameListBinding) {
        this.allEmpNameListBinding = allEmpNameListBinding;
    }

    public RichSelectManyChoice getAllEmpNameListBinding() {
        return allEmpNameListBinding;
    }

    public void crtValNmVCL(ValueChangeEvent valueChangeEvent) {
        JUCtrlListBinding listBindings = (JUCtrlListBinding) ADFBeanUtils.getBindingContainer().get("AllEmpNmForMail1");
        listBindings.clearSelectedIndices();
        OperationBinding clearListob = ADFBeanUtils.getOperationBinding("clearEmployeMailList");
        clearListob.execute();
        ADFBeanUtils.getOperationBinding("Execute").execute();
        empNmBinding.setValue("");
    }

    public void setEmpNmBinding(RichInputListOfValues empNmBinding) {
        this.empNmBinding = empNmBinding;
    }

    public RichInputListOfValues getEmpNmBinding() {
        return empNmBinding;
    }

    public void setIdCBBind(RichSelectBooleanCheckbox idCBBind) {
        this.idCBBind = idCBBind;
    }

    public RichSelectBooleanCheckbox getIdCBBind() {
        return idCBBind;
    }

    public void setCtcCBBind(RichSelectBooleanCheckbox ctcCBBind) {
        this.ctcCBBind = ctcCBBind;
    }

    public RichSelectBooleanCheckbox getCtcCBBind() {
        return ctcCBBind;
    }

    public void setEsiDetCBBind(RichSelectBooleanCheckbox esiDetCBBind) {
        this.esiDetCBBind = esiDetCBBind;
    }

    public RichSelectBooleanCheckbox getEsiDetCBBind() {
        return esiDetCBBind;
    }

    public void setPfDetCBBind(RichSelectBooleanCheckbox pfDetCBBind) {
        this.pfDetCBBind = pfDetCBBind;
    }

    public void setDisidreg(Boolean disidreg) {
        this.disidreg = disidreg;
    }

    public Boolean getDisidreg() {
        return disidreg;
    }

    public void setDisctc(Boolean disctc) {
        this.disctc = disctc;
    }

    public Boolean getDisctc() {
        return disctc;
    }

    public void setDisPfDet(Boolean disPfDet) {
        this.disPfDet = disPfDet;
    }

    public Boolean getDisPfDet() {
        return disPfDet;
    }

    public void setDisEsiDet(Boolean disEsiDet) {
        this.disEsiDet = disEsiDet;
    }

    public Boolean getDisEsiDet() {
        return disEsiDet;
    }

    public void setDisDailyHours(Boolean disDailyHours) {
        this.disDailyHours = disDailyHours;
    }

    public Boolean getDisDailyHours() {
        return disDailyHours;
    }

    public void setDisMonthlySumm(Boolean disMonthlySumm) {
        this.disMonthlySumm = disMonthlySumm;
    }

    public Boolean getDisMonthlySumm() {
        return disMonthlySumm;
    }

    public RichSelectBooleanCheckbox getPfDetCBBind() {
        return pfDetCBBind;
    }

    public void setDailyCBBind(RichSelectBooleanCheckbox dailyCBBind) {
        this.dailyCBBind = dailyCBBind;
    }

    public RichSelectBooleanCheckbox getDailyCBBind() {
        return dailyCBBind;
    }

    public void setMonthlySummCBBInd(RichSelectBooleanCheckbox monthlySummCBBInd) {
        this.monthlySummCBBInd = monthlySummCBBInd;
    }

    public RichSelectBooleanCheckbox getMonthlySummCBBInd() {
        return monthlySummCBBInd;
    }

    public void setLeaveDetCBBind(RichSelectBooleanCheckbox leaveDetCBBind) {
        this.leaveDetCBBind = leaveDetCBBind;
    }

    public RichSelectBooleanCheckbox getLeaveDetCBBind() {
        return leaveDetCBBind;
    }

    public void setNhifBind(RichSelectBooleanCheckbox nhifBind) {
        this.nhifBind = nhifBind;
    }

    public RichSelectBooleanCheckbox getNhifBind() {
        return nhifBind;
    }

    public void setNssfBind(RichSelectBooleanCheckbox nssfBind) {
        this.nssfBind = nssfBind;
    }

    public RichSelectBooleanCheckbox getNssfBind() {
        return nssfBind;
    }

    public void setBankCbBind(RichSelectBooleanCheckbox bankCbBind) {
        this.bankCbBind = bankCbBind;
    }

    public RichSelectBooleanCheckbox getBankCbBind() {
        return bankCbBind;
    }

    public void setMpesaCbBind(RichSelectBooleanCheckbox mpesaCbBind) {
        this.mpesaCbBind = mpesaCbBind;
    }

    public RichSelectBooleanCheckbox getMpesaCbBind() {
        return mpesaCbBind;
    }

    public void setPayeReturn(RichSelectBooleanCheckbox payeReturn) {
        this.payeReturn = payeReturn;
    }

    public RichSelectBooleanCheckbox getPayeReturn() {
        return payeReturn;
    }

    public void setEmpListCBBind(RichSelectBooleanCheckbox empListCBBind) {
        this.empListCBBind = empListCBBind;
    }

    public RichSelectBooleanCheckbox getEmpListCBBind() {
        return empListCBBind;
    }

    public void setHolidayListCBBind(RichSelectBooleanCheckbox holidayListCBBind) {
        this.holidayListCBBind = holidayListCBBind;
    }

    public RichSelectBooleanCheckbox getHolidayListCBBind() {
        return holidayListCBBind;
    }

    public void setLeaveBalSumCBBind(RichSelectBooleanCheckbox leaveBalSumCBBind) {
        this.leaveBalSumCBBind = leaveBalSumCBBind;
    }

    public RichSelectBooleanCheckbox getLeaveBalSumCBBind() {
        return leaveBalSumCBBind;
    }

    public void setSupeAnnuationSummCBBind(RichSelectBooleanCheckbox supeAnnuationSummCBBind) {
        this.supeAnnuationSummCBBind = supeAnnuationSummCBBind;
    }

    public RichSelectBooleanCheckbox getSupeAnnuationSummCBBind() {
        return supeAnnuationSummCBBind;
    }

    public void setLwfCBBind(RichSelectBooleanCheckbox lwfCBBind) {
        this.lwfCBBind = lwfCBBind;
    }

    public RichSelectBooleanCheckbox getLwfCBBind() {
        return lwfCBBind;
    }

    public void setBankLetterCBBind(RichSelectBooleanCheckbox bankLetterCBBind) {
        this.bankLetterCBBind = bankLetterCBBind;
    }

    public RichSelectBooleanCheckbox getBankLetterCBBind() {
        return bankLetterCBBind;
    }

    public void setSuperAnnuDataCBBind(RichSelectBooleanCheckbox superAnnuDataCBBind) {
        this.superAnnuDataCBBind = superAnnuDataCBBind;
    }

    public RichSelectBooleanCheckbox getSuperAnnuDataCBBind() {
        return superAnnuDataCBBind;
    }

    public void setSuperAnnuCBBind(RichLink superAnnuCBBind) {
        this.superAnnuCBBind = superAnnuCBBind;
    }

    public RichLink getSuperAnnuCBBind() {
        return superAnnuCBBind;
    }

    public void setGratuityCBBind(RichSelectBooleanCheckbox gratuityCBBind) {
        this.gratuityCBBind = gratuityCBBind;
    }

    public RichSelectBooleanCheckbox getGratuityCBBind() {
        return gratuityCBBind;
    }

    public void setErlyDepCompWiseCBBind(RichSelectBooleanCheckbox erlyDepCompWiseCBBind) {
        this.erlyDepCompWiseCBBind = erlyDepCompWiseCBBind;
    }

    public RichSelectBooleanCheckbox getErlyDepCompWiseCBBind() {
        return erlyDepCompWiseCBBind;
    }

    public void setErlyDepEmpWiseCBBind(RichSelectBooleanCheckbox erlyDepEmpWiseCBBind) {
        this.erlyDepEmpWiseCBBind = erlyDepEmpWiseCBBind;
    }

    public RichSelectBooleanCheckbox getErlyDepEmpWiseCBBind() {
        return erlyDepEmpWiseCBBind;
    }

    public void setFullNFinalCBBind(RichSelectBooleanCheckbox fullNFinalCBBind) {
        this.fullNFinalCBBind = fullNFinalCBBind;
    }

    public RichSelectBooleanCheckbox getFullNFinalCBBind() {
        return fullNFinalCBBind;
    }

    public void setLateArrCompWiseCBBind(RichSelectBooleanCheckbox lateArrCompWiseCBBind) {
        this.lateArrCompWiseCBBind = lateArrCompWiseCBBind;
    }

    public RichSelectBooleanCheckbox getLateArrCompWiseCBBind() {
        return lateArrCompWiseCBBind;
    }

    public void setLateArrEmpWiseCBBind(RichSelectBooleanCheckbox lateArrEmpWiseCBBind) {
        this.lateArrEmpWiseCBBind = lateArrEmpWiseCBBind;
    }

    public RichSelectBooleanCheckbox getLateArrEmpWiseCBBind() {
        return lateArrEmpWiseCBBind;
    }

    public void setMusterCompWiseCBBind(RichSelectBooleanCheckbox musterCompWiseCBBind) {
        this.musterCompWiseCBBind = musterCompWiseCBBind;
    }

    public RichSelectBooleanCheckbox getMusterCompWiseCBBind() {
        return musterCompWiseCBBind;
    }

    public void setErlyDepartrCBBind(RichLink erlyDepartrCBBind) {
        this.erlyDepartrCBBind = erlyDepartrCBBind;
    }

    public RichLink getErlyDepartrCBBind() {
        return erlyDepartrCBBind;
    }

    public void dailyHoursLinkACE(ActionEvent actionEvent) {
        /*  if (crtValBinding.getValue() != null) {
            if (strtDtBinding.getValue() != null) {
                if (endDtBinding.getValue() != null) {

                    ADFBeanUtils.getOperationBinding("insertDataByDailyHoursLink").execute();
                } else {
                    FacesMessage message = new FacesMessage("Please Select End Date.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(endDtBinding.getClientId(), message);
                }
            } else {
                FacesMessage message = new FacesMessage("Please Select From Date.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(strtDtBinding.getClientId(), message);
            }
        } else {
            FacesMessage message = new FacesMessage("Please Select Criteria Value.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(crtValBinding.getClientId(), message);
        }*/

    }

    public void setSalaryCompnentWiseSummCBBind(RichSelectBooleanCheckbox salaryCompnentWiseSummCBBind) {
        this.salaryCompnentWiseSummCBBind = salaryCompnentWiseSummCBBind;
    }

    public RichSelectBooleanCheckbox getSalaryCompnentWiseSummCBBind() {
        return salaryCompnentWiseSummCBBind;
    }
}
