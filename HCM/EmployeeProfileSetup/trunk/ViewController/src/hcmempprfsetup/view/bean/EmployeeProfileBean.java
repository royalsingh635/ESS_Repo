package hcmempprfsetup.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import javax.imageio.ImageIO;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.util.CalendarActivityRamp;
import oracle.adf.view.rich.util.InstanceStyles;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlValueBindingRef;

import oracle.sql.NUMBER;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class EmployeeProfileBean
{
    private RichInputText imgIdBinding;
    public String modeOD = "V";
    public String modePD = "V";
    public String modeID = "V";
    public String modePMD = "V";
    public String modeSD = "V";
    public String modeLD = "V";
    public String modeTC = "V";
    public String modeSkD = "V";
    public String modeDoj = "V";
    public String modeOthrDed = "V";
    private RichInputText issuAuthorityBinding;
    private RichInputDate expDtBinding;
    private RichInputDate issueDtBinding;
    private RichInputText issuePlaceBinding;
    private RichInputText documentNoBinding;
    private RichSelectOneChoice othDetailTypeBinding;
    private List<UploadedFile> uploadedFile;
    private RichInputText memberNameBinding;
    private RichInputText memberRelationBinding;
    private RichSelectOneChoice memberGenderBinding;
    private RichInputText noticePrdBinding;
    private RichInputDate relevngDateBinding;
    private RichInputText leaveBalanceBinding;
    private RichSelectOneChoice salGradeBinding;
    private RichSelectOneChoice empDesigBinding;
    private RichOutputText gradePayChkBiinding;

    public void setUploadedFile(List<UploadedFile> uploadedFile)
    {
        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFile()
    {
        return uploadedFile;
    }

    public void setModeOthrDed(String modeOthrDed)
    {
        this.modeOthrDed = modeOthrDed;
    }

    public String getModeOthrDed()
    {
        return modeOthrDed;
    }
    private RichInputText isDedChkBinding;
    private RichInputText empSalIdBinding;
    private RichInputText salValBinding;
    private RichSelectOneChoice subSalIdBinding;
    private RichInputText subSalAmntBinding;
    private RichInputText transIsOtherDedBinding;
    private RichTable subSalCompTbleBinding;
    private RichPopup subSalPopBinding;
    private RichPopup refSalPopBinding;
    private RichSelectOneChoice refSalIdbinding;
    private RichInputText refSalPercBinding;
    private RichTable reffSalTableBinding;

    public void setModeDoj(String modeDoj)
    {
        this.modeDoj = modeDoj;
    }

    public String getModeDoj()
    {
        return modeDoj;
    }
    private RichSelectOneChoice salIdBinding;
    private RichInputText ifscCodeBinding;
    private RichInputText swiftCodeBinding;
    private RichInputText eoNmBinding;
    private RichInputDate wrkStatDtBinding;
    private RichInputDate salEndDt;
    private RichInputDate reffValidStrtDt;
    private RichInputDate reffValidEndDt;
    private RichSelectOneChoice workStatusBinding;
    private RichInputText requirementAreaNmBinding;
    private RichInputListOfValues reqAddressBinding;
    private RichInputListOfValues wareHouseBinding;
    private RichSelectBooleanCheckbox isCostCentrBinding;
    private RichSelectBooleanCheckbox isTemIssueOnly;
    private RichSelectBooleanCheckbox isProductionBinding;
    private RichSelectBooleanCheckbox isServceCentrBinding;
    private RichInputText employeeNmBinding;
    private RichInputText reqAddressNmBinding;
    private RichSelectBooleanCheckbox reqmntAreaFlagBinding;
    private RichLink addRqmtAreaBttn;
    private RichInputText employeeIdBinding;

    public void setModeTC(String modeTC)
    {
        this.modeTC = modeTC;
    }

    public String getModeTC()
    {
        return modeTC;
    }
    private RichInputText empNmSearchBinding;
    private RichInputText empIdSearchBinding;
    private RichInputDate dojBinding;
    private RichInputDate dobBinding;
    private RichInputDate leaveStDtBinding;
    private RichInputDate salStDtBinding;
    private HashMap activityStyles = new HashMap<Set<String>, InstanceStyles>();
    private RichSelectOneRadio salTypeBinding;

    public void setModeSD(String modeSD)
    {
        this.modeSD = modeSD;
    }

    public String getModeSD()
    {
        return modeSD;
    }
    private RichPopup imgPopupBinding;
    private RichInputFile upldFileBinding;
    private UploadedFile file;
    InputStream inputStream;


    public void setModeID(String modeID)
    {
        this.modeID = modeID;
    }

    public String getModeID()
    {
        return modeID;
    }

    public void setModePMD(String modePMD)
    {
        this.modePMD = modePMD;
    }

    public String getModePMD()
    {
        return modePMD;
    }

    public EmployeeProfileBean()
    {
    }


    public void setFile(UploadedFile file)
    {
        this.file = file;
    }

    public UploadedFile getFile()
    {
        return file;
    }


    private String getVal()
    {
        OperationBinding op = ADFBeanUtils.getOperationBinding("pathGetFrmDb");
        op.execute();
        return (String) op.getResult();
    }

    protected void refreshPage()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }


    public String resolvEl(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public void setImgIdBinding(RichInputText imgIdBinding)
    {
        this.imgIdBinding = imgIdBinding;
    }

    public RichInputText getImgIdBinding()
    {
        return imgIdBinding;
    }

    public void imgUploadPopDL(DialogEvent dialogEvent)
    {
        String OrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");

        if (dialogEvent.getOutcome().name().equals("ok"))
        {
            UploadedFile myfile = this.getFile();
            if (myfile.getContentType().equalsIgnoreCase("image/jpeg") ||
                myfile.getContentType().equalsIgnoreCase("image/png") ||
                myfile.getContentType().equalsIgnoreCase("image/bmp") ||
                myfile.getContentType().equalsIgnoreCase("image/gif"))
            {

                String path = getVal();
                String type = "PNG";
                String TypeVal = ".png";
                if (myfile.getContentType().equalsIgnoreCase("image/jpeg"))
                {
                    type = "JPEG";
                    TypeVal = ".jpeg";
                }
                else if (myfile.getContentType().equalsIgnoreCase("image/png"))
                {
                    type = "PNG";
                    TypeVal = ".png";
                }
                else if (myfile.getContentType().equalsIgnoreCase("image/bmp"))
                {
                    type = "BMP";
                    TypeVal = ".bmp";
                }
                else if (myfile.getContentType().equalsIgnoreCase("image/gif"))
                {
                    type = "GIF";
                    TypeVal = ".gif";
                }

                OperationBinding opbind = ADFBeanUtils.getOperationBinding("imageIdGenerate");
                opbind.execute();

                String ImgId = (String) opbind.execute();
                System.out.println("Image Id =" + ImgId);
                try
                {
                    System.out.println("My file type=" + myfile.getContentType());
                    System.out.println("Input Stream=" + inputStream);
                    BufferedImage input = ImageIO.read(inputStream);
                    System.out.println("2");
                    //to save image in another directory..............
                    File outputFile = new File(path + OrgId + ImgId + TypeVal);
                    System.out.println("OutputFile=" + outputFile);
                    ImageIO.write(input, type, outputFile);
                    String Serverpath = outputFile.getAbsolutePath();
                    OperationBinding opSetAtt = ADFBeanUtils.getOperationBinding("imgAttSetter");
                    opSetAtt.getParamsMap().put("fileType", type);
                    opSetAtt.getParamsMap().put("imgPath", Serverpath);
                    opSetAtt.getParamsMap().put("imgId", OrgId + ImgId);
                    opSetAtt.execute();
                    inputStream.close();
                    System.out.println("Refresh Page");
                    refreshPage();
                    /* if (getMode().equals("E") || getMode().equals("A")) {
                                //Mode2="D";
                            refreshPage();
                            } */


                }
                catch (Exception ex) {
                    System.out.println("exception=" + ex.getStackTrace());
                }
            }
            else
            {
                String valMsg = "Invalid Image Type"; //resolvEl("#{bundle['MSG.478']}").toString();
                FacesMessage msg = new FacesMessage(valMsg);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
            }
            //this.Mode2="D";
            setFile(null);
            /*  if(imagepathbinding.getValue()!=null) {
                    System.out.println("Image Path is:"+imagepathbinding.getValue());
                    this.Mode3="E";
                    this.Mode4="E";

                } */
        }
    }

    public void addEditOffDtlAL(ActionEvent actionEvent)
    {
        Integer status = (Integer) workStatusBinding.getValue();
        if (status.equals(32) || status.equals(33))
        {
            OperationBinding opchkProc = ADFBeanUtils.getOperationBinding("isSalaryProcPending");
            opchkProc.execute();
            if (opchkProc.getErrors() != null && opchkProc.getResult().toString().equals("N"))
            {
                OperationBinding opchk = ADFBeanUtils.getOperationBinding("isAnySalaryProcess");
                opchk.execute();
                if (opchk.getErrors() != null && opchk.getResult().toString().equals("N"))
                {
                    setModeDoj("A");
                }
                modeOD = "A";
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_OFF_DTL", "A");
                OperationBinding opexec = ADFBeanUtils.getOperationBinding("executeLovVoInOffcialDtl");
                opexec.execute();
            }
            else
            {
                showFacesMessage("MSG.1477", "I", true, "F");
            }
        }
        else
        {
            String msg = "Can not edit this employee:Employee working status is On Hold/Resigned.";
            showFacesMessage(msg, "I", false, "F");
        }

    }

    public void setModeOD(String modeOD)
    {
        this.modeOD = modeOD;
    }

    public String getModeOD()
    {
        return modeOD;
    }

    public void setModePD(String modePD)
    {
        this.modePD = modePD;
    }

    public String getModePD()
    {
        return modePD;
    }

    public void editPersDtlAL(ActionEvent actionEvent)
    {
        Integer status = (Integer) workStatusBinding.getValue();
        if (status.equals(32) || status.equals(33))
        {
            modePD = "A";
        }
        else
        {
            String msg = "Can not edit this employee:Employee working status is On Hold/Resigned.";
            showFacesMessage(msg, "I", false, "F");
        }


    }

    public void addEditImgAL(ActionEvent actionEvent)
    {
        Integer status = (Integer) workStatusBinding.getValue();
        if (status.equals(32) || status.equals(33))
        {
            modeID = "A";
            showPopup(imgPopupBinding, true);
        }
        else
        {
            String msg = "Can not edit this employee:Employee working status is On Hold/Resigned.";
            showFacesMessage(msg, "I", false, "F");
        }

    }


    public void saveEmpDtlAL(ActionEvent actionEvent)
    {
        boolean status = chkReffSalComponent();
        if (status)
        {
            status = chkdependentDtl();
            if (status)
            {
                status = chkOtherDtl();
            }
        }
        if (status)
        {
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkForNullField");
            op.execute();
            if (op.getErrors().size() == 0 && op.getResult() != null)
                if (op.getResult().toString().equals("OK"))
                {
                    OperationBinding opSetEmpCode = ADFBeanUtils.getOperationBinding("genEmpCodeForEmp");
                    opSetEmpCode.execute();
                    OperationBinding opSavCostcenter = ADFBeanUtils.getOperationBinding("sendDateFromTempCcToSlsRmaCc");
                    opSavCostcenter.execute();

                    if (opSetEmpCode.getErrors().size() == 0 && opSetEmpCode.getResult() != null &&
                        opSetEmpCode.getResult().equals("Y"))
                    {
                        ADFBeanUtils.getOperationBinding("deleteView").execute();
                        OperationBinding opsv = null;

                        opsv = ADFBeanUtils.getOperationBinding("Commit");
                        opsv.execute();

                        if (opsv.getErrors().size() == 0)
                        {
                            modePD = "V";
                            modeOD = "V";
                            modeID = "V";
                            modePMD = "V";
                            modeSD = "V";
                            modeLD = "V";
                            modeTC = "V";
                            modeSkD = "V";
                            setModeOthrDed("V");
                            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_LEAVE", "V");
                            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_SAL", "V");
                            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_SKILL", "V");
                            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_OFF_DTL", "V");
                            setModeDoj("V");

                            showFacesMessage("MSG.91", "I", true, "F");
                        }
                        else
                            showFacesMessage("Something went wrong. Error is " + opSetEmpCode.getErrors(), "E", false,
                                             "F");
                    }
                    else if (opSetEmpCode.getResult() == null)
                    {
                        showFacesMessage("Something went wrong. Error is " + opSetEmpCode.getErrors(), "E", false, "F");
                    }
                    else if (opSetEmpCode.getResult().toString().equals("N"))
                        showFacesMessage("MSG.1479", "E", true, "F");
                }
                else if (op.getResult().toString().equals("OD"))
                {
                    showFacesMessage("MSG.1480", "E", true, "F");
                }
                else if (op.getResult().toString().equals("PD"))
                {
                    showFacesMessage("Please Fill all (*) Personal Details.", "E", false, "F");
                }
                else if (op.getResult().toString().equals("BD"))
                {
                    showFacesMessage("Please Fill all (*) Payment Details.", "E", false, "F");
                }
        }


    }

    public void editPaymentDtlAL(ActionEvent actionEvent)
    {
        Integer status = (Integer) workStatusBinding.getValue();
        if (status.equals(32) || status.equals(33))
        {
            OperationBinding opchkProc = ADFBeanUtils.getOperationBinding("isSalaryProcPending");
            opchkProc.execute();
            if (opchkProc.getErrors() != null && opchkProc.getResult().toString().equals("N"))
            {
                modePMD = "A";
            }
            else
            {
                showFacesMessage("MSG.1477", "I", true, "F");
            }
        }
        else
        {
            String msg = "Can not edit this employee:Employee working status is On Hold/Resigned.";
            showFacesMessage(msg, "I", false, "F");
        }

    }

    private void showPopup(RichPopup popup, boolean visible)
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null)
            {
                String popupId = popup.getClientId(context);
                if (popupId != null)
                {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
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

    public void setImgPopupBinding(RichPopup imgPopupBinding)
    {
        this.imgPopupBinding = imgPopupBinding;
    }

    public RichPopup getImgPopupBinding()
    {
        return imgPopupBinding;
    }

    public void setUpldFileBinding(RichInputFile upldFileBinding)
    {
        this.upldFileBinding = upldFileBinding;
    }

    public RichInputFile getUpldFileBinding()
    {
        return upldFileBinding;
    }

    public void imgUploadVCE(ValueChangeEvent valueChangeEvent)
    {
        System.out.println("Inside vce");
        UploadedFile myfile = (UploadedFile) valueChangeEvent.getNewValue();
        try
        {
            inputStream = myfile.getInputStream();
        }
        catch (IOException e) {
            System.out.println("Error in get input stream");
        }
    }

    public void emailValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        // Add event code here...
        if (object != null && object.toString().trim().length() > 0)
        {
            String name = object.toString();
            String expression =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-_.]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            // String expression = "^([a-zA-Z0-9_-.]+)@([a-zA-Z0-9_-.]+).([a-zA-Z]{2,5})$";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = "Email is not in Proper Format";
            if (matcher.matches())
            {
                System.out.println("Inside duplicate employee MAIL id validator");
                if (object != null && object.toString().length() > 0)
                {
                    OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDupliMailId");
                    opChk.getParamsMap().put("mailId", object);
                    opChk.execute();
                    if (opChk.getErrors().size() > 0)
                        System.out.println("Error on check duplicate=" + opChk.getErrors());
                    else
                    {
                        if (opChk.getResult() == null || opChk.getResult().toString().equals("Y"))
                            showFacesMessage("MSG.1481", "E", true, "V");
                    }

                }

            }
            else
            {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void phoneNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        String msg2 = "";
        if (object != null && object.toString().length() > 0)
        {
            String phnNo = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = phnNo.toCharArray();
            for (char c : xx)
            {
                if (c == '(')
                {
                    openB = openB + 1;
                }
                else if (c == ')')
                {
                    closeB = closeB + 1;
                }
                if (closeB > openB)
                {
                    closeFg = true; //closed brackets will not be more than open brackets at any given time.
                }
            }
            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (phnNo.lastIndexOf("(") > phnNo.lastIndexOf(")")) ||
                (phnNo.indexOf(")") < phnNo.indexOf("(")))
            {
                msg2 = "Brackets not closed properly.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (phnNo.contains("()"))
            {
                msg2 = "Empty Brackets are not allowed.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (phnNo.contains("(.") || phnNo.contains("(-") || phnNo.contains("-)"))
            {
                msg2 = "Invalid Phone Number.Check content inside brackets.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            openB = 0;
            closeB = 0;
            closeFg = false;
            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "([0-9\\-\\+\\(\\)]+)";
            CharSequence inputStr = phnNo;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = "Invalid Phone Number";
            System.out.println("Index of plus is--->" + phnNo.lastIndexOf("+"));
            System.out.println("Bracket index--->" + phnNo.charAt(0));
            if (matcher.matches())
            {
                if (phnNo.contains("++") || phnNo.contains("--"))
                {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  "Can not contain two hyphen(--) or plus(++)"));
                }
                else if (phnNo.lastIndexOf("+") > 1)
                {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  "Plus sign should be in proper place"));
                }
                else if (phnNo.lastIndexOf("+") == 1 && phnNo.charAt(0) != '(')
                {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  "Plus sign should be in proper place"));
                }
                else if (phnNo.startsWith(" ") || phnNo.endsWith(" "))
                {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  "Space Not allowed at start and end"));
                }
            }
            else
            {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              "Only numeric character,+,() and - allowed"));
            }
        }
    }

    public void cancelAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        modePD = "V";
        modeOD = "V";
        modeID = "V";
        modePMD = "V";
        modeSD = "V";
        modeLD = "V";
        modeTC = "V";
        modeSkD = "V";
        setModeDoj("V");
        setModeOthrDed("V");
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_LEAVE", "V");
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_SAL", "V");
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_SKILL", "V");
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_OFF_DTL", "V");

    }

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I-Info,E-Error,W-Warning)
     *      chk:true=if resource bundle is used; false=If Resource Bundle is not used.
     *      typFlg: 'F' for FacesMessage , 'V' for ValidatorException
     * */
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

    public String editSalaryDtlAction()
    {
        Integer status = (Integer) workStatusBinding.getValue();
        if (status.equals(32) || status.equals(33))
        {
            OperationBinding opchkProc = ADFBeanUtils.getOperationBinding("isSalaryProcPending");
            opchkProc.execute();
            if (opchkProc.getErrors() != null && opchkProc.getResult().toString().equals("N"))
            {
                modeSD = "A";
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_SAL", "A");
            }
            else
            {
                showFacesMessage("MSG.1477", "I", true, "F");
            }
        }
        else
        {
            String msg = "Can not edit this employee:Employee working status is On Hold/Resigned.";
            showFacesMessage(msg, "I", false, "F");
        }


        return null;
    }

    public String addNewRefComAction()
    {
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
        return null;
    }

    public String addNewSalCompAction()
    {
        String message = "";
        boolean status = chkReffSalComponent();
        if (status)
        {
            ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
        }
        return null;
    }

    public String editLeaveDtlAction()
    {
        Integer status = (Integer) workStatusBinding.getValue();
        if (status.equals(32) || status.equals(33))
        {
            OperationBinding opchkProc = ADFBeanUtils.getOperationBinding("isSalaryProcPending");
            opchkProc.execute();
            if (opchkProc.getErrors() != null && opchkProc.getResult().toString().equals("N"))
            {
                modeLD = "A";
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_LEAVE", "A");

            }
            else
            {
                showFacesMessage("MSG.1477", "I", true, "F");
            }
        }
        else
        {
            String msg = "Can not edit this employee:Employee working status is On Hold/Resigned.";
            showFacesMessage(msg, "I", false, "F");
        }


        return null;
    }

    public void setModeLD(String modeLD)
    {
        this.modeLD = modeLD;
    }

    public String getModeLD()
    {
        return modeLD;
    }

    public String addNewLeave()
    {
        ADFBeanUtils.getOperationBinding("CreateInsert2").execute();
        return null;
    }

    public String editTimeCalAction()
    {
        Integer status = (Integer) workStatusBinding.getValue();
        if (status.equals(32) || status.equals(33))
        {
            if (modePD.equals("V") && modeOD.equals("V") && modeID.equals("V") && modePMD.equals("V") &&
                modeSD.equals("V") && modeLD.equals("V") && modeSkD.equals("V"))
            {
                modeTC = "A";
                return "goToTimeCal";
            }
            else
            {
                showFacesMessage("MSG.1482", "E", true, "F");
            }
        }
        else
        {
            String msg = "Can not edit this employee:Employee working status is On Hold/Resigned.";
            showFacesMessage(msg, "I", false, "F");
        }
        return null;

    }

    public void salDtlPopUp(DialogEvent dialogEvent)
    {
        if (dialogEvent.getOutcome().name().equals("ok"))
        {
            OperationBinding opUpdt = ADFBeanUtils.getOperationBinding("updtSalAmt");
            opUpdt.execute();
        }
    }


    public void salValVCE(ValueChangeEvent valueChangeEvent)
    {
        oracle.jbo.domain.Number amt = new oracle.jbo.domain.Number(0);
        if (valueChangeEvent.getNewValue() != null)
            amt = (oracle.jbo.domain.Number) valueChangeEvent.getNewValue();
        OperationBinding op = ADFBeanUtils.getOperationBinding("changeSalaryAmt");
        op.getParamsMap().put("newAmt", amt);
        op.execute();
    }

    public String resetSearchAction()
    {
        OperationBinding reset = ADFBeanUtils.getOperationBinding("resetValues");
        reset.execute();
        empNmSearchBinding.resetValue();
        empIdSearchBinding.resetValue();
        empNmSearchBinding.setValue(null);
        empIdSearchBinding.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(empNmSearchBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(empIdSearchBinding);
        OperationBinding opreset = ADFBeanUtils.getOperationBinding("searchEmployee");
        opreset.getParamsMap().put("cldId", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        opreset.getParamsMap().put("slocId", resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        opreset.getParamsMap().put("hoOrgId", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));
        opreset.getParamsMap().put("orgId", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        opreset.getParamsMap().put("empNm", null);
        opreset.getParamsMap().put("empId", '~');
        opreset.execute();
        return null;
    }

    public void setEmpNmSearchBinding(RichInputText empNmSearchBinding)
    {
        this.empNmSearchBinding = empNmSearchBinding;
    }

    public RichInputText getEmpNmSearchBinding()
    {
        return empNmSearchBinding;
    }

    public void setEmpIdSearchBinding(RichInputText empIdSearchBinding)
    {
        this.empIdSearchBinding = empIdSearchBinding;
    }

    public RichInputText getEmpIdSearchBinding()
    {
        return empIdSearchBinding;
    }

    public void searchPopupDL(DialogEvent dialogEvent)
    {
        if (dialogEvent.getOutcome().name().equals("ok"))
        {
            OperationBinding srch = ADFBeanUtils.getOperationBinding("searchFromPopup");
            srch.execute();
        }
    }

    public void salIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("Inside Salary validator");
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDupliSalaryId");
            opChk.getParamsMap().put("salId", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else
            {
                if (opChk.getResult() == null || opChk.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1484", "E", true, "V");
            }

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(salValBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(isDedChkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(salTypeBinding);

    }

    public void leaveIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("Inside Leave validator");
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDupliLeaveId");
            opChk.getParamsMap().put("leaveId", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else
            {
                if (opChk.getResult() == null || opChk.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1485", "E", true, "V");
            }

        }


    }

    public void refSalIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("Inside Salary validator");
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding chkIsOthrDed = ADFBeanUtils.getOperationBinding("chkIsOtherDeduction");
            chkIsOthrDed.getParamsMap().put("refSalId", object);
            chkIsOthrDed.execute();
            if (chkIsOthrDed.getResult() == null || chkIsOthrDed.getResult().toString().equals("Y"))
                showFacesMessage("This salary component is other dedcution type component", "E", false, "V");
            else
            {
                OperationBinding chkSalComp = ADFBeanUtils.getOperationBinding("chkSalIdItSelf");
                chkSalComp.getParamsMap().put("refSalId", object);
                chkSalComp.execute();
                if (chkSalComp.getErrors().size() > 0)
                    showFacesMessage("Something went wrong.", "E", false, "V");
                else if (chkSalComp.getResult() == null || chkSalComp.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1487", "E", true, "V");
                else
                {
                    OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDupliRefSalaryId");
                    opChk.getParamsMap().put("refSalId", object);
                    opChk.execute();
                    if (opChk.getErrors().size() > 0)
                        showFacesMessage("Something went wrong.", "E", false, "V");
                    else if (opChk.getResult() == null || opChk.getResult().toString().equals("Y"))
                    {
                        showFacesMessage("MSG.1484", "E", true, "V");
                    }
                    reffValidStrtDt.setValue(salStDtBinding.getValue());
                    reffValidEndDt.setValue(salEndDt.getValue());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(reffSalTableBinding);
                }
            }

        }
    }

    public void dobValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("Inside validator of date");
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date birthDt = null;
            java.sql.Date sysDt = null;
            //validate age of 18 year
            try
            {
                sysDt = (new Timestamp(System.currentTimeMillis())).dateValue();
                birthDt = ((Timestamp) object).dateValue();
                System.out.println("Time in birthdt1=" + birthDt.getTime());
                long bd = birthDt.getTime();
                System.out.println("Time in birthdt2=" + bd);
                long sd = sysDt.getTime();
                System.out.println("Time in sysdt=" + sd);
                long diff = sd - bd;
                int age = (int) ((diff / (60 * 60 * 24 * 365)) / 1000);
                System.out.println("employee age in year=" + age);
                if (age >= 18)
                {
                }
                else
                {
                    showFacesMessage("MSG.1488", "E", true, "V");
                }

            }
            catch (SQLException e) {
                System.out.println(e.getStackTrace());
            }
        }
    }

    public void dojValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date dobDt = null;
            java.sql.Date dojDt = null;
            if (dobBinding.getValue() != null && dobBinding.getValue().toString().length() > 0)
            {
                try
                {
                    dobDt = ((Timestamp) dobBinding.getValue()).dateValue();
                    dojDt = ((Timestamp) object).dateValue();
                }
                catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (dojDt.compareTo(dobDt) > 0)
                {

                    long bd = dobDt.getTime();
                    long jd = dojDt.getTime();
                    long diff = jd - bd;
                    int age = (int) ((diff / (60 * 60 * 24 * 365)) / 1000);
                    System.out.println("employee age in year=" + age);
                    if (age >= 18)
                    {
                    }
                    else
                    {
                        showFacesMessage("MSG.1488", "E", true, "V");
                    }
                }
                else
                {
                    showFacesMessage("MSG.1489", "E", true, "V");
                }
            }
        }

    }

    public void empIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            if (object.toString().trim().length() > 0)
            {
                OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDupliEmpId");
                opChk.getParamsMap().put("empId", object.toString().trim());
                opChk.execute();
                if (opChk.getErrors().size() > 0)
                    System.out.println("Error on check duplicate=" + opChk.getErrors());
                else
                {
                    if (opChk.getResult() == null || opChk.getResult().toString().equals("Y"))
                        showFacesMessage("MSG.1490", "E", true, "V");
                }
            }
            else
            {
                showFacesMessage("MSG.1396", "E", true, "V");

            }
        }

    }

    public void empCardNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("Inside duplicate emp card no. validator");
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDupliEmpCard");
            opChk.getParamsMap().put("empCardNo", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else
            {
                if (opChk.getResult() == null || opChk.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1491", "E", true, "V");
            }

        }


    }

    public void leaveStrtDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (dojBinding.getValue() != null && dojBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) dojBinding.getValue()).dateValue();
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
                        showFacesMessage("MSG.1492", "E", true, "V");
                    }
                }
                //check for leave start date in leave setup
                OperationBinding chkLevDtValid = ADFBeanUtils.getOperationBinding("chkLevDtValid");
                chkLevDtValid.getParamsMap().put("leaveDt", endDt);
                chkLevDtValid.execute();
                if (chkLevDtValid.getResult() != null && chkLevDtValid.getResult().toString().equals("Y"))
                {
                }
                else
                    showFacesMessage("MSG.1494", "E", true, "V");

            }
            Date calDate = null;
            try
            {
                calDate = ((Timestamp) object).dateValue();
            }
            catch (SQLException e) {
            }
            if (calDate != null)
            {
                Calendar cal = Calendar.getInstance();
                int curYear = cal.get(cal.YEAR);
                cal.setTime(calDate);
                int givenYear = cal.get(cal.YEAR);
                if (givenYear > curYear)
                {
                    //showFacesMessage("Year must be less than or equal to the current year ", "E", true, "V");
                    FacesMessage msg = new FacesMessage("Year must be less than or equal to the current year.", "");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }
            }
        }
    }


    public void leaveEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (leaveStDtBinding.getValue() != null && leaveStDtBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) leaveStDtBinding.getValue()).dateValue();
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
                        showFacesMessage("MSG.1495", "E", true, "V");
                    }
                }
                //check for leave start date in leave setup
                OperationBinding chkLevDtValid = ADFBeanUtils.getOperationBinding("chkLevDtValid");
                chkLevDtValid.getParamsMap().put("leaveDt", endDt);
                chkLevDtValid.execute();
                if (chkLevDtValid.getResult() != null && chkLevDtValid.getResult().toString().equals("Y"))
                {
                }
                else
                    showFacesMessage("MSG.1497", "E", true, "V");

            }
        }

    }

    public void salStrtDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (dojBinding.getValue() != null && dojBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) dojBinding.getValue()).dateValue();
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
                        showFacesMessage("MSG.1498", "E", true, "V");
                    }
                }
            }

            //check for leave start date in leave setup
            OperationBinding chkLevDtValid = ADFBeanUtils.getOperationBinding("chkSalDtValid");
            chkLevDtValid.getParamsMap().put("salDt", endDt);
            chkLevDtValid.execute();
            if (chkLevDtValid.getResult() != null && chkLevDtValid.getResult().toString().equals("Y"))
            {
            }
            else
                showFacesMessage("MSG.1499", "E", true, "V");

        }

    }

    public void salEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {

        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (salStDtBinding.getValue() != null && salStDtBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) salStDtBinding.getValue()).dateValue();
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
                        showFacesMessage("MSG.1500", "E", true, "V");
                    }
                }
            }

            //check for leave start date in leave setup
            OperationBinding chkLevDtValid = ADFBeanUtils.getOperationBinding("chkSalDtValid");
            chkLevDtValid.getParamsMap().put("salDt", endDt);
            chkLevDtValid.execute();
            if (chkLevDtValid.getResult() != null && chkLevDtValid.getResult().toString().equals("Y"))
            {
            }
            else
                showFacesMessage("MSG.1502", "E", true, "V");

        }
    }

    public void setDojBinding(RichInputDate dojBinding)
    {
        this.dojBinding = dojBinding;
    }

    public RichInputDate getDojBinding()
    {
        return dojBinding;
    }

    public void setDobBinding(RichInputDate dobBinding)
    {
        this.dobBinding = dobBinding;
    }

    public RichInputDate getDobBinding()
    {
        return dobBinding;
    }

    public void workingStDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (dojBinding.getValue() != null && dojBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) dojBinding.getValue()).dateValue();
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
                        showFacesMessage("MSG.1503", "E", true, "V");
                    }
                }
                if (workStatusBinding.getValue() != null && workStatusBinding.getValue().equals(31))
                {
                    OperationBinding binding = ADFBeanUtils.getOperationBinding("chkResignitionDate");
                    binding.getParamsMap().put("resgDt", object);
                    binding.execute();
                    if (binding.getErrors().isEmpty() && binding.getResult() != null)
                    {
                        String rslt = binding.getResult().toString();
                        if (rslt.equalsIgnoreCase("Y"))
                        {
                            String msg = "Resignation date will be only after last salary processing date.";
                            showFacesMessage(msg, "E", false, "V");
                        }
                    }

                }
            }
        }

    }

    public void setLeaveStDtBinding(RichInputDate leaveStDtBinding)
    {
        this.leaveStDtBinding = leaveStDtBinding;
    }

    public RichInputDate getLeaveStDtBinding()
    {
        return leaveStDtBinding;
    }

    public void setSalStDtBinding(RichInputDate salStDtBinding)
    {
        this.salStDtBinding = salStDtBinding;
    }

    public RichInputDate getSalStDtBinding()
    {
        return salStDtBinding;
    }


    public void setActivityStyles(HashMap activityStyles)
    {
        this.activityStyles = activityStyles;
    }

    //code to change color of activity in calendar
    public HashMap getActivityStyles()
    {
        activityStyles.clear();
        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding shiftIterator = (DCIteratorBinding) dcBindings.get("HcmEmpShiftIterator");
        Row[] rsi = shiftIterator.getAllRowsInRange();
        ArrayList<HashSet<String>> arrList = new ArrayList<HashSet<String>>();
        int i = 0;
        for (Row r : rsi)
        {
            HashSet hset = new HashSet<String>();
            hset.add(r.getAttribute("ShiftId"));
            arrList.add(i, hset);
            String[] str = null;
            if (r.getAttribute("ShiftClrCd") != null)
                str = r.getAttribute("ShiftClrCd").toString().split(",");
            activityStyles.put(arrList.get(i),
                               CalendarActivityRamp.getActivityRamp(new Color(Integer.parseInt(str[0]),
                                                                              Integer.parseInt(str[1]),
                                                                              Integer.parseInt(str[2]))));
            i++;
        }
        return activityStyles;
    }

    public void timCalTabDiscListener(DisclosureEvent disclosureEvent)
    {
    }

    public String refreshCalendarAction()
    {
        DCBindingContainer dcbind = ADFBeanUtils.getDCBindingContainer();
        boolean isDirty = dcbind.getDataControl().isTransactionModified();
        System.out.println("Is dirty in profile: " + isDirty);
        ADFBeanUtils.getOperationBinding("deleteView").execute();
        OperationBinding op = ADFBeanUtils.getOperationBinding("genCalendarForCurrMonth");
        op.getParamsMap().put("flg", isDirty);
        op.execute();

        return null;
    }

    public void salAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (isDedChkBinding.getValue() != null && isDedChkBinding.getValue().toString().equals("N"))
            if (salTypeBinding.getValue() != null && salTypeBinding.getValue().toString().equals("A"))
            {
                if (object != null)
                {
                    if (isPrecisionValid(26, 6, (Number) object))
                    {
                        if (((Number) object).compareTo(new Number(0)) <= 0 && salIdBinding.getValue() != null)
                        {
                            showFacesMessage("MSG.265", "E", true, "V");
                        }
                    }
                    else
                        showFacesMessage("MSG.57", "E", true, "V");
                }
                else
                {
                    showFacesMessage("MSG.977", "E", true, "V");

                }

            }
    }

    public void subSalAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            if (isPrecisionValid(26, 6, (Number) object))
            {
                if (((Number) object).compareTo(new Number(0)) <= 0 && subSalIdBinding.getValue() != null)
                {
                    showFacesMessage("MSG.265", "E", true, "V");
                }
            }
            else
            {
                showFacesMessage("MSG.57", "E", true, "V");
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(subSalCompTbleBinding);


        }
    }


    /**
     *      Method to Apply Precision Validation(P,S,V)
     *      P- Precision
     *      S- Scale
     *      V- Value to validate
     * */
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total)
    {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void setSalTypeBinding(RichSelectOneRadio salTypeBinding)
    {
        this.salTypeBinding = salTypeBinding;
    }

    public RichSelectOneRadio getSalTypeBinding()
    {
        return salTypeBinding;
    }

    public void panNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("Inside duplicate Pan card no. validator");
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDupliPanNo");
            opChk.getParamsMap().put("empPanNo", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else
            {
                if (opChk.getResult() == null || opChk.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1504", "E", true, "V");
            }

        }

    }

    public void accNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("Inside duplicate Account No. validator");

        if (object != null && object.toString().length() > 0)
        {
            if (object.toString().trim().length() > 0)
            {
                String name = object.toString();
                String expression = "\\d+";
                CharSequence inputStr = name;
                Pattern pattern = Pattern.compile(expression);
                Matcher matcher = pattern.matcher(inputStr);
                String msg = "Account Number is not in Proper Format";
                if (matcher.matches())
                {
                    OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDupliAccNo");
                    opChk.getParamsMap().put("accNo", object.toString().trim());
                    opChk.execute();
                    if (opChk.getErrors().size() > 0)
                        System.out.println("Error on check duplicate=" + opChk.getErrors());
                    else
                    {
                        if (opChk.getResult() == null || opChk.getResult().toString().equals("Y"))
                            showFacesMessage("MSG.1505", "E", true, "V");
                    }
                }
                else
                {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
            else
            {
                showFacesMessage("MSG.1396", "E", true, "V");
            }
        }

    }

    public void deleteSalaryAction(ActionEvent actionEvent)
    {
        OperationBinding opDel = ADFBeanUtils.getOperationBinding("deleteSalComp");
        opDel.execute();
        if (opDel.getResult() != null && opDel.getResult().toString().equals("Y"))
        {
            OperationBinding opDelRw = ADFBeanUtils.getOperationBinding("Delete3");
            opDelRw.execute();
        }
        else
        {
            showFacesMessage("MSG.1507", "E", true, "F");

        }
    }

    public void yearValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0 && leaveStDtBinding.getValue() != null)
        {
            Date currDt = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currDt);
            int curryear = calendar.get(Calendar.YEAR);

            Date strtDt = new Date();
            try
            {
                strtDt = ((Timestamp) leaveStDtBinding.getValue()).dateValue();
            }
            catch (SQLException e) {
                System.out.println("error on cast date");
            }
            calendar.setTime(strtDt);
            int strtyear = calendar.get(Calendar.YEAR);

            Integer chkYear = (Integer) object;
            if ((strtyear >= chkYear && chkYear >= curryear) || (curryear >= chkYear && chkYear >= strtyear))
            {
            }
            else
            {
                showFacesMessage("MSG.1508", "E", true, "V");

            }
        }

    }

    public void desgIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding opChkLimit = ADFBeanUtils.getOperationBinding("chkNoOfPositions");
            opChkLimit.getParamsMap().put("desgId", object);
            opChkLimit.execute();
            if (opChkLimit.getErrors().size() == 0 && opChkLimit.getResult() != null &&
                opChkLimit.getResult().toString().equals("Y"))
            {
                if (gradePayChkBiinding.getValue() != null && gradePayChkBiinding.getValue() != "")
                {
                    if (gradePayChkBiinding.getValue().equals("Y"))
                    {
                        OperationBinding opChkGradeLinking =
                            ADFBeanUtils.getOperationBinding("chkIsDesigLinkWithGradeInOrg");
                        opChkGradeLinking.getParamsMap().put("desgId", object);
                        opChkGradeLinking.execute();
                        if (opChkGradeLinking.getErrors().size() == 0 && opChkGradeLinking.getResult() != null &&
                            opChkGradeLinking.getResult().toString().equals("Y"))
                        {

                        }
                        else
                        {
                            salGradeBinding.setValue("");
                            showFacesMessage("Designation not linked with grade.Please refer grade profile setup", "E",
                                             false, "V");
                        }
                    }
                }

            }
            else
            {
                showFacesMessage("MSG.1509", "E", true, "V");
            }

        }

    }

    public String addSkilAction()
    {
        OperationBinding opAdd = ADFBeanUtils.getOperationBinding("CreateInsert3");
        opAdd.execute();

        return null;
    }

    public String editSkillAction()
    {
        Integer status = (Integer) workStatusBinding.getValue();
        if (status.equals(32) || status.equals(33))
        {
            modeSkD = "A";
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_SKILL", "A");
        }
        else
        {
            String msg = "Can not edit this employee:Employee working status is On Hold/Resigned.";
            showFacesMessage(msg, "I", false, "F");
        }
        return null;

    }

    public void setModeSkD(String modeSkD)
    {
        this.modeSkD = modeSkD;
    }

    public String getModeSkD()
    {
        return modeSkD;
    }

    public void skillIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        //check for duplicate skill
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding opDupli = ADFBeanUtils.getOperationBinding("chkDupliSkilId");
            opDupli.getParamsMap().put("skilId", object);
            opDupli.execute();
            if (opDupli.getErrors().size() == 0 && opDupli.getResult() != null &&
                opDupli.getResult().toString().equals("N"))
            {
            }
            else
            {
                showFacesMessage("MSG.1510", "E", true, "V");
            }
        }

    }

    public void skilApliDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (dojBinding.getValue() != null && dojBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) dojBinding.getValue()).dateValue();
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
                        showFacesMessage("MSG.1511", "E", true, "V");
                    }
                }
            }

            //check for leave start date in leave setup
            OperationBinding chkLevDtValid = ADFBeanUtils.getOperationBinding("chkSkilDtValid");
            chkLevDtValid.getParamsMap().put("skilDt", endDt);
            chkLevDtValid.execute();
            if (chkLevDtValid.getResult() != null && chkLevDtValid.getResult().toString().equals("Y"))
            {
            }
            else
                showFacesMessage("MSG.1512", "E", true, "V");

        }
    }

    public String addEmployeeAction()
    {
        OperationBinding op = ADFBeanUtils.getOperationBinding("addEditOfficialDetail");
        op.execute();
        if (op.getErrors().size() == 0)
        {
            modeOD = "A";
            setModeDoj("A");
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_OFF_DTL", "A");
            OperationBinding opexec = ADFBeanUtils.getOperationBinding("executeLovVoInOffcialDtl");
            opexec.execute();
        }
        else
            System.out.println("Error on add/edit->" + op.getErrors());
        return null;
    }

    public void refSalPercValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            if (isPrecisionValid(3, 0, (Number) object))
            {
                if (((Number) object).compareTo(new Number(0)) <= 0)
                {
                    showFacesMessage("MSG.1513", "E", true, "V");
                }
            }
            else
                showFacesMessage("MSG.1514", "E", true, "V");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(reffSalTableBinding);
    }

    public void refSalStrtDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (salStDtBinding.getValue() != null && salStDtBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) salStDtBinding.getValue()).dateValue();
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
                        showFacesMessage("MSG.1515", "E", true, "V");
                    }
                }
            }
        }
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (salEndDt.getValue() != null && salEndDt.getValue().toString().length() > 0)
            {
                try
                {
                    endDt = ((Timestamp) salEndDt.getValue()).dateValue();
                    strtDt = ((Timestamp) object).dateValue();
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
                        showFacesMessage("Reference salary start date should be before main salary component end date",
                                         "E", false, "V");
                    }
                }
            }
        }

    }

    public void setSalIdBinding(RichSelectOneChoice salIdBinding)
    {
        this.salIdBinding = salIdBinding;
    }

    public RichSelectOneChoice getSalIdBinding()
    {
        return salIdBinding;
    }

    public void refSalPopupCancelListener(PopupCanceledEvent popupCanceledEvent)
    {

        //check if any row has blank value then remove row and then update sal amount
        OperationBinding chkBlnkRow = ADFBeanUtils.getOperationBinding("chkBlankRowAndRemove");
        chkBlnkRow.execute();

    }

    public void bnkBrnchVCE(ValueChangeEvent valueChangeEvent)
    {
        AdfFacesContext.getCurrentInstance().addPartialTarget(ifscCodeBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(swiftCodeBinding);

    }

    public void setIfscCodeBinding(RichInputText ifscCodeBinding)
    {
        this.ifscCodeBinding = ifscCodeBinding;
    }

    public RichInputText getIfscCodeBinding()
    {
        return ifscCodeBinding;
    }

    public void setSwiftCodeBinding(RichInputText swiftCodeBinding)
    {
        this.swiftCodeBinding = swiftCodeBinding;
    }

    public RichInputText getSwiftCodeBinding()
    {
        return swiftCodeBinding;
    }

    public void empNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            if (object.toString().trim().length() > 0)
            {
                String pt = "^[a-zA-Z0-9\\. ]*$";
                Pattern ptt = null;
                ptt = ptt.compile(pt);
                object = object.toString().trim();
                Matcher matcher = ptt.matcher(object.toString());
                if (!matcher.matches())
                {
                    String msg = "Please Enter Valid Employee Name.";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
            else
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Only Spaces not allowed.",
                                                              null));

        }


    }

    public void salTypeVCE(ValueChangeEvent valueChangeEvent)
    {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void employeeNmVCL(ValueChangeEvent valueChangeEvent)
    {
        /* if(valueChangeEvent.getNewValue()!= null)
        {
            String nm = valueChangeEvent.getNewValue().toString();
            String eoName = null;
            OperationBinding updtEoNm = bindings.getOperationBinding("updateEoNmFrmEmpNm");
                updtEoNm.getParamsMap().put("empNm", nm);
                updtEoNm.execute();
                if(updtEoNm.getErrors().size()==0 && updtEoNm.getResult()!=null)
                eoName = (String) updtEoNm.getResult();
                else
                eoName = nm;
                eoNmBinding.setValue(eoName);
            } */
    }

    public void setEoNmBinding(RichInputText eoNmBinding)
    {
        this.eoNmBinding = eoNmBinding;
    }

    public RichInputText getEoNmBinding()
    {
        return eoNmBinding;
    }

    public void guardNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            if (object.toString().trim().length() > 0)
            {
                String pt = "^[a-zA-Z0-9 ]*$";
                Pattern ptt = null;
                ptt = ptt.compile(pt);
                object = object.toString().trim();
                Matcher matcher = ptt.matcher(object.toString());
                if (!matcher.matches())
                {
                    String msg = "Please Enter Valid Father/Husband Name.";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
            else
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Only Spaces not allowed.",
                                                              null));

        }


    }

    public void deptIdVCE(ValueChangeEvent valueChangeEvent)
    {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        empDesigBinding.setValue("");
    }

    public void dojVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0)
        {
            wrkStatDtBinding.setValue(valueChangeEvent.getNewValue());
        }
        else
        {
            wrkStatDtBinding.setValue(null);
            wrkStatDtBinding.resetValue();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(wrkStatDtBinding);

    }

    public void setWrkStatDtBinding(RichInputDate wrkStatDtBinding)
    {
        this.wrkStatDtBinding = wrkStatDtBinding;
    }

    public RichInputDate getWrkStatDtBinding()
    {
        return wrkStatDtBinding;
    }

    public List onSuggest(String string)
    {
        //get access to the binding context and binding container at runtime
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        //set the bind variable value that is used to filter the View Object
        //query of the suggest list. The View Object instance has a View Criteria assigned
        System.out.println("String entered is =" + string);
        String OrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String HoOrgId = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String CldId = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        OperationBinding setVariable = (OperationBinding) bindings.get("setslocIdBind");
        setVariable.getParamsMap().put("value", SlocId);
        setVariable.execute();
        setVariable = (OperationBinding) bindings.get("setcldIdBind");
        setVariable.getParamsMap().put("value", CldId);
        setVariable.execute();
        setVariable = (OperationBinding) bindings.get("sethoOrgIdBind");
        setVariable.getParamsMap().put("value", HoOrgId);
        setVariable.execute();
        setVariable = (OperationBinding) bindings.get("setorgIdBind");
        setVariable.getParamsMap().put("value", OrgId);
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setempNmBindVar");
        setVariable.getParamsMap().put("value", string);
        setVariable.execute();
        //the data in the suggest list is queried by a tree binding.
        JUCtrlHierBinding hierBinding = (JUCtrlHierBinding) bindings.get("LovEmpNmAutoSuggst");
        //re- query the list based on the new bind variable values
        hierBinding.executeQuery();
        //The rangeSet,the list of queries entries, is of type JUCtrlValueBndingRef.
        List<JUCtrlValueBindingRef> displayDataList = hierBinding.getRangeSet();
        System.out.println("No .of values in displayDataList=" + displayDataList.size());
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (JUCtrlValueBindingRef displayData : displayDataList)
        {
            Row rw = displayData.getRow();
            //populate the SelectItem list
            selectItems.add(new SelectItem((String) rw.getAttribute("EmpNm"), (String) rw.getAttribute("EmpNm")));
        }
        return selectItems;
    }

    public String exportToExcelAction()
    {
        return null;
    }

    public void chkDuplicateLanguageValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        String chkRsult = "";
        if (object != null)
        {
            OperationBinding opChk = null;
            String empLngId = object.toString();
            opChk = ADFBeanUtils.getOperationBinding("chkDupliaceLanguage");
            opChk.getParamsMap().put("empLngId", empLngId);
            opChk.execute();

            if (opChk.getErrors().size() == 0 && opChk.getResult() != null)
            {
                chkRsult = (String) opChk.getResult();
                if (chkRsult.equals("Y"))
                {
                    showFacesMessage("Duplicate entry!!", "E", false, "V");
                }
            }
            else
            {
                System.out.println("Error during duplicate check =" + opChk.getErrors());
            }
        }
    }


    public void chkDuplicateCompanyNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        String chkRsult = "";
        if (object != null)
        {
            OperationBinding opChk = null;
            String empCompName = object.toString();
            opChk = ADFBeanUtils.getOperationBinding("chkDupliaceCompanyName");
            opChk.getParamsMap().put("empCompName", empCompName);
            opChk.execute();

            if (opChk.getErrors().size() == 0 && opChk.getResult() != null)
            {
                chkRsult = (String) opChk.getResult();
                if (chkRsult.equals("Y"))
                {
                    showFacesMessage("Duplicate entry!!", "E", false, "V");
                }
            }
            else
            {
                System.out.println("Error during duplicate check =" + opChk.getErrors());
            }
        }
    }

    public void chkDuplicateCourseValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        String chkRsult = "";
        System.out.println("object" + object);
        if (object != null)
        {
            OperationBinding opChk = null;
            String empCrseId = object.toString();
            opChk = ADFBeanUtils.getOperationBinding("chkDupliaceCource");
            opChk.getParamsMap().put("empCrseId", empCrseId);
            opChk.execute();

            if (opChk.getErrors().size() == 0 && opChk.getResult() != null)
            {
                chkRsult = (String) opChk.getResult();
                if (chkRsult.equals("Y"))
                {
                    showFacesMessage("Duplicate entry!!", "E", false, "V");
                }
            }
            else
            {
                System.out.println("Error during duplicate check =" + opChk.getErrors());
            }
        }
    }

    public void exportExcelActionListener(FacesContext facesContext, OutputStream outputStream)
    {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);


        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i <= 27; i++)
        {
            Cell cell = createRow.createCell(i);
            switch (i)
            {
            case 0:
                cell.setCellValue("EMP_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("EMP_CODE");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("EMP_NM");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("EMP_CARD_NO");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("EMP_DOB");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("EMP_DOJ");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("EMP_DEPT_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("EMP_DESG_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 8:
                cell.setCellValue("EMP_LOC_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 9:
                cell.setCellValue("EMP_GRP_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 10:
                cell.setCellValue("WRK_STAT");
                cell.setCellStyle(cellStyle);
                break;
            case 11:
                cell.setCellValue("WRK_STAT_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 12:
                cell.setCellValue("EMP_GEN");
                cell.setCellStyle(cellStyle);
                break;
            case 13:
                cell.setCellValue("EMP_DED_CH");
                cell.setCellStyle(cellStyle);
                break;
            case 14:
                cell.setCellValue("EMP_PERM_ADD");
                cell.setCellStyle(cellStyle);
                break;
            case 15:
                cell.setCellValue("EMP_CURR_ADD");
                cell.setCellStyle(cellStyle);
                break;
            case 16:
                cell.setCellValue("EMP_EMAIL");
                cell.setCellStyle(cellStyle);
                break;
            case 17:
                cell.setCellValue("EMP_PHONE1");
                cell.setCellStyle(cellStyle);
                break;
            case 18:
                cell.setCellValue("EMP_PHONE2");
                cell.setCellStyle(cellStyle);
                break;
            case 19:
                cell.setCellValue("EMP_PAY_MODE");
                cell.setCellStyle(cellStyle);
                break;
            case 20:
                cell.setCellValue("EMP_BNK_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 21:
                cell.setCellValue("BNK_BRNCH_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 22:
                cell.setCellValue("ACC_NO");
                cell.setCellStyle(cellStyle);
                break;
            case 23:
                cell.setCellValue("ACC_TYPE");
                cell.setCellStyle(cellStyle);
                break;
            case 24:
                cell.setCellValue("IFSC_CODE");
                cell.setCellStyle(cellStyle);
                break;
            case 25:
                cell.setCellValue("SWIFT_CODE");
                cell.setCellStyle(cellStyle);
                break;
            case 26:
                cell.setCellValue("EMP_PAN_NO");
                cell.setCellStyle(cellStyle);
                break;
            case 27:
                cell.setCellValue("ACTV_FLG");
                cell.setCellStyle(cellStyle);
                break;

            }
        }
        DCBindingContainer container = ADFBeanUtils.getDCBindingContainer();
        /* JUCtrlHierBinding obj = (JUCtrlHierBinding)container.findCtrlBinding("OrgHcmEmpPrf1Iterator");
               ViewObject object = obj.getViewObject();
               DistinctPriceMasterVoImpl view = (DistinctPriceMasterVoImpl)object; */
        String OrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String HoOrgId = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String CldId = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItr = (DCIteratorBinding) dcBindings.get("OrgHcmEmpPrfIterator");
        ViewObjectImpl object = (ViewObjectImpl) dcItr.getViewObject();
        RowQualifier rq = new RowQualifier(object);
        rq.setWhereClause("CldId='" + CldId + "' and SlocId=" + SlocId + " and HoOrgId='" + HoOrgId + "' and OrgId='" +
                          OrgId + "'");
        Row[] fr = object.getFilteredRows(rq);
        RowSetIterator itr = (RowSetIterator) object.createRowSetIterator(null);
        int rownum = 1;
        for (Row next : fr)
        {
            // Row next = itr.next();
            HSSFRow row = sheet.createRow(rownum++);
            Object empIdO = next.getAttribute("EmpId");
            Object empCodeO = next.getAttribute("EmpCode");
            Object empNmO = next.getAttribute("EmpNm");
            Object empCardNoO = next.getAttribute("EmpCardNo");
            Object empDobO = next.getAttribute("EmpDob");
            if (next.getAttribute("EmpDob") != null && next.getAttribute("EmpDob").toString().length() > 0)
            {
                empDobO = getConvertTimeStampToStr(next.getAttribute("EmpDob").toString());
            }
            Object empDojO = next.getAttribute("EmpDoj");
            if (next.getAttribute("EmpDoj") != null && next.getAttribute("EmpDoj").toString().length() > 0)
            {
                empDojO = getConvertTimeStampToStr(next.getAttribute("EmpDoj").toString());
            }
            Object empDeptIdO = next.getAttribute("EmpDeptId");
            Object empDesgIdO = next.getAttribute("EmpDesgId");
            Object empLocIdO = next.getAttribute("EmpLocId");
            Object empGrpIdO = next.getAttribute("EmpGrpId");
            Object wrkStatO = next.getAttribute("WrkStat");
            Object wrkStatDtO = next.getAttribute("WrkStatDt");
            if (next.getAttribute("WrkStatDt") != null && next.getAttribute("WrkStatDt").toString().length() > 0)
            {
                wrkStatDtO = getConvertTimeStampToStr(next.getAttribute("WrkStatDt").toString());
            }
            Object empGenO = next.getAttribute("EmpGen");
            Object empDedChO = next.getAttribute("EmpDedCh");
            Object empPermAddO = next.getAttribute("EmpPermAdd");
            Object empCurrAddO = next.getAttribute("EmpCurrAdd");
            Object empEmailO = next.getAttribute("EmpEmail");
            Object empPhone1O = next.getAttribute("EmpPhone1");
            Object empPhone2O = next.getAttribute("EmpPhone2");
            Object empPayModeO = next.getAttribute("EmpPayMode");
            Object empBnkIdO = next.getAttribute("EmpBnkId");
            Object bnkBrnchIdO = next.getAttribute("BnkBrnchId");
            Object accNoO = next.getAttribute("AccNo");
            Object accTypeO = next.getAttribute("AccType");
            Object ifscCodeO = next.getAttribute("IfscCode");
            Object swiftCodeO = next.getAttribute("SwiftCode");
            Object empPanNoO = next.getAttribute("EmpPanNo");
            Object actvFlgO = next.getAttribute("ActvFlg");

            StringBuilder empId = (empIdO == null ? new StringBuilder("") : new StringBuilder(empIdO.toString()));
            StringBuilder empCode = (empCodeO == null ? new StringBuilder("") : new StringBuilder(empCodeO.toString()));
            StringBuilder empNm = (empNmO == null ? new StringBuilder("") : new StringBuilder(empNmO.toString()));
            StringBuilder empCardNo =
                (empCardNoO == null ? new StringBuilder("") : new StringBuilder(empCardNoO.toString()));
            StringBuilder empDob = (empDobO == null ? new StringBuilder("") : new StringBuilder(empDobO.toString()));
            StringBuilder empDoj = (empDojO == null ? new StringBuilder("") : new StringBuilder(empDojO.toString()));
            StringBuilder empDeptId =
                (empDeptIdO == null ? new StringBuilder("") : new StringBuilder(empDeptIdO.toString()));
            StringBuilder empDesgId =
                (empDesgIdO == null ? new StringBuilder("") : new StringBuilder(empDesgIdO.toString()));
            StringBuilder empLocId =
                (empLocIdO == null ? new StringBuilder("") : new StringBuilder(empLocIdO.toString()));
            StringBuilder empGrpId =
                (empGrpIdO == null ? new StringBuilder("") : new StringBuilder(empGrpIdO.toString()));
            StringBuilder wrkStat = (wrkStatO == null ? new StringBuilder("") : new StringBuilder(wrkStatO.toString()));
            StringBuilder wrkStatDt =
                (wrkStatDtO == null ? new StringBuilder("") : new StringBuilder(wrkStatDtO.toString()));
            StringBuilder empGen = (empGenO == null ? new StringBuilder("") : new StringBuilder(empGenO.toString()));
            StringBuilder empDedCh =
                (empDedChO == null ? new StringBuilder("Y") : new StringBuilder(empDedChO.toString()));
            StringBuilder empPermAdd =
                (empPermAddO == null ? new StringBuilder("") : new StringBuilder(empPermAddO.toString()));
            StringBuilder empCurrAdd =
                (empCurrAddO == null ? new StringBuilder("") : new StringBuilder(empCurrAddO.toString()));
            StringBuilder empEmail =
                (empEmailO == null ? new StringBuilder("") : new StringBuilder(empEmailO.toString()));
            StringBuilder empPhone1 =
                (empPhone1O == null ? new StringBuilder("") : new StringBuilder(empPhone1O.toString()));
            StringBuilder empPhone2 =
                (empPhone2O == null ? new StringBuilder("") : new StringBuilder(empPhone2O.toString()));
            StringBuilder empPayMode =
                (empPayModeO == null ? new StringBuilder("") : new StringBuilder(empPayModeO.toString()));
            StringBuilder empBnkId =
                (empBnkIdO == null ? new StringBuilder("") : new StringBuilder(empBnkIdO.toString()));
            StringBuilder bnkBrnchId =
                (bnkBrnchIdO == null ? new StringBuilder("") : new StringBuilder(bnkBrnchIdO.toString()));
            StringBuilder accNo = (accNoO == null ? new StringBuilder("") : new StringBuilder(accNoO.toString()));
            StringBuilder accType = (accTypeO == null ? new StringBuilder("") : new StringBuilder(accTypeO.toString()));
            StringBuilder ifscCode =
                (ifscCodeO == null ? new StringBuilder("") : new StringBuilder(ifscCodeO.toString()));
            StringBuilder swiftCode =
                (swiftCodeO == null ? new StringBuilder("") : new StringBuilder(swiftCodeO.toString()));
            StringBuilder empPanNo =
                (empPanNoO == null ? new StringBuilder("") : new StringBuilder(empPanNoO.toString()));
            StringBuilder actvFlg =
                (actvFlgO == null ? new StringBuilder("Y") : new StringBuilder(actvFlgO.toString()));
            StringBuilder blank = new StringBuilder("");

            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            Cell cell4 = row.createCell(4);
            Cell cell5 = row.createCell(5);
            Cell cell6 = row.createCell(6);
            Cell cell7 = row.createCell(7);
            Cell cell8 = row.createCell(8);
            Cell cell9 = row.createCell(9);
            Cell cell10 = row.createCell(10);
            Cell cell11 = row.createCell(11);
            Cell cell12 = row.createCell(12);
            Cell cell13 = row.createCell(13);
            Cell cell14 = row.createCell(14);
            Cell cell15 = row.createCell(15);
            Cell cell16 = row.createCell(16);
            Cell cell17 = row.createCell(17);
            Cell cell18 = row.createCell(18);
            Cell cell19 = row.createCell(19);
            Cell cell20 = row.createCell(20);
            Cell cell21 = row.createCell(21);
            Cell cell22 = row.createCell(22);
            Cell cell23 = row.createCell(23);
            Cell cell24 = row.createCell(24);
            Cell cell25 = row.createCell(25);
            Cell cell26 = row.createCell(26);
            Cell cell27 = row.createCell(27);
            cell0.setCellValue(empId.toString());
            if (empCodeO != null && empCodeO.toString().length() > 0)
                cell1.setCellValue(new Double(empCodeO.toString()));
            else
                cell1.setCellValue(blank.toString());
            cell2.setCellValue(empNm.toString());
            cell3.setCellValue(empCardNo.toString());
            cell4.setCellValue(empDob.toString());
            cell5.setCellValue(empDoj.toString());
            cell6.setCellValue(empDeptId.toString());
            cell7.setCellValue(empDesgId.toString());
            cell8.setCellValue(empLocId.toString());
            cell9.setCellValue(empGrpId.toString());
            if (wrkStatO != null && wrkStatO.toString().length() > 0)
                cell10.setCellValue(new Double(wrkStatO.toString()));
            else
                cell10.setCellValue(blank.toString());
            cell11.setCellValue(wrkStatDt.toString());
            cell12.setCellValue(empGen.toString());
            cell13.setCellValue(empDedCh.toString());
            cell14.setCellValue(empPermAdd.toString());
            cell15.setCellValue(empCurrAdd.toString());
            cell16.setCellValue(empEmail.toString());
            cell17.setCellValue(empPhone1.toString());
            cell18.setCellValue(empPhone2.toString());
            if (empPayModeO != null && empPayModeO.toString().length() > 0)
                cell19.setCellValue(new Double(empPayModeO.toString()));
            else
                cell19.setCellValue(blank.toString());
            if (empBnkIdO != null && empBnkIdO.toString().length() > 0)
                cell20.setCellValue(new Double(empBnkIdO.toString()));
            else
                cell20.setCellValue(blank.toString());
            if (bnkBrnchIdO != null && bnkBrnchIdO.toString().length() > 0)
                cell21.setCellValue(new Double(bnkBrnchIdO.toString()));
            else
                cell21.setCellValue(blank.toString());
            cell22.setCellValue(accNo.toString());
            cell23.setCellValue(accType.toString());
            if (ifscCodeO != null && ifscCodeO.toString().length() > 0)
                //           cell24.setCellValue(new Double(ifscCodeO.toString()));
                cell24.setCellValue(ifscCodeO.toString());
            else
                cell24.setCellValue(blank.toString());
            if (swiftCodeO != null && swiftCodeO.toString().length() > 0)
                // cell25.setCellValue(new Double(swiftCodeO.toString()));
                cell25.setCellValue(swiftCodeO.toString());
            else
                cell25.setCellValue(blank.toString());
            cell26.setCellValue(empPanNo.toString());
            cell27.setCellValue(actvFlg.toString());
            System.out.println("Row added ___________________");
            for (int i = 0; i <= 27; i++)
            {
                sheet.autoSizeColumn(i);
            }
        }

        try
        {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private java.util.Date getConvertDate(String str)
    {
        java.util.Date d = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        try
        {
            d = format.parse(str);
            System.out.println("Date parsed=" + d);
        }
        catch (Exception e) {
            System.out.println("Error during parse");
            e.printStackTrace();
        }
        return d;
    }

    private String getConvertTimeStampToStr(String str)
    {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //Format to match actual String to parse
        Date dt = null;
        try
        {
            dt = format.parse(str);
        }
        catch (ParseException e) {
            System.out.println("Exception Caught=" + e.getStackTrace());
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-YYY");
        // System.out.println(newFormat.format(dt));
        return newFormat.format(dt);
    }

    public boolean chkReffSalComponent()
    {
        boolean status = true;
        String message = "";
        DCIteratorBinding difrSumm = ADFBeanUtils.findIterator("HcmEmpSalRefIterator");
        DCIteratorBinding remtDci = ADFBeanUtils.findIterator("RqmtAreaIterator");
        DCIteratorBinding subcom = ADFBeanUtils.findIterator("HcmEmpSubSalIterator");
        if (salTypeBinding.getValue() != null && salTypeBinding.getValue().toString().equals("P"))
        {
            if (difrSumm.getEstimatedRowCount() > 0)
            {

            }
            else
            {
                message = "In Salary Detail Please either add reference salary component or change type as amount.";
                showFacesMessage(message, "E", false, "F");
                status = false;
            }
        }
        System.out.println(reqmntAreaFlagBinding.getValue());
        if (reqmntAreaFlagBinding.getValue() != null && reqmntAreaFlagBinding.getValue().equals(true))
        {
            if (remtDci.getEstimatedRowCount() > 0)
            {

            }
            else
            {
                message = "In requirement Area Detail Please add requirement area or uncheck requirement area flag.";
                showFacesMessage(message, "E", false, "F");
                status = false;
            }
        }
        if (isDedChkBinding.getValue() != null && isDedChkBinding.getValue().equals("Y"))
        {
            if (subcom.getEstimatedRowCount() > 0)
            {

            }
            else
            {
                //                message = "Please add sub salary commponent.";
                //                showFacesMessage(message, "E", false, "F");
                //                status = false;
                OperationBinding opchk = ADFBeanUtils.getOperationBinding("calTotalSubDedAmnt");
                opchk.execute();
            }

        }
        return status;
    }

    public void validReffEndDt(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (reffValidStrtDt.getValue() != null && reffValidStrtDt.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) reffValidStrtDt.getValue()).dateValue();

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
                        showFacesMessage("Reference Component end date should be after Reference Component start date",
                                         "E", false, "V");
                    }
                }
            }
        }

        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (salEndDt.getValue() != null && salEndDt.getValue().toString().length() > 0)
            {
                try
                {
                    endDt = ((Timestamp) salEndDt.getValue()).dateValue();
                    strtDt = ((Timestamp) object).dateValue();
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
                        showFacesMessage("Reference Component End date should be before main salary component end date",
                                         "E", false, "V");
                    }
                }
            }
        }
    }

    public void setSalEndDt(RichInputDate salEndDt)
    {
        this.salEndDt = salEndDt;
    }

    public RichInputDate getSalEndDt()
    {
        return salEndDt;
    }

    public void setReffValidStrtDt(RichInputDate reffValidStrtDt)
    {
        this.reffValidStrtDt = reffValidStrtDt;
    }

    public RichInputDate getReffValidStrtDt()
    {
        return reffValidStrtDt;
    }

    public void setReffValidEndDt(RichInputDate reffValidEndDt)
    {
        this.reffValidEndDt = reffValidEndDt;
    }

    public RichInputDate getReffValidEndDt()
    {
        return reffValidEndDt;
    }

    public void setWorkStatusBinding(RichSelectOneChoice workStatusBinding)
    {
        this.workStatusBinding = workStatusBinding;
    }

    public RichSelectOneChoice getWorkStatusBinding()
    {
        return workStatusBinding;
    }

    public String editEmpDed()
    {
        Integer status = (Integer) workStatusBinding.getValue();
        if (status.equals(32) || status.equals(33))
        {
            //            OperationBinding opchkProc = ADFBeanUtils.getOperationBinding("isSalaryProcPending");
            //            opchkProc.execute();
            //            if (opchkProc.getErrors() != null && opchkProc.getResult().toString().equals("N"))
            //            {
            return "goToDed";
            //            }
            //            else
            //            {
            //                showFacesMessage("MSG.1477", "I", true, "F");
            //                return null;
            //            }
            //


        }
        else
        {
            String msg = "Can not edit this employee:Employee working status is On Hold/Resigned.";
            showFacesMessage(msg, "I", false, "F");
            return null;
        }
    }


    public void setRequirementAreaNmBinding(RichInputText requirementAreaNmBinding)
    {
        this.requirementAreaNmBinding = requirementAreaNmBinding;
    }

    public RichInputText getRequirementAreaNmBinding()
    {
        return requirementAreaNmBinding;
    }

    public void setReqAddressBinding(RichInputListOfValues reqAddressBinding)
    {
        this.reqAddressBinding = reqAddressBinding;
    }

    public RichInputListOfValues getReqAddressBinding()
    {
        return reqAddressBinding;
    }

    public void setWareHouseBinding(RichInputListOfValues wareHouseBinding)
    {
        this.wareHouseBinding = wareHouseBinding;
    }

    public RichInputListOfValues getWareHouseBinding()
    {
        return wareHouseBinding;
    }

    public void setIsCostCentrBinding(RichSelectBooleanCheckbox isCostCentrBinding)
    {
        this.isCostCentrBinding = isCostCentrBinding;
    }

    public RichSelectBooleanCheckbox getIsCostCentrBinding()
    {
        return isCostCentrBinding;
    }

    public void setIsTemIssueOnly(RichSelectBooleanCheckbox isTemIssueOnly)
    {
        this.isTemIssueOnly = isTemIssueOnly;
    }

    public RichSelectBooleanCheckbox getIsTemIssueOnly()
    {
        return isTemIssueOnly;
    }

    public void setIsProductionBinding(RichSelectBooleanCheckbox isProductionBinding)
    {
        this.isProductionBinding = isProductionBinding;
    }

    public RichSelectBooleanCheckbox getIsProductionBinding()
    {
        return isProductionBinding;
    }

    public void setIsServceCentrBinding(RichSelectBooleanCheckbox isServceCentrBinding)
    {
        this.isServceCentrBinding = isServceCentrBinding;
    }

    public RichSelectBooleanCheckbox getIsServceCentrBinding()
    {
        return isServceCentrBinding;
    }

    public void setEmployeeNmBinding(RichInputText employeeNmBinding)
    {
        this.employeeNmBinding = employeeNmBinding;
    }

    public RichInputText getEmployeeNmBinding()
    {
        return employeeNmBinding;
    }


    public void insertRequirementAreaDataAction(ActionEvent actionEvent)
    {
        boolean result = true;
        if (result == true)
        {
            OperationBinding opchkProc = ADFBeanUtils.getOperationBinding("insrtReqmntAreaInfo");
            opchkProc.execute();
            if (opchkProc.getErrors() != null && opchkProc.getResult().toString().equals("Y"))
            {
                String message = "Requirement area information inserted successfully.";
                showFacesMessage(message, "I", false, "F");
                //   ADFBeanUtils.getOperationBinding("Commit").execute();
                ADFBeanUtils.getOperationBinding("refreshDummyRqmtVo").execute();
                ADFBeanUtils.getOperationBinding("refreshAftDeletion").execute();
            }
            else
            {
                ADFBeanUtils.getOperationBinding("refreshAftDeletion").execute();
                showFacesMessage("Requirement area information not inserted.:Some error occurs.", "E", false, "F");
            }
        }

    }


    public String showMessage(String message, String cid)
    {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public void delReqmtAreaAL(ActionEvent actionEvent)
    {
        OperationBinding del = ADFBeanUtils.getOperationBinding("chkReqAreaForDeletion");
        del.execute();
        if (del.getErrors().isEmpty() && del.getResult() != null)
        {
            BigDecimal delRslt = (BigDecimal) del.getResult();
            if (delRslt.compareTo(new BigDecimal(1)) == 0)
            {
                //   ADFBeanUtils.getOperationBinding("Commit").execute();
                ADFBeanUtils.getOperationBinding("refreshAftDeletion").execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(reqmntAreaFlagBinding);
                FacesMessage message = new FacesMessage("Requirement area deleted successfully.");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            }
            else if (delRslt.compareTo(new BigDecimal(-1)) == 0)
            {
                FacesMessage message =
                    new FacesMessage("Requirement area can't be deleted due to its further linking.");
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }

            else
            {
                FacesMessage message = new FacesMessage("Somthing went wrong in deleting: " + delRslt.toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
        else
        {
            FacesMessage message =
                new FacesMessage("Somthing went wrong in deleting function returned null:  " + del.getResult());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void setReqAddressNmBinding(RichInputText reqAddressNmBinding)
    {
        this.reqAddressNmBinding = reqAddressNmBinding;
    }

    public RichInputText getReqAddressNmBinding()
    {
        return reqAddressNmBinding;
    }

    public void setReqmntAreaFlagBinding(RichSelectBooleanCheckbox reqmntAreaFlagBinding)
    {
        this.reqmntAreaFlagBinding = reqmntAreaFlagBinding;
    }

    public RichSelectBooleanCheckbox getReqmntAreaFlagBinding()
    {
        return reqmntAreaFlagBinding;
    }


    public void setAddRqmtAreaBttn(RichLink addRqmtAreaBttn)
    {
        this.addRqmtAreaBttn = addRqmtAreaBttn;
    }

    public RichLink getAddRqmtAreaBttn()
    {
        return addRqmtAreaBttn;
    }

    public void refreshRqmtAreaButton(ValueChangeEvent valueChangeEvent)
    {
        AdfFacesContext.getCurrentInstance().addPartialTarget(addRqmtAreaBttn);
    }

    public void setEmployeeIdBinding(RichInputText employeeIdBinding)
    {
        this.employeeIdBinding = employeeIdBinding;
    }

    public RichInputText getEmployeeIdBinding()
    {
        return employeeIdBinding;
    }

    public void refreshEmpDedAction(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("refreshEmployeDed").execute();
    }

    public void setRqmtAreaNm(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
            if (object.toString().equals("true"))
            {
                System.out.println("object.toString()" + object.toString());
                String empname = (String) employeeNmBinding.getValue();
                String empId = (String) employeeIdBinding.getValue();
                if (empname != null)
                {
                    if (empname.length() > 7)
                        requirementAreaNmBinding.setValue(empname.substring(0, 5) + empId);
                    else
                    {
                        requirementAreaNmBinding.setValue(empname + empId);
                    }
                }
            }
            else
            {
                requirementAreaNmBinding.setValue("");
            }

    }

    public void setIsDedChkBinding(RichInputText isDedChkBinding)
    {
        this.isDedChkBinding = isDedChkBinding;
    }

    public RichInputText getIsDedChkBinding()
    {
        return isDedChkBinding;
    }

    public void setEmpSalIdBinding(RichInputText empSalIdBinding)
    {
        this.empSalIdBinding = empSalIdBinding;
    }

    public RichInputText getEmpSalIdBinding()
    {
        return empSalIdBinding;
    }

    public void setSalValBinding(RichInputText salValBinding)
    {
        this.salValBinding = salValBinding;
    }

    public RichInputText getSalValBinding()
    {
        return salValBinding;
    }

    public void salIdVC(ValueChangeEvent vce)
    {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(salValBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(isDedChkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(salTypeBinding);
    }

    public void addSubComAction(ActionEvent actionEvent)
    {
        DCIteratorBinding salSubDci = ADFBeanUtils.findIterator("HcmEmpSubSalIterator");
        if (salSubDci.getEstimatedRowCount() > 0)
        {
            if (chkSalSubValidation())
            {
                ADFBeanUtils.getOperationBinding("CreateInsert4").execute();

            }
        }
        else
        {
            ADFBeanUtils.getOperationBinding("CreateInsert4").execute();
        }
        setModeOthrDed("E");

    }

    public void deletSubComAction(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete5").execute();
    }

    public boolean chkSalSubValidation()
    {
        String message = "";
        if (subSalIdBinding.getValue() == null || subSalIdBinding.getValue().toString().length() == 0)
        {
            String cid = subSalIdBinding.getClientId();
            message = "Please select sub salary component";
            showMessage(message, cid);
            return false;
        }
        if (subSalAmntBinding.getValue() == null || subSalAmntBinding.getValue().toString().length() == 0)
        {
            String cid = subSalAmntBinding.getClientId();
            message = "Please enter notation";
            showMessage(message, cid);
            return false;
        }
        return true;
    }

    public boolean chkRefSaValidation()
    {
        String message = "";
        if (refSalIdbinding.getValue() == null || refSalIdbinding.getValue().toString().length() == 0)
        {
            String cid = refSalIdbinding.getClientId();
            message = "Please select reference salary component";
            showMessage(message, cid);
            return false;
        }
        if (refSalPercBinding.getValue() == null || refSalPercBinding.getValue().toString().length() == 0)
        {
            String cid = refSalPercBinding.getClientId();
            message = "Please enter percentage";
            showMessage(message, cid);
            return false;
        }
        if (transIsOtherDedBinding.getValue() != null && transIsOtherDedBinding.getValue().toString().equals("Y"))
        {
            String cid = refSalIdbinding.getClientId();
            message = "This salary component is other dedcution type component.";
            showMessage(message, cid);
            return false;
        }
        return true;
    }


    public void subSalIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkSubSalCompAction");
            opchk.getParamsMap().put("subSalId", object.toString());
            opchk.execute();
            if (opchk.getErrors().isEmpty())
            {
                if (opchk.getResult().toString().equals("N"))
                {
                    showFacesMessage("Duplicate Sub salary component.", "E", false, "V");
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(subSalCompTbleBinding);
        }
    }

    public void setSubSalIdBinding(RichSelectOneChoice subSalIdBinding)
    {
        this.subSalIdBinding = subSalIdBinding;
    }

    public RichSelectOneChoice getSubSalIdBinding()
    {
        return subSalIdBinding;
    }

    public void setSubSalAmntBinding(RichInputText subSalAmntBinding)
    {
        this.subSalAmntBinding = subSalAmntBinding;
    }

    public RichInputText getSubSalAmntBinding()
    {
        return subSalAmntBinding;
    }

    public void setTransIsOtherDedBinding(RichInputText transIsOtherDedBinding)
    {
        this.transIsOtherDedBinding = transIsOtherDedBinding;
    }

    public RichInputText getTransIsOtherDedBinding()
    {
        return transIsOtherDedBinding;
    }

    public void setSubSalCompTbleBinding(RichTable subSalCompTbleBinding)
    {
        this.subSalCompTbleBinding = subSalCompTbleBinding;
    }

    public RichTable getSubSalCompTbleBinding()
    {
        return subSalCompTbleBinding;
    }

    public void setSubSalPopBinding(RichPopup subSalPopBinding)
    {
        this.subSalPopBinding = subSalPopBinding;
    }

    public RichPopup getSubSalPopBinding()
    {
        return subSalPopBinding;
    }

    public void closeSubSalPopAction(ActionEvent actionEvent)
    {
        DCIteratorBinding subcom = ADFBeanUtils.findIterator("HcmEmpSubSalIterator");
        if (subcom.getEstimatedRowCount() > 0)
        {
            if (chkSalSubValidation())
            {
                OperationBinding opchk = ADFBeanUtils.getOperationBinding("calTotalSubDedAmnt");
                opchk.execute();
                subSalPopBinding.hide();
                setModeOthrDed("V");
            }
        }
        else
        {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("calTotalSubDedAmnt");
            opchk.execute();
            subSalPopBinding.hide();
            setModeOthrDed("V");
        }
    }

    public void closeRefSalPop(ActionEvent actionEvent)
    {
        DCIteratorBinding difrSumm = ADFBeanUtils.findIterator("HcmEmpSalRefIterator");
        if (difrSumm.getEstimatedRowCount() > 0)
        {
            if (chkRefSaValidation())
            {
                refSalPopBinding.hide();
            }
        }
        else
        {
            refSalPopBinding.hide();
        }
    }

    public void setRefSalPopBinding(RichPopup refSalPopBinding)
    {
        this.refSalPopBinding = refSalPopBinding;
    }

    public RichPopup getRefSalPopBinding()
    {
        return refSalPopBinding;
    }

    public void setRefSalIdbinding(RichSelectOneChoice refSalIdbinding)
    {
        this.refSalIdbinding = refSalIdbinding;
    }

    public RichSelectOneChoice getRefSalIdbinding()
    {
        return refSalIdbinding;
    }

    public void setRefSalPercBinding(RichInputText refSalPercBinding)
    {
        this.refSalPercBinding = refSalPercBinding;
    }

    public RichInputText getRefSalPercBinding()
    {
        return refSalPercBinding;
    }

    public void setReffSalTableBinding(RichTable reffSalTableBinding)
    {
        this.reffSalTableBinding = reffSalTableBinding;
    }

    public RichTable getReffSalTableBinding()
    {
        return reffSalTableBinding;
    }

    public void editOthrDedAction(ActionEvent actionEvent)
    {
        setModeOthrDed("E");
    }

    public void saveSubSalComAction(ActionEvent actionEvent)
    {
        DCIteratorBinding subcom = ADFBeanUtils.findIterator("HcmEmpSubSalIterator");
        if (subcom.getEstimatedRowCount() > 0)
        {
            if (chkSalSubValidation())
            {
                setModeOthrDed("V");
            }
        }
        else
        {
            setModeOthrDed("V");
        }
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

    public void addOtherDtlAL(ActionEvent actionEvent)
    {
        DCIteratorBinding dtlItr = ADFBeanUtils.findIterator("OrgHcmEmpOthDtlVO1Iterator");
        if (dtlItr.getEstimatedRowCount() > 0)
        {

            if (dtlItr.getCurrentRow().getAttribute("OthDtlTypId") != null)
            {
                if (dtlItr.getCurrentRow().getAttribute("EmpDocNo") != null)
                {
                    if (dtlItr.getCurrentRow().getAttribute("DocIssuePlace") != null)
                    {
                        if (dtlItr.getCurrentRow().getAttribute("DocIssueDt") != null)
                        {
                            if (dtlItr.getCurrentRow().getAttribute("DocExpDt") != null)
                            {
                                if (dtlItr.getCurrentRow().getAttribute("OthDtlTypId").equals(77) &&
                                    dtlItr.getCurrentRow().getAttribute("IssAuth") == null)
                                {
                                    String msg = resolvElDCMsg("#{bundle['MSG.1905']}").toString();
                                    showMessage(msg, issuAuthorityBinding.getClientId());
                                }
                                else
                                {
                                    ADFBeanUtils.getBindingContainer().getOperationBinding("CreateInsert5").execute();
                                }
                            }
                            else
                            {
                                String msg = resolvElDCMsg("#{bundle['MSG.1904']}").toString();
                                showMessage(msg, expDtBinding.getClientId());
                            }

                        }
                        else
                        {
                            String msg = resolvElDCMsg("#{bundle['LBL.4290']}").toString();
                            showMessage(msg, issueDtBinding.getClientId());
                        }

                    }
                    else
                    {
                        String msg = resolvElDCMsg("#{bundle['MSG.1903']}").toString();
                        showMessage(msg, issuePlaceBinding.getClientId());
                    }


                }
                else
                {
                    String msg = resolvElDCMsg("#{bundle['MSG.1902']}").toString();
                    showMessage(msg, documentNoBinding.getClientId());
                }

            }
            else
            {
                String msg = resolvElDCMsg("#{bundle['MSG.1901']}").toString();
                showMessage(msg, othDetailTypeBinding.getClientId());
            }
        }
        else
        {
            ADFBeanUtils.getBindingContainer().getOperationBinding("CreateInsert5").execute();
        }
    }

    public boolean chkOtherDtl()
    {
        boolean othDtl = true;
        DCIteratorBinding dtlItr = ADFBeanUtils.findIterator("OrgHcmEmpOthDtlVO1Iterator");
        if (dtlItr.getEstimatedRowCount() > 0)
        {

            if (dtlItr.getCurrentRow().getAttribute("OthDtlTypId") != null)
            {
                if (dtlItr.getCurrentRow().getAttribute("EmpDocNo") != null)
                {
                    if (dtlItr.getCurrentRow().getAttribute("DocIssuePlace") != null)
                    {
                        if (dtlItr.getCurrentRow().getAttribute("DocIssueDt") != null)
                        {
                            if (dtlItr.getCurrentRow().getAttribute("DocExpDt") != null)
                            {
                                /*  if ((!dtlItr.getCurrentRow().getAttribute("OthDtlTypId").equals(77)) &&
                                    dtlItr.getCurrentRow().getAttribute("IssAuth") == null) {


                                } else {
                                    String msg = resolvElDCMsg("#{bundle['MSG.1905']}").toString();
                                    showMessage(msg, issuAuthorityBinding.getClientId());
                                    othDtl = false;
                                } */


                            }
                            else
                            {
                                String msg = resolvElDCMsg("#{bundle['MSG.1904']}").toString();
                                showMessage(msg, expDtBinding.getClientId());
                                othDtl = false;
                            }

                        }
                        else
                        {
                            String msg = resolvElDCMsg("#{bundle['LBL.4290']}").toString();
                            showMessage(msg, issueDtBinding.getClientId());
                            othDtl = false;
                        }

                    }
                    else
                    {
                        String msg = resolvElDCMsg("#{bundle['MSG.1903']}").toString();
                        showMessage(msg, issuePlaceBinding.getClientId());
                        othDtl = false;
                    }


                }
                else
                {
                    String msg = resolvElDCMsg("#{bundle['MSG.1902']}").toString();
                    showMessage(msg, documentNoBinding.getClientId());
                    othDtl = false;
                }

            }
            else
            {
                String msg = resolvElDCMsg("#{bundle['MSG.1901']}").toString();
                showMessage(msg, othDetailTypeBinding.getClientId());
                othDtl = false;
            }
        }
        return othDtl;
    }

    public void documentNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && othDetailTypeBinding.getValue() != null)
        {
            String obj = object.toString().trim();
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkDuplicateDocumentNo");
            op.getParamsMap().put("docNo", obj);
            op.getParamsMap().put("dtlType", othDetailTypeBinding.getValue());
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null)
            {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y"))
                {
                    String msg = "Duplicate document no. not allowed!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void setIssuAuthorityBinding(RichInputText issuAuthorityBinding)
    {
        this.issuAuthorityBinding = issuAuthorityBinding;
    }

    public RichInputText getIssuAuthorityBinding()
    {
        return issuAuthorityBinding;
    }

    public void dtlTypeVCL(ValueChangeEvent valueChangeEvent)
    {
        // valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(issuAuthorityBinding);
    }

    public void deleteOtherDetailAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete6").execute();
    }

    public void setExpDtBinding(RichInputDate expDtBinding)
    {
        this.expDtBinding = expDtBinding;
    }

    public RichInputDate getExpDtBinding()
    {
        return expDtBinding;
    }

    public void setIssueDtBinding(RichInputDate issueDtBinding)
    {
        this.issueDtBinding = issueDtBinding;
    }

    public RichInputDate getIssueDtBinding()
    {
        return issueDtBinding;
    }

    public void setIssuePlaceBinding(RichInputText issuePlaceBinding)
    {
        this.issuePlaceBinding = issuePlaceBinding;
    }

    public RichInputText getIssuePlaceBinding()
    {
        return issuePlaceBinding;
    }

    public void setDocumentNoBinding(RichInputText documentNoBinding)
    {
        this.documentNoBinding = documentNoBinding;
    }

    public RichInputText getDocumentNoBinding()
    {
        return documentNoBinding;
    }

    public void setOthDetailTypeBinding(RichSelectOneChoice othDetailTypeBinding)
    {
        this.othDetailTypeBinding = othDetailTypeBinding;
    }

    public RichSelectOneChoice getOthDetailTypeBinding()
    {
        return othDetailTypeBinding;
    }

    public void detailTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && documentNoBinding.getValue() != null)
        {
            String doc = documentNoBinding.getValue().toString().trim();
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkDuplicateDocumentNo");
            op.getParamsMap().put("dtlType", object);
            op.getParamsMap().put("docNo", doc);
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null)
            {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y"))
                {
                    String msg = "Duplicate record not allowed!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }


    }

    public String uploadFiles()
    {
        String path = "";
        String extn = "";
        OperationBinding ob = null;
        System.out.println("inside upload file click");
        List<UploadedFile> files = this.getUploadedFile();
        if (files != null)
        {
            System.out.println("files are-->" + files);
            for (int i = 0; i < files.size(); i++)
            {

                try
                {

                    //get file extension
                    extn = files.get(i).getFilename().substring(files.get(i).getFilename().lastIndexOf("."));

                    //Add files to the Table
                    ob = ADFBeanUtils.getOperationBinding("createAttchmntRow");
                    ob.getParamsMap().put("fileNm", files.get(i).getFilename());
                    ob.getParamsMap().put("contentTyp", files.get(i).getContentType());
                    ob.getParamsMap().put("extn", extn);
                    ob.execute();
                    System.out.println("row inserted in vo");
                    if (ob.getResult() != null)
                    {
                        path = ob.getResult().toString();
                    }


                    System.out.println("extn " + extn);
                    //write files in the file system.

                    InputStream in = files.get(i).getInputStream();
                    //System.out.println(files.get(i).getInputStream());

                    //System.out.println("write file at " + path + extn);
                    FileOutputStream out = new FileOutputStream(path + extn);
                    byte[] buffer = new byte[8192];
                    int bytesRead = 0;

                    while ((bytesRead = in.read(buffer, 0, 8192)) != -1)
                    {
                        out.write(buffer, 0, bytesRead);
                    }
                    in.close();
                    out.close();

                    //call commit after checking all validations

                    ADFBeanUtils.getOperationBinding("Commit").execute();
                }
                catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                }
            }
        }
        //ADFBeanUtils.getOperationBinding("Commit").execute();
        //remove the files to prepare for new uploads
        setUploadedFile(null);

        return null;
    }

    public void downloadFileListener(FacesContext facesContext, OutputStream outputStream) throws IOException
    {
        //Read file from particular path, path bind is binding of table field that contains path
        String path = resolvEl("#{row.AttchFlPath}");


        File file = new File(path);

        FileInputStream fis;
        byte[] b;
        try
        {
            fis = new FileInputStream(file);

            int n;
            while ((n = fis.available()) > 0)
            {
                b = new byte[n];
                int result = fis.read(b);
                outputStream.write(b, 0, b.length);
                if (result == -1)
                    break;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        outputStream.flush();
    }

    public void attachmentDeleteAL(ActionEvent actionEvent)
    {
        Object filePath = actionEvent.getComponent().getAttributes().get("pathWithName");
        String rowKey = actionEvent.getComponent().getAttributes().get("rowKey").toString();
        System.out.println("Key Value : " + rowKey);
        if (filePath != null)
        {
            //System.out.println("File Path : "+filePath.toString());
            OperationBinding binding =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("deleteAttachFileRow");
            binding.getParamsMap().put("path", filePath.toString());
            binding.execute();
            ADFBeanUtils.findOperation("Commit").execute();
        }
    }

    public void addDependentDtlAL(ActionEvent actionEvent)
    {
        System.out.println("inside add");
        DCIteratorBinding dtlItr = ADFBeanUtils.findIterator("HcmEmpDpndDtlVO1Iterator");
        if (dtlItr.getEstimatedRowCount() > 0)
        {
            if (dtlItr.getCurrentRow().getAttribute("MemNm") != null)
            {
                if (dtlItr.getCurrentRow().getAttribute("MemRel") != null)
                {
                    if (dtlItr.getCurrentRow().getAttribute("MemGen") != null)
                    {
                        System.out.println("before create insert");
                        ADFBeanUtils.getBindingContainer().getOperationBinding("CreateInsert6").execute();
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_OFF_DTL", "A");
                        OperationBinding opexec = ADFBeanUtils.getOperationBinding("executeLovVoInOffcialDtl");
                        opexec.execute();
                    }
                    else
                    {
                        String msg =
                            "Member gender can not be left blank!"; //resolvElDCMsg("#{bundle['MSG.1903']}").toString();
                        showMessage(msg, memberGenderBinding.getClientId());
                    }
                }
                else
                {
                    String msg = "Relation with employee can not be left blank!"; //resolvElDCMsg("  ").toString();
                    showMessage(msg, memberRelationBinding.getClientId());
                }
            }
            else
            {
                String msg = "Dependent name can not be left balnk!"; //resolvElDCMsg(" ").toString();
                showMessage(msg, memberNameBinding.getClientId());
            }
        }
        else
        {
            ADFBeanUtils.getBindingContainer().getOperationBinding("CreateInsert6").execute();
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_OFF_DTL", "A");
            OperationBinding opexec = ADFBeanUtils.getOperationBinding("executeLovVoInOffcialDtl");
            opexec.execute();
        }
    }

    public boolean chkdependentDtl()
    {
        boolean depDtl = true;
        DCIteratorBinding dtlItr = ADFBeanUtils.findIterator("HcmEmpDpndDtlVO1Iterator");
        if (dtlItr.getEstimatedRowCount() > 0)
        {
            if (dtlItr.getCurrentRow().getAttribute("MemNm") != null)
            {
                if (dtlItr.getCurrentRow().getAttribute("MemRel") != null)
                {
                    if (dtlItr.getCurrentRow().getAttribute("MemGen") != null)
                    {

                    }
                    else
                    {
                        String msg =
                            "Member gender can not be left blank!"; //resolvElDCMsg("#{bundle['MSG.1903']}").toString();
                        showMessage(msg, memberGenderBinding.getClientId());
                        depDtl = false;

                    }
                }
                else
                {
                    String msg = "Relation with employee can not be left blank!"; //resolvElDCMsg("  ").toString();
                    showMessage(msg, memberRelationBinding.getClientId());
                    depDtl = false;

                }
            }
            else
            {
                String msg = "Dependent name can not be left balnk!"; //resolvElDCMsg(" ").toString();
                showMessage(msg, memberNameBinding.getClientId());
                depDtl = false;
            }
        }
        return depDtl;
    }

    public void deleteDependentDetail(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete7").execute();
    }

    public void setMemberNameBinding(RichInputText memberNameBinding)
    {
        this.memberNameBinding = memberNameBinding;
    }

    public RichInputText getMemberNameBinding()
    {
        return memberNameBinding;
    }

    public void setMemberRelationBinding(RichInputText memberRelationBinding)
    {
        this.memberRelationBinding = memberRelationBinding;
    }

    public RichInputText getMemberRelationBinding()
    {
        return memberRelationBinding;
    }

    public void setMemberGenderBinding(RichSelectOneChoice memberGenderBinding)
    {
        this.memberGenderBinding = memberGenderBinding;
    }

    public RichSelectOneChoice getMemberGenderBinding()
    {
        return memberGenderBinding;
    }

    public void memberNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            String name = object.toString().trim();
            OperationBinding op = ADFBeanUtils.getBindingContainer().getOperationBinding("chkDuplicateDependentName");
            op.getParamsMap().put("nm", name);
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null)
            {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y"))
                {
                    String msg = "Duplicate dependent name not allowed!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }

        }
    }

    public void memberDobVCL(ValueChangeEvent valueChangeEvent)
    {
        // Add event code here...
    }

    public void issueDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && othDetailTypeBinding.getValue() != null)
        {
            Timestamp DT = (Timestamp) object;
            OperationBinding ob = ADFBeanUtils.getOperationBinding("chDockIssuDt");
            ob.getParamsMap().put("newIssueDate", DT);
            ob.getParamsMap().put("dtlType", othDetailTypeBinding.getValue());
            ob.execute();
            System.out.println("ob===" + ob.getResult());
            if (ob.getErrors().isEmpty() && ob.getResult() != null)
            {
                String rslt = ob.getResult().toString();
                if (rslt.equalsIgnoreCase("Y"))
                {
                    String msg = "Issue Date should be greater than previous expiry date for document!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
        else
        {
            String msgIssueDate = "Fill Document details";
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msgIssueDate, null));
        }
    }

    public void userIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            OperationBinding ob = ADFBeanUtils.getOperationBinding("checkDuplicateUser");
            ob.getParamsMap().put("usrId", object);
            ob.execute();
            if (ob.getErrors().isEmpty() && ob.getResult() != null)
            {
                String rslt = ob.getResult().toString();
                if (rslt.equalsIgnoreCase("Y"))
                {
                    String msg = "Duplicate user not allowed!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
            else
            {
                ob.getErrors();
            }
        }

    }


    public void addMedicalIssueAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("CreateInsert7").execute();
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_OFF_DTL", "A");
    }

    public void addPastExpAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("CreateInsert8").execute();
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_OFF_DTL", "A");
    }

    public void addLanguageAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("CreateInsert9").execute();
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_OFF_DTL", "A");
    }

    public void addQualifAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("CreateInsert10").execute();
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("MODE_OFF_DTL", "A");
    }

    public void deletePastExpAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete9").execute();
    }

    public void deleteLanguageAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete10").execute();
    }

    public void deleteQualiAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete11").execute();
    }

    public void deleteMedicalIssueAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete8").execute();
    }

    public void medicalIssueNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            String issueNm = object.toString().trim();

            OperationBinding op = ADFBeanUtils.getBindingContainer().getOperationBinding("chkDuplicateMedicalIssue");
            op.getParamsMap().put("issuName", issueNm);
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null)
            {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y"))
                {
                    String msg = "Duplicate medical issue not allowed!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void wrkStatusVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            if (valueChangeEvent.getNewValue().equals(31) && noticePrdBinding.getValue() != null)
            {
                Integer notPrd = (Integer) noticePrdBinding.getValue();
                wrkStatDtBinding.setValue(new Timestamp(System.currentTimeMillis()));
                AdfFacesContext.getCurrentInstance().addPartialTarget(wrkStatDtBinding);
                try
                {
                    Timestamp resdt = (Timestamp) wrkStatDtBinding.getValue();
                    Date res = null;
                    try
                    {
                        res = resdt.dateValue();
                    }
                    catch (Exception e) {
                    }
                    OperationBinding binding = ADFBeanUtils.getOperationBinding("calcRelvngDate");
                    binding.getParamsMap().put("days", notPrd);
                    binding.getParamsMap().put("resDt", res);
                    binding.execute();
                    System.out.println("result to set relvng date---" + binding.getResult());
                    relevngDateBinding.setValue((Timestamp) binding.getResult());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(relevngDateBinding);
                }
                catch (Exception e) {
                }

            }
            else
            {
                relevngDateBinding.setValue(null);
            }
        }
    }

    public void setNoticePrdBinding(RichInputText noticePrdBinding)
    {
        this.noticePrdBinding = noticePrdBinding;
    }

    public RichInputText getNoticePrdBinding()
    {
        return noticePrdBinding;
    }

    public void setRelevngDateBinding(RichInputDate relevngDateBinding)
    {
        this.relevngDateBinding = relevngDateBinding;
    }

    public RichInputDate getRelevngDateBinding()
    {
        return relevngDateBinding;
    }


    public void wrkStatusDateVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            if (noticePrdBinding.getValue() != null)
            {
                if (workStatusBinding.getValue().equals(31))
                {
                    System.out.println("resigend in wrkstatdate vcl");
                    Integer notPrd = (Integer) noticePrdBinding.getValue();
                    oracle.jbo.domain.Date dt2 = null;
                    try
                    {
                        Timestamp resdt = (Timestamp) wrkStatDtBinding.getValue();
                        Date res = null;
                        try
                        {
                            res = resdt.dateValue();
                        }
                        catch (Exception e) {
                        }
                        OperationBinding binding = ADFBeanUtils.getOperationBinding("calcRelvngDate");
                        binding.getParamsMap().put("days", notPrd);
                        binding.getParamsMap().put("resDt", res);
                        binding.execute();
                        relevngDateBinding.setValue((Timestamp) binding.getResult());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(relevngDateBinding);
                    }
                    catch (Exception e) {
                    }
                }
                else
                    relevngDateBinding.setValue(null);
            }
        }
    }

    public void setLeaveBalanceBinding(RichInputText leaveBalanceBinding)
    {
        this.leaveBalanceBinding = leaveBalanceBinding;
    }

    public RichInputText getLeaveBalanceBinding()
    {
        return leaveBalanceBinding;
    }

    public void leaveOpBalVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            Object newBal = valueChangeEvent.getNewValue();
            leaveBalanceBinding.setValue(newBal);
            AdfFacesContext.getCurrentInstance().addPartialTarget(leaveBalanceBinding);
        }
    }

    public void noticePrdVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && wrkStatDtBinding.getValue() != null)
        {
            if (workStatusBinding.getValue().equals(31))
            {
                System.out.println("resigend in notie vcl");
                Integer notPrd = (Integer) valueChangeEvent.getNewValue();
                if (notPrd.compareTo(new Integer(0)) == 0)
                {
                    relevngDateBinding.setValue(((Timestamp) wrkStatDtBinding.getValue()));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(relevngDateBinding);
                }
                else
                {
                    Timestamp resdt = (Timestamp) wrkStatDtBinding.getValue();
                    Date res = null;
                    try
                    {
                        res = resdt.dateValue();
                    }
                    catch (SQLException e) {
                    }
                    OperationBinding binding = ADFBeanUtils.getOperationBinding("calcRelvngDate");
                    binding.getParamsMap().put("days", notPrd);
                    binding.getParamsMap().put("resDt", res);
                    binding.execute();
                    System.out.println("result to set relvng date---" + binding.getResult());
                    relevngDateBinding.setValue((Timestamp) binding.getResult());
                }
            }
        }
        else
            relevngDateBinding.setValue(null);
    }

    public void setSalGradeBinding(RichSelectOneChoice salGradeBinding)
    {
        this.salGradeBinding = salGradeBinding;
    }

    public RichSelectOneChoice getSalGradeBinding()
    {
        return salGradeBinding;
    }

    public void designationVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0)
        {
            //            OperationBinding opChkGradeLinking = ADFBeanUtils.getOperationBinding("setEmployeeGrade");
            //            opChkGradeLinking.getParamsMap().put("desgId", valueChangeEvent.getNewValue());
            //            opChkGradeLinking.execute();

        }
    }

    public void setEmpDesigBinding(RichSelectOneChoice empDesigBinding)
    {
        this.empDesigBinding = empDesigBinding;
    }

    public RichSelectOneChoice getEmpDesigBinding()
    {
        return empDesigBinding;
    }

    public void insertGradeSalStructAL(ActionEvent actionEvent)
    {
        if (empDesigBinding.getValue() != null && empDesigBinding.getValue() != "")
        {
            OperationBinding opChkGradeLinking = ADFBeanUtils.getOperationBinding("setEmployeeGrade");
            opChkGradeLinking.getParamsMap().put("desgId", (String) empDesigBinding.getValue());
            opChkGradeLinking.execute();
        }

    }

    public void setGradePayChkBiinding(RichOutputText gradePayChkBiinding)
    {
        this.gradePayChkBiinding = gradePayChkBiinding;
    }

    public RichOutputText getGradePayChkBiinding()
    {
        return gradePayChkBiinding;
    }

    public String headEmpCostCenterAction()
    {
        OperationBinding binding = (OperationBinding) ADFBeanUtils.getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult())
        {
            return "goToCostCenter";
        }
        else
        {
            String msg1 = "Cost Center Not Applicable for this Profile. Please update the Profile Center Structure";
            FacesMessage msg = new FacesMessage(msg1);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
            System.out.println("Not Applicable");
        }
        return null;
    }

    public void MarkobtenValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            Integer per = (Integer) object;
            if (per.compareTo(0) <= 0 || per.compareTo(100) > 0)
            {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Marks Obtain Value",
                                                              null));
            }
        }
    }

    public void empTypeVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0)
        {
            OperationBinding opChkGradeLinking = ADFBeanUtils.getOperationBinding("setEmpNoticePrdNDProbPrd");
            opChkGradeLinking.getParamsMap().put("empType", valueChangeEvent.getNewValue());
            opChkGradeLinking.execute();
            if (opChkGradeLinking.getErrors().isEmpty() && opChkGradeLinking.getResult() != "")
            {

                if (opChkGradeLinking.getResult().toString().equals("Y"))
                {

                }
                else
                {
                    System.out.println("No probaation period found");
                }
            }

        }
    }


    public String callCtcAction()
    {
        // Add event code here...
        return "GotoCtcDetails";
    }
}

