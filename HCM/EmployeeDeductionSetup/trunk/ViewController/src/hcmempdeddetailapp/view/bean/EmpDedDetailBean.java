package hcmempdeddetailapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.Calendar;

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

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class EmpDedDetailBean
{
    private RichInputListOfValues pagebindEmplGrp;
    private RichInputListOfValues pagebindEmplNam;
    private RichInputText pagebindtransGrpId;
    private RichSelectOneChoice pagebindtaxname;
    private RichSelectOneRadio pagebindDedPrfFacet;
    private RichInputDate pagebindTaxStartDate;
    private RichPopup pagebindpop;
    private RichInputDate pagebindDedStartDate;
    private RichInputDate pagebindDedEndDate;
    private RichInputDate pagebindTaxEndDate;
    private RichSelectOneChoice pagebinddedname;
    private RichInputText pagebindtaxamount;
    private RichInputDate pagebinddeddocumentdate;
    private RichInputText pagebinddeddocumentno;
    private String pass = "";
    private String refAmntMode = "N";
    private RichSelectBooleanCheckbox pfOnActualBasicBinding;
    private RichSelectOneChoice vpfChkBinding;
    private RichInputText vpfAmtBinding;

    public void setRefAmntMode(String refAmntMode)
    {
        this.refAmntMode = refAmntMode;
    }

    public String getRefAmntMode()
    {
        return refAmntMode;
    }

    public EmpDedDetailBean()
    {
    }

    public void searchEmpName(ActionEvent actionEvent)
    {
        // Add event code here...
        //Integer.parseInt((pagebindtransGrpId.getValue()).toString());
        System.out.println(pagebindtransGrpId.getValue());
        System.out.println(pagebindEmplNam.getValue());
        OperationBinding ob = ADFBeanUtils.getOperationBinding("searchEmpNamAm");
        //        ob.getParamsMap().put("grpId",pagebindtransGrpId.getValue() );
        //        ob.getParamsMap().put("emplNam",pagebindEmplNam.getValue());
        ob.execute();
        System.out.println(ob.getErrors());
    }

    public void setPagebindEmplGrp(RichInputListOfValues pagebindEmplGrp)
    {
        this.pagebindEmplGrp = pagebindEmplGrp;
    }

    public RichInputListOfValues getPagebindEmplGrp()
    {
        return pagebindEmplGrp;
    }

    public void setPagebindEmplNam(RichInputListOfValues pagebindEmplNam)
    {
        this.pagebindEmplNam = pagebindEmplNam;
    }

    public RichInputListOfValues getPagebindEmplNam()
    {
        return pagebindEmplNam;
    }

    public void setPagebindtransGrpId(RichInputText pagebindtransGrpId)
    {
        this.pagebindtransGrpId = pagebindtransGrpId;
    }

    public RichInputText getPagebindtransGrpId()
    {
        return pagebindtransGrpId;
    }


    public void addEmplDed(ActionEvent actionEvent)
    {
        OperationBinding ob1 = ADFBeanUtils.getOperationBinding("chkFrSalryComp");
        ob1.execute();
        if (ob1.getErrors().isEmpty() && ob1.getResult().toString().equals("Y"))
        {
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
            OperationBinding ob2 = ADFBeanUtils.getOperationBinding("CreateInsert");
            ob2.execute();
        }
        else
        {
            String mesg = "This employee don't have any salary component:Please add salary component for deduction.";
            showFacesMessage(mesg, "E", false, "F");
        }

    }

    public void cancelEmpDed(ActionEvent actionEvent)
    {

        OperationBinding ob1 = ADFBeanUtils.getOperationBinding("Rollback");
        ob1.execute();
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
    }


    public void setPagebindtaxname(RichSelectOneChoice pagebindtaxname)
    {
        this.pagebindtaxname = pagebindtaxname;
    }

    public RichSelectOneChoice getPagebindtaxname()
    {
        return pagebindtaxname;
    }

    public void editEmpDed(ActionEvent actionEvent)
    {
        RequestContext context = RequestContext.getCurrentInstance();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pagebindtaxamount);
        context.getPageFlowScope().put("ADD_EDIT_MODE", "P");
        //isSalaryProcPending
        //        OperationBinding op = getBindings().getOperationBinding("isSalaryProcPending");
        //        op.execute();
        //        if(op.getErrors().size()==0&&op.getResult().toString().equals("N"))
        //        {
        //
        //        }
        //        else
        //        {
        //            String msg = "Salary Process of this Employee is Pending.";
        //            showFacesMessage(msg, "I", false, "F");
        //        }

    }

    public Object resolvElDCMsg(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void saveEmpDed(ActionEvent actionEvent)
    {
        String msg = "";
        boolean result = true;
        Integer i = (Integer) pagebindDedPrfFacet.getValue();
        System.out.println(i);

        if (i == 48)
        {
            result = chkValidFrtax();
        }
        if (i == 50)
        {
            result = chkValidFrMisc();
        }
        if (result == true)
        {
            OperationBinding ob = ADFBeanUtils.getOperationBinding("Commit");
            ob.execute();
            msg = "MSG.91";
            showFacesMessage(msg, "I", true, "F");
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
        }


    }


    public boolean chkValidFrtax()
    {
        boolean result = true;
        String msg = "";

        if (pagebindtaxname.getValue() != null && pagebindtaxname.getValue() != "")
        {
            OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("ChkTaxId");
            operationBinding.getParamsMap().put("dedId", pagebindtaxname.getValue().toString());
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty())
            {
                String TaxIx = operationBinding.getResult().toString();
                if (TaxIx.equals("64"))
                {
                    if ((pagebindtaxamount.getValue() == null))
                    {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1576']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(pagebindtaxamount.getClientId(), message);
                        return false;
                    }
                    else if (pagebindTaxStartDate.getValue() == null)
                    {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1576']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(pagebindTaxStartDate.getClientId(), message);
                        return false;
                    }
                }
                if (TaxIx.equals("65"))
                {
                    if ((pagebindTaxStartDate.getValue() == null))
                    {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1576']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(pagebindTaxStartDate.getClientId(), message);
                        return false;
                    }
                }
            }
            else
            {
                return false;
            }

        }
        else
        {
            if ((pagebindtaxname.getValue() == null || pagebindtaxname.getValue().toString().length() <= 0))
            {

                System.out.println("taxname" + pagebindtaxname.getValue());
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1576']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(pagebindtaxname.getClientId(), message);
                return false;
            }
        }


        return result;
    }


    public boolean chkValidFrMisc()
    {
        boolean result = true;
        String msg = "";
        if ((pagebindDedStartDate.getValue()) == null || pagebinddedname.getValue() == null ||
            pagebinddeddocumentdate.getValue() == null || pagebinddeddocumentno.getValue() == null ||
            vpfAmtBinding.getValue() == null)
        {

            if ((pagebinddedname.getValue() == null))
            {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1576']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(pagebinddedname.getClientId(), message);
                return false;
            }

            if ((pagebinddeddocumentdate.getValue() == null))
            {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1576']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(pagebinddeddocumentdate.getClientId(), message);
                return false;
            }
            if ((pagebindDedStartDate.getValue() == null))
            {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1576']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(pagebindDedStartDate.getClientId(), message);
                return false;
            }
            //Commented By Vishal Kr on 19-08-2015
            /*  if ((pagebinddeddocumentno.getValue() == null)) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1576']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(pagebinddeddocumentno.getClientId(), message);
                return false;
            } */
            if ((vpfChkBinding.getValue() != null && vpfChkBinding.getValue().toString().length() > 0))
            {
                if (vpfAmtBinding.getValue() == null)
                {
                    FacesMessage message = new FacesMessage("VPF value can not be left blank!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(vpfAmtBinding.getClientId(), message);
                    return false;
                }
            }
        }
        return result;

    }


    public void setPagebindDedPrfFacet(RichSelectOneRadio pagebindDedPrfFacet)
    {
        this.pagebindDedPrfFacet = pagebindDedPrfFacet;
    }

    public RichSelectOneRadio getPagebindDedPrfFacet()
    {
        return pagebindDedPrfFacet;
    }


    public void setPagebindTaxStartDate(RichInputDate pagebindTaxStartDate)
    {
        this.pagebindTaxStartDate = pagebindTaxStartDate;
    }

    public RichInputDate getPagebindTaxStartDate()
    {
        return pagebindTaxStartDate;
    }

    public void popupshow(ActionEvent actionEvent)
    {

        OperationBinding ob = ADFBeanUtils.getOperationBinding("lovhcndedslab");
        ob.execute();
        showPopup(pagebindpop, true);
    }

    private void showPopup(RichPopup pop, boolean visible)
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null)
            {
                String popupId = pop.getClientId(context);
                if (popupId != null)
                {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup =AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible)
                    {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    }
                    else
                    {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void setPagebindpop(RichPopup pagebindpop)
    {
        this.pagebindpop = pagebindpop;
    }

    public RichPopup getPagebindpop()
    {
        return pagebindpop;
    }

    public void resetEmpDed(ActionEvent actionEvent)
    {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("resetSearch");
        ob.execute();
        System.out.println(ob.getErrors());

    }


    public void setPagebindDedStartDate(RichInputDate pagebindDedStartDate)
    {
        this.pagebindDedStartDate = pagebindDedStartDate;
    }

    public RichInputDate getPagebindDedStartDate()
    {
        return pagebindDedStartDate;
    }

    public void setPagebindDedEndDate(RichInputDate pagebindDedEndDate)
    {
        this.pagebindDedEndDate = pagebindDedEndDate;
    }

    public RichInputDate getPagebindDedEndDate()
    {
        return pagebindDedEndDate;
    }

    public void setPagebindTaxEndDate(RichInputDate pagebindTaxEndDate)
    {
        this.pagebindTaxEndDate = pagebindTaxEndDate;
    }

    public RichInputDate getPagebindTaxEndDate()
    {
        return pagebindTaxEndDate;
    }


    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg)
    {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true)
        {
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E"))
        {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        else if (sev.equalsIgnoreCase("W"))
        {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        }
        else if (sev.equalsIgnoreCase("I"))
        {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        else
        {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F"))
        {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else if (typFlg.equals("V"))
        {
            throw new ValidatorException(message);
        }
    }

    public String resolvEl(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void validateDedEndDate(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        // Add event code here...
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (pagebindDedStartDate.getValue() != null && pagebindDedStartDate.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) pagebindDedStartDate.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                }
                catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0)
                {
                    if (strtDt.toString().equals(endDt.toString()))
                    {
                    }
                    else
                    {
                        showFacesMessage("MSG.1338", "E", true, "V");
                    }
                }
            }
        }
        OperationBinding opchk1 = ADFBeanUtils.getOperationBinding("chkdedenddate");
        opchk1.getParamsMap().put("strtDt", object);
        opchk1.execute();


        if (opchk1.getErrors().size() == 0 && opchk1.getResult().toString().equals("YE"))
        {
            showFacesMessage("MSG.1578", "E", true, "V");
        }


    }

    public void validateTaxEndDate(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        // Add event code here...
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (pagebindTaxStartDate.getValue() != null && pagebindTaxStartDate.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) pagebindTaxStartDate.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                }
                catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0)
                {
                    if (strtDt.toString().equals(endDt.toString()))
                    {
                    }
                    else
                    {
                        showFacesMessage("MSG.1338", "E", true, "V");
                    }
                }
            }
        }
        OperationBinding opchk1 = ADFBeanUtils.getOperationBinding("chkdedenddate");
        opchk1.getParamsMap().put("strtDt", object);
        opchk1.execute();


        if (opchk1.getErrors().size() == 0 && opchk1.getResult().toString().equals("YE"))
        {
            showFacesMessage("MSG.1579", "E", true, "V");
        }


    }


    public void setPagebinddedname(RichSelectOneChoice pagebinddedname)
    {
        this.pagebinddedname = pagebinddedname;
    }

    public RichSelectOneChoice getPagebinddedname()
    {
        return pagebinddedname;
    }

    public void validatetaxamount(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            if ((pagebindtaxname.getValue() != null && pagebindtaxname.getValue() != ""))
            {
                OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("ChkTaxId");
                operationBinding.getParamsMap().put("dedId", pagebindtaxname.getValue().toString());
                operationBinding.execute();
                if (operationBinding.getErrors().isEmpty())
                {
                    if (operationBinding.getResult().toString().equals("64"))
                    {
                        Boolean flag = isPrecisionValid(26, 6, ((oracle.jbo.domain.Number) object));
                        if (flag)
                        {
                        }
                        else
                        {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          resolvElDCMsg("#{bundle['MSG.57']}").toString(),
                                                                          null));
                        }
                        oracle.jbo.domain.Number f = (oracle.jbo.domain.Number) object;

                        if (f.compareTo(0) <= 0)
                        {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          resolvElDCMsg("#{bundle['MSG.1580']}").toString(),
                                                                          null));
                        }
                    }
                    if (operationBinding.getResult().toString().equals("65"))
                    {

                    }
                }
            }


        }

    }

    public void setPagebindtaxamount(RichInputText pagebindtaxamount)
    {
        this.pagebindtaxamount = pagebindtaxamount;
    }

    public RichInputText getPagebindtaxamount()
    {
        return pagebindtaxamount;
    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total)
    {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }


    public void setPagebinddeddocumentdate(RichInputDate pagebinddeddocumentdate)
    {
        this.pagebinddeddocumentdate = pagebinddeddocumentdate;
    }

    public RichInputDate getPagebinddeddocumentdate()
    {
        return pagebinddeddocumentdate;
    }

    public void setPagebinddeddocumentno(RichInputText pagebinddeddocumentno)
    {
        this.pagebinddeddocumentno = pagebinddeddocumentno;
    }

    public RichInputText getPagebinddeddocumentno()
    {
        return pagebinddeddocumentno;
    }


    public void validateloanstartdate(FacesContext facesContext, UIComponent uIComponent, Object object)
    {

        if (object != null && object.toString().length() > 0)
        {
            OperationBinding ob = null;
            Calendar calendar = Calendar.getInstance();
            java.sql.Date todayDate = null;
            try
            {
                todayDate = new java.sql.Date(calendar.getTime().getTime());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Current Date Found : " + todayDate);
            ob = ADFBeanUtils.getOperationBinding("chkPrevDate");
            ob.getParamsMap().put("dedDCurrDtt", todayDate);
            ob.execute();
            System.out.println("Output : " + ob.getResult());
            if (ob.getResult().toString() != null)
            {
                if (ob.getResult().toString().equals("N"))
                {
                    ob = ADFBeanUtils.getOperationBinding("getDedDate");
                    ob.execute();
                    if (ob.getResult().toString() != "")
                    {

                        String date = ob.getResult().toString();
                        String message =
                            "Can not create new deduction :Previous Deduction will end on " + date +
                            " After this date new deduction can be create.";
                        showFacesMessage(message, "E", false, "V");
                    }

                }
                else

                {
                    java.sql.Date ValidStrtDt = null;
                    try
                    {
                        ValidStrtDt = ((Timestamp) object).dateValue();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ob = ADFBeanUtils.getOperationBinding("chkPrevDate");
                    ob.getParamsMap().put("dedDCurrDtt", ValidStrtDt);
                    ob.execute();
                    if (ob.getResult().toString().equals("N"))
                    {
                        ob = ADFBeanUtils.getOperationBinding("getDedDate");
                        ob.execute();
                        if (ob.getResult().toString() != "")
                        {

                            String date = ob.getResult().toString();
                            String message =
                                "Last inactive deduction end date is " + date +
                                " After this date new deduction can be create.";
                            showFacesMessage(message, "E", false, "V");
                        }

                    }
                }

            }
        }


    }


    public void validatUANNumber(FacesContext facesContext, UIComponent uIComponent, Object object)
    {

        if (object != null)
        {
            Boolean flag = isPrecisionValid(26, 6, ((oracle.jbo.domain.Number) object));
            if (flag)
            {
                if (object.toString().matches("\\d{12}"))
                {
                    oracle.jbo.domain.Number st = (oracle.jbo.domain.Number) object;
                    OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("validateUANNumber");
                    operationBinding.getParamsMap().put("type", st);
                    operationBinding.execute();
                    pass = (String) operationBinding.getResult();
                    if (pass.equals("Y"))
                    {

                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Duplicate UAN Number not allowed", null));
                    }
                }
                else
                {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "UAN Number must be of 12 digit", null));
                }
            }
            else
            {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.57']}").toString(), null));
            }

        }
    }


    public void validatetaxstrtdate(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding ob = null;
            Calendar calendar = Calendar.getInstance();
            java.sql.Date todayDate = null;
            try
            {
                todayDate = new java.sql.Date(calendar.getTime().getTime());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Current Date Found : " + todayDate);
            ob = ADFBeanUtils.getOperationBinding("chkPrevDate");
            ob.getParamsMap().put("dedDCurrDtt", todayDate);
            ob.execute();
            System.out.println("Output : " + ob.getResult());
            if (ob.getResult().toString() != null)
            {
                if (ob.getResult().toString().equals("N"))
                {
                    ob = ADFBeanUtils.getOperationBinding("getDedDate");
                    ob.execute();
                    if (ob.getResult().toString() != "")
                    {

                        String date = ob.getResult().toString();
                        String message =
                            "Can not create new deduction :Previous Deduction will end on " + date +
                            " After this date new deduction can be create.";
                        showFacesMessage(message, "E", false, "V");
                    }

                }
                else

                {
                    java.sql.Date ValidStrtDt = null;
                    try
                    {
                        ValidStrtDt = ((Timestamp) object).dateValue();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ob = ADFBeanUtils.getOperationBinding("chkPrevDate");
                    ob.getParamsMap().put("dedDCurrDtt", ValidStrtDt);
                    ob.execute();
                    if (ob.getResult().toString().equals("N"))
                    {
                        ob = ADFBeanUtils.getOperationBinding("getDedDate");
                        ob.execute();
                        if (ob.getResult().toString() != "")
                        {

                            String date = ob.getResult().toString();
                            String message =
                                "Last inactive deduction end date is " + date +
                                " After this date new deduction can be create.";
                            showFacesMessage(message, "E", false, "V");
                        }

                    }
                }

            }
        }

        // Add event code here...
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkValidStrtdt");
        opchk.getParamsMap().put("strtDt", object);
        opchk.execute();
        if (opchk.getErrors().size() == 0 && opchk.getResult().toString().equals("Y"))
        {

        }
        else
        {
            showFacesMessage("MSG.1591", "E", true, "V");
        }

        OperationBinding opchk1 = ADFBeanUtils.getOperationBinding("chkhcmdedstartdate");
        opchk1.getParamsMap().put("strtDt", object);
        opchk1.execute();

        if (opchk1.getErrors().size() == 0 && opchk1.getResult().toString().equals("YS"))
        {
            showFacesMessage("MSG.1592", "E", true, "V");
        }
        if (opchk1.getErrors().size() == 0 && opchk1.getResult().toString().equals("YE"))
        {
            showFacesMessage("MSG.1593", "E", true, "V");
        }


    }

    public void validatedocumentdate(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkValidStrtdt");
        opchk.getParamsMap().put("strtDt", object);
        opchk.execute();
        if (opchk.getErrors().size() == 0 && opchk.getResult().toString().equals("Y"))
        {

        }
        else
        {
            showFacesMessage("MSG.1594", "E", true, "V");
        }
    }

    public void validatededstartdate(FacesContext facesContext, UIComponent uIComponent, Object object)
    {

        if (object != null && object.toString().length() > 0)
        {
            OperationBinding ob = null;
            Calendar calendar = Calendar.getInstance();
            java.sql.Date todayDate = null;
            try
            {
                todayDate = new java.sql.Date(calendar.getTime().getTime());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Current Date Found : " + todayDate);
            ob = ADFBeanUtils.getOperationBinding("chkPrevDate");
            ob.getParamsMap().put("dedDCurrDtt", todayDate);
            ob.execute();
            System.out.println("Output : " + ob.getResult());
            if (ob.getResult() != null)
            {
                if (ob.getResult().toString().equals("N"))
                {
                    ob = ADFBeanUtils.getOperationBinding("getDedDate");
                    ob.execute();
                    if (ob.getResult().toString() != "")
                    {

                        String date = ob.getResult().toString();
                        String message =
                            "Can not create new deduction :Previous Deduction will end on " + date +
                            " After this date new deduction can be create.";
                        showFacesMessage(message, "E", false, "V");
                    }

                }
                else

                {
                    java.sql.Date ValidStrtDt = null;
                    try
                    {
                        ValidStrtDt = ((Timestamp) object).dateValue();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ob = ADFBeanUtils.getOperationBinding("chkPrevDate");
                    ob.getParamsMap().put("dedDCurrDtt", ValidStrtDt);
                    ob.execute();
                    if (ob.getResult().toString().equals("N"))
                    {
                        ob = ADFBeanUtils.getOperationBinding("getDedDate");
                        ob.execute();
                        if (ob.getResult().toString() != "")
                        {

                            String date = ob.getResult().toString();
                            String message =
                                "Last inactive deduction end date is " + date +
                                " After this date new deduction can be create.";
                            showFacesMessage(message, "E", false, "V");
                        }

                    }
                }

            }
        }

        // Add event code here...

        OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkValidStrtdt");
        opchk.getParamsMap().put("strtDt", object);
        opchk.execute();
        if (opchk.getErrors().size() == 0 && opchk.getResult().toString().equals("Y"))
        {

        }
        else
        {
            showFacesMessage("MSG.1595", "E", true, "V");
        }

        OperationBinding opchk1 = ADFBeanUtils.getOperationBinding("chkhcmdedstartdate");
        opchk1.getParamsMap().put("strtDt", object);
        opchk1.execute();

        if (opchk1.getErrors().size() == 0 && opchk1.getResult().toString().equals("YS"))
        {
            showFacesMessage("MSG.1596", "E", true, "V");
        }
        if (opchk1.getErrors().size() == 0 && opchk1.getResult().toString().equals("YE"))
        {
            showFacesMessage("MSG.1597", "E", true, "V");
        }

    }

    public void validatedocmntno(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        // Add event code here...
        if (object != null)
        {
            String st = (String) object;
            OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("documentNamevalidate");
            operationBinding.getParamsMap().put("type", st);
            operationBinding.execute();
            pass = (String) operationBinding.getResult();
            if (pass.equals("Y"))
            {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1598']}").toString(), null));
            }

        }


    }


    public void refTaxAmnt(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("ChkTaxId");
            operationBinding.getParamsMap().put("dedId", object.toString());
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty())
            {
                if (operationBinding.getResult().toString().equals("64"))
                {
                    setRefAmntMode("Y");
                }
                if (operationBinding.getResult().toString().equals("65"))
                {
                    setRefAmntMode("N");
                }
            }
        }

    }

    public String goBackToEmpPrf()
    {
        OperationBinding ob1 = ADFBeanUtils.getOperationBinding("Rollback");
        ob1.execute();
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
        return "back";
    }

    public void resetFormFields(ValueChangeEvent valueChangeEvent)
    {
        pagebindtaxamount.setValue(null);
        pagebindTaxStartDate.setValue("");
        pagebindTaxEndDate.setValue("");
        pagebindDedEndDate.setValue("");
        pagebindDedStartDate.setValue("");
        pagebinddeddocumentdate.setValue("");
        System.out.println("before setting null in ded Doc");
        pagebinddedname.setValue(null);
    }

    public void dedDocIdVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            pfOnActualBasicBinding.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pfOnActualBasicBinding);
            vpfChkBinding.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(vpfChkBinding);
            vpfAmtBinding.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(vpfAmtBinding);
        }
    }

    public void setPfOnActualBasicBinding(RichSelectBooleanCheckbox pfOnActualBasicBinding)
    {
        this.pfOnActualBasicBinding = pfOnActualBasicBinding;
    }

    public RichSelectBooleanCheckbox getPfOnActualBasicBinding()
    {
        return pfOnActualBasicBinding;
    }

    public void setVpfChkBinding(RichSelectOneChoice vpfChkBinding)
    {
        this.vpfChkBinding = vpfChkBinding;
    }

    public RichSelectOneChoice getVpfChkBinding()
    {
        return vpfChkBinding;
    }

    public void setVpfAmtBinding(RichInputText vpfAmtBinding)
    {
        this.vpfAmtBinding = vpfAmtBinding;
    }

    public RichInputText getVpfAmtBinding()
    {
        return vpfAmtBinding;
    }

    public void vpfVCL(ValueChangeEvent valueChangeEvent)
    {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() == null)
        {
            vpfAmtBinding.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(vpfAmtBinding);
        }
        else
            vpfAmtBinding.setValue(null);
    }

    public void maxSalComValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        oracle.jbo.domain.Number num = (oracle.jbo.domain.Number) object;
        Boolean flag = isPrecisionValid(26, 6, num);
        if (flag == false)
        {
            String msg = "Invalid Amount";
            showFacesMessage(msg, "E", false, "V");
            //Amount can't be lesser than 0
        }
        else if (num.compareTo(0) == -1)
        {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Amount".toString(),
                                                          null));
        }
    }

    public void empContuributionValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            oracle.jbo.domain.Number num = (oracle.jbo.domain.Number) object;
            Boolean flag = isPrecisionValid(26, 6, num);
            if (flag == false)
            {
                String msg = "Invalid Amount";
                showFacesMessage(msg, "E", false, "V");
                //Amount can't be lesser than 0
            }
            else if (num.compareTo(0) == -1)
            {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Amount".toString(),
                                                              null));
            }
        }
    }

    public void chkDuplicateDedName(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        checkDuplicateDeductionName(object);
    }

    public void checkDuplicateDeductionName(Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            if (object.toString().trim().length() > 0)
            {
                System.out.println("Data Sent : " + object.toString());
                OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateDedName");
                opChk.getParamsMap().put("dedDesc", object.toString().trim());
                opChk.execute();
                if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                {
                    System.out.println("Error on check duplicate=" + opChk.getErrors());
                }
                else
                {
                    System.out.println("Error on check duplicate=" + opChk.getResult());
                    if (opChk.getResult().toString().equals("Y"))
                        showFacesMessage("MSG.1395", "E", true, "V");
                }
            }
            else
            {
                showFacesMessage("MSG.1396", "E", true, "V");

            }
        }
    }

    public void chkDuplicateMDedName(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        checkDuplicateDeductionName(object);
    }

    public void chkDuplicateTDedName(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        checkDuplicateDeductionName(object);
    }

    public void vpfValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {

        if (object != null && vpfChkBinding.getValue() != null && vpfChkBinding.getValue() != " ")
        {
            if (vpfChkBinding.getValue().toString().equalsIgnoreCase("P"))
            {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal frCmprehund = new BigDecimal(100);
                BigDecimal value = new BigDecimal(object.toString());
                int newvalue = value.compareTo(frCmpreZro);
                if (newvalue == -1)
                {
                    String msg = "Invalid value, value should not be in negative!";
                    showFacesMessage(msg, "E", false, "V");
                }
                else
                {
                    newvalue = value.compareTo(frCmprehund);
                    if (newvalue == +1)
                    {
                        String msg = "Invalid value,can not be greater than or equal to 100 (Percent)!";
                        showFacesMessage(msg, "E", false, "V");
                    }
                }

            }
        }


    }

    public String setGlblBindVarNdMOveToLoanPageAction()
    {
        OperationBinding obsalChk = ADFBeanUtils.getOperationBinding("chkFrSalryComp");
        obsalChk.execute();
        if (obsalChk.getErrors().isEmpty() && obsalChk.getResult().toString().equals("Y"))
        {
            OperationBinding ob1 = ADFBeanUtils.getOperationBinding("setEmpDocIdInGlblVar");
            ob1.execute();
            return "goToEmployeeLoanPage";
        }
        else
        {
            String mesg = "This employee don't have any salary component:Please add salary component.";
            showFacesMessage(mesg, "E", false, "F");
            return null;
        }
    }
}
