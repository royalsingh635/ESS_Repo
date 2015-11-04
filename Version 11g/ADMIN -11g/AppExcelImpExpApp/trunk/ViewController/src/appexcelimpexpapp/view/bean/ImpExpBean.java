package appexcelimpexpapp.view.bean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.ListIterator;
import java.util.Map;

import java.util.Map.Entry;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.SortableModel;
import org.apache.myfaces.trinidad.model.UploadedFile;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ImpExpBean {
    /**
     *  Developed by Shubham Bansal
     *
     *  Here i am using poi Api for reading the Excel content
     */
    private UploadedFile file;
    private SortableModel model;
    private List<String> columns;
    private List<Map> rowData;
    private RichTable table;
    private List<SelectItem> excelColName;
    private RichSelectOneChoice excelColNameLovBind;
    private List AllinsertionRows;
    String error = "";
    private RichSelectOneChoice dbColBind;

    public ImpExpBean() {
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void uploadAction(ActionEvent actionEvent) {
        if (file == null) {
            showFacesMessage("Please Select an Excel File !!", FacesMessage.SEVERITY_ERROR, null);
            return;
        }
        String filename = file.getFilename();
        int index = filename.lastIndexOf(".");
        String fileType = filename.substring(index+1,filename.length());
        System.out.println("File type is: "+fileType);
        if(!fileType.equalsIgnoreCase("XLS")) {
            showFacesMessage("Please Select an Excel File !!", FacesMessage.SEVERITY_ERROR, null);
            return;
        }
        try {
            InputStream inputStream = file.getInputStream();
            //  System.out.println(inputStream);
            HSSFWorkbook workBook = new HSSFWorkbook(inputStream);

            HSSFSheet at = workBook.getSheetAt(0);
            HSSFRow headerRow = at.getRow(0);
            int cellCount = headerRow.getPhysicalNumberOfCells();
            columns = new ArrayList<String>();
            excelColName = new ArrayList<SelectItem>(); //  for showing excel col name on pop up.
            for (int i = 0; i < cellCount; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell == null) {
                    cellCount += 1;
                    continue;
                } else {
                    columns.add("" + cell);
                    boolean add = excelColName.add(new SelectItem("" + cell, "" + cell));
                    System.out.println("Added : " + add);
                }
            }

            //       System.out.println("Total number of columns are: " + columns.size());

            int excelRows = at.getPhysicalNumberOfRows();
            if (excelRows > 1) {
                rowData = new ArrayList<Map>();

                for (int i = 1; i < excelRows; i++) {
                    HSSFRow fRow = at.getRow(i);
                    int cells = fRow.getPhysicalNumberOfCells();
                    //Map map = new HashMap();
                    Map map = new LinkedHashMap();
                    for (int j = 0; j < columns.size(); j++) {
                        Cell cell = fRow.getCell(j);
                        if (cell == null) {
                            map.put(columns.get(j), null);
                        } else {
                            switch (cell.getCellType()) {
                            case 0:
                                map.put(columns.get(j), cell.getNumericCellValue());
                                break;
                            case 1:
                                map.put(columns.get(j), cell.getStringCellValue());
                                break;
                            case 3:
                                map.put(columns.get(j), null);
                                break;
                            }
                        }
                    }
                    //   System.out.println("done");

                    rowData.add(map);
                }
                this.model = new SortableModel(rowData);
            }
            //columns
        } catch (IOException io) {
            io.printStackTrace();
            showFacesMessage("error is occuring !!\n" +
                    io, FacesMessage.SEVERITY_ERROR, null);
        }
        setFile(null);
    }

    private void showFacesMessage(String msg, FacesMessage.Severity ser, String clientId) {
        FacesMessage fc = new FacesMessage(msg);
        fc.setSeverity(ser);
        //        facesContext.addMessage(clientId, fc);
        FacesContext.getCurrentInstance().addMessage(clientId, fc);
    }

    public CollectionModel getModel() {
        return model;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setRowData(List<Map> rowData) {
        this.rowData = rowData;
    }

    public List<Map> getRowData() {
        return rowData;
    }

    public void shoeDataAction(ActionEvent actionEvent) {
        // Add event code here...
        Iterator<Map> itr = rowData.iterator();
        while (itr.hasNext()) {
            Map map = itr.next();
            System.out.println("map is:  " + map);
        }
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }


    public void check(ActionEvent actionEvent) {
        // Add event code here...
        Collection<String> collection = table.getSelectedColumns();
        Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("Row data is " + table.getSelectedRowData());
        Map tmap = (Map)table.getSelectedRowData();
        boolean b = rowData.contains(tmap);
        System.out.println("Is exists:  " + b);
    }

    public void deleteRowAction(ActionEvent actionEvent) {
        // Add event code here...
        Map tmap = (Map)table.getSelectedRowData();
        boolean b = rowData.contains(tmap);
        System.out.println("Is exists:  " + b);
        boolean b_2 = rowData.remove(tmap);
        System.out.println("Deleted :  " + b_2);
    }

    public void setExcelColName(List<SelectItem> excelColName) {
        this.excelColName = excelColName;
    }

    public List<SelectItem> getExcelColName() {
        return excelColName;
    }

    public void AddtableAction(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("A1 :"+dbColBind.getValue()+"--");
        System.out.println("A2 :"+excelColNameLovBind.getValue()+"--");
        if ( excelColNameLovBind.getValue() != null && (!dbColBind.getValue().toString().equalsIgnoreCase("")) ) {
            getBinding().getOperationBinding("CreateInsert").execute();
            OperationBinding binding = getBinding().getOperationBinding("insertTabColName");
            binding.getParamsMap().put("excelColName", excelColNameLovBind.getValue().toString());
            binding.execute();
        }
    }

    public BindingContainer getBinding() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setExcelColNameLovBind(RichSelectOneChoice excelColNameLovBind) {
        this.excelColNameLovBind = excelColNameLovBind;
    }

    public RichSelectOneChoice getExcelColNameLovBind() {
        return excelColNameLovBind;
    }

    public void dialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        getBinding().getOperationBinding("Commit").execute();
    }

    public void TableDeleteAction(ActionEvent actionEvent) {
        // Add event code here...
        getBinding().getOperationBinding("Delete").execute();
        getBinding().getOperationBinding("Execute").execute();

    }

    public void insertintoTableAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding binding = getBinding().getOperationBinding("insertTableData"); //dataList
        binding.getParamsMap().put("dataList", rowData);
        Object execute = binding.execute();
        System.out.println("Execute is: " + execute);

        if (execute != null) {
            List list = (List)execute;
            if (list.size() > 0) {
                try {

                    FileOutputStream out = new FileOutputStream("D:\\Error.CSV");
                    String clmName = columns.toString();
                    System.out.println("Columns are:  " + clmName);
                    error += clmName.substring(1, clmName.length() - 1);
                    error += "\n";
                    System.out.println("Error is: " + error);

                    out.write(clmName.substring(1, clmName.length() - 1).getBytes());
                    out.write("\n".getBytes());
                    ListIterator<Map> itr = list.listIterator();
                    while (itr.hasNext()) {
                        Map<String, Object> map = itr.next();
                        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
                        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();

                        while (iterator.hasNext()) {
                            Map.Entry<String, Object> entry = iterator.next();
                            Object object = entry.getValue();

                            if (object == null) {
                                error += ",";
                            } else {
                                error += object.toString() + ",";
                            }
                        }
                        out.write(error.getBytes());
                        out.write("\n".getBytes());
                        error += "\n";
                        //    System.out.println("list id: orf error:  " + list);
                    }
                    out.close();
                    //  writeToStream(error);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Object execute_2 = getBinding().getOperationBinding("getInsertionRows").execute();
        System.out.println("Result is:  " + execute_2);
        if (execute_2 != null) {
            AllinsertionRows = (List)execute_2;
        }
        System.out.println("data isL " + execute_2);
        getBinding().getOperationBinding("Rollback").execute();
    }

    public ServletOutputStream getStream() throws Exception {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse res = (HttpServletResponse)context.getResponse();
        ServletOutputStream outputStream = res.getOutputStream();
        return outputStream;

    }

    public void writeToStream(String data) throws Exception {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext context = fc.getExternalContext();
        HttpServletResponse res = (HttpServletResponse)context.getResponse();
        ServletOutputStream outputStream = res.getOutputStream();
        res.setContentType("text/plain");
        res.setHeader("Content-Disposition", "attachment; filename=Price.txt");
        System.out.println("opening !!!");
        //  ServletOutputStream outputStream = this.getStream();
        outputStream.write(data.getBytes());
        outputStream.flush();
        outputStream.close();
        fc.responseComplete();
        System.out.println("Written==");
    }

    public void badFileDownloadAction(FacesContext facesContext,
                                      java.io.OutputStream outputStream) throws IOException {
        // Add event code here...
        System.out.println("Error file data is:  \n" +
                error);
        outputStream.write(error.getBytes());
        outputStream.flush();
        outputStream.close();
        error = "";
    }

    public List returnAllinsertionRows() {
        return (java.util.List)getBinding().getOperationBinding("getInsertionRows").execute();
    }

    public String returnToprvPage() {
        // Add event code here...
       // Map paramMap = RequestContext.getCurrentInstance().getPageFlowScope();
     //   paramMap.put("GLBL_INSERTION_ROWS", returnAllinsertionRows());
        getBinding().getOperationBinding("Rollback").execute();
        return "retunPAGE";
    }

    public void setAllinsertionRows(List AllinsertionRows) {
        this.AllinsertionRows = AllinsertionRows;
    }

    public List getAllinsertionRows() {
        // return (java.util.List)getBinding().getOperationBinding("getInsertionRows").execute();
        return AllinsertionRows;
        // return new ArrayList();
    }

    public void setDbColBind(RichSelectOneChoice dbColBind) {
        this.dbColBind = dbColBind;
    }

    public RichSelectOneChoice getDbColBind() {
        return dbColBind;
    }
}
