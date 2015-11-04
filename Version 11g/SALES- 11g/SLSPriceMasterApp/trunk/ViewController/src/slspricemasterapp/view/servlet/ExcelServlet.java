package slspricemasterapp.view.servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelServlet extends HttpServlet {
    Connection con;
    Statement st;
    ResultSet rs;
    ServletOutputStream output;
    String fixQuery =
    "SELECT DISTINCT APP.APP$SLS$ITM_VW.ITM_DESC,  APP.APP$UOM$CONV$STD.UOM_DESC," + "A.BASE_PRICE,  A.MIN_PRICE,  A.MRP_RATE," +
    "DECODE(A.MRP_TYP,'A','Amt', 'Aa', 'Add', 'Catag', 'Category', 'Cust', 'Customer', 'N', 'No', 'P', 'Per', 'S', 'Subtract', 'Y', 'Yes') AS MRP_TYP," +
    "A.MRP_PRICE,  to_char(A.EFFECTIVE_DT,'dd-Mon-yyyy') AS EFFECTIVE_DT," +
    "to_char(A.EXPIRY_DT,'dd-Mon-yyyy')    AS EXPIRY_DT FROM SLS$EO$PRICE A,  APP.APP$SLS$ITM_VW," +
    "APP.APP$UOM$CONV$STD WHERE APP.APP$SLS$ITM_VW.ITM_ID    = A.ITM_ID AND" +
    " APP.APP$SLS$ITM_VW.CLD_ID      = A.CLD_ID AND" +
    " APP.APP$SLS$ITM_VW.SLOC_ID     = A.SLOC_ID AND " +
    "APP.APP$SLS$ITM_VW.HO_ORG_ID   = A.HO_ORG_ID AND APP.APP$SLS$ITM_VW.ORG_ID      = A.ORG_ID AND " +
    "APP.APP$UOM$CONV$STD.CLD_ID    =A.CLD_ID AND APP.APP$UOM$CONV$STD.SLOC_ID   =A.SLOC_ID " +
    "AND APP.APP$UOM$CONV$STD.UOM_ID    = A.ITM_UOM AND";

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        output = res.getOutputStream();
        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment; filename=Price.xls");
        HttpSession session = req.getSession();
        String param = session.getAttribute("param").toString();
        String query = session.getAttribute("query").toString();
        session.removeAttribute("param");
        session.removeAttribute("query");
        
        int index = query.indexOf("WHERE");
        String newQuery = getBindValue(query.substring(index + 6, query.length()));
        //      System.out.println("Came in servlet");

        StringTokenizer token = new StringTokenizer(param, "{=}, ");
        Map<String, String> map = new HashMap<String, String>();
        while (token.hasMoreTokens()) {
            String key = token.nextToken();
            String value = token.nextToken();
            map.put(key, value);
        }
System.out.println("New query is: "+newQuery);
        String s = fixQuery + newQuery;
        StringBuilder completeQuey = new StringBuilder(s);
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            String find = ":" + key;
            int i = completeQuey.indexOf(find);
            if (i >= 0)
                completeQuey.replace(i, i + find.length(), "'" + map.get(key).toString() + "'");
            //      System.out.println("find "+i);
        }
        //     System.out.println(completeQuey);

        //      System.out.println("Complete Query is: \n"+completeQuey.toString());


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        
        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
                

        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i < 9; i++) {
            Cell cell = createRow.createCell(i);
           // sheet.autoSizeColumn(i);
           // sheet.setColumnWidth(100+i,100);
            switch (i) {
            case 0:
                cell.setCellValue("Item Name");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("Item UOM");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("Base Price");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("Minimum Price");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("MRP Rate");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("MRP Type");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("MRP Price");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("Effective Date");
                cell.setCellStyle(cellStyle);
                break;
            case 8:
                cell.setCellValue("Expiry Date");
                cell.setCellStyle(cellStyle);
                break;
            }
        }

        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/SLSDS");
            System.out.println("Query is: "+completeQuey.toString());
            con = ds.getConnection();
            st = con.createStatement();
            ResultSet rs = st.executeQuery(completeQuey.toString());
            int rownum = 1;
            while (rs.next()) {
                HSSFRow row = sheet.createRow(rownum++);
                for (int i = 1; i <= 9; i++) {
                    Cell cell = row.createCell(i - 1);
                    cell.setCellValue((String)rs.getString(i));
                }
            }
            workbook.write(output);
            output.flush();
            output.close();
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private String getBindValue(String bindVal) {
        String name = bindVal;
        String tbName = "SLS$EO$PRICE";
        int count = 0;
        int first = 0, last = 0;
        String query = "";
        for (int i = 0; i < name.length(); i++) {
            if (i + 5 < name.length() && name.charAt(i) == '(' && name.charAt(i + 1) == 'U' &&
                name.charAt(i + 2) == 'P' && name.charAt(i + 3) == 'P' && name.charAt(i + 4) == 'E' &&
                name.charAt(i + 5) == 'R') {
                query += "(UPPER";
                i = i + 5;
            } else if (i + 1 < name.length() &&
                       ((name.charAt(i) == '(' && name.charAt(i + 1) >= 'a' && name.charAt(i + 1) <= 'z') ||
                        (name.charAt(i) == '(' && name.charAt(i + 1) >= 'A' && name.charAt(i + 1) <= 'Z'))) {
                count++;
                first = i + 1;
                for (int j = i + 1; j < name.length(); j++) {
                    if (name.charAt(j) == ' ' || j == name.length() - 1 || name.charAt(j) == ')') {
                        i = j - 1;
                        last = j - 1;
                        break;
                    }
                }
                //        System.out.println(first + "  " + last + "  " + name.substring(first, last + 1));
                query += "(" + tbName + "." + name.substring(first, last + 1);
            } else {
                query += name.charAt(i);
            }
        }
        // System.out.println(count);
        //  System.out.println("Quey is: \n" +
        ///          query);
        return query;
    }
}
