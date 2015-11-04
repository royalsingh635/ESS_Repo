package opbalforallcoa.view.bean;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.jbo.domain.Number;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;


import opbalforallcoa.model.module.OpBalCoasAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ValidationException;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class OpbalForAllCoas {
    private String mode = "V";
    private RichInputText opBalPgBind;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
    private RichTable tabBind;
    Integer count = Integer.parseInt(getBindings().getOperationBinding("on_load_page").execute().toString());
    private RichSelectOneChoice fyBindVar;
    private RichInputListOfValues bindCoaId;
    private String Cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

    public OpbalForAllCoas() {
    }

    public String resetAction() {
        mode = "V";
        BindingContainer bindings = getBindings();
        OperationBinding createOpBAddrees = bindings.getOperationBinding("resetAction");
        createOpBAddrees.execute();
        fyBindVar.setValue("");
        bindCoaId.setValue("");
        return null;
    }

    public String searchAction() {

        mode = "V";
        BindingContainer bindings = getBindings();
        OperationBinding createOpBAddrees = bindings.getOperationBinding("searchAction");
        createOpBAddrees.execute();

        return null;
    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }


     public void validatorListener(FacesContext facesContext, UIComponent uIComponent, Object object) {


    //    System.out.println("Object : "+object);


        if (object != null) {
            System.out.println("Validator Initiating...............");

            oracle.jbo.domain.Number n = (oracle.jbo.domain.Number) object;
            Boolean isValid = isPrecisionValid(26, 6, n);
            if (n.compareTo(Number.zero()) < 0) {

                 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              (String) resolvEl("#{bundle['MSG.553']}"), null)); 
                
            }  /*  else if (n.compareTo(Number.zero()) == 0) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              (String) resolvEl("#{bundle['MSG.1332']}"), null));

            } */  else if (!isValid) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              (String) resolvEl("#{bundle['MSG.57']}"), null));

            }
        }
    } 


    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            OpBalCoasAMImpl am = (OpBalCoasAMImpl) resolvElDC("OpBalCoasAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            return st.getObject(1);

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }


    public void setBindCoaId(RichInputListOfValues bindCoaId) {
        this.bindCoaId = bindCoaId;
    }

    public RichInputListOfValues getBindCoaId() {
        return bindCoaId;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setTabBind(RichTable tabBind) {
        this.tabBind = tabBind;
    }

    public RichTable getTabBind() {
        return tabBind;
    }

    public void setFyBindVar(RichSelectOneChoice fyBindVar) {
        this.fyBindVar = fyBindVar;
    }

    public RichSelectOneChoice getFyBindVar() {
        return fyBindVar;
    }


    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void opEditActionListener(ActionEvent actionEvent) {
        Integer fyId = null;
        if (fyBindVar.getValue() != null) {
            fyId = Integer.parseInt(fyBindVar.getValue().toString());
            System.out.println("fyBindVar Value-----" + fyId);
        }

        Integer fyIdParam = null;
        if (resolvEl("#{pageFlowScope.PARAM_FY_ID}") != null) {
            fyIdParam = Integer.parseInt(resolvEl("#{pageFlowScope.PARAM_FY_ID}").toString());
            System.out.println("FY ID for param---" + fyIdParam);
        } else {
            fyIdParam = Integer.parseInt(fyId.toString());
            System.out.println("In else FY ID for param---" + fyIdParam);
        }

        String Org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();


        // called function when fy_allowe_op_bal_for_next_year and prev year isn't check in Organization setup.
        Object allowForOrg = callStoredFunction(Types.VARCHAR, "fin.fn_CHK_OP_BAL_ALL_ORG(?,?,?)", new Object[] {
                                                Cld_Id, Org_Id, fyId
        });
        System.out.println("allowForOrg--------------------------------" + allowForOrg);
        if (allowForOrg != null) {
            if (allowForOrg.toString().equalsIgnoreCase("N")) {
                FacesMessage Message = new FacesMessage("Opening Balance is not allowed for this Organizations...!!!");
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, Message);
            } else {
                mode = "E";
            }
        }
    }

    public void opSaveAction(ActionEvent actionEvent) {
        
                BindingContainer bindings = getBindings();
                OperationBinding createOpBAddrees = bindings.getOperationBinding("Commit");
                createOpBAddrees.execute();
                mode = "V";
    }


    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();

    /** For Exporting data with FY_Id and Coa_Id. **/
    public void exportToExcelActionListener(FacesContext facesContext, OutputStream outputStream) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);


        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i <= 5; i++) {
            HSSFCell cell = createRow.createCell(i);

            switch (i) {
            case 0:
                cell.setCellValue("COA_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("CHART_OF_ACCOUNT");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("OPENING_BALANCE");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("OPENING_BALANCE_TYPE");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("FY_ID");
                cell.setCellStyle(cellStyle);
                break;

            }

        }
        String OrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String HoOrgId = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String CldId = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItr = (DCIteratorBinding) dcBindings.get("OrgCoaFy1Iterator");
        ViewObjectImpl object = (ViewObjectImpl) dcItr.getViewObject();
        RowQualifier rq = new RowQualifier(object);

        rq.setWhereClause("OrgCoaCldId='" + CldId + "' and OrgSlocId=" + SlocId + " and OrgCoaHoOrgId='" + HoOrgId +
                          "' and OrgId='" + OrgId + "'");

        Row[] fr = object.getFilteredRows(rq);
        RowSetIterator itr = (RowSetIterator) object.createRowSetIterator(null);
        int rownum = 1;

        for (Row next : fr) {

            HSSFRow row = sheet.createRow(rownum++);
            Object coaIdObj = next.getAttribute("OrgCoaId");
            Object coaNmObj = next.getAttribute("CoaNm");
            Object opBalObj = next.getAttribute("OrgCoaOpBal");
            Object opBalTypeObj = next.getAttribute("OrgCoaOpBalTyp");
            Object fyIdObj = next.getAttribute("OrgFyId");

            StringBuilder coaId = (coaIdObj == null ? new StringBuilder("") : new StringBuilder(coaIdObj.toString()));
            StringBuilder coaNm = (coaNmObj == null ? new StringBuilder("") : new StringBuilder(coaNmObj.toString()));
            StringBuilder opBal = (opBalObj == null ? new StringBuilder("") : new StringBuilder(opBalObj.toString()));
            StringBuilder oPBalType =
                (opBalTypeObj == null ? new StringBuilder("") : new StringBuilder(opBalTypeObj.toString()));
            StringBuilder fyId = (fyIdObj == null ? new StringBuilder("") : new StringBuilder(fyIdObj.toString()));
            StringBuilder blank = new StringBuilder("");


            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            Cell cell4 = row.createCell(4);

            if (coaIdObj != null && coaIdObj.toString().length() > 0)
                cell0.setCellValue(new Double(coaIdObj.toString()));
            else
                cell0.setCellValue(blank.toString());

            cell1.setCellValue(coaNm.toString());

            if (opBalObj != null && opBalObj.toString().length() > 0)
                cell2.setCellValue(new Double(opBalObj.toString()));
            else
                cell2.setCellValue(blank.toString());

            cell3.setCellValue(oPBalType.toString());

            if (fyIdObj != null && fyIdObj.toString().length() > 0)
                cell4.setCellValue(new Double(fyIdObj.toString()));
            else
                cell4.setCellValue(blank.toString());

            System.out.println("Row added ___________________" + rownum);

            for (int i = 0; i <= 5; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setOpBalPgBind(RichInputText opBalPgBind) {
        this.opBalPgBind = opBalPgBind;
    }

    public RichInputText getOpBalPgBind() {
        return opBalPgBind;
    }
}
