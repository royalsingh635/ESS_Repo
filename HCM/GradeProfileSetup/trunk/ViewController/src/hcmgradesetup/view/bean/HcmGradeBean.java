package hcmgradesetup.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.exception.ADFUtilsException;

import adf.utils.model.ADFModelUtils;

import java.math.BigDecimal;

import java.sql.SQLException;

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

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class HcmGradeBean
{
    private RichInputListOfValues desigNmBinding;
    private RichInputText minPayScaleBinding;
    private RichInputText maxPayScaleBinding;
    private RichInputDate validStrtDtBinding;
    private RichInputDate validEndDtBinding;
    private RichInputDate orgValidStrtDtBinding;
    private RichInputDate orgValidEndDtBinding;
    private String add_edit_mode = "E";
    private String add_edit_mode_frSal = "E";
    private String add_edit_mode_frCTC = "E";
    private RichSelectOneChoice ctcCompTypeBinding;
    private RichSelectOneChoice ctcCompIdBinding;
    private RichSelectOneChoice ctcDedIdBinding;
    private RichInputText ctcAmntBinding;
    private RichInputDate ctcValidFromDtBinding;
    private RichSelectOneChoice ctcReffCompId;
    private RichInputText ctcPercBinding;

    public void setAdd_edit_mode_frCTC(String add_edit_mode_frCTC)
    {
        this.add_edit_mode_frCTC = add_edit_mode_frCTC;
    }

    public String getAdd_edit_mode_frCTC()
    {
        return add_edit_mode_frCTC;
    }
    private RichInputDate salValidStartBinding;
    private RichInputDate salValidEndDtBinding;
    private RichInputDate gradValidStrtDtBinding;
    private RichPopup ctcReffSalBinding;

    public void setAdd_edit_mode_frSal(String add_edit_mode_frSal)
    {
        this.add_edit_mode_frSal = add_edit_mode_frSal;
    }

    public String getAdd_edit_mode_frSal()
    {
        return add_edit_mode_frSal;
    }
    private RichInputListOfValues organizatiionNmBinding;

    public void setAdd_edit_mode(String add_edit_mode)
    {

        this.add_edit_mode = add_edit_mode;
    }

    public String getAdd_edit_mode()
    {
        return add_edit_mode;
    }

    public HcmGradeBean()
    {
    }

    public void addGradeSalPayAL(ActionEvent actionEvent)
    {
        OperationBinding opInsert = ADFBeanUtils.getOperationBinding("CreateInsert4");
        opInsert.execute();
        OperationBinding opInsertDocId = ADFBeanUtils.getOperationBinding("setDocId");
        opInsertDocId.execute();
        setAdd_edit_mode("D");
    }

    public void addDesignationWiseGradeAL(ActionEvent actionEvent)
    {
        boolean result = chkSveGrdPayValidation();
        if (result)
        {
            result = chkSveDesiGrdValidation();
        }
        if (result)
        {
            OperationBinding opInsert = ADFBeanUtils.getOperationBinding("CreateInsert");
            opInsert.execute();
            OperationBinding opInsertDocId = ADFBeanUtils.getOperationBinding("setMaxAndMinPayValue");
            opInsertDocId.execute();
            setAdd_edit_mode("D");
        }

    }


    public boolean chkSveDesiGrdValidation()
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmGrdPrf1Iterator");
        String message = "";
        if (di.getEstimatedRowCount() > 0)
        {
            if (desigNmBinding.getValue() == null || desigNmBinding.getValue() == "")
            {
                String cid = desigNmBinding.getClientId();
                message = "Please select designation.";
                showMessage(message, cid);
                return false;
            }
            if (minPayScaleBinding.getValue() == null || minPayScaleBinding.getValue() == "")
            {
                String cid = minPayScaleBinding.getClientId();
                message = "Please enter minimum pay scale.";
                showMessage(message, cid);
                return false;
            }
            if (maxPayScaleBinding.getValue() == null || maxPayScaleBinding.getValue() == "")
            {
                String cid = maxPayScaleBinding.getClientId();
                message = "Please enter maximum pay scale.";
                showMessage(message, cid);
                return false;
            }
            if (validStrtDtBinding.getValue() == null || validStrtDtBinding.getValue() == "")
            {
                String cid = validStrtDtBinding.getClientId();
                message = "Please enter valid start date.";
                showMessage(message, cid);
                return false;
            }
        }

        return true;
    }

    public boolean chkSveGrdPayValidation()
    {
        String message = "";
        if (minPayScaleBinding.getValue() == null || minPayScaleBinding.getValue() == "")
        {
            String cid = minPayScaleBinding.getClientId();
            message = "Please enter minimum pay scale.";
            showMessage(message, cid);
            return false;
        }
        if (maxPayScaleBinding.getValue() == null || maxPayScaleBinding.getValue() == "")
        {
            String cid = maxPayScaleBinding.getClientId();
            message = "Please enter maximum pay scale.";
            showMessage(message, cid);
            return false;
        }
        if (gradValidStrtDtBinding.getValue() == null || gradValidStrtDtBinding.getValue() == "")
        {
            String cid = gradValidStrtDtBinding.getClientId();
            message = "Please enter valid start date.";
            showMessage(message, cid);
            return false;
        }

        return true;
    }

    public boolean chkOrgSaveValidation()
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("OrgHcmGrdPrf1Iterator");
        String message = "";
        if (di.getEstimatedRowCount() > 0)
        {
            if (orgValidStrtDtBinding.getValue() == null || orgValidStrtDtBinding.getValue() == "")
            {
                String cid = orgValidStrtDtBinding.getClientId();
                message = "Please select valid start date.";
                showMessage(message, cid);
                return false;
            }
            if (organizatiionNmBinding.getValue() == null || organizatiionNmBinding.getValue() == "")
            {
                String cid = organizatiionNmBinding.getClientId();
                message = "Please select organization.";
                showMessage(message, cid);
                return false;
            }
        }
        return true;
    }

    public void addOrgWiseDesigGradeAL(ActionEvent actionEvent)
    {

        boolean result = chkSveGrdPayValidation();
        if (result)
        {
            result = chkSveDesiGrdValidation();
        }
        if (result)
        {
            result = chkOrgSaveValidation();
        }

        if (result)
        {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("CreateInsert1");
            opchk.execute();
        }

    }

    public void saveDesignationWiseGradeAL(ActionEvent actionEvent)
    {
        OperationBinding salRangeBinding = ADFBeanUtils.getOperationBinding("isSalRangeOnSave");
        Object o = salRangeBinding.getResult();
        Boolean b = (o == null ? false : (Boolean) o);

        boolean result = chkSveGrdPayValidation();
        if (result)
        {
            result = chkSveDesiGrdValidation();
        }
        if (result)
        {
            result = chkOrgSaveValidation();
        }
        if (result)
        {
            DCIteratorBinding di = null;
            di = ADFBeanUtils.findIterator("HcmGrdSal1Iterator");
            if (di.getEstimatedRowCount() == 0)
            {
                showFacesMessage("Please add grade salary structure .", "E", false, "F");
            }
            else if (!isSalaryGradeValid())
            {
            }
            else
            {
                ADFBeanUtils.getOperationBinding("Commit").execute();
                showFacesMessage("Record saved successfully.", "I", false, "F");
                setAdd_edit_mode("E");
            }
        }
    }

    /**
     * Method to validate Salary
     * @return
     */
    public Boolean isSalaryGradeValid()
    {
        OperationBinding salRangeBinding = ADFBeanUtils.getOperationBinding("isSalRangeOnSave");
        salRangeBinding.execute();
        Object o = salRangeBinding.getResult();
        System.out.println("Returned  : " + o);
        return (o == null ? false : (Boolean) o);
    }


    public void cancelGradeAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        setAdd_edit_mode("E");

    }

    public void editGradeAL(ActionEvent actionEvent)
    {
        System.out.println("editGradeAL ");
        setAdd_edit_mode("D");
    }

    public void deletGradeAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete").execute();
    }

    public void deletGradeSalPayAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete4").execute();
        setAdd_edit_mode("E");
    }


    public void setGrdDocIdInGlblVariableAL(ActionEvent actionEvent)
    {
        boolean result = chkSveDesiGrdValidation();
        if (result)
        {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("setGrdDocIdInGlblVar");
            opchk.execute();
        }

    }

    public void setDesigNmBinding(RichInputListOfValues desigNmBinding)
    {
        this.desigNmBinding = desigNmBinding;
    }

    public RichInputListOfValues getDesigNmBinding()
    {
        return desigNmBinding;
    }

    public void setMinPayScaleBinding(RichInputText minPayScaleBinding)
    {
        this.minPayScaleBinding = minPayScaleBinding;
    }

    public RichInputText getMinPayScaleBinding()
    {
        return minPayScaleBinding;
    }

    public void setMaxPayScaleBinding(RichInputText maxPayScaleBinding)
    {
        this.maxPayScaleBinding = maxPayScaleBinding;
    }

    public RichInputText getMaxPayScaleBinding()
    {
        return maxPayScaleBinding;
    }

    public void setValidStrtDtBinding(RichInputDate validStrtDtBinding)
    {
        this.validStrtDtBinding = validStrtDtBinding;
    }

    public RichInputDate getValidStrtDtBinding()
    {
        return validStrtDtBinding;
    }

    public void setValidEndDtBinding(RichInputDate validEndDtBinding)
    {
        this.validEndDtBinding = validEndDtBinding;
    }

    public RichInputDate getValidEndDtBinding()
    {
        return validEndDtBinding;
    }


    public void setOrgValidStrtDtBinding(RichInputDate orgValidStrtDtBinding)
    {
        this.orgValidStrtDtBinding = orgValidStrtDtBinding;
    }

    public RichInputDate getOrgValidStrtDtBinding()
    {
        return orgValidStrtDtBinding;
    }

    public void setOrgValidEndDtBinding(RichInputDate orgValidEndDtBinding)
    {
        this.orgValidEndDtBinding = orgValidEndDtBinding;
    }

    public RichInputDate getOrgValidEndDtBinding()
    {
        return orgValidEndDtBinding;
    }

    public Object resolvEl(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
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


    public String showMessage(String message, String cid)
    {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
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

    public void chkDuplicateValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        String msg = "";
        if (object != null && object.toString().length() > 0)
        {
            String id = uIComponent.getId();
            System.out.println("Id is :::::" + id);
            if (id != null)
            {
                OperationBinding opchk = null;
                if (id.equals("designationNm"))
                {
                    chkValidationForDesig(id, object);
                }

                if (id.equals("organizationNm"))
                {
                    chkValidFrOrg(id, object);
                }
                if (id.equals("salComponent"))
                {
                    chkValidFrSalComp(id, object);
                }
                if (id.equals("refSalComponent"))
                {
                    chkValidFrSalRefComp(id, object);
                }
            }

        }


    }

    public boolean chkValidationForDesig(String id, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            if (id != null)
            {
                OperationBinding opchk = null;
                if (id.equals("designationNm"))
                {
                    String desigNm = object.toString();
                    opchk = ADFBeanUtils.getOperationBinding("chkDuplicateDesignation");
                    opchk.getParamsMap().put("desigNm", desigNm);
                    opchk.execute();
                    if (opchk.getErrors().size() == 0 && opchk.getResult() != null)
                    {
                        String chkRsult = (String) opchk.getResult();
                        System.out.println("result::::  " + chkRsult);
                        if (chkRsult.equals("Y"))
                        {
                            showFacesMessage("Duplicate entry!!", "E", false, "V");
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean chkValidFrOrg(String id, Object object)
    {
        String msg = "";
        if (object != null && object.toString().length() > 0)
        {
            System.out.println("id is :::::" + id);
            if (id != null)
            {
                OperationBinding opchk = null;
                if (id.equals("organizationNm"))
                {
                    String orgNm = object.toString();
                    System.out.println("orgNm=" + orgNm);
                    opchk = ADFBeanUtils.getOperationBinding("chkDuplicateOrganization");
                    opchk.getParamsMap().put("orgNm", orgNm);
                    opchk.execute();
                }

                if (opchk.getErrors().size() == 0 && opchk.getResult() != null)
                {
                    String chkRsult = (String) opchk.getResult();
                    if (chkRsult.equals("Y"))
                    {
                        showFacesMessage("Duplicate entry!!", "E", false, "V");
                    }

                }
                else
                {
                    System.out.println("Error during duplicate check =" + opchk.getErrors());
                }
            }

        }
        return false;
    }

    public boolean chkValidFrSalComp(String id, Object object)
    {
        String msg = "";
        if (object != null && object.toString().length() > 0)
        {
            System.out.println("id is :::::" + id);
            if (id != null)
            {
                OperationBinding opchk = null;
                if (id.equals("salComponent"))
                {
                    String salId = object.toString();
                    System.out.println("salId=" + salId);
                    opchk = ADFBeanUtils.getOperationBinding("chkDuplicateSalComp");
                    opchk.getParamsMap().put("salId", salId);
                    opchk.execute();
                }

                if (opchk.getErrors().size() == 0 && opchk.getResult() != null)
                {
                    String chkRsult = (String) opchk.getResult();
                    if (chkRsult.equals("Y"))
                    {
                        showFacesMessage("Duplicate entry!!", "E", false, "V");
                    }

                }
                else
                {
                    System.out.println("Error during duplicate check =" + opchk.getErrors());
                }
            }

        }
        return false;
    }

    public boolean chkValidFrSalRefComp(String id, Object object)
    {
        String msg = "";

        if (object != null && object.toString().length() > 0)
        {

            if (id != null)
            {
                boolean result = chkIsRefCompSameAsMainComp(object);
                if (result)
                {
                    OperationBinding opchk = null;
                    if (id.equals("refSalComponent"))
                    {
                        String refsalId = object.toString();
                        System.out.println("refsalId=" + refsalId);
                        opchk = ADFBeanUtils.getOperationBinding("chkDuplicateRefSalComp");
                        opchk.getParamsMap().put("refsalId", refsalId);
                        opchk.execute();
                    }

                    if (opchk.getErrors().size() == 0 && opchk.getResult() != null)
                    {
                        String chkRsult = (String) opchk.getResult();
                        if (chkRsult.equals("Y"))
                        {
                            showFacesMessage("Duplicate entry!!", "E", false, "V");
                        }

                    }
                    else
                    {
                        System.out.println("Error during duplicate check =" + opchk.getErrors());
                    }
                }

            }

        }
        return false;
    }


    public void setOrganizatiionNmBinding(RichInputListOfValues organizatiionNmBinding)
    {
        this.organizatiionNmBinding = organizatiionNmBinding;
    }

    public RichInputListOfValues getOrganizatiionNmBinding()
    {
        return organizatiionNmBinding;
    }

    public void deleteOrgAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete1").execute();
    }

    public void validateGradeEndDate(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("*****validateGradeEndDate*********");
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;

            if (validStrtDtBinding.getValue() != null && validStrtDtBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) validStrtDtBinding.getValue()).dateValue();
                    System.out.println("start date::::" + strtDt);
                    endDt = ((Timestamp) object).dateValue();
                    System.out.println("end date ::::::::" + endDt);
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
                        showFacesMessage("To Date can not be before Valid From Date.", "E", false, "V");
                    }
                }
            }
        }

    }

    public void validateOrganizationStartDate(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("*****validateOrganizationStartDate*********");
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            java.sql.Date orgStrtDt = null;

            if (validStrtDtBinding.getValue() != null && validStrtDtBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) validStrtDtBinding.getValue()).dateValue();
                    System.out.println("start date for grade::::" + strtDt);

                    endDt = ((Timestamp) validEndDtBinding.getValue()).dateValue();
                    System.out.println("end date for grade::::" + endDt);


                    orgStrtDt = ((Timestamp) object).dateValue();
                    System.out.println("start date for org ::::::::" + orgStrtDt);
                }
                catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(orgStrtDt) > 0)
                {
                    if (strtDt.toString().equals(orgStrtDt.toString()))
                    {
                    }
                    else
                    {
                        showFacesMessage("Organization Start Date can not be before Valid Grade From Date.", "E", false,
                                         "V");
                    }
                }
                /*  else if(orgStrtDt.compareTo(endDt) > 0) {
                    if (endDt.toString().equals(orgStrtDt.toString())) {
                    } else {
                        showFacesMessage("Organization Start Date can not be after Valid Grade From Date.", "E", false,
                                         "V");
                    }
                }  */

            }

        }
    }

    public void validateOrganizationEndDate(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        // Add event code here...
        System.out.println("*****validateOrganizationEndDate*********");
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;

            if (orgValidStrtDtBinding.getValue() != null && orgValidStrtDtBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) orgValidStrtDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                    System.out.println("end date ::::::::" + endDt);
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
                        showFacesMessage("To Date can not be before Valid From Date.", "E", false, "V");
                    }
                }
            }
        }
    }

    public void validateMaxPayScale(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("*****validateMaxPayScale *********");

        if (object != null)
        {
            if (minPayScaleBinding.getValue() != null)
            {
                oracle.jbo.domain.Number max = (oracle.jbo.domain.Number) object;
                oracle.jbo.domain.Number min = (oracle.jbo.domain.Number) minPayScaleBinding.getValue();
                if (max.compareTo(0) < 0)
                {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                  "Max Pay Scale can not be in negative"));
                }
                if (max.compareTo(min) < 0)
                {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                  "Max Pay Scale should be greater than Min pay Scale"));
                }
            }

        }

    }

    public void validateMinSalScale(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        // Add event code here...

        System.out.println("***** in validateMinPayScale *********");

        if (object != null)
        {
            if (minPayScaleBinding.getValue() != null)
            {
                oracle.jbo.domain.Number max = (oracle.jbo.domain.Number) object;
                oracle.jbo.domain.Number min = (oracle.jbo.domain.Number) minPayScaleBinding.getValue();
                if (max.compareTo(0) < 0)
                {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                  "Mix Pay Scale can not be in negative"));
                }
                if (max.compareTo(min) > 0)
                {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                  "Min Pay Scale should be less than Max pay Scale"));
                }
            }

        }

    }

    public void searchActionAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.findOperation("SearchmthedAM").execute();

        // ADFBeanUtils.callBindingsMethod("SearchmthedAM", null, null);
    }

    public void resetAL(ActionEvent actionEvent)
    {

        System.out.println("in reset action listner");
        ADFBeanUtils.findOperation("resetAm").execute();


        // ADFBeanUtils.callBindingsMethod("resetAm", null, null);
    }


    public void replicatetoAllACL(ActionEvent actionEvent)
    {
        boolean result = chkSveDesiGrdValidation();
        if (result)
        {
            result = chkOrgSaveValidation();
        }
        if (result)
        {
            OperationBinding ob = ADFBeanUtils.findOperation("replicateToALL");
            ob.execute();
        }

    }


    private RichPopup gradReffSalPopUpBinding;
    private RichSelectOneChoice salCompNmBinding;
    private RichSelectOneChoice salTypeBinding;
    private RichInputText salAmntBinding;
    private RichInputText salValBinding;

    private RichSelectOneChoice reffsalCompBinding;
    private RichInputText reffSalPercBinding;
    private RichInputDate reffValidStrtDtBinding;
    private RichInputDate reffValidEndDtBinding;
    private RichSelectOneChoice salCompBinding;


    public void setGradReffSalPopUpBinding(RichPopup gradReffSalPopUpBinding)
    {
        this.gradReffSalPopUpBinding = gradReffSalPopUpBinding;
    }

    public RichPopup getGradReffSalPopUpBinding()
    {
        return gradReffSalPopUpBinding;
    }

    public boolean chkReffSallCompSaveValidation()
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmGrdSalReff1Iterator");
        String message = "";
        if (di.getEstimatedRowCount() > 0)
        {
            if (reffsalCompBinding.getValue() == null || reffsalCompBinding.getValue() == "")
            {
                String cid = reffsalCompBinding.getClientId();
                message = "Please select refrencae .";
                showMessage(message, cid);
                return false;
            }
            if (reffSalPercBinding.getValue() == null || reffSalPercBinding.getValue() == "")
            {
                String cid = reffSalPercBinding.getClientId();
                message = "Please enter percentage.";
                showMessage(message, cid);
                return false;
            }
            if (reffValidStrtDtBinding.getValue() == null || reffValidStrtDtBinding.getValue() == "")
            {
                String cid = reffValidStrtDtBinding.getClientId();
                message = "Please select valid start date.";
                showMessage(message, cid);
                return false;
            }
        }
        return true;
    }

    public boolean chkCTCReffSallCompSaveValidation()
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmCtcGrdSalReff1Iterator");
        String message = "";
        if (di.getEstimatedRowCount() > 0)
        {
            if (ctcReffCompId.getValue() == null || ctcReffCompId.getValue() == "")
            {
                String cid = ctcReffCompId.getClientId();
                message = "Please select refrencae .";
                showMessage(message, cid);
                return false;
            }
            if (ctcPercBinding.getValue() == null || ctcPercBinding.getValue() == "")
            {
                String cid = ctcPercBinding.getClientId();
                message = "Please enter percentage.";
                showMessage(message, cid);
                return false;
            }
        }
        return true;
    }

    public boolean chkSveSalStructValidation()
    {
        DCIteratorBinding di = null;
        di = ADFBeanUtils.findIterator("HcmGrdSal1Iterator");
        String message = "";
        if (di.getEstimatedRowCount() > 0)
        {
            if (salCompNmBinding.getValue() == null || salCompNmBinding.getValue() == "")
            {
                String cid = salCompNmBinding.getClientId();
                message = "Please select salary component.";
                showMessage(message, cid);
                return false;
            }
            if (salTypeBinding.getValue() == null || salTypeBinding.getValue() == "")
            {
                String cid = salTypeBinding.getClientId();
                message = "Please select type.";
                showMessage(message, cid);
                return false;
            }

            if (salValBinding.getValue() == null || salValBinding.getValue() == "")
            {
                String cid = salValBinding.getClientId();
                message = "Please enter salary amount.";
                showMessage(message, cid);
                return false;
            }
            else
            {
                if (salTypeBinding.getValue().equals("A"))
                {
                    BigDecimal ComZero = BigDecimal.ZERO;
                    BigDecimal value = new BigDecimal(salValBinding.getValue().toString());
                    int newValue = value.compareTo(ComZero);
                    if (newValue <= 0)
                    {
                        message = "salary amount can not be zero or less than zero";
                        String cid = salValBinding.getClientId();
                        showMessage(message, cid);
                        return false;
                    }
                }


            }
            if (salValidStartBinding.getValue() == null || salValidStartBinding.getValue() == "")
            {
                String cid = salValidStartBinding.getClientId();
                message = "Please enter valid start date.";
                showMessage(message, cid);
                return false;
            }
        }

        return true;
    }

    public boolean chkSveCTCSalStructValidation()
    {
        DCIteratorBinding di = null;
        di = ADFBeanUtils.findIterator("HcmCtcGrdSal1Iterator");
        String message = "";
        if (di.getEstimatedRowCount() > 0)
        {
            if ((ctcCompIdBinding.getValue() == null || ctcCompIdBinding.getValue() == "") &&
                (ctcDedIdBinding.getValue() == null || ctcDedIdBinding.getValue() == ""))
            {
                String cid = ctcCompIdBinding.getClientId();
                message = "Please select either salary component or Deduction.";
                showMessage(message, cid);
                return false;
            }
            if (ctcCompTypeBinding.getValue() == null || ctcCompTypeBinding.getValue() == "")
            {
                String cid = ctcCompTypeBinding.getClientId();
                message = "Please select type.";
                showMessage(message, cid);
                return false;
            }
            if (ctcCompTypeBinding.getValue().equals("A"))
            {
                if (ctcAmntBinding.getValue() == null || ctcAmntBinding.getValue() == "")
                {
                    String cid = ctcAmntBinding.getClientId();
                    message = "Please enter salary amount.";
                    showMessage(message, cid);
                    return false;
                }
                else
                {
                    if (ctcAmntBinding.getValue().equals("A"))
                    {
                        BigDecimal ComZero = BigDecimal.ZERO;
                        BigDecimal value = new BigDecimal(salValBinding.getValue().toString());
                        int newValue = value.compareTo(ComZero);
                        if (newValue <= 0)
                        {
                            message = "salary amount can not be zero or less than zero";
                            String cid = salValBinding.getClientId();
                            showMessage(message, cid);
                            return false;
                        }
                    }
                }
            }
            if (ctcValidFromDtBinding.getValue() == null || ctcValidFromDtBinding.getValue() == "")
            {
                String cid = ctcValidFromDtBinding.getClientId();
                message = "Please enter valid start date.";
                showMessage(message, cid);
                return false;
            }
        }

        return true;
    }

    public void openReffSallPopForAddAL(ActionEvent actionEvent)
    {
        boolean result = chkSveSalStructValidation();
        if (result)
        {
            OperationBinding opchk = null;
            opchk = ADFBeanUtils.getOperationBinding("makePostChanges");
            opchk.execute();
            showPopup(gradReffSalPopUpBinding, true);
        }

    }

    public void openCTCReffSallPopForAddAL(ActionEvent actionEvent)
    {
        boolean result = true;
        if (result)
        {
            OperationBinding opchk = null;
            opchk = ADFBeanUtils.getOperationBinding("makePostChanges");
            opchk.execute();
            showPopup(ctcReffSalBinding, true);
        }

    }

    public void closeReffSallPopupAL(ActionEvent actionEvent)
    {
        boolean result = chkReffSallCompSaveValidation();
        if (result)
        {
            OperationBinding opchk = null;
            opchk = ADFBeanUtils.getOperationBinding("updateSalAmt");
            opchk.execute();
            gradReffSalPopUpBinding.hide();
        }

    }

    public void closeCTCReffSallPopupAL(ActionEvent actionEvent)
    {
        boolean result = chkCTCReffSallCompSaveValidation();
        if (result)
        {

            ctcReffSalBinding.hide();
        }

    }

    public void addGradeSalComAL(ActionEvent actionEvent)
    {
        boolean result = chkSveDesiGrdValidation();
        if (result)
        {
            OperationBinding opchk = null;
            opchk = ADFBeanUtils.getOperationBinding("CreateInsert2");
            opchk.execute();
            setAdd_edit_mode_frSal("D");

        }
    }

    public void addCTCGradeSalComAL(ActionEvent actionEvent)
    {

        boolean result = chkSveDesiGrdValidation();
        if (result)
        {
            OperationBinding opchk = null;
            OperationBinding opslc = null;
            opchk = ADFBeanUtils.getOperationBinding("CreateInsert5");
            opchk.execute();
            setAdd_edit_mode_frCTC("D");
//            opslc = ADFBeanUtils.getOperationBinding("generatesequenceid");
//            opslc.execute();
            
        }
    }


    /*  public void editGradeAL(ActionEvent actionEvent)
    {
        RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "D");
    } */

    public void addGradeReffSallCompAL(ActionEvent actionEvent)
    {
        OperationBinding opchk = null;
        opchk = ADFBeanUtils.getOperationBinding("CreateInsert3");
        opchk.execute();
        opchk = ADFBeanUtils.getOperationBinding("setValidStartDt");
        opchk.execute();
    }

    public void addCTCGradeReffSallCompAL(ActionEvent actionEvent)
    {
        OperationBinding opchk = null;
        opchk = ADFBeanUtils.getOperationBinding("CreateInsert6");
        opchk.execute();
        opchk = ADFBeanUtils.getOperationBinding("setValidStartDtFrCTC");
        opchk.execute();
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


    public void setSalCompNmBinding(RichSelectOneChoice salCompNmBinding)
    {
        this.salCompNmBinding = salCompNmBinding;
    }

    public RichSelectOneChoice getSalCompNmBinding()
    {
        return salCompNmBinding;
    }

    public void setSalTypeBinding(RichSelectOneChoice salTypeBinding)
    {
        this.salTypeBinding = salTypeBinding;
    }

    public RichSelectOneChoice getSalTypeBinding()
    {
        return salTypeBinding;
    }

    public void setSalAmntBinding(RichInputText salAmntBinding)
    {
        this.salAmntBinding = salAmntBinding;
    }

    public RichInputText getSalAmntBinding()
    {
        return salAmntBinding;
    }

    public void setSalValBinding(RichInputText salValBinding)
    {
        this.salValBinding = salValBinding;
    }

    public RichInputText getSalValBinding()
    {
        return salValBinding;
    }

    public void setReffsalCompBinding(RichSelectOneChoice reffsalCompBinding)
    {
        this.reffsalCompBinding = reffsalCompBinding;
    }

    public RichSelectOneChoice getReffsalCompBinding()
    {
        return reffsalCompBinding;
    }

    public void setReffSalPercBinding(RichInputText reffSalPercBinding)
    {
        this.reffSalPercBinding = reffSalPercBinding;
    }

    public RichInputText getReffSalPercBinding()
    {
        return reffSalPercBinding;
    }

    public void setReffValidStrtDtBinding(RichInputDate reffValidStrtDtBinding)
    {
        this.reffValidStrtDtBinding = reffValidStrtDtBinding;
    }

    public RichInputDate getReffValidStrtDtBinding()
    {
        return reffValidStrtDtBinding;
    }

    public void setReffValidEndDtBinding(RichInputDate reffValidEndDtBinding)
    {
        this.reffValidEndDtBinding = reffValidEndDtBinding;
    }

    public RichInputDate getReffValidEndDtBinding()
    {
        return reffValidEndDtBinding;
    }

    public void deleteGradeSalStructAL(ActionEvent actionEvent)
    {
        OperationBinding opchk = null;
        opchk = ADFBeanUtils.getOperationBinding("deleteSalComp");
        opchk.execute();
        if (opchk.getResult() != null && opchk.getResult().toString().equals("Y"))
        {
            OperationBinding opDelRw = ADFBeanUtils.getOperationBinding("Delete2");
            opDelRw.execute();
        }
        else
        {
            showFacesMessage("Can not delete this salary component!!.Salary component is being used for refrence..",
                             "E", false, "F");

        }

    }

    public void deleteCTCGradeSalStructAL(ActionEvent actionEvent)
    {
        OperationBinding opchk = null;
        opchk = ADFBeanUtils.getOperationBinding("deleteCTCSalComp");
        opchk.execute();
        if (opchk.getResult() != null && opchk.getResult().toString().equals("Y"))
        {
            OperationBinding opDelRw = ADFBeanUtils.getOperationBinding("Delete5");
            opDelRw.execute();
        }
        else
        {
            showFacesMessage("Can not delete this salary component!!.Salary component is being used for refrence..",
                             "E", false, "F");

        }

    }

    public void deleteReffSalCompAL(ActionEvent actionEvent)
    {
        OperationBinding opchk = null;
        opchk = ADFBeanUtils.getOperationBinding("Delete3");
        opchk.execute();
    }

    public void deleteCTCReffSalCompAL(ActionEvent actionEvent)
    {
        OperationBinding opchk = null;
        opchk = ADFBeanUtils.getOperationBinding("Delete6");
        opchk.execute();
    }

    public void salValVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "")
        {
            salAmntBinding.setValue(valueChangeEvent.getNewValue());
        }
    }

    public void salValValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("object==" + object);
        String msg = "";
        if (object != null || object != "")
        {

            BigDecimal frCmpreZro = BigDecimal.ZERO;
            BigDecimal value = new BigDecimal(object.toString());
            int newvalue = value.compareTo(frCmpreZro);
            if (newvalue == -1)
            {
                msg = "salary value should not be zero!!";
                showFacesMessage(msg, "E", true, "V");
            }
        }

        else
        {
            String msg1 = "enter the salary!";
            ADFModelUtils.showFacesMessage(msg1, msg1, FacesMessage.SEVERITY_ERROR, salValBinding.getClientId());
        }
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total)
    {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void setSalCompBinding(RichSelectOneChoice salCompBinding)
    {
        this.salCompBinding = salCompBinding;
    }

    public RichSelectOneChoice getSalCompBinding()
    {
        return salCompBinding;
    }

    public boolean chkIsRefCompSameAsMainComp(Object object)
    {
        String msg = "";
        if (object != null && object.toString().length() > 0)
        {
            String reffsalid = object.toString();
            String salId = salCompBinding.getValue().toString();
            if (salId.equals(reffsalid))
            {
                msg = "Refrence component can not be same as main component. ";
                showFacesMessage(msg, "E", false, "V");
                return false;
            }
            return true;
        }
        return true;
    }


    public void okAllValidationAL(ActionEvent actionEvent)
    {
        boolean result = chkSveSalStructValidation();
        if (result)
        {
            DCIteratorBinding di = null;
            di = ADFBeanUtils.findIterator("HcmGrdSal1Iterator");
            String message = "";
            if (di.getEstimatedRowCount() > 0)
            {
                if (salTypeBinding.getValue().equals("P"))
                {
                    di = ADFBeanUtils.findIterator("HcmGrdSalReff1Iterator");
                    if (di.getEstimatedRowCount() == 0)
                    {
                        message = "Please add refrence component or change type to amount.";
                        String cid = salTypeBinding.getClientId();
                        showMessage(message, cid);
                        result = false;
                    }
                }
            }
            if (result)
            {
                showFacesMessage("Saved successfully.", "I", false, "F");
                setAdd_edit_mode_frSal("E");
            }

            // RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "E");
        }
    }

    public void okAllCTCValidationAL(ActionEvent actionEvent)
    {
        boolean result = chkSveCTCSalStructValidation();
        if (result)
        {
            DCIteratorBinding di = null;
            di = ADFBeanUtils.findIterator("HcmCtcGrdSal1Iterator");
            String message = "";
            if (di.getEstimatedRowCount() > 0)
            {
                if (ctcCompTypeBinding.getValue().equals("P"))
                {
                    di = ADFBeanUtils.findIterator("HcmCtcGrdSalReff1Iterator");
                    if (di.getEstimatedRowCount() == 0)
                    {
                        message = "Please add refrence component or change type to amount.";
                        String cid = salTypeBinding.getClientId();
                        showMessage(message, cid);
                        result = false;
                    }
                }
            }
            if (result)
            {
                showFacesMessage("Saved successfully.", "I", false, "F");
                setAdd_edit_mode_frCTC("E");
            }

            // RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "E");
        }
    }


    public void validateSalaryStructureEndDate(FacesContext facesContext, UIComponent uIComponent, Object object)
    {

        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;

            if (salValidStartBinding.getValue() != null && salValidStartBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) salValidStartBinding.getValue()).dateValue();
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
                        showFacesMessage("To Date can not be before Valid From Date. ", "E", false, "V");
                    }
                }
            }
        }
    }

    public void editGradeACL(ActionEvent actionEvent)
    {
        setAdd_edit_mode_frSal("D");
        // RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "D");
    }

    public void editCTCGradeACL(ActionEvent actionEvent)
    {
        setAdd_edit_mode_frCTC("D");
        // RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "D");
    }


    public void setSalValidStartBinding(RichInputDate salValidStartBinding)
    {
        this.salValidStartBinding = salValidStartBinding;
    }

    public RichInputDate getSalValidStartBinding()
    {
        return salValidStartBinding;
    }

    public void setSalValidEndDtBinding(RichInputDate salValidEndDtBinding)
    {
        this.salValidEndDtBinding = salValidEndDtBinding;
    }

    public RichInputDate getSalValidEndDtBinding()
    {
        return salValidEndDtBinding;
    }

    public void salaryAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        // Add event code here...
        //if(this.get)


    }

    public void setGradValidStrtDtBinding(RichInputDate gradValidStrtDtBinding)
    {
        this.gradValidStrtDtBinding = gradValidStrtDtBinding;
    }

    public RichInputDate getGradValidStrtDtBinding()
    {
        return gradValidStrtDtBinding;
    }

    public void setCtcReffSalBinding(RichPopup ctcReffSalBinding)
    {
        this.ctcReffSalBinding = ctcReffSalBinding;
    }

    public RichPopup getCtcReffSalBinding()
    {
        return ctcReffSalBinding;
    }

    public void setCtcCompTypeBinding(RichSelectOneChoice ctcCompTypeBinding)
    {
        this.ctcCompTypeBinding = ctcCompTypeBinding;
    }

    public RichSelectOneChoice getCtcCompTypeBinding()
    {
        return ctcCompTypeBinding;
    }

    public void setCtcCompIdBinding(RichSelectOneChoice ctcCompIdBinding)
    {
        this.ctcCompIdBinding = ctcCompIdBinding;
    }

    public RichSelectOneChoice getCtcCompIdBinding()
    {
        return ctcCompIdBinding;
    }

    public void setCtcDedIdBinding(RichSelectOneChoice ctcDedIdBinding)
    {
        this.ctcDedIdBinding = ctcDedIdBinding;
    }

    public RichSelectOneChoice getCtcDedIdBinding()
    {
        return ctcDedIdBinding;
    }

    public void setCtcAmntBinding(RichInputText ctcAmntBinding)
    {
        this.ctcAmntBinding = ctcAmntBinding;
    }

    public RichInputText getCtcAmntBinding()
    {
        return ctcAmntBinding;
    }

    public void setCtcValidFromDtBinding(RichInputDate ctcValidFromDtBinding)
    {
        this.ctcValidFromDtBinding = ctcValidFromDtBinding;
    }

    public RichInputDate getCtcValidFromDtBinding()
    {
        return ctcValidFromDtBinding;
    }

    public void setCtcReffCompId(RichSelectOneChoice ctcReffCompId)
    {
        this.ctcReffCompId = ctcReffCompId;
    }

    public RichSelectOneChoice getCtcReffCompId()
    {
        return ctcReffCompId;
    }

    public void setCtcPercBinding(RichInputText ctcPercBinding)
    {
        this.ctcPercBinding = ctcPercBinding;
    }

    public RichInputText getCtcPercBinding()
    {
        return ctcPercBinding;
    }

    public void ComponentValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
                    Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateCompMethod", new Object[] { object }, new Object[] {
                                                                            "Comp" });
                    if (bindingsMethod != null) {
                        if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          bindingsMethod.toString(), null));

                        }
                    } // Add event code here...

    }
}

    public void DeductionValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
                    Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateDeductMethod", new Object[] { object }, new Object[] {
                                                                            "Deduct" });
                    if (bindingsMethod != null) {
                        if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          bindingsMethod.toString(), null));

                        }
                    } // Add event code here...
  // Add event code here...

    }
}

    public void TypeValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
                    Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateTypeMethod", new Object[] { object }, new Object[] {
                                                                            "Type" });
                    if (bindingsMethod != null) {
                        if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          bindingsMethod.toString(), null));

                        }
                    } // Add event code here...

    }
}

    public void AmountValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
    if (object != null) {
                Number AMT = (Number) object;                               
                 if (AMT.compareTo(0) <= 0 ) 
                 {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Amount Value",
                                                                  null));

                }
            }
            
        }

    public void Component2Validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
                    Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateComp2Method", new Object[] { object }, new Object[] {
                                                                            "Comp" });
                    if (bindingsMethod != null) {
                        if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          bindingsMethod.toString(), null));

                        }
                    }  // Add event code here...

    }
}
public void PercentageValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
                    Integer val = Integer.parseInt(object.toString());
                    if (val < 0) {
                        throw new ValidatorException(new FacesMessage("Invalid Value"," Value must be in between 1 to 100"));
                    }
                }  // Add event code here...

    }

    public void Comp3Validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
                    Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateComp3Method", new Object[] { object }, new Object[] {
                                                                            "Comp" });
                    if (bindingsMethod != null) {
                        if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          bindingsMethod.toString(), null));

                        }
                    }  // Add event code here...

    }
}

    public void Deduction2Validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
    
            if (object != null) {
                        Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateDeduct2Method", new Object[] { object }, new Object[] {
                                                                                "Deduct" });
                        if (bindingsMethod != null) {
                            if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                              bindingsMethod.toString(), null));

                            }
                        }  // Add event code here...
   

    }
    }

    public void Referencevalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
                    Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateReffMethod", new Object[] { object }, new Object[] {
                                                                            "reff" });
                    if (bindingsMethod != null) {
                        if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          bindingsMethod.toString(), null));

                       // Add event code here...
                        }
    }
}
    }

    public void Percentage2Validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
                    Integer val = Integer.parseInt(object.toString());
                    if (val < 0||val>100) {
                        throw new ValidatorException(new FacesMessage("Invalid Value"," Value must be in between 1 to 100"));
                    }
                }  // Add event code here...

    }
}
