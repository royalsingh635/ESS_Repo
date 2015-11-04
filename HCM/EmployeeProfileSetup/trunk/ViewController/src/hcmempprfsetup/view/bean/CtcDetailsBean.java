package hcmempprfsetup.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.bean.StaticValue;

import java.math.BigDecimal;

import java.sql.SQLException;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.jbo.domain.Number;

import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class CtcDetailsBean
{
    public String AddMode = "A";
    private RichPopup ctcRefPopUpBinding;
    private RichInputText totalAmountBinding;
    private RichInputText remainAmountBinding;
    private RichInputText amountSubBinding;
    private RichSelectOneChoice varTypeBinding;

    public void setAddMode(String AddMode)
    {
        this.AddMode = AddMode;
    }

    public String getAddMode()
    {
        return AddMode;
    }

    public void setAddEditMode(String AddEditMode)
    {
        this.AddEditMode = AddEditMode;
    }

    public String getAddEditMode()
    {
        return AddEditMode;
    }

    public void setAddDeleteMode(String AddDeleteMode)
    {
        this.AddDeleteMode = AddDeleteMode;
    }

    public String getAddDeleteMode()
    {
        return AddDeleteMode;
    }

    public void setAddCancelMode(String AddCancelMode)
    {
        this.AddCancelMode = AddCancelMode;
    }

    public String getAddCancelMode()
    {
        return AddCancelMode;
    }
    public String AddEditMode = "E";
    public String AddDeleteMode = "D";
    public String AddCancelMode = "C";
    public String AddSaveMode = "S";
    public String ViewMode = "V";

    public void setViewMode(String ViewMode)
    {
        this.ViewMode = ViewMode;
    }

    public String getViewMode()
    {
        return ViewMode;
    }


    public void setAddSaveMode(String AddSaveMode)
    {
        this.AddSaveMode = AddSaveMode;
    }

    public String getAddSaveMode()
    {
        return AddSaveMode;
    }

    public CtcDetailsBean()
    {

    }

    public String CtcCreateAction()
    {
        // Add event code here...
        ADFBeanUtils.callBindingsMethod("CreateInsert", null, null);
        AddMode = "A";
        ViewMode = "B";
        System.out.println("CreateInsert");

        return null;
    }

    public String ctcSaveAction()
    {
        ADFBeanUtils.callBindingsMethod("Commit", null, null);
        showFacesMessage("Record saved successfully.", "I", false, "F");
        ViewMode = "V";
        System.out.println("save");

        return null;
    }

    public String CtcCancelAction()
    {
        // Add event code here...
        ADFBeanUtils.callBindingsMethod("Rollback", null, null);
        ViewMode = "V";
        System.out.println("save");

        return null;
    }

    public String CtcDeleteAction()
    {
        ADFBeanUtils.callBindingsMethod("Delete", null, null);
        ViewMode = "V";
        System.out.println("save");
        return null;
    }

    public String CtcSalAddAction()
    {
        OperationBinding opchk = null;

        ADFBeanUtils.callBindingsMethod("CreateInsert1", null, null);
        opchk = ADFBeanUtils.getOperationBinding("setValidStartNdEndDtFrCTC");
        opchk.execute();
        return null;
    }

    public String ctcSalDeleteAction()
    {
        ADFBeanUtils.callBindingsMethod("Delete1", null, null);
        ViewMode = "V";

        return null;
    }

    public String editCtcAction()
    {
        ViewMode = "N";
        return null;
    }

    public void setCtcRefPopUpBinding(RichPopup ctcRefPopUpBinding)
    {
        this.ctcRefPopUpBinding = ctcRefPopUpBinding;
    }

    public RichPopup getCtcRefPopUpBinding()
    {
        return ctcRefPopUpBinding;
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

    public String calCtcRefAction()
    {
        showPopup(ctcRefPopUpBinding, true);
        return null;
    }

    public String CtcrefrenceAction()
    {
        // Add event code here...
        ADFBeanUtils.callBindingsMethod("CreateInsert2", null, null);
        return null;
    }

    public String ctcrefrenceDelete()
    {
        ADFBeanUtils.callBindingsMethod("Delete2", null, null);
        return null;
    }

    public void totalAmountVCL(ValueChangeEvent valueChangeEvent)
    {
        if (totalAmountBinding.getValue() != null)
        {
            Number remainAmt = (Number) totalAmountBinding.getValue();
            remainAmountBinding.setValue(remainAmt);
        }

    }

    public void setTotalAmountBinding(RichInputText totalAmountBinding)
    {
        this.totalAmountBinding = totalAmountBinding;
    }

    public RichInputText getTotalAmountBinding()
    {
        return totalAmountBinding;
    }

    public void setRemainAmountBinding(RichInputText remainAmountBinding)
    {
        this.remainAmountBinding = remainAmountBinding;
    }

    public RichInputText getRemainAmountBinding()
    {
        return remainAmountBinding;
    }

    public void newremainCtcAmount(ValueChangeEvent valueChangeEvent)
    {
        // Add event code here...
    }

    public void newRemainCtcAmountVCL(ValueChangeEvent valueChangeEvent)
    {
        //    System.out.println("newRemainCtcAmountVCL");
        //         if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0)
        //         {
        //             BigDecimal amount = new BigDecimal(valueChangeEvent.getNewValue().toString());
        //             System.out.println("amount=="+amount);
        //             BigDecimal remain = new BigDecimal(getRemainAmountBinding().getValue().toString());
        //             System.out.println("remain=="+remain);
        //             Number remainvalue=remain.subtract(amount);
        //             System.out.println("remainvalue=="+remainvalue);
        //
        //             remainAmountBinding.setValue(remainvalue);
        //
        //         }
    }

    public void setAmountSubBinding(RichInputText amountSubBinding)
    {
        this.amountSubBinding = amountSubBinding;
    }

    public RichInputText getAmountSubBinding()
    {
        return amountSubBinding;
    }

    public void remainAmountVLC(ValueChangeEvent vce)
    {
        System.out.println("newRemainCtcAmountVCL");
        if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0)
        {
            Number amount = (Number) vce.getNewValue();
            Number remainAmt = (Number) remainAmountBinding.getValue();
            Number finalAmt = remainAmt.subtract(amount);
            System.out.println("1.00000" + amount);
            System.out.println("1.111111" + remainAmt);
            System.out.println("fianl amount iss==" + finalAmt);
            remainAmountBinding.setValue(finalAmt);
            System.out.println("value of feemiN AMT ISS===" + remainAmountBinding.getValue());

            //                 BigDecimal amount = new BigDecimal(valueChangeEvent.getNewValue().toString());
            //                 System.out.println("amount=="+amount);
            //                 BigDecimal remain = new BigDecimal(getRemainAmountBinding().getValue().toString());
            //                 System.out.println("remain=="+remain);
            //                 Number remainvalue=remain.subtract(amount);
            //                 System.out.println("remainvalue=="+remainvalue);
            //
            //                 remainAmountBinding.setValue(remainvalue);

        }
    }

    public void CalPerVCL(ValueChangeEvent valueChange)
    {
        System.out.println("CalPerVCL");
        if (valueChange.getNewValue() != null && valueChange.getNewValue().toString().length() > 0)
        {
            Number Per = (Number) valueChange.getNewValue();
            System.out.println("Per==" + Per);
            Number remainAmt = (Number) remainAmountBinding.getValue();
            System.out.println("remainAmt==" + remainAmt);
            Number perAMT = (Per.multiply(remainAmt).divide(100));
            System.out.println("finalperAMT==" + perAMT);
            Number finalperAmt = remainAmt.subtract(perAMT);
            remainAmountBinding.setValue(finalperAmt);
            System.out.println("value of feemiN AMT ISS===" + remainAmountBinding.getValue());
        }
    }

    public void variableValVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "")
        {
           
            if (remainAmountBinding.getValue() != null && remainAmountBinding.getValue() != "")
            {
                Number finalLnAmnt = null;

                if (varTypeBinding.getValue() != null && varTypeBinding.getValue() != "")
                {
                    Number varAmount = (Number) valueChangeEvent.getNewValue();
                    Number totAmount = (Number) totalAmountBinding.getValue();

                    if (varTypeBinding.getValue().toString().equals("P"))
                    {

                        Number perCalVal;
                        try
                        {
                            perCalVal = (Number) (varAmount.mul(totAmount)).div(new Number(100));
                            finalLnAmnt = totAmount.subtract(perCalVal);
                            remainAmountBinding.setValue(finalLnAmnt);
                        }
                        catch (SQLException e) {
                            System.out.println(e);
                        }


                    }
                    else if (varTypeBinding.getValue().toString().equals("A"))
                    {
                        finalLnAmnt = totAmount.subtract(varAmount);
                        remainAmountBinding.setValue(finalLnAmnt);
                    }
                }

            }
        }
    }

    public void variableAmntValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object != "")
        {
            
            String msg = "";
            BigDecimal frCmpreZro = BigDecimal.ZERO;
            BigDecimal value =  new BigDecimal(object.toString());
            int newvalue = value.compareTo(frCmpreZro);
            if (newvalue == -1)
            {
                msg = "variable amount should not be less than zero!!";
                showFacesMessage(msg, "E", false, "V");
            }
            else
            {
                BigDecimal varAmount = new BigDecimal(object.toString());
                BigDecimal totAmount = new BigDecimal(totalAmountBinding.getValue().toString());

                int cparevalue = totAmount.compareTo(varAmount);
                if (cparevalue == -1)
                {
                    msg = "Total CTC  amount should not be less than variable amount!!";
                    showFacesMessage(msg, "E", false, "V");
                }
            }


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

    public void setVarTypeBinding(RichSelectOneChoice varTypeBinding)
    {
        this.varTypeBinding = varTypeBinding;
    }

    public RichSelectOneChoice getVarTypeBinding()
    {
        return varTypeBinding;
    }


}
