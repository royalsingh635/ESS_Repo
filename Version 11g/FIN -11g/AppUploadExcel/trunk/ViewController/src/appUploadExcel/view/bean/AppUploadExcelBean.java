package appUploadExcel.view.bean;


import appUploadExcel.model.service.AppUploadExcelAMImpl;

import appUploadExcel.model.views.AppXlRowErrorVOImpl;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.jbo.JboException;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class AppUploadExcelBean {
    private RichPopup errorPopup;

    public AppUploadExcelBean() {
    }
    private UploadedFile _file;
    private Number FileId = new Number(0);
    private Integer value = 1;

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
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
    private static int NUMBER = Types.INTEGER;
    private static int STRING = Types.VARCHAR;

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {

            // 1. Create a JDBC CallabledStatement
            AppUploadExcelAMImpl am = (AppUploadExcelAMImpl)resolvElDC("AppUploadExcelAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                // 3. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 4. Set the value of user-supplied bind vars in the stmt
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            // 6. Return the value of the first bind variable
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private String getVal() {

        return (String)callStoredFunction(STRING, "APP.GETITEMIMAGEPATH()", new Object[] { });
    }

    private Number getFileId() {
        Number retVal = new Number(0);
        try {
            retVal = new Number(callStoredFunction(NUMBER, "APP.PKG_XL_UPLD.FN_UPLD_FILE_ID", new Object[] { }));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ;
        return retVal;
    }

    public String UploadAction() {


        UploadedFile myfile = getFile();
        if (myfile == null) {
            String valMsg = resolvEl("#{bundle['MSG.173']}");
            FacesMessage msg = new FacesMessage(valMsg);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        } else {
            try {
                String Id = resolvEl("#{pageFlowScope.ID}");
                Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                String SessionId = "1";
                Integer UserId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
                Integer DocId = Integer.parseInt(resolvEl("#{pageFlowScope.DOC_ID}"));
                FileId = getFileId();


                String fileType = myfile.getContentType();
                System.out.println(fileType);
                System.out.println(myfile.getFilename());
                System.out.println(myfile.getLength());


                if (getFile().getFilename().endsWith("xls") || getFile().getFilename().endsWith("xlsx") ||
                    getFile().getFilename().endsWith("csv")) {


                    if (getFile().getFilename().endsWith("xls")) {
                        try {
                            AppUploadExcelAMImpl am = (AppUploadExcelAMImpl)resolvElDC("AppUploadExcelAMDataControl");

                            ViewObjectImpl v1 = am.getAppXlFileRows1();
                            HSSFWorkbook workbook = new HSSFWorkbook(myfile.getInputStream());

                            HSSFSheet sheet = workbook.getSheetAt(0);


                            //Iterating Row
                            Iterator<Row> rowIterator = sheet.iterator();
                            int countRow = 1;
                            while (rowIterator.hasNext()) {
                                Row row = rowIterator.next();

                                if (countRow == 1) {

                                    Iterator<Cell> cellIterator = row.cellIterator();

                                    String Value = "";

                                    int count = 0;
                                    while (cellIterator.hasNext()) {
                                        Cell cell = cellIterator.next();
                                        switch (cell.getCellType()) {
                                        case Cell.CELL_TYPE_BOOLEAN:
                                            Boolean BoolValue = cell.getBooleanCellValue();


                                            Value = BoolValue.toString();
                                            break;
                                        case Cell.CELL_TYPE_NUMERIC:
                                            Double dValue = cell.getNumericCellValue();
                                            Value = dValue.toString();
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            Value = cell.getStringCellValue();
                                            break;
                                        case Cell.CELL_TYPE_BLANK:
                                            Value = "";
                                            break;
                                        case Cell.CELL_TYPE_ERROR:
                                            Value = "Error value";
                                            break;
                                        case Cell.CELL_TYPE_FORMULA:
                                            Double doValue = cell.getNumericCellValue();
                                            Value = doValue.toString();
                                            break;
                                        }
                                        System.out.println("Value-->" + Value + "<---");
                                        if (count == 0 && Value.equals("")) {

                                            String valMsg = resolvEl("#{bundle['MSG.174']}");
                                            FacesMessage msg = new FacesMessage(valMsg);
                                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext ctx = FacesContext.getCurrentInstance();
                                            ctx.addMessage(null, msg);
                                            value = 1;
                                            return null;


                                        }
                                        count = count + 1;

                                    }

                                }


                                //Reading Xls File


                                else if (countRow > 1) {
                                    //For each row, iterate through each columns


                                    String Column1 = Id;
                                    String Column2 = "";
                                    String Column3 = "";
                                    String Column4 = "";
                                    String Column5 = "";
                                    String Column6 = "";
                                    String Column7 = "";
                                    String Column8 = "";
                                    String Column9 = "";
                                    String Column10 = "";
                                    String Column11 = "";
                                    String Column12 = "";
                                    String Column13 = "";
                                    String Column14 = "";
                                    String Column15 = "";
                                    String Column16 = "";
                                    String Column17 = "";
                                    String Column18 = "";
                                    String Column19 = "";
                                    String Column20 = "";

                                    Iterator<Cell> cellIterator = row.cellIterator();
                                    int count = 0;
                                    while (cellIterator.hasNext()) {


                                        Cell cell = cellIterator.next();

                                        String Value = "";
                                        switch (cell.getCellType()) {
                                        case Cell.CELL_TYPE_BOOLEAN:
                                            Boolean BoolValue = cell.getBooleanCellValue();


                                            Value = BoolValue.toString();
                                            break;
                                        case Cell.CELL_TYPE_NUMERIC:
                                            Double dValue = cell.getNumericCellValue();
                                            Value = dValue.toString();
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            Value = cell.getStringCellValue();
                                            break;
                                        case Cell.CELL_TYPE_BLANK:
                                            Value = "";
                                            break;
                                        case Cell.CELL_TYPE_ERROR:
                                            Value = "Error value";
                                            break;
                                        case Cell.CELL_TYPE_FORMULA:
                                            Double doValue = cell.getNumericCellValue();
                                            Value = doValue.toString();
                                            break;
                                        }


                                        if (count == 0) {

                                            Column2 = Value;

                                        } else if (count == 1) {
                                            Column3 = Value;

                                        } else if (count == 2) {
                                            Column4 = Value;
                                        } else if (count == 3) {
                                            Column5 = Value;
                                        } else if (count == 4) {
                                            Column6 = Value;
                                        } else if (count == 5) {
                                            Column7 = Value;
                                        } else if (count == 6) {
                                            Column8 = Value;
                                        } else if (count == 7) {

                                            Column9 = Value;
                                        } else if (count == 8) {

                                            Column10 = Value;
                                        } else if (count == 9) {
                                            Column11 = Value;
                                        } else if (count == 10) {
                                            Column12 = Value;
                                        } else if (count == 11) {
                                            Column13 = Value;
                                        } else if (count == 12) {
                                            Column14 = Value;
                                        } else if (count == 13) {
                                            Column15 = Value;
                                        } else if (count == 14) {
                                            Column16 = Value;
                                        } else if (count == 15) {
                                            Column17 = Value;
                                        } else if (count == 16) {
                                            Column18 = Value;
                                        } else if (count == 17) {
                                            Column19 = Value;
                                        } else if (count == 18) {
                                            Column20 = Value;
                                        }
                                        count = count + 1;
                                    }


                                    oracle.jbo.Row newRw = v1.createRow();

                                    newRw.setAttribute("FileId", FileId);
                                    newRw.setAttribute("RowId1", countRow - 1);
                                    newRw.setAttribute("Col1", Column1);
                                    newRw.setAttribute("Col2", Column2);
                                    newRw.setAttribute("Col3", Column3);
                                    newRw.setAttribute("Col4", Column4);
                                    newRw.setAttribute("Col5", Column5);
                                    newRw.setAttribute("Col6", Column6);
                                    newRw.setAttribute("Col7", Column7);
                                    newRw.setAttribute("Col8", Column8);
                                    newRw.setAttribute("Col9", Column9);
                                    newRw.setAttribute("Col10", Column10);
                                    newRw.setAttribute("Col11", Column11);
                                    newRw.setAttribute("Col12", Column12);
                                    newRw.setAttribute("Col13", Column13);
                                    newRw.setAttribute("Col14", Column14);
                                    newRw.setAttribute("Col15", Column15);
                                    newRw.setAttribute("Col16", Column16);
                                    newRw.setAttribute("Col17", Column17);
                                    newRw.setAttribute("Col18", Column18);
                                    newRw.setAttribute("Col19", Column19);
                                    newRw.setAttribute("Col20", Column20);

                                    v1.insertRow(newRw);
                                }
                                countRow = countRow + 1;
                            }

                            myfile.getInputStream().close();


                            ViewObjectImpl v2 = am.getAppXlFile1();

                            //Insert Into APP$XL$FILE
                            oracle.jbo.Row FileRw = v2.createRow();

                            FileRw.setAttribute("FileId", FileId);
                            FileRw.setAttribute("SlocId", SlocId);
                            FileRw.setAttribute("OrgId", OrgId);
                            FileRw.setAttribute("CldId", CldId);
                            FileRw.setAttribute("SessionId", SessionId);
                            FileRw.setAttribute("DocId", DocId);
                            FileRw.setAttribute("FileName", getFile().getFilename());
                            FileRw.setAttribute("RowCount", 0);
                            FileRw.setAttribute("UploadBy", UserId);
                            FileRw.setAttribute("UploadDate", Date.getCurrentDate());
                            FileRw.setAttribute("Status", "I");
                            FileRw.setAttribute("Remark", "Initialised");

                            v2.insertRow(FileRw);
                            am.getDBTransaction().commit();

                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }


                    } //end of xls upload

                    //Check for xlsx file and read it.
                    else if (getFile().getFilename().endsWith("xlsx")) {
                        try {


                            AppUploadExcelAMImpl am = (AppUploadExcelAMImpl)resolvElDC("AppUploadExcelAMDataControl");

                            ViewObjectImpl v1 = am.getAppXlFileRows1();


                            //Reading xlsx File


                            OPCPackage pkg = OPCPackage.open(myfile.getInputStream());


                            XSSFWorkbook workbook = new XSSFWorkbook(pkg);
                            XSSFSheet sheet = workbook.getSheetAt(0);


                            //Iterating Row
                            Iterator<Row> rowIterator = sheet.iterator();

                            int countRow = 1;
                            while (rowIterator.hasNext()) {
                                Row row = rowIterator.next();

                                //For each row, iterate through each columns
                                if (countRow == 1) {
                                    Iterator<Cell> cellIterator = row.cellIterator();
                                    int count = 0;
                                    while (cellIterator.hasNext()) {


                                        Cell cell = cellIterator.next();

                                        String Value = "";
                                        switch (cell.getCellType()) {
                                        case Cell.CELL_TYPE_BOOLEAN:
                                            Boolean BoolValue = cell.getBooleanCellValue();


                                            Value = BoolValue.toString();
                                            break;
                                        case Cell.CELL_TYPE_NUMERIC:
                                            Double dValue = cell.getNumericCellValue();
                                            Value = dValue.toString();
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            Value = cell.getStringCellValue();
                                            break;
                                        case Cell.CELL_TYPE_BLANK:
                                            Value = "";
                                            break;
                                        case Cell.CELL_TYPE_ERROR:
                                            Value = "Error value";
                                            break;
                                        case Cell.CELL_TYPE_FORMULA:
                                            Double doValue = cell.getNumericCellValue();
                                            Value = doValue.toString();
                                            break;
                                        }
                                        System.out.println("Value-->" + Value + "<---");
                                        if (count == 0 && Value.equals("")) {
                                            String valMsg = resolvEl("#{bundle['MSG.174']}");
                                            FacesMessage msg = new FacesMessage(valMsg);
                                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext ctx = FacesContext.getCurrentInstance();
                                            ctx.addMessage(null, msg);
                                            value = 1;
                                            return null;


                                        }
                                        count = count + 1;
                                    }
                                } else if (countRow > 1) {
                                    String Column1 = Id;
                                    String Column2 = "";
                                    String Column3 = "";
                                    String Column4 = "";
                                    String Column5 = "";
                                    String Column6 = "";
                                    String Column7 = "";
                                    String Column8 = "";
                                    String Column9 = "";
                                    String Column10 = "";
                                    String Column11 = "";
                                    String Column12 = "";
                                    String Column13 = "";
                                    String Column14 = "";
                                    String Column15 = "";
                                    String Column16 = "";
                                    String Column17 = "";
                                    String Column18 = "";
                                    String Column19 = "";
                                    String Column20 = "";

                                    Iterator<Cell> cellIterator = row.cellIterator();
                                    int count = 0;
                                    while (cellIterator.hasNext()) {


                                        Cell cell = cellIterator.next();

                                        String Value = "";
                                        switch (cell.getCellType()) {
                                        case Cell.CELL_TYPE_BOOLEAN:
                                            Boolean BoolValue = cell.getBooleanCellValue();


                                            Value = BoolValue.toString();
                                            break;
                                        case Cell.CELL_TYPE_NUMERIC:
                                            Double dValue = cell.getNumericCellValue();
                                            Value = dValue.toString();
                                            break;
                                        case Cell.CELL_TYPE_STRING:
                                            Value = cell.getStringCellValue();
                                            break;
                                        case Cell.CELL_TYPE_BLANK:
                                            Value = "";
                                            break;
                                        case Cell.CELL_TYPE_ERROR:
                                            Value = "Error value";
                                            break;
                                        case Cell.CELL_TYPE_FORMULA:
                                            Double doValue = cell.getNumericCellValue();
                                            Value = doValue.toString();
                                            break;
                                        }


                                        if (count == 0) {

                                            Column2 = Value;

                                        } else if (count == 1) {
                                            Column3 = Value;

                                        } else if (count == 2) {
                                            Column4 = Value;
                                        } else if (count == 3) {
                                            Column5 = Value;
                                        } else if (count == 4) {
                                            Column6 = Value;
                                        } else if (count == 5) {
                                            Column7 = Value;
                                        } else if (count == 6) {
                                            Column8 = Value;
                                        } else if (count == 7) {

                                            Column9 = Value;
                                        } else if (count == 8) {

                                            Column10 = Value;
                                        } else if (count == 9) {
                                            Column11 = Value;
                                        } else if (count == 10) {
                                            Column12 = Value;
                                        } else if (count == 11) {
                                            Column13 = Value;
                                        } else if (count == 12) {
                                            Column14 = Value;
                                        } else if (count == 13) {
                                            Column15 = Value;
                                        } else if (count == 14) {
                                            Column16 = Value;
                                        } else if (count == 15) {
                                            Column17 = Value;
                                        } else if (count == 16) {
                                            Column18 = Value;
                                        } else if (count == 17) {
                                            Column19 = Value;
                                        } else if (count == 18) {
                                            Column20 = Value;
                                        }
                                        count = count + 1;
                                    }


                                    oracle.jbo.Row newRw = v1.createRow();

                                    newRw.setAttribute("FileId", FileId);
                                    newRw.setAttribute("RowId1", countRow - 1);
                                    newRw.setAttribute("Col1", Column1);
                                    newRw.setAttribute("Col2", Column2);
                                    newRw.setAttribute("Col3", Column3);
                                    newRw.setAttribute("Col4", Column4);
                                    newRw.setAttribute("Col5", Column5);
                                    newRw.setAttribute("Col6", Column6);
                                    newRw.setAttribute("Col7", Column7);
                                    newRw.setAttribute("Col8", Column8);
                                    newRw.setAttribute("Col9", Column9);
                                    newRw.setAttribute("Col10", Column10);
                                    newRw.setAttribute("Col11", Column11);
                                    newRw.setAttribute("Col12", Column12);
                                    newRw.setAttribute("Col13", Column13);
                                    newRw.setAttribute("Col14", Column14);
                                    newRw.setAttribute("Col15", Column15);
                                    newRw.setAttribute("Col16", Column16);
                                    newRw.setAttribute("Col17", Column17);
                                    newRw.setAttribute("Col18", Column18);
                                    newRw.setAttribute("Col19", Column19);
                                    newRw.setAttribute("Col20", Column20);

                                    v1.insertRow(newRw);
                                }
                                countRow = countRow + 1;

                            }

                            myfile.getInputStream().close();

                            ViewObjectImpl v2 = am.getAppXlFile1();

                            //Insert Into APP$XL$FILE
                            oracle.jbo.Row FileRw = v2.createRow();

                            FileRw.setAttribute("FileId", FileId);
                            FileRw.setAttribute("SlocId", SlocId);
                            FileRw.setAttribute("OrgId", OrgId);
                            FileRw.setAttribute("CldId", CldId);
                            FileRw.setAttribute("SessionId", SessionId);
                            FileRw.setAttribute("DocId", DocId);
                            FileRw.setAttribute("FileName", getFile().getFilename());
                            FileRw.setAttribute("RowCount", 0);
                            FileRw.setAttribute("UploadBy", UserId);
                            FileRw.setAttribute("UploadDate", Date.getCurrentDate());
                            FileRw.setAttribute("Status", "I");
                            FileRw.setAttribute("Remark", "Initialised");

                            v2.insertRow(FileRw);


                            am.getDBTransaction().commit();

                        } catch (InvalidFormatException a) {
                            System.out.println(a.getMessage());

                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }


                    } else if (getFile().getFilename().endsWith("csv")) {
                        try {
                            System.out.println(getFile().getLength());
                            AppUploadExcelAMImpl am = (AppUploadExcelAMImpl)resolvElDC("AppUploadExcelAMDataControl");

                            ViewObjectImpl v1 = am.getAppXlFileRows1();


                            String path = getVal() + myfile.getFilename();

                            InputStream inputStream = myfile.getInputStream();


                            //to save filr in another directory..............

                            FileOutputStream outputFile = new FileOutputStream(path);

                            for (int bytes = 0; bytes < myfile.getLength(); bytes++) {

                                outputFile.write(inputStream.read());

                            }

                            inputStream.close();
                            outputFile.close();


                            CSVReader csvReader = new CSVReader(new FileReader(path));
                            List content = csvReader.readAll();

                            int countRow = 1;
                            for (Object object : content) {
                                String[] row = (String[])object;

                                if (countRow == 1) {

                                    for (int a = 0; a < row.length; a++) {
                                        System.out.println("Value-->" + row[0] + "<---");
                                        if (row[0].equals("")) {
                                            String valMsg = resolvEl("#{bundle['MSG.174']}");
                                            FacesMessage msg = new FacesMessage(valMsg);
                                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext ctx = FacesContext.getCurrentInstance();
                                            ctx.addMessage(null, msg);
                                            value = 1;
                                            return null;
                                        }
                                    }
                                } else if (countRow > 1) {
                                    String[] value = new String[19];

                                    for (int a = 0; a < row.length; a++) {

                                        value[a] = row[a];
                                    }


                                    oracle.jbo.Row newRw = v1.createRow();

                                    newRw.setAttribute("FileId", FileId);
                                    newRw.setAttribute("RowId1", countRow - 1);
                                    newRw.setAttribute("Col1", Id);
                                    newRw.setAttribute("Col2", value[0]);
                                    newRw.setAttribute("Col3", value[1]);
                                    newRw.setAttribute("Col4", value[2]);
                                    newRw.setAttribute("Col5", value[3]);
                                    newRw.setAttribute("Col6", value[4]);
                                    newRw.setAttribute("Col7", value[5]);
                                    newRw.setAttribute("Col8", value[6]);
                                    newRw.setAttribute("Col9", value[7]);
                                    newRw.setAttribute("Col10", value[8]);
                                    newRw.setAttribute("Col11", value[9]);
                                    newRw.setAttribute("Col12", value[10]);
                                    newRw.setAttribute("Col13", value[11]);
                                    newRw.setAttribute("Col14", value[12]);
                                    newRw.setAttribute("Col15", value[13]);
                                    newRw.setAttribute("Col16", value[14]);
                                    newRw.setAttribute("Col17", value[15]);
                                    newRw.setAttribute("Col18", value[16]);
                                    newRw.setAttribute("Col19", value[17]);
                                    newRw.setAttribute("Col20", value[18]);

                                    v1.insertRow(newRw);


                                }

                                countRow = countRow + 1;

                            }


                            myfile.getInputStream().close();
                            ViewObjectImpl v2 = am.getAppXlFile1();

                            //Insert Into APP$XL$FILE
                            oracle.jbo.Row FileRw = v2.createRow();

                            FileRw.setAttribute("FileId", FileId);
                            FileRw.setAttribute("SlocId", SlocId);
                            FileRw.setAttribute("OrgId", OrgId);
                            FileRw.setAttribute("CldId", CldId);
                            FileRw.setAttribute("SessionId", SessionId);
                            FileRw.setAttribute("DocId", DocId);
                            FileRw.setAttribute("FileName", getFile().getFilename());
                            FileRw.setAttribute("RowCount", 0);
                            FileRw.setAttribute("UploadBy", UserId);
                            FileRw.setAttribute("UploadDate", Date.getCurrentDate());
                            FileRw.setAttribute("Status", "I");
                            FileRw.setAttribute("Remark", "Initialised");

                            v2.insertRow(FileRw);
                            am.getDBTransaction().commit();
                            csvReader.close();
                            File deleteFile = new File(path);
                            // check if the file is present or not

                            if (deleteFile.exists()) {
                                deleteFile.delete();
                            }

                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }


                    value = validateUploadedFile(FileId);
                    System.out.println(value);
                    if (value == 1) {
                        String valMsg = resolvEl("#{bundle['MSG.177']}");
                        FacesMessage msg = new FacesMessage(valMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, msg);
                    } else if (value == -1) {
                        String valMsg = resolvEl("#{bundle['MSG.179']}");
                        FacesMessage msg = new FacesMessage(valMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, msg);


                    }

                } else {

                    String valMsg = resolvEl("#{bundle['MSG.180']}");
                    FacesMessage msg = new FacesMessage(valMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                }
            }
            
            catch (NullPointerException e) {
                        String valMsg = resolvEl("#{bundle['MSG.180']}");
                        FacesMessage msg = new FacesMessage(valMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_WARN);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, msg);
                    }
        }
        AppUploadExcelAMImpl am = (AppUploadExcelAMImpl)resolvElDC("AppUploadExcelAMDataControl");
        am.getDBTransaction().commit();
        am.getDBTransaction().rollback();
        System.out.println(value);

        this.setFile(null);
        return null;
    }

    public String ViewErrorAction() {
        if (value == -1) {
            AppUploadExcelAMImpl am = (AppUploadExcelAMImpl)resolvElDC("AppUploadExcelAMDataControl");

            AppXlRowErrorVOImpl v = am.getAppXlRowError1();
            v.setFileIdBindVar(FileId);
            v.setNamedWhereClauseParam("FileIdBindVar", FileId);
            v.executeQuery();
            showPopup(errorPopup, true);
        }


        return null;
    }

    private Integer validateUploadedFile(Number FileId) {
        Integer retVal =
            (Integer)callStoredFunction(NUMBER, "APP.PKG_XL_UPLD.FN_TVOULINES_VALIDATE(?)", new Object[] { FileId });

        return retVal;
    }

    public void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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

    public void setFile(UploadedFile _file) {
        this._file = _file;
    }

    public UploadedFile getFile() {
        return _file;
    }


    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setErrorPopup(RichPopup errorPopup) {
        this.errorPopup = errorPopup;
    }

    public RichPopup getErrorPopup() {
        return errorPopup;
    }
}
