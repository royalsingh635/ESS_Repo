package hcmparameterapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import hcmparameterapp.model.views.ExportParamVORowImpl;

import java.io.OutputStream;

import java.sql.SQLException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Timestamp;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;


public class ParameterBean {
    private RichInputText paramNameBinding;
    private String mode = "D";
    private RichTable paramTblBinding;
    private RichInputDate startDateBinding;
    private RichSelectOneChoice orgNameBinding;
    private RichInputDate orgStartDtBinding;
    String newdt = "";
    private RichOutputText paramIdBndingi;
    private RichInputText attIdBinding;
    private RichInputListOfValues paramValueBinding;

    public void setNewdt(String newdt) {
        this.newdt = newdt;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String getNewdt() {
        return newdt;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public ParameterBean() {
    }


    public void searchParamAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("searchAction").execute();
    }

    public void resetParamAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("resetSearchpanel").execute();
    }

    public void addParamAL(ActionEvent actionEvent) {
        Object execute = ADFBeanUtils.getOperationBinding("chkDocumentInOrg").execute();
        if (execute != null && execute.equals(true)) {
            ADFBeanUtils.getOperationBinding("CreateInsert").execute();
            ADFBeanUtils.getOperationBinding("setCurrDate").execute();
            ADFBeanUtils.getOperationBinding("genParamId").execute();
            this.mode = "A";

        } else {
            //  FacesMessage message = new FacesMessage(resolvElDCMsg("This Document has not been added in the Organization !!").toString());
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1868']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void saveBtnAL(ActionEvent actionEvent) {
        if (this.mode == "DEL") {
            ADFBeanUtils.getOperationBinding("Commit").execute();
            ADFBeanUtils.getOperationBinding("execHcmSetup").execute();
            //FacesMessage message = new FacesMessage("Parameter deleted successfully.");
            String msg = resolvElDCMsg("#{bundle['MSG.1358']}").toString();
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            this.mode = "D";
        } else {
            if (startDateBinding.getValue() != null && startDateBinding.getValue().toString().length() > 0) {
                if (this.mode == "A") {
                    //  ADFBeanUtils.getOperationBinding("genParamId").execute();










                }
                ADFBeanUtils.getOperationBinding("Commit").execute();
                ADFBeanUtils.getOperationBinding("execHcmSetup").execute();
                //  FacesMessage message = new FacesMessage("Parameter saved successfully.");
                String msg = resolvElDCMsg("#{bundle['MSG.1359']}").toString();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                this.mode = "D";
            } else {
                // FacesMessage message = new FacesMessage("Start Date is mandatory!");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1360']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(startDateBinding.getClientId(), message);
            }
        }
    }

    public void cancelBtnAL(ActionEvent actionEvent) {
        DCIteratorBinding itr = ADFBeanUtils.findIterator("ParameterType1Iterator");
        Key parentKey = itr.getCurrentRow().getKey();
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        ADFBeanUtils.getOperationBinding("Execute").execute();
        itr.setCurrentRowWithKey(parentKey.toStringFormat(true));
        this.mode = "D";
    }

    public void paramNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            // Commented By Vishal Kr on 19-08-2015 Not working Properly
            // String pt = "([a-zA-Z0-9_]+)(([ ])([a-zA-Z0-9_]+))*";
            System.out.println("paramid"+attIdBinding.getValue());
           
            if(!attIdBinding.getValue().toString().equals("102"))
            {
           
            object = object.toString().replaceAll("\\s+", " ");
            String pt = "([a-zA-Z0-9-][a-zA-Z0-9-\\.]+)(([ ])([a-zA-Z0-9-\\.]+))*";
            Pattern ptt = null;          
            ptt = ptt.compile(pt);
            object = object.toString().trim();
            Matcher matcher = ptt.matcher(object.toString());
            if (!matcher.matches()) {
                // String msg = "Not a valid name.Special character/spaces not allowed!";
                String msg = resolvElDCMsg("#{bundle['MSG.1399']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            
            OperationBinding op = ADFBeanUtils.getOperationBinding("paramNameValidator");
            op.getParamsMap().put("pName", object);
            op.execute();

            if (op.getResult() != null && op.getErrors().isEmpty()) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    // String msg = "Duplicate parameter name not allowed!";
                    String msg = resolvElDCMsg("#{bundle['MSG.1400']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        
            paramNameBinding.setValue(object);
            }            
            
        }
    }

    public void setParamNameBinding(RichInputText paramNameBinding) {
        this.paramNameBinding = paramNameBinding;
    }

    public RichInputText getParamNameBinding() {
        return paramNameBinding;
    }

    public void editParamAL(ActionEvent actionEvent) {
        this.mode = "E";
    }


    public void setStartDateBinding(RichInputDate startDateBinding) {
        this.startDateBinding = startDateBinding;
    }

    public RichInputDate getStartDateBinding() {
        return startDateBinding;
    }

    public void linkOrgAL(ActionEvent actionEvent) {
        DCIteratorBinding itr = ADFBeanUtils.findIterator("OrgHcmSetup1Iterator");
        if (itr.getEstimatedRowCount() == 0) {
            ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
        } else {
            if (orgNameBinding.getValue() != null && orgNameBinding.getValue().toString().length() > 0) {
                if (orgStartDtBinding.getValue() != null && orgStartDtBinding.getValue().toString().length() > 0) {
                    ADFBeanUtils.getOperationBinding("CreateInsert1").execute();

                } else {
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1360']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(orgStartDtBinding.getClientId(), message);
                }
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1118']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(orgNameBinding.getClientId(), message);
                // AdfFacesContext.getCurrentInstance().addPartialTarget(orgNameBinding);
            }
        }
    }

    public void setOrgNameBinding(RichSelectOneChoice orgNameBinding) {
        this.orgNameBinding = orgNameBinding;
    }

    public RichSelectOneChoice getOrgNameBinding() {
        return orgNameBinding;
    }

    public void setOrgStartDtBinding(RichInputDate orgStartDtBinding) {
        this.orgStartDtBinding = orgStartDtBinding;
    }

    public RichInputDate getOrgStartDtBinding() {
        return orgStartDtBinding;
    }

    public void orgStartDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (startDateBinding.getValue() != null && startDateBinding.getValue().toString().length() > 0) {
            Timestamp s = (Timestamp) startDateBinding.getValue();
            try {
                String date = s.dateValue().toString();
                newdt = date.substring(0, 10);
            } catch (SQLException e) {
            }
        }
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date orgstartDt = null;
            if (startDateBinding.getValue() != null && startDateBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) startDateBinding.getValue()).dateValue();
                    orgstartDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                }
                if (strtDt.compareTo(orgstartDt) > 0) {
                    if (strtDt.toString().equals(orgstartDt.toString())) {
                    } else {
                        //showFacesMessage("Start date can not be before parameter start Date.", "E", false, "V");
                        showFacesMessage("MSG.1401", "E", true, "V");
                    }
                }
            }

            Timestamp sd = (Timestamp) object;
            OperationBinding op = ADFBeanUtils.getOperationBinding("orgSdateValid");
            op.getParamsMap().put("sd", sd);
            op.execute();
            if (op.getResult() != null && op.getErrors().isEmpty()) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    //  String msg = "Start date should be less than or equal to parameter end date!!";
                    String msg = resolvElDCMsg("#{bundle['MSG.1402']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }

        }
    }

    public void orgEndDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Timestamp ed = (Timestamp) object;
            OperationBinding op = ADFBeanUtils.getOperationBinding("orgEndDtValid");
            op.getParamsMap().put("ed", ed);
            op.execute();
            if (op.getResult() != null && op.getErrors().isEmpty()) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    // String msg = "End Date should be less than or equal to parameter end date!!";
                    String msg = resolvElDCMsg("#{bundle['MSG.1403']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void paramNameVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            paramNameBinding.setValue(valueChangeEvent.getNewValue().toString().trim());
            AdfFacesContext.getCurrentInstance().addPartialTarget(paramNameBinding);
        }
    }

    public void deleteParamAL(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("chkDeleteParam");
        ob.execute();
        if (ob.getErrors().isEmpty() && ob.getResult() != null) {
            String rslt = ob.getResult().toString();
            if (rslt.equalsIgnoreCase("Y")) {
                ADFBeanUtils.getOperationBinding("Delete").execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(paramNameBinding);
                this.mode = "DEL";
            } else {
                // FacesMessage message = new FacesMessage("Multiple references found, can't delete this parameter!!");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1404']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
    }

    public void paramEndDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (startDateBinding.getValue() != null && startDateBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) startDateBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        //showFacesMessage("End Date can not be before Start Date.", "E", false, "V");
                        showFacesMessage("MSG.1338", "E", true, "V");
                    }
                }
            }
        }
    }

    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg) {
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
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public Object resolvEl(String data) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, data, Object.class);
        return exp.getValue(elContext);
    }

    public void replicateToAllAL(ActionEvent actionEvent) {

        ADFBeanUtils.getOperationBinding("replicateToAll").execute();

    }

    public void orgNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding op = ADFBeanUtils.getOperationBinding("orgLinkValidator");
            op.getParamsMap().put("org", object.toString());
            op.execute();
            if (op.getResult() != null && op.getErrors().isEmpty()) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    //String msg = "Duplicate organization name not allowed!";
                    String msg = resolvElDCMsg("#{bundle['MSG.1405']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            } else {
                System.out.println("errror--" + op.getErrors());
            }

        }
    }

    public void getExportDataAction(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        Object execute = ADFBeanUtils.getOperationBinding("getExportRowSetItr").execute();
        if (execute != null) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Sheet1");

            HSSFCellStyle cellStyle = workbook.createCellStyle();

            HSSFFont font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyle.setFont(font);

            HSSFRow createRow = sheet.createRow(0);
            for (int i = 0; i < 3; i++) {
                Cell cell = createRow.createCell(i);
                // sheet.autoSizeColumn(i);
                // sheet.setColumnWidth(100+i,100);
                switch (i) {
                case 0:
                    cell.setCellValue("Parameter Type");
                    cell.setCellStyle(cellStyle);
                    break;
                case 1:
                    cell.setCellValue("Parameter Id");
                    cell.setCellStyle(cellStyle);
                    break;
                case 2:
                    cell.setCellValue("Parameter Name");
                    cell.setCellStyle(cellStyle);
                    break;
                }
            }
            int rownum = 1;
            RowSetIterator itr = (RowSetIterator) execute;
            while (itr.hasNext()) {
                ExportParamVORowImpl next = (ExportParamVORowImpl) itr.next();
                String typName = next.getParamTypNm();
                String paramId = next.getParamId();
                String paraDesc = next.getParamDesc();

                HSSFRow row = sheet.createRow(rownum++);
                for (int i = 0; i < 3; i++) {
                    Cell cell = row.createCell(i);
                    switch (i) {
                    case 0:
                        cell.setCellValue(typName);
                        break;
                    case 1:
                        cell.setCellValue(paramId == null ? null : paramId);
                        break;
                    case 2:
                        cell.setCellValue(paraDesc == null ? null : paraDesc);
                        break;
                    }
                }
            }

            try {
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            itr.closeRowSetIterator();
        }
    }

    public void setParamIdBndingi(RichOutputText paramIdBndingi) {
        this.paramIdBndingi = paramIdBndingi;
    }

    public RichOutputText getParamIdBndingi() {
        return paramIdBndingi;
    }

    public void setAttIdBinding(RichInputText attIdBinding) {
        this.attIdBinding = attIdBinding;
    }

    public RichInputText getAttIdBinding() {
        return attIdBinding;
    }

    public void setParamValueBinding(RichInputListOfValues paramValueBinding) {
        this.paramValueBinding = paramValueBinding;
    }

    public RichInputListOfValues getParamValueBinding() {
        return paramValueBinding;
    }

    public void paramTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null)
        {
            paramValueBinding.setValue("");
            }
    }
}
